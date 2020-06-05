/*
 *Diálogo de mensagens do TetrisIDE
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 27 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.janelas;

import javax.swing.*;

import componentes.visao.JTetrisButton;
import componentes.visao.JTetrisPanel;

import java.awt.*;
import java.awt.event.*;


@SuppressWarnings("serial")
public class JDialogMensagem extends JDialog implements ActionListener{
	//Variável de retorno
	private int i = 0;
	//Componentes gráficos
	private JLabel imgmsg = new JLabel();
	private JEditorPane jEditorPaneTexto = null;
	private JTetrisButton jButtonYes = new JTetrisButton("Yes");
	private JTetrisButton jButtonNo = new JTetrisButton("No");

	public JDialogMensagem(){
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/br/com/analisasoftware/tetris/imagem/icone.png")));
		setTitle("Mensagem");
		
		JTetrisPanel jTetrisPanelContent = new JTetrisPanel(new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/painelmsg.png")));
		setContentPane(jTetrisPanelContent);
		
		getContentPane().setLayout(null);
		setSize(465, 222);
		setUndecorated(true);
		setResizable(false);
		setAlwaysOnTop(true);
		
		jEditorPaneTexto = new JEditorPane();
		jEditorPaneTexto.setContentType("text/html");
		jEditorPaneTexto.setEditable(false);
		jEditorPaneTexto.setFont(new Font("Dialog", Font.BOLD, 12));
		jEditorPaneTexto.setOpaque(false);
		
		getContentPane().setLayout(null);
		
		getContentPane().add(jButtonYes);
		getContentPane().add(jButtonNo);
		getContentPane().add(imgmsg);

		jButtonNo.setLocation(290, 160);
		jButtonYes.setLocation(180, 160);

		jButtonYes.setIcon(new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/sim.png")));
		jButtonNo.setIcon(new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/sair.png")));

		jButtonNo.setMnemonic(KeyEvent.VK_N);

		jButtonNo.setSize(100, 30);
		jButtonYes.setSize(100, 30);
		
		jButtonYes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonNo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	
		jEditorPaneTexto.setSize(330, 100);
		
		JScrollPane jScrollPane = new JScrollPane(jEditorPaneTexto);
		jScrollPane.setSize(330, 100);
		jScrollPane.setLocation(130, 40);
		jScrollPane.setOpaque(false);
		jScrollPane.getViewport().setOpaque(false);
		jScrollPane.setBorder(null);
		
		getContentPane().add(jScrollPane);

		imgmsg.setLocation(5, 30);
		
		//Corrige bug grafico que ocorre com janelas dialog no gnome
		setVisible(true);
		setVisible(false);
		setModal(true);
		setLocationRelativeTo(null);
	}
	//Inicializa e exibe a janela de acordo com o tipo definido por parâmetro
	public int init(String tipo,  String msg){
		jEditorPaneTexto.setText("<html><head><title></title></head><body><b>"+msg+"</b></body></html>");
		jEditorPaneTexto.setFocusable(false);

		if(tipo.equals("Informar")){
			jButtonYes.setVisible(true);
			jButtonYes.setText("Ok");
			jButtonYes.setMnemonic(KeyEvent.VK_O);
			jButtonNo.setVisible(false);
			imgmsg.setSize(105, 105);
			imgmsg.setIcon(new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/informacao.png")));
		}else if(tipo.equals("Sem Botao")){
			jButtonYes.setVisible(false);
			jButtonYes.setText("Ok");
			jButtonYes.setMnemonic(KeyEvent.VK_O);
			jButtonNo.setVisible(false);
			imgmsg.setSize(105, 105);
			imgmsg.setIcon(new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/informacao.png")));
		} else if(tipo.equals("Perigo")){
			jButtonYes.setVisible(true);
			jButtonYes.setText("Ok");
			jButtonYes.setMnemonic(KeyEvent.VK_O);
			jButtonNo.setVisible(false);
			imgmsg.setSize(105, 105);
			imgmsg.setIcon(new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/cuidado.png")));
		} else if(tipo.equals("Perguntar")){
			jButtonYes.setVisible(true);
			jButtonYes.setText("Yes");
			jButtonYes.setMnemonic(KeyEvent.VK_S);
			jButtonNo.setVisible(true);
			imgmsg.setSize(105, 105);
			imgmsg.setIcon(new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/informacao.png")));
		} else if(tipo.equals("Erro")){
			jButtonYes.setVisible(true);
			jButtonYes.setText("Ok");
			jButtonYes.setMnemonic(KeyEvent.VK_O);
			jButtonNo.setVisible(false);
			imgmsg.setSize(105, 105);
			imgmsg.setIcon(new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/erro.png")));
		}				

		addEventos();
		
		setVisible(true);
		return i;
	}
	//Adiciona Eventos
	public void addEventos(){
		jButtonYes.addActionListener(this);
		jButtonNo.addActionListener(this);

		//Adicionando Listener para teclas de acao
		Component[] componentes = getContentPane().getComponents();
		KeyAdapter keyAdapter = new KeyAdapter(){
			public void keyReleased(KeyEvent arg0){
				if((arg0.getKeyCode() == KeyEvent.VK_LEFT) || (arg0.getKeyCode() == KeyEvent.VK_RIGHT)){
					if(jButtonNo.hasFocus()){
						jButtonYes.requestFocus();
					}else{
						jButtonNo.requestFocus();
					}
				}else if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
					dispose();
				}
			}
		};
		for (int i = 0; i < componentes.length; i++) {
			componentes[i].addKeyListener(keyAdapter);
		}
	}
	//De acordo com o pressionamento dos botões, define retorno
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == jButtonYes){
			i = 1;
			setVisible(false);
			dispose();
		}else if(e.getSource() == jButtonNo){
			i = 0;
			setVisible(false);
			dispose();
		}
	}
	//Método estático que cria o diálogo de mensagem
	public static int exibirMensagem(String tipo, String msg){
		JDialogMensagem jDialogMensagem = new JDialogMensagem();
		return jDialogMensagem.init(tipo, msg);

	}

}