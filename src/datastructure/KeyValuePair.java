package datastructure;

/**
 * 键值对,为不可变对象，hashcode不改变
 * 
 * @author Administrator
 *
 */
public final class KeyValuePair {
	private final String K;
	private final String V;

	public KeyValuePair() {
		super();
		K = null;
		V = null;
	}

	public KeyValuePair(String key, String value) {
		super();
		this.K = key;
		this.V = value;
	}

	public final String getKey() {
		return K;
	}

	public final String getValue() {
		return V;
	}

	@Override
	public boolean equals(Object pair2) {
		if (pair2 instanceof KeyValuePair) {
			if ((this.K.equals(((KeyValuePair) pair2).getKey()))
					&& (this.getValue().equals(((KeyValuePair) pair2)
							.getValue()))) {
				return true;
			} else {
				return false;
			}
		} else {
			return super.equals(pair2);
		}

	}

	/**
	 * 
	 */
	@Override
	public int hashCode() {
		
		return super.hashCode();
	}

	public void print() {
		System.out.print("hashcode: " + this.hashCode());
		System.out.println("key= " + K + " value= " + V);
	}

}
