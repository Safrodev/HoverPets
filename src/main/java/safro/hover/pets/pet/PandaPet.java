package safro.hover.pets.pet;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import safro.hover.pets.base.BasePetEntity;
import safro.hover.pets.registry.ItemRegistry;

public class PandaPet extends BasePetEntity {
    private int cooldown;

    public PandaPet(EntityType<? extends BasePetEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ItemStack getPetStack() {
        return new ItemStack(ItemRegistry.PANDA_PET);
    }

    @Override
    public void perk(World world, PlayerEntity player) {
        --cooldown;
        if (cooldown < 1) {
            if (player.canConsume(false)) {
                player.getHungerManager().add(8, 0.8F);
            }
            cooldown = 1200 + random.nextInt(600);
        }
    }
}
