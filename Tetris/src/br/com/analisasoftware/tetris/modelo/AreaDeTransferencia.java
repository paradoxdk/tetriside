/*
 *Classe utilizada para copiar ou mover componentes nas janelas
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 23 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.modelo;

import java.util.ArrayList;

import br.com.analisasoftware.tetris.visao.janelas.JFramePrincipal;
import tetris.modelo.componentes.Atributo;
import tetris.modelo.componentes.Componente;

public class AreaDeTransferencia {
	//Lista utilizada para armazenar os componentes que estão na área de transferência
	private ArrayList<Componente> arrayListComponenteBuffer;
	//Variável que aponta para a instância do JFramePrincipal
	private JFramePrincipal jFramePrincipal;
	
	//Inicializa o objeto
	public AreaDeTransferencia(JFramePrincipal jFramePrincipal){
		this.jFramePrincipal=jFramePrincipal;
		
		arrayListComponenteBuffer = new ArrayList<Componente>();
	}
	
	//Copia os componentes para a area de transferencia
	public void bufferizarComponente(ArrayList<Componente> arrayListComponentes){
		//Cria nova instância com a lista vazia
		arrayListComponenteBuffer = new ArrayList<Componente>();
		//Variável local para coletar os componentes filhos
		ArrayList<Componente> arrayListComponentesFilhos;
		//Percorre a lista de componentes passada por parâmetro com os elementos selecionados 
		for (int i = 0; i < arrayListComponentes.size(); i++) {
			//Adiciona uma cópia da instância do componente na posição i
			arrayListComponenteBuffer.add(arrayListComponentes.get(i).clone());
			//Muda o Atributo Pai para null, deixando a cópia do componente livre para ser colada em outro componente (Janela, Painel, etc)
			arrayListComponenteBuffer.get(arrayListComponenteBuffer.size() - 1).mudarAtributo(new Atributo("Pai", "String", "null"));
			//Muda os Atributos x e y, adicionando 5 pixels a cada um, para caso seja colado no mesmo lugar do objeto copiado, não fique exatamente em cima
			arrayListComponenteBuffer.get(arrayListComponenteBuffer.size() - 1).mudarAtributo(new Atributo("x", "int", ""+(Integer.parseInt(arrayListComponenteBuffer.get(arrayListComponenteBuffer.size() - 1).getAtributo("x").getValor())+5)));
			arrayListComponenteBuffer.get(arrayListComponenteBuffer.size() - 1).mudarAtributo(new Atributo("y", "int", ""+(Integer.parseInt(arrayListComponenteBuffer.get(arrayListComponenteBuffer.size() - 1).getAtributo("y").getValor())+5)));
			//Carrega os componentes filhos 
			arrayListComponentesFilhos =  bufferizaComponentesFilhos(arrayListComponentes.get(i).getNome());
			//Percorre a lista dos componentes filhos
			for (int j = 0; j < arrayListComponentesFilhos.size(); j++) {
				//Adiciona uma cópia da instância de cada componente filho
				arrayListComponenteBuffer.add(arrayListComponentesFilhos.get(j).clone());
			}
		}
	}
	
	//Seleciona os componentes filhos
	public ArrayList<Componente> bufferizaComponentesFilhos(String pai){
		//Carrega os componentes que estão contidos no componente informado no parâmetro pai
		ArrayList<Componente> arrayListComponentesFilhos = getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela().getArrayListComponentesFilhos(pai);
		ArrayList<Componente> arrayListComponentesFilhosDosFilhos;
		//Cria lista com os componentes que serão retornados
		ArrayList<Componente> arrayListComponentesRetorno = new ArrayList<Componente>();
		//Percorre a lista de componentes filhos
		for (int i = 0; i < arrayListComponentesFilhos.size(); i++) {
			//Carrega lista de componentes filhos do componente filho na posição i e chama de forma recursiva este mesmo método
			arrayListComponentesFilhosDosFilhos = bufferizaComponentesFilhos(arrayListComponentesFilhos.get(i).getNome());
			//Percorre a lista retornada
			for (int j = 0; j < arrayListComponentesFilhosDosFilhos.size(); j++) {
				//Adiciona à lista de retorno cada componente da lista
				arrayListComponentesRetorno.add(arrayListComponentesFilhosDosFilhos.get(j));
			}
			//Adiciona à lista de retorno cada componente da lista
			arrayListComponentesRetorno.add(arrayListComponentesFilhos.get(i));
		}
		//Retorna a lista de retorno com todos os componentes contidos no pai e em seus filhos
		return arrayListComponentesRetorno;
	}
	
	//Getters e Setters
	public JFramePrincipal getjFramePrincipal() {
		return jFramePrincipal;
	}

	public ArrayList<Componente> getArrayListComponenteBuffer() {
		return arrayListComponenteBuffer;
	}
}
