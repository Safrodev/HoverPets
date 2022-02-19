package safro.hover.pets.util;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import safro.hover.pets.api.PetAccess;
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

    public static void setPet(PlayerEntity player, int id) {
        ComponentsRegistry.PET_COMPONENT.get(player).setPetId(id);
    }

    public static void setRemovedPet(PlayerEntity player) {
        player.getDataTracker().set(((PetAccess)player).get(), false);
    }

    public static void setHasPet(PlayerEntity player) {
        player.getDataTracker().set(((PetAccess)player).get(), true);
    }
}
