/*
 *Painel de ContentPane do JInternalFrame utilizado na área de trabalho do TetrisIDE
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 25 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.componentes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;

import br.com.analisasoftware.tetris.visao.janelas.JInternalFrameJanela;
import componentes.visao.JTetrisPanel;

@SuppressWarnings("serial")
public class JTetrisPanelInternalFrame extends JTetrisPanel {
	//Aponta para a JInternalFrame aberta na área de trabalho
	private JInternalFrameJanela jInternalFrameJanela;

	public JTetrisPanelInternalFrame(JInternalFrameJanela jInternalFrameJanela){
		super(null);
		this.jInternalFrameJanela=jInternalFrameJanela;
	}

	//Pinta retângulo ao redor dos componentes selecionados
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Aponta para a lista com os componentes em foco
		ArrayList<Component> arrayListComponentFoco = getjInternalFrameJanela().getArrayListComponentFoco();

		try{
			//Percorre a lista
			for (int i = 0; i < arrayListComponentFoco.size(); i++) {
				if(arrayListComponentFoco.get(i)!=null){
					//Verifica se o componente está contido no Painel
					if(arrayListComponentFoco.get(i).getParent().equals(this)){
						//Desenha um retângulo ao redor do componente
						g.setColor(Color.BLACK);
						g.drawRoundRect(arrayListComponentFoco.get(i).getX() - 2, arrayListComponentFoco.get(i).getY() - 2,
								arrayListComponentFoco.get(i).getWidth() + 3,
								arrayListComponentFoco.get(i).getHeight() + 3, 10, 10);

						//Desenhando quadrados ao redor da marcacao
						g.fillRect(arrayListComponentFoco.get(i).getX() - 3, arrayListComponentFoco.get(i).getY() + (arrayListComponentFoco.get(i).getHeight() / 2)-1, 3, 3);
						g.fillRect(arrayListComponentFoco.get(i).getX() + arrayListComponentFoco.get(i).getWidth(), arrayListComponentFoco.get(i).getY() + (arrayListComponentFoco.get(i).getHeight() / 2)-1, 3, 3);

						g.fillRect(arrayListComponentFoco.get(i).getX() + (arrayListComponentFoco.get(i).getWidth() / 2)-1, arrayListComponentFoco.get(i).getY() - 3, 3, 3);
						g.fillRect(arrayListComponentFoco.get(i).getX() + (arrayListComponentFoco.get(i).getWidth() / 2)-1, arrayListComponentFoco.get(i).getY() + arrayListComponentFoco.get(i).getHeight(), 3, 3);
					}
				}
			}

			//Desenha selecao do mouse			
			if((getjInternalFrameJanela().getMouseXInicial() >= 0) && (getjInternalFrameJanela().getContentPane().equals(this)) 
					&& (!getjInternalFrameJanela().getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().isDragged())) {
				g.setColor(Color.BLACK);


				g.drawRoundRect(getjInternalFrameJanela().getMouseXInicial(), getjInternalFrameJanela().getMouseYInicial(),
						getjInternalFrameJanela().getMouseXFinal() - getjInternalFrameJanela().getMouseXInicial(),
						getjInternalFrameJanela().getMouseYFinal() - getjInternalFrameJanela().getMouseYInicial(), 10, 10);

				g.drawRoundRect(getjInternalFrameJanela().getMouseXFinal(), getjInternalFrameJanela().getMouseYFinal(),
						getjInternalFrameJanela().getMouseXInicial() - getjInternalFrameJanela().getMouseXFinal(),
						getjInternalFrameJanela().getMouseYInicial() - getjInternalFrameJanela().getMouseYFinal(), 10, 10);

				g.drawRoundRect(getjInternalFrameJanela().getMouseXInicial(), getjInternalFrameJanela().getMouseYFinal(),
						getjInternalFrameJanela().getMouseXFinal() - getjInternalFrameJanela().getMouseXInicial(),
						getjInternalFrameJanela().getMouseYInicial() - getjInternalFrameJanela().getMouseYFinal(), 10, 10);

				g.drawRoundRect(getjInternalFrameJanela().getMouseXFinal(), getjInternalFrameJanela().getMouseYInicial(),
						getjInternalFrameJanela().getMouseXInicial() - getjInternalFrameJanela().getMouseXFinal(),
						getjInternalFrameJanela().getMouseYFinal() - getjInternalFrameJanela().getMouseYInicial(), 10, 10);

			}
			
		}catch(Exception exc){

		}
	}
	//Getters e Setters
	public JInternalFrameJanela getjInternalFrameJanela() {
		return jInternalFrameJanela;
	}

	public void setjInternalFrameJanela(JInternalFrameJanela jInternalFrameJanela) {
		this.jInternalFrameJanela = jInternalFrameJanela;
	}


}
