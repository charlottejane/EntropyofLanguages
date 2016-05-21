
//uncomment to print to file
//import java.io.FileOutputStream;  
//import java.io.PrintStream;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.text.DecimalFormat;
import java.util.List;
import java.util.HashMap;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.Normalizer.Form;

public class CalculateEntropy {
	public static String removeDiacriticalMarks(String string) {
	    return Normalizer.normalize(string, Form.NFD)
	        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}
	public static void main (String[] args){
		int total = 0;
		double probability = 0;
		double entropy = 0;
		DecimalFormat dformat = new DecimalFormat("#.#####");
		HashMap<Integer, Integer> hash = new HashMap<>();
		String dirName = "/Users/charlottewuennemann/Documents/CS 560";
		String foreignText = /* "greek560.txt";*/"gujarati.txt";
		Path pathCS560 = Paths.get(dirName, foreignText);
		Charset charset = Charset.forName("UTF-8");

		try {
			//uncomment to print to file
			//FileOutputStream f = new FileOutputStream(dirName+"/output.txt"); 
			// System.setOut(new PrintStream(f));

			List<String> lines = Files.readAllLines(pathCS560, charset);
			for (String line : lines) {
			//	System.setOut(new PrintStream(f));
				//System.out.println(line);
				//line = line.replaceAll("[\\W]", "");
				line = line.replaceAll("\\p{IsPunctuation}+\\p{Graph}+"
						+ "\\p{Blank}+\\p{M}+\\p{Space}"/*+\\P{InGreek}*/,"");
				line =removeDiacriticalMarks(line);

				 for (int i = 0; i < line.length(); i++) {					  
					  char c = line.charAt(i);
						if (Character.isDigit(c) ==false && c != '’' && c!= ' ' 
								&& c != '!' && c != ',' && c != '.' && c != ';'
								&& c != '	' && c != '΄' && c != '“' && c != '”'
								&& c != '-' ) {
						    // Increment existing value in HashMap.
						    // ... Start with zero if no key exists.
							c = Character.toLowerCase(c);
						    int value = hash.getOrDefault((int) c, 0);
						    hash.put((int) c, value + 1);
						}
		      }
		    }
			for (int key : hash.keySet()) {
				 total +=  hash.get(key);
			}
		    System.out.println("Total character count: " + total);

			for (int key : hash.keySet()) {
				
				probability = hash.get(key)/(double)total;
			    entropy += probability * (Math.log(probability)/Math.log(2));

			    System.out.print((char) key + ": " + hash.get(key)+", probability("+ (char) key +") = " );
			    System.out.println(dformat.format(probability)+ 
			    		", entropy(..."+(char) key +") = "+
			    		dformat.format(entropy));
			}
			entropy = -entropy;
			System.out.println("Entropy = " + entropy);

		}

		catch (IOException e) {
			System.out.println(e);
		    }
	}

}
