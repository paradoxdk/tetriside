/*
 *Janela para manipular Evento de um componente de uma janela do projeto
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 26 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.janelas;

import java.awt.Dimension;

import javax.swing.JDialog;

import componentes.visao.JTetrisButton;
import componentes.visao.JTetrisList;
import componentes.visao.JTetrisPanel;
import componentes.visao.JTetrisTextField;
import tetris.modelo.componentes.Atributo;
import tetris.modelo.componentes.Metodo;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.analisasoftware.tetris.modelo.Data;
import br.com.analisasoftware.tetris.modelo.Idioma;
import br.com.analisasoftware.tetris.visao.componentes.jsyntaxtextpane.JSyntaxTextPane;

import javax.swing.JList;
import javax.swing.JTextArea;

import java.awt.Component;
import java.awt.SystemColor;
import java.awt.Cursor;

import javax.swing.SwingConstants;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class JDialogEditarEvento extends JDialog implements ListSelectionListener{
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Aponta para a lista de métodos do evento do componente
	private ArrayList<Metodo> arrayListMetodos;
	//Lista de métodos para retorno
	private ArrayList<Metodo> retorno;
	//Flag de indicação de salvamento de método em edição
	private boolean semSalvar;
	//Componentes gráficos
	private JComboBox<String> jComboBoxFuncoes;
	private JComboBox<String> jComboBoxFuncoesTraduzidas;
	private JTetrisButton jTetrisButtonAdicionar;
	private JSyntaxTextPane jSyntaxTextPaneParametros;
	private JTetrisButton jTetrisButtonRemover;
	private JTetrisButton jTetrisButtonOk;
	private JTetrisButton jTetrisButtonCancelar;
	private JTextArea jTextAreaDica;
	private JTetrisButton jTetrisButtonSalvar;
	private JTetrisTextField jTextFieldTecla;
	private JLabel jLabelTecla;
	private JTetrisList jListEspelho;
	private JList<String> jListFuncoes;
	private JScrollPane jScrollPaneFuncoes;
	private JLabel jLabelFuncoes;
	private JScrollPane jScrollPaneDica;
	private JScrollPane jScrollPaneParametros;
	private JTetrisButton jTetrisButtonMoverCima;
	private JTetrisButton jTetrisButtonMoverBaixo;
	private JLabel jLabelFuncao;
	private JLabel jLabelParametros;

	public JDialogEditarEvento(JFramePrincipal jFramePrincipal, ArrayList<Metodo> arrayListMetodos) {
		super();
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogEditarEvento.class.getResource("/br/com/analisasoftware/tetris/imagem/icone.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fecharJanela();
			}
		});
		retorno=null;
		semSalvar=true;
		this.jFramePrincipal = jFramePrincipal;
		this.arrayListMetodos=arrayListMetodos;
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(jFramePrincipal);
		
		setTitle("Editar Evento");
		setSize(new Dimension(790, 600));
		setLocationRelativeTo(jFramePrincipal);

		JTetrisPanel tetrisPanel = new JTetrisPanel();
		tetrisPanel.addComponentListener(new ComponentAdapter() {
			//Representa evento de redimensionamento de componente
			@Override
			public void componentResized(ComponentEvent e) {
				//Redimensiona componentes
				redimensionarComponentes();
			}
		});
		setContentPane(tetrisPanel);

		jLabelFuncao = new JLabel("Fun\u00E7\u00E3o");
		jLabelFuncao.setBounds(279, 12, 452, 15);
		tetrisPanel.add(jLabelFuncao);

		jComboBoxFuncoes = new JComboBox<String>();
		jComboBoxFuncoes.setBounds(12, 32, 522, 24);

		jComboBoxFuncoesTraduzidas = new JComboBox<String>();
		String[] metodos = Metodo.getMetodos();
		String[] metodosTraduzidos = Metodo.getMetodosTraduzidos(jFramePrincipal);
		for (int i = 0; i < metodos.length; i++) {
			jComboBoxFuncoes.addItem(metodos[i]);
			jComboBoxFuncoesTraduzidas.addItem(metodosTraduzidos[i]);
		}
		jComboBoxFuncoesTraduzidas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Ao selecionar uma função no combobox, exibe dicas e limpa os parâmetros
				jComboBoxFuncoes.setSelectedIndex(jComboBoxFuncoesTraduzidas.getSelectedIndex());
				jSyntaxTextPaneParametros.setText("");
				jTextAreaDica.setText(Metodo.getDicaMetodo(""+jComboBoxFuncoes.getSelectedItem(), getjFramePrincipal()));
			}
		});
		jComboBoxFuncoesTraduzidas.setBounds(279, 32, 452, 25);
		tetrisPanel.add(jComboBoxFuncoesTraduzidas);

		jScrollPaneParametros = new JScrollPane();
		jScrollPaneParametros.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		jScrollPaneParametros.setBounds(279, 90, 497, 289);
		tetrisPanel.add(jScrollPaneParametros);

		jSyntaxTextPaneParametros = new JSyntaxTextPane();
		jScrollPaneParametros.setViewportView(jSyntaxTextPaneParametros);
		
		jTetrisButtonOk = new JTetrisButton("OK");
		jTetrisButtonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Retorna os métodos editados e fecha a janela
				retorno = getArrayListMetodos();
				semSalvar=false;
				fecharJanela();
			}
		});
		jTetrisButtonOk.setIcon(new ImageIcon(JDialogEditarEvento.class.getResource("/br/com/analisasoftware/tetris/imagem/sim.png")));
		jTetrisButtonOk.setBounds(532, 536, 117, 25);
		jTetrisButtonOk.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jTetrisButtonOk);

		jTetrisButtonCancelar = new JTetrisButton("Cancelar");
		jTetrisButtonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Fecha a janela
				fecharJanela();
			}
		});
		jTetrisButtonCancelar.setIcon(new ImageIcon(JDialogEditarEvento.class.getResource("/br/com/analisasoftware/tetris/imagem/sair.png")));
		jTetrisButtonCancelar.setBounds(659, 536, 117, 25);
		jTetrisButtonCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jTetrisButtonCancelar);

		jTetrisButtonAdicionar = new JTetrisButton("");
		jTetrisButtonAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Adiciona uma função/método
				adicionarFuncao();
			}
		});
		jTetrisButtonAdicionar.setToolTipText("Adicionar Fun\u00E7\u00E3o");
		jTetrisButtonAdicionar.setIcon(new ImageIcon(JDialogEditarEvento.class.getResource("/br/com/analisasoftware/tetris/imagem/botao_adicionar.png")));
		jTetrisButtonAdicionar.setBounds(736, 32, 40, 25);
		jTetrisButtonAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jTetrisButtonAdicionar);

		jTetrisButtonRemover = new JTetrisButton("");
		jTetrisButtonRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Exclui uma função/método
				excluirFuncao();
			}
		});
		jTetrisButtonRemover.setToolTipText("Remover Fun\u00E7\u00E3o");
		jTetrisButtonRemover.setIcon(new ImageIcon(JDialogEditarEvento.class.getResource("/br/com/analisasoftware/tetris/imagem/botao_remover.png")));
		jTetrisButtonRemover.setBounds(12, 536, 40, 25);
		jTetrisButtonRemover.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jTetrisButtonRemover);

		jScrollPaneFuncoes = new JScrollPane();
		jScrollPaneFuncoes.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		jScrollPaneFuncoes.setBounds(12, 32, 255, 493);
		tetrisPanel.add(jScrollPaneFuncoes);

		jListEspelho = new JTetrisList(new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/menu_exportar.png")));
		jListEspelho.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				//Lista de funções adicionadas
				jListFuncoes.setSelectedIndex(jListEspelho.getSelectedIndex());
			}
		});
		jScrollPaneFuncoes.setViewportView(jListEspelho);

		jLabelParametros = new JLabel("Par\u00E2metros");
		jLabelParametros.setBounds(279, 65, 102, 20);
		tetrisPanel.add(jLabelParametros);

		jLabelFuncoes = new JLabel("Fun\u00E7\u00F5es");
		jLabelFuncoes.setBounds(12, 12, 117, 15);
		tetrisPanel.add(jLabelFuncoes);

		jScrollPaneDica = new JScrollPane();
		jScrollPaneDica.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		jScrollPaneDica.setBounds(279, 379, 497, 147);
		tetrisPanel.add(jScrollPaneDica);

		jTextAreaDica = new JTextArea();
		jTextAreaDica.setEditable(false);
		jTextAreaDica.setBackground(SystemColor.control);
		jScrollPaneDica.setViewportView(jTextAreaDica);

		jTextAreaDica.setText(Metodo.getDicaMetodo(""+jComboBoxFuncoes.getSelectedItem(), getjFramePrincipal()));

		jTetrisButtonSalvar = new JTetrisButton("Salvar");
		jTetrisButtonSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Modifica função selecionada
				mudarFuncao();
			}
		});
		jTetrisButtonSalvar.setVisible(false);
		jTetrisButtonSalvar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jTetrisButtonSalvar.setBounds(516, 64, 110, 25);
		jTetrisButtonSalvar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jTetrisButtonSalvar.setIcon(new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/menu_salvar.png")));
		tetrisPanel.add(jTetrisButtonSalvar);

		jLabelTecla = new JLabel("Tecla:");
		jLabelTecla.setBounds(634, 66, 55, 20);
		tetrisPanel.add(jLabelTecla);

		jTextFieldTecla = new JTetrisTextField();
		jTextFieldTecla.setHorizontalAlignment(SwingConstants.CENTER);
		jTextFieldTecla.setBounds(690, 64, 86, 25);
		tetrisPanel.add(jTextFieldTecla);
		jTextFieldTecla.setColumns(10);
		jTextFieldTecla.setMascara(JTetrisTextField.MASCARA_MAIUSCULO);

		jListFuncoes = new JList<String>();
		
		jListFuncoes.setVisible(false);
		jListFuncoes.setBounds(250, 474, 69, 87);
		jListFuncoes.addListSelectionListener(this);
		tetrisPanel.add(jListFuncoes);
		
		jTetrisButtonMoverCima = new JTetrisButton("");
		jTetrisButtonMoverCima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Movendo funcao para cima
				//Verifica se o item selecionado é maior que o primeiro
				if(jListFuncoes.getSelectedIndex() > 1){
					//Aponta para lista de métodos
					ArrayList<Metodo> arrayListMetodos = getArrayListMetodos();
					//Clona o método selecionado e armazena em uma variável
					Metodo metodo1 = arrayListMetodos.get(jListEspelho.getSelectedIndex() - 1).clone();
					//Move para cima a posição do JList
					jListEspelho.setSelectedIndex(jListEspelho.getSelectedIndex()-1);
					//Clona o método selecionado e armazena em outra variável
					Metodo metodo2 = arrayListMetodos.get(jListEspelho.getSelectedIndex() - 1).clone();
					//Percorre a lista
					for(int i=0; i < arrayListMetodos.size(); i++){
						//Efetua a troca dos métodos. O de cima vai para baixo e o de baixo para cima
						if(arrayListMetodos.get(i).getNome().equals(metodo2.getNome())){
							arrayListMetodos.set(i, metodo1);
						}else if(arrayListMetodos.get(i).getNome().equals(metodo1.getNome())){
							arrayListMetodos.set(i, metodo2);
						}
					}
					//Captura a posição do item selecionado
					int posicao = jListEspelho.getSelectedIndex();
					//Atualiza lista de funções
					preencherListaFuncoes();
					//Define a posição capturada
					jListEspelho.setSelectedIndex(posicao);
				}
			}
		});
		jTetrisButtonMoverCima.setToolTipText("Mover fun\u00E7\u00E3o para cima");
		jTetrisButtonMoverCima.setIcon(new ImageIcon(JDialogEditarEvento.class.getResource("/br/com/analisasoftware/tetris/imagem/seta_cima.png")));
		jTetrisButtonMoverCima.setText("");
		jTetrisButtonMoverCima.setBounds(62, 536, 40, 25);
		jTetrisButtonMoverCima.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jTetrisButtonMoverCima);
		
		jTetrisButtonMoverBaixo = new JTetrisButton("");
		jTetrisButtonMoverBaixo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Movendo funcao para baixo
				//Aponta para lista de métodos
				ArrayList<Metodo> arrayListMetodos = getArrayListMetodos();
				//Verifica se o item selecionado está dentro dos limites movíveis
				if((jListFuncoes.getSelectedIndex() < arrayListMetodos.size()) && (jListFuncoes.getSelectedIndex() > 0)){
					//Clona o método selecionado e armazena em uma variável
					Metodo metodo1 = arrayListMetodos.get(jListEspelho.getSelectedIndex() - 1).clone();
					//Move a posição do JList para baixo
					jListEspelho.setSelectedIndex(jListEspelho.getSelectedIndex()+1);
					//Clona o método selecionado e armazena em outra variável
					Metodo metodo2 = arrayListMetodos.get(jListEspelho.getSelectedIndex() - 1).clone();
					//Percorre a lista
					for(int i=0; i < arrayListMetodos.size(); i++){
						//Faz a troca dos métodos. O de cima vai para baixo e o de baixo vai para cima
						if(arrayListMetodos.get(i).getNome().equals(metodo2.getNome())){
							arrayListMetodos.set(i, metodo1);
						}else if(arrayListMetodos.get(i).getNome().equals(metodo1.getNome())){
							arrayListMetodos.set(i, metodo2);
						}
					}
					//Captura a posição selecionada da lista
					int posicao = jListEspelho.getSelectedIndex();
					//Atualiza lista
					preencherListaFuncoes();
					//Define a posição capturada
					jListEspelho.setSelectedIndex(posicao);
				}
			}
		});
		jTetrisButtonMoverBaixo.setToolTipText("Mover fun\u00E7\u00E3o para baixo");
		jTetrisButtonMoverBaixo.setIcon(new ImageIcon(JDialogEditarEvento.class.getResource("/br/com/analisasoftware/tetris/imagem/seta_baixo.png")));
		jTetrisButtonMoverBaixo.setText("");
		jTetrisButtonMoverBaixo.setBounds(112, 536, 40, 25);
		jTetrisButtonMoverBaixo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jTetrisButtonMoverBaixo);

		if(((""+getjFramePrincipal().getjTetrisListEventosInspetorDeObjetos().getSelectedValue()).startsWith("OnKey"))){
			jTextFieldTecla.setVisible(true);
			jLabelTecla.setVisible(true);
		}else{
			jTextFieldTecla.setVisible(false);
			jLabelTecla.setVisible(false);
		}

		preencherListaFuncoes();
		
		//Maximiza janela
		setLocation(0, 0);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setSize(getWidth(), getHeight() - 50);
	}
	
	//Carrega idioma nos componentes
	public void carregarIdioma(){
		setTitle(Idioma.getTraducao("tetris.event_editor", jFramePrincipal));
		jLabelFuncao.setText(Idioma.getTraducao("tetris.function", jFramePrincipal));
		jLabelFuncoes.setText(Idioma.getTraducao("tetris.functions", jFramePrincipal));
		jLabelParametros.setText(Idioma.getTraducao("tetris.parameters", jFramePrincipal));
		jLabelTecla.setText(Idioma.getTraducao("tetris.key", jFramePrincipal));
		jTetrisButtonSalvar.setText(Idioma.getTraducao("tetris.save", jFramePrincipal));
		jTetrisButtonCancelar.setText(Idioma.getTraducao("tetris.cancel", jFramePrincipal));
		jTetrisButtonOk.setText(Idioma.getTraducao("tetris.ok", jFramePrincipal));
		jTetrisButtonMoverCima.setToolTipText(Idioma.getTraducao("tetris.move_function_up", jFramePrincipal));
		jTetrisButtonMoverBaixo.setToolTipText(Idioma.getTraducao("tetris.move_function_down", jFramePrincipal));
		jTetrisButtonAdicionar.setToolTipText(Idioma.getTraducao("tetris.add_function", jFramePrincipal));
		jTetrisButtonRemover.setToolTipText(Idioma.getTraducao("tetris.remove_function", jFramePrincipal));
	}
	//Fecha janela, verificando se tem alteração sem salvar
	public void fecharJanela(){
		if (semSalvar) {
			if(JDialogMensagem.exibirMensagem("Perguntar", Idioma.getTraducao("tetris.message_discard_changes", jFramePrincipal))==1){
				dispose();
			}
		}else{
			dispose();
		}
	}
	//Preenche lista de métodos/funções
	public void preencherListaFuncoes(){
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		DefaultListModel<String> listModelEspelho = new DefaultListModel<String>();

		listModel.addElement(Idioma.getTraducao("tetris.new_function", jFramePrincipal));
		listModelEspelho.addElement(Idioma.getTraducao("tetris.new_function", jFramePrincipal));
		//Percorre a lista de métodos
		for (int i = 0; i < arrayListMetodos.size(); i++) {
			//Adiciona ao modelo do JList
			listModel.addElement(arrayListMetodos.get(i).getNome());
			//Adiciona argumentos do método ao texto do item no JList
			String argumentos="";
			String nomeMetodo = arrayListMetodos.get(i).getNome().replace("0", "").replace("1", "").replace("2", "").replace("3", "").replace("4", "").replace("5", "").replace("6", "").replace("7", "").replace("8", "").replace("9", "").replace("_", "");
			if((nomeMetodo.equals("comandoJava")==false) && (nomeMetodo.equals("habilitarCampos")==false) && (nomeMetodo.equals("desabilitarCampos")==false)){
				ArrayList<Atributo> arrayListAtributos = arrayListMetodos.get(i).getArrayListAtributos(); 
				for (int j = 0; j < arrayListAtributos.size(); j++) {
					if(arrayListAtributos.get(j).getNome().startsWith("$")){
						if(argumentos.equals("")==false){
							argumentos=argumentos+", ";
						}
						argumentos+=arrayListAtributos.get(j).getValor();
					}
				}
			}
			listModelEspelho.addElement(Idioma.getTraducao("tetris."+nomeMetodo, jFramePrincipal)+"("+argumentos+")");
		}
		jListFuncoes.setModel(listModel);
		jListEspelho.setModel(listModelEspelho);
	}
	//Exclui uma função
	public void excluirFuncao(){
		if(jListFuncoes.getSelectedIndex() > 0){
			arrayListMetodos.remove(jListFuncoes.getSelectedIndex()-1);
			preencherListaFuncoes();
		}else{
			JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_remove_function", jFramePrincipal));
		}
	}

	//Adicionando um metodo
	public void adicionarFuncao(){
		String[] parametros = jSyntaxTextPaneParametros.getText().replace("\r", "").split("\n");

		if(jSyntaxTextPaneParametros.getText().equals("")){
			parametros = new String [0];
		}

		Metodo metodo = null;
		if(jTextFieldTecla.getText().equals("")==false){
			metodo = Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+jComboBoxFuncoes.getSelectedItem(), ""+getjFramePrincipal().getjTetrisListEventosInspetorDeObjetos().getSelectedValue(), parametros, getjFramePrincipal(), jTextFieldTecla.getText());
		}else{
			metodo = Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+jComboBoxFuncoes.getSelectedItem(), ""+getjFramePrincipal().getjTetrisListEventosInspetorDeObjetos().getSelectedValue(), parametros, getjFramePrincipal());
		}
		arrayListMetodos.add(metodo);

		preencherListaFuncoes();
	}

	//Modificando o metodo
	public void mudarFuncao(){
		String[] parametros = jSyntaxTextPaneParametros.getText().replace("\r", "").split("\n");

		if(jSyntaxTextPaneParametros.getText().equals("")){
			parametros = new String [0];
		}

		Metodo metodo = null;
		if(jTextFieldTecla.getText().equals("")==false){
			metodo = Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+jComboBoxFuncoes.getSelectedItem(), ""+getjFramePrincipal().getjTetrisListEventosInspetorDeObjetos().getSelectedValue(), parametros, getjFramePrincipal(), jTextFieldTecla.getText());
			arrayListMetodos.get(jListFuncoes.getSelectedIndex()-1).setTipo(jTextFieldTecla.getText());
		}else{
			metodo = Metodo.criarMetodo(""+Data.padraoAmericano(Data.getDataAtual()).replace("-", "")+Data.getHoraAtual().replace(":", "")+jComboBoxFuncoes.getSelectedItem(), ""+getjFramePrincipal().getjTetrisListEventosInspetorDeObjetos().getSelectedValue(), parametros, getjFramePrincipal());
		}

		metodo.setNome(arrayListMetodos.get(jListFuncoes.getSelectedIndex()-1).getNome());

		arrayListMetodos.set(jListFuncoes.getSelectedIndex()-1, metodo);

		preencherListaFuncoes();
	}
	//Redimensiona componentes na janela
	public void redimensionarComponentes(){
		jTetrisButtonRemover.setLocation(12, getContentPane().getHeight() - jTetrisButtonRemover.getHeight() - 12);
		jTetrisButtonMoverCima.setLocation(jTetrisButtonMoverCima.getX(), getContentPane().getHeight() - jTetrisButtonMoverCima.getHeight() - 12);
		jTetrisButtonMoverBaixo.setLocation(jTetrisButtonMoverBaixo.getX(), getContentPane().getHeight() - jTetrisButtonMoverBaixo.getHeight() - 12);
		jScrollPaneFuncoes.setBounds(jScrollPaneFuncoes.getX(), jScrollPaneFuncoes.getY(), jScrollPaneFuncoes.getWidth(), getContentPane().getHeight() - jScrollPaneFuncoes.getY() - jTetrisButtonRemover.getHeight() - 22);
		
		jTetrisButtonCancelar.setLocation(getContentPane().getWidth() - jTetrisButtonCancelar.getWidth() - 12, getContentPane().getHeight() - jTetrisButtonCancelar.getHeight() - 12);
		jTetrisButtonOk.setLocation(getContentPane().getWidth() - jTetrisButtonOk.getWidth() - jTetrisButtonOk.getWidth() - 22, getContentPane().getHeight() - jTetrisButtonOk.getHeight() - 12);
		
		jScrollPaneDica.setBounds(jScrollPaneDica.getX(), getContentPane().getHeight() - jTetrisButtonRemover.getHeight() - 12 - jScrollPaneDica.getHeight() - 10, getContentPane().getWidth() - jScrollPaneDica.getX() - 12, jScrollPaneDica.getHeight());
		jScrollPaneParametros.setSize(getContentPane().getWidth() - jScrollPaneParametros.getX() - 12, (int)jScrollPaneDica.getLocation().getY() - (int)jScrollPaneParametros.getLocation().getY());
		
		jSyntaxTextPaneParametros.setSize(getContentPane().getWidth() - jScrollPaneParametros.getX() - 12, (int)jScrollPaneDica.getLocation().getY() - (int)jScrollPaneParametros.getLocation().getY());
		jListEspelho.setSize(getContentPane().getWidth() - 24, 91);
		jTextAreaDica.setSize(getContentPane().getWidth() - jScrollPaneDica.getX() - 12, 147);
		
		jTextFieldTecla.setLocation(getContentPane().getWidth() - jTextFieldTecla.getWidth() - 12, jTextFieldTecla.getY());
		jLabelTecla.setLocation(jTextFieldTecla.getX() - 56, jLabelTecla.getY());
		if(jTextFieldTecla.isVisible()) {
			jTetrisButtonSalvar.setLocation(jLabelTecla.getX() - 118, jTetrisButtonSalvar.getY());
		}else {
			jTetrisButtonSalvar.setLocation(getContentPane().getWidth() - jTetrisButtonSalvar.getWidth() - 12, jTetrisButtonSalvar.getY());
		}
		
		jTetrisButtonAdicionar.setLocation(getContentPane().getWidth() - 12 - jTetrisButtonAdicionar.getWidth(), jTetrisButtonAdicionar.getY());
		jComboBoxFuncoesTraduzidas.setSize(jTetrisButtonAdicionar.getX() - jComboBoxFuncoesTraduzidas.getX() - 5, jComboBoxFuncoesTraduzidas.getHeight());
		jComboBoxFuncoesTraduzidas.setVisible(false);
		jComboBoxFuncoesTraduzidas.setVisible(true);
	}
	//Inicializa e exibe janela
	public ArrayList<Metodo> init(){
		carregarIdioma();
		setVisible(true);
		jSyntaxTextPaneParametros.requestFocus();
		setVisible(false);
		setModal(true);
		
		//Adicionando Listener para teclas de acao
		Component[] componentes = getContentPane().getComponents();
		KeyAdapter keyAdapter = new KeyAdapter(){
			public void keyReleased(KeyEvent arg0){
				if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
					fecharJanela();
				}
			}
		};
		jListEspelho.addKeyListener(keyAdapter);
		jSyntaxTextPaneParametros.addKeyListener(keyAdapter);
		for (int i = 0; i < componentes.length; i++) {
			componentes[i].addKeyListener(keyAdapter);
		}
		
		setVisible(true);
		return retorno;
	}
	//Getters e Setters
	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}

	public ArrayList<Metodo> getArrayListMetodos() {
		return arrayListMetodos;
	}
	//Representa método de mudança de valor de JList
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		//Verifica se o item selecionado está em uma posição maior que zero
		if(jListFuncoes.getSelectedIndex()>0){
			//Seleciona função do mesmo tipo no combobox de funções
			jComboBoxFuncoes.setSelectedItem(""+arrayListMetodos.get(jListFuncoes.getSelectedIndex()-1).getNome().replace("0", "").replace("1", "").replace("2", "").replace("3", "").replace("4", "").replace("5", "").replace("6", "").replace("7", "").replace("8", "").replace("9", "").replace("_", ""));
			jComboBoxFuncoesTraduzidas.setSelectedIndex(jComboBoxFuncoes.getSelectedIndex());
			String param="", tipo="";
			//Preenche atributos no SyntaxPane de parâmetros
			ArrayList<Atributo> arrayListAtributo = arrayListMetodos.get(jListFuncoes.getSelectedIndex()-1).getArrayListAtributos();
			for (int i = 1; i < arrayListAtributo.size(); i++) {
				if(param.equals("")==false){
					param+="\n";
				}
				param+=arrayListMetodos.get(jListFuncoes.getSelectedIndex()-1).getAtributo("$"+i).getValor();
			}

			tipo=arrayListMetodos.get(jListFuncoes.getSelectedIndex()-1).getTipo();

			if(tipo.equals("void")){
				jTextFieldTecla.setText("");
			}else{
				jTextFieldTecla.setText(tipo);
			}

			jSyntaxTextPaneParametros.setText(param);

			jTetrisButtonSalvar.setVisible(true);

		}else{
			jSyntaxTextPaneParametros.setText("");
			jTextFieldTecla.setText("");
			jComboBoxFuncoes.setSelectedIndex(0);
			jComboBoxFuncoesTraduzidas.setSelectedIndex(0);
			jTetrisButtonSalvar.setVisible(false);
		}
	}
}
