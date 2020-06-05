package componentes.modelo.bancodedados;

import java.io.Serializable;
import java.util.ArrayList;

public class Tabela implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private ArrayList<Coluna> arrayListColunas;
	
	public Tabela(String nome, ArrayList<Coluna> arrayListColunas) {
		super();
		this.nome = nome;
		this.arrayListColunas = arrayListColunas;
	}
	
	public boolean adicionarColuna(Coluna coluna){
		boolean retorno=true;
		
		for (int i = 0; i < arrayListColunas.size(); i++) {
			if(arrayListColunas.get(i).getNome().equals(coluna.getNome())){
				retorno=false;
				break;
			}
		}
		
		if(retorno){
			arrayListColunas.add(coluna);
		}
		
		return retorno;
	}
	
	public boolean removerColuna(String nome){
		boolean retorno=false;
		
		for (int i = 0; i < arrayListColunas.size(); i++) {
			if(arrayListColunas.get(i).getNome().equals(nome)){
				arrayListColunas.remove(i);
				retorno=true;
				break;
			}
		}
		
		return retorno;
	}

	public Coluna getColuna(String nome){
		Coluna col=null;
		for (int i = 0; i < arrayListColunas.size(); i++) {
			if(arrayListColunas.get(i).getNome().equals(nome)){
				col=arrayListColunas.get(i);
				break;
			}
		}

		return col;
	}
	
	//Retorna as colunas que formam a primary key;
	public ArrayList<Coluna> getPrimaryKey(){
		ArrayList<Coluna> arrayListColunasPrimaryKey = new ArrayList<Coluna>();
		
		for (int i = 0; i < arrayListColunas.size(); i++) {
			if(arrayListColunas.get(i).getTipo().contains("primary key")){
				arrayListColunasPrimaryKey.add(arrayListColunas.get(i));
			}
		}
		
		if(arrayListColunasPrimaryKey.size()==0){
			Coluna colunaId=getColuna("id");
		
			if(colunaId==null){
				colunaId = new Coluna("id", "int not null primary key auto_increment");
				arrayListColunas.add(colunaId);
			}
			
			arrayListColunasPrimaryKey.add(colunaId);
		}
		
		return arrayListColunasPrimaryKey;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<Coluna> getArrayListColunas() {
		return arrayListColunas;
	}

	public void setArrayListColunas(ArrayList<Coluna> arrayListColunas) {
		this.arrayListColunas = arrayListColunas;
	}

}
