package pulxes.ab.api;

public abstract class EffectRender {
	
	public int tick;
	
	public EffectRender(int tick) {
		this.tick = tick;
	}
	
	public abstract void doEffect();
}
