package default_package;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import divider.*;
import pronoun_match.*;
import synonym_match.*;

public class Main_preprocessing {
	
	static String compound_dic = "D:/JUN/MCMT/preprocessing/packaging_input/input_DCDB_compo.txt";					//Input compound Dictionary
	static String synonym_dic = "D:/JUN/MCMT/preprocessing/synonyms_dic/synonyms_dic_real.tsv";		//synonym Dictionary
	static String UMLS_dic = "D:/JUN/MCMT/REmodule/dictionary/UMLS_DICTIONARY_FOR_20_SEM.txt";		//UMLS Dictionary
	static String Output_Path = "D:/JUN/MCMT/preprocessing/packaging_output/output_DCDB_compo.txt";	//Output path
	
	public static void main(String[] args) throws IOException {
		
		
		//////// Open the compound_dic /////////
		synonyms synonym = new synonyms();
		divide_sentence divider = new divide_sentence();
		pronoun_matcher pronoun_matcher = new pronoun_matcher();
		
		File MOAFile = new File(compound_dic);
		File SYNFile = new File(synonym_dic);
		File UMLSFile = new File(UMLS_dic);
		File OutputFile = new File(Output_Path); 
		
		/*
		// Synonyms match //
		*/
		
		LinkedHashSet<String> after_SYN = new LinkedHashSet<String>();
		after_SYN.clear();
		
		after_SYN = synonym.synonym(MOAFile, SYNFile);
		
		for (String a : after_SYN)
		{
			System.out.println(a);
		}
		
		/*
		// Divide sentence //
		*/
		
		LinkedHashSet<String> after_divide = new LinkedHashSet<String>();
		after_divide.clear();
		
		after_divide = divider.MOAdivider(after_SYN, UMLSFile);
		
		for( String b : after_divide)
		{
			System.out.println(b);
		}
		
		/*
		// Pronoun match //
		*/
		
		pronoun_matcher.pronoun_matcher(after_divide, OutputFile);
	}	
}
