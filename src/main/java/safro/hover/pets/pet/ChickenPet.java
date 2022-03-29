package safro.hover.pets.pet;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import safro.hover.pets.api.BasePetEntity;
import safro.hover.pets.registry.ItemRegistry;
import safro.hover.pets.registry.TagRegistry;

public class ChickenPet extends BasePetEntity {
    private int cooldown;

    public ChickenPet(EntityType<? extends BasePetEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ItemStack getPetStack() {
        return new ItemStack(ItemRegistry.CHICKEN_PET);
    }

    @Override
    public void tickPerk(World world, PlayerEntity player) {
        --cooldown;
        if (cooldown < 1) {
            BlockPos pos = player.getBlockPos();
            double d = (double)(world.random.nextFloat() * 0.7F) + 0.15000000596046448D;
            double e = (double)(world.random.nextFloat() * 0.7F) + 0.06000000238418579D + 0.6D;
            double g = (double)(world.random.nextFloat() * 0.7F) + 0.15000000596046448D;
            ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + d, (double)pos.getY() + e, (double)pos.getZ() + g, getRandomSeed());
            itemEntity.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity);
            cooldown = 600;
        }
    }

    private ItemStack getRandomSeed() {
        ItemStack stack = ItemStack.EMPTY;
        stack = Registry.ITEM.getEntryList(TagRegistry.SEEDS).flatMap((items) -> items.getRandom(random)).map((itemEntry) -> (itemEntry.value()).getDefaultStack()).orElse(stack);
        return stack;
    }
}
