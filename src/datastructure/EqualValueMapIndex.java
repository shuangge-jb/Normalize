package datastructure;

import java.util.Map;

public class EqualValueMapIndex extends EqualMapIndex {
	private static final double THRESHOLD = 0.82;

	public EqualValueMapIndex() {
		super(THRESHOLD);

	}

	public EqualValueMapIndex(Map<String, EqualMap> equalMapIndex) {
		super(equalMapIndex, THRESHOLD);

	}

	@Override
	public void print() {
		System.out.println("EqualValueMapIndex print.");
		super.print();
	}

}
