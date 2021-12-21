package safro.hover.pets;

import net.fabricmc.api.ClientModInitializer;
import safro.hover.pets.registry.ClientRegistry;

public class HoverPetsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientRegistry.init();
    }
}
