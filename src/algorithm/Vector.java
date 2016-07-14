package algorithm;

import java.util.List;

public final class Vector {
	private List<Double> vector;
	private int dimension;

	public Vector() {
		super();
		this.vector = null;
		this.dimension = 0;
	}

	public Vector(List<Double> vector) {
		super();
		this.vector = vector;
		this.dimension = vector.size();
	}

	public Vector(List<Double> vector, int dimension) {
		super();
		this.vector = vector;
		this.dimension = dimension;
	}

	public List<Double> getVector() {
		return vector;
	}

	public int getDimension() {
		return dimension;
	}

	public double get(int index) {
		return vector.get(index).doubleValue();// Double to double
	}

	/**
	 * 求两个向量的内积
	 * 
	 * @param vector2
	 * @return
	 */
	public double transvection(Vector vector2) {
		double sum = 0;
		try {
			if (this.getDimension() != vector2.getDimension()) {
				throw new Exception("维度不相等.");
			} else {

				for (int i = 0; i < getDimension(); i++) {
					sum += this.get(i) * vector2.get(i);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return sum;
	}

	/**
	 * 求模的平方
	 * 
	 * @return
	 */
	public double moldSquare() {
		double sum = 0;
		for (Double item : vector) {
			//System.out.println(item);
			sum += item * item;// 无需转换，自动拆箱
		}
		return sum;
	}
}
