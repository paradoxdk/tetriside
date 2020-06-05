/*
 *Janela para Manipular componentes externos do projeto
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 26 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.janelas;

import javax.swing.JDialog;

import componentes.visao.JTetrisList;
import componentes.visao.JTetrisPanel;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.com.analisasoftware.tetris.modelo.Arquivo;
import br.com.analisasoftware.tetris.modelo.Idioma;

import java.awt.Toolkit;
import java.io.File;
import java.awt.Cursor;

@SuppressWarnings("serial")
public class JDialogComponentesExternos extends JDialog {
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Componentes gráficos
	private JTetrisList jListComponentes;
	private JLabel jLabelAdicioneOuRemova;
	private JButton jButtonAdicionar;
	private JButton jButtonRemover;
	private JButton jButtonFechar;

	public JDialogComponentesExternos(JFramePrincipal jFramePrincipal) {
		super();
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogComponentesExternos.class.getResource("/br/com/analisasoftware/tetris/imagem/icone.png")));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		JTetrisPanel tetrisPanel = new JTetrisPanel();
		setContentPane(tetrisPanel);

		jButtonAdicionar = new JButton("Adicionar");
		jButtonAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Adiciona componente externo
				adicionarComponente();
			}
		});
		jButtonAdicionar.setMargin(new Insets(2, 0, 2, 0));
		jButtonAdicionar.setIcon(new ImageIcon(JDialogComponentesExternos.class.getResource("/br/com/analisasoftware/tetris/imagem/botao_adicionar.png")));
		jButtonAdicionar.setBounds(274, 390, 120, 30);
		tetrisPanel.add(jButtonAdicionar);

		jButtonRemover = new JButton("Remover");
		jButtonRemover.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Remove componente externo
				removerComponente();
			}
		});
		jButtonRemover.setIcon(new ImageIcon(JDialogComponentesExternos.class.getResource("/br/com/analisasoftware/tetris/imagem/botao_remover.png")));
		jButtonRemover.setMargin(new Insets(2, 0, 2, 0));
		jButtonRemover.setBounds(399, 390, 120, 30);
		tetrisPanel.add(jButtonRemover);

		jButtonFechar = new JButton("Fechar");
		jButtonFechar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Fecha a janela
				dispose();
			}
		});
		jButtonFechar.setIcon(new ImageIcon(JDialogComponentesExternos.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_fechar.png")));
		jButtonFechar.setMargin(new Insets(2, 0, 2, 0));
		jButtonFechar.setBounds(524, 390, 100, 30);
		tetrisPanel.add(jButtonFechar);

		jLabelAdicioneOuRemova = new JLabel("Adicione ou remova componentes externos (Arquivos .jar)");
		jLabelAdicioneOuRemova.setBounds(10, 11, 614, 14);
		tetrisPanel.add(jLabelAdicioneOuRemova);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 614, 343);
		tetrisPanel.add(scrollPane);

		jListComponentes = new JTetrisList(new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/icone_jar.png")));
		jListComponentes.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setViewportView(jListComponentes);
		setTitle("Componentes Externos");
		setSize(new Dimension(640, 480));
		setResizable(false);
		this.jFramePrincipal = jFramePrincipal;

		setLocationRelativeTo(jFramePrincipal);
		setModal(true);

	}

	//Carrega idioma nos componentes
	public void carregarIdioma(){
		setTitle(Idioma.getTraducao("tetris.external_components", jFramePrincipal));
		jButtonAdicionar.setText(Idioma.getTraducao("tetris.add", jFramePrincipal));
		jButtonRemover.setText(Idioma.getTraducao("tetris.remove", jFramePrincipal));
		jButtonFechar.setText(Idioma.getTraducao("tetris.close", jFramePrincipal));
		jLabelAdicioneOuRemova.setText(Idioma.getTraducao("tetris.phrase_add_or_remove", jFramePrincipal));
	}
	//Inicializa e exibe a janela
	public void init(){
		//Preenche lista de componentes externos
		if(preencherLista()){
			//Carrega o idioma dos componentes gráficos
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
			jListComponentes.addKeyListener(keyAdapter);
			for (int i = 0; i < componentes.length; i++) {
				componentes[i].addKeyListener(keyAdapter);
			}
			
			setVisible(true);
		}
	}
	//Preenche a lista de componentes externos e retorna true, caso sucesso, e false, caso contrário
	public boolean preencherLista(){
		//Verifica se há um projeto aberto
		if(getjFramePrincipal().getProjeto()!=null){
			//Verifica se o diretório de componentes externos do projeto existe. Caso não, cria
			if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/comp/")==false){
				Arquivo.criarPasta(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/comp/");
			}
			//Captura os componentes em um vetor
			String[] arquivos = Arquivo.listarPastas(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/comp/");
			//Adiciona ao JList na tela
			DefaultListModel<String> listModel = new DefaultListModel<String>();
			for (int i = 0; i < arquivos.length; i++) {
				listModel.addElement(arquivos[i]);
			}
			jListComponentes.setModel(listModel);
			return true;
		}else{
			JDialogMensagem.exibirMensagem("Erro", Idioma.getTraducao("tetris.message_open_a_project", jFramePrincipal));
			dispose();

			return false;
		}
	}
	//Adiciona componente externo
	public void adicionarComponente(){
		//Escolhendo o componente
		JFileChooser jFileChooser = new JFileChooser();  //Cria uma instância do JFileChooser  
		FileNameExtensionFilter filter = new FileNameExtensionFilter(  
				Idioma.getTraducao("tetris.jar_file", jFramePrincipal), "jar");  //Cria um filtro  
		jFileChooser.setFileFilter(filter);  //Altera o filtro do JFileChooser  
		jFileChooser.setMultiSelectionEnabled(true); //Habilita multiselecao
		int returnVal = jFileChooser.showOpenDialog(getjFramePrincipal()); //Abre o diálogo JFileChooser  
		if(returnVal == JFileChooser.APPROVE_OPTION) {  //Verifica se o usuário clicou no botão OK  
			boolean adiciona=false;
			//Percorre os arquivos selecionados
			File[] files = jFileChooser.getSelectedFiles();
			for (int i = 0; i < files.length; i++) {
				//Cria o diretório do componente no projeto
				Arquivo.criarPasta(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/comp/"+files[i].getName()+"/");
				//Extrai arquivo Jar do componente no projeto
				if(Arquivo.extrairArquivoJar(files[i].getAbsolutePath(), System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/comp/"+files[i].getName()+"/")){
					adiciona=true;
				}
			}

			if(adiciona){
				//Preenche a lista de componentes externos
				preencherLista();
			}

		}
		
	}
	//Remove o componente externo selecionado
	public void removerComponente(){
		//Verifica se há um componente externo selecionado
		if(jListComponentes.getSelectedIndex() >= 0){
			//Pergunta se deseja realmente remover o componente
			if(JDialogMensagem.exibirMensagem("Perguntar", Idioma.getTraducao("tetris.message_do_you_want_to_remove_the_external_component", jFramePrincipal))==1){
				//Apara diretório do componente externo
				if(Arquivo.apagarDiretorio(new File(System.getProperty("user.home")+"/TetrisWorkspace/"+getjFramePrincipal().getProjeto().getNome()+"/comp/"+jListComponentes.getSelectedValue()+"/"))){
					//Preenche lista de componentes externos
					preencherLista();
				}
			}
		}else{
			JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_remove_external_component", jFramePrincipal));
		}
	}

	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}
}
