package ab.common.lib.utils;

import java.util.Arrays;

public class Pair <T, U> {
	
	private Object[] object;
	
	public Pair() {
		object = new Object[2];
	}
	
	public Pair(T key, U value) {
		object = new Object[2];
		object[0] = key;
		object[1] = value;
	}
	
	public Pair(Pair<T, U> pair) {
		object = new Object[2];
		object[0] = pair.getKey();
		object[1] = pair.getValue();
	}
	
	public T getKey() {
		return (T)object[0];
	}
	
	public U getValue() {
		return (U) object[1];
	}
	
	public void setKey(T key) {
		object[0] = key;
	}
	
	public void setValue(U value) {
		object[1] = value;
	}
	
	public Pair<T, U> set(T key, U value) {
		object[0] = key;
		object[1] = value;
		return this;
	}
	
	public Pair<U, T> swap() {
		return new Pair<U, T>(getValue(), getKey());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(object);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (!Arrays.deepEquals(object, other.object))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pair: " + Arrays.toString(object);
	}
}
