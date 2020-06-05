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
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class JTetrisScrolledEditorPane extends JScrollPane {
	private JEditorPane jEditorPane;

	public JTetrisScrolledEditorPane() {
		super();
		jEditorPane = new JEditorPane();
		getViewport().setView(jEditorPane);
		
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent arg0){
				getViewport().setView(jEditorPane);
			}
		});
	}

	public JEditorPane getjEditorPane() {
		return jEditorPane;
	}
	
	public void setText(String text){
		jEditorPane.setText(text);
	}
	
	public String getText(){
		return jEditorPane.getText();
	}
	
	@Override
	public void setToolTipText(String text){
		super.setToolTipText(text);
		if(jEditorPane!=null){
			jEditorPane.setToolTipText(text);
		}
	}
	
	@Override
	public void setFont(Font font){
		super.setFont(font);
		if(jEditorPane!=null){
			jEditorPane.setFont(font);
		}
	}
	
	@Override
	public void setBackground(Color color){
		super.setBackground(color);
		if(jEditorPane!=null){
			jEditorPane.setBackground(color);
		}
	}
	
	@Override
	public void setForeground(Color color){
		super.setForeground(color);
		if(jEditorPane!=null){
			jEditorPane.setForeground(color);
		}
	}
	
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		if(jEditorPane!=null){
			jEditorPane.setEnabled(enabled);
		}
	}
	
	@Override
	public void setCursor(Cursor cursor){
		super.setCursor(cursor);
		if(jEditorPane!=null){
			jEditorPane.setCursor(cursor);
		}
	}
	
	public void setEditable(boolean editable){
		jEditorPane.setEditable(editable);
	}
	
	public boolean isEditable(){
		return jEditorPane.isEditable();
	}
	
	@Override
	public boolean hasFocus(){
		if(jEditorPane!=null){
			return jEditorPane.hasFocus();
		}else{
			return super.hasFocus();
		}
	}
	
	@Override
	public void requestFocus(){
		if(jEditorPane!=null){
			jEditorPane.requestFocus();
		}else{
			super.requestFocus();
		}
	}
	
	public void setContentType(String contentType){
		jEditorPane.setContentType(contentType);
	}
	
	public String getContentType(){
		return jEditorPane.getContentType();
	}
	
	public void setPage(String page) throws IOException{
		jEditorPane.setPage(page);
	}
	
	public String getPage(){
		return jEditorPane.getPage().toString();
	}
	
	@Override
	public void addKeyListener(KeyListener keyListener){
		super.addKeyListener(keyListener);
		if(jEditorPane!=null){
			jEditorPane.addKeyListener(keyListener);
		}
	}
	
	@Override
	public void addMouseListener(MouseListener mouseListener){
		super.addMouseListener(mouseListener);
		if(jEditorPane!=null){
			jEditorPane.addMouseListener(mouseListener);
		}
	}
	
	@Override
	public void addMouseMotionListener(MouseMotionListener mouseMotionListener){
		super.addMouseMotionListener(mouseMotionListener);
		if(jEditorPane!=null){
			jEditorPane.addMouseMotionListener(mouseMotionListener);
		}
	}
	
	@Override
	public void addMouseWheelListener(MouseWheelListener mouseWheelListener){
		super.addMouseWheelListener(mouseWheelListener);
		if(jEditorPane!=null){
			jEditorPane.addMouseWheelListener(mouseWheelListener);
		}
	}
	
	@Override
	public void addFocusListener(FocusListener focusListener){
		super.addFocusListener(focusListener);
		if(jEditorPane!=null){
			jEditorPane.addFocusListener(focusListener);
		}
	}
	
}
