/*
 *Janela principal do TetrisIDE
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 27 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.janelas;

import javax.swing.JFrame;

import componentes.modelo.bancodedados.Coluna;
import componentes.modelo.bancodedados.Tabela;
import componentes.visao.JTetrisPanel;
import componentes.visao.JTetrisToolBar;
import tetris.modelo.componentes.Janela;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import java.awt.Cursor;


import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.util.ArrayList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.com.analisasoftware.tetris.modelo.AreaDeTransferencia;
import br.com.analisasoftware.tetris.modelo.Arquivo;
import br.com.analisasoftware.tetris.modelo.Atualizacao;
import br.com.analisasoftware.tetris.modelo.Idioma;
import br.com.analisasoftware.tetris.modelo.Projeto;
import br.com.analisasoftware.tetris.visao.componentes.JDesktopPaneAreaDeTrabalho;
import br.com.analisasoftware.tetris.visao.componentes.JPopupMenuAreaDeTransferencia;
import br.com.analisasoftware.tetris.visao.componentes.JPopupMenuColunasGerenciadorDeBancoDeDados;
import br.com.analisasoftware.tetris.visao.componentes.JPopupMenuJanela;
import br.com.analisasoftware.tetris.visao.componentes.JPopupMenuProjeto;
import br.com.analisasoftware.tetris.visao.componentes.JTetrisComboBoxTabelas;
import br.com.analisasoftware.tetris.visao.componentes.JTetrisListColunasGerenciadorDeBancoDeDados;
import br.com.analisasoftware.tetris.visao.componentes.JTetrisListEventosInspetorDeObjetos;
import br.com.analisasoftware.tetris.visao.componentes.JTetrisListExploradorDeJanelas;
import br.com.analisasoftware.tetris.visao.componentes.JTetrisListExploradorDeProjetos;
import br.com.analisasoftware.tetris.visao.componentes.JTetrisListPaletaDeObjetos;
import br.com.analisasoftware.tetris.visao.componentes.JTetrisListPropriedadesInspetorDeObjetos;
import br.com.analisasoftware.tetris.visao.componentes.JTetrisPanelBarraLateralDireita;
import br.com.analisasoftware.tetris.visao.componentes.JTetrisPanelBarraLateralEsquerda;
import br.com.analisasoftware.tetris.visao.componentes.JTetrisPanelLog;
import br.com.analisasoftware.tetris.visao.componentes.JTetrisSeparator;
import br.com.analisasoftware.tetris.visao.componentes.jsyntaxtextpane.JSyntaxTextPane;

import javax.swing.border.EmptyBorder;


@SuppressWarnings("serial")
public class JFramePrincipal extends JFrame implements ComponentListener{
	//Componentes gráficos
	private JTetrisPanel jTetrisPanelToolBar;
	private JTetrisPanelLog jTetrisPanelLog;
	private JButton jButtonNovo;
	private JButton jButtonSalvar;
	private JButton jButtonAbrir;
	private JButton jButtonFechar;
	private JButton jButtonExecutar;
	private JButton jButtonExportar;
	private JButton jButtonSobre;
	private JTetrisPanelBarraLateralEsquerda jTetrisPanelBarraLateralEsquerda;
	private JTetrisPanelBarraLateralDireita jTetrisPanelBarraLateralDireita;
	private JTabbedPane jTabbedPaneAreaDeTrabalho;
	private JScrollPane jScrollPaneDesktopPaneAreaDeTrabalho;
	private JScrollPane jScrollPaneSyntaxTextPaneCodigoFonteGerado;
	private JDesktopPaneAreaDeTrabalho jDesktopPaneAreaDeTrabalho;
	private JSyntaxTextPane jSyntaxTextPaneCodigoFonteGerado;
	private JTetrisListExploradorDeProjetos jTetrisListExploradorDeProjetos;
	private JScrollPane jScrollPaneExploradorDeProjetos;
	private JLabel jLabelExploradorDeJanelas;
	private JScrollPane jScrollPaneExploradorDeJanelas;
	private JTetrisListExploradorDeJanelas jTetrisListExploradorDeJanelas;
	private JTetrisSeparator jSeparator2;
	private JLabel jLabelGerenciadorDeBanco;
	private JTetrisComboBoxTabelas jComboBoxTabelaGerenciadorDeBancoDeDados;
	private JTetrisListColunasGerenciadorDeBancoDeDados jTetrisListColunasGerenciadorDeBancoDeDados;
	private JScrollPane jScrollPaneGerenciadorDeBancoDeDados;
	private JScrollPane jScrollPanePaletaDeObjetos;
	private JTetrisListPaletaDeObjetos jTetrisListPaletaDeObjetos;
	private JTetrisSeparator jSeparator3;
	private JLabel jLabelInspetorDeObjetos;
	private JScrollPane jScrollPanePropriedadesInspetorDeObjetos;
	private JTabbedPane jTabbedPaneInspetorDeObjetos;
	//Instância do Projeto aberto
	private Projeto projeto;
	//Área de transferência
	private AreaDeTransferencia areaDeTransferencia;

	//Janelas
	private JDialogComponentesExternos jDialogComponentesExternos;
	private JDialogListaDeComponentes jDialogListaDeComponentes;
	private JDialogConfiguracoes jDialogConfiguracoes;

	//Menus
	private JPopupMenuProjeto jPopupMenuProjeto;
	private JPopupMenuJanela jPopupMenuJanela;
	private JPopupMenuColunasGerenciadorDeBancoDeDados jPopupMenuColunasGerenciadorDeBancoDeDados;
	private JPopupMenuAreaDeTransferencia jPopupMenuAreaDeTransferencia;
	//Ferramentas e listas das barras laterais
	private JTetrisListPropriedadesInspetorDeObjetos jTetrisListPropriedadesInspetorDeObjetos;
	private JTetrisListEventosInspetorDeObjetos jTetrisListEventosInspetorDeObjetos;
	private JButton jButtonJanelaPrincipal;
	private JButton jButtonComponentes;
	private JTetrisSeparator jSeparator1;
	private JLabel jLabelTabela;
	private JButton jButtonAdicionarTabela;
	private JButton jButtonRemoverTabela;
	private JButton jButtonAdicionarColuna;
	private JButton jButtonRemoverColuna;
	private JLabel jLabelJanelaPrincipal;
	private JSeparator jSeparatorJanelaPrincipal;
	private JSeparator jSeparatorJanelaPrincipal2;
	private JButton jButtonLog;
	private JButton jButtonConfiguracoes;
	private JLabel jLabelExploradorDeProjetos;
	private JLabel jLabelPaletaDeObjetos;
	//Objeto de atualização do sistema
	private Atualizacao atualizacao;
	//Variáveis de configurações globais
	private String lookAnFeel, idioma;

	//Criando componentes da janela
	public JFramePrincipal() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				//Fecha janela
				fecharJanela();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/br/com/analisasoftware/tetris/imagem/icone.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setMinimumSize(new Dimension(800, 600));
		setSize(800, 600);
		setTitle("TetrisIDE");
		getContentPane().setLayout(null);
		
		areaDeTransferencia = new AreaDeTransferencia(this);

		jTetrisPanelToolBar = new JTetrisPanel();
		jTetrisPanelToolBar.setBorder(null);
		jTetrisPanelToolBar.setBounds(0, 0, 784, 70);
		getContentPane().add(jTetrisPanelToolBar);

		JTetrisToolBar toolBar = new JTetrisToolBar();
		toolBar.setIcon(new ImageIcon(getClass().getResource("/componentes/imagem/bgpn.png")));
		toolBar.setBounds(0, 0, 398, 69);
		jTetrisPanelToolBar.add(toolBar);
		
		jTetrisPanelLog = new JTetrisPanelLog(this);
		getContentPane().add(jTetrisPanelLog);

		jButtonNovo = new JButton("Novo");
		jButtonNovo.setFocusable(false);
		toolBar.add(jButtonNovo);
		jButtonNovo.setFont(new Font("Dialog", Font.BOLD, 10));
		jButtonNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Cria um projeto
				getjPopupMenuProjeto().getjMenuItemNovo().doClick();
			}
		});
		jButtonNovo.setMargin(new Insets(2, 0, 2, 0));
		jButtonNovo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonNovo.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButtonNovo.setHorizontalTextPosition(SwingConstants.CENTER);
		jButtonNovo.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/br/com/analisasoftware/tetris/imagem/botao_novo.png")));

		jButtonSalvar = new JButton("Salvar");
		jButtonSalvar.setFocusable(false);
		toolBar.add(jButtonSalvar);
		jButtonSalvar.setFont(new Font("Dialog", Font.BOLD, 10));
		jButtonSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Salva projeto aberto
				getjPopupMenuProjeto().getjMenuItemSalvar().doClick();
			}
		});
		jButtonSalvar.setEnabled(false);
		jButtonSalvar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonSalvar.setMargin(new Insets(2, 0, 2, 0));
		jButtonSalvar.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/br/com/analisasoftware/tetris/imagem/botao_salvar.png")));
		jButtonSalvar.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButtonSalvar.setHorizontalTextPosition(SwingConstants.CENTER);

		toolBar.addSeparator();
		jButtonAbrir = new JButton("Abrir");
		jButtonAbrir.setFocusable(false);
		toolBar.add(jButtonAbrir);
		jButtonAbrir.setFont(new Font("Dialog", Font.BOLD, 10));
		jButtonAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Abre projeto selecionado
				getjPopupMenuProjeto().getjMenuItemAbrir().doClick();
			}
		});
		jButtonAbrir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonAbrir.setMargin(new Insets(2, 0, 2, 0));
		jButtonAbrir.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/br/com/analisasoftware/tetris/imagem/botao_abrir.png")));
		jButtonAbrir.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButtonAbrir.setHorizontalTextPosition(SwingConstants.CENTER);

		jButtonFechar = new JButton("Fechar");
		jButtonFechar.setFocusable(false);
		toolBar.add(jButtonFechar);
		jButtonFechar.setFont(new Font("Dialog", Font.BOLD, 10));
		jButtonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Fecha projeto aberto
				getjPopupMenuProjeto().getjMenuItemFechar().doClick();
			}
		});
		jButtonFechar.setEnabled(false);
		jButtonFechar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonFechar.setMargin(new Insets(2, 0, 2, 0));
		jButtonFechar.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/br/com/analisasoftware/tetris/imagem/botao_fechar.png")));
		jButtonFechar.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButtonFechar.setHorizontalTextPosition(SwingConstants.CENTER);

		toolBar.addSeparator();
		jButtonExecutar = new JButton("Executar");
		jButtonExecutar.setFocusable(false);
		toolBar.add(jButtonExecutar);
		jButtonExecutar.setFont(new Font("Dialog", Font.BOLD, 10));
		jButtonExecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Executa projeto aberto
				getjPopupMenuProjeto().getjMenuItemExecutar().doClick();
			}
		});
		jButtonExecutar.setEnabled(false);
		jButtonExecutar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonExecutar.setMargin(new Insets(2, 0, 2, 0));
		jButtonExecutar.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/br/com/analisasoftware/tetris/imagem/botao_executar.png")));
		jButtonExecutar.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButtonExecutar.setHorizontalTextPosition(SwingConstants.CENTER);

		jButtonExportar = new JButton("Exportar");
		jButtonExportar.setFocusable(false);
		toolBar.add(jButtonExportar);
		jButtonExportar.setFont(new Font("Dialog", Font.BOLD, 10));
		jButtonExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Exporta projeto aberto
				getjPopupMenuProjeto().getjMenuItemExportar().doClick();
			}
		});
		jButtonExportar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonExportar.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/br/com/analisasoftware/tetris/imagem/botao_exportar.png")));
		jButtonExportar.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButtonExportar.setMargin(new Insets(2, 0, 2, 0));
		jButtonExportar.setHorizontalTextPosition(SwingConstants.CENTER);
		jButtonExportar.setEnabled(false);

		

		jButtonSobre = new JButton("Sobre");
		jButtonSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Exibe JDialogSobre
				new JDialogSobre(getjTetrisListExploradorDeProjetos().getjFramePrincipal()).init();
			}
		});
		jButtonSobre.setFont(new Font("Dialog", Font.BOLD, 10));
		jButtonSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonSobre.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/br/com/analisasoftware/tetris/imagem/botao_sobre.png")));
		jButtonSobre.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButtonSobre.setMargin(new Insets(2, 0, 2, 0));
		jButtonSobre.setHorizontalTextPosition(SwingConstants.CENTER);
		jButtonSobre.setContentAreaFilled(false);
		jButtonSobre.setBorderPainted(false);
		jButtonSobre.setBounds(709, 0, 65, 65);
		jTetrisPanelToolBar.add(jButtonSobre);

		jSeparatorJanelaPrincipal = new JSeparator();
		jSeparatorJanelaPrincipal.setOrientation(SwingConstants.VERTICAL);
		jSeparatorJanelaPrincipal.setBounds(442, 11, 12, 48);
		jTetrisPanelToolBar.add(jSeparatorJanelaPrincipal);

		jButtonJanelaPrincipal = new JButton("Nenhuma");
		jButtonJanelaPrincipal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Define janela principal
				//Verifica se há um projeto aberto
				if(getProjeto()!=null){
					//Captura janela principal escolhida
					String retorno = new JDialogJanelaPrincipal(jTetrisListExploradorDeJanelas.getjFramePrincipal()).init();
					if(retorno!=null){
						//Seta janela principal no projeto
						ArrayList<Janela> arrayListJanelas = getProjeto().getArrayListJanelas();
						for (int i = 0; i < arrayListJanelas.size(); i++) {
							if(retorno.equals(arrayListJanelas.get(i).getNome())){
								getProjeto().setJanelaPrincipal(arrayListJanelas.get(i));
								getjButtonJanelaPrincipal().setText(retorno);
								break;
							}
						}
					}
				}
			}
		});
		jButtonJanelaPrincipal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonJanelaPrincipal.setFocusable(false);
		jButtonJanelaPrincipal.setEnabled(false);
		jButtonJanelaPrincipal.setMargin(new Insets(2, 0, 2, 0));
		jButtonJanelaPrincipal.setBounds(452, 34, 200, 25);
		jTetrisPanelToolBar.add(jButtonJanelaPrincipal);

		jLabelJanelaPrincipal = new JLabel("Janela Principal");
		jLabelJanelaPrincipal.setBounds(452, 12, 143, 15);
		jTetrisPanelToolBar.add(jLabelJanelaPrincipal);
		
		jSeparatorJanelaPrincipal2 = new JSeparator();
		jSeparatorJanelaPrincipal2.setOrientation(SwingConstants.VERTICAL);
		jSeparatorJanelaPrincipal2.setBounds(657, 11, 12, 48);
		jTetrisPanelToolBar.add(jSeparatorJanelaPrincipal2);
		
		jButtonLog = new JButton("");
		jButtonLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Exibe painel de Log
				if(getjTetrisPanelLog().isVisible()){
					getjTetrisPanelLog().setVisible(false);
				}else{
					getjTetrisPanelLog().setVisible(true);
				}
			}
		});
		jButtonLog.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonLog.setToolTipText("Log");
		jButtonLog.setContentAreaFilled(false);
		jButtonLog.setBorderPainted(false);
		jButtonLog.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/br/com/analisasoftware/tetris/imagem/botao_terminal.png")));
		jButtonLog.setBounds(402, 38, 33, 25);
		jTetrisPanelToolBar.add(jButtonLog);
		
		jButtonConfiguracoes = new JButton("");
		jButtonConfiguracoes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonConfiguracoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Abre janela de configurações
				abrirConfiguracoes();
			}
		});
		jButtonConfiguracoes.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/br/com/analisasoftware/tetris/imagem/botao_configuracoes.png")));
		jButtonConfiguracoes.setToolTipText("Configura\u00E7\u00F5es");
		jButtonConfiguracoes.setContentAreaFilled(false);
		jButtonConfiguracoes.setBorderPainted(false);
		jButtonConfiguracoes.setBounds(402, 11, 33, 25);
		jTetrisPanelToolBar.add(jButtonConfiguracoes);

		jTetrisPanelBarraLateralEsquerda = new JTetrisPanelBarraLateralEsquerda();
		jTetrisPanelBarraLateralEsquerda.setBorder(null);
		jTetrisPanelBarraLateralEsquerda.setBounds(0, 70, 161, 491);
		getContentPane().add(jTetrisPanelBarraLateralEsquerda);

		jLabelExploradorDeProjetos = new JLabel("Explo. de Projetos");
		jLabelExploradorDeProjetos.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		jLabelExploradorDeProjetos.setBounds(10, 10, 145, 14);
		jTetrisPanelBarraLateralEsquerda.add(jLabelExploradorDeProjetos);

		jTetrisListExploradorDeProjetos = new JTetrisListExploradorDeProjetos();
		jTetrisListExploradorDeProjetos.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		jTetrisListExploradorDeProjetos.preencherLista(this);
		jScrollPaneExploradorDeProjetos = new JScrollPane(jTetrisListExploradorDeProjetos);
		jScrollPaneExploradorDeProjetos.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		jScrollPaneExploradorDeProjetos.setBounds(10, 32, 141, 120);
		jTetrisPanelBarraLateralEsquerda.add(jScrollPaneExploradorDeProjetos);

		jLabelExploradorDeJanelas = new JLabel("Explo. de Janelas");
		jLabelExploradorDeJanelas.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		jLabelExploradorDeJanelas.setBounds(10, 176, 141, 14);
		jTetrisPanelBarraLateralEsquerda.add(jLabelExploradorDeJanelas);

		jSeparator1 = new JTetrisSeparator();
		jSeparator1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		jSeparator1.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
		jSeparator1.setBounds(10, 163, 141, 2);
		jTetrisPanelBarraLateralEsquerda.add(jSeparator1);

		jScrollPaneExploradorDeJanelas = new JScrollPane();
		jScrollPaneExploradorDeJanelas.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		jScrollPaneExploradorDeJanelas.setBounds(10, 198, 141, 120);
		jTetrisPanelBarraLateralEsquerda.add(jScrollPaneExploradorDeJanelas);

		jTetrisListExploradorDeJanelas = new JTetrisListExploradorDeJanelas();
		jTetrisListExploradorDeJanelas.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		jTetrisListExploradorDeJanelas.preencherLista(this);
		jScrollPaneExploradorDeJanelas.setViewportView(jTetrisListExploradorDeJanelas);

		jSeparator2 = new JTetrisSeparator();
		jSeparator2.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
		jSeparator2.setBorder(new EmptyBorder(0, 0, 0, 0));
		jSeparator2.setBounds(10, 329, 141, 2);
		jTetrisPanelBarraLateralEsquerda.add(jSeparator2);

		jLabelGerenciadorDeBanco = new JLabel("Banco de Dados");
		jLabelGerenciadorDeBanco.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		jLabelGerenciadorDeBanco.setBounds(10, 342, 200, 14);
		jTetrisPanelBarraLateralEsquerda.add(jLabelGerenciadorDeBanco);

		jComboBoxTabelaGerenciadorDeBancoDeDados = new JTetrisComboBoxTabelas(this);
		jComboBoxTabelaGerenciadorDeBancoDeDados.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		jComboBoxTabelaGerenciadorDeBancoDeDados.setBounds(49, 364, 102, 20);
		jTetrisPanelBarraLateralEsquerda.add(jComboBoxTabelaGerenciadorDeBancoDeDados);

		jLabelTabela = new JLabel("Tab.:");
		jLabelTabela.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		jLabelTabela.setBounds(10, 367, 46, 14);
		jTetrisPanelBarraLateralEsquerda.add(jLabelTabela);

		jScrollPaneGerenciadorDeBancoDeDados = new JScrollPane();
		jScrollPaneGerenciadorDeBancoDeDados.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		jScrollPaneGerenciadorDeBancoDeDados.setBounds(10, 427, 141, 53);
		jTetrisPanelBarraLateralEsquerda.add(jScrollPaneGerenciadorDeBancoDeDados);

		jTetrisListColunasGerenciadorDeBancoDeDados = new JTetrisListColunasGerenciadorDeBancoDeDados(new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/icone_coluna.png")));
		jTetrisListColunasGerenciadorDeBancoDeDados.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		jTetrisListColunasGerenciadorDeBancoDeDados.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		jScrollPaneGerenciadorDeBancoDeDados.setViewportView(jTetrisListColunasGerenciadorDeBancoDeDados);
		jTetrisListColunasGerenciadorDeBancoDeDados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//Caso pressionado botão direito do mouse, exibe PopupMenu do Gerenciador de Banco de dados
				if(arg0.getButton() == MouseEvent.BUTTON3){
					getjPopupMenuColuna().show(getjTetrisListColunasGerenciadorDeBancoDeDados(), arg0.getX(), arg0.getY());
				}
			}
		});
		jTetrisListColunasGerenciadorDeBancoDeDados.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				//Caso o botão ContextMenu do teclado seja pressionado, exibe PopupMenu do Gerenciador de Banco de Dados
				if(arg0.getKeyCode() == KeyEvent.VK_CONTEXT_MENU) {
					getjPopupMenuColuna().show(getjTetrisListColunasGerenciadorDeBancoDeDados(), getjTetrisListColunasGerenciadorDeBancoDeDados().getX(), getjTetrisListColunasGerenciadorDeBancoDeDados().getY());
				}
			}
		});

		jButtonAdicionarTabela = new JButton("");
		jButtonAdicionarTabela.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonAdicionarTabela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Adiciona tabela ao banco de dados do projeto
				//Verifica se tem projeto aberto
				if(getProjeto()!=null){
					//Captura informações da nova tabela
					Tabela retorno = new JDialogNovaTabela(jTetrisListExploradorDeJanelas.getjFramePrincipal()).init();
					if(retorno!=null){
						//Adiciona tabela
						if(getProjeto().getBancoDeDados().adicionarTabela(retorno)){
							//Atualiza combobox
							getjComboBoxTabelaGerenciadorDeBancoDeDados().preencherTabelas();
							//Seta Banco de dados com modificação
							getProjeto().getBancoDeDados().setAlterado(true);
							//Seleciona tabela salva
							getjComboBoxTabelaGerenciadorDeBancoDeDados().setSelectedItem(retorno.getNome());
						}else{
							JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_add_table", getjFramePrincipal()));
						}
					}
				}
			}
		});
		jButtonAdicionarTabela.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/br/com/analisasoftware/tetris/imagem/botao_adicionar_tabela.png")));
		jButtonAdicionarTabela.setToolTipText("Adicionar tabela");
		jButtonAdicionarTabela.setMargin(new Insets(2, 0, 2, 0));
		jButtonAdicionarTabela.setBounds(10, 392, 23, 23);
		jTetrisPanelBarraLateralEsquerda.add(jButtonAdicionarTabela);

		jButtonRemoverTabela = new JButton("");
		jButtonRemoverTabela.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonRemoverTabela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Remove tabela do banco de dados do projeto
				//Verifica se há item selecionado no combobox
				if(jComboBoxTabelaGerenciadorDeBancoDeDados.getSelectedIndex() >=0){
					//Pergunta se deseja realmente remover a tabela
					if(JDialogMensagem.exibirMensagem("Perguntar", Idioma.getTraducao("tetris.message_remove_table", getjFramePrincipal()))==1){
						//Remove a tabela
						getProjeto().getBancoDeDados().removerTabela(""+jComboBoxTabelaGerenciadorDeBancoDeDados.getSelectedItem());
						//Atualiza combobox
						jComboBoxTabelaGerenciadorDeBancoDeDados.preencherTabelas();
						//Seta banco de dados com modificação
						getProjeto().getBancoDeDados().setAlterado(true);
					}
				}
			}
		});
		jButtonRemoverTabela.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/br/com/analisasoftware/tetris/imagem/botao_remover_tabela.png")));
		jButtonRemoverTabela.setToolTipText("Remover tabela");
		jButtonRemoverTabela.setMargin(new Insets(2, 0, 2, 0));
		jButtonRemoverTabela.setBounds(35, 392, 23, 23);
		jTetrisPanelBarraLateralEsquerda.add(jButtonRemoverTabela);

		jButtonAdicionarColuna = new JButton("");
		jButtonAdicionarColuna.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonAdicionarColuna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Adiciona uma coluna a uma tabela do banco de dados do projeto
				//Verifica se há um projeto aberto
				if(getProjeto()!=null){
					//Verifica se tem item selecionado no combobox
					if(jComboBoxTabelaGerenciadorDeBancoDeDados.getSelectedIndex()>=0){
						//Captura informações da nova coluna
						Coluna retorno = new JDialogNovaColuna(getjTetrisListExploradorDeProjetos().getjFramePrincipal()).init();
						if(retorno!=null){
							//Adiciona coluna
							if(getProjeto().getBancoDeDados().getTabela(""+jComboBoxTabelaGerenciadorDeBancoDeDados.getSelectedItem()).adicionarColuna(retorno)){
								//Atualiza JList
								getjComboBoxTabelaGerenciadorDeBancoDeDados().preencherColunas();
								//Seta banco de dados com modificação
								getProjeto().getBancoDeDados().setAlterado(true);
							}else{
								JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_add_column", getjFramePrincipal()));
							}
						}
					}
				}
			}
		});
		jButtonAdicionarColuna.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/br/com/analisasoftware/tetris/imagem/botao_adicionar_coluna.png")));
		jButtonAdicionarColuna.setToolTipText("Adicionar coluna");
		jButtonAdicionarColuna.setMargin(new Insets(2, 0, 2, 0));
		jButtonAdicionarColuna.setBounds(103, 392, 23, 23);
		jTetrisPanelBarraLateralEsquerda.add(jButtonAdicionarColuna);

		jButtonRemoverColuna = new JButton("");
		jButtonRemoverColuna.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonRemoverColuna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Remove coluna
				//Verifica se há coluna selecionada
				if(jTetrisListColunasGerenciadorDeBancoDeDados.getSelectedIndex() >=0){
					//Pergunta se deseja realmente excluir a coluna
					if(JDialogMensagem.exibirMensagem("Perguntar", Idioma.getTraducao("tetris.message_remove_column", getjFramePrincipal()))==1){
						//Remove a coluna
						getProjeto().getBancoDeDados().getTabela(""+jComboBoxTabelaGerenciadorDeBancoDeDados.getSelectedItem()).removerColuna(jTetrisListColunasGerenciadorDeBancoDeDados.getValores()[jTetrisListColunasGerenciadorDeBancoDeDados.getSelectedIndex()]);
						//Atualiza JList
						jComboBoxTabelaGerenciadorDeBancoDeDados.preencherColunas();
						//Seta banco de dados com modificação
						getProjeto().getBancoDeDados().setAlterado(true);
					}
				}
			}
		});
		jButtonRemoverColuna.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/br/com/analisasoftware/tetris/imagem/botao_remover_coluna.png")));
		jButtonRemoverColuna.setToolTipText("Remover coluna");
		jButtonRemoverColuna.setMargin(new Insets(2, 0, 2, 0));
		jButtonRemoverColuna.setBounds(128, 392, 23, 23);
		jTetrisPanelBarraLateralEsquerda.add(jButtonRemoverColuna);

		jTetrisPanelBarraLateralDireita = new JTetrisPanelBarraLateralDireita();
		jTetrisPanelBarraLateralDireita.setBorder(null);
		jTetrisPanelBarraLateralDireita.setBounds(623, 70, 161, 491);
		getContentPane().add(jTetrisPanelBarraLateralDireita);

		jLabelPaletaDeObjetos = new JLabel("Paleta de Objetos");
		jLabelPaletaDeObjetos.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		jLabelPaletaDeObjetos.setBounds(10, 10, 141, 14);
		jTetrisPanelBarraLateralDireita.add(jLabelPaletaDeObjetos);

		jScrollPanePaletaDeObjetos = new JScrollPane();
		jScrollPanePaletaDeObjetos.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		jScrollPanePaletaDeObjetos.setBounds(10, 32, 141, 142);
		jTetrisPanelBarraLateralDireita.add(jScrollPanePaletaDeObjetos);

		jTetrisListPaletaDeObjetos = new JTetrisListPaletaDeObjetos();
		jTetrisListPaletaDeObjetos.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		jTetrisListPaletaDeObjetos.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		jScrollPanePaletaDeObjetos.setViewportView(jTetrisListPaletaDeObjetos);

		jSeparator3 = new JTetrisSeparator();
		jSeparator3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		jSeparator3.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
		jSeparator3.setBounds(10, 185, 141, 2);
		jTetrisPanelBarraLateralDireita.add(jSeparator3);

		jLabelInspetorDeObjetos = new JLabel("Inspetor de Objeto");
		jLabelInspetorDeObjetos.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		jLabelInspetorDeObjetos.setBounds(10, 198, 141, 14);
		jTetrisPanelBarraLateralDireita.add(jLabelInspetorDeObjetos);

		jTabbedPaneInspetorDeObjetos = new JTabbedPane(JTabbedPane.TOP);
		jTabbedPaneInspetorDeObjetos.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		jTabbedPaneInspetorDeObjetos.setBounds(10, 224, 141, 227);
		jTetrisPanelBarraLateralDireita.add(jTabbedPaneInspetorDeObjetos);

		jScrollPanePropriedadesInspetorDeObjetos = new JScrollPane();
		jTabbedPaneInspetorDeObjetos.addTab("Propriedades", null, jScrollPanePropriedadesInspetorDeObjetos, null);

		jTetrisListPropriedadesInspetorDeObjetos = new JTetrisListPropriedadesInspetorDeObjetos();
		jTetrisListPropriedadesInspetorDeObjetos.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		jScrollPanePropriedadesInspetorDeObjetos.setViewportView(jTetrisListPropriedadesInspetorDeObjetos);

		JScrollPane scrollPane_1 = new JScrollPane();
		jTabbedPaneInspetorDeObjetos.addTab("Eventos", null, scrollPane_1, null);

		jTetrisListEventosInspetorDeObjetos = new JTetrisListEventosInspetorDeObjetos();
		jTetrisListEventosInspetorDeObjetos.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		scrollPane_1.setViewportView(jTetrisListEventosInspetorDeObjetos);

		jButtonComponentes = new JButton("Component.");
		jButtonComponentes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Exibe lista de componentes
				mostrarListaDeComponentes();
			}
		});
		jButtonComponentes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButtonComponentes.setIcon(new ImageIcon(JFramePrincipal.class.getResource("/br/com/analisasoftware/tetris/imagem/icone_projeto_tetris.png")));
		jButtonComponentes.setBounds(10, 463, 141, 25);
		jButtonComponentes.setToolTipText("CTRL + F");
		jTetrisPanelBarraLateralDireita.add(jButtonComponentes);

		jTetrisListPaletaDeObjetos.preencherLista(this);

		jTabbedPaneAreaDeTrabalho = new JTabbedPane();
		jTabbedPaneAreaDeTrabalho.setFocusable(false);
		jTabbedPaneAreaDeTrabalho.setBounds(160, 70, 465, 491);
		jTabbedPaneAreaDeTrabalho.addChangeListener(new ChangeListener() {
			//Gera código-fonte da janela aberta, se mudar de aba
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				//Variáveis de auxílio
				int posicao=getjSyntaxTextPaneCodigoFonteGerado().getCaretPosition();
				String codigoFonte="";
				Janela janela = getjDesktopPaneAreaDeTrabalho().getJanela();
				//Verifica se tem uma janela aberta na área de trabalho
				if(janela != null) {
					if(getjTabbedPaneAreaDeTrabalho().getSelectedIndex()==1) {
						janela.gerarCodigoFonte(getjFramePrincipal());
						codigoFonte=Arquivo.lerArquivo(System.getProperty("user.home")+"/TetrisWorkspace/"+getProjeto().getNome()+"/src/tetris/"+getProjeto().getNome().toLowerCase()+"/visao/"+janela.getNome()+".java");
					}
				}
				
				getjSyntaxTextPaneCodigoFonteGerado().setText(codigoFonte);
				if(posicao < getjSyntaxTextPaneCodigoFonteGerado().getText().length()) {
					getjSyntaxTextPaneCodigoFonteGerado().setCaretPosition(posicao);
				}
			}
		});
		
		jDesktopPaneAreaDeTrabalho = new JDesktopPaneAreaDeTrabalho(this);
		jDesktopPaneAreaDeTrabalho.setPreferredSize(new Dimension(465, 491));
		jDesktopPaneAreaDeTrabalho.removeAll();

		jDesktopPaneAreaDeTrabalho.setBackground(jTetrisPanelBarraLateralEsquerda.getBackground());
		
		jScrollPaneDesktopPaneAreaDeTrabalho = new JScrollPane();
		jScrollPaneDesktopPaneAreaDeTrabalho.setBounds(160, 70, 465, 491);
		jScrollPaneDesktopPaneAreaDeTrabalho.getViewport().setView(jDesktopPaneAreaDeTrabalho);
		
		getContentPane().add(jTabbedPaneAreaDeTrabalho);
		
		jSyntaxTextPaneCodigoFonteGerado = new JSyntaxTextPane();
		//jSyntaxTextPaneCodigoFonteGerado.setPreferredSize(new Dimension(465, 491));
		jSyntaxTextPaneCodigoFonteGerado.setEditable(false);
		
		jScrollPaneSyntaxTextPaneCodigoFonteGerado = new JScrollPane();
		jScrollPaneSyntaxTextPaneCodigoFonteGerado.setBounds(160, 70, 465, 491);
		jScrollPaneSyntaxTextPaneCodigoFonteGerado.getViewport().setView(jSyntaxTextPaneCodigoFonteGerado);

		addComponentListener(this);
		
		jTetrisPanelBarraLateralDireita.addComponentListener(this);
		jTetrisPanelBarraLateralEsquerda.addComponentListener(this);
		
		jSeparator1.addComponentListener(this);
		jSeparator2.addComponentListener(this);
		jSeparator3.addComponentListener(this);
		
		MouseAdapter mouseAdapterSalvarLayout = new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0){
				//Manipula redimensionamento de barras laterais
				if(jSeparator1.getY() < 50 ){
					jSeparator1.setLocation(jSeparator1.getX(), 50);
				}
				if(jSeparator2.getY() < jSeparator1.getY()+50 ){
					jSeparator2.setLocation(jSeparator2.getX(), jSeparator1.getY() + 50);
				}
				if(jSeparator3.getY() < 50 ){
					jSeparator3.setLocation(jSeparator3.getX(), 50);
				}
				if(jTetrisPanelBarraLateralDireita.getWidth() < 50 ){
					jTetrisPanelBarraLateralDireita.setSize(50, jTetrisPanelBarraLateralDireita.getHeight());
				}
				
				if(jTetrisPanelBarraLateralEsquerda.getWidth() < 50 ){
					jTetrisPanelBarraLateralEsquerda.setSize(50, jTetrisPanelBarraLateralEsquerda.getHeight());
				}
				salvarLayout();
			}
		};
		
		jTetrisPanelBarraLateralEsquerda.addMouseListener(mouseAdapterSalvarLayout);
		jTetrisPanelBarraLateralDireita.addMouseListener(mouseAdapterSalvarLayout);
		
		jSeparator1.addMouseListener(mouseAdapterSalvarLayout);
		jSeparator2.addMouseListener(mouseAdapterSalvarLayout);
		jSeparator3.addMouseListener(mouseAdapterSalvarLayout);
		
		jTetrisListExploradorDeProjetos.requestFocus();
	}

	//Exibe a janela
	public void init(){
		Arquivo.carregarLayout(jTetrisPanelBarraLateralEsquerda, jTetrisPanelBarraLateralDireita, jSeparator1, jSeparator2, jSeparator3, this);
		
		//Carrega idioma dos componentes
		carregarIdioma();
		
		setVisible(true);
		setExtendedState(MAXIMIZED_BOTH);
		
		getContentPane().add(getjPopupMenuProjeto());
		getContentPane().add(getjPopupMenuJanela());
		
		atualizacao = new Atualizacao(this);
	}
	
	//Salva as configuracoes e layout
	public void salvarLayout(){
		Arquivo.salvarLayout(jTetrisPanelBarraLateralEsquerda.getWidth(), jTetrisPanelBarraLateralDireita.getWidth(), jSeparator1.getY(), jSeparator2.getY(), jSeparator3.getY(), getLookAnFeel(), getIdioma());
	}
	
	//Carrega idioma nos componentes
	public void carregarIdioma(){
		//Barra de ferramentas
		jButtonNovo.setText(Idioma.getTraducao("tetris.new", this));
		jButtonSalvar.setText(Idioma.getTraducao("tetris.save", this));
		jButtonAbrir.setText(Idioma.getTraducao("tetris.open", this));
		jButtonFechar.setText(Idioma.getTraducao("tetris.close", this));
		jButtonExecutar.setText(Idioma.getTraducao("tetris.run", this));
		jButtonExportar.setText(Idioma.getTraducao("tetris.export", this));
		jButtonSobre.setText(Idioma.getTraducao("tetris.about", this));
		if(!jButtonJanelaPrincipal.isEnabled()){
			jButtonJanelaPrincipal.setText(Idioma.getTraducao("tetris.none", this));
		}
		jLabelJanelaPrincipal.setText(Idioma.getTraducao("tetris.main_window", this));
		jButtonLog.setToolTipText(Idioma.getTraducao("tetris.log", this));
		jButtonConfiguracoes.setToolTipText(Idioma.getTraducao("tetris.settings", this));
		
		//Barra lateral esquerda
		jLabelExploradorDeProjetos.setText(Idioma.getTraducao("tetris.project_explorer", this));
		jLabelExploradorDeJanelas.setText(Idioma.getTraducao("tetris.window_explorer", this));
		jLabelGerenciadorDeBanco.setText(Idioma.getTraducao("tetris.mysql_database", this));
		jLabelTabela.setText(Idioma.getTraducao("tetris.tab", this));
		jButtonAdicionarTabela.setToolTipText(Idioma.getTraducao("tetris.add_table", this));
		jButtonRemoverTabela.setToolTipText(Idioma.getTraducao("tetris.remove_table", this));
		jButtonAdicionarColuna.setToolTipText(Idioma.getTraducao("tetris.add_column", this));
		jButtonRemoverColuna.setToolTipText(Idioma.getTraducao("tetris.remove_column", this));
		
		//Barra lateral direita
		jLabelPaletaDeObjetos.setText(Idioma.getTraducao("tetris.object_palette", this));
		jLabelInspetorDeObjetos.setText(Idioma.getTraducao("tetris.object_inspector", this));
		jTabbedPaneInspetorDeObjetos.setTitleAt(0, Idioma.getTraducao("tetris.properties", this));
		jTabbedPaneInspetorDeObjetos.setTitleAt(1, Idioma.getTraducao("tetris.events", this));
		jButtonComponentes.setText(Idioma.getTraducao("tetris.components", this));
		//Área de trabalho
		jTabbedPaneAreaDeTrabalho.removeAll();
		jTabbedPaneAreaDeTrabalho.addTab(Idioma.getTraducao("tetris.desktop", this), jScrollPaneDesktopPaneAreaDeTrabalho);
		jTabbedPaneAreaDeTrabalho.addTab(Idioma.getTraducao("tetris.generated_code", this), jScrollPaneSyntaxTextPaneCodigoFonteGerado);
	}

	//Mostra lista de componentes da janela ativa
	public void mostrarListaDeComponentes(){
		//Aponta para o JInternalFrame
		JInternalFrameJanela jInternalFrameJanela = getjDesktopPaneAreaDeTrabalho().getjInternalFrame();
		//Verifica se há janela aberta na área de trabalho
		if(jInternalFrameJanela!=null){
			if(jInternalFrameJanela.isVisible()){
				if(getjDialogListaDeComponentes() != null){
					getjDialogListaDeComponentes().dispose();
					setjDialogListaDeComponentes(null);
				}
				//Exibe JDialogListaDeComponentes
				setjDialogListaDeComponentes(new JDialogListaDeComponentes(this));
				getjDialogListaDeComponentes().init();
			}
		}
	}

	//Habilita botoes ao abrir projeto
	public void habilitarBotoes(){
		jButtonSalvar.setEnabled(true);
		jButtonFechar.setEnabled(true);
		jButtonExecutar.setEnabled(true);
		jButtonExportar.setEnabled(true);
		jButtonJanelaPrincipal.setEnabled(true);
	}

	//Desabilita botoes ao abrir projeto
	public void desabilitarBotoes(){
		jButtonSalvar.setEnabled(false);
		jButtonFechar.setEnabled(false);
		jButtonExecutar.setEnabled(false);
		jButtonExportar.setEnabled(false);
		jButtonJanelaPrincipal.setEnabled(false);
	}

	//Habilita menus ao abrir projeto
	public void habilitarMenus(JPopupMenuProjeto jPopupMenuProjeto){
		jPopupMenuProjeto.getjMenuItemSalvar().setEnabled(true);
		jPopupMenuProjeto.getjMenuItemFechar().setEnabled(true);
		jPopupMenuProjeto.getjMenuItemExecutar().setEnabled(true);
		jPopupMenuProjeto.getjMenuItemExportar().setEnabled(true);

	}

	//Desabilita menus ao abrir projeto
	public void desabilitarMenus(JPopupMenuProjeto jPopupMenuProjeto){
		jPopupMenuProjeto.getjMenuItemSalvar().setEnabled(false);
		jPopupMenuProjeto.getjMenuItemFechar().setEnabled(false);
		jPopupMenuProjeto.getjMenuItemExecutar().setEnabled(false);
		jPopupMenuProjeto.getjMenuItemExportar().setEnabled(false);
	}
	
	//Abre configuracoes
	public void abrirConfiguracoes(){
		if(getjDialogConfiguracoes() != null){
			getjDialogConfiguracoes().dispose();
			setjDialogConfiguracoes(null);
		}
		setjDialogConfiguracoes(new JDialogConfiguracoes(this));
		getjDialogConfiguracoes().init();
		
		carregarIdioma();
	}

	//Eventos
	public void componentResizedJFramePrincipal(){
		//Ao redimensionar janela, ajusta componentes 
		jTetrisPanelToolBar.setSize(getContentPane().getWidth(), 70);
		jButtonSobre.setLocation(jTetrisPanelToolBar.getWidth() - jButtonSobre.getWidth() - 5, 0);
		jButtonJanelaPrincipal.setLocation(jTetrisPanelToolBar.getWidth() - jButtonSobre.getWidth() - jButtonJanelaPrincipal.getWidth() - 15, jButtonJanelaPrincipal.getY());
		jLabelJanelaPrincipal.setLocation(jTetrisPanelToolBar.getWidth() - jButtonSobre.getWidth() - jButtonJanelaPrincipal.getWidth() - 15, jLabelJanelaPrincipal.getY());
		jSeparatorJanelaPrincipal.setLocation(jTetrisPanelToolBar.getWidth() - jButtonSobre.getWidth() - 5, jSeparatorJanelaPrincipal.getY());
		jSeparatorJanelaPrincipal2.setLocation(jTetrisPanelToolBar.getWidth() - jButtonSobre.getWidth() - jButtonJanelaPrincipal.getWidth() - 30, jLabelJanelaPrincipal.getY());
		jButtonLog.setLocation(jSeparatorJanelaPrincipal2.getX() - 35, jButtonLog.getY());
		jButtonConfiguracoes.setLocation(jSeparatorJanelaPrincipal2.getX() - 35, jButtonConfiguracoes.getY());
		
		//Painel de Log
		jTetrisPanelLog.setLocation(getContentPane().getWidth() - jTetrisPanelLog.getWidth() - jTetrisPanelBarraLateralDireita.getWidth(), 70);

		//Barra lateral esquerda
		jTetrisPanelBarraLateralEsquerda.setSize(jTetrisPanelBarraLateralEsquerda.getWidth(), getContentPane().getHeight() - jTetrisPanelToolBar.getHeight());

		jScrollPaneExploradorDeProjetos.setSize(jTetrisPanelBarraLateralEsquerda.getWidth() - jScrollPaneExploradorDeProjetos.getX()*2, jSeparator1.getY() - jScrollPaneExploradorDeProjetos.getY() - 5);
		jTetrisListExploradorDeProjetos.setSize(jTetrisPanelBarraLateralEsquerda.getWidth() - jScrollPaneExploradorDeProjetos.getX()*2, jSeparator1.getY() - jScrollPaneExploradorDeProjetos.getY() - 5);
		
		jLabelExploradorDeJanelas.setLocation(jLabelExploradorDeJanelas.getX(), jSeparator1.getY() + 5);
		
		jScrollPaneExploradorDeJanelas.setLocation(jScrollPaneExploradorDeJanelas.getX(), jLabelExploradorDeJanelas.getY() + 20);
		jScrollPaneExploradorDeJanelas.setSize(jTetrisPanelBarraLateralEsquerda.getWidth() - jScrollPaneExploradorDeJanelas.getX()*2, jSeparator2.getY() - jScrollPaneExploradorDeJanelas.getY() - 5);
		jTetrisListExploradorDeJanelas.setSize(jTetrisPanelBarraLateralEsquerda.getWidth() - jScrollPaneExploradorDeJanelas.getX()*2, jSeparator2.getY() - jScrollPaneExploradorDeJanelas.getY() - 5);
		
		jLabelGerenciadorDeBanco.setLocation(jLabelGerenciadorDeBanco.getX(), jSeparator2.getY() + 5);
		jLabelTabela.setLocation(jLabelTabela.getX(), jLabelGerenciadorDeBanco.getY() + 25);
		jComboBoxTabelaGerenciadorDeBancoDeDados.setLocation(jComboBoxTabelaGerenciadorDeBancoDeDados.getX(), jLabelGerenciadorDeBanco.getY() + 25);
		
		jButtonAdicionarTabela.setLocation(jButtonAdicionarTabela.getX(), jLabelTabela.getY() + 25);
		jButtonAdicionarColuna.setLocation(jButtonAdicionarColuna.getX(), jLabelTabela.getY() + 25);
		
		jButtonRemoverTabela.setLocation(jButtonRemoverTabela.getX(), jLabelTabela.getY() + 25);
		jButtonRemoverColuna.setLocation(jButtonRemoverColuna.getX(), jLabelTabela.getY() + 25);
		
		jComboBoxTabelaGerenciadorDeBancoDeDados.setSize(jTetrisPanelBarraLateralEsquerda.getWidth() - jComboBoxTabelaGerenciadorDeBancoDeDados.getX() - 10, jComboBoxTabelaGerenciadorDeBancoDeDados.getHeight());
		jComboBoxTabelaGerenciadorDeBancoDeDados.setVisible(false);
		jComboBoxTabelaGerenciadorDeBancoDeDados.setVisible(true);
		
		jScrollPaneGerenciadorDeBancoDeDados.setLocation(jScrollPaneGerenciadorDeBancoDeDados.getX(), jButtonAdicionarTabela.getY() + jButtonAdicionarTabela.getHeight() + 5);
		jScrollPaneGerenciadorDeBancoDeDados.setSize(jTetrisPanelBarraLateralEsquerda.getWidth() - jScrollPaneGerenciadorDeBancoDeDados.getX()*2, jTetrisPanelBarraLateralEsquerda.getHeight() - (int)jScrollPaneGerenciadorDeBancoDeDados.getLocation().getY() - 10);
		jTetrisListColunasGerenciadorDeBancoDeDados.setSize(jTetrisPanelBarraLateralEsquerda.getWidth() - jScrollPaneGerenciadorDeBancoDeDados.getX()*2, jTetrisPanelBarraLateralEsquerda.getHeight() - (int)jTetrisListColunasGerenciadorDeBancoDeDados.getLocation().getY() - 10);
		
		jSeparator1.setSize(jTetrisPanelBarraLateralEsquerda.getWidth() - jSeparator1.getX()*2, jSeparator1.getHeight());
		jSeparator2.setSize(jTetrisPanelBarraLateralEsquerda.getWidth() - jSeparator2.getX()*2, jSeparator2.getHeight());

		//Barra lateral direita
		jTetrisPanelBarraLateralDireita.setBounds(getContentPane().getWidth() - jTetrisPanelBarraLateralDireita.getWidth(), jTetrisPanelToolBar.getHeight(), jTetrisPanelBarraLateralDireita.getWidth(), getContentPane().getHeight() - jTetrisPanelToolBar.getHeight());

		jTabbedPaneInspetorDeObjetos.setLocation(10, jTetrisPanelBarraLateralDireita.getHeight() - jTabbedPaneInspetorDeObjetos.getHeight() - 35);
		jButtonComponentes.setLocation(10, jTetrisPanelBarraLateralDireita.getHeight() - 30);

		jScrollPanePaletaDeObjetos.setSize(jTetrisPanelBarraLateralDireita.getWidth() - jScrollPanePaletaDeObjetos.getX()*2, (int)jSeparator3.getY() - (int)jScrollPanePaletaDeObjetos.getLocation().getY() - 5);
		jTetrisListPaletaDeObjetos.setSize(jTetrisPanelBarraLateralDireita.getWidth() - jScrollPanePaletaDeObjetos.getX()*2, (int)jSeparator3.getY() - (int)jScrollPanePaletaDeObjetos.getLocation().getY() - 5);
		
		jSeparator3.setSize(jTetrisPanelBarraLateralDireita.getWidth() - jSeparator3.getX()*2, jSeparator3.getHeight());
		jLabelInspetorDeObjetos.setLocation(10, (int)jSeparator3.getY() + 5);
		
		jButtonComponentes.setSize(jTetrisPanelBarraLateralDireita.getWidth() - jButtonComponentes.getX()*2, jButtonComponentes.getHeight());
		
		jTabbedPaneInspetorDeObjetos.setBounds(jTabbedPaneInspetorDeObjetos.getX(), jLabelInspetorDeObjetos.getY() + 20, jTetrisPanelBarraLateralDireita.getWidth() - jTabbedPaneInspetorDeObjetos.getX()*2, jTetrisPanelBarraLateralDireita.getHeight() - jLabelInspetorDeObjetos.getY() - 20 - jButtonComponentes.getHeight() - 10);

		jTabbedPaneAreaDeTrabalho.setBounds(jTetrisPanelBarraLateralEsquerda.getWidth(), jTetrisPanelToolBar.getHeight(), getContentPane().getWidth() - jTetrisPanelBarraLateralDireita.getWidth() - jTetrisPanelBarraLateralEsquerda.getWidth(), getContentPane().getHeight() - jTetrisPanelToolBar.getHeight());

	}
	//Finaliza o TetrisIDE
	public void fecharJanela(){
		if(getProjeto()!=null){
			if(JDialogMensagem.exibirMensagem("Perguntar", Idioma.getTraducao("tetris.message_close", this))==1){
				System.exit(0);
			}
		}else{
			if(JDialogMensagem.exibirMensagem("Perguntar", Idioma.getTraducao("tetris.message_exit_application", this))==1){
				System.exit(0);
			}
		}
	}
	//Getters e Setters
	public JFramePrincipal getjFramePrincipal(){
		return this;
	}

	public JPopupMenuProjeto getjPopupMenuProjeto() {
		if(jPopupMenuProjeto == null){
			jPopupMenuProjeto = new JPopupMenuProjeto(this);
		}

		if(getProjeto()!=null){
			habilitarMenus(jPopupMenuProjeto);
		}else{
			desabilitarMenus(jPopupMenuProjeto);
		}

		return jPopupMenuProjeto;

	}

	public JPopupMenuJanela getjPopupMenuJanela() {
		if(jPopupMenuJanela == null){
			jPopupMenuJanela = new JPopupMenuJanela(this);
		}
		return jPopupMenuJanela;

	}
	
	public JPopupMenuAreaDeTransferencia getjPopupMenuAreaDeTransferencia() {
		if(jPopupMenuAreaDeTransferencia == null){
			jPopupMenuAreaDeTransferencia = new JPopupMenuAreaDeTransferencia(this);
		}
		return jPopupMenuAreaDeTransferencia;

	}

	public JPopupMenuColunasGerenciadorDeBancoDeDados getjPopupMenuColuna() {
		if(jPopupMenuColunasGerenciadorDeBancoDeDados == null){
			jPopupMenuColunasGerenciadorDeBancoDeDados = new JPopupMenuColunasGerenciadorDeBancoDeDados(this);
		}
		return jPopupMenuColunasGerenciadorDeBancoDeDados;

	}

	public JTetrisListExploradorDeProjetos getjTetrisListExploradorDeProjetos() {
		return jTetrisListExploradorDeProjetos;
	}

	public JTetrisListExploradorDeJanelas getjTetrisListExploradorDeJanelas() {
		return jTetrisListExploradorDeJanelas;
	}

	public JTetrisListColunasGerenciadorDeBancoDeDados getjTetrisListColunasGerenciadorDeBancoDeDados() {
		return jTetrisListColunasGerenciadorDeBancoDeDados;
	}

	public JTetrisListPaletaDeObjetos getjTetrisListPaletaDeObjetos() {
		return jTetrisListPaletaDeObjetos;
	}

	public JTetrisListPropriedadesInspetorDeObjetos getjTetrisListPropriedadesInspetorDeObjetos() {
		return jTetrisListPropriedadesInspetorDeObjetos;
	}

	public JTetrisListEventosInspetorDeObjetos getjTetrisListEventosInspetorDeObjetos() {
		return jTetrisListEventosInspetorDeObjetos;
	}

	public JTetrisPanelLog getjTetrisPanelLog() {
		return jTetrisPanelLog;
	}

	public JDesktopPaneAreaDeTrabalho getjDesktopPaneAreaDeTrabalho() {
		return jDesktopPaneAreaDeTrabalho;
	}
	
	public JSyntaxTextPane getjSyntaxTextPaneCodigoFonteGerado() {
		return jSyntaxTextPaneCodigoFonteGerado;
	}

	public JTetrisComboBoxTabelas getjComboBoxTabelaGerenciadorDeBancoDeDados() {
		return jComboBoxTabelaGerenciadorDeBancoDeDados;
	}

	public JButton getjButtonJanelaPrincipal() {
		return jButtonJanelaPrincipal;
	}
	
	public JDialogComponentesExternos getjDialogComponentesExternos() {
		return jDialogComponentesExternos;
	}
	
	public void setjDialogComponentesExternos(
			JDialogComponentesExternos jDialogComponentesExternos) {
		this.jDialogComponentesExternos = jDialogComponentesExternos;
	}

	public JDialogListaDeComponentes getjDialogListaDeComponentes() {
		return jDialogListaDeComponentes;
	}

	public void setjDialogListaDeComponentes(
			JDialogListaDeComponentes jDialogListaDeComponentes) {
		this.jDialogListaDeComponentes = jDialogListaDeComponentes;
	}
	
	public JDialogConfiguracoes getjDialogConfiguracoes() {
		return jDialogConfiguracoes;
	}

	public void setjDialogConfiguracoes(JDialogConfiguracoes jDialogConfiguracoes) {
		this.jDialogConfiguracoes = jDialogConfiguracoes;
	}
	
	public JScrollPane getjScrollPaneDesktopPaneAreaDeTrabalho() {
		return jScrollPaneDesktopPaneAreaDeTrabalho;
	}
	
	public JScrollPane getjScrollPaneSyntaxTextPaneCodigoFonteGerado() {
		return jScrollPaneSyntaxTextPaneCodigoFonteGerado;
	}
	
	public JTabbedPane getjTabbedPaneAreaDeTrabalho() {
		return jTabbedPaneAreaDeTrabalho;
	}
	
	public JButton getjButtonComponentes() {
		return jButtonComponentes;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public AreaDeTransferencia getAreaDeTransferencia() {
		return areaDeTransferencia;
	}
	
	public String getLookAnFeel() {
		
		return lookAnFeel;
	}

	public void setLookAnFeel(String lookAnFeel) {
		this.lookAnFeel = lookAnFeel;
	}
	
	public Atualizacao getAtualizacao(){
		return atualizacao;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	//Representa evento de movimento de componentes
	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		//Redimensiona listas dentro de barras laterais
		if(e.getSource() instanceof JTetrisSeparator){
			componentResizedJFramePrincipal();
		}
	}
	//Representa evento de redimensionamento de componentes
	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		//Redimensiona barra lateral
		if((e.getSource() instanceof JTetrisSeparator)==false){
			componentResizedJFramePrincipal();
		}
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
