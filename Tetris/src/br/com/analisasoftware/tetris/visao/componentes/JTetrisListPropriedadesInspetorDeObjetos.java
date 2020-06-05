/*
 *JList das Propriedades no Inspetor de Objetos do componente selecionado do TetrisIDE
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 25 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.componentes;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import br.com.analisasoftware.tetris.modelo.Idioma;
import br.com.analisasoftware.tetris.visao.janelas.JDialogEditarPropriedade;
import br.com.analisasoftware.tetris.visao.janelas.JDialogMensagem;
import br.com.analisasoftware.tetris.visao.janelas.JFramePrincipal;
import componentes.visao.JTetrisList;
import componentes.visao.JTetrisTextField;
import tetris.modelo.componentes.Atributo;
import tetris.modelo.componentes.Componente;
import tetris.modelo.componentes.RelacionamentoComponente;

@SuppressWarnings("serial")
public class JTetrisListPropriedadesInspetorDeObjetos extends JTetrisList implements MouseListener, KeyListener{
	//Vetor de campos de texto para exibição dos valores das propriedades
	private JTextField[] jTextFieldsPropriedades;
	//Aponta para o relacionamento do componente do projeto na memória com o component gráfico na JInternalFrame
	private RelacionamentoComponente relacionamentoComponente;
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Vetor auxiliar para tipos de valores
	private String[] tiposValores;

	@SuppressWarnings("unchecked")
	public JTetrisListPropriedadesInspetorDeObjetos(){
		super();
		setSize(130, 120);
		setBackground(new Color(240, 240, 240));
		setCellRenderer(new MyCellRendererPropriedades());
		addMouseListener(this);
		addKeyListener(this);
	}
	//Preenche a lista
	public void preencherLista(RelacionamentoComponente relacionamentoComponente, JFramePrincipal jFramePrincipal){
		this.relacionamentoComponente=relacionamentoComponente;
		this.jFramePrincipal=jFramePrincipal;
		String[] valores = new String[0];
		//Preenche os valores e os tipos dos valores de acordo com o tipo do componente selecionado
		if(relacionamentoComponente!=null){
			if(jFramePrincipal.getjDesktopPaneAreaDeTrabalho().getjInternalFrame().getComponentFoco().equals(jFramePrincipal.getjDesktopPaneAreaDeTrabalho().getjInternalFrame().getContentPane())){
				valores = new String [] {"Title", "x", "y", "width", "height", "Background", "DefaultCloseOperation", "ExtendedState", "LocationRelativeTo", "Modal", "Opacity", "Closable", "Iconifiable", "Resizable", "Icon", "Window", "Cursor", "Import", "CloseQuestion"};
				tiposValores = new String [] {"String", "int", "int", "int", "int", "Color", "DefaultCloseOperation", "ExtendedState", "LocationRelativeTo", "boolean", "int", "boolean", "boolean", "boolean", "Icon", "Janela", "Cursor", "Lista", "String"};
			}else{


				switch(relacionamentoComponente.getComponente().getTipo()){
				case "Rotulo":
					valores = new String [] {"Name", "Text", "x", "y", "width", "height", "Border", "Background", "Foreground", "Font", "Focusable", "Icon", "HorizontalAlignment", "VerticalAlignment", "Column", "Cursor", "ToolTipText", "Visible"};
					tiposValores = new String [] {"String", "String", "int", "int", "int", "int", "Border", "Color", "Color", "Font", "boolean", "Icon", "HorizontalAlignment", "VerticalAlignment", "String", "Cursor", "String", "boolean"};
					break;

				case "Campo de texto":
					valores = new String [] {"Name", "Text", "x", "y", "width", "height", "Enabled", "Editable", "Border", "Background", "Foreground", "Font", "Focusable", "HorizontalAlignment", "Mask", "MaxLength", "Column", "Cursor", "ToolTipText", "Visible"};
					tiposValores = new String [] {"String", "String", "int", "int", "int", "int", "boolean", "boolean", "Border", "Color", "Color", "Font", "boolean", "HorizontalAlignment", "Mascara", "int", "String", "Cursor", "String", "boolean"};
					break;

				case "Caixa de combinacao":
					valores = new String [] {"Name", "Text", "x", "y", "width", "height", "Enabled", "Editable", "Border", "Background", "Foreground", "Font", "Focusable", "Items", "Column", "Cursor", "ToolTipText", "Visible"};
					tiposValores = new String [] {"String", "String", "int", "int", "int", "int", "boolean", "boolean", "Border", "Color", "Color", "Font", "boolean", "Lista", "String", "Cursor", "String", "boolean"};
					break;

				case "Lista":
					valores = new String [] {"Name", "Text", "x", "y", "width", "height", "Enabled", "Border", "Background", "Foreground", "Font", "Focusable", "Items", "Column", "Cursor", "ToolTipText", "Visible"};
					tiposValores = new String [] {"String", "String", "int", "int", "int", "int", "boolean", "Border", "Color", "Color", "Font", "boolean", "Lista", "String", "Cursor", "String", "boolean"};
					
					break;
					
				case "TextArea":
					valores = new String [] {"Name", "Text", "x", "y", "width", "height", "Enabled", "Editable", "Border", "Background", "Foreground", "Font", "Focusable", "Column", "Cursor", "ToolTipText", "Visible"};
					tiposValores = new String [] {"String", "Text", "int", "int", "int", "int", "boolean", "boolean", "Border", "Color", "Color", "Font", "boolean", "String", "Cursor", "String", "boolean"};
					break;
					
				case "EditorPane":
					valores = new String [] {"Name", "Text", "ContentType", "Page", "x", "y", "width", "height", "Enabled", "Editable", "Border", "Background", "Foreground", "Font", "Focusable", "Column", "Cursor", "ToolTipText", "Visible"};
					tiposValores = new String [] {"String", "Text", "String", "String", "int", "int", "int", "int", "boolean", "boolean", "Border", "Color", "Color", "Font", "boolean", "String", "Cursor", "String", "boolean"};
					break;

				case "Botao":
					valores = new String [] {"Name", "Text", "x", "y", "width", "height", "Enabled", "Border", "Background", "Foreground", "Font", "Focusable", "Icon", "Mnemonic", "HorizontalAlignment", "VerticalAlignment", "HorizontalTextPosition", "VerticalTextPosition", "Undecorated", "Cursor", "ToolTipText", "Visible"};
					tiposValores = new String [] {"String", "String", "int", "int", "int", "int", "boolean", "Border", "Color", "Color", "Font", "boolean", "Icon", "char", "HorizontalAlignment", "VerticalAlignment", "HorizontalTextPosition", "VerticalTextPosition", "boolean", "Cursor", "String", "boolean"};
					break;

				case "Campo de checagem":
					valores = new String [] {"Name", "Title", "Valor", "x", "y", "width", "height", "Enabled", "Border", "Background", "Foreground", "Font", "Focusable", "Checked", "HorizontalAlignment", "Column", "Cursor", "ToolTipText", "Visible"};
					tiposValores = new String [] {"String", "String", "String", "int", "int", "int", "int", "boolean", "Border", "Color", "Color", "Font", "boolean", "boolean", "HorizontalAlignment", "String", "Cursor", "String", "boolean"};
					break;

				case "Tabela":
					valores = new String [] {"Name", "Border", "x", "y", "width", "height", "Columns", "Titles", "Background", "Foreground", "Font", "Focusable", "Cursor", "ToolTipText", "Visible"};
					tiposValores = new String [] {"String", "Border", "int", "int", "int", "int", "Lista", "Lista", "Color", "Color", "Font", "boolean", "Cursor", "String", "boolean"};
					break;

				case "Campo de escolha":
					valores = new String [] {"Name", "Text", "Enabled", "Border", "x", "y", "width", "height", "RadioButtons", "Background", "Foreground", "Font", "Focusable", "Column", "Cursor", "ToolTipText", "Visible"};
					tiposValores = new String [] {"String", "String", "boolean", "Border", "int", "int", "int", "int", "Lista", "Color", "Color", "Font", "boolean", "String", "Cursor", "String", "boolean"};
					break;

				case "Painel":
					valores = new String [] {"Name", "Text", "Border", "x", "y", "width", "height", "Focusable", "Icon", "Background", "Cursor", "ToolTipText", "Visible"};
					tiposValores = new String [] {"String", "String", "Border", "int", "int", "int", "int", "boolean", "Icon", "Color", "Cursor", "String", "boolean"};
					break;
					
				case "Barra de ferramentas":
					valores = new String [] {"Name", "Border", "x", "y", "width", "height", "Icon", "Focusable", "Floatable", "Background", "Cursor", "ToolTipText", "Visible"};
					tiposValores = new String [] {"String", "Border", "int", "int", "int", "int", "Icon", "boolean", "boolean", "Color", "Cursor", "String", "boolean"};
					break;

				case "Abas":
					valores = new String [] {"Name", "Border", "x", "y", "width", "height", "Background", "Focusable", "Tab", "Cursor", "ToolTipText", "Visible"};
					tiposValores = new String [] {"String", "Border", "int", "int", "int", "int", "Color", "boolean", "Abas", "Cursor", "String", "boolean"};
					break;

				case "Barra de menu":
					valores = new String [] {"Name", "Items", "Focusable", "Cursor", "ToolTipText", "Visible"};
					tiposValores = new String [] {"String", "Menu", "boolean", "Cursor", "String", "boolean"};
					break;

				case "Menu":
					valores = new String [] {"Name", "Text", "Items", "Background", "Foreground", "Font", "Focusable", "Mnemonic", "Icon", "Cursor", "ToolTipText", "Visible"};
					tiposValores = new String [] {"String", "String", "Menu", "Color", "Color", "Font", "boolean", "char", "Icon", "Cursor", "String", "boolean"};
					break;

				case "Menu Item":
					valores = new String [] {"Name", "Text", "Shortcut", "Background", "Foreground", "Font", "Focusable", "Mnemonic", "Icon", "Cursor", "ToolTipText", "Visible"};
					tiposValores = new String [] {"String", "String", "String", "Color", "Color", "Font", "boolean", "char", "Icon", "Cursor", "String", "boolean"};
					break;

				case "Timer":
					valores = new String [] {"Name", "Delay", "Active"};
					tiposValores = new String [] {"String", "int", "boolean"};
					break;

				case "Procedure":
					valores = new String [] {"Name", "Parameters", "Type", "Return"};
					tiposValores = new String [] {"String", "String", "String", "String"};
					break;
					
				case "Variable":
					valores = new String [] {"Name", "Type", "Value", "Column"};
					tiposValores = new String [] {"String", "String", "String", "String"};
					break;

				case "Relatorio":
					valores = new String [] {"Name", "Report", "Orientation", "MarginLeft", "MarginRight", "MarginTop", "MarginBottom", "Head", "Foot"};
					tiposValores = new String [] {"String", "Report", "Orientacao", "int", "int", "int", "int", "Text", "Text"};
					break;

				}
			}
		}
		//Inicializa o vetor com os campos de texto a partir da quantidade de propriedades
		jTextFieldsPropriedades = new JTextField[valores.length];
		for (int i = 0; i < valores.length; i++) {
			jTextFieldsPropriedades[i] = new JTextField();
		}
		//Preenche lista
		setValores(valores);
	}
	//Muda um atributo/propriedade
	public void mudarAtributo(){
		//Verifica se há uma propriedade selecionada
		if(getSelectedIndex() >= 0){
			//Capturando o componente foco
			String componenteFoco = ""+relacionamentoComponente.getComponente().getNome();
			//Capturando o novo valor da propriedade
			String retorno = new JDialogEditarPropriedade(jFramePrincipal, relacionamentoComponente.getComponente().getAtributo(getSelectedValue()), tiposValores[getSelectedIndex()]).init();
			if(retorno!=null){
				//Valida valores incompatíveis parao retorno
				if((retorno.equals("")==false) || (relacionamentoComponente.getComponente().getAtributo(""+getSelectedValue())==null) || (relacionamentoComponente.getComponente().getAtributo(""+getSelectedValue()).getTipo().equals("int")==false)){
					if(tiposValores!=null){
						boolean muda=true;
						ArrayList<Componente> arrayListComponentes = jFramePrincipal.getjDesktopPaneAreaDeTrabalho().getJanela().getArrayListComponentes();
						//Se a propriedade a ser modificada é o Name, verifica se há um componente com o mesmo nome
						if(getSelectedValue().equals("Name")){

							for (int i = 0; i < arrayListComponentes.size(); i++) {
								if((arrayListComponentes.get(i).getNome().equals(retorno)) && (!retorno.equals(componenteFoco))){
									muda=false;
									JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_add_component", jFramePrincipal));
									break;
								}
							}
						}

						//Muda o valor do atributo
						if(muda){
							//Adiciona estado de modificação da janela
							jFramePrincipal.getjDesktopPaneAreaDeTrabalho().getJanela().adicionaEstadoDeJanela();
							//Muda atributo no componente
							relacionamentoComponente.getComponente().mudarAtributo(new Atributo(getSelectedValue(), tiposValores[getSelectedIndex()], retorno));
							if(getSelectedValue().equals("Name")){
								//Caso o atributo que esteja mudando de nome seja Nome, muda o Parent dos componentes filhos
								for (int i = 0; i < arrayListComponentes.size(); i++) {
									Atributo atributo = arrayListComponentes.get(i).getAtributo("Pai"); 
									if(atributo!=null){
										if(atributo.getValor().equals(componenteFoco)){
											arrayListComponentes.get(i).mudarAtributo(new Atributo("Pai", "String", retorno));
										}
									}
								}
							}
						}
					}
					//Atualiza a janela na área de trabalho
					jFramePrincipal.getjDesktopPaneAreaDeTrabalho().atualizarJanela();
					//Define o foco no componente
					ArrayList<RelacionamentoComponente> arrayListRelacionamentoComponentes = jFramePrincipal.getjDesktopPaneAreaDeTrabalho().getjInternalFrame().getArrayListRelacionamentoComponentes(); 
					for (int i = 0; i < arrayListRelacionamentoComponentes.size(); i++) {
						if(arrayListRelacionamentoComponentes.get(i).getComponente().getNome().equals(componenteFoco)){
							jFramePrincipal.getjDesktopPaneAreaDeTrabalho().getjInternalFrame().setComponentFoco(arrayListRelacionamentoComponentes.get(i).getComponent(), false);
							break;
						}
					}

				}
			}
		}
	}
	//Getters e Setters
	public JTextField[] getjTextFieldsPropriedades() {
		return jTextFieldsPropriedades;
	}

	public void setjTextFieldsPropriedades(JTextField[] jTextFieldsPropriedades) {
		this.jTextFieldsPropriedades = jTextFieldsPropriedades;
	}

	public RelacionamentoComponente getRelacionamentoComponente() {
		return relacionamentoComponente;
	}

	public void setRelacionamentoComponente(
			RelacionamentoComponente relacionamentoComponente) {
		this.relacionamentoComponente = relacionamentoComponente;
	}
	//Representa evento de clique do mouse
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		//Muda valor de atributo de um componente se efetuado dois cliques
		if(e.getClickCount()== 2){
			mudarAtributo();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	//Representa evento de realização de pressionamento de tecla
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		//Se precionada a tecla ENTER, muda atributo
		if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
			mudarAtributo();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}



}

//Modelo de renderizacao das celulas da lista
@SuppressWarnings({ "rawtypes", "serial" })
class MyCellRendererPropriedades extends JLabel implements ListCellRenderer {
	// This is the only method defined by ListCellRenderer.
	// We just reconfigure the JLabel each time we're called.

	public Component getListCellRendererComponent(
			JList list,
			Object value,            // value to display
			int index,               // cell index
			boolean isSelected,      // is the cell selected
			boolean cellHasFocus)    // the list and the cell have the focus
	{
		setText(""+value);


		JTetrisListPropriedadesInspetorDeObjetos lista = (JTetrisListPropriedadesInspetorDeObjetos)list;
		//Remove todos os objetos do item da lista
		removeAll();
		//Adiciona o campo de texto
		add(lista.getjTextFieldsPropriedades()[index]);
		lista.getjTextFieldsPropriedades()[index].setBounds(80, -5, 370, 30);
		//Seta o valor da propriedade ao campo de texto
		Componente componente = lista.getRelacionamentoComponente().getComponente();
		Atributo atrib =componente.getAtributo(getText()); 
		if(atrib!=null){
			lista.getjTextFieldsPropriedades()[index].setText(atrib.getValor());
		}

		if(isSelected){
			setForeground(Color.RED);
		}else{
			setForeground(Color.BLACK);
		}

		//Amostragem de tipos especiais
		switch (getText()) {
		case "Background":
			String[] cores = lista.getjTextFieldsPropriedades()[index].getText().split(", ");

			if(cores.length > 2){
				remove(lista.getjTextFieldsPropriedades()[index]);
				JPanel jPanel = new JPanel();
				add(jPanel);



				jPanel.setBackground(new Color(Integer.parseInt(cores[0]), Integer.parseInt(cores[1]), Integer.parseInt(cores[2])));
				jPanel.setBounds(80, -5, 370, 30);
			}
			break;
			
		case "Foreground":
			cores = lista.getjTextFieldsPropriedades()[index].getText().split(", ");

			if(cores.length > 2){
				remove(lista.getjTextFieldsPropriedades()[index]);
				JPanel jPanel = new JPanel();
				add(jPanel);



				jPanel.setBackground(new Color(Integer.parseInt(cores[0]), Integer.parseInt(cores[1]), Integer.parseInt(cores[2])));
				jPanel.setBounds(80, -5, 370, 30);
			}
			break;
			
		case "Tipo":
			remove(lista.getjTextFieldsPropriedades()[index]);
			
			String tipo="Frame";
			if(lista.getjTextFieldsPropriedades()[index].getText().equals("1")){
				tipo="Dialog";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals("2")){
				tipo="Internal Frame";
			}
			
			JTetrisTextField jTetrisTextField = new JTetrisTextField();
			jTetrisTextField.setText(tipo);
			
			add(jTetrisTextField);
			jTetrisTextField.setBounds(80, -5, 370, 30);
			
			break;
			
		case "Window":
			remove(lista.getjTextFieldsPropriedades()[index]);
			
			String window="Frame";
			if(lista.getjTextFieldsPropriedades()[index].getText().equals("1")){
				window="Dialog";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals("2")){
				window="Internal Frame";
			}
			
			JTetrisTextField jTetrisTextFieldWindow = new JTetrisTextField();
			jTetrisTextFieldWindow.setText(window);
			
			add(jTetrisTextFieldWindow);
			jTetrisTextFieldWindow.setBounds(80, -5, 370, 30);
			
			break;
			
		case "ExtendedState":
			if(lista.getjTextFieldsPropriedades()[index].getText().equals("6")){
				remove(lista.getjTextFieldsPropriedades()[index]);
				jTetrisTextField = new JTetrisTextField();
				jTetrisTextField.setText("MAXIMIZED_BOTH");
				
				add(jTetrisTextField);
				jTetrisTextField.setBounds(80, -5, 370, 30);
			}
			
			break;
			
		case "LocationRelativeTo":
			if(lista.getjTextFieldsPropriedades()[index].getText().equals("null")){
				remove(lista.getjTextFieldsPropriedades()[index]);
				jTetrisTextField = new JTetrisTextField();
				jTetrisTextField.setText("Center");
				
				add(jTetrisTextField);
				jTetrisTextField.setBounds(80, -5, 370, 30);
			}
			
			break;
			
		case "Border":
			remove(lista.getjTextFieldsPropriedades()[index]);
			
			String borda="";
			if(lista.getjTextFieldsPropriedades()[index].getText().equals("0")){
				borda="EtchedBorder";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals("1")){
				borda="LoweredBorder";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals("2")){
				borda="RaisedBorder";
			}
			
			jTetrisTextField = new JTetrisTextField();
			jTetrisTextField.setText(borda);
			
			add(jTetrisTextField);
			jTetrisTextField.setBounds(80, -5, 370, 30);
			
			break;
			
		case "HorizontalAlignment":
			remove(lista.getjTextFieldsPropriedades()[index]);
			
			String horizontalAlignment="";
			if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.CENTER)){
				horizontalAlignment="CENTER";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.LEADING)){
				horizontalAlignment="LEADING";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.LEFT)){
				horizontalAlignment="LEFT";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.RIGHT)){
				horizontalAlignment="RIGHT";
			}
			
			jTetrisTextField = new JTetrisTextField();
			jTetrisTextField.setText(horizontalAlignment);
			
			add(jTetrisTextField);
			jTetrisTextField.setBounds(80, -5, 370, 30);
			
			break;
			
		case "VerticalAlignment":
			remove(lista.getjTextFieldsPropriedades()[index]);
			
			String verticalAlignment="";
			if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.CENTER)){
				verticalAlignment="CENTER";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.TOP)){
				verticalAlignment="TOP";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.BOTTOM)){
				verticalAlignment="BOTTOM";
			}
			
			jTetrisTextField = new JTetrisTextField();
			jTetrisTextField.setText(verticalAlignment);
			
			add(jTetrisTextField);
			jTetrisTextField.setBounds(80, -5, 370, 30);
			
			break;
			
		case "HorizontalTextPosition":
			remove(lista.getjTextFieldsPropriedades()[index]);
			
			horizontalAlignment="";
			if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.CENTER)){
				horizontalAlignment="CENTER";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.LEADING)){
				horizontalAlignment="LEADING";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.TRAILING)){
				horizontalAlignment="TRAILING";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.LEFT)){
				horizontalAlignment="LEFT";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.RIGHT)){
				horizontalAlignment="RIGHT";
			}
			
			jTetrisTextField = new JTetrisTextField();
			jTetrisTextField.setText(horizontalAlignment);
			
			add(jTetrisTextField);
			jTetrisTextField.setBounds(80, -5, 370, 30);
			
			break;
			
		case "VerticalTextPosition":
			remove(lista.getjTextFieldsPropriedades()[index]);
			
			verticalAlignment="";
			if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.CENTER)){
				verticalAlignment="CENTER";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.TOP)){
				verticalAlignment="TOP";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.BOTTOM)){
				verticalAlignment="BOTTOM";
			}
			
			jTetrisTextField = new JTetrisTextField();
			jTetrisTextField.setText(verticalAlignment);
			
			add(jTetrisTextField);
			jTetrisTextField.setBounds(80, -5, 370, 30);
			
			break;
			
		case "Mask":
			remove(lista.getjTextFieldsPropriedades()[index]);
			
			String mascara="";
			if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.MASCARA_MAIUSCULO)){
				mascara="Upper Case";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.MASCARA_CEP)){
				mascara="Zip Code";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.MASCARA_CNPJ)){
				mascara="CNPJ";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.MASCARA_TELEFONE)){
				mascara="Phone";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.MASCARA_INSCRICAO_ESTADUAL)){
				mascara="State Registration";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.MASCARA_RG)){
				mascara="Password";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.MASCARA_CPF)){
				mascara="CPF";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.MASCARA_NUMERO_INTEIRO)){
				mascara="Integer";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.MASCARA_DECIMAL)){
				mascara="Decimal";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.MASCARA_DATA)){
				mascara="Date";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.MASCARA_HORA)){
				mascara="Time";
			}else if(lista.getjTextFieldsPropriedades()[index].getText().equals(""+JTetrisTextField.MASCARA_SEM_ESPACOS)){
				mascara="No Space";
			}
			
			jTetrisTextField = new JTetrisTextField();
			jTetrisTextField.setText(mascara);
			
			add(jTetrisTextField);
			jTetrisTextField.setBounds(80, -5, 370, 30);
			
			break;

		default:
			break;
		}
		
		return this;
	}
}
