/*
 *JList do Explorador de Janelas do TetrisIDE
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 25 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.componentes;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;

import br.com.analisasoftware.tetris.visao.janelas.JFramePrincipal;
import componentes.visao.JTetrisList;
import tetris.modelo.componentes.Janela;

@SuppressWarnings("serial")
public class JTetrisListExploradorDeJanelas extends JTetrisList {
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;

	public JTetrisListExploradorDeJanelas() {
		super();
		addMouseListener(new MouseAdapter() {
			//Representa o evento de clique do mouse
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//Verifica se o botão direito do mouse foi acionado
				if(arg0.getButton() == MouseEvent.BUTTON3){
					//Exibe o PopupMenu para manipulação da lista de janelas
					getjFramePrincipal().getjPopupMenuJanela().show(getjFramePrincipal().getjTetrisListExploradorDeJanelas(), arg0.getX(), arg0.getY());
				}else{
					//Verifica se foi efetuado dois cliques
					if(arg0.getClickCount() == 2){
						//Abre a janela selecionada
						getjFramePrincipal().getjPopupMenuJanela().getjMenuItemAbrir().doClick();
					}
				}
			}
		});
		
		addKeyListener(new KeyAdapter() {
			//Representa o evento de realização de pressionamento de tecla
			@Override
			public void keyReleased(KeyEvent arg0){
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					//Abre a janela selecionada
					getjFramePrincipal().getjPopupMenuJanela().getjMenuItemAbrir().doClick();
				}else if(arg0.getKeyCode() == KeyEvent.VK_F2){
					//Renomea janela selecionada
					getjFramePrincipal().getjPopupMenuJanela().getjMenuItemRenomear().doClick();
				}else if(arg0.getKeyCode() == KeyEvent.VK_DELETE){
					//Exclui janela selecionada
					getjFramePrincipal().getjPopupMenuJanela().getjMenuItemExcluir().doClick();
				}else if(arg0.getKeyCode() == KeyEvent.VK_CONTEXT_MENU) {
					//Exibe o PopupMenu para manipulação da lista de janelas
					getjFramePrincipal().getjPopupMenuJanela().show(getjFramePrincipal().getjTetrisListExploradorDeJanelas(), getX() - (getWidth() / 2), getY() - (getHeight() / 2));
				}
			}
		});
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
	}
	//Preenche lista de janelas
	public void preencherLista(JFramePrincipal jFramePrincipal){
		this.jFramePrincipal=jFramePrincipal;
		//Define ícone que fica a esquerda dos nomes das janelas
		setImagens(new ImageIcon []{new ImageIcon(jFramePrincipal.getClass().getResource("/br/com/analisasoftware/tetris/imagem/icone_janela.png"))});
		//Verifica se há um projeto aberto
		if(jFramePrincipal.getProjeto()!=null){
			//Aponta para a lista de janelas do projeto
			ArrayList<Janela> arrayListJanelas = jFramePrincipal.getProjeto().getArrayListJanelas();

			//Preenche o JList
			String[] valores = new String[arrayListJanelas.size()];

			for (int i = 0; i < valores.length; i++) {
				valores[i]=arrayListJanelas.get(i).getNome();
			}

			setValores(valores);

		}else{
			setValores(null);
		}
	}

	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}


}
