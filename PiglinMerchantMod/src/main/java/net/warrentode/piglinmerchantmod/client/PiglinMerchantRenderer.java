package net.warrentode.piglinmerchantmod.client;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemTransforms.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.warrentode.piglinmerchantmod.entity.custom.*;
import org.jetbrains.annotations.*;
import software.bernie.geckolib.cache.object.*;
import software.bernie.geckolib.renderer.*;
import software.bernie.geckolib.renderer.layer.*;

import javax.annotation.Nullable;
import javax.annotation.*;

import static net.warrentode.piglinmerchantmod.PiglinMerchantMod.*;

public class PiglinMerchantRenderer extends GeoEntityRenderer<PiglinMerchantEntity> {
    private static final String LEFT_HAND = "handLeft";
    private static final String RIGHT_HAND = "handRight";
    protected ItemStack mainHandItem;
    protected ItemStack offhandItem;

    public PiglinMerchantRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PiglinMerchantModel());
        this.shadowRadius = 0.6f;
        this.scaleWidth = 1;
        this.scaleHeight = 1;

        // Add some armor rendering
        addRenderLayer(new ItemArmorGeoLayer<>(this) {
            // Return the equipment slot relevant to the bone we're using
            @Nonnull
            @Override
            protected EquipmentSlot getEquipmentSlotForBone(GeoBone bone, ItemStack stack, PiglinMerchantEntity animatable) {
                return switch (bone.getName()) {
                    case RIGHT_HAND -> !animatable.isLeftHanded() ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
                    case LEFT_HAND -> animatable.isLeftHanded() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
                    default -> super.getEquipmentSlotForBone(bone, stack, animatable);
                };
            }
        });

        // Add some held item rendering
        addRenderLayer(new BlockAndItemGeoLayer<>(this) {
            @Nullable
            @Override
            protected ItemStack getStackForBone(GeoBone bone, PiglinMerchantEntity animatable) {
                // Retrieve the items in the entity's hands for the relevant bone
                return switch (bone.getName()) {
                    case LEFT_HAND -> animatable.isLeftHanded() ?
                            PiglinMerchantRenderer.this.mainHandItem : PiglinMerchantRenderer.this.offhandItem;
                    case RIGHT_HAND -> animatable.isLeftHanded() ?
                            PiglinMerchantRenderer.this.offhandItem : PiglinMerchantRenderer.this.mainHandItem;
                    default -> null;
                };
            }

            @Override
            protected TransformType getTransformTypeForStack(GeoBone bone, ItemStack stack, PiglinMerchantEntity animatable) {
                // Apply the camera transform for the given hand
                return switch (bone.getName()) {
                    case LEFT_HAND, RIGHT_HAND -> TransformType.THIRD_PERSON_RIGHT_HAND;
                    default -> TransformType.NONE;
                };
            }

            // Do some quick render modifications depending on what the item is
            @Override
            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, PiglinMerchantEntity animatable,
                                              MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
                if (stack == PiglinMerchantRenderer.this.mainHandItem) {
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90f));

                    if (stack.getItem() instanceof ShieldItem)
                        poseStack.translate(0, 0.125, -0.25);
                }
                else if (stack == PiglinMerchantRenderer.this.offhandItem) {
                    poseStack.mulPose(Axis.XP.rotationDegrees(-90f));

                    if (stack.getItem() instanceof ShieldItem) {
                        poseStack.translate(0, 0.125, 0.25);
                        poseStack.mulPose(Axis.YP.rotationDegrees(180));
                    }
                }

                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
            }
        });
    }
    @Override
    public void preRender(PoseStack poseStack, PiglinMerchantEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        this.mainHandItem = animatable.getMainHandItem();
        this.offhandItem = animatable.getOffhandItem();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull PiglinMerchantEntity animatable) {
        return new ResourceLocation(MODID, "textures/entity/piglinmerchant.png");
    }

    @Override
    public void render(@NotNull PiglinMerchantEntity entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}