package safro.hover.pets.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import safro.hover.pets.pet.MagmaCubePet;
import safro.hover.pets.util.PetUtil;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "canWalkOnFluid", at = @At("HEAD"), cancellable = true)
    private void hasMagmaPet(FluidState fluidState, CallbackInfoReturnable<Boolean> cir) {
        if (((LivingEntity)(Object) this) instanceof PlayerEntity p) {
            if (PetUtil.hasPet(p) && PetUtil.getPet(p) instanceof MagmaCubePet) {
                cir.setReturnValue(fluidState.isIn(FluidTags.LAVA));
            }
        }
    }
}
