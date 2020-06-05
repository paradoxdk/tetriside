/*
 *Popup menu do Explorador de Janelas do TetrisIDE
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
import java.util.ArrayList;
import javax.swing.KeyStroke;

import br.com.analisasoftware.tetris.modelo.Idioma;
import br.com.analisasoftware.tetris.modelo.Projeto;
import br.com.analisasoftware.tetris.visao.janelas.JDialogMensagem;
import br.com.analisasoftware.tetris.visao.janelas.JDialogNomeJanela;
import br.com.analisasoftware.tetris.visao.janelas.JFramePrincipal;
import tetris.modelo.componentes.Atributo;
import tetris.modelo.componentes.Componente;
import tetris.modelo.componentes.Janela;
import tetris.modelo.componentes.Metodo;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

@SuppressWarnings("serial")
public class JPopupMenuJanela extends JPopupMenu {
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Itens de Menu do PopupMenu
	private JMenuItem jMenuItemNova;
	private JMenuItem jMenuItemAbrir;
	private JMenuItem jMenuItemRenomear;
	private JMenuItem jMenuItemExcluir;
	private JSeparator separator_1;
	private JMenuItem jMenuItemDuplicarJanela;

	public JPopupMenuJanela(JFramePrincipal jFramePrincipal) {
		super();
		this.jFramePrincipal = jFramePrincipal;

		jMenuItemNova = new JMenuItem("Nova");
		jMenuItemNova.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
		jMenuItemNova.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Cria uma janela
				novaJanela();
			}
		});
		jMenuItemNova.setIcon(new ImageIcon(JPopupMenuJanela.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_novo.png")));
		add(jMenuItemNova);

		JSeparator separator = new JSeparator();
		add(separator);

		jMenuItemAbrir = new JMenuItem("Abrir");
		jMenuItemAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Abre uma janela
				abrirJanela();
			}
		});
		jMenuItemAbrir.setIcon(new ImageIcon(JPopupMenuJanela.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_abrir.png")));
		add(jMenuItemAbrir);

		jMenuItemRenomear = new JMenuItem("Renomear");
		jMenuItemRenomear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Renomea uma janela
				renomearJanela();
			}
		});
		jMenuItemRenomear.setIcon(new ImageIcon(JPopupMenuJanela.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_renomear.png")));
		add(jMenuItemRenomear);

		jMenuItemExcluir = new JMenuItem("Excluir");
		
		jMenuItemExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Exclui uma janela
				excluirJanela();
			}
		});
		jMenuItemExcluir.setIcon(new ImageIcon(JPopupMenuJanela.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_excluir.png")));
		add(jMenuItemExcluir);

		separator_1 = new JSeparator();
		add(separator_1);

		jMenuItemDuplicarJanela = new JMenuItem("Duplicar Janela");
		jMenuItemDuplicarJanela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Duplica uma janela
				duplicarJanela();
			}
		});
		jMenuItemDuplicarJanela.setIcon(new ImageIcon(JPopupMenuJanela.class.getResource("/br/com/analisasoftware/tetris/imagem/icone_janela.png")));
		add(jMenuItemDuplicarJanela);
	}
	//Exibe o PopupMenu
	@Override
	public void show(Component component, int x, int y){
		super.show(component, x, y);
		carregarIdioma();
	}
	
	//Carrega o idioma
	public void carregarIdioma(){
		jMenuItemAbrir.setText(Idioma.getTraducao("tetris.open", jFramePrincipal));
		jMenuItemDuplicarJanela.setText(Idioma.getTraducao("tetris.clone_window", jFramePrincipal));
		jMenuItemExcluir.setText(Idioma.getTraducao("tetris.delete", jFramePrincipal));
		jMenuItemNova.setText(Idioma.getTraducao("tetris.new2", jFramePrincipal));
		jMenuItemRenomear.setText(Idioma.getTraducao("tetris.rename", jFramePrincipal));
	}

	//Abre uma janela
	public void abrirJanela(){
		JTetrisListExploradorDeJanelas jTetrisListExploradorDeJanelas = getjFramePrincipal().getjTetrisListExploradorDeJanelas();
		if(jTetrisListExploradorDeJanelas.getSelectedIndex() >= 0){
			Projeto.abrirJanela(jTetrisListExploradorDeJanelas.getSelectedValue(), jFramePrincipal);
		}else{
			JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_no_selected_item", jFramePrincipal));
		}
	}

	//Cria uma janela
	public void novaJanela(){
		//Capturando o nome e o titulo da nova janela
		if(jFramePrincipal.getProjeto() != null){
			String[] nomeTituloJanela = new JDialogNomeJanela(getjFramePrincipal()).init();
			if(nomeTituloJanela[0]!=null){
				String[] janelas = getjFramePrincipal().getjTetrisListExploradorDeJanelas().getValores();
				boolean criaJanela=true;
				for (int i = 0; i < janelas.length; i++) {
					if(janelas[i].toLowerCase().equals(nomeTituloJanela[0].toLowerCase())){
						criaJanela=false;
						break;
					}
				}

				if(criaJanela){
					//Caso nao haja uma janela com este nome, cria a janela
					ArrayList<Atributo> arrayListAtributos = new ArrayList<Atributo>();
					arrayListAtributos.add(new Atributo("Title", "String", nomeTituloJanela[1]));
					arrayListAtributos.add(new Atributo("x", "int", "0"));
					arrayListAtributos.add(new Atributo("y", "int", "0"));
					arrayListAtributos.add(new Atributo("width", "int", "200"));
					arrayListAtributos.add(new Atributo("height", "int", "200"));
					//Adiciona a janela ao projeto
					if(Projeto.adicionarJanela(new Janela(nomeTituloJanela[0], arrayListAtributos, new ArrayList<Metodo>()), jFramePrincipal)){
						//Atualiza o Explorador de Janelas
						getjFramePrincipal().getjTetrisListExploradorDeJanelas().preencherLista(getjFramePrincipal());
						getjFramePrincipal().getjTetrisListExploradorDeJanelas().setSelectedValue(nomeTituloJanela[0], true);
						//Abre janela na área de trabalho
						Projeto.abrirJanela(nomeTituloJanela[0], jFramePrincipal);

					}
				}else{
					JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_add_window", jFramePrincipal));
				}
			}

			//Chama o garbage collection
			System.gc();
		}else{
			JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_open_a_project", jFramePrincipal));
		}
	}

	//Duplica uma janela
	public void duplicarJanela(){
		//Capturando o nome e o titulo da nova janela
		if(jFramePrincipal.getProjeto() != null){
			String[] nomeTituloJanela = new JDialogNomeJanela(getjFramePrincipal()).init();
			if(nomeTituloJanela[0]!=null){
				String[] janelas = getjFramePrincipal().getjTetrisListExploradorDeJanelas().getValores();
				boolean criaJanela=true;
				for (int i = 0; i < janelas.length; i++) {
					if(janelas[i].toLowerCase().equals(nomeTituloJanela[0].toLowerCase())){
						criaJanela=false;
						break;
					}
				}

				if(criaJanela){
					//Caso nao haja uma janela com este nome, cria a janela
					
					//Duplicando seus atributos, componentes e métodos
					ArrayList<Atributo> arrayListAtributos = getjFramePrincipal().getProjeto().getJanela(janelas[getjFramePrincipal().getjTetrisListExploradorDeJanelas().getSelectedIndex()]).getArrayListAtributos();
					ArrayList<Componente> arrayListComponentes = getjFramePrincipal().getProjeto().getJanela(janelas[getjFramePrincipal().getjTetrisListExploradorDeJanelas().getSelectedIndex()]).getArrayListComponentes();
					ArrayList<Metodo> arrayListMetodos = getjFramePrincipal().getProjeto().getJanela(janelas[getjFramePrincipal().getjTetrisListExploradorDeJanelas().getSelectedIndex()]).getArrayListMetodos();
					
					ArrayList<Atributo> arrayListAtributosNovo = new ArrayList<Atributo>();
					ArrayList<Componente> arrayListComponentesNovo = new ArrayList<Componente>();
					ArrayList<Metodo> arrayListMetodoNovo = new ArrayList<Metodo>();
					
					for (int i = 0; i < arrayListAtributos.size(); i++) {
						arrayListAtributosNovo.add(new Atributo(arrayListAtributos.get(i).getNome(), arrayListAtributos.get(i).getTipo(), arrayListAtributos.get(i).getValor()));
					}
					
					for (int i = 0; i < arrayListComponentes.size(); i++) {
						ArrayList<Atributo> arrayListAtributoComponente = arrayListComponentes.get(i).getArrayListAtributos();
						ArrayList<Atributo> arrayListAtributoComponenteNovo = new ArrayList<Atributo>();
						
						ArrayList<Metodo> arrayListMetodoComponente = arrayListComponentes.get(i).getArrayListMetodos();
						ArrayList<Metodo> arrayListMetodoComponenteNovo = new ArrayList<Metodo>();
						
						for (int j = 0; j < arrayListAtributoComponente.size(); j++) {
							arrayListAtributoComponenteNovo.add(new Atributo(arrayListAtributoComponente.get(j).getNome(), arrayListAtributoComponente.get(j).getTipo(), arrayListAtributoComponente.get(j).getValor()));
						}
						
						for (int j = 0; j < arrayListMetodoComponente.size(); j++) {
							ArrayList<Atributo> arrayListAtributoMetodo = arrayListMetodoComponente.get(j).getArrayListAtributos();
							ArrayList<Atributo> arrayListAtributoMetodoNovo = new ArrayList<Atributo>();
							
							for (int k = 0; k < arrayListAtributoMetodo.size(); k++) {
								arrayListAtributoMetodoNovo.add(new Atributo(arrayListAtributoMetodo.get(k).getNome(), arrayListAtributoMetodo.get(k).getTipo(), arrayListAtributoMetodo.get(k).getValor()));
							}
							
							Metodo metodo = new Metodo(arrayListMetodoComponente.get(j).getNome(), arrayListMetodoComponente.get(j).getTipo(), arrayListMetodoComponente.get(j).getCodigoFonte(), arrayListAtributoMetodoNovo);
							arrayListMetodoComponenteNovo.add(metodo);
						}
						
						Componente componente = new Componente(arrayListComponentes.get(i).getNome(), arrayListAtributoComponenteNovo, arrayListMetodoComponenteNovo);
						
						Atributo pai = componente.getAtributo("Pai");
						if(pai!=null){
							if(pai.getValor().equals(getjFramePrincipal().getProjeto().getJanela(janelas[getjFramePrincipal().getjTetrisListExploradorDeJanelas().getSelectedIndex()]).getNome())){
								componente.mudarAtributo(new Atributo("Pai", "String", nomeTituloJanela[0]));
							}
						}
						
						arrayListComponentesNovo.add(componente);
					}
					
					for (int i = 0; i < arrayListMetodos.size(); i++) {
						ArrayList<Atributo> arrayListAtributoMetodo = arrayListMetodos.get(i).getArrayListAtributos();
						ArrayList<Atributo> arrayListAtributoMetodoNovo = new ArrayList<Atributo>();
						
						for (int j = 0; j < arrayListAtributoMetodo.size(); j++) {
							arrayListAtributoMetodoNovo.add(new Atributo(arrayListAtributoMetodo.get(j).getNome(), arrayListAtributoMetodo.get(j).getTipo(), arrayListAtributoMetodo.get(j).getValor()));
						}
						
						Metodo metodo = new Metodo(arrayListMetodos.get(i).getNome(), arrayListMetodos.get(i).getTipo(), arrayListMetodos.get(i).getCodigoFonte(), arrayListAtributoMetodoNovo);
						arrayListMetodoNovo.add(metodo);
					}
					
					//Cria o objeto da janela
					Janela janela = new Janela(nomeTituloJanela[0], arrayListAtributosNovo, arrayListMetodoNovo);
					janela.mudarAtributo(new Atributo("Title", "String", nomeTituloJanela[1]));
					janela.setArrayListComponentes(arrayListComponentesNovo);
					//Adiciona janela ao projeto
					if(Projeto.adicionarJanela(janela, jFramePrincipal)){
						//Atualiza o Explorador de Janelas
						getjFramePrincipal().getjTetrisListExploradorDeJanelas().preencherLista(getjFramePrincipal());
						getjFramePrincipal().getjTetrisListExploradorDeJanelas().setSelectedValue(nomeTituloJanela[0], true);
						//Abre a janela na área de trabalho
						Projeto.abrirJanela(nomeTituloJanela[0], jFramePrincipal);

					}
				}else{
					JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_add_window", jFramePrincipal));
				}
			}

			//Chama o garbage collection
			System.gc();
		}else{
			JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_open_a_project", jFramePrincipal));
		}
	}

	//Renomeia uma janela
	public void renomearJanela(){
		//Aponta para o JList do Explorador de Janelas
		JTetrisListExploradorDeJanelas jTetrisListExploradorDeJanelas = getjFramePrincipal().getjTetrisListExploradorDeJanelas();
		//Verifica se tem uma janela selecionada
		if(jTetrisListExploradorDeJanelas.getSelectedIndex() >= 0){
			//Captura o novo nome e título da janela
			JDialogNomeJanela jDialogNomeJanela = new JDialogNomeJanela(getjFramePrincipal());
			jDialogNomeJanela.setTitle(Idioma.getTraducao("tetris.rename", jFramePrincipal)+" "+Idioma.getTraducao("tetris.window", jFramePrincipal)+": "+jTetrisListExploradorDeJanelas.getSelectedValue());
			jDialogNomeJanela.getjTetrisButtonCriar().setText(Idioma.getTraducao("tetris.rename", jFramePrincipal));

			String[] novoNomeTitulo = jDialogNomeJanela.init();

			if(novoNomeTitulo[0]!=null){
				String[] janelas = getjFramePrincipal().getjTetrisListExploradorDeJanelas().getValores();
				boolean renomearJanela=true;
				for (int i = 0; i < janelas.length; i++) {
					if((janelas[i].toLowerCase().equals(novoNomeTitulo[0].toLowerCase())) && (novoNomeTitulo[0].toLowerCase().equals(jTetrisListExploradorDeJanelas.getSelectedValue().toLowerCase())==false)){
						renomearJanela=false;
						break;
					}
				}

				if(renomearJanela){
					//Caso nao haja uma janela com este nome, renomeia a janela
					if(Projeto.renomearJanela(getjFramePrincipal(), jTetrisListExploradorDeJanelas.getSelectedValue(), novoNomeTitulo[0], novoNomeTitulo[1])){
						//Atualiza o Explorador de Janelas
						getjFramePrincipal().getjTetrisListExploradorDeJanelas().preencherLista(getjFramePrincipal());
						getjFramePrincipal().getjTetrisListExploradorDeJanelas().setSelectedValue(novoNomeTitulo[0], true);
						//Se for a janela principal, modifica seu nome na Janela Principal definida no projeto e no TetrisIDE
						if(getjFramePrincipal().getProjeto().getJanelaPrincipal()!=null){
							getjFramePrincipal().getjButtonJanelaPrincipal().setText(getjFramePrincipal().getProjeto().getJanelaPrincipal().getNome());
						}
						//Abre a janela na área de trabalho
						Projeto.abrirJanela(novoNomeTitulo[0], jFramePrincipal);

					}
				}else{
					JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_add_window", jFramePrincipal));
				}
			}
		}else{
			JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_no_selected_item", jFramePrincipal));
		}
	}

	//Exclui uma janela
	public void excluirJanela(){
		JTetrisListExploradorDeJanelas jTetrisListExploradorDeJanelas = getjFramePrincipal().getjTetrisListExploradorDeJanelas();

		if(getjFramePrincipal().getProjeto()!=null){
			//Verifica se há uma janela selecionada
			if(jTetrisListExploradorDeJanelas.getSelectedIndex() >= 0){
				//Pergunta se deseja realmente excluir essa janela
				if(JDialogMensagem.exibirMensagem("Perguntar", Idioma.getTraducao("tetris.message_remove_window", getjFramePrincipal())+" "+jTetrisListExploradorDeJanelas.getSelectedValue()+"?"
						+ "<br/>"+Idioma.getTraducao("tetris.message_warning_remove_window", jFramePrincipal))==1){
					//Exclui a janela do projeto
					if(Projeto.excluirJanela(jTetrisListExploradorDeJanelas.getSelectedValue(), getjFramePrincipal())){
						//Se a janela estiver aberta na área de trabalho, fecha
						if(getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela()!=null){
							if(jTetrisListExploradorDeJanelas.getSelectedValue().equals(getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela().getNome())){
								getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().removeAll();
								getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().setJanela(null);
								getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().repaint();
								getjFramePrincipal().getjSyntaxTextPaneCodigoFonteGerado().setText("");
								getjFramePrincipal().getjTabbedPaneAreaDeTrabalho().setSelectedIndex(0);
							}
						}
						//Atualiza o Explorador de Janelas
						jTetrisListExploradorDeJanelas.preencherLista(getjFramePrincipal());

					}
				}
			}else{
				JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_no_selected_item", jFramePrincipal));
			}
		}else{
			JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_open_a_project", jFramePrincipal));
		}
	}
	//Getters e Setters
	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}

	public JMenuItem getjMenuItemNova() {
		return jMenuItemNova;
	}

	public JMenuItem getjMenuItemAbrir() {
		return jMenuItemAbrir;
	}

	public JMenuItem getjMenuItemRenomear() {
		return jMenuItemRenomear;
	}

	public JMenuItem getjMenuItemExcluir() {
		return jMenuItemExcluir;
	}


}
