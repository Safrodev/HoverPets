package safro.hover.pets.pet;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import safro.hover.pets.base.BasePetEntity;
import safro.hover.pets.registry.ItemRegistry;

import java.util.EnumSet;
import java.util.function.Predicate;

public class FoxPet extends BasePetEntity {
    static final Predicate<Entity> CHICKEN_AND_RABBIT_FILTER;

    public FoxPet(EntityType<? extends BasePetEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.add(2, new FoxPet.AttackGoal(1.2000000476837158D, true));
        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(10, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal(this, AnimalEntity.class, 10, false, false, (entity) -> {
            return entity instanceof ChickenEntity || entity instanceof RabbitEntity;
        }));
    }

    public static DefaultAttributeContainer.Builder createFoxPetAttributes() {
        return createPetAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0D);
    }

    @Override
    public ItemStack getPetStack() {
        return new ItemStack(ItemRegistry.FOX_PET);
    }

    @Override
    public void perk(World world, PlayerEntity player) {

    }

    public static boolean canJumpChase(FoxPet fox, LivingEntity chasedEntity) {
        double d = chasedEntity.getZ() - fox.getZ();
        double e = chasedEntity.getX() - fox.getX();
        double f = d / e;

        for(int j = 0; j < 6; ++j) {
            double g = f == 0.0D ? 0.0D : d * (double)((float)j / 6.0F);
            double h = f == 0.0D ? e * (double)((float)j / 6.0F) : g / f;

            for(int k = 1; k < 4; ++k) {
                if (!fox.world.getBlockState(new BlockPos(fox.getX() + h, fox.getY() + (double)k, fox.getZ() + g)).getMaterial().isReplaceable()) {
                    return false;
                }
            }
        }

        return true;
    }

    static {
        CHICKEN_AND_RABBIT_FILTER = (entity) -> {
            return entity instanceof ChickenEntity || entity instanceof RabbitEntity;
        };
    }

    private class AttackGoal extends MeleeAttackGoal {
        public AttackGoal(double speed, boolean pauseWhenIdle) {
            super(FoxPet.this, speed, pauseWhenIdle);
        }

        protected void attack(LivingEntity target, double squaredDistance) {
            double d = this.getSquaredMaxAttackDistance(target);
            if (squaredDistance <= d && this.isCooledDown()) {
                this.resetCooldown();
                this.mob.tryAttack(target);
                FoxPet.this.playSound(SoundEvents.ENTITY_FOX_BITE, 1.0F, 1.0F);
            }
        }

        public boolean canStart() {
            return !FoxPet.this.isSitting() && !FoxPet.this.isSleeping() && !FoxPet.this.isInSneakingPose() && super.canStart();
        }
    }

    class MoveToHuntGoal extends Goal {
        public MoveToHuntGoal() {
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        public boolean canStart() {
            if (FoxPet.this.isSleeping()) {
                return false;
            } else {
                LivingEntity livingEntity = FoxPet.this.getTarget();
                return livingEntity != null && livingEntity.isAlive() && FoxPet.CHICKEN_AND_RABBIT_FILTER.test(livingEntity) && FoxPet.this.squaredDistanceTo(livingEntity) > 36.0D && !FoxPet.this.isInSneakingPose() && !FoxPet.this.jumping;
            }
        }

        public void start() {
            FoxPet.this.setSitting(false);
        }

        public void stop() {
            LivingEntity livingEntity = FoxPet.this.getTarget();
            if (livingEntity != null && FoxPet.canJumpChase(FoxPet.this, livingEntity)) {
                FoxPet.this.getNavigation().stop();
                FoxPet.this.getLookControl().lookAt(livingEntity, (float)FoxPet.this.getMaxHeadRotation(), (float)FoxPet.this.getMaxLookPitchChange());
            }
        }

        public void tick() {
            LivingEntity livingEntity = FoxPet.this.getTarget();
            if (livingEntity != null) {
                FoxPet.this.getLookControl().lookAt(livingEntity, (float)FoxPet.this.getMaxHeadRotation(), (float)FoxPet.this.getMaxLookPitchChange());
                if (FoxPet.this.squaredDistanceTo(livingEntity) <= 36.0D) {
                    FoxPet.this.getNavigation().stop();
                } else {
                    FoxPet.this.getNavigation().startMovingTo(livingEntity, 1.5D);
                }

            }
        }
    }
}
