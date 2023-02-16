package safro.hover.pets.pet;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import safro.hover.pets.api.BasePetEntity;
import safro.hover.pets.registry.ItemRegistry;

public class WitchPet extends BasePetEntity implements RangedAttackMob {

    public WitchPet(EntityType<? extends BasePetEntity> entityType, World world) {
        super(entityType, world);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	protected void initGoals() {
        this.goalSelector.add(1, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.add(2, new ProjectileAttackGoal(this, 1.0D, 60, 10.0F));
        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(10, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal(this, LivingEntity.class, 10, false, false, (entity) -> {
            return entity instanceof IronGolemEntity || entity instanceof PlayerEntity || entity instanceof VillagerEntity;
        }));
    }

    public void attack(LivingEntity target, float pullProgress) {
        if (this.hasOwner() && target.getHealth() < target.getMaxHealth()) {
            Vec3d vec3d = target.getVelocity();
            double d = target.getX() + vec3d.x - this.getX();
            double e = target.getEyeY() - 1.100000023841858D - this.getY();
            double f = target.getZ() + vec3d.z - this.getZ();
            double g = Math.sqrt(d * d + f * f);
            Potion potion = Potions.HEALING;
            PotionEntity potionEntity = new PotionEntity(this.world, this);
            potionEntity.setItem(PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), potion));
            potionEntity.setPitch(potionEntity.getPitch() - -20.0F);
            potionEntity.setVelocity(d, e + g * 0.2D, f, 0.75F, 8.0F);
            if (!this.isSilent()) {
                this.world.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_SPLASH_POTION_THROW, this.getSoundCategory(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
            }
            this.world.spawnEntity(potionEntity);
        }
    }

    @Override
    public ItemStack getPetStack() {
        return new ItemStack(ItemRegistry.WITCH_PET);
    }

    @Override
    public void tickPerk(World world, PlayerEntity player) {

    }
}
