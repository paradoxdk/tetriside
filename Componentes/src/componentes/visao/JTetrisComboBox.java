package componentes.visao;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComboBox;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class JTetrisComboBox extends JComboBox<String> {
	private KeyAdapter keyAdapter;
	public JTetrisComboBox(){
		super();
		keyAdapter = new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					if(arg0.getSource() instanceof JTextField){
						((JTextField)arg0.getSource()).transferFocus();
					}else{
						transferFocus();
					}
					
				}
			}
		};
		
		addKeyListener(keyAdapter);
	}
	
	public String getText(){
		return ""+getSelectedItem();
	}
	
	public void setText(String texto){
		setSelectedItem(texto);
	}
	
	@Override
	public void addMouseListener(MouseListener m){
		super.addMouseListener(m);
		
		Component[] componentes=this.getComponents();
		
		for (int i = 0; i < componentes.length; i++) {
			if(componentes[i] instanceof JTextField){
				((JTextField)componentes[i]).addMouseListener(m);
				
			}
		}
	}
	
	@Override
	public void addMouseMotionListener(MouseMotionListener m){
		super.addMouseMotionListener(m);
		
		Component[] componentes=this.getComponents();
		
		for (int i = 0; i < componentes.length; i++) {
			if(componentes[i] instanceof JTextField){
				((JTextField)componentes[i]).addMouseMotionListener(m);
				
			}
		}
	}
	
	@Override
	public void addKeyListener(KeyListener k){
		super.addKeyListener(k);
		
		Component[] componentes=this.getComponents();
		
		for (int i = 0; i < componentes.length; i++) {
			if(componentes[i] instanceof JTextField){
				((JTextField)componentes[i]).addKeyListener(k);
				
			}
		}
	}
	
	@Override
	public void setEditable(boolean editable){
		super.setEditable(editable);
		/*Component[] componentes=this.getComponents();
		
		//Adicionando eventos ao JTextField do JComboBox
		for (int i = 0; i < componentes.length; i++) {
			if(componentes[i] instanceof JTextField){
				KeyListener[] keyListeners = getKeyListeners();
				for (int j = 0; j < keyListeners.length; j++) {
					componentes[i].addKeyListener(keyListeners[j]);
				}
				MouseListener[] mouseListeners = getMouseListeners();
				for (int j = 0; j < mouseListeners.length; j++) {
					componentes[i].addMouseListener(mouseListeners[j]);
				}
				MouseMotionListener[] mouseMotionListeners = getMouseMotionListeners();
				for (int j = 0; j < mouseMotionListeners.length; j++) {
					componentes[i].addMouseMotionListener(mouseMotionListeners[j]);
				}
				FocusListener[] focusListeners = getFocusListeners();
				for (int j = 0; j < focusListeners.length; j++) {
					componentes[i].addFocusListener(focusListeners[j]);
				}
				
				
			}
		}*/
	}
	
}
