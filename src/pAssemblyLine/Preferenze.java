package pAssemblyLine;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @author Eugenio Sarubbi
 */

public class Preferenze implements Cloneable {
	private JSONObject preferenze;
	private String rootJTree,stileRSyntaxTextArea,stileFinestra,fontFinestra,rootDosBOX;
	private int dimensioneTesto;
	
	public Preferenze() {
		Object obj = null;
		JSONParser jsonParser = new JSONParser();
		File file = new File(System.getProperty("user.dir")+File.separator+"AL");
		PrintWriter printWriter;
		
		file.mkdir();
		
		file = new File(System.getProperty("user.dir")+File.separator+"AL"+File.separator+"preferenze.json");
		
		try {
			file.createNewFile();
			
			Scanner reader = new Scanner(file);
			
			if(!reader.hasNext()) {
				printWriter = new PrintWriter(file.getAbsolutePath());
				
				printWriter.print("{}");
				
				printWriter.close();
				
				obj = jsonParser.parse(new FileReader(file.getAbsolutePath()));
				this.setPreferenze((JSONObject) obj);
				
				this.inizializzaMap();
				
				this.salvaPreferenze();
			}else if(reader.next()=="{}") {
				obj = jsonParser.parse(new FileReader(file.getAbsolutePath()));
				this.setPreferenze((JSONObject) obj);
				
				this.inizializzaMap();
				
				this.salvaPreferenze();
			}else{
				obj = jsonParser.parse(new FileReader(file.getAbsolutePath()));
				this.setPreferenze((JSONObject) obj);
										
				this.startup();
			}
			
			reader.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	public String getRootJTree() {
		return rootJTree;
	}
	
	public void setRootJTree(String rjt) {
		this.rootJTree = rjt;
	}
	
	public JSONObject getPreferenze() {
		return preferenze;
	}
	
	public void setPreferenze(JSONObject p) {
		this.preferenze = p;
	}
	
	public String getStileRSyntaxTextArea() {
		return stileRSyntaxTextArea;
	}
	
	public void setStileRSyntaxTextArea(String srsta) {
		this.stileRSyntaxTextArea = srsta;
	}
	
	public int getDimensioneTesto() {
		return dimensioneTesto;
	}
	
	public void setDimensioneTesto(int dt) {
		this.dimensioneTesto = dt;
	}
	
	public String getStileFinestra() {
		return stileFinestra;
	}
	
	public void setStileFinestra(String sf) {
		this.stileFinestra = sf;
	}
	
	public String getFontFinestra() {
		return fontFinestra;
	}
	
	public void setFontFinestra(String ff) {
		this.fontFinestra = ff;
	}
	
	public String getRootDosBOX() {
		return rootDosBOX;
	}

	public void setRootDosBOX(String rootDosBOX) {
		this.rootDosBOX = rootDosBOX;
	}
	
	public void startup() {
		try {
			this.setRootJTree(this.getPreferenze().get("rootJTree").toString());
			this.setDimensioneTesto(Integer.parseInt(this.getPreferenze().get("dimensioneTesto").toString()));
			this.setFontFinestra(this.getPreferenze().get("fontFinestra").toString());
			this.setStileFinestra(this.getPreferenze().get("stileFinestra").toString());
			this.setStileRSyntaxTextArea(this.getPreferenze().get("stileRSyntaxTextArea").toString());
			this.setRootDosBOX(this.getPreferenze().get("rootDosBOX").toString());
		} catch (NullPointerException npe) {
			this.inizializzaMap();
			this.salvaPreferenze();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void salvaPreferenze() {
		this.getPreferenze().put("rootJTree", this.getRootJTree());
		this.getPreferenze().put("dimensioneTesto", this.getDimensioneTesto());
		this.getPreferenze().put("fontFinestra", this.getFontFinestra());
		this.getPreferenze().put("stileFinestra", this.getStileFinestra());
		this.getPreferenze().put("stileRSyntaxTextArea", this.getStileRSyntaxTextArea());
		this.getPreferenze().put("rootDosBOX", this.getRootDosBOX());
		
		try {
			FileWriter fileWriter = new FileWriter(System.getProperty("user.dir")+File.separator+"AL"+File.separator+"preferenze.json");
			
			fileWriter.write(this.getPreferenze().toJSONString());
			
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	public void inizializzaMap() {
		this.setRootJTree(System.getProperty("user.dir"));
		this.setDimensioneTesto(10);
		this.setFontFinestra("Tahoma");
		this.setStileFinestra("Dark Look and Feel");
		this.setStileRSyntaxTextArea("/org/fife/ui/rsyntaxtextarea/themes/dark.xml");
		this.setRootDosBOX("C:"+File.separator+"Program Files (x86)"+File.separator+"DOSBox-0.74-3"+File.separator+"DOSBox.exe");
		
		try {
			Files.copy(new File("files/TASM.EXE").toPath(), new File(System.getProperty("user.dir")+File.separator+"AL"+File.separator+"TASM.EXE").toPath());
			Files.copy(new File("files/TLINK.EXE").toPath(), new File(System.getProperty("user.dir")+File.separator+"AL"+File.separator+"TLINK.EXE").toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}