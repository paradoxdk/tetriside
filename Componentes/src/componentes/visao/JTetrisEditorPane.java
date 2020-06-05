package componentes.visao;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class JTetrisEditorPane extends JScrollPane {
	private JEditorPane jEditorPane;

	public JTetrisEditorPane() {
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
		return jEditorPane.hasFocus();
	}
	
	@Override
	public void requestFocus(){
		jEditorPane.requestFocus();
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
	
}
