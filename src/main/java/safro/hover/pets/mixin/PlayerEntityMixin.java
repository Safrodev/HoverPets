package safro.hover.pets.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import safro.hover.pets.base.PetAccess;
import safro.hover.pets.util.PetUtil;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PetAccess {
    @Shadow private boolean reducedDebugInfo;
    private static final TrackedData<Boolean> HAS_PET;
    // Booleans for pet checks
    private boolean hasPufferfish = false;
    private boolean hasMagmaCube = false;

    public PlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(EntityType.PLAYER, world);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void initPetData(CallbackInfo ci) {
        PlayerEntity p = (PlayerEntity) (Object) this;
        p.getDataTracker().startTracking(HAS_PET, false);
    }

    @Inject(method = "damage", at = @At("TAIL"))
    private void pufferfishPet(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity p = (PlayerEntity) (Object) this;
        if (source.getAttacker() instanceof LivingEntity attacker && p.getHealth() <= 6.0F) {
            if (hasPufferfish && PetUtil.hasPet(p)) {
                attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 120, 0), p);
                attacker.playSound(SoundEvents.ENTITY_PUFFER_FISH_STING, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public boolean canWalkOnFluid(Fluid fluid) {
        if (hasMagmaCube && PetUtil.hasPet((PlayerEntity) (Object) this)) {
            return fluid.isIn(FluidTags.LAVA);
        } else
            return super.canWalkOnFluid(fluid);
    }

    public TrackedData<Boolean> get() {
        return HAS_PET;
    }

    public void setPufferfish(boolean bl) {
        hasPufferfish = bl;
    }

    public void setMagmaCube(boolean bl) {
        hasMagmaCube = bl;
    }

    static {
        HAS_PET = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
}
