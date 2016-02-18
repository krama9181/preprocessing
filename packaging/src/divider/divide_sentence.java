package divider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

import edu.stanford.nlp.dcoref.*;

public class divide_sentence {
	public LinkedHashSet<String> MOAdivider(LinkedHashSet<String> after_SYN, File UMLSFile) throws IOException {
		// creates a StanfordCoreNLP object, with POS tagging, lemmatization,
		// NER, parsing, and coreference resolution
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit  pos, lemma");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		int count = 0;

		String result_1 = ""; // open main Dic
		String result_2 = ""; // open semantic Types

		String[] array1 = null;
		String[] array2 = null;

		String UMLS_num = null;
		String UMLS_type = null;
		String entity2_space = null;
		String entity2_comma = null;
		String entity2_period = null;
		String entity2_semi = null;
		String entity2_bar = null;
		String core_sentence = null;
		String check_core = null;

//		File file = new File("D:/JUN/MCMT/preprocessing/2.divide_sentence/core_sentence_all.txt");		//새롭게 저장할 파일
//		FileWriter fw = new FileWriter(file.getAbsoluteFile());
//		bw = new BufferedWriter(fw);

		LinkedHashSet<String> after_divide = new LinkedHashSet<String>();
		
		for (String o : after_SYN) {
			array1 = o.split("\t");
			String entity1 = array1[0];

			String MOA = array1[1];

			String regExpSpcial = "[^\"'\\{\\}\\[\\]/?.,;:|\\)\\(*~`!^\\-_+<>@#$%^\\\\=]";
			String regExpAlpha = "abcdefghijkmnlopqrstuvwxyzABCDEFGHIJKMNLOPQRSTUVWXYZ";
			String regExpNumber = "0123456789";

			StringBuffer sb = new StringBuffer();

			for (int j = 0; j < MOA.length(); j++) {
				char c = MOA.charAt(j);
				String c_string = "" + c;

				if (regExpSpcial.contains(c_string)) {
					sb.append(c_string);
				} else if (regExpAlpha.contains(c_string)) {
					sb.append(c_string);
				} else if (regExpNumber.contains(c_string)) {
					sb.append(c_string);
				} else if (c_string.equals(" ")) {
					sb.append(c_string);
				} else {
				}
			}
			MOA = sb.toString();
			// System.out.println(MOA);

			String NUM = array1[2];
			entity1 = entity1.toLowerCase();

			// create an empty Annotation just with the given text
			Annotation document = new Annotation(MOA);
			// run all Annotators on this text
			pipeline.annotate(document);
			// these are all the sentences in this document
			// a CoreMap is essentially a Map that uses class objects as keys
			// and has values with custom types
			List<CoreMap> sentences = document.get(SentencesAnnotation.class);

			StringBuffer core_MOA = new StringBuffer();

			for (int i = 0; i < sentences.size(); i++) {
				// System.out.println("--divide each sentence--");
				count = 0;
				count++;
				StringBuffer arr_moa = new StringBuffer();
				arr_moa.append(sentences.get(i));
				check_core = arr_moa.toString();
				check_core = check_core.toLowerCase();

				BufferedReader entity2_Dic = new BufferedReader(new FileReader(UMLSFile));

				while ((result_2 = entity2_Dic.readLine()) != null) {
					array2 = result_2.split("\t");
					UMLS_num = array2[0];
					UMLS_type = array2[1]; // T038

					String func = array2[2];
					func = func.toLowerCase();

					StringBuffer entity2_temp_1 = new StringBuffer(); // _entity2_
					StringBuffer entity2_temp_2 = new StringBuffer(); // _entity2.
					StringBuffer entity2_temp_3 = new StringBuffer(); // _entity2,
					StringBuffer entity2_temp_4 = new StringBuffer(); // -entity2
					StringBuffer entity2_temp_5 = new StringBuffer(); // entity2;

					entity2_temp_1.append(" ");
					entity2_temp_1.append(func);
					entity2_temp_1.append(" ");
					entity2_space = entity2_temp_1.toString();
					entity2_temp_3.append(" ");
					entity2_temp_3.append(func);
					entity2_temp_3.append(",");
					entity2_comma = entity2_temp_3.toString();
					entity2_temp_2.append(" ");
					entity2_temp_2.append(func);
					entity2_temp_2.append(".");
					entity2_period = entity2_temp_2.toString();
					entity2_temp_4.append("-");
					entity2_temp_4.append(func);
					entity2_bar = entity2_temp_4.toString();
					entity2_temp_5.append(" ");
					entity2_temp_5.append(func);
					entity2_temp_5.append(";");
					entity2_semi = entity2_temp_5.toString();

					//////////////////////////////////////////////
					if (func.length() < 6) {			///For removing noise
					}

					else {
						if ((check_core.contains(entity2_semi) || check_core.contains(entity2_bar)
								|| check_core.contains(entity2_space) || check_core.contains(entity2_comma)
								|| check_core.contains(entity2_period)) && check_core.contains(entity1)) {

							// if (check_core.contains(func)&&
							// check_core.contains(entity1)) {

							System.out.println(entity1 + " and " + func);
							System.out.println("There are entity1 and enitity2 in a sentence");

							after_divide.add(NUM + "\t" + entity1 + "\t" + check_core + "\t" + "1" + "\t" + func +"\t" + UMLS_num + "\t" +UMLS_type);
							break;
						} else if ((check_core.contains("it ") && check_core.contains(func))
								|| (check_core.contains("this ") && check_core.contains(func))
								|| (check_core.contains("these ") && check_core.contains(func))) {

							if (i > 0) {
								StringBuffer temp_moa = new StringBuffer();
								temp_moa.append(sentences.get(i - 1));
								String before_core = temp_moa.toString();
								before_core = before_core.toLowerCase();

								if (before_core.contains(entity1)) {

									System.out.println(entity1 + " and " + func);
									core_MOA.append(sentences.get(i - 1));
									core_MOA.append(" ");
									core_MOA.append(sentences.get(i));

									System.out.println("There are pronoun and enitity2 in a sentence");
									core_sentence = core_MOA.toString();
									after_divide.add(NUM + "\t" + entity1 + "\t" + core_sentence + "\t" + "2" + "\t" + func +"\t" + UMLS_num + "\t" +UMLS_type);
									core_MOA.setLength(0);
									break;

								} else {
								}
							} else {
							}
						} else {
						}
					}
				}
				entity2_Dic.close();
			}

		}
		System.out.println("finish");

		return after_divide;
	}
}
