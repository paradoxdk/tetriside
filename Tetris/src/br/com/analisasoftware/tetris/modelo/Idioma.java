/*
 *Fornecimento de Idioma do software
 *Por: David de Almeida Bezerra J�nior
 *Vers�o: 1.3
 *Data: 24 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.modelo;

import java.util.Properties;

import br.com.analisasoftware.tetris.visao.janelas.JDialogMensagem;
import br.com.analisasoftware.tetris.visao.janelas.JFramePrincipal;

public class Idioma {
	//Retorna traducao
	public static String getTraducao(String propriedade, JFramePrincipal jFramePrincipal){
		String retorno="";
		try{
			//L� propriedade passada por par�metro no arquivo de idioma e retorna seu conte�do
			Properties properties = new Properties();
			properties.load(jFramePrincipal.getClass().getResourceAsStream("/br/com/analisasoftware/tetris/language/"+jFramePrincipal.getIdioma()+".properties"));
			retorno = properties.getProperty(propriedade);
		}catch(Exception exc){
			JDialogMensagem.exibirMensagem("Erro", ""+exc.getMessage());
		}
		
		return retorno;
	}
	
	//Retorna idiomas
	public static String[] getIdiomas(JFramePrincipal jFramePrincipal){
		String retorno="";
		try{
			//L� lista de idiomas presente no arquivo de propriedades e retorna seu conte�do
			Properties properties = new Properties();
			properties.load(jFramePrincipal.getClass().getResourceAsStream("/br/com/analisasoftware/tetris/language/language.properties"));
			retorno = properties.getProperty("tetris.languages");
		}catch(Exception exc){
			JDialogMensagem.exibirMensagem("Erro", ""+exc.getMessage());
		}
		
		return retorno.split(",");
	}
}
