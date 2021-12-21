package safro.hover.pets.pet;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import safro.hover.pets.base.BasePetEntity;
import safro.hover.pets.registry.ItemRegistry;

public class BlazePet extends BasePetEntity {

    public BlazePet(EntityType<? extends BasePetEntity> entityType, World world) {
        super(entityType, world);
    }

    public void perk(World world, PlayerEntity player) {
        if (player.isOnFire()) {
            player.extinguish();
        }
    }

    public ItemStack getPetStack() {
        return new ItemStack(ItemRegistry.BLAZE_PET);
    }
}
