package safro.hover.pets.client.render;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import safro.hover.pets.HoverPets;
import safro.hover.pets.api.BasePetEntity;
import safro.hover.pets.client.model.BasicPetModel;
import safro.hover.pets.registry.ClientRegistry;

public class BasicPetRenderer extends LivingEntityRenderer<BasePetEntity, BasicPetModel> {

    public BasicPetRenderer(EntityRendererFactory.Context context) {
        super(context, new BasicPetModel(context.getPart(ClientRegistry.PET_LAYER)), 0.1F);
    }

    @Override
    public Identifier getTexture(BasePetEntity entity) {
        return new Identifier(HoverPets.MODID, "textures/entity/" + Registry.ENTITY_TYPE.getId(entity.getType()).getPath() + ".png");
    }

    public void render(BasePetEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        float h = getYOffset(entity, g);
        matrixStack.translate(0.0D, (double)(1.5F + h / 2.0F), 0.0D);
        if (entity.getOwner() != null && entity.getOwner() instanceof PlayerEntity player) {
            this.renderLabelIfPresent(entity, new LiteralText(player.getDisplayName().asString() + "'s Pet"), matrixStack, vertexConsumerProvider, i);
        }
        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    public static float getYOffset(BasePetEntity entity, float tickDelta) {
        float f = (float)entity.getBounce() + tickDelta;
        float g = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
        g = (g * g + g) * 0.4F;
        return g - 1.4F;
    }

    @Override
    protected boolean hasLabel(BasePetEntity entity) {
        return false;
    }
}
