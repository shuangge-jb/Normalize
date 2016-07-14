package tokenizer;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Tokenizer {

	public Tokenizer() {
		// TODO Auto-generated constructor stub
	}

	public static List<String> tokenize(String str) {
		StringReader reader = new StringReader(str);
		IKSegmenter ik = new IKSegmenter(reader, true);// 当为true时，分词器进行最大词长切分
		Lexeme lexeme = null;
		List<String> list = new ArrayList<String>();
		try {
			while ((lexeme = ik.next()) != null)
				list.add(lexeme.getLexemeText());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			reader.close();
		}
		return list;
	}

	public static List<String> getKeyWords(String str) {
		List<String> list = new ArrayList<String>();
		IKAnalyzer ka = new IKAnalyzer();
		Reader r = new StringReader(str);
		TokenStream ts;
		try {
			ts = (TokenStream) ka.tokenStream("", r);

			ts.addAttribute(CharTermAttribute.class);

			while (ts.incrementToken()) {
				CharTermAttribute ta = ts.getAttribute(CharTermAttribute.class);
				list.add(ta.toString());
				System.out.println(ta.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ka.close();
		}

		return list;
	}
}
