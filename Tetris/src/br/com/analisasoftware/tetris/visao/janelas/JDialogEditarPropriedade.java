/*
 *Janela para manipular propriedades de componentes e de janelas do projeto
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 27 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.janelas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JDialog;

import componentes.modelo.Mascara;
import componentes.visao.JTetrisButton;
import componentes.visao.JTetrisComboBox;
import componentes.visao.JTetrisPanel;
import componentes.visao.JTetrisTextField;
import tetris.modelo.componentes.Atributo;

import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.com.analisasoftware.tetris.modelo.Arquivo;
import br.com.analisasoftware.tetris.modelo.Idioma;
import br.com.analisasoftware.tetris.visao.componentes.JTetrisFontChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class JDialogEditarPropriedade extends JDialog {
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Atributo em edição
	private Atributo atributo;
	//Variável de retorno
	private String retorno;
	//Tipo da propriedade
	private String tipo;
	//Componentes gráficos
	private JTetrisTextField jTetrisTextFieldValor;
	private JTetrisComboBox jComboBoxValor;
	private JTetrisButton jTetrisButtonOk;
	private JTetrisButton jTetrisButtonCancelar;
	private JTetrisButton jTetrisButtonCor;
	private JTextArea jTextAreaLista;

	private JList<String> jListImagens;
	private JScrollPane jScrollPaneListImagens;
	private JTetrisButton jTetrisButtonImagens;
	private JTetrisButton jTetrisButtonExcluirImagem;

	private JPanel jPanelCor;
	private JScrollPane jScrollPaneTextAreaLista;

	private JLabel jLabelImagem;
	private JLabel jLabelValor;

	public JDialogEditarPropriedade(JFramePrincipal jFramePrincipal, Atributo atributo, String tipo) {
		super();
		this.tipo=tipo;
		retorno=null;
		this.jFramePrincipal = jFramePrincipal;
		this.atributo=atributo;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(jFramePrincipal);
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogComponentesExternos.class.getResource("/br/com/analisasoftware/tetris/imagem/icone.png")));
		setTitle("Editar Propriedade");
		setSize(new Dimension(374, 146));
		setResizable(false);

		JTetrisPanel tetrisPanel = new JTetrisPanel();
		setContentPane(tetrisPanel);

		jLabelValor = new JLabel("Valor");
		jLabelValor.setBounds(12, 12, 70, 15);
		tetrisPanel.add(jLabelValor);

		jTetrisTextFieldValor = new JTetrisTextField();
		jTetrisTextFieldValor.setBounds(12, 32, 348, 25);
		tetrisPanel.add(jTetrisTextFieldValor);
		jTetrisTextFieldValor.setColumns(10);

		if(atributo!=null){
			jTetrisTextFieldValor.setText(atributo.getValor());
		}

		jPanelCor = new JPanel();
		jPanelCor.setBounds(12, 32, 30, 19);
		jTetrisButtonCor = new JTetrisButton("Selecione a Cor");
		jTetrisButtonCor.setBounds(42, 32, 318, 19);
		jTetrisButtonCor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		jTetrisButtonCor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Seleciona uma Cor
				Color cor = JColorChooser.showDialog(getjFramePrincipal(), Idioma.getTraducao("tetris.select_a_color", getjFramePrincipal()), jTetrisButtonCor.getBackground());
				if(cor!=null){
					jPanelCor.setBackground(cor);
					jTetrisButtonCor.setBackground(cor);
					jTetrisTextFieldValor.setText(Mascara.filtroZerosEsquerda(""+cor.getRed(), 3)+", "+Mascara.filtroZerosEsquerda(""+cor.getGreen(), 3)+", "+Mascara.filtroZerosEsquerda(""+cor.getBlue(), 3));
				}
			}
		});

		jComboBoxValor = new JTetrisComboBox();
		jComboBoxValor.setBounds(12, 32, 348, 19);
		jComboBoxValor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Ao selecionar item no combobox, passa para o campo de texto de valor de propriedade
				if(jComboBoxValor.getSelectedItem().equals("TRAILING")){
					jTetrisTextFieldValor.setText(""+JTetrisTextField.TRAILING);
				}else if(jComboBoxValor.getSelectedItem().equals("LEADING")){
					jTetrisTextFieldValor.setText(""+JTetrisTextField.LEADING);
				}else if(jComboBoxValor.getSelectedItem().equals("RIGHT")){
					jTetrisTextFieldValor.setText(""+JTetrisTextField.RIGHT);
				}else if(jComboBoxValor.getSelectedItem().equals("LEFT")){
					jTetrisTextFieldValor.setText(""+JTetrisTextField.LEFT);
				}else if(jComboBoxValor.getSelectedItem().equals("CENTER")){
					jTetrisTextFieldValor.setText(""+JTetrisTextField.CENTER);
				}else if(jComboBoxValor.getSelectedItem().equals("TOP")){
					jTetrisTextFieldValor.setText(""+JTetrisTextField.TOP);
				}else if(jComboBoxValor.getSelectedItem().equals("BOTTOM")){
					jTetrisTextFieldValor.setText(""+JTetrisTextField.BOTTOM);
				}else if(jComboBoxValor.getSelectedItem().equals("DISPOSE_ON_CLOSE")){
					jTetrisTextFieldValor.setText("DISPOSE_ON_CLOSE");
				}else if(jComboBoxValor.getSelectedItem().equals("DO_NOTHING_ON_CLOSE")){
					jTetrisTextFieldValor.setText("DO_NOTHING_ON_CLOSE");
				}else if((jComboBoxValor.getSelectedItem().equals(Idioma.getTraducao("tetris.none", getjFramePrincipal()))) 
						|| (jComboBoxValor.getSelectedItem().equals(Idioma.getTraducao("tetris.none2", getjFramePrincipal())))){
					jTetrisTextFieldValor.setText("");
				}else if(jComboBoxValor.getSelectedItem().equals("true")){
					jTetrisTextFieldValor.setText("true");
				}else if(jComboBoxValor.getSelectedItem().equals("false")){
					jTetrisTextFieldValor.setText("false");
				}else if(jComboBoxValor.getSelectedItem().equals(Idioma.getTraducao("tetris.maximized", getjFramePrincipal()))){
					jTetrisTextFieldValor.setText(""+JFrame.MAXIMIZED_BOTH);
				}else if(jComboBoxValor.getSelectedItem().equals(Idioma.getTraducao("tetris.center", getjFramePrincipal()))){
					jTetrisTextFieldValor.setText("null");
				}else if(jComboBoxValor.getSelectedItem().equals(Idioma.getTraducao("tetris.portrait", getjFramePrincipal()))){
					jTetrisTextFieldValor.setText("PORTRAIT");
				}else if(jComboBoxValor.getSelectedItem().equals(Idioma.getTraducao("tetris.landscape", getjFramePrincipal()))){
					jTetrisTextFieldValor.setText("LANDSCAPE");
				}else{
					if(getTipo().equals("Cursor")){
						jTetrisTextFieldValor.setText(""+jComboBoxValor.getSelectedItem());
					}else{
						jTetrisTextFieldValor.setText(""+jComboBoxValor.getSelectedIndex());
					}
				}
			}
		});

		jTextAreaLista = new JTextArea();
		jScrollPaneTextAreaLista = new JScrollPane(jTextAreaLista);
		jScrollPaneTextAreaLista.setBounds(12, 32, 348, 70);
		jTextAreaLista.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				//Ao perder o foco, passa o valor selecionado na lista para o campo de texto de valor da propriedade
				String[] texto=jTextAreaLista.getText().split("\n");
				String valor="";
				for (int i = 0; i < texto.length; i++) {
					if(valor.equals("")==false){
						valor+="/";
					}
					valor+=texto[i];
				}
				jTetrisTextFieldValor.setText(valor);
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});

		jListImagens = new JList<String>();
		jScrollPaneListImagens = new JScrollPane(jListImagens);
		jScrollPaneListImagens.setBounds(12, 32, 200, 70);

		jListImagens.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				//Ao modificar valor no JList de Imagens, modifica no campo de texto de valor da propriedade
				jTetrisTextFieldValor.setText(jListImagens.getSelectedValue());
				if(jTetrisTextFieldValor.getText().equals(Idioma.getTraducao("tetris.none", getjFramePrincipal()))){
					jTetrisTextFieldValor.setText("");
				}
				jLabelImagem.setIcon(new ImageIcon(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/"+jListImagens.getSelectedValue()));
			}
		});

		jTetrisButtonOk = new JTetrisButton("OK");
		jTetrisButtonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Retorna o valor da propriedade e fecha a janela
				if(getTipo().equals("Text")){
					retorno = jTextAreaLista.getText();
				}else{
					retorno = jTetrisTextFieldValor.getText();
				}
				dispose();
			}
		});
		jTetrisButtonOk.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		jTetrisButtonImagens = new JTetrisButton("Selecione uma imagem");
		jTetrisButtonImagens.setBounds(12, 104, 150, 25);
		jTetrisButtonImagens.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Escolhe uma imagem
				JFileChooser jFileChooser = new JFileChooser();  //Cria uma instância do JFileChooser  
				FileNameExtensionFilter filter = new FileNameExtensionFilter(  
						"PNG, JPG & GIF", "png", "jpeg", "jpg", "gif");  //Cria um filtro  
				jFileChooser.setFileFilter(filter);  //Altera o filtro do JFileChooser  
				int returnVal = jFileChooser.showOpenDialog(getjFramePrincipal()); //Abre o diálogo JFileChooser  
				if(returnVal == JFileChooser.APPROVE_OPTION) {  //Verifica se o usuário clicou no botão OK  
					//Cria diretório de recursos
					if(Arquivo.criarPasta(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/")){
						//Copia imagem para o projeto
						boolean adiciona=false;
						if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/"+jFileChooser.getSelectedFile().getName())==false){
							if(Arquivo.copiarArquivo(jFileChooser.getSelectedFile().getAbsolutePath(), System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/"+jFileChooser.getSelectedFile().getName())){
								adiciona=true;
							}
						}else{
							JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_image", getjFramePrincipal()));
						}

						if(adiciona){
							jTetrisTextFieldValor.setText(jFileChooser.getSelectedFile().getName());
							jTetrisButtonOk.doClick();
						}
					}
				}
				//
			}
		});
		jTetrisButtonImagens.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		jTetrisButtonExcluirImagem = new JTetrisButton("");
		jTetrisButtonExcluirImagem.setBounds(172, 104, 40, 25);
		jTetrisButtonExcluirImagem.setToolTipText("Excluir imagem selecionada");
		jTetrisButtonExcluirImagem.setIcon(new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/botao_remover.png")));
		jTetrisButtonExcluirImagem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//Exclui imagem do projeto
				//Verifica se tem imagem selecionada
				if(jListImagens.getSelectedIndex()>0){
					//Pergunta se deseja realmente excluir a imagem
					if(JDialogMensagem.exibirMensagem("Perguntar", Idioma.getTraducao("tetris.message_remove_image", getjFramePrincipal())+"<font color='red'><b>"+jListImagens.getSelectedValue()+"</b></font>?<br/>"+Idioma.getTraducao("tetris.message_warning_remove_image", getjFramePrincipal()))==1){
						//Apaga a imagem
						Arquivo.apagarArquivo(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/"+jListImagens.getSelectedValue());
						//Atualiza a lista
						preencherListaImagens();
					}
				}else{
					JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_select_remove_image", getjFramePrincipal()));
				}
			}
		});
		jTetrisButtonExcluirImagem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


		jTetrisButtonOk.setIcon(new ImageIcon(JDialogEditarPropriedade.class.getResource("/br/com/analisasoftware/tetris/imagem/sim.png")));
		jTetrisButtonOk.setBounds(114, 63, 117, 25);
		tetrisPanel.add(jTetrisButtonOk);

		jTetrisButtonCancelar = new JTetrisButton("Cancelar");
		jTetrisButtonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Fecha janela
				dispose();
			}
		});
		jTetrisButtonCancelar.setIcon(new ImageIcon(JDialogEditarPropriedade.class.getResource("/br/com/analisasoftware/tetris/imagem/sair.png")));
		jTetrisButtonCancelar.setBounds(243, 63, 117, 25);
		jTetrisButtonCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jTetrisButtonCancelar);

		jLabelImagem = new JLabel("");
		jLabelImagem.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelImagem.setBounds(221, 12, 137, 150);
		tetrisPanel.add(jLabelImagem);
		//Preenche campos de valores da propriedade de acordo com o tipo
		if(tipo.equals("int")){
			jTetrisTextFieldValor.setMascara(JTetrisTextField.MASCARA_NUMERO_INTEIRO);
		}else if(tipo.equals("char")){
			jTetrisTextFieldValor.setMaxLength(1);
			jTetrisTextFieldValor.setMascara(JTetrisTextField.MASCARA_MAX_LENGTH);
		}else if(tipo.equals("Border")){
			tetrisPanel.remove(jTetrisTextFieldValor);
			tetrisPanel.add(jComboBoxValor);

			jComboBoxValor.addItem("EtchedBorder");
			jComboBoxValor.addItem("LoweredBorder");
			jComboBoxValor.addItem("RaisedBorder");
			jComboBoxValor.addItem(Idioma.getTraducao("tetris.none", jFramePrincipal));

			if(atributo!=null){
				jComboBoxValor.setSelectedIndex(Integer.parseInt(atributo.getValor()));
			}

			jTetrisTextFieldValor.setText(""+jComboBoxValor.getSelectedIndex());

		}else if(tipo.equals("Janela")){
			tetrisPanel.remove(jTetrisTextFieldValor);
			tetrisPanel.add(jComboBoxValor);

			jComboBoxValor.addItem("Frame");
			jComboBoxValor.addItem("Dialog");
			jComboBoxValor.addItem("Internal Frame");

			if(atributo!=null){
				jComboBoxValor.setSelectedIndex(Integer.parseInt(atributo.getValor()));
			}

			jTetrisTextFieldValor.setText(""+jComboBoxValor.getSelectedIndex());

		}else if(tipo.equals("Cursor")){
			tetrisPanel.remove(jTetrisTextFieldValor);
			tetrisPanel.add(jComboBoxValor);

			jComboBoxValor.addItem("CROSSHAIR_CURSOR");
			jComboBoxValor.addItem("CUSTOM_CURSOR");
			jComboBoxValor.addItem("DEFAULT_CURSOR");
			jComboBoxValor.addItem("E_RESIZE_CURSOR");
			jComboBoxValor.addItem("HAND_CURSOR");
			jComboBoxValor.addItem("MOVE_CURSOR");
			jComboBoxValor.addItem("N_RESIZE_CURSOR");
			jComboBoxValor.addItem("NE_RESIZE_CURSOR");
			jComboBoxValor.addItem("NW_RESIZE_CURSOR");
			jComboBoxValor.addItem("S_RESIZE_CURSOR");
			jComboBoxValor.addItem("SE_RESIZE_CURSOR");
			jComboBoxValor.addItem("SW_RESIZE_CURSOR");
			jComboBoxValor.addItem("TEXT_CURSOR");
			jComboBoxValor.addItem("W_RESIZE_CURSOR");
			jComboBoxValor.addItem("WAIT_CURSOR");
			
			if(atributo!=null){
				jComboBoxValor.setSelectedItem(atributo.getValor());
			}

			jTetrisTextFieldValor.setText(""+jComboBoxValor.getSelectedItem());

		}else if(tipo.equals("HorizontalAlignment")){
			tetrisPanel.remove(jTetrisTextFieldValor);
			tetrisPanel.add(jComboBoxValor);

			jComboBoxValor.addItem("CENTER");
			jComboBoxValor.addItem("LEADING");
			jComboBoxValor.addItem("LEFT");
			jComboBoxValor.addItem("RIGHT");


			if(atributo!=null){
				if(atributo.getValor().equals(""+JTetrisTextField.LEADING)){
					jComboBoxValor.setSelectedItem("LEADING");
				}else if(atributo.getValor().equals(""+JTetrisTextField.LEFT)){
					jComboBoxValor.setSelectedItem("LEFT");
				}else if(atributo.getValor().equals(""+JTetrisTextField.RIGHT)){
					jComboBoxValor.setSelectedItem("RIGHT");
				}else if(atributo.getValor().equals(""+JTetrisTextField.CENTER)){
					jComboBoxValor.setSelectedItem("CENTER");
				}
			}else{
				jTetrisTextFieldValor.setText(""+JTetrisTextField.CENTER);
			}


		}else if(tipo.equals("DefaultCloseOperation")){
			tetrisPanel.remove(jTetrisTextFieldValor);
			tetrisPanel.add(jComboBoxValor);

			jComboBoxValor.addItem("DISPOSE_ON_CLOSE");
			jComboBoxValor.addItem("DO_NOTHING_ON_CLOSE");

			if(atributo!=null){
				if(atributo.getValor().equals(""+JFrame.DISPOSE_ON_CLOSE)){
					jComboBoxValor.setSelectedItem("DISPOSE_ON_CLOSE");
				}else if(atributo.getValor().equals(""+JFrame.EXIT_ON_CLOSE)){
					jComboBoxValor.setSelectedItem("EXIT_ON_CLOSE");
				}else if(atributo.getValor().equals(""+JFrame.DO_NOTHING_ON_CLOSE)){
					jComboBoxValor.setSelectedItem("DO_NOTHING_ON_CLOSE");
				}
			}else{
				jTetrisTextFieldValor.setText(""+JFrame.DISPOSE_ON_CLOSE);
			}


		}else if(tipo.equals("VerticalAlignment")){
			tetrisPanel.remove(jTetrisTextFieldValor);
			tetrisPanel.add(jComboBoxValor);

			jComboBoxValor.addItem("CENTER");
			jComboBoxValor.addItem("TOP");
			jComboBoxValor.addItem("BOTTOM");


			if(atributo!=null){
				if(atributo.getValor().equals(""+JTetrisTextField.TOP)){
					jComboBoxValor.setSelectedItem("TOP");
				}else if(atributo.getValor().equals(""+JTetrisTextField.BOTTOM)){
					jComboBoxValor.setSelectedItem("BOTTOM");
				}else if(atributo.getValor().equals(""+JTetrisTextField.CENTER)){
					jComboBoxValor.setSelectedItem("CENTER");
				}
			}else{
				jTetrisTextFieldValor.setText(""+JTetrisTextField.CENTER);
			}

			jTetrisTextFieldValor.setText(""+jComboBoxValor.getSelectedIndex());

		}else if(tipo.equals("HorizontalTextPosition")){
			tetrisPanel.remove(jTetrisTextFieldValor);
			tetrisPanel.add(jComboBoxValor);

			jComboBoxValor.addItem("TRAILING");
			jComboBoxValor.addItem("LEADING");
			jComboBoxValor.addItem("LEFT");
			jComboBoxValor.addItem("RIGHT");
			jComboBoxValor.addItem("CENTER");

			if(atributo!=null){
				if(atributo.getValor().equals(""+JTetrisTextField.TRAILING)){
					jComboBoxValor.setSelectedItem("TRAILING");
				}else if(atributo.getValor().equals(""+JTetrisTextField.LEADING)){
					jComboBoxValor.setSelectedItem("LEADING");
				}else if(atributo.getValor().equals(""+JTetrisTextField.LEFT)){
					jComboBoxValor.setSelectedItem("LEFT");
				}else if(atributo.getValor().equals(""+JTetrisTextField.RIGHT)){
					jComboBoxValor.setSelectedItem("RIGHT");
				}else if(atributo.getValor().equals(""+JTetrisTextField.CENTER)){
					jComboBoxValor.setSelectedItem("CENTER");
				}
			}else{
				jTetrisTextFieldValor.setText(""+JTetrisTextField.TRAILING);
			}


		}else if(tipo.equals("VerticalTextPosition")){
			tetrisPanel.remove(jTetrisTextFieldValor);
			tetrisPanel.add(jComboBoxValor);

			jComboBoxValor.addItem("CENTER");
			jComboBoxValor.addItem("TOP");
			jComboBoxValor.addItem("BOTTOM");

			if(atributo!=null){
				if(atributo.getValor().equals(""+JTetrisTextField.TOP)){
					jComboBoxValor.setSelectedItem("TOP");
				}else if(atributo.getValor().equals(""+JTetrisTextField.BOTTOM)){
					jComboBoxValor.setSelectedItem("BOTTOM");
				}else if(atributo.getValor().equals(""+JTetrisTextField.CENTER)){
					jComboBoxValor.setSelectedItem("CENTER");
				}
			}else{
				jTetrisTextFieldValor.setText(""+JTetrisTextField.CENTER);
			}

			jTetrisTextFieldValor.setText(""+jComboBoxValor.getSelectedIndex());

		}else if(tipo.equals("Orientacao")){
			tetrisPanel.remove(jTetrisTextFieldValor);
			tetrisPanel.add(jComboBoxValor);

			jComboBoxValor.addItem(Idioma.getTraducao("tetris.portrait", getjFramePrincipal()));
			jComboBoxValor.addItem(Idioma.getTraducao("tetris.landscape", getjFramePrincipal()));
			
			if(atributo!=null){
				if((atributo.getValor().equals("PAISAGEM")) || atributo.getValor().equals("LANDSCAPE")){
					jComboBoxValor.setSelectedIndex(1);
					jTetrisTextFieldValor.setText("LANDSCAPE");
				}else{
					jComboBoxValor.setSelectedIndex(0);
					jTetrisTextFieldValor.setText("PORTRAIT");
				}
			}else{
				jComboBoxValor.setSelectedIndex(0);
				jTetrisTextFieldValor.setText("PORTRAIT");
			}

		}else if(tipo.equals("Icon")){
			//Falta implementar
			tetrisPanel.remove(jTetrisTextFieldValor);
			tetrisPanel.add(jScrollPaneListImagens);
			tetrisPanel.add(jTetrisButtonImagens);
			tetrisPanel.add(jTetrisButtonExcluirImagem);
			setSize(374, 216);

			jTetrisButtonOk.setLocation(114, 133);
			jTetrisButtonCancelar.setLocation(243, 133);

			preencherListaImagens();
		}else if(tipo.equals("boolean")){
			tetrisPanel.remove(jTetrisTextFieldValor);
			tetrisPanel.add(jComboBoxValor);

			jComboBoxValor.addItem("true");
			jComboBoxValor.addItem("false");

			if(atributo!=null){
				jComboBoxValor.setSelectedItem(atributo.getValor());
			}

			jTetrisTextFieldValor.setText(""+jComboBoxValor.getSelectedItem());

		}else if(tipo.equals("Mascara")){
			tetrisPanel.remove(jTetrisTextFieldValor);
			tetrisPanel.add(jComboBoxValor);

			jComboBoxValor.addItem(Idioma.getTraducao("tetris.none", jFramePrincipal));
			jComboBoxValor.addItem(Idioma.getTraducao("tetris.upper_case", jFramePrincipal));
			jComboBoxValor.addItem(Idioma.getTraducao("tetris.zipcode", jFramePrincipal));
			jComboBoxValor.addItem("CNPJ");
			jComboBoxValor.addItem(Idioma.getTraducao("tetris.phone", jFramePrincipal));
			jComboBoxValor.addItem(Idioma.getTraducao("tetris.state_registration", jFramePrincipal));
			jComboBoxValor.addItem(Idioma.getTraducao("tetris.password", jFramePrincipal));
			jComboBoxValor.addItem("CPF");
			jComboBoxValor.addItem(Idioma.getTraducao("tetris.integer", jFramePrincipal));
			jComboBoxValor.addItem(Idioma.getTraducao("tetris.decimal", jFramePrincipal));
			jComboBoxValor.addItem(Idioma.getTraducao("tetris.date", jFramePrincipal));
			jComboBoxValor.addItem(Idioma.getTraducao("tetris.time", jFramePrincipal));
			jComboBoxValor.addItem(Idioma.getTraducao("tetris.no_space", jFramePrincipal));

			if(atributo!=null){
				jComboBoxValor.setSelectedIndex(Integer.parseInt(atributo.getValor()));
			}

			jTetrisTextFieldValor.setText(""+jComboBoxValor.getSelectedIndex());

		}else if(tipo.equals("Lista")){
			tetrisPanel.remove(jTetrisTextFieldValor);
			tetrisPanel.add(jScrollPaneTextAreaLista);
			setSize(374, 216);
			jTetrisButtonOk.setLocation(114, 133);
			jTetrisButtonCancelar.setLocation(243, 133);

			if(atributo!=null){
				String[] texto = jTetrisTextFieldValor.getText().split("/");

				for (int i = 0; i < texto.length; i++) {
					if(i!=0){
						jTextAreaLista.setText(jTextAreaLista.getText()+"\n");
					}
					jTextAreaLista.setText(jTextAreaLista.getText()+texto[i]);
				}

			}
		}else if(tipo.equals("Text")){
			tetrisPanel.remove(jTetrisTextFieldValor);
			tetrisPanel.add(jScrollPaneTextAreaLista);
			setSize(374, 216);
			jTetrisButtonOk.setLocation(114, 133);
			jTetrisButtonCancelar.setLocation(243, 133);

			if(atributo!=null){
				jTextAreaLista.setText(atributo.getValor());
			}
		}else if(tipo.equals("Color")){
			tetrisPanel.remove(jTetrisTextFieldValor);
			tetrisPanel.add(jTetrisButtonCor);
			tetrisPanel.add(jPanelCor);
			if(atributo!=null){
				int red=Integer.parseInt(atributo.getValor().substring(0, 3));
				int green=Integer.parseInt(atributo.getValor().substring(5, 8));
				int blue=Integer.parseInt(atributo.getValor().substring(10, 13));

				jTetrisButtonCor.setBackground(new Color(red, green, blue));
				jPanelCor.setBackground(new Color(red, green, blue));
			}
		}else if(tipo.equals("ExtendedState")){
			tetrisPanel.remove(jTetrisTextFieldValor);
			tetrisPanel.add(jComboBoxValor);

			jComboBoxValor.addItem(Idioma.getTraducao("tetris.none2", jFramePrincipal));
			jComboBoxValor.addItem(Idioma.getTraducao("tetris.maximized", jFramePrincipal));

			if(atributo!=null){
				if(atributo.getValor().equals(""+JFrame.MAXIMIZED_BOTH)){
					jComboBoxValor.setSelectedItem(Idioma.getTraducao("tetris.maximized", jFramePrincipal));
					jTetrisTextFieldValor.setText(""+JFrame.MAXIMIZED_BOTH);
				}else{
					jComboBoxValor.setSelectedItem(Idioma.getTraducao("tetris.none2", jFramePrincipal));
					jTetrisTextFieldValor.setText("");
				}
			}


		}else if(tipo.equals("LocationRelativeTo")){
			tetrisPanel.remove(jTetrisTextFieldValor);
			tetrisPanel.add(jComboBoxValor);

			jComboBoxValor.addItem(Idioma.getTraducao("tetris.none2", jFramePrincipal));
			jComboBoxValor.addItem(Idioma.getTraducao("tetris.center", jFramePrincipal));

			if(atributo!=null){
				if(atributo.getValor().equals("null")){
					jComboBoxValor.setSelectedItem(Idioma.getTraducao("tetris.center", jFramePrincipal));
					jTetrisTextFieldValor.setText("null");
				}else{
					jComboBoxValor.setSelectedItem(Idioma.getTraducao("tetris.none2", jFramePrincipal));
					jTetrisTextFieldValor.setText("");
				}
			}
		}
		//Caso tenha elementos no combo box, seleciona o primeiro
		if(jComboBoxValor.getSelectedIndex()<0){
			if(jComboBoxValor.getItemCount() > 0){
				jComboBoxValor.setSelectedIndex(0);
			}
		}
	}
	
	//Carrega idioma nos componentes
	public void carregarIdioma(){
		setTitle(Idioma.getTraducao("tetris.property_editor", jFramePrincipal));
		jLabelValor.setText(Idioma.getTraducao("tetris.value", jFramePrincipal));
		jTetrisButtonOk.setText(Idioma.getTraducao("tetris.ok", jFramePrincipal));
		jTetrisButtonCancelar.setText(Idioma.getTraducao("tetris.cancel", jFramePrincipal));
		jTetrisButtonCor.setText(Idioma.getTraducao("tetris.select_a_color", jFramePrincipal));
		jTetrisButtonExcluirImagem.setToolTipText(Idioma.getTraducao("tetris.remove_image", jFramePrincipal));
		jTetrisButtonImagens.setText(Idioma.getTraducao("tetris.select_a_image", jFramePrincipal));
		
	}
	//Inicializa e exibe janela
	public String init(){
		carregarIdioma();
		//Trata os tipos especiais
		if(tipo.equals("Menu")){

			String pai=getjFramePrincipal().getjTetrisListPropriedadesInspetorDeObjetos().getjTextFieldsPropriedades()[0].getText();
			new JDialogEditarMenu(getjFramePrincipal(), pai).init();

			jFramePrincipal.getjDesktopPaneAreaDeTrabalho().atualizarJanela();

		}else if(tipo.equals("Abas")){

			String pai=getjFramePrincipal().getjTetrisListPropriedadesInspetorDeObjetos().getjTextFieldsPropriedades()[0].getText();
			new JDialogEditarAbas(getjFramePrincipal(), pai).init();

			jFramePrincipal.getjDesktopPaneAreaDeTrabalho().atualizarJanela();

		}else if(tipo.equals("Report")){
			String pai=getjFramePrincipal().getjTetrisListPropriedadesInspetorDeObjetos().getjTextFieldsPropriedades()[0].getText();
			new JDialogTetrisReportEditor(getjFramePrincipal(), pai).init();

		}else if(tipo.equals("Font")){
			JTetrisFontChooser jTetrisFontChooser = new JTetrisFontChooser();
			if(atributo!=null) {
				if(!atributo.getValor().equals("")) {
					String[] fonte = atributo.getValor().split("/");
					jTetrisFontChooser.setSelectedFontFamily(fonte[0]);
					jTetrisFontChooser.setSelectedFontSize(Integer.parseInt(fonte[1]));
					jTetrisFontChooser.setSelectedFontStyle(Integer.parseInt(fonte[2]));
				}
			}
			if(jTetrisFontChooser.showDialog(getjFramePrincipal())==JTetrisFontChooser.OK_OPTION){
				Font font = jTetrisFontChooser.getSelectedFont();
				String fonte=font.getFontName()+"/"+font.getSize()+"/"+font.getStyle();
				
				getjFramePrincipal().getjTetrisListPropriedadesInspetorDeObjetos().getRelacionamentoComponente().getComponente().mudarAtributo(new Atributo("Font", "Font", fonte));
				
				jFramePrincipal.getjDesktopPaneAreaDeTrabalho().atualizarJanela();
			}
		}else{
		
			//Adicionando Listener para teclas de acao
			Component[] componentes = getContentPane().getComponents();
			KeyAdapter keyAdapter = new KeyAdapter(){
				public void keyReleased(KeyEvent arg0){
					if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
						dispose();
					}
				}
			};
			jListImagens.addKeyListener(keyAdapter);
			jTextAreaLista.addKeyListener(keyAdapter);
			for (int i = 0; i < componentes.length; i++) {
				componentes[i].addKeyListener(keyAdapter);
			}
			
			setVisible(true);
			jTetrisTextFieldValor.requestFocus();
			setVisible(false);
			setModal(true);
			setVisible(true);
		}
		return retorno;
	}
	//Preenche JList de Imagens
	public void preencherListaImagens(){
		//Retorna vetor com lista de imagens no diretório de recursos do projeto
		String[] icones = Arquivo.listarArquivos(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/res/");
		//Modelo do JList
		DefaultListModel<String> listModel= new DefaultListModel<String>();
		//Adiciona itens àlista
		listModel.addElement(Idioma.getTraducao("tetris.none", jFramePrincipal));

		if(icones!=null){
			for (int i = 0; i < icones.length; i++) {
				listModel.addElement(icones[i]);
			}
		}

		jListImagens.setModel(listModel);

		if(atributo!=null){
			jListImagens.setSelectedValue(jTetrisTextFieldValor.getText(), true);
		}

		if(jListImagens.getSelectedIndex() < 0){
			jListImagens.setSelectedIndex(0);
		}
	}

	public Atributo getAtributo() {
		return atributo;
	}
	//Getters e Setters
	public void setAtributo(Atributo atributo) {
		this.atributo = atributo;
	}
	
	public String getTipo() {
		return tipo;
	}

	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}

	public void setjFramePrincipal(JFramePrincipal jFramePrincipal) {
		this.jFramePrincipal = jFramePrincipal;
	}
}
