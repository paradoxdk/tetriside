package componentes.visao;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class JTetrisScrolledTextArea extends JScrollPane {
	private JTextArea jTextArea;

	public JTetrisScrolledTextArea() {
		super();
		jTextArea = new JTextArea();
		getViewport().setView(jTextArea);
		
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent arg0){
				getViewport().setView(jTextArea);
			}
		});
	}

	public JTextArea getjTextArea() {
		return jTextArea;
	}
	
	public void setText(String text){
		jTextArea.setText(text);
	}
	
	public String getText(){
		return jTextArea.getText();
	}
	
	@Override
	public void setToolTipText(String text){
		super.setToolTipText(text);
		if(jTextArea!=null){
			jTextArea.setToolTipText(text);
		}
	}
	
	@Override
	public void setFont(Font font){
		super.setFont(font);
		if(jTextArea!=null){
			jTextArea.setFont(font);
		}
	}
	
	@Override
	public void setBackground(Color color){
		super.setBackground(color);
		if(jTextArea!=null){
			jTextArea.setBackground(color);
		}
	}
	
	@Override
	public void setForeground(Color color){
		super.setForeground(color);
		if(jTextArea!=null){
			jTextArea.setForeground(color);
		}
	}
	
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		if(jTextArea!=null){
			jTextArea.setEnabled(enabled);
		}
	}
	
	@Override
	public void setCursor(Cursor cursor){
		super.setCursor(cursor);
		if(jTextArea!=null){
			jTextArea.setCursor(cursor);
		}
	}
	
	public void setEditable(boolean editable){
		jTextArea.setEditable(editable);
	}
	
	public boolean isEditable(){
		return jTextArea.isEditable();
	}
	
	@Override
	public boolean hasFocus(){
		if(jTextArea!=null){
			return jTextArea.hasFocus();
		}else{
			return super.hasFocus();
		}
	}
	
	@Override
	public void requestFocus(){
		if(jTextArea!=null){
			jTextArea.requestFocus();
		}else{
			super.requestFocus();
		}
	}
	
	@Override
	public void addKeyListener(KeyListener keyListener){
		super.addKeyListener(keyListener);
		if(jTextArea!=null){
			jTextArea.addKeyListener(keyListener);
		}
	}
	
	@Override
	public void addMouseListener(MouseListener mouseListener){
		super.addMouseListener(mouseListener);
		if(jTextArea!=null){
			jTextArea.addMouseListener(mouseListener);
		}
	}
	
	@Override
	public void addMouseMotionListener(MouseMotionListener mouseMotionListener){
		super.addMouseMotionListener(mouseMotionListener);
		if(jTextArea!=null){
			jTextArea.addMouseMotionListener(mouseMotionListener);
		}
	}
	
	@Override
	public void addMouseWheelListener(MouseWheelListener mouseWheelListener){
		super.addMouseWheelListener(mouseWheelListener);
		if(jTextArea!=null){
			jTextArea.addMouseWheelListener(mouseWheelListener);
		}
	}
	
	@Override
	public void addFocusListener(FocusListener focusListener){
		super.addFocusListener(focusListener);
		if(jTextArea!=null){
			jTextArea.addFocusListener(focusListener);
		}
	}
}
