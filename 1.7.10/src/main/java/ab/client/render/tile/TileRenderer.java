package ab.client.render.tile;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

public class TileRenderer {
	
	public static void renderCube(double xCenter, double yCenter, double zCenter, double r, IIcon icon, Tessellator t) {
		renderCube(xCenter, yCenter, zCenter, r, icon, t, 0.0f);
	}
	
	public static void renderLateralSides(double xCenter, double yCenter, double zCenter, double r, IIcon icon, Tessellator t) {
		renderLateralSides(xCenter, yCenter, zCenter, r, icon, t, 0.0f);
	}
	
	public static void renderUpperlSide(double xCenter, double yCenter, double zCenter, double r, IIcon icon, Tessellator t) {
		renderUpperlSide(xCenter, yCenter, zCenter, r, icon, t, 0.0f);
	}
	
	public static void renderDownside(double xCenter, double yCenter, double zCenter, double r, IIcon icon, Tessellator t) {
		renderUpperlSide(xCenter, yCenter, zCenter, r, icon, t, 0.0f);
	}
	
	public static void renderCube(double xCenter, double yCenter, double zCenter, double r, IIcon icon, Tessellator t, float sideOffset) {

		renderLateralSides(xCenter, yCenter, zCenter, r, icon, t, sideOffset);
		
		renderUpperlSide(xCenter, yCenter, zCenter, r, icon, t, sideOffset);
		
		renderDownside(xCenter, yCenter, zCenter, r, icon, t, sideOffset);
		
	}

	public static void renderLateralSides(double xCenter, double yCenter, double zCenter, double r, IIcon icon, Tessellator t, float sideOffset) {
		
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

		t.addVertexWithUV(maxX, minY, maxZ + sideOffset, maxU, maxV);
		t.addVertexWithUV(maxX, maxY, maxZ + sideOffset, maxU, minV);
		t.addVertexWithUV(minX, maxY, maxZ + sideOffset, minU, minV);
		t.addVertexWithUV(minX, minY, maxZ + sideOffset, minU, maxV);

		t.addVertexWithUV(minX, minY, minZ - sideOffset, maxU, maxV);
		t.addVertexWithUV(minX, maxY, minZ - sideOffset, maxU, minV);
		t.addVertexWithUV(maxX, maxY, minZ - sideOffset, minU, minV);
		t.addVertexWithUV(maxX, minY, minZ - sideOffset, minU, maxV);

		t.addVertexWithUV(minX - sideOffset, minY, maxZ, maxU, maxV);
		t.addVertexWithUV(minX - sideOffset, maxY, maxZ, maxU, minV);
		t.addVertexWithUV(minX - sideOffset, maxY, minZ, minU, minV);
		t.addVertexWithUV(minX - sideOffset, minY, minZ, minU, maxV);

		t.addVertexWithUV(maxX + sideOffset, minY, minZ, maxU, maxV);
		t.addVertexWithUV(maxX + sideOffset, maxY, minZ, maxU, minV);
		t.addVertexWithUV(maxX + sideOffset, maxY, maxZ, minU, minV);
		t.addVertexWithUV(maxX + sideOffset, minY, maxZ, minU, maxV);

	}

	public static void renderUpperlSide(double xCenter, double yCenter, double zCenter, double r, IIcon icon, Tessellator t, float sideOffset) {

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

		t.addVertexWithUV(maxX, maxY + sideOffset, maxZ, maxU, maxV);
		t.addVertexWithUV(maxX, maxY + sideOffset, minZ, maxU, minV);
		t.addVertexWithUV(minX, maxY + sideOffset, minZ, minU, minV);
		t.addVertexWithUV(minX, maxY + sideOffset, maxZ, minU, maxV);

	}

	public static void renderDownside(double xCenter, double yCenter, double zCenter, double r, IIcon icon, Tessellator t, float sideOffset) {

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

		t.addVertexWithUV(minX, minY - sideOffset, minZ, maxU, maxV);
		t.addVertexWithUV(maxX, minY - sideOffset, minZ, maxU, minV);
		t.addVertexWithUV(maxX, minY - sideOffset, maxZ, minU, minV);
		t.addVertexWithUV(minX, minY - sideOffset, maxZ, minU, maxV);

	}
}