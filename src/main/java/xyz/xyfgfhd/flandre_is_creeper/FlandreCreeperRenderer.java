/*
 * ModelFumo —— 原始代码
 * 作者：KosherKomeiji
 * 来源：https://github.com/KosherKomeiji/Fumo-Mcreator/tree/main/src/main/java/io/github/projectfumo/fumo/client/renderer/FlandreRenderer.java
 * 许可证：CC BY-NC-SA 4.0 <https://creativecommons.org/licenses/by-nc-sa/4.0/>
 *
 * 本文件由 xyfgfhd 修改：
 *   - 包名由 io.github.projectfumo.fumo.client.renderer 改为
 *     xyz.xyfgfhd.flandre_is_creeper
 *   - FlandreRenderer由文件名改为FlandreCreeperRenderer
 *   - 增加 render 方法
 */
package xyz.xyfgfhd.flandre_is_creeper;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;
import xyz.xyfgfhd.flandre_is_creeper.api.Scalable;

public class FlandreCreeperRenderer extends MobRenderer<Creeper, ModelFumo<Creeper>> {
    private final ResourceLocation entityTexture = new ResourceLocation("flandre_is_creeper:textures/flandre.png");

    public FlandreCreeperRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelFumo<Creeper>(context.bakeLayer(ModelFumo.LAYER_LOCATION)), 0.4f);
    }
    @Override
    public ResourceLocation getTextureLocation(Creeper entity) {
        return entityTexture;
    }

    @Override
    public void render(Creeper entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        Scalable self = (Scalable)(Object) entity;
        float size = self.renderScale(partialTick);
        poseStack.scale(1+(size-1)*0.5F, size, 1+(size-1)*0.5F);
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }
}