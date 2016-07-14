package datastructure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import algorithm.Similarity;

public class EqualMap {
	private Set<String> equalMap;

	public EqualMap() {
		super();
		equalMap = new HashSet<String>();
	}

	public EqualMap(Set<String> equalMap) {
		super();
		this.equalMap = equalMap;
	}

	public Set<String> getEqualMap() {
		return equalMap;
	}

	public void setEqualMap(Set<String> equalMap) {
		this.equalMap = equalMap;
	}

	public boolean add(String str) {
		return equalMap.add(str);
	}

	public Iterator<String> iterator() {
		return equalMap.iterator();
	}

	public List<String> toList() {
		return new ArrayList<String>(equalMap);
	}

	/**
	 * ����һ���ȼۼ��Ϻϲ�����������
	 * 
	 * @param equalMap2
	 * @return
	 */
	public boolean addAll(Set<String> stringSet) {
		return equalMap.addAll(stringSet);
	}

	public boolean addAll(EqualMap equalMap2) {
		Set<String> stringSet = equalMap2.getEqualMap();
		return addAll(stringSet);
	}

	public void print() {
		Iterator<String> iterator = equalMap.iterator();
		System.out.println("�ȼ�ֵ��");
		while (iterator.hasNext()) {
			String value = iterator.next();

			System.out.print("  value: " + value+"\n");
		}
		System.out.println();
		// System.out.println("EdgeMap print finish. ");
	}

	/**
	 * ��������ֵ/����֮������ƶ�,ȡƽ��
	 * 
	 * @param graph
	 *            Ϊ�˺͸������һ�¶���ӵĲ�������������û���õ�
	 * @param map2
	 * @return
	 */
	public double getEqualMapSimilarity(final PatternGraph graph, EqualMap map2) {
		System.out.println("EqualMap getEqualMapSimilarity");
		double sum = 0;
		double counter = 0;
		List<String> arrayOfMap1 = toList();
		List<String> arrayOfMap2 = map2.toList();
		System.out.println("arrayOfMap1.size:  " + arrayOfMap1.size());
		System.out.println("arrayOfMap2.size:  " + arrayOfMap2.size());

		for (int i = 0; i < arrayOfMap1.size(); i++) {
			String key1 = arrayOfMap1.get(i);
			for (int j = i; j < arrayOfMap2.size(); j++) {
				String key2 = arrayOfMap2.get(j);
				sum += Similarity.getValueSimilarity(key1, key2);
				counter++;
			}
		}
		try {
			if (counter == 0) {
				throw new Exception("��������Ϊ0.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sum / counter;

	}

	public boolean contains(String string) {
		return equalMap.contains(string);
	}

	public int size() {
		return equalMap.size();
	}
}
