/*
 *Popup menu do Explorador de Projetos do TetrisIDE
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
import br.com.analisasoftware.tetris.visao.janelas.JDialogComponentesExternos;
import br.com.analisasoftware.tetris.visao.janelas.JDialogExecutar;
import br.com.analisasoftware.tetris.visao.janelas.JDialogExportar;
import br.com.analisasoftware.tetris.visao.janelas.JDialogMensagem;
import br.com.analisasoftware.tetris.visao.janelas.JDialogNomeProjeto;
import br.com.analisasoftware.tetris.visao.janelas.JFramePrincipal;
import tetris.modelo.componentes.Atributo;
import tetris.modelo.componentes.Janela;
import tetris.modelo.componentes.Metodo;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

@SuppressWarnings("serial")
public class JPopupMenuProjeto extends JPopupMenu {
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Itens de Menu do PopupMenu
	private JMenuItem jMenuItemAbrir;
	private JMenuItem jMenuItemSalvar;
	private JMenuItem jMenuItemNovo;
	private JMenuItem jMenuItemRenomear;
	private JMenuItem jMenuItemExcluir;
	private JMenuItem jMenuItemExecutar;
	private JMenuItem jMenuItemExportar;
	private JMenuItem jMenuItemImportar;
	private JMenuItem jMenuItemComponentesExternos;
	private JMenuItem jMenuItemFechar;


	public JPopupMenuProjeto(JFramePrincipal jFramePrincipal) {
		super();
		this.jFramePrincipal = jFramePrincipal;

		jMenuItemAbrir = new JMenuItem("Abrir");
		jMenuItemAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Abre um projeto
				abrirProjeto();
			}
		});
		jMenuItemAbrir.setIcon(new ImageIcon(JPopupMenuProjeto.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_abrir.png")));
		add(jMenuItemAbrir);

		jMenuItemSalvar = new JMenuItem("Salvar");
		jMenuItemSalvar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		jMenuItemSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Salva o projeto aberto
				salvarProjeto();
			}
		});
		jMenuItemSalvar.setIcon(new ImageIcon(JPopupMenuProjeto.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_salvar.png")));
		add(jMenuItemSalvar);

		JSeparator separator_1 = new JSeparator();
		add(separator_1);

		jMenuItemNovo = new JMenuItem("Novo");
		jMenuItemNovo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		jMenuItemNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Cria um projeto
				novoProjeto();
			}
		});
		jMenuItemNovo.setIcon(new ImageIcon(JPopupMenuProjeto.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_novo.png")));
		add(jMenuItemNovo);

		JSeparator separator = new JSeparator();
		add(separator);

		jMenuItemRenomear = new JMenuItem("Renomear");
		jMenuItemRenomear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Renomeia um projeto
				renomearProjeto();
			}
		});
		jMenuItemRenomear.setIcon(new ImageIcon(JPopupMenuProjeto.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_renomear.png")));
		add(jMenuItemRenomear);

		jMenuItemExcluir = new JMenuItem("Excluir");
		jMenuItemExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Exclui um projeto
				excluirProjeto();
			}
		});
		jMenuItemExcluir.setIcon(new ImageIcon(JPopupMenuProjeto.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_excluir.png")));
		add(jMenuItemExcluir);

		JSeparator separator_3 = new JSeparator();
		add(separator_3);

		jMenuItemExecutar = new JMenuItem("Executar");
		jMenuItemExecutar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0));
		jMenuItemExecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Executa um projeto
				if(getjFramePrincipal().getProjeto()!=null){
					new JDialogExecutar(getjFramePrincipal()).init(JDialogExecutar.EXECUTAR);
					getjFramePrincipal().getjTetrisPanelLog().carregarLog();
				}
			}
		});
		jMenuItemExecutar.setIcon(new ImageIcon(JPopupMenuProjeto.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_executar.png")));
		add(jMenuItemExecutar);

		JSeparator separator_2 = new JSeparator();
		add(separator_2);

		jMenuItemExportar = new JMenuItem("Exportar");
		jMenuItemExportar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
		jMenuItemExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Exporta o projeto aberto
				//Verifica se tem um projeto aberto
				if(getjFramePrincipal().getProjeto()!=null){
					//Captura a forma de exportação selecionada
					String retorno=null;
					retorno=new JDialogExportar(getjFramePrincipal()).init();
					if(retorno!=null){
						if(retorno.equals("0")){
							//Exporta um projeto TetrisIDE
							new JDialogExecutar(getjFramePrincipal()).init(JDialogExecutar.EXPORTAR_TETRIS);
						}else if(retorno.equals("6")){
							//Exporta um Arquivo Jar Executável do projeto
							new JDialogExecutar(getjFramePrincipal()).init(JDialogExecutar.EXPORTAR_JAR);
						}else{
							//Exporta o código-fonte Java do projeto
							new JDialogExecutar(getjFramePrincipal()).init(JDialogExecutar.EXPORTAR_JAVA);
							getjFramePrincipal().getjTetrisPanelLog().carregarLog();
						}
					}
				}
			}
		});
		jMenuItemExportar.setIcon(new ImageIcon(JPopupMenuProjeto.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_exportar.png")));
		add(jMenuItemExportar);

		jMenuItemImportar = new JMenuItem("Importar");
		jMenuItemImportar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
		jMenuItemImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Importa um projeto TetrisIDE ao workspace
				new JDialogExecutar(getjFramePrincipal()).init(JDialogExecutar.IMPORTAR_TETRIS);
			}
		});
		jMenuItemImportar.setIcon(new ImageIcon(JPopupMenuProjeto.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_exportar.png")));
		add(jMenuItemImportar);

		jMenuItemComponentesExternos = new JMenuItem("Componentes Externos");
		jMenuItemComponentesExternos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Abre janela para manter componentes externos do projeto
				if(getjFramePrincipal().getjDialogComponentesExternos() != null){
					getjFramePrincipal().getjDialogComponentesExternos().dispose();
					getjFramePrincipal().setjDialogComponentesExternos(null);
				}
				getjFramePrincipal().setjDialogComponentesExternos(new JDialogComponentesExternos(getjFramePrincipal()));
				getjFramePrincipal().getjDialogComponentesExternos().init();
			}
		});
		jMenuItemComponentesExternos.setIcon(new ImageIcon(JPopupMenuProjeto.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_exportar.png")));
		add(jMenuItemComponentesExternos);

		JSeparator separator_4 = new JSeparator();
		add(separator_4);

		jMenuItemFechar = new JMenuItem("Fechar");
		jMenuItemFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Fecha o projeto aberto
				fecharProjeto();
			}
		});
		jMenuItemFechar.setIcon(new ImageIcon(JPopupMenuProjeto.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_fechar.png")));
		add(jMenuItemFechar);

	}
	//Exibe o PopupMenu
	@Override
	public void show(Component component, int x, int y){
		super.show(component, x, y);
		carregarIdioma();
	}

	//Carregar idioma
	public void carregarIdioma(){
		jMenuItemAbrir.setText(Idioma.getTraducao("tetris.open", jFramePrincipal));
		jMenuItemComponentesExternos.setText(Idioma.getTraducao("tetris.external_components", jFramePrincipal));
		jMenuItemExcluir.setText(Idioma.getTraducao("tetris.delete", jFramePrincipal));
		jMenuItemExecutar.setText(Idioma.getTraducao("tetris.run", jFramePrincipal));
		jMenuItemExportar.setText(Idioma.getTraducao("tetris.export", jFramePrincipal));
		jMenuItemFechar.setText(Idioma.getTraducao("tetris.close", jFramePrincipal));
		jMenuItemImportar.setText(Idioma.getTraducao("tetris.import", jFramePrincipal));
		jMenuItemNovo.setText(Idioma.getTraducao("tetris.new", jFramePrincipal));
		jMenuItemRenomear.setText(Idioma.getTraducao("tetris.rename", jFramePrincipal));
		jMenuItemSalvar.setText(Idioma.getTraducao("tetris.save", jFramePrincipal));
	}

	//Cria um projeto
	public void novoProjeto(){

		//Capturando o nome do novo projeto
		String nomeProjeto = new JDialogNomeProjeto(getjFramePrincipal()).init();
		if(nomeProjeto!=null){
			String[] projetos = getjFramePrincipal().getjTetrisListExploradorDeProjetos().getValores();
			boolean criaProjeto=true;
			for (int i = 0; i < projetos.length; i++) {
				if(projetos[i].toLowerCase().equals(nomeProjeto.toLowerCase())){
					criaProjeto=false;
					break;
				}
			}

			if(criaProjeto){
				//Caso nao haja um projeto com este nome, cria o projeto
				if(Projeto.criarProjeto(nomeProjeto, getjFramePrincipal())){
					//Atualiza o Explorador de Projetos
					getjFramePrincipal().getjTetrisListExploradorDeProjetos().preencherLista(getjFramePrincipal());
					getjFramePrincipal().getjTetrisListExploradorDeProjetos().setSelectedValue(nomeProjeto, true);
					//Abre o projeto
					Projeto.abrirProjeto(nomeProjeto, jFramePrincipal);
					//Definindo atributos da nova janela
					ArrayList<Atributo> arrayListAtributos = new ArrayList<Atributo>();
					arrayListAtributos.add(new Atributo("Title", "String", "JFrameMain"));
					arrayListAtributos.add(new Atributo("x", "int", "0"));
					arrayListAtributos.add(new Atributo("y", "int", "0"));
					arrayListAtributos.add(new Atributo("width", "int", "200"));
					arrayListAtributos.add(new Atributo("height", "int", "200"));
					//Adiciona a primeira janela ao projeto
					if(Projeto.adicionarJanela(new Janela("JFrameMain", arrayListAtributos, new ArrayList<Metodo>()), jFramePrincipal)){
						//Atualiza o Explorador de Janelas
						jFramePrincipal.getjTetrisListExploradorDeJanelas().preencherLista(jFramePrincipal);
						jFramePrincipal.getjTetrisListExploradorDeJanelas().setSelectedValue("JFrameMain", true);
						//Abre a janela na área de trabalho
						Projeto.abrirJanela("JFrameMain", jFramePrincipal);
						//Define como janela principal do projeto
						jFramePrincipal.getProjeto().setJanelaPrincipal(jFramePrincipal.getProjeto().getArrayListJanelas().get(0));
						jFramePrincipal.getjButtonJanelaPrincipal().setText(jFramePrincipal.getProjeto().getArrayListJanelas().get(0).getNome());
						//Salva o projeto
						Projeto.salvarProjeto(jFramePrincipal);
						
					}
				}
			}else{
				JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_there_is_a_project_with_this_name", jFramePrincipal));
			}
		}

		//Chama o garbage collection
		System.gc();
	}

	//Renomea um projeto existente
	public void renomearProjeto(){
		//Aponta para o JList do Explorador de Projetos
		JTetrisListExploradorDeProjetos jTetrisListExploradorDeProjetos = getjFramePrincipal().getjTetrisListExploradorDeProjetos();
		//Verifica se há um projeto selecionado
		if(jTetrisListExploradorDeProjetos.getSelectedIndex() >= 0){
			//Captura novo nome do projeto
			JDialogNomeProjeto jDialogNomeProjeto = new JDialogNomeProjeto(getjFramePrincipal());
			jDialogNomeProjeto.setTitle(Idioma.getTraducao("tetris.rename", jFramePrincipal)+" "+Idioma.getTraducao("tetris.project", jFramePrincipal)+": "+jTetrisListExploradorDeProjetos.getSelectedValue());
			jDialogNomeProjeto.getjTetrisButtonCriar().setText(Idioma.getTraducao("tetris.rename", jFramePrincipal));

			String novoNome = jDialogNomeProjeto.init();

			if(novoNome!=null){
				//Pergunta se deseja realmente renomear o projeto
				if(JDialogMensagem.exibirMensagem("Perguntar", Idioma.getTraducao("tetris.message_rename_project", jFramePrincipal))==1){

					String[] projetos = getjFramePrincipal().getjTetrisListExploradorDeProjetos().getValores();
					boolean renomearProjeto=true;
					for (int i = 0; i < projetos.length; i++) {
						if((projetos[i].toLowerCase().equals(novoNome.toLowerCase())) && (novoNome.toLowerCase().equals(jTetrisListExploradorDeProjetos.getSelectedValue().toLowerCase())==false)){
							renomearProjeto=false;
							break;
						}
					}

					if(renomearProjeto){
						//Caso nao haja um projeto com este nome, renomeia o projeto
						if(Projeto.renomearProjeto(jTetrisListExploradorDeProjetos.getSelectedValue(), novoNome)){
							//Atualiza o Explorador de Projetos
							getjFramePrincipal().getjTetrisListExploradorDeProjetos().preencherLista(getjFramePrincipal());
							getjFramePrincipal().getjTetrisListExploradorDeProjetos().setSelectedValue(novoNome, true);
							//Abre o projeto
							Projeto.abrirProjeto(novoNome, jFramePrincipal);
							//Limpa a área de trabalho
							getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().removeAll();
							getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().repaint();
						}
					}else{
						JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_there_is_a_project_with_this_name", jFramePrincipal));
					}
				}
			}
		}else{
			JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_select_a_project", jFramePrincipal));
		}
	}

	//Exclui um projeto selecionado
	public void excluirProjeto(){
		//Aponta para o JList do Explorador de Projetos
		JTetrisListExploradorDeProjetos jTetrisListExploradorDeProjetos = getjFramePrincipal().getjTetrisListExploradorDeProjetos();
		//Verifica se há um projeto selecionado
		if(jTetrisListExploradorDeProjetos.getSelectedIndex() >= 0){
			//Pergunta se deseja realmente excluir o projeto
			if(JDialogMensagem.exibirMensagem("Perguntar", Idioma.getTraducao("tetris.message_remove_project", jFramePrincipal)+" "+jTetrisListExploradorDeProjetos.getSelectedValue()+"?"
					+ "<br/>"+Idioma.getTraducao("tetris.message_warning_remove_project", jFramePrincipal))==1){
				//Excluir o projeto
				if(Projeto.excluirProjeto(jTetrisListExploradorDeProjetos.getSelectedValue())){
					//Caso esteja aberto, fecha o projeto
					if(getjFramePrincipal().getProjeto()!=null){
						if(jTetrisListExploradorDeProjetos.getSelectedValue().equals(getjFramePrincipal().getProjeto().getNome())){
							fecharProjeto();
						}
					}
					//Atualiza o Explorador de Projetos
					jTetrisListExploradorDeProjetos.preencherLista(getjFramePrincipal());

				}
			}
		}else{
			JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_select_a_project", jFramePrincipal));
		}

	}

	//Salva o projeto aberto
	public void salvarProjeto(){
		//Aponta para o projeto aberto
		Projeto projeto = getjFramePrincipal().getProjeto();

		if(projeto != null){
			//Abre janela que informa ao usuario que o projeto esta sendo salvo
			new JDialogExecutar(getjFramePrincipal()).init(JDialogExecutar.SALVAR_PROJETO);
		}else{
			JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_open_a_project", jFramePrincipal));
		}
	}

	//Abre um projeto selecionado
	public void abrirProjeto(){
		//Aponta para o JList do Explorador de Projetos
		JTetrisListExploradorDeProjetos jTetrisListExploradorDeProjetos = getjFramePrincipal().getjTetrisListExploradorDeProjetos();
		//Verifica se há projeto selecionado
		if(jTetrisListExploradorDeProjetos.getSelectedIndex() >= 0){
			//Se houver um projeto aberto, pergunta se deseja realmente fechar para abrir o selecionado
			if(getjFramePrincipal().getProjeto()!=null){
				if(JDialogMensagem.exibirMensagem("Perguntar", Idioma.getTraducao("tetris.message_project_close", jFramePrincipal))==0){
					return;
				}
			}
			//Abre projeto
			new JDialogExecutar(getjFramePrincipal()).init(JDialogExecutar.ABRIR_PROJETO);
		}else{
			JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_select_a_project", jFramePrincipal));
		}
	}

	//Fecha o projeto aberto
	public void fecharProjeto(){
		//Pergunta se deseja realmente fechar o projeto
		if(getjFramePrincipal().getProjeto()!=null){
			if(JDialogMensagem.exibirMensagem("Perguntar", Idioma.getTraducao("tetris.message_project_close", jFramePrincipal))==0){
				return;
			}
		}
		//Fecha o projeto
		Projeto.fecharProjeto(getjFramePrincipal());
		//Atualiza o Explorador de Janelas
		getjFramePrincipal().getjTetrisListExploradorDeJanelas().preencherLista(getjFramePrincipal());
		//Limpa área de trabalho e Gerenciador de Banco de Dados
		if(getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getjInternalFrame()!=null){
			getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getjInternalFrame().dispose();
		}
		getjFramePrincipal().getjButtonJanelaPrincipal().setText(Idioma.getTraducao("tetris.none", jFramePrincipal));
		getjFramePrincipal().getjComboBoxTabelaGerenciadorDeBancoDeDados().preencherTabelas();

		getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().removeAll();
		getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().repaint();
		getjFramePrincipal().getjSyntaxTextPaneCodigoFonteGerado().setText("");
		getjFramePrincipal().getjTabbedPaneAreaDeTrabalho().setSelectedIndex(0);
	}

	//Getters
	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}

	public JMenuItem getjMenuItemAbrir() {
		return jMenuItemAbrir;
	}

	public JMenuItem getjMenuItemSalvar() {
		return jMenuItemSalvar;
	}

	public JMenuItem getjMenuItemNovo() {
		return jMenuItemNovo;
	}

	public JMenuItem getjMenuItemRenomear() {
		return jMenuItemRenomear;
	}

	public JMenuItem getjMenuItemExcluir() {
		return jMenuItemExcluir;
	}

	public JMenuItem getjMenuItemExecutar() {
		return jMenuItemExecutar;
	}

	public JMenuItem getjMenuItemExportar() {
		return jMenuItemExportar;
	}

	public JMenuItem getjMenuItemImportar() {
		return jMenuItemImportar;
	}

	public JMenuItem getjMenuItemComponentesExternos() {
		return jMenuItemComponentesExternos;
	}

	public JMenuItem getjMenuItemFechar() {
		return jMenuItemFechar;
	}

}
