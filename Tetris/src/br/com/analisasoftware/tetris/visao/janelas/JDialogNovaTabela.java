/*
 *Janela para preenchimento de dados de tabela de banco de dados do projeto
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

import java.util.ArrayList;

import componentes.modelo.bancodedados.Tabela;
import componentes.modelo.bancodedados.Coluna;
import componentes.visao.JTetrisButton;
import componentes.visao.JTetrisPanel;
import componentes.visao.JTetrisTextField;

import javax.swing.JLabel;

import br.com.analisasoftware.tetris.modelo.Idioma;

import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class JDialogNovaTabela extends JDialog {
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Variável de retorno
	private Tabela retorno;
	//Componentes gráficos
	private JTetrisTextField jTetrisTextFieldNome;
	private JLabel jLabelNome;
	private JTetrisButton jButtonCriar;
	private JTetrisButton jButtonCancelar;

	public JDialogNovaTabela(JFramePrincipal jFramePrincipal) {
		super();
		retorno=null;
		this.jFramePrincipal = jFramePrincipal;
		JTetrisPanel tetrisPanel = new JTetrisPanel();
		setContentPane(tetrisPanel);

		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogComponentesExternos.class.getResource("/br/com/analisasoftware/tetris/imagem/icone.png")));

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(jFramePrincipal);
		//setModal(true);
		setTitle("Nova Tabela");
		setSize(new Dimension(374, 150));
		setResizable(false);
		setLocationRelativeTo(jFramePrincipal);

		jLabelNome = new JLabel("Nome");
		jLabelNome.setBounds(12, 12, 350, 15);
		tetrisPanel.add(jLabelNome);

		jTetrisTextFieldNome = new JTetrisTextField();
		jTetrisTextFieldNome.setBounds(12, 32, 350, 25);
		tetrisPanel.add(jTetrisTextFieldNome);
		jTetrisTextFieldNome.setColumns(10);

		jButtonCriar = new JTetrisButton("Criar");
		jButtonCriar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Cria tabela
				//Verifica se o nome foi preenchido corretamente
				if(jTetrisTextFieldNome.getText().equals("")==false){
					//Define o retorno e fecha a janela
					retorno=new Tabela(jTetrisTextFieldNome.getText(), new ArrayList<Coluna>());
					dispose();
				}
			}
		});
		jButtonCriar.setIcon(new ImageIcon(JDialogNovaTabela.class.getResource("/br/com/analisasoftware/tetris/imagem/sim.png")));
		jButtonCriar.setBounds(105, 63, 117, 25);
		jButtonCriar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jButtonCriar);

		jButtonCancelar = new JTetrisButton("Cancelar");
		jButtonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Fecha a janela
				dispose();
			}
		});
		jButtonCancelar.setIcon(new ImageIcon(JDialogNovaTabela.class.getResource("/br/com/analisasoftware/tetris/imagem/sair.png")));
		jButtonCancelar.setBounds(234, 63, 126, 25);
		jButtonCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jButtonCancelar);

	}

	//Carrega idioma nos componentes
	public void carregarIdioma(){
		setTitle(Idioma.getTraducao("tetris.new_table", jFramePrincipal));
		jLabelNome.setText(Idioma.getTraducao("tetris.name", jFramePrincipal));
		jButtonCriar.setText(Idioma.getTraducao("tetris.create", jFramePrincipal));
		jButtonCancelar.setText(Idioma.getTraducao("tetris.cancel", jFramePrincipal));
	}
	//Inicializa e exibe a janela, retornando um objeto Tabela
	public Tabela init(){
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
