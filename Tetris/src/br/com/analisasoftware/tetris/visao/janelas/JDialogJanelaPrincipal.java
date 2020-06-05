/*
 *Seleciona janela principal
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 27 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.janelas;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;

import componentes.visao.JTetrisButton;
import componentes.visao.JTetrisList;
import componentes.visao.JTetrisPanel;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import br.com.analisasoftware.tetris.modelo.Idioma;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class JDialogJanelaPrincipal extends JDialog {
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Variável de retorno
	private String retorno;
	//Componentes gráficos
	private JTetrisList jTetrisListJanelas;
	private JTetrisButton jTetrisButtonOk;
	private JTetrisButton jTetrisButtonCancelar;
	private JLabel jLabelSelecioneAJanela;

	public JDialogJanelaPrincipal(JFramePrincipal jFramePrincipal) {
		super();
		retorno=null;
		this.jFramePrincipal = jFramePrincipal;
		JTetrisPanel tetrisPanel = new JTetrisPanel();
		setContentPane(tetrisPanel);

		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogComponentesExternos.class.getResource("/br/com/analisasoftware/tetris/imagem/icone.png")));

		jLabelSelecioneAJanela = new JLabel("Selecione a janela principal do sistema");
		jLabelSelecioneAJanela.setBounds(12, 12, 348, 15);
		tetrisPanel.add(jLabelSelecioneAJanela);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 32, 348, 180);
		tetrisPanel.add(scrollPane);
		//Lista janelas do projeto no JList
		String[] valores = new String[jFramePrincipal.getProjeto().getArrayListJanelas().size()];
		int selectedIndex=-1;

		for (int i = 0; i < valores.length; i++) {
			valores[i]=jFramePrincipal.getProjeto().getArrayListJanelas().get(i).getNome();
			if(jFramePrincipal.getProjeto().getJanelaPrincipal()!=null){
				if(valores[i].equals(jFramePrincipal.getProjeto().getJanelaPrincipal().getNome())){
					selectedIndex=i;
				}
			}
		}

		jTetrisListJanelas = new JTetrisList(valores, new ImageIcon []{new ImageIcon(jFramePrincipal.getClass().getResource("/br/com/analisasoftware/tetris/imagem/icone_janela.png"))});
		jTetrisListJanelas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				//Caso pressione ENTER, aciona o botão OK
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					jTetrisButtonOk.doClick();
				}
			}
		});
		jTetrisListJanelas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Caso efetue dois cliques do mouse, aciona o botão OK
				if(e.getClickCount()==2){
					jTetrisButtonOk.doClick();
				}
			}
		});
		jTetrisListJanelas.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setViewportView(jTetrisListJanelas);

		jTetrisListJanelas.setSelectedIndex(selectedIndex);

		jTetrisButtonOk = new JTetrisButton("OK");
		jTetrisButtonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Define janela principal
				//Verifica se há uma janela selecionada
				if(jTetrisListJanelas.getSelectedIndex() >=0){
					//Define a janela selecionada como retorno
					retorno = jTetrisListJanelas.getSelectedValue();
					//Fecha janela
					dispose();
				}else{
					JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_there_is_no_selected_window", getjFramePrincipal()));
				}
			}
		});
		jTetrisButtonOk.setIcon(new ImageIcon(JDialogJanelaPrincipal.class.getResource("/br/com/analisasoftware/tetris/imagem/sim.png")));
		jTetrisButtonOk.setBounds(106, 224, 117, 25);
		jTetrisButtonOk.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jTetrisButtonOk);

		jTetrisButtonCancelar = new JTetrisButton("Cancelar");
		jTetrisButtonCancelar.setIcon(new ImageIcon(JDialogJanelaPrincipal.class.getResource("/br/com/analisasoftware/tetris/imagem/sair.png")));
		jTetrisButtonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Fecha a janela
				dispose();
			}
		});
		jTetrisButtonCancelar.setBounds(235, 224, 125, 25);
		jTetrisButtonCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jTetrisButtonCancelar);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(jFramePrincipal);
		setModal(true);
		setTitle("Janela Principal");
		setSize(new Dimension(374, 314));
		setResizable(false);
		setLocationRelativeTo(jFramePrincipal);
	}

	//Carrega idioma nos componentes
	public void carregarIdioma(){
		setTitle(Idioma.getTraducao("tetris.main_window", jFramePrincipal));
		jTetrisButtonOk.setText(Idioma.getTraducao("tetris.ok", jFramePrincipal));
		jTetrisButtonCancelar.setText(Idioma.getTraducao("tetris.cancel", jFramePrincipal));
		jLabelSelecioneAJanela.setText(Idioma.getTraducao("tetris.select_the_main_window", jFramePrincipal));
	}
	//Inicializa e exibe a janela, retornando uma String
	public String init(){
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
		jTetrisListJanelas.addKeyListener(keyAdapter);
		for (int i = 0; i < componentes.length; i++) {
			componentes[i].addKeyListener(keyAdapter);
		}
		
		setVisible(true);
		return retorno;
	}

	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}

	public void setjFramePrincipal(JFramePrincipal jFramePrincipal) {
		this.jFramePrincipal = jFramePrincipal;
	}
}
