package pronoun_match;

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

public class pronoun_matcher {

	public void pronoun_matcher(LinkedHashSet<String> after_divide, File OutputFile) throws IOException {
		System.out.println("Start");

		// creates a StanfordCoreNLP object, with POS tagging, lemmatization,
		// NER, parsing, and coreference resolution
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		String result_1 = ""; // open main Dic
		String regExpNumber = "0123456789 ";
		
		int count = 00001;
		
		String[] array1 = null;
		String[] array2 = null;

		BufferedWriter out = new BufferedWriter(new FileWriter(OutputFile));
				
		for (String o : after_divide) {
			array1 = o.split("\t");
			int size = Integer.parseInt(array1[3]);

			String core_MOA = "";
			String num = array1[0];
			String entity_1 = array1[1];
			String MOA = array1[2];
			String case_type = array1[3];
			String UMLS_entity2 = array1[4];
			String UMLS_num	= array1[5];
			String UMLS_type = array1[6];
			String MOA_1ch = "";
			String MOA_2ch = "";
			String key_set = "";
			
			if (size > 1) {
				MOA_1ch = MOA.replace(entity_1, "Apple");
								
				if(MOA_1ch.contains("these events")){
					MOA_2ch = MOA_1ch.replace("these events", "It");
				}
				else if(MOA_1ch.contains("this binding")){
					MOA_2ch = MOA_1ch.replace("this binding", "It");
				}
				else if(MOA_1ch.contains("this inotropic effect")){
					MOA_2ch = MOA_1ch.replace("this inotropic effect", "It");
				}
				else if(MOA_1ch.contains("this effect")){
					MOA_2ch = MOA_1ch.replace("this effect", "It");
				}
				else if(MOA_1ch.contains("this enzyme")){
					MOA_2ch = MOA_1ch.replace("this enzyme", "It");
				}
				else if (MOA_1ch.contains(" this ")) {
					MOA_2ch = MOA_1ch.replace(" this ", " It ");
				}
				else if(MOA_1ch.contains(" it ")){
					MOA_2ch = MOA_1ch.replace(" it ", " It ");
				}
				else{ }
				
				Annotation document = new Annotation(MOA_2ch);
				pipeline.annotate(document);
				List<CoreMap> sentences = document.get(SentencesAnnotation.class);
				
				Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);
		
			/////////////////////////get chain of pronoun//////////////////////////	
				key_set = graph.keySet().toString();

				StringBuffer sb = new StringBuffer();
				
				for(int j=0; j < key_set.length(); j++){
					char c = key_set.charAt(j);
					String c_string = ""+c;
					if(regExpNumber.contains(c_string))
					{
						sb.append(c_string);
					}
					else{}					
				}
				
				key_set = sb.toString();				
				array2 = key_set.split(" ");
				
				for (int k = 0; k < array2.length; k++) {
					if (array2[k].equals(null)||array2[k].equals("")||array2[k].length()==0) {} 
					else {
						int chain_num = Integer.parseInt(array2[k]);
						StringBuffer temp = new StringBuffer();
						temp.append(graph.get(chain_num));
						String check_MOA = temp.toString();

						if (check_MOA.contains("Apple") && check_MOA.contains("It")) {
							StringBuffer temp_sen = new StringBuffer();
							temp_sen.append(sentences.get(1));
							core_MOA = temp_sen.toString();
							core_MOA = core_MOA.replace("It", entity_1);
							//bw.write("moa"+count +"\t" + num + "\t" + entity_1 + "\t" + core_MOA + "\t" + UMLS_entity2 + "\t" + case_type );

							out.write("moa"+count +"\t" + num + "\t" + entity_1 + "\t" + core_MOA + "\t" + UMLS_entity2 + "\t" + UMLS_num + "\t" + UMLS_type + "\t" + case_type );
							count++;
							out.newLine();
							break ;
						} else {
							
						}
					}
				}
				
			////////////////////////////////////////////////////////////////////////	
				
				
			}
			else{
				//	bw.write("moa"+count +"\t" + num + "\t" + entity_1 + "\t" + MOA + "\t" + UMLS_entity2 + "\t" + case_type);

				out.write("moa"+count +"\t" + num + "\t" + entity_1 + "\t" + MOA + "\t" + UMLS_entity2 + "\t" + UMLS_num + "\t" + UMLS_type + "\t" + case_type);
				count++;
				out.newLine();
			}
		}
		System.out.println("end");
		out.close();
		
	}

}

