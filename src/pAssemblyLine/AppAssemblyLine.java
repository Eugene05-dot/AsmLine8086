package pAssemblyLine;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTree;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import org.fife.ui.rtextarea.*;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.runestroicons.Runestroicons;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.zondicons.Zondicons;
import org.fife.ui.rsyntaxtextarea.*;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AppAssemblyLine {
	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenu mnModifica;
	private JMenuItem mntmApri;
	private JMenuItem mntmSalva;
	private JMenuItem mntmTaglia;
	private JMenuItem mntmCopia;
	private JMenuItem mntmIncolla;
	private JMenuItem mntmElimina;
	private JSplitPane splitPane;
	private JScrollPane scrollPane1;
	public JTree tree;
	private JMenu mnCodice;
	private JMenuItem mntmCommenta;
	private JMenuItem mntmDecommenta;
	private JMenuItem mntmSelezionaTutto;
	private JMenu mnNuovo;
	private JMenuItem mntmProgetto;
	private JMenuItem mntmFile;
	private JMenu mnGenera;
	private JToolBar toolBar;
	private JButton btnNuovo;
	private JMenuItem mntmAnnulla;
	private JMenuItem mntmRipristina;
	private JMenuItem mntmOpzioni;
	JTabbedPane tabbedPane;
	private JButton btnApri;
	private JButton btnSalva;
	private JButton btnSalvaTutto;
	private JButton btnAnnulla;
	private JButton btnRipristina;
	private JButton btnTaglia;
	private JButton btnCopia;
	private JButton btnIncolla;
	private JButton btnElimina;
	private JMenuItem mntmSalvaTutto;
	private JMenu mnEsecuzione;
	private JMenuItem mntmCompila;
	private JMenuItem mntmEsegui;
	private JMenuItem mntmCompilaEdEsegui;
	private JLabel lblRiga1;
	private JLabel lblRiga2;
	private JButton btnCompila;
	private JButton btnEsegui;
	private JButton btnCompilaEdEsegui;
	private JPopupMenu popupMenu,pmChiudi;
	private JMenuItem mntmProgettoBtn;
	private JMenuItem mntmFileBtn;
	private JFileChooser fileChooser;
	private JMenuItem mntmChiudi;
	JPanel pBenvenuto;
	private JLabel lblBenvenuto;
	private JMenu mnCondizione;
	private JMenuItem mntmSemplice;
	private JMenuItem mntmDoppia;
	private JMenuItem mntmMultipla;
	private JMenu mnCiclo;
	private JMenuItem mntmDefinito;
	private JMenuItem mntmPreCondizione;
	private JMenuItem mntmPostCondizione;
	private JMenuItem mntmInput;
	private JMenuItem mntmOutput;
	private JMenuItem mntmStampa;
	private JMenu mnAiuto;
	private JMenuItem mntmAggiornamenti;
	private Opzioni o;
	private static AppAssemblyLine window;
	
	/**
	 * Launch the application.
	 * 
	 * @author Eugenio Sarubbi & Domenico Ricci
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new AppAssemblyLine();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppAssemblyLine() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		o = new Opzioni();
		
		try {
			if(o.opzioniSalvate().getStileFinestra().equals("Dark Look and Feel"))
				UIManager.setLookAndFeel(new FlatDarkLaf());
			else
				UIManager.setLookAndFeel(new FlatLightLaf());
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
		
		UIManager.getLookAndFeelDefaults().put("defaultFont", new Font(o.opzioniSalvate().getFontFinestra(), 0, 10));
		
		frame = new JFrame();
		frame.setIconImage(new ImageIcon("img/Icona.png").getImage());
		frame.setTitle("AssemblyLine");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				String[] options = new String[3];
				int i=0;
				boolean flag=false;
				
				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				
    			options[0] = "Salva";
    			options[1] = "Non salvare";
    			options[2] = "Annulla";
    			
    			while(i<tabbedPane.getTabCount() && !flag) {
    				if(tabbedPane.getTitleAt(i).charAt(0)=='●')
    					flag=true;
    				
    				i++;
    			}
    			
    			if(flag && !pBenvenuto.isVisible()) {
    				i=JOptionPane.showOptionDialog(frame.getContentPane(), "Salvare tutti i progetti?", "Avviso", 0, JOptionPane.WARNING_MESSAGE, null, options, null);
    				
    				if(i==0) {
    					for(int j=0;j<tabbedPane.getTabCount();j++)
    						FileUtility.salvaFile(new File(tabbedPane.getToolTipTextAt(j)),reperisciTextArea().getTextArea().getText());
    				}else if(i==-1 || i==2)
    					frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    			}
			}
		});
		menuBar = new JMenuBar();
		mnFile = new JMenu("File");
		mntmApri = new JMenuItem("Apri...");
		mntmApri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				apri();
			}
		});
		mntmSalva = new JMenuItem("Salva");
		mntmSalva.setEnabled(false);
		mntmSalva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSalva.doClick();
			}
		});
		mnModifica = new JMenu("Modifica");
		mnModifica.setEnabled(false);
		mntmTaglia = new JMenuItem("Taglia");
		mntmTaglia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnTaglia.doClick();
			}
		});
		mntmCopia = new JMenuItem("Copia");
		mntmCopia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCopia.doClick();
			}
		});
		mntmIncolla = new JMenuItem("Incolla");
		mntmIncolla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnIncolla.doClick();
			}
		});
		mntmElimina = new JMenuItem("Elimina");
		mntmElimina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnElimina.doClick();
			}
		});
		mntmProgettoBtn = new JMenuItem("Progetto...");
		mntmProgettoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				creaProgetto();
			}
		});
		mntmFileBtn = new JMenuItem("File...");
		mntmFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				creaFile();
			}
		});
		splitPane = new JSplitPane();
		fileChooser = new JFileChooser();
		splitPane.setOneTouchExpandable(true);
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setToolTipText("");
		scrollPane1 = new JScrollPane();
		tree = new JTree();
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && !e.isConsumed()) {
				     e.consume();
				     
				     Object percorso[] = tree.getSelectionPath().getPath();
				     String p="";
				     
				     for (Object i : percorso)
				    	 p+=i.toString()+File.separator;
				     
				     File file = new File(p);
				     
				     JPanel appo = aggiungiTab();
				     
				     tabbedPane.addTab(percorso[percorso.length-1].toString(),appo);
				     
				     reperisciTextArea(tabbedPane.getTabCount()-1).getTextArea().setText(FileUtility.apriFile(file));
						
				     tabbedPane.setToolTipTextAt(tabbedPane.getTabCount()-1, file.getAbsolutePath());
						
				     if(pBenvenuto.isVisible()) {
				    	 tabbedPane.remove(pBenvenuto);
				    	 pBenvenuto.setVisible(false);
				    	 mnModifica.setEnabled(true);
				    	 mnCodice.setEnabled(true);
				    	 mnEsecuzione.setEnabled(true);
				    	 mntmSalva.setEnabled(true);
				    	 mntmSalvaTutto.setEnabled(true);
				    	 mntmStampa.setEnabled(true);
				     }
				}
			}
		});
		mnCodice = new JMenu("Codice");
		mnCodice.setEnabled(false);
		mntmCommenta = new JMenuItem("Commenta");
		mntmCommenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RTextScrollPane rtsp = reperisciTextArea();
				String s = ";",text = rtsp.getTextArea().getSelectedText();
				
				if(text!=null) {
					for(int i=0;i<text.length();i++) {
						if(text.charAt(i)=='\n' && i!=text.length()-1)
							s+=text.charAt(i)+";";
						else
							s+=text.charAt(i);
					}
					
					rtsp.getTextArea().replaceSelection(s);
				}
			}
		});
		mntmDecommenta = new JMenuItem("Decommenta");
		mntmDecommenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RTextScrollPane rtsp = reperisciTextArea();
				String s = "",text = rtsp.getTextArea().getSelectedText();
				
				if(text!=null) {
					for(int i=0;i<text.length();i++) {
						if(text.charAt(i)!=';')
							s+=text.charAt(i);
					}
					
					rtsp.getTextArea().replaceSelection(s);
				}
			}
		});
		mnGenera = new JMenu("Genera");
		mnNuovo = new JMenu("Nuovo");
		mntmProgetto = new JMenuItem("Progetto...");
		mntmProgetto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				creaProgetto();
			}
		});
		mntmFile = new JMenuItem("File...");
		mntmFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				creaFile();
			}
		});
		mntmSelezionaTutto = new JMenuItem("Seleziona tutto");
		mntmSelezionaTutto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reperisciTextArea().getTextArea().selectAll();
			}
		});
		btnNuovo = new JButton("");
		btnNuovo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				popupMenu.show(btnNuovo, btnNuovo.getX(), btnNuovo.getY());
			}
		});
		mntmAnnulla = new JMenuItem("Annulla");
		mntmAnnulla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reperisciTextArea().getTextArea().undoLastAction();
			}
		});
		mntmRipristina = new JMenuItem("Ripristina");
		mntmRipristina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reperisciTextArea().getTextArea().redoLastAction();
			}
		});
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()==MouseEvent.BUTTON3)
					pmChiudi.show(tabbedPane, e.getX(), e.getY());
			}
		});
		btnApri = new JButton("");
		btnApri.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				apri();
			}
		});
		btnSalva = new JButton("");
		btnSalva.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!pBenvenuto.isVisible()) {
					int index = tabbedPane.getSelectedIndex();
					String file[] = tabbedPane.getToolTipTextAt(index).split("[\\\\]");
					
					FileUtility.salvaFile(new File(tabbedPane.getToolTipTextAt(index)),reperisciTextArea().getTextArea().getText());
					
					tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), file[file.length-1]);
				}
			}
		});
		btnSalvaTutto = new JButton("");
		btnSalvaTutto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!pBenvenuto.isVisible()) {
					int index;
					String file[];
					
					for(int i=0;i<tabbedPane.getTabCount();i++) {
						index = tabbedPane.getSelectedIndex();
						file = tabbedPane.getToolTipTextAt(index).split("[\\\\]");
						
						FileUtility.salvaFile(new File(tabbedPane.getToolTipTextAt(index)),reperisciTextArea().getTextArea().getText());
						
						tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), file[file.length-1]);
					}
				}
			}
		});
		btnAnnulla = new JButton("");
		btnAnnulla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!pBenvenuto.isVisible())
					reperisciTextArea().getTextArea().undoLastAction();
			}
		});
		btnRipristina = new JButton("");
		btnRipristina.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!pBenvenuto.isVisible())
					reperisciTextArea().getTextArea().redoLastAction();
			}
		});
		btnTaglia = new JButton("");
		btnTaglia.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!pBenvenuto.isVisible())
					reperisciTextArea().getTextArea().cut();
			}
		});
		btnCopia = new JButton("");
		btnCopia.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!pBenvenuto.isVisible())
					reperisciTextArea().getTextArea().copy();
			}
		});
		btnIncolla = new JButton("");
		btnIncolla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!pBenvenuto.isVisible())
					reperisciTextArea().getTextArea().paste();
			}
		});
		btnElimina = new JButton("");
		btnElimina.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!pBenvenuto.isVisible())
					reperisciTextArea().getTextArea().replaceSelection("");
			}
		});
		mntmSalvaTutto = new JMenuItem("Salva tutto");
		mntmSalvaTutto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSalvaTutto.doClick();
			}
		});
		mntmSalvaTutto.setEnabled(false);
		lblRiga1 = new JLabel("|");
		lblRiga2 = new JLabel("|");
		mnEsecuzione = new JMenu("Esecuzione");
		mnEsecuzione.setEnabled(false);
		mntmCompila = new JMenuItem("Compila");
		mntmCompila.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compila();
			}
		});
		mntmEsegui = new JMenuItem("Esegui");
		mntmEsegui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				esegui();
			}
		});
		mntmCompilaEdEsegui = new JMenuItem("Compila & Esegui");
		mntmCompilaEdEsegui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compilaEdEsegui();
			}
		});
		btnCompila = new JButton("");
		btnCompila.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				compila();
			}
		});
		btnEsegui = new JButton("");
		btnEsegui.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				esegui();
			}
		});
		btnCompilaEdEsegui = new JButton("");
		btnCompilaEdEsegui.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				compilaEdEsegui();
			}
		});
		popupMenu = new JPopupMenu();
		pmChiudi = new JPopupMenu();
		mntmChiudi = new JMenuItem("Chiudi");
		mntmChiudi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tabbedPane.getTabCount()!=1)
					tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
			}
		});
		lblBenvenuto = new JLabel("");
		pBenvenuto = new JPanel();
		mntmInput = new JMenuItem("Input");
		mntmInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RTextScrollPane rtsp = reperisciTextArea();
				String s;
				int index = rtsp.getTextArea().getCaretPosition();
				
				s="MOV AH, 01H\nINT 21H\nMOV \"VARIABILE\", AL";
				
				rtsp.getTextArea().insert(s, index);
			}
		});
		mnCondizione = new JMenu("Condizione");
		mntmSemplice = new JMenuItem("Semplice");
		mntmDoppia = new JMenuItem("Doppia");
		mntmMultipla = new JMenuItem("Multipla");
		mnCiclo = new JMenu("Ciclo");
		mntmDefinito = new JMenuItem("Definito");
		mntmOutput = new JMenuItem("Output");
		mntmOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RTextScrollPane rtsp = reperisciTextArea();
				String s;
				int index = rtsp.getTextArea().getCaretPosition();
				
				s="MOV AH, 09H\nMOV DX, OFFSET \"VARIABILE\"\nINT 21H";
				
				rtsp.getTextArea().insert(s, index);
			}
		});
		mntmPreCondizione = new JMenuItem("Pre-condizione");
		mntmPostCondizione = new JMenuItem("Post-condizione");
		mntmStampa = new JMenuItem("Stampa");
		mntmStampa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser.setDialogTitle("Stampa");
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				if(fileChooser.showOpenDialog(frame.getContentPane())==JFileChooser.APPROVE_OPTION) {
					String path = fileChooser.getCurrentDirectory().getAbsolutePath();
					Document doc = new Document();
					
					try {
						String nome = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
						
						nome = nome.substring(0,nome.length()-3);
						
						nome+="pdf";
						
						File file = new File(path+File.separator+nome);
						
						file.createNewFile();
		
						PdfWriter.getInstance(doc, new FileOutputStream(file.getAbsolutePath()));
						doc.open();
						
						doc.addTitle(nome.substring(0,nome.length()-4));
						
						doc.add(new Paragraph(reperisciTextArea().getTextArea().getText(),FontFactory.getFont(UIManager.getDefaults().getString("defaultFont"))));
						
						doc.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (DocumentException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		tree.setModel(new FileSystemModel(new File(o.opzioniSalvate().getRootJTree())));
		
		frame.setBounds(100, 100, 690, 480);
		//frame.setExtendedState(frame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(menuBar);
		
		menuBar.add(mnFile);
		
		mnFile.add(mnNuovo);
		
		mnNuovo.add(mntmProgetto);
		mnNuovo.add(mntmFile);
		
		mnFile.add(mntmApri);
		mnFile.add(mntmSalva);
		mnFile.add(mntmSalvaTutto);
		
		mntmStampa.setEnabled(false);
		
		mnFile.add(mntmStampa);
		
		menuBar.add(mnModifica);
		
		mnModifica.add(mntmAnnulla);
		mnModifica.add(mntmRipristina);
		mnModifica.add(mntmTaglia);
		mnModifica.add(mntmCopia);
		mnModifica.add(mntmIncolla);
		mnModifica.add(mntmElimina);
		mnModifica.add(mntmSelezionaTutto);
		
		menuBar.add(mnCodice);
		
		mnCodice.add(mntmCommenta);
		mnCodice.add(mntmDecommenta);
		mnCodice.add(mnGenera);
		
		mnGenera.add(mnCondizione);
		
		mntmSemplice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RTextScrollPane rtsp = reperisciTextArea();
				String s;
				int index = rtsp.getTextArea().getCaretPosition();
				
				s="CMP \"OP1\", \"OP2\"\n\"SALTO\" ALLORA\nJMP FINE\n\nALLORA:\n\nFINE: NOP";
				
				rtsp.getTextArea().insert(s, index);
			}
		});
		mnCondizione.add(mntmSemplice);
		
		mntmDoppia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RTextScrollPane rtsp = reperisciTextArea();
				String s;
				int index = rtsp.getTextArea().getCaretPosition();
				
				s="CMP \"OP1\", \"OP2\"\n\"SALTO\" ALLORA\nJMP ALTRIMENTI\n\nALLORA:\n\nJMP FINE\n\nALTRIMENTI:\n\nFINE: NOP";
				
				rtsp.getTextArea().insert(s, index);
			}
		});
		mnCondizione.add(mntmDoppia);
		
		mntmMultipla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RTextScrollPane rtsp = reperisciTextArea();
				String s="";
				int index = rtsp.getTextArea().getCaretPosition();
				
				for(int i=1;i<=3;i++)
					s+="CMP \"OP1\", \"OP2\"\n\"SALTO\" CASO"+i+"\n";
				
				s+="\n";
				
				for(int i=1;i<=3;i++)
					s+="CASO"+i+":\nJMP FINE\n\n";
				
				s+="JMP DEFAULT\n\nDEFAULT:\n\nFINE: NOP";
				
				rtsp.getTextArea().insert(s, index);
			}
		});
		mnCondizione.add(mntmMultipla);
		
		mnGenera.add(mnCiclo);
		
		mntmDefinito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RTextScrollPane rtsp = reperisciTextArea();
				String s;
				int index = rtsp.getTextArea().getCaretPosition();
				
				s="MOV CX, \"NUMERO DI RIPETIZIONI\"\n\nCICLO:\nLOOP CICLO";
				
				rtsp.getTextArea().insert(s, index);
			}
		});
		mnCiclo.add(mntmDefinito);
		
		mntmPreCondizione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RTextScrollPane rtsp = reperisciTextArea();
				String s;
				int index = rtsp.getTextArea().getCaretPosition();
				
				s="INIZIO_CICLO: CMP \"OP1\", \"OP2\"\n\"SALTO\" FINE_CICLO\n\nJMP INIZIO_CICLO:\n\nFINE_CICLO: NOP";
				
				rtsp.getTextArea().insert(s, index);
			}
		});
		mnCiclo.add(mntmPreCondizione);
		
		mntmPostCondizione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RTextScrollPane rtsp = reperisciTextArea();
				String s;
				int index = rtsp.getTextArea().getCaretPosition();
				
				s="INIZIO_CICLO:\n\nCMP \"OP1\", \"OP2\"\n\"SALTO\" INIZIO_CICLO";
				
				rtsp.getTextArea().insert(s, index);
			}
		});
		mnCiclo.add(mntmPostCondizione);
		
		mnCodice.add(mntmInput);
		mnCodice.add(mntmOutput);
		
		menuBar.add(mnEsecuzione);
		
		mnEsecuzione.add(mntmCompila);
		mnEsecuzione.add(mntmEsegui);
		mnEsecuzione.add(mntmCompilaEdEsegui);
		
		mnAiuto = new JMenu("Aiuto");
		menuBar.add(mnAiuto);
		mntmOpzioni = new JMenuItem("Opzioni...");
		mnAiuto.add(mntmOpzioni);
		
		mntmAggiornamenti = new JMenuItem("Cerca nuovi aggiornamenti");
		mnAiuto.add(mntmAggiornamenti);
		mntmOpzioni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				o.mostraOpzioni(window);
			}
		});
		
		popupMenu.add(mntmProgettoBtn);
		popupMenu.add(mntmFileBtn);
		
		splitPane.setLeftComponent(scrollPane1);
		scrollPane1.setViewportView(tree);
		
        frame.getContentPane().setLayout(new BorderLayout(0,0));
        frame.getContentPane().add(toolBar, BorderLayout.NORTH);
        
        toolBar.add(btnNuovo);
        toolBar.add(btnApri);
        toolBar.add(btnSalva);
        toolBar.add(btnSalvaTutto);
        toolBar.add(lblRiga1);
        toolBar.add(btnAnnulla);
        toolBar.add(btnRipristina);
        toolBar.add(btnTaglia);
        toolBar.add(btnCopia);
        toolBar.add(btnIncolla);
        toolBar.add(btnElimina);
        toolBar.add(lblRiga2);
        toolBar.add(btnCompila);
        toolBar.add(btnEsegui);
        toolBar.add(btnCompilaEdEsegui);
        
        frame.getContentPane().add(splitPane, BorderLayout.CENTER);
        
        fileChooser.addChoosableFileFilter(new FileFilter() {
            public String getDescription() {
                return "Assembly Language Source File (*.asm)";
            }
         
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    return f.getName().toLowerCase().endsWith(".asm");
                }
            }
        });
        
        splitPane.setRightComponent(tabbedPane);
        
        tabbedPane.addTab("Benvenuto", null, pBenvenuto, null);
        pBenvenuto.setLayout(new BorderLayout(0, 0));
        
        lblBenvenuto.setHorizontalAlignment(SwingConstants.CENTER);
        pBenvenuto.add(lblBenvenuto, BorderLayout.CENTER);
        
        btnNuovo.setIcon(FontIcon.of(Zondicons.DOCUMENT_ADD,16,Color.WHITE));
        btnApri.setIcon(FontIcon.of(FontAwesome.FOLDER_OPEN,16,Color.WHITE));
        btnSalva.setIcon(FontIcon.of(FontAwesome.SAVE,16,Color.WHITE));
        btnSalvaTutto.setIcon(FontIcon.of(Runestroicons.SAVE,16,Color.WHITE));
        btnAnnulla.setIcon(FontIcon.of(FontAwesome.UNDO,16,Color.WHITE));
        btnRipristina.setIcon(FontIcon.of(FontAwesome.REPEAT,16,Color.WHITE));
        btnTaglia.setIcon(FontIcon.of(FontAwesome.CUT,16,Color.WHITE));
        btnCopia.setIcon(FontIcon.of(FontAwesome.COPY,16,Color.WHITE));
        btnIncolla.setIcon(FontIcon.of(FontAwesome.PASTE,16,Color.WHITE));
        btnElimina.setIcon(FontIcon.of(Runestroicons.CIRCLEDELETE,16,Color.WHITE));
        btnCompila.setIcon(FontIcon.of(Runestroicons.SQUARE_BULLET2,16,Color.WHITE));
        btnEsegui.setIcon(FontIcon.of(FontAwesome.PLAY,16,Color.WHITE));
        btnCompilaEdEsegui.setIcon(FontIcon.of(Runestroicons.SQUARE_BULLET,16,Color.WHITE));
        
        btnNuovo.setToolTipText("Nuovo...");
        btnApri.setToolTipText("Apri...");
        btnSalva.setToolTipText("Salva");
        btnSalvaTutto.setToolTipText("Salva tutto");
        btnAnnulla.setToolTipText("Annulla");
        btnRipristina.setToolTipText("Ripristina");
        btnTaglia.setToolTipText("Taglia");
        btnCopia.setToolTipText("Copia");
        btnIncolla.setToolTipText("Incolla");
        btnElimina.setToolTipText("Elimina");
        btnCompila.setToolTipText("Compila");
        btnEsegui.setToolTipText("Esegui");
        btnCompilaEdEsegui.setToolTipText("Compila & Esegui");
        
        pmChiudi.add(mntmChiudi);
        
	    lblBenvenuto.setIcon(new ImageIcon("img/LogoGrigio.png"));
	}
	
	void changeStyleViaThemeXml(RSyntaxTextArea textArea) {
		try {
			Theme theme = Theme.load(getClass().getResourceAsStream(o.opzioniSalvate().getStileRSyntaxTextArea()));
	        theme.apply(textArea);
	    } catch (IOException ioe) { //Never happens
	         ioe.printStackTrace();
	    }
	}
	
	private void creaProgetto() {
		fileChooser.setDialogTitle("Crea progetto");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		int scelta = fileChooser.showOpenDialog(frame.getContentPane());
		
		if(scelta==JFileChooser.APPROVE_OPTION) {
			if(pBenvenuto.isVisible()) {
				tabbedPane.remove(pBenvenuto);
				pBenvenuto.setVisible(false);
			}
			
			JPanel appo = aggiungiTab();
			
			String s = fileChooser.getSelectedFile().getAbsolutePath().split("[\\\\]")[fileChooser.getSelectedFile().getAbsolutePath().split("[\\\\]").length-1];
			
			tabbedPane.addTab(s+".asm",appo);
			
			tabbedPane.setTitleAt(tabbedPane.getTabCount()-1, s+".asm");
			
			tabbedPane.setToolTipTextAt(tabbedPane.getTabCount()-1, fileChooser.getSelectedFile().getAbsolutePath());
			
			System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
			
			if(!FileUtility.creaProgetto(fileChooser.getCurrentDirectory().getAbsolutePath(),s))
				JOptionPane.showMessageDialog(frame, "Errore durante la creazione del progetto");
			else
				reperisciTextArea(tabbedPane.getTabCount()-1).getTextArea().setText(FileUtility.apriFile(fileChooser.getSelectedFile()));
			
			if(!mnModifica.isEnabled()) {
				mnModifica.setEnabled(true);
				mnCodice.setEnabled(true);
				mnEsecuzione.setEnabled(true);
				mntmSalva.setEnabled(true);
				mntmSalvaTutto.setEnabled(true);
				mntmStampa.setEnabled(true);
			}
		}
	}
	
	private void creaFile() {
		fileChooser.setDialogTitle("Crea file");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		int scelta = fileChooser.showOpenDialog(frame.getContentPane());
		
		if(scelta==JFileChooser.APPROVE_OPTION) {
			if(pBenvenuto.isVisible()) {
				tabbedPane.remove(pBenvenuto);
				pBenvenuto.setVisible(false);
			}
				
			JPanel appo = aggiungiTab();
			
			String s = fileChooser.getSelectedFile().toString().split("[\\\\]")[fileChooser.getSelectedFile().getAbsolutePath().split("[\\\\]").length-1];
			
			tabbedPane.addTab(s+".asm",appo);
			
			tabbedPane.setTitleAt(tabbedPane.getTabCount()-1, s+".asm");
			
			tabbedPane.setToolTipTextAt(tabbedPane.getTabCount()-1, fileChooser.getSelectedFile().getAbsolutePath());
			
			if(!FileUtility.creaFile(fileChooser.getCurrentDirectory().getAbsolutePath(),s))
				JOptionPane.showMessageDialog(frame, "Errore durante la creazione del file");
			else
				reperisciTextArea(tabbedPane.getTabCount()-1).getTextArea().setText(FileUtility.apriFile(fileChooser.getSelectedFile()));
			
			if(!mnModifica.isEnabled()) {
				mnModifica.setEnabled(true);
				mnCodice.setEnabled(true);
				mnEsecuzione.setEnabled(true);
				mntmSalva.setEnabled(true);
				mntmSalvaTutto.setEnabled(true);
				mntmStampa.setEnabled(true);
			}
		}
	}
	
	private void apri() {
		fileChooser.setDialogTitle("Apri");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		int scelta = fileChooser.showOpenDialog(frame.getContentPane());
		
		if(scelta==JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().exists()) {
			JPanel appo = aggiungiTab();
			
			tabbedPane.addTab(fileChooser.getSelectedFile().getName(),appo);
			
			tabbedPane.setTitleAt(tabbedPane.getTabCount()-1, fileChooser.getSelectedFile().getName());
			
			reperisciTextArea(tabbedPane.getTabCount()-1).getTextArea().setText(FileUtility.apriFile(fileChooser.getSelectedFile()));
			
			tabbedPane.setToolTipTextAt(tabbedPane.getTabCount()-1, fileChooser.getSelectedFile().toString());
			
			if(pBenvenuto.isVisible()) {
				tabbedPane.remove(pBenvenuto);
				pBenvenuto.setVisible(false);
			}
			
			if(!mnModifica.isEnabled()) {
				mnModifica.setEnabled(true);
				mnCodice.setEnabled(true);
				mnEsecuzione.setEnabled(true);
				mntmSalva.setEnabled(true);
				mntmSalvaTutto.setEnabled(true);
				mntmStampa.setEnabled(true);
			}
		}else if(fileChooser.getSelectedFile()!=null) {
			if(!fileChooser.getSelectedFile().exists())
				JOptionPane.showMessageDialog(frame.getContentPane(), "File non esistente");
		}
	}
	
	private JPanel aggiungiTab() {
		JPanel appo = new JPanel();
		RSyntaxTextArea rsta = new RSyntaxTextArea();
		RTextScrollPane rtsp = new RTextScrollPane(rsta);
		
		appo.setLayout(new BorderLayout(0, 0));
        
		changeStyleViaThemeXml(rsta);
		
		rsta.setBackground(frame.getBackground());
		rsta.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_ASSEMBLER_X86);
        rsta.setCodeFoldingEnabled(true);
		rtsp.getGutter().setBackground(frame.getBackground());
		rsta.setFont(new Font(o.opzioniSalvate().getFontFinestra(),0,o.opzioniSalvate().getDimensioneTesto()));
		
		rsta.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent e) {
        		int index = tabbedPane.getComponentZOrder(rtsp.getParent());
        		String file[] = tabbedPane.getToolTipTextAt(index).split("[\\\\]");
        		
        		tabbedPane.setTitleAt(index, "●"+file[file.length-1]);
        	}
        });
		
		appo.add(rtsp);
		
        return appo;
	}
	
	private RTextScrollPane reperisciTextArea() {
		Component c = tabbedPane.getSelectedComponent();
		
		JPanel p = (JPanel) c;
		
		Component[] cs = p.getComponents();
		
		int i=0;
		
		RTextScrollPane rtsp = null;
		
		while(i<cs.length) {
			if(cs[i] instanceof RTextScrollPane) {
				rtsp = (RTextScrollPane) cs[i];
				
				break;
			}
			
			i++;
		}
		
		return rtsp;
	}
	
	RTextScrollPane reperisciTextArea(int index) {
		Component c = tabbedPane.getComponentAt(index);
		
		JPanel p = (JPanel) c;
		
		Component[] cs = p.getComponents();
		
		int i=0;
		
		RTextScrollPane rtsp = null;
		
		while(i<cs.length) {
			if(cs[i] instanceof RTextScrollPane) {
				rtsp = (RTextScrollPane) cs[i];
				
				break;
			}
			
			i++;
		}
		
		return rtsp;
	}
	
	public static class InputStreamConsumer implements Runnable {
	    private InputStream is;
	    
	    public InputStreamConsumer(InputStream inputStream) {
	        is = inputStream;
	    }

	    @Override
	    public void run() {
	        int in = -1;
	        try {
	            while ((in = is.read()) != -1) {
	                System.out.print((char) in);
	            }
	        } catch (IOException exp) {
	            exp.printStackTrace();
	        }
	    }
	}
	
	private void compila() {
		if(tabbedPane.getToolTipTextAt(tabbedPane.getSelectedIndex())!=null) {
			String cmds,appo2[],objFile="";
			
			appo2=tabbedPane.getToolTipTextAt(tabbedPane.getSelectedIndex()).split("[.]");
			
			appo2[appo2.length-1]=".obj";
			
			for(int i=0;i<appo2.length;i++)
				objFile+=appo2[i];
			
			appo2[appo2.length-1]=".exe";
			
			cmds="mount C C:"+File.separator+"\n"
					+ "C:"+"\n"
					+ "cd "+"AL"+"\n"
					+ "tasm "+tabbedPane.getToolTipTextAt(tabbedPane.getSelectedIndex())+"\n"
					+ "tlink "+objFile+"\n";
			
			try {
	            ProcessBuilder pb = new ProcessBuilder(o.opzioniSalvate().getRootDosBOX(), "-c", cmds);
	            pb.redirectError();
	            Process p = pb.start();
	            
	            new Thread(new InputStreamConsumer(p.getInputStream())).start();
	        } catch (IOException exp) {
	            exp.printStackTrace();
	        }
		}
	}
	
	private void esegui() {
		if(tabbedPane.getToolTipTextAt(tabbedPane.getSelectedIndex())!=null) {
			String appo1[]=tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).split("[.]"),cmds,exe="",appo2[],indexFile="";
			
			appo1[appo1.length-1]=".exe";
			
			for(int i=0;i<appo1.length;i++)
				exe+=appo1[i];
			
			appo2=tabbedPane.getToolTipTextAt(tabbedPane.getSelectedIndex()).split("[\\\\]");
			
			for(int i=0;i<appo2.length-1;i++) {
				indexFile+=appo2[i]+File.separator;
			}
			
			cmds="mount C C:"+File.separator+"\n"
					+ "C:"+"\n"
					+ "cd "+indexFile+"\n"
					+ exe;
			
			try {
	            ProcessBuilder pb = new ProcessBuilder(o.opzioniSalvate().getRootDosBOX(), "-c", cmds);
	            pb.redirectError();
	            Process p = pb.start();
	            
	            new Thread(new InputStreamConsumer(p.getInputStream())).start();
	        } catch (IOException exp) {
	            exp.printStackTrace();
	        }
		}
	}
	
	private void compilaEdEsegui() {
		if(tabbedPane.getToolTipTextAt(tabbedPane.getSelectedIndex())!=null) {
			String cmds,appo1[],appo2[],indexFile="",objFile="",exe="";
			
			appo1=tabbedPane.getToolTipTextAt(tabbedPane.getSelectedIndex()).split("[\\\\]");
			appo2=tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).split("[.]");
			
			appo2[appo2.length-1]=".obj";
			
			for(int i=0;i<appo1.length-1;i++)
				indexFile+=appo1[i]+File.separator;
			
			for(int i=0;i<appo2.length;i++)
				objFile+=appo2[i];
			
			appo2[appo2.length-1]=".exe";
			
			for(int i=0;i<appo2.length;i++)
				exe+=appo2[i];
			
			cmds="mount C C:"+File.separator+"\n"
					+ "C:"+"\n"
					+ "cd "+indexFile+"\n"
					+ "tasm "+tabbedPane.getTitleAt(tabbedPane.getSelectedIndex())+"\n"
					+ "tlink "+objFile+"\n"
					+ exe;
			
			try {
	            ProcessBuilder pb = new ProcessBuilder(o.opzioniSalvate().getRootDosBOX(), "-c", cmds);
	            pb.redirectError();
	            Process p = pb.start();
	            
	            new Thread(new InputStreamConsumer(p.getInputStream())).start();
	        } catch (IOException exp) {
	            exp.printStackTrace();
	        }
		}
	}
}