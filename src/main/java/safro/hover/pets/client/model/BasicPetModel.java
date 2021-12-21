package safro.hover.pets.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import safro.hover.pets.base.BasePetEntity;

public class BasicPetModel extends EntityModel<BasePetEntity> {
	private final ModelPart cube;

	public BasicPetModel(ModelPart modelPart) {
		this.cube = modelPart.getChild("cube");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("cube", ModelPartBuilder.create().uv(0,0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), ModelTransform.pivot(0.0F,24.0F,0.0F));
		return TexturedModelData.of(modelData,32,32);
	}

	@Override
	public void setAngles(BasePetEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){

	}

	@Override
	public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		cube.render(matrixStack, buffer, packedLight, packedOverlay);
	}
}