/*
 *Popup menu da lista de colunas de tabelas de banco de dados do TetrisIDE
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 25 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.componentes;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JSeparator;

import br.com.analisasoftware.tetris.modelo.Idioma;
import br.com.analisasoftware.tetris.modelo.Projeto;
import br.com.analisasoftware.tetris.visao.janelas.JDialogConfiguracoesBancoDeDados;
import br.com.analisasoftware.tetris.visao.janelas.JDialogEspelharBancoDeDados;
import br.com.analisasoftware.tetris.visao.janelas.JDialogMensagem;
import br.com.analisasoftware.tetris.visao.janelas.JDialogModeloJanela;
import br.com.analisasoftware.tetris.visao.janelas.JFramePrincipal;
import tetris.modelo.componentes.Janela;

@SuppressWarnings("serial")
public class JPopupMenuColunasGerenciadorDeBancoDeDados extends JPopupMenu {
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	//Itens de Menus do PopupMenu
	private JMenuItem jMenuItemCriarJanelaA;
	private JMenuItem jMenuItemCriarJanelaSelecionados;
	private JMenuItem jMenuItemEspelharBancoDe;
	private JMenuItem jMenuItemConfiguracoesDeBancoDeDados;

	public JPopupMenuColunasGerenciadorDeBancoDeDados(JFramePrincipal jFramePrincipal) {
		this.jFramePrincipal=jFramePrincipal;

		jMenuItemCriarJanelaA = new JMenuItem("Criar janela a partir de tabela inteira");
		jMenuItemCriarJanelaA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Cria janela a partir de tabela inteira
				//Verifica se tem colunas na tabela selecionada
				if(getjFramePrincipal().getjTetrisListColunasGerenciadorDeBancoDeDados().getValores()!=null){
					if(getjFramePrincipal().getjTetrisListColunasGerenciadorDeBancoDeDados().getValores().length > 0){
						//Captura o vetor das colunas da tabela
						int quantidadeColunas = getjFramePrincipal().getjTetrisListColunasGerenciadorDeBancoDeDados().getValores().length;
						String[] colunas = new String[quantidadeColunas];
						for(int i=0; i< quantidadeColunas; i++){
							colunas[i] = getjFramePrincipal().getjTetrisListColunasGerenciadorDeBancoDeDados().getValores()[i];
						}
						//Captura o modelo da janela desejado
						String retorno = new JDialogModeloJanela(getjFramePrincipal()).init();
						
						if(retorno!=null){
							//Cria janela
							if(Janela.criarJanela(colunas, getjFramePrincipal(), retorno)){
								//Atualiza o Explorador de Janelas
								getjFramePrincipal().getjTetrisListExploradorDeJanelas().preencherLista(getjFramePrincipal());
								getjFramePrincipal().getjTetrisListExploradorDeJanelas().setSelectedValue(getjFramePrincipal().getjComboBoxTabelaGerenciadorDeBancoDeDados().getSelectedItem(), true);
								//Abre a janela na área de trabalho
								Projeto.abrirJanela(""+getjFramePrincipal().getjComboBoxTabelaGerenciadorDeBancoDeDados().getSelectedItem(), getjFramePrincipal());
								//Salva o projeto
								Projeto.salvarProjeto(getjFramePrincipal());
							
							}
						}
					}else{
						JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_remove", getjFramePrincipal()));
					}
				}else{
					JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_remove", getjFramePrincipal()));
				}
			}
		});
		jMenuItemCriarJanelaA.setIcon(new ImageIcon(JPopupMenuColunasGerenciadorDeBancoDeDados.class.getResource("/br/com/analisasoftware/tetris/imagem/icone_janela.png")));
		add(jMenuItemCriarJanelaA);

		jMenuItemCriarJanelaSelecionados = new JMenuItem("Criar janela a partir de campos selecionados");
		jMenuItemCriarJanelaSelecionados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Cria janela a partir de campos selecionados
				//Verifica se tem colunas selecionadas
				if(getjFramePrincipal().getjTetrisListColunasGerenciadorDeBancoDeDados().getSelectedValuesList().size() > 0){
					//Captura lista de colunas selecionadas
					List<String> lista = getjFramePrincipal().getjTetrisListColunasGerenciadorDeBancoDeDados().getSelectedValuesList();
					int quantidadeColunas = lista.size();
					String[] colunas = new String[quantidadeColunas];
					for(int i=0; i< quantidadeColunas; i++){
						for (int j = 0; j < getjFramePrincipal().getjTetrisListColunasGerenciadorDeBancoDeDados().getValores().length; j++) {
							if((getjFramePrincipal().getjTetrisListColunasGerenciadorDeBancoDeDados().getValores()[j]+": "+getjFramePrincipal().getjTetrisListColunasGerenciadorDeBancoDeDados().getTipos()[j]).equals(lista.get(i))){
								colunas[i] = getjFramePrincipal().getjTetrisListColunasGerenciadorDeBancoDeDados().getValores()[j];
								break;
							}
						}
						
					}
					//Captura modelo da janela desejado
					String retorno = new JDialogModeloJanela(getjFramePrincipal()).init();

					if(retorno!=null){
						//Cria janela
						if(Janela.criarJanela(colunas, getjFramePrincipal(), retorno)){
							//Atualiza o Explorador de Janelas
							getjFramePrincipal().getjTetrisListExploradorDeJanelas().preencherLista(getjFramePrincipal());
							getjFramePrincipal().getjTetrisListExploradorDeJanelas().setSelectedValue(getjFramePrincipal().getjComboBoxTabelaGerenciadorDeBancoDeDados().getSelectedItem(), true);
							//Abre a janela na área de trabalho
							Projeto.abrirJanela(""+getjFramePrincipal().getjComboBoxTabelaGerenciadorDeBancoDeDados().getSelectedItem(), getjFramePrincipal());
							//Salva projeto
							Projeto.salvarProjeto(getjFramePrincipal());
							
						}
					}
				}else{
					JDialogMensagem.exibirMensagem("Perigo", Idioma.getTraducao("tetris.message_no_selected_item", getjFramePrincipal()));
				}
			}
		});
		jMenuItemCriarJanelaSelecionados.setIcon(new ImageIcon(JPopupMenuColunasGerenciadorDeBancoDeDados.class.getResource("/br/com/analisasoftware/tetris/imagem/icone_coluna.png")));
		add(jMenuItemCriarJanelaSelecionados);
		
		JSeparator separator1 = new JSeparator();
		add(separator1);
		
		jMenuItemEspelharBancoDe = new JMenuItem("Espelhar banco de dados no projeto");
		jMenuItemEspelharBancoDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Espelha um banco de dados do sistema gerenciador de banco de dados no projeto
				if(getjFramePrincipal().getProjeto()!=null){
					//Captura o banco de dados desejado
					String[] retorno = new JDialogEspelharBancoDeDados(getjFramePrincipal()).init();
					
					if(retorno != null){
						//Espelha banco de dados
						if(getjFramePrincipal().getProjeto().getBancoDeDados().espelharBancoDeDados(retorno[2], retorno[0], retorno[1], retorno[3])){
							//Atualiza o Gerenciador de Banco de Dados
							getjFramePrincipal().getjComboBoxTabelaGerenciadorDeBancoDeDados().preencherTabelas();
							getjFramePrincipal().getProjeto().getBancoDeDados().setAlterado(true);
						}
					}
				}else{
					JDialogMensagem.exibirMensagem("Erro", Idioma.getTraducao("tetris.message_open_a_project", getjFramePrincipal()));
				}
			}
		});
		jMenuItemEspelharBancoDe.setIcon(new ImageIcon(JPopupMenuColunasGerenciadorDeBancoDeDados.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_exportar.png")));
		add(jMenuItemEspelharBancoDe);
		
		JSeparator separator2 = new JSeparator();
		add(separator2);
		
		jMenuItemConfiguracoesDeBancoDeDados = new JMenuItem("Configurações de Banco de Dados");
		jMenuItemConfiguracoesDeBancoDeDados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Configura informações de conexão ao banco de dados do projeto
				if(getjFramePrincipal().getProjeto()!=null){
					new JDialogConfiguracoesBancoDeDados(getjFramePrincipal()).init();
				}else{
					JDialogMensagem.exibirMensagem("Erro", Idioma.getTraducao("tetris.message_open_a_project", getjFramePrincipal()));
				}
			}
		});
		jMenuItemConfiguracoesDeBancoDeDados.setIcon(new ImageIcon(JPopupMenuColunasGerenciadorDeBancoDeDados.class.getResource("/br/com/analisasoftware/tetris/imagem/menu_configuracoes.png")));
		add(jMenuItemConfiguracoesDeBancoDeDados);
	}
	//Exibe PopupMenu
	@Override
	public void show(Component component, int x, int y){
		super.show(component, x, y);
		
		carregarIdioma();
	}
	
	//Carrega o idioma
	public void carregarIdioma(){
		jMenuItemCriarJanelaA.setText(Idioma.getTraducao("tetris.create_window_from_entire_table", jFramePrincipal));
		jMenuItemCriarJanelaSelecionados.setText(Idioma.getTraducao("tetris.create_window_from_selected_columns", jFramePrincipal));
		jMenuItemEspelharBancoDe.setText(Idioma.getTraducao("tetris.mirror_database_in_the_project", jFramePrincipal));
		jMenuItemConfiguracoesDeBancoDeDados.setText(Idioma.getTraducao("tetris.database_settings", jFramePrincipal));
	}

	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}

}
