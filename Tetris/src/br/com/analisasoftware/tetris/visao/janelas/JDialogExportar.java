/*
 *Janela de seleção de Exportação do Projeto
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 27 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.janelas;

import javax.swing.JDialog;

import br.com.analisasoftware.tetris.modelo.Idioma;
import componentes.visao.JTetrisButton;
import componentes.visao.JTetrisPanel;

import java.awt.Component;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


@SuppressWarnings("serial")
public class JDialogExportar extends JDialog {
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Variável de retorno
	private String retorno;
	//Componentes gráficos
	private JTetrisButton jTetrisButtonExportarProjetoTetris;
	private JTetrisButton jTetrisButtonExportarProjetoJava;
	private JTetrisButton jTetrisButtonFechar;
	private JTetrisButton jTetrisButtonExportarExecutavelJar;

	public JDialogExportar(JFramePrincipal jFramePrincipal) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		retorno=null;
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogExportar.class.getResource("/br/com/analisasoftware/tetris/imagem/icone.png")));
		this.jFramePrincipal=jFramePrincipal;
		JTetrisPanel tetrisPanel = new JTetrisPanel();
		setContentPane(tetrisPanel);

		jTetrisButtonExportarProjetoTetris = new JTetrisButton("Exportar Projeto Tetris");
		jTetrisButtonExportarProjetoTetris.setText("Exportar Projeto TetrisIDE");
		jTetrisButtonExportarProjetoTetris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Define retorno para Exportar Projeto TetrisIDEe fecha a janela
				retorno="0";
				dispose();
			}
		});
		jTetrisButtonExportarProjetoTetris.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jTetrisButtonExportarProjetoTetris.setIcon(new ImageIcon(JDialogExportar.class.getResource("/br/com/analisasoftware/tetris/imagem/botao_tetris.png")));
		jTetrisButtonExportarProjetoTetris.setBounds(10, 11, 224, 48);
		tetrisPanel.add(jTetrisButtonExportarProjetoTetris);

		jTetrisButtonExportarProjetoJava = new JTetrisButton("Exportar Projeto Java");
		jTetrisButtonExportarProjetoJava.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Define retorno para Exportar Projeto Java e fecha a janela
				retorno="1";
				dispose();
			}
		});
		jTetrisButtonExportarProjetoJava.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jTetrisButtonExportarProjetoJava.setIcon(new ImageIcon(JDialogExportar.class.getResource("/br/com/analisasoftware/tetris/imagem/botao_java.png")));
		jTetrisButtonExportarProjetoJava.setBounds(10, 63, 224, 48);
		tetrisPanel.add(jTetrisButtonExportarProjetoJava);
		
		jTetrisButtonExportarExecutavelJar = new JTetrisButton("Exportar Execut\u00E1vel JAR");
		jTetrisButtonExportarExecutavelJar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Define retorno para Exportar Executável Jar e fecha a janela
				retorno="6";
				dispose();
			}
		});
		jTetrisButtonExportarExecutavelJar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jTetrisButtonExportarExecutavelJar.setIcon(new ImageIcon(JDialogExportar.class.getResource("/br/com/analisasoftware/tetris/imagem/botao_java.png")));
		jTetrisButtonExportarExecutavelJar.setBounds(10, 116, 224, 48);
		tetrisPanel.add(jTetrisButtonExportarExecutavelJar);
		
		jTetrisButtonFechar = new JTetrisButton("Fechar");
		jTetrisButtonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Fecha a janela
				dispose();
			}
		});
		jTetrisButtonFechar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jTetrisButtonFechar.setIcon(new ImageIcon(JDialogExportar.class.getResource("/br/com/analisasoftware/tetris/imagem/sair.png")));
		jTetrisButtonFechar.setBounds(125, 191, 109, 23);
		tetrisPanel.add(jTetrisButtonFechar);
		setTitle("Exportar Projeto");
		setSize(250, 254);
		setResizable(false);
		setLocationRelativeTo(jFramePrincipal);

	}

	//Carrega idioma nos componentes
	public void carregarIdioma(){
		setTitle(Idioma.getTraducao("tetris.export_project", jFramePrincipal));
		jTetrisButtonExportarProjetoJava.setText(Idioma.getTraducao("tetris.export_java_project", jFramePrincipal));
		jTetrisButtonExportarProjetoTetris.setText(Idioma.getTraducao("tetris.export_tetris_project", jFramePrincipal));
		jTetrisButtonFechar.setText(Idioma.getTraducao("tetris.close", jFramePrincipal));
		jTetrisButtonExportarExecutavelJar.setText(Idioma.getTraducao("tetris.export_runnable_jar", jFramePrincipal));
	}
	//Inicializa e exibe a janela modal, retornando uma string
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
