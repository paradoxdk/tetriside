/*
 *Classe utilizada para manipular arquivos de texto e bin�rios
 *Por: David de Almeida Bezerra J�nior
 *Vers�o: 1.3
 *Data: 23 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */

package br.com.analisasoftware.tetris.modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import br.com.analisasoftware.tetris.visao.componentes.JTetrisPanelBarraLateralDireita;
import br.com.analisasoftware.tetris.visao.componentes.JTetrisPanelBarraLateralEsquerda;
import br.com.analisasoftware.tetris.visao.componentes.JTetrisSeparator;
import br.com.analisasoftware.tetris.visao.janelas.JDialogMensagem;
import br.com.analisasoftware.tetris.visao.janelas.JFramePrincipal;
import componentes.modelo.bancodedados.BancoDeDados;
import tetris.modelo.componentes.Janela;

public class Arquivo {
	//Cria diret�rio referente a um caminho espec�fico e retorna true, caso tenha sucesso, ou false, caso contr�rio 
	public static boolean criarPasta(String caminho){
		try {
			//Carrega o caminho desejado
			File fileCaminho = new File(caminho);
			//Cria diret�rios
			fileCaminho.mkdir();
			fileCaminho.mkdirs();

			return true;
		} catch (Exception exc) {
			// TODO: handle exception
			JDialogMensagem.exibirMensagem("Erro", "Error:<br/>"+exc.getMessage());
			return false;
		}
	}

	//Verifica se um determinado caminho ou arquivo existe. Caso sim, retorna true, caso n�o, retorna false
	public static boolean verificarCaminho(String caminho){
		try{
			//Carrega o caminho ou arquivo desejado
			File fileCaminho = new File(caminho);
			//Verifica a exist�ncia
			if(fileCaminho.exists()){
				return true;
			}else{
				return false;
			}
		}catch(Exception exc){
			JDialogMensagem.exibirMensagem("Erro", "Error:<br/>"+exc.getMessage());
			return false;
		}
	}

	//Renomea arquivo especificado e retorna true, caso tenha sucesso, e false, caso contr�rio
	public static boolean renomearArquivo(String arquivoVelho, String arquivoNovo){
		boolean retorno=false;
		try {
			//Carrega o arquivo desejado com o nome antigo
			File fileVelho = new File(arquivoVelho);
			//Renomea para com o novo nome
			fileVelho.renameTo(new File(arquivoNovo));
			retorno=true;
		} catch (Exception exc) {
			// TODO: handle exception
			JDialogMensagem.exibirMensagem("Erro", "Error:<br/>"+exc.getMessage());
		}
		return retorno;
	}

	//Transforma uma String contendo um endere�o de URL para String, removendo o termo file:/ do in�cio
	public static String urlToString(String s){
		//Verifica se a String s tem mais de 5 caracteres
		if(s.length() > 5){
			//Verifica se tem o termo file:/ na sua composi��o
			if((Character.toString(s.charAt(0))+Character.toString(s.charAt(1))+Character.toString(s.charAt(2))+Character.toString(s.charAt(3))+Character.toString(s.charAt(4))).equals("file:")){
				//String de retorno
				String r="";
				//Percorre o par�metro s e adiciona � String r
				for (int i = 5; i < s.length(); i++) {
					r=r+Character.toString(s.charAt(i));
				}

				//Tenta retornar a String r com codifica��o UTF-8
				try {
					return URLDecoder.decode(r, "UTF-8");
				}
				catch (Exception e) {
					//Caso n�o consiga, tenta retornar eliminando a codifica��o %20 da URL
					return r.replace("%20", " ");
				}

			}else{
				//Caso n�o seja uma URL, retorna a mesma String
				return s;
			}
		}else{
			//Caso n�o tenha mais de 5 caracteres, retorna a mesma String
			return s;
		}
	}

	//Copia arquivo e retorna true, caso sucesso, e false, caso contr�rio
	public static boolean copiarArquivo(String caminho1, String caminho2){
		try{		
			//Carregando vari�veis de Streaming
			FileInputStream is = new FileInputStream(caminho1);
			FileOutputStream os = new FileOutputStream(caminho2);

			// Cria channel na origem
			FileChannel oriChannel = is.getChannel();
			// Cria channel no destino
			FileChannel destChannel = os.getChannel();
			// Copia conte�do da origem no destino
			destChannel.transferFrom(oriChannel, 0, oriChannel.size());

			// Fecha channels
			is.close();
			os.close();

			oriChannel.close();
			destChannel.close();

			return true;
		}catch(Exception exc){
			//System.out.println(exc);
			return false;
		}
	}

	//Copia diret�rio, e retorna true, caso sucesso, e false, caso contr�rio
	public static boolean copiarPasta(String origem, String destino){
		//Vari�vel de retorno
		boolean retorno=false;
		try {
			//Carregando diret�rio e seus arquivos filhos
			File diretorio = new File(origem);
			File[] arquivos = diretorio.listFiles();

			//Cria diret�rios no destino
			criarPasta(destino);
			//Percorre os arquivos da origem
			for (int i = 0; i < arquivos.length; i++) {
				if(arquivos[i].isDirectory()){
					//Caso o elemento na posi��o i seja um diret�rio, cria no destino
					copiarPasta(origem+"/"+arquivos[i].getName()+"/", destino+"/"+arquivos[i].getName()+"/");
				}else{
					//Caso seja uma arquivo, copia para o destino
					copiarArquivo(origem+"/"+arquivos[i].getName(), destino+"/"+arquivos[i].getName());
				}
			}

			retorno=true;
		} catch (Exception exc) {
			// TODO: handle exception
			JDialogMensagem.exibirMensagem("Erro", "Error:<br/>"+exc.getMessage());
		}

		return retorno;
	}

	//Grava as janelas em disco
	public static boolean gravarJanelas(JFramePrincipal jFramePrincipal){
		//Vari�vel de retorno
		boolean retorno=false;
		//Aponta para o projeto aberno na inst�ncia da JFramePrincipal
		Projeto projeto = jFramePrincipal.getProjeto();
		try {
			//Aponta para a lista de janelas da inst�ncia do projeto aberto
			ArrayList<Janela> arrayLisJanelas = projeto.getArrayListJanelas();
			//Vari�vel de aux�lio para apontar para as janelas
			Janela janela = null;
			//Percorre a lista de janelas
			for (int i = 0; i < arrayLisJanelas.size(); i++) {
				//Aponta para a janela na posi��o i
				janela = arrayLisJanelas.get(i);

				//Caso a janela tenha sido alterada
				if(janela.isAlterado()){
					//Limpa os estados da janela (hist�rico de altera��o)
					janela.getArrayListEstadoJanela().clear();
					janela.setPosicaoEstadoJanela(-1);
					//Se a janela n�o estiver aberta na �rea de Trabalho, seta ela como n�o alterada
					if(jFramePrincipal.getjDesktopPaneAreaDeTrabalho().getJanela()!=null){
						if(!jFramePrincipal.getjDesktopPaneAreaDeTrabalho().getJanela().equals(janela)){
							janela.setAlterado(false);
						}
					}

					//Grava a janela no disco
					gravarJanela(projeto, janela);
				}
			}

			retorno = true;
		} catch (Exception exc) {
			// TODO: handle exception
			exc.printStackTrace();
			JDialogMensagem.exibirMensagem("Erro", "Error:<br/>"+exc.getMessage());
		}

		return retorno;
	}

	//Grava uma janela no disco
	public static boolean gravarJanela(Projeto projeto, Janela janela){
		//Vari�vel de retorno
		boolean retorno=false;
		try {
			//Carrega vari�veis de Streaming com o caminho de grava��o da janela
			FileOutputStream fileOutputStream = new FileOutputStream(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/janelas/"+janela.getNome()+".dabj");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			//Grava a janela no disco
			objectOutputStream.writeObject(janela);
			//Finaliza e fecha as vari�veis de Streaming
			objectOutputStream.flush();
			objectOutputStream.close();

			fileOutputStream.flush();
			fileOutputStream.close();

			retorno=true;
		} catch (Exception exc) {
			// TODO: handle exception
			exc.printStackTrace();
			JDialogMensagem.exibirMensagem("Erro", "Error:<br/>"+exc.getMessage());
		}
		return retorno;
	}

	//Gravando banco de dados em disco
	public static boolean gravarBancoDeDados(Projeto projeto){
		//Vari�vel de retorno
		boolean retorno=false;

		try {
			//Aponta para a inst�ncia do BancoDeDados do projeto aberto
			BancoDeDados bancoDeDados = projeto.getBancoDeDados();
			//Caso o BancoDeDados tenha sido alterado
			if(bancoDeDados.isAlterado()){
				//Seta ele como n�o alterado
				bancoDeDados.setAlterado(false);
				//Carrega as vari�veis de Streaming com o caminho de grava��o do BancoDeDados
				FileOutputStream fileOutputStream = new FileOutputStream(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/db/"+projeto.getNome()+".dabj");
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				//Grava o banco de dados no disco
				objectOutputStream.writeObject(bancoDeDados);
				//Finaliza e fecha as vari�veis de Streaming
				objectOutputStream.flush();
				objectOutputStream.close();

				fileOutputStream.flush();
				fileOutputStream.close();
			}


			retorno = true;
		} catch (Exception exc) {
			// TODO: handle exception
			exc.printStackTrace();
			JDialogMensagem.exibirMensagem("Erro", "Error:<br/>"+exc.getMessage());
		}

		return retorno;
	}

	//L� banco de dados do disco para a memoria
	public static BancoDeDados lerBancoDeDados(String nomeProjeto){
		//Cria vari�vel que ser� preenchida
		BancoDeDados bancoDeDados = null;
		try {
			//Verifica se o caminho do arquivo do BancoDeDados existe
			if (verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+nomeProjeto+"/db/"+nomeProjeto+".dabj")) {
				//Cria vari�veis de Streaming
				FileInputStream fileInputStream;
				ObjectInputStream objectInputStream;
				//Carrega vari�veis de Streaming com o caminho do arquivo do BancoDeDados do projeto
				fileInputStream = new FileInputStream(System.getProperty("user.home")+"/TetrisWorkspace/"+nomeProjeto+"/db/"+nomeProjeto+".dabj");
				objectInputStream = new ObjectInputStream(fileInputStream);
				//L� o arquivo e carrega a inst�ncia na mem�ria
				bancoDeDados = (BancoDeDados)objectInputStream.readObject();
				//Fecha as vari�veis de Streaming
				objectInputStream.close();
				fileInputStream.close();
			}

		} catch (Exception exc) {
			// TODO: handle exception
			JDialogMensagem.exibirMensagem("Erro", "Error:<br/>"+exc.getMessage());
			return null;
		}
		//Retorna a inst�ncia carregada do BancoDeDados
		return bancoDeDados;
	}

	//L� janelas do disco para a memoria
	public static ArrayList<Janela> lerJanelas(String nomeProjeto){
		//Cria vari�vel de lista de janelas
		ArrayList<Janela> arrayLisJanelas = new ArrayList<Janela>();
		try {
			//Lista as janelas gravadas do projeto
			String[] janelas = listarArquivos(System.getProperty("user.home")+"/TetrisWorkspace/"+nomeProjeto+"/janelas/");
			//Cria vari�veis de Streaming
			FileInputStream fileInputStream;
			ObjectInputStream objectInputStream;
			//Percorre o vetor com o nome das janelas do projeto
			for (int i = 0; i < janelas.length; i++) {
				//Inicializa o Streaming da janela com o nome na posi��o i do vetor
				fileInputStream = new FileInputStream(System.getProperty("user.home")+"/TetrisWorkspace/"+nomeProjeto+"/janelas/"+janelas[i]);
				objectInputStream = new ObjectInputStream(fileInputStream);
				//Carrega a inst�ncia da janela e adiciona � lista
				Janela janela = (Janela)objectInputStream.readObject();
				janela.setAlterado(true);
				arrayLisJanelas.add(janela);
				//Fecha as vari�veis de Streaming
				objectInputStream.close();
				fileInputStream.close();
			}
		} catch (Exception exc) {
			// TODO: handle exception
			JDialogMensagem.exibirMensagem("Erro", "Error:<br/>"+exc.getMessage());
			return null;
		}
		//Retorna a lista de janelas carregadas
		return arrayLisJanelas;
	}

	//Grava arquivo no disco e retorna true, caso sucesso, e false, caso contr�rio
	public static boolean gravarArquivo(String arquivo, String conteudo){
		//Vari�vel de retorno
		boolean retorno=false;
		try {
			//Carrega vari�vel de Buffer para escrever no disco
			BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(arquivo)),"UTF-8"));
			//Quebra o conte�do em linhas e carrega em um vetor
			String[] vetorConteudo = conteudo.split("\n");
			//Percorre o vetorConteudo
			for (int i = 0; i < vetorConteudo.length; i++) {
				//Caso nao esteja na posi��o 0, gera uma quebra de linha antes de escrever o conte�do da linha da posi��o i
				if(i!=0){
					fileWriter.newLine();
				}
				//Escreve o conte�do da linha da posi��o i no arquivo
				fileWriter.write(vetorConteudo[i]);
			}

			//Finaliza e fecha a vari�vel de escrita
			fileWriter.flush();
			fileWriter.close();
			retorno=true;
		} catch (Exception exc) {
			// TODO: handle exception
			exc.printStackTrace();
			JDialogMensagem.exibirMensagem("Erro", "Error:<br/>"+exc.getMessage());
		}

		return retorno;
	}

	//L� arquivo do disco
	public static String lerArquivo(String caminho){
		//Vari�vel que abrigar� o conte�do do arquivo
		String conteudo="";
		try {
			//Carrega vari�vel de buffer de leitura
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(caminho), "UTF-8"));
			//Vari�vel auxiliar para ler cada linha do arquivo
			String linha="";
			//Percorre cada linha do arquivo lido
			while((linha=bufferedReader.readLine())!=null){
				//Caso a vari�vel conte�do n�o esteja vazia, adiciona uma quebra de linha
				if(!conteudo.equals("")){
					conteudo+="\n";
				}
				//Adiciona o conte�do da linha lida � vari�vel conteudo
				conteudo+=linha;
			}
			//Fecha vari�vel de leitura
			bufferedReader.close();
		} catch (Exception exc) {
			// TODO: handle exception
			JDialogMensagem.exibirMensagem("Erro", "Error:<br/>"+exc.getMessage());
			conteudo="";
		}
		//Retorna a vari�vel conteudo
		return conteudo;
	}
	//L� um arquivo e carrega em uma lista, separando suas linhas
	public static ArrayList<String> lerArquivoVetor(String caminho){
		//Cria lista vazia que abrigar� o conte�do do arquivo
		ArrayList<String> arrayListString = new ArrayList<String>();
		try {
			//Carrega vari�vel de buffer de leitura
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(caminho), "UTF-8"));
			//Vari�vel auxiliar para ler cada linha do arquivo
			String linha="";
			//Percorre cada linha do arquivo lido
			while((linha=bufferedReader.readLine())!=null){
				//Adiciona cada linha � lista
				arrayListString.add(linha);
			}
			//Fecha a vari�vel de leitura
			bufferedReader.close();
		} catch (Exception exc) {
			// TODO: handle exception
			JDialogMensagem.exibirMensagem("Erro", "Error:<br/>"+exc.getMessage());

		}

		//Retorna a lista com o conte�do do arquivo
		return arrayListString;
	}

	//Apaga um arquivo e retorna true, caso sucesso, e false, caso contr�rio
	public static boolean apagarArquivo(String arquivo){
		//Vari�vel de retorno
		boolean retorno=false;
		try {
			//Carrega o arquivo que ser� exclu�do
			File file = new File(arquivo);
			//Verifica se ele existe
			if(file.exists()){
				//Caso sim, exclui
				file.delete();
				retorno=true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			JDialogMensagem.exibirMensagem("Erro", "Error:<br/>"+e.getMessage());
		}
		return retorno;
	}

	//Apaga um diret�rio
	public static boolean apagarDiretorio(File dir) {
		//Verifica se o par�metro dir passado � um diret�rio
		if (dir.isDirectory()) {  
			//Carrega vetor com arquivos e diret�rios contidos no diret�rio a ser exclu�do
			String[] children = dir.list();  
			//Percorre o vetor
			for (int i=0; i<children.length; i++) {
				//Chama recursivamente este m�todo para apagar os arquivos e diret�rios dentro dos filhos
				boolean success = apagarDiretorio(new File(dir, children[i]));  
				if (!success) {  
					return false;  
				}  
			}  
		}  

		// Agora o diret�rio est� vazio, restando apenas delet�-lo.  
		return dir.delete();  
	}  

	//Cria um vetor com os arquivos de um diretorio
	public static String[] listarArquivos(String caminho){
		try {
			//Carrega o diret�rio passa do por par�metro
			File fileCaminho = new File(caminho);
			//Verifica se � um diret�rio
			if(fileCaminho.isDirectory()){
				//Vari�veis auxiliares
				int numeroArquivos=0, posicaoArquivo=0;
				//Percorre a lista de elementos do diret�rio, caso n�o seja um diret�rio, soma ao numeroArquivos
				for (int i = 0; i < fileCaminho.listFiles().length; i++) {
					if(!fileCaminho.listFiles()[i].isDirectory()){
						numeroArquivos++;
					}
				}

				//Cria vetor com o tamanho do numeroArquivos
				String[] arquivos= new String[numeroArquivos];

				//Percorre novamente a lista de elementos, caso n�o seja um diret�rio, adiciona ao vetor arquivos
				for (int i = 0; i < fileCaminho.listFiles().length; i++) {
					if(!fileCaminho.listFiles()[i].isDirectory()){
						arquivos[posicaoArquivo]=fileCaminho.listFiles()[i].getName();
						posicaoArquivo++;
					}
				}
				//Retorna o vetor arquivos
				return arquivos;
			}else{
				return null;
			}
		} catch (Exception exc) {
			// TODO: handle exception
			JDialogMensagem.exibirMensagem("Erro", "Error:<br/>"+exc.getMessage());
			return null;
		}
	}

	//Cria um vetor com as pastas de um diretorio
	public static String[] listarPastas(String caminho){
		try {
			//Carrega o diret�rio passado por par�metro
			File fileCaminho = new File(caminho);
			//Verifica se � um diret�rio
			if(fileCaminho.isDirectory()){
				//Vari�veis auxiliares
				int numeroPastas=0, posicaoArquivo=0;;
				//Percorre a lista de elementos do diret�rio, caso seja um diret�rio, soma ao numeroPastas
				for (int i = 0; i < fileCaminho.listFiles().length; i++) {
					if(fileCaminho.listFiles()[i].isDirectory()){
						numeroPastas++;
					}
				}

				//Cria vetor com tamanho numeroPastas
				String[] arquivos= new String[numeroPastas];

				//Percorre novamente a lista de elementos, caso seja um diret�rio, adiciona ao vetor arquivos
				for (int i = 0; i < fileCaminho.listFiles().length; i++) {
					if(fileCaminho.listFiles()[i].isDirectory()){
						arquivos[posicaoArquivo]=fileCaminho.listFiles()[i].getName();
						posicaoArquivo++;
					}
				}
				//Retorna o vetor arquivos
				return arquivos;
			}else{
				return null;
			}
		} catch (Exception exc) {
			// TODO: handle exception
			JDialogMensagem.exibirMensagem("Erro", "Error:<br/>"+exc.getMessage());
			return null;
		}
	}

	//Extrai arquivos e pastas de um arquivo Jar
	public static boolean extrairArquivoJar(String arquivo, String caminho){
		//Vari�vel de retorno
		boolean retorno=false;
		try{
			//Carrega o arquivo Jar informado
			java.util.jar.JarFile jar = new java.util.jar.JarFile(arquivo);
			java.util.Enumeration<?> enumEntries = jar.entries();
			while (enumEntries.hasMoreElements()) {
				java.util.jar.JarEntry file = (java.util.jar.JarEntry) enumEntries.nextElement();
				java.io.File f = new java.io.File(caminho + java.io.File.separator + file.getName());
				// Se for um diret�rio, Cria
				if (file.isDirectory()) { 
					f.mkdir();
					f.mkdirs();
					continue;
				}else{
					f.getParentFile().mkdir();
					f.getParentFile().mkdirs();
				}
				//Carregando as vari�veis de Streaming
				java.io.InputStream is = jar.getInputStream(file); 
				java.io.FileOutputStream fos = new java.io.FileOutputStream(f);
				//Escreve o arquivo lido em disco no destino passado por par�metro
				while (is.available() > 0) {
					fos.write(is.read());
				}
				//Fecha as vari�veis de Streaming
				fos.close();
				is.close();
				retorno=true;
			}
			//Fecha o arquivo Jar
			jar.close();
		}catch(Exception exc){
			JDialogMensagem.exibirMensagem("Erro", "Error:<br/>"+exc.getMessage());
			exc.printStackTrace();
		}
		return retorno;
	}


	//Salvando configuracoes referente ao layout do Tetris
	public static void salvarLayout(int barraLateralEsquerda, int barraLateralDireita, int separador1, int separador2, int separador3, String lookAndFeel, String idioma){
		try {
			//Verifica se o workspace do TetrisIDE existe. Caso n�o, cria
			if(verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/")==false){
				criarPasta(System.getProperty("user.home")+"/TetrisWorkspace/");
			}
			//Grava arquivo de configura��es do TetrisIDE
			Arquivo.gravarArquivo(System.getProperty("user.home")+"/TetrisWorkspace/conf", ""+barraLateralEsquerda+"\n"
					+ barraLateralDireita+"\n"
					+ separador1+"\n"
					+ separador2+"\n"
					+ separador3+"\n"
					+ lookAndFeel+"\n"
					+ idioma+"\n");
		} catch (Exception exc) {
			// TODO: handle exception
			exc.printStackTrace();
		}
	}

	//Carregando configuracoes de layout do Tetris
	public static void carregarLayout(JTetrisPanelBarraLateralEsquerda jTetrisPanelBarraLateralEsquerda, JTetrisPanelBarraLateralDireita jTetrisPanelBarraLateralDireita, JTetrisSeparator jSeparator1, JTetrisSeparator jSeparator2, JTetrisSeparator jSeparator3, JFramePrincipal jFramePrincipal){
		//Verifica se o arquivo de configura��es existe
		if(verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/conf")){
			//Caso sim, carrega uma lista com o conte�do do arquivo de configura��es
			ArrayList<String> arrayListLayout = lerArquivoVetor(System.getProperty("user.home")+"/TetrisWorkspace/conf");
			//Verifica se a vari�vel lista n�o � nula
			if (arrayListLayout!=null) {
				//Verifica se a lista tem mais de 5 elementos
				if(arrayListLayout.size() >= 5){
					//Preenche as informa��es de tamanho das barras laterais
					jTetrisPanelBarraLateralEsquerda.setSize(Integer.parseInt(arrayListLayout.get(0)), jTetrisPanelBarraLateralEsquerda.getHeight());
					jTetrisPanelBarraLateralDireita.setSize(Integer.parseInt(arrayListLayout.get(1)), jTetrisPanelBarraLateralDireita.getHeight());
					//Preenche a localiza��o de cada separador, dimensionando os elementos das barras laterais
					jSeparator1.setLocation(jSeparator1.getX(), Integer.parseInt(arrayListLayout.get(2)));
					jSeparator2.setLocation(jSeparator2.getX(), Integer.parseInt(arrayListLayout.get(3)));
					jSeparator3.setLocation(jSeparator3.getX(), Integer.parseInt(arrayListLayout.get(4)));
					//Preenche a informa��o LookAndFeel dos projetos gerados pelo TetrisIDE
					if(arrayListLayout.size() >= 6){
						//Caso tenha pelo menos 6 elementos na lista, verifica se est� vazia a posi��o 5
						if(arrayListLayout.get(5).equals("")){
							//Caso sim, preenche a configura��o de LookAndFeel dos projetos com SystemLookAndFeel
							jFramePrincipal.setLookAnFeel("SystemLookAndFeel");
						}else{
							//Caso n�o, preenche com a informa��o salva
							jFramePrincipal.setLookAnFeel(arrayListLayout.get(5));
						}
					}else{
						//Se n�o tiver pelo menos 6 elementos, preenche a configura��o de LookAndFeel com SystemLookAndFeel
						jFramePrincipal.setLookAnFeel("SystemLookAndFeel");
					}
					//Preenche a informa��o de idioma do TetrisIDE
					if(arrayListLayout.size() >= 7){
						//Caso tenha pelo menos 7 elementos na lista, preenche o idioma com a informa��o da posi��o 6 
						jFramePrincipal.setIdioma(arrayListLayout.get(6));
					}else{
						//Caso n�o, preenche com o idioma En-US
						jFramePrincipal.setIdioma("En-US");
					}
				}
			}
		}
	}

	//Baixa arquivo e retorna true, caso sucesso, e false, caso contr�rio
	public static boolean baixarArquivo(String stringUrl, String arquivoLocal) {  
		try{  
			//Carrega vari�vel que simboliza arquivo no disco que recebr� o conte�do do arquivo remoto
			File file = new File(arquivoLocal);  
			//Carrega vari�vel de Streaming de sa�da
			OutputStream out = new FileOutputStream(file, false);  
			//Carrega a URL do arquivo remoto
			URL url = new URL(stringUrl);  
			URLConnection conn = url.openConnection();  
			conn.setUseCaches(false);

			conn.addRequestProperty("User-Agent", 
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			//Carrega vari�vel de Streaming de entrada
			InputStream in = conn.getInputStream();  
			//Percorre Streaming de entrada e escreve no Streaming de sa�da
			int i=0;  
			while ((i = in.read()) != -1){  
				out.write(i);  
			}  
			//Finaliza e Fecha os Streamings
			out.flush();
			in.close();  
			out.close();  


			System.out.println("Download Completed!");  

			return true;
		}  

		catch (FileNotFoundException e){  
			e.printStackTrace();  
		}  
		catch (MalformedURLException e){  
			e.printStackTrace();    
		}  
		catch (IOException e){  
			e.printStackTrace();   
		}

		return false;
	}

	//Retorna uma string com o hash do arquivo
	public static String geraHash(File f) throws NoSuchAlgorithmException, FileNotFoundException{
		//Cria vari�vel de codifica��o
		MessageDigest digest = MessageDigest.getInstance("MD5");
		//Cria vari�vel de Streaming com o arquivo passado como par�metro
		InputStream is = new FileInputStream(f);                  
		//Vari�veis auxiliares
		byte[] buffer = new byte[8192];  
		int read = 0;  
		String output = null;  
		try {  
			//Percorre o Streaming e preenche a vari�vel digest
			while( (read = is.read(buffer)) > 0) {  
				digest.update(buffer, 0, read);  
			}         
			//Gera o Hash
			byte[] md5sum = digest.digest();  
			BigInteger bigInt = new BigInteger(1, md5sum);  
			output = bigInt.toString(16);  
			System.out.println("MD5: " + output);  
		}  
		catch(IOException e) {  
			throw new RuntimeException("N�o foi possivel processar o arquivo.", e);  
		}  
		finally {  
			try {  
				//Fecha a vari�vel de Streaming
				is.close();  
			}  
			catch(IOException e) {  
				throw new RuntimeException("N�o foi possivel fechar o arquivo", e);  
			}  
		}     
		//Retorna o Hash
		return output;  

	}
}
