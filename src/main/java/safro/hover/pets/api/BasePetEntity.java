package safro.hover.pets.api;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.TrackOwnerAttackerGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import safro.hover.pets.util.PetUtil;
import safro.hover.pets.util.RespawnAccess;

public abstract class BasePetEntity extends TameableEntity {
    private int bobbingage;

    public BasePetEntity(EntityType<? extends BasePetEntity> entityType, World world) {
        super(entityType, world);
        this.bobbingage = this.random.nextInt(100000);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(10, new LookAroundGoal(this));
        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
    }

    public static DefaultAttributeContainer.Builder createPetAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30000001192092896D).add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D);
    }

    public boolean isCollidable() {
        return false;
    }

    public boolean isPushable() {
        return false;
    }

    public boolean isPushedByFluids() {
        return false;
    }

    public boolean collidesWith(Entity entity) {
        return !this.hasOwner() || !(entity instanceof LivingEntity other) || !this.isOwner(other);
    }

    public int getNextAirUnderwater(int air) {
        return air;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    public boolean damage(DamageSource source, float amount) {
        if (PetUtil.isImmune(source)) {
            return false;
        }
        return super.damage(source, amount);
    }

    @Override
    public boolean isInsideWall() {
        return false;
    }

    @Override
    public void tick() {
        ++bobbingage;
        super.tick();
        if (this.hasOwner()) {
            PlayerEntity owner = (PlayerEntity) this.getOwner();
            PetUtil.setPet(owner, this.getId());
            this.tickPerk(world, owner);

            if (!world.isClient) {
                if (((RespawnAccess) owner).isReadyForRespawn()) {
                    this.teleport(owner.getX(), owner.getY(), owner.getZ(), false);
                }
            }
        }
    }

    public boolean hasOwner() {
        return this.getOwner() != null;
    }

    public int getBounce() {
        return bobbingage;
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (this.isOwner(player) && !player.isSneaking()) {
            this.onRemoved(world, player);
            if (!player.getInventory().insertStack(getPetStack())) {
                player.dropItem(getPetStack(), false);
            }
            PetUtil.setPet(player, 0);
            this.remove(RemovalReason.DISCARDED);
            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }

    public void kill() {
        if (this.hasOwner() && this.getOwner() instanceof PlayerEntity player) {
            PetUtil.setPet(player, 0);
        }
        super.kill();
    }

    /**
     * Used for selecting the pet item that drops when the pet is despawned
     *
     * @return - Returns an ItemStack (Usually new ItemStack(...);)
     */
    public abstract ItemStack getPetStack();

    /**
     * This method is called every tick while the pet has an owner
     * Most pets use/need this method for their perk functions
     *
     * @param world - A World object (can be any)
     * @param player - A Player object (the pet owner)
     */
    public abstract void tickPerk(World world, PlayerEntity player);

    /**
     * This method is called when the player interacts and despawns the pet
     * This method is not abstract due to it not being required for most pets
     * Can be used for turning off abilities or other disabling checks
     *
     * @param world - A World object (can be any)
     * @param player - A Player object (the pet owner)
     */
    public void onRemoved(World world, PlayerEntity player) {}

    /**
     * Called when the player pressed the 'pet key' while having a pet equipped
     * Used for pets with perks that aren't called every tick
     * NOTE: This method is called on the server
     *
     * @param world - The player's world
     * @param player - The player with the pet
     */
    public void onPetKey(World world, PlayerEntity player) {}

    /**
     * Called when the player is about to summon in their pet using a PetItem
     * Used for permission specific pets
     * @param player - Player summoning pet
     * @return - Returns true if this pet can be summoned by the player
     */
    public boolean canBeSummoned(PlayerEntity player) {
        String type = Registry.ENTITY_TYPE.getId(this.getType()).getPath();
        return Permissions.check(player, "hoverpets.allowed." + type, true);
    }
}
