/*
 *Janela para manipular Menus e Itens de Menus de uma janela do projeto
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 27 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.janelas;

import javax.swing.JDialog;

import componentes.visao.JTetrisButton;
import componentes.visao.JTetrisPanel;
import componentes.visao.JTetrisTextField;
import tetris.modelo.componentes.Atributo;
import tetris.modelo.componentes.Componente;
import tetris.modelo.componentes.Janela;
import tetris.modelo.componentes.Metodo;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.analisasoftware.tetris.modelo.Idioma;

import javax.swing.DefaultComboBoxModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class JDialogEditarMenu extends JDialog {
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Componente pai
	private String pai;
	//Componentes gráficos
	private JTetrisTextField jTextFieldNome;
	private JTetrisTextField jTextFieldTexto;
	private JComboBox<String> jComboBoxTipo;
	private JTetrisButton jButtonAdicionar;
	private JTetrisButton jButtonExcluir;
	private JTetrisButton jButtonFechar;
	private JList<String> jListItens;
	private JTetrisButton jButtonEditarSubitens;
	private JButton jButtonMoverCima;
	private JButton jButtonMoverBaixo;
	private JLabel jLabelTipo;
	private JLabel jLabelNome;
	private JLabel jLabelTexto;

	public JDialogEditarMenu(JFramePrincipal jFramePrincipal, String pai) {
		this.pai=pai;
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogEditarMenu.class.getResource("/br/com/analisasoftware/tetris/imagem/icone.png")));
		setModal(true);
		setResizable(false);
		setTitle("Editar Menu");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.jFramePrincipal=jFramePrincipal;
		JTetrisPanel tetrisPanel = new JTetrisPanel();
		setContentPane(tetrisPanel);

		jLabelTipo = new JLabel("Tipo");
		jLabelTipo.setBounds(12, 13, 109, 16);
		tetrisPanel.add(jLabelTipo);

		jComboBoxTipo = new JComboBox<String>();
		jComboBoxTipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Gera um novo nome para o menu ou item
				gerarNome();
			}
		});
		jComboBoxTipo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					jComboBoxTipo.transferFocus();
				}
			}
		});
		jComboBoxTipo.setModel(new DefaultComboBoxModel<String>(new String[] {"Menu", "Menu Item", "Separator"}));
		jComboBoxTipo.setBounds(12, 32, 233, 25);
		tetrisPanel.add(jComboBoxTipo);

		jTextFieldNome = new JTetrisTextField();
		jTextFieldNome.setBounds(257, 32, 225, 25);
		tetrisPanel.add(jTextFieldNome);
		jTextFieldNome.setColumns(10);
		jTextFieldNome.setMascara(JTetrisTextField.MASCARA_SEM_ESPACOS);

		jLabelNome = new JLabel("Nome");
		jLabelNome.setBounds(257, 13, 104, 16);
		tetrisPanel.add(jLabelNome);

		jLabelTexto = new JLabel("Texto");
		jLabelTexto.setBounds(12, 70, 55, 16);
		tetrisPanel.add(jLabelTexto);

		jTextFieldTexto = new JTetrisTextField();
		jTextFieldTexto.setBounds(12, 92, 325, 25);
		tetrisPanel.add(jTextFieldTexto);
		jTextFieldTexto.setColumns(10);

		jButtonAdicionar = new JTetrisButton("Adicionar");
		jButtonAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Adiciona um menu ou item
				//Verifica se o nome foi digitado
				if(jTextFieldNome.getText().equals("")==false){
					//Aponta para a lista de componentes da janela
					ArrayList<Componente> arrayListComponentes = getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela().getArrayListComponentes();
					boolean insere=true;
					//Verifica se o nome já está sendo usado
					for (int i = 0; i < arrayListComponentes.size(); i++) {
						if((jTextFieldNome.getText()).equals(arrayListComponentes.get(i).getNome())){
							insere=false;
							break;
						}
					}
					//Insere o componente
					if(insere){
						//Define atributos
						ArrayList<Atributo> arrayListAtributos = new ArrayList<Atributo>();
						arrayListAtributos.add(new Atributo("Nome", "String", jTextFieldNome.getText()));
						if(jComboBoxTipo.getSelectedIndex()!=2){
							arrayListAtributos.add(new Atributo("Text", "String", jTextFieldTexto.getText()));
						}
						arrayListAtributos.add(new Atributo("Tipo", "String", ""+jComboBoxTipo.getSelectedItem()));
						arrayListAtributos.add(new Atributo("Pai", "String", getPai()));
						//Adiciona  o componente
						Componente componente = new Componente(jTextFieldNome.getText(), arrayListAtributos, new ArrayList<Metodo>());
						arrayListComponentes.add(componente);
						//Atualiza lista
						preencherLista();
						//Gera um novo nome
						gerarNome();
					}else{
						JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_add_component", getjFramePrincipal()));
					}
				}else{
					JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_fill_in_the_name_field", getjFramePrincipal()));
				}
			}
		});
		jButtonAdicionar.setIcon(new ImageIcon(JDialogEditarMenu.class.getResource("/br/com/analisasoftware/tetris/imagem/sim.png")));
		jButtonAdicionar.setBounds(349, 92, 133, 25);
		jButtonAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jButtonAdicionar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 125, 425, 194);
		tetrisPanel.add(scrollPane);

		jListItens = new JList<String>();
		jListItens.setBackground(SystemColor.menu);
		jListItens.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//Edita subitens do menu selecionado
				if(arg0.getClickCount() == 2){
					jButtonEditarSubitens.doClick();
				}
			}
		});
		jListItens.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setViewportView(jListItens);
		jListItens.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
				//Ao selecionar um item, ativa botão editar ou não
				ativarBotaoEditar();
			}
		});

		jButtonExcluir = new JTetrisButton("Excluir");
		jButtonExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Remove menu ou item selecionado
				//Verifica se há item selecionado
				if(jListItens.getModel().getSize()>0){
					//Aponta para a instância da janela aberta na área de trabalho
					Janela janela = getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela();
					//Aponta para os filhos do componente a ser removido
					ArrayList<Componente> arrayListComponentesFilhos = getjFramePrincipal().getAreaDeTransferencia().bufferizaComponentesFilhos(""+jListItens.getSelectedValue().split(" - ")[0]); 
					
					//Percorre a lista
					for (int j = 0; j < arrayListComponentesFilhos.size(); j++) {
						//Remove cada um
						janela.getArrayListComponentes().remove(arrayListComponentesFilhos.get(j));
					}
					//Remove o item ou menu selecionado
					janela.removerComponente(""+jListItens.getSelectedValue().split(" - ")[0]);
					//Atualiza lista
					preencherLista();
				}else{
					JDialogMensagem.exibirMensagem("Erro", Idioma.getTraducao("tetris.message_remove", getjFramePrincipal()));
				}
			}
		});
		jButtonExcluir.setIcon(new ImageIcon(JDialogEditarMenu.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_excluir.png")));
		jButtonExcluir.setBounds(12, 331, 124, 26);
		jButtonExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jButtonExcluir);

		jButtonFechar = new JTetrisButton("Fechar");
		jButtonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Fecha janela
				dispose();
			}
		});
		jButtonFechar.setIcon(new ImageIcon(JDialogEditarMenu.class.getResource("/br/com/analisasoftware/tetris/imagem/sair.png")));
		jButtonFechar.setBounds(373, 331, 109, 26);
		jButtonFechar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jButtonFechar);

		jButtonEditarSubitens = new JTetrisButton("Editar Subitens");
		jButtonEditarSubitens.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Edita subitens
				String[] menu = jListItens.getSelectedValue().split(" - ");
				if(menu[2].equals("Menu")){
					new JDialogEditarMenu(getjFramePrincipal(), menu[0]).init();
				}
			}
		});
		jButtonEditarSubitens.setBounds(148, 331, 213, 26);
		jButtonEditarSubitens.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jButtonEditarSubitens);

		jButtonMoverCima = new JButton("");
		jButtonMoverCima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Movendo um menu para cima
				//Verifica se a posição selecionada é maior que zero
				if(jListItens.getSelectedIndex() > 0){
					//Aponta para a janela aberta na área de trabalho
					Janela janela = getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela();
					//Aponta para a lista de componentes da janela
					ArrayList<Componente> arrayListComponentes = janela.getArrayListComponentes();
					//Clona e armazena o componente selecionado
					Componente componente1 = janela.getComponente(""+jListItens.getSelectedValue().split(" - ")[0]).clone();
					//Muda a posição no JList para cima
					jListItens.setSelectedIndex(jListItens.getSelectedIndex()-1);
					//Clona e armazena o outro componente selecionado 
					Componente componente2 = janela.getComponente(""+jListItens.getSelectedValue().split(" - ")[0]).clone();
					//Percorre a lista de componentes
					for(int i=0; i < arrayListComponentes.size(); i++){
						//Efetua a troca dos componentes, colocando o de cima em baixo e o de baixo em cima
						if(arrayListComponentes.get(i).getNome().equals(componente2.getNome())){
							janela.getArrayListComponentes().set(i, componente1);
						}else if(arrayListComponentes.get(i).getNome().equals(componente1.getNome())){
							janela.getArrayListComponentes().set(i, componente2);
						}
					}
					//Captura a posição
					int posicao = jListItens.getSelectedIndex();
					//Atualiza a lista
					preencherLista();
					//Define a posição capturada
					jListItens.setSelectedIndex(posicao);
				}
			}
		});
		jButtonMoverCima.setToolTipText("Mover componente para cima");
		jButtonMoverCima.setIcon(new ImageIcon(JDialogEditarMenu.class.getResource("/br/com/analisasoftware/tetris/imagem/seta_cima.png")));
		jButtonMoverCima.setBounds(449, 127, 33, 33);
		jButtonMoverCima.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jButtonMoverCima);

		jButtonMoverBaixo = new JButton("");
		jButtonMoverBaixo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Movendo um menu para baixo
				//Verifica se a seleção está em uma posição válida
				if(jListItens.getSelectedIndex() < jListItens.getModel().getSize()-1){
					//Aponta para a janela aberta na área de trabalho
					Janela janela = getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela();
					//Aponta para a lista de componentes da janela
					ArrayList<Componente> arrayListComponentes = janela.getArrayListComponentes();
					//Clona e armazena o componente selecionado
					Componente componente1 = janela.getComponente(""+jListItens.getSelectedValue().split(" - ")[0]).clone();
					//Muda uma posição para baixo
					jListItens.setSelectedIndex(jListItens.getSelectedIndex()+1);
					//Clona e armazena o outro componente
					Componente componente2 = janela.getComponente(""+jListItens.getSelectedValue().split(" - ")[0]).clone();
					//Percorre a lista de componentes
					for(int i=0; i < arrayListComponentes.size(); i++){
						//Efetua a troca entre os componentes, colocando o de cima em baixo e o de baixo em cima
						if(arrayListComponentes.get(i).getNome().equals(componente2.getNome())){
							janela.getArrayListComponentes().set(i, componente1);
						}else if(arrayListComponentes.get(i).getNome().equals(componente1.getNome())){
							janela.getArrayListComponentes().set(i, componente2);
						}
					}
					//Captura a posição selecionada
					int posicao = jListItens.getSelectedIndex();
					//Atualiza a lista
					preencherLista();
					//Define a posição capturada
					jListItens.setSelectedIndex(posicao);
				}
			}
		});
		jButtonMoverBaixo.setToolTipText("Mover componente para baixo");
		jButtonMoverBaixo.setIcon(new ImageIcon(JDialogEditarMenu.class.getResource("/br/com/analisasoftware/tetris/imagem/seta_baixo.png")));
		jButtonMoverBaixo.setBounds(449, 172, 33, 33);
		jButtonMoverBaixo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jButtonMoverBaixo);
		setSize(500, 400);
		setLocationRelativeTo(null);


	}

	//Carrega idioma nos componentes
	public void carregarIdioma(){
		setTitle(Idioma.getTraducao("tetris.menu_editor", jFramePrincipal));
		jLabelTipo.setText(Idioma.getTraducao("tetris.type", jFramePrincipal));
		jLabelNome.setText(Idioma.getTraducao("tetris.name", jFramePrincipal));
		jLabelTexto.setText(Idioma.getTraducao("tetris.text", jFramePrincipal));
		jButtonAdicionar.setText(Idioma.getTraducao("tetris.add", jFramePrincipal));
		jButtonExcluir.setText(Idioma.getTraducao("tetris.remove", jFramePrincipal));
		jButtonFechar.setText(Idioma.getTraducao("tetris.close", jFramePrincipal));
		jButtonEditarSubitens.setText(Idioma.getTraducao("tetris.edit_subitens", jFramePrincipal));
		jButtonMoverCima.setToolTipText(Idioma.getTraducao("tetris.move_up_component", jFramePrincipal));
		jButtonMoverBaixo.setToolTipText(Idioma.getTraducao("tetris.move_down_component", jFramePrincipal));
	}
	//Inicializa e exibe janela
	public void init(){
		carregarIdioma();
		gerarNome();
		preencherLista();
		
		//Adicionando Listener para teclas de acao
		Component[] componentes = getContentPane().getComponents();
		KeyAdapter keyAdapter = new KeyAdapter(){
			public void keyReleased(KeyEvent arg0){
				if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
					dispose();
				}
			}
		};
		jListItens.addKeyListener(keyAdapter);
		for (int i = 0; i < componentes.length; i++) {
			componentes[i].addKeyListener(keyAdapter);
		}
		
		setVisible(true);
	}

	public String getPai(){
		return pai;
	}
	//Gera novo nome para menu, separador ou item
	public void gerarNome(){
		String nome="";
		if(jComboBoxTipo.getSelectedIndex()==0){
			nome="jMenu";
		}else if(jComboBoxTipo.getSelectedIndex()==1){
			nome="jMenuItem";
		}else{
			nome="jSeparator";
		}
		//Define índice que compõe nome
		ArrayList<Componente> arrayListComponentes = getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela().getArrayListComponentes();
		int contagem=1;
		for (int i = 0; i < arrayListComponentes.size(); i++) {
			if((nome+contagem).equals(arrayListComponentes.get(i).getNome())){
				i=0;
				contagem++;
			}
		}

		nome=nome+contagem;

		jTextFieldNome.setText(nome);
		if(jComboBoxTipo.getSelectedIndex()!=2){
			jTextFieldTexto.setText(nome);
			jTextFieldTexto.setEnabled(true);
		}else{
			jTextFieldTexto.setText("");
			jTextFieldTexto.setEnabled(false);
		}
	}
	//Preenche o JList
	public void preencherLista(){
		//Variável de modelo da JList
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		//Aponta para a lista de componentes da janela
		ArrayList<Componente> arrayListComponentes = getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela().getArrayListComponentes();
		//Percorre a lista
		for (int i = 0; i < arrayListComponentes.size(); i++) {
			//Filtra os componentes que pertencem ao componente
			if(arrayListComponentes.get(i).getAtributo("Pai")!=null){
				if((pai).equals(arrayListComponentes.get(i).getAtributo("Pai").getValor())){
					String texto=" - ";
					if(arrayListComponentes.get(i).getAtributo("Text")!=null){
						texto=arrayListComponentes.get(i).getAtributo("Text").getValor() + " - ";
					}
					//Adiciona elemento
					listModel.addElement(arrayListComponentes.get(i).getNome()+ " - " +texto + arrayListComponentes.get(i).getTipo());
				}
			}
		}
		jListItens.setModel(listModel);

		ativarBotaoEditar();
	}
	//Ativa ou desativa botão editar subitens
	public void ativarBotaoEditar(){
		//Se o componente for um menu, ativa o botão, caso contrário, deixa inativo
		jButtonEditarSubitens.setEnabled(false);
		if(jListItens.getSelectedIndex()>=0){
			if(jListItens.getSelectedValue().split(" - ")[2].equals("Menu")){
				jButtonEditarSubitens.setEnabled(true);
			}
		}
	}

	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}
}
