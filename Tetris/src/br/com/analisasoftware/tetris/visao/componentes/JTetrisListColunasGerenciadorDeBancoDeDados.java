/*
 *JList das colunas de tabela do Gerenciador de Banco de Dados do projeto
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 25 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.componentes;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

import componentes.visao.JTetrisList;

@SuppressWarnings("serial")
public class JTetrisListColunasGerenciadorDeBancoDeDados extends JTetrisList {
	//Vetores com informações das colunas
	private String[] tipos, valores;
	
	public JTetrisListColunasGerenciadorDeBancoDeDados(ImageIcon imageIcon){
		super(imageIcon);
	}
	//Define informações das colunas
	public void setValores(String[] valores, String[] tipos){
		this.valores=valores;
		this.tipos=tipos;
		
		adicionarItens();
	}
	//Adiciona itens ao JList
	private void adicionarItens(){
		//Cria modelo de lista
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		//Adiciona os elementos
		if(valores!=null){
			for (int i = 0; i < valores.length; i++) {
				listModel.addElement(valores[i]+": "+tipos[i]);
			}
		}
		//Seta o novo modelo
		setModel(listModel);
	}
	
	public String[] getValores(){
		return valores;
	}
	
	public String[] getTipos(){
		return tipos;
	}
	
}
