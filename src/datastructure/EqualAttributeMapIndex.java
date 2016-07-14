package datastructure;

import java.util.Map;

public class EqualAttributeMapIndex extends EqualMapIndex {
	private static final double THRESHOLD = 0.93;

	public EqualAttributeMapIndex() {
		super(THRESHOLD);

	}

	public EqualAttributeMapIndex(Map<String, EqualMap> equalMapIndex) {
		super(equalMapIndex, THRESHOLD);

	}

	
}
