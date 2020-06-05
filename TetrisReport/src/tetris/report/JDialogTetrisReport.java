package tetris.report;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JDialog;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JScrollPane;
import javax.swing.JEditorPane;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.Color;

@SuppressWarnings("serial")
public class JDialogTetrisReport extends JDialog {
	private JToolBar jToolBar;
	private JScrollPane jScrollPane;
	private JEditorPane jEditorPane;
	private String html;
	private JButton jButtonImprimir;
	private JPanelPagina jPanelPagina;

	private Printable printable;
	private PageFormat pageFormat;
	private PrinterJob printerJob;
	private int pagina, maxPagina, marginLeft, marginRight, marginTop, marginBottom;

	private static final int RETRATO=0, PAISAGEM=1;

	private int orientacao=RETRATO;
	private JSpinner jSpinnerPagina;
	private JButton jButtonTamanhoDaTela;
	private JButton jButtonTamanhoNormal;
	private JScrollPane jScrollPanePagina;
	private JPanel jPanelMesa;
	
	private Timer timer; 
	private JLabel label;
	private JLabel jLabelMaxPagina;

	public JDialogTetrisReport(String html, int orientacao, int marginLeft, int marginRight, int marginTop, int marginBottom) {
		this.html=html;
		this.orientacao=orientacao;
		this.marginLeft=marginLeft;
		this.marginRight=marginRight;
		this.marginTop=marginTop;
		this.marginBottom=marginBottom;

		setTitle("Visualizar Impress\u00E3o");
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogTetrisReport.class.getResource("/tetris/report/imagem/icone.png")));
		setSize(new Dimension(640, 480));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		jToolBar = new JToolBar();
		jToolBar.setFloatable(false);

		jToolBar.setBounds(0, 0, 233, 29);
		getContentPane().add(jToolBar);

		jButtonImprimir = new JButton("Imprimir");
		jButtonImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					HashPrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();             
					attr.add(new MediaPrintableArea(getMarginLeft(), getMarginTop(), 200 - getMarginLeft() - getMarginRight(), 275 - getMarginTop() - getMarginBottom(), MediaPrintableArea.MM));
					

					if(getOrientacao()==PAISAGEM){
						attr.add(OrientationRequested.LANDSCAPE);
					}else{
						attr.add(OrientationRequested.PORTRAIT);
					}

					jEditorPane.print(null, null, true, null, attr, true);

				} catch (PrinterException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(getJFrameTetrisReport(), "Erro ao imprimir: \n"+e1, "Erro", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		jButtonImprimir.setIcon(new ImageIcon(JDialogTetrisReport.class.getResource("/tetris/report/imagem/botao_imprimir.png")));
		jToolBar.add(jButtonImprimir);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jFileChooser = new JFileChooser();
				if(jFileChooser.showSaveDialog(getJFrameTetrisReport()) == JFileChooser.APPROVE_OPTION){
					try{
						BufferedWriter bufferedWriter  = new BufferedWriter(new FileWriter(jFileChooser.getSelectedFile()));

						bufferedWriter.write(getHtml());
						bufferedWriter.flush();

						bufferedWriter.close();
					}catch(Exception exc){
						JOptionPane.showMessageDialog(getJFrameTetrisReport(), "Erro ao salvar relatório:\n"+exc, "Erro", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnSalvar.setIcon(new ImageIcon(JDialogTetrisReport.class.getResource("/tetris/report/imagem/botao_gravar.png")));
		jToolBar.add(btnSalvar);

		JButton btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnFechar.setIcon(new ImageIcon(JDialogTetrisReport.class.getResource("/tetris/report/imagem/sair.png")));
		jToolBar.add(btnFechar);

		jSpinnerPagina = new JSpinner();
		jSpinnerPagina.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				try {
					pagina = Integer.parseInt(jSpinnerPagina.getValue().toString())-1;
					mostrarPagina();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		jSpinnerPagina.setBounds(285, 9, 46, 20);
		getContentPane().add(jSpinnerPagina);

		jScrollPane = new JScrollPane();
		jScrollPane.setVisible(false);
		jScrollPane.setBounds(0, 30, 624, 412);
		getContentPane().add(jScrollPane);

		jEditorPane = new JEditorPane();
		jEditorPane.setEditable(false);
		jEditorPane.setContentType("text/html");
		jEditorPane.setText(""+html);
		jEditorPane.setBorder(null);
		jScrollPane.setViewportView(jEditorPane);

		jPanelMesa = new JPanel();
		jPanelMesa.setLayout(null);
		jPanelMesa.setBackground(Color.GRAY);
		jPanelMesa.setBounds(0, 0, getContentPane().getWidth(), getContentPane().getHeight());

		jScrollPanePagina = new JScrollPane();
		jScrollPanePagina.setBounds(0, 0, getContentPane().getWidth(), getContentPane().getHeight());
		getContentPane().add(jScrollPanePagina);
		jScrollPanePagina.setViewportView(jPanelMesa);

		jPanelPagina = new JPanelPagina();
		jPanelPagina.setBounds(126, 68, 63, 69);
		jPanelMesa.add(jPanelPagina);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				mudarTamanho();
			}
		});

		printerJob = PrinterJob.getPrinterJob();
		pageFormat = printerJob.defaultPage();

		printable = jEditorPane.getPrintable(null, null);
		if (orientacao==PAISAGEM) {
			pageFormat.setOrientation(PageFormat.LANDSCAPE);
		}else{
			pageFormat.setOrientation(PageFormat.PORTRAIT);
		}
		
		Paper paper = new Paper();
		paper.setImageableArea(marginLeft, marginTop, paper.getWidth() - marginLeft - marginRight, paper.getHeight() - marginTop - marginBottom);
		
		pageFormat.setPaper(paper);
		printerJob.setPrintable(printable, pageFormat);

		pagina=0;

		jButtonTamanhoDaTela = new JButton("");
		jButtonTamanhoDaTela.setFocusable(false);
		jButtonTamanhoDaTela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jButtonTamanhoDaTela.setEnabled(false);
				jButtonTamanhoNormal.setEnabled(true);

				mudarTamanho();
			}
		});
		jButtonTamanhoDaTela.setIcon(new ImageIcon(JDialogTetrisReport.class.getResource("/tetris/report/imagem/botao_tamanho_da_tela.png")));
		jButtonTamanhoDaTela.setBounds(384, 4, 40, 25);
		getContentPane().add(jButtonTamanhoDaTela);

		jButtonTamanhoNormal = new JButton("");
		jButtonTamanhoNormal.setEnabled(false);
		jButtonTamanhoNormal.setFocusable(false);
		jButtonTamanhoNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jButtonTamanhoDaTela.setEnabled(true);
				jButtonTamanhoNormal.setEnabled(false);

				mudarTamanho();
			}
		});
		jButtonTamanhoNormal.setIcon(new ImageIcon(JDialogTetrisReport.class.getResource("/tetris/report/imagem/botao_tamanho_normal.png")));
		jButtonTamanhoNormal.setBounds(434, 4, 40, 25);
		getContentPane().add(jButtonTamanhoNormal);
		
		label = new JLabel("P\u00E1gina:");
		label.setBounds(243, 11, 46, 14);
		getContentPane().add(label);
		
		jLabelMaxPagina = new JLabel("/0");
		jLabelMaxPagina.setBounds(340, 11, 46, 14);
		getContentPane().add(jLabelMaxPagina);

		timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {

					while(printable.print(jPanelPagina.getGraphics(), pageFormat, maxPagina+1)==Printable.PAGE_EXISTS){
						//System.out.println(maxPagina);
						maxPagina++;
					}
				} catch (PrinterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

				}
				jLabelMaxPagina.setText("/"+(maxPagina+1));
				SpinnerModel spinnerModel = new SpinnerNumberModel(pagina+1, 1, maxPagina+1, 1);
				jSpinnerPagina.setModel(spinnerModel);
				jSpinnerPagina.setValue(1);
				
				pararTimer();
				mostrarPagina();
			}
		});
		
		timer.start();
		setModal(true);
		
	}
	
	public void pararTimer(){
		timer.stop();
	}

	public void preview(){
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		mostrarPagina();
		setVisible(true);
	}

	public void print(){
		jButtonImprimir.doClick();
	}

	public void mostrarPagina(){
		jPanelPagina.atualizarPagina(printable, pageFormat, pagina);
	}

	public void mudarTamanho(){
		jScrollPane.setSize(getContentPane().getWidth(), getContentPane().getHeight()-30);
		jEditorPane.setSize(getContentPane().getWidth(), getContentPane().getHeight()-30);

		mostrarPagina();

		if(!jButtonTamanhoDaTela.isEnabled()){
			if(orientacao == PAISAGEM){
				jPanelPagina.setSize(getContentPane().getWidth()- 50, (((int)pageFormat.getHeight())*(getContentPane().getWidth()-50)) /((int)pageFormat.getWidth()));
				jPanelMesa.setSize(getContentPane().getWidth()- 50, (((int)pageFormat.getHeight())*(getContentPane().getWidth()-50)) /((int)pageFormat.getWidth()));
			}else if(orientacao == RETRATO){
				jPanelPagina.setSize((((int)pageFormat.getWidth())*(getContentPane().getHeight()-50)) /((int)pageFormat.getHeight()), getContentPane().getHeight()- 50);
				jPanelMesa.setSize((((int)pageFormat.getWidth())*(getContentPane().getHeight()-50)) /((int)pageFormat.getHeight()), getContentPane().getHeight()- 50);
			}
		}else{
			jPanelPagina.setSize((int)pageFormat.getWidth(), (int)pageFormat.getHeight());
			jPanelMesa.setSize((int)pageFormat.getWidth(), (int)pageFormat.getHeight());
		}

		jPanelMesa.setPreferredSize(new Dimension(getContentPane().getWidth()- 50, jPanelMesa.getHeight()));
		jScrollPanePagina.setSize(new Dimension(getContentPane().getWidth(), getContentPane().getHeight() - 50));

		jScrollPanePagina.setLocation(0, 30);
		jPanelPagina.setLocation((getContentPane().getWidth()/2) - (jPanelPagina.getWidth()/2), 0);
	}

	public JDialogTetrisReport getJFrameTetrisReport(){
		return this;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public int getOrientacao() {
		return orientacao;
	}

	public void setOrientacao(int orientacao) {
		this.orientacao = orientacao;
	}

	public int getMarginLeft() {
		return marginLeft;
	}

	public int getMarginRight() {
		return marginRight;
	}

	public int getMarginTop() {
		return marginTop;
	}

	public int getMarginBottom() {
		return marginBottom;
	}
	
}


