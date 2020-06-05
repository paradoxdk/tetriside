/*
 *ComboBox com as tabelas do banco de dados do projeto
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 25 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.componentes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import br.com.analisasoftware.tetris.modelo.Projeto;
import br.com.analisasoftware.tetris.visao.janelas.JFramePrincipal;
import componentes.modelo.bancodedados.Coluna;
import componentes.modelo.bancodedados.Tabela;
import componentes.visao.JTetrisComboBox;

@SuppressWarnings("serial")
public class JTetrisComboBoxTabelas extends JTetrisComboBox{
	//Aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	
	public JTetrisComboBoxTabelas(JFramePrincipal jFramePrincipal){
		super();
		this.jFramePrincipal=jFramePrincipal;
		
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//Preenche a lista de colunas
				preencherColunas();
				
			}
		});
		//Preenche as tabelas no combobox
		preencherTabelas();
	}
	
	//Preenche as tabelas no combobox
	public void preencherTabelas(){
		//Aponta para a instância do projeto aberto
		Projeto projeto = getjFramePrincipal().getProjeto();
		//Remove todos os itens do combobox
		removeAllItems();
		//Verifica se tem um projeto aberto
		if(projeto!=null){
			//Aponta para a lista de tabelas do banco de dados do projeto
			ArrayList<Tabela> arrayListTabelas = projeto.getBancoDeDados().getArrayListTabelas();
			//Percorre a lista e adiciona cada tabela ao combobox
			for (int i = 0; i < arrayListTabelas.size(); i++) {
				addItem(arrayListTabelas.get(i).getNome());
			}
		}
	}
	
	//Preenche colunas da tabela
	public void preencherColunas(){
		//Verifica se há tabela selecionada
		if(getSelectedIndex()>=0){
			//Aponta para a lista de colunas da tabela
			ArrayList<Coluna> arrayListColunas = getjFramePrincipal().getProjeto().getBancoDeDados().getTabela(""+getSelectedItem()).getArrayListColunas();
			//Vetores de auxilio
			String[] valores = new String[arrayListColunas.size()];
			String[] tipos = new String[arrayListColunas.size()];
			//Captura os nomes de tipos das colunas
			for (int i = 0; i < valores.length; i++) {
				valores[i] = arrayListColunas.get(i).getNome();
				tipos[i] = arrayListColunas.get(i).getTipo();
			}
			//Preenche o JList das colunas do banco de dados do projeto
			getjFramePrincipal().getjTetrisListColunasGerenciadorDeBancoDeDados().setValores(valores, tipos);
		}else{
			//Caso não exista tabela selecionada, limpa o JList
			getjFramePrincipal().getjTetrisListColunasGerenciadorDeBancoDeDados().setValores(new String[] {}, new String[] {});
		}
	}

	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}
	
}
