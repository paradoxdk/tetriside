package componentes.modelo;
import java.awt.Component;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

import componentes.modelo.bancodedados.BancoDeDados;

public class Mascara {
	//Funções
	
	//Transferir foco de um componente para outro
	public static void transferirFoco(Component ed, KeyEvent k){
		if(k.getKeyCode() == KeyEvent.VK_ENTER)
			ed.transferFocus();
	}
	
	public static boolean isTeclaIsenta(KeyEvent k){
		boolean retorno=false;
		
		if ((k.getKeyCode() == KeyEvent.VK_SHIFT) || (k.getKeyCode() == KeyEvent.VK_CONTROL)  || (k.getKeyCode() == KeyEvent.VK_HOME) 
				|| (k.getKeyCode() == KeyEvent.VK_END) || (k.getKeyCode() == KeyEvent.VK_UP) || (k.getKeyCode() == KeyEvent.VK_DOWN)
				|| (k.getKeyCode() == KeyEvent.VK_LEFT) || (k.getKeyCode() == KeyEvent.VK_RIGHT) || (k.getKeyCode() == KeyEvent.VK_BACK_SPACE)){
			retorno=true;
		}
		
		return retorno;
	}
	
	//Filtros
	public static String filtroSemEspacos(String s){
		String r="";
		for(int i=0; i<s.length();i++){
			if((Character.toString(s.charAt(i)).equals(" ")==false)){
				r=r+Character.toString(s.charAt(i));
			}
		}

		return r;
	}
	
	public static String filtroVariavel(String s){
		String r="";
		for(int i=0; i<s.length();i++){
			if(i==0){
				r=r+Character.toString(s.charAt(i)).toLowerCase();
			}else{
				r=r+Character.toString(s.charAt(i));
			}
		}

		return r;
	}
	
	public static String filtroClasse(String s){
		String r="";
		for(int i=0; i<s.length();i++){
			if(i==0){
				r=r+Character.toString(s.charAt(i)).toUpperCase();
			}else{
				r=r+Character.toString(s.charAt(i));
			}
		}

		return r;
	}
	
	public static String filtroNumeros(String s){
		String r="";
		for(int i=0; i<s.length();i++){
			if(Character.toString(s.charAt(i)).equals("-")){
				if(i > 0){
					s = s.replace('-', '0');
				}
			}

			if((Character.isDigit(s.charAt(i))) || (Character.toString(s.charAt(i)).equals("-"))){
				r=r+Character.toString(s.charAt(i));
			}
		}

		return r;
	}

	public static String mascaraCodigoProduto(String s){
		int digitos=s.length();
		String r=s;
		if(digitos < 14){
			for(int i=0;i<14-digitos; i++){
				s="0"+s;
			}
			r=s;
		}else if(digitos > 14){
			r="";
			for(int i=0;i<14; i++){
				r=r+Character.toString(s.charAt(i));
			}

		}

		return r;
	}
	
	public static String filtroZerosEsquerda(String s, int tamanho){
		int digitos=s.length();
		String r=s;
		if(digitos < tamanho){
			for(int i=0;i<tamanho-digitos; i++){
				s="0"+s;
			}
			r=s;
		}else if(digitos > tamanho){
			r="";
			for(int i=0;i<tamanho; i++){
				r=r+Character.toString(s.charAt(i));
			}

		}

		return r;
	}
	
	public static String filtroEspacosEsquerda(String s, int tamanho){
		int digitos=s.length();
		String r=s;
		if(digitos < tamanho){
			for(int i=0;i<tamanho-digitos; i++){
				s=s+" ";
			}
			r=s;
		}else if(digitos > tamanho){
			r="";
			for(int i=0;i<tamanho; i++){
				r=r+Character.toString(s.charAt(i));
			}

		}

		return r;
	}

	public static String filtroDecimalCasas(String s, int casas){
		try{
			String casa="";
			for (int i = 0; i < casas; i++) {
				casa=casa+"0";
			}
			java.text.DecimalFormat formata = new java.text.DecimalFormat("#0."+casa);
			String r="";

			for(int i=0; i<s.length();i++){
				if(Character.toString(s.charAt(i)).equals("-")){
					if(i > 0){
						s = s.replace('-', '0');
					}
				}
				if((Character.isDigit(s.charAt(i))) || (Character.toString(s.charAt(i)).equals(".")) || (Character.toString(s.charAt(i)).equals(",")) || (Character.toString(s.charAt(i)).equals("-"))){
					if(Character.toString(s.charAt(i)).equals(",") == false){
						r = r+Character.toString(s.charAt(i));
					}else{
						r = r+".";
					}
				}
			}
			if(r.equals("")==false){
				String g = formata.format(Double.parseDouble(r));
				r="";
				for(int i=0; i<g.length();i++){
					if((Character.isDigit(g.charAt(i))) || (Character.toString(g.charAt(i)).equals(".")) || (Character.toString(g.charAt(i)).equals(",")) || (Character.toString(s.charAt(i)).equals("-"))){
						if(Character.toString(g.charAt(i)).equals(",") == false){
							r = r+Character.toString(g.charAt(i));
						}else{
							r = r+".";
						}
					}
				}
			}

			return r;
		}catch(Exception exc){
			return "";
		}
	}

	public static String filtroCNPJ(String s){
		String r="";
		for(int i=0; i<s.length();i++){
			if((Character.isDigit(s.charAt(i))) || (Character.toString(s.charAt(i)).equals(".")) || (Character.toString(s.charAt(i)).equals("/")) || (Character.toString(s.charAt(i)).equals("-"))){
				r=r+Character.toString(s.charAt(i));
			}
		}
		s=r;
		if((s.length() == 2) || (s.length() == 6)){
			r=s+".";
		}else if (s.length() == 10){
			r = s+"/";
		}else if (s.length() == 15){
			r = s+"-";
		}else {
			r=s;
		}
		return r;
	}
	
	public static String formatCNPJ(String s){
		String r="";
		for(int i=0; i<s.length();i++){
			if((Character.isDigit(s.charAt(i))) || (Character.toString(s.charAt(i)).equals(".")) || (Character.toString(s.charAt(i)).equals("/")) || (Character.toString(s.charAt(i)).equals("-"))){
				
				r=r+Character.toString(s.charAt(i));
				if((i == 1) || (i == 4)){
					r+=".";
				}else if (i == 7){
					r += "/";
				}else if (i == 11){
					r += "-";
				}
			}
		}
		s=r;
		
		return r;
	}
	
	public static String filtroRG(String s){
		String r="";
		for(int i=0; i<s.length();i++){
			if((Character.isDigit(s.charAt(i))) || (Character.toString(s.charAt(i)).equals(".")) || (Character.toString(s.charAt(i)).equals("-"))){
				r=r+Character.toString(s.charAt(i));
			}
		}
		s=r;
		if((s.length() == 2) || (s.length() == 6)){
			r=s+".";
		}else if (s.length() == 10){
			r = s+"-";
		}else {
			r=s;
		}
		return r;
	}
	
	public static String filtroInscricaoEstadual(String s){
		String r="";
		for(int i=0; i<s.length();i++){
			if((Character.isDigit(s.charAt(i))) || (Character.toString(s.charAt(i)).equals("."))){
				r=r+Character.toString(s.charAt(i));
			}
		}
		s=r;
		if((s.length() == 3) || (s.length() == 7)){
			r=s+".";
		}else if (s.length() == 11){
			r = s+".";
		}else{
			r=s;
		}
		return r;
	}

	public static String filtroDecimal(String s){
		String r="";
		for(int i=0; i<s.length();i++){
			if(Character.toString(s.charAt(i)).equals("-")){
				if(i > 0){
					s = s.replace('-', '0');
				}
			}
			if((Character.isDigit(s.charAt(i))) || (Character.toString(s.charAt(i)).equals(".")) || (Character.toString(s.charAt(i)).equals(",")) || (Character.toString(s.charAt(i)).equals("-"))){
				if(Character.toString(s.charAt(i)).equals(",") == false){
					r = r+Character.toString(s.charAt(i));
				}else{
					r = r+".";
				}
			}
		}
		return r;
	}
	
	public static String filtroHora(String s){
		String r="";
		for(int i=0; i<s.length();i++){
			if((Character.isDigit(s.charAt(i))) || (Character.toString(s.charAt(i)).equals(":"))){
				r=r+Character.toString(s.charAt(i));
			}
		}
		s=r;
		if((s.length() == 2) || (s.length() == 5)){
			r = s+":";
			return r;
		}else{
			r=s;
			if((s.length() == 8)){
				try{
					java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("HH:mm:ss");
					java.sql.Date d = new java.sql.Date(format.parse(r).getTime());
					r = format.format(d);
				}catch(Exception exc){
					r="";
				}
			}
			return r;
		}

	}

	public static String getMaiusculo(String s){
		String r="";
		for(int i=0;i<s.length();i++){
			r = r+ Character.toString(Character.toUpperCase(s.charAt(i)));
		}
		return r;

	}

	public static String maxLength(String s, int max){
		String r="";
		if(s.length() > max){
			for(int i=0; i < max; i++){
				r = r+Character.toString(s.charAt(i));
			}
			return r;
		}else{
			return s;
		}
	}

	public static String formatCEP(String s){
		String r="";
		for(int i=0; i<s.length();i++){
			if((Character.isDigit(s.charAt(i))) || (Character.toString(s.charAt(i)).equals("-"))){
				r=r+Character.toString(s.charAt(i));
			}
		}
		s=r;
		if(s.length() == 5){
			r=s+"-";
		}else {
			r=s;
		}
		return r;
	}
	
	public static String formataCEP(String s){
		String r="";
		for(int i=0; i<s.length();i++){
			if((Character.isDigit(s.charAt(i))) || (Character.toString(s.charAt(i)).equals("-"))){
				if(i == 5){
					r=r+"-";
				}
				r=r+Character.toString(s.charAt(i));
			}
		}
		s=r;
		
		return r;
	}

	public static String formatTelefone(String s){
		String r="";
		for(int i=0; i<s.length();i++){
			if((Character.isDigit(s.charAt(i))) || (Character.toString(s.charAt(i)).equals("(")) || (Character.toString(s.charAt(i)).equals(")")) || (Character.toString(s.charAt(i)).equals("-"))){
				r=r+Character.toString(s.charAt(i));
			}
		}
		s=r;
		if(s.length() == 1){
			if(Character.toString(s.charAt(0)).equals("(") == false){
				r = "("+Character.toString(s.charAt(0));
			} else{
				r = "(";
			}
		}else if (s.length() == 4){
			r = s+")";
		}else if (s.length() == 9){
			r = s+"-";
		}else {
			r=s;
		}
		return r;
	}
	
	public static String formataTelefone(String s){
		String r="";
		for(int i=0; i<s.length();i++){
			if((Character.isDigit(s.charAt(i))) || (Character.toString(s.charAt(i)).equals("(")) || (Character.toString(s.charAt(i)).equals(")")) || (Character.toString(s.charAt(i)).equals("-"))){
				if(i == 0){					
					r += "(";
				}
				r=r+Character.toString(s.charAt(i));
				if (i == 2){
					r += ")";
				}else if (i == 6){
					r += "-";
				}
			}
		}
		s=r;
		
		return r;
	}

	public static String filtroData(String s){
		String r="";
		for(int i=0; i<s.length();i++){
			if((Character.isDigit(s.charAt(i))) || (Character.toString(s.charAt(i)).equals("-"))){
				r=r+Character.toString(s.charAt(i));
			}
		}
		s=r;
		if((s.length() == 2) || (s.length() == 5)){
			r = s+"-";
			return r;
		}else{
			r=s;
			if((s.length() == 10)){
				try{
					java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("dd-MM-yyyy");
					java.sql.Date d = new java.sql.Date(format.parse(r).getTime());
					r = format.format(d);
				}catch(Exception exc){
					r="";
				}
			}
			return r;
		}

	}

	//Mascaras
	public static void mascaraMax(JTextField ed, KeyEvent k, int maxLength){
		int linha = ed.getCaretPosition();
		transferirFoco(ed, k);
		if (isTeclaIsenta(k)==false){
			ed.setText(BancoDeDados.antiInject(ed.getText()));
			if(maxLength != 0)
				ed.setText(maxLength(ed.getText(), maxLength));
			if (linha < ed.getText().length()){
				ed.setCaretPosition(linha);
			}else{
				ed.setCaretPosition(ed.getText().length());
			}
		}
	}

	public static void mascaraMaiusculo(JTextField ed, KeyEvent k, int maxLength){
		int linha = ed.getCaretPosition();
		transferirFoco(ed, k);
		if (isTeclaIsenta(k)==false){
			ed.setText(BancoDeDados.antiInject(ed.getText()));
			ed.setText(getMaiusculo(ed.getText()));
			if(maxLength != 0)
				ed.setText(maxLength(ed.getText(), maxLength));
			if (linha < ed.getText().length()){
				ed.setCaretPosition(linha);
			}else{
				ed.setCaretPosition(ed.getText().length());
			}
		}
	}

	public static void mascaraCEP(JTextField ed, KeyEvent k, int maxLength){
		int linha = ed.getCaretPosition();
		transferirFoco(ed, k);
		if (isTeclaIsenta(k)==false){
			ed.setText(BancoDeDados.antiInject(ed.getText()));
			ed.setText(formatCEP(ed.getText()));
			if(maxLength != 0)
				ed.setText(maxLength(ed.getText(), maxLength));
			if ((linha < ed.getText().length()) && (linha != 5)){
				ed.setCaretPosition(linha);
			}else{
				ed.setCaretPosition(ed.getText().length());
			}
		}
	}
	
	public static String filtroCPF(String s){
		String r="";
		for(int i=0; i<s.length();i++){
			if((Character.isDigit(s.charAt(i))) || (Character.toString(s.charAt(i)).equals(".")) || (Character.toString(s.charAt(i)).equals("-"))){
				r=r+Character.toString(s.charAt(i));
			}
		}
		s=r;
		if((s.length() == 3) || (s.length() == 7)){
			r=s+".";
		}else if (s.length() == 11){
			r = s+"-";
		}else {
			r=s;
		}
		return r;
	}
	
	public static String formatCPF(String s){
		String r="";
		for(int i=0; i<s.length();i++){
			if((Character.isDigit(s.charAt(i))) || (Character.toString(s.charAt(i)).equals(".")) || (Character.toString(s.charAt(i)).equals("-"))){
				
				r=r+Character.toString(s.charAt(i));
				if((i == 2) || (i == 5)){
					r+=".";
				}else if (i == 8){
					r += "-";
				}
				
			}
		}
		
		
		return r;
	}

	public static void mascaraCNPJ(JTextField ed, KeyEvent k, int maxLength){
		int linha = ed.getCaretPosition();
		transferirFoco(ed, k);
		if (isTeclaIsenta(k)==false){
			ed.setText(BancoDeDados.antiInject(ed.getText()));
			ed.setText(filtroCNPJ(ed.getText()));
			if(maxLength != 0)
				ed.setText(maxLength(ed.getText(), maxLength));
			if ((linha < ed.getText().length()) && (linha != 2) && (linha != 6) && (linha != 10) && (linha != 15)){
				ed.setCaretPosition(linha);
			}else{
				ed.setCaretPosition(ed.getText().length());
			}
		}
	}

	public static void mascaraTelefone(JTextField ed, KeyEvent k, int maxLength){
		int linha = ed.getCaretPosition();
		transferirFoco(ed, k);
		if (isTeclaIsenta(k)==false){
			ed.setText(BancoDeDados.antiInject(ed.getText()));
			ed.setText(formatTelefone(ed.getText()));
			if(maxLength != 0)
				ed.setText(maxLength(ed.getText(), maxLength));
			if ((linha < ed.getText().length()) && (linha != 1) && (linha != 4) && (linha != 9)){
				ed.setCaretPosition(linha);
			}else{
				ed.setCaretPosition(ed.getText().length());
			}
		}
	}
	
	public static void mascaraInscricaoEstadual(JTextField ed, KeyEvent k, int maxLength){
		int linha = ed.getCaretPosition();
		transferirFoco(ed, k);
		if (isTeclaIsenta(k)==false){
			ed.setText(BancoDeDados.antiInject(ed.getText()));
			ed.setText(filtroInscricaoEstadual(ed.getText()));
			if(maxLength != 0)
				ed.setText(maxLength(ed.getText(), maxLength));
			if ((linha < ed.getText().length()) && (linha != 3) && (linha != 7) && (linha != 11)){
				ed.setCaretPosition(linha);
			}else{
				ed.setCaretPosition(ed.getText().length());
			}
		}
	}
	
	public static void mascaraRG(JTextField ed, KeyEvent k, int maxLength){
		int linha = ed.getCaretPosition();
		transferirFoco(ed, k);
		if (isTeclaIsenta(k)==false){
			ed.setText(BancoDeDados.antiInject(ed.getText()));
			ed.setText(filtroRG(ed.getText()));
			if(maxLength != 0)
				ed.setText(maxLength(ed.getText(), maxLength));
			if ((linha < ed.getText().length()) && (linha != 2) && (linha != 6) && (linha != 10)){
				ed.setCaretPosition(linha);
			}else{
				ed.setCaretPosition(ed.getText().length());
			}
		}
	}
	
	public static void mascaraCPF(JTextField ed, KeyEvent k, int maxLength){
		int linha = ed.getCaretPosition();
		transferirFoco(ed, k);
		if (isTeclaIsenta(k)==false){
			ed.setText(BancoDeDados.antiInject(ed.getText()));
			ed.setText(filtroCPF(ed.getText()));
			if(maxLength != 0)
				ed.setText(maxLength(ed.getText(), maxLength));
			if ((linha < ed.getText().length()) && (linha != 3) && (linha != 7) && (linha != 11)){
				ed.setCaretPosition(linha);
			}else{
				ed.setCaretPosition(ed.getText().length());
			}
		}
	}

	public static void mascaraNumero(JTextField ed, KeyEvent k, int maxLength){
		int linha = ed.getCaretPosition();
		transferirFoco(ed, k);
		if (isTeclaIsenta(k)==false){
			ed.setText(BancoDeDados.antiInject(ed.getText()));
			ed.setText(filtroNumeros(ed.getText()));
			if(maxLength != 0)
				ed.setText(maxLength(ed.getText(), maxLength));
			if (linha < ed.getText().length()){
				ed.setCaretPosition(linha);
			}else{
				ed.setCaretPosition(ed.getText().length());
			}
		}
	}
	
	public static void mascaraSemEspacos(JTextField ed, KeyEvent k){
		int linha = ed.getCaretPosition();
		transferirFoco(ed, k);
		if (isTeclaIsenta(k)==false){
			ed.setText(BancoDeDados.antiInject(ed.getText()));
			ed.setText(filtroSemEspacos(ed.getText()));
			
			if (linha < ed.getText().length()){
				ed.setCaretPosition(linha);
			}else{
				ed.setCaretPosition(ed.getText().length());
			}
		}
	}

	public static void mascaraDecimal(JTextField ed, KeyEvent k, int maxLength){
		int linha = ed.getCaretPosition();
		transferirFoco(ed, k);
		if (isTeclaIsenta(k)==false){

			ed.setText(filtroDecimal(ed.getText()));
			if(maxLength != 0)
				ed.setText(maxLength(ed.getText(), maxLength));
			if (linha < ed.getText().length()){
				ed.setCaretPosition(linha);
			}else{
				ed.setCaretPosition(ed.getText().length());
			}
		}
	}

	public static void mascaraData(JTextField ed, KeyEvent k, int maxLength){
		int linha = ed.getCaretPosition();
		transferirFoco(ed, k);
		if (isTeclaIsenta(k)==false){
			ed.setText(filtroData(ed.getText()));

			if(maxLength != 0)
				ed.setText(maxLength(ed.getText(), maxLength));
			if ((linha < ed.getText().length())  && (linha != 2) && (linha != 5)){
				ed.setCaretPosition(linha);
			}else{
				ed.setCaretPosition(ed.getText().length());
			}
		}
	}
	
	public static void mascaraHora(JTextField ed, KeyEvent k, int maxLength){
		int linha = ed.getCaretPosition();
		transferirFoco(ed, k);
		if (isTeclaIsenta(k)==false){
			ed.setText(filtroHora(ed.getText()));

			if(maxLength != 0)
				ed.setText(maxLength(ed.getText(), maxLength));
			if ((linha < ed.getText().length())  && (linha != 2) && (linha != 5)){
				ed.setCaretPosition(linha);
			}else{
				ed.setCaretPosition(ed.getText().length());
			}
		}
	}
}