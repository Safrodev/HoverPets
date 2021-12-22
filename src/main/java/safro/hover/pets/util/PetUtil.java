package safro.hover.pets.util;

import net.minecraft.entity.player.PlayerEntity;
import safro.hover.pets.base.PetAccess;

public class PetUtil {
    /**
     * Checks if the Player currently owns a pet
     *
     * @param player - A PlayerEntity object
     * @return Returns the HAS_PET tracked data from the Player
     */
    public static boolean hasPet(PlayerEntity player) {
        return player.getDataTracker().get(((PetAccess)player).get());
    }

    /*
    if (!world1.isClient) {
         FabricDimensions.teleport(this, (ServerWorld) world1, (new TeleportTarget(new Vec3d((double)this.getX() + 0.5D, (double)this.getY(), (double)this.getZ() + 0.5D), this.getVelocity(), this.getYaw(), this.getPitch())));
       }
     */
}
