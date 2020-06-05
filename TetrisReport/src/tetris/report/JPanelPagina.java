package tetris.report;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class JPanelPagina extends JPanel {
	private Printable printable;
	private PageFormat pageFormat;
	private int pageIndex;

	public JPanelPagina(){
		super();
		setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		setBackground(Color.WHITE);
		
		setLayout(null);
	}

	public void atualizarPagina(Printable printable, PageFormat pageFormat, int pageIndex){
		this.printable=printable;
		this.pageFormat=pageFormat;
		this.pageIndex=pageIndex;
		
		repaint();
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(printable!=null){
			try {
				
				BufferedImage bufferedImage = new BufferedImage((int)pageFormat.getWidth(), (int)pageFormat.getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics bufferedImageGraphics = bufferedImage.getGraphics();
				
				bufferedImageGraphics.setColor(Color.WHITE);
				bufferedImageGraphics.fillRect(0, 0, (int)pageFormat.getWidth(), (int)pageFormat.getHeight());
				
				printable.print(bufferedImageGraphics, pageFormat, pageIndex);
				
				g.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), null);
			} catch (PrinterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Printable getPrintable() {
		return printable;
	}

}
