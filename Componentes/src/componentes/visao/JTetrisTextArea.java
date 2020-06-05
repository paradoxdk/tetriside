package componentes.visao;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class JTetrisTextArea extends JScrollPane {
	private JTextArea jTextArea;

	public JTetrisTextArea() {
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
		return jTextArea.hasFocus();
	}
	
	@Override
	public void requestFocus(){
		jTextArea.requestFocus();
	}
}
