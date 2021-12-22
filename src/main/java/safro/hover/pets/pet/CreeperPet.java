package safro.hover.pets.pet;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import safro.hover.pets.base.BasePetEntity;
import safro.hover.pets.base.PetAccess;
import safro.hover.pets.registry.ItemRegistry;

public class CreeperPet extends BasePetEntity {

    public CreeperPet(EntityType<? extends BasePetEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ItemStack getPetStack() {
        return new ItemStack(ItemRegistry.CREEPER_PET);
    }

    @Override
    public void perk(World world, PlayerEntity player) {
        ((PetAccess)player).setCreeper(true);
    }

    @Override
    public void onRemoved(World world, PlayerEntity player) {
        ((PetAccess)player).setCreeper(false);
    }
}
