package pulxes.ab.client.core.helper;

import vazkii.botania.common.core.helper.Vector3;

public class ClientHelper {

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
