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
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

@SuppressWarnings("serial")
public class JTetrisScrolledList extends JScrollPane {
	private JTetrisList jTetrisList;

	public JTetrisScrolledList() {
		super();
		jTetrisList = new JTetrisList();
		getViewport().setView(jTetrisList);
		
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent arg0){
				getViewport().setView(jTetrisList);
			}
		});
	}

	public JTetrisList getjList() {
		return jTetrisList;
	}
	
	public void setSelectedValue(Object anObject, boolean shouldScroll){
		jTetrisList.setSelectedValue(anObject, shouldScroll);
	}
	
	public void setSelectedValue(Object value){
		setSelectedValue(value, true);
	}
	
	public void setText(Object value){
		setSelectedValue(value);
	}
	
	public Object getText(){
		return getSelectedValue();
	}
	
	public Object getSelectedValue(){
		return jTetrisList.getSelectedValue();
	}
	
	@Deprecated
	public Object[] getSelectedValues(){
		return jTetrisList.getSelectedValues();
	}
	
	public List<String> getSelectedValuesList(){
		return jTetrisList.getSelectedValuesList();
	}
	
	public void setSelectedIndex(int index){
		jTetrisList.setSelectedIndex(index);
	}
	
	public int getSelectedIndex(){
		return jTetrisList.getSelectedIndex();
	}
	
	public int[] getSelectedIndices(){
		return jTetrisList.getSelectedIndices();
	}
	
	public void setSelectionMode(int selectionMode){
		jTetrisList.setSelectionMode(selectionMode);
	}
	
	public int getSelectionMode(){
		return jTetrisList.getSelectionMode();
	}
	
	public void setModel(ListModel<String> model){
		jTetrisList.setModel(model);
	}
	
	public ListModel<String> getModel(){
		return jTetrisList.getModel();
	}
	
	public void setValues(String[] values){
		jTetrisList.setValues(values);
	}
	
	public String[] getValues(){
		return jTetrisList.getValues();
	}
	
	public void setImages(ImageIcon[] images){
		jTetrisList.setImages(images);
	}
	
	public ImageIcon[] getImages(){
		return jTetrisList.getImages();
	}
	
	@Override
	public void setToolTipText(String text){
		super.setToolTipText(text);
		if(jTetrisList!=null){
			jTetrisList.setToolTipText(text);
		}
	}
	
	@Override
	public void setFont(Font font){
		super.setFont(font);
		if(jTetrisList!=null){
			jTetrisList.setFont(font);
		}
	}
	
	@Override
	public void setBackground(Color color){
		super.setBackground(color);
		if(jTetrisList!=null){
			jTetrisList.setBackground(color);
		}
	}
	
	@Override
	public void setForeground(Color color){
		super.setForeground(color);
		if(jTetrisList!=null){
			jTetrisList.setForeground(color);
		}
	}
	
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		if(jTetrisList!=null){
			jTetrisList.setEnabled(enabled);
		}
	}
	
	@Override
	public void setCursor(Cursor cursor){
		super.setCursor(cursor);
		if(jTetrisList!=null){
			jTetrisList.setCursor(cursor);
		}
	}
	
	
	@Override
	public boolean hasFocus(){
		if(jTetrisList!=null){
			return jTetrisList.hasFocus();
		}else{
			return super.hasFocus();
		}
	}
	
	@Override
	public void requestFocus(){
		if(jTetrisList!=null){
			jTetrisList.requestFocus();
		}else{
			super.requestFocus();
		}
	}
	
	@Override
	public void addKeyListener(KeyListener keyListener){
		super.addKeyListener(keyListener);
		if(jTetrisList!=null){
			jTetrisList.addKeyListener(keyListener);
		}
	}
	
	@Override
	public void addMouseListener(MouseListener mouseListener){
		super.addMouseListener(mouseListener);
		if(jTetrisList!=null){
			jTetrisList.addMouseListener(mouseListener);
		}
	}
	
	@Override
	public void addMouseMotionListener(MouseMotionListener mouseMotionListener){
		super.addMouseMotionListener(mouseMotionListener);
		if(jTetrisList!=null){
			jTetrisList.addMouseMotionListener(mouseMotionListener);
		}
	}
	
	@Override
	public void addMouseWheelListener(MouseWheelListener mouseWheelListener){
		super.addMouseWheelListener(mouseWheelListener);
		if(jTetrisList!=null){
			jTetrisList.addMouseWheelListener(mouseWheelListener);
		}
	}
	
	@Override
	public void addFocusListener(FocusListener focusListener){
		super.addFocusListener(focusListener);
		if(jTetrisList!=null){
			jTetrisList.addFocusListener(focusListener);
		}
	}
}
