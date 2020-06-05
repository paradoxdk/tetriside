/*
 *Representa janela do projeto
 *Classe de objetos serializables (graváveis em disco)e cloneables
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 25 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package tetris.modelo.componentes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.SwingConstants;

import br.com.analisasoftware.tetris.modelo.Arquivo;
import br.com.analisasoftware.tetris.modelo.Data;
import br.com.analisasoftware.tetris.modelo.Idioma;
import br.com.analisasoftware.tetris.modelo.Projeto;
import br.com.analisasoftware.tetris.visao.janelas.JDialogMensagem;
import br.com.analisasoftware.tetris.visao.janelas.JDialogNomeJanela;
import br.com.analisasoftware.tetris.visao.janelas.JFramePrincipal;
import componentes.modelo.Mascara;
import componentes.modelo.bancodedados.Coluna;
import componentes.modelo.bancodedados.Tabela;
import componentes.visao.JTetrisTextField;

public class Janela extends Componente  implements Cloneable, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Lista de componentes da janela
	private ArrayList<Componente> arrayListComponentes;
	//Flag que sinaliza se a janela sofreu alterações no projeto para auxiliar no salvamento de progresso
	private boolean alterado;
	//Lista para armazenar o estado da janela, para auxiliar no processo de Refazer e Desfazer junto a seu índice de posição
	private ArrayList<Janela> arrayListEstadoJanela;
	private int posicaoEstadoJanela;
	//Flag que sinaliza se a janela ja teve seu código-fonte gerado
	private boolean gerado;


	public Janela(String nome, ArrayList<Atributo> arrayListAtributos,
			ArrayList<Metodo> arrayListMetodos) {
		super(nome, arrayListAtributos, arrayListMetodos);
		arrayListComponentes = new ArrayList<Componente>();
		arrayListEstadoJanela = new ArrayList<Janela>();
		posicaoEstadoJanela=-1;
		alterado=false;
		gerado=false;
		// TODO Auto-generated constructor stub
	}

	public Janela(String nome, ArrayList<Atributo> arrayListAtributos,
			ArrayList<Metodo> arrayListMetodos, ArrayList<Componente> arrayListComponentes) {
		this(nome, arrayListAtributos, arrayListMetodos);
		this.arrayListComponentes=arrayListComponentes;

		// TODO Auto-generated constructor stub
	}

	//Gera o código-fonte da janela
	public void gerarCodigoFonte(JFramePrincipal jFramePrincipal){
		try {
			//Aponta para a instância do projeto aberto
			Projeto projeto = jFramePrincipal.getProjeto();
			//Define o arquivo de gravação em disco da janela
			File fileJanela = new File(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/src/tetris/"+projeto.getNome().toLowerCase()+"/visao/"+getNome()+".java");
			//Verifica se o arquivo existe
			if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/src/tetris/"+projeto.getNome().toLowerCase()+"/visao/"+getNome()+".java")){
				if(isGerado()){
					//Se a janela já foi gerada, finaliza o fluxo do método
					return;
				}else{
					//Se a janela ainda não foi gerada, exclui o arquivo antigo
					fileJanela.delete();
				}
			}
			//Cria diretório do código-fonte
			if(Arquivo.criarPasta(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/src/tetris/"+projeto.getNome().toLowerCase()+"/visao/")){
				//Captura o atributo que define o tipo da janela
				Atributo atributoTipo = getAtributo("Window");
				if(atributoTipo==null){
					atributoTipo = getAtributo("Tipo");
				}
				String tipo="JFrame";
				if(atributoTipo!=null){
					if(atributoTipo.getValor().equals("1")){
						tipo="JDialog";
					}else if(atributoTipo.getValor().equals("2")){
						tipo="JInternalFrame";
					}
				}


				//Cria variável de buffer de escrita
				BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileJanela), "UTF-8"));
				//Gera código-fonte
				bufferedWriter.write("package tetris."+projeto.getNome().toLowerCase()+".visao;");
				bufferedWriter.newLine();
				bufferedWriter.newLine();

				//Imports
				Atributo atributoImport = getAtributo("Import");
				if(atributoImport!=null){
					String itensImport =atributoImport.getValor();

					String[] itens = itensImport.split("/");
					for (int k = 0; k < itens.length; k++) {
						bufferedWriter.write("import "+itens[k]+";");
						bufferedWriter.newLine();

					}
				}

				bufferedWriter.write("import javax.swing."+tipo+";");
				bufferedWriter.newLine();
				bufferedWriter.write("import componentes.visao.*;");
				bufferedWriter.newLine();
				bufferedWriter.write("import componentes.modelo.bancodedados.*;");
				bufferedWriter.newLine();
				bufferedWriter.write("public class "+getNome()+" extends "+tipo+"{");
				bufferedWriter.newLine();
				//Cria variáveis que apontam para lista de componentes da janela
				ArrayList<Componente> arrayListComponentes = getArrayListComponentes();
				ArrayList<Componente> arrayListComponentesProcedures = new ArrayList<Componente>();
				ArrayList<Componente> arrayListComponentesVariables = new ArrayList<Componente>();
				//Percorre lista de componentes e gera seus códigos-fontes
				for (int i = 0; i < arrayListComponentes.size(); i++) {
					String tipoComponente="";
					switch (arrayListComponentes.get(i).getTipo()) {
					case "Rotulo":
						tipoComponente="javax.swing.JLabel";
						break;

					case "Botao":
						tipoComponente="JTetrisButton";
						break;

					case "Campo de checagem":
						tipoComponente="JTetrisCheckBox";
						break;

					case "Barra de menu":
						tipoComponente="javax.swing.JMenuBar";
						break;

					case "Menu":
						tipoComponente="javax.swing.JMenu";
						break;

					case "Menu Item":
						tipoComponente="javax.swing.JMenuItem";
						break;

					case "Separador":
						tipoComponente="javax.swing.JSeparator";
						break;

					case "Separator":
						tipoComponente="javax.swing.JSeparator";
						break;

					case "Painel":
						tipoComponente="JTetrisPanel";
						break;

					case "Barra de ferramentas":
						tipoComponente="JTetrisToolBar";
						break;

					case "Abas":
						tipoComponente="javax.swing.JTabbedPane";
						break;

					case "Tabela":
						tipoComponente="JTetrisTable";
						break;

					case "Campo de escolha":
						tipoComponente="JTetrisCampoDeEscolha";
						break;

					case "Caixa de combinacao":
						tipoComponente="JTetrisComboBox";
						break;

					case "Lista":
						tipoComponente="JTetrisScrolledList";
						break;

					case "TextArea":
						tipoComponente="JTetrisScrolledTextArea";
						break;

					case "EditorPane":
						tipoComponente="JTetrisScrolledEditorPane";
						break;

					case "Timer":
						tipoComponente="javax.swing.Timer";
						break;

					case "Procedure":
						tipoComponente="Procedure";
						arrayListComponentesProcedures.add(arrayListComponentes.get(i));
						break;

					case "Variable":
						tipoComponente="Variable";
						arrayListComponentesVariables.add(arrayListComponentes.get(i));
						break;

					case "Relatorio":
						tipoComponente="tetris.report.Report";

						break;

					default:
						tipoComponente="JTetrisTextField";
						if(arrayListComponentes.get(i).getAtributo("Mascara")!=null){
							if(arrayListComponentes.get(i).getAtributo("Mascara").getValor().equals("10")){
								tipoComponente="JTetrisDateField";
							}else if(arrayListComponentes.get(i).getAtributo("Mascara").getValor().equals("6")){
								tipoComponente="JTetrisPasswordField";
							}
						}else if(arrayListComponentes.get(i).getAtributo("Mask")!=null){
							if(arrayListComponentes.get(i).getAtributo("Mask").getValor().equals("10")){
								tipoComponente="JTetrisDateField";
							}else if(arrayListComponentes.get(i).getAtributo("Mask").getValor().equals("6")){
								tipoComponente="JTetrisPasswordField";
							}
						}

						break;
					}
					if((tipoComponente.equals("Procedure")==false) && (tipoComponente.equals("Variable")==false)){
						bufferedWriter.write("	private "+tipoComponente+" "+arrayListComponentes.get(i).getNome()+";");
						bufferedWriter.newLine();
					}
				}

				bufferedWriter.newLine();

				//Adicionando variaveis
				for (int i = 0; i < arrayListComponentesVariables.size(); i++) {
					bufferedWriter.write("	private "+arrayListComponentesVariables.get(i).getAtributo("Type").getValor()+" "+arrayListComponentesVariables.get(i).getNome()+";");
					bufferedWriter.newLine();
				}

				bufferedWriter.write("	private String retorno;");
				bufferedWriter.newLine();

				//Tabelas do banco de dados
				ArrayList<Tabela> arrayListTabelas = projeto.getBancoDeDados().getArrayListTabelas();
				//Metodo construtor
				if(projeto.getJanelaPrincipal().equals(this)){
					if(arrayListTabelas.size() > 0){
						bufferedWriter.write("	private componentes.modelo.bancodedados.BancoDeDados bancoDeDados;");
						bufferedWriter.newLine();
						bufferedWriter.newLine();
					}
					bufferedWriter.write("	public "+getNome()+" get"+getNome()+"(){");
					bufferedWriter.newLine();
					bufferedWriter.write("		return this;");
					bufferedWriter.newLine();
					bufferedWriter.write("	}");
					bufferedWriter.newLine();
					bufferedWriter.newLine();
					bufferedWriter.write("	public "+getNome()+"(){");
					bufferedWriter.newLine();
					bufferedWriter.write("		setDefaultCloseOperation("+tipo+".DO_NOTHING_ON_CLOSE);");
					bufferedWriter.newLine();
					bufferedWriter.newLine();

					//

					if(arrayListTabelas.size() > 0){
						bufferedWriter.write("		try{");
						bufferedWriter.newLine();
						bufferedWriter.write("			Class.forName(\""+projeto.getBancoDeDados().getDriver()+"\");");
						bufferedWriter.newLine();


						//

						bufferedWriter.write("		java.util.ArrayList<componentes.modelo.bancodedados.Tabela> arrayListTabelas = new java.util.ArrayList<componentes.modelo.bancodedados.Tabela>();");
						bufferedWriter.newLine();
						for (int j = 0; j < arrayListTabelas.size(); j++) {
							ArrayList<Coluna> arrayListColunas = arrayListTabelas.get(j).getArrayListColunas();
							if(j==0){
								bufferedWriter.write("		java.util.ArrayList<componentes.modelo.bancodedados.Coluna> arrayListColunas = new java.util.ArrayList<componentes.modelo.bancodedados.Coluna>();");
								bufferedWriter.newLine();
							}else{
								bufferedWriter.write("		arrayListColunas = new java.util.ArrayList<componentes.modelo.bancodedados.Coluna>();");
								bufferedWriter.newLine();
							}
							for (int j2 = 0; j2 < arrayListColunas.size(); j2++) {
								bufferedWriter.write("		arrayListColunas.add(new componentes.modelo.bancodedados.Coluna(\""+arrayListColunas.get(j2).getNome()+"\", \""+arrayListColunas.get(j2).getTipo()+"\"));");
								bufferedWriter.newLine();
							}
							bufferedWriter.write("		arrayListTabelas.add(new componentes.modelo.bancodedados.Tabela(\""+arrayListTabelas.get(j).getNome()+"\", arrayListColunas));");
							bufferedWriter.newLine();
						}
						bufferedWriter.write("		bancoDeDados = new componentes.modelo.bancodedados.BancoDeDados(\""+projeto.getBancoDeDados().getNome()+"\", arrayListTabelas);");
						bufferedWriter.newLine();

						bufferedWriter.write("			bancoDeDados.setDriver(\""+projeto.getBancoDeDados().getDriver()+"\");");
						bufferedWriter.newLine();

						bufferedWriter.write("			bancoDeDados.setConnectionString(\""+projeto.getBancoDeDados().getConnectionString()+"\");");
						bufferedWriter.newLine();

						bufferedWriter.write("			bancoDeDados.getConexao().close();");
						bufferedWriter.newLine();
						bufferedWriter.write("		}catch(Exception exc){");
						bufferedWriter.newLine();
						bufferedWriter.write("			exc.printStackTrace();");
						bufferedWriter.newLine();
						bufferedWriter.write("			System.exit(0);");
						bufferedWriter.newLine();
						bufferedWriter.write("		}");
						bufferedWriter.newLine();
					}
				}else{
					bufferedWriter.write("	private "+projeto.getJanelaPrincipal().getNome()+" "+Mascara.filtroVariavel(projeto.getJanelaPrincipal().getNome())+";");
					bufferedWriter.newLine();
					bufferedWriter.newLine();
					bufferedWriter.write("	public "+projeto.getJanelaPrincipal().getNome()+" get"+projeto.getJanelaPrincipal().getNome()+"(){");
					bufferedWriter.newLine();
					bufferedWriter.write("		return "+Mascara.filtroVariavel(projeto.getJanelaPrincipal().getNome())+";");
					bufferedWriter.newLine();
					bufferedWriter.write("	}");
					bufferedWriter.newLine();
					bufferedWriter.newLine();

					bufferedWriter.write("	public "+getNome()+" get"+getNome()+"(){");
					bufferedWriter.newLine();
					bufferedWriter.write("		return this;");
					bufferedWriter.newLine();
					bufferedWriter.write("	}");
					bufferedWriter.newLine();
					bufferedWriter.newLine();

					bufferedWriter.write("	public "+getNome()+"("+projeto.getJanelaPrincipal().getNome()+" "+Mascara.filtroVariavel(projeto.getJanelaPrincipal().getNome())+"){");
					bufferedWriter.newLine();
					bufferedWriter.write("		super();");
					bufferedWriter.newLine();
					bufferedWriter.write("		this."+Mascara.filtroVariavel(projeto.getJanelaPrincipal().getNome())+"="+Mascara.filtroVariavel(projeto.getJanelaPrincipal().getNome())+";");
					bufferedWriter.newLine();
				}

				if(tipo.equals("JInternalFrame")){
					bufferedWriter.write("		addInternalFrameListener( new javax.swing.event.InternalFrameAdapter(){");
					bufferedWriter.newLine();
					bufferedWriter.write("			public void internalFrameClosing(javax.swing.event.InternalFrameEvent arg0) {");
					bufferedWriter.newLine();
				}else{
					bufferedWriter.write("		addWindowListener( new java.awt.event.WindowAdapter(){");
					bufferedWriter.newLine();
					bufferedWriter.write("			public void windowClosing(java.awt.event.WindowEvent arg0){");
					bufferedWriter.newLine();
				}
				bufferedWriter.write("				fecharJanela();");
				bufferedWriter.newLine();

				bufferedWriter.write("			}");
				bufferedWriter.newLine();
				bufferedWriter.write("		});");
				bufferedWriter.newLine();

				//Carregando propriedades da janela
				ArrayList<Atributo> arrayListAtributos = getArrayListAtributos();
				int x=0, y=0, width=0, height=0;
				String extendedState="", defaultCloseOperation="DISPOSE_ON_CLOSE";
				int undecorated=0;
				String closable="true", iconifiable="true", resizable="true", locationRelativeTo="";

				//Adicionando container principal
				boolean temJanelaInterna=false;
				if(projeto.getJanelaPrincipal().equals(this)){
					//Caso exista alguma janela interna, adiciona um JDesktopPane.

					ArrayList<Janela> arrayListJanelas = projeto.getArrayListJanelas();

					for (int i = 0; i < arrayListJanelas.size(); i++) {
						if(arrayListJanelas.get(i).getTipo().equals("2")){
							temJanelaInterna=true;
							break;
						}
					}
				}

				if (temJanelaInterna) {
					bufferedWriter.write("		setContentPane(new javax.swing.JDesktopPane()");
				}else{
					bufferedWriter.write("		setContentPane(new JTetrisPanel(null)");
				}

				//
				//Verificando OnPaint
				if(getMetodosFuncao("OnPaint").size() > 0){
					bufferedWriter.write("{");
					bufferedWriter.newLine();
					bufferedWriter.write("			@Override");
					bufferedWriter.newLine();
					bufferedWriter.write("			public void paintComponent(java.awt.Graphics g){");
					bufferedWriter.newLine();
					bufferedWriter.write("				super.paintComponent(g);");
					bufferedWriter.newLine();
					ArrayList<Metodo> arrayListMetodos = getMetodosFuncao("OnPaint");
					for (int i = 0; i < arrayListMetodos.size(); i++) {
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), this, this, jFramePrincipal);
					}

					bufferedWriter.write("			}");
					bufferedWriter.newLine();
					bufferedWriter.write("		}");


				}

				if (temJanelaInterna) {
					bufferedWriter.write(");");
					bufferedWriter.newLine();
					bufferedWriter.write("		getContentPane().setBackground(javax.swing.UIManager.getColor(\"Panel.background\"));");
					bufferedWriter.newLine();
					bufferedWriter.write("		setLayout(null);");
					bufferedWriter.newLine();
				}else{
					bufferedWriter.write(");");
					bufferedWriter.newLine();
				}

				if(projeto.getJanelaPrincipal().equals(this)==false){
					if(tipo.equals("JInternalFrame")){
						bufferedWriter.write("		get"+projeto.getJanelaPrincipal().getNome()+"().getContentPane().add(this);");
						bufferedWriter.newLine();
					}
				}

				for (int i = 0; i < arrayListAtributos.size(); i++) {
					switch (arrayListAtributos.get(i).getNome()) {
					case "Title":
						bufferedWriter.write("		setTitle(\""+arrayListAtributos.get(i).getValor()+"\");");
						bufferedWriter.newLine();
						break;
					case "x":
						x = Integer.parseInt(arrayListAtributos.get(i).getValor());
						break;
					case "y":
						y = Integer.parseInt(arrayListAtributos.get(i).getValor());
						break;
					case "width":
						width=Integer.parseInt(arrayListAtributos.get(i).getValor());
						break;

					case "height":
						height=Integer.parseInt(arrayListAtributos.get(i).getValor());
						break;

					case "Background":
						int red=Integer.parseInt(arrayListAtributos.get(i).getValor().substring(0, 3));
						int green=Integer.parseInt(arrayListAtributos.get(i).getValor().substring(5, 8));
						int blue=Integer.parseInt(arrayListAtributos.get(i).getValor().substring(10, 13));

						bufferedWriter.write("		getContentPane().setBackground(new java.awt.Color("+red+", "+green+", "+blue+"));");
						bufferedWriter.newLine();

						break;
					case "ExtendedState":
						if(tipo.equals("JFrame")){
							extendedState = "		setExtendedState("+arrayListAtributos.get(i).getValor()+");";
						}
						break;

					case "DefaultCloseOperation":

						defaultCloseOperation=arrayListAtributos.get(i).getValor();


						break;

					case "LocationRelativeTo":
						if(tipo.equals("JInternalFrame")==false){
							locationRelativeTo = "		setLocationRelativeTo("+arrayListAtributos.get(i).getValor()+");";

						}
						break;
					case "Closable":
						if(arrayListAtributos.get(i).getValor().equals("false")){
							undecorated++;
						}
						closable=arrayListAtributos.get(i).getValor();

						break;

					case "Iconifiable":
						if(arrayListAtributos.get(i).getValor().equals("false")){
							undecorated++;
						}
						iconifiable=arrayListAtributos.get(i).getValor();

						break;
					case "Resizable":
						if(arrayListAtributos.get(i).getValor().equals("false")){
							undecorated++;
						}
						resizable=arrayListAtributos.get(i).getValor();

						break;

					case "Modal":
						if(tipo.equals("JDialog")){
							bufferedWriter.write("		setModal("+arrayListAtributos.get(i).getValor()+");");
							bufferedWriter.newLine();
						}
						break;

					case "Icon":
						if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/res/"+arrayListAtributos.get(i).getValor())){
							if(getTipo().equals("2")){

								bufferedWriter.write("		setFrameIcon(new javax.swing.ImageIcon(getClass().getResource(\"/tetris/"+projeto.getNome().toLowerCase() +"/res/"+arrayListAtributos.get(i).getValor()+"\")));");
								bufferedWriter.newLine();

							}else{

								bufferedWriter.write("		setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource(\"/tetris/"+projeto.getNome().toLowerCase()+"/res/"+arrayListAtributos.get(i).getValor()+"\")));");
								bufferedWriter.newLine();

							}
						}

						break;

					case "Cursor":
						bufferedWriter.write("		setCursor(new java.awt.Cursor(java.awt.Cursor."+arrayListAtributos.get(i).getValor()+"));");
						bufferedWriter.newLine();

						break;

					case "Retorno":

						bufferedWriter.write("		setRetorno(\""+arrayListAtributos.get(i).getValor()+"\");");
						bufferedWriter.newLine();

						break;

					}


				}

				if(projeto.getJanelaPrincipal().equals(this)==false){
					bufferedWriter.write("		setDefaultCloseOperation("+tipo+"."+defaultCloseOperation+");");
					bufferedWriter.newLine();
				}

				if(tipo.equals("JInternalFrame")){
					bufferedWriter.write("		setClosable("+closable+");");
					bufferedWriter.newLine();



					bufferedWriter.write("		setIconifiable("+iconifiable+");");
					bufferedWriter.newLine();


				}

				bufferedWriter.write("		setResizable("+resizable+");");
				bufferedWriter.newLine();


				bufferedWriter.write("		setBounds("+x+", "+y+", "+width+", "+height+");");
				bufferedWriter.newLine();

				if(undecorated==3){
					if(tipo.equals("JInternalFrame")){
						bufferedWriter.write("		((javax.swing.plaf.basic.BasicInternalFrameUI)getUI()).setNorthPane(null);");
						bufferedWriter.newLine();

						bufferedWriter.write("		setBorder(null);");
						bufferedWriter.newLine();
					}else{
						bufferedWriter.write("		setUndecorated(true);");
						bufferedWriter.newLine();
					}
				}

				//Location Relative To
				bufferedWriter.write(locationRelativeTo);
				bufferedWriter.newLine();

				//Adicionando componentes
				for (int i = 0; i < arrayListComponentes.size(); i++) {
					if(arrayListComponentes.get(i).getTipo().equals("Procedure")==false){
						if(arrayListComponentes.get(i).getTipo().equals("Variable")==false){
							arrayListComponentes.get(i).gerarCodigoFonte(bufferedWriter, this, jFramePrincipal);
						}else{
							if(arrayListComponentes.get(i).getAtributo("Type").getValor().equals("String")){
								bufferedWriter.write("		"+arrayListComponentes.get(i).getNome()+" = \""+arrayListComponentes.get(i).getAtributo("Value").getValor()+"\";");
								bufferedWriter.newLine();
							}else if(arrayListComponentes.get(i).getAtributo("Type").getValor().equals("char")){
								bufferedWriter.write("		"+arrayListComponentes.get(i).getNome()+" = '"+arrayListComponentes.get(i).getAtributo("Value").getValor()+"';");
								bufferedWriter.newLine();
							}else{
								bufferedWriter.write("		"+arrayListComponentes.get(i).getNome()+" = "+arrayListComponentes.get(i).getAtributo("Value").getValor()+";");
								bufferedWriter.newLine();
							}
						}
					}
				}

				//Definindo o parent do componente
				String pai = "";
				for (int i = 0; i < arrayListComponentes.size(); i++) {
					pai = "getContentPane()";

					if(arrayListComponentes.get(i).getAtributo("Pai")!=null){
						if(arrayListComponentes.get(i).getAtributo("Pai").getValor().equals(getNome())==false){
							pai=arrayListComponentes.get(i).getAtributo("Pai").getValor();
						}
					}

					//Se for uma barra de menu
					if(arrayListComponentes.get(i).getTipo().equals("Barra de menu")){
						bufferedWriter.write("		setJMenuBar("+arrayListComponentes.get(i).getNome()+");");
					}else{
						//Se nao..
						//Se nao for um Timer,Procedure, Variable ou Report
						if((arrayListComponentes.get(i).getTipo().equals("Timer")==false) && (arrayListComponentes.get(i).getTipo().equals("Procedure")==false)
								&& (arrayListComponentes.get(i).getTipo().equals("Relatorio")==false) && (arrayListComponentes.get(i).getTipo().equals("Variable")==false)){

							//Capturando o tipo do Pai
							Componente componentePai = getComponente(pai);
							String tipoPai = "";
							if(componentePai!=null){
								tipoPai=componentePai.getTipo();
							}

							//Se o pai for uma TabbedPane
							if(tipoPai.equals("Abas")){
								String tituloAbas="";
								Atributo atributoTituloAbas = arrayListComponentes.get(i).getAtributo("TituloAbas");
								if(atributoTituloAbas!=null){
									tituloAbas = atributoTituloAbas.getValor();
								}
								bufferedWriter.write("		"+pai+".addTab(\""+tituloAbas+"\", "+arrayListComponentes.get(i).getNome()+");");

							}else{

								//Se nao
								bufferedWriter.write("		"+pai+".add("+arrayListComponentes.get(i).getNome()+");");
							}
						}
					}
					bufferedWriter.newLine();
				}

				for(int i=0; i<arrayListComponentes.size(); i++){
					if(arrayListComponentes.get(i).getTipo().equals("Abas")){
						Atributo atributoTab = arrayListComponentes.get(i).getAtributo("SelectedIndex");
						if(atributoTab!=null){
							bufferedWriter.write("		"+arrayListComponentes.get(i).getNome()+".setSelectedIndex("+atributoTab.getValor()+");");
							bufferedWriter.newLine();
						}
					}
				}

				//Adicionando eventos
				bufferedWriter.newLine();
				//Verificando o OnResize
				if(getMetodosFuncao("OnResize").size() > 0){
					bufferedWriter.write("		getContentPane().addComponentListener( new java.awt.event.ComponentAdapter(){");
					bufferedWriter.newLine();

					bufferedWriter.write("			public void componentResized(java.awt.event.ComponentEvent arg0){");
					bufferedWriter.newLine();
					ArrayList<Metodo> arrayListMetodos = getMetodosFuncao("OnResize");
					for (int i = 0; i < arrayListMetodos.size(); i++) {
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), this, this, jFramePrincipal);
					}
					bufferedWriter.write("			}");
					bufferedWriter.newLine();

					bufferedWriter.write("		});");
					bufferedWriter.newLine();
				}

				//Verificando o KeyListener
				if((getMetodosFuncao("OnKeyReleased").size() > 0) || (getMetodosFuncao("OnKeyPressed").size() > 0)
						|| (getMetodosFuncao("OnKeyTyped").size() > 0)){
					bufferedWriter.write("		getContentPane().addKeyListener( new java.awt.event.KeyAdapter(){");
					bufferedWriter.newLine();

					//Verificando keyReleased
					ArrayList<Metodo> arrayListMetodos = getMetodosFuncao("OnKeyReleased");
					if(arrayListMetodos.size() > 0){
						bufferedWriter.write("			public void keyReleased(java.awt.event.KeyEvent arg0){");
						bufferedWriter.newLine();

						for (int i = 0; i < arrayListMetodos.size(); i++) {
							//Verificando tecla
							if (!arrayListMetodos.get(i).getTipo().equals("void")){
								bufferedWriter.write("				if(arg0.getKeyCode() == java.awt.event.KeyEvent.VK_" + arrayListMetodos.get(i).getTipo() + "){");
								bufferedWriter.newLine();
							}

							Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), this, this, jFramePrincipal);

							if (!arrayListMetodos.get(i).getTipo().equals("void")){
								bufferedWriter.write("				}");
								bufferedWriter.newLine();
							}
						}
						bufferedWriter.write("			}");
						bufferedWriter.newLine();
					}

					//Verificando keyPressed
					arrayListMetodos = getMetodosFuncao("OnKeyPressed");
					if(arrayListMetodos.size() > 0){
						bufferedWriter.write("			public void keyPressed(java.awt.event.KeyEvent arg0){");
						bufferedWriter.newLine();

						for (int i = 0; i < arrayListMetodos.size(); i++) {
							//Verificando tecla
							if (!arrayListMetodos.get(i).getTipo().equals("void")){
								bufferedWriter.write("				if(arg0.getKeyCode() == java.awt.event.KeyEvent.VK_" + arrayListMetodos.get(i).getTipo() + "){");
								bufferedWriter.newLine();
							}

							Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), this, this, jFramePrincipal);

							//Verificando tecla
							if (!arrayListMetodos.get(i).getTipo().equals("void")){
								bufferedWriter.write("				}");
								bufferedWriter.newLine();
							}
						}
						bufferedWriter.write("			}");
						bufferedWriter.newLine();
					}

					//Verificando keyTyped
					arrayListMetodos = getMetodosFuncao("OnKeyTyped");
					if(arrayListMetodos.size() > 0){
						bufferedWriter.write("			public void keyTyped(java.awt.event.KeyEvent arg0){");
						bufferedWriter.newLine();

						for (int i = 0; i < arrayListMetodos.size(); i++) {
							//Verificando tecla
							if (!arrayListMetodos.get(i).getTipo().equals("void")){
								bufferedWriter.write("				if(arg0.getKeyCode() == java.awt.event.KeyEvent.VK_" + arrayListMetodos.get(i).getTipo() + "){");
								bufferedWriter.newLine();
							}

							Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), this, this, jFramePrincipal);

							//Verificando tecla
							if (!arrayListMetodos.get(i).getTipo().equals("void")){
								bufferedWriter.write("				}");
								bufferedWriter.newLine();
							}
						}
						bufferedWriter.write("			}");
						bufferedWriter.newLine();
					}


					bufferedWriter.write("		});");
					bufferedWriter.newLine();
				}
				//

				//Verificando o MouseListener
				if((getMetodosFuncao("OnClick").size() > 0) || (getMetodosFuncao("OnMouseReleased").size() > 0) || (getMetodosFuncao("OnMousePressed").size() > 0)
						|| (getMetodosFuncao("OnMouseEntered").size() > 0) || (getMetodosFuncao("OnMouseExited").size() > 0)){
					bufferedWriter.write("		getContentPane().addMouseListener( new java.awt.event.MouseAdapter(){");
					bufferedWriter.newLine();

					//Verificando mouseClicked
					ArrayList<Metodo> arrayListMetodos = getMetodosFuncao("OnClick");
					if(arrayListMetodos.size() > 0){
						bufferedWriter.write("			public void mouseClicked(java.awt.event.MouseEvent arg0){");
						bufferedWriter.newLine();

						for (int i = 0; i < arrayListMetodos.size(); i++) {
							Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), this, this, jFramePrincipal);
						}
						bufferedWriter.write("			}");
						bufferedWriter.newLine();
					}

					//Verificando mouseReleased
					arrayListMetodos = getMetodosFuncao("OnMouseReleased");
					if(arrayListMetodos.size() > 0){
						bufferedWriter.write("			public void mouseReleased(java.awt.event.MouseEvent arg0){");
						bufferedWriter.newLine();

						for (int i = 0; i < arrayListMetodos.size(); i++) {
							Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), this, this, jFramePrincipal);
						}
						bufferedWriter.write("			}");
						bufferedWriter.newLine();
					}

					//Verificando mousePressed
					arrayListMetodos = getMetodosFuncao("OnMousePressed");
					if(arrayListMetodos.size() > 0){
						bufferedWriter.write("			public void mousePressed(java.awt.event.MouseEvent arg0){");
						bufferedWriter.newLine();

						for (int i = 0; i < arrayListMetodos.size(); i++) {
							Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), this, this, jFramePrincipal);
						}
						bufferedWriter.write("			}");
						bufferedWriter.newLine();
					}

					//Verificando mouseEntered
					arrayListMetodos = getMetodosFuncao("OnMouseEntered");
					if(arrayListMetodos.size() > 0){
						bufferedWriter.write("			public void mouseEntered(java.awt.event.MouseEvent arg0){");
						bufferedWriter.newLine();

						for (int i = 0; i < arrayListMetodos.size(); i++) {
							Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), this, this, jFramePrincipal);
						}
						bufferedWriter.write("			}");
						bufferedWriter.newLine();
					}

					//Verificando mouseExited
					arrayListMetodos = getMetodosFuncao("OnMouseExited");
					if(arrayListMetodos.size() > 0){
						bufferedWriter.write("			public void mouseExited(java.awt.event.MouseEvent arg0){");
						bufferedWriter.newLine();

						for (int i = 0; i < arrayListMetodos.size(); i++) {
							Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), this, this, jFramePrincipal);
						}
						bufferedWriter.write("			}");
						bufferedWriter.newLine();
					}

					bufferedWriter.write("		});");
					bufferedWriter.newLine();
				}


				//Verificando o MouseMotionListener
				if((getMetodosFuncao("OnMouseMoved").size() > 0) || (getMetodosFuncao("OnMouseDragged").size() > 0)){
					bufferedWriter.write("		getContentPane().addMouseMotionListener( new java.awt.event.MouseMotionAdapter(){");
					bufferedWriter.newLine();

					//Verificando mouseMoved
					ArrayList<Metodo> arrayListMetodos = getMetodosFuncao("OnMouseMoved");
					if(arrayListMetodos.size() > 0){
						bufferedWriter.write("			public void mouseMoved(java.awt.event.MouseEvent arg0){");
						bufferedWriter.newLine();

						for (int i = 0; i < arrayListMetodos.size(); i++) {
							Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), this, this, jFramePrincipal);
						}
						bufferedWriter.write("			}");
						bufferedWriter.newLine();
					}

					//Verificando mouseDragged
					arrayListMetodos = getMetodosFuncao("OnMouseDragged");
					if(arrayListMetodos.size() > 0){
						bufferedWriter.write("			public void mouseDragged(java.awt.event.MouseEvent arg0){");
						bufferedWriter.newLine();

						for (int i = 0; i < arrayListMetodos.size(); i++) {
							Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), this, this, jFramePrincipal);
						}
						bufferedWriter.write("			}");
						bufferedWriter.newLine();
					}


					bufferedWriter.write("		});");
					bufferedWriter.newLine();
				}
				//

				//Verificando o MouseWheelListener
				if((getMetodosFuncao("OnMouseWheelMoved").size() > 0)){
					bufferedWriter.write("		getContentPane().addMouseWheelListener( new java.awt.event.MouseWheelListener(){");
					bufferedWriter.newLine();

					//Verificando mouseWheelMoved
					ArrayList<Metodo> arrayListMetodos = getMetodosFuncao("OnMouseWheelMoved");
					if(arrayListMetodos.size() > 0){
						bufferedWriter.write("			public void mouseWheelMoved(java.awt.event.MouseWheelEvent arg0){");
						bufferedWriter.newLine();

						for (int i = 0; i < arrayListMetodos.size(); i++) {
							Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), this, this, jFramePrincipal);
						}
						bufferedWriter.write("			}");
						bufferedWriter.newLine();
					}


					bufferedWriter.write("		});");
					bufferedWriter.newLine();
				}

				//Verificando o FocusListener
				if((getMetodosFuncao("OnFocusGained").size() > 0) || (getMetodosFuncao("OnFocusLost").size() > 0)){
					bufferedWriter.write("		getContentPane().addFocusListener( new java.awt.event.FocusAdapter(){");
					bufferedWriter.newLine();

					//Verificando focusGained
					ArrayList<Metodo> arrayListMetodos = getMetodosFuncao("OnFocusGained");
					if(arrayListMetodos.size() > 0){
						bufferedWriter.write("			public void focusGained(java.awt.event.FocusEvent arg0){");
						bufferedWriter.newLine();

						for (int i = 0; i < arrayListMetodos.size(); i++) {
							Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), this, this, jFramePrincipal);
						}
						bufferedWriter.write("			}");
						bufferedWriter.newLine();
					}

					//Verificando focusLost
					arrayListMetodos = getMetodosFuncao("OnFocusLost");
					if(arrayListMetodos.size() > 0){
						bufferedWriter.write("			public void focusLost(java.awt.event.FocusEvent arg0){");
						bufferedWriter.newLine();

						for (int i = 0; i < arrayListMetodos.size(); i++) {
							Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), this, this, jFramePrincipal);
						}
						bufferedWriter.write("			}");
						bufferedWriter.newLine();
					}


					bufferedWriter.write("		});");
					bufferedWriter.newLine();
				}
				//
				//

				//Metodo onCreate
				//Verificando o OnCreate
				if(getMetodosFuncao("OnCreate").size() > 0){
					bufferedWriter.newLine();
					ArrayList<Metodo> arrayListMetodos = getMetodosFuncao("OnCreate");
					for (int i = 0; i < arrayListMetodos.size(); i++) {
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), this, this, jFramePrincipal);
					}

				}

				bufferedWriter.newLine();
				bufferedWriter.write("	}");
				bufferedWriter.newLine();
				//Fecha metodo construtor

				//Abre metodo init
				bufferedWriter.write("	public void init(){");
				bufferedWriter.newLine();
				//Verificando o OnShow
				if(getMetodosFuncao("OnShow").size() > 0){

					ArrayList<Metodo> arrayListMetodos = getMetodosFuncao("OnShow");
					for (int i = 0; i < arrayListMetodos.size(); i++) {
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), this, this, jFramePrincipal);
					}

					bufferedWriter.newLine();
				}
				bufferedWriter.write("		setVisible(true);");
				bufferedWriter.newLine();

				bufferedWriter.write(extendedState);
				bufferedWriter.newLine();
				bufferedWriter.write("	}");
				bufferedWriter.newLine();

				//Fecha metodo init

				//Abre metodo init com retorno
				bufferedWriter.write("	public String init(String retorno){");
				bufferedWriter.newLine();
				//Verificando o OnShow

				bufferedWriter.write("		setRetorno(retorno);");
				bufferedWriter.newLine();

				bufferedWriter.write("		this.init();");
				bufferedWriter.newLine();

				bufferedWriter.write("		return getRetorno();");
				bufferedWriter.newLine();

				bufferedWriter.write("	}");
				bufferedWriter.newLine();
				//Fecha metodo init

				//Cria metodos getters e setters de variable
				for (int i = 0; i < arrayListComponentesVariables.size(); i++) {
					bufferedWriter.write("	public void set"+arrayListComponentesVariables.get(i).getNome()+"("+arrayListComponentesVariables.get(i).getAtributo("Type").getValor()+" "+arrayListComponentesVariables.get(i).getNome()+"){");
					bufferedWriter.newLine();

					bufferedWriter.write("		this."+arrayListComponentesVariables.get(i).getNome()+" = "+arrayListComponentesVariables.get(i).getNome()+";");
					bufferedWriter.newLine();

					bufferedWriter.write("	}");
					bufferedWriter.newLine();

					bufferedWriter.newLine();

					bufferedWriter.write("	public "+arrayListComponentesVariables.get(i).getAtributo("Type").getValor()+" get"+arrayListComponentesVariables.get(i).getNome()+"(){");
					bufferedWriter.newLine();

					bufferedWriter.write("		return "+arrayListComponentesVariables.get(i).getNome()+";");
					bufferedWriter.newLine();

					bufferedWriter.write("	}");
					bufferedWriter.newLine();
				}

				//Abre metodo fecharJanela
				bufferedWriter.newLine();
				bufferedWriter.write("	public void fecharJanela(){");
				bufferedWriter.newLine();

				//CloseQuestion
				Atributo atributoCloseQuestion = getAtributo("CloseQuestion");

				if(atributoCloseQuestion!=null){
					if(!atributoCloseQuestion.getValor().equals("")){
						bufferedWriter.write("		if(javax.swing.JOptionPane.showConfirmDialog(get"+getNome()+"(), \""+atributoCloseQuestion.getValor()+"\", \""+Idioma.getTraducao("tetris.message", jFramePrincipal)+"\", javax.swing.JOptionPane.YES_NO_OPTION)==javax.swing.JOptionPane.YES_OPTION){");
						bufferedWriter.newLine();
					}
				}

				//Verificando o OnClose
				if(getMetodosFuncao("OnClose").size() > 0 ){

					ArrayList<Metodo> arrayListMetodos = getMetodosFuncao("OnClose");
					for (int i = 0; i < arrayListMetodos.size(); i++) {
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), this, this, jFramePrincipal);
					}

					bufferedWriter.newLine();
				}

				if(projeto.getJanelaPrincipal().equals(this)){
					bufferedWriter.write("			System.exit(0);");
					bufferedWriter.newLine();
				}else{
					if(defaultCloseOperation.equals("DO_NOTHING_ON_CLOSE")==false){
						bufferedWriter.write("		dispose();");
						bufferedWriter.newLine();
					}else{
						if(atributoCloseQuestion!=null){
							if(!atributoCloseQuestion.getValor().equals("")){
								bufferedWriter.write("		dispose();");
								bufferedWriter.newLine();
							}
						}
					}
				}

				//Fecha CloseQuestion
				if(atributoCloseQuestion!=null){
					if(!atributoCloseQuestion.getValor().equals("")){
						bufferedWriter.write("		}");
						bufferedWriter.newLine();
						bufferedWriter.newLine();
					}
				}

				bufferedWriter.write("	}");
				bufferedWriter.newLine();

				//Adicionando Procedures
				for (int i = 0; i < arrayListComponentesProcedures.size(); i++) {
					arrayListComponentesProcedures.get(i).gerarCodigoFonte(bufferedWriter, this, jFramePrincipal);
				}

				if(arrayListTabelas.size() > 0){
					if(projeto.getJanelaPrincipal().equals(this)){

						bufferedWriter.write("	public componentes.modelo.bancodedados.BancoDeDados getBancoDeDados(){");
						bufferedWriter.newLine();
						bufferedWriter.write("		return bancoDeDados;");
						bufferedWriter.newLine();
						bufferedWriter.write("	}");
						bufferedWriter.newLine();
						bufferedWriter.newLine();

					}

					bufferedWriter.write("	public componentes.modelo.bancodedados.BancoDeDados getDB(){");
					bufferedWriter.newLine();
					bufferedWriter.write("		return get"+projeto.getJanelaPrincipal().getNome()+"().getBancoDeDados();");
					bufferedWriter.newLine();
					bufferedWriter.write("	}");
					bufferedWriter.newLine();
				}

				//Metodo get de retorno
				bufferedWriter.write("	public String getRetorno(){");
				bufferedWriter.newLine();

				bufferedWriter.write("		return retorno;");
				bufferedWriter.newLine();

				bufferedWriter.write("	}");
				bufferedWriter.newLine();
				bufferedWriter.newLine();

				bufferedWriter.write("	public String getReturn(){");
				bufferedWriter.newLine();

				bufferedWriter.write("		return retorno;");
				bufferedWriter.newLine();

				bufferedWriter.write("	}");
				bufferedWriter.newLine();

				//Metodo set de retorno
				bufferedWriter.write("	public void setRetorno(String retorno){");
				bufferedWriter.newLine();

				bufferedWriter.write("		this.retorno=retorno;");
				bufferedWriter.newLine();

				bufferedWriter.write("	}");
				bufferedWriter.newLine();
				bufferedWriter.newLine();

				bufferedWriter.write("	public void setReturn(String retorno){");
				bufferedWriter.newLine();

				bufferedWriter.write("		this.retorno=retorno;");
				bufferedWriter.newLine();

				bufferedWriter.write("	}");
				bufferedWriter.newLine();

				bufferedWriter.newLine();
				bufferedWriter.write("}");



				bufferedWriter.flush();
				bufferedWriter.close();

				setGerado(true);

				if(jFramePrincipal.getjDesktopPaneAreaDeTrabalho().getJanela()!=null){
					if(jFramePrincipal.getjDesktopPaneAreaDeTrabalho().getJanela().equals(this)){
						setGerado(false);
					}
				}
			}
		} catch (Exception exc) {
			// TODO: handle exception
			exc.printStackTrace();
		}



	}

	//Cria uma janela a partir de campos de uma tabela do banco de dados e retorna true, caso sucesso, e false, caso contrário
	public static boolean criarJanela(String[] campos, JFramePrincipal jFramePrincipal, String modelo){
		//Variável de retorno
		boolean retorno=false;
		//Aponta para a tabela no objeto BancoDeDados do projeto aberto
		Tabela tabela = jFramePrincipal.getProjeto().getBancoDeDados().getTabela(""+jFramePrincipal.getjComboBoxTabelaGerenciadorDeBancoDeDados().getSelectedItem());
		ArrayList<Componente> arrayListComponentes = new ArrayList<Componente>();
		int maiorTamanho=100;

		//Verifica, caso o modelo seja doisemum, se há chave primaria selecionada

		//Captura chaves primarias da tabela
		ArrayList<Coluna> arrayListColunas = tabela.getPrimaryKey();
		String primaryKey=""+arrayListColunas.get(0).getNome();
		for (int i = 1; i < arrayListColunas.size(); i++) {
			primaryKey=primaryKey+", "+arrayListColunas.get(i).getNome();
		}
		//Cria janela do modelo dois em um
		if(modelo.equals("doisemum")){
			int countPrimaryKey=0;
			//Conta as chaves primárias
			for (int i = 0; i < arrayListColunas.size(); i++) {
				for (int j = 0; j < campos.length; j++) {
					if(arrayListColunas.get(i).getNome().equals(campos[j])){
						countPrimaryKey++;
					}
				}
			}
			//Verifica se as chaves primárias estão entre os campos selecionados
			if(countPrimaryKey!=arrayListColunas.size()){
				JDialogMensagem.exibirMensagem("Erro", Idioma.getTraducao("tetris.message_select_primary_key", jFramePrincipal));
				return false;
			}
		}
		//Captura nome e título da nova janela
		JDialogNomeJanela jDialogNomeJanela = new JDialogNomeJanela(jFramePrincipal);
		jDialogNomeJanela.getjTetrisTextFieldNome().setText(Mascara.filtroClasse(tabela.getNome()));
		jDialogNomeJanela.getjTetrisTextFieldTitulo().setText(Mascara.filtroClasse(tabela.getNome()));
		String[] nomeTituloJanela = jDialogNomeJanela.init();

		if(nomeTituloJanela[0]!=null){
			boolean cria=true;
			//Verifica se o nome da janela já está sendo usado
			ArrayList<Janela> arrayListJanela = jFramePrincipal.getProjeto().getArrayListJanelas();
			for (int i = 0; i < arrayListJanela.size(); i++) {
				if(arrayListJanela.get(i).getNome().toUpperCase().equals(nomeTituloJanela[0].toUpperCase())){
					cria=false;
				}
			}
			if(cria){
				//Cria a janela a partir do modelo selecionado
				switch (modelo) {
				case "vertical":
					//Percorre os campos e define seus atributos
					for (int i = 0; i < campos.length; i++) {

						int tamanho=100;
						int maxLength=100;
						Coluna coluna = tabela.getColuna(campos[i]);
						try{
							if(coluna.getTipo().startsWith("varchar")){
								tamanho=7*Integer.parseInt(Mascara.filtroNumeros(coluna.getTipo()));
								maxLength=Integer.parseInt(Mascara.filtroNumeros(coluna.getTipo()));
							}else{
								if(coluna.getTipo().startsWith("decimal")){
									tamanho=70;
									maxLength=15;
								}else if(coluna.getTipo().startsWith("date")){
									tamanho=110;
									maxLength=10;
								}
							}
						}catch(Exception exc){
							tamanho=100;
						}

						if(tamanho < 50){
							tamanho=50;
						}

						if(tamanho> 505){
							tamanho=505;
						}

						//Adiciona o rotulo
						ArrayList<Atributo> arrayListAtributoRotulo = new ArrayList<Atributo>();
						arrayListAtributoRotulo.add(new Atributo("Tipo", "String", "Rotulo"));
						if(coluna.getTipo().contains("not null")){
							arrayListAtributoRotulo.add(new Atributo("Text", "String", ""+Mascara.filtroClasse(coluna.getNome())+"*"));
						}else{
							arrayListAtributoRotulo.add(new Atributo("Text", "String", ""+Mascara.filtroClasse(coluna.getNome())));
						}
						arrayListAtributoRotulo.add(new Atributo("width", "int", "145"));
						arrayListAtributoRotulo.add(new Atributo("height", "int", "20"));
						arrayListAtributoRotulo.add(new Atributo("x", "int", "0"));
						arrayListAtributoRotulo.add(new Atributo("y", "int", ""+((25*i)+10)));
						arrayListAtributoRotulo.add(new Atributo("HorizontalAlignment", "int", ""+SwingConstants.RIGHT));

						Componente componenteRotulo = new Componente("jLabel"+Mascara.filtroClasse(coluna.getNome()), arrayListAtributoRotulo, new ArrayList<Metodo>());
						arrayListComponentes.add(componenteRotulo);

						//Adiciona o Campo de texto
						ArrayList<Atributo> arrayListAtributoComponente = new ArrayList<Atributo>();
						arrayListAtributoComponente.add(new Atributo("Tipo", "String", "Campo de texto"));
						arrayListAtributoComponente.add(new Atributo("width", "int", ""+tamanho));
						arrayListAtributoComponente.add(new Atributo("height", "int", "20"));
						arrayListAtributoComponente.add(new Atributo("x", "int", "150"));
						arrayListAtributoComponente.add(new Atributo("y", "int", ""+((25*i)+10)));
						if((coluna.getTipo().startsWith("int")) || (coluna.getTipo().startsWith("decimal"))){
							arrayListAtributoComponente.add(new Atributo("HorizontalAlignment", "int", ""+SwingConstants.RIGHT));
						}

						if(coluna.getTipo().startsWith("int")){
							arrayListAtributoComponente.add(new Atributo("Mask", "int", ""+JTetrisTextField.MASCARA_NUMERO_INTEIRO));
						}else if(coluna.getTipo().startsWith("decimal")){
							arrayListAtributoComponente.add(new Atributo("Mask", "int", ""+JTetrisTextField.MASCARA_DECIMAL));
						}else if(coluna.getTipo().startsWith("date")){
							arrayListAtributoComponente.add(new Atributo("Mask", "int", ""+JTetrisTextField.MASCARA_DATA));
						}else{
							arrayListAtributoComponente.add(new Atributo("Mask", "int", ""+JTetrisTextField.MASCARA_MAX_LENGTH));
						}

						arrayListAtributoComponente.add(new Atributo("MaxLength", "int", ""+maxLength));

						Componente componente = new Componente("jTextField"+Mascara.filtroClasse(coluna.getNome()), arrayListAtributoComponente, new ArrayList<Metodo>());
						arrayListComponentes.add(componente);

						if(tamanho>maiorTamanho){
							maiorTamanho=tamanho;
						}
					}

					//Adiciona o botao
					ArrayList<Atributo> arrayListAtributoBotao = new ArrayList<Atributo>();
					arrayListAtributoBotao.add(new Atributo("Tipo", "String", "Botao"));
					arrayListAtributoBotao.add(new Atributo("Text", "String", ""+Idioma.getTraducao("tetris.save2", jFramePrincipal)));
					arrayListAtributoBotao.add(new Atributo("width", "int", "100"));
					arrayListAtributoBotao.add(new Atributo("height", "int", "25"));
					arrayListAtributoBotao.add(new Atributo("x", "int", ""+(maiorTamanho+200-150)));
					arrayListAtributoBotao.add(new Atributo("y", "int", ""+((25*campos.length)+60-40)));

					//Metodos botao
					ArrayList<Metodo> arrayListMetodos = new ArrayList<Metodo>();

					for (int i = 0; i < campos.length; i++) {

						Coluna coluna = tabela.getColuna(campos[i]);
						if(coluna.getTipo().contains("not null")){
							String[] parametros = new String []{
									"jTextField"+Mascara.filtroClasse(coluna.getNome()),
									"Text",
									"==",
									"",
									Idioma.getTraducao("tetris.message_fill_out_the_field", jFramePrincipal)+" "+Mascara.filtroClasse(coluna.getNome())+" "+Idioma.getTraducao("tetris.message_correctly", jFramePrincipal)
							};

							arrayListMetodos.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodos.size())+"verificarValor", "OnClick", parametros, jFramePrincipal));
						}
					}

					String colunas = "", valores="";
					for (int j = 0; j < campos.length; j++) {
						if(colunas.equals("")==false){
							colunas+=", ";
							valores+=", ";
						}
						colunas+=campos[j];
						valores+="'\"+jTextField"+Mascara.filtroClasse(campos[j])+".getText()+\"'";
					}

					String[] parametros = new String []{
							""+tabela.getNome(),
							""+colunas,
							""+valores
					};

					arrayListMetodos.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodos.size())+"gravarRegistro", "OnClick", parametros, jFramePrincipal));

					for (int j = 0; j < campos.length; j++) {
						arrayListMetodos.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodos.size())+"mudarValor", "OnClick", new String []{"jTextField"+Mascara.filtroClasse(campos[j]), "Text", "", " "}, jFramePrincipal));
					}

					Componente componente = new Componente("jButtonGravar", arrayListAtributoBotao, arrayListMetodos);
					arrayListComponentes.add(componente);

					//Atributos da janela
					ArrayList<Atributo> arrayListAtributo = new ArrayList<Atributo>();

					arrayListAtributo.add(new Atributo("Title", "String", nomeTituloJanela[1]));
					arrayListAtributo.add(new Atributo("x", "int", "0"));
					arrayListAtributo.add(new Atributo("y", "int", "0"));
					arrayListAtributo.add(new Atributo("width", "int", ""+(maiorTamanho+200)));
					arrayListAtributo.add(new Atributo("height", "int", ""+(campos.length*25+100)));

					//Metodos
					//ArrayList<Metodo> arrayListMetodos = new ArrayList<Metodo>();

					Janela janela = new Janela(nomeTituloJanela[0], arrayListAtributo, new ArrayList<Metodo>());

					janela.setArrayListComponentes(arrayListComponentes);

					if(Projeto.adicionarJanela(janela, jFramePrincipal)){
						retorno=true;
					}

					break;

				case "login":
					//Percorre os campos e define seus atributos
					for (int i = 0; i < campos.length; i++) {

						int tamanho=100;
						Coluna coluna = tabela.getColuna(campos[i]);
						try{
							if(coluna.getTipo().startsWith("varchar")){
								tamanho=7*Integer.parseInt(Mascara.filtroNumeros(coluna.getTipo()));
							}
						}catch(Exception exc){
							tamanho=100;
						}

						if(tamanho> 505){
							tamanho=505;
						}

						//Adiciona o rotulo
						ArrayList<Atributo> arrayListAtributoRotulo = new ArrayList<Atributo>();
						arrayListAtributoRotulo.add(new Atributo("Tipo", "String", "Rotulo"));
						if(coluna.getTipo().contains("not null")){
							arrayListAtributoRotulo.add(new Atributo("Text", "String", ""+Mascara.filtroClasse(coluna.getNome())+"*"));
						}else{
							arrayListAtributoRotulo.add(new Atributo("Text", "String", ""+Mascara.filtroClasse(coluna.getNome())));
						}
						arrayListAtributoRotulo.add(new Atributo("width", "int", "145"));
						arrayListAtributoRotulo.add(new Atributo("height", "int", "20"));
						arrayListAtributoRotulo.add(new Atributo("x", "int", "0"));
						arrayListAtributoRotulo.add(new Atributo("y", "int", ""+((25*i)+10)));
						arrayListAtributoRotulo.add(new Atributo("HorizontalAlignment", "int", ""+SwingConstants.RIGHT));

						Componente componenteRotulo = new Componente("jLabel"+Mascara.filtroClasse(coluna.getNome()), arrayListAtributoRotulo, new ArrayList<Metodo>());
						arrayListComponentes.add(componenteRotulo);

						//Adiciona o Campo de texto
						ArrayList<Atributo> arrayListAtributoComponente = new ArrayList<Atributo>();
						arrayListAtributoComponente.add(new Atributo("Tipo", "String", "Campo de texto"));
						arrayListAtributoComponente.add(new Atributo("width", "int", ""+tamanho));
						arrayListAtributoComponente.add(new Atributo("height", "int", "20"));
						arrayListAtributoComponente.add(new Atributo("x", "int", "150"));
						arrayListAtributoComponente.add(new Atributo("y", "int", ""+((25*i)+10)));
						if((coluna.getTipo().startsWith("int")) || (coluna.getTipo().startsWith("decimal"))){
							arrayListAtributoComponente.add(new Atributo("HorizontalAlignment", "int", ""+SwingConstants.RIGHT));
						}

						if(coluna.getTipo().startsWith("int")){
							arrayListAtributoComponente.add(new Atributo("Mask", "int", ""+JTetrisTextField.MASCARA_NUMERO_INTEIRO));
						}else if(coluna.getTipo().startsWith("decimal")){
							arrayListAtributoComponente.add(new Atributo("Mask", "int", ""+JTetrisTextField.MASCARA_DECIMAL));
						}else if(coluna.getTipo().startsWith("date")){
							arrayListAtributoComponente.add(new Atributo("Mask", "int", ""+JTetrisTextField.MASCARA_DATA));
						}

						Componente componenteL = new Componente("jTextField"+Mascara.filtroClasse(coluna.getNome()), arrayListAtributoComponente, new ArrayList<Metodo>());
						arrayListComponentes.add(componenteL);

						if(tamanho>maiorTamanho){
							maiorTamanho=tamanho;
						}
					}

					//Adiciona o botao
					ArrayList<Atributo> arrayListAtributoBotaoL = new ArrayList<Atributo>();
					arrayListAtributoBotaoL.add(new Atributo("Tipo", "String", "Botao"));
					arrayListAtributoBotaoL.add(new Atributo("Text", "String", ""+Idioma.getTraducao("tetris.enter", jFramePrincipal)));
					arrayListAtributoBotaoL.add(new Atributo("width", "int", "100"));
					arrayListAtributoBotaoL.add(new Atributo("height", "int", "25"));
					arrayListAtributoBotaoL.add(new Atributo("x", "int", ""+(maiorTamanho+200-150)));
					arrayListAtributoBotaoL.add(new Atributo("y", "int", ""+((25*campos.length)+60-40)));

					//Metodos botao
					ArrayList<Metodo> arrayListMetodosL = new ArrayList<Metodo>();

					for (int i = 0; i < campos.length; i++) {

						Coluna coluna = tabela.getColuna(campos[i]);
						if(coluna.getTipo().contains("not null")){
							String[] parametrosL = new String []{
									"jTextField"+Mascara.filtroClasse(coluna.getNome()),
									"Text",
									"==",
									"",
									Idioma.getTraducao("tetris.message_fill_out_the_field", jFramePrincipal)+" "+Mascara.filtroClasse(coluna.getNome())+" "+Idioma.getTraducao("tetris.message_correctly", jFramePrincipal)
							};

							arrayListMetodosL.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosL.size())+"verificarValor", "OnClick", parametrosL, jFramePrincipal));
						}
					}

					String condicao = "";
					for (int j = 0; j < campos.length; j++) {
						if(condicao.equals("")==false){
							condicao+=" and ";

						}
						condicao+=campos[j]+"='\"+jTextField"+Mascara.filtroClasse(campos[j])+".getText()+\"'";

					}


					String[] parametrosL = new String []{
							""+tabela.getNome(),
							""+primaryKey,
							"where "+condicao,
							""+Idioma.getTraducao("tetris.message_remove", jFramePrincipal)
					};

					arrayListMetodosL.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosL.size())+"verificarRegistro", "OnClick", parametrosL, jFramePrincipal));

					arrayListMetodosL.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosL.size())+"fecharJanela", "OnClick", new String []{}, jFramePrincipal));

					Componente componenteL = new Componente("jButtonEntrar", arrayListAtributoBotaoL, arrayListMetodosL);
					arrayListComponentes.add(componenteL);

					//Atributos da janela
					ArrayList<Atributo> arrayListAtributoL = new ArrayList<Atributo>();

					arrayListAtributoL.add(new Atributo("Title", "String", nomeTituloJanela[1]));
					arrayListAtributoL.add(new Atributo("x", "int", "0"));
					arrayListAtributoL.add(new Atributo("y", "int", "0"));
					arrayListAtributoL.add(new Atributo("width", "int", ""+(maiorTamanho+200)));
					arrayListAtributoL.add(new Atributo("height", "int", ""+(campos.length*25+100)));

					//Metodos
					//ArrayList<Metodo> arrayListMetodos = new ArrayList<Metodo>();

					Janela janelaL = new Janela(nomeTituloJanela[0], arrayListAtributoL, new ArrayList<Metodo>());

					janelaL.setArrayListComponentes(arrayListComponentes);

					if(Projeto.adicionarJanela(janelaL, jFramePrincipal)){
						retorno=true;
					}

					break;

				case "horizontal":
					//Percorre os campos e define seus atributos
					maiorTamanho=0;
					int tamanho=100, tamanhoX=12;
					int posYAtual=0;
					int maxLengthH=100;
					for (int i = 0; i < campos.length; i++) {


						Coluna coluna = tabela.getColuna(campos[i]);
						try{
							if(coluna.getTipo().startsWith("varchar")){
								tamanho=7*Integer.parseInt(Mascara.filtroNumeros(coluna.getTipo()));
								maxLengthH=Integer.parseInt(Mascara.filtroNumeros(coluna.getTipo()));
							}else{
								if(coluna.getTipo().startsWith("decimal")){
									tamanho=70;
									maxLengthH=15;
								}else if(coluna.getTipo().startsWith("date")){
									tamanho=110;
									maxLengthH=10;
								}
							}
						}catch(Exception exc){
							tamanho=100;
						}

						if(tamanho<50){
							tamanho=50;
						}

						if(tamanho> 350){
							tamanho=350;
						}

						if(tamanhoX+tamanho > 750){
							posYAtual+=1;
							tamanhoX=12;

							maiorTamanho+=1;
						}

						//Adiciona o rotulo
						ArrayList<Atributo> arrayListAtributoRotulo = new ArrayList<Atributo>();
						arrayListAtributoRotulo.add(new Atributo("Tipo", "String", "Rotulo"));
						if(coluna.getTipo().contains("not null")){
							arrayListAtributoRotulo.add(new Atributo("Text", "String", ""+Mascara.filtroClasse(coluna.getNome())+"*"));
						}else{
							arrayListAtributoRotulo.add(new Atributo("Text", "String", ""+Mascara.filtroClasse(coluna.getNome())));
						}
						arrayListAtributoRotulo.add(new Atributo("width", "int", ""+tamanho));
						arrayListAtributoRotulo.add(new Atributo("height", "int", "20"));
						arrayListAtributoRotulo.add(new Atributo("x", "int", ""+(tamanhoX)));
						arrayListAtributoRotulo.add(new Atributo("y", "int", ""+(12+(52*posYAtual))));

						Componente componenteRotulo = new Componente("jLabel"+Mascara.filtroClasse(coluna.getNome()), arrayListAtributoRotulo, new ArrayList<Metodo>());
						arrayListComponentes.add(componenteRotulo);

						//Adiciona o Campo de texto
						ArrayList<Atributo> arrayListAtributoComponente = new ArrayList<Atributo>();
						arrayListAtributoComponente.add(new Atributo("Tipo", "String", "Campo de texto"));
						arrayListAtributoComponente.add(new Atributo("width", "int", ""+tamanho));
						arrayListAtributoComponente.add(new Atributo("height", "int", "20"));
						arrayListAtributoComponente.add(new Atributo("x", "int", ""+(tamanhoX)));
						arrayListAtributoComponente.add(new Atributo("y", "int", ""+(32+(52*posYAtual))));
						if((coluna.getTipo().startsWith("int")) || (coluna.getTipo().startsWith("decimal"))){
							arrayListAtributoComponente.add(new Atributo("HorizontalAlignment", "int", ""+SwingConstants.RIGHT));
						}

						if(coluna.getTipo().startsWith("int")){
							arrayListAtributoComponente.add(new Atributo("Mask", "int", ""+JTetrisTextField.MASCARA_NUMERO_INTEIRO));
						}else if(coluna.getTipo().startsWith("decimal")){
							arrayListAtributoComponente.add(new Atributo("Mask", "int", ""+JTetrisTextField.MASCARA_DECIMAL));
						}else if(coluna.getTipo().startsWith("date")){
							arrayListAtributoComponente.add(new Atributo("Mask", "int", ""+JTetrisTextField.MASCARA_DATA));
						}else{
							arrayListAtributoComponente.add(new Atributo("Mask", "int", ""+JTetrisTextField.MASCARA_MAX_LENGTH));
						}

						arrayListAtributoComponente.add(new Atributo("MaxLength", "int", ""+maxLengthH));

						Componente componenteH = new Componente("jTextField"+Mascara.filtroClasse(coluna.getNome()), arrayListAtributoComponente, new ArrayList<Metodo>());
						arrayListComponentes.add(componenteH);

						tamanhoX=tamanhoX+tamanho+12;
					}

					//Adiciona o botao
					ArrayList<Atributo> arrayListAtributoBotaoH = new ArrayList<Atributo>();
					arrayListAtributoBotaoH.add(new Atributo("Tipo", "String", "Botao"));
					arrayListAtributoBotaoH.add(new Atributo("Text", "String", ""+Idioma.getTraducao("tetris.save2", jFramePrincipal)));
					arrayListAtributoBotaoH.add(new Atributo("width", "int", "100"));
					arrayListAtributoBotaoH.add(new Atributo("height", "int", "25"));
					arrayListAtributoBotaoH.add(new Atributo("x", "int", ""+(750-150)));
					arrayListAtributoBotaoH.add(new Atributo("y", "int", ""+((maiorTamanho*52)+100)));

					//Metodos botao
					ArrayList<Metodo> arrayListMetodosH = new ArrayList<Metodo>();

					for (int i = 0; i < campos.length; i++) {

						Coluna coluna = tabela.getColuna(campos[i]);
						if(coluna.getTipo().contains("not null")){
							String[] parametrosH = new String []{
									"jTextField"+Mascara.filtroClasse(coluna.getNome()),
									"Text",
									"==",
									"",
									Idioma.getTraducao("tetris.message_fill_out_the_field", jFramePrincipal)+" "+Mascara.filtroClasse(coluna.getNome())+" "+Idioma.getTraducao("tetris.message_correctly", jFramePrincipal)
							};

							arrayListMetodosH.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosH.size())+"verificarValor", "OnClick", parametrosH, jFramePrincipal));
						}
					}

					String colunasH = "", valoresH="";
					for (int j = 0; j < campos.length; j++) {
						if(colunasH.equals("")==false){
							colunasH+=", ";
							valoresH+=", ";
						}
						colunasH+=campos[j];
						valoresH+="'\"+jTextField"+Mascara.filtroClasse(campos[j])+".getText()+\"'";
					}

					String[] parametrosH = new String []{
							""+tabela.getNome(),
							""+colunasH,
							""+valoresH
					};

					arrayListMetodosH.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosH.size())+"gravarRegistro", "OnClick", parametrosH, jFramePrincipal));

					for (int j = 0; j < campos.length; j++) {
						arrayListMetodosH.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosH.size())+"mudarValor", "OnClick", new String []{"jTextField"+Mascara.filtroClasse(campos[j]), "Text", "", " "}, jFramePrincipal));
					}

					Componente componenteH = new Componente("jButtonGravar", arrayListAtributoBotaoH, arrayListMetodosH);
					arrayListComponentes.add(componenteH);

					//Atributos da janela
					ArrayList<Atributo> arrayListAtributoH = new ArrayList<Atributo>();

					arrayListAtributoH.add(new Atributo("Title", "String", nomeTituloJanela[1]));
					arrayListAtributoH.add(new Atributo("x", "int", "0"));
					arrayListAtributoH.add(new Atributo("y", "int", "0"));
					arrayListAtributoH.add(new Atributo("width", "int", "750"));
					arrayListAtributoH.add(new Atributo("height", "int", ""+((maiorTamanho*52)+200)));

					//Metodos
					//ArrayList<Metodo> arrayListMetodos = new ArrayList<Metodo>();

					Janela janelaH = new Janela(nomeTituloJanela[0], arrayListAtributoH, new ArrayList<Metodo>());

					janelaH.setArrayListComponentes(arrayListComponentes);

					if(Projeto.adicionarJanela(janelaH, jFramePrincipal)){
						retorno=true;
					}

					break;

				case "doisemum":
					//Percorre os campos e define seus atributos
					maiorTamanho=0;
					int tamanhoD=100, tamanhoDX=12;
					int posYAtualD=0;
					int maxLengthD=100;


					for (int i = 0; i < campos.length; i++) {

						Coluna coluna = tabela.getColuna(campos[i]);

						try{
							if(coluna.getTipo().startsWith("varchar")){
								tamanhoD=7*Integer.parseInt(Mascara.filtroNumeros(coluna.getTipo()));
								maxLengthD=Integer.parseInt(Mascara.filtroNumeros(coluna.getTipo()));
							}else{
								if(coluna.getTipo().startsWith("decimal")){
									tamanhoD=70;
									maxLengthD=15;
								}else if(coluna.getTipo().startsWith("date")){
									tamanhoD=110;
									maxLengthD=10;
								}
							}
						}catch(Exception exc){
							tamanhoD=100;
						}

						if(tamanhoD < 50){
							tamanhoD = 50;
						}

						if(tamanhoD> 350){
							tamanhoD=350;
						}

						if(tamanhoDX+tamanhoD > 750){
							posYAtualD+=1;
							tamanhoDX=12;

							maiorTamanho+=1;
						}

						//Adiciona o rotulo
						ArrayList<Atributo> arrayListAtributoRotulo = new ArrayList<Atributo>();
						arrayListAtributoRotulo.add(new Atributo("Tipo", "String", "Rotulo"));
						if(coluna.getTipo().contains("not null")){
							arrayListAtributoRotulo.add(new Atributo("Text", "String", ""+Mascara.filtroClasse(coluna.getNome())+"*"));
						}else{
							arrayListAtributoRotulo.add(new Atributo("Text", "String", ""+Mascara.filtroClasse(coluna.getNome())));
						}
						arrayListAtributoRotulo.add(new Atributo("width", "int", ""+tamanhoD));
						arrayListAtributoRotulo.add(new Atributo("height", "int", "20"));
						arrayListAtributoRotulo.add(new Atributo("x", "int", ""+(tamanhoDX)));
						arrayListAtributoRotulo.add(new Atributo("y", "int", ""+(12+(52*posYAtualD))));

						Componente componenteRotulo = new Componente("jLabel"+Mascara.filtroClasse(coluna.getNome()), arrayListAtributoRotulo, new ArrayList<Metodo>());
						arrayListComponentes.add(componenteRotulo);

						//Adiciona o Campo de texto
						ArrayList<Atributo> arrayListAtributoComponente = new ArrayList<Atributo>();
						arrayListAtributoComponente.add(new Atributo("Tipo", "String", "Campo de texto"));
						arrayListAtributoComponente.add(new Atributo("width", "int", ""+tamanhoD));
						arrayListAtributoComponente.add(new Atributo("height", "int", "20"));
						arrayListAtributoComponente.add(new Atributo("x", "int", ""+(tamanhoDX)));
						arrayListAtributoComponente.add(new Atributo("y", "int", ""+(32+(52*posYAtualD))));
						if((coluna.getTipo().startsWith("int")) || (coluna.getTipo().startsWith("decimal"))){
							arrayListAtributoComponente.add(new Atributo("HorizontalAlignment", "int", ""+SwingConstants.RIGHT));
						}

						if(coluna.getTipo().startsWith("int")){
							arrayListAtributoComponente.add(new Atributo("Mask", "int", ""+JTetrisTextField.MASCARA_NUMERO_INTEIRO));
						}else if(coluna.getTipo().startsWith("decimal")){
							arrayListAtributoComponente.add(new Atributo("Mask", "int", ""+JTetrisTextField.MASCARA_DECIMAL));
						}else if(coluna.getTipo().startsWith("date")){
							arrayListAtributoComponente.add(new Atributo("Mask", "int", ""+JTetrisTextField.MASCARA_DATA));
						}else{
							arrayListAtributoComponente.add(new Atributo("Mask", "int", ""+JTetrisTextField.MASCARA_MAX_LENGTH));
						}
						arrayListAtributoComponente.add(new Atributo("Column", "String", ""+coluna.getNome()));
						arrayListAtributoComponente.add(new Atributo("MaxLength", "int", ""+maxLengthD));

						Componente componenteD = new Componente("jTextField"+Mascara.filtroClasse(coluna.getNome()), arrayListAtributoComponente, new ArrayList<Metodo>());
						arrayListComponentes.add(componenteD);

						tamanhoDX=tamanhoDX+tamanhoD+12;
					}

					//Muda foco para jButtonGravar
					arrayListComponentes.get(arrayListComponentes.size()-1).getArrayListMetodos().add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"mudarFoco", "OnKeyReleased", new String []{"jButtonGravar"}, jFramePrincipal, "ENTER"));
					arrayListComponentes.get(arrayListComponentes.size()-1).getArrayListMetodos().add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"mudarFoco", "OnKeyReleased", new String []{"jButtonGravarAlteracao"}, jFramePrincipal, "ENTER"));

					//Inicio tabela
					String colunasT="";
					String colunasConsultaT="";
					for (int i = 0; i < campos.length; i++) {
						if(colunasT!=""){
							colunasT+="/";
							colunasConsultaT+=", ";
						}
						colunasT+=campos[i];
						colunasConsultaT+=campos[i];
					}

					String[] colunasPrimaryKey = primaryKey.split(", ");
					String condicaoConsulta=""+colunasPrimaryKey[0]+"='\"+jTable"+Mascara.filtroClasse(tabela.getNome())+".getSelectedValue(\""+colunasPrimaryKey[0]+"\")+\"'";
					String condicaoSelecao = ""+colunasPrimaryKey[0]+"='\"+jTextField"+Mascara.filtroClasse(colunasPrimaryKey[0])+".getText()+\"'";
					for (int i = 1; i < colunasPrimaryKey.length; i++) {
						condicaoConsulta=condicaoConsulta+" and "+colunasPrimaryKey[i]+"='\"+jTable"+Mascara.filtroClasse(tabela.getNome())+".getSelectedValue(\""+colunasPrimaryKey[i]+"\")+\"'";
						condicaoSelecao =condicaoSelecao+ " and "+colunasPrimaryKey[i]+"='\"+jTextField"+Mascara.filtroClasse(colunasPrimaryKey[i])+".getText()+\"'";
					}

					String[] parametrosDTabela = new String []{
							""+tabela.getNome(),
							""+colunasConsultaT,
							"where "+condicaoConsulta
					};

					//Adiciona a tabela
					ArrayList<Atributo> arrayListAtributoTabelaD = new ArrayList<Atributo>();
					arrayListAtributoTabelaD.add(new Atributo("Tipo", "String", "Tabela"));
					arrayListAtributoTabelaD.add(new Atributo("Tabela", "String", ""+tabela.getNome()));
					arrayListAtributoTabelaD.add(new Atributo("width", "int", "700"));
					arrayListAtributoTabelaD.add(new Atributo("height", "int", "200"));
					arrayListAtributoTabelaD.add(new Atributo("x", "int", "15"));
					arrayListAtributoTabelaD.add(new Atributo("y", "int", ""+((maiorTamanho*52)+100)));
					arrayListAtributoTabelaD.add(new Atributo("Columns", "String", colunasT));
					arrayListAtributoTabelaD.add(new Atributo("Titles", "String", colunasT));

					ArrayList<Metodo> arrayListMetodoTabelaD = new ArrayList<Metodo>();

					arrayListMetodoTabelaD.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodoTabelaD.size())+"executarProcedure", "OnClick", new String[]{"selecionarRegistro"}, jFramePrincipal));
					arrayListMetodoTabelaD.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodoTabelaD.size())+"executarProcedure", "OnKeyReleased", new String[]{"selecionarRegistro"}, jFramePrincipal));

					Componente componenteD = new Componente("jTable"+Mascara.filtroClasse(tabela.getNome()), arrayListAtributoTabelaD, arrayListMetodoTabelaD);
					arrayListComponentes.add(componenteD);
					//Fim tabela

					//Inicio Procedure Preencher tabela
					//Adiciona o procedure
					ArrayList<Atributo> arrayListAtributoProcedurePreencherTabela = new ArrayList<Atributo>();
					arrayListAtributoProcedurePreencherTabela.add(new Atributo("Tipo", "String", "Procedure"));
					arrayListAtributoProcedurePreencherTabela.add(new Atributo("width", "int", "20"));
					arrayListAtributoProcedurePreencherTabela.add(new Atributo("height", "int", "20"));
					arrayListAtributoProcedurePreencherTabela.add(new Atributo("x", "int", "0"));
					arrayListAtributoProcedurePreencherTabela.add(new Atributo("y", "int", "0"));

					//Metodos procedure
					ArrayList<Metodo> arrayListMetodosDProcedurePreencherTabela = new ArrayList<Metodo>();

					arrayListMetodosDProcedurePreencherTabela.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDProcedurePreencherTabela.size(), 3)+"preencherTabela", "OnExecute", new String []{"jTable"+Mascara.filtroClasse(tabela.getNome()), tabela.getNome(), colunasConsultaT, "order by "+colunasPrimaryKey[0]}, jFramePrincipal));

					componenteD = new Componente("preencherTabela", arrayListAtributoProcedurePreencherTabela, arrayListMetodosDProcedurePreencherTabela);
					arrayListComponentes.add(componenteD);
					//Fim Procedure Preencher tabela

					//Inicio Procedure Habilitar Campos
					//Adiciona o procedure
					ArrayList<Atributo> arrayListAtributoProcedureHabilitarCampos = new ArrayList<Atributo>();
					arrayListAtributoProcedureHabilitarCampos.add(new Atributo("Tipo", "String", "Procedure"));
					arrayListAtributoProcedureHabilitarCampos.add(new Atributo("width", "int", "20"));
					arrayListAtributoProcedureHabilitarCampos.add(new Atributo("height", "int", "20"));
					arrayListAtributoProcedureHabilitarCampos.add(new Atributo("x", "int", "20"));
					arrayListAtributoProcedureHabilitarCampos.add(new Atributo("y", "int", "0"));

					//Metodos procedure
					ArrayList<Metodo> arrayListMetodosDProcedureHabilitarCampos = new ArrayList<Metodo>();

					String[] parametrosDListaTextField = new String[campos.length];
					//parametrosDListaTextField[0] = "jTextFieldCodigo";
					for (int i = 0; i < campos.length; i++) {
						parametrosDListaTextField[i] = "jTextField"+Mascara.filtroClasse(campos[i]);
					}

					//Habilita os campos
					arrayListMetodosDProcedureHabilitarCampos.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosDProcedureHabilitarCampos.size())+"habilitarCampos", "OnExecute", parametrosDListaTextField, jFramePrincipal));
					arrayListMetodosDProcedureHabilitarCampos.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDProcedureHabilitarCampos.size(), 3)+"habilitarCampos", "OnExecute", new String []{"jButtonGravar", "jButtonGravarAlteracao", "jButtonCancelar"}, jFramePrincipal));
					arrayListMetodosDProcedureHabilitarCampos.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDProcedureHabilitarCampos.size(), 3)+"desabilitarCampos", "OnExecute", new String []{"jButtonNovo", "jButtonAlterar", "jButtonExcluir"}, jFramePrincipal));

					componenteD = new Componente("habilitarCampos", arrayListAtributoProcedureHabilitarCampos, arrayListMetodosDProcedureHabilitarCampos);
					arrayListComponentes.add(componenteD);
					//Fim Procedure Habilitar Campos

					//Inicio Procedure Desabilitar Campos
					//Adiciona o procedure
					ArrayList<Atributo> arrayListAtributoProcedureDesabilitarCampos = new ArrayList<Atributo>();
					arrayListAtributoProcedureDesabilitarCampos.add(new Atributo("Tipo", "String", "Procedure"));
					arrayListAtributoProcedureDesabilitarCampos.add(new Atributo("width", "int", "20"));
					arrayListAtributoProcedureDesabilitarCampos.add(new Atributo("height", "int", "20"));
					arrayListAtributoProcedureDesabilitarCampos.add(new Atributo("x", "int", "40"));
					arrayListAtributoProcedureDesabilitarCampos.add(new Atributo("y", "int", "0"));

					//Metodos procedure
					ArrayList<Metodo> arrayListMetodosDProcedureDesabilitarCampos = new ArrayList<Metodo>();

					//Desabilita os campos
					arrayListMetodosDProcedureDesabilitarCampos.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosDProcedureDesabilitarCampos.size())+"desabilitarCampos", "OnExecute", parametrosDListaTextField, jFramePrincipal));
					arrayListMetodosDProcedureDesabilitarCampos.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDProcedureDesabilitarCampos.size(), 3)+"habilitarCampos", "OnExecute", new String []{"jButtonNovo", "jButtonAlterar", "jButtonExcluir"}, jFramePrincipal));
					arrayListMetodosDProcedureDesabilitarCampos.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDProcedureDesabilitarCampos.size(), 3)+"desabilitarCampos", "OnExecute", new String []{"jButtonGravar", "jButtonGravarAlteracao", "jButtonCancelar"}, jFramePrincipal));

					componenteD = new Componente("desabilitarCampos", arrayListAtributoProcedureDesabilitarCampos, arrayListMetodosDProcedureDesabilitarCampos);
					arrayListComponentes.add(componenteD);
					//Fim Procedure Desabilitar Campos

					//Inicio Procedure Selecionar Registro
					//Adiciona o procedure
					ArrayList<Atributo> arrayListAtributoProcedureSelecionarRegistro = new ArrayList<Atributo>();
					arrayListAtributoProcedureSelecionarRegistro.add(new Atributo("Tipo", "String", "Procedure"));
					arrayListAtributoProcedureSelecionarRegistro.add(new Atributo("width", "int", "20"));
					arrayListAtributoProcedureSelecionarRegistro.add(new Atributo("height", "int", "20"));
					arrayListAtributoProcedureSelecionarRegistro.add(new Atributo("x", "int", "60"));
					arrayListAtributoProcedureSelecionarRegistro.add(new Atributo("y", "int", "0"));

					//Metodos procedure
					ArrayList<Metodo> arrayListMetodosDProcedureSelecionarRegistro = new ArrayList<Metodo>();

					//Selecionar registros
					arrayListMetodosDProcedureSelecionarRegistro.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDProcedureSelecionarRegistro.size(), 3)+"selecionarRegistro", "OnExecute", parametrosDTabela, jFramePrincipal));

					componenteD = new Componente("selecionarRegistro", arrayListAtributoProcedureSelecionarRegistro, arrayListMetodosDProcedureSelecionarRegistro);
					arrayListComponentes.add(componenteD);
					//Fim Procedure Selecionar Registro

					//Inicio Botao Novo
					//Adiciona o botao
					ArrayList<Atributo> arrayListAtributoBotaoDNovo = new ArrayList<Atributo>();
					arrayListAtributoBotaoDNovo.add(new Atributo("Tipo", "String", "Botao"));
					arrayListAtributoBotaoDNovo.add(new Atributo("Text", "String", ""+Idioma.getTraducao("tetris.new", jFramePrincipal)));
					arrayListAtributoBotaoDNovo.add(new Atributo("width", "int", "100"));
					arrayListAtributoBotaoDNovo.add(new Atributo("height", "int", "25"));
					arrayListAtributoBotaoDNovo.add(new Atributo("x", "int", "12"));
					arrayListAtributoBotaoDNovo.add(new Atributo("y", "int", ""+((maiorTamanho*52)+305)));

					//Metodos botao
					ArrayList<Metodo> arrayListMetodosDBotaoNovo = new ArrayList<Metodo>();

					//Habilita os campos
					arrayListMetodosDBotaoNovo.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosDBotaoNovo.size())+"executarProcedure", "OnClick", new String[]{"habilitarCampos"}, jFramePrincipal));


					//arrayListMetodosDBotaoNovo.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosDBotaoNovo.size())+"mudarValor", "OnClick", new String []{"jTextFieldCodigo", "Text", "", " "}, jFramePrincipal));
					for (int j = 0; j < campos.length; j++) {
						//Limpando demais campos
						arrayListMetodosDBotaoNovo.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosDBotaoNovo.size())+"mudarValor", "OnClick", new String []{"jTextField"+Mascara.filtroClasse(campos[j]), "Text", "", " "}, jFramePrincipal));
					}

					String colunasBotaoNovo="";
					for (int i = 0; i < arrayListColunas.size(); i++) {
						if((arrayListColunas.get(i).getTipo().contains("auto_increment")) && (arrayListColunas.get(i).getTipo().startsWith("int"))){
							if(!colunasBotaoNovo.equals("")){
								colunasBotaoNovo=colunasBotaoNovo+", ";
							}
							colunasBotaoNovo=colunasBotaoNovo+"max("+arrayListColunas.get(i).getNome()+")+1 as "+arrayListColunas.get(i).getNome();
						}
					}


					String[] parametrosDBotaoNovo = new String []{
							""+tabela.getNome(),
							""+colunasBotaoNovo,
							"order by "+colunasPrimaryKey[0]
					};

					arrayListMetodosDBotaoNovo.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosDBotaoNovo.size())+"selecionarRegistro", "OnClick", parametrosDBotaoNovo, jFramePrincipal));

					//Muda foco para jTextFieldCodigo
					arrayListMetodosDBotaoNovo.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_4_"+(arrayListMetodosDBotaoNovo.size())+"mudarFoco", "OnClick", new String []{"jTextField"+Mascara.filtroClasse(colunasPrimaryKey[0])}, jFramePrincipal));

					componenteD = new Componente("jButtonNovo", arrayListAtributoBotaoDNovo, arrayListMetodosDBotaoNovo);
					arrayListComponentes.add(componenteD);
					//Fim Botao Novo

					//Inicio Botao Alterar
					//Adiciona o botao
					ArrayList<Atributo> arrayListAtributoBotaoDAlterar = new ArrayList<Atributo>();
					arrayListAtributoBotaoDAlterar.add(new Atributo("Tipo", "String", "Botao"));
					arrayListAtributoBotaoDAlterar.add(new Atributo("Text", "String", ""+Idioma.getTraducao("tetris.alter", jFramePrincipal)));
					arrayListAtributoBotaoDAlterar.add(new Atributo("width", "int", "100"));
					arrayListAtributoBotaoDAlterar.add(new Atributo("height", "int", "25"));
					arrayListAtributoBotaoDAlterar.add(new Atributo("x", "int", "122"));
					arrayListAtributoBotaoDAlterar.add(new Atributo("y", "int", ""+((maiorTamanho*52)+305)));

					//Metodos botao
					ArrayList<Metodo> arrayListMetodosDBotaoAlterar = new ArrayList<Metodo>();

					//Verifica se tem registro
					arrayListMetodosDBotaoAlterar.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDBotaoAlterar.size(), 3)+"verificarValor", "OnClick", new String []{"jTable"+Mascara.filtroClasse(tabela.getNome()), "SelectedRow", "<", "0", ""+Idioma.getTraducao("tetris.message_remove", jFramePrincipal)}, jFramePrincipal));

					//Habilita os campos
					arrayListMetodosDBotaoAlterar.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDBotaoAlterar.size(), 3)+"executarProcedure", "OnClick", new String[]{"habilitarCampos"}, jFramePrincipal));

					arrayListMetodosDBotaoAlterar.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDBotaoAlterar.size(), 3)+"executarProcedure", "OnClick", new String[]{"selecionarRegistro"}, jFramePrincipal));

					//Muda valor do visible
					arrayListMetodosDBotaoAlterar.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDBotaoAlterar.size(), 3)+"mudarValor", "OnClick", new String []{"jButtonGravarAlteracao", "Visible", "true"}, jFramePrincipal));
					arrayListMetodosDBotaoAlterar.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDBotaoAlterar.size(), 3)+"mudarValor", "OnClick", new String []{"jButtonGravar", "Visible", "false"}, jFramePrincipal));

					arrayListMetodosDBotaoAlterar.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDBotaoAlterar.size(), 3)+"mudarFoco", "OnClick", new String []{"jTextField"+Mascara.filtroClasse(campos[0])}, jFramePrincipal));

					componenteD = new Componente("jButtonAlterar", arrayListAtributoBotaoDAlterar, arrayListMetodosDBotaoAlterar);
					arrayListComponentes.add(componenteD);
					//Fim Botao Alterar

					//Inicio Botao Gravar
					//Adiciona o botao
					ArrayList<Atributo> arrayListAtributoBotaoDGravar = new ArrayList<Atributo>();
					arrayListAtributoBotaoDGravar.add(new Atributo("Tipo", "String", "Botao"));
					arrayListAtributoBotaoDGravar.add(new Atributo("Text", "String", ""+Idioma.getTraducao("tetris.save2", jFramePrincipal)));
					arrayListAtributoBotaoDGravar.add(new Atributo("width", "int", "100"));
					arrayListAtributoBotaoDGravar.add(new Atributo("height", "int", "25"));
					arrayListAtributoBotaoDGravar.add(new Atributo("x", "int", "232"));
					arrayListAtributoBotaoDGravar.add(new Atributo("y", "int", ""+((maiorTamanho*52)+305)));

					//Metodos botao
					ArrayList<Metodo> arrayListMetodosDBotaoGravar = new ArrayList<Metodo>();

					for (int i = 0; i < campos.length; i++) {

						Coluna coluna = tabela.getColuna(campos[i]);
						if(coluna.getTipo().contains("not null")){
							String[] parametrosD = new String []{
									"jTextField"+Mascara.filtroClasse(coluna.getNome()),
									"Text",
									"==",
									"",
									Idioma.getTraducao("tetris.message_fill_out_the_field", jFramePrincipal)+" "+Mascara.filtroClasse(coluna.getNome())+" "+Idioma.getTraducao("tetris.message_correctly", jFramePrincipal)
							};

							arrayListMetodosDBotaoGravar.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDBotaoGravar.size(), 3)+"verificarValor", "OnClick", parametrosD, jFramePrincipal));
						}
					}

					//Verificando se este codigo ja existe
					arrayListMetodosDBotaoGravar.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDBotaoGravar.size(), 3)+"verificarRegistro", "OnClick", new String[] {tabela.getNome(), ""+primaryKey, "where "+condicaoSelecao, Idioma.getTraducao("tetris.message_primary_key_error", jFramePrincipal), "!="}, jFramePrincipal));

					String colunasD = "", valoresD="";
					for (int j = 0; j < campos.length; j++) {
						if(colunasD.equals("")==false){
							colunasD+=", ";
							valoresD+=", ";
						}
						colunasD+=campos[j];
						valoresD+="'\"+jTextField"+Mascara.filtroClasse(campos[j])+".getText()+\"'";
					}

					String[] parametrosD = new String []{
							""+tabela.getNome(),
							""+colunasD,
							""+valoresD
					};

					//Adicionando metodo que grava novo registro
					arrayListMetodosDBotaoGravar.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDBotaoGravar.size(), 3)+"gravarRegistro", "OnClick", parametrosD, jFramePrincipal));

					//Metodo que limpa campo jTextFieldCodigo
					//arrayListMetodosDBotaoGravar.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDBotaoGravar.size(), 3)+"mudarValor", "OnClick", new String []{"jTextFieldCodigo", "Text", "", " "}, jFramePrincipal));

					//Adicionando metodos que limpa os campos
					for (int j = 0; j < campos.length; j++) {
						arrayListMetodosDBotaoGravar.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDBotaoGravar.size(), 3)+"mudarValor", "OnClick", new String []{"jTextField"+Mascara.filtroClasse(campos[j]), "Text", "", " "}, jFramePrincipal));
					}

					//Adicionando metodo de desativar botoes
					arrayListMetodosDBotaoGravar.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDBotaoGravar.size(), 3)+"executarProcedure", "OnClick", new String[]{"desabilitarCampos"}, jFramePrincipal));

					//Adiciona metodo que preenche a tabela
					arrayListMetodosDBotaoGravar.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDBotaoGravar.size(), 3)+"executarProcedure", "OnClick", new String []{"preencherTabela"}, jFramePrincipal));

					//Adiciona metodo que seleciona registro
					arrayListMetodosDBotaoGravar.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDBotaoGravar.size(), 3)+"executarProcedure", "OnClick", new String[]{"selecionarRegistro"}, jFramePrincipal));

					componenteD = new Componente("jButtonGravar", arrayListAtributoBotaoDGravar, arrayListMetodosDBotaoGravar);
					arrayListComponentes.add(componenteD);
					//Fim Botao Gravar

					//Inicio Botao Gravar Alteracao
					//Adiciona o botao
					ArrayList<Atributo> arrayListAtributoBotaoDGravarAlteracao = new ArrayList<Atributo>();
					arrayListAtributoBotaoDGravarAlteracao.add(new Atributo("Tipo", "String", "Botao"));
					arrayListAtributoBotaoDGravarAlteracao.add(new Atributo("Text", "String", ""+Idioma.getTraducao("tetris.save2", jFramePrincipal)));
					arrayListAtributoBotaoDGravarAlteracao.add(new Atributo("width", "int", "100"));
					arrayListAtributoBotaoDGravarAlteracao.add(new Atributo("height", "int", "25"));
					arrayListAtributoBotaoDGravarAlteracao.add(new Atributo("x", "int", "232"));
					arrayListAtributoBotaoDGravarAlteracao.add(new Atributo("Visible", "boolean", "false"));
					arrayListAtributoBotaoDGravarAlteracao.add(new Atributo("y", "int", ""+((maiorTamanho*52)+305)));

					//Metodos botao
					ArrayList<Metodo> arrayListMetodosDBotaoGravarAlteracao = new ArrayList<Metodo>();

					for (int i = 0; i < campos.length; i++) {

						Coluna coluna = tabela.getColuna(campos[i]);
						if(coluna.getTipo().contains("not null")){
							String[] parametrosDAlteracao = new String []{
									"jTextField"+Mascara.filtroClasse(coluna.getNome()),
									"Text",
									"==",
									"",
									Idioma.getTraducao("tetris.message_fill_out_the_field", jFramePrincipal)+" "+Mascara.filtroClasse(coluna.getNome())+" "+Idioma.getTraducao("tetris.message_correctly", jFramePrincipal)
							};

							arrayListMetodosDBotaoGravarAlteracao.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDBotaoGravarAlteracao.size(), 3)+"verificarValor", "OnClick", parametrosDAlteracao, jFramePrincipal));
						}
					}

					String colunasDAlteracao = "";
					for (int j = 0; j < campos.length; j++) {
						if(colunasDAlteracao.equals("")==false){
							colunasDAlteracao+=", ";

						}
						colunasDAlteracao+=campos[j]+"='\"+jTextField"+Mascara.filtroClasse(campos[j])+".getText()+\"'";

					}

					String[] parametrosDAlteracao = new String []{
							""+tabela.getNome(),
							""+colunasDAlteracao,
							"where "+condicaoConsulta
					};

					//Adicionando metodo que altera o registro
					arrayListMetodosDBotaoGravarAlteracao.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDBotaoGravarAlteracao.size(), 3)+"alterarRegistro", "OnClick", parametrosDAlteracao, jFramePrincipal));

					//Adicionando metodo de desabilitar campos
					arrayListMetodosDBotaoGravarAlteracao.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDBotaoGravarAlteracao.size(), 3)+"executarProcedure", "OnClick", new String[]{"desabilitarCampos"}, jFramePrincipal));

					//Adiciona metodo que preenche a tabela
					arrayListMetodosDBotaoGravarAlteracao.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDBotaoGravarAlteracao.size(), 3)+"executarProcedure", "OnClick", new String []{"preencherTabela"}, jFramePrincipal));

					//Adiciona metodo que seleciona registro
					arrayListMetodosDBotaoGravarAlteracao.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDBotaoGravarAlteracao.size(), 3)+"executarProcedure", "OnClick", new String[]{"selecionarRegistro"}, jFramePrincipal));

					//Muda valor do visible
					arrayListMetodosDBotaoGravarAlteracao.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDBotaoGravarAlteracao.size(), 3)+"mudarValor", "OnClick", new String []{"jButtonGravarAlteracao", "Visible", "false"}, jFramePrincipal));
					arrayListMetodosDBotaoGravarAlteracao.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+Mascara.filtroZerosEsquerda(""+arrayListMetodosDBotaoGravarAlteracao.size(), 3)+"mudarValor", "OnClick", new String []{"jButtonGravar", "Visible", "true"}, jFramePrincipal));

					componenteD = new Componente("jButtonGravarAlteracao", arrayListAtributoBotaoDGravarAlteracao, arrayListMetodosDBotaoGravarAlteracao);
					arrayListComponentes.add(componenteD);
					//Fim Botao Gravar Alteracao

					//Inicio Botao Cancelar
					//Adiciona o botao
					ArrayList<Atributo> arrayListAtributoBotaoDCancelar = new ArrayList<Atributo>();
					arrayListAtributoBotaoDCancelar.add(new Atributo("Tipo", "String", "Botao"));
					arrayListAtributoBotaoDCancelar.add(new Atributo("Text", "String", ""+Idioma.getTraducao("tetris.cancel", jFramePrincipal)));
					arrayListAtributoBotaoDCancelar.add(new Atributo("width", "int", "100"));
					arrayListAtributoBotaoDCancelar.add(new Atributo("height", "int", "25"));
					arrayListAtributoBotaoDCancelar.add(new Atributo("x", "int", "342"));
					arrayListAtributoBotaoDCancelar.add(new Atributo("y", "int", ""+((maiorTamanho*52)+305)));

					//Metodos botao
					ArrayList<Metodo> arrayListMetodosDBotaoCancelar = new ArrayList<Metodo>();

					String[] parametrosDBotaoCancelarListaTextField = new String[campos.length+1];
					parametrosDBotaoCancelarListaTextField[0] = "jTextFieldCodigo";
					for (int i = 0; i < campos.length; i++) {
						parametrosDBotaoCancelarListaTextField[i+1] = "jTextField"+Mascara.filtroClasse(campos[i]);
					}

					//Habilita os campos
					arrayListMetodosDBotaoCancelar.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosDBotaoCancelar.size())+"executarProcedure", "OnClick", new String[]{"desabilitarCampos"}, jFramePrincipal));

					//Seleciona o registro
					arrayListMetodosDBotaoCancelar.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosDBotaoCancelar.size())+"executarProcedure", "OnClick", new String[]{"selecionarRegistro"}, jFramePrincipal));

					//Muda valor do visible
					arrayListMetodosDBotaoCancelar.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosDBotaoCancelar.size())+"mudarValor", "OnClick", new String []{"jButtonGravarAlteracao", "Visible", "false"}, jFramePrincipal));
					arrayListMetodosDBotaoCancelar.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosDBotaoCancelar.size())+"mudarValor", "OnClick", new String []{"jButtonGravar", "Visible", "true"}, jFramePrincipal));

					componenteD = new Componente("jButtonCancelar", arrayListAtributoBotaoDCancelar, arrayListMetodosDBotaoCancelar);
					arrayListComponentes.add(componenteD);
					//Fim Botao Cancelar

					//Inicio Botao Excluir
					//Adiciona o botao
					ArrayList<Atributo> arrayListAtributoBotaoDExcluir = new ArrayList<Atributo>();
					arrayListAtributoBotaoDExcluir.add(new Atributo("Tipo", "String", "Botao"));
					arrayListAtributoBotaoDExcluir.add(new Atributo("Text", "String", ""+Idioma.getTraducao("tetris.delete", jFramePrincipal)));
					arrayListAtributoBotaoDExcluir.add(new Atributo("width", "int", "100"));
					arrayListAtributoBotaoDExcluir.add(new Atributo("height", "int", "25"));
					arrayListAtributoBotaoDExcluir.add(new Atributo("x", "int", "452"));
					arrayListAtributoBotaoDExcluir.add(new Atributo("y", "int", ""+((maiorTamanho*52)+305)));

					//Metodos botao
					ArrayList<Metodo> arrayListMetodosDBotaoExcluir = new ArrayList<Metodo>();

					//Verifica se tem registro
					arrayListMetodosDBotaoExcluir.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosDBotaoExcluir.size())+"verificarValor", "OnClick", new String []{"jTable"+Mascara.filtroClasse(tabela.getNome()), "SelectedRow", "<", "0", ""+Idioma.getTraducao("tetris.message_remove", jFramePrincipal)}, jFramePrincipal));

					//Pergunta se deseja excluir
					arrayListMetodosDBotaoExcluir.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosDBotaoExcluir.size())+"exibirMensagem", "OnClick", new String []{""+Idioma.getTraducao("tetris.message_remove_record", jFramePrincipal), "Perguntar"}, jFramePrincipal));

					String[] parametrosDBotaoExcluir = new String []{
							""+tabela.getNome(),
							"where "+condicaoConsulta
					};

					//Exclui registro
					arrayListMetodosDBotaoExcluir.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosDBotaoExcluir.size())+"excluirRegistro", "OnClick", parametrosDBotaoExcluir, jFramePrincipal));

					//Adiciona metodo que preenche a tabela
					arrayListMetodosDBotaoExcluir.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosDBotaoExcluir.size())+"executarProcedure", "OnClick", new String []{"preencherTabela"}, jFramePrincipal));

					//Adiciona metodo que seleciona registro
					arrayListMetodosDBotaoExcluir.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosDBotaoExcluir.size())+"executarProcedure", "OnClick", new String[]{"selecionarRegistro"}, jFramePrincipal));

					componenteD = new Componente("jButtonExcluir", arrayListAtributoBotaoDExcluir, arrayListMetodosDBotaoExcluir);
					arrayListComponentes.add(componenteD);
					//Fim Botao Excluir

					//Atributos da janela
					ArrayList<Atributo> arrayListAtributoD = new ArrayList<Atributo>();

					arrayListAtributoD.add(new Atributo("Title", "String", nomeTituloJanela[1]));
					arrayListAtributoD.add(new Atributo("x", "int", "0"));
					arrayListAtributoD.add(new Atributo("y", "int", "0"));
					arrayListAtributoD.add(new Atributo("width", "int", "750"));
					arrayListAtributoD.add(new Atributo("height", "int", ""+((maiorTamanho*52)+410)));

					//Metodos
					ArrayList<Metodo> arrayListMetodosD = new ArrayList<Metodo>();

					//Metodo que desabilita os campos ao iniciar
					arrayListMetodosD.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosD.size())+"executarProcedure", "OnShow", new String[]{"desabilitarCampos"}, jFramePrincipal));

					//Metodo que preenche a Tabela ao iniciar
					arrayListMetodosD.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosD.size())+"executarProcedure", "OnShow", new String []{"preencherTabela"}, jFramePrincipal));

					Janela janelaD = new Janela(nomeTituloJanela[0], arrayListAtributoD, arrayListMetodosD);

					janelaD.setArrayListComponentes(arrayListComponentes);

					if(Projeto.adicionarJanela(janelaD, jFramePrincipal)){
						retorno=true;
					}

					break;

				case "consulta":
					String colunasC="";
					String colunasConsulta="";
					for (int i = 0; i < campos.length; i++) {
						if(colunasC!=""){
							colunasC+="/";
							colunasConsulta+=", ";
						}
						colunasC+=campos[i];
						colunasConsulta+=campos[i];
					}

					//Adiciona a tabela
					ArrayList<Atributo> arrayListAtributoTabela = new ArrayList<Atributo>();
					arrayListAtributoTabela.add(new Atributo("Tipo", "String", "Tabela"));
					arrayListAtributoTabela.add(new Atributo("Tabela", "String", ""+tabela.getNome()));
					arrayListAtributoTabela.add(new Atributo("width", "int", "700"));
					arrayListAtributoTabela.add(new Atributo("height", "int", "550"));
					arrayListAtributoTabela.add(new Atributo("x", "int", "15"));
					arrayListAtributoTabela.add(new Atributo("y", "int", "15"));
					arrayListAtributoTabela.add(new Atributo("Columns", "String", colunasC));
					arrayListAtributoTabela.add(new Atributo("Titles", "String", colunasC));

					Componente componenteTabela = new Componente("jTable"+Mascara.filtroClasse(tabela.getNome()), arrayListAtributoTabela, new ArrayList<Metodo>());
					arrayListComponentes.add(componenteTabela);

					//Atributos da janela

					ArrayList<Atributo> arrayListAtributoC = new ArrayList<Atributo>();

					arrayListAtributoC.add(new Atributo("Title", "String", nomeTituloJanela[1]));
					arrayListAtributoC.add(new Atributo("x", "int", "0"));
					arrayListAtributoC.add(new Atributo("y", "int", "0"));
					arrayListAtributoC.add(new Atributo("width", "int", "750"));
					arrayListAtributoC.add(new Atributo("height", "int", "620"));

					//Metodos
					ArrayList<Metodo> arrayListMetodosC = new ArrayList<Metodo>();

					arrayListMetodosC.add(Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+"_"+(arrayListMetodosC.size())+"preencherTabela", "OnShow", new String []{"jTable"+Mascara.filtroClasse(tabela.getNome()), tabela.getNome(), colunasConsulta, "order by "+campos[0]}, jFramePrincipal));

					Janela janelaC = new Janela(nomeTituloJanela[0], arrayListAtributoC, arrayListMetodosC);

					janelaC.setArrayListComponentes(arrayListComponentes);

					if(Projeto.adicionarJanela(janelaC, jFramePrincipal)){
						retorno=true;
					}

					break;
				default:
					break;
				}
			}else{
				JDialogMensagem.exibirMensagem("Perigo", ""+Idioma.getTraducao("tetris.message_add_window", jFramePrincipal));
			}
		}
		System.gc();
		return retorno;
	}

	//Adiciona um componente à janela e retorna true, caso sucesso, e false, caso contrário
	public boolean adicionarComponente(Componente componente, JFramePrincipal jFramePrincipal){
		//Variável de retorno
		boolean retorno=false;

		retorno=true;
		//Verifica se há componente com o mesmo nome
		for (int i = 0; i < arrayListComponentes.size(); i++) {
			if(arrayListComponentes.get(i).getNome().equals(componente.getNome())){
				retorno=false;
				break;
			}
		}

		if(retorno){
			//Adiciona o componente
			arrayListComponentes.add(componente);
		}else{
			JDialogMensagem.exibirMensagem("Perigo", ""+Idioma.getTraducao("tetris.message_add_component", jFramePrincipal)
			+ "<br/><br/>"+Idioma.getTraducao("tetris.name", jFramePrincipal)+": "+componente.getNome());
		}

		return retorno;
	}

	//Remove um componente da janela e retorna true, caso sucesso, e false, caso contrário
	public boolean removerComponente(String nome){
		//Variável de retorno
		boolean retorno=false;
		//Percorre a lista de componentes
		for (int i = 0; i < arrayListComponentes.size(); i++) {
			//Verifica se o componente na posição i é o desejado
			if(arrayListComponentes.get(i).getNome().equals(nome)){
				//Remove o componente
				arrayListComponentes.remove(i);
				retorno=true;
				break;
			}
		}

		return retorno;
	}

	//Adiciona um estado de janela
	public void adicionaEstadoDeJanela(){
		//Remove as modificacoes a frente
		if(posicaoEstadoJanela < arrayListEstadoJanela.size() - 1){
			while(posicaoEstadoJanela < arrayListEstadoJanela.size() - 1){
				arrayListEstadoJanela.remove(arrayListEstadoJanela.size() - 1);
			}
		}

		//adiciona um estado
		arrayListEstadoJanela.add(this.clone());
		posicaoEstadoJanela = arrayListEstadoJanela.size() - 1;

	}

	//Volta um estado de janela
	public void voltaEstadoJanela(){
		if(posicaoEstadoJanela == arrayListEstadoJanela.size() - 1){
			adicionaEstadoDeJanela();
		}

		if(posicaoEstadoJanela > 0){

			posicaoEstadoJanela--;
			selecionaEstadojanela();

		}
	}

	//Avanca um estado de janela
	public void avancaEstadoJanela(){
		if(posicaoEstadoJanela < arrayListEstadoJanela.size() - 1){

			posicaoEstadoJanela++;
			selecionaEstadojanela();

			if(posicaoEstadoJanela == arrayListEstadoJanela.size() - 1){
				arrayListEstadoJanela.remove(posicaoEstadoJanela);
				posicaoEstadoJanela = arrayListEstadoJanela.size() - 1;
			}
		}
	}

	//Seleciona estado de janela
	private void selecionaEstadojanela(){
		Janela janelaEstado = arrayListEstadoJanela.get(posicaoEstadoJanela);
		setNome(janelaEstado.getNome());

		ArrayList<Atributo> arrayListAtributosClone = new ArrayList<Atributo>();
		ArrayList<Metodo> arrayListMetodosClone = new ArrayList<Metodo>();
		ArrayList<Componente> arrayListComponentesClone = new ArrayList<Componente>();

		ArrayList<Atributo> arrayListAtributos = janelaEstado.getArrayListAtributos();
		ArrayList<Metodo> arrayListMetodos = janelaEstado.getArrayListMetodos();
		ArrayList<Componente> arrayListComponentes = janelaEstado.getArrayListComponentes();


		for (int i = 0; i < arrayListAtributos.size(); i++) {
			arrayListAtributosClone.add(arrayListAtributos.get(i).clone());
		}

		for (int i = 0; i < arrayListMetodos.size(); i++) {
			arrayListMetodosClone.add(arrayListMetodos.get(i).clone());
		}

		for (int i = 0; i < arrayListComponentes.size(); i++) {
			arrayListComponentesClone.add(arrayListComponentes.get(i).clone());
		}

		setArrayListAtributos(arrayListAtributosClone);
		setArrayListMetodos(arrayListMetodosClone);
		setArrayListComponentes(arrayListComponentesClone);
	}
	//Getters e Setters
	public Componente getComponente(String componente){
		Componente comp=null;
		for (int i = 0; i < arrayListComponentes.size(); i++) {
			if(arrayListComponentes.get(i).getNome().equals(componente)){
				comp=arrayListComponentes.get(i);
				break;
			}
		}

		return comp;
	}

	public ArrayList<Componente> getArrayListComponentesFilhos(String pai){
		ArrayList<Componente> arrayListComponentesFilhos = new ArrayList<Componente>();

		for (int i = 0; i < arrayListComponentes.size(); i++) {
			if(arrayListComponentes.get(i).getAtributo("Pai")!=null){
				if(arrayListComponentes.get(i).getAtributo("Pai").getValor().equals(pai)){
					arrayListComponentesFilhos.add(arrayListComponentes.get(i));
				}
			}
		}

		return arrayListComponentesFilhos;
	}

	//Retorna um clone da janela
	public Janela clone(){
		//Clona a instância
		Janela janelaClone= null;
		try{
			janelaClone = (Janela)super.clone();
		}catch(Exception exc){
			exc.printStackTrace();
		}
		//Clona os atributos, componentes e métodos
		String nomeClone=""+getNome();
		ArrayList<Atributo> arrayListAtributosClone = new ArrayList<Atributo>();
		ArrayList<Metodo> arrayListMetodosClone = new ArrayList<Metodo>();
		ArrayList<Componente> arrayListComponentesClone = new ArrayList<Componente>();

		ArrayList<Atributo> arrayListAtributos = getArrayListAtributos();
		ArrayList<Metodo> arrayListMetodos = getArrayListMetodos();


		for (int i = 0; i < arrayListAtributos.size(); i++) {
			arrayListAtributosClone.add(arrayListAtributos.get(i).clone());
		}

		for (int i = 0; i < arrayListMetodos.size(); i++) {
			arrayListMetodosClone.add(arrayListMetodos.get(i).clone());
		}

		for (int i = 0; i < arrayListComponentes.size(); i++) {
			arrayListComponentesClone.add(arrayListComponentes.get(i).clone());
		}

		janelaClone.setNome(nomeClone);
		janelaClone.setArrayListAtributos(arrayListAtributosClone);
		janelaClone.setArrayListMetodos(arrayListMetodosClone);
		janelaClone.setArrayListComponentes(arrayListComponentesClone);

		return janelaClone;
	}

	public ArrayList<Componente> getArrayListComponentes() {
		return arrayListComponentes;
	}

	public void setArrayListComponentes(ArrayList<Componente> arrayListComponentes) {
		this.arrayListComponentes = arrayListComponentes;
	}

	public boolean isAlterado() {
		return alterado;
	}

	public void setAlterado(boolean alterado) {
		this.alterado = alterado;
		if(alterado){
			gerado=false;
		}
	}

	public ArrayList<Janela> getArrayListEstadoJanela() {
		return arrayListEstadoJanela;
	}

	public int getPosicaoEstadoJanela() {
		return posicaoEstadoJanela;
	}

	public void setPosicaoEstadoJanela(int posicaoEstadoJanela) {
		this.posicaoEstadoJanela = posicaoEstadoJanela;
	}

	public boolean isGerado() {
		return gerado;
	}

	public void setGerado(boolean gerado) {
		this.gerado = gerado;
	}


}
