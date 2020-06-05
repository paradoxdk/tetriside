package componentes.visao;


import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import componentes.modelo.Mascara;

@SuppressWarnings("serial")
public class JTetrisTextField extends JFormattedTextField implements FocusListener, KeyListener{
	public static final int MASCARA_MAX_LENGTH=0, MASCARA_MAIUSCULO=1, MASCARA_CEP=2, MASCARA_CNPJ=3, MASCARA_TELEFONE=4, 
			MASCARA_INSCRICAO_ESTADUAL=5, MASCARA_RG=6, MASCARA_CPF=7, MASCARA_NUMERO_INTEIRO=8, MASCARA_DECIMAL=9,
			MASCARA_DATA=10, MASCARA_HORA=11, MASCARA_SEM_ESPACOS=12;

	private int mascara, maxLength;

	public JTetrisTextField(){
		super();
		addFocusListener(this);
		addKeyListener(this);
		mascara=-1;
		maxLength=30;
		setDisabledTextColor(Color.BLACK);
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public int getMascara() {
		return mascara;
	}

	public int getMask(){
		return mascara;
	}

	public void setMascara(int mascara) {
		setMask(mascara);
	}

	public void setMask(int mask){
		this.mascara = mask;

		try{
			if(getMascara() == MASCARA_CEP){
				setFormatterFactory(new DefaultFormatterFactory( new MaskFormatter("#####-###")));
			}else if(getMascara() == MASCARA_CNPJ){
				setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##.###.###/####-##")));
			}else if(getMascara() == MASCARA_CPF){
				setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("###.###.###-##")));
			}else if(getMascara() == MASCARA_HORA){
				setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("##:##:##")));
			}else if(getMascara() == MASCARA_TELEFONE){
				setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("(##)####-####")));
			}
			
			setFocusLostBehavior(JFormattedTextField.COMMIT);
			setValue(null);
		}catch(Exception exc){
			exc.printStackTrace();
		}
	}

	public void focusGained(FocusEvent f){
		selectAll();
	}

	public void focusLost(FocusEvent f){
		if(getMascara() == MASCARA_DECIMAL){
			try{
				setText(Mascara.filtroDecimalCasas(""+Double.parseDouble(getText()), 2));
			}catch(Exception exc){
				setText("0.00");
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(getMascara() == MASCARA_DECIMAL){
			Mascara.mascaraDecimal(this, arg0, 15);
		}else if(getMascara() == MASCARA_INSCRICAO_ESTADUAL){
			Mascara.mascaraInscricaoEstadual(this, arg0, 20);
		}else if(getMascara() == MASCARA_MAIUSCULO){
			Mascara.mascaraMaiusculo(this, arg0, getMaxLength());
		}else if(getMascara() == MASCARA_MAX_LENGTH){
			Mascara.mascaraMax(this, arg0, getMaxLength());
		}else if(getMascara() == MASCARA_NUMERO_INTEIRO){
			Mascara.mascaraNumero(this, arg0, 11);
		}else if(getMascara() == MASCARA_RG){
			Mascara.mascaraRG(this, arg0, 20);
		}else if(getMascara() == MASCARA_SEM_ESPACOS){
			Mascara.mascaraSemEspacos(this, arg0);
		}else{
			if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
				this.transferFocus();
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}

