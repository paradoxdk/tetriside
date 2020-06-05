/*
 *Editor de Relatórios TetrisReport do TetrisIDE
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 27 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.janelas;

import javax.swing.JDialog;

import java.awt.Toolkit;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import br.com.analisasoftware.tetris.modelo.Idioma;
import br.com.analisasoftware.tetris.visao.componentes.jsyntaxtextpane.JSyntaxTextPane;
import tetris.modelo.componentes.Atributo;
import tetris.modelo.componentes.Componente;

import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

@SuppressWarnings("serial")
public class JDialogTetrisReportEditor extends JDialog {
	//Nome do componente Report
	private String report;
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Componentes gráficos
	private JToolBar jToolBar;
	private JSyntaxTextPane jSyntaxTextPaneRelatorio;
	private JScrollPane jScrollPaneRelatorio;
	private JSyntaxTextPane jSyntaxTextPaneFoco;
	private JButton jButtonSalvar;
	private JButton jButtonLimpar;
	private JButton jButtonCancelar;
	private JLabel jLabelRelatorio;

	public JDialogTetrisReportEditor(JFramePrincipal jFramePrincipal, String report){
		super();
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				//Redimensiona componentes
				jToolBar.setSize(getContentPane().getWidth(), 50);

				jScrollPaneRelatorio.setSize(getContentPane().getWidth() - 10,getContentPane().getHeight() - 15 - jScrollPaneRelatorio.getY());
				jSyntaxTextPaneRelatorio.setSize(jScrollPaneRelatorio.getWidth() - 5, jScrollPaneRelatorio.getHeight()-5);
			}
		});
		getContentPane().setBackground(new Color(255, 255, 255));
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogTetrisReportEditor.class.getResource("/br/com/analisasoftware/tetris/imagem/icone.png")));
		this.report = report;
		this.jFramePrincipal = jFramePrincipal;
		setSize(640, 480);
		getContentPane().setLayout(null);

		jToolBar = new JToolBar();
		jToolBar.setFloatable(false);
		jToolBar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		jToolBar.setBounds(0, 0, 624, 50);
		getContentPane().add(jToolBar);

		jButtonSalvar = new JButton("Salvar");
		jButtonSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Salva componente
				salvarComponente();
			}
		});
		jButtonSalvar.setHorizontalTextPosition(SwingConstants.CENTER);
		jButtonSalvar.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButtonSalvar.setVerticalAlignment(SwingConstants.BOTTOM);
		jButtonSalvar.setIcon(new ImageIcon(JDialogTetrisReportEditor.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_salvar.png")));
		jButtonSalvar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jToolBar.add(jButtonSalvar);

		jButtonLimpar = new JButton("Limpar");
		jButtonLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Limpa campos
				limparCampos();
			}
		});
		jButtonLimpar.setIcon(new ImageIcon(JDialogTetrisReportEditor.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_excluir.png")));
		jButtonLimpar.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButtonLimpar.setVerticalAlignment(SwingConstants.BOTTOM);
		jButtonLimpar.setHorizontalTextPosition(SwingConstants.CENTER);
		jButtonLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jToolBar.add(jButtonLimpar);

		jButtonCancelar = new JButton("Cancelar");
		jButtonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Cancela edição
				cancelarEdicao();
			}
		});
		jButtonCancelar.setIcon(new ImageIcon(JDialogTetrisReportEditor.class.getResource("/br/com/analisasoftware/tetris/imagem/sair.png")));
		jButtonCancelar.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButtonCancelar.setVerticalAlignment(SwingConstants.BOTTOM);
		jButtonCancelar.setHorizontalTextPosition(SwingConstants.CENTER);
		jButtonCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jToolBar.add(jButtonCancelar);

		jScrollPaneRelatorio = new JScrollPane();
		jScrollPaneRelatorio.setBounds(5, 73, 609, 357);
		getContentPane().add(jScrollPaneRelatorio);

		jSyntaxTextPaneRelatorio = new JSyntaxTextPane();
		jSyntaxTextPaneRelatorio.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				jSyntaxTextPaneFoco =jSyntaxTextPaneRelatorio;
			}
		});

		jScrollPaneRelatorio.setViewportView(jSyntaxTextPaneRelatorio);

		jLabelRelatorio = new JLabel("Relat\u00F3rio");
		jLabelRelatorio.setBounds(10, 52, 112, 14);
		getContentPane().add(jLabelRelatorio);
		setTitle("Tetris Report Editor");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		jSyntaxTextPaneFoco = jSyntaxTextPaneRelatorio;

		//Preenchendo os editores
		Componente componente = jFramePrincipal.getjDesktopPaneAreaDeTrabalho().getJanela().getComponente(report);

		Atributo atributo;
		if((atributo=componente.getAtributo("Header"))!=null){
			if(!jSyntaxTextPaneRelatorio.getText().equals("")){
				jSyntaxTextPaneRelatorio.setText(jSyntaxTextPaneRelatorio.getText()+"\n");
			}
			jSyntaxTextPaneRelatorio.setText(jSyntaxTextPaneRelatorio.getText()+"#Header\n"+atributo.getValor());

		}

		if((atributo=componente.getAtributo("Detail"))!=null){
			if(!jSyntaxTextPaneRelatorio.getText().equals("")){
				jSyntaxTextPaneRelatorio.setText(jSyntaxTextPaneRelatorio.getText()+"\n");
			}
			jSyntaxTextPaneRelatorio.setText(jSyntaxTextPaneRelatorio.getText()+"#Detail\n"+atributo.getValor());
		}

		if((atributo=componente.getAtributo("Sumary"))!=null){
			if(!jSyntaxTextPaneRelatorio.getText().equals("")){
				jSyntaxTextPaneRelatorio.setText(jSyntaxTextPaneRelatorio.getText()+"\n");
			}
			jSyntaxTextPaneRelatorio.setText(jSyntaxTextPaneRelatorio.getText()+"#Sumary\n"+atributo.getValor());
		}

		if(jSyntaxTextPaneRelatorio.getText().equals("")){
			jSyntaxTextPaneRelatorio.setText(
					"#Header\n"
							+ "<h3>\n"
							+ "	Report1\n"
							+ "</h3>\n"
							+ "\n"
							+ "#Detail\n"
							+ "<p>\n"
							+ "	Row1\n"
							+ "</p>\n"
							+ "\n"
							+ "#Sumary\n"
							+ "<p>\n"
							+ "	Sum: 0.00\n"
							+ "</p>"
					);

		}

		setModal(true);

	}

	//Carrega idioma nos componentes
	public void carregarIdioma(){
		jLabelRelatorio.setText(Idioma.getTraducao("tetris.report", jFramePrincipal));;
		jButtonSalvar.setText(Idioma.getTraducao("tetris.save", jFramePrincipal));
		jButtonLimpar.setText(Idioma.getTraducao("tetris.clean", jFramePrincipal));
		jButtonCancelar.setText(Idioma.getTraducao("tetris.close", jFramePrincipal));
	}
	//Inicializa e exibe janela
	public void init(){
		carregarIdioma();
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setSize(getWidth(), getHeight() - 50);
		setVisible(true);
	}
	//Salva componente
	public void salvarComponente(){
		Componente componente = jFramePrincipal.getjDesktopPaneAreaDeTrabalho().getJanela().getComponente(report);
		//Lê tags especiais
		String relatorio = jSyntaxTextPaneRelatorio.getText().replace("#Header\n", "").replace("#Header", "");

		String header="";
		String detail="";
		String sumary="";

		String[] vetorDetail = relatorio.split("\n#Detail\n");
		if(vetorDetail.length < 2){
			header=relatorio;
		}else{
			header=vetorDetail[0];
			String[] vetorSumary = vetorDetail[1].split("\n#Sumary\n");
			if(vetorSumary.length < 2){
				detail=vetorDetail[1];
			}else{
				detail=vetorSumary[0];
				sumary=vetorSumary[1];
			}
		}
		//Modifica atributos do componente
		componente.mudarAtributo(new Atributo("Header", "String", header.replace("<html>", "").replace("</html>", "").replace("<head>", "").replace("</head>", "").replace("<title>", "").replace("</title>", "").replace("<body>", "").replace("</body>", "").replace("&quot;", "\"")));
		componente.mudarAtributo(new Atributo("Detail", "String", detail.replace("<html>", "").replace("</html>", "").replace("<head>", "").replace("</head>", "").replace("<title>", "").replace("</title>", "").replace("<body>", "").replace("</body>", "").replace("&quot;", "\"")));
		componente.mudarAtributo(new Atributo("Sumary", "String", sumary.replace("<html>", "").replace("</html>", "").replace("<head>", "").replace("</head>", "").replace("<title>", "").replace("</title>", "").replace("<body>", "").replace("</body>", "").replace("&quot;", "\"")));
	}
	//Cancela edição e fecha janela
	public void cancelarEdicao(){
		dispose();
	}
	//Limpa campos
	public void limparCampos(){
		if(JDialogMensagem.exibirMensagem("Perguntar", Idioma.getTraducao("tetris.message_clean_tetris_report", jFramePrincipal))==1){
			jSyntaxTextPaneRelatorio.setText(
					"#Header\n"
							+ "<h3>\n"
							+ "	Report1\n"
							+ "</h3>\n"
							+ "\n"
							+ "#Detail\n"
							+ "<p>\n"
							+ "	Row1\n"
							+ "</p>\n"
							+ "\n"
							+ "#Sumary\n"
							+ "<p>\n"
							+ "	Sum: 0.00\n"
							+ "</p>"
					);

		}
	}
	//Getterse Setters
	public String getReport() {
		return report;
	}

	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}

	public JSyntaxTextPane getjSyntaxTextPaneFoco() {
		return jSyntaxTextPaneFoco;
	}


}
