/*
 *Janela de Diálogo que implementa Runnable que Executa, Exporta, Importa, Abre e Salva projeto
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 27 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.janelas;

import java.awt.Toolkit;
import java.io.File;

import javax.swing.JDialog;

import componentes.visao.JTetrisPanel;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.border.EtchedBorder;

import br.com.analisasoftware.tetris.modelo.Arquivo;
import br.com.analisasoftware.tetris.modelo.CriarJar;
import br.com.analisasoftware.tetris.modelo.Idioma;
import br.com.analisasoftware.tetris.modelo.Projeto;
import br.com.analisasoftware.tetris.visao.componentes.JTetrisListExploradorDeProjetos;

@SuppressWarnings("serial")
public class JDialogExecutar extends JDialog implements Runnable{
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Variáveis e constantes de auxílio
	private int acao;
	public static final int EXECUTAR=0, EXPORTAR_JAVA=1, EXPORTAR_TETRIS=2, IMPORTAR_TETRIS=3, ABRIR_PROJETO=4, SALVAR_PROJETO=5, EXPORTAR_JAR=6;
	//Componentes gráficos
	private JLabel jLabelMensagem;

	public JDialogExecutar(JFramePrincipal jFramePrincipal){
		setUndecorated(true);
		this.jFramePrincipal=jFramePrincipal;
		acao=0;

		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogComponentesExternos.class.getResource("/br/com/analisasoftware/tetris/imagem/icone.png")));
		setTitle("Execução");
		JTetrisPanel tetrisPanel = new JTetrisPanel();
		tetrisPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setContentPane(tetrisPanel);

		jLabelMensagem = new JLabel("");
		jLabelMensagem.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabelMensagem.setVerticalTextPosition(SwingConstants.BOTTOM);
		jLabelMensagem.setIcon(new ImageIcon(JDialogExecutar.class.getResource("/br/com/analisasoftware/tetris/imagem/carregando.gif")));
		jLabelMensagem.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelMensagem.setBounds(10, 11, 280, 108);
		tetrisPanel.add(jLabelMensagem);
		setSize(300, 130);
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		//Corrige bug grafico que ocorre com janelas dialog no gnome
		setVisible(true);
		setVisible(false);
		setModal(true);
		setLocationRelativeTo(jFramePrincipal);
	}

	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}

	//Carrega idioma nos componentes
	public void carregarIdioma(){
		setTitle(Idioma.getTraducao("tetris.execution", jFramePrincipal));

	}
	//Exibe e inicializa janela
	public void init(int acao){
		carregarIdioma();
		//Cria thread desta classe
		try {
			this.acao=acao;

			new Thread(this).start();

			setVisible(true);

		} catch (Exception exc) {
			// TODO: handle exception
			JDialogMensagem.exibirMensagem("Erro", Idioma.getTraducao("tetris.message_error_building_application", jFramePrincipal)+"<br/>"+exc);
		}
	}
	//Reescreve método run, executando as funções
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//Aponta para o projeto
		Projeto projeto = getjFramePrincipal().getProjeto();
		if(acao==IMPORTAR_TETRIS){
			//Importa projeto
			jLabelMensagem.setText(Idioma.getTraducao("tetris.importing", jFramePrincipal));
			//Seleciona projeto a ser importado
			JFileChooser jFileChooser = new JFileChooser();
			jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if(jFileChooser.showOpenDialog(getjFramePrincipal()) == JFileChooser.APPROVE_OPTION){
				//Verifica se é um projeto válido
				if(Arquivo.verificarCaminho(jFileChooser.getSelectedFile().getPath()+"/conf.dabj")){
					boolean importa=true;
					//Verifica se já não tem um projeto com o mesmo nome
					if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+jFileChooser.getSelectedFile().getName()+"/")){
						importa=false;
						//Pergunta se deseja substituir o projeto
						if(JDialogMensagem.exibirMensagem("Perguntar", Idioma.getTraducao("tetris.message_there_is_a_project_with_the_name", jFramePrincipal)+" "+jFileChooser.getSelectedFile().getName()+"!<br/>"
								+ Idioma.getTraducao("tetris.message_do_you_want_to_replace", jFramePrincipal))==1){
							//Fecha o projeto se estiver aberto
							if(projeto!=null){
								if((projeto.getNome().toUpperCase()).equals(jFileChooser.getSelectedFile().getName().toUpperCase())){
									getjFramePrincipal().getjPopupMenuProjeto().getjMenuItemFechar().doClick();
								}
							}
							//Apaga diretório do projeto
							Arquivo.apagarDiretorio(new File(System.getProperty("user.home")+"/TetrisWorkspace/"+jFileChooser.getSelectedFile().getName()+"/"));
							importa=true;
						}
					}
					//Importa projeto
					if(importa){
						Arquivo.copiarPasta(jFileChooser.getSelectedFile().getPath()+"/", System.getProperty("user.home")+"/TetrisWorkspace/"+jFileChooser.getSelectedFile().getName()+"/");
						getjFramePrincipal().getjTetrisListExploradorDeProjetos().preencherLista(getjFramePrincipal());
					}
				}else{
					JDialogMensagem.exibirMensagem("Erro", Idioma.getTraducao("tetris.message_this_is_not_a_valid_project", jFramePrincipal));
				}
			}
		}else if (acao==EXPORTAR_TETRIS) {
			//Exporta o projeto todo
			jLabelMensagem.setText(Idioma.getTraducao("tetris.exporting", jFramePrincipal));
			//Seleciona destino 
			JFileChooser jFileChooser = new JFileChooser();
			jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			//Exporta projeto
			if(jFileChooser.showOpenDialog(getjFramePrincipal()) == JFileChooser.APPROVE_OPTION){
				Arquivo.copiarPasta(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/", jFileChooser.getSelectedFile().getPath()+"/"+projeto.getNome()+"/");
			}
		}else if (acao==EXPORTAR_JAR) {
			//Exporta o arquivo jar
			jLabelMensagem.setText(Idioma.getTraducao("tetris.exporting", jFramePrincipal));

			
			if(JDialogMensagem.exibirMensagem("Perguntar", Idioma.getTraducao("tetris.message_export_jar", jFramePrincipal))==1){
				//Seleciona destino
				JFileChooser jFileChooser = new JFileChooser();
				jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(jFileChooser.showOpenDialog(getjFramePrincipal()) == JFileChooser.APPROVE_OPTION){
					//verifica se o arquivo Jar já existe
					if(Arquivo.verificarCaminho(jFileChooser.getSelectedFile().getPath()+"/"+projeto.getNome()+".jar")){
						//Pergunta se deseja substituir
						if(JDialogMensagem.exibirMensagem("Perguntar", Idioma.getTraducao("tetris.message_file_exists", jFramePrincipal))!=1){
							dispose();
							return;
						}
					}
					//Define componentes a serem comprimidos
					String[] componentesJar=null;
					File[] fileTetrisWorkspace = new File[3];

					fileTetrisWorkspace[0] = new File(System.getProperty("user.home")+"/TetrisWorkspace/componentes/");
					fileTetrisWorkspace[1] = new File(System.getProperty("user.home")+"/TetrisWorkspace/org/");
					fileTetrisWorkspace[2] = new File(System.getProperty("user.home")+"/TetrisWorkspace/com/");

					jLabelMensagem.setText(Idioma.getTraducao("tetris.extracting_components", jFramePrincipal));
					//Copiando componentes jar
					if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/comp/")){
						componentesJar = Arquivo.listarPastas(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/comp/");
						for (int i = 0; i < componentesJar.length; i++) {
							File fileDiretorioComponente = new File(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/comp/"+componentesJar[i]+"/");
							if(fileDiretorioComponente.isDirectory()){
								File[] fileArquivosComponente = fileDiretorioComponente.listFiles();
								for (int j = 0; j < fileArquivosComponente.length; j++) {
									if(!fileArquivosComponente[j].isDirectory()){
										Arquivo.copiarArquivo(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/comp/"+componentesJar[i]+"/"+fileArquivosComponente[j].getName(), System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/"+fileArquivosComponente[j].getName());
									}
								}
							}
							
							Arquivo.copiarPasta(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/comp/"+componentesJar[i]+"/", System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/");
						}
					}
					//Lê arquivos e diretórios do diretório bin
					String[] diretoriosBin = Arquivo.listarPastas(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/");
					String[] arquivosBin = Arquivo.listarArquivos(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/");

					File[] pastasCompactar = new File[arquivosBin.length+diretoriosBin.length];
					//Adiciona diretórios para compactar
					for (int i = 0; i < diretoriosBin.length; i++) {
						pastasCompactar[i] = new File(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/"+diretoriosBin[i]+"/");
					}
					//Adiciona arquivos do diretório bin
					for (int i = 0; i < arquivosBin.length; i++) {
						pastasCompactar[diretoriosBin.length+i] = new File(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/"+arquivosBin[i]);
					}

					//Excluindo pasta META-INF
					if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/META-INF/")){
						Arquivo.apagarDiretorio(new File(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/META-INF/"));
					}
					//Cria arquivo Jar
					try{
						CriarJar.criarArquivoJAR(new File(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/"+projeto.getNome()+".jar"), pastasCompactar, "tetris."+projeto.getNome().toLowerCase()+".Principal", System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome(), fileTetrisWorkspace);
					}catch(Exception exc){
						JDialogMensagem.exibirMensagem("Erro", Idioma.getTraducao("tetris.message_error_exporting_jar", jFramePrincipal)+"<br/>"+exc);
					}

					jLabelMensagem.setText(Idioma.getTraducao("tetris.exporting", jFramePrincipal));
					Arquivo.copiarArquivo(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/"+projeto.getNome()+".jar", jFileChooser.getSelectedFile().getPath()+"/"+projeto.getNome()+".jar");
				}
			}

		}else if (acao==ABRIR_PROJETO) {
			//Abre o projeto
			jLabelMensagem.setText(Idioma.getTraducao("tetris.opening", jFramePrincipal));
			//Aponta para o Explorador de Projetos
			JTetrisListExploradorDeProjetos jTetrisListExploradorDeProjetos = getjFramePrincipal().getjTetrisListExploradorDeProjetos();
			//Verifica se tem um projeto selecionado
			if(jTetrisListExploradorDeProjetos.getSelectedIndex() >= 0){
				//Abre o projeto
				Projeto.abrirProjeto(jTetrisListExploradorDeProjetos.getSelectedValue(), jFramePrincipal);
				//Fecha a janela aberta na área de trabalho
				if(getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getjInternalFrame()!=null){
					getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getjInternalFrame().dispose();
				}

				//Verificando impurezas na pasta src
				if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+jTetrisListExploradorDeProjetos.getSelectedValue()+"/src/")){
					//Limpa impurezas na pasta src do projeto 
					String[] listaPastas = Arquivo.listarPastas(System.getProperty("user.home")+"/TetrisWorkspace/"+jTetrisListExploradorDeProjetos.getSelectedValue()+"/src/");
					if(listaPastas!=null){
						for (int i = 0; i < listaPastas.length; i++) {
							if(!listaPastas[i].equals("tetris")){
								Arquivo.apagarDiretorio(new File(System.getProperty("user.home")+"/TetrisWorkspace/"+jTetrisListExploradorDeProjetos.getSelectedValue()+"/src/"+listaPastas[i]+"/"));
							}
						}
					}
					//Limpa impurezas na pasta src/tetris do projeto
					listaPastas = Arquivo.listarPastas(System.getProperty("user.home")+"/TetrisWorkspace/"+jTetrisListExploradorDeProjetos.getSelectedValue()+"/src/tetris/");
					if(listaPastas!=null){
						for (int i = 0; i < listaPastas.length; i++) {
							if(!listaPastas[i].equals(jTetrisListExploradorDeProjetos.getSelectedValue().toLowerCase())){
								Arquivo.apagarDiretorio(new File(System.getProperty("user.home")+"/TetrisWorkspace/"+jTetrisListExploradorDeProjetos.getSelectedValue()+"/src/tetris/"+listaPastas[i]+"/"));
							}
						}
					}
				}
				//Limpa área de trabalho
				getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().removeAll();
				getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().repaint();
				getjFramePrincipal().getjSyntaxTextPaneCodigoFonteGerado().setText("");
				getjFramePrincipal().getjTabbedPaneAreaDeTrabalho().setSelectedIndex(0);
			}else{
				JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_select_a_project", jFramePrincipal));
			}

		}else if (acao==SALVAR_PROJETO) {
			//Salva o projeto
			jLabelMensagem.setText(Idioma.getTraducao("tetris.saving", jFramePrincipal));

			if(projeto != null){	
				Projeto.salvarProjeto(getjFramePrincipal());
			}else{
				JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_open_a_project", jFramePrincipal));
			}

		}else{
			//Gerando código-fonte
			jLabelMensagem.setText(Idioma.getTraducao("tetris.generating_source", jFramePrincipal));
			//Verifica se há uma janela principal definida. Caso não, abre diálogo para o usuário definir
			if(projeto.getJanelaPrincipal()==null){
				jFramePrincipal.getjButtonJanelaPrincipal().doClick();
			}
			String classPath="";
			String[] componentesJar=null;
			
			if(projeto.getJanelaPrincipal()!=null){
				//Gera o código-fonte
				if(Projeto.gerarCodigoFonte(getjFramePrincipal())){
					//Executa o programa
					if (acao==EXECUTAR) {
						//Limpa diretório bin
						Arquivo.apagarDiretorio(new File(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/"));
						//Cria diretório bin
						Arquivo.criarPasta(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/");
						//Define classpath do projeto
						classPath=System.getProperty("path.separator")+System.getProperty("user.home")+"/TetrisWorkspace/"+System.getProperty("path.separator");
						//Copia componentes tetris para o projeto
						Arquivo.copiarPasta(System.getProperty("user.home")+"/TetrisWorkspace/tetris/", System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/tetris/");
						//Define vetor com componentes padões para cópia
						File[] fileTetrisWorkspace = new File[3];

						fileTetrisWorkspace[0] = new File(System.getProperty("user.home")+"/TetrisWorkspace/componentes/");
						fileTetrisWorkspace[1] = new File(System.getProperty("user.home")+"/TetrisWorkspace/org/");
						fileTetrisWorkspace[2] = new File(System.getProperty("user.home")+"/TetrisWorkspace/com/");

						jLabelMensagem.setText(Idioma.getTraducao("tetris.extracting_components", jFramePrincipal));
						//Verifica caminho de componentes externos do projeto
						if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/comp/")){
							//Lista componentes externos do projeto
							componentesJar = Arquivo.listarPastas(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/comp/");
							//Percorre vetor
							for (int i = 0; i < componentesJar.length; i++) {
								//Adiciona ao classpath do projeto
								classPath=classPath+System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/comp/"+componentesJar[i]+"/"+System.getProperty("path.separator");
							}
						}
						//Escreve comando de compilação no prompt de comando 
						System.out.println("\""+System.getProperty("user.home")+"/TetrisWorkspace/jdk/bin/javac\" -encoding utf8 -cp \""+System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/src/"+classPath+"\". -d \""+System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/\" \""+System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/src/tetris/"+projeto.getNome().toLowerCase()+"/Principal.java\" -Xstdout \""+System.getProperty("user.home")+"/TetrisWorkspace/logcomp.dabj\"");

						jLabelMensagem.setText(Idioma.getTraducao("tetris.compiling", jFramePrincipal));
						try {
							//Exclui arquivo de log
							if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/logcomp.dabj")){
								Arquivo.apagarArquivo(System.getProperty("user.home")+"/TetrisWorkspace/logcomp.dabj");
							}

							//Compilando
							Process process = Runtime.getRuntime().exec("\""+System.getProperty("user.home")+"/TetrisWorkspace/jdk/bin/javac\" -encoding utf8 -cp \""+System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/src/"+classPath+"\". -d \""+System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/\" \""+System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/src/tetris/"+projeto.getNome().toLowerCase()+"/Principal.java\" -Xstdout \""+System.getProperty("user.home")+"/TetrisWorkspace/logcomp.dabj\"");
							process.waitFor();
							//Enquanto a classe não foi gerada, faz a thread aguardar por 1s
							while(!Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/tetris/"+projeto.getNome().toLowerCase()+"/Principal.class")){
								try{

									Thread.sleep(1000);
									//Verificando arquivo de log
									if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/logcomp.dabj")){
										//Caso exista erro de compilação, fecha a janela e para o método
										if(!Arquivo.lerArquivo(System.getProperty("user.home")+"/TetrisWorkspace/logcomp.dabj").equals("")){
											dispose();
											return;
										}
									}
								}catch(Exception e){

								}
							}

							jLabelMensagem.setText(Idioma.getTraducao("tetris.copying_assets", jFramePrincipal));
							//Copiando recursos;
							if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/src/tetris/"+projeto.getNome().toLowerCase()+"/res/")){
								Arquivo.copiarPasta(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/src/tetris/"+projeto.getNome().toLowerCase()+"/res/", System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/tetris/"+projeto.getNome().toLowerCase()+"/res/");
							}

							//Excluindo pasta meta-inf
							if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/META-INF/")){
								Arquivo.apagarDiretorio(new File(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/META-INF/"));
							}

							jLabelMensagem.setText(Idioma.getTraducao("tetris.compressing", jFramePrincipal));
							
							//Mostrando comando no prompt
							System.out.println("\""+System.getProperty("user.home")+"/TetrisWorkspace/jdk/bin/java\" -cp \""+System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/"+classPath+"\".  \"tetris/"+projeto.getNome().toLowerCase()+"/Principal\"");
							
							//Executando
							jLabelMensagem.setText(Idioma.getTraducao("tetris.running", jFramePrincipal));

							Runtime.getRuntime().exec("\""+System.getProperty("user.home")+"/TetrisWorkspace/jdk/bin/java\" -cp \""+System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/"+classPath+"\".  \"tetris/"+projeto.getNome().toLowerCase()+"/Principal\"");
							

						} catch (Exception e) {
							// TODO: handle exception
							try{
								//Exclui arquivo de log
								if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/logcomp.dabj")){
									Arquivo.apagarArquivo(System.getProperty("user.home")+"/TetrisWorkspace/logcomp.dabj");
								}
								//Mostra comando no prompt
								System.out.println("javac -encoding utf8 -cp "+System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/src/"+classPath+". -d "+System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/ "+System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/src/tetris/"+projeto.getNome().toLowerCase()+"/Principal.java -Xstdout "+System.getProperty("user.home")+"/TetrisWorkspace/logcomp.dabj");
								//Compilando
								Process process = Runtime.getRuntime().exec("javac -encoding utf8 -cp "+System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/src/"+classPath+". -d "+System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/ "+System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/src/tetris/"+projeto.getNome().toLowerCase()+"/Principal.java -Xstdout "+System.getProperty("user.home")+"/TetrisWorkspace/logcomp.dabj");
								process.waitFor();
								//Verifica se a classe foi gerada. Caso não, faz a thread aguardar 1s
								while(!Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/tetris/"+projeto.getNome().toLowerCase()+"/Principal.class")){
									try{
										Thread.sleep(1000);
										//Verificando arquivo de log
										if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/logcomp.dabj")){
											//Caso exista erro de compilação, fecha a janela e encerra o método
											if(!Arquivo.lerArquivo(System.getProperty("user.home")+"/TetrisWorkspace/logcomp.dabj").equals("")){
												dispose();
												return;
											}
										}
									}catch(Exception exc){

									}
								}

								jLabelMensagem.setText(Idioma.getTraducao("tetris.copying_assets", jFramePrincipal));
								//Copiando recursos;
								if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/src/tetris/"+projeto.getNome().toLowerCase()+"/res/")){
									Arquivo.copiarPasta(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/src/tetris/"+projeto.getNome().toLowerCase()+"/res/", System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/tetris/"+projeto.getNome().toLowerCase()+"/res/");
								}

								//Excluindo pasta meta-inf
								if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/META-INF/")){
									Arquivo.apagarDiretorio(new File(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/META-INF/"));
								}
								jLabelMensagem.setText(Idioma.getTraducao("tetris.compressing", jFramePrincipal));

								//Mostra comando no prompt
								System.out.println("java -cp "+System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/"+classPath+". tetris/"+projeto.getNome().toLowerCase()+"/Principal");
								
								//Executando
								jLabelMensagem.setText(Idioma.getTraducao("tetris.running", jFramePrincipal));
								Runtime.getRuntime().exec("java -cp "+System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/bin/"+classPath+". tetris/"+projeto.getNome().toLowerCase()+"/Principal");								

							}catch(Exception exc){
								JDialogMensagem.exibirMensagem("Erro", Idioma.getTraducao("tetris.message_error_running", jFramePrincipal)+"<br/>"+exc);
							}
						}
					}else if (acao==EXPORTAR_JAVA) {
						//Exporta o codigo-fonte
						jLabelMensagem.setText(Idioma.getTraducao("tetris.exporting", jFramePrincipal));
						//Seleciona diretório destino
						JFileChooser jFileChooser = new JFileChooser();
						jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						if(jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
							//Copia pasta src do projeto
							Arquivo.copiarPasta(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/src/", jFileChooser.getSelectedFile().getPath()+"/"+projeto.getNome()+"/");
						}
					}

				}else{
					JDialogMensagem.exibirMensagem("Erro", Idioma.getTraducao("tetris.message_error_execution", jFramePrincipal)+" "+System.getProperty("user.home")+"/TetrisWorkspace/!");
				}
			}else{
				JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_main_window", jFramePrincipal));
			}
		}
		//Fecha a janela
		dispose();
	}
}
