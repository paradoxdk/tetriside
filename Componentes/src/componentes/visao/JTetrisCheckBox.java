package componentes.visao;

import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class JTetrisCheckBox extends JCheckBox {
	private String valor;
	
	public JTetrisCheckBox(){
		super();
	}
	
	public JTetrisCheckBox(String texto){
		super(texto);
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public void setTitle(String text){
		super.setText(text);
	}
	
	public String getTitle(){
		return super.getText();
	}
	
	public void setSelectedValue(String val){
		if(val.equals(getValor())){
			setSelected(true);
		}else{
			setSelected(false);
		}
	}
	
	public String getSelectedValue(){
		if(isSelected()){
			return getValor();
		}else{
			return "";
		}
	}
}
