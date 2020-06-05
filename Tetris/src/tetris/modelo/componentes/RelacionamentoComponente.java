/*
 *Relaciona um componente do projeto com um component gráfico na janela da área de trabalho
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 25 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package tetris.modelo.componentes;

import java.awt.Component;

public class RelacionamentoComponente {
	//Variáveis que apontam para as entidades relacionadas
	private Component component;
	private Componente componente;
	
	public RelacionamentoComponente(Component component, Componente componente) {
		super();
		this.component = component;
		this.componente = componente;
	}
	//Getters e Setters
	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public Componente getComponente() {
		return componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}
	
}
