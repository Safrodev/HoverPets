package safro.hover.pets.client.render;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import safro.hover.pets.base.BasePetEntity;

public class GlowPetRenderer extends BasicPetRenderer {

    public GlowPetRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    protected int getBlockLight(BasePetEntity entity, BlockPos pos) {
        return 15;
    }
}
