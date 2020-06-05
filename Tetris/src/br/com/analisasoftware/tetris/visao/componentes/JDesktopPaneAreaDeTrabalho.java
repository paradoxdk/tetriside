/*
 *Área de trabalho do TetrisIDE onde se manipula uma janela graficamente
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 25 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.componentes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import br.com.analisasoftware.tetris.modelo.Arquivo;
import br.com.analisasoftware.tetris.visao.janelas.JFramePrincipal;
import br.com.analisasoftware.tetris.visao.janelas.JInternalFrameJanela;

import javax.swing.UIManager;

import componentes.visao.JTetrisButton;
import componentes.visao.JTetrisCampoDeEscolha;
import componentes.visao.JTetrisComboBox;
import componentes.visao.JTetrisList;
import componentes.visao.JTetrisPanel;
import componentes.visao.JTetrisTable;
import componentes.visao.JTetrisTextField;
import componentes.visao.JTetrisToolBar;
import tetris.modelo.componentes.Atributo;
import tetris.modelo.componentes.Componente;
import tetris.modelo.componentes.Janela;
import tetris.modelo.componentes.RelacionamentoComponente;

@SuppressWarnings("serial")
public class JDesktopPaneAreaDeTrabalho extends JDesktopPane implements MouseListener, MouseMotionListener, KeyListener{
	//Aponta para janela em edição
	private Janela janela;
	//Aponta para a instância da JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Janela gráfica para edição na área de trabalho
	private JInternalFrameJanela jInternalFrame;
	//Constantes e variáveis de auxílio para a manipulação de objetos através do mouse
	private static final int NORTE=0, SUL=1, LESTE=2, OESTE=3, NORTE_LESTE=4, NORTE_OESTE=5, SUL_LESTE=6, SUL_OESTE=7, CENTRO=8;
	private int posXMouse, posYMouse, posMouse;
	private boolean dragged;
	private String direcao;

	//Cria objeto que monitora teclas pressionadas
	private HashMap<Integer, Boolean> hashMapKeyPool;

	public JDesktopPaneAreaDeTrabalho(JFramePrincipal jFramePrincipal) {
		super();
		direcao="";
		this.jFramePrincipal=jFramePrincipal;
		setBackground(UIManager.getColor("Panel.background"));
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		posXMouse=0;
		posYMouse=0;
		posMouse=CENTRO;

		dragged=false;

		//Inicializa componente que monitora teclas pressionadas
		hashMapKeyPool = new HashMap<Integer, Boolean>();
	}

	//Abre uma janela na area de trabalho e retorna true, caso sucesso, e false, caso contrário
	public boolean abrirJanela(Janela janela){
		this.janela=janela;
		boolean retorno=false;
		
		getjFramePrincipal().getjSyntaxTextPaneCodigoFonteGerado().setText("");
		getjFramePrincipal().getjTabbedPaneAreaDeTrabalho().setSelectedIndex(0);
		atualizarJanela();
		
		retorno=true;

		return retorno;
	}

	//Copia componentes para area de transferencia
	public void copiarComponentes(){
		//Captura lista de components gráficos em foco na JInternalFrame em edição
		ArrayList<Component> arrayListComponentFoco = getjInternalFrame().getArrayListComponentFoco();
		ArrayList<Componente> arrayListComponentes = new ArrayList<Componente>();
		//Percorre os components e identifica seus relacionamentos com componentes do projeto em memória
		for (int i = 0; i < arrayListComponentFoco.size(); i++) {
			Component component = arrayListComponentFoco.get(i);
			for (int k = 0; k < jInternalFrame.getArrayListRelacionamentoComponentes().size(); k++) {
				if(jInternalFrame.getArrayListRelacionamentoComponentes().get(k).getComponent().equals(component)){
					arrayListComponentes.add(jInternalFrame.getArrayListRelacionamentoComponentes().get(k).getComponente());
					break;
				}
			}
		}
		//Bufferiza componentes
		getjInternalFrame().getjFramePrincipal().getAreaDeTransferencia().bufferizarComponente(arrayListComponentes);
	}

	//Move componentes para a area de transferencia
	public void moverComponentes(){
		copiarComponentes();

		//Remove os componentes da origem
		excluirComponentes();

		atualizarComponentes(getJanela().getArrayListComponentes());
	}

	//Exclui componentes selecionados
	public void excluirComponentes(){
		//Adiciona estado de modificação da janela
		jFramePrincipal.getjDesktopPaneAreaDeTrabalho().getJanela().adicionaEstadoDeJanela();
		//captura a lista de components em foco na JInternalFrame e a lista de relacionamentos
		ArrayList<RelacionamentoComponente> arrayListRelacionamentoComponente = getjInternalFrame().getArrayListRelacionamentoComponentes();
		ArrayList<Component> arrayListComponentFoco = getjInternalFrame().getArrayListComponentFoco();
		//Percore a lista, identifica os relacionamentos e remove os componentes selecionados
		for(int x=0; x<arrayListComponentFoco.size(); x++){
			for (int i = 0; i < arrayListRelacionamentoComponente.size(); i++) {
				if(arrayListRelacionamentoComponente.get(i).getComponent().equals(arrayListComponentFoco.get(x))){
					ArrayList<Componente> arrayListComponentesFilhos = jFramePrincipal.getAreaDeTransferencia().bufferizaComponentesFilhos(arrayListRelacionamentoComponente.get(i).getComponente().getNome());

					for (int j = 0; j < arrayListComponentesFilhos.size(); j++) {
						janela.getArrayListComponentes().remove(arrayListComponentesFilhos.get(j));
					}

					janela.getArrayListComponentes().remove(arrayListRelacionamentoComponente.get(i).getComponente());

					atualizarJanela();
					break;
				}
			}
		}
	}

	//Cola componentes
	public void colarComponentes(){
		//Adiciona estado de modificação da janela
		jFramePrincipal.getjDesktopPaneAreaDeTrabalho().getJanela().adicionaEstadoDeJanela();

		//Criando e iniciando vetores com os componentes
		ArrayList<Componente> arrayListComponenteBuffer = getjInternalFrame().getjFramePrincipal().getAreaDeTransferencia().getArrayListComponenteBuffer();
		ArrayList<Componente> arrayListComponentes = new ArrayList<Componente>();
		ArrayList<Componente> arrayListComponentesJanela = getJanela().getArrayListComponentes();

		//Component pai
		Component componentPai=null;

		//Passando os componentes do Buffer para uma variavel. Faz-se isso para o buffer nao ser alterado
		for (int i = 0; i < arrayListComponenteBuffer.size(); i++) {
			arrayListComponentes.add(arrayListComponenteBuffer.get(i).clone());
		}	
		//Define o componente que vai conter o componente colado
		String pai=getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela().getNome();
		//Verifica se o componente em foco é um Panel ou ToolBar. Caso sim, seta como pai
		if((getjInternalFrame().getComponentFoco() instanceof JTetrisPanel) || (getjInternalFrame().getComponentFoco() instanceof JToolBar)){
			ArrayList<RelacionamentoComponente> arrayListRelacionamentoComponentes = getjInternalFrame().getArrayListRelacionamentoComponentes();
			for (int i = 0; i < arrayListRelacionamentoComponentes.size(); i++) {
				if(arrayListRelacionamentoComponentes.get(i).getComponent().equals(getjInternalFrame().getComponentFoco())){
					componentPai=arrayListRelacionamentoComponentes.get(i).getComponent();
					pai = arrayListRelacionamentoComponentes.get(i).getComponente().getNome();
					break;
				}
			}
		}
		//Variável de auxílio para definir os nomes dos componentes
		ArrayList<String> arrayListNomeComponentesFoco = new ArrayList<String>();
		//Percorre a lista de componentes
		for (int i = 0; i < arrayListComponentes.size(); i++) {
			//Variável de auxílio
			Componente componente = arrayListComponentes.get(i);
			
			for (int j = 0; j < arrayListComponentesJanela.size(); j++) {
				//Caso já exista um componente com o nome do componente colado, modifica e modifica o nome do pai de seus componentes contidos
				if((arrayListComponentesJanela.get(j).getNome()).equals(componente.getNome())){
					for (int j2 = 0; j2 < arrayListComponentes.size(); j2++) {
						if(arrayListComponentes.get(j2).getAtributo("Pai").getValor().equals(componente.getNome())){
							arrayListComponentes.get(j2).mudarAtributo(new Atributo("Pai", "String", componente.getNome()+"_1"));
						}
					}
					componente.setNome(componente.getNome()+"_1");
					j=0;
				}
			}
			//Caso o atributo Pai for nulo, adiciona o componente focado como pai
			if(componente.getAtributo("Pai").getValor().equals("null")){
				arrayListNomeComponentesFoco.add(componente.getNome());
				componente.mudarAtributo(new Atributo("Pai", "String", pai));
			}
			//Adiciona o componente
			arrayListComponentesJanela.add(componente);


		}

		atualizarComponentes(getJanela().getArrayListComponentes());

		//Selecionando os novos componentes copiados na janela
		ArrayList<RelacionamentoComponente> arrayListRelacionamentoComponente = getjInternalFrame().getArrayListRelacionamentoComponentes();
		for (int j2 = 0; j2 < arrayListNomeComponentesFoco.size(); j2++) {
			for (int j = 0; j < arrayListRelacionamentoComponente.size(); j++) {
				if(arrayListRelacionamentoComponente.get(j).getComponente().getNome().equals(arrayListNomeComponentesFoco.get(j2))){
					if(j2==0){
						getjInternalFrame().setComponentFoco(arrayListRelacionamentoComponente.get(j).getComponent(), false);
					}else{
						getjInternalFrame().setComponentFoco(arrayListRelacionamentoComponente.get(j).getComponent(), true);
					}
					break;
				}
			}
		}

		if(componentPai!=null){
			componentPai.repaint();
		}
	}

	//Atualiza a janela e seus componentes
	public void atualizarJanela(){
		//Remove a janela existente na area de trabalho
		removeAll();

		//Capturando atributos
		int width=320, height=240, x=0, y=0, extendedState=0;
		String titulo="", iconeJanela="";
		Border border = null;
		Color background = null;
		Component componenteLocationRelativeTo = null;
		boolean closable=true;
		boolean iconifiable=true;
		boolean resizable=true;

		ArrayList<Atributo> arrayListAtributos = janela.getArrayListAtributos();

		for (int i = 0; i < arrayListAtributos.size(); i++) {
			switch(arrayListAtributos.get(i).getNome()){
			case "Title":
				titulo=arrayListAtributos.get(i).getValor();
				break;
			case "x":
				x=Integer.parseInt(arrayListAtributos.get(i).getValor());
				break;
			case "y":
				y=Integer.parseInt(arrayListAtributos.get(i).getValor());
				break;
			case "width":
				width=Integer.parseInt(arrayListAtributos.get(i).getValor());
				break;
			case "height":
				height=Integer.parseInt(arrayListAtributos.get(i).getValor());
				break;
			case "Border":
				int borda=Integer.parseInt(arrayListAtributos.get(i).getValor());
				if(borda==0){
					//Borda marcada
					border=BorderFactory.createEtchedBorder();
				}else if(borda==1){
					//Borda afundada
					border=new BevelBorder(BevelBorder.LOWERED, null, null, null, null);
				}else if(borda==2){
					//Borda Enraizada
					border=new BevelBorder(BevelBorder.RAISED, null, null, null, null);
				}
				break;
			case "Background":
				int red=Integer.parseInt(arrayListAtributos.get(i).getValor().substring(0, 3));
				int green=Integer.parseInt(arrayListAtributos.get(i).getValor().substring(5, 8));
				int blue=Integer.parseInt(arrayListAtributos.get(i).getValor().substring(10, 13));

				background = new Color(red, green, blue);
				break;
			case "ExtendedState":
				extendedState=Integer.parseInt(arrayListAtributos.get(i).getValor());
				break;
			case "LocationRelativeTo":
				componenteLocationRelativeTo=this;
				break;
			case "Closable":
				if(arrayListAtributos.get(i).getValor().equals("false")){
					closable=false;
				}
				break;

			case "Iconifiable":
				if(arrayListAtributos.get(i).getValor().equals("false")){
					iconifiable=false;
				}
				break;

			case "Resizable":
				if(arrayListAtributos.get(i).getValor().equals("false")){
					resizable=false;
				}
				break;

			case "Icon":
				iconeJanela=arrayListAtributos.get(i).getValor();

				break;
			}
		}

		//Criando a janela
		jInternalFrame = new JInternalFrameJanela(getjFramePrincipal());
		jInternalFrame.setTitle(titulo);
		if(extendedState!=6){
			jInternalFrame.setBounds(x, y, width, height);
		}else{
			jInternalFrame.setBounds(0, 0, getWidth(), getHeight());
		}
		if(border!=null){
			jInternalFrame.setBorder(border);
		}
		if(background!=null){
			jInternalFrame.getContentPane().setBackground(background);
		}
		if(componenteLocationRelativeTo!=null){
			jInternalFrame.setLocation((getjFramePrincipal().getjScrollPaneDesktopPaneAreaDeTrabalho().getWidth()/2)-(width/2), (getjFramePrincipal().getjScrollPaneDesktopPaneAreaDeTrabalho().getHeight()/2)-(height/2));
		}
		jInternalFrame.setClosable(closable);
		jInternalFrame.setResizable(resizable);
		jInternalFrame.setIconifiable(iconifiable);

		if((!closable) && (!resizable) && (!iconifiable)){
			((BasicInternalFrameUI)jInternalFrame.getUI()).setNorthPane(null);
			jInternalFrame.setBorder(null);
		}

		if(iconeJanela.equals("")==false){
			if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/"+iconeJanela)){
				jInternalFrame.setFrameIcon(new ImageIcon(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/"+iconeJanela));
			}
		}

		atualizarComponentes(janela.getArrayListComponentes());

		//Mostrando a janela
		add(jInternalFrame);
		jInternalFrame.getContentPane().setFocusable(true);
		jInternalFrame.getContentPane().addKeyListener(this);
		jInternalFrame.init();

		repaint();

		System.gc();
	}


	//Atualiza os componentes da janela

	public void atualizarComponentes(ArrayList<Componente> arrayListComponentes){
		jInternalFrame.getContentPane().removeAll();
		jInternalFrame.setArrayListRelacionamentoComponentes(new ArrayList<RelacionamentoComponente>());

		for (int i = 0; i < arrayListComponentes.size(); i++) {

			ArrayList<Atributo> arrayListAtributosComponente = arrayListComponentes.get(i).getArrayListAtributos();
			//System.out.println(arrayListComponentes.get(i).getTipo());
			switch(arrayListComponentes.get(i).getTipo()){

			case "Rotulo":
				JLabel jLabel = new JLabel();
				jLabel.addMouseListener(this);
				jLabel.addMouseMotionListener(this);

				jInternalFrame.getArrayListRelacionamentoComponentes().add(new RelacionamentoComponente(jLabel, arrayListComponentes.get(i)));

				//Capturando atributos
				int widthLabel=100, heightLabel=20, xLabel=0, yLabel=0, horizontalAlignmentLabel=JLabel.LEADING, verticalAlignmentLabel=0;
				String textoLabel="", iconeLabel="";
				Border borderLabel = null;
				Color backgroundLabel = null, foregroundLabel=null;

				for (int j = 0; j < arrayListAtributosComponente.size(); j++) {
					switch(arrayListAtributosComponente.get(j).getNome()){
					case "Text":
						textoLabel=arrayListAtributosComponente.get(j).getValor();
						break;
					case "x":
						xLabel=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "y":
						yLabel=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "width":
						widthLabel=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "height":
						heightLabel=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "Border":
						int borda=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						if(borda==0){
							//Borda marcada
							borderLabel=BorderFactory.createEtchedBorder();
						}else if(borda==1){
							//Borda afundada
							borderLabel=new BevelBorder(BevelBorder.LOWERED, null, null, null, null);
						}else if(borda==2){
							//Borda Enraizada
							borderLabel=new BevelBorder(BevelBorder.RAISED, null, null, null, null);
						}
						break;
					case "Background":
						int red=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int green=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blue=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						backgroundLabel = new Color(red, green, blue);
						break;

					case "Foreground":
						int redF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int greenF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blueF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						foregroundLabel = new Color(redF, greenF, blueF);
						break;

					case "Font":
						String[] fonte = arrayListAtributosComponente.get(j).getValor().split("/");
						jLabel.setFont(new Font(fonte[0], Integer.parseInt(fonte[2]), Integer.parseInt(fonte[1])));
						break;

					case "HorizontalAlignment":
						horizontalAlignmentLabel=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());

						break;

					case "VerticalAlignment":
						verticalAlignmentLabel=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());

						break;

					case "Icon":
						iconeLabel=arrayListAtributosComponente.get(j).getValor();

						break;
					}
				}

				jLabel.setText(textoLabel);
				jLabel.setBounds(xLabel, yLabel, widthLabel, heightLabel);
				jLabel.setHorizontalAlignment(horizontalAlignmentLabel);
				jLabel.setVerticalAlignment(verticalAlignmentLabel);
				if(borderLabel!=null){
					jLabel.setBorder(borderLabel);
				}
				if(backgroundLabel!=null){
					jLabel.setBackground(backgroundLabel);
				}
				if(foregroundLabel!=null){
					jLabel.setForeground(foregroundLabel);
				}
				if(iconeLabel.equals("")==false){
					if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/"+iconeLabel)){
						jLabel.setIcon(new ImageIcon(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/"+iconeLabel));
					}
				}

				jInternalFrame.getContentPane().add(jLabel);

				break;

			case "Campo de texto":
				JTetrisTextField jTetrisTextField = new JTetrisTextField();

				jTetrisTextField.addMouseListener(this);
				jTetrisTextField.addMouseMotionListener(this);

				jInternalFrame.getArrayListRelacionamentoComponentes().add(new RelacionamentoComponente(jTetrisTextField, arrayListComponentes.get(i)));

				jTetrisTextField.setCursor(getCursor());

				jTetrisTextField.setFocusable(false);

				//Capturando atributos
				int widthTextField=100, heightTextField=20, xTextField=0, yTextField=0, horizontalAlignmentTextField=JTetrisTextField.LEADING;
				String textoTextField="";
				Border borderTextField = null;
				Color backgroundTextField = null, foregroundTextField=null;
				boolean enabled=true, editable=true;

				for (int j = 0; j < arrayListAtributosComponente.size(); j++) {
					switch(arrayListAtributosComponente.get(j).getNome()){
					case "Text":
						textoTextField=arrayListAtributosComponente.get(j).getValor();
						break;
					case "x":
						xTextField=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "y":
						yTextField=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "width":
						widthTextField=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "height":
						heightTextField=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "Border":
						int borda=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						if(borda==0){
							//Borda marcada
							borderTextField=BorderFactory.createEtchedBorder();
						}else if(borda==1){
							//Borda afundada
							borderTextField=new BevelBorder(BevelBorder.LOWERED, null, null, null, null);
						}else if(borda==2){
							//Borda Enraizada
							borderTextField=new BevelBorder(BevelBorder.RAISED, null, null, null, null);
						}
						break;
					case "Background":
						int red=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int green=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blue=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						backgroundTextField = new Color(red, green, blue);
						break;

					case "Foreground":
						int redF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int greenF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blueF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						foregroundTextField = new Color(redF, greenF, blueF);
						break;

					case "Font":
						String[] fonte = arrayListAtributosComponente.get(j).getValor().split("/");
						jTetrisTextField.setFont(new Font(fonte[0], Integer.parseInt(fonte[2]), Integer.parseInt(fonte[1])));
						break;

					case "HorizontalAlignment":
						horizontalAlignmentTextField=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());

						break;

					case "Enabled":
						if(arrayListAtributosComponente.get(j).getValor().equals("false")){
							enabled=false;
						}

						break;

					case "Editable":
						if(arrayListAtributosComponente.get(j).getValor().equals("false")){
							editable=false;
						}

						break;

					}
				}


				jTetrisTextField.setText(textoTextField);
				jTetrisTextField.setBounds(xTextField, yTextField, widthTextField, heightTextField);
				jTetrisTextField.setHorizontalAlignment(horizontalAlignmentTextField);
				if(borderTextField!=null){
					jTetrisTextField.setBorder(borderTextField);
				}
				if(backgroundTextField!=null){
					jTetrisTextField.setBackground(backgroundTextField);
				}
				if(foregroundTextField!=null){
					jTetrisTextField.setForeground(foregroundTextField);
				}
				jTetrisTextField.setEnabled(enabled);
				jTetrisTextField.setEditable(editable);

				jInternalFrame.getContentPane().add(jTetrisTextField);

				break;

			case "Caixa de combinacao":
				JTetrisComboBox jTetrisComboBox = new JTetrisComboBox();

				jTetrisComboBox.addMouseListener(this);
				jTetrisComboBox.addMouseMotionListener(this);

				jInternalFrame.getArrayListRelacionamentoComponentes().add(new RelacionamentoComponente(jTetrisComboBox, arrayListComponentes.get(i)));

				jTetrisComboBox.setCursor(getCursor());

				jTetrisComboBox.setFocusable(false);

				//Capturando atributos
				int widthComboBox=100, heightComboBox=20, xComboBox=0, yComboBox=0;
				String textoComboBox="", itensComboBox="";
				Border borderComboBox = null;
				Color backgroundComboBox = null, foregroundComboBox=null;
				boolean enabledComboBox=true, editableComboBox=false;

				for (int j = 0; j < arrayListAtributosComponente.size(); j++) {
					switch(arrayListAtributosComponente.get(j).getNome()){
					case "Text":
						textoComboBox=arrayListAtributosComponente.get(j).getValor();
						break;
					case "x":
						xComboBox=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "y":
						yComboBox=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "width":
						widthComboBox=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "height":
						heightComboBox=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "Border":
						int borda=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						if(borda==0){
							//Borda marcada
							borderComboBox=BorderFactory.createEtchedBorder();
						}else if(borda==1){
							//Borda afundada
							borderComboBox=new BevelBorder(BevelBorder.LOWERED, null, null, null, null);
						}else if(borda==2){
							//Borda Enraizada
							borderComboBox=new BevelBorder(BevelBorder.RAISED, null, null, null, null);
						}
						break;
					case "Background":
						int red=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int green=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blue=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						backgroundComboBox = new Color(red, green, blue);
						break;

					case "Foreground":
						int redF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int greenF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blueF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						foregroundComboBox = new Color(redF, greenF, blueF);
						break;

					case "Font":
						String[] fonte = arrayListAtributosComponente.get(j).getValor().split("/");
						jTetrisComboBox.setFont(new Font(fonte[0], Integer.parseInt(fonte[2]), Integer.parseInt(fonte[1])));
						break;

					case "Enabled":
						if(arrayListAtributosComponente.get(j).getValor().equals("false")){
							enabledComboBox=false;
						}

						break;


					case "Itens":

						itensComboBox = arrayListAtributosComponente.get(j).getValor();

						String[] itens = itensComboBox.split("/");
						for (int k = 0; k < itens.length; k++) {
							jTetrisComboBox.addItem(itens[k]);

						}

						break;

					case "Items":

						itensComboBox = arrayListAtributosComponente.get(j).getValor();

						String[] items = itensComboBox.split("/");
						for (int k = 0; k < items.length; k++) {
							jTetrisComboBox.addItem(items[k]);

						}

						break;

					}
				}

				jTetrisComboBox.setEnabled(enabledComboBox);
				jTetrisComboBox.setEditable(editableComboBox);

				jTetrisComboBox.setSelectedItem(textoComboBox);
				jTetrisComboBox.setBounds(xComboBox, yComboBox, widthComboBox, heightComboBox);
				if(borderComboBox!=null){
					jTetrisComboBox.setBorder(borderComboBox);
				}
				if(backgroundComboBox!=null){
					jTetrisComboBox.setBackground(backgroundComboBox);
				}
				if(foregroundComboBox!=null){
					jTetrisComboBox.setForeground(foregroundComboBox);
				}

				jInternalFrame.getContentPane().add(jTetrisComboBox);

				break;

			case "Lista":
				JTetrisList jTetrisList = new JTetrisList();

				jTetrisList.addMouseListener(this);
				jTetrisList.addMouseMotionListener(this);

				jInternalFrame.getArrayListRelacionamentoComponentes().add(new RelacionamentoComponente(jTetrisList, arrayListComponentes.get(i)));

				jTetrisList.setCursor(getCursor());

				jTetrisList.setFocusable(false);

				//Capturando atributos
				int widthList=100, heightList=20, xList=0, yList=0;
				String textoList="", itensList="";
				Border borderList = null;
				Color backgroundList = null, foregroundList=null;
				boolean enabledList=true;

				for (int j = 0; j < arrayListAtributosComponente.size(); j++) {
					switch(arrayListAtributosComponente.get(j).getNome()){
					case "Text":
						textoList=arrayListAtributosComponente.get(j).getValor();
						break;
					case "x":
						xList=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "y":
						yList=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "width":
						widthList=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "height":
						heightList=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "Border":
						int borda=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						if(borda==0){
							//Borda marcada
							borderList=BorderFactory.createEtchedBorder();
						}else if(borda==1){
							//Borda afundada
							borderList=new BevelBorder(BevelBorder.LOWERED, null, null, null, null);
						}else if(borda==2){
							//Borda Enraizada
							borderList=new BevelBorder(BevelBorder.RAISED, null, null, null, null);
						}
						break;
					case "Background":
						int red=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int green=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blue=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						backgroundList = new Color(red, green, blue);
						break;

					case "Foreground":
						int redF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int greenF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blueF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						foregroundList = new Color(redF, greenF, blueF);
						break;

					case "Font":
						String[] fonte = arrayListAtributosComponente.get(j).getValor().split("/");
						jTetrisList.setFont(new Font(fonte[0], Integer.parseInt(fonte[2]), Integer.parseInt(fonte[1])));
						break;

					case "Enabled":
						if(arrayListAtributosComponente.get(j).getValor().equals("false")){
							enabledList=false;
						}

						break;

					case "Itens":

						itensList = arrayListAtributosComponente.get(j).getValor();

						String[] itens = itensList.split("/");

						jTetrisList.setValores(itens);

						break;

					case "Items":

						itensList = arrayListAtributosComponente.get(j).getValor();

						String[] items = itensList.split("/");

						jTetrisList.setValores(items);

						break;

					}
				}


				jTetrisList.setSelectedValue(""+textoList, true);

				jTetrisList.setBounds(xList, yList, widthList, heightList);
				if(borderList!=null){
					jTetrisList.setBorder(borderList);
				}
				if(backgroundList!=null){
					jTetrisList.setBackground(backgroundList);
				}
				if(foregroundList!=null){
					jTetrisList.setForeground(foregroundList);
				}
				jTetrisList.setEnabled(enabledList);

				jInternalFrame.getContentPane().add(jTetrisList);

				break;

			case "TextArea":
				JTextArea jTextArea = new JTextArea();

				jTextArea.addMouseListener(this);
				jTextArea.addMouseMotionListener(this);

				jInternalFrame.getArrayListRelacionamentoComponentes().add(new RelacionamentoComponente(jTextArea, arrayListComponentes.get(i)));

				jTextArea.setCursor(getCursor());

				jTextArea.setFocusable(false);

				//Capturando atributos
				int widthTextArea=100, heightTextArea=20, xTextArea=0, yTextArea=0;
				String textoTextArea="";
				Border borderTextArea = null;
				Color backgroundTextArea = null, foregroundTextArea=null;
				boolean enabledTextArea=true;

				for (int j = 0; j < arrayListAtributosComponente.size(); j++) {
					switch(arrayListAtributosComponente.get(j).getNome()){
					case "Text":
						textoTextArea=arrayListAtributosComponente.get(j).getValor();
						break;
					case "x":
						xTextArea=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "y":
						yTextArea=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "width":
						widthTextArea=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "height":
						heightTextArea=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "Border":
						int borda=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						if(borda==0){
							//Borda marcada
							borderTextArea=BorderFactory.createEtchedBorder();
						}else if(borda==1){
							//Borda afundada
							borderTextArea=new BevelBorder(BevelBorder.LOWERED, null, null, null, null);
						}else if(borda==2){
							//Borda Enraizada
							borderTextArea=new BevelBorder(BevelBorder.RAISED, null, null, null, null);
						}
						break;
					case "Background":
						int red=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int green=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blue=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						backgroundTextArea = new Color(red, green, blue);
						break;

					case "Foreground":
						int redF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int greenF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blueF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						foregroundTextArea = new Color(redF, greenF, blueF);
						break;

					case "Font":
						String[] fonte = arrayListAtributosComponente.get(j).getValor().split("/");
						jTextArea.setFont(new Font(fonte[0], Integer.parseInt(fonte[2]), Integer.parseInt(fonte[1])));
						break;

					case "Enabled":
						if(arrayListAtributosComponente.get(j).getValor().equals("false")){
							enabledTextArea=false;
						}

						break;
					}
				}


				jTextArea.setText(""+textoTextArea);

				jTextArea.setBounds(xTextArea, yTextArea, widthTextArea, heightTextArea);
				if(borderTextArea!=null){
					jTextArea.setBorder(borderTextArea);
				}
				if(backgroundTextArea!=null){
					jTextArea.setBackground(backgroundTextArea);
				}
				if(foregroundTextArea!=null){
					jTextArea.setForeground(foregroundTextArea);
				}
				jTextArea.setEnabled(enabledTextArea);

				jInternalFrame.getContentPane().add(jTextArea);

				break;

			case "EditorPane":
				JEditorPane jEditorPane = new JEditorPane();

				jEditorPane.addMouseListener(this);
				jEditorPane.addMouseMotionListener(this);

				jInternalFrame.getArrayListRelacionamentoComponentes().add(new RelacionamentoComponente(jEditorPane, arrayListComponentes.get(i)));

				jEditorPane.setCursor(getCursor());

				jEditorPane.setFocusable(false);

				//Capturando atributos
				int widthEditorPane=100, heightEditorPane=20, xEditorPane=0, yEditorPane=0;
				String textoEditorPane="";
				Border borderEditorPane = null;
				Color backgroundEditorPane = null, foregroundEditorPane=null;
				boolean enabledEditorPane=true;

				for (int j = 0; j < arrayListAtributosComponente.size(); j++) {
					switch(arrayListAtributosComponente.get(j).getNome()){
					case "Text":
						textoEditorPane=arrayListAtributosComponente.get(j).getValor();
						break;
					case "x":
						xEditorPane=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "y":
						yEditorPane=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "width":
						widthEditorPane=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "height":
						heightEditorPane=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "Border":
						int borda=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						if(borda==0){
							//Borda marcada
							borderEditorPane=BorderFactory.createEtchedBorder();
						}else if(borda==1){
							//Borda afundada
							borderEditorPane=new BevelBorder(BevelBorder.LOWERED, null, null, null, null);
						}else if(borda==2){
							//Borda Enraizada
							borderEditorPane=new BevelBorder(BevelBorder.RAISED, null, null, null, null);
						}
						break;
					case "Background":
						int red=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int green=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blue=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						backgroundEditorPane = new Color(red, green, blue);
						break;

					case "Foreground":
						int redF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int greenF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blueF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						foregroundEditorPane = new Color(redF, greenF, blueF);
						break;

					case "Font":
						String[] fonte = arrayListAtributosComponente.get(j).getValor().split("/");
						jEditorPane.setFont(new Font(fonte[0], Integer.parseInt(fonte[2]), Integer.parseInt(fonte[1])));
						break;

					case "Enabled":
						if(arrayListAtributosComponente.get(j).getValor().equals("false")){
							enabledEditorPane=false;
						}

						break;
					}
				}


				jEditorPane.setText(""+textoEditorPane);

				jEditorPane.setBounds(xEditorPane, yEditorPane, widthEditorPane, heightEditorPane);
				if(borderEditorPane!=null){
					jEditorPane.setBorder(borderEditorPane);
				}
				if(backgroundEditorPane!=null){
					jEditorPane.setBackground(backgroundEditorPane);
				}
				if(foregroundEditorPane!=null){
					jEditorPane.setForeground(foregroundEditorPane);
				}
				jEditorPane.setEnabled(enabledEditorPane);

				jInternalFrame.getContentPane().add(jEditorPane);

				break;

			case "Botao":
				JTetrisButton jTetrisButton = new JTetrisButton("");

				jTetrisButton.addMouseListener(this);
				jTetrisButton.addMouseMotionListener(this);

				jInternalFrame.getArrayListRelacionamentoComponentes().add(new RelacionamentoComponente(jTetrisButton, arrayListComponentes.get(i)));

				jTetrisButton.setFocusable(false);

				//Capturando atributos
				int widthButton=100, heightButton=20, xButton=0, yButton=0, horizontalAlignmentButton=SwingConstants.CENTER, verticalAlignmentButton=SwingConstants.CENTER, horizontalTextAlignmentButton=SwingConstants.TRAILING, verticalTextAlignmentButton=SwingConstants.CENTER;
				String textoButton="", iconeButton="";
				Border borderButton = null;
				Color backgroundButton = null, foregroundButton=null;
				boolean undecorated=false;

				for (int j = 0; j < arrayListAtributosComponente.size(); j++) {
					switch(arrayListAtributosComponente.get(j).getNome()){
					case "Text":
						textoButton=arrayListAtributosComponente.get(j).getValor();
						break;
					case "x":
						xButton=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "y":
						yButton=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "width":
						widthButton=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "height":
						heightButton=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "Border":
						int borda=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						if(borda==0){
							//Borda marcada
							borderButton=BorderFactory.createEtchedBorder();
						}else if(borda==1){
							//Borda afundada
							borderButton=new BevelBorder(BevelBorder.LOWERED, null, null, null, null);
						}else if(borda==2){
							//Borda Enraizada
							borderButton=new BevelBorder(BevelBorder.RAISED, null, null, null, null);
						}
						break;
					case "Background":
						int red=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int green=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blue=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						backgroundButton = new Color(red, green, blue);
						break;

					case "Foreground":
						int redF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int greenF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blueF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						foregroundButton = new Color(redF, greenF, blueF);
						break;

					case "Font":
						String[] fonte = arrayListAtributosComponente.get(j).getValor().split("/");
						jTetrisButton.setFont(new Font(fonte[0], Integer.parseInt(fonte[2]), Integer.parseInt(fonte[1])));
						break;

					case "HorizontalAlignment":
						horizontalAlignmentButton=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());

						break;

					case "VerticalAlignment":
						verticalAlignmentButton=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());

						break;

					case "HorizontalTextPosition":
						horizontalTextAlignmentButton=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());

						break;

					case "VerticalTextPosition":
						verticalTextAlignmentButton=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());

						break;

					case "Undecorated":
						if(arrayListAtributosComponente.get(j).getValor().equals("true")){
							undecorated=true;
						}

						break;

					case "Icon":
						iconeButton=arrayListAtributosComponente.get(j).getValor();

						break;

					}
				}


				jTetrisButton.setText(textoButton);
				jTetrisButton.setBounds(xButton, yButton, widthButton, heightButton);
				jTetrisButton.setHorizontalAlignment(horizontalAlignmentButton);
				jTetrisButton.setVerticalAlignment(verticalAlignmentButton);
				jTetrisButton.setHorizontalTextPosition(horizontalTextAlignmentButton);
				jTetrisButton.setVerticalTextPosition(verticalTextAlignmentButton);
				if(borderButton!=null){
					jTetrisButton.setBorder(borderButton);
				}
				if(backgroundButton!=null){
					jTetrisButton.setBackground(backgroundButton);
				}
				if(foregroundButton!=null){
					jTetrisButton.setForeground(foregroundButton);
				}

				if(iconeButton.equals("")==false){
					if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/"+iconeButton)){
						jTetrisButton.setIcon(new ImageIcon(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/"+iconeButton));
					}
				}

				if(undecorated){
					jTetrisButton.setContentAreaFilled(false);
					jTetrisButton.setBorderPainted(false);
				}

				jInternalFrame.getContentPane().add(jTetrisButton);

				break;

			case "Campo de checagem":
				JCheckBox jCheckBox = new JCheckBox();

				jCheckBox.addMouseListener(this);
				jCheckBox.addMouseMotionListener(this);

				jCheckBox.setFocusable(false);
				jCheckBox.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						((JCheckBox)e.getSource()).setSelected(false);
					}

				});

				jInternalFrame.getArrayListRelacionamentoComponentes().add(new RelacionamentoComponente(jCheckBox, arrayListComponentes.get(i)));

				//Capturando atributos
				int widthCheckBox=100, heightCheckBox=20, xCheckBox=0, yCheckBox=0, horizontalAlignmentCheckBox=JTetrisTextField.LEADING;
				String textoCheckBox="";
				Border borderCheckBox = null;
				Color backgroundCheckBox = null, foregroundCheckBox=null;
				boolean checked=false;

				for (int j = 0; j < arrayListAtributosComponente.size(); j++) {
					switch(arrayListAtributosComponente.get(j).getNome()){
					case "Title":
						textoCheckBox=arrayListAtributosComponente.get(j).getValor();
						break;
					case "x":
						xCheckBox=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "y":
						yCheckBox=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "width":
						widthCheckBox=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "height":
						heightCheckBox=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "Border":
						int borda=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						if(borda==0){
							//Borda marcada
							borderCheckBox=BorderFactory.createEtchedBorder();
						}else if(borda==1){
							//Borda afundada
							borderCheckBox=new BevelBorder(BevelBorder.LOWERED, null, null, null, null);
						}else if(borda==2){
							//Borda Enraizada
							borderCheckBox=new BevelBorder(BevelBorder.RAISED, null, null, null, null);
						}
						break;
					case "Background":
						int red=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int green=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blue=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						backgroundCheckBox = new Color(red, green, blue);
						break;

					case "Foreground":
						int redF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int greenF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blueF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						foregroundCheckBox = new Color(redF, greenF, blueF);
						break;

					case "Font":
						String[] fonte = arrayListAtributosComponente.get(j).getValor().split("/");
						jCheckBox.setFont(new Font(fonte[0], Integer.parseInt(fonte[2]), Integer.parseInt(fonte[1])));
						break;

					case "HorizontalAlignment":
						horizontalAlignmentButton=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());

						break;

					case "Checked":
						if(arrayListAtributosComponente.get(j).getValor().equals("true")){
							checked=true;
						}

						break;

					}
				}


				jCheckBox.setText(textoCheckBox);
				jCheckBox.setBounds(xCheckBox, yCheckBox, widthCheckBox, heightCheckBox);
				jCheckBox.setHorizontalAlignment(horizontalAlignmentCheckBox);
				if(borderCheckBox!=null){
					jCheckBox.setBorder(borderCheckBox);
				}
				if(backgroundCheckBox!=null){
					jCheckBox.setBackground(backgroundCheckBox);
				}
				if(foregroundCheckBox!=null){
					jCheckBox.setForeground(foregroundCheckBox);
				}
				jCheckBox.setSelected(checked);

				jInternalFrame.getContentPane().add(jCheckBox);

				break;

			case "Painel":
				JTetrisPanelInternalFrame jPanel = new JTetrisPanelInternalFrame(getjInternalFrame());

				jPanel.addMouseListener(this);
				jPanel.addMouseListener(jInternalFrame.getMouseListener());
				jPanel.addMouseMotionListener(this);

				jPanel.setLayout(null);

				jInternalFrame.getArrayListRelacionamentoComponentes().add(new RelacionamentoComponente(jPanel, arrayListComponentes.get(i)));


				//Capturando atributos
				int widthPanel=100, heightPanel=100, xPanel=0, yPanel=0;
				Border borderPanel = null;
				String iconePainel="", textPainel="";
				Color backgroundPanel = null;

				for (int j = 0; j < arrayListAtributosComponente.size(); j++) {
					switch(arrayListAtributosComponente.get(j).getNome()){
					case "x":
						xPanel=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "y":
						yPanel=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "width":
						widthPanel=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "height":
						heightPanel=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;

					case "Text":
						textPainel=arrayListAtributosComponente.get(j).getValor();
						break;

					case "Border":
						int borda=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						if(borda==0){
							//Borda marcada
							borderPanel=BorderFactory.createEtchedBorder();
						}else if(borda==1){
							//Borda afundada
							borderPanel=new BevelBorder(BevelBorder.LOWERED, null, null, null, null);
						}else if(borda==2){
							//Borda Enraizada
							borderPanel=new BevelBorder(BevelBorder.RAISED, null, null, null, null);
						}
						break;
					case "Background":
						int red=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int green=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blue=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						backgroundPanel = new Color(red, green, blue);
						break;

					case "Icon":
						iconePainel=arrayListAtributosComponente.get(j).getValor();

						break;

					}
				}

				jPanel.setBounds(xPanel, yPanel, widthPanel, heightPanel);
				if(textPainel.equals("")==false){
					jPanel.setText(textPainel);
				}
				if(borderPanel!=null){
					jPanel.setBorder(borderPanel);
				}
				if(backgroundPanel!=null){
					jPanel.setBackground(backgroundPanel);
				}

				if(iconePainel.equals("")==false){
					if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/"+iconePainel)){
						jPanel.setIcon(new ImageIcon(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/"+iconePainel));
					}
				}

				jInternalFrame.getContentPane().add(jPanel);

				break;

			case "Barra de ferramentas":
				JTetrisToolBar jToolBar = new JTetrisToolBar();

				jToolBar.addMouseListener(this);
				jToolBar.addMouseListener(jInternalFrame.getMouseListener());
				jToolBar.addMouseMotionListener(this);
				jToolBar.setFloatable(false);

				//jToolBar.setLayout(null);

				jInternalFrame.getArrayListRelacionamentoComponentes().add(new RelacionamentoComponente(jToolBar, arrayListComponentes.get(i)));


				//Capturando atributos
				int widthToolBar=100, heightToolBar=50, xToolBar=0, yToolBar=0;
				Border borderToolBar = new EtchedBorder();
				String iconeToolBar="";

				Color backgroundToolBar = null;

				for (int j = 0; j < arrayListAtributosComponente.size(); j++) {
					switch(arrayListAtributosComponente.get(j).getNome()){
					case "x":
						xToolBar=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "y":
						yToolBar=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "width":
						widthToolBar=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "height":
						heightToolBar=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "Border":
						int borda=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						if(borda==0){
							//Borda marcada
							borderToolBar=BorderFactory.createEtchedBorder();
						}else if(borda==1){
							//Borda afundada
							borderToolBar=new BevelBorder(BevelBorder.LOWERED, null, null, null, null);
						}else if(borda==2){
							//Borda Enraizada
							borderToolBar=new BevelBorder(BevelBorder.RAISED, null, null, null, null);
						}
						break;
					case "Background":
						int red=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int green=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blue=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						backgroundToolBar = new Color(red, green, blue);
						break;

					case "Icon":
						iconeToolBar=arrayListAtributosComponente.get(j).getValor();

						break;

					}
				}

				jToolBar.setBounds(xToolBar, yToolBar, widthToolBar, heightToolBar);
				if(borderToolBar!=null){
					jToolBar.setBorder(borderToolBar);
				}
				if(backgroundToolBar!=null){
					jToolBar.setBackground(backgroundToolBar);
				}

				if(iconeToolBar.equals("")==false){
					if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/"+iconeToolBar)){
						jToolBar.setIcon(new ImageIcon(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/"+iconeToolBar));
					}
				}

				jInternalFrame.getContentPane().add(jToolBar);

				break;

			case "Abas":
				JTabbedPane jTabbedPane = new JTabbedPane();

				jTabbedPane.addMouseListener(this);
				jTabbedPane.addMouseListener(jInternalFrame.getMouseListener());
				jTabbedPane.addMouseMotionListener(this);

				jInternalFrame.getArrayListRelacionamentoComponentes().add(new RelacionamentoComponente(jTabbedPane, arrayListComponentes.get(i)));


				//Capturando atributos
				int widthTabbedPane=100, heightTabbedPane=100, xTabbedPane=0, yTabbedPane=0;
				Border borderTabbedPane = new BevelBorder(BevelBorder.RAISED, null, null, null, null);

				Color backgroundTabbedPane = null;

				for (int j = 0; j < arrayListAtributosComponente.size(); j++) {
					switch(arrayListAtributosComponente.get(j).getNome()){
					case "x":
						xTabbedPane=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "y":
						yTabbedPane=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "width":
						widthTabbedPane=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "height":
						heightTabbedPane=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "Border":
						int borda=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						if(borda==0){
							//Borda marcada
							borderTabbedPane=BorderFactory.createEtchedBorder();
						}else if(borda==1){
							//Borda afundada
							borderTabbedPane=new BevelBorder(BevelBorder.LOWERED, null, null, null, null);
						}else if(borda==2){
							//Borda Enraizada
							borderTabbedPane=new BevelBorder(BevelBorder.RAISED, null, null, null, null);
						}
						break;
					case "Background":
						int red=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int green=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blue=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						backgroundTabbedPane = new Color(red, green, blue);
						break;

					}
				}

				jTabbedPane.setBounds(xTabbedPane, yTabbedPane, widthTabbedPane, heightTabbedPane);
				if(borderTabbedPane!=null){
					jTabbedPane.setBorder(borderTabbedPane);
				}
				if(backgroundTabbedPane!=null){
					jTabbedPane.setBackground(backgroundTabbedPane);
				}

				jInternalFrame.getContentPane().add(jTabbedPane);

				break;

			case "Barra de menu":
				JMenuBar jMenuBar = new JMenuBar();

				jMenuBar.addMouseListener(this);
				jMenuBar.addMouseListener(jInternalFrame.getMouseListener());
				jMenuBar.addMouseMotionListener(this);

				jMenuBar.setBounds(0, 0, jInternalFrame.getWidth(), 20);

				jInternalFrame.getArrayListRelacionamentoComponentes().add(new RelacionamentoComponente(jMenuBar, arrayListComponentes.get(i)));

				jInternalFrame.setJMenuBar(jMenuBar);

				break;

			case "Menu":

				JMenu jMenu = new JMenu();

				jMenu.addMouseListener(this);
				jMenu.addMouseListener(jInternalFrame.getMouseListener());
				jMenu.addMouseMotionListener(this);

				jInternalFrame.getArrayListRelacionamentoComponentes().add(new RelacionamentoComponente(jMenu, arrayListComponentes.get(i)));


				for (int j = 0; j < arrayListAtributosComponente.size(); j++) {
					switch(arrayListAtributosComponente.get(j).getNome()){
					case "Text":
						jMenu.setText(arrayListAtributosComponente.get(j).getValor());

						break;

					case "Background":
						int red=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int green=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blue=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						jMenu.setBackground(new Color(red, green, blue));
						break;

					case "Foreground":
						int redF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int greenF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blueF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						jMenu.setForeground(new Color(redF, greenF, blueF));
						break;

					case "Font":
						String[] fonte = arrayListAtributosComponente.get(j).getValor().split("/");
						jMenu.setFont(new Font(fonte[0], Integer.parseInt(fonte[2]), Integer.parseInt(fonte[1])));
						break;

					case "Icon":

						if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/"+arrayListAtributosComponente.get(j).getValor())){
							jMenu.setIcon(new ImageIcon(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/"+arrayListAtributosComponente.get(j).getValor()));
						}

						break;


					}
				}

				break;

			case "Separator":

				JSeparator jSeparator = new JSeparator();

				jSeparator.addMouseListener(this);
				jSeparator.addMouseListener(jInternalFrame.getMouseListener());
				jSeparator.addMouseMotionListener(this);

				jInternalFrame.getArrayListRelacionamentoComponentes().add(new RelacionamentoComponente(jSeparator, arrayListComponentes.get(i)));

				break;

			case "Menu Item":
				JMenuItem jMenuItem = new JMenuItem();

				jMenuItem.addMouseListener(this);
				jMenuItem.addMouseListener(jInternalFrame.getMouseListener());
				jMenuItem.addMouseMotionListener(this);


				jInternalFrame.getArrayListRelacionamentoComponentes().add(new RelacionamentoComponente(jMenuItem, arrayListComponentes.get(i)));

				for (int j = 0; j < arrayListAtributosComponente.size(); j++) {
					switch(arrayListAtributosComponente.get(j).getNome()){
					case "Text":
						jMenuItem.setText(arrayListAtributosComponente.get(j).getValor());

						break;

					case "Background":
						int red=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int green=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blue=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						jMenuItem.setBackground(new Color(red, green, blue));
						break;

					case "Foreground":
						int redF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int greenF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blueF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						jMenuItem.setForeground(new Color(redF, greenF, blueF));
						break;

					case "Font":
						String[] fonte = arrayListAtributosComponente.get(j).getValor().split("/");
						jMenuItem.setFont(new Font(fonte[0], Integer.parseInt(fonte[2]), Integer.parseInt(fonte[1])));
						break;

					case "Icon":

						if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/"+arrayListAtributosComponente.get(j).getValor())){
							jMenuItem.setIcon(new ImageIcon(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/"+arrayListAtributosComponente.get(j).getValor()));
						}

						break;
					}
				}

				break;

			case "Tabela":
				JTetrisTable jTable;
				String[] tits = new String[0];
				String[] cols = new String[0];

				//Capturando atributos
				int widthTable=100, heightTable=100, xTable=0, yTable=0;
				Border borderTable = new BevelBorder(BevelBorder.LOWERED, null, null, null, null);
				Color backgroundTable = null,foregroundTable=null;
				Font fonteTable=null;
				String tabela="";

				for (int j = 0; j < arrayListAtributosComponente.size(); j++) {
					switch(arrayListAtributosComponente.get(j).getNome()){
					case "x":
						xTable=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "y":
						yTable=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "width":
						widthTable=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "height":
						heightTable=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "Border":
						int borda=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						if(borda==0){
							//Borda marcada
							borderTable=BorderFactory.createEtchedBorder();
						}else if(borda==1){
							//Borda afundada
							borderTable=new BevelBorder(BevelBorder.LOWERED, null, null, null, null);
						}else if(borda==2){
							//Borda Enraizada
							borderTable=new BevelBorder(BevelBorder.RAISED, null, null, null, null);
						}
						break;
					case "Background":
						int red=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int green=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blue=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						backgroundTable = new Color(red, green, blue);
						break;

					case "Foreground":
						int redF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int greenF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blueF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						foregroundTable = new Color(redF, greenF, blueF);
						break;

					case "Font":
						String[] fonte = arrayListAtributosComponente.get(j).getValor().split("/");
						fonteTable = (new Font(fonte[0], Integer.parseInt(fonte[2]), Integer.parseInt(fonte[1])));
						break;

					case "Titulos":
						String titulos = arrayListAtributosComponente.get(j).getValor();

						tits = titulos.split("/");

						break;

					case "Titles":
						String titles = arrayListAtributosComponente.get(j).getValor();

						tits = titles.split("/");

						break;

					case "Colunas":
						String colunas = arrayListAtributosComponente.get(j).getValor();

						cols = colunas.split("/");

						break;

					case "Columns":
						String columns = arrayListAtributosComponente.get(j).getValor();

						cols = columns.split("/");

						break;

					case "Tabela":
						tabela=arrayListAtributosComponente.get(j).getValor();
						break;

					}
				}

				//Falta colunas, titulos e tabela
				jTable = new JTetrisTable(tits, cols, tabela);
				jTable.setBounds(xTable, yTable, widthTable, heightTable);
				if(borderTable!=null){
					jTable.setBorder(borderTable);
				}
				if(backgroundTable!=null){
					jTable.setBackground(backgroundTable);
				}

				if(foregroundTable!=null){
					jTable.setForeground(backgroundTable);
				}

				if(fonteTable!=null){
					jTable.setFont(fonteTable);
				}

				jTable.addMouseListener(this);

				jTable.addMouseMotionListener(this);

				jTable.setFocusable(false);

				jInternalFrame.getArrayListRelacionamentoComponentes().add(new RelacionamentoComponente(jTable, arrayListComponentes.get(i)));


				jInternalFrame.getContentPane().add(jTable);

				break;

			case "Campo de escolha":
				JTetrisCampoDeEscolha jTetrisCampoDeEscolha = new JTetrisCampoDeEscolha("");

				jTetrisCampoDeEscolha.addMouseListener(this);

				jTetrisCampoDeEscolha.addMouseMotionListener(this);

				jTetrisCampoDeEscolha.setLayout(null);

				jInternalFrame.getArrayListRelacionamentoComponentes().add(new RelacionamentoComponente(jTetrisCampoDeEscolha, arrayListComponentes.get(i)));


				//Capturando atributos
				String textCampoDeEscolha="";
				String radioButtons="";

				int widthCampoDeEscolha=100, heightCampoDeEscolha=100, xCampoDeEscolha=0, yCampoDeEscolha=0;
				Border borderCampoDeEscolha = null;
				Color backgroundCampoDeEscolha = null, foregroundCampoDeEscolha=null;
				Font fonteCampoDeEscolha=null;

				for (int j = 0; j < arrayListAtributosComponente.size(); j++) {
					switch(arrayListAtributosComponente.get(j).getNome()){
					case "Text":
						textCampoDeEscolha=arrayListAtributosComponente.get(j).getValor();
						break;
					case "x":
						xCampoDeEscolha=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "y":
						yCampoDeEscolha=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "width":
						widthCampoDeEscolha=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "height":
						heightCampoDeEscolha=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "Border":
						int borda=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						if(borda==0){
							//Borda marcada
							borderCampoDeEscolha=BorderFactory.createEtchedBorder();
						}else if(borda==1){
							//Borda afundada
							borderCampoDeEscolha=new BevelBorder(BevelBorder.LOWERED, null, null, null, null);
						}else if(borda==2){
							//Borda Enraizada
							borderCampoDeEscolha=new BevelBorder(BevelBorder.RAISED, null, null, null, null);
						}
						break;
					case "Background":
						int red=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int green=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blue=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						backgroundCampoDeEscolha = new Color(red, green, blue);
						break;

					case "Foreground":
						int redF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(0, 3));
						int greenF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(5, 8));
						int blueF=Integer.parseInt(arrayListAtributosComponente.get(j).getValor().substring(10, 13));

						foregroundCampoDeEscolha = new Color(redF, greenF, blueF);
						break;

					case "Font":
						String[] fonte = arrayListAtributosComponente.get(j).getValor().split("/");
						fonteCampoDeEscolha = (new Font(fonte[0], Integer.parseInt(fonte[2]), Integer.parseInt(fonte[1])));
						break;

					case "RadioButtons":

						radioButtons = arrayListAtributosComponente.get(j).getValor();

						String[] radios = radioButtons.split("/");
						for (int k = 0; k < radios.length; k++) {
							jTetrisCampoDeEscolha.adicionarJRadioButton(new JRadioButton(radios[k]));
							jTetrisCampoDeEscolha.getArrayListJRadioButtons().get(k).setFocusable(false);
						}

						break;

					}
				}

				jTetrisCampoDeEscolha.setText(textCampoDeEscolha);
				jTetrisCampoDeEscolha.setBounds(xCampoDeEscolha, yCampoDeEscolha, widthCampoDeEscolha, heightCampoDeEscolha);
				if(borderCampoDeEscolha!=null){
					jTetrisCampoDeEscolha.setBorder(borderCampoDeEscolha);
				}
				if(backgroundCampoDeEscolha!=null){
					jTetrisCampoDeEscolha.setBackground(backgroundCampoDeEscolha);
				}
				if(foregroundCampoDeEscolha!=null){
					jTetrisCampoDeEscolha.setForeground(foregroundCampoDeEscolha);
				}
				if(fonteCampoDeEscolha!=null){
					jTetrisCampoDeEscolha.setFont(fonteCampoDeEscolha);
				}

				jInternalFrame.getContentPane().add(jTetrisCampoDeEscolha);

				break;

			case "Timer":
				JLabel jLabelTimer = new JLabel();
				jLabelTimer.addMouseListener(this);
				jLabelTimer.addMouseMotionListener(this);

				jInternalFrame.getArrayListRelacionamentoComponentes().add(new RelacionamentoComponente(jLabelTimer, arrayListComponentes.get(i)));

				//Capturando atributos
				int widthLabelTimer=20, heightLabelTimer=20, xLabelTimer=0, yLabelTimer=0;

				for (int j = 0; j < arrayListAtributosComponente.size(); j++) {
					switch(arrayListAtributosComponente.get(j).getNome()){

					case "x":
						xLabelTimer=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "y":
						yLabelTimer=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "width":
						widthLabelTimer=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "height":
						heightLabelTimer=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;

					}
				}

				jLabelTimer.setBounds(xLabelTimer, yLabelTimer, widthLabelTimer, heightLabelTimer);
				jLabelTimer.setIcon(new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_timer.png")));

				jInternalFrame.getContentPane().add(jLabelTimer);

				break;

			case "Procedure":
				JLabel jLabelProcedure = new JLabel();
				jLabelProcedure.addMouseListener(this);
				jLabelProcedure.addMouseMotionListener(this);

				jInternalFrame.getArrayListRelacionamentoComponentes().add(new RelacionamentoComponente(jLabelProcedure, arrayListComponentes.get(i)));

				//Capturando atributos
				int widthLabelProcedure=20, heightLabelProcedure=20, xLabelProcedure=0, yLabelProcedure=0;

				for (int j = 0; j < arrayListAtributosComponente.size(); j++) {
					switch(arrayListAtributosComponente.get(j).getNome()){

					case "x":
						xLabelProcedure=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "y":
						yLabelProcedure=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "width":
						widthLabelProcedure=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "height":
						heightLabelProcedure=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;

					}
				}

				jLabelProcedure.setBounds(xLabelProcedure, yLabelProcedure, widthLabelProcedure, heightLabelProcedure);
				jLabelProcedure.setIcon(new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/menu_exportar.png")));

				jInternalFrame.getContentPane().add(jLabelProcedure);

				break;

			case "Variable":
				JLabel jLabelVariable = new JLabel();
				jLabelVariable.addMouseListener(this);
				jLabelVariable.addMouseMotionListener(this);

				jInternalFrame.getArrayListRelacionamentoComponentes().add(new RelacionamentoComponente(jLabelVariable, arrayListComponentes.get(i)));

				//Capturando atributos
				int widthLabelVariable=20, heightLabelVariable=20, xLabelVariable=0, yLabelVariable=0;

				for (int j = 0; j < arrayListAtributosComponente.size(); j++) {
					switch(arrayListAtributosComponente.get(j).getNome()){

					case "x":
						xLabelVariable=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "y":
						yLabelVariable=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "width":
						widthLabelVariable=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "height":
						heightLabelVariable=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;

					}
				}

				jLabelVariable.setBounds(xLabelVariable, yLabelVariable, widthLabelVariable, heightLabelVariable);
				jLabelVariable.setIcon(new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_variavel.png")));

				jInternalFrame.getContentPane().add(jLabelVariable);

				break;

			case "Relatorio":
				JLabel jLabelRelatorio = new JLabel();
				jLabelRelatorio.addMouseListener(this);
				jLabelRelatorio.addMouseMotionListener(this);

				jInternalFrame.getArrayListRelacionamentoComponentes().add(new RelacionamentoComponente(jLabelRelatorio, arrayListComponentes.get(i)));

				//Capturando atributos
				int widthLabelRelatorio=20, heightLabelRelatorio=20, xLabelRelatorio=0, yLabelRelatorio=0;

				for (int j = 0; j < arrayListAtributosComponente.size(); j++) {
					switch(arrayListAtributosComponente.get(j).getNome()){

					case "x":
						xLabelRelatorio=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "y":
						yLabelRelatorio=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "width":
						widthLabelRelatorio=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;
					case "height":
						heightLabelRelatorio=Integer.parseInt(arrayListAtributosComponente.get(j).getValor());
						break;

					}
				}

				jLabelRelatorio.setBounds(xLabelRelatorio, yLabelRelatorio, widthLabelRelatorio, heightLabelRelatorio);
				jLabelRelatorio.setIcon(new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/componentes/icone_report.png")));

				jInternalFrame.getContentPane().add(jLabelRelatorio);

				break;
				//"Rótulo", "Campo de texto", "Painel", "Imagem", "Botão", |"Tabela"|, "Campo de checagem", "Campo de escolha"
			}
		}
		
		//Adicionando o ToolTipText
		for (int j = 0; j < jInternalFrame.getArrayListRelacionamentoComponentes().size(); j++) {
			((JComponent)jInternalFrame.getArrayListRelacionamentoComponentes().get(j).getComponent()).setToolTipText("<html><head></head><body><b>"+jInternalFrame.getArrayListRelacionamentoComponentes().get(j).getComponente().getNome()+"</b><br/>x: "+jInternalFrame.getArrayListRelacionamentoComponentes().get(j).getComponent().getX()+" y: "+jInternalFrame.getArrayListRelacionamentoComponentes().get(j).getComponent().getY()+" width: "+jInternalFrame.getArrayListRelacionamentoComponentes().get(j).getComponent().getWidth()+" height: "+jInternalFrame.getArrayListRelacionamentoComponentes().get(j).getComponent().getHeight()+"</body></html>");
		}
		//Adiciona as Abas de um TabbedPane, menus de MenuBars e componentes de conteiners 
		for (int i = 0; i < arrayListComponentes.size(); i++) {
			if(arrayListComponentes.get(i).getAtributo("Pai")!=null){

				if(arrayListComponentes.get(i).getAtributo("Pai").getValor().equals(getJanela().getNome())==false){
					for (int k = 0; k < jInternalFrame.getArrayListRelacionamentoComponentes().size(); k++) {
						if(jInternalFrame.getArrayListRelacionamentoComponentes().get(k).getComponente().equals(arrayListComponentes.get(i))){
							jInternalFrame.getContentPane().remove(jInternalFrame.getArrayListRelacionamentoComponentes().get(k).getComponent());
							for (int k2 = 0; k2 < jInternalFrame.getArrayListRelacionamentoComponentes().size(); k2++) {
								if(jInternalFrame.getArrayListRelacionamentoComponentes().get(k2).getComponente().getNome().equals(arrayListComponentes.get(i).getAtributo("Pai").getValor())){
									if(arrayListComponentes.get(k2).getTipo().equals("Abas")){
										Atributo atributoTitulo = jInternalFrame.getArrayListRelacionamentoComponentes().get(k).getComponente().getAtributo("TituloAbas");
										String titulo="";
										if(atributoTitulo!=null){
											titulo = atributoTitulo.getValor();
										}
										((JTabbedPane)jInternalFrame.getArrayListRelacionamentoComponentes().get(k2).getComponent()).addTab(titulo, jInternalFrame.getArrayListRelacionamentoComponentes().get(k).getComponent());

										Atributo atributoTab= jInternalFrame.getArrayListRelacionamentoComponentes().get(k2).getComponente().getAtributo("SelectedIndex");
										if(atributoTab!=null){
											if(((JTabbedPane)jInternalFrame.getArrayListRelacionamentoComponentes().get(k2).getComponent()).getTabCount()==Integer.parseInt(atributoTab.getValor())+1){
												((JTabbedPane)jInternalFrame.getArrayListRelacionamentoComponentes().get(k2).getComponent()).setSelectedIndex(Integer.parseInt(atributoTab.getValor()));

											}
										}

										break;
									}else if(arrayListComponentes.get(k2).getTipo().equals("Barra de menu")){
										((JMenuBar)jInternalFrame.getArrayListRelacionamentoComponentes().get(k2).getComponent()).add(jInternalFrame.getArrayListRelacionamentoComponentes().get(k).getComponent());
										break;
									}else if(arrayListComponentes.get(k2).getTipo().equals("Menu")){
										((JMenu)jInternalFrame.getArrayListRelacionamentoComponentes().get(k2).getComponent()).add(jInternalFrame.getArrayListRelacionamentoComponentes().get(k).getComponent());
										break;
									}else{
										if(jInternalFrame.getArrayListRelacionamentoComponentes().get(k2).getComponent() instanceof JTetrisPanel){
											((JTetrisPanel)jInternalFrame.getArrayListRelacionamentoComponentes().get(k2).getComponent()).add(jInternalFrame.getArrayListRelacionamentoComponentes().get(k).getComponent());
										}else if(jInternalFrame.getArrayListRelacionamentoComponentes().get(k2).getComponent() instanceof JToolBar){
											((JToolBar)jInternalFrame.getArrayListRelacionamentoComponentes().get(k2).getComponent()).add(jInternalFrame.getArrayListRelacionamentoComponentes().get(k).getComponent());
										}
										break;
									}
								}
							}
							break;
						}

					}
				}

			}
		}

		jInternalFrame.repaint();
	}
	//Getters e Setters
	public Janela getJanela() {
		return janela;
	}

	public void setJanela(Janela janela) {
		this.janela = janela;
	}

	public JInternalFrameJanela getjInternalFrame() {
		return jInternalFrame;
	}

	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}
	
	public void setDragged(boolean dragged) {
		this.dragged=dragged;
	}
	public boolean isDragged() {
		return dragged;
	}
	//Representa o evento de segurar um objeto com o mouse
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

		if(getjFramePrincipal().getjTetrisListPaletaDeObjetos().getSelectedValue().equals("Pointer")){
			if(!(arg0.getSource() instanceof JMenuBar) && !(arg0.getSource() instanceof JMenu) && !(arg0.getSource() instanceof JMenuItem)){
				ArrayList<Component> arrayListComponentFoco = getjInternalFrame().getArrayListComponentFoco();

				//Verificando se o compenente clicado esta entre os componentes selecionados
				boolean selecionado=false;
				for (int i = 0; i < arrayListComponentFoco.size(); i++) {
					if(((Component)arg0.getSource()).equals(arrayListComponentFoco.get(i))){
						selecionado=true;
						break;
					}
				}

				if(selecionado){
					for (int i = 0; i < arrayListComponentFoco.size(); i++) {
						Component componente = arrayListComponentFoco.get(i);

						//Redimensiona componente
						if(posMouse==NORTE_OESTE){ 
							componente.setBounds(componente.getX()+ arg0.getX(), componente.getY()+arg0.getY(), componente.getWidth()-arg0.getX(), componente.getHeight()-arg0.getY());
						}else if(posMouse==SUL_OESTE){ 
							componente.setBounds(componente.getX()+ arg0.getX(), componente.getY(), componente.getWidth()-arg0.getX(), componente.getHeight()+(arg0.getY()-componente.getHeight()));
						}else if(posMouse==SUL_LESTE){  
							componente.setSize(componente.getWidth()+(arg0.getX()-componente.getWidth()), componente.getHeight()+(arg0.getY()-componente.getHeight()));
						}else if(posMouse==NORTE_LESTE){  
							componente.setBounds(componente.getX(), componente.getY()+arg0.getY(), componente.getWidth()+(arg0.getX()-componente.getWidth()), componente.getHeight()-arg0.getY());
						}else if(posMouse==LESTE){  
							componente.setSize(componente.getWidth()+(arg0.getX()-componente.getWidth()), componente.getHeight());
						}else if(posMouse==SUL){
							componente.setSize(componente.getWidth(), componente.getHeight()+(arg0.getY()-componente.getHeight()));
						}else if(posMouse==OESTE){
							componente.setBounds(componente.getX() + arg0.getX(), componente.getY(), componente.getWidth()-arg0.getX(), componente.getHeight());
						}else if(posMouse==NORTE){
							componente.setBounds(componente.getX(), componente.getY() + arg0.getY(), componente.getWidth(), componente.getHeight()-arg0.getY());
						}else{
							//Ao clicar e segurar o componente, mover junto com o mouse
							componente.setLocation(componente.getX()+ arg0.getX()-posXMouse, componente.getY()+arg0.getY()-posYMouse);
						}

					}
				}
				jInternalFrame.repaint();

				if(!dragged){
					//Adiciona estado de modificação da janela
					jFramePrincipal.getjDesktopPaneAreaDeTrabalho().getJanela().adicionaEstadoDeJanela();
				}

				dragged=true;
			}
		}

	}
	//Representa evento de movimento do mouse
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(getjFramePrincipal().getjTetrisListPaletaDeObjetos().getSelectedValue().equals("Pointer")){
			if(!(arg0.getSource() instanceof JMenuBar) && !(arg0.getSource() instanceof JMenu) && !(arg0.getSource() instanceof JMenuItem)){
				Component componente = (Component)arg0.getSource();

				//Mostrando um cursor diferente de acordo com a posicao do mouse no componente
				if((arg0.getX() <= 3) && (arg0.getY() <= 3)){ 
					componente.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
				}else if((arg0.getX() <= 3) && (arg0.getY() >= componente.getHeight() - 3)){ 
					componente.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
				}else if((arg0.getX() >= componente.getWidth() - 3) && (arg0.getY() >= componente.getHeight() - 3)){ 
					componente.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
				}else if((arg0.getX() >= componente.getWidth() - 3) && (arg0.getY() <= 3)){ 
					componente.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
				}else if(arg0.getX() >= componente.getWidth() - 3){ 
					componente.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
				}else if(arg0.getY() >= componente.getHeight() - 3){
					componente.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
				}else if(arg0.getX() <= 3){
					componente.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
				}else if(arg0.getY() <= 3){
					componente.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
				}else{
					componente.setCursor(new Cursor(Cursor.MOVE_CURSOR));
				}
			}
		}
	}
	//Representa o evento de clicar com o mouse
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//Caso clicado com botão direito do mouse, exibe o PopupMenu de área de transferência
		if(arg0.getButton() == MouseEvent.BUTTON3){
			getjFramePrincipal().getjPopupMenuAreaDeTransferencia().show(getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getjInternalFrame(), ((Component)arg0.getSource()).getX()+arg0.getX(), ((Component)arg0.getSource()).getY()+arg0.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	//Representa o evento de pressionar o botão do mouse
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

		if(getjFramePrincipal().getjTetrisListPaletaDeObjetos().getSelectedValue().equals("Pointer")){
			//Ao pressionar o mouse, captura a acao a se fazer
			Component componente = (Component)arg0.getSource();
			//Definindo posição do mouse
			posXMouse=arg0.getX();
			posYMouse=arg0.getY();

			if((arg0.getX() <= 3) && (arg0.getY() <= 3)){ 
				posMouse=NORTE_OESTE;
			}else if((arg0.getX() <= 3) && (arg0.getY() >= componente.getHeight() - 3)){ 
				posMouse=SUL_OESTE;
			}else if((arg0.getX() >= componente.getWidth() - 3) && (arg0.getY() >= componente.getHeight() - 3)){ 
				posMouse=SUL_LESTE;
			}else if((arg0.getX() >= componente.getWidth() - 3) && (arg0.getY() <= 3)){ 
				posMouse=NORTE_LESTE;
			}else if(arg0.getX() >= componente.getWidth() - 3){ 
				posMouse=LESTE;
			}else if(arg0.getY() >= componente.getHeight() - 3){
				posMouse=SUL;
			}else if(arg0.getX() <= 3){
				posMouse=OESTE;
			}else if(arg0.getY() <= 3){
				posMouse=NORTE;
			}else{
				posMouse=CENTRO;
			}
		}
	}
	//Representa o evento de soltar o botão do mouse
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getButton() == MouseEvent.BUTTON1){
			if(getjFramePrincipal().getjTetrisListPaletaDeObjetos().getSelectedValue().equals("Pointer")){
				//Ao soltar o componente atualiza as posicoes X e Y

				Component componente = (Component)arg0.getSource();

				boolean manterAnteriores=false;
				if(!dragged){
					//Caso a tecla Shift esteja acionada, adicioa a lista de componentes em foco
					if(hashMapKeyPool.get(KeyEvent.VK_SHIFT)!=null){
						manterAnteriores=true;
					}

					jInternalFrame.setComponentFoco(componente, manterAnteriores);
				}
				jInternalFrame.repaint();
				//Atualizando informações do componente no projeto em memória, através do relacionamento
				ArrayList<RelacionamentoComponente> arrayListRelacionamentoComponentes = jInternalFrame.getArrayListRelacionamentoComponentes();
				ArrayList<Component> arrayListComponentFoco = getjInternalFrame().getArrayListComponentFoco();
				//Percorre a lista de componentes focados
				for (int x = 0; x < arrayListComponentFoco.size(); x++) {
					//Percorre a lista de relacionamento
					for (int i = 0; i < arrayListRelacionamentoComponentes.size(); i++) {
						if(arrayListRelacionamentoComponentes.get(i).getComponent().equals(arrayListComponentFoco.get(x))){
							//Atualiza atributos
							ArrayList<Atributo> arrayListAtributos = arrayListRelacionamentoComponentes.get(i).getComponente().getArrayListAtributos();
							for (int j = 0; j < arrayListAtributos.size(); j++) {
								if(arrayListAtributos.get(j).getNome().equals("x")){
									arrayListAtributos.get(j).setValor(""+arrayListRelacionamentoComponentes.get(i).getComponent().getX());
								}else if(arrayListAtributos.get(j).getNome().equals("y")){
									arrayListAtributos.get(j).setValor(""+arrayListRelacionamentoComponentes.get(i).getComponent().getY());
								}else if(arrayListAtributos.get(j).getNome().equals("width")){
									arrayListAtributos.get(j).setValor(""+arrayListRelacionamentoComponentes.get(i).getComponent().getWidth());
								}else if(arrayListAtributos.get(j).getNome().equals("height")){
									arrayListAtributos.get(j).setValor(""+arrayListRelacionamentoComponentes.get(i).getComponent().getHeight());
								}
							}
							//Define aba selecionada, caso o objeto seja um TabbedPane
							if(arrayListRelacionamentoComponentes.get(i).getComponente().getTipo().equals("Abas")){
								arrayListRelacionamentoComponentes.get(i).getComponente().mudarAtributo(new Atributo("SelectedIndex", "int", ""+((JTabbedPane)arrayListRelacionamentoComponentes.get(i).getComponent()).getSelectedIndex()));
							}

							//Atualiza Inspetor de Objetos
							if(x==arrayListComponentFoco.size()-1){
								getjFramePrincipal().getjTetrisListPropriedadesInspetorDeObjetos().preencherLista(arrayListRelacionamentoComponentes.get(i), getjFramePrincipal());
							}
							
							//Atualiza ToolTipText
							((JComponent)arrayListRelacionamentoComponentes.get(i).getComponent()).setToolTipText("<html><head></head><body><b>"+arrayListRelacionamentoComponentes.get(i).getComponente().getNome()+"</b><br/>x: "+arrayListRelacionamentoComponentes.get(i).getComponent().getX()+" y: "+arrayListRelacionamentoComponentes.get(i).getComponent().getY()+" width: "+arrayListRelacionamentoComponentes.get(i).getComponent().getWidth()+" height: "+arrayListRelacionamentoComponentes.get(i).getComponent().getHeight()+"</body></html>");
							

							break;
						}
					}

				}

				dragged=false;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	//Representa evento de tecla do teclado pressionada
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		//Adiciona ao objeto de mapeamento de teclas a tecla como pressionada
		hashMapKeyPool.put(e.getKeyCode(), true);

		//Fator que move os componentes na janela
		int velocidade=1;
		//Aponta para a lista de components em foco da JInternalFrame
		ArrayList<Component> arrayListComponentFoco = jInternalFrame.getArrayListComponentFoco();
		//Define direção de movimento através da tecla pressionada
		direcao="";
		if(e.getKeyCode() == KeyEvent.VK_UP){
			direcao="cima";
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			direcao="baixo";
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			direcao="esquerda";
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			direcao="direita";
		}

		if(hashMapKeyPool.get(KeyEvent.VK_CONTROL)!=null){
			//Se a tecla Control estiver pressionada, move os components em foco na JInternalFrame
			for (int i = 0; i < arrayListComponentFoco.size(); i++) {
				if(direcao.equals("cima")){
					arrayListComponentFoco.get(i).setLocation(arrayListComponentFoco.get(i).getX(), arrayListComponentFoco.get(i).getY()-velocidade);
					jInternalFrame.repaint();
				}else if(direcao.equals("baixo")){
					arrayListComponentFoco.get(i).setLocation(arrayListComponentFoco.get(i).getX(), arrayListComponentFoco.get(i).getY()+velocidade);
					jInternalFrame.repaint();
				}else if(direcao.equals("esquerda")){
					arrayListComponentFoco.get(i).setLocation(arrayListComponentFoco.get(i).getX()-velocidade, arrayListComponentFoco.get(i).getY());
					jInternalFrame.repaint();
				}else if(direcao.equals("direita")){
					arrayListComponentFoco.get(i).setLocation(arrayListComponentFoco.get(i).getX()+velocidade, arrayListComponentFoco.get(i).getY());
					jInternalFrame.repaint();
				}
			}
		}else if(hashMapKeyPool.get(KeyEvent.VK_SHIFT)!=null){
			//Se a tecla Shift estiver selecionada, redimensiona os components em foco na JInternalFrame
			for (int i = 0; i < arrayListComponentFoco.size(); i++) {
				if(direcao.equals("cima")){
					arrayListComponentFoco.get(i).setSize(arrayListComponentFoco.get(i).getWidth(), arrayListComponentFoco.get(i).getHeight()-velocidade);
					jInternalFrame.repaint();
				}else if(direcao.equals("baixo")){
					arrayListComponentFoco.get(i).setSize(arrayListComponentFoco.get(i).getWidth(), arrayListComponentFoco.get(i).getHeight()+velocidade);
					jInternalFrame.repaint();
				}else if(direcao.equals("esquerda")){
					arrayListComponentFoco.get(i).setSize(arrayListComponentFoco.get(i).getWidth()-velocidade, arrayListComponentFoco.get(i).getHeight());
					jInternalFrame.repaint();
				}else if(direcao.equals("direita")){
					arrayListComponentFoco.get(i).setSize(arrayListComponentFoco.get(i).getWidth()+velocidade, arrayListComponentFoco.get(i).getHeight());
					jInternalFrame.repaint();
				}
			}

		}

	}
	//Representa um evento de soltar a tecla
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		//Remove a tecla do mapa de teclas pressionadas
		hashMapKeyPool.remove(e.getKeyCode());
		//Atalhos
		if(e.getKeyCode() == KeyEvent.VK_DELETE){
			excluirComponentes();
		}else if(e.getKeyCode() == KeyEvent.VK_F9){
			getjFramePrincipal().getjPopupMenuProjeto().getjMenuItemExecutar().doClick();
		}else if(e.getKeyCode() == KeyEvent.VK_S){
			if(hashMapKeyPool.get(KeyEvent.VK_CONTROL)!=null){
				getjFramePrincipal().getjPopupMenuProjeto().getjMenuItemSalvar().doClick();
			}
		}else if(e.getKeyCode() == KeyEvent.VK_E){
			if(hashMapKeyPool.get(KeyEvent.VK_CONTROL)!=null){
				getjFramePrincipal().getjPopupMenuProjeto().getjMenuItemExportar().doClick();
			}
		}else if(e.getKeyCode() == KeyEvent.VK_C){
			if(hashMapKeyPool.get(KeyEvent.VK_CONTROL)!=null){
				copiarComponentes();
			}
		}else if(e.getKeyCode() == KeyEvent.VK_X){
			if(hashMapKeyPool.get(KeyEvent.VK_CONTROL)!=null){
				moverComponentes();
			}
		}else if(e.getKeyCode() == KeyEvent.VK_V){
			if(hashMapKeyPool.get(KeyEvent.VK_CONTROL)!=null){
				colarComponentes();
			}
		}else if(e.getKeyCode() == KeyEvent.VK_F){
			if(hashMapKeyPool.get(KeyEvent.VK_CONTROL)!=null){
				getjFramePrincipal().getjButtonComponentes().doClick();
			}
		}else if(e.getKeyCode() == KeyEvent.VK_Z){
			if(hashMapKeyPool.get(KeyEvent.VK_CONTROL)!=null){
				if(hashMapKeyPool.get(KeyEvent.VK_SHIFT)!=null){
					getjFramePrincipal().getjPopupMenuAreaDeTransferencia().getjMenuItemRefazer().doClick();
				}else{
					getjFramePrincipal().getjPopupMenuAreaDeTransferencia().getjMenuItemDesfazer().doClick();
				}
			}
		}else if(e.getKeyCode() == KeyEvent.VK_CONTEXT_MENU) {
			//Caso o botão ContextMenu do teclado seja pressionado, exibe PopupMenu da área de transferência
			getjFramePrincipal().getjPopupMenuAreaDeTransferencia().show(getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getjInternalFrame(), ((Component)e.getSource()).getX(), ((Component)e.getSource()).getY());
		}

		//Muda a posicao e tamanho do componente na memoria
		ArrayList<Component> arrayListComponentFoco = jInternalFrame.getArrayListComponentFoco();

		if(!direcao.equals("")){
			//Adiciona estado de modificação da janela
			jFramePrincipal.getjDesktopPaneAreaDeTrabalho().getJanela().adicionaEstadoDeJanela();
			//Atualizando posicoes na memoria
			ArrayList<RelacionamentoComponente> arrayListRelacionamentoComponentes = jInternalFrame.getArrayListRelacionamentoComponentes();

			for (int x = 0; x < arrayListComponentFoco.size(); x++) {
				//Atualiza os componentes do projeto na memória a partir do relacionamento
				for (int i = 0; i < arrayListRelacionamentoComponentes.size(); i++) {
					if(arrayListRelacionamentoComponentes.get(i).getComponent().equals(arrayListComponentFoco.get(x))){
						ArrayList<Atributo> arrayListAtributos = arrayListRelacionamentoComponentes.get(i).getComponente().getArrayListAtributos();
						for (int j = 0; j < arrayListAtributos.size(); j++) {
							if(arrayListAtributos.get(j).getNome().equals("x")){
								arrayListAtributos.get(j).setValor(""+arrayListRelacionamentoComponentes.get(i).getComponent().getX());
							}else if(arrayListAtributos.get(j).getNome().equals("y")){
								arrayListAtributos.get(j).setValor(""+arrayListRelacionamentoComponentes.get(i).getComponent().getY());
							}else if(arrayListAtributos.get(j).getNome().equals("width")){
								arrayListAtributos.get(j).setValor(""+arrayListRelacionamentoComponentes.get(i).getComponent().getWidth());
							}else if(arrayListAtributos.get(j).getNome().equals("height")){
								arrayListAtributos.get(j).setValor(""+arrayListRelacionamentoComponentes.get(i).getComponent().getHeight());
							}
						}

						//Atualiza Inspetor de Objetos
						if(x==arrayListComponentFoco.size()-1){
							getjFramePrincipal().getjTetrisListPropriedadesInspetorDeObjetos().preencherLista(arrayListRelacionamentoComponentes.get(i), getjFramePrincipal());
						}
						
						//Atualiza ToolTipText
						((JComponent)arrayListRelacionamentoComponentes.get(i).getComponent()).setToolTipText("<html><head></head><body><b>"+arrayListRelacionamentoComponentes.get(i).getComponente().getNome()+"</b><br/>x: "+arrayListRelacionamentoComponentes.get(i).getComponent().getX()+" y: "+arrayListRelacionamentoComponentes.get(i).getComponent().getY()+" width: "+arrayListRelacionamentoComponentes.get(i).getComponent().getWidth()+" height: "+arrayListRelacionamentoComponentes.get(i).getComponent().getHeight()+"</body></html>");

						break;
					}
				}

			}

			jInternalFrame.repaint();
		}

	}


}
