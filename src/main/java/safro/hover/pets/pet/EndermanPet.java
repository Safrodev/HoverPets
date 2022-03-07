package safro.hover.pets.pet;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import safro.hover.pets.api.BasePetEntity;
import safro.hover.pets.registry.ItemRegistry;

public class EndermanPet extends BasePetEntity {
    private int cooldown;

    public EndermanPet(EntityType<? extends EndermanPet> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ItemStack getPetStack() {
        return new ItemStack(ItemRegistry.ENDERMAN_PET);
    }

    @Override
    public void tickPerk(World world, PlayerEntity player) {
        if (cooldown > Integer.MIN_VALUE) {
            --cooldown;
        }
    }

    @Override
    public void onPetKey(World world, PlayerEntity player) {
        if (cooldown < 1) {
            HitResult hit = player.raycast(10, 0.0F, false);
            player.teleport(hit.getPos().x, hit.getPos().y, hit.getPos().z);
            if (world.isClient) {
                world.playSound(player, player.getBlockPos(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 0.5F, 1.0F);
            }
            cooldown = 100;
        }
    }
}
