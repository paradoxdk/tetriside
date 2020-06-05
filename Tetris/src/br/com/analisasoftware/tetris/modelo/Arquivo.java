/*
 *Classe utilizada para manipular arquivos de texto e binários
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
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
	//Cria diretório referente a um caminho específico e retorna true, caso tenha sucesso, ou false, caso contrário 
	public static boolean criarPasta(String caminho){
		try {
			//Carrega o caminho desejado
			File fileCaminho = new File(caminho);
			//Cria diretórios
			fileCaminho.mkdir();
			fileCaminho.mkdirs();

			return true;
		} catch (Exception exc) {
			// TODO: handle exception
			JDialogMensagem.exibirMensagem("Erro", "Error:<br/>"+exc.getMessage());
			return false;
		}
	}

	//Verifica se um determinado caminho ou arquivo existe. Caso sim, retorna true, caso não, retorna false
	public static boolean verificarCaminho(String caminho){
		try{
			//Carrega o caminho ou arquivo desejado
			File fileCaminho = new File(caminho);
			//Verifica a existência
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

	//Renomea arquivo especificado e retorna true, caso tenha sucesso, e false, caso contrário
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

	//Transforma uma String contendo um endereço de URL para String, removendo o termo file:/ do início
	public static String urlToString(String s){
		//Verifica se a String s tem mais de 5 caracteres
		if(s.length() > 5){
			//Verifica se tem o termo file:/ na sua composição
			if((Character.toString(s.charAt(0))+Character.toString(s.charAt(1))+Character.toString(s.charAt(2))+Character.toString(s.charAt(3))+Character.toString(s.charAt(4))).equals("file:")){
				//String de retorno
				String r="";
				//Percorre o parâmetro s e adiciona à String r
				for (int i = 5; i < s.length(); i++) {
					r=r+Character.toString(s.charAt(i));
				}

				//Tenta retornar a String r com codificação UTF-8
				try {
					return URLDecoder.decode(r, "UTF-8");
				}
				catch (Exception e) {
					//Caso não consiga, tenta retornar eliminando a codificação %20 da URL
					return r.replace("%20", " ");
				}

			}else{
				//Caso não seja uma URL, retorna a mesma String
				return s;
			}
		}else{
			//Caso não tenha mais de 5 caracteres, retorna a mesma String
			return s;
		}
	}

	//Copia arquivo e retorna true, caso sucesso, e false, caso contrário
	public static boolean copiarArquivo(String caminho1, String caminho2){
		try{		
			//Carregando variáveis de Streaming
			FileInputStream is = new FileInputStream(caminho1);
			FileOutputStream os = new FileOutputStream(caminho2);

			// Cria channel na origem
			FileChannel oriChannel = is.getChannel();
			// Cria channel no destino
			FileChannel destChannel = os.getChannel();
			// Copia conteúdo da origem no destino
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

	//Copia diretório, e retorna true, caso sucesso, e false, caso contrário
	public static boolean copiarPasta(String origem, String destino){
		//Variável de retorno
		boolean retorno=false;
		try {
			//Carregando diretório e seus arquivos filhos
			File diretorio = new File(origem);
			File[] arquivos = diretorio.listFiles();

			//Cria diretórios no destino
			criarPasta(destino);
			//Percorre os arquivos da origem
			for (int i = 0; i < arquivos.length; i++) {
				if(arquivos[i].isDirectory()){
					//Caso o elemento na posição i seja um diretório, cria no destino
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
		//Variável de retorno
		boolean retorno=false;
		//Aponta para o projeto aberno na instância da JFramePrincipal
		Projeto projeto = jFramePrincipal.getProjeto();
		try {
			//Aponta para a lista de janelas da instância do projeto aberto
			ArrayList<Janela> arrayLisJanelas = projeto.getArrayListJanelas();
			//Variável de auxílio para apontar para as janelas
			Janela janela = null;
			//Percorre a lista de janelas
			for (int i = 0; i < arrayLisJanelas.size(); i++) {
				//Aponta para a janela na posição i
				janela = arrayLisJanelas.get(i);

				//Caso a janela tenha sido alterada
				if(janela.isAlterado()){
					//Limpa os estados da janela (histórico de alteração)
					janela.getArrayListEstadoJanela().clear();
					janela.setPosicaoEstadoJanela(-1);
					//Se a janela não estiver aberta na Área de Trabalho, seta ela como não alterada
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
		//Variável de retorno
		boolean retorno=false;
		try {
			//Carrega variáveis de Streaming com o caminho de gravação da janela
			FileOutputStream fileOutputStream = new FileOutputStream(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/janelas/"+janela.getNome()+".dabj");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			//Grava a janela no disco
			objectOutputStream.writeObject(janela);
			//Finaliza e fecha as variáveis de Streaming
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
		//Variável de retorno
		boolean retorno=false;

		try {
			//Aponta para a instância do BancoDeDados do projeto aberto
			BancoDeDados bancoDeDados = projeto.getBancoDeDados();
			//Caso o BancoDeDados tenha sido alterado
			if(bancoDeDados.isAlterado()){
				//Seta ele como não alterado
				bancoDeDados.setAlterado(false);
				//Carrega as variáveis de Streaming com o caminho de gravação do BancoDeDados
				FileOutputStream fileOutputStream = new FileOutputStream(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/db/"+projeto.getNome()+".dabj");
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				//Grava o banco de dados no disco
				objectOutputStream.writeObject(bancoDeDados);
				//Finaliza e fecha as variáveis de Streaming
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

	//Lê banco de dados do disco para a memoria
	public static BancoDeDados lerBancoDeDados(String nomeProjeto){
		//Cria variável que será preenchida
		BancoDeDados bancoDeDados = null;
		try {
			//Verifica se o caminho do arquivo do BancoDeDados existe
			if (verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+nomeProjeto+"/db/"+nomeProjeto+".dabj")) {
				//Cria variáveis de Streaming
				FileInputStream fileInputStream;
				ObjectInputStream objectInputStream;
				//Carrega variáveis de Streaming com o caminho do arquivo do BancoDeDados do projeto
				fileInputStream = new FileInputStream(System.getProperty("user.home")+"/TetrisWorkspace/"+nomeProjeto+"/db/"+nomeProjeto+".dabj");
				objectInputStream = new ObjectInputStream(fileInputStream);
				//Lê o arquivo e carrega a instância na memória
				bancoDeDados = (BancoDeDados)objectInputStream.readObject();
				//Fecha as variáveis de Streaming
				objectInputStream.close();
				fileInputStream.close();
			}

		} catch (Exception exc) {
			// TODO: handle exception
			JDialogMensagem.exibirMensagem("Erro", "Error:<br/>"+exc.getMessage());
			return null;
		}
		//Retorna a instância carregada do BancoDeDados
		return bancoDeDados;
	}

	//Lê janelas do disco para a memoria
	public static ArrayList<Janela> lerJanelas(String nomeProjeto){
		//Cria variável de lista de janelas
		ArrayList<Janela> arrayLisJanelas = new ArrayList<Janela>();
		try {
			//Lista as janelas gravadas do projeto
			String[] janelas = listarArquivos(System.getProperty("user.home")+"/TetrisWorkspace/"+nomeProjeto+"/janelas/");
			//Cria variáveis de Streaming
			FileInputStream fileInputStream;
			ObjectInputStream objectInputStream;
			//Percorre o vetor com o nome das janelas do projeto
			for (int i = 0; i < janelas.length; i++) {
				//Inicializa o Streaming da janela com o nome na posição i do vetor
				fileInputStream = new FileInputStream(System.getProperty("user.home")+"/TetrisWorkspace/"+nomeProjeto+"/janelas/"+janelas[i]);
				objectInputStream = new ObjectInputStream(fileInputStream);
				//Carrega a instância da janela e adiciona à lista
				Janela janela = (Janela)objectInputStream.readObject();
				janela.setAlterado(true);
				arrayLisJanelas.add(janela);
				//Fecha as variáveis de Streaming
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

	//Grava arquivo no disco e retorna true, caso sucesso, e false, caso contrário
	public static boolean gravarArquivo(String arquivo, String conteudo){
		//Variável de retorno
		boolean retorno=false;
		try {
			//Carrega variável de Buffer para escrever no disco
			BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(arquivo)),"UTF-8"));
			//Quebra o conteúdo em linhas e carrega em um vetor
			String[] vetorConteudo = conteudo.split("\n");
			//Percorre o vetorConteudo
			for (int i = 0; i < vetorConteudo.length; i++) {
				//Caso nao esteja na posição 0, gera uma quebra de linha antes de escrever o conteúdo da linha da posição i
				if(i!=0){
					fileWriter.newLine();
				}
				//Escreve o conteúdo da linha da posição i no arquivo
				fileWriter.write(vetorConteudo[i]);
			}

			//Finaliza e fecha a variável de escrita
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

	//Lê arquivo do disco
	public static String lerArquivo(String caminho){
		//Variável que abrigará o conteúdo do arquivo
		String conteudo="";
		try {
			//Carrega variável de buffer de leitura
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(caminho), "UTF-8"));
			//Variável auxiliar para ler cada linha do arquivo
			String linha="";
			//Percorre cada linha do arquivo lido
			while((linha=bufferedReader.readLine())!=null){
				//Caso a variável conteúdo não esteja vazia, adiciona uma quebra de linha
				if(!conteudo.equals("")){
					conteudo+="\n";
				}
				//Adiciona o conteúdo da linha lida à variável conteudo
				conteudo+=linha;
			}
			//Fecha variável de leitura
			bufferedReader.close();
		} catch (Exception exc) {
			// TODO: handle exception
			JDialogMensagem.exibirMensagem("Erro", "Error:<br/>"+exc.getMessage());
			conteudo="";
		}
		//Retorna a variável conteudo
		return conteudo;
	}
	//Lê um arquivo e carrega em uma lista, separando suas linhas
	public static ArrayList<String> lerArquivoVetor(String caminho){
		//Cria lista vazia que abrigará o conteúdo do arquivo
		ArrayList<String> arrayListString = new ArrayList<String>();
		try {
			//Carrega variável de buffer de leitura
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(caminho), "UTF-8"));
			//Variável auxiliar para ler cada linha do arquivo
			String linha="";
			//Percorre cada linha do arquivo lido
			while((linha=bufferedReader.readLine())!=null){
				//Adiciona cada linha à lista
				arrayListString.add(linha);
			}
			//Fecha a variável de leitura
			bufferedReader.close();
		} catch (Exception exc) {
			// TODO: handle exception
			JDialogMensagem.exibirMensagem("Erro", "Error:<br/>"+exc.getMessage());

		}

		//Retorna a lista com o conteúdo do arquivo
		return arrayListString;
	}

	//Apaga um arquivo e retorna true, caso sucesso, e false, caso contrário
	public static boolean apagarArquivo(String arquivo){
		//Variável de retorno
		boolean retorno=false;
		try {
			//Carrega o arquivo que será excluído
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

	//Apaga um diretório
	public static boolean apagarDiretorio(File dir) {
		//Verifica se o parâmetro dir passado é um diretório
		if (dir.isDirectory()) {  
			//Carrega vetor com arquivos e diretórios contidos no diretório a ser excluído
			String[] children = dir.list();  
			//Percorre o vetor
			for (int i=0; i<children.length; i++) {
				//Chama recursivamente este método para apagar os arquivos e diretórios dentro dos filhos
				boolean success = apagarDiretorio(new File(dir, children[i]));  
				if (!success) {  
					return false;  
				}  
			}  
		}  

		// Agora o diretório está vazio, restando apenas deletá-lo.  
		return dir.delete();  
	}  

	//Cria um vetor com os arquivos de um diretorio
	public static String[] listarArquivos(String caminho){
		try {
			//Carrega o diretório passa do por parâmetro
			File fileCaminho = new File(caminho);
			//Verifica se é um diretório
			if(fileCaminho.isDirectory()){
				//Variáveis auxiliares
				int numeroArquivos=0, posicaoArquivo=0;
				//Percorre a lista de elementos do diretório, caso não seja um diretório, soma ao numeroArquivos
				for (int i = 0; i < fileCaminho.listFiles().length; i++) {
					if(!fileCaminho.listFiles()[i].isDirectory()){
						numeroArquivos++;
					}
				}

				//Cria vetor com o tamanho do numeroArquivos
				String[] arquivos= new String[numeroArquivos];

				//Percorre novamente a lista de elementos, caso não seja um diretório, adiciona ao vetor arquivos
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
			//Carrega o diretório passado por parâmetro
			File fileCaminho = new File(caminho);
			//Verifica se é um diretório
			if(fileCaminho.isDirectory()){
				//Variáveis auxiliares
				int numeroPastas=0, posicaoArquivo=0;;
				//Percorre a lista de elementos do diretório, caso seja um diretório, soma ao numeroPastas
				for (int i = 0; i < fileCaminho.listFiles().length; i++) {
					if(fileCaminho.listFiles()[i].isDirectory()){
						numeroPastas++;
					}
				}

				//Cria vetor com tamanho numeroPastas
				String[] arquivos= new String[numeroPastas];

				//Percorre novamente a lista de elementos, caso seja um diretório, adiciona ao vetor arquivos
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
		//Variável de retorno
		boolean retorno=false;
		try{
			//Carrega o arquivo Jar informado
			java.util.jar.JarFile jar = new java.util.jar.JarFile(arquivo);
			java.util.Enumeration<?> enumEntries = jar.entries();
			while (enumEntries.hasMoreElements()) {
				java.util.jar.JarEntry file = (java.util.jar.JarEntry) enumEntries.nextElement();
				java.io.File f = new java.io.File(caminho + java.io.File.separator + file.getName());
				// Se for um diretório, Cria
				if (file.isDirectory()) { 
					f.mkdir();
					f.mkdirs();
					continue;
				}else{
					f.getParentFile().mkdir();
					f.getParentFile().mkdirs();
				}
				//Carregando as variáveis de Streaming
				java.io.InputStream is = jar.getInputStream(file); 
				java.io.FileOutputStream fos = new java.io.FileOutputStream(f);
				//Escreve o arquivo lido em disco no destino passado por parâmetro
				while (is.available() > 0) {
					fos.write(is.read());
				}
				//Fecha as variáveis de Streaming
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
			//Verifica se o workspace do TetrisIDE existe. Caso não, cria
			if(verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/")==false){
				criarPasta(System.getProperty("user.home")+"/TetrisWorkspace/");
			}
			//Grava arquivo de configurações do TetrisIDE
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
		//Verifica se o arquivo de configurações existe
		if(verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/conf")){
			//Caso sim, carrega uma lista com o conteúdo do arquivo de configurações
			ArrayList<String> arrayListLayout = lerArquivoVetor(System.getProperty("user.home")+"/TetrisWorkspace/conf");
			//Verifica se a variável lista não é nula
			if (arrayListLayout!=null) {
				//Verifica se a lista tem mais de 5 elementos
				if(arrayListLayout.size() >= 5){
					//Preenche as informações de tamanho das barras laterais
					jTetrisPanelBarraLateralEsquerda.setSize(Integer.parseInt(arrayListLayout.get(0)), jTetrisPanelBarraLateralEsquerda.getHeight());
					jTetrisPanelBarraLateralDireita.setSize(Integer.parseInt(arrayListLayout.get(1)), jTetrisPanelBarraLateralDireita.getHeight());
					//Preenche a localização de cada separador, dimensionando os elementos das barras laterais
					jSeparator1.setLocation(jSeparator1.getX(), Integer.parseInt(arrayListLayout.get(2)));
					jSeparator2.setLocation(jSeparator2.getX(), Integer.parseInt(arrayListLayout.get(3)));
					jSeparator3.setLocation(jSeparator3.getX(), Integer.parseInt(arrayListLayout.get(4)));
					//Preenche a informação LookAndFeel dos projetos gerados pelo TetrisIDE
					if(arrayListLayout.size() >= 6){
						//Caso tenha pelo menos 6 elementos na lista, verifica se está vazia a posição 5
						if(arrayListLayout.get(5).equals("")){
							//Caso sim, preenche a configuração de LookAndFeel dos projetos com SystemLookAndFeel
							jFramePrincipal.setLookAnFeel("SystemLookAndFeel");
						}else{
							//Caso não, preenche com a informação salva
							jFramePrincipal.setLookAnFeel(arrayListLayout.get(5));
						}
					}else{
						//Se não tiver pelo menos 6 elementos, preenche a configuração de LookAndFeel com SystemLookAndFeel
						jFramePrincipal.setLookAnFeel("SystemLookAndFeel");
					}
					//Preenche a informação de idioma do TetrisIDE
					if(arrayListLayout.size() >= 7){
						//Caso tenha pelo menos 7 elementos na lista, preenche o idioma com a informação da posição 6 
						jFramePrincipal.setIdioma(arrayListLayout.get(6));
					}else{
						//Caso não, preenche com o idioma En-US
						jFramePrincipal.setIdioma("En-US");
					}
				}
			}
		}
	}

	//Baixa arquivo e retorna true, caso sucesso, e false, caso contrário
	public static boolean baixarArquivo(String stringUrl, String arquivoLocal) {  
		try{  
			//Carrega variável que simboliza arquivo no disco que recebrá o conteúdo do arquivo remoto
			File file = new File(arquivoLocal);  
			//Carrega variável de Streaming de saída
			OutputStream out = new FileOutputStream(file, false);  
			//Carrega a URL do arquivo remoto
			URL url = new URL(stringUrl);  
			URLConnection conn = url.openConnection();  
			conn.setUseCaches(false);

			conn.addRequestProperty("User-Agent", 
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			//Carrega variável de Streaming de entrada
			InputStream in = conn.getInputStream();  
			//Percorre Streaming de entrada e escreve no Streaming de saída
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
		//Cria variável de codificação
		MessageDigest digest = MessageDigest.getInstance("MD5");
		//Cria variável de Streaming com o arquivo passado como parâmetro
		InputStream is = new FileInputStream(f);                  
		//Variáveis auxiliares
		byte[] buffer = new byte[8192];  
		int read = 0;  
		String output = null;  
		try {  
			//Percorre o Streaming e preenche a variável digest
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
			throw new RuntimeException("Não foi possivel processar o arquivo.", e);  
		}  
		finally {  
			try {  
				//Fecha a variável de Streaming
				is.close();  
			}  
			catch(IOException e) {  
				throw new RuntimeException("Não foi possivel fechar o arquivo", e);  
			}  
		}     
		//Retorna o Hash
		return output;  

	}
}
