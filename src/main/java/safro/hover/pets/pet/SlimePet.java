package safro.hover.pets.pet;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import safro.hover.pets.api.BasePetEntity;
import safro.hover.pets.registry.ItemRegistry;

public class SlimePet extends BasePetEntity {
    private int cooldown;

    public SlimePet(EntityType<? extends BasePetEntity> entityType, World world) {
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
            Vec3d vec3d = player.getVelocity();
            player.setVelocity(vec3d.x, 0.42 + 0.8, vec3d.z);
            if (player.isSprinting()) {
                float f = player.getYaw() * 0.017453292F;
                player.setVelocity(player.getVelocity().add(-MathHelper.sin(f) * 0.2F, 0.0D, MathHelper.cos(f) * 0.2F));
            }
            this.velocityDirty = true;

            cooldown = 40;
        }
    }
}
