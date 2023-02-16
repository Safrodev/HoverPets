package safro.hover.pets.util;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import safro.hover.pets.api.BasePetEntity;
import safro.hover.pets.registry.ComponentsRegistry;

public class PetUtil {
    /**
     * Checks if the Player currently owns a pet
     *
     * @param player - A PlayerEntity object
     * @return Returns if the current pet from ID is null or not
     */
    public static boolean hasPet(PlayerEntity player) {
        return ComponentsRegistry.PET_COMPONENT.get(player).hasPet();
    }

    /**
     * Checks if the pet is immune to the DamageSource
     *
     * @param source - The source the pet was damaged by
     * @return - Returns true if the pet is immune to the give source, returns false if not
     */
    public static boolean isImmune(DamageSource source) {
        if (source.isOutOfWorld()) {
            return false;
        }
        if (source.getAttacker() instanceof PlayerEntity) {
            return true;
        }
        return source.isFire() || source.isMagic() || source.isExplosive() || source.isFallingBlock() || source.isFromFalling() || source.isProjectile() || source.bypassesArmor();
    }

    /**
     * Sets the provided Player's pet ID with the provided ID
     *
     * @param player - Pet Owner whose ID is being set
     * @param id - The new ID to be set
     */
    public static void setPet(PlayerEntity player, int id) {
        ComponentsRegistry.PET_COMPONENT.get(player).setPetId(id);
    }

    /**
     * Returns the current pet of the Player
     * Note: You should always use the hasPet method before running this to prevent null exceptions
     * @see #hasPet(PlayerEntity)
     *
     * @param player - The owner whose pet is being returned
     * @return - A pet entity object
     */
    public static BasePetEntity getPet(PlayerEntity player) {
        return ComponentsRegistry.PET_COMPONENT.get(player).getPet();
    }

    public static boolean isMoving(PlayerEntity player) {
        return (player.prevX != player.getX()) || (player.prevZ != player.getZ()) || (player.prevY != player.getY());
    }
}
