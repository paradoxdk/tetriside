/*
 *Implementa a atualizacao do TetrisIDE através de uma classe Runnable
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 24 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.analisasoftware.tetris.visao.janelas.JDialogMensagem;
import br.com.analisasoftware.tetris.visao.janelas.JFramePrincipal;

public class Atualizacao implements Runnable{
	//Variável que aponta para a instância da JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Ao inicializar a instância, o construtor inicia uma nova Thread desta classe
	public Atualizacao(JFramePrincipal jFramePrincipal){
		this.jFramePrincipal = jFramePrincipal;
		new Thread(this).start();
	}

	//Reescreve o método run, que será executado em uma nova Thread
	@Override
	public void run() {
		// TODO Auto-generated method stub
		iniciarAtualizacao();
	}
	
	//Inicia a atualizacao
	public void iniciarAtualizacao(){
		//Verifica se ha atualizacao
		if(verificarAtualizacao()){
			System.out.println("Update starts!");
			
			//Enquanto o arquivo update nao estiver corretamente baixado...
			while(!verificarArquivoUpdate()){
				try{
					Thread.sleep(5000);
				}catch(Exception e){
					
				}
				//Baixa arquivo update.jar
				baixarArquivoUpdate();
			}
			
			//Atualiza o TetrisIDE
			if(atualizar()){
				try {
					Runtime.getRuntime().exec("java -jar "+System.getProperty("user.home")+"/TetrisWorkspace/update/TetrisUpdate.jar");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					try {
						Runtime.getRuntime().exec("java -jar \""+System.getProperty("user.home")+"/TetrisWorkspace/update/TetrisUpdate.jar\"");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				//Finaliza a aplicação
				System.exit(0);
			}
		}
		System.out.println("Updated!");
	}

	//Verifica se ha atualizacao, retornando true se positivo, e false, se negativo
	public boolean verificarAtualizacao(){
		//Variável de retorno
		boolean retorno = false;
		//Variáveis para a comparação entre a versão atual do sistema e a versão disponível para atualização
		String versaoLocal = Desenvolvedor.VERSAO;
		String versaoRemota = "";

		//Verifica se ja verificou atualizacao hoje
		if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/versao.jar")){
			File fileVersao = new File(System.getProperty("user.home")+"/TetrisWorkspace/versao.jar");
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			if(dateFormat.format(fileVersao.lastModified()).equals(dateFormat.format(new Date()))){
				return false;
			}
		}
		
		//Baixa o arquivo de versao para comparar
		if(Arquivo.baixarArquivo("http://tetris.analisasoftware.com.br/downloads/versao/versao.jar", System.getProperty("user.home")+"/TetrisWorkspace/versao.jar")){
			if(Arquivo.extrairArquivoJar(System.getProperty("user.home")+"/TetrisWorkspace/versao.jar", System.getProperty("user.home")+"/TetrisWorkspace/")){
				versaoRemota = Arquivo.lerArquivo(System.getProperty("user.home")+"/TetrisWorkspace/versao.txt");
			}else{
				return false;
			}
		}else{
			return false;
		}

		System.out.println(versaoLocal+"|"+versaoRemota);
		if(!versaoRemota.equals(versaoLocal)){
			retorno=true;
		}

		return retorno;
	}

	//Verifica se arquivo update.jar ja foi baixado, retornando true, se positivo, e false, se negativo
	public boolean verificarArquivoUpdate(){
		//Variável de retorno
		boolean retorno = false;
		//Verifica se há um arquivo update.jar baixado
		String arquivoUpdate = System.getProperty("user.home")+"/TetrisWorkspace/update.jar";
		if(Arquivo.verificarCaminho(arquivoUpdate)){
			try {
				//Baixa arquivo update.jar.md5 para verificar a integridade do arquivo update.jar
				if(Arquivo.baixarArquivo("http://tetris.analisasoftware.com.br/downloads/versao/update.jar.md5", System.getProperty("user.home")+"/TetrisWorkspace/update.jar.md5")){
					//Verifica se o hash do arquivo update.jar é igual ao hash baixado no arquivo update.jar.md5
					String hashArquivo = Arquivo.geraHash(new File(arquivoUpdate));
					String hashCorreto = Arquivo.lerArquivo(System.getProperty("user.home")+"/TetrisWorkspace/update.jar.md5");
					if(hashArquivo.equals(hashCorreto)){
						retorno=true;
					}
				}else{
					retorno = false;
				}
				
			} catch (NoSuchAlgorithmException | FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}else{
			retorno = false;
		}

		return retorno;
	}
	
	//Baixa o arquivo update e retorna true, se sucesso, e false, caso contrário
	public boolean baixarArquivoUpdate(){
		//Variável de retorno
		boolean retorno = false;
		//Baixa arquivo update.jar
		if(Arquivo.baixarArquivo("http://tetris.analisasoftware.com.br/downloads/versao/update.jar", System.getProperty("user.home")+"/TetrisWorkspace/update.jar")){
			retorno=true;
		}
		
		return retorno;
	}

	//Atualiza o sistema e retorna true, caso sucesso, e false, caso contrário
	public boolean atualizar(){
		//Variável de retorno
		boolean retorno=false;
		
		//Define variáveis com o caminho do diretório update e do arquivo update.jar
		String pastaUpdate = System.getProperty("user.home")+"/TetrisWorkspace/update/";
		String arquivoUpdate = System.getProperty("user.home")+"/TetrisWorkspace/update.jar";
		
		//Cria pasta, se nao existir
		if(Arquivo.verificarCaminho(pastaUpdate)){
			Arquivo.apagarDiretorio(new File(pastaUpdate));
		}
		
		Arquivo.criarPasta(pastaUpdate);
		
		//Extrai arquivo update do Jar
		if(Arquivo.extrairArquivoJar(arquivoUpdate, pastaUpdate)){
			if(JDialogMensagem.exibirMensagem("Perguntar", Idioma.getTraducao("tetris.message_update", jFramePrincipal))==1){
				retorno = true;
			}
		}
		
		return retorno;
	}

}
