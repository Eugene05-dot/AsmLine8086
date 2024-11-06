package pAssemblyLine;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author Eugenio Sarubbi
 */

public class FileUtility {
	public static boolean creaProgetto(String indirizzo, String nome) {
		File directory = new File(indirizzo+File.separator+nome);
		
		directory.mkdir();
		
		indirizzo+=File.separator+nome;
		
		if(creaFile(indirizzo,nome))
			return true;
		
		return false;
	}
	
	public static boolean fileEsistente(File file) {
		return file.exists() && !file.isDirectory();
    }
	
	public static boolean creaFile(String indirizzo, String nome) {
		File file = new File(indirizzo+File.separator+nome+".asm");
		
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			
			return false;
		}
		
		if(fileEsistente(file))
			return true;
		
		
		return false;
	}
	
	public static boolean pronto(File file) {
		return (fileEsistente(file) && file.canExecute() && file.canRead() && file.canWrite());
	}
	
	public static String apriFile(File file) {
		if(pronto(file)) {
			try {
				String s="";
				Scanner scan = new Scanner(file);
				
				while(scan.hasNext())
					s+=scan.nextLine()+"\n";
				
				scan.close();
				
				return s;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return "";
	}
	
	public static boolean salvaFile(File file, String s) {	
		if(pronto(file)) {
			try {
				PrintWriter printWriter = new PrintWriter(file);
				
				printWriter.print(s);
				
				printWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
				
				return false;
			}
			
			return true;
		}
		
		return false;
	}
}