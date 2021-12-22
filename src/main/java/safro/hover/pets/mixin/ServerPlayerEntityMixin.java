package safro.hover.pets.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import safro.hover.pets.util.RespawnAccess;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements RespawnAccess {
    private boolean ready = false;

    @Inject(method = "onSpawn", at = @At("HEAD"))
    private void respawnPet(CallbackInfo ci) {
        ready = true;
    }

    @Override
    public boolean isReadyForRespawn() {
        if (ready) {
            ready = false;
            return true;
        } else
            return false;
    }
}
