package safro.hover.pets.pet;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import safro.hover.pets.base.BasePetEntity;
import safro.hover.pets.registry.ItemRegistry;

public class GlowSquidPet extends BasePetEntity {

    public GlowSquidPet(EntityType<? extends BasePetEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ItemStack getPetStack() {
        return new ItemStack(ItemRegistry.GLOW_SQUID_PET);
    }

    @Override
    public void perk(World world, PlayerEntity player) {
        this.world.addParticle(ParticleTypes.GLOW, this.getParticleX(0.6D), this.getRandomBodyY(), this.getParticleZ(0.6D), 0.0D, 0.0D, 0.0D);
    }
}
