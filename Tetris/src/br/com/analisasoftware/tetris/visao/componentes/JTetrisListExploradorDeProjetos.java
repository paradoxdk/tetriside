/*
 *JList do Explorador de Projetos do TetrisIDE
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 25 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.componentes;

import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;

import br.com.analisasoftware.tetris.modelo.Arquivo;
import br.com.analisasoftware.tetris.modelo.Idioma;
import br.com.analisasoftware.tetris.visao.janelas.JDialogMensagem;
import br.com.analisasoftware.tetris.visao.janelas.JFramePrincipal;
import componentes.visao.JTetrisList;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class JTetrisListExploradorDeProjetos extends JTetrisList {
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	
	public JTetrisListExploradorDeProjetos() {
		super();
		addMouseListener(new MouseAdapter() {
			//Representa evento de clique do mouse
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//Verifica se o botão direito do mouse foi acionado
				if(arg0.getButton() == MouseEvent.BUTTON3){
					//Exibe o PopupMenu do Explorador de Projetos
					getjFramePrincipal().getjPopupMenuProjeto().show(getjFramePrincipal().getjTetrisListExploradorDeProjetos(), arg0.getX(), arg0.getY());
				}else{
					//Verifica se foi efetuado dois cliques
					if(arg0.getClickCount() == 2){
						//Abre o projeto selecionado
						getjFramePrincipal().getjPopupMenuProjeto().getjMenuItemAbrir().doClick();
					}
				}
			}
		});
		
		addKeyListener(new KeyAdapter() {
			//Representa o evento de realização de pressionamento de tecla
			@Override
			public void keyReleased(KeyEvent arg0){
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					//Abre o projeto selecionado
					getjFramePrincipal().getjPopupMenuProjeto().getjMenuItemAbrir().doClick();
				}else if(arg0.getKeyCode() == KeyEvent.VK_F2){
					//Renomea projeto selecionado
					getjFramePrincipal().getjPopupMenuProjeto().getjMenuItemRenomear().doClick();
				}else if(arg0.getKeyCode() == KeyEvent.VK_DELETE){
					//Exclui projeto selecionado
					getjFramePrincipal().getjPopupMenuProjeto().getjMenuItemExcluir().doClick();
				}else if(arg0.getKeyCode() == KeyEvent.VK_CONTEXT_MENU) {
					//Exibe o PopupMenu do Explorador de Projetos
					getjFramePrincipal().getjPopupMenuProjeto().show(getjFramePrincipal().getjTetrisListExploradorDeProjetos(), getX() + (getWidth() / 2), getY() + (getHeight() / 2));
				}
			}
		});
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
	}
	
	//Lista projetos do workspace
	public void preencherLista(JFramePrincipal jFramePrincipal){
		this.jFramePrincipal=jFramePrincipal;
		//Define ícone que aparece ao lado do nome dos projetos
		setImagens(new ImageIcon []{new ImageIcon(jFramePrincipal.getClass().getResource("/br/com/analisasoftware/tetris/imagem/icone_projeto_tetris.png"))});
		
		//Listando projetos no workspace
		try {
			//Pegando lista de pastas no workspace
			String[] pastas = Arquivo.listarPastas(System.getProperty("user.home")+"/TetrisWorkspace/");
			
			//Validando se em cada pasta há um projeto Tetris válido
			int numProjetos=0;
			for (int i = 0; i < pastas.length; i++) {
				if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+pastas[i]+"/conf.dabj")){
					numProjetos++;
				}
			}
			
			String[] projetos = new String[numProjetos];
			int posicaoProjetos=0;
			//Preenchendo lista de projetos
			for (int i = 0; i < pastas.length; i++) {
				if(Arquivo.verificarCaminho(System.getProperty("user.home")+"/TetrisWorkspace/"+pastas[i]+"/conf.dabj")){
					projetos[posicaoProjetos]=pastas[i];
					posicaoProjetos++;
				}
			}
			
			setValores(projetos);
		} catch (Exception exc) {
			// TODO: handle exception
			JDialogMensagem.exibirMensagem("Erro", Idioma.getTraducao("tetris.message_error_listing_projects", jFramePrincipal)+":<br/>"+exc);
		}
	}

	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}
	
}
