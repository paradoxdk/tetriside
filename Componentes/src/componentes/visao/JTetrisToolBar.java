package componentes.visao;

import javax.swing.JToolBar;
import javax.swing.ImageIcon;
import java.awt.Color;

import java.awt.Graphics;

@SuppressWarnings("serial")
public class JTetrisToolBar extends JToolBar{
	private ImageIcon icon;
	public JTetrisToolBar(){
		icon = null;
		setBackground(new Color(240, 240, 240));
	}
	
	public JTetrisToolBar(ImageIcon icon){
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
	
}
