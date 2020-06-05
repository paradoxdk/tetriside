/*
 *Separador dos objetos das barras laterais do TetrisIDE
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 25 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.componentes;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JSeparator;

@SuppressWarnings("serial")
public class JTetrisSeparator extends JSeparator implements MouseMotionListener{
	//Barra lateral direita do Tetris
	public JTetrisSeparator(){
		super();
		addMouseMotionListener(this);
		
	}
	//Representa evento de segurar o objeto com o mouse
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//Modifica localização do objeto a partir do mouse
		setLocation(getX(), getY() + arg0.getY());
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
