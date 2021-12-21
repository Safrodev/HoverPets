package safro.hover.pets.pet;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import safro.hover.pets.base.BasePetEntity;
import safro.hover.pets.registry.ItemRegistry;

public class OcelotPet extends BasePetEntity {

    public OcelotPet(EntityType<? extends BasePetEntity> entityType, World world) {
        super(entityType, world);
    }

    public void perk(World world, PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 2, true, false));
    }

    public ItemStack getPetStack() {
        return new ItemStack(ItemRegistry.OCELOT_PET);
    }
}
