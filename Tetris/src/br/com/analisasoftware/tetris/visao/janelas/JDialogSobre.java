/*
 *Exibe informações de versão sobre o TetrisIDE
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 27 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.janelas;

import javax.swing.JDialog;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Font;
import java.awt.Color;

import javax.swing.SwingConstants;

import componentes.visao.JTetrisButton;
import componentes.visao.JTetrisPanel;

import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.border.BevelBorder;

import br.com.analisasoftware.tetris.modelo.Desenvolvedor;
import br.com.analisasoftware.tetris.modelo.Idioma;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

@SuppressWarnings("serial")
public class JDialogSobre extends JDialog {
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Componentes gráficos
	private JLabel jLabelDesenvolvidoPor;
	private JLabel jLabelContato;
	private JLabel jLabelTodosOsDireitos;
	private JTetrisButton jButtonOk;
	private JLabel jLabelAprendaOnline;
	private JLabel jLabelGPL;
	
	public JDialogSobre(JFramePrincipal jFramePrincipal){
		this.jFramePrincipal = jFramePrincipal;
		setSize(new Dimension(600, 340));
		setTitle("Sobre o TetrisIDE");
		setUndecorated(true);
		setResizable(false);
		JTetrisPanel jTetrisPanelContent = new JTetrisPanel(new ImageIcon(JDialogSobre.class.getResource("/br/com/analisasoftware/tetris/imagem/sobrefundo.png")));
		setContentPane(jTetrisPanelContent);
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogSobre.class.getResource("/br/com/analisasoftware/tetris/imagem/icone.png")));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		jLabelDesenvolvidoPor = new JLabel("Desenvolvido por");
		jLabelDesenvolvidoPor.setForeground(Color.WHITE);
		jLabelDesenvolvidoPor.setFont(new Font("Serif", Font.BOLD, 14));
		jLabelDesenvolvidoPor.setBounds(135, 123, 279, 19);
		getContentPane().add(jLabelDesenvolvidoPor);
		
		JLabel lblDavidDeAlmeida = new JLabel("David de Almeida Bezerra J\u00FAnior");
		lblDavidDeAlmeida.setForeground(Color.BLUE);
		lblDavidDeAlmeida.setFont(new Font("Serif", Font.BOLD, 14));
		lblDavidDeAlmeida.setBounds(193, 152, 279, 19);
		getContentPane().add(lblDavidDeAlmeida);
		
		JLabel lblVer = new JLabel("ver.: "+Desenvolvedor.VERSAO);
		lblVer.setForeground(Color.BLUE);
		lblVer.setFont(new Font("Serif", Font.BOLD, 11));
		lblVer.setBounds(368, 74, 80, 14);
		getContentPane().add(lblVer);
		
		JLabel label = new JLabel("IDE");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Tahoma", Font.BOLD, 10));
		label.setBounds(208, 88, 70, 24);
		getContentPane().add(label);
		
		jLabelContato = new JLabel("Contato");
		jLabelContato.setForeground(Color.WHITE);
		jLabelContato.setFont(new Font("Serif", Font.BOLD, 14));
		jLabelContato.setBounds(135, 174, 279, 19);
		getContentPane().add(jLabelContato);
		
		JLabel lblDavidbezerrajrhotmailcom = new JLabel("davidbezerrajr@hotmail.com");
		lblDavidbezerrajrhotmailcom.setForeground(Color.BLUE);
		lblDavidbezerrajrhotmailcom.setFont(new Font("Serif", Font.BOLD, 14));
		lblDavidbezerrajrhotmailcom.setBounds(193, 204, 279, 19);
		getContentPane().add(lblDavidbezerrajrhotmailcom);
		
		JLabel lblCopyright = new JLabel("CopyRight \u00A9 2020");
		lblCopyright.setHorizontalAlignment(SwingConstants.CENTER);
		lblCopyright.setForeground(Color.WHITE);
		lblCopyright.setFont(new Font("Serif", Font.BOLD, 14));
		lblCopyright.setBounds(10, 310, 580, 19);
		getContentPane().add(lblCopyright);
		
		jLabelTodosOsDireitos = new JLabel("Licensed by GNU General Public License");
		jLabelTodosOsDireitos.setFont(new Font("Dialog", Font.BOLD, 9));
		jLabelTodosOsDireitos.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelTodosOsDireitos.setBounds(10, 230, 580, 14);
		getContentPane().add(jLabelTodosOsDireitos);
		
		jButtonOk = new JTetrisButton("OK");
		jButtonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Fecha janela
				dispose();
			}
		});
		jButtonOk.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonOk.setIcon(new ImageIcon(JDialogSobre.class.getResource("/br/com/analisasoftware/tetris/imagem/sim.png")));
		jButtonOk.setBounds(480, 294, 89, 23);
		getContentPane().add(jButtonOk);
		
		jLabelAprendaOnline = new JLabel("Aprenda Online");
		jLabelAprendaOnline.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		jLabelAprendaOnline.setOpaque(true);
		jLabelAprendaOnline.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		jLabelAprendaOnline.setBackground(Color.BLUE);
		jLabelAprendaOnline.setFont(new Font("Dialog", Font.BOLD, 14));
		jLabelAprendaOnline.setForeground(Color.WHITE);
		jLabelAprendaOnline.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelAprendaOnline.setBounds(220, 267, 157, 32);
		getContentPane().add(jLabelAprendaOnline);
		
		jLabelGPL = new JLabel("https://www.gnu.org/licenses/gpl-3.0.txt");
		jLabelGPL.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jLabelGPL.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelGPL.setFont(new Font("Dialog", Font.BOLD, 9));
		jLabelGPL.setBounds(10, 245, 580, 14);
		jTetrisPanelContent.add(jLabelGPL);
		
		setLocationRelativeTo(jFramePrincipal);
		
		//Corrige bug grafico que ocorre com janelas dialog no gnome
		setVisible(true);
		setVisible(false);
		setModal(true);
		setLocationRelativeTo(null);
	}
	
	//Carrega idioma nos componentes
	public void carregarIdioma(){
		setTitle(Idioma.getTraducao("tetris.about", jFramePrincipal)+" TetrisIDE");
		jLabelContato.setText(Idioma.getTraducao("tetris.contact", jFramePrincipal));;
		jLabelDesenvolvidoPor.setText(Idioma.getTraducao("tetris.developed_by", jFramePrincipal));
		jButtonOk.setText(Idioma.getTraducao("tetris.ok", jFramePrincipal));
		jLabelAprendaOnline.setText(Idioma.getTraducao("tetris.learn_online", jFramePrincipal));
		
		jLabelAprendaOnline.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					//Ao clicar o mouse, abre página de Aprenda Online no site do TetrisIDE
					Desktop.getDesktop().browse( new URI(Idioma.getTraducao("tetris.learn_uri", jFramePrincipal)));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		});
		
		jLabelGPL.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					//Ao clicar o mouse, abre página com o texto da Licença GPL
					Desktop.getDesktop().browse( new URI("https://www.gnu.org/licenses/gpl-3.0.txt"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		});
	}
	//Inicializa e exibe janela
	public void init(){
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
}
