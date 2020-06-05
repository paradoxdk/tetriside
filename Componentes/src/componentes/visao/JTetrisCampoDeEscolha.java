package componentes.visao;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class JTetrisCampoDeEscolha extends JPanel {
	private String text;
	private ArrayList<JRadioButton> arrayListJRadioButtons;
	private ButtonGroup buttonGroup;

	public JTetrisCampoDeEscolha(String text) {
		super();
		this.text = text;
		arrayListJRadioButtons = new ArrayList<JRadioButton>();
		buttonGroup = new ButtonGroup();
		setLayout(null);

		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent c){
				dimensionarComponentes();
			}
		});
		setText(text);

	}

	@Override
	public void setFont(Font font){
		super.setFont(font);
		if(arrayListJRadioButtons!=null){
			for (int i = 0; i < arrayListJRadioButtons.size(); i++) {
				arrayListJRadioButtons.get(i).setFont(font);
			}
		}
	}

	@Override
	public void setBackground(Color color){
		super.setBackground(color);
		if(arrayListJRadioButtons!=null){
			for (int i = 0; i < arrayListJRadioButtons.size(); i++) {
				arrayListJRadioButtons.get(i).setBackground(color);
			}
		}
	}

	@Override
	public void setForeground(Color color){
		super.setForeground(color);
		if(arrayListJRadioButtons!=null){
			for (int i = 0; i < arrayListJRadioButtons.size(); i++) {
				arrayListJRadioButtons.get(i).setForeground(color);
			}
		}
	}

	public void addActionListener(ActionListener actionListener){
		for (int i = 0; i < arrayListJRadioButtons.size(); i++) {
			arrayListJRadioButtons.get(i).addActionListener(actionListener);
		}
	}

	public void addFocusListener(FocusAdapter focusAdapter){
		for (int i = 0; i < arrayListJRadioButtons.size(); i++) {
			arrayListJRadioButtons.get(i).addFocusListener(focusAdapter);
		}
	}

	public void adicionarJRadioButton(JRadioButton jRadioButton){
		arrayListJRadioButtons.add(jRadioButton);
		buttonGroup.add(jRadioButton);
		add(jRadioButton);
		dimensionarComponentes();
	}

	public void removerJRadioButton(JRadioButton jRadioButton){
		arrayListJRadioButtons.remove(jRadioButton);
		buttonGroup.remove(jRadioButton);
		remove(jRadioButton);
		dimensionarComponentes();
	}

	public void dimensionarComponentes(){
		for (int i = 0; i < arrayListJRadioButtons.size(); i++) {
			arrayListJRadioButtons.get(i).setBounds(5, 15+(i*20), getWidth() - 10, 20);
		}

	}

	public String getSelectedValue(){
		String retorno="";

		for (int i = 0; i < arrayListJRadioButtons.size(); i++) {
			if(arrayListJRadioButtons.get(i).getModel().equals(buttonGroup.getSelection())){
				retorno=arrayListJRadioButtons.get(i).getText();
			}
		}
		return retorno;
	}

	public int getSelectedIndex(){
		int retorno=-1;

		for (int i = 0; i < arrayListJRadioButtons.size(); i++) {
			if(arrayListJRadioButtons.get(i).getModel().equals(buttonGroup.getSelection())){
				retorno=i;
			}
		}
		return retorno;
	}
	
	public void setSelectedIndex(int index){
		for (int i = 0; i < arrayListJRadioButtons.size(); i++) {
			if(i==index){
				arrayListJRadioButtons.get(i).setSelected(true);
			}else{
				arrayListJRadioButtons.get(i).setSelected(false);
			}
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		setBorder(BorderFactory.createTitledBorder(text));
	}

	public ArrayList<JRadioButton> getArrayListJRadioButtons() {
		return arrayListJRadioButtons;
	}

	public ButtonGroup getButtonGroup() {
		return buttonGroup;
	}



}
