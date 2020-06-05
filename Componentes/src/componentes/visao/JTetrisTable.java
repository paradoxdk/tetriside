package componentes.visao;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.util.Collections;
import java.util.Vector;
import java.util.Comparator;

@SuppressWarnings("serial")
public class JTetrisTable extends JScrollPane {
	private JTable jTable;
	private String[] titulos;
	private String[] colunas;
	private String tabela;
	private int[] tamanhos;

	public JTetrisTable(String[] titulos, String[] colunas, String tabela){
		super();
		this.titulos=titulos;
		preencherTabela(null);

		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		jTable.getTableHeader().addMouseListener(new MouseAdapter() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void mouseClicked(MouseEvent arg0){
				JTableHeader header = (JTableHeader)arg0.getSource();  
				int colIndex = header.columnAtPoint(arg0.getPoint());
				
				DefaultTableModel modelo = (DefaultTableModel)jTable.getModel();
				Vector data = modelo.getDataVector();
				Collections.sort(data, new ColumnSorter(colIndex, true));
				modelo.fireTableStructureChanged();
				
				if(tamanhos!=null){
					for (int i = 0; i < tamanhos.length; i++) {
						jTable.getColumnModel().getColumn(i).setPreferredWidth(tamanhos[i]);
					}
				}
			}
		});
		
		ComponentAdapter componentAdapter = new ComponentAdapter() {
			public void componentResized(ComponentEvent arg0){
				if(getjTable()!=null){
					setViewportView(getjTable());
				}
			}
		};
		
		addComponentListener(componentAdapter);

	}

	public JTetrisTable(){
		this(new String[0], new String[0], "");
	}
	
	public void fillTable(ResultSet resultSet){
		preencherTabela(resultSet);
	}

	public void preencherTabela(ResultSet resultSet){
		try {
			tamanhos = new int[titulos.length];
			DefaultTableModel defaultTableModel = new DefaultTableModel();
			for (int i = 0; i < titulos.length; i++) {
				defaultTableModel.addColumn(titulos[i]);
				tamanhos[i] = titulos[i].length()*7;
			}

			if(resultSet!=null){
				resultSet.next();
				if(resultSet.getRow() > 0){
					String[] linha;
					do{
						linha = new String[titulos.length];
						for (int i = 0; i < titulos.length; i++) {
							if(resultSet.getString(colunas[i])!=null){
								linha[i] = resultSet.getString(colunas[i]);
							}else{
								linha[i] = "";
							}
							if(tamanhos[i]<linha[i].length()*10){
								tamanhos[i]=linha[i].length()*10;
							}

						}
						defaultTableModel.addRow(linha);
					}while(resultSet.next());
				}
			}

			MouseListener[] mouseListeners = null, mouseListenersHeader = null;
			KeyListener[] keyListeners = null;
			if(jTable!=null){
				mouseListeners = jTable.getMouseListeners();
				mouseListenersHeader = jTable.getTableHeader().getMouseListeners();
				keyListeners = jTable.getKeyListeners();
			}
			
			jTable= new JTable(defaultTableModel){
				public boolean isCellEditable(int rowIndex, int vColIndex){
					return false;
				}
			};

			if(mouseListeners!=null){
				for (int i = 0; i < mouseListeners.length; i++) {
					jTable.addMouseListener(mouseListeners[i]);
				}
			}
			
			if(mouseListenersHeader!=null){
				for (int i = 0; i < mouseListenersHeader.length; i++) {
					jTable.getTableHeader().addMouseListener(mouseListenersHeader[i]);
				}
			}

			if(keyListeners!=null){
				for (int i = 0; i < keyListeners.length; i++) {
					jTable.addKeyListener(keyListeners[i]);
				}
			}

			setViewportView(jTable);

			jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTable.changeSelection(0, 0, false, false);

			for (int i = 0; i < tamanhos.length; i++) {
				if(tamanhos[i]< 50){
					tamanhos[i]=50;
				}else if(tamanhos[i]>300){
					tamanhos[i]=300;
				}
				jTable.getColumnModel().getColumn(i).setPreferredWidth(tamanhos[i]);
			}

		} catch (Exception exc) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Error:\n"+exc);
			exc.printStackTrace();
		}
	}

	public void inserirLinhas(ArrayList<String []> linhas){
		int[] tamanhos = new int[titulos.length];
		DefaultTableModel defaultTableModel = new DefaultTableModel();
		for (int i = 0; i < titulos.length; i++) {
			defaultTableModel.addColumn(titulos[i]);
			tamanhos[i] = titulos[i].length()*7;
		}


		for (int i = 0; i < linhas.size(); i++) {

			String[] linha=linhas.get(i);
			
			for (int j = 0; j < titulos.length; j++) {
				
				if(tamanhos[j]<linha[j].length()*10){
					tamanhos[j]=linha[j].length()*10;
				}

			}

			defaultTableModel.addRow(linha);

		}


		MouseListener[] mouseListeners = null;
		if(jTable!=null){
			mouseListeners = jTable.getMouseListeners();
		}
		
		MouseMotionListener[] mouseMotionListeners = null;
		if(jTable!=null){
			mouseMotionListeners = jTable.getMouseMotionListeners();
		}
		
		MouseWheelListener[] mouseWheelListeners = null;
		if(jTable!=null){
			mouseWheelListeners = jTable.getMouseWheelListeners();
		}

		KeyListener[] keyListeners = null;
		if(jTable!=null){
			keyListeners = jTable.getKeyListeners();
		}
		
		FocusListener[] focusListeners = null;
		if(jTable!=null){
			focusListeners = jTable.getFocusListeners();
		}

		jTable= new JTable(defaultTableModel){
			public boolean isCellEditable(int rowIndex, int vColIndex){
				return false;
			}
		};

		if(mouseListeners!=null){
			for (int i = 0; i < mouseListeners.length; i++) {
				jTable.addMouseListener(mouseListeners[i]);
			}
		}
		
		if(mouseMotionListeners!=null){
			for (int i = 0; i < mouseMotionListeners.length; i++) {
				jTable.addMouseMotionListener(mouseMotionListeners[i]);
			}
		}
		
		if(mouseWheelListeners!=null){
			for (int i = 0; i < mouseWheelListeners.length; i++) {
				jTable.addMouseWheelListener(mouseWheelListeners[i]);
			}
		}

		if(keyListeners!=null){
			for (int i = 0; i < keyListeners.length; i++) {
				jTable.addKeyListener(keyListeners[i]);
			}
		}
		
		if(focusListeners!=null){
			for (int i = 0; i < focusListeners.length; i++) {
				jTable.addFocusListener(focusListeners[i]);
			}
		}
		
		jTable.setToolTipText(getToolTipText());

		setViewportView(jTable);

		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTable.changeSelection(0, 0, false, false);

		for (int i = 0; i < tamanhos.length; i++) {
			if(tamanhos[i]< 60){
				tamanhos[i]=60;
			}else if(tamanhos[i]>300){
				tamanhos[i]=300;
			}
			jTable.getColumnModel().getColumn(i).setPreferredWidth(tamanhos[i]);
		}
	}
	
	@Override
	public void setToolTipText(String text){
		super.setToolTipText(text);
		if(jTable!=null){
			jTable.setToolTipText(text);
		}
	}
	
	@Override
	public void requestFocus(){
		super.requestFocus();
		if(jTable!=null){
			jTable.requestFocus();
		}
	}
	
	@Override
	public boolean hasFocus(){
		if(jTable!=null){
			return jTable.hasFocus();
		}else{
			return super.hasFocus();
		}
	}
	
	@Override
	public void setFont(Font font){
		super.setFont(font);
		if(jTable!=null){
			jTable.setFont(font);
		}
	}
	
	@Override
	public void setBackground(Color color){
		super.setBackground(color);
		if(jTable!=null){
			jTable.setBackground(color);
		}
	}
	
	@Override
	public void setForeground(Color color){
		super.setForeground(color);
		if(jTable!=null){
			jTable.setForeground(color);
		}
	}

	public String getValorSelecionadoColuna(String coluna){
		String retorno = null;
		if(getSelectedRow() >= 0){
			int col=0;
			for (int i = 0; i < colunas.length; i++) {
				if(colunas[i].equals(coluna)){
					col=i;
					break;
				}
			}

			retorno = ""+jTable.getValueAt(jTable.getSelectedRow(), col);
		}

		return retorno;
	}
	
	public String getSelectedValue(String column){
		return getValorSelecionadoColuna(column);
	}

	public int getSelectedRow(){

		if(jTable.getRowCount() > 0){
			return jTable.getSelectedRow();
		}else{
			return -1;
		}

	}
	
	public void setSelectedRow(int pos){
		jTable.changeSelection(pos, 0, false, false);
	}
	
	public int getRowCount(){
		return jTable.getRowCount();
	}

	public JTable getjTable() {
		return jTable;
	}

	public String[] getTitulos() {
		return titulos;
	}

	public String[] getColunas() {
		return colunas;
	}
	
	public String[] getColumns(){
		return getColunas();
	}

	public String getTabela() {
		return tabela;
	}
	
	public String getTable(){
		return getTabela();
	}

	public void setTabela(String tabela) {
		this.tabela = tabela;
	}
	
	public void setTable(String table){
		setTable(table);
	}

	public void setTitulos(String[] titulos) {
		this.titulos = titulos;
	}
	
	public void setTitles(String[] titles){
		setTitulos(titles);
	}
	
	public String[] getTitles(){
		return getTitulos();
	}

	public void setColunas(String[] colunas) {
		this.colunas = colunas;
	}

	public void setColumns(String[] columns) {
		setColunas(columns);
	}

}


@SuppressWarnings("rawtypes")
class ColumnSorter implements Comparator {
        int colIndex;
        boolean ascending;
        ColumnSorter(int colIndex, boolean ascending) {
            this.colIndex = colIndex;
           this.ascending = ascending;
        }
        @SuppressWarnings("unchecked")
		public int compare(Object a, Object b) {
            Vector v1 = (Vector)a;
            Vector v2 = (Vector)b;
            Object o1 = v1.get(colIndex);
            Object o2 = v2.get(colIndex);                if (o1 instanceof String && ((String)o1).length() == 0) {
                o1 = null;
            }
            if (o2 instanceof String && ((String)o2).length() == 0) {
                o2 = null;
            }                if (o1 == null && o2 == null) {
                return 0;
            } else if (o1 == null) {
               return 1;
            } else if (o2 == null) {
                return -1;
            } else if (o1 instanceof Comparable) {
                if (ascending) {
                    return ((Comparable)o1).compareTo(o2);
                } else {
                    return ((Comparable)o2).compareTo(o1);
                }
            } else {
                if (ascending) {
                    return o1.toString().compareTo(o2.toString());
                } else {
                    return o2.toString().compareTo(o1.toString());
                }
            }
        }
}
