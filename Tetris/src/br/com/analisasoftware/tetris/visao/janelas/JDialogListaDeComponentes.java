/*
 *Janela com lista de ordenação e seleção de componentes
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 27 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.janelas;

import javax.swing.JDialog;

import componentes.visao.JTetrisButton;
import componentes.visao.JTetrisList;
import componentes.visao.JTetrisPanel;
import tetris.modelo.componentes.Componente;
import tetris.modelo.componentes.Janela;
import tetris.modelo.componentes.RelacionamentoComponente;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;

import br.com.analisasoftware.tetris.modelo.Idioma;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class JDialogListaDeComponentes extends JDialog {
		//Aponta para a instância do JFramePrincipal
		private JFramePrincipal jFramePrincipal;
		//Componentes gráficos
		private JTetrisButton jButtonOk;
		private JTetrisList jListComponentes;
		private JLabel jLabelComponentesDaJanela;
		private JTetrisButton jButtonMoverCima;
		private JTetrisButton jButtonMoverBaixo;

		public JDialogListaDeComponentes(JFramePrincipal jFramePrincipal) {
			super();
			setTitle("Lista de Componentes");
			setResizable(false);
			setIconImage(Toolkit.getDefaultToolkit().getImage(JDialogListaDeComponentes.class.getResource("/br/com/analisasoftware/tetris/imagem/icone.png")));
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.jFramePrincipal = jFramePrincipal;
			JTetrisPanel tetrisPanel = new JTetrisPanel();
			setContentPane(tetrisPanel);
			
			jLabelComponentesDaJanela = new JLabel("Componentes da janela ativa");
			jLabelComponentesDaJanela.setBounds(12, 12, 376, 15);
			tetrisPanel.add(jLabelComponentesDaJanela);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(12, 39, 376, 364);
			tetrisPanel.add(scrollPane);
			
			jListComponentes = new JTetrisList(new ImageIcon(getClass().getResource("/br/com/analisasoftware/tetris/imagem/icone_projeto_tetris.png")));
			jListComponentes.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					//Caso pressionado ENTER, aciona o botão OK
					if(e.getKeyCode() == KeyEvent.VK_ENTER){
						jButtonOk.doClick();
					}
				}
			});
			jListComponentes.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//Caso efetue dois cliques no mouse, aciona o botão OK
					if(e.getClickCount() == 2){
						jButtonOk.doClick();
					}
				}
			});
			jListComponentes.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			scrollPane.setViewportView(jListComponentes);
			
			jButtonOk = new JTetrisButton("OK");
			jButtonOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					//Altera foco da janela para o componente selecionado
					//Verifica se há um componente selecionado
					if(jListComponentes.getSelectedIndex() >= 0){
						//Aponta para a lista de relacionamento de componentes e components gráficos
						ArrayList<RelacionamentoComponente> arrayListRelacionamentoComponentes = getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getjInternalFrame().getArrayListRelacionamentoComponentes();
						//Percorre o JList
						for(int j=0;j < jListComponentes.getSelectedValuesList().size();j++){
							//Percorre a lista de relacionamentos
							for (int i = 0; i < arrayListRelacionamentoComponentes.size(); i++) {
								//Seleciona o componente no JInternalFrame
								if(arrayListRelacionamentoComponentes.get(i).getComponente().getNome().equals(jListComponentes.getSelectedValuesList().get(j))){
									if(j==0){
										getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getjInternalFrame().setComponentFoco(arrayListRelacionamentoComponentes.get(i).getComponent(), false);
									}else{
										getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getjInternalFrame().setComponentFoco(arrayListRelacionamentoComponentes.get(i).getComponent(), true);
									}
									getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getjInternalFrame().repaint();
									break;
								}
							}
						}
						
					}
					//Fecha janela
					dispose();
				}
			});
			jButtonOk.setIcon(new ImageIcon(JDialogListaDeComponentes.class.getResource("/br/com/analisasoftware/tetris/imagem/sim.png")));
			jButtonOk.setBounds(12, 415, 117, 25);
			jButtonOk.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			tetrisPanel.add(jButtonOk);
			
			jButtonMoverCima = new JTetrisButton("New button");
			jButtonMoverCima.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					//Movendo componente para cima
					//Verifica se o componente está numa posição maoir que zero
					if(jListComponentes.getSelectedIndex() > 0){
						//Aponta para a janela aberta na área de trabalho
						Janela janela = getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela();
						//Aponta para a lista de componentes da janela
						ArrayList<Componente> arrayListComponentes = janela.getArrayListComponentes();
						//Clona o componente selecionado e armazena em uma variável
						Componente componente1 = janela.getComponente(""+jListComponentes.getSelectedValue()).clone();
						//Move a posição no JList para cima
						jListComponentes.setSelectedIndex(jListComponentes.getSelectedIndex()-1);
						//Clona o componente selecionado e armazena em uma variável
						Componente componente2 = janela.getComponente(""+jListComponentes.getSelectedValue()).clone();
						//Percorre a lista de componentes
						for(int i=0; i < arrayListComponentes.size(); i++){
							//Efetua a troca dos componentes. O de cima vai para baixo e o de baixo vai para cima
							if(arrayListComponentes.get(i).getNome().equals(componente2.getNome())){
								janela.getArrayListComponentes().set(i, componente1);
							}else if(arrayListComponentes.get(i).getNome().equals(componente1.getNome())){
								janela.getArrayListComponentes().set(i, componente2);
							}
						}
						//Captura a posição do JList
						int posicao = jListComponentes.getSelectedIndex();
						//Astualiza o JList
						preencherListaComponentes();
						//Define a posição capturada
						jListComponentes.setSelectedIndex(posicao);
					}
				}
			});
			jButtonMoverCima.setText("");
			jButtonMoverCima.setIcon(new ImageIcon(JDialogListaDeComponentes.class.getResource("/br/com/analisasoftware/tetris/imagem/seta_cima.png")));
			jButtonMoverCima.setBounds(400, 39, 40, 25);
			jButtonMoverCima.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			tetrisPanel.add(jButtonMoverCima);
			
			jButtonMoverBaixo = new JTetrisButton("New button");
			jButtonMoverBaixo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Movendo componente para baixo
					//Verifica se o componente não é o último
					if(jListComponentes.getSelectedIndex() < jListComponentes.getModel().getSize()-1){
						//Aponta para a janela aberta na área de trabalho
						Janela janela = getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela();
						//Aponta para a lista de componentes da janela
						ArrayList<Componente> arrayListComponentes = janela.getArrayListComponentes();
						//Clona componente selecionado e armazena em uma variável
						Componente componente1 = janela.getComponente(""+jListComponentes.getSelectedValue()).clone();
						//Move a seleção para baixo
						jListComponentes.setSelectedIndex(jListComponentes.getSelectedIndex()+1);
						//Clona componente selecionado e armazena em outra variável
						Componente componente2 = janela.getComponente(""+jListComponentes.getSelectedValue()).clone();
						//Percorre a lista de componentes
						for(int i=0; i < arrayListComponentes.size(); i++){
							//Efetua a troca dos componentes. O de cima vai para baixo e o de baixo vai para cima
							if(arrayListComponentes.get(i).getNome().equals(componente2.getNome())){
								janela.getArrayListComponentes().set(i, componente1);
							}else if(arrayListComponentes.get(i).getNome().equals(componente1.getNome())){
								janela.getArrayListComponentes().set(i, componente2);
							}
						}
						//Captura posição do JList
						int posicao = jListComponentes.getSelectedIndex();
						//Atualiza a lista
						preencherListaComponentes();
						//Define a posição capturada
						jListComponentes.setSelectedIndex(posicao);
					}
				}
			});
			jButtonMoverBaixo.setIcon(new ImageIcon(JDialogListaDeComponentes.class.getResource("/br/com/analisasoftware/tetris/imagem/seta_baixo.png")));
			jButtonMoverBaixo.setText("");
			jButtonMoverBaixo.setBounds(400, 76, 40, 25);
			jButtonMoverBaixo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			tetrisPanel.add(jButtonMoverBaixo);
			setSize(453, 500);
			setLocationRelativeTo(jFramePrincipal);
			setModal(true);
		}
		
		//Carrega idioma nos componentes
		public void carregarIdioma(){
			setTitle(Idioma.getTraducao("tetris.component_list", jFramePrincipal));
			jLabelComponentesDaJanela.setText(Idioma.getTraducao("tetris.components_of_the_active_window", jFramePrincipal));
			jButtonOk.setText(Idioma.getTraducao("tetris.ok", jFramePrincipal));
			jButtonMoverCima.setToolTipText(Idioma.getTraducao("tetris.move_up_component", jFramePrincipal));
			jButtonMoverBaixo.setToolTipText(Idioma.getTraducao("tetris.move_down_component", jFramePrincipal));
		}
		//Inicializa e exibe a janela
		public void init(){
			carregarIdioma();
			preencherListaComponentes();
			
			//Adicionando Listener para teclas de acao
			Component[] componentes = getContentPane().getComponents();
			KeyAdapter keyAdapter = new KeyAdapter(){
				public void keyReleased(KeyEvent arg0){
					if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
						dispose();
					}
				}
			};
			jListComponentes.addKeyListener(keyAdapter);
			for (int i = 0; i < componentes.length; i++) {
				componentes[i].addKeyListener(keyAdapter);
			}
			
			setVisible(true);
		}
		
		//Preenche lista de componentes da janela ativa
		public void preencherListaComponentes(){
			//Aponta para a lista de componentes da janela aberta na área de trabalho
			ArrayList<Componente> arrayListComponentes = getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getJanela().getArrayListComponentes();
			//Cria um vetor com os componentes
			String[] componentes = new String[arrayListComponentes.size()];
			for (int i = 0; i < arrayListComponentes.size(); i++) {
				componentes[i]=arrayListComponentes.get(i).getNome();
			}
			//Preenche a JList
			jListComponentes.setValores(componentes);
			//Verifica se há um componente focado
			if(getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getjInternalFrame().getComponentFoco()!=null){
				//Aponta para a lista de relacionamento de componentes e components gráficos
				ArrayList<RelacionamentoComponente> arrayListRelacionamentoComponentes = getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getjInternalFrame().getArrayListRelacionamentoComponentes();
				//Percorre a lista de relacionamentos
				for (int i = 0; i < arrayListRelacionamentoComponentes.size(); i++) {
					//Seleciona componente em foco na lista
					if(arrayListRelacionamentoComponentes.get(i).getComponent().equals(getjFramePrincipal().getjDesktopPaneAreaDeTrabalho().getjInternalFrame().getComponentFoco())){
						for (int j = 0; j < componentes.length; j++) {
							if(componentes[j].equals(arrayListRelacionamentoComponentes.get(i).getComponente().getNome())){
								jListComponentes.setSelectedIndex(j);
								
								break;
							}
						}
						
						break;
					}
				}
			}
		}

		public JFramePrincipal getjFramePrincipal() {
			return jFramePrincipal;
		}
}
