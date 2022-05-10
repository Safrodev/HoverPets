package safro.hover.pets.registry;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import safro.hover.pets.HoverPets;
import safro.hover.pets.client.model.BasicPetModel;
import safro.hover.pets.client.render.BasicPetRenderer;
import safro.hover.pets.client.render.GlowPetRenderer;

public class ClientRegistry {
    public static final EntityModelLayer PET_LAYER = new EntityModelLayer(new Identifier(HoverPets.MODID, "pet_layer"), "pet_layer");

    public static void init() {
        EntityRendererRegistry.register(EntityRegistry.BLAZE_PET, BasicPetRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.OCELOT_PET, BasicPetRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.CHICKEN_PET, BasicPetRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.COW_PET, BasicPetRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.FOX_PET, BasicPetRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.GLOW_SQUID_PET, GlowPetRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.PUFFERFISH_PET, BasicPetRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.MAGMA_CUBE_PET, BasicPetRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.CREEPER_PET, BasicPetRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.PANDA_PET, BasicPetRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.WITCH_PET, BasicPetRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.ENDERMAN_PET, BasicPetRenderer::new);
    //    EntityRendererRegistry.register(EntityRegistry.SLIME_PET, BasicPetRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(PET_LAYER, BasicPetModel::getTexturedModelData);
    }
}
