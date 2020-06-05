/*
 *Espelha banco de dados de SGDB no projeto
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 27 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.janelas;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Toolkit;

import javax.swing.JDialog;

import componentes.visao.JTetrisPanel;
import componentes.visao.JTetrisTextField;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JList;
import javax.swing.border.BevelBorder;

import br.com.analisasoftware.tetris.modelo.Idioma;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@SuppressWarnings("serial")
public class JDialogEspelharBancoDeDados extends JDialog {
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Variável de retorno
	private String retorno[];
	//Componentes gráficos
	private JTetrisTextField jTextFieldServidor;
	private JPasswordField jPasswordFieldSenha;
	private JList<String> jListBancoDeDados;
	private JButton jButtonCarregar;
	private JLabel jLabelSelecioneOsDados;
	private JLabel jLabelServidor;
	private JLabel jLabelSenha;
	private JLabel jLabelSelecioneOBanco;
	private JButton jButtonEspelhar;
	private JButton jButtonCancelar;
	private JTetrisTextField jTextFieldUsuario;
	private JLabel jLabelUsuario;

	public JDialogEspelharBancoDeDados(JFramePrincipal jFramePrincipal) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.jFramePrincipal=jFramePrincipal;
		retorno = null;
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogNomeProjeto.class.getResource("/br/com/analisasoftware/tetris/imagem/icone.png")));
		setResizable(false);
		setTitle("Espelhar Banco de Dados");
		JTetrisPanel tetrisPanel = new JTetrisPanel();
		setContentPane(tetrisPanel);

		jLabelSelecioneOsDados = new JLabel("Insira os dados do servidor");
		jLabelSelecioneOsDados.setBounds(10, 11, 314, 14);
		tetrisPanel.add(jLabelSelecioneOsDados);

		jLabelServidor = new JLabel("Servidor");
		jLabelServidor.setBounds(10, 36, 80, 14);
		tetrisPanel.add(jLabelServidor);

		jTextFieldServidor = new JTetrisTextField();
		jTextFieldServidor.setBounds(10, 56, 100, 25);
		tetrisPanel.add(jTextFieldServidor);
		jTextFieldServidor.setColumns(10);

		jPasswordFieldSenha = new JPasswordField();
		jPasswordFieldSenha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					jButtonCarregar.requestFocus();
				}
			}
		});
		jPasswordFieldSenha.setBounds(221, 56, 103, 25);
		tetrisPanel.add(jPasswordFieldSenha);

		jLabelSenha = new JLabel("Senha");
		jLabelSenha.setBounds(221, 36, 103, 14);
		tetrisPanel.add(jLabelSenha);

		jButtonCarregar = new JButton("Carregar lista de banco de dados");
		jButtonCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Carrega lista de banco de dados
				try {
					//Captura senha do banco de dados
					String passwd = new String (jPasswordFieldSenha.getPassword());
					String senha="";
					if(passwd.equals("")==false){
						senha=""+passwd;
					}
					//Tenta efetuar uma conexão
					Connection conexao = DriverManager.getConnection(getjFramePrincipal().getProjeto().getBancoDeDados().getConnectionString().replace("$server", jTextFieldServidor.getText()).replace("$database", ""), jTextFieldUsuario.getText(), senha);
					//Cria o statement
					Statement statementBancoDeDados = conexao.createStatement();
					//Lê lista de bancos de dados
					ResultSet resultSetBancoDeDados = statementBancoDeDados.executeQuery("show databases");
					resultSetBancoDeDados.first();

					DefaultListModel<String> listModel = new DefaultListModel<String>();

					if(resultSetBancoDeDados.getRow() > 0){
						do{
							listModel.addElement(resultSetBancoDeDados.getString("Database"));
						}while(resultSetBancoDeDados.next());
					}

					jListBancoDeDados.setModel(listModel);
					//Fecha resultSet, statement e conexão
					resultSetBancoDeDados.close();
					statementBancoDeDados.close();

					conexao.close();
				} catch (Exception exc) {
					// TODO: handle exception
					JDialogMensagem.exibirMensagem("Erro", Idioma.getTraducao("tetris.message_error_load_database_list", getjFramePrincipal())+"<br/>"+exc);
				}
			}
		});
		jButtonCarregar.setIcon(new ImageIcon(JDialogEspelharBancoDeDados.class.getResource("/br/com/analisasoftware/tetris/imagem/atualizar.png")));
		jButtonCarregar.setBounds(10, 85, 314, 25);
		jButtonCarregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jButtonCarregar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 136, 314, 158);
		tetrisPanel.add(scrollPane);

		jListBancoDeDados = new JList<String>();
		jListBancoDeDados.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setViewportView(jListBancoDeDados);

		jLabelSelecioneOBanco = new JLabel("Selecione o banco de dados");
		jLabelSelecioneOBanco.setBounds(10, 114, 314, 14);
		tetrisPanel.add(jLabelSelecioneOBanco);

		jButtonEspelhar = new JButton("Espelhar");
		jButtonEspelhar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Retorna vetor com informações para espelhamento e fecha janela
				if((jTextFieldServidor.getText().equals("") == false) && (jListBancoDeDados.getSelectedIndex() >= 0)){
					retorno = new String [] {jTextFieldServidor.getText(), new String(jPasswordFieldSenha.getPassword()), jListBancoDeDados.getSelectedValue(), jTextFieldUsuario.getText()};
					dispose();
				}else{
					JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_select_a_database_to_mirror_to_the_project", getjFramePrincipal()));
				}
			}
		});
		jButtonEspelhar.setIcon(new ImageIcon(JDialogEspelharBancoDeDados.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_exportar.png")));
		jButtonEspelhar.setBounds(36, 305, 132, 26);
		jButtonEspelhar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jButtonEspelhar);

		jButtonCancelar = new JButton("Cancelar");
		jButtonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Fecha janela
				dispose();
			}
		});
		jButtonCancelar.setIcon(new ImageIcon(JDialogEspelharBancoDeDados.class.getResource("/br/com/analisasoftware/tetris/imagem/sair.png")));
		jButtonCancelar.setBounds(180, 305, 144, 26);
		jButtonCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jButtonCancelar);
		
		jTextFieldUsuario = new JTetrisTextField();
		jTextFieldUsuario.setColumns(10);
		jTextFieldUsuario.setText("root");
		jTextFieldUsuario.setBounds(121, 56, 90, 25);
		tetrisPanel.add(jTextFieldUsuario);
		
		jLabelUsuario = new JLabel("Usu\u00E1rio");
		jLabelUsuario.setBounds(121, 36, 90, 14);
		tetrisPanel.add(jLabelUsuario);
		setSize(340, 375);
		setLocationRelativeTo(jFramePrincipal);
		setModal(true);
		setLocationRelativeTo(jFramePrincipal);
	}

	//Carrega idioma nos componentes
	public void carregarIdioma(){
		setTitle(Idioma.getTraducao("tetris.database_mirroring", jFramePrincipal));
		jLabelSelecioneOsDados.setText(Idioma.getTraducao("tetris.insert_data_mirror_database", jFramePrincipal));
		jLabelServidor.setText(Idioma.getTraducao("tetris.server", jFramePrincipal));
		jLabelSenha.setText(Idioma.getTraducao("tetris.password", jFramePrincipal));
		jLabelUsuario.setText(Idioma.getTraducao("tetris.user", jFramePrincipal));
		jLabelSelecioneOBanco.setText(Idioma.getTraducao("tetris.select_the_database", jFramePrincipal));
		jButtonCarregar.setText(Idioma.getTraducao("tetris.load_the_database_list", jFramePrincipal));
		jButtonCancelar.setText(Idioma.getTraducao("tetris.cancel", jFramePrincipal));
		jButtonEspelhar.setText(Idioma.getTraducao("tetris.mirror", jFramePrincipal));
	}
	//Inicializa e exibe a janela
	public String[] init(){
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
		jListBancoDeDados.addKeyListener(keyAdapter);
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
