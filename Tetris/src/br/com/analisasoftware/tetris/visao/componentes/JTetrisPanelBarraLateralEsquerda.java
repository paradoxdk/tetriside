/*
 *Barra lateral esquerda do TetrisIDE
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 25 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.componentes;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import componentes.visao.JTetrisPanel;

@SuppressWarnings("serial")
public class JTetrisPanelBarraLateralEsquerda extends JTetrisPanel implements MouseMotionListener, MouseListener{
	//Constantes e variáveis de auxílio
	private static final int LESTE=2, CENTRO=8;
	private int posMouse;
	//Barra lateral esquerda do Tetris
	public JTetrisPanelBarraLateralEsquerda(){
		super();
		addMouseMotionListener(this);
		addMouseListener(this);
		posMouse=CENTRO;
	}

	//Métodos de redimensionamento da barra lateral
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(posMouse == LESTE){
			setSize(getWidth()+(arg0.getX()-getWidth()), getHeight());
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getX() >= getWidth() - 3){ 
			setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
		}else{
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getX() >= getWidth() - 3){ 
			posMouse=LESTE;
		}else{
			posMouse=CENTRO;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
