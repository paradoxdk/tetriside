/*
 *Janela de configurações de Banco de Dados do projeto
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 26 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.janelas;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import br.com.analisasoftware.tetris.modelo.Idioma;
import componentes.visao.JTetrisButton;
import componentes.visao.JTetrisComboBox;
import componentes.visao.JTetrisPanel;

@SuppressWarnings("serial")
public class JDialogConfiguracoesBancoDeDados extends JDialog {
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Componentes gráficos
	private JTetrisComboBox jTetrisComboBoxDriver;
	private JTetrisComboBox jTetrisComboBoxConnectionString;
	private JTetrisButton jTetrisButtonSalvar;
	private JTetrisButton jTetrisButtonCancelar;
	private JLabel jLabelDriver;
	private JLabel jLabelConnectionString;
	
	public JDialogConfiguracoesBancoDeDados(JFramePrincipal jFramePrincipal) {
		super();
		this.jFramePrincipal = jFramePrincipal;
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogComponentesExternos.class.getResource("/br/com/analisasoftware/tetris/imagem/icone.png")));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		JTetrisPanel tetrisPanel = new JTetrisPanel();
		setContentPane(tetrisPanel);
		
		jLabelDriver = new JLabel("Driver");
		jLabelDriver.setBounds(12, 12, 106, 20);
		tetrisPanel.add(jLabelDriver);
		
		jTetrisComboBoxDriver = new JTetrisComboBox();
		jTetrisComboBoxDriver.setEditable(true);
		jTetrisComboBoxDriver.setBounds(12, 32, 326, 25);
		
		jTetrisComboBoxDriver.addItem("com.mysql.jdbc.Driver");
		jTetrisComboBoxDriver.addItem("sun.jdbc.odbc.JdbcOdbcDriver");
		
		jTetrisComboBoxDriver.setSelectedIndex(0);
		tetrisPanel.add(jTetrisComboBoxDriver);
		
		jLabelConnectionString = new JLabel("Connection String");
		jLabelConnectionString.setBounds(12, 62, 328, 20);
		tetrisPanel.add(jLabelConnectionString);
		
		jTetrisComboBoxConnectionString = new JTetrisComboBox();
		jTetrisComboBoxConnectionString.setEditable(true);
		jTetrisComboBoxConnectionString.addItem("jdbc:mysql://$server/$database");
		jTetrisComboBoxConnectionString.addItem("jdbc:odbc:$database");
		
		jTetrisComboBoxConnectionString.setBounds(12, 82, 326, 25);
		jTetrisComboBoxConnectionString.setSelectedIndex(0);
		tetrisPanel.add(jTetrisComboBoxConnectionString);
		if(jFramePrincipal.getProjeto().getBancoDeDados()!=null){
			jTetrisComboBoxDriver.setSelectedItem(""+jFramePrincipal.getProjeto().getBancoDeDados().getDriver());
			jTetrisComboBoxConnectionString.setText(""+jFramePrincipal.getProjeto().getBancoDeDados().getConnectionString());
		}
		
		jTetrisButtonSalvar = new JTetrisButton("Salvar");
		jTetrisButtonSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Modifica as informações de configuração na instância de BancoDeDados do projeto e fecha a janela
				getjFramePrincipal().getProjeto().getBancoDeDados().setDriver(jTetrisComboBoxDriver.getText());
				getjFramePrincipal().getProjeto().getBancoDeDados().setConnectionString(jTetrisComboBoxConnectionString.getText());
				getjFramePrincipal().getProjeto().getBancoDeDados().setAlterado(true);
				if(getjFramePrincipal().getProjeto().getJanelaPrincipal()!=null){
					getjFramePrincipal().getProjeto().getJanelaPrincipal().setAlterado(true);
				}
								
				dispose();
			}
		});
		jTetrisButtonSalvar.setIcon(new ImageIcon(JDialogConfiguracoes.class.getResource("/br/com/analisasoftware/tetris/imagem/sim.png")));
		jTetrisButtonSalvar.setBounds(82, 150, 118, 25);
		jTetrisButtonSalvar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jTetrisButtonSalvar);
		
		jTetrisButtonCancelar = new JTetrisButton("Cancelar");
		jTetrisButtonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Fecha a janela
				dispose();
			}
		});
		jTetrisButtonCancelar.setIcon(new ImageIcon(JDialogConfiguracoes.class.getResource("/br/com/analisasoftware/tetris/imagem/sair.png")));
		jTetrisButtonCancelar.setBounds(210, 150, 128, 25);
		jTetrisButtonCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jTetrisButtonCancelar);
		
		setTitle("Configurações");
		setSize(new Dimension(354, 229));
		setResizable(false);
		setLocationRelativeTo(jFramePrincipal);
		setModal(true);
	}
	
	//Carrega idioma nos componentes
	public void carregarIdioma(){
		setTitle(Idioma.getTraducao("tetris.settings", jFramePrincipal));
		jTetrisButtonSalvar.setText(Idioma.getTraducao("tetris.save", jFramePrincipal));
		jTetrisButtonCancelar.setText(Idioma.getTraducao("tetris.cancel", jFramePrincipal));
	}
	
	//Inicializa e exibe a janela
	public void init(){
		//Carrega idioma dos componentes
		carregarIdioma();
		
		//Adicionando Listener para teclas de acao
		Component[] componentes = getContentPane().getComponents();
		KeyAdapter keyAdapter = new KeyAdapter(){
			public void keyReleased(KeyEvent arg0){
				if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
					dispose();
				}
			}
		};
		for (int i = 0; i < componentes.length; i++) {
			componentes[i].addKeyListener(keyAdapter);
		}
		
		setVisible(true);
	}

	//Getters and Setters
	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}
}
