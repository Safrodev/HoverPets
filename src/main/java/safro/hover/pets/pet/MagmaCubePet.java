package safro.hover.pets.pet;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.world.World;
import safro.hover.pets.api.BasePetEntity;
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
    }

    @Override
    public boolean canWalkOnFluid(FluidState fluid) {
        return fluid.isIn(FluidTags.LAVA);
    }
}
