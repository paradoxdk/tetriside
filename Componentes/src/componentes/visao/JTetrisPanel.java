package componentes.visao;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class JTetrisPanel extends JPanel {
	private ImageIcon icon;
	private String text;
	public JTetrisPanel(){
		icon = new ImageIcon(getClass().getResource("/componentes/imagem/bgpn.png"));
		text=null;
		setLayout(null);
		setBackground(new Color(240, 240, 240));
	}
	
	public JTetrisPanel(ImageIcon icon){
		this();
		this.icon=icon;
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(icon!=null){
			g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
		}
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		setBorder(BorderFactory.createTitledBorder(text));
	}
	
}
