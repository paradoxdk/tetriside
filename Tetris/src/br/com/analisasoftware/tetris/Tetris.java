/*
 *Classe principal do projeto, contendo o método main
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
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
		
		//Tenta modificar a aparência do sistema
		try{
			//Verifica se o SystemLookAndFeel chama o CrossPlatformLookAndFeel
			if(UIManager.getSystemLookAndFeelClassName().equals(UIManager.getCrossPlatformLookAndFeelClassName())){
				//Caso sim, modifica a aparência para a NimbusLookAndFeel
				UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
				//Torna os objetos JDesktopPanes com aparência padrão, para remover a imagem de background que o NimbusLookAndFeel define
				UIManager.put("DesktopPaneUI","javax.swing.plaf.basic.BasicDesktopPaneUI");
			}else{
				//Caso não, utiliza a aparência padrão do Sistema Operacional
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
