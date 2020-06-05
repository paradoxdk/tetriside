/*
 *Painel com highlight para código Java do Editor de Eventos do TetrisIDE
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 25 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.visao.componentes.jsyntaxtextpane;

import javax.swing.JTextPane;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class JSyntaxTextPane extends JTextPane {
	public JSyntaxTextPane(){
		super();
		//Definindo as regras de highlight
		CodeDocument doc = new CodeDocument();
		Map<String,Color> keywords = new HashMap<String,Color>();
	   
	   
		Color comment = new Color(63,197,95);
		Color javadoc = new Color(63,95,191);
		Color annotation = new Color(100,100,100);
		doc.setCommentColor(comment);
		doc.setJavadocColor(javadoc);
		doc.setAnnotationColor(annotation);
	   
		Color defColor = new Color(127,0,85);
		keywords.put("abstract",defColor);
		keywords.put("boolean",defColor);
		keywords.put("break",defColor);
		keywords.put("byte",defColor);
		keywords.put("case",defColor);
		keywords.put("catch",defColor);
		keywords.put("char",defColor);
		keywords.put("class",defColor);
		keywords.put("continue",defColor);
		keywords.put("default",defColor);
		keywords.put("do",defColor);
		keywords.put("double",defColor);
		keywords.put("enum",defColor);
		keywords.put("extends",defColor);
		keywords.put("else",defColor);
		keywords.put("false",defColor);
		keywords.put("final",defColor);
		keywords.put("finally",defColor);
		keywords.put("float",defColor);
		keywords.put("for",defColor);
		keywords.put("if",defColor);
		keywords.put("implements",defColor);
		keywords.put("import",defColor);
		keywords.put("instanceof",defColor);
		keywords.put("int",defColor);
		keywords.put("interface",defColor);
		keywords.put("long",defColor);
		keywords.put("native",defColor);
		keywords.put("new",defColor);
		keywords.put("null",defColor);
		keywords.put("package",defColor);
		keywords.put("private",defColor);
		keywords.put("protected",defColor);
		keywords.put("public",defColor);
		keywords.put("return",defColor);
		keywords.put("short",defColor);
		keywords.put("static",defColor);
		keywords.put("super",defColor);
		keywords.put("switch",defColor);
		keywords.put("synchronized",defColor);
		keywords.put("this",defColor);
		keywords.put("throw",defColor);
		keywords.put("throws",defColor);
		keywords.put("transient",defColor);
		keywords.put("true",defColor);
		keywords.put("try",defColor);
		keywords.put("catch",defColor);
		keywords.put("}catch",defColor);
		keywords.put("exception",defColor);
		keywords.put("void",defColor);
		keywords.put("volatile",defColor);
		keywords.put("while",defColor);
		
		doc.setKeywords(keywords);
		setDocument(doc);	
	}
	
	 @Override
     public boolean getScrollableTracksViewportWidth() {
         return getUI().getPreferredSize(this).width <= getParent().getSize().width;
     }
		
}
