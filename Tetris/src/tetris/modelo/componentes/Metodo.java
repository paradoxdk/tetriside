/*
 *Representa método de compoente ou janela do projeto
 *Classe de objetos serializables (graváveis em disco)e cloneables
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 25 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package tetris.modelo.componentes;

import java.io.BufferedWriter;
import java.io.Serializable;
import java.util.ArrayList;

import br.com.analisasoftware.tetris.modelo.Idioma;
import br.com.analisasoftware.tetris.modelo.Projeto;
import br.com.analisasoftware.tetris.visao.janelas.JDialogMensagem;
import br.com.analisasoftware.tetris.visao.janelas.JFramePrincipal;
import componentes.modelo.Mascara;

public class Metodo implements Cloneable, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Definições do método
	private String nome, tipo, codigoFonte;
	//Lista de atributos do método
	private ArrayList<Atributo> arrayListAtributos;

	public Metodo(String nome, String tipo, String codigoFonte,
			ArrayList<Atributo> arrayListAtributos) {
		super();
		this.nome = nome;
		this.tipo = tipo;
		this.codigoFonte = codigoFonte;
		this.arrayListAtributos = arrayListAtributos;
	}
	//Getters e Setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCodigoFonte() {
		return codigoFonte;
	}

	public void setCodigoFonte(String codigoFonte) {
		this.codigoFonte = codigoFonte;
	}

	public ArrayList<Atributo> getArrayListAtributos() {

		return arrayListAtributos;
	}

	public void setArrayListAtributos(ArrayList<Atributo> arrayListAtributos) {
		this.arrayListAtributos = arrayListAtributos;
	}

	public Atributo getAtributo(String atributo){
		Atributo atrib=null;
		for (int i = 0; i < arrayListAtributos.size(); i++) {
			if(arrayListAtributos.get(i).getNome().equals(atributo)){
				atrib=arrayListAtributos.get(i);
				break;
			}
		}

		return atrib;
	}

	//Retorna um vetor com todas as funcoes do TetrisIDE
	public static String[] getMetodos(){
		return new String [] {"abrirJanela", "fecharJanela", "habilitarCampos", 
				"desabilitarCampos", "mudarValor", "mudarFoco", "selecionarRegistro", "gravarRegistro", "alterarRegistro", "preencherTabela", 
				"excluirRegistro", "verificarRegistro", "operacaoMatematica", "exibirMensagem", "verificarValor", "executarProcedure", "visualizarRelatorio", "imprimirRelatorio", "comandoJava"};
	}
	
	//Retorna as funcoes no idioma do sistema
	public static String[] getMetodosTraduzidos(JFramePrincipal jFramePrincipal){
		return new String [] {Idioma.getTraducao("tetris.abrirJanela", jFramePrincipal), Idioma.getTraducao("tetris.fecharJanela", jFramePrincipal), Idioma.getTraducao("tetris.habilitarCampos", jFramePrincipal), 
				Idioma.getTraducao("tetris.desabilitarCampos", jFramePrincipal), Idioma.getTraducao("tetris.mudarValor", jFramePrincipal), Idioma.getTraducao("tetris.mudarFoco", jFramePrincipal), Idioma.getTraducao("tetris.selecionarRegistro", jFramePrincipal), Idioma.getTraducao("tetris.gravarRegistro", jFramePrincipal), Idioma.getTraducao("tetris.alterarRegistro", jFramePrincipal), Idioma.getTraducao("tetris.preencherTabela", jFramePrincipal), 
				Idioma.getTraducao("tetris.excluirRegistro", jFramePrincipal), Idioma.getTraducao("tetris.verificarRegistro", jFramePrincipal), Idioma.getTraducao("tetris.operacaoMatematica", jFramePrincipal), Idioma.getTraducao("tetris.exibirMensagem", jFramePrincipal), Idioma.getTraducao("tetris.verificarValor", jFramePrincipal), Idioma.getTraducao("tetris.executarProcedure", jFramePrincipal), Idioma.getTraducao("tetris.visualizarRelatorio", jFramePrincipal), Idioma.getTraducao("tetris.imprimirRelatorio", jFramePrincipal), Idioma.getTraducao("tetris.comandoJava", jFramePrincipal)};
	}
	//Retorna a dica do método informado por parâmetro
	public static String getDicaMetodo(String metodo, JFramePrincipal jFramePrincipal){
		String retorno="";

		switch (metodo) {
		case "abrirJanela":
			retorno = "1º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.window", jFramePrincipal)+";\n"
					+ "2º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_initial_return_value", jFramePrincipal)+"\n"
					+ "3º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_component_that_receives_the_return", jFramePrincipal)+"\n"
					+ "4º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_component_property", jFramePrincipal)+";\n"
					+ "Ex.:\n"
					+ "JFrameTest";
			break;

		case "fecharJanela":
			retorno = "1º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.window", jFramePrincipal)+" ("+Idioma.getTraducao("tetris.optional", jFramePrincipal)+");\n"
					+ "Ex.:\n"
					+ "JFrameTest";
			break;

		case "habilitarCampos":
			retorno = ""+Idioma.getTraducao("tetris.message_list_the_fields_to_be_enabled", jFramePrincipal)+"\n"
					+ "Ex.:\n"
					+ "jTextField1\n"
					+ "jTextField2\n"
					+ "jTextField3";
			break;

		case "desabilitarCampos":
			retorno = ""+Idioma.getTraducao("tetris.message_list_the_fields_to_be_disabled", jFramePrincipal)+"\n"
					+ "Ex.:\n"
					+ "jTextField1\n"
					+ "jTextField2\n"
					+ "jTextField3";
			break;

		case "mudarValor":
			retorno = "1º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.component", jFramePrincipal)+";\n"
					+ "2º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.property", jFramePrincipal)+";\n"
					+ "3º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.value", jFramePrincipal)+";\n"
					+ "Ex.:\n"
					+ "jTextField1\n"
					+ "Text\n"
					+ "Hello, World!";
			break;

		case "mudarFoco":
			retorno = "1º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.component", jFramePrincipal)+";\n"
					+ "Ex.:\n"
					+ "jTextField1";
			break;

		case "selecionarRegistro":
			retorno = "1º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_database_table", jFramePrincipal)+"\n"
					+ "2º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_fields", jFramePrincipal)+"\n"
					+ "3º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_where_condition", jFramePrincipal)+"\n"
					+ "Ex.:\n"
					+ "client\n"
					+ "id, name\n"
					+ "where id='2'";
			break;

		case "gravarRegistro":
			retorno = "1º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_database_table", jFramePrincipal)+"\n"
					+ "2º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_fields", jFramePrincipal)+"\n"
					+ "3º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.values", jFramePrincipal)+";\n"
					+ "Ex.:\n"
					+ "client\n"
					+ "id, name\n"
					+ "'2', 'David'";
			break;

		case "alterarRegistro":
			retorno = "1º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_database_table", jFramePrincipal)+"\n"
					+ "2º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_fields", jFramePrincipal)+"\n"
					+ "3º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_where_condition", jFramePrincipal)+"\n"
					+ "Ex.:\n"
					+ "client\n"
					+ "id='3', name='David'\n"
					+ "where id='2'";
			break;

		case "preencherTabela":
			retorno ="1º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": jTable;\n" 
					+ "2º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_database_table", jFramePrincipal)+"\n"
					+ "3º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_fields", jFramePrincipal)+"\n"
					+ "4º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_where_condition", jFramePrincipal)+"\n"
					+ "Ex.:\n"
					+ "jTable1\n"
					+ "client\n"
					+ "id, name\n"
					+ "where id='2'";
			break;

		case "excluirRegistro":
			retorno = "1º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_database_table", jFramePrincipal)+"\n"
					+ "2º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_where_condition", jFramePrincipal)+"\n"

					+ "Ex.:\n"
					+ "client\n"
					+ "where id='2'";
			break;

		case "verificarRegistro":
			retorno = "1º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_database_table", jFramePrincipal)+"\n"
					+ "2º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_fields", jFramePrincipal)+"\n"
					+ "3º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_where_condition", jFramePrincipal)+"\n"
					+ "4º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": ("+Idioma.getTraducao("tetris.optional", jFramePrincipal)+") "+Idioma.getTraducao("tetris.message", jFramePrincipal)+";\n"
					+ "5º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+" - "+Idioma.getTraducao("tetris.default", jFramePrincipal)+" (==): ("+Idioma.getTraducao("tetris.optional", jFramePrincipal)+") "+Idioma.getTraducao("tetris.comparator", jFramePrincipal)+" (!=, ==);\n"
					+ "Ex.:\n"
					+ "client\n"
					+ "id\n"
					+ "where id='2'\n"
					+ ""+Idioma.getTraducao("tetris.message_remove", jFramePrincipal)+"\n"
					+ "==";
			break;

		case "operacaoMatematica":
			retorno = "1º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.first_number", jFramePrincipal)+";\n"
					+ "2º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.second_number", jFramePrincipal)+";\n"
					+ "3º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.operation", jFramePrincipal)+" (+, -, *, /);\n"
					+ "4º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_component_to_receive_the_result", jFramePrincipal)+"\n"
					+ "5º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.property", jFramePrincipal)+";\n"
					+ "Ex.:\n"
					+ "5\n"
					+ "3\n"
					+ "+\n"
					+ "jTextField1\n"
					+ "Text";
			break;

		case "exibirMensagem":
			retorno = "1º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_to_show", jFramePrincipal)+"\n"
					+ "2º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.type", jFramePrincipal)+" (Information, Warning, Error, Confirm);\n"
					+ "Ex.:\n"
					+ ""+Idioma.getTraducao("tetris.message_exit_application", jFramePrincipal)+"\n"
					+ "Confirm";
			break;

		case "verificarValor":
			retorno = "1º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.component", jFramePrincipal)+";\n"
					+ "2º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.property", jFramePrincipal)+";\n"
					+ "3º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.comparator", jFramePrincipal)+" (==, !=, >, <, >=, <=);\n"
					+ "4º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_value_to_compare", jFramePrincipal)+"\n"
					+ "5º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": ("+Idioma.getTraducao("tetris.optional", jFramePrincipal)+") "+Idioma.getTraducao("tetris.message", jFramePrincipal)+";\n"
					+ "Ex.:\n"
					+ "jTextField1\n"
					+ "Text\n"
					+ "==\n"
					+ "Test\n"
					+ ""+Idioma.getTraducao("tetris.message_the_value_is_test", jFramePrincipal)+"";
			break;

		case "visualizarRelatorio":
			retorno = "1º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.report", jFramePrincipal)+";\n"
					+ "2º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_database_table", jFramePrincipal)+"\n"
					+ "3º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_fields", jFramePrincipal)+"\n"
					+ "4º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_where_condition", jFramePrincipal)+"\n"
					+ "Ex.:\n"
					+ "report1\n"
					+ "client\n"
					+ "id, name\n"
					+ "where id=2";
			break;

		case "imprimirRelatorio":
			retorno = "1º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.report", jFramePrincipal)+";\n"
					+ "2º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_database_table", jFramePrincipal)+"\n"
					+ "3º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_fields", jFramePrincipal)+"\n"
					+ "4º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": "+Idioma.getTraducao("tetris.message_where_condition", jFramePrincipal)+"\n"
					+ "Ex.:\n"
					+ "report1\n"
					+ "client\n"
					+ "id, name\n"
					+ "where id=2";
			break;

		case "executarProcedure":
			retorno = "1º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": Procedure;\n"
					+ "2º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": ("+Idioma.getTraducao("tetris.optional", jFramePrincipal)+") "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+";\n"
					+ "3º "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+": ("+Idioma.getTraducao("tetris.optional", jFramePrincipal)+") "+Idioma.getTraducao("tetris.parameter", jFramePrincipal)+";\n"
					+ "Ex.:\n"
					+ "procedureSum\n"
					+ "1\n"
					+ "2";
			break;

		case "comandoJava":
			retorno = ""+Idioma.getTraducao("tetris.message_write_any_java_code", jFramePrincipal)+"";
			break;

		default:
			break;
		}

		return retorno;
	}
	//Gera código-fonte do método
	public static void gerarCodigoFonte(BufferedWriter bufferedWriter, Metodo metodo, Janela janela, Componente componente, JFramePrincipal jFramePrincipal){
		try {
			//Captura variáveis
			String codigoFonte = metodo.getCodigoFonte();
			Projeto projeto = jFramePrincipal.getProjeto();
			ArrayList<Atributo> arrayListAtributos = metodo.getArrayListAtributos();

			String nomeMetodo = metodo.getNome().replace("0", "").replace("1", "").replace("2", "").replace("3", "").replace("4", "").replace("5", "").replace("6", "").replace("7", "").replace("8", "").replace("9", "").replace("_", "");
			//Gera código-fonte a partir do tipo do método
			switch (nomeMetodo) {
			case "abrirJanela":
				String parametroRetorno="";
				if(metodo.getAtributo("$2")!=null){
					parametroRetorno="\""+metodo.getAtributo("$2").getValor()+"\"";
				}
				codigoFonte=codigoFonte.replace("$1", metodo.getAtributo("$1").getValor()).replace("#1", Mascara.filtroVariavel(metodo.getAtributo("$1").getValor())).replace("$2", parametroRetorno);

				if(projeto.getJanelaPrincipal().getNome().equals(metodo.getAtributo("$1").getValor())==false){
					codigoFonte=codigoFonte.replace("new "+metodo.getAtributo("$1").getValor()+"()", "new "+metodo.getAtributo("$1").getValor()+"(get"+projeto.getJanelaPrincipal().getNome()+"())");
				}
				bufferedWriter.write("				"+codigoFonte);
				bufferedWriter.newLine();

				if((metodo.getAtributo("$3")!=null) && (metodo.getAtributo("$4")!=null)){
					bufferedWriter.write("				"+metodo.getAtributo("$3").getValor()+".set"+metodo.getAtributo("$4").getValor()+"("+Mascara.filtroVariavel(metodo.getAtributo("$1").getValor())+".getRetorno());");
					bufferedWriter.newLine();
				}

				break;

			case "fecharJanela":
				if(arrayListAtributos.size()>1){
					codigoFonte=Mascara.filtroVariavel(metodo.getAtributo("$1").getValor())+".fecharJanela();";
				}

				bufferedWriter.write("				"+codigoFonte);
				bufferedWriter.newLine();
				break;

			case "habilitarCampos":
				if(arrayListAtributos.size()>1){
					String componentes="";
					for (int i = 0; i < arrayListAtributos.size(); i++) {
						if(arrayListAtributos.get(i).getNome().equals("Evento")==false){
							if(componentes.equals("")==false){
								componentes+=", ";
							}
							componentes+=arrayListAtributos.get(i).getValor();
						}
					}
					codigoFonte="java.awt.Component[] parametros"+metodo.getNome()+" = new java.awt.Component []{\n"+componentes+"\n};\n"+codigoFonte.replace("$parametros", "parametros"+metodo.getNome());
				}

				bufferedWriter.write("				"+codigoFonte);
				bufferedWriter.newLine();
				break;

			case "desabilitarCampos":
				if(arrayListAtributos.size()>1){
					String componentes="";
					for (int i = 0; i < arrayListAtributos.size(); i++) {
						if(arrayListAtributos.get(i).getNome().equals("Evento")==false){
							if(componentes.equals("")==false){
								componentes+=", ";
							}
							componentes+=arrayListAtributos.get(i).getValor();
						}
					}
					codigoFonte="java.awt.Component[] parametros"+metodo.getNome()+" = new java.awt.Component []{\n"+componentes+"\n};\n"+codigoFonte.replace("$parametros", "parametros"+metodo.getNome());
				}

				bufferedWriter.write("				"+codigoFonte);
				bufferedWriter.newLine();
				break;

			case "mudarValor":
				if(arrayListAtributos.size()>1){

					if(metodo.getAtributo("$2").getValor().equals("x")){
						codigoFonte="$1.setLocation($3, $1.getLocation().y);";
					} else if(metodo.getAtributo("$2").getValor().equals("y")){
						codigoFonte="$1.setLocation($1.getLocation().x, $3);";
					}else if(metodo.getAtributo("$2").getValor().equals("width")){
						codigoFonte="$1.setSize($3, $1.getHeight());";
					}else if(metodo.getAtributo("$2").getValor().equals("height")){
						codigoFonte="$1.setSize($1.getWidth(), $3);";
					}

					if(metodo.getAtributo("$1").getValor().equals("this")){
						codigoFonte=codigoFonte.replace("$1.", "");
					}

					String novoValor="";
					if(metodo.getAtributo("$3")!=null){
						novoValor=metodo.getAtributo("$3").getValor();
					}
					if(metodo.getAtributo("$2").getTipo().equals("String")){
						codigoFonte=codigoFonte.replace("$1", metodo.getAtributo("$1").getValor()).replace("$2", metodo.getAtributo("$2").getValor()).replace("$3","\""+novoValor+"\"");
					}else {
						codigoFonte=codigoFonte.replace("$1", metodo.getAtributo("$1").getValor()).replace("$2", metodo.getAtributo("$2").getValor()).replace("$3",""+novoValor+"");
					}

				}

				bufferedWriter.write("				"+codigoFonte);
				bufferedWriter.newLine();
				break;

			case "mudarFoco":
				//Muda o foco para um componente
				codigoFonte=codigoFonte.replace("$1", metodo.getAtributo("$1").getValor());

				bufferedWriter.write("				"+codigoFonte);
				bufferedWriter.newLine();

				break;

			case "operacaoMatematica":
				//Realiza uma das quatro operacoes matematicas
				if(metodo.getAtributo("$5").getTipo().equals("String")){
					codigoFonte=codigoFonte.replace("$1 $3 $2", "\"\"+($1 $3 $2)");
				}

				codigoFonte=codigoFonte.replace("$1", metodo.getAtributo("$1").getValor()).replace("$2", metodo.getAtributo("$2").getValor()).replace("$3",metodo.getAtributo("$3").getValor()).replace("$4",metodo.getAtributo("$4").getValor()).replace("$5",metodo.getAtributo("$5").getValor());


				bufferedWriter.write("				"+codigoFonte);
				bufferedWriter.newLine();

				break;

			case "gravarRegistro":
				//Grava um registro no banco de dados
				
				codigoFonte=codigoFonte.replace("$1", metodo.getAtributo("$1").getValor()).replace("$2", metodo.getAtributo("$2").getValor()).replace("$3",metodo.getAtributo("$3").getValor()).replace("$janelaPrincipal", jFramePrincipal.getProjeto().getJanelaPrincipal().getNome());

				bufferedWriter.write("				"+codigoFonte);
				bufferedWriter.newLine();

				break;

			case "preencherTabela":
				//Preenche uma tabela com registros do banco de dados

				if(metodo.getAtributo("$4")!=null){
					codigoFonte=codigoFonte.replace("$1", metodo.getAtributo("$1").getValor()).replace("$2", metodo.getAtributo("$2").getValor()).replace("$3",metodo.getAtributo("$3").getValor()).replace("$4",metodo.getAtributo("$4").getValor()).replace("$janelaPrincipal", jFramePrincipal.getProjeto().getJanelaPrincipal().getNome());
				}


				bufferedWriter.write("				"+codigoFonte);
				bufferedWriter.newLine();

				break;

			case "selecionarRegistro":
				//Seleciona um registro do banco de dados
				String camposPreencher="";
				String[] colunas = metodo.getAtributo("$2").getValor().split(",");

				ArrayList<Componente> arrayListComponentes = janela.getArrayListComponentes();
				for (int i = 0; i < arrayListComponentes.size(); i++) {
					for (int k = 0; k < colunas.length; k++) {
						if((arrayListComponentes.get(i).getAtributo("Coluna")!=null) || (arrayListComponentes.get(i).getAtributo("Column")!=null)){
							
							Atributo atributo = null;
							if(arrayListComponentes.get(i).getAtributo("Column")!=null){
								atributo = arrayListComponentes.get(i).getAtributo("Column");
							}else if(arrayListComponentes.get(i).getAtributo("Coluna")!=null){
								atributo = arrayListComponentes.get(i).getAtributo("Coluna");
							}
							
							if((atributo.getValor().replace("(", "").replace(")", "").replace("max", "").replace("sum", "").replace("+", "").replace("-", "").replace("count", "")
									.replace("min", "").replace("0", "").replace("1", "").replace("2", "").replace("3", "").replace("4", "").replace("5", "")
									.replace("6", "").replace("7", "").replace("8", "").replace("9", "").replace("/", "").replace("*", "").equals(Mascara.filtroSemEspacos(colunas[k])
											.replace("(", "").replace(")", "").replace("max", "").replace("sum", "").replace("+", "").replace("-", "").replace("count", "")
											.replace("min", "").replace("0", "").replace("1", "").replace("2", "").replace("3", "").replace("4", "").replace("5", "")
											.replace("6", "").replace("7", "").replace("8", "").replace("9", "").replace("/", "").replace("*", ""))) 
											|| (colunas[k].endsWith("as "+atributo.getValor()))
											|| (colunas[k].endsWith(" "+atributo.getValor()))){
								switch (arrayListComponentes.get(i).getTipo()) {
								case "Campo de texto":
									camposPreencher+=arrayListComponentes.get(i).getNome()+".setText(resultSet.getString(\""+Mascara.filtroSemEspacos(atributo.getValor())+"\"));\n";
									break;
									
								case "TextArea":
									camposPreencher+=arrayListComponentes.get(i).getNome()+".setText(resultSet.getString(\""+Mascara.filtroSemEspacos(atributo.getValor())+"\"));\n";
									break;
									
								case "EditorPane":
									camposPreencher+=arrayListComponentes.get(i).getNome()+".setText(resultSet.getString(\""+Mascara.filtroSemEspacos(atributo.getValor())+"\"));\n";
									break;
									
								case "Rotulo":
									camposPreencher+=arrayListComponentes.get(i).getNome()+".setText(resultSet.getString(\""+Mascara.filtroSemEspacos(atributo.getValor())+"\"));\n";
									break;
									
								case "Variable":
									camposPreencher+="set"+arrayListComponentes.get(i).getNome()+"(resultSet.getString(\""+Mascara.filtroSemEspacos(atributo.getValor())+"\"));\n";
									break;

								case "Campo de checagem":
									camposPreencher+=arrayListComponentes.get(i).getNome()+".setSelectedValue(resultSet.getString(\""+Mascara.filtroSemEspacos(atributo.getValor())+"\"));\n";
									break;

								case "Caixa de combinacao":
									camposPreencher+=arrayListComponentes.get(i).getNome()+".setSelectedItem(resultSet.getString(\""+Mascara.filtroSemEspacos(atributo.getValor())+"\"));\n";
									break;
									//colunas[k]
								case "Lista":
									camposPreencher+=arrayListComponentes.get(i).getNome()+".setSelectedValue(resultSet.getString(\""+Mascara.filtroSemEspacos(atributo.getValor())+"\"));\n";
									break;

								default:
									break;
								}
								break;
							}

						}
					}

				}

				if(metodo.getAtributo("$3")!=null){
					codigoFonte=codigoFonte.replace("$1", metodo.getAtributo("$1").getValor()).replace("$2", metodo.getAtributo("$2").getValor()).replace("$3",metodo.getAtributo("$3").getValor()).replace("//...", camposPreencher).replace("$janelaPrincipal", jFramePrincipal.getProjeto().getJanelaPrincipal().getNome());
				}else{
					codigoFonte=codigoFonte.replace("$1", metodo.getAtributo("$1").getValor()).replace("$2", metodo.getAtributo("$2").getValor()).replace("$3","").replace("//...", camposPreencher).replace("$janelaPrincipal", jFramePrincipal.getProjeto().getJanelaPrincipal().getNome());
				}


				bufferedWriter.write("				"+codigoFonte);
				bufferedWriter.newLine();

				break;

			case "verificarRegistro":
				//Verifica um registro do banco de dados

				colunas = metodo.getAtributo("$2").getValor().split(",");

				if(metodo.getAtributo("$5")!=null){
					codigoFonte=codigoFonte.replace("==", metodo.getAtributo("$5").getValor());
				}

				if(metodo.getAtributo("$3")!=null){
					codigoFonte=codigoFonte.replace("$1", metodo.getAtributo("$1").getValor()).replace("$2", metodo.getAtributo("$2").getValor()).replace("$3",metodo.getAtributo("$3").getValor()).replace("$janelaPrincipal", jFramePrincipal.getProjeto().getJanelaPrincipal().getNome());
				}else{
					codigoFonte=codigoFonte.replace("$1", metodo.getAtributo("$1").getValor()).replace("$2", metodo.getAtributo("$2").getValor()).replace("$3","").replace("$janelaPrincipal", jFramePrincipal.getProjeto().getJanelaPrincipal().getNome());
				}

				if(metodo.getAtributo("$4")!=null){
					codigoFonte=codigoFonte.replace("//TODO", "javax.swing.JOptionPane.showMessageDialog(get"+janela.getNome()+"(), \""+metodo.getAtributo("$4").getValor()+"\", \""+Idioma.getTraducao("tetris.message", jFramePrincipal)+"\", javax.swing.JOptionPane.INFORMATION_MESSAGE);\n");
				}else{
					codigoFonte=codigoFonte.replace("//TODO", "\n");
				}

				bufferedWriter.write("				"+codigoFonte);
				bufferedWriter.newLine();

				break;

			case "excluirRegistro":
				//Exclui registro do banco de dados
				
				if(metodo.getAtributo("$2")!=null){
					codigoFonte=codigoFonte.replace("$1", metodo.getAtributo("$1").getValor()).replace("$2", metodo.getAtributo("$2").getValor()).replace("$janelaPrincipal", jFramePrincipal.getProjeto().getJanelaPrincipal().getNome());
				}else{
					codigoFonte=codigoFonte.replace("$1", metodo.getAtributo("$1").getValor()).replace("$2", "").replace("$janelaPrincipal", jFramePrincipal.getProjeto().getJanelaPrincipal().getNome());
				}


				bufferedWriter.write("				"+codigoFonte);
				bufferedWriter.newLine();

				break;

			case "alterarRegistro":
				//altera registro do banco de dados
				
				if(metodo.getAtributo("$3")!=null){
					codigoFonte=codigoFonte.replace("$1", metodo.getAtributo("$1").getValor()).replace("$2", metodo.getAtributo("$2").getValor()).replace("$3", metodo.getAtributo("$3").getValor()).replace("$janelaPrincipal", jFramePrincipal.getProjeto().getJanelaPrincipal().getNome());
				}else{
					codigoFonte=codigoFonte.replace("$1", metodo.getAtributo("$1").getValor()).replace("$2", metodo.getAtributo("$2").getValor()).replace("$3", "").replace("$janelaPrincipal", jFramePrincipal.getProjeto().getJanelaPrincipal().getNome());
				}


				bufferedWriter.write("				"+codigoFonte);
				bufferedWriter.newLine();

				break;

			case "exibirMensagem":

				if((metodo.getAtributo("$2").getValor().equals("Informar")) || (metodo.getAtributo("$2").getValor().equals("Information"))){
					codigoFonte="javax.swing.JOptionPane.showMessageDialog(get"+janela.getNome()+"(), \""+metodo.getAtributo("$1").getValor()+"\", \""+Idioma.getTraducao("tetris.message", jFramePrincipal)+"\", javax.swing.JOptionPane.INFORMATION_MESSAGE);";
				}else if((metodo.getAtributo("$2").getValor().equals("Perigo")) || (metodo.getAtributo("$2").getValor().equals("Warning"))){
					codigoFonte="javax.swing.JOptionPane.showMessageDialog(get"+janela.getNome()+"(), \""+metodo.getAtributo("$1").getValor()+"\", \""+Idioma.getTraducao("tetris.message", jFramePrincipal)+"\", javax.swing.JOptionPane.WARNING_MESSAGE);";
				}else if((metodo.getAtributo("$2").getValor().equals("Erro")) || (metodo.getAtributo("$2").getValor().equals("Error"))){
					codigoFonte="javax.swing.JOptionPane.showMessageDialog(get"+janela.getNome()+"(), \""+metodo.getAtributo("$1").getValor()+"\", \""+Idioma.getTraducao("tetris.message", jFramePrincipal)+"\", javax.swing.JOptionPane.ERROR_MESSAGE);";
				}else if((metodo.getAtributo("$2").getValor().equals("Perguntar")) || (metodo.getAtributo("$2").getValor().equals("Confirm"))){
					codigoFonte="if(javax.swing.JOptionPane.showConfirmDialog(get"+janela.getNome()+"(), \""+metodo.getAtributo("$1").getValor()+"\", \""+Idioma.getTraducao("tetris.message", jFramePrincipal)+"\", javax.swing.JOptionPane.YES_NO_OPTION)!=javax.swing.JOptionPane.YES_OPTION){\n"
							+ "	return;\n"
							+ "}\n";
				}

				bufferedWriter.write("				"+codigoFonte);
				bufferedWriter.newLine();

				break;

			case "verificarValor":
				String valorComparar="";
				if(metodo.getAtributo("$4")!=null){
					valorComparar=metodo.getAtributo("$4").getValor();
				}
				if((metodo.getAtributo("$2").getValor().equals("Text")) || (metodo.getAtributo("$2").getValor().equals("Title"))
						|| (metodo.getAtributo("$2").getValor().equals("Retorno")) || (metodo.getAtributo("$2").getValor().equals("Return"))){
					if(metodo.getAtributo("$3").getValor().equals("!=")){
						codigoFonte=codigoFonte.replace("$1", "!"+metodo.getAtributo("$1").getValor()).replace("$2", metodo.getAtributo("$2").getValor()).replace("$3", ".equals").replace("$4", "\""+valorComparar+"\"");
					}else{
						codigoFonte=codigoFonte.replace("$1", metodo.getAtributo("$1").getValor()).replace("$2", metodo.getAtributo("$2").getValor()).replace("$3", ".equals").replace("$4", "\""+valorComparar+"\"");
					}
				}else{
					codigoFonte=codigoFonte.replace("$1", metodo.getAtributo("$1").getValor()).replace("$2", metodo.getAtributo("$2").getValor()).replace("$3", metodo.getAtributo("$3").getValor()).replace("$4", valorComparar);
				}

				if((metodo.getAtributo("$2").getValor().equals("Enabled")) || (metodo.getAtributo("$2").getValor().equals("Editable"))
						|| (metodo.getAtributo("$2").getValor().equals("Visible")) || (metodo.getAtributo("$2").getValor().equals("Resizable"))
						|| (metodo.getAtributo("$2").getValor().equals("Iconifiable")) || (metodo.getAtributo("$2").getValor().equals("Closable"))
						|| (metodo.getAtributo("$2").getValor().equals("Modal"))){
					codigoFonte=codigoFonte.replace("get", "is");
				}

				if(metodo.getAtributo("$5")!=null){
					codigoFonte=codigoFonte.replace("\\", "javax.swing.JOptionPane.showMessageDialog(get"+janela.getNome()+"(), \""+metodo.getAtributo("$5").getValor()+"\", \""+Idioma.getTraducao("tetris.message", jFramePrincipal)+"\", javax.swing.JOptionPane.INFORMATION_MESSAGE);");
				}else{
					codigoFonte=codigoFonte.replace("\\", "");
				}

				bufferedWriter.write("				"+codigoFonte);
				bufferedWriter.newLine();

				break;

			case "executarProcedure":
				//Executa procedure
				codigoFonte=codigoFonte.replace("$1", metodo.getAtributo("$1").getValor());
				String parameters="";
				int parameterCount=2;
				Atributo atributo;
				while((atributo=metodo.getAtributo("$"+parameterCount))!=null) {
					if(!parameters.equals("")) {
						parameters=parameters+", ";
					}
					parameters=parameters+atributo.getValor();
					
					parameterCount++;
				}
				
				if(!parameters.equals("")) {
					codigoFonte=codigoFonte.replace("();", "("+parameters+");");
				}

				bufferedWriter.write("				"+codigoFonte);
				bufferedWriter.newLine();

				break;

			case "visualizarRelatorio":
				//Visualiza um relatorio


				String codigoFont=""+codigoFonte;
				
				if(metodo.getAtributo("$4")!=null){
					codigoFont=codigoFont.replace("$1.preview();", "\n\r").replace("$1", metodo.getAtributo("$1").getValor()).replace("$2", metodo.getAtributo("$2").getValor()).replace("$3", metodo.getAtributo("$3").getValor()).replace("$4",metodo.getAtributo("$4").getValor()).replace("$janelaPrincipal", jFramePrincipal.getProjeto().getJanelaPrincipal().getNome());
				}else{
					codigoFont=codigoFont.replace("$1.preview();", "\n\r").replace("$1", metodo.getAtributo("$1").getValor()).replace("$2", metodo.getAtributo("$2").getValor()).replace("$3", metodo.getAtributo("$3").getValor()).replace("$4","").replace("$janelaPrincipal", jFramePrincipal.getProjeto().getJanelaPrincipal().getNome());
				}

				Atributo atributoVisualizar = janela.getComponente(metodo.getAtributo("$1").getValor()).getAtributo("Detail");
				Atributo atributoHeader = janela.getComponente(metodo.getAtributo("$1").getValor()).getAtributo("Header");
				Atributo atributoSumary = janela.getComponente(metodo.getAtributo("$1").getValor()).getAtributo("Sumary");

				String[] vetorColunasVisualizar = metodo.getAtributo("$3").getValor().split(",");

				
				if(atributoVisualizar!=null){
					String detail = "";
					if(atributoVisualizar!=null){
						detail = atributoVisualizar.getValor().replace("\"", "\\\"").replace("\n", "").replace("\r", "").replace("$\\\\\"", "\"").replace("$\\\"", "\"");
					}
					String header = "";
					if(atributoHeader!=null){
						header = atributoHeader.getValor().replace("\"", "\\\"").replace("\n", "").replace("\r", "").replace("$\\\\\"", "\"").replace("$\\\"", "\"");
					}
					String sumary = "";
					if(atributoSumary!=null){
						sumary = atributoSumary.getValor().replace("\"", "\\\"").replace("\n", "").replace("\r", "").replace("$\\\\\"", "\"").replace("$\\\"", "\"");
					}

					for (int i = 0; i < vetorColunasVisualizar.length; i++) {
						if(vetorColunasVisualizar[i].contains(" as ")){
							String colu = vetorColunasVisualizar[i].split(" as ")[1];
							detail=detail.replace("$"+colu.replace(" ", ""), "\"+resultSet.getString(\""+colu.replace(" ", "")+"\")+\"");
						}else{
							detail=detail.replace("$"+vetorColunasVisualizar[i].replace(" ", ""), "\"+resultSet.getString(\""+vetorColunasVisualizar[i].replace(" ", "")+"\")+\"");
						}
					}

					String headFoot="";
					
					Atributo atributoHead = janela.getComponente(metodo.getAtributo("$1").getValor()).getAtributo("Head");
					Atributo atributoFoot = janela.getComponente(metodo.getAtributo("$1").getValor()).getAtributo("Foot");
					
					if(atributoHead!=null){
						headFoot=metodo.getAtributo("$1").getValor()+".setHead(\""+atributoHead.getValor()+"\");\r\n";
					}
					
					if(atributoFoot!=null){
						headFoot=headFoot+metodo.getAtributo("$1").getValor()+".setFoot(\""+atributoFoot.getValor()+"\");\r\n";
					}

					codigoFont=headFoot+"\r\n"+metodo.getAtributo("$1").getValor()+".setHeader("+"\""+header.replace("\n", "").replace("\r", "")+"\");\n\r"+codigoFont;
					codigoFont=codigoFont.replace("//TODO", metodo.getAtributo("$1").getValor()+".setDetail("+metodo.getAtributo("$1").getValor()+".getDetail()+\""+detail.replace("\n", "").replace("\r", "")+"\");\n\r");
					codigoFont=codigoFont+metodo.getAtributo("$1").getValor()+".setSumary("+"\""+sumary.replace("\n", "").replace("\r", "")+"\");\n\r"+metodo.getAtributo("$1").getValor()+".preview();\n\r";
				}

				bufferedWriter.write("				"+codigoFont);
				bufferedWriter.newLine();

				break;

			case "imprimirRelatorio":
				//Imprime um relatorio

				codigoFont=""+codigoFonte;
				
				if(metodo.getAtributo("$4")!=null){
					codigoFont=codigoFont.replace("$1.print();", "\n\r").replace("$1", metodo.getAtributo("$1").getValor()).replace("$2", metodo.getAtributo("$2").getValor()).replace("$3", metodo.getAtributo("$3").getValor()).replace("$4",metodo.getAtributo("$4").getValor()).replace("$janelaPrincipal", jFramePrincipal.getProjeto().getJanelaPrincipal().getNome());
				}else{
					codigoFont=codigoFont.replace("$1.print();", "\n\r").replace("$1", metodo.getAtributo("$1").getValor()).replace("$2", metodo.getAtributo("$2").getValor()).replace("$3", metodo.getAtributo("$3").getValor()).replace("$4","").replace("$janelaPrincipal", jFramePrincipal.getProjeto().getJanelaPrincipal().getNome());
				}

				Atributo atributoImprimir = janela.getComponente(metodo.getAtributo("$1").getValor()).getAtributo("Detail");
				Atributo atributoHeaderI = janela.getComponente(metodo.getAtributo("$1").getValor()).getAtributo("Header");
				Atributo atributoSumaryI = janela.getComponente(metodo.getAtributo("$1").getValor()).getAtributo("Sumary");

				String[] vetorColunasImprimir = metodo.getAtributo("$3").getValor().split(",");

				if(atributoImprimir!=null){
					String detail = "";
					if(atributoImprimir!=null){
						detail = atributoImprimir.getValor().replace("\"", "\\\"").replace("\n", "").replace("\r", "").replace("$\\\\\"", "\"").replace("$\\\"", "\"");
					}
					String header = "";
					if(atributoHeaderI!=null){
						header = atributoHeaderI.getValor().replace("\"", "\\\"").replace("\n", "").replace("\r", "").replace("$\\\\\"", "\"").replace("$\\\"", "\"");
					}
					String sumary = "";
					if(atributoSumaryI!=null){
						sumary = atributoSumaryI.getValor().replace("\"", "\\\"").replace("\n", "").replace("\r", "").replace("$\\\\\"", "\"").replace("$\\\"", "\"");
					}

					for (int i = 0; i < vetorColunasImprimir.length; i++) {
						if(vetorColunasImprimir[i].contains(" as ")){
							String colu = vetorColunasImprimir[i].split(" as ")[1];
							detail=detail.replace("$"+colu.replace(" ", ""), "\"+resultSet.getString(\""+colu.replace(" ", "")+"\")+\"");
						}else{
							detail=detail.replace("$"+vetorColunasImprimir[i].replace(" ", ""), "\"+resultSet.getString(\""+vetorColunasImprimir[i].replace(" ", "")+"\")+\"");
						}
					}
					
					String headFoot="";
					
					Atributo atributoHead = janela.getComponente(metodo.getAtributo("$1").getValor()).getAtributo("Head");
					Atributo atributoFoot = janela.getComponente(metodo.getAtributo("$1").getValor()).getAtributo("Foot");
					
					if(atributoHead!=null){
						headFoot=metodo.getAtributo("$1").getValor()+".setHead(\""+atributoHead.getValor()+"\");\r\n";
					}
					
					if(atributoFoot!=null){
						headFoot=headFoot+metodo.getAtributo("$1").getValor()+".setFoot(\""+atributoFoot.getValor()+"\");\r\n";
					}

					codigoFont=headFoot+"\r\n"+metodo.getAtributo("$1").getValor()+".setHeader("+"\""+header.replace("\n", "").replace("\r", "")+"\");\n\r"+codigoFont;
					codigoFont=codigoFont.replace("//TODO", metodo.getAtributo("$1").getValor()+".setDetail("+metodo.getAtributo("$1").getValor()+".getDetail()+\""+detail.replace("\n", "").replace("\r", "")+"\");\n\r");
					codigoFont=codigoFont+metodo.getAtributo("$1").getValor()+".setSumary("+"\""+sumary.replace("\n", "").replace("\r", "")+"\");\n\r"+metodo.getAtributo("$1").getValor()+".print();\n\r";
				}

				bufferedWriter.write("				"+codigoFont);
				bufferedWriter.newLine();

				break;

			case "comandoJava":
				if(arrayListAtributos.size()>1){
					for (int i = 1; i < arrayListAtributos.size(); i++) {
						codigoFonte=codigoFonte+"\n"+metodo.getAtributo("$"+i).getValor();
					}
				}

				bufferedWriter.write("				"+codigoFonte);
				bufferedWriter.newLine();
				break;

			default:
				break;
			}
		} catch (Exception exc) {
			// TODO: handle exception
			exc.printStackTrace();
			JDialogMensagem.exibirMensagem("Erro", ""+Idioma.getTraducao("tetris.message_error_generating_source_of_the_method", jFramePrincipal)+" "+metodo.getNome());
		}
	}
	//Cria e retorna um método com tecla
	public static Metodo criarMetodo(String nomeMetodo, String evento, String[] parametros, JFramePrincipal jFramePrincipal, String tecla){
		//Cria método
		Metodo metodo = criarMetodo(nomeMetodo, evento, parametros, jFramePrincipal);
		//Verifica se tem tecla associada ao método
		if(tecla!=null){
			metodo.setTipo(tecla);
		}

		return metodo;
	}
	//Cria e retorna um método
	public static Metodo criarMetodo(String nomeMetodo, String evento, String[] parametros, JFramePrincipal jFramePrincipal){
		//Variáveis de definições do método
		Metodo metodo = null;
		ArrayList<Atributo> arrayListAtrib = new ArrayList<Atributo>();
		String tipo="", codigoFonte="";
		String nomeDoMetodo=""+nomeMetodo;
		//Remove a codificação numérica do nome do método
		nomeMetodo=nomeMetodo.replace("0", "").replace("1", "").replace("2", "").replace("3", "").replace("4", "").replace("5", "").replace("6", "").replace("7", "").replace("8", "").replace("9", "").replace("_", "");
		//Gera código-fonte a partir do tipo do método
		switch (nomeMetodo) {
		case "abrirJanela":
			//Abre uma janela

			tipo="void";

			//Codigo fonte do metodo
			codigoFonte="$1 #1 = new $1();\n"
					+ "#1.init($2);\n";

			arrayListAtrib.add(new Atributo("Evento", "String", ""+evento));
			for (int i = 0; i < parametros.length; i++) {
				String tipoAtributo="";
				if(i==0){
					tipoAtributo="Component";

				}
				arrayListAtrib.add(new Atributo("$"+(i+1), tipoAtributo, parametros[i]));
			}

			break;


		case "fecharJanela":
			//Fecha uma janela

			tipo="void";

			//Codigo fonte do metodo
			codigoFonte="fecharJanela();";

			arrayListAtrib.add(new Atributo("Evento", "String", ""+evento));
			for (int i = 0; i < parametros.length; i++) {
				String tipoAtributo="";
				if(i==0){
					tipoAtributo="Component";

				}
				arrayListAtrib.add(new Atributo("$"+(i+1), tipoAtributo, parametros[i]));
			}

			break;

		case "habilitarCampos":
			//Habilita campos numa janela

			tipo="void";

			//Codigo fonte do metodo

			codigoFonte="for(int i=0; i<$parametros.length; i++){\n"
					+ "	$parametros[i].setEnabled(true);\n"
					+ "}";

			arrayListAtrib.add(new Atributo("Evento", "String", ""+evento));
			for (int i = 0; i < parametros.length; i++) {
				String tipoAtributo="";

				tipoAtributo="Component";

				arrayListAtrib.add(new Atributo("$"+(i+1), tipoAtributo, parametros[i]));
			}

			break;

		case "desabilitarCampos":
			//Desabilita campos numa janela

			tipo="void";

			//Codigo fonte do metodo

			codigoFonte="for(int i=0; i<$parametros.length; i++){\n"
					+ "	$parametros[i].setEnabled(false);\n"
					+ "}";

			arrayListAtrib.add(new Atributo("Evento", "String", ""+evento));
			for (int i = 0; i < parametros.length; i++) {
				String tipoAtributo="";
				tipoAtributo="Component";

				arrayListAtrib.add(new Atributo("$"+(i+1), tipoAtributo, parametros[i]));
			}

			break;

		case "mudarValor":
			//Muda valor de atributo de um componente

			tipo="void";

			//Codigo fonte do metodo
			codigoFonte="$1.set$2($3);";

			arrayListAtrib.add(new Atributo("Evento", "String", ""+evento));
			for (int i = 0; i < parametros.length; i++) {
				String tipoAtributo="Variant";
				if(i==1){
					if((parametros[i].equals("Text")) || (parametros[i].equals("Title")) 
							|| (parametros[i].equals("Valor")) || (parametros[i].equals("SelectedValue"))
							|| (parametros[i].equals("Retorno")) || (parametros[i].equals("Return"))){
						tipoAtributo="String";
					}
				}

				arrayListAtrib.add(new Atributo("$"+(i+1), tipoAtributo, parametros[i]));
			}

			break;

		case "mudarFoco":
			//Muda o foco para um componente

			tipo="void";

			//Codigo fonte do metodo
			codigoFonte="$1.requestFocus();";

			arrayListAtrib.add(new Atributo("Evento", "String", ""+evento));
			for (int i = 0; i < parametros.length; i++) {
				String tipoAtributo="Component";

				arrayListAtrib.add(new Atributo("$"+(i+1), tipoAtributo, parametros[i]));
			}

			break;

		case "operacaoMatematica":
			//Realiza uma das quatro operacoes matematicas

			tipo="void";

			//Codigo fonte do metodo
			codigoFonte="	$4.set$5($1 $3 $2);\n";

			arrayListAtrib.add(new Atributo("Evento", "String", ""+evento));
			for (int i = 0; i < parametros.length; i++) {
				String tipoAtributo="";
				if(i==0){
					tipoAtributo="double";

				}else if(i==1){
					tipoAtributo="double";

				}else if(i==2){
					tipoAtributo="String";

				}else if(i==3){
					tipoAtributo="Variant";

				}else if(i==4){
					tipoAtributo="Variant";
					if((parametros[i].equals("Text")) || (parametros[i].equals("Title"))){
						tipoAtributo="String";
					}
				}

				arrayListAtrib.add(new Atributo("$"+(i+1), tipoAtributo, parametros[i]));
			}

			break;

		case "gravarRegistro":
			//Grava registro no banco de dados

			tipo="void";

			//Codigo fonte do metodo
			codigoFonte="	try{\n"
					+ "			java.sql.Connection conexao = get$janelaPrincipal().getBancoDeDados().getConexao();\n"
					+ "			get$janelaPrincipal().getBancoDeDados().comandoInsert(conexao, \"$1\", \"$2\", \"$3\");\n"
					+ "			conexao.close();\n"
					+ "		}catch(Exception exc){\n"
					+ "			javax.swing.JOptionPane.showMessageDialog(null, \""+Idioma.getTraducao("tetris.message_error_inserting_record", jFramePrincipal)+"\\n\"+exc);\n"
					+ "		}";

			arrayListAtrib.add(new Atributo("Evento", "String", ""+evento));
			for (int i = 0; i < parametros.length; i++) {
				String tipoAtributo="Variant";

				arrayListAtrib.add(new Atributo("$"+(i+1), tipoAtributo, parametros[i]));
			}

			break;

		case "preencherTabela":
			//Preenche uma tabela com dados do banco

			tipo="void";

			//Codigo fonte do metodo
			codigoFonte="	try{\n"
					+ "			java.sql.Connection conexao = get$janelaPrincipal().getBancoDeDados().getConexao();\n"
					+ "			java.sql.ResultSet resultSet = get$janelaPrincipal().getBancoDeDados().comandoSelect(conexao, \"$2\", \"$3\", \"$4\");\n"
					+ "			$1.fillTable(resultSet);\n"
					+ "			resultSet.close();\n"
					+ "			conexao.close();\n"
					+ "		}catch(Exception exc){\n"
					+ "			javax.swing.JOptionPane.showMessageDialog(null, \""+Idioma.getTraducao("tetris.message_error_filling_table", jFramePrincipal)+"\\n\"+exc);\n"
					+ "		}";

			arrayListAtrib.add(new Atributo("Evento", "String", ""+evento));
			for (int i = 0; i < parametros.length; i++) {
				String tipoAtributo="Variant";

				arrayListAtrib.add(new Atributo("$"+(i+1), tipoAtributo, parametros[i]));
			}

			break;

		case "selecionarRegistro":
			//Seleciona registro do banco de dados

			tipo="void";

			//Codigo fonte do metodo
			codigoFonte="	try{\n"
					+ "			java.sql.Connection conexao = get$janelaPrincipal().getBancoDeDados().getConexao();\n"
					+ "			java.sql.ResultSet resultSet = get$janelaPrincipal().getBancoDeDados().comandoSelect(conexao, \"$1\", \"$2\", \"$3\");\n"
					+ "			if(resultSet != null){\n"
					+ "				resultSet.next();\n"
					+ "				if(resultSet.getRow() > 0){\n"
					+ "					//..."
					+ "				}\n"
					+ "				resultSet.close();\n"
					+ "			}\n"
					+ "			conexao.close();\n"
					+ "		}catch(Exception exc){\n"
					+ "			javax.swing.JOptionPane.showMessageDialog(null, \""+Idioma.getTraducao("tetris.message_error_selecting_record", jFramePrincipal)+"\\n\"+exc);\n"
					+ "		}";

			arrayListAtrib.add(new Atributo("Evento", "String", ""+evento));
			for (int i = 0; i < parametros.length; i++) {
				String tipoAtributo="Variant";

				arrayListAtrib.add(new Atributo("$"+(i+1), tipoAtributo, parametros[i]));
			}

			break;

		case "verificarRegistro":
			//Verificar registro do banco de dados

			tipo="void";

			//Codigo fonte do metodo
			codigoFonte="	try{\n"
					+ "			java.sql.Connection conexao = get$janelaPrincipal().getBancoDeDados().getConexao();\n"
					+ "			java.sql.ResultSet resultSet = get$janelaPrincipal().getBancoDeDados().comandoSelect(conexao, \"$1\", \"$2\", \"$3\");\n"
					+ "			if(resultSet != null){\n"
					+ "				resultSet.next();\n"
					+ "				if(resultSet.getRow() == 0){\n"
					+ "					//TODO"
					+ "					return;"
					+ "				}\n"
					+ "				resultSet.close();\n"
					+ "			}\n"
					+ "			conexao.close();\n"
					+ "		}catch(Exception exc){\n"
					+ "			javax.swing.JOptionPane.showMessageDialog(null, \""+Idioma.getTraducao("tetris.message_error_selecting_record", jFramePrincipal)+"\\n\"+exc);\n"
					+ "		}";

			arrayListAtrib.add(new Atributo("Evento", "String", ""+evento));
			for (int i = 0; i < parametros.length; i++) {
				String tipoAtributo="Variant";

				arrayListAtrib.add(new Atributo("$"+(i+1), tipoAtributo, parametros[i]));
			}

			break;

		case "excluirRegistro":
			//Exclui registro do banco de dados

			tipo="void";

			//Codigo fonte do metodo
			codigoFonte="	try{\n"
					+ "			java.sql.Connection conexao = get$janelaPrincipal().getBancoDeDados().getConexao();\n"
					+ "			get$janelaPrincipal().getBancoDeDados().comandoDelete(conexao, \"$1\", \"$2\");\n"

					+ "			conexao.close();\n"
					+ "		}catch(Exception exc){\n"
					+ "			javax.swing.JOptionPane.showMessageDialog(null, \""+Idioma.getTraducao("tetris.message_error_deleting_record", jFramePrincipal)+"\\n\"+exc);\n"
					+ "		}";

			arrayListAtrib.add(new Atributo("Evento", "String", ""+evento));
			for (int i = 0; i < parametros.length; i++) {
				String tipoAtributo="Variant";

				arrayListAtrib.add(new Atributo("$"+(i+1), tipoAtributo, parametros[i]));
			}

			break;

		case "alterarRegistro":
			//Altera registro do banco de dados

			tipo="void";

			//Codigo fonte do metodo
			codigoFonte="	try{\n"
					+ "			java.sql.Connection conexao = get$janelaPrincipal().getBancoDeDados().getConexao();\n"
					+ "			get$janelaPrincipal().getBancoDeDados().comandoUpdate(conexao, \"$1\", \"$2\", \"$3\");\n"

					+ "			conexao.close();\n"
					+ "		}catch(Exception exc){\n"
					+ "			javax.swing.JOptionPane.showMessageDialog(null, \""+Idioma.getTraducao("tetris.message_error_updating_record", jFramePrincipal)+"\\n\"+exc);\n"
					+ "		}";

			arrayListAtrib.add(new Atributo("Evento", "String", ""+evento));
			for (int i = 0; i < parametros.length; i++) {
				String tipoAtributo="Variant";

				arrayListAtrib.add(new Atributo("$"+(i+1), tipoAtributo, parametros[i]));
			}

			break;

		case "executarProcedure":
			//Executa procedure

			tipo="void";

			//Codigo fonte do metodo
			codigoFonte="$1();";

			arrayListAtrib.add(new Atributo("Evento", "String", ""+evento));
			for (int i = 0; i < parametros.length; i++) {
				String tipoAtributo="Procedure";

				arrayListAtrib.add(new Atributo("$"+(i+1), tipoAtributo, parametros[i]));
			}

			break;

		case "comandoJava":
			//Executa um comando Java

			tipo="void";

			//Codigo fonte do metodo

			codigoFonte="//\n";

			arrayListAtrib.add(new Atributo("Evento", "String", ""+evento));
			for (int i = 0; i < parametros.length; i++) {
				String tipoAtributo="";

				tipoAtributo="Variant";

				arrayListAtrib.add(new Atributo("$"+(i+1), tipoAtributo, parametros[i]));
			}

			break;

		case "exibirMensagem":
			//Exibe uma mensagem

			tipo="void";

			//Codigo fonte do metodo

			codigoFonte="//\n";

			arrayListAtrib.add(new Atributo("Evento", "String", ""+evento));
			for (int i = 0; i < parametros.length; i++) {
				String tipoAtributo="";

				tipoAtributo="Variant";

				arrayListAtrib.add(new Atributo("$"+(i+1), tipoAtributo, parametros[i]));
			}

			break;

		case "verificarValor":
			//Verifica valor
			tipo="void";

			//Codigo fonte do metodo

			codigoFonte="if($1.get$2()$3($4)){\n";
			codigoFonte=codigoFonte+"\\\n";
			codigoFonte=codigoFonte+ "return;\n"
					+ "}\n";

			arrayListAtrib.add(new Atributo("Evento", "String", ""+evento));
			for (int i = 0; i < parametros.length; i++) {
				String tipoAtributo="";

				tipoAtributo="Variant";

				arrayListAtrib.add(new Atributo("$"+(i+1), tipoAtributo, parametros[i]));
			}

			break;

		case "visualizarRelatorio":
			//Visualiza relatorio

			tipo="void";

			//Codigo fonte do metodo
			codigoFonte="	try{\n"
					+ "			"
					+ "			"
					+ "			java.sql.Connection conexao = get$janelaPrincipal().getBancoDeDados().getConexao();\n"
					+ "			java.sql.ResultSet resultSet = get$janelaPrincipal().getBancoDeDados().comandoSelect(conexao, \"$2\", \"$3\", \"$4\");\n"
					+ "			if(resultSet != null){\n"
					+ "				resultSet.next();\n"
					+"				\n\r"
					+ "				$1.setDetail(\"\");\n"
					+ "				if(resultSet.getRow() > 0){\n"
					+ "					\n"
					+ "					do{\n"
					+ "						//TODO\n"
					+ "					}while(resultSet.next());\n"
					+ "					$1.preview();\n"
					+ "				}else{\n"
					+ "					javax.swing.JOptionPane.showMessageDialog(null, \""+Idioma.getTraducao("tetris.message_remove", jFramePrincipal)+"\");\n"
					+ "				}\n"
					+ "			"
					+ "				resultSet.close();\n"
					+ "			}\n"
					+ "			conexao.close();\n"
					+ "		}catch(Exception exc){\n"
					+ "			javax.swing.JOptionPane.showMessageDialog(null, \""+Idioma.getTraducao("tetris.message_error_selecting_record", jFramePrincipal)+"\\n\"+exc);\n"
					+ "		}";

			arrayListAtrib.add(new Atributo("Evento", "String", ""+evento));
			for (int i = 0; i < parametros.length; i++) {
				String tipoAtributo="Variant";

				arrayListAtrib.add(new Atributo("$"+(i+1), tipoAtributo, parametros[i]));
			}

			break;

		case "imprimirRelatorio":
			//Imprimir relatorio

			tipo="void";

			//Codigo fonte do metodo
			codigoFonte="	try{\n"
					+ "			"
					+ "			"
					+ "			java.sql.Connection conexao = get$janelaPrincipal().getBancoDeDados().getConexao();\n"
					+ "			java.sql.ResultSet resultSet = get$janelaPrincipal().getBancoDeDados().comandoSelect(conexao, \"$2\", \"$3\", \"$4\");\n"
					+ "			if(resultSet != null){\n"
					+ "				resultSet.next();\n"
					+ "				$1.setDetail(\"\");\n"
					+ "				if(resultSet.getRow() > 0){\n"
					+ "					\n"
					+ "					do{\n"
					+ "						//TODO\n"
					+ "					}while(resultSet.next());\n"
					+ "					$1.print();\n"
					+ "				}else{\n"
					+ "					javax.swing.JOptionPane.showMessageDialog(null, \""+Idioma.getTraducao("tetris.message_remove", jFramePrincipal)+"\");\n"
					+ "				}\n"
					+ "				resultSet.close();\n"
					+ "			}\n"
					+ "			conexao.close();\n"
					+ "		}catch(Exception exc){\n"
					+ "			javax.swing.JOptionPane.showMessageDialog(null, \""+Idioma.getTraducao("tetris.message_error_selecting_record", jFramePrincipal)+"\\n\"+exc);\n"
					+ "		}";

			arrayListAtrib.add(new Atributo("Evento", "String", ""+evento));
			for (int i = 0; i < parametros.length; i++) {
				String tipoAtributo="Variant";

				arrayListAtrib.add(new Atributo("$"+(i+1), tipoAtributo, parametros[i]));
			}

			break;

		default:
			tipo="";
			codigoFonte="";
			metodo=null;

			break;
		}

		//Montando o metodo

		if(codigoFonte.equals("")==false){
			metodo = new Metodo(nomeDoMetodo, tipo, codigoFonte, arrayListAtrib);
		}

		return metodo;
	}

	//Retorna um clone do método
	public Metodo clone(){
		//Clona a instância
		Metodo metodoClone= null;
		try{
			metodoClone = (Metodo)super.clone();
		}catch(Exception exc){
			exc.printStackTrace();
		}
		//Clona atributos e definições
		metodoClone.setNome(""+nome);
		metodoClone.setTipo(""+tipo);
		metodoClone.setCodigoFonte(""+codigoFonte);
		
		ArrayList<Atributo> arrayListAtributosClone = new ArrayList<Atributo>();
		
		for (int i = 0; i < arrayListAtributos.size(); i++) {
			arrayListAtributosClone.add(arrayListAtributos.get(i).clone());
		}
		
		metodoClone.setArrayListAtributos(arrayListAtributosClone);

		return metodoClone;
	}
	

}
