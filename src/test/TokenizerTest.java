package test;

import java.util.List;

import org.junit.Test;

import tokenizer.Tokenizer;

public class TokenizerTest {

	@Test
	public void testTokenizer() {
		String content = "��ȫ�����бر��������Ѻú�����̸�й��ܱ��⽻�������޹����ദ֮����ǿ�������š���ͬ���졢����������������Ӯ���������ɹ�ϵ��չ��ʱ�������������ȶ�����";
		List<String> list = Tokenizer.tokenize(content);
		for (String item : list) {
			System.out.println(item + " ");
		}
	}

	@Test
	public void testGetKeyWords() {
		String content = "��ȫ�����бر��������Ѻú�����̸�й��ܱ��⽻�������޹����ദ֮����ǿ�������š���ͬ���졢����������������Ӯ���������ɹ�ϵ��չ��ʱ�������������ȶ�����";
		List<String> list = Tokenizer.getKeyWords(content);
		for (String item : list) {
			System.out.println(item + " ");
		}
	}

}
