package safro.hover.pets.compat;

import dev.lambdaurora.lambdynlights.api.DynamicLightHandler;
import dev.lambdaurora.lambdynlights.api.DynamicLightHandlers;
import dev.lambdaurora.lambdynlights.api.DynamicLightsInitializer;
import safro.hover.pets.registry.EntityRegistry;

public class HPDynamicLights implements DynamicLightsInitializer {

    @Override
    public void onInitializeDynamicLights() {
        DynamicLightHandlers.registerDynamicLightHandler(EntityRegistry.GLOW_SQUID_PET, DynamicLightHandler.makeHandler((squid) -> 10, (squid) -> true));
    }
}
