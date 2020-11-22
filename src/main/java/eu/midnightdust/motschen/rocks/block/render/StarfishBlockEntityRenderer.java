package eu.midnightdust.motschen.rocks.block.render;

import eu.midnightdust.motschen.rocks.block.blockentity.StarfishBlockEntity;
import eu.midnightdust.motschen.rocks.blockstates.StarfishVariation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class StarfishBlockEntityRenderer extends BlockEntityRenderer<StarfishBlockEntity> {
	private static final ModelPart side1;
	private static final ModelPart side2;
	private static final ModelPart side3;
	private static final ModelPart side4;
	private static final ModelPart side5;
	private static final ModelPart bb_main;

	static {
		side1 = new ModelPart(16, 16, 0, 0);
		side1.setPivot(0.0F, 24.0F, 0.0F);
		side1.setTextureOffset(0, 0).addCuboid(-0.5F, -1.0F, 1.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
		side1.setTextureOffset(1, 1).addCuboid(-0.75F, -1.02F, 1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		side1.setTextureOffset(1, 2).addCuboid(-1.0F, -0.99F, 1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
		side1.setTextureOffset(1, 1).addCuboid(-0.25F, -1.01F, 1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		side2 = new ModelPart(16, 16, 0, 0);
		side2.setPivot(0.0F, 24.0F, 0.0F);
		setRotationAngle(side2, 0.0F, -1.2654F, 0.0F);
		side2.setTextureOffset(0, 0).addCuboid(-0.5F, -1.0F, 1.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
		side2.setTextureOffset(1, 1).addCuboid(-0.75F, -1.02F, 1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		side2.setTextureOffset(1, 1).addCuboid(-0.25F, -1.01F, 1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		side2.setTextureOffset(1, 2).addCuboid(-1.0F, -0.99F, 1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);

		side3 = new ModelPart(16, 16, 0, 0);
		side3.setPivot(0.0F, 24.0F, 0.0F);
		setRotationAngle(side3, 0.0F, 1.2654F, 0.0F);
		side3.setTextureOffset(0, 0).addCuboid(-0.5F, -1.0F, 1.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
		side3.setTextureOffset(1, 1).addCuboid(-0.75F, -1.02F, 1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		side3.setTextureOffset(1, 1).addCuboid(-0.25F, -1.01F, 1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		side3.setTextureOffset(1, 2).addCuboid(-1.0F, -0.99F, 1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);

		side4 = new ModelPart(16, 16, 0, 0);
		side4.setPivot(0.0F, 24.0F, 0.0F);
		setRotationAngle(side4, 0.0F, 2.5307F, 0.0F);
		side4.setTextureOffset(0, 0).addCuboid(-0.5F, -1.0F, 1.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
		side4.setTextureOffset(1, 1).addCuboid(-0.75F, -1.02F, 1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		side4.setTextureOffset(1, 1).addCuboid(-0.25F, -1.01F, 1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		side4.setTextureOffset(1, 2).addCuboid(-1.0F, -0.99F, 1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);

		side5 = new ModelPart(16, 16, 0, 0);
		side5.setPivot(0.0F, 24.0F, 0.0F);
		setRotationAngle(side5, 0.0F, -2.5307F, 0.0F);
		side5.setTextureOffset(0, 0).addCuboid(-0.5F, -1.0F, 1.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
		side5.setTextureOffset(1, 1).addCuboid(-0.75F, -1.02F, 1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		side5.setTextureOffset(1, 1).addCuboid(-0.25F, -1.01F, 1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		side5.setTextureOffset(1, 2).addCuboid(-1.0F, -0.99F, 1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);

		bb_main = new ModelPart(16, 16, 0, 0);
		bb_main.setPivot(0.0F, 24.0F, 0.0F);
		bb_main.setTextureOffset(1, 2).addCuboid(-1.0F, -1.005F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(1, 2).addCuboid(-0.8F, -1.0F, -1.25F, 2.0F, 1.0F, 2.0F, 0.0F, false);
		bb_main.setTextureOffset(2, 2).addCuboid(-1.2F, -1.0F, -1.25F, 1.0F, 1.0F, 2.0F, 0.0F, false);
	}

	public StarfishBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
		super(blockEntityRenderDispatcher);
	}
	@Override
	public void render(StarfishBlockEntity blockEntity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		if (blockEntity.getVariation().equals(StarfishVariation.RED)) {
			matrixStack.push();
			VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(new Identifier("rocks:textures/block/starfish_red.png")));

			matrixStack.translate(0.4, -1.44, 0.6);
			side1.render(matrixStack, vertexConsumer, light, overlay);
			side2.render(matrixStack, vertexConsumer, light, overlay);
			side3.render(matrixStack, vertexConsumer, light, overlay);
			side4.render(matrixStack, vertexConsumer, light, overlay);
			side5.render(matrixStack, vertexConsumer, light, overlay);
			bb_main.render(matrixStack, vertexConsumer, light, overlay);
			matrixStack.pop();
		}
		else if (blockEntity.getVariation().equals(StarfishVariation.PINK)) {
			matrixStack.push();
			VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(new Identifier("rocks:textures/block/starfish_pink.png")));

			matrixStack.translate(0.4, -1.44, 0.4);
			matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90));
			side1.render(matrixStack, vertexConsumer, light, overlay);
			side2.render(matrixStack, vertexConsumer, light, overlay);
			side3.render(matrixStack, vertexConsumer, light, overlay);
			side4.render(matrixStack, vertexConsumer, light, overlay);
			side5.render(matrixStack, vertexConsumer, light, overlay);
			bb_main.render(matrixStack, vertexConsumer, light, overlay);
			matrixStack.pop();
		}
		else {
			matrixStack.push();
			VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(new Identifier("rocks:textures/block/starfish_orange.png")));

			matrixStack.translate(0.65, -1.44, 0.65);
			matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(123));
			side1.render(matrixStack, vertexConsumer, light, overlay);
			side2.render(matrixStack, vertexConsumer, light, overlay);
			side3.render(matrixStack, vertexConsumer, light, overlay);
			side4.render(matrixStack, vertexConsumer, light, overlay);
			side5.render(matrixStack, vertexConsumer, light, overlay);
			bb_main.render(matrixStack, vertexConsumer, light, overlay);
			matrixStack.pop();
		}
	}
	public static void setRotationAngle(ModelPart bone, float x, float y, float z) {
		bone.pitch = x;
		bone.yaw = y;
		bone.roll = z;
	}
}