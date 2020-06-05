/*
 *Manipula informa��es do Projeto em mem�ria
 *Por: David de Almeida Bezerra J�nior
 *Vers�o: 1.3
 *Data: 24 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.modelo;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.analisasoftware.tetris.visao.janelas.JDialogMensagem;
import br.com.analisasoftware.tetris.visao.janelas.JFramePrincipal;
import componentes.modelo.bancodedados.BancoDeDados;
import componentes.modelo.bancodedados.Tabela;
import tetris.modelo.componentes.Atributo;
import tetris.modelo.componentes.Componente;
import tetris.modelo.componentes.Janela;

public class Projeto {
	//Vari�veis de identifica��o do projeto
	private String nome;
	private String icone;
	private Date data;
	//Vari�vel com a inst�ncia do projeto do banco de dados em mem�ria
	private BancoDeDados bancoDeDados;
	//Janela principal definida no projeto
	private Janela janelaPrincipal;
	//Lista de janelas do projeto
	private ArrayList<Janela> arrayListJanelas;

	public Projeto(String nome, String icone, Date data, ArrayList<Janela> arrayListJanelas, Janela janelaPrincipal) {
		super();
		this.nome = nome;
		this.icone = icone;
		this.data = data;
		this.arrayListJanelas = arrayListJanelas;
		this.janelaPrincipal=janelaPrincipal;
	}

	//Cria um projeto e retorna true, caso sucesso, e false, caso contr�rio
	public static boolean criarProjeto(String nome, JFramePrincipal jFramePrincipal){
		//Vari�vel de retorno
		boolean retorno=false;
		//Cria diret�rio do projeto no workspace do TetrisIDE
		if(Arquivo.criarPasta(System.getProperty("user.home")+"/TetrisWorkspace/"+nome+"/")){
			//Grava arquivo de configura��o do projeto
			if(Arquivo.gravarArquivo(System.getProperty("user.home")+"/TetrisWorkspace/"+nome+"/conf.dabj", Desenvolvedor.VERSAO+"\n"
					+nome+"\n"
					+ "default\n"
					+ Data.getDataAtual()+"\n"
					+ "$none")){


				retorno=true;
			}
		}

		return retorno;
	}

	//Renomea um projeto e retorna true, caso sucesso, e false, caso contr�rio
	public static boolean renomearProjeto(String nomeAntigo, String nomeNovo){
		//Vari�vel de retorno
		boolean retorno=false;
		//Renomea o diret�rio do projeto
		if(Arquivo.renomearArquivo(System.getProperty("user.home")+"/TetrisWorkspace/"+nomeAntigo+"/", System.getProperty("user.home")+"/TetrisWorkspace/"+nomeNovo+"/")){
			//Captura o conte�do do arquivo de configura��o e armazena em uma lista
			ArrayList<String> arrayListArquivo = Arquivo.lerArquivoVetor(System.getProperty("user.home")+"/TetrisWorkspace/"+nomeNovo+"/conf.dabj");
			//Grava o arquivo de configura��o, modificando o nome do projeto
			if(Arquivo.gravarArquivo(System.getProperty("user.home")+"/TetrisWorkspace/"+nomeNovo+"/conf.dabj", arrayListArquivo.get(0)+"\n"
					+nomeNovo+"\n"
					+ arrayListArquivo.get(2)+"\n"
					+ Data.getDataAtual()+"\n"
					+ arrayListArquivo.get(4))){
				retorno=true;
			}
		}

		return retorno;
	}

	//Exclui um projeto e retorna true, caso sucesso, e false, caso contr�rio
	public static boolean excluirProjeto(String nome){
		//Vari�vel de retorno
		boolean retorno=false;
		//Exclui o diret�rio do projeto
		if(Arquivo.apagarDiretorio(new File(System.getProperty("user.home")+"/TetrisWorkspace/"+nome+"/"))){
			retorno=true;
		}

		return retorno;
	}

	//Abre um projeto e retorna true, caso sucesso, e false, caso contr�rio
	public static boolean abrirProjeto(String nome, JFramePrincipal jFramePrincipal){
		//Vari�vel de retorno
		boolean retorno=false;
		//Verifica se o arquivo de configura��o do projeto existe
		if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+nome+"/conf.dabj")){
			//Vari�veis de cabe�alho
			String icone="";
			Janela janelaPrincipal=null;
			Date data;

			//Carregando cabe�alho do projeto
			ArrayList<String> arrayListConteudoProjeto = Arquivo.lerArquivoVetor(System.getProperty("user.home")+"/TetrisWorkspace/"+nome+"/conf.dabj");
			icone = arrayListConteudoProjeto.get(2);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			try{
				data = dateFormat.parse(arrayListConteudoProjeto.get(3));
			}catch(Exception exc){
				data = new Date();
			}
			//Vari�vel para a lista de janelas
			ArrayList<Janela> arrayListJanelas = new ArrayList<Janela>();

			//Verificando se h� janelas neste projeto;
			if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+nome+"/janelas/")){
				//Carregando janelas
				arrayListJanelas = Arquivo.lerJanelas(nome);
			}
			//Percorre a lista de janelas para capturar a janela principal
			for (int j = 0; j < arrayListJanelas.size(); j++) {
				if(arrayListConteudoProjeto.size() > 4){
					if(arrayListJanelas.get(j).getNome().equals(arrayListConteudoProjeto.get(4))){
						janelaPrincipal=arrayListJanelas.get(j);
						break;
					}
				}
			}
			//Cria a inst�ncia do projeto
			jFramePrincipal.setProjeto(new Projeto(nome, icone, data, arrayListJanelas, janelaPrincipal));
			//Preenche o Explorador de Janelas no TetrisIDE com as janelas do projeto
			jFramePrincipal.getjTetrisListExploradorDeJanelas().preencherLista(jFramePrincipal);
			//Habilita os bot�es da barra de ferramentas
			jFramePrincipal.habilitarBotoes();
			//Muda o t�tulo da barra de t�tulos do TetrisIDE
			jFramePrincipal.setTitle("TetrisIDE - "+nome);
			//Seleciona a janela principal na tela do TetrisIDE
			if(janelaPrincipal!=null){
				jFramePrincipal.getjButtonJanelaPrincipal().setText(janelaPrincipal.getNome());
			}else{
				jFramePrincipal.getjButtonJanelaPrincipal().setText(Idioma.getTraducao("tetris.none", jFramePrincipal));
			}

			//Carregando banco de dados
			BancoDeDados bancoDeDados = null;
			//Verifica se o diret�rio do banco de dados do projeto existe
			if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+nome+"/db/")){
				//L� arquivo de banco de dados
				bancoDeDados = Arquivo.lerBancoDeDados(nome);
				//Corrigindo campos tragos pela versao 1.1
				if(bancoDeDados!=null){
					if(bancoDeDados.getConnectionString()==null){
						bancoDeDados.setConnectionString("jdbc:mysql://$server/$database");
						bancoDeDados.setDriver("com.mysql.jdbc.Driver");
					}
				}
			}
			//Define a inst�ncia do banco de dados no projeto
			if(bancoDeDados!=null){
				jFramePrincipal.getProjeto().setBancoDeDados(bancoDeDados);
			}else{
				jFramePrincipal.getProjeto().setBancoDeDados(new BancoDeDados(nome, new ArrayList<Tabela>()));
				jFramePrincipal.getProjeto().getBancoDeDados().setConnectionString("jdbc:mysql://$server/$database");
				jFramePrincipal.getProjeto().getBancoDeDados().setDriver("com.mysql.jdbc.Driver");
			}
			//Preenche a lista de tabela e colunas do Banco de Dados na tela do TetrisIDE
			jFramePrincipal.getjComboBoxTabelaGerenciadorDeBancoDeDados().preencherTabelas();
			//

			System.gc();

			retorno=true;
		}
		return retorno;
	}

	//Salva um projeto e retorna true, caso sucesso, e false, caso contr�rio
	public static boolean salvarProjeto(JFramePrincipal jFramePrincipal){
		//Vari�vel de retorno
		boolean retorno=false;
		//Vari�vel que aponta para a inst�ncia do projeto aberto
		Projeto projeto = jFramePrincipal.getProjeto();
		//Informa��es de cabe�alho
		String nome = projeto.getNome();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String janelaPrincipal="";
		//Capturando nome da janela principal
		if(projeto.getJanelaPrincipal()!=null){
			janelaPrincipal="\n"+projeto.getJanelaPrincipal().getNome();
		}

		//Salvando cabe�alho do projeto
		if(Arquivo.gravarArquivo(System.getProperty("user.home")+"/TetrisWorkspace/"+nome+"/conf.dabj", Desenvolvedor.VERSAO+"\n"
				+nome+"\n"
				+ projeto.getIcone()+"\n"
				+ dateFormat.format(projeto.getData())
				+ janelaPrincipal)){

			//Criando pasta janelas;
			if(Arquivo.criarPasta(System.getProperty("user.home")+"/TetrisWorkspace/"+nome+"/janelas/")){
				//Salvando janelas
				Arquivo.gravarJanelas(jFramePrincipal);
			}
			//

			//Salvando banco de dados
			if(Arquivo.criarPasta(System.getProperty("user.home")+"/TetrisWorkspace/"+nome+"/db/")){
				Arquivo.gravarBancoDeDados(projeto);
			}
			projeto.getBancoDeDados().setAlterado(false);

			System.gc();

			retorno=true;
		}
		return retorno;
	}

	//Fecha o projeto abertoe retorna true, caso sucesso, e false, caso contr�rio
	public static boolean fecharProjeto(JFramePrincipal jFramePrincipal){
		//Vari�vel de retorno
		boolean retorno=false;
		//Desfaz a liga��o da vari�vel do projeto na mem�ria, deixando-a nula
		jFramePrincipal.setProjeto(null);
		//Modifica o t�tulo da barra de t�tulos do TetrisIDE
		jFramePrincipal.setTitle("TetrisIDE");
		//Desabilita os bot�es na barra de ferramentas
		jFramePrincipal.desabilitarBotoes();

		retorno=true;

		return retorno;
	}

	//Gera o c�digo-fonte do Projeto e retorna true, caso verdadeiro, e false, caso contr�rio
	public static boolean gerarCodigoFonte(JFramePrincipal jFramePrincipal){
		//Vari�vel que aponta para a inst�ncia do projeto aberto
		Projeto projeto = jFramePrincipal.getProjeto();
		//Vari�vel de retorno
		boolean retorno=false;

		//Criando pasta de c�digo-fonte
		if(Arquivo.criarPasta(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/src/tetris/"+projeto.getNome().toLowerCase()+"/")){
			//Criando arquivo principal

			String lookAndFeel="";
			if(jFramePrincipal.getLookAnFeel().equals("SystemLookAndFeel")){
				lookAndFeel= "			if(javax.swing.UIManager.getSystemLookAndFeelClassName().equals(javax.swing.UIManager.getCrossPlatformLookAndFeelClassName())){\n"
						+ "				javax.swing.UIManager.setLookAndFeel(\"com.sun.java.swing.plaf.gtk.GTKLookAndFeel\");\n"
						+ "			}else{\n"
						+"				javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());\n"
						+ "			}\n";
			}else{
				lookAndFeel= 
						"			javax.swing.UIManager.setLookAndFeel(\""+jFramePrincipal.getLookAnFeel()+"\");\n";
			}
			Arquivo.gravarArquivo(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/src/tetris/"+projeto.getNome().toLowerCase()+"/Principal.java", 
					"package tetris."+projeto.getNome().toLowerCase()+";\n\n"
							+ "import tetris."+projeto.getNome().toLowerCase()+".visao."+projeto.getJanelaPrincipal().getNome()+";\n"
							+ "\n"
							+ "public class Principal{\n"
							+ "	public static void main(String[] args){\n"
							+ "		try{\n"
							+ lookAndFeel
							+"		}catch(Exception exc){\n"

							+"		}\n"
							+ "		new "+projeto.getJanelaPrincipal().getNome()+"().init();\n"
							+ "	}\n"
							+ "}");

			//Criando pasta de recursos
			if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/res/")){
				if(Arquivo.criarPasta(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/src/tetris/"+projeto.getNome().toLowerCase()+"/res/")){
					String[] arquivos = Arquivo.listarArquivos(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/res/");
					for (int i = 0; i < arquivos.length; i++) {
						//Copiando os recursos
						Arquivo.copiarArquivo(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/res/"+arquivos[i], System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/src/tetris/"+projeto.getNome().toLowerCase()+"/res/"+arquivos[i]);
					}
				}
			}

			//Vari�vel com lista contendo as janelas do projeto
			ArrayList<Janela> arrayListJanelas = projeto.getArrayListJanelas();
			//Percorre a lista de janelas
			for (int i = 0; i < arrayListJanelas.size(); i++) {
				//Gera c�digo-fonte de cada janela
				arrayListJanelas.get(i).gerarCodigoFonte(jFramePrincipal);
			}

			retorno=true;
		}

		return retorno;
	}

	//Adiciona uma janelaao projeto e retorna true, caso sucesso, e false, caso contr�rio
	public static boolean adicionarJanela(Janela janela, JFramePrincipal jFramePrincipal){
		//Vari�vel de retorno
		boolean retorno=false;

		try {
			//Vari�vel que aponta para a inst�ncia do projeto aberto
			Projeto projeto = jFramePrincipal.getProjeto();
			//Cria diret�rio janelas, caso n�o exista
			if(Arquivo.criarPasta(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/janelas/")){
				//Grava a janela em disco
				if(Arquivo.gravarJanela(projeto, janela)){
					//Adiciona a janela � lista de janelas do projeto
					jFramePrincipal.getProjeto().getArrayListJanelas().add(janela);

					retorno=true;
				}
			}

		} catch (Exception exc) {
			// TODO: handle exception
			JDialogMensagem.exibirMensagem("Erro", Idioma.getTraducao("tetris.message_creating_window", jFramePrincipal)+"<br/>"+exc);
		}

		return retorno;
	}

	//Abre uma janela na �rea de trabalho e retorna true, caso sucesso, e false, caso contr�rio
	public static boolean abrirJanela(String nome, JFramePrincipal jFramePrincipal){
		//Vari�vel de retorno
		boolean retorno=false;
		//Verifica se h� um projeto aberto
		if(jFramePrincipal.getProjeto()!=null){
			//Vari�vel que aponta para a lista de janelas do projeto
			ArrayList<Janela> arrayListJanelas = jFramePrincipal.getProjeto().getArrayListJanelas();
			//Vari�vel de aux�lio
			Janela janela=null;
			//Percorre a lista de janelas e captura a janela desejada
			for (int i = 0; i < arrayListJanelas.size(); i++) {
				if(arrayListJanelas.get(i).getNome().equals(nome)){
					janela=arrayListJanelas.get(i);
					break;
				}
			}
			//Verifica se encontrou a janela desejada e abre na �rea de Trabalho
			if(janela!=null){
				retorno=jFramePrincipal.getjDesktopPaneAreaDeTrabalho().abrirJanela(janela);
				jFramePrincipal.getjDesktopPaneAreaDeTrabalho().getJanela().setAlterado(true);
				jFramePrincipal.getjSyntaxTextPaneCodigoFonteGerado().setText("");
				jFramePrincipal.getjTabbedPaneAreaDeTrabalho().setSelectedIndex(0);
			}
		}else{
			JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_open_a_project", jFramePrincipal));
		}

		return retorno;
	}

	//Renomea uma janela e retorna true, caso sucesso, e false, caso contr�rio
	public static boolean renomearJanela(JFramePrincipal jFramePrincipal, String nomeAntigo, String nomeNovo, String tituloNovo){
		//Vari�vel de retorno
		boolean retorno=false;

		//Captura lista de janelas do projeto
		ArrayList<Janela> arrayListJanelas = jFramePrincipal.getProjeto().getArrayListJanelas();
		//Percorre a lista de janelas
		for (int i = 0; i < arrayListJanelas.size(); i++) {
			//Verifica se o nome da janela � igual ao nomeAntigo passado por par�metro
			if(arrayListJanelas.get(i).getNome().equals(nomeAntigo)){
				//Renomea a janela
				arrayListJanelas.get(i).setNome(nomeNovo);
				//Verifica se a janela � janela principal
				if(jFramePrincipal.getProjeto().getJanelaPrincipal()!=null){
					//Se for, modifica o nome no projeto
					if(jFramePrincipal.getProjeto().getJanelaPrincipal().getNome().equals(nomeAntigo)){
						jFramePrincipal.getjButtonJanelaPrincipal().setText(nomeNovo);
					}
				}
				//Captura lista de atributos
				ArrayList<Atributo> arrayListAtributos = arrayListJanelas.get(i).getArrayListAtributos();
				//Percorre lista de atributos e modifica a propriedade Title para o novo nome
				for (int j = 0; j < arrayListAtributos.size(); j++) {
					if(arrayListAtributos.get(j).getNome().equals("Title")){
						arrayListAtributos.get(j).setValor(tituloNovo);
						break;
					}
				}
				//Captura lista de componentes da janela
				ArrayList<Componente> arrayListComponentes = arrayListJanelas.get(i).getArrayListComponentes();
				//Percorre a lista de componentes e modifica o atributo Pai que estavam com o nomeAntigo para o nomeNovo
				for (int j = 0; j < arrayListComponentes.size(); j++) {
					if(arrayListComponentes.get(j).getAtributo("Pai")!=null){
						if(arrayListComponentes.get(j).getAtributo("Pai").getValor().equals(nomeAntigo)){
							arrayListComponentes.get(j).getAtributo("Pai").setValor(nomeNovo);
						}
					}
				}

				retorno=true;
				//Apaga o arquivo em disco da janela com o nome antigo
				Arquivo.apagarArquivo(System.getProperty("user.home")+"/TetrisWorkspace/"+jFramePrincipal.getProjeto().getNome()+"/janelas/"+nomeAntigo+".dabj");
				//Grava a janela em disco
				Arquivo.gravarJanela(jFramePrincipal.getProjeto(), arrayListJanelas.get(i));

				break;
			}

		}


		return retorno;
	}

	//Exclui uma janela e retorna true, caso sucesso, e false, caso contr�rio
	public static boolean excluirJanela(String nome, JFramePrincipal jFramePrincipal){
		//Vari�vel de retorno
		boolean retorno=false;
		//Apaga o arquivo da janela em disco
		if(Arquivo.apagarArquivo(System.getProperty("user.home")+"/TetrisWorkspace/"+jFramePrincipal.getProjeto().getNome()+"/janelas/"+nome+".dabj")){
			//Verifica se tem c�digo-fonte da janela gerado
			if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+jFramePrincipal.getProjeto().getNome()+"/src/tetris/"+jFramePrincipal.getProjeto().getNome().toLowerCase()+"/visao/"+nome+".java")){
				//Apaga o c�digo-fonte da janela gerado
				Arquivo.apagarArquivo(System.getProperty("user.home")+"/TetrisWorkspace/"+jFramePrincipal.getProjeto().getNome()+"/src/tetris/"+jFramePrincipal.getProjeto().getNome().toLowerCase()+"/visao/"+nome+".java");
			}
			//Vari�vel que aponta para a lista de janelas do projeto
			ArrayList<Janela> arrayListJanelas = jFramePrincipal.getProjeto().getArrayListJanelas();
			//Percorre a lista de janelas
			for (int i = 0; i < arrayListJanelas.size(); i++) {
				//Verifica se a janela na posi��o i � a janela selecionada
				if(arrayListJanelas.get(i).getNome().equals(nome)){
					//Remove a inst�ncia da janela da lista
					arrayListJanelas.remove(i);
					//Verifica se � a janela principal
					if(jFramePrincipal.getProjeto().getJanelaPrincipal()!=null){
						if(jFramePrincipal.getProjeto().getJanelaPrincipal().getNome().equals(nome)){
							//Seta nulo na janela principal do projeto, caso a janela selecionada seja janela principal
							jFramePrincipal.getProjeto().setJanelaPrincipal(null);
							jFramePrincipal.getjButtonJanelaPrincipal().setText(Idioma.getTraducao("tetris.none", jFramePrincipal));
						}
					}
					break;
				}
			}

			retorno=true;

		}

		return retorno;
	}
	//Retorna uma janela
	public Janela getJanela(String janela){
		//Vari�vel de retorno
		Janela janel=null;
		//Percorre a lista de janelas do projeto e retorna a janela desejada
		for (int i = 0; i < arrayListJanelas.size(); i++) {
			if(arrayListJanelas.get(i).getNome().equals(janela)){
				janel=arrayListJanelas.get(i);
				break;
			}
		}

		return janel;
	}

	//Getters e Setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIcone() {
		return icone;
	}

	public void setIcone(String icone) {
		this.icone = icone;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public BancoDeDados getBancoDeDados() {
		return bancoDeDados;
	}

	public void setBancoDeDados(BancoDeDados bancoDeDados) {
		this.bancoDeDados = bancoDeDados;
	}

	public ArrayList<Janela> getArrayListJanelas() {
		return arrayListJanelas;
	}

	public void setArrayListJanelas(ArrayList<Janela> arrayListJanelas) {
		this.arrayListJanelas = arrayListJanelas;
	}

	public Janela getJanelaPrincipal() {
		return janelaPrincipal;
	}

	public void setJanelaPrincipal(Janela janelaPrincipal) {
		this.janelaPrincipal = janelaPrincipal;
	}

}
