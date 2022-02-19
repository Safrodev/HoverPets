package safro.hover.pets.pet;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.world.World;
import safro.hover.pets.api.BasePetEntity;
import safro.hover.pets.api.PetAccess;
import safro.hover.pets.registry.ItemRegistry;

public class MagmaCubePet extends BasePetEntity {

    public MagmaCubePet(EntityType<? extends BasePetEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ItemStack getPetStack() {
        return new ItemStack(ItemRegistry.MAGMA_CUBE_PET);
    }

    @Override
    public void tickPerk(World world, PlayerEntity player) {
        ((PetAccess)player).setMagmaCube(true);
    }

    @Override
    public void onRemoved(World world, PlayerEntity player) {
        ((PetAccess)player).setMagmaCube(false);
    }

    @Override
    public boolean canWalkOnFluid(Fluid fluid) {
        return fluid.isIn(FluidTags.LAVA);
    }
}
