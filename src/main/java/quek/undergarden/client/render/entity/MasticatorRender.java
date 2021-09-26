package quek.undergarden.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import quek.undergarden.Undergarden;
import quek.undergarden.client.model.MasticatorModel;
import quek.undergarden.client.render.layer.MasticatorEyesLayer;
import quek.undergarden.entity.boss.MasticatorEntity;

public class MasticatorRender extends MobRenderer<MasticatorEntity, MasticatorModel<MasticatorEntity>> {

    public MasticatorRender(EntityRenderDispatcher renderManagerIn) {
        super(renderManagerIn, new MasticatorModel<>(), 2.0F);
        this.addLayer(new MasticatorEyesLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(MasticatorEntity entity) {
        return new ResourceLocation(Undergarden.MODID, "textures/entity/masticator.png");
    }
}