/*
 *Painel de Logs de compila��o dos projetos
 *Por: David de Almeida Bezerra J�nior
 *Vers�o: 1.3
 *Data: 25 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.componentes;

import componentes.visao.JTetrisPanel;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import br.com.analisasoftware.tetris.modelo.Arquivo;
import br.com.analisasoftware.tetris.visao.janelas.JFramePrincipal;

import javax.swing.JLabel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

@SuppressWarnings("serial")
public class JTetrisPanelLog extends JTetrisPanel {
	//Aponta para a inst�ncia do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//�rea de texto que exibe o log
	private JTextArea jTextAreaLog;

	//Construindo o objeto
	public JTetrisPanelLog(JFramePrincipal jFramePrincipal) {
		super();
		setSize(new Dimension(550, 350));
		this.jFramePrincipal = jFramePrincipal;
		
		setVisible(false);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 32, 530, 308);
		add(scrollPane);
		
		jTextAreaLog = new JTextArea();
		jTextAreaLog.addFocusListener(new FocusAdapter() {
			//Representa evento de perda de foco do componente
			@Override
			public void focusLost(FocusEvent arg0) {
				//Define o ambiente invis�vel
				setVisible(false);
			}
		});
		jTextAreaLog.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		jTextAreaLog.setEditable(false);
		scrollPane.setViewportView(jTextAreaLog);
		
		JLabel lblLog = new JLabel("Log");
		lblLog.setBounds(12, 12, 64, 14);
		add(lblLog);
	}
	
	//Define um texto ao log
	public void setText(String text){
		//Caso o texto seja vazio, o componente fica invis�vel, caso contr�rio, fica vis�vel
		if(text.equals("")){
			setVisible(false);
		}else{
			setVisible(true);
		}
		jTextAreaLog.setText(text);
	}
	
	public String getText(){
		return jTextAreaLog.getText();
	}
	//Reescreve m�todo setVisible para requerer foco � �rea de texto
	@Override
	public void setVisible(boolean visible){
		super.setVisible(visible);
		if(jTextAreaLog!=null){
			jTextAreaLog.requestFocus();
		}
	}
	//Carrega o log
	public void carregarLog(){
		//Vari�vel de aux�lio
		String text="";
		//Verifica se o arquivo de log existe
		if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/logcomp.dabj")){
			//L� o arquivo em forma de lista
			ArrayList<String> arrayListArquivo = Arquivo.lerArquivoVetor(System.getProperty("user.home")+"/TetrisWorkspace/logcomp.dabj");
			//Adiciona as linhas � vari�vel
			for (int i = 0; i < arrayListArquivo.size(); i++) {
				if(i!=0){
					text=text+"\n";
				}
				text=text+arrayListArquivo.get(i);
			}
		}
		//Modifica o texto da �rea de texto
		setText(text);
	}

	//Getters and Setters
	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}
}
