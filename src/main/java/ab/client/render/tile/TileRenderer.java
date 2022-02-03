package ab.client.render.tile;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

public class TileRenderer {

	public static void renderCube(double xCenter, double yCenter, double zCenter, double r, IIcon icon, Tessellator t) {

		float maxU = 0;
		float maxV = 0;
		float minU = 0;
		float minV = 0;

		if (icon != null) {
			maxU = icon.getMaxU();
			maxV = icon.getMaxV();
			minU = icon.getMinU();
			minV = icon.getMinV();
		}

		double minX = xCenter - r;
		double minY = yCenter - r;
		double minZ = zCenter - r;
		double maxX = xCenter + r;
		double maxY = yCenter + r;
		double maxZ = zCenter + r;

		t.addVertexWithUV(maxX, minY, maxZ, maxU, maxV);
		t.addVertexWithUV(maxX, maxY, maxZ, maxU, minV);
		t.addVertexWithUV(minX, maxY, maxZ, minU, minV);
		t.addVertexWithUV(minX, minY, maxZ, minU, maxV);

		t.addVertexWithUV(minX, minY, minZ, maxU, maxV);
		t.addVertexWithUV(minX, maxY, minZ, maxU, minV);
		t.addVertexWithUV(maxX, maxY, minZ, minU, minV);
		t.addVertexWithUV(maxX, minY, minZ, minU, maxV);

		t.addVertexWithUV(minX, minY, minZ, maxU, maxV);
		t.addVertexWithUV(maxX, minY, minZ, maxU, minV);
		t.addVertexWithUV(maxX, minY, maxZ, minU, minV);
		t.addVertexWithUV(minX, minY, maxZ, minU, maxV);

		t.addVertexWithUV(maxX, maxY, maxZ, maxU, maxV);
		t.addVertexWithUV(maxX, maxY, minZ, maxU, minV);
		t.addVertexWithUV(minX, maxY, minZ, minU, minV);
		t.addVertexWithUV(minX, maxY, maxZ, minU, maxV);

		t.addVertexWithUV(minX, minY, maxZ, maxU, maxV);
		t.addVertexWithUV(minX, maxY, maxZ, maxU, minV);
		t.addVertexWithUV(minX, maxY, minZ, minU, minV);
		t.addVertexWithUV(minX, minY, minZ, minU, maxV);

		t.addVertexWithUV(maxX, minY, minZ, maxU, maxV);
		t.addVertexWithUV(maxX, maxY, minZ, maxU, minV);
		t.addVertexWithUV(maxX, maxY, maxZ, minU, minV);
		t.addVertexWithUV(maxX, minY, maxZ, minU, maxV);

	}

	public static void renderLateralSides(double xCenter, double yCenter, double zCenter, double r, IIcon icon,
			Tessellator t) {

		float maxU = icon.getMaxU();
		float maxV = icon.getMaxV();
		float minU = icon.getMinU();
		float minV = icon.getMinV();

		double minX = xCenter - r;
		double minY = yCenter - r;
		double minZ = zCenter - r;
		double maxX = xCenter + r;
		double maxY = yCenter + r;
		double maxZ = zCenter + r;

		t.addVertexWithUV(maxX, minY, maxZ, maxU, maxV);
		t.addVertexWithUV(maxX, maxY, maxZ, maxU, minV);
		t.addVertexWithUV(minX, maxY, maxZ, minU, minV);
		t.addVertexWithUV(minX, minY, maxZ, minU, maxV);

		t.addVertexWithUV(minX, minY, minZ, maxU, maxV);
		t.addVertexWithUV(minX, maxY, minZ, maxU, minV);
		t.addVertexWithUV(maxX, maxY, minZ, minU, minV);
		t.addVertexWithUV(maxX, minY, minZ, minU, maxV);

		t.addVertexWithUV(minX, minY, maxZ, maxU, maxV);
		t.addVertexWithUV(minX, maxY, maxZ, maxU, minV);
		t.addVertexWithUV(minX, maxY, minZ, minU, minV);
		t.addVertexWithUV(minX, minY, minZ, minU, maxV);

		t.addVertexWithUV(maxX, minY, minZ, maxU, maxV);
		t.addVertexWithUV(maxX, maxY, minZ, maxU, minV);
		t.addVertexWithUV(maxX, maxY, maxZ, minU, minV);
		t.addVertexWithUV(maxX, minY, maxZ, minU, maxV);

	}

	public static void renderUpperlSide(double xCenter, double yCenter, double zCenter, double r, IIcon icon,
			Tessellator t) {

		float maxU = icon.getMaxU();
		float maxV = icon.getMaxV();
		float minU = icon.getMinU();
		float minV = icon.getMinV();

		double minX = xCenter - r;
		double minY = yCenter - r;
		double minZ = zCenter - r;
		double maxX = xCenter + r;
		double maxY = yCenter + r;
		double maxZ = zCenter + r;

		t.addVertexWithUV(maxX, maxY, maxZ, maxU, maxV);
		t.addVertexWithUV(maxX, maxY, minZ, maxU, minV);
		t.addVertexWithUV(minX, maxY, minZ, minU, minV);
		t.addVertexWithUV(minX, maxY, maxZ, minU, maxV);

	}

	public static void renderDownside(double xCenter, double yCenter, double zCenter, double r, IIcon icon,
			Tessellator t) {

		float maxU = icon.getMaxU();
		float maxV = icon.getMaxV();
		float minU = icon.getMinU();
		float minV = icon.getMinV();

		double minX = xCenter - r;
		double minY = yCenter - r;
		double minZ = zCenter - r;
		double maxX = xCenter + r;
		double maxY = yCenter + r;
		double maxZ = zCenter + r;

		t.addVertexWithUV(minX, minY, minZ, maxU, maxV);
		t.addVertexWithUV(maxX, minY, minZ, maxU, minV);
		t.addVertexWithUV(maxX, minY, maxZ, minU, minV);
		t.addVertexWithUV(minX, minY, maxZ, minU, maxV);

	}
}
