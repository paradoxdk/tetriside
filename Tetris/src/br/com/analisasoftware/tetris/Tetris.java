/*
 *Classe principal do projeto, contendo o m�todo main
 *Por: David de Almeida Bezerra J�nior
 *Vers�o: 1.3
 *Data: 23 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */

package br.com.analisasoftware.tetris;

import javax.swing.UIManager;

import br.com.analisasoftware.tetris.visao.janelas.JDialogSplash;
import br.com.analisasoftware.tetris.visao.janelas.JFramePrincipal;

public class Tetris {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Tenta modificar a apar�ncia do sistema
		try{
			//Verifica se o SystemLookAndFeel chama o CrossPlatformLookAndFeel
			if(UIManager.getSystemLookAndFeelClassName().equals(UIManager.getCrossPlatformLookAndFeelClassName())){
				//Caso sim, modifica a apar�ncia para a NimbusLookAndFeel
				UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
				//Torna os objetos JDesktopPanes com apar�ncia padr�o, para remover a imagem de background que o NimbusLookAndFeel define
				UIManager.put("DesktopPaneUI","javax.swing.plaf.basic.BasicDesktopPaneUI");
			}else{
				//Caso n�o, utiliza a apar�ncia padr�o do Sistema Operacional
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
		}catch(Exception exc){
			
		}
		
		//Carregando driver do banco de dados
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception exc) {
			// TODO: handle exception
			exc.printStackTrace();
		}
		
		//Iniciando a janela de Splash
		JDialogSplash jDialogSplash = new JDialogSplash();
		jDialogSplash.init();
		
		//Iniciando a janela principal do sistema
		JFramePrincipal jFramePrincipal = new JFramePrincipal();
		jFramePrincipal.init();
	}

}
