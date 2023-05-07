package net.warrentode.piglinmerchantmod.client;

import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.warrentode.piglinmerchantmod.entity.custom.*;
import software.bernie.geckolib.constant.*;
import software.bernie.geckolib.core.animatable.model.*;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.model.*;
import software.bernie.geckolib.model.data.*;

import static net.warrentode.piglinmerchantmod.PiglinMerchantMod.*;

public class PiglinMerchantModel extends GeoModel<PiglinMerchantEntity> {
    @Override
    public ResourceLocation getModelResource(PiglinMerchantEntity animatable) {
        return new ResourceLocation(MODID, "geo/piglinmerchant.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PiglinMerchantEntity animatable) {
        return new ResourceLocation(MODID, "textures/entity/piglinmerchant.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PiglinMerchantEntity animatable) {
        return new ResourceLocation(MODID, "animations/piglinmerchant.animation.json");
    }

    @Override
    public void setCustomAnimations(PiglinMerchantEntity animatable, long instanceId, AnimationState<PiglinMerchantEntity> animationState) {
        float f = (Mth.PI / 6F);
        float f1 = (float)(animationState.animationTick * 0.1F + animationState.getLimbSwingAmount() * 0.5F);
        float f2 = (0.08F + animationState.getLimbSwingAmount() * 0.4F);

        CoreGeoBone head = getAnimationProcessor().getBone("head");
        CoreGeoBone leftear = getAnimationProcessor().getBone("leftear");
        CoreGeoBone rightear = getAnimationProcessor().getBone("rightear");

        if (head != null && leftear != null && rightear != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);

            leftear.setRotZ(entityData.headPitch() - f - Mth.cos(f1) * f2);
            rightear.setRotZ(entityData.headPitch() + f  + Mth.cos(f1 * 1.2F) * f2);
        }
    }
}