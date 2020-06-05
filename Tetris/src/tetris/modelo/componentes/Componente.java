/*
 *Representa componente de janelas do projeto
 *Classe de objetos serializables (graváveis em disco)e cloneables
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 24 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package tetris.modelo.componentes;

import java.io.BufferedWriter;
import java.io.Serializable;
import java.util.ArrayList;

import br.com.analisasoftware.tetris.modelo.Arquivo;
import br.com.analisasoftware.tetris.modelo.Projeto;
import br.com.analisasoftware.tetris.visao.janelas.JFramePrincipal;

public class Componente implements Cloneable, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	//Lista de atributos
	private ArrayList<Atributo> arrayListAtributos;
	//Lista de métodos
	private ArrayList<Metodo> arrayListMetodos;

	public Componente(String nome, ArrayList<Atributo> arrayListAtributos,
			ArrayList<Metodo> arrayListMetodos) {
		super();
		this.nome=nome;
		this.arrayListAtributos = arrayListAtributos;
		this.arrayListMetodos = arrayListMetodos;
	}

	//Muda um atributo do componente e retorna true, caso sucesso, e false, caso contrário
	public boolean mudarAtributo(Atributo atributo){
		//Variável de retorno
		boolean retorno=false;
		//Verifica se o atributo desejado é o Name
		if(atributo.getNome().equals("Name")){
			//Modifica o nome do componente
			setNome(atributo.getValor());
		}else{
			//Percorre a lista de atributos
			for (int i = 0; i < arrayListAtributos.size(); i++) {
				//Verifica se o atributo na posição i é o atributo desejado
				if(arrayListAtributos.get(i).getNome().equals(atributo.getNome())){
					//Remove o antigo atributo da lista
					arrayListAtributos.remove(i);
					break;
				}
			}
			//Verifica se o valor do novo atributo não está vazio
			if(atributo.getValor().equals("")==false){
				//Adiciona atributo à lista de atributos do componente
				arrayListAtributos.add(atributo);
			}
			retorno=true;
		}

		return retorno;
	}

	//Muda um metodo do componente e retorna true, caso sucesso, e false, caso contrário
	public boolean mudarMetodo(Metodo metodo){
		//Variável de retorno
		boolean retorno=false;
		//Percorre a lista de métodos do componente
		for (int i = 0; i < arrayListMetodos.size(); i++) {
			//Verifica se o método na posição i é o método desejado
			if(arrayListMetodos.get(i).getNome().equals(metodo.getNome())){
				//Remove o antigo método da lista
				arrayListMetodos.remove(i);
				break;
			}
		}
		//Adiciona o novo método
		arrayListMetodos.add(metodo);
		retorno=true;

		return retorno;
	}

	//Retorna um componente clone
	public Componente clone(){
		//Clona o componente e armazena em uma nova variável
		Componente componenteClone= null;
		try{
			componenteClone = (Componente)super.clone();
		}catch(Exception exc){
			exc.printStackTrace();
		}
		//Preenchendo os atributos e métodos do componente clonado
		String nomeClone=""+nome;
		ArrayList<Atributo> arrayListAtributosClone = new ArrayList<Atributo>();
		ArrayList<Metodo> arrayListMetodosClone = new ArrayList<Metodo>();

		for (int i = 0; i < arrayListAtributos.size(); i++) {
			arrayListAtributosClone.add(arrayListAtributos.get(i).clone());
		}

		for (int i = 0; i < arrayListMetodos.size(); i++) {
			arrayListMetodosClone.add(arrayListMetodos.get(i).clone());
		}

		componenteClone.setNome(nomeClone);
		componenteClone.setArrayListAtributos(arrayListAtributosClone);
		componenteClone.setArrayListMetodos(arrayListMetodosClone);

		return componenteClone;
	}
	//Gera código-fonte do componente
	public void gerarCodigoFonte(BufferedWriter bufferedWriter, Janela janela, JFramePrincipal jFramePrincipal){
		try {
			//Variável que aponta para a instância aberta do projeto
			Projeto projeto=jFramePrincipal.getProjeto(); 
			//Define o construtor do componente
			String construtorComponente="";
			switch (getTipo()) {
			case "Rotulo":
				construtorComponente="javax.swing.JLabel()";
				break;

			case "Botao":
				construtorComponente="JTetrisButton(\"\")";
				break;

			case "Campo de checagem":
				construtorComponente="JTetrisCheckBox()";
				break;

			case "Barra de menu":
				construtorComponente="javax.swing.JMenuBar()";
				break;

			case "Menu":
				construtorComponente="javax.swing.JMenu()";
				break;

			case "Menu Item":
				construtorComponente="javax.swing.JMenuItem()";
				break;

			case "Separador":
				construtorComponente="javax.swing.JSeparator()";
				break;

			case "Separator":
				construtorComponente="javax.swing.JSeparator()";
				break;

			case "Painel":
				construtorComponente="JTetrisPanel(null)";
				break;

			case "Barra de ferramentas":
				construtorComponente="JTetrisToolBar()";
				break;

			case "Abas":
				construtorComponente="javax.swing.JTabbedPane()";
				break;

			case "Tabela":
				construtorComponente="JTetrisTable()";
				break;

			case "Campo de escolha":
				construtorComponente="JTetrisCampoDeEscolha(\"\")";
				break;

			case "Caixa de combinacao":
				construtorComponente="JTetrisComboBox()";
				break;

			case "Lista":
				construtorComponente="JTetrisScrolledList()";
				break;

			case "TextArea":
				construtorComponente="JTetrisScrolledTextArea()";
				break;

			case "EditorPane":
				construtorComponente="JTetrisScrolledEditorPane()";
				break;

			case "Timer":
				construtorComponente="javax.swing.Timer";
				break;

			case "Procedure":

				break;

			case "Relatorio":
				construtorComponente="tetris.report.Report()";
				break;

			default:
				construtorComponente="JTetrisTextField()";
				if(getAtributo("Mascara")!=null){
					if(getAtributo("Mascara").getValor().equals("10")){
						construtorComponente="JTetrisDateField()";
					}else if(getAtributo("Mascara").getValor().equals("6")){
						construtorComponente="JTetrisPasswordField()";
					}

				}else if(getAtributo("Mask")!=null){
					if(getAtributo("Mask").getValor().equals("10")){
						construtorComponente="JTetrisDateField()";
					}else if(getAtributo("Mask").getValor().equals("6")){
						construtorComponente="JTetrisPasswordField()";
					}

				}

				break;
			}
			
			if(getTipo().equals("Timer")){
				String delay="1000";
				if(getAtributo("Delay")!=null){
					delay=getAtributo("Delay").getValor();
				}

				bufferedWriter.write("		"+getNome()+" = new "+construtorComponente+"("+delay+", new java.awt.event.ActionListener(){");
				bufferedWriter.newLine();

				bufferedWriter.write("			public void actionPerformed(java.awt.event.ActionEvent arg0){");
				bufferedWriter.newLine();

				ArrayList<Metodo> arrayListMetodos = getMetodosFuncao("OnTimer");
				if(arrayListMetodos.size() > 0){
					for (int i = 0; i < arrayListMetodos.size(); i++) {
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
					}
				}

				bufferedWriter.write("			}");
				bufferedWriter.newLine();

				bufferedWriter.write("		});");
				bufferedWriter.newLine();
			}else if(getTipo().equals("Procedure")){
				String type = "void";
				Atributo atributo = getAtributo("Type");
				if(atributo!=null) {
					type=atributo.getValor();
				}
				if(type!=null){
					if(type.equals("")) {
						type="void";
					}
				}else {
					type="void";
				}
				String parameters = "";
				atributo = getAtributo("Parameters");
				if(atributo!=null) {
					parameters=atributo.getValor();
				} 
				
				if(parameters!=null){
					if(parameters.equals("")) {
						parameters="";
					}
				}else {
					parameters="";
				}
				bufferedWriter.write("	public "+type+" "+getNome()+"("+parameters+"){");
				bufferedWriter.newLine();
				if(!type.equals("void")) {
					String retorno="";
					atributo = getAtributo("Return");
					if(atributo!=null) {
						retorno=atributo.getValor();
					}
					
					if(retorno!=null){
						if(retorno.equals("")) {
							retorno="";
						}
					}else {
						retorno="";
					}
					if(type.equals("String")) {
						retorno="\""+retorno+"\"";
					}else if(type.equals("char")) {
						retorno="'"+retorno+"'";
					}
					bufferedWriter.write("		"+type+" retorno = "+retorno+";");
					bufferedWriter.newLine();
				}

				ArrayList<Metodo> arrayListMetodos = getMetodosFuncao("OnExecute");
				if(arrayListMetodos.size() > 0){
					for (int i = 0; i < arrayListMetodos.size(); i++) {
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
					}
				}
				if(!type.equals("void")) {
					bufferedWriter.write("		return retorno;");
					bufferedWriter.newLine();
				}
				bufferedWriter.write("	}");
				bufferedWriter.newLine();

			}else{
				bufferedWriter.write("		"+getNome()+" = new "+construtorComponente+"");
				
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
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
					}

					bufferedWriter.write("			}");
					bufferedWriter.newLine();
					bufferedWriter.write("		}");


				}
				
				bufferedWriter.write(";");
				bufferedWriter.newLine();
			}
			//Gerando atributos
			int x=0, y=0, width=100, height=20;
			for (int i = 0; i < arrayListAtributos.size(); i++) {
				switch(arrayListAtributos.get(i).getNome()){
				case "Text":
					if(getTipo().equals("Caixa de combinacao")){
						bufferedWriter.write("		"+getNome()+".setSelectedItem(\""+arrayListAtributos.get(i).getValor()+"\");");
						bufferedWriter.newLine();
					}else if(getTipo().equals("Lista")){
						bufferedWriter.write("		"+getNome()+".setSelectedValue(\""+arrayListAtributos.get(i).getValor()+"\");");
						bufferedWriter.newLine();
					}else{
						bufferedWriter.write("		"+getNome()+".setText(\""+arrayListAtributos.get(i).getValor().replace("\n", "\\n\"+\"")+"\");");
						bufferedWriter.newLine();
					}

					break;

				case "Title":

					bufferedWriter.write("		"+getNome()+".setTitle(\""+arrayListAtributos.get(i).getValor()+"\");");
					bufferedWriter.newLine();

					break;

				case "ContentType":

					bufferedWriter.write("		"+getNome()+".setContentType(\""+arrayListAtributos.get(i).getValor()+"\");");
					bufferedWriter.newLine();

					break;

				case "Page":

					bufferedWriter.write("		try{");
					bufferedWriter.newLine();
					bufferedWriter.write("			"+getNome()+".setPage(\""+arrayListAtributos.get(i).getValor()+"\");");
					bufferedWriter.newLine();
					bufferedWriter.write("		}catch(Exception exc){");
					bufferedWriter.newLine();
					bufferedWriter.write("			javax.swing.JOptionPane.showMessageDialog(this, \"\"+exc);");
					bufferedWriter.newLine();
					bufferedWriter.write("		}");
					bufferedWriter.newLine();

					break;

				case "x":
					x=Integer.parseInt(arrayListAtributos.get(i).getValor());
					break;
				case "y":
					y=Integer.parseInt(arrayListAtributos.get(i).getValor());
					break;
				case "width":
					width=Integer.parseInt(arrayListAtributos.get(i).getValor());
					break;
				case "height":
					height=Integer.parseInt(arrayListAtributos.get(i).getValor());
					break;
				case "Border":

					int borda=Integer.parseInt(arrayListAtributos.get(i).getValor());
					if(borda==0){
						//Borda marcada
						bufferedWriter.write("		"+getNome()+".setBorder(javax.swing.BorderFactory.createEtchedBorder());");
						bufferedWriter.newLine();
					}else if(borda==1){
						//Borda afundada
						bufferedWriter.write("		"+getNome()+".setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED, null, null, null, null));");
						bufferedWriter.newLine();
					}else if(borda==2){
						//Borda Enraizada
						bufferedWriter.write("		"+getNome()+".setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, null));");
						bufferedWriter.newLine();
					}
					break;
				case "Background":
					int red=Integer.parseInt(arrayListAtributos.get(i).getValor().substring(0, 3));
					int green=Integer.parseInt(arrayListAtributos.get(i).getValor().substring(5, 8));
					int blue=Integer.parseInt(arrayListAtributos.get(i).getValor().substring(10, 13));

					bufferedWriter.write("		"+getNome()+".setBackground(new java.awt.Color("+red+", "+green+", "+blue+"));");
					bufferedWriter.newLine();

					break;

				case "Foreground":
					int redF=Integer.parseInt(arrayListAtributos.get(i).getValor().substring(0, 3));
					int greenF=Integer.parseInt(arrayListAtributos.get(i).getValor().substring(5, 8));
					int blueF=Integer.parseInt(arrayListAtributos.get(i).getValor().substring(10, 13));

					bufferedWriter.write("		"+getNome()+".setForeground(new java.awt.Color("+redF+", "+greenF+", "+blueF+"));");
					bufferedWriter.newLine();

					break;

				case "Font":
					String[] fonte = arrayListAtributos.get(i).getValor().split("/");

					bufferedWriter.write("		"+getNome()+".setFont(new java.awt.Font(\""+fonte[0]+"\", "+fonte[2]+", "+fonte[1]+"));");
					bufferedWriter.newLine();

					break;

				case "HorizontalAlignment":
					bufferedWriter.write("		"+getNome()+".setHorizontalAlignment("+arrayListAtributos.get(i).getValor()+");");
					bufferedWriter.newLine();

					break;

				case "VerticalAlignment":
					bufferedWriter.write("		"+getNome()+".setVerticalAlignment("+arrayListAtributos.get(i).getValor()+");");
					bufferedWriter.newLine();

					break;

				case "HorizontalTextPosition":
					bufferedWriter.write("		"+getNome()+".setHorizontalTextPosition("+arrayListAtributos.get(i).getValor()+");");
					bufferedWriter.newLine();

					break;

				case "VerticalTextPosition":
					bufferedWriter.write("		"+getNome()+".setVerticalTextPosition("+arrayListAtributos.get(i).getValor()+");");
					bufferedWriter.newLine();

					break;

				case "Orientacao":
					String orientacao="0";
					if(arrayListAtributos.get(i).getValor().equals("PAISAGEM")){
						orientacao="1";
					}
					bufferedWriter.write("		"+getNome()+".setOrientacao("+orientacao+");");
					bufferedWriter.newLine();

					break;

				case "Orientation":
					String orientation="0";
					if((arrayListAtributos.get(i).getValor().equals("PAISAGEM")) || (arrayListAtributos.get(i).getValor().equals("LANDSCAPE"))){
						orientation="1";
					}
					bufferedWriter.write("		"+getNome()+".setOrientation("+orientation+");");
					bufferedWriter.newLine();

					break;

				case "MarginLeft":
					bufferedWriter.write("		"+getNome()+".setMarginLeft("+arrayListAtributos.get(i).getValor()+");");
					bufferedWriter.newLine();

					break;

				case "MarginRight":
					bufferedWriter.write("		"+getNome()+".setMarginRight("+arrayListAtributos.get(i).getValor()+");");
					bufferedWriter.newLine();

					break;

				case "MarginTop":
					bufferedWriter.write("		"+getNome()+".setMarginTop("+arrayListAtributos.get(i).getValor()+");");
					bufferedWriter.newLine();

					break;

				case "MarginBottom":
					bufferedWriter.write("		"+getNome()+".setMarginBottom("+arrayListAtributos.get(i).getValor()+");");
					bufferedWriter.newLine();

					break;

				case "Icon":
					if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+projeto.getNome()+"/res/"+arrayListAtributos.get(i).getValor())){
						bufferedWriter.write("		"+getNome()+".setIcon(new javax.swing.ImageIcon(getClass().getResource(\"/tetris/"+projeto.getNome().toLowerCase()+"/res/"+arrayListAtributos.get(i).getValor()+"\")));");
						bufferedWriter.newLine();
					}

					break;

				case "Checked":
					bufferedWriter.write("		"+getNome()+".setSelected("+arrayListAtributos.get(i).getValor()+");");
					bufferedWriter.newLine();

					break;

				case "Floatable":
					bufferedWriter.write("		"+getNome()+".setFloatable("+arrayListAtributos.get(i).getValor()+");");
					bufferedWriter.newLine();

					break;
					
				case "Focusable":
					bufferedWriter.write("		"+getNome()+".setFocusable("+arrayListAtributos.get(i).getValor()+");");
					bufferedWriter.newLine();

					break;

				case "Enabled":
					bufferedWriter.write("		"+getNome()+".setEnabled("+arrayListAtributos.get(i).getValor()+");");
					bufferedWriter.newLine();

					break;

				case "Editable":
					bufferedWriter.write("		"+getNome()+".setEditable("+arrayListAtributos.get(i).getValor()+");");
					bufferedWriter.newLine();

					break;

				case "Mascara":
					if((arrayListAtributos.get(i).getValor().equals("10")==false) && (arrayListAtributos.get(i).getValor().equals("6")==false)){
						bufferedWriter.write("		"+getNome()+".setMascara("+arrayListAtributos.get(i).getValor()+");");
						bufferedWriter.newLine();
					}

					break;

				case "Mask":
					if((arrayListAtributos.get(i).getValor().equals("10")==false) && (arrayListAtributos.get(i).getValor().equals("6")==false)){
						bufferedWriter.write("		"+getNome()+".setMask("+arrayListAtributos.get(i).getValor()+");");
						bufferedWriter.newLine();
					}

					break;

				case "Titulos":

					String titulos = arrayListAtributos.get(i).getValor();

					String[] tits = titulos.split("/");
					bufferedWriter.write("		String[] titulos"+getNome()+" = new String["+tits.length+"];");
					bufferedWriter.newLine();
					for (int k = 0; k < tits.length; k++) {
						bufferedWriter.write("		titulos"+getNome()+"["+k+"] = \""+tits[k]+"\";");
						bufferedWriter.newLine();

					}
					bufferedWriter.write("		"+getNome()+".setTitulos(titulos"+getNome()+");");
					bufferedWriter.newLine();

					break;

				case "Titles":

					String titles = arrayListAtributos.get(i).getValor();

					String[] tit = titles.split("/");
					bufferedWriter.write("		String[] titles"+getNome()+" = new String["+tit.length+"];");
					bufferedWriter.newLine();
					for (int k = 0; k < tit.length; k++) {
						bufferedWriter.write("		titles"+getNome()+"["+k+"] = \""+tit[k]+"\";");
						bufferedWriter.newLine();

					}
					bufferedWriter.write("		"+getNome()+".setTitles(titles"+getNome()+");");
					bufferedWriter.newLine();

					break;

				case "Colunas":

					String colunas = arrayListAtributos.get(i).getValor();

					String[] cols = colunas.split("/");
					bufferedWriter.write("		String[] colunas"+getNome()+" = new String["+cols.length+"];");
					bufferedWriter.newLine();
					for (int k = 0; k < cols.length; k++) {
						bufferedWriter.write("		colunas"+getNome()+"["+k+"] = \""+cols[k]+"\";");
						bufferedWriter.newLine();

					}
					bufferedWriter.write("		"+getNome()+".setColunas(colunas"+getNome()+");");
					bufferedWriter.newLine();

					break;

				case "Columns":

					String columns = arrayListAtributos.get(i).getValor();

					String[] col = columns.split("/");
					bufferedWriter.write("		String[] columns"+getNome()+" = new String["+col.length+"];");
					bufferedWriter.newLine();
					for (int k = 0; k < col.length; k++) {
						bufferedWriter.write("		columns"+getNome()+"["+k+"] = \""+col[k]+"\";");
						bufferedWriter.newLine();

					}
					bufferedWriter.write("		"+getNome()+".setColumns(columns"+getNome()+");");
					bufferedWriter.newLine();

					break;

				case "Tabela":
					bufferedWriter.write("		"+getNome()+".setTabela(\""+arrayListAtributos.get(i).getValor()+"\");");
					bufferedWriter.newLine();

					break;
					
				case "Mnemonic":
					bufferedWriter.write("		"+getNome()+".setMnemonic('"+arrayListAtributos.get(i).getValor()+"');");
					bufferedWriter.newLine();

					break;

				case "RadioButtons":

					String radioButtons = arrayListAtributos.get(i).getValor();

					String[] radios = radioButtons.split("/");
					for (int k = 0; k < radios.length; k++) {
						bufferedWriter.write("		"+getNome()+".adicionarJRadioButton(new javax.swing.JRadioButton(\""+radios[k]+"\"));");
						bufferedWriter.newLine();

					}

					break;

				case "Undecorated":
					if(arrayListAtributos.get(i).getValor().equals("true")){
						bufferedWriter.write("		"+getNome()+".setContentAreaFilled(false);");
						bufferedWriter.newLine();
						bufferedWriter.write("		"+getNome()+".setBorderPainted(false);");
						bufferedWriter.newLine();
					}

					break;

				case "Itens":

					if(getTipo().equals("Caixa de combinacao")){
						String itensLista = arrayListAtributos.get(i).getValor();

						String[] itens = itensLista.split("/");
						for (int k = 0; k < itens.length; k++) {
							bufferedWriter.write("		"+getNome()+".addItem(\""+itens[k]+"\");");
							bufferedWriter.newLine();

						}
					}else if(getTipo().equals("Lista")){

						bufferedWriter.write("		"+getNome()+".setValues((\""+arrayListAtributos.get(i).getValor()+"\").split(\"/\"));");
						bufferedWriter.newLine();

					}

					break;

				case "Items":

					if(getTipo().equals("Caixa de combinacao")){
						String itensLista = arrayListAtributos.get(i).getValor();

						String[] itens = itensLista.split("/");
						for (int k = 0; k < itens.length; k++) {
							bufferedWriter.write("		"+getNome()+".addItem(\""+itens[k]+"\");");
							bufferedWriter.newLine();

						}
					}else if(getTipo().equals("Lista")){

						bufferedWriter.write("		"+getNome()+".setValues((\""+arrayListAtributos.get(i).getValor()+"\").split(\"/\"));");
						bufferedWriter.newLine();

					}

					break;

				case "Cursor":
					bufferedWriter.write("		"+getNome()+".setCursor(new java.awt.Cursor(java.awt.Cursor."+arrayListAtributos.get(i).getValor()+"));");
					bufferedWriter.newLine();

					break;
					
				case "ToolTipText":
					bufferedWriter.write("		"+getNome()+".setToolTipText(\""+arrayListAtributos.get(i).getValor()+"\");");
					bufferedWriter.newLine();

					break;

				case "Visible":
					bufferedWriter.write("		"+getNome()+".setVisible("+arrayListAtributos.get(i).getValor()+");");
					bufferedWriter.newLine();

					break;

				case "Active":
					if(arrayListAtributos.get(i).getValor().equals("true")){
						bufferedWriter.write("		"+getNome()+".start();");
						bufferedWriter.newLine();
					}
					break;

				case "MaxLength":
					if(construtorComponente.equals("JTetrisTextField()")){
						bufferedWriter.write("		"+getNome()+".setMaxLength("+arrayListAtributos.get(i).getValor()+");");
						bufferedWriter.newLine();
					}
					break;

				case "Valor":
					bufferedWriter.write("		"+getNome()+".setValor(\""+arrayListAtributos.get(i).getValor()+"\");");
					bufferedWriter.newLine();

					break;

				case "Detail":

					//bufferedWriter.write("		"+getNome()+".setDetail(\""+arrayListAtributos.get(i).getValor().replace("\"", "\\\"").replace("\n", "").replace("\r", "").replace("$\\\\\"", "\"").replace("$\\\"", "\"")+"\");");
					//bufferedWriter.newLine();

					break;

				case "Sumary":

					//bufferedWriter.write("		"+getNome()+".setSumary(\""+arrayListAtributos.get(i).getValor().replace("\"", "\\\"").replace("\n", "").replace("\r", "").replace("$\\\\\"", "\"").replace("$\\\"", "\"")+"\");");
					//bufferedWriter.newLine();

					break;

				case "Header":

					//bufferedWriter.write("		"+getNome()+".setHeader(\""+arrayListAtributos.get(i).getValor().replace("\"", "\\\"").replace("\n", "").replace("\r", "").replace("$\\\\\"", "\"").replace("$\\\"", "\"")+"\");");
					//bufferedWriter.newLine();

					break;

				case "Shortcut":

					String[] teclas = arrayListAtributos.get(i).getValor().split("\\+");
					String tecla="";
					for (int j = 0; j < teclas.length-1; j++) {
						if(tecla.equals("")==false){
							tecla=tecla+" | ";
						}
						tecla=tecla+"java.awt.event.InputEvent."+teclas[j].trim()+"_MASK";
					}
					if(tecla.equals("")){
						tecla="0";
					}
					bufferedWriter.write("		"+getNome()+".setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_"+teclas[teclas.length-1]+", "+tecla+"));");
					bufferedWriter.newLine();


					break;

				}

			}

			if((getTipo().equals("Timer")==false) && (getTipo().equals("Procedure")==false) && (getTipo().equals("Relatorio")==false)){
				bufferedWriter.write("		"+getNome()+".setBounds("+x+", "+y+", "+width+", "+height+");");
				bufferedWriter.newLine();
			}

			bufferedWriter.newLine();
			
			//Gerando métodos
			
			//Verificando o ActionListener
			if(getMetodosFuncao("OnClick").size() > 0){
				if(!((getTipo().equals("Barra de ferramentas")) || (getTipo().equals("Painel")) || (getTipo().equals("Rotulo"))
						|| (getTipo().equals("Campo de texto")) || (getTipo().equals("Tabela")) || (getTipo().equals("TextArea"))
						|| (getTipo().equals("EditorPane")))){

					bufferedWriter.write("		"+getNome()+".addActionListener( new java.awt.event.ActionListener(){");
					bufferedWriter.newLine();
					bufferedWriter.write("			public void actionPerformed(java.awt.event.ActionEvent arg0){");
					bufferedWriter.newLine();

					ArrayList<Metodo> arrayListMetodos = getMetodosFuncao("OnClick");
					for (int i = 0; i < arrayListMetodos.size(); i++) {
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
					}
					bufferedWriter.write("			}");
					bufferedWriter.newLine();
					bufferedWriter.write("		});");
					bufferedWriter.newLine();
				}
			}

			//Verificando o MouseListener
			if(((getMetodosFuncao("OnClick").size() > 0) && ((getTipo().equals("Barra de ferramentas")) || (getTipo().equals("Painel")) || (getTipo().equals("Rotulo"))
					|| (getTipo().equals("Campo de texto")) || (getTipo().equals("Tabela")) || (getTipo().equals("TextArea"))
					|| (getTipo().equals("EditorPane")))) || (getMetodosFuncao("OnMouseReleased").size() > 0) || (getMetodosFuncao("OnMousePressed").size() > 0)
					|| (getMetodosFuncao("OnMouseEntered").size() > 0) || (getMetodosFuncao("OnMouseExited").size() > 0)){

				if(getTipo().equals("Tabela")){
					bufferedWriter.write("		"+getNome()+".getjTable().addMouseListener( new java.awt.event.MouseAdapter(){");
					bufferedWriter.newLine();
				}else{
					bufferedWriter.write("		"+getNome()+".addMouseListener( new java.awt.event.MouseAdapter(){");
					bufferedWriter.newLine();
				}
				
				//Verificando mouseClicked
				ArrayList<Metodo> arrayListMetodos = getMetodosFuncao("OnClick");
				if(arrayListMetodos.size() > 0){
					bufferedWriter.write("			public void mouseClicked(java.awt.event.MouseEvent arg0){");
					bufferedWriter.newLine();

					for (int i = 0; i < arrayListMetodos.size(); i++) {
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
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
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
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
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
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
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
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
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
					}
					bufferedWriter.write("			}");
					bufferedWriter.newLine();
				}
					
				bufferedWriter.write("		});");
				bufferedWriter.newLine();

			}
			
			//Verificando o OnMouseMotionListener
			if((getMetodosFuncao("OnMouseMoved").size() > 0) || (getMetodosFuncao("OnMouseDragged").size() > 0)){

				if(getTipo().equals("Tabela")){
					bufferedWriter.write("		"+getNome()+".getjTable().addMouseMotionListener( new java.awt.event.MouseMotionAdapter(){");
					bufferedWriter.newLine();
				}else{
					bufferedWriter.write("		"+getNome()+".addMouseMotionListener( new java.awt.event.MouseMotionAdapter(){");
					bufferedWriter.newLine();
				}
				//Verificando mouseMoved
				ArrayList<Metodo> arrayListMetodos = getMetodosFuncao("OnMouseMoved");
				if(arrayListMetodos.size() > 0){
					bufferedWriter.write("			public void mouseMoved(java.awt.event.MouseEvent arg0){");
					bufferedWriter.newLine();

					for (int i = 0; i < arrayListMetodos.size(); i++) {
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
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
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
					}
					bufferedWriter.write("			}");
					bufferedWriter.newLine();
				}

				bufferedWriter.write("		});");
				bufferedWriter.newLine();
			}
			//
			
			//Verificando o OnMouseWheelListener
			if(getMetodosFuncao("OnMouseWheelMoved").size() > 0){

				if(getTipo().equals("Tabela")){
					bufferedWriter.write("		"+getNome()+".getjTable().addMouseWheelListener( new java.awt.event.MouseWheelListener(){");
					bufferedWriter.newLine();
				}else{
					bufferedWriter.write("		"+getNome()+".addMouseWheelListener( new java.awt.event.MouseWheelListener(){");
					bufferedWriter.newLine();
				}
				//Verificando mouseWheelMoved
				ArrayList<Metodo> arrayListMetodos = getMetodosFuncao("OnMouseWheelMoved");
				if(arrayListMetodos.size() > 0){
					bufferedWriter.write("			public void mouseWheelMoved(java.awt.event.MouseWheelEvent arg0){");
					bufferedWriter.newLine();

					for (int i = 0; i < arrayListMetodos.size(); i++) {
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
					}
					bufferedWriter.write("			}");
					bufferedWriter.newLine();
				}

				bufferedWriter.write("		});");
				bufferedWriter.newLine();
			}
			//Verificando o OnResize, OnMove, OnShow e OnHide
			if((getMetodosFuncao("OnResize").size() > 0) || (getMetodosFuncao("OnMove").size() > 0)
					|| (getMetodosFuncao("OnShow").size() > 0) || (getMetodosFuncao("OnHide").size() > 0)){

				
				bufferedWriter.write("		"+getNome()+".addComponentListener( new java.awt.event.ComponentAdapter(){");
				bufferedWriter.newLine();
				
				//Verificando componentResized
				ArrayList<Metodo> arrayListMetodos = getMetodosFuncao("OnResize");
				if(arrayListMetodos.size() > 0){
					bufferedWriter.write("			public void componentResized(java.awt.event.ComponentEvent arg0){");
					bufferedWriter.newLine();

					for (int i = 0; i < arrayListMetodos.size(); i++) {
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
					}
					bufferedWriter.write("			}");
					bufferedWriter.newLine();
				}

				//Verificando componentMoved
				arrayListMetodos = getMetodosFuncao("OnMove");
				if(arrayListMetodos.size() > 0){
					bufferedWriter.write("			public void componentMoved(java.awt.event.ComponentEvent arg0){");
					bufferedWriter.newLine();

					for (int i = 0; i < arrayListMetodos.size(); i++) {
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
					}
					bufferedWriter.write("			}");
					bufferedWriter.newLine();
				}

				//Verificando componentShown
				arrayListMetodos = getMetodosFuncao("OnShow");
				if(arrayListMetodos.size() > 0){
					bufferedWriter.write("			public void componentShown(java.awt.event.ComponentEvent arg0){");
					bufferedWriter.newLine();

					for (int i = 0; i < arrayListMetodos.size(); i++) {
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
					}
					bufferedWriter.write("			}");
					bufferedWriter.newLine();
				}
				
				//Verificando componentHidden
				arrayListMetodos = getMetodosFuncao("OnHide");
				if(arrayListMetodos.size() > 0){
					bufferedWriter.write("			public void componentHidden(java.awt.event.ComponentEvent arg0){");
					bufferedWriter.newLine();

					for (int i = 0; i < arrayListMetodos.size(); i++) {
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
					}
					bufferedWriter.write("			}");
					bufferedWriter.newLine();
				}

				bufferedWriter.write("		});");
				bufferedWriter.newLine();
			}

			//Verificando o OnKeyReleased
			if((getMetodosFuncao("OnKeyReleased").size() > 0) || (getMetodosFuncao("OnKeyPressed").size() > 0)
					|| (getMetodosFuncao("OnKeyTyped").size() > 0)){

				if(getTipo().equals("Tabela")){
					bufferedWriter.write("		"+getNome()+".getjTable().addKeyListener( new java.awt.event.KeyAdapter(){");
					bufferedWriter.newLine();
				}else{
					bufferedWriter.write("		"+getNome()+".addKeyListener( new java.awt.event.KeyAdapter(){");
					bufferedWriter.newLine();
				}
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
						
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
						
						//Verificando tecla
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
						
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
						
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
						
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
						
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

			//Verificando o OnChange
			if(getMetodosFuncao("OnChange").size() > 0){
				if((getTipo().equals("Campo de texto")) || (getTipo().equals("TextArea")) || (getTipo().equals("EditorPane"))){
					bufferedWriter.write("		"+getNome()+".getDocument().addDocumentListener( new javax.swing.event.DocumentListener(){");
					bufferedWriter.newLine();
					bufferedWriter.write("			public void changedUpdate(javax.swing.event.DocumentEvent arg0){");
					bufferedWriter.newLine();

					ArrayList<Metodo> arrayListMetodos = getMetodosFuncao("OnChange");
					for (int i = 0; i < arrayListMetodos.size(); i++) {
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
					}
					bufferedWriter.write("			}");
					bufferedWriter.newLine();
					bufferedWriter.write("			public void removeUpdate(javax.swing.event.DocumentEvent arg0){");
					bufferedWriter.newLine();
					for (int i = 0; i < arrayListMetodos.size(); i++) {
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
					}
					bufferedWriter.write("			}");
					bufferedWriter.newLine();
					bufferedWriter.write("			public void insertUpdate(javax.swing.event.DocumentEvent arg0){");
					bufferedWriter.newLine();
					for (int i = 0; i < arrayListMetodos.size(); i++) {
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
					}
					bufferedWriter.write("			}");
					bufferedWriter.newLine();
					bufferedWriter.write("		});");
					bufferedWriter.newLine();
				}else if((getTipo().equals("Campo de checagem")) || (getTipo().equals("Caixa de combinacao"))){
					bufferedWriter.write("		"+getNome()+".addActionListener( new java.awt.event.ActionListener(){");
					bufferedWriter.newLine();
					bufferedWriter.write("			public void actionPerformed(java.awt.event.ActionEvent arg0){");
					bufferedWriter.newLine();

					ArrayList<Metodo> arrayListMetodos = getMetodosFuncao("OnChange");
					for (int i = 0; i < arrayListMetodos.size(); i++) {
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
					}
					bufferedWriter.write("			}");
					bufferedWriter.newLine();

					bufferedWriter.write("		});");
					bufferedWriter.newLine();
				}else if(getTipo().equals("Lista")){

					bufferedWriter.write("		"+getNome()+".addListSelectionListener( new javax.swing.event.ListSelectionListener() {");
					bufferedWriter.newLine();
					bufferedWriter.write("			@Override");
					bufferedWriter.newLine();
					bufferedWriter.write("			public void valueChanged(javax.swing.event.ListSelectionEvent arg0) {");
					bufferedWriter.newLine();

					ArrayList<Metodo> arrayListMetodos = getMetodosFuncao("OnChange");
					for (int i = 0; i < arrayListMetodos.size(); i++) {
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
					}
					bufferedWriter.write("			}");
					bufferedWriter.newLine();

					bufferedWriter.write("		});");
					bufferedWriter.newLine();
				}
			}

			//Verificando o OnFocusGained e OnFocusLost
			if((getMetodosFuncao("OnFocusGained").size() > 0) || (getMetodosFuncao("OnFocusLost").size() > 0)){

				if(getTipo().equals("Tabela")){
					bufferedWriter.write("		"+getNome()+".getjTable().addFocusListener( new java.awt.event.FocusAdapter(){");
					bufferedWriter.newLine();
				}else{
					bufferedWriter.write("		"+getNome()+".addFocusListener( new java.awt.event.FocusAdapter(){");
					bufferedWriter.newLine();
				}

				ArrayList<Metodo> arrayListMetodos = getMetodosFuncao("OnFocusGained");

				if(arrayListMetodos.size() > 0){
					bufferedWriter.write("			public void focusGained(java.awt.event.FocusEvent arg0){");
					bufferedWriter.newLine();
					for (int i = 0; i < arrayListMetodos.size(); i++) {
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
					}
					bufferedWriter.write("			}");
					bufferedWriter.newLine();
				}

				arrayListMetodos = getMetodosFuncao("OnFocusLost");

				if(arrayListMetodos.size() > 0){
					bufferedWriter.write("			public void focusLost(java.awt.event.FocusEvent arg0){");
					bufferedWriter.newLine();
					for (int i = 0; i < arrayListMetodos.size(); i++) {
						Metodo.gerarCodigoFonte(bufferedWriter, arrayListMetodos.get(i), janela, this, jFramePrincipal);
					}
					bufferedWriter.write("			}");
					bufferedWriter.newLine();
				}


				bufferedWriter.write("		});");
				bufferedWriter.newLine();
			}
		} catch (Exception exc) {
			// TODO: handle exception
			exc.printStackTrace();
		}
	}

	//Getters e Setters
	public ArrayList<Atributo> getArrayListAtributos() {
		return arrayListAtributos;
	}

	public void setArrayListAtributos(ArrayList<Atributo> arrayListAtributos) {
		this.arrayListAtributos = arrayListAtributos;
	}

	public ArrayList<Metodo> getArrayListMetodos() {
		return arrayListMetodos;
	}

	public void setArrayListMetodos(ArrayList<Metodo> arrayListMetodos) {
		this.arrayListMetodos = arrayListMetodos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo(){
		String tipo="";

		for (int i = 0; i < arrayListAtributos.size(); i++) {
			if(arrayListAtributos.get(i).getNome().equals("Window")){
				tipo=arrayListAtributos.get(i).getValor();
				break;
			}
			if(arrayListAtributos.get(i).getNome().equals("Tipo")){
				tipo=arrayListAtributos.get(i).getValor();
				break;
			}
		}

		return tipo;
	}

	public Atributo getAtributo(String atributo){
		Atributo atrib=null;
		if(atributo.equals("Name")){
			atrib=new Atributo("Name", "String", nome);
		}else{
			for (int i = 0; i < arrayListAtributos.size(); i++) {
				if(arrayListAtributos.get(i).getNome().equals(atributo)){
					atrib=arrayListAtributos.get(i);
					break;
				}
			}
		}

		return atrib;
	}

	public Metodo getMetodo(String metodo){
		Metodo metod=null;
		for (int i = 0; i < arrayListMetodos.size(); i++) {
			if(arrayListMetodos.get(i).getNome().equals(metodo)){
				metod=arrayListMetodos.get(i);
				break;
			}
		}

		return metod;
	}

	public ArrayList<Metodo> getMetodosFuncao(String funcao){
		ArrayList<Metodo> retorno = new ArrayList<Metodo>();
		for (int i = 0; i < arrayListMetodos.size(); i++) {
			if(arrayListMetodos.get(i).getAtributo("Evento").getValor().equals(funcao)){
				retorno.add(arrayListMetodos.get(i));
			}
		}

		return retorno;
	}

}
