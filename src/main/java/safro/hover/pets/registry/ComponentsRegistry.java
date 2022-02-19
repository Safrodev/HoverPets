package safro.hover.pets.registry;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.util.Identifier;
import safro.hover.pets.HoverPets;
import safro.hover.pets.api.PetComponent;

public class ComponentsRegistry implements EntityComponentInitializer {
    public static final ComponentKey<PetComponent> PET_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(HoverPets.MODID, "pet"), PetComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(PET_COMPONENT, PetComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
