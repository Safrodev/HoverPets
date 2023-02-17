package safro.hover.pets.pet.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class WitchPetActiveTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {

    public WitchPetActiveTargetGoal(MobEntity mob, Class<T> targetClass, int reciprocalChance, boolean checkVisibility, boolean checkCanNavigate, @Nullable Predicate<LivingEntity> targetPredicate) {
        super(mob, targetClass, reciprocalChance, checkVisibility, checkCanNavigate, targetPredicate);
        this.targetPredicate = TargetPredicate.createNonAttackable().setBaseMaxDistance(this.getFollowRange()).setPredicate(targetPredicate);
    }

    protected void findClosestTarget() {
        this.targetEntity = this.mob.world.getClosestEntity(this.mob.world.getEntitiesByClass(this.targetClass, this.getSearchBox(this.getFollowRange()), (livingEntity) -> true), this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        if (this.targetEntity == null) {
            this.targetEntity = this.mob.world.getClosestPlayer(this.mob.getX(), this.mob.getEyeY(), this.mob.getZ(), this.getFollowRange(), entity -> {
                return entity instanceof PlayerEntity p && !p.isCreative() && p.getHealth() < p.getMaxHealth();
            });
        }
    }
}
