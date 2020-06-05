package componentes.modelo.bancodedados;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import componentes.visao.JDialogRegistro;


public class BancoDeDados implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private ArrayList<Tabela> arrayListTabelas;
	private boolean alterado;

	private String usuario;
	private String senha;
	private String servidor;
	private String bancoDeDados;
	
	private String connectionString;
	private String driver;

	public BancoDeDados(String nome, ArrayList<Tabela> arrayListTabelas) {
		super();
		this.nome = nome;
		this.arrayListTabelas = arrayListTabelas;
		alterado=false;

		usuario="";
		senha="";
		servidor="";
		bancoDeDados="";
		connectionString="jdbc:mysql://$server/$database";
		driver="com.mysql.jdbc.Driver";
	}

	public boolean verificaCampo(Connection conexao, String tabela, String campo, String valor, String codigo){
		boolean retorno=false;
		try {
			Statement st = conexao.createStatement();
			ResultSet tb = st.executeQuery("select "+campo+" from "+tabela+" where "+campo+"='"+valor+"' and codigo!='"+codigo+"'");
			tb.first();
			if(tb.getRow() > 0){
				retorno = true;
			}
			tb.close();
			st.close();
		} catch (Exception e) {
			// TODO: handle exception
			//JDialogMensagem.exibirMensagem("Erro", "N�o foi poss�vel verificar campo!\n"+e);
			JOptionPane.showMessageDialog(null, e);
		}
		return retorno;
	}

	//Verifica inconsistencias no banco de dados
	public void verificarBancoDeDados(Connection conexao, String tab){
		try{
			Tabela tabela = null;
			for (int i = 0; i < arrayListTabelas.size(); i++) {
				if(arrayListTabelas.get(i).getNome().equals(tab)){
					tabela = arrayListTabelas.get(i);
					break;
				}
			}

			if(tabela == null){
				JOptionPane.showMessageDialog(null, "Não há tabela com este nome no banco de dados!\nTabela: "+tab);
			}else{
				//Criando banco
				Statement st;
				for (int i = 0; i < tabela.getArrayListColunas().size(); i++) {
					try{
						st = conexao.createStatement();
						st.executeQuery("select "+tabela.getArrayListColunas().get(i).getNome()+" from "+tab+" limit 1");
						st.close();
					}catch(Exception exc){
						try{
							st = conexao.createStatement();
							st.executeUpdate("alter table "+tab+" add ("+tabela.getArrayListColunas().get(i).getNome()+" "+tabela.getArrayListColunas().get(i).getTipo()+")");
							st.close();
						}catch(Exception exc1){
							//exc1.printStackTrace();
							String atributos="";
							for (int j = 0; j < tabela.getArrayListColunas().size(); j++) {
								if(!atributos.equals("")){
									atributos=atributos+", ";
								}
								atributos=atributos+tabela.getArrayListColunas().get(j).getNome()+" "+tabela.getArrayListColunas().get(j).getTipo();
							}
							st = conexao.createStatement();
							st.executeUpdate("create table "+tab+" ("+atributos+");");
							st.close();
							break;
						}
					}
				}


			}

		}catch(Exception exc){
			JOptionPane.showMessageDialog(null, "Erro ao verificar banco de dados:\n"+exc);
		}
	}
	
	//Cria e conecta-se ao banco de dados
	public Connection getConnection(){
		return getConexao();
	}
	
	//Executa comando select
	public ResultSet select(Connection conexao, String tabela, String atributos, String condicao){
		return comandoSelect(conexao, tabela, atributos, condicao);
	}
	
	//Executa comando insert
	public boolean insert(Connection conexao, String tabela, String atributos, String valores){
		return comandoInsert(conexao, tabela, atributos, valores);
	}
	
	//Executa comando update
	public boolean update(Connection conexao, String tabela, String valores, String condicao){
		return comandoUpdate(conexao, tabela, valores, condicao);
	}
	
	//Executa comando delete
	public boolean delete(Connection conexao, String tabela, String condicao){
		return comandoDelete(conexao, tabela, condicao);
	}

	//Cria e conecta-se ao banco de dados
	public Connection getConexao(){
		Connection conexao;
		String[] configuracoes = new String []{"localhost", getNome(), "", "root"};
		try {
			if(bancoDeDados.equals("")){
				BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/tetris/"+getNome().toLowerCase()+".dabj"));
				String linha="";
				int pos=0;
				while ((linha=bufferedReader.readLine())!=null) {
					configuracoes[pos]=Criptografia.Descriptografar(linha);
					pos++;

				}
				bufferedReader.close();
				
				servidor = configuracoes[0];
				bancoDeDados = configuracoes[1].replace("\\", "\\\\");
				senha = configuracoes[2];
				usuario = configuracoes[3];
			}else{
				configuracoes = new String []{servidor, bancoDeDados, senha, usuario};
			}
		} catch (Exception e) {
			// TODO: handle exception
			new JDialogRegistro(this);
		}

		try{
			//Tenta conectar-se ao banco de dados
			String senha="";
			if(configuracoes[2]!=null){
				if(configuracoes[2].equals("")==false){
					senha=""+configuracoes[2];
				}
			}
			conexao = DriverManager.getConnection(getConnectionString().replace("$server", configuracoes[0]).replace("$database", configuracoes[1]), configuracoes[3], senha);
			return conexao;
		}catch(Exception exc){

			//Se não conseguir...
			try{
				String senha="";
				if(configuracoes[2]!=null){
					if(configuracoes[2].equals("")==false){
						senha=""+configuracoes[2];
					}
				}
				//Tenta conectar-se ao servidor sem abrir o banco de dados
				conexao = DriverManager.getConnection(getConnectionString().replace("$server", configuracoes[0]).replace("$database", ""), configuracoes[3], senha);

				//Cria o banco de dados
				Statement statementCriarBanco = conexao.createStatement();
				String comando="create database "+getNome()+";";
				statementCriarBanco.executeUpdate(comando);

				statementCriarBanco.close();
				conexao.close();

				//Tenta conectar-se ao banco de dados
				conexao = DriverManager.getConnection(getConnectionString().replace("$server", configuracoes[0]).replace("$database", getNome()), configuracoes[3], senha);

				//Cria todas as tabelas
				for (int i = 0; i < arrayListTabelas.size(); i++) {
					statementCriarBanco = conexao.createStatement();
					comando="create table "+arrayListTabelas.get(i).getNome()+" (";
					for (int j = 0; j < arrayListTabelas.get(i).getArrayListColunas().size(); j++) {
						if(!comando.endsWith("(")){
							comando=comando+",  ";
						}
						comando=comando+arrayListTabelas.get(i).getArrayListColunas().get(j).getNome()+" "+arrayListTabelas.get(i).getArrayListColunas().get(j).getTipo();
					}
					comando=comando+");";

					//System.out.println(comando);
					statementCriarBanco.executeUpdate(comando);
					statementCriarBanco.close();
				}

				conexao.close();

				//Tenta conectar-se ao banco de dados
				conexao = DriverManager.getConnection(getConnectionString().replace("$server", configuracoes[0]).replace("$database", configuracoes[1]), configuracoes[3], senha);
				return conexao;
			}catch(Exception exc2){
				//Se não conseguir exibe uma mensagem
				JOptionPane.showMessageDialog(null, "Não foi possível conectar ao banco de dados!\n" +
						""+exc2+"\n"+
						"Caso não deseje mudar as configurações de conexão, selecione a opção Sair da janela seguinte!");

				//JOptionPane.showMessageDialog(null, exc2);
				new JDialogRegistro(this);
				//exc2.printStackTrace();

				System.exit(0);
				return null;
			}


		}
	}

	public String getMD5(String senha){
		java.security.MessageDigest md = null;
		String retorno="";
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			// TODO: handle exception

		}

		java.math.BigInteger hash = new java.math.BigInteger(1, md.digest(senha.getBytes()));
		retorno=hash.toString(16);
		return retorno;
	}

	//Realiza consultas no banco de dados
	public ResultSet comandoSelect(Connection conexao, String tabela, String atributos, String condicao){
		ResultSet resultSet;

		try{
			//Tenta realizar a consulta
			Statement statement = conexao.createStatement();
			//System.out.println("select "+atributos+" from "+tabela+" "+condicao);
			resultSet = statement.executeQuery("select "+atributos+" from "+tabela+" "+condicao);
			//statement.close();
			return resultSet;
		}catch(Exception exc){
			//Se não conseguir verifica inconsistencias na tabela e corrige se achar
			verificarBancoDeDados(conexao, tabela);
			try{
				//Tenta consultar novamente
				Statement statement = conexao.createStatement();
				resultSet = statement.executeQuery("select "+atributos+" from "+tabela+" "+condicao);
				//statement.close();
				return resultSet;
			}catch(Exception exc1){
				//Se não conseguir exibe uma mensagem
				JOptionPane.showMessageDialog(null, "Erro ao selecionar dados:\n"+exc1);
				return null;
			}
		}
	}

	public boolean gerarHashTabela(String tabela, String codigo, Connection conexao){
		boolean retorno=false;

		try{
			Tabela tab=null;
			for (int j = 0; j < arrayListTabelas.size(); j++) {
				if(arrayListTabelas.get(j).getNome().equals(tabela)){
					tab = arrayListTabelas.get(j);
					break;
				}
			}
			String camposConcatenados="";

			if(tab!=null){
				ResultSet resultSet=comandoSelect(conexao, tab.getNome(), "*", "where codigo='"+codigo+"'");
				resultSet.first();

				if(resultSet.getRow() > 0){
					camposConcatenados=resultSet.getString("codigo");
					for(int i=0; i<tab.getArrayListColunas().size();i++){
						camposConcatenados+=resultSet.getString(tab.getArrayListColunas().get(i).getNome());
					}

					//Caso n�o ache campo Blackpearl, cria
					try{
						Statement statementVerificaCampoHash = conexao.createStatement();
						statementVerificaCampoHash.executeQuery("select blackpearl from "+tab.getNome()+" where codigo="+codigo);
						statementVerificaCampoHash.close();
					}catch(Exception exc){
						try{
							Statement statementVerificaCampoHash = conexao.createStatement();
							statementVerificaCampoHash.executeUpdate("alter table "+tab.getNome()+" add (blackpearl varchar(255))");
							statementVerificaCampoHash.close();
						}catch(Exception exc2){

						}
					}

					Statement statement = conexao.createStatement();
					statement.executeUpdate("update "+tab.getNome()+" set blackpearl='"+getMD5(camposConcatenados)+"' where codigo='"+codigo+"'");

					statement.close();
				}

				resultSet.close();
			}

		}catch(Exception exc){
			JOptionPane.showMessageDialog(null, "Erro ao gerar Hash de tabela:\n"+exc);
		}

		return retorno;
	}

	//Insere dados na tabela
	public boolean comandoInsert(Connection conexao, String tabela, String atributos, String valores){
		try{
			//Tenta inserir dados na tabela
			Statement statement = conexao.createStatement();
			statement.executeUpdate("insert into "+tabela+" ("+atributos+") values ("+valores+")");
			statement.close();

			/*ResultSet resultSet=comandoSelect(conexao, tabela, "codigo", " order by codigo desc limit 1");
			resultSet.first();
			if(resultSet.getRow() > 0){
				gerarHashTabela(tabela, resultSet.getString("codigo"), conexao);
			}

			resultSet.close();*/
			return true;
		}catch(Exception exc){
			//Se não conseguir verifica inconsistencias na tabela e corrige se achar
			verificarBancoDeDados(conexao, tabela);
			try{
				//Tenta de novo
				Statement statement = conexao.createStatement();
				statement.executeUpdate("insert into "+tabela+" ("+atributos+") values ("+valores+")");
				statement.close();

				/*ResultSet resultSet=comandoSelect(conexao, tabela, "codigo", " order by codigo desc limit 1");
				resultSet.first();
				if(resultSet.getRow() > 0){
					gerarHashTabela(tabela, resultSet.getString("codigo"), conexao);
				}

				resultSet.close();*/

				return true;
			}catch(Exception exc1){
				//Se não conseguir exibe uma mensagem
				JOptionPane.showMessageDialog(null, "Erro ao inserir dados:\n"+exc1);
				return false;
			}
		}
	}

	//Atualiza linhas numa tabela
	public boolean comandoUpdate(Connection conexao, String tabela, String valores, String condicao){
		try{
			//Tenta atualizar registros
			Statement statement = conexao.createStatement();

			statement.executeUpdate("update "+tabela+" set "+valores+" "+condicao);
			statement.close();

			/*ResultSet resultSet=comandoSelect(conexao, tabela, "codigo", " "+condicao);
			resultSet.first();
			if(resultSet.getRow() > 0){
				do{
					gerarHashTabela(tabela, resultSet.getString("codigo"), conexao);
				}while(resultSet.next());
			}

			resultSet.close();*/

			return true;
		}catch(Exception exc){
			//Se não conseguir verifica inconsistencias na tabela e corrige se achar
			verificarBancoDeDados(conexao, tabela);
			try{
				//Tenta novamente
				Statement statement = conexao.createStatement();
				statement.executeUpdate("update "+tabela+" set "+valores+" "+condicao);
				statement.close();

				/*ResultSet resultSet=comandoSelect(conexao, tabela, "codigo", " "+condicao);
				resultSet.first();
				if(resultSet.getRow() > 0){
					do{
						gerarHashTabela(tabela, resultSet.getString("codigo"), conexao);
					}while(resultSet.next());
				}

				resultSet.close();*/

				return true;
			}catch(Exception exc1){
				//Se não conseguir exibe uma mensagem
				JOptionPane.showMessageDialog(null, "Erro ao atualizar dados:\n"+exc1);
				return false;
			}
		}
	}

	//Exclui linhas numa tabela
	public boolean comandoDelete(Connection conexao, String tabela, String condicao){
		try{
			//Tenta excluir
			Statement statement = conexao.createStatement();
			statement.executeUpdate("delete from "+tabela+" "+condicao);
			statement.close();
			return true;
		}catch(Exception exc){
			//Se não conseguir verifica inconsistencias na tabela e corrige se achar
			verificarBancoDeDados(conexao, tabela);
			try{
				//Tenta de novo
				Statement statement = conexao.createStatement();
				statement.executeUpdate("delete from "+tabela+" "+condicao);
				statement.close();
				return true;
			}catch(Exception exc1){
				//Se não conseguir exibe uma mensagem
				JOptionPane.showMessageDialog(null, "Erro ao excluir dados:\n"+exc1);
				return false;
			}
		}
	}

	//Localiza registro em um resultSet
	public boolean localizarRegistro(ResultSet resultSet, String field, String palavra){
		try{
			//resultSet.first();
			if(resultSet.getRow() > 0){
				int linha=0;
				int fim=0;
				linha = resultSet.getRow();

				resultSet.last();
				fim = resultSet.getRow();
				resultSet.first();
				String palav="";
				if(resultSet.getString(field).length() >= palavra.length()){
					palav = resultSet.getString(field).substring(0, palavra.length());
					//JOptionPane.showMessageDialog(null, palav+"|");
				}
				while((resultSet.getRow() < fim) && (palav.equals(palavra) == false) ){					
					resultSet.next();
					if(resultSet.getString(field).length() >= palavra.length()){
						palav = resultSet.getString(field).substring(0, palavra.length());
					}else{
						palav="";
					}
				}

				if(resultSet.getString(field).length() >= palavra.length()){
					if(resultSet.getString(field).substring(0, palavra.length()).equals(palavra) == false){
						resultSet.absolute(linha);
					}else{
						return true;
					}
				}else{
					resultSet.absolute(linha);
				}
			}
		}catch(Exception exc){
			JOptionPane.showMessageDialog(null, "Erro ao localizar:\n"+exc);
		}

		return false;

	}

	public boolean adicionarTabela(Tabela tabela){
		boolean retorno=true;

		for (int i = 0; i < arrayListTabelas.size(); i++) {
			if(arrayListTabelas.get(i).getNome().equals(tabela.getNome())){
				retorno=false;
			}
		}
		if(retorno){
			tabela.adicionarColuna(new Coluna("id", "int not null primary key auto_increment"));
			arrayListTabelas.add(tabela);
		}

		return retorno;
	}

	public boolean removerTabela(String nome){
		boolean retorno=false;

		for (int i = 0; i < arrayListTabelas.size(); i++) {
			if(arrayListTabelas.get(i).getNome().equals(nome)){
				arrayListTabelas.remove(i);
				retorno=true;
				break;
			}
		}

		return retorno;
	}

	public static String antiInject(String s){
		String r = "";
		for (int i = 0; i < s.length(); i++) {
			if((Character.toString(s.charAt(i)).equals("'")==false) 
					&& (Character.toString(s.charAt(i)).equals("\\")==false)
					&& (Character.toString(s.charAt(i)).equals("\\'")==false)
					&& (Character.toString(s.charAt(i)).equals("#")==false)
					&& (Character.toString(s.charAt(i)).equals("'")==false)
					&& (Character.toString(s.charAt(i)).equals("`")==false)
					&& (Character.toString(s.charAt(i)).equals("%")==false)
					&& (Character.toString(s.charAt(i)).equals("\"")==false)){
				r=r+Character.toString(s.charAt(i));
			}
		}
		return r;
	}

	public boolean espelharBancoDeDados(String bancoDeDados, String servidor, String senha, String usuario){
		boolean retorno=false;
		try {
			String passwd = "";
			if(senha.equals("")==false){
				passwd = ""+senha;
			}
			Connection conexao = DriverManager.getConnection(getConnectionString().replace("$server", servidor).replace("$database", bancoDeDados), usuario, passwd);

			Statement statementTabelas = conexao.createStatement();
			ResultSet resultSetTabelas = statementTabelas.executeQuery("show tables");
			resultSetTabelas.first();

			if(resultSetTabelas.getRow() > 0){
				ArrayList<Tabela> arrayListTabelasEspelhadas = new ArrayList<Tabela>();
				do{
					ArrayList<Coluna> arrayListColunas = new ArrayList<Coluna>();

					Statement statementColunas = conexao.createStatement();
					ResultSet resultSetColunas = statementColunas.executeQuery("desc "+resultSetTabelas.getString("Tables_in_"+bancoDeDados));
					resultSetColunas.first();

					if(resultSetColunas.getRow() > 0){
						do{
							
							String nulo="", autoIncrement="", primaryKey="";
							if(resultSetColunas.getString("Null").equals("NO")){
								nulo=" not null";
							}
							if(resultSetColunas.getString("Extra").equals("auto_increment")){
								autoIncrement=" auto_increment";
							}
							if(resultSetColunas.getString("Key").equals("PRI")){
								primaryKey=" primary key";
							}
							Coluna coluna = new Coluna(resultSetColunas.getString("Field"), resultSetColunas.getString("Type")+nulo+primaryKey+autoIncrement);

							arrayListColunas.add(coluna);
							
						}while(resultSetColunas.next());
					}

					resultSetColunas.close();
					statementColunas.close();

					Tabela tabela = new Tabela(resultSetTabelas.getString("Tables_in_"+bancoDeDados), arrayListColunas);

					arrayListTabelasEspelhadas.add(tabela);
				}while(resultSetTabelas.next());
				setArrayListTabelas(arrayListTabelasEspelhadas);
			}else{
				JOptionPane.showMessageDialog(null, "Não há tabelas para serem espelhadas!", "Informar", JOptionPane.INFORMATION_MESSAGE);
			}

			resultSetTabelas.close();
			statementTabelas.close();

			conexao.close();
			retorno =true;
		} catch (Exception exc) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Erro ao espelhar banco de dados:\n"+exc, "Erro", JOptionPane.ERROR_MESSAGE);
		}

		return retorno;
	}

	//Getterse Setters

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Tabela getTabela(String nome){
		Tabela tab=null;
		for (int i = 0; i < arrayListTabelas.size(); i++) {
			if(arrayListTabelas.get(i).getNome().equals(nome)){
				tab=arrayListTabelas.get(i);
				break;
			}
		}

		return tab;
	}

	public ArrayList<Tabela> getArrayListTabelas() {
		return arrayListTabelas;
	}

	public void setArrayListTabelas(ArrayList<Tabela> arrayListTabelas) {
		this.arrayListTabelas = arrayListTabelas;
	}

	public boolean isAlterado() {
		return alterado;
	}

	public void setAlterado(boolean alterado) {
		this.alterado = alterado;
	}

	public String getConnectionString() {
		return connectionString;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	
}
