/*
 *Janela Interna utilizada para editar janelas na área de trabalho do TetrisIDE
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 27 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.janelas;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JToolBar;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.com.analisasoftware.tetris.modelo.Arquivo;
import br.com.analisasoftware.tetris.visao.componentes.JTetrisListPaletaDeObjetos;
import br.com.analisasoftware.tetris.visao.componentes.JTetrisPanelInternalFrame;
import componentes.visao.JTetrisPanel;
import tetris.modelo.componentes.Atributo;
import tetris.modelo.componentes.Componente;
import tetris.modelo.componentes.Janela;
import tetris.modelo.componentes.Metodo;
import tetris.modelo.componentes.RelacionamentoComponente;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class JInternalFrameJanela extends JInternalFrame implements ComponentListener, InternalFrameListener, KeyListener{
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Lista de relacionamentos entre componentes do projeto e components gráficos
	private ArrayList<RelacionamentoComponente> arrayListRelacionamentoComponentes;
	//Lista de components em foco
	private ArrayList<Component> arrayListComponentFoco;
	//Escutadores de Eventos
	private MouseListener mouseListener;
	private MouseMotionListener mouseMotionListener;
	//Variáveis auxiliares para manipulação de objetos com o mouse
	private int mouseXInicial;
	private int mouseYInicial;
	private int mouseXFinal;
	private int mouseYFinal;

	public JInternalFrameJanela(JFramePrincipal jFramePrincipal){
		JTetrisPanelInternalFrame tetrisPanelInternalFrame = new JTetrisPanelInternalFrame(this);
		setContentPane(tetrisPanelInternalFrame);

		mouseXInicial=-1;
		mouseYInicial=-1;

		mouseXFinal=-1;
		mouseYFinal=-1;

		arrayListComponentFoco = new ArrayList<Component>();

		mouseMotionListener = new MouseMotionAdapter() {
			//Representa movimento de segurar com o mouse
			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				super.mouseDragged(arg0);

				if(getMouseXInicial() >= 0) {
					//Define x e y final da selecao de componentes arrastada do mouse
					setMouseXFinal(arg0.getX());
					setMouseYFinal(arg0.getY());

					getContentPane().repaint();
				}
			}
		};
		mouseListener = new MouseAdapter() {
			//Representa evento de clique do mouse
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getButton() == MouseEvent.BUTTON1){
					//Adiciona um componente
					adicionarComponente(arg0);
				}else if(arg0.getButton() == MouseEvent.BUTTON3){
					//Mostra o PopupMenu da área de transferência
					getjFramePrincipal().getjPopupMenuAreaDeTransferencia().show(getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getjInternalFrame(), ((Component)arg0.getSource()).getX()+arg0.getX(), ((Component)arg0.getSource()).getY()+arg0.getY());
				}
			}
			//Representa realização de pressionamento do mouse
			@Override
			public void mouseReleased(MouseEvent arg0){
				//Caso o item Pointer da Paleta de Objetos esteja selecionado, muda o foco de do componente para a janela
				if(getjFramePrincipal().getjTetrisListPaletaDeObjetos().getSelectedValue().equals("Pointer")){
					if(arg0.getSource()==getContentPane()){
						setComponentFoco(getContentPane(), false);
					}
					repaint();
				}

				//Seleciona componentes pela selecao arrastada do mouse
				if(getMouseXFinal()!=-1) {
					Component[] components = getContentPane().getComponents();
					Rectangle rectangle1 = null;
					
					if(getMouseXFinal() > getMouseXInicial()) {
						if(getMouseYFinal() > getMouseYInicial()) {
							rectangle1 = new Rectangle(getMouseXInicial(), getMouseYInicial(), getMouseXFinal() - getMouseXInicial(), getMouseYFinal() - getMouseYInicial());
						}else if(getMouseYFinal() < getMouseYInicial()) {
							rectangle1 = new Rectangle(getMouseXInicial(), getMouseYFinal(), getMouseXFinal() - getMouseXInicial(), getMouseYInicial() - getMouseYFinal());
						}
					}else if(getMouseXFinal() < getMouseXInicial()) {
						if(getMouseYFinal() > getMouseYInicial()) {
							rectangle1 = new Rectangle(getMouseXFinal(), getMouseYInicial(), getMouseXInicial() - getMouseXFinal(), getMouseYFinal() - getMouseYInicial());
						}else if(getMouseYFinal() < getMouseYInicial()) {
							rectangle1 = new Rectangle(getMouseXFinal(), getMouseYFinal(), getMouseXInicial() - getMouseXFinal(), getMouseYInicial() - getMouseYFinal());
						}
					}
					Rectangle rectangle2 = new Rectangle();
					
					boolean passouPrimeiro=false;
					try {
						for (int i = 0; i < components.length; i++) {
							rectangle2.setBounds(components[i].getX(), components[i].getY(), components[i].getWidth(), components[i].getHeight());
							if(rectangle1.intersects(rectangle2)) {
								setComponentFoco(components[i], passouPrimeiro);
								passouPrimeiro=true;
							}
						}
					}catch(Exception exc) {
						
					}
				}

				setMouseXInicial(-1);
				setMouseYInicial(-1);
				setMouseXFinal(-1);
				setMouseYFinal(-1);
			}
			//Representa evento de pressionamento do mouse
			@Override
			public void mousePressed(MouseEvent arg0) {
				//Quando clica com o mouse na tela, modifica o x e y inicial de selecao arrastando o mouse
				setMouseXInicial(arg0.getX());
				setMouseYInicial(arg0.getY());
			}
		};

		tetrisPanelInternalFrame.addKeyListener(this);

		getContentPane().addMouseListener(mouseListener);
		getContentPane().addMouseMotionListener(mouseMotionListener);

		this.jFramePrincipal=jFramePrincipal;

		arrayListRelacionamentoComponentes=new ArrayList<RelacionamentoComponente>();

		addComponentListener(this);

		addInternalFrameListener(this);
	}
	//Inicializa e exibe janela
	public void init(){
		setVisible(true);
		setComponentFoco(getContentPane(), false);
		int widthAreaDeTrabalho=getjFramePrincipal().getjScrollPaneDesktopPaneAreaDeTrabalho().getWidth(), heightAreaDeTrabalho=getjFramePrincipal().getjScrollPaneDesktopPaneAreaDeTrabalho().getHeight();

		getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().setPreferredSize(new Dimension(widthAreaDeTrabalho, heightAreaDeTrabalho));

		ajustarScrollPane();
	}

	//Ajusta o scroll do scrollpane da area de trabalho
	public void ajustarScrollPane(){
		int widthAreaDeTrabalho=getjFramePrincipal().getjScrollPaneDesktopPaneAreaDeTrabalho().getWidth(), heightAreaDeTrabalho=getjFramePrincipal().getjScrollPaneDesktopPaneAreaDeTrabalho().getHeight();

		if(getWidth() + getX() > widthAreaDeTrabalho){
			widthAreaDeTrabalho = getWidth() + getX(); 
		}
		if(getHeight() + getY() > heightAreaDeTrabalho){
			heightAreaDeTrabalho = getHeight() + getY(); 
		}
		getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().setPreferredSize(new Dimension(widthAreaDeTrabalho, heightAreaDeTrabalho));
	}

	//Adicionando um componente à janela
	public void adicionarComponente(MouseEvent mouseEvent){
		//Captura a janela aberta na área de trabalho
		Janela janela = getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela();
		//Aponta para a JList da Paleta de Objetos
		JTetrisListPaletaDeObjetos jTetrisListPaletaDeObjetos = getjFramePrincipal().getjTetrisListPaletaDeObjetos();
		//Aponta para a lista de componentes da janela
		ArrayList<Componente> arrayListComponentes = janela.getArrayListComponentes();
		//Define a janela como componente que conterá o novo componente
		String pai=getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela().getNome();
		//Verifica se o mouse clicou em outro componente que não seja a janela
		if(!mouseEvent.getSource().equals(this)){
			//Percorre a lista de relacionamento
			for (int i = 0; i < arrayListRelacionamentoComponentes.size(); i++) {
				//Caso o componente clicado seja um Painel ou uma ToolBar, seta ele como pai
				if(mouseEvent.getSource() instanceof JTetrisPanel){
					if(((JTetrisPanel)mouseEvent.getSource()).equals(arrayListRelacionamentoComponentes.get(i).getComponent())){
						pai = arrayListRelacionamentoComponentes.get(i).getComponente().getNome();
						break;
					}
				}else if(mouseEvent.getSource() instanceof JToolBar){
					if(((JToolBar)mouseEvent.getSource()).equals(arrayListRelacionamentoComponentes.get(i).getComponent())){
						pai = arrayListRelacionamentoComponentes.get(i).getComponente().getNome();
						break;
					}
				}
			}
		}
		//Índice que compõe nome do novo componente
		int contagem=1;
		
		ArrayList<Atributo> arrayListAtributos;
		Componente componente;
		//Adiciona estado de modificação da janela
		if(!jTetrisListPaletaDeObjetos.getSelectedValue().equals("Pointer")){
			jFramePrincipal.getjDesktopPaneAreaDeTrabalho().getJanela().adicionaEstadoDeJanela();
		}
		
		switch (jTetrisListPaletaDeObjetos.getSelectedValue()) {
		//Adiciona um rótulo à janela
		case "Label":
			contagem=1;
			for (int i = 0; i < arrayListComponentes.size(); i++) {
				if(("jLabel"+contagem).equals(arrayListComponentes.get(i).getNome())){
					i=0;
					contagem++;
				}
			}

			String nome="jLabel"+contagem;

			arrayListAtributos = new ArrayList<Atributo>();
			arrayListAtributos.add(new Atributo("Tipo", "String", "Rotulo"));
			arrayListAtributos.add(new Atributo("Text", "String", nome));
			arrayListAtributos.add(new Atributo("x", "int", ""+mouseEvent.getX()));
			arrayListAtributos.add(new Atributo("y", "int", ""+mouseEvent.getY()));
			arrayListAtributos.add(new Atributo("width", "int", "100"));
			arrayListAtributos.add(new Atributo("height", "int", "20"));
			arrayListAtributos.add(new Atributo("Pai", "String", pai));

			componente = new Componente(nome, arrayListAtributos, new ArrayList<Metodo>());

			janela.adicionarComponente(componente, getjFramePrincipal());

			getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().atualizarComponentes(janela.getArrayListComponentes());

			break;

			//Adiciona um campo de texto a janela
		case "TextField":
			contagem=1;
			for (int i = 0; i < arrayListComponentes.size(); i++) {
				if(("jTextField"+contagem).equals(arrayListComponentes.get(i).getNome())){
					i=0;
					contagem++;
				}
			}

			nome="jTextField"+contagem;

			arrayListAtributos = new ArrayList<Atributo>();
			arrayListAtributos.add(new Atributo("Tipo", "String", "Campo de texto"));
			arrayListAtributos.add(new Atributo("Text", "String", nome));
			arrayListAtributos.add(new Atributo("x", "int", ""+mouseEvent.getX()));
			arrayListAtributos.add(new Atributo("y", "int", ""+mouseEvent.getY()));
			arrayListAtributos.add(new Atributo("width", "int", "100"));
			arrayListAtributos.add(new Atributo("height", "int", "20"));
			arrayListAtributos.add(new Atributo("Pai", "String", pai));

			componente = new Componente(nome, arrayListAtributos, new ArrayList<Metodo>());

			janela.adicionarComponente(componente, getjFramePrincipal());

			getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().atualizarComponentes(janela.getArrayListComponentes());

			break;
			//Adiciona uma caixa de combinação a janela 	
		case "ComboBox":
			contagem=1;
			for (int i = 0; i < arrayListComponentes.size(); i++) {
				if(("jComboBox"+contagem).equals(arrayListComponentes.get(i).getNome())){
					i=0;
					contagem++;
				}
			}

			nome="jComboBox"+contagem;

			arrayListAtributos = new ArrayList<Atributo>();
			arrayListAtributos.add(new Atributo("Tipo", "String", "Caixa de combinacao"));

			arrayListAtributos.add(new Atributo("x", "int", ""+mouseEvent.getX()));
			arrayListAtributos.add(new Atributo("y", "int", ""+mouseEvent.getY()));
			arrayListAtributos.add(new Atributo("width", "int", "100"));
			arrayListAtributos.add(new Atributo("height", "int", "20"));
			arrayListAtributos.add(new Atributo("Pai", "String", pai));

			componente = new Componente(nome, arrayListAtributos, new ArrayList<Metodo>());

			janela.adicionarComponente(componente, getjFramePrincipal());

			getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().atualizarComponentes(janela.getArrayListComponentes());

			break;
			//Adiciona uma lista a janela
		case "List":
			contagem=1;
			for (int i = 0; i < arrayListComponentes.size(); i++) {
				if(("jList"+contagem).equals(arrayListComponentes.get(i).getNome())){
					i=0;
					contagem++;
				}
			}

			nome="jList"+contagem;

			arrayListAtributos = new ArrayList<Atributo>();
			arrayListAtributos.add(new Atributo("Tipo", "String", "Lista"));

			arrayListAtributos.add(new Atributo("x", "int", ""+mouseEvent.getX()));
			arrayListAtributos.add(new Atributo("y", "int", ""+mouseEvent.getY()));
			arrayListAtributos.add(new Atributo("width", "int", "100"));
			arrayListAtributos.add(new Atributo("height", "int", "20"));
			arrayListAtributos.add(new Atributo("Pai", "String", pai));

			componente = new Componente(nome, arrayListAtributos, new ArrayList<Metodo>());

			janela.adicionarComponente(componente, getjFramePrincipal());

			getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().atualizarComponentes(janela.getArrayListComponentes());

			break;
			//Adiciona TextArea a janela
		case "TextArea":
			contagem=1;
			for (int i = 0; i < arrayListComponentes.size(); i++) {
				if(("jTextArea"+contagem).equals(arrayListComponentes.get(i).getNome())){
					i=0;
					contagem++;
				}
			}

			nome="jTextArea"+contagem;

			arrayListAtributos = new ArrayList<Atributo>();
			arrayListAtributos.add(new Atributo("Tipo", "String", "TextArea"));

			arrayListAtributos.add(new Atributo("x", "int", ""+mouseEvent.getX()));
			arrayListAtributos.add(new Atributo("y", "int", ""+mouseEvent.getY()));
			arrayListAtributos.add(new Atributo("width", "int", "100"));
			arrayListAtributos.add(new Atributo("height", "int", "100"));
			arrayListAtributos.add(new Atributo("Pai", "String", pai));

			componente = new Componente(nome, arrayListAtributos, new ArrayList<Metodo>());

			janela.adicionarComponente(componente, getjFramePrincipal());

			getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().atualizarComponentes(janela.getArrayListComponentes());

			break;

			//Adiciona EditorPane a janela
		case "EditorPane":
			contagem=1;
			for (int i = 0; i < arrayListComponentes.size(); i++) {
				if(("jEditorPane"+contagem).equals(arrayListComponentes.get(i).getNome())){
					i=0;
					contagem++;
				}
			}

			nome="jEditorPane"+contagem;

			arrayListAtributos = new ArrayList<Atributo>();
			arrayListAtributos.add(new Atributo("Tipo", "String", "EditorPane"));

			arrayListAtributos.add(new Atributo("x", "int", ""+mouseEvent.getX()));
			arrayListAtributos.add(new Atributo("y", "int", ""+mouseEvent.getY()));
			arrayListAtributos.add(new Atributo("width", "int", "100"));
			arrayListAtributos.add(new Atributo("height", "int", "100"));
			arrayListAtributos.add(new Atributo("Pai", "String", pai));

			componente = new Componente(nome, arrayListAtributos, new ArrayList<Metodo>());

			janela.adicionarComponente(componente, getjFramePrincipal());

			getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().atualizarComponentes(janela.getArrayListComponentes());

			break;

			//Adiciona um botao a janela
		case "Button":
			contagem=1;
			for (int i = 0; i < arrayListComponentes.size(); i++) {
				if(("jButton"+contagem).equals(arrayListComponentes.get(i).getNome())){
					i=0;
					contagem++;
				}
			}

			nome="jButton"+contagem;

			arrayListAtributos = new ArrayList<Atributo>();
			arrayListAtributos.add(new Atributo("Tipo", "String", "Botao"));
			arrayListAtributos.add(new Atributo("Text", "String", nome));
			arrayListAtributos.add(new Atributo("x", "int", ""+mouseEvent.getX()));
			arrayListAtributos.add(new Atributo("y", "int", ""+mouseEvent.getY()));
			arrayListAtributos.add(new Atributo("width", "int", "100"));
			arrayListAtributos.add(new Atributo("height", "int", "20"));
			arrayListAtributos.add(new Atributo("Pai", "String", pai));

			componente = new Componente(nome, arrayListAtributos, new ArrayList<Metodo>());

			janela.adicionarComponente(componente, getjFramePrincipal());

			getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().atualizarComponentes(janela.getArrayListComponentes());

			break;

			//Adiciona uma imagem à janela
		case "Image":

			//Escolhendo a imagem
			JFileChooser jFileChooser = new JFileChooser();  //Cria uma instância do JFileChooser  
			FileNameExtensionFilter filter = new FileNameExtensionFilter(  
					"PNG, JPG & GIF", "png", "jpeg", "jpg", "gif");  //Cria um filtro  
			jFileChooser.setFileFilter(filter);  //Altera o filtro do JFileChooser  
			int returnVal = jFileChooser.showOpenDialog(getjFramePrincipal()); //Abre o diálogo JFileChooser  
			if(returnVal == JFileChooser.APPROVE_OPTION) {  //Verifica se o usuário clicou no botão OK  

				if(Arquivo.criarPasta(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/")){
					//System.out.println(jFileChooser.getSelectedFile().getAbsolutePath());

					boolean adiciona=false;
					if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/"+jFileChooser.getSelectedFile().getName())==false){
						if(Arquivo.copiarArquivo(jFileChooser.getSelectedFile().getAbsolutePath(), System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/"+jFileChooser.getSelectedFile().getName())){
							adiciona=true;
						}
					}else{

						adiciona=true;
					}

					if(adiciona){

						contagem=1;
						for (int i = 0; i < arrayListComponentes.size(); i++) {
							if(("jLabel"+contagem).equals(arrayListComponentes.get(i).getNome())){
								i=0;
								contagem++;
							}
						}

						String nomeImagem="jLabel"+contagem;

						ImageIcon imageIcon = new ImageIcon(jFileChooser.getSelectedFile().getAbsolutePath());

						arrayListAtributos = new ArrayList<Atributo>();
						arrayListAtributos.add(new Atributo("Tipo", "String", "Rotulo"));
						arrayListAtributos.add(new Atributo("Text", "String", " "));
						arrayListAtributos.add(new Atributo("x", "int", ""+mouseEvent.getX()));
						arrayListAtributos.add(new Atributo("y", "int", ""+mouseEvent.getY()));
						arrayListAtributos.add(new Atributo("width", "int", ""+imageIcon.getIconWidth()));
						arrayListAtributos.add(new Atributo("height", "int", ""+imageIcon.getIconHeight()));
						arrayListAtributos.add(new Atributo("Icon", "String", jFileChooser.getSelectedFile().getName()));
						arrayListAtributos.add(new Atributo("Pai", "String", pai));

						componente = new Componente(nomeImagem, arrayListAtributos, new ArrayList<Metodo>());

						janela.adicionarComponente(componente, getjFramePrincipal());

						getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().atualizarComponentes(janela.getArrayListComponentes());
					}
				}
			}
			break;

			//Adiciona um campo de checagem a janela
		case "CheckBox":
			contagem=1;
			for (int i = 0; i < arrayListComponentes.size(); i++) {
				if(("jCheckBox"+contagem).equals(arrayListComponentes.get(i).getNome())){
					i=0;
					contagem++;
				}
			}

			nome="jCheckBox"+contagem;

			arrayListAtributos = new ArrayList<Atributo>();
			arrayListAtributos.add(new Atributo("Tipo", "String", "Campo de checagem"));
			arrayListAtributos.add(new Atributo("Title", "String", nome));
			arrayListAtributos.add(new Atributo("x", "int", ""+mouseEvent.getX()));
			arrayListAtributos.add(new Atributo("y", "int", ""+mouseEvent.getY()));
			arrayListAtributos.add(new Atributo("width", "int", "100"));
			arrayListAtributos.add(new Atributo("height", "int", "20"));
			arrayListAtributos.add(new Atributo("Pai", "String", pai));

			componente = new Componente(nome, arrayListAtributos, new ArrayList<Metodo>());

			janela.adicionarComponente(componente, getjFramePrincipal());

			getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().atualizarComponentes(janela.getArrayListComponentes());

			break;
			//Adiciona Painel a Janela
		case "Panel":
			contagem=1;
			for (int i = 0; i < arrayListComponentes.size(); i++) {
				if(("jPanel"+contagem).equals(arrayListComponentes.get(i).getNome())){
					i=0;
					contagem++;
				}
			}

			nome="jPanel"+contagem;

			arrayListAtributos = new ArrayList<Atributo>();
			arrayListAtributos.add(new Atributo("Tipo", "String", "Painel"));
			arrayListAtributos.add(new Atributo("x", "int", ""+mouseEvent.getX()));
			arrayListAtributos.add(new Atributo("y", "int", ""+mouseEvent.getY()));
			arrayListAtributos.add(new Atributo("width", "int", "150"));
			arrayListAtributos.add(new Atributo("height", "int", "150"));
			arrayListAtributos.add(new Atributo("Pai", "String", pai));

			componente = new Componente(nome, arrayListAtributos, new ArrayList<Metodo>());

			janela.adicionarComponente(componente, getjFramePrincipal());

			getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().atualizarComponentes(janela.getArrayListComponentes());

			break;
			//Adiciona ToolBar a janela
		case "ToolBar":
			contagem=1;
			for (int i = 0; i < arrayListComponentes.size(); i++) {
				if(("jToolBar"+contagem).equals(arrayListComponentes.get(i).getNome())){
					i=0;
					contagem++;
				}
			}

			nome="jToolBar"+contagem;

			arrayListAtributos = new ArrayList<Atributo>();
			arrayListAtributos.add(new Atributo("Tipo", "String", "Barra de ferramentas"));
			arrayListAtributos.add(new Atributo("x", "int", ""+mouseEvent.getX()));
			arrayListAtributos.add(new Atributo("y", "int", ""+mouseEvent.getY()));
			arrayListAtributos.add(new Atributo("width", "int", "100"));
			arrayListAtributos.add(new Atributo("height", "int", "50"));
			arrayListAtributos.add(new Atributo("Pai", "String", pai));

			componente = new Componente(nome, arrayListAtributos, new ArrayList<Metodo>());

			janela.adicionarComponente(componente, getjFramePrincipal());

			getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().atualizarComponentes(janela.getArrayListComponentes());

			break;
			//Adiciona TabbedPane a janela
		case "TabbedPane":
			contagem=1;
			for (int i = 0; i < arrayListComponentes.size(); i++) {
				if(("jTabbedPane"+contagem).equals(arrayListComponentes.get(i).getNome())){
					i=0;
					contagem++;
				}
			}

			nome="jTabbedPane"+contagem;

			arrayListAtributos = new ArrayList<Atributo>();
			arrayListAtributos.add(new Atributo("Tipo", "String", "Abas"));
			arrayListAtributos.add(new Atributo("x", "int", ""+mouseEvent.getX()));
			arrayListAtributos.add(new Atributo("y", "int", ""+mouseEvent.getY()));
			arrayListAtributos.add(new Atributo("width", "int", "150"));
			arrayListAtributos.add(new Atributo("height", "int", "150"));
			arrayListAtributos.add(new Atributo("Pai", "String", pai));

			componente = new Componente(nome, arrayListAtributos, new ArrayList<Metodo>());

			janela.adicionarComponente(componente, getjFramePrincipal());

			getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().atualizarComponentes(janela.getArrayListComponentes());

			break;
			//Adiciona Tabela a janela
		case "Table":
			contagem=1;
			for (int i = 0; i < arrayListComponentes.size(); i++) {
				if(("jTable"+contagem).equals(arrayListComponentes.get(i).getNome())){
					i=0;
					contagem++;
				}
			}

			nome="jTable"+contagem;

			arrayListAtributos = new ArrayList<Atributo>();
			arrayListAtributos.add(new Atributo("Tipo", "String", "Tabela"));
			arrayListAtributos.add(new Atributo("x", "int", ""+mouseEvent.getX()));
			arrayListAtributos.add(new Atributo("y", "int", ""+mouseEvent.getY()));
			arrayListAtributos.add(new Atributo("width", "int", "150"));
			arrayListAtributos.add(new Atributo("height", "int", "150"));
			arrayListAtributos.add(new Atributo("Pai", "String", pai));

			componente = new Componente(nome, arrayListAtributos, new ArrayList<Metodo>());

			janela.adicionarComponente(componente,getjFramePrincipal());

			getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().atualizarComponentes(janela.getArrayListComponentes());

			break;
			//Adiciona RadioButton a janela
		case "RadioButton":
			contagem=1;
			for (int i = 0; i < arrayListComponentes.size(); i++) {
				if(("jRadioButton"+contagem).equals(arrayListComponentes.get(i).getNome())){
					i=0;
					contagem++;
				}
			}

			nome="jRadioButton"+contagem;

			arrayListAtributos = new ArrayList<Atributo>();
			arrayListAtributos.add(new Atributo("Tipo", "String", "Campo de escolha"));
			arrayListAtributos.add(new Atributo("Text", "String", nome));
			arrayListAtributos.add(new Atributo("x", "int", ""+mouseEvent.getX()));
			arrayListAtributos.add(new Atributo("y", "int", ""+mouseEvent.getY()));
			arrayListAtributos.add(new Atributo("width", "int", "150"));
			arrayListAtributos.add(new Atributo("height", "int", "150"));
			arrayListAtributos.add(new Atributo("Pai", "String", pai));

			componente = new Componente(nome, arrayListAtributos, new ArrayList<Metodo>());

			janela.adicionarComponente(componente, getjFramePrincipal());

			getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().atualizarComponentes(janela.getArrayListComponentes());

			break;
			//Adiciona Barra de menu a janela
		case "Menu Bar":
			contagem=1;
			for (int i = 0; i < arrayListComponentes.size(); i++) {
				if(("jMenuBar"+contagem).equals(arrayListComponentes.get(i).getNome())){
					i=0;
					contagem++;
				}
			}

			nome="jMenuBar"+contagem;

			arrayListAtributos = new ArrayList<Atributo>();
			arrayListAtributos.add(new Atributo("Tipo", "String", "Barra de menu"));


			componente = new Componente(nome, arrayListAtributos, new ArrayList<Metodo>());

			janela.adicionarComponente(componente, getjFramePrincipal());

			getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().atualizarComponentes(janela.getArrayListComponentes());

			break;
			//Adiciona Timer a janela
		case "Timer":
			contagem=1;
			for (int i = 0; i < arrayListComponentes.size(); i++) {
				if(("timer"+contagem).equals(arrayListComponentes.get(i).getNome())){
					i=0;
					contagem++;
				}
			}

			nome="timer"+contagem;

			arrayListAtributos = new ArrayList<Atributo>();
			arrayListAtributos.add(new Atributo("Tipo", "String", "Timer"));
			arrayListAtributos.add(new Atributo("Delay", "int", "1000"));
			arrayListAtributos.add(new Atributo("x", "int", ""+mouseEvent.getX()));
			arrayListAtributos.add(new Atributo("y", "int", ""+mouseEvent.getY()));
			arrayListAtributos.add(new Atributo("width", "int", "20"));
			arrayListAtributos.add(new Atributo("height", "int", "20"));


			componente = new Componente(nome, arrayListAtributos, new ArrayList<Metodo>());

			janela.adicionarComponente(componente, getjFramePrincipal());

			getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().atualizarComponentes(janela.getArrayListComponentes());

			break;
			//Adiciona procedure a janela
		case "Procedure":
			contagem=1;
			for (int i = 0; i < arrayListComponentes.size(); i++) {
				if(("procedure"+contagem).equals(arrayListComponentes.get(i).getNome())){
					i=0;
					contagem++;
				}
			}

			nome="procedure"+contagem;

			arrayListAtributos = new ArrayList<Atributo>();
			arrayListAtributos.add(new Atributo("Tipo", "String", "Procedure"));
			arrayListAtributos.add(new Atributo("x", "int", ""+mouseEvent.getX()));
			arrayListAtributos.add(new Atributo("y", "int", ""+mouseEvent.getY()));
			arrayListAtributos.add(new Atributo("width", "int", "20"));
			arrayListAtributos.add(new Atributo("height", "int", "20"));


			componente = new Componente(nome, arrayListAtributos, new ArrayList<Metodo>());

			janela.adicionarComponente(componente, getjFramePrincipal());

			getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().atualizarComponentes(janela.getArrayListComponentes());

			break;
			//Adiciona variável a janela
		case "Variable":
			contagem=1;
			for (int i = 0; i < arrayListComponentes.size(); i++) {
				if(("variable"+contagem).equals(arrayListComponentes.get(i).getNome())){
					i=0;
					contagem++;
				}
			}

			nome="variable"+contagem;

			arrayListAtributos = new ArrayList<Atributo>();
			arrayListAtributos.add(new Atributo("Tipo", "String", "Variable"));
			arrayListAtributos.add(new Atributo("Type", "String", "String"));
			arrayListAtributos.add(new Atributo("Value", "String", ""));
			arrayListAtributos.add(new Atributo("x", "int", ""+mouseEvent.getX()));
			arrayListAtributos.add(new Atributo("y", "int", ""+mouseEvent.getY()));
			arrayListAtributos.add(new Atributo("width", "int", "20"));
			arrayListAtributos.add(new Atributo("height", "int", "20"));


			componente = new Componente(nome, arrayListAtributos, new ArrayList<Metodo>());

			janela.adicionarComponente(componente, getjFramePrincipal());

			getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().atualizarComponentes(janela.getArrayListComponentes());

			break;
			//Adiciona Relatório a janela
		case "Report":
			contagem=1;
			for (int i = 0; i < arrayListComponentes.size(); i++) {
				if(("report"+contagem).equals(arrayListComponentes.get(i).getNome())){
					i=0;
					contagem++;
				}
			}

			nome="report"+contagem;

			arrayListAtributos = new ArrayList<Atributo>();
			arrayListAtributos.add(new Atributo("Tipo", "String", "Relatorio"));
			arrayListAtributos.add(new Atributo("x", "int", ""+mouseEvent.getX()));
			arrayListAtributos.add(new Atributo("y", "int", ""+mouseEvent.getY()));
			arrayListAtributos.add(new Atributo("width", "int", "20"));
			arrayListAtributos.add(new Atributo("height", "int", "20"));
			arrayListAtributos.add(new Atributo("Header", "String", "<h3>\n"
					+ "	Report1\n"
					+ "</h3>\n"));
			arrayListAtributos.add(new Atributo("Detail", "String", "<p>\n"
					+ "	Row 1\n"
					+ "</p>\n"));
			arrayListAtributos.add(new Atributo("Sumary", "String", "<p>\n"
					+ "	Sum 0.00\n"
					+ "</p>\n"));


			componente = new Componente(nome, arrayListAtributos, new ArrayList<Metodo>());

			janela.adicionarComponente(componente, getjFramePrincipal());

			getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().atualizarComponentes(janela.getArrayListComponentes());

			break;

		default:
			break;
		}

		if(jTetrisListPaletaDeObjetos.getSelectedIndex() > 0){
			setComponentFoco(getArrayListRelacionamentoComponentes().get(getArrayListRelacionamentoComponentes().size()-1).getComponent(), false);
		}

		jTetrisListPaletaDeObjetos.setSelectedIndex(0);


	}

	//Altera os atributos de posicoes e dimensoes da janela
	public void mudaPosicaoJanela(){
		Janela janela = getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela();

		ArrayList<Atributo> arrayListAtributos = janela.getArrayListAtributos();
		boolean xOk=false, yOk=false, widthOk=false, heightOk=false;
		for (int j = 0; j < arrayListAtributos.size(); j++) {
			if(arrayListAtributos.get(j).getNome().equals("x")){
				arrayListAtributos.get(j).setValor(""+getX());
				xOk=true;
			}else if(arrayListAtributos.get(j).getNome().equals("y")){
				arrayListAtributos.get(j).setValor(""+getY());
				yOk=true;
			}else if(arrayListAtributos.get(j).getNome().equals("width")){
				arrayListAtributos.get(j).setValor(""+getWidth());
				widthOk=true;
			}else if(arrayListAtributos.get(j).getNome().equals("height")){
				arrayListAtributos.get(j).setValor(""+getHeight());
				heightOk=true;
			}
		}

		//Caso nao haja estes atributos, cria
		if(!xOk){
			arrayListAtributos.add(new Atributo("x", "int", ""+getX()));
		}else if(!yOk){
			arrayListAtributos.add(new Atributo("y", "int", ""+getY()));
		}else if(!widthOk){
			arrayListAtributos.add(new Atributo("width", "int", ""+getWidth()));
		}else if(!heightOk){
			arrayListAtributos.add(new Atributo("height", "int", ""+getHeight()));
		}
	}
	//Getters e Setters
	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}

	public Component getComponentFoco() {
		if(arrayListComponentFoco.size() > 0){
			return arrayListComponentFoco.get(arrayListComponentFoco.size()-1);
		}else{
			return null;
		}
	}
	//Seta components focados
	public void setComponentFoco(Component componentFoco, boolean manterAnteriores) {
		if(!manterAnteriores){
			arrayListComponentFoco.clear();
		}
		boolean adicionado=false;
		for (int i = 0; i < arrayListComponentFoco.size(); i++) {
			if(componentFoco.equals(arrayListComponentFoco.get(i))){
				adicionado=true;
				break;
			}
		}

		if(!adicionado){
			arrayListComponentFoco.add(componentFoco);
		}
		if(componentFoco.equals(getContentPane())){
			getjFramePrincipal().getjTetrisListPropriedadesInspetorDeObjetos().preencherLista(new RelacionamentoComponente(componentFoco, getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela()), getjFramePrincipal());
			getjFramePrincipal().getjTetrisListEventosInspetorDeObjetos().preencherLista(new RelacionamentoComponente(componentFoco, getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela()), getjFramePrincipal());
		}else{
			for (int i = 0; i < arrayListRelacionamentoComponentes.size(); i++) {
				if(componentFoco.equals(arrayListRelacionamentoComponentes.get(i).getComponent())){
					getjFramePrincipal().getjTetrisListPropriedadesInspetorDeObjetos().preencherLista(arrayListRelacionamentoComponentes.get(i), getjFramePrincipal());
					getjFramePrincipal().getjTetrisListEventosInspetorDeObjetos().preencherLista(arrayListRelacionamentoComponentes.get(i), getjFramePrincipal());
				}
			}
		}

	}

	public ArrayList<RelacionamentoComponente> getArrayListRelacionamentoComponentes() {
		return arrayListRelacionamentoComponentes;
	}

	public void setArrayListRelacionamentoComponentes(
			ArrayList<RelacionamentoComponente> arrayListRelacionamentoComponentes) {
		this.arrayListRelacionamentoComponentes = arrayListRelacionamentoComponentes;
	}



	public MouseListener getMouseListener() {
		return mouseListener;
	}

	public ArrayList<Component> getArrayListComponentFoco() {
		return arrayListComponentFoco;
	}

	public int getMouseXInicial() {
		return mouseXInicial;
	}

	public void setMouseXInicial(int mouseXInicial) {
		this.mouseXInicial = mouseXInicial;
	}

	public int getMouseYInicial() {
		return mouseYInicial;
	}

	public void setMouseYInicial(int mouseYInicial) {
		this.mouseYInicial = mouseYInicial;
	}

	public int getMouseXFinal() {
		return mouseXFinal;
	}

	public void setMouseXFinal(int mouseXFinal) {
		this.mouseXFinal = mouseXFinal;
	}

	public int getMouseYFinal() {
		return mouseYFinal;
	}

	public void setMouseYFinal(int mouseYFinal) {
		this.mouseYFinal = mouseYFinal;
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		//Altera o tamanho da janela
		mudaPosicaoJanela();
		ajustarScrollPane();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		//Altera a posicao da janela
		mudaPosicaoJanela();

		ajustarScrollPane();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		//Limpa listas do Inspetor de Objetos
		getjFramePrincipal().getjTetrisListPropriedadesInspetorDeObjetos().preencherLista(null, jFramePrincipal);
		getjFramePrincipal().getjTetrisListEventosInspetorDeObjetos().preencherLista(null, jFramePrincipal);
		getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().setJanela(null);

		int widthAreaDeTrabalho=getjFramePrincipal().getjScrollPaneDesktopPaneAreaDeTrabalho().getWidth(), heightAreaDeTrabalho=getjFramePrincipal().getjScrollPaneDesktopPaneAreaDeTrabalho().getHeight();


		getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().setPreferredSize(new Dimension(widthAreaDeTrabalho, heightAreaDeTrabalho));
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent arg0) {

	}

	public void keyReleased(KeyEvent arg0){

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
