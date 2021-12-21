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
}
