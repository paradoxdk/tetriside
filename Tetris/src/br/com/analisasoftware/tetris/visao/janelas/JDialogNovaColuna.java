/*
 *Janela para preenchimento de dados de coluna de banco de dados do projeto
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 27 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.janelas;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;

import componentes.modelo.bancodedados.Coluna;
import componentes.modelo.bancodedados.Tabela;
import componentes.visao.JTetrisButton;
import componentes.visao.JTetrisComboBox;
import componentes.visao.JTetrisPanel;
import componentes.visao.JTetrisTextField;

import javax.swing.JLabel;

import br.com.analisasoftware.tetris.modelo.Idioma;

import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class JDialogNovaColuna extends JDialog {
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Variável de retorno
	private Coluna retorno;
	//Componentes gráficos
	private JTetrisTextField jTetrisTextFieldNome;
	private JTetrisComboBox jComboBoxTipo;
	private JTetrisComboBox jComboBoxRelacionamento;
	private JCheckBox jCheckBoxNulo;
	private JLabel jLabelNome;
	private JLabel jLabelTipo;
	private JLabel jLabelRelacionamento;
	private JTetrisButton jButtonAdicionar;
	private JTetrisButton jButtonCancelar;
	private JCheckBox jCheckBoxAutoincrement;
	private JCheckBox jCheckBoxPrimaryKey;

	public JDialogNovaColuna(JFramePrincipal jFramePrincipal) {
		super();
		retorno=null;
		this.jFramePrincipal = jFramePrincipal;
		JTetrisPanel tetrisPanel = new JTetrisPanel();
		setContentPane(tetrisPanel);

		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogComponentesExternos.class.getResource("/br/com/analisasoftware/tetris/imagem/icone.png")));

		jLabelNome = new JLabel("Nome");
		jLabelNome.setBounds(12, 12, 348, 15);
		tetrisPanel.add(jLabelNome);

		jTetrisTextFieldNome = new JTetrisTextField();
		jTetrisTextFieldNome.setBounds(12, 32, 282, 25);
		tetrisPanel.add(jTetrisTextFieldNome);
		jTetrisTextFieldNome.setColumns(10);
		jTetrisTextFieldNome.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0){
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					jComboBoxTipo.requestFocus();
				}
			}
		});

		jLabelTipo = new JLabel("Tipo");
		jLabelTipo.setBounds(12, 63, 151, 15);
		tetrisPanel.add(jLabelTipo);

		jComboBoxTipo = new JTetrisComboBox();
		jComboBoxTipo.setEditable(true);
		jComboBoxTipo.setModel(new DefaultComboBoxModel<String>(new String[] {"varchar(50)", "int", "decimal(9, 2)", "date", "datetime", "text"}));
		jComboBoxTipo.setSelectedIndex(0);
		jComboBoxTipo.setBounds(12, 83, 282, 25);
		tetrisPanel.add(jComboBoxTipo);

		jLabelRelacionamento = new JLabel("Relacionamento");
		jLabelRelacionamento.setVisible(false);
		jLabelRelacionamento.setBounds(304, 83, 185, 15);
		tetrisPanel.add(jLabelRelacionamento);

		jComboBoxRelacionamento = new JTetrisComboBox();
		jComboBoxRelacionamento.setVisible(false);
		jComboBoxRelacionamento.setBounds(304, 103, 185, 25);

		tetrisPanel.add(jComboBoxRelacionamento);
		jComboBoxRelacionamento.addItem(Idioma.getTraducao("tetris.none2", jFramePrincipal));

		ArrayList<Tabela> arrayListTabelas = getjFramePrincipal().getProjeto().getBancoDeDados().getArrayListTabelas();

		for (int i = 0; i < arrayListTabelas.size(); i++) {
			jComboBoxRelacionamento.addItem(arrayListTabelas.get(i).getNome());
		}

		jButtonAdicionar = new JTetrisButton("Adicionar");
		jButtonAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Adiciona coluna
				//Verifica se o nome da coluna foi preenchido
				if(jTetrisTextFieldNome.getText().equals("")==false){
					//Coleta parâmetros
					String nulo="", autoIncrement="", primaryKey="";
					if(jCheckBoxNulo.isSelected()==false){
						nulo=" not null";
					}
					if(jCheckBoxAutoincrement.isSelected()){
						autoIncrement=" auto_increment";
					}
					if(jCheckBoxPrimaryKey.isSelected()){
						primaryKey=" primary key";
					}
					//Define o retorno
					retorno=new Coluna(jTetrisTextFieldNome.getText(), ""+jComboBoxTipo.getSelectedItem()+nulo+primaryKey+autoIncrement);
					//Fecha janela
					dispose();
				}
			}
		});
		jButtonAdicionar.setIcon(new ImageIcon(JDialogNovaColuna.class.getResource("/br/com/analisasoftware/tetris/imagem/sim.png")));
		jButtonAdicionar.setBounds(100, 119, 124, 25);
		jButtonAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jButtonAdicionar);

		jButtonCancelar = new JTetrisButton("Cancelar");
		jButtonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Fecha janela
				dispose();
			}
		});
		jButtonCancelar.setIcon(new ImageIcon(JDialogNovaColuna.class.getResource("/br/com/analisasoftware/tetris/imagem/sair.png")));
		jButtonCancelar.setBounds(236, 119, 124, 25);
		jButtonCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jButtonCancelar);

		jCheckBoxNulo = new JCheckBox("nulo");
		jCheckBoxNulo.setSelected(true);
		jCheckBoxNulo.setBounds(302, 31, 60, 23);
		tetrisPanel.add(jCheckBoxNulo);
		
		jCheckBoxAutoincrement = new JCheckBox("auto_increment");
		jCheckBoxAutoincrement.setBounds(364, 31, 113, 23);
		tetrisPanel.add(jCheckBoxAutoincrement);
		
		jCheckBoxPrimaryKey = new JCheckBox("primary key");
		jCheckBoxPrimaryKey.setBounds(302, 59, 117, 23);
		tetrisPanel.add(jCheckBoxPrimaryKey);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(jFramePrincipal);

		setTitle("Nova Coluna");
		setSize(new Dimension(499, 200));
		setResizable(false);
		setLocationRelativeTo(jFramePrincipal);


	}

	//Carrega idioma nos componentes
	public void carregarIdioma(){
		setTitle(Idioma.getTraducao("tetris.new_column", jFramePrincipal));
		jLabelNome.setText(Idioma.getTraducao("tetris.name", jFramePrincipal));
		jLabelTipo.setText(Idioma.getTraducao("tetris.type", jFramePrincipal));
		jLabelRelacionamento.setText(Idioma.getTraducao("tetris.relationship", jFramePrincipal));
		jCheckBoxNulo.setText(Idioma.getTraducao("tetris.null", jFramePrincipal));
		jButtonAdicionar.setText(Idioma.getTraducao("tetris.add", jFramePrincipal));
		jButtonCancelar.setText(Idioma.getTraducao("tetris.cancel", jFramePrincipal));
	}
	//Inicializa e exibe janela,retornando um objeto Coluna
	public Coluna init(){
		carregarIdioma();
		setVisible(true);
		jTetrisTextFieldNome.requestFocus();
		setVisible(false);
		setModal(true);
		
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
