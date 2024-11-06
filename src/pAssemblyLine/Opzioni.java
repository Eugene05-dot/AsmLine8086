package pAssemblyLine;

import javax.swing.JFrame;
import javax.swing.UIManager;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import java.awt.Component;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JRadioButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Opzioni {
	private JFrame opzioni;
	private JLabel lblPersonalizzaJToolBar;
	private JLabel lblRootJTree;
	private JTextField txtRootJTree;
	private JButton btnCambiaRoot;
	private JLabel lblStileRsyntaxtextarea;
	private JComboBox<String> cbStileTextArea;
	private JSpinner sDimensioneTesto;
	private JLabel lblTema;
	private JLabel lblFontFinestra;
	private JComboBox<String> cbFontFinestra;
	private JButton btnAnnulla;
	private JButton btnApplica;
	private JButton btnOK;
	private String fonts[];
	private Preferenze pVecchie,pNuove;
	private JFileChooser fileChooser;
	private HashMap<String, Object> stiliFinestra;
	private HashMap<String, String> stiliTextArea;
	private JRadioButton rdbtnChiaro;
	private JRadioButton rdbtnScuro;
	private ButtonGroup buttonGroup;
	private JLabel lblVersione;
	private JLabel lblPosizioneDosBOX;
	private JTextField txtPosizioneDosBOX;
	private JButton btnCambiaDosBOX;
	
	public Opzioni() {
		pVecchie = new Preferenze();
	}
	
	public HashMap<String, Object> getStiliFinestra() {
		return this.stiliFinestra;
	}
	
	public Preferenze opzioniAggiornate() {
		return this.pNuove;
	}
	
	public Preferenze opzioniSalvate() {
		return this.pVecchie;
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void mostraOpzioni(AppAssemblyLine aal) {
		stiliFinestra = new HashMap<String, Object>();
		stiliTextArea = new HashMap<String, String>();
		
        stiliFinestra.put("Dark Look and Feel",new FlatDarkLaf());
        stiliFinestra.put("Light Look and Feel",new FlatLightLaf());
		
        stiliTextArea.put("dark","/org/fife/ui/rsyntaxtextarea/themes/dark.xml");
		stiliTextArea.put("default","/org/fife/ui/rsyntaxtextarea/themes/default.xml");
		stiliTextArea.put("default-alt","/org/fife/ui/rsyntaxtextarea/themes/default-alt.xml");
		stiliTextArea.put("eclipse","/org/fife/ui/rsyntaxtextarea/themes/eclipse.xml");
		stiliTextArea.put("idea","/org/fife/ui/rsyntaxtextarea/themes/idea.xml");
		stiliTextArea.put("monokai","/org/fife/ui/rsyntaxtextarea/themes/monokai.xml");
		stiliTextArea.put("vs","/org/fife/ui/rsyntaxtextarea/themes/vs.xml");
		
		opzioni = new JFrame();
		opzioni.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(pNuove!=null) {
					if(!aal.pBenvenuto.isVisible()) {
		    			for(int i=0;i<aal.tabbedPane.getTabCount();i++) {
		    				aal.changeStyleViaThemeXml((RSyntaxTextArea) aal.reperisciTextArea(i).getTextArea());
		    				aal.reperisciTextArea(i).getTextArea().setFont(new Font(pNuove.getFontFinestra(),0,pNuove.getDimensioneTesto()));
		    			}
		    		}
		    		
		    		aal.tree.setModel(new FileSystemModel(new File(pNuove.getRootJTree())));
		    		
		    		String s=UIManager.getLookAndFeelDefaults().get("defaultFont").toString();
			        
			        s=s.substring(s.indexOf("name"), s.length());
			        s=s.substring(s.indexOf("="),s.indexOf(",")).replaceAll("[=]", "");
				}
				
				String[] options = new String[2];
				
    			options[0] = "Riavvia ora";
    			options[1] = "Annulla";
    			
    			if(JOptionPane.showOptionDialog(opzioni.getContentPane(), "Per applicare le nuove opzioni è necessario riavviare il programma", "Avviso", 0, JOptionPane.WARNING_MESSAGE, null, options, null)==0)
    				restartApplication();
			}
		});
		cbFontFinestra = new JComboBox<String>();
		
		fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		
		for(int i=0;i<fonts.length;i++)
	    	cbFontFinestra.addItem(fonts[i]);
		
		lblPersonalizzaJToolBar = new JLabel("Personalizza JToolBar:");
		lblRootJTree = new JLabel("Root JTree:");
		txtRootJTree = new JTextField();
		btnCambiaRoot = new JButton("Cambia...");
	    btnCambiaRoot.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		fileChooser.setDialogTitle("Cambia root");
	    		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    		
	    		int scelta = fileChooser.showOpenDialog(opzioni.getContentPane());
	    		
	    		if(scelta==JFileChooser.APPROVE_OPTION)
	    			txtRootJTree.setText(fileChooser.getCurrentDirectory().getAbsolutePath());
	    	}
	    });
	    lblStileRsyntaxtextarea = new JLabel("Stile RSyntaxTextArea:");
	    cbStileTextArea = new JComboBox<String>();
	    lblTema = new JLabel("Tema:");
	    
	    btnOK = new JButton("OK");
	    btnOK.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		if(pNuove!=null)
	    			pVecchie = (Preferenze) pNuove.clone();
	    		
	    		pVecchie.salvaPreferenze();
	    		
	    		opzioni.dispatchEvent(new WindowEvent(opzioni, WindowEvent.WINDOW_CLOSING));
	    		
	    		pNuove = null;
	    	}
	    });
	    btnApplica = new JButton("Applica");
	    btnApplica.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		pNuove = new Preferenze();
	    		
	    		pNuove.setFontFinestra(cbFontFinestra.getSelectedItem().toString());
	    		
	    		if(new File(txtRootJTree.getText()).exists())
	    			pNuove.setRootJTree(txtRootJTree.getText());
	    		else{
	    			JOptionPane.showMessageDialog(opzioni.getContentPane(), "Root non trovata");
	    			pNuove.setRootJTree("C:"+File.separator);
	    		}
	    		
	    		if(rdbtnScuro.isSelected())
	    			pNuove.setStileFinestra("Dark Look and Feel");
	    		else
	    			pNuove.setStileFinestra("Light Look and Feel");
	    		
	    		pNuove.setStileRSyntaxTextArea(stiliTextArea.get(cbStileTextArea.getSelectedItem()).toString());
	    		
	    		String s = sDimensioneTesto.getValue().toString();
	    		
	    		pNuove.setDimensioneTesto(Integer.parseInt(s));
	    	}
	    });
	    btnAnnulla = new JButton("Annulla");
	    btnAnnulla.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		pNuove = pVecchie;
	    		
	    		opzioni.setVisible(false);
	    	}
	    });
	    lblFontFinestra = new JLabel("Font finestra:");
	    sDimensioneTesto = new JSpinner();
	    sDimensioneTesto.setModel(new SpinnerNumberModel(Integer.valueOf(10), Integer.valueOf(1), null, Integer.valueOf(1)));
	    rdbtnChiaro = new JRadioButton("Chiaro");
	    rdbtnScuro = new JRadioButton("Scuro");
	    buttonGroup = new ButtonGroup();
	    lblVersione = new JLabel("Ver 1.0.0");
	    fileChooser = new JFileChooser();
	    
	    if(this.opzioniSalvate().getStileFinestra().equals("Dark Look and Feel"))
	    	rdbtnScuro.setSelected(true);
	    else
	    	rdbtnChiaro.setSelected(true);
	    
	    buttonGroup.add(rdbtnChiaro);
	    buttonGroup.add(rdbtnScuro);
	    
	    sDimensioneTesto.setValue(pVecchie.getDimensioneTesto());
	    
		opzioni.setVisible(true);
		opzioni.setBounds(100, 100, 410, 250);
		opzioni.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		opzioni.setTitle("Opzioni");
		opzioni.setResizable(false);
		
		lblPersonalizzaJToolBar.setBounds(10, 10, 152, 26);
		
		lblRootJTree.setHorizontalAlignment(SwingConstants.LEFT);
		lblRootJTree.setBounds(10, 9, 73, 26);
		opzioni.getContentPane().add(lblRootJTree);
		txtRootJTree.setBounds(70, 10, 222, 26);
		txtRootJTree.setColumns(10);
		txtRootJTree.setText(this.opzioniSalvate().getRootJTree());
		opzioni.getContentPane().add(txtRootJTree);
	    opzioni.getContentPane().setLayout(null);
	    
	    btnCambiaRoot.setBounds(302, 12, 85, 21);
	    opzioni.getContentPane().add(btnCambiaRoot);
	    
	    lblStileRsyntaxtextarea.setHorizontalAlignment(SwingConstants.LEFT);
	    lblStileRsyntaxtextarea.setBounds(10, 45, 110, 26);
	    opzioni.getContentPane().add(lblStileRsyntaxtextarea);
	    
	    cbStileTextArea.addItem("dark");
		cbStileTextArea.addItem("default");
		cbStileTextArea.addItem("default-alt");
		cbStileTextArea.addItem("eclipse");
		cbStileTextArea.addItem("idea");
		cbStileTextArea.addItem("monokai");
		cbStileTextArea.addItem("vs");
	    
	    cbStileTextArea.setBounds(120, 48, 197, 21);
	    opzioni.getContentPane().add(cbStileTextArea);
	    
	    lblTema.setHorizontalAlignment(SwingConstants.LEFT);
	    lblTema.setBounds(10, 81, 38, 26);
	    opzioni.getContentPane().add(lblTema);
	    
	    btnOK.setBounds(302, 179, 85, 21);
	    opzioni.getContentPane().add(btnOK);
	    
	    btnApplica.setBounds(207, 179, 85, 21);
	    opzioni.getContentPane().add(btnApplica);
	    
	    btnAnnulla.setBounds(112, 179, 85, 21);
	    opzioni.getContentPane().add(btnAnnulla);
	    
	    lblFontFinestra.setHorizontalAlignment(SwingConstants.LEFT);
	    lblFontFinestra.setBounds(10, 115, 72, 26);
	    opzioni.getContentPane().add(lblFontFinestra);
	    
	    int i=0;
	    String s=UIManager.getLookAndFeelDefaults().get("defaultFont").toString();
	    
	    s=s.substring(s.indexOf("name"), s.length());
	    s=s.substring(s.indexOf("="),s.indexOf(",")).replaceAll("[=]", "");
	    
	    while(i<cbFontFinestra.getItemCount()) {
	    	if(cbFontFinestra.getItemAt(i).equals(s)) {
	    		cbFontFinestra.setSelectedIndex(i);
	    		
	    		break;
	    	}
	    	
	    	i++;
	    }
	    
	    if(stiliTextArea.get("dark").equals(this.opzioniSalvate().getStileRSyntaxTextArea()))
	    	cbStileTextArea.setSelectedItem(stiliTextArea.get("dark"));
	    else if(stiliTextArea.get("default").equals(this.opzioniSalvate().getStileRSyntaxTextArea()))
	    	cbStileTextArea.setSelectedItem("default");
	    else if(stiliTextArea.get("default-alt").equals(this.opzioniSalvate().getStileRSyntaxTextArea()))
	    	cbStileTextArea.setSelectedItem("default-alt");
	    else if(stiliTextArea.get("eclipse").equals(this.opzioniSalvate().getStileRSyntaxTextArea()))
	    	cbStileTextArea.setSelectedItem("eclipse");
	    else if(stiliTextArea.get("idea").equals(this.opzioniSalvate().getStileRSyntaxTextArea()))
	    	cbStileTextArea.setSelectedItem("idea");
	    else if(stiliTextArea.get("monokai").equals(this.opzioniSalvate().getStileRSyntaxTextArea()))
	    	cbStileTextArea.setSelectedItem("monokai");
	    else
	    	cbStileTextArea.setSelectedItem(stiliTextArea.get("vs"));
	    
	    cbFontFinestra.setBounds(76, 117, 311, 21);
	    opzioni.getContentPane().add(cbFontFinestra);
	    
	    sDimensioneTesto.setBounds(327, 48, 60, 21);
	    opzioni.getContentPane().add(sDimensioneTesto);
	    
	    rdbtnChiaro.setBounds(43, 84, 60, 21);
	    opzioni.getContentPane().add(rdbtnChiaro);
	    
	    rdbtnScuro.setBounds(105, 84, 60, 21);
	    opzioni.getContentPane().add(rdbtnScuro);
	    
	    lblVersione.setBounds(10, 179, 73, 21);
	    opzioni.getContentPane().add(lblVersione);
	    
	    lblPosizioneDosBOX = new JLabel("Posizione dosBOX:");
	    lblPosizioneDosBOX.setBounds(10, 152, 93, 16);
	    opzioni.getContentPane().add(lblPosizioneDosBOX);
	    
	    txtPosizioneDosBOX = new JTextField();
	    txtPosizioneDosBOX.setBounds(105, 147, 187, 26);
	    opzioni.getContentPane().add(txtPosizioneDosBOX);
	    txtPosizioneDosBOX.setColumns(10);
	    txtPosizioneDosBOX.setText(this.opzioniSalvate().getRootDosBOX());
	    
	    btnCambiaDosBOX = new JButton("Cambia...");
	    btnCambiaDosBOX.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		fileChooser.setDialogTitle("Cambia root");
	    		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    		
	    		int scelta = fileChooser.showOpenDialog(opzioni.getContentPane());
	    		
	    		if(scelta==JFileChooser.APPROVE_OPTION)
	    			txtPosizioneDosBOX.setText(fileChooser.getCurrentDirectory().getAbsolutePath());
	    	}
	    });
	    btnCambiaDosBOX.setBounds(302, 150, 85, 21);
	    opzioni.getContentPane().add(btnCambiaDosBOX);
	}

	public Component getContentPane() {
		return opzioni.getContentPane();
	}
	
	private void restartApplication() {
		final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		File currentJar=null;
		try {
			currentJar = new File(AppAssemblyLine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		/* is it a jar file? /
	  	if(!currentJar.getName().endsWith(".jar"))
	    	return;
		
	  	/ Build command: java -jar application.jar */
		final ArrayList<String> command = new ArrayList<String>();
		command.add(javaBin);
		command.add("-jar");
		command.add(currentJar.getPath());
		
		final ProcessBuilder builder = new ProcessBuilder(command);
		
		try {
			builder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	}
}