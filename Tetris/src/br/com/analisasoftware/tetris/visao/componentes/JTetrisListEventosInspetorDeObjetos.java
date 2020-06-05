/*
 *JList com os eventos do componente selecionado no Inspetor de Objetos
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 25 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.componentes;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import br.com.analisasoftware.tetris.modelo.Idioma;
import br.com.analisasoftware.tetris.visao.janelas.JDialogEditarEvento;
import br.com.analisasoftware.tetris.visao.janelas.JFramePrincipal;
import componentes.visao.JTetrisList;
import tetris.modelo.componentes.Componente;
import tetris.modelo.componentes.Metodo;
import tetris.modelo.componentes.RelacionamentoComponente;

@SuppressWarnings("serial")
public class JTetrisListEventosInspetorDeObjetos extends JTetrisList implements MouseListener{
	//Vetor com Campos de texto que exibe os eventos
	private JTextField[] jTextFieldsEventos;
	//Aponta para o relacionamento entre o componente selecionado no projeto e o component na tela 
	private RelacionamentoComponente relacionamentoComponente;
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;

	@SuppressWarnings("unchecked")
	public JTetrisListEventosInspetorDeObjetos(){
		super();
		setSize(130, 120);
		setBackground(new Color(240, 240, 240));
		setCellRenderer(new MyCellRendererEventos());
		addMouseListener(this);

	}
	//Preenche a lista de Eventos
	public void preencherLista(RelacionamentoComponente relacionamentoComponente, JFramePrincipal jFramePrincipal){
		this.relacionamentoComponente=relacionamentoComponente;
		this.jFramePrincipal=jFramePrincipal;

		String[] metodos = new String[0];
		if(relacionamentoComponente!=null){
			//Preenche a lista de eventos de acordo com o tipo do componente selecionado
			if(jFramePrincipal.getjDesktopPaneAreaDeTrabalho().getjInternalFrame().getComponentFoco().equals(jFramePrincipal.getjDesktopPaneAreaDeTrabalho().getjInternalFrame().getContentPane())){
				metodos = new String [] {"OnCreate", "OnClose", "OnClick", "OnFocusGained", "OnFocusLost", "OnKeyReleased", "OnKeyPressed", "OnKeyTyped", "OnMouseReleased", "OnMousePressed", "OnMouseEntered", "OnMouseExited", "OnMouseDragged", "OnMouseMoved", "OnMouseWheelMoved", "OnPaint", "OnResize", "OnShow"};
			}else{


				switch(relacionamentoComponente.getComponente().getTipo()){
				case "Rotulo":
					metodos = new String [] {"OnClick", "OnFocusGained", "OnFocusLost", "OnHide", "OnKeyReleased", "OnKeyPressed", "OnKeyTyped", "OnMouseReleased", "OnMousePressed", "OnMouseEntered", "OnMouseExited", "OnMouseDragged", "OnMouseMoved", "OnMouseWheelMoved", "OnMove", "OnPaint", "OnResize", "OnShow"};
					break;

				case "Campo de texto":
					metodos = new String [] {"OnClick", "OnChange", "OnFocusGained", "OnFocusLost", "OnHide", "OnKeyReleased", "OnKeyPressed", "OnKeyTyped", "OnMouseReleased", "OnMousePressed", "OnMouseEntered", "OnMouseExited", "OnMouseDragged", "OnMouseMoved", "OnMouseWheelMoved", "OnMove", "OnPaint", "OnResize", "OnShow"};
					break;
				
				case "Caixa de combinacao":
					metodos = new String [] {"OnClick", "OnChange", "OnFocusGained", "OnFocusLost", "OnHide", "OnKeyReleased", "OnKeyPressed", "OnKeyTyped", "OnMouseReleased", "OnMousePressed", "OnMouseEntered", "OnMouseExited", "OnMouseDragged", "OnMouseMoved", "OnMouseWheelMoved", "OnMove", "OnPaint", "OnResize", "OnShow"};
					break;
					
				case "Lista":
					metodos = new String [] {"OnClick", "OnChange", "OnFocusGained", "OnFocusLost", "OnHide", "OnKeyReleased", "OnKeyPressed", "OnKeyTyped", "OnMouseReleased", "OnMousePressed", "OnMouseEntered", "OnMouseExited", "OnMouseDragged", "OnMouseMoved", "OnMouseWheelMoved", "OnMove", "OnPaint", "OnResize", "OnShow"};
					break;
					
				case "TextArea":
					metodos = new String [] {"OnClick", "OnChange", "OnFocusGained", "OnFocusLost", "OnHide", "OnKeyReleased", "OnKeyPressed", "OnKeyTyped", "OnMouseReleased", "OnMousePressed", "OnMouseEntered", "OnMouseExited", "OnMouseDragged", "OnMouseMoved", "OnMouseWheelMoved", "OnMove", "OnPaint", "OnResize", "OnShow"};
					break;
				
				case "EditorPane":
					metodos = new String [] {"OnClick", "OnChange", "OnFocusGained", "OnFocusLost", "OnHide", "OnKeyReleased", "OnKeyPressed", "OnKeyTyped", "OnMouseReleased", "OnMousePressed", "OnMouseEntered", "OnMouseExited", "OnMouseDragged", "OnMouseMoved", "OnMouseWheelMoved", "OnMove", "OnPaint", "OnResize", "OnShow"};
					break;

				case "Botao":
					metodos = new String [] {"OnClick", "OnFocusGained", "OnFocusLost", "OnHide", "OnKeyReleased", "OnKeyPressed", "OnKeyTyped", "OnMouseReleased", "OnMousePressed", "OnMouseEntered", "OnMouseExited", "OnMouseDragged", "OnMouseMoved", "OnMouseWheelMoved", "OnMove", "OnPaint", "OnResize", "OnShow"};
					break;

				case "Campo de checagem":
					metodos = new String [] {"OnClick", "OnChange", "OnFocusGained", "OnFocusLost", "OnHide", "OnKeyReleased", "OnKeyPressed", "OnKeyTyped", "OnMouseReleased", "OnMousePressed", "OnMouseEntered", "OnMouseExited", "OnMouseDragged", "OnMouseMoved", "OnMouseWheelMoved", "OnMove", "OnPaint", "OnResize", "OnShow"};
					break;

				case "Tabela":
					metodos = new String [] {"OnClick", "OnFocusGained", "OnFocusLost", "OnHide", "OnKeyReleased", "OnKeyPressed", "OnKeyTyped", "OnMouseReleased", "OnMousePressed", "OnMouseEntered", "OnMouseExited", "OnMouseDragged", "OnMouseMoved", "OnMouseWheelMoved", "OnMove", "OnPaint", "OnResize", "OnShow"};
					break;

				case "Campo de escolha":
					metodos = new String [] {"OnClick", "OnChange", "OnFocusGained", "OnFocusLost", "OnHide", "OnKeyReleased", "OnKeyPressed", "OnKeyTyped", "OnMouseReleased", "OnMousePressed", "OnMouseEntered", "OnMouseExited", "OnMouseDragged", "OnMouseMoved", "OnMouseWheelMoved", "OnMove", "OnPaint", "OnResize", "OnShow"};
					break;

				case "Painel":
					metodos = new String [] {"OnClick", "OnHide", "OnKeyReleased", "OnKeyPressed", "OnKeyTyped", "OnMouseReleased", "OnMousePressed", "OnMouseEntered", "OnMouseExited", "OnMouseDragged", "OnMouseMoved", "OnMouseWheelMoved", "OnMove", "OnPaint", "OnResize", "OnShow"};
					break;
					
				case "Barra de ferramentas":
					metodos = new String [] {"OnClick", "OnHide", "OnKeyReleased", "OnKeyPressed", "OnKeyTyped", "OnMouseReleased", "OnMousePressed", "OnMouseEntered", "OnMouseExited", "OnMouseDragged", "OnMouseMoved", "OnMouseWheelMoved", "OnMove", "OnPaint", "OnResize", "OnShow"};
					break;
					
				case "Menu Item":
					metodos = new String [] {"OnClick", "OnHide", "OnKeyReleased", "OnKeyPressed", "OnKeyTyped", "OnMouseReleased", "OnMousePressed", "OnMouseEntered", "OnMouseExited", "OnMouseDragged", "OnMouseMoved", "OnMouseWheelMoved", "OnMove", "OnPaint", "OnResize", "OnShow"};
					break;
					
				case "Timer":
					metodos = new String [] {"OnTimer"};
					break;
					
				case "Procedure":
					metodos = new String [] {"OnExecute"};
					break;
				}
			}
		}

		jTextFieldsEventos = new JTextField[metodos.length];
		for (int i = 0; i < metodos.length; i++) {
			jTextFieldsEventos[i] = new JTextField();
		}

		setValores(metodos);
	}

	public JTextField[] getjTextFieldsEventos() {
		return jTextFieldsEventos;
	}

	public void setjTextFieldsPropriedades(JTextField[] jTextFieldsEventos) {
		this.jTextFieldsEventos = jTextFieldsEventos;
	}

	public RelacionamentoComponente getRelacionamentoComponente() {
		return relacionamentoComponente;
	}

	public void setRelacionamentoComponente(
			RelacionamentoComponente relacionamentoComponente) {
		this.relacionamentoComponente = relacionamentoComponente;
	}

	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}
	//Representa o evento de clique do mouse
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		//Mudando valor de funcao de um componente
		//Verifica se efetuou duplo clique
		if(e.getClickCount()== 2){
			//Verifica se há item selecionado
			if(getSelectedIndex() >= 0){
				//Abre o editor de eventos e captura as alterações
				ArrayList<Metodo> retorno = new JDialogEditarEvento(getjFramePrincipal(), relacionamentoComponente.getComponente().getMetodosFuncao(getSelectedValue())).init();
				if(retorno!=null){
					//Adiciona um estado de modificação da janela
					jFramePrincipal.getjDesktopPaneAreaDeTrabalho().getJanela().adicionaEstadoDeJanela();
					//Altera o método
					ArrayList<Metodo> arrayListMetodos = relacionamentoComponente.getComponente().getArrayListMetodos(); 
					for (int i = 0; i < arrayListMetodos.size(); i++) {
						if(arrayListMetodos.get(i).getAtributo("Evento").getValor().equals(getSelectedValue())){
							arrayListMetodos.remove(i);
							i--;
						}
					}
					for (int i = 0; i < retorno.size(); i++) {
						arrayListMetodos.add(retorno.get(i));
					}
					//Atualiza a lista
					preencherLista(getRelacionamentoComponente(), getjFramePrincipal());
					
					
				}
			}
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



}

//Modelo de renderizacao das celulas da lista
@SuppressWarnings({ "rawtypes", "serial" })
class MyCellRendererEventos extends JLabel implements ListCellRenderer {
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


		JTetrisListEventosInspetorDeObjetos lista = (JTetrisListEventosInspetorDeObjetos)list;
		//Adiciona o Campo de texto ao item da lista
		removeAll();
		add(lista.getjTextFieldsEventos()[index]);
		lista.getjTextFieldsEventos()[index].setBounds(80, -5, 370, 30);
		
		Componente componente = lista.getRelacionamentoComponente().getComponente();
		//Preenche o nome do evento e o nome dos métodos adicionados ao evento do componente
		ArrayList<Metodo> arrayListMetodos = componente.getMetodosFuncao(""+value); 

		String valorFuncao = "";

		for (int i = 0; i < arrayListMetodos.size(); i++) {
			if(i!=0){
				valorFuncao+=";";
			}
			valorFuncao+=Idioma.getTraducao("tetris."+arrayListMetodos.get(i).getNome().replace("0", "").replace("1", "").replace("2", "").replace("3", "").replace("4", "").replace("5", "").replace("6", "").replace("7", "").replace("8", "").replace("9", "").replace("_", ""), lista.getjFramePrincipal())+"(";
			
			valorFuncao+=")";
		}

		lista.getjTextFieldsEventos()[index].setText(valorFuncao);


		if(isSelected){
			setForeground(Color.RED);
		}else{
			setForeground(Color.BLACK);
		}

		return this;
	}
}

