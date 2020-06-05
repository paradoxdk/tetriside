/*
 *Janela de configurações do TetrisIDE
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 26 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.janelas;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;

import componentes.visao.JTetrisButton;
import componentes.visao.JTetrisComboBox;
import componentes.visao.JTetrisPanel;

import javax.swing.JLabel;

import br.com.analisasoftware.tetris.modelo.Idioma;

import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class JDialogConfiguracoes extends JDialog {
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Componentes gráficos
	private JTetrisComboBox jTetrisComboBoxIdioma;
	private JTetrisComboBox jTetrisComboBoxLookAndFeel;
	private JTetrisButton jTetrisButtonSalvar;
	private JTetrisButton jTetrisButtonCancelar;
	private JLabel jLabelIdioma;
	private JLabel jLabelLookandfeelDosProjetos;
	
	public JDialogConfiguracoes(JFramePrincipal jFramePrincipal) {
		super();
		this.jFramePrincipal = jFramePrincipal;
		setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogComponentesExternos.class.getResource("/br/com/analisasoftware/tetris/imagem/icone.png")));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		JTetrisPanel tetrisPanel = new JTetrisPanel();
		setContentPane(tetrisPanel);
		
		jLabelIdioma = new JLabel("Idioma");
		jLabelIdioma.setBounds(12, 12, 106, 14);
		tetrisPanel.add(jLabelIdioma);
		
		jTetrisComboBoxIdioma = new JTetrisComboBox();
		
		jTetrisComboBoxIdioma.setBounds(12, 32, 326, 25);
		String[] idiomas = Idioma.getIdiomas(getjFramePrincipal());
		
		for (int i = 0; i < idiomas.length; i++) {
			jTetrisComboBoxIdioma.addItem(idiomas[i]);
		}
		
		jTetrisComboBoxIdioma.setSelectedIndex(0);
		tetrisPanel.add(jTetrisComboBoxIdioma);
		
		jLabelLookandfeelDosProjetos = new JLabel("LookAndFeel dos Projetos");
		jLabelLookandfeelDosProjetos.setBounds(12, 62, 328, 14);
		tetrisPanel.add(jLabelLookandfeelDosProjetos);
		
		jTetrisComboBoxLookAndFeel = new JTetrisComboBox();
		jTetrisComboBoxLookAndFeel.setEditable(true);
		jTetrisComboBoxLookAndFeel.addItem("SystemLookAndFeel");
		jTetrisComboBoxLookAndFeel.addItem("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		jTetrisComboBoxLookAndFeel.addItem("javax.swing.plaf.metal.MetalLookAndFeel");
		jTetrisComboBoxLookAndFeel.addItem("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		jTetrisComboBoxLookAndFeel.addItem("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		jTetrisComboBoxLookAndFeel.addItem("javax.swing.plaf.mac.MacLookAndFeel");
		jTetrisComboBoxLookAndFeel.addItem("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		jTetrisComboBoxLookAndFeel.setBounds(12, 82, 326, 25);
		jTetrisComboBoxLookAndFeel.setSelectedIndex(0);
		tetrisPanel.add(jTetrisComboBoxLookAndFeel);
		
		jTetrisComboBoxIdioma.setSelectedItem(""+jFramePrincipal.getIdioma());
		jTetrisComboBoxLookAndFeel.setText(""+jFramePrincipal.getLookAnFeel());
		
		jTetrisButtonSalvar = new JTetrisButton("Salvar");
		jTetrisButtonSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Salva configurações do TetrisIDE e fecha a janela
				getjFramePrincipal().setIdioma(jTetrisComboBoxIdioma.getText());
				getjFramePrincipal().setLookAnFeel(jTetrisComboBoxLookAndFeel.getText());
				
				getjFramePrincipal().salvarLayout();
				
				dispose();
			}
		});
		jTetrisButtonSalvar.setIcon(new ImageIcon(JDialogConfiguracoes.class.getResource("/br/com/analisasoftware/tetris/imagem/sim.png")));
		jTetrisButtonSalvar.setBounds(82, 150, 118, 25);
		jTetrisButtonSalvar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jTetrisButtonSalvar);
		
		jTetrisButtonCancelar = new JTetrisButton("Cancelar");
		jTetrisButtonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Fecha a janela
				dispose();
			}
		});
		jTetrisButtonCancelar.setIcon(new ImageIcon(JDialogConfiguracoes.class.getResource("/br/com/analisasoftware/tetris/imagem/sair.png")));
		jTetrisButtonCancelar.setBounds(210, 150, 128, 25);
		jTetrisButtonCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tetrisPanel.add(jTetrisButtonCancelar);
		
		setTitle("Configurações");
		setSize(new Dimension(354, 229));
		setResizable(false);
		setLocationRelativeTo(jFramePrincipal);
		setModal(true);
	}
	
	//Carrega idioma nos componentes
	public void carregarIdioma(){
		setTitle(Idioma.getTraducao("tetris.settings", jFramePrincipal));
		jLabelIdioma.setText(Idioma.getTraducao("tetris.language", jFramePrincipal));
		jLabelLookandfeelDosProjetos.setText(Idioma.getTraducao("tetris.projects_look_and_feel", jFramePrincipal));
		jTetrisButtonSalvar.setText(Idioma.getTraducao("tetris.save", jFramePrincipal));
		jTetrisButtonCancelar.setText(Idioma.getTraducao("tetris.cancel", jFramePrincipal));
	}
	
	//Inicializa e exibe a janela
	public void init(){
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
		for (int i = 0; i < componentes.length; i++) {
			componentes[i].addKeyListener(keyAdapter);
		}
		
		setVisible(true);
	}

	//Getters and Setters
	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}
}
