package componentes.visao;


import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class JTetrisPasswordField extends JPasswordField implements FocusListener, KeyListener{
	private int maxLength;
	
	public JTetrisPasswordField(){
		addFocusListener(this);
		addKeyListener(this);

		setDisabledTextColor(Color.BLACK);
	}
	
	public String getText(){
		return new String(getPassword());
	}

	public void focusGained(FocusEvent f){
		selectAll();
	}

	public void focusLost(FocusEvent f){

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
			this.transferFocus();
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	
	
}

