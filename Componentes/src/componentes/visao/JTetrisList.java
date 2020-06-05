package componentes.visao;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

@SuppressWarnings("serial")
public class JTetrisList extends JList<String> {
	private String[] valores;
	private ImageIcon[] imagens;

	public JTetrisList(){
		super();
		adicionarPropriedades();
	}

	public JTetrisList(String[] valores, ImageIcon[] imagens){
		this.valores=valores;
		this.imagens=imagens;
		adicionarPropriedades();
		adicionarItens();
	}

	public JTetrisList(ImageIcon imagem){
		super();
		adicionarPropriedades();
		imagens = new ImageIcon[1];
		imagens[0]=imagem;

	}

	@SuppressWarnings("unchecked")
	private void adicionarPropriedades(){
		setCellRenderer(new MyCellRenderer());

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

	public String[] getValores() {
		return valores;
	}
	
	public String[] getValues(){
		return getValores();
	}

	public ImageIcon[] getImagens() {
		return imagens;
	}
	
	public ImageIcon[] getImages(){
		return getImagens();
	}

	public void setValores(String[] valores) {
		this.valores = valores;
		adicionarItens();
	}
	
	public void setValues(String[] values){
		setValores(values);
	}

	public void setImagens(ImageIcon[] imagens) {
		this.imagens = imagens;
	}
	
	public void setImages(ImageIcon[] images){
		setImagens(images);
	}

	private void adicionarItens(){
		DefaultListModel<String> listModel = new DefaultListModel<String>();

		if(valores!=null){
			for (int i = 0; i < valores.length; i++) {
				listModel.addElement(valores[i]);
			}
		}

		setModel(listModel);
	}

}

//Modelo de renderizacao das celulas da lista
@SuppressWarnings({ "rawtypes", "serial" })
class MyCellRenderer extends JLabel implements ListCellRenderer {
	// This is the only method defined by ListCellRenderer.
	// We just reconfigure the JLabel each time we're called.
	
	public MyCellRenderer(){
		super();
		
	}
	
	public Component getListCellRendererComponent(
			JList list,
			Object value,            // value to display
			int index,               // cell index
			boolean isSelected,      // is the cell selected
			boolean cellHasFocus)    // the list and the cell have the focus
	{
		setText(""+value);


		JTetrisList lista = (JTetrisList)list;
		ImageIcon[] imagens = lista.getImagens();
		if(imagens != null){
			if(imagens.length > index){
				//Adiciona icone ao item

				try {
					this.setIcon(imagens[index]);
				} catch (Exception exc) {
					// TODO: handle exception
				}

			}
		}

		if(isSelected){
			setForeground(lista.getSelectionForeground());
			setBackground(lista.getSelectionBackground());
		}else{
			setForeground(lista.getForeground());
			setBackground(lista.getBackground());
		}

		return this;
	}
	
	@Override
	public void paintComponent(Graphics g){
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		super.paintComponent(g);
	}
}




