/*
 *Janela para manipular abas de um TabbedPane de uma janela do projeto
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 26 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.janelas;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Toolkit;

import javax.swing.JDialog;

import componentes.visao.JTetrisButton;
import componentes.visao.JTetrisPanel;
import componentes.visao.JTetrisTextField;
import tetris.modelo.componentes.Atributo;
import tetris.modelo.componentes.Componente;
import tetris.modelo.componentes.Janela;
import tetris.modelo.componentes.Metodo;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.border.BevelBorder;

import br.com.analisasoftware.tetris.modelo.Idioma;

import java.awt.SystemColor;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class JDialogEditarAbas extends JDialog {
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Representa o TabbedPane em foco
	private String pai;
	//Componentes gráficos
	private JTetrisTextField jTextFieldTexto;
	private JList<String> jListItens;
	private JTetrisButton jButtonAdicionar;
	private JTetrisButton jButtonExcluir;
	private JLabel jLabelTtulo;
	private JTetrisButton jButtonFechar;

	public JDialogEditarAbas(JFramePrincipal jFramePrincipal, String pai){
		this.jFramePrincipal=jFramePrincipal;
		this.pai=pai;
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogEditarMenu.class.getResource("/br/com/analisasoftware/tetris/imagem/icone.png")));
		setModal(true);
		setResizable(false);
		setTitle("Editar Aba");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.jFramePrincipal=jFramePrincipal;
		JTetrisPanel tetrisPanel = new JTetrisPanel();
		setContentPane(tetrisPanel);

		jLabelTtulo = new JLabel("T\u00EDtulo");
		jLabelTtulo.setBounds(10, 11, 151, 14);
		tetrisPanel.add(jLabelTtulo);

		jTextFieldTexto = new JTetrisTextField();
		jTextFieldTexto.setBounds(10, 32, 345, 25);
		tetrisPanel.add(jTextFieldTexto);
		jTextFieldTexto.setColumns(10);

		jButtonAdicionar = new JTetrisButton("Adicionar");
		jButtonAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Adiciona uma aba
				adicionaAba();
			}
		});
		jButtonAdicionar.setIcon(new ImageIcon(JDialogEditarAbas.class.getResource("/br/com/analisasoftware/tetris/imagem/sim.png")));
		jButtonAdicionar.setBounds(363, 31, 119, 25);
		jButtonAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jButtonAdicionar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 63, 472, 232);
		tetrisPanel.add(scrollPane);

		jListItens = new JList<String>();
		jListItens.setBackground(SystemColor.menu);
		jListItens.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setViewportView(jListItens);

		jButtonExcluir = new JTetrisButton("Excluir");
		jButtonExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Remove uma aba do TabbedPane
				removeAba();
			}
		});
		jButtonExcluir.setIcon(new ImageIcon(JDialogEditarAbas.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_excluir.png")));
		jButtonExcluir.setBounds(10, 306, 119, 23);
		jButtonExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jButtonExcluir);

		jButtonFechar = new JTetrisButton("Fechar");
		jButtonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Fecha a janela
				dispose();
			}
		});
		jButtonFechar.setIcon(new ImageIcon(JDialogEditarAbas.class.getResource("/br/com/analisasoftware/tetris/imagem/sair.png")));
		jButtonFechar.setBounds(376, 306, 108, 23);
		jButtonFechar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jButtonFechar);
		setSize(500, 376);
		setLocationRelativeTo(null);
	}
	
	//Adiciona uma aba
	public void adicionaAba() {
		//Verifica se foi inserido informação do campo de texto
		if(jTextFieldTexto.getText().equals("")==false){
			//Aponta para a lista de componentes da janela aberta na área de trabalho 
			ArrayList<Componente> arrayListComponentes = getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela().getArrayListComponentes();
			boolean insere=true;
			int contagem=1;
			//Percorre a lista
			for (int i = 0; i < arrayListComponentes.size(); i++) {
				//Verifica os painéis de abas do TabbedPane e define o índice para o nome
				if(("jPanel"+getPai()+contagem).equals(arrayListComponentes.get(i).getNome())){
					i=0;
					contagem++;
				}
			}
			//Define o nome do Painel da aba
			String nome = "jPanel"+getPai()+contagem;
			//Insere o componente
			if(insere){
				//Define atributos
				ArrayList<Atributo> arrayListAtributos = new ArrayList<Atributo>();
				arrayListAtributos.add(new Atributo("Nome", "String", nome));

				arrayListAtributos.add(new Atributo("Tipo", "String", "Painel"));
				arrayListAtributos.add(new Atributo("TituloAbas", "String", jTextFieldTexto.getText()));
				arrayListAtributos.add(new Atributo("Pai", "String", getPai()));
				//Cria e adiciona componente à janela
				Componente componente = new Componente(nome, arrayListAtributos, new ArrayList<Metodo>());
				arrayListComponentes.add(componente);
				//Foca na nova aba do TabbedPane
				getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela().getComponente(getPai()).mudarAtributo(new Atributo("SelectedIndex", "int", ""+(jListItens.getModel().getSize())));
				//Atualiza o JList com as abas
				preencherLista();

			}else{
				JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_add_component", getjFramePrincipal()));
			}
		}else{
			JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_fill_in_the_name_field", getjFramePrincipal()));
		}
	}
	
	//Remove aba selecionada
	public void removeAba() {
		//Verifica se há um item selecionado
		if(jListItens.getModel().getSize()>0){
			//Aponta para a lista de abas do TabbedPane
			ArrayList<Componente> arrayListComponentes = getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela().getArrayListComponentesFilhos(getPai());
			//Variável de auxílio
			Janela janela = getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela();
			//Remove componentes filhos
			removeComponentesFilhos(janela, arrayListComponentes.get(jListItens.getSelectedIndex()).getNome());
			//Remove a aba
			janela.removerComponente(arrayListComponentes.get(jListItens.getSelectedIndex()).getNome());
			//Modifica a aba em foco
			janela.getComponente(getPai()).mudarAtributo(new Atributo("SelectedIndex", "int", "-1"));
			//Atualiza o JList
			preencherLista();
		}else{
			JDialogMensagem.exibirMensagem("Erro", Idioma.getTraducao("tetris.message_remove", getjFramePrincipal()));
		}
	}
	
	//Remove componentes filhos
	public void removeComponentesFilhos(Janela janela, String pai) {
		//Lista os filhos da aba 
		ArrayList<Componente> arrayListComponentesFilhos = janela.getArrayListComponentesFilhos(pai);
		//Percorre a lista dos filhos
		for (int j = 0; j < arrayListComponentesFilhos.size(); j++) {
			//Remove filhos dos filhos, chamando este método recursivamente
			removeComponentesFilhos(janela, arrayListComponentesFilhos.get(j).getNome());
			
			//Remove cada filho da aba
			janela.getArrayListComponentes().remove(arrayListComponentesFilhos.get(j));
		}
	}
	
	//Preenche o JList com as abas do TabbedPane
	public void preencherLista(){
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		//Aponta para a lista de componentes filhos do TabbedPane 
		ArrayList<Componente> arrayListComponentes = getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela().getArrayListComponentesFilhos(getPai());
		//Percorre a lista
		for (int i = 0; i < arrayListComponentes.size(); i++) {
			//Variáveis de auxílio
			String texto="";
			Atributo atributo = arrayListComponentes.get(i).getAtributo("TituloAbas");
			if(atributo!=null){
				texto=atributo.getValor();
			}
			//Adiciona aba à lista
			listModel.addElement(texto);
		}

		jListItens.setModel(listModel);

	}

	//Carrega idioma nos componentes
	public void carregarIdioma(){
		setTitle(Idioma.getTraducao("tetris.tab_editor", jFramePrincipal));
		jLabelTtulo.setText(Idioma.getTraducao("tetris.title", jFramePrincipal));
		jButtonAdicionar.setText(Idioma.getTraducao("tetris.add", jFramePrincipal));
		jButtonExcluir.setText(Idioma.getTraducao("tetris.remove", jFramePrincipal));
		jButtonFechar.setText(Idioma.getTraducao("tetris.close", jFramePrincipal));
	}
	//Inicializa e exibe a janela
	public void init(){
		//Atualiza o JList
		preencherLista();
		//Carrega idioma dos componentes
		carregarIdioma();
		
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
	//Getters e Setters
	public JFramePrincipal getjFramePrincipal(){
		return jFramePrincipal;
	}

	public String getPai() {
		return pai;
	}


}
