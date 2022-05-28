package pulxes.ab.client.core.helper;

import java.util.function.IntFunction;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import pulxes.ab.AdvancedBotany;
import vazkii.botania.client.core.proxy.ClientProxy;
import vazkii.botania.common.core.helper.Vector3;

public class ClientHelper {
	
	public static void registerCustomItemblock(Block b, String path) {
		registerCustomItemblock(b, 1, i -> path);
	}
	
	public static void registerCustomItemblock(Block b, int maxExclusive, IntFunction<String> metaToPath) {
	    Item item = Item.getItemFromBlock(b);
	    for (int i = 0; i < maxExclusive; i++)
	    	ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(AdvancedBotany.MODID + ":itemblock/" + (String)metaToPath.apply(i), "inventory")); 
	  }
	
	public static void setLightmapTextureCoords() {
		int light = 15728880;
        int lightmapX = light % 65536;
        int lightmapY = light / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);
	}

	public static void renderIcon(TextureAtlasSprite icon, int light) {
		Tessellator tessellator = Tessellator.getInstance();
		float f = icon.getMinU();
		float f1 = icon.getMaxU();
	    float f2 = icon.getMinV();
	    float f3 = icon.getMaxV();
	    float f4 = 1.0F;
	    float f5 = 0.5F;
	    float f6 = 0.25F;
	    tessellator.getBuffer().begin(7, ClientProxy.POSITION_TEX_LMAP);
	    tessellator.getBuffer().pos((0.0F - f5), (0.0F - f6), 0.0D).tex(f, f3).lightmap(light, light).endVertex();
	    tessellator.getBuffer().pos((f4 - f5), (0.0F - f6), 0.0D).tex(f1, f3).lightmap(light, light).endVertex();
	    tessellator.getBuffer().pos((f4 - f5), (f4 - f6), 0.0D).tex(f1, f2).lightmap(light, light).endVertex();
	    tessellator.getBuffer().pos((0.0F - f5), (f4 - f6), 0.0D).tex(f, f2).lightmap(light, light).endVertex();
	    tessellator.draw();
	}

	public static Vector3 setRotation(float angel, float vX, float vY, float vZ, Vector3 v3) {
		Vector3 rVec = new Vector3(vX, vY, vZ);
		Vector3 rVec1 = new Vector3(v3.x, v3.y, v3.z).normalize();
		double rAngel = Math.toRadians(angel) * 0.5f;
		double sin = Math.sin(rAngel);
		double x = rVec.x * sin;
		double y = rVec.y * sin;
		double z = rVec.z * sin;
		rAngel = Math.cos(rAngel);
		double d = -x * rVec1.x - y * rVec1.y - z * rVec1.z;
		double d1 = rAngel * rVec1.x + y * rVec1.z - z * rVec1.y;
		double d2 = rAngel * rVec1.y - x * rVec1.z + z * rVec1.x;
		double d3 = rAngel * rVec1.z + x * rVec1.y - y * rVec1.x;
		double finX = d1 * rAngel - d * x - d2 * z + d3 * y;
		double finY = d2 * rAngel - d * y + d1 * z - d3 * x;
		double finZ = d3 * rAngel - d * z - d1 * y + d2 * x;
		return new Vector3(finX, finY, finZ);
	}
}
