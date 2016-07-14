package test;

import java.util.List;

import org.junit.Test;

import tokenizer.Tokenizer;



public class KeyExtractorTest {

	
	@Test
	public void test() {
		String content = "，全球旅行必备话中蒙友好合作，谈中国周边外交，论亚洲国家相处之道，强调互尊互信、聚同化异、守望相助、合作共赢，共创中蒙关系发展新时代，共促亚洲稳定繁荣";
		List<String> list = Tokenizer.getKeyWords(content);
		for(String item:list){
			System.out.println(item+" ");
		}
		
	}
}
