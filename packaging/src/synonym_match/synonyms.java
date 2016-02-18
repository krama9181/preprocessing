package synonym_match;

import java.util.Iterator;
import java.util.Set;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Locale.FilteringMode;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class synonyms {
	private static BufferedReader syn_Dic;
	    
	public LinkedHashSet<String> synonym(File MOAFile, File synonym_dic) throws IOException {
		// TODO Auto-generated method stub

		int count = 0;

		String result_1 = ""; // open main Dic
		String result_2 = ""; // open synonyms

		String[] array1 = null;
		String[] SYN = null;
		String[] array2 = null;

		String after_change = null;
		String syn = null;
		String MAS = null;
		String entity1 = null;

		BufferedReader main_Dic = new BufferedReader(new FileReader(MOAFile));
		
		LinkedHashSet<String> synonymmatch = new LinkedHashSet<String>();

		LinkedHashMap<String, LinkedHashSet<String>> sentences_map = new LinkedHashMap<String, LinkedHashSet<String>>();

		synonymmatch.clear();
		
		System.out.println("Start");

		while ((result_2 = main_Dic.readLine()) != null) {
			
			array2 = result_2.split("\t");
			
			String NUM = array2[0];
			entity1 = array2[1];					//compound's name
			String MOA = array2[2];					//compound's Machanism of Action
			
			String L_entity1 = entity1.toLowerCase();
			after_change = MOA.toLowerCase();
			String except_entity1 = after_change.replace(L_entity1, " ");
						
			syn_Dic = new BufferedReader(new FileReader(synonym_dic));

			//////////////////// Start comparing/////////////////////
			while ((result_1 = syn_Dic.readLine()) != null) {

				array1 = result_1.split("\t");

				StringBuffer temp_arr = new StringBuffer();
				temp_arr.append(array1[0]);
				//temp_arr.append(" ");
				MAS = temp_arr.toString();

				String temp = array1[1];
				temp = temp.replaceAll("\"", "");
				SYN = temp.split("\\/"); // synonyms

				String syn_num = array1[3];

				int SYN_length = SYN.length;

				if (NUM.equals(syn_num)) {
					for (int i = 0; i < SYN_length; i++) {

						StringBuffer temp_syn = new StringBuffer();
						temp_syn.append(SYN[i]);
						//temp_syn.append(" ");
						syn = temp_syn.toString();

						String syn_Lower = syn.toLowerCase(); // change to
																// lowercase

						if (syn_Lower.equals("his ") || syn_Lower.equals("d ") || syn_Lower.equals("a ")
								|| syn_Lower.equals("n ") || syn_Lower.equals("fs ") || syn_Lower.equals("95 ")
								|| syn_Lower.equals("dv ") || syn_Lower.equals("sm ") || syn_Lower.equals("amp ")
								|| syn_Lower.equals("i ") || syn_Lower.equals("g ") || syn_Lower.equals("r ")
								|| syn_Lower.equals("h ") || syn_Lower.equals("tht ") || syn_Lower.equals("val ")
								|| syn_Lower.equals("tg ") || syn_Lower.equals("ki ") || syn_Lower.equals("cp ")
								|| syn_Lower.equals("fer ") || syn_Lower.equals("f ") || syn_Lower.equals("k ")
								|| syn_Lower.equals("gh ") || syn_Lower.equals("gly ") || syn_Lower.equals("pv ")
								|| syn_Lower.equals("al ") || syn_Lower.equals("w ") || syn_Lower.equals("5 ")
								|| syn_Lower.equals("pa ") || syn_Lower.equals("+ ") || syn_Lower.equals("(S ")
								|| syn_Lower.equals("ap ") || syn_Lower.equals("z ") || syn_Lower.equals("cd ")
								|| syn_Lower.equals("sa ") || syn_Lower.equals("ps ") || syn_Lower.equals("(+ ")
								|| syn_Lower.equals("cc ") || syn_Lower.equals("lh ") || syn_Lower.equals("no ")
								|| syn_Lower.equals("phe ") || syn_Lower.equals("pg ") || syn_Lower.equals("fe ")
								|| syn_Lower.equals("ng ") || syn_Lower.equals("dm ") || syn_Lower.equals("dt ")
								|| syn_Lower.equals("gu ") || syn_Lower.equals("fu ")) {
						} else {

							if (except_entity1.contains(syn_Lower)) {
								after_change = after_change.replace(syn_Lower, MAS);
							} else {
							}
						}
					}

				}

				else {
				}
			}

			synonymmatch.add(entity1 + "\t" + after_change + "\t" + NUM);
		}

		main_Dic.close();
		syn_Dic.close();
		System.out.println("finish");
		
		
		return synonymmatch;

	}
}
