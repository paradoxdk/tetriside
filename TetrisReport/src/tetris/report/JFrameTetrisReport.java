package tetris.report;

import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;

import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.border.BevelBorder;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;

@SuppressWarnings("serial")
public class JFrameTetrisReport extends JFrame {
	private JToolBar jToolBar;
	private JScrollPane jScrollPane;
	private JEditorPane jEditorPane;
	private String html;
	
	public JFrameTetrisReport(String html) {
		this.html=html;
		
		setTitle("Visualizar Impress\u00E3o");
		setIconImage(Toolkit.getDefaultToolkit().getImage(JFrameTetrisReport.class.getResource("/tetris/report/imagem/icone.png")));
		setSize(new Dimension(640, 480));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		jToolBar = new JToolBar();
		
		jToolBar.setBounds(0, 0, 624, 29);
		getContentPane().add(jToolBar);
		
		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					jEditorPane.print();
				} catch (PrinterException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(getJFrameTetrisReport(), "Erro ao imprimir: \n"+e1, "Erro", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		btnImprimir.setIcon(new ImageIcon(JFrameTetrisReport.class.getResource("/tetris/report/imagem/botao_imprimir.png")));
		jToolBar.add(btnImprimir);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jFileChooser = new JFileChooser();
				if(jFileChooser.showSaveDialog(getJFrameTetrisReport()) == JFileChooser.APPROVE_OPTION){
					try{
						BufferedWriter bufferedWriter  = new BufferedWriter(new FileWriter(jFileChooser.getSelectedFile()));
						
						bufferedWriter.write(jEditorPane.getText());
						bufferedWriter.flush();
						
						bufferedWriter.close();
					}catch(Exception exc){
						JOptionPane.showMessageDialog(getJFrameTetrisReport(), "Erro ao salvar relatório:\n"+exc, "Erro", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnSalvar.setIcon(new ImageIcon(JFrameTetrisReport.class.getResource("/tetris/report/imagem/botao_gravar.png")));
		jToolBar.add(btnSalvar);
		
		JButton btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnFechar.setIcon(new ImageIcon(JFrameTetrisReport.class.getResource("/tetris/report/imagem/sair.png")));
		jToolBar.add(btnFechar);
		
		jScrollPane = new JScrollPane();
		jScrollPane.setBounds(0, 30, 624, 412);
		getContentPane().add(jScrollPane);
		
		jEditorPane = new JEditorPane();
		jEditorPane.setEditable(false);
		jEditorPane.setContentType("text/html");
		jEditorPane.setText(html);
		jEditorPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		jScrollPane.setViewportView(jEditorPane);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				mudarTamanho();
			}
		});
		
		setVisible(true);
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	
	public void mudarTamanho(){
		jToolBar.setSize(getContentPane().getWidth(), 30);
		jScrollPane.setSize(getContentPane().getWidth(), getContentPane().getHeight()-30);
		jEditorPane.setSize(getContentPane().getWidth(), getContentPane().getHeight()-30);
	}
	
	public JFrameTetrisReport getJFrameTetrisReport(){
		return this;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}
}
