/*
 *Tela de Splash do TetrisIDE
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 27 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.janelas;

import javax.swing.JDialog;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFileChooser;
import javax.swing.Timer;

import br.com.analisasoftware.tetris.modelo.Arquivo;
import br.com.analisasoftware.tetris.modelo.Desenvolvedor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Color;

import javax.swing.SwingConstants;

import componentes.visao.JTetrisPanel;

import java.awt.Font;

@SuppressWarnings("serial")
public class JDialogSplash extends JDialog {
	//Timer que define o tempo de amostragem da janela
	private Timer timer;
	
	public JDialogSplash() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/br/com/analisasoftware/tetris/imagem/icone.png")));
		
		JTetrisPanel jTetrisPanelSplash = new JTetrisPanel(new ImageIcon(JDialogSplash.class.getResource("/br/com/analisasoftware/tetris/imagem/splashtetris.png")));
		setContentPane(jTetrisPanelSplash);
		
		setSize(new Dimension(600, 340));
		
		setTitle("TetrisIDE");
		
		setResizable(false);
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		getContentPane().setLayout(null);
		
		JLabel lblVer = new JLabel("ver.: "+Desenvolvedor.VERSAO);
		lblVer.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblVer.setForeground(Color.WHITE);
		lblVer.setBounds(36, 23, 246, 14);
		getContentPane().add(lblVer);
		
		JLabel lblIde = new JLabel("IDE");
		lblIde.setForeground(Color.WHITE);
		lblIde.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblIde.setBounds(413, 207, 70, 24);
		getContentPane().add(lblIde);
		
		JLabel lblCopyrightCDavid = new JLabel("CopyRight \u00A9 2020 David de Almeida Bezerra J\u00FAnior");
		lblCopyrightCDavid.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCopyrightCDavid.setForeground(Color.WHITE);
		lblCopyrightCDavid.setHorizontalAlignment(SwingConstants.CENTER);
		lblCopyrightCDavid.setBounds(10, 305, 580, 24);
		getContentPane().add(lblCopyrightCDavid);
		
		timer = new Timer(2000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Para o timer
				pararTimer();
				//Verifica se o sistema está integro
				verificarIntegridadeDoSistema();
				//Fecha janela
				dispose();
			}
		});
		//Inicia o timer
		timer.start();
		
		setUndecorated(true);
		
		//Corrige bug grafico que ocorre com janelas dialog no gnome
		setVisible(true);
		setVisible(false);
		setModal(true);
		setLocationRelativeTo(null);
		
	}
	//Exibe janela
	public void init(){
		setVisible(true);
	}
	//Verifica integridade do sistema
	public void verificarIntegridadeDoSistema(){
		try {
			//Cria workspace, caso não exista
			File fileWorkspace = new File(System.getProperty("user.home")+"/TetrisWorkspace/");
			fileWorkspace.mkdir(); fileWorkspace.mkdirs();
			//Verifica arquivo de configurações e cria, caso não exista
			if(!Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/conf")){
				Arquivo.gravarArquivo(System.getProperty("user.home")+"/TetrisWorkspace/conf", "200\n"
						+ "200\n"
						+ "150\n"
						+ "300\n"
						+ "200\n"
						+ "SystemLookAndFeel\n"
						+ "En-US\n");
			}
			
			
			//Verifica o JDK. Caso nao ache, da a opcao de o usuario selecionar o local
			try{
				Runtime.getRuntime().exec("javac -version");
			}catch(Exception exc){
				try{
					Runtime.getRuntime().exec(System.getProperty("user.home")+"/TetrisWorkspace/jdk/bin/javac -version");
				}catch(Exception exc2){
					//Pergunta se deseja selecionaro local de instalação do JDK para copiar para o workspace
					if(JDialogMensagem.exibirMensagem("Perguntar", "Java Development Kit (JDK) not found!<br/>Do you want to select the JDK installed location?")==1){
						//Seleciona diretório
						JFileChooser jFileChooser = new JFileChooser();
						jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						if(jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
							try{
								//Verifica se é o diretório do JDK
								Runtime.getRuntime().exec(jFileChooser.getSelectedFile().getPath()+"/bin/javac -version");
								//Copia o diretório do JDK
								if(!Arquivo.copiarPasta(jFileChooser.getSelectedFile().getPath()+"/", System.getProperty("user.home")+"/TetrisWorkspace/jdk/")){
									JDialogMensagem.exibirMensagem("Erro", "Error copying JDK!<br/>Tetris IDE will exit!");
									System.exit(0);
								}
							}catch(Exception exc3){
								JDialogMensagem.exibirMensagem("Erro", "There is no JDK installed on this directory!<br/>Tetris IDE will exit!");
								System.exit(0);
							}
						}
					}
				}
			}
		} catch (Exception exc) {
			// TODO: handle exception
			JDialogMensagem.exibirMensagem("Erro", "Error:<br/>"+exc.getMessage());
		}
	}
	//Para o timer
	public void pararTimer(){
		timer.stop();
	}
	
}


