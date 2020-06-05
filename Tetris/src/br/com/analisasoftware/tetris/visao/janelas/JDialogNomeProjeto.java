/*
 *Janela para preenchimento de nome de projeto
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 27 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.janelas;

import javax.swing.JDialog;

import componentes.visao.JTetrisButton;
import componentes.visao.JTetrisPanel;
import componentes.visao.JTetrisTextField;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Toolkit;

import javax.swing.JLabel;

import br.com.analisasoftware.tetris.modelo.Idioma;

import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Insets;

@SuppressWarnings("serial")
public class JDialogNomeProjeto extends JDialog {
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Variável de retorno
	private String nome;
	//Componentes gráficos
	private JTetrisTextField jTetrisTextFieldNome;
	private JTetrisButton jTetrisButtonCancelar;
	private JTetrisButton jTetrisButtonCriar;
	private JLabel jLabelNome;
	
	public JDialogNomeProjeto(JFramePrincipal jFramePrincipal) {
		this.jFramePrincipal=jFramePrincipal;
		nome=null;
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogNomeProjeto.class.getResource("/br/com/analisasoftware/tetris/imagem/icone.png")));
		setResizable(false);
		setTitle(Idioma.getTraducao("tetris.new_project", jFramePrincipal));
		JTetrisPanel tetrisPanel = new JTetrisPanel();
		setContentPane(tetrisPanel);
		setSize(450, 170);
		setLocationRelativeTo(jFramePrincipal);
		
		jTetrisTextFieldNome = new JTetrisTextField();
		jTetrisTextFieldNome.setMascara(JTetrisTextField.MASCARA_SEM_ESPACOS);
		jTetrisTextFieldNome.setBounds(12, 32, 421, 25);
		tetrisPanel.add(jTetrisTextFieldNome);
		jTetrisTextFieldNome.setColumns(10);
		
		jLabelNome = new JLabel("Nome");
		jLabelNome.setBounds(12, 12, 421, 16);
		tetrisPanel.add(jLabelNome);
		
		jTetrisButtonCriar = new JTetrisButton("Criar");
		jTetrisButtonCriar.setMargin(new Insets(2, 0, 2, 0));
		jTetrisButtonCriar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Define nome do projeto e fecha a janela
				if(jTetrisTextFieldNome.getText().equals("")==false){
					nome=jTetrisTextFieldNome.getText();
					dispose();
				}else{
					JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_insert_a_name_to_the_project", getjFramePrincipal()));
				}
			}
		});
		jTetrisButtonCriar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jTetrisButtonCriar.setIcon(new ImageIcon(JDialogNomeProjeto.class.getResource("/br/com/analisasoftware/tetris/imagem/sim.png")));
		jTetrisButtonCriar.setBounds(211, 65, 98, 25);
		tetrisPanel.add(jTetrisButtonCriar);
		
		jTetrisButtonCancelar = new JTetrisButton("Cancelar");
		jTetrisButtonCancelar.setMargin(new Insets(2, 0, 2, 0));
		jTetrisButtonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Fecha a janela
				dispose();
			}
		});
		jTetrisButtonCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jTetrisButtonCancelar.setIcon(new ImageIcon(JDialogNomeProjeto.class.getResource("/br/com/analisasoftware/tetris/imagem/sair.png")));
		jTetrisButtonCancelar.setBounds(321, 65, 112, 25);
		tetrisPanel.add(jTetrisButtonCancelar);
		
	}
	
	//Carrega idioma nos componentes
	public void carregarIdioma(){
		jLabelNome.setText(Idioma.getTraducao("tetris.name", jFramePrincipal));
		jTetrisButtonCriar.setText(Idioma.getTraducao("tetris.create", jFramePrincipal));
		jTetrisButtonCancelar.setText(Idioma.getTraducao("tetris.cancel", jFramePrincipal));
	}
	
	//Inicializa e exibe a janela, retornando uma string
	public String init(){
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
		return nome;
	}
	//Getters e Setters
	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}
	
	public JTetrisButton getjTetrisButtonCancelar() {
		return jTetrisButtonCancelar;
	}

	public JTetrisButton getjTetrisButtonCriar() {
		return jTetrisButtonCriar;
	}

}
