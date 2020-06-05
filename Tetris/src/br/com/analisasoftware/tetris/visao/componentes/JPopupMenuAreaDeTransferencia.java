/*
 *Popup da Área de transferência do TetrisIDE
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 25 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.componentes;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.KeyStroke;

import br.com.analisasoftware.tetris.modelo.Idioma;
import br.com.analisasoftware.tetris.visao.janelas.JFramePrincipal;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

@SuppressWarnings("serial")
public class JPopupMenuAreaDeTransferencia extends JPopupMenu {
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Itens de Menus do PopupMenu
	private JMenuItem jMenuItemCopiar;
	private JMenuItem jMenuItemRecortar;
	private JMenuItem jMenuItemColoar;
	private JMenuItem jMenuItemDesfazer;
	private JMenuItem jMenuItemRefazer;
	private JMenuItem jMenuItemExcluir;

	//Inicialica o objeto
	public JPopupMenuAreaDeTransferencia(JFramePrincipal jFramePrincipal) {
		this.jFramePrincipal=jFramePrincipal;

		jMenuItemCopiar = new JMenuItem("Copiar");
		jMenuItemCopiar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		jMenuItemCopiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Copia componentes em foco para a área de transferência
				getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().copiarComponentes();
			}
		});
		jMenuItemCopiar.setIcon(new ImageIcon(JPopupMenuAreaDeTransferencia.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_copiar.png")));
		add(jMenuItemCopiar);

		jMenuItemRecortar = new JMenuItem("Recortar");
		jMenuItemRecortar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		jMenuItemRecortar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Recorta componentes em foco para a área de transferência
				getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().moverComponentes();
			}
		});
		jMenuItemRecortar.setIcon(new ImageIcon(JPopupMenuAreaDeTransferencia.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_recortar.png")));
		add(jMenuItemRecortar);

		JSeparator separator = new JSeparator();
		add(separator);

		jMenuItemColoar = new JMenuItem("Colar");
		jMenuItemColoar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		jMenuItemColoar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Cola componentes da área de transferência para o componente em foco
				getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().colarComponentes();
			}
		});
		jMenuItemColoar.setIcon(new ImageIcon(JPopupMenuAreaDeTransferencia.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_colar.png")));
		add(jMenuItemColoar);

		JSeparator separator_1 = new JSeparator();
		add(separator_1);

		jMenuItemDesfazer = new JMenuItem("Desfazer");
		jMenuItemDesfazer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
		jMenuItemDesfazer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Desfaz uma ação
				getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela().voltaEstadoJanela();
				getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().atualizarJanela();
			}
		});
		jMenuItemDesfazer.setIcon(new ImageIcon(JPopupMenuAreaDeTransferencia.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_desfazer.png")));
		add(jMenuItemDesfazer);

		jMenuItemRefazer = new JMenuItem("Refazer");
		jMenuItemRefazer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		jMenuItemRefazer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Refaz uma ação
				getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela().avancaEstadoJanela();
				getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().atualizarJanela();
			}
		});
		jMenuItemRefazer.setIcon(new ImageIcon(JPopupMenuAreaDeTransferencia.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_refazer.png")));
		add(jMenuItemRefazer);

		JSeparator separator_2 = new JSeparator();
		add(separator_2);

		jMenuItemExcluir = new JMenuItem("Excluir");
		jMenuItemExcluir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		jMenuItemExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Exclui componentes em foco de uma janela
				getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().excluirComponentes();
			}
		});
		jMenuItemExcluir.setIcon(new ImageIcon(JPopupMenuAreaDeTransferencia.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_excluir.png")));
		add(jMenuItemExcluir);

	}
	//Mostra o PopupMenu
	@Override
	public void show(Component component, int x, int y){
		super.show(component, x, y);

		carregarIdioma();
	}

	//Carrega idioma nos componentes
	public void carregarIdioma(){

		jMenuItemColoar.setText(Idioma.getTraducao("tetris.paste", jFramePrincipal));
		jMenuItemCopiar.setText(Idioma.getTraducao("tetris.copy", jFramePrincipal));
		jMenuItemDesfazer.setText(Idioma.getTraducao("tetris.undo", jFramePrincipal));
		jMenuItemExcluir.setText(Idioma.getTraducao("tetris.delete", jFramePrincipal));
		jMenuItemRecortar.setText(Idioma.getTraducao("tetris.cut", jFramePrincipal));
		jMenuItemRefazer.setText(Idioma.getTraducao("tetris.redo", jFramePrincipal));
	}

	//Getters and Setters
	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}

	public JMenuItem getjMenuItemDesfazer() {
		return jMenuItemDesfazer;
	}

	public JMenuItem getjMenuItemRefazer() {
		return jMenuItemRefazer;
	}

}
