/*
 *JList da Paleta de Objetos do TetrisIDE
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 25 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.componentes;

import java.awt.Color;

import javax.swing.ImageIcon;

import br.com.analisasoftware.tetris.visao.janelas.JFramePrincipal;
import componentes.visao.JTetrisList;

@SuppressWarnings("serial")
public class JTetrisListPaletaDeObjetos extends JTetrisList {
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	
	//Cria Paleta de Objetos
	public JTetrisListPaletaDeObjetos() {
		super();
		
		setSize(130, 300);
		setBackground(new Color(240, 240, 240));
	}
	
	//Preenchendo a lista da Paleta de Objetos
	public void preencherLista(JFramePrincipal jFramePrincipal){
		this.jFramePrincipal=jFramePrincipal;
		//Preenche a lista com os componentes e as imagens da Paleta de Objetos
		String[] componentes = getComponentes();
		setValores(componentes);
		setImagens(getImagens());
		
		setSelectedIndex(0);
	}

	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}
	
	//Lista de Componentes
	public String[] getComponentes(){
		return new String[] {"Pointer", "Label", "TextField", "ComboBox", "List", "TextArea", "EditorPane", "Panel", "ToolBar", "TabbedPane", "Image", "Button", "Table", "CheckBox", "RadioButton", "Menu Bar", "Timer", "Procedure", "Variable", "Report"};
	}
	
	//Lista de Imagens
	public ImageIcon[] getImagens(){
		return new ImageIcon[] {new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_mouse.png")),
				new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_rotulo.png")),
				new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_campo_texto.png")),
				new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_caixa_de_combinacao.png")),
				new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_lista.png")),
				new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_textarea.png")),
				new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_editorpane.png")),
				new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_painel.png")),
				new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_menu.png")),
				new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_abas.png")),
				new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_imagem.png")),
				new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_botao.png")),
				new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_tabela.png")),
				new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_checagem.png")),
				new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_escolha.png")),
				new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_menu.png")),
				new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_timer.png")),
				new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_procedure.png")),
				new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_variavel.png")),
				new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_report.png"))};
	}
	
}
