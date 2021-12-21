package safro.hover.pets.pet;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import safro.hover.pets.HoverPets;
import safro.hover.pets.base.BasePetEntity;
import safro.hover.pets.registry.ItemRegistry;

public class ChickenPet extends BasePetEntity {

    public ChickenPet(EntityType<? extends BasePetEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ItemStack getPetStack() {
        return new ItemStack(ItemRegistry.CHICKEN_PET);
    }

    private int cooldown;

    @Override
    public void perk(World world, PlayerEntity player) {
        --cooldown;
        if (cooldown < 1) {
            BlockPos pos = player.getBlockPos();
            double d = (double)(world.random.nextFloat() * 0.7F) + 0.15000000596046448D;
            double e = (double)(world.random.nextFloat() * 0.7F) + 0.06000000238418579D + 0.6D;
            double g = (double)(world.random.nextFloat() * 0.7F) + 0.15000000596046448D;
            ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + d, (double)pos.getY() + e, (double)pos.getZ() + g, new ItemStack(randomSeed()));
            itemEntity.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity);
            cooldown = 600;
        }
    }

    private Item randomSeed() {
        if (random.nextFloat() <= 0.25F) {
            return Items.BEETROOT_SEEDS;
        } else if (random.nextFloat() <= 0.25F) {
            return Items.MELON_SEEDS;
        } else if (random.nextFloat() <= 0.25F) {
            return Items.PUMPKIN_SEEDS;
        } else
            return Items.WHEAT_SEEDS;
    }
}
