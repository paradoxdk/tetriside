package componentes.modelo.bancodedados;

import java.io.Serializable;

public class Coluna implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome, tipo;

	public Coluna(String nome, String tipo) {
		super();
		this.nome = nome;
		this.tipo = tipo;
	}

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
	
}
