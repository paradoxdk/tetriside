package componentes.visao;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.text.Document;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class JTetrisDateField extends JDateChooser{
	private Component component;
	private Document document;
	
	public JTetrisDateField(){
		super("dd/MM/yyyy", "##/##/####", '_');
		Component[] components = getComponents();
		for (int i = 0; i < components.length; i++) {
			if(components[i] instanceof JTextField){
				component=components[i];
				document = ((JTextField)component).getDocument();
				components[i].addKeyListener(new KeyAdapter() {
					public void keyReleased(KeyEvent arg0){
						if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
							component.transferFocus();
						}
					}
				});
				break;
			}
		}
	}
	
	public JTetrisDateField(String data){
		this();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try{
			setDate(dateFormat.parse(data));
		}catch(Exception exc){
			setDate(new Date());
		}
	}
	
	//Preenche a data
	public void setText(String data){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try{
			setDate(dateFormat.parse(data));
		}catch(Exception exc){
			setDate(new Date());
		}
	}
	
	//Retorna a data no formato americano
	public String getText(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try{
			return dateFormat.format(getDate());
		}catch(Exception exc){
			return dateFormat.format(new Date());
		}
	}
	
	//Retorna o elemento document
	public Document getDocument() {
		return document;
	}
	
	public void setEditable(boolean editable){
		((JTextField)component).setEditable(editable);
	}
	
	public boolean isEditable(){
		return ((JTextField)component).isEditable();
	}
	
	//Define alinhamento horizontal
	public void setHorizontalAlignment(int horizontalAlignment){
		Component[] components = getComponents();
		for (int i = 0; i < components.length; i++) {
			if(components[i] instanceof JTextField){
				((JTextField)components[i]).setHorizontalAlignment(horizontalAlignment);
				break;
			}
		}
	}
}
