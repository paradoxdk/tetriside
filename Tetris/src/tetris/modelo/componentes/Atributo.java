/*
 *Representa atributo de componentes e janelas do projeto
 *Classe de objetos serializables (graváveis em disco)e cloneables
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 24 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package tetris.modelo.componentes;

import java.io.Serializable;

public class Atributo implements Cloneable, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome, tipo, valor;

	public Atributo(String nome, String tipo, String valor) {
		super();
		this.nome = nome;
		this.tipo = tipo;
		this.valor = valor;
	}
	//Getters e Setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	//Clona um atributo
	public Atributo clone(){
		Atributo atributoClone= null;
		try{
			atributoClone = (Atributo)super.clone();
		}catch(Exception exc){
			exc.printStackTrace();
		}
		
		atributoClone.setNome(""+nome);
		atributoClone.setTipo(""+tipo);
		atributoClone.setValor(""+valor);
		
		return atributoClone;
	}

}
