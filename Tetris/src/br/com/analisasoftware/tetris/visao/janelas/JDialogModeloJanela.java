/*
 *Diálogo de escolha de modelo de janela para gerar a partir de banco de dados
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 27 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.janelas;

import java.awt.Component;
import java.awt.Toolkit;

import javax.swing.JDialog;

import componentes.visao.JTetrisButton;
import componentes.visao.JTetrisPanel;

import javax.swing.JLabel;
import javax.swing.border.BevelBorder;

import br.com.analisasoftware.tetris.modelo.Idioma;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class JDialogModeloJanela extends JDialog {
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Variável de retorno
	private String retorno;
	//Componentes gráficos
	private JLabel jLabelSelecioneUmModelo;
	private JTetrisButton jButtonCadastroVertical;
	private JTetrisButton jButtonCadastroHorizontal;
	private JTetrisButton jButtonTabelaDeConsulta;
	private JTetrisButton jButtonDoisEmUm;
	private JTetrisButton jButtonLogin;
	private JTetrisButton jButtonCancelar;
	public JDialogModeloJanela(JFramePrincipal jFramePrincipal) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.jFramePrincipal=jFramePrincipal;
		retorno=null;
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogNomeProjeto.class.getResource("/br/com/analisasoftware/tetris/imagem/icone.png")));
		setResizable(false);
		setTitle("Nova Janela");
		JTetrisPanel tetrisPanel = new JTetrisPanel();
		setContentPane(tetrisPanel);

		jLabelSelecioneUmModelo = new JLabel("Selecione um modelo de janela");
		jLabelSelecioneUmModelo.setBounds(12, 12, 420, 16);
		tetrisPanel.add(jLabelSelecioneUmModelo);

		jButtonCadastroVertical = new JTetrisButton("Cadastro vertical");
		jButtonCadastroVertical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Define retorno janela vertical e fecha a janela
				retorno="vertical";
				dispose();
			}
		});
		jButtonCadastroVertical.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonCadastroVertical.setIcon(new ImageIcon(JDialogModeloJanela.class.getResource("/br/com/analisasoftware/tetris/imagem/janela_vertical.png")));
		jButtonCadastroVertical.setHorizontalTextPosition(SwingConstants.CENTER);
		jButtonCadastroVertical.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButtonCadastroVertical.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		jButtonCadastroVertical.setBounds(12, 40, 200, 110);
		tetrisPanel.add(jButtonCadastroVertical);

		jButtonCadastroHorizontal = new JTetrisButton("Cadastro vertical");
		jButtonCadastroHorizontal.setText("Cadastro horizontal");
		jButtonCadastroHorizontal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Define retorno janela horizontal e fecha a janela
				retorno="horizontal";
				dispose();
			}
		});
		jButtonCadastroHorizontal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonCadastroHorizontal.setIcon(new ImageIcon(JDialogModeloJanela.class.getResource("/br/com/analisasoftware/tetris/imagem/janela_horizontal.png")));
		jButtonCadastroHorizontal.setHorizontalTextPosition(SwingConstants.CENTER);
		jButtonCadastroHorizontal.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButtonCadastroHorizontal.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		jButtonCadastroHorizontal.setBounds(224, 40, 200, 110);
		tetrisPanel.add(jButtonCadastroHorizontal);

		jButtonTabelaDeConsulta = new JTetrisButton("Cadastro vertical");
		jButtonTabelaDeConsulta.setText("Tabela de consulta");
		jButtonTabelaDeConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Define retorno janela de consulta e fecha a janela
				retorno="consulta";
				dispose();
			}
		});
		jButtonTabelaDeConsulta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonTabelaDeConsulta.setIcon(new ImageIcon(JDialogModeloJanela.class.getResource("/br/com/analisasoftware/tetris/imagem/janela_consulta.png")));
		jButtonTabelaDeConsulta.setHorizontalTextPosition(SwingConstants.CENTER);
		jButtonTabelaDeConsulta.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButtonTabelaDeConsulta.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		jButtonTabelaDeConsulta.setBounds(12, 162, 200, 110);
		tetrisPanel.add(jButtonTabelaDeConsulta);

		jButtonDoisEmUm = new JTetrisButton("Cadastro vertical");
		jButtonDoisEmUm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Define retorno janela dois em um e fecha a janela
				retorno="doisemum";
				dispose();
			}
		});
		jButtonDoisEmUm.setText("Dois em um");
		jButtonDoisEmUm.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonDoisEmUm.setIcon(new ImageIcon(JDialogModeloJanela.class.getResource("/br/com/analisasoftware/tetris/imagem/janela_dois_em_um.png")));
		jButtonDoisEmUm.setHorizontalTextPosition(SwingConstants.CENTER);
		jButtonDoisEmUm.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButtonDoisEmUm.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		jButtonDoisEmUm.setBounds(224, 162, 200, 110);
		tetrisPanel.add(jButtonDoisEmUm);

		jButtonCancelar = new JTetrisButton("Cancelar");
		jButtonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Fecha a janela
				dispose();
			}
		});
		jButtonCancelar.setIcon(new ImageIcon(JDialogModeloJanela.class.getResource("/br/com/analisasoftware/tetris/imagem/sair.png")));
		jButtonCancelar.setBounds(313, 420, 110, 26);
		jButtonCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jButtonCancelar);

		jButtonLogin = new JTetrisButton("Cadastro vertical");
		jButtonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Define retorno janela de login e fecha a janela
				retorno="login";
				dispose();
			}
		});
		jButtonLogin.setIcon(new ImageIcon(JDialogModeloJanela.class.getResource("/br/com/analisasoftware/tetris/imagem/janela_login.png")));
		jButtonLogin.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButtonLogin.setText("Login");
		jButtonLogin.setHorizontalTextPosition(SwingConstants.CENTER);
		jButtonLogin.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		jButtonLogin.setBounds(12, 284, 200, 110);
		jButtonLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jButtonLogin);
		setSize(441, 486);
		setLocationRelativeTo(jFramePrincipal);
		setModal(true);
		setLocationRelativeTo(jFramePrincipal);

	}
	
	//Carrega idioma nos componentes
	public void carregarIdioma(){
		setTitle(Idioma.getTraducao("tetris.new_window", jFramePrincipal));
		jLabelSelecioneUmModelo.setText(Idioma.getTraducao("tetris.select_a_window_model", jFramePrincipal));
		jButtonCadastroHorizontal.setText(Idioma.getTraducao("tetris.horizontal_registration", jFramePrincipal));
		jButtonCadastroVertical.setText(Idioma.getTraducao("tetris.vertical_registration", jFramePrincipal));
		jButtonCancelar.setText(Idioma.getTraducao("tetris.cancel", jFramePrincipal));
		jButtonDoisEmUm.setText(Idioma.getTraducao("tetris.two_in_one", jFramePrincipal));
		jButtonLogin.setText(Idioma.getTraducao("tetris.login", jFramePrincipal));
		jButtonTabelaDeConsulta.setText(Idioma.getTraducao("tetris.query_table", jFramePrincipal));
	}
	//Inicializa e exibe a janela, retornando uma string
	public String init(){
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
		return retorno;
	}

	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}
}
