/*
 *Manipula Datas
 *Por: David de Almeida Bezerra Júnior
 *Versão: 1.3
 *Data: 24 de abril de 2020
 *Licensed by GNU General Public License (https://www.gnu.org/licenses/gpl-3.0.txt)
 */
package br.com.analisasoftware.tetris.modelo;

public class Data {
	//Retorna a data atual em formato de String
	public static String getDataAtual(){
		java.util.Date dat = new java.util.Date();
		java.text.SimpleDateFormat formatdata = new java.text.SimpleDateFormat("dd/MM/yyyy");

		return formatdata.format(dat);
	}
	//Retorna a hora atual em formato de String
	public static String getHoraAtual(){
		java.util.Date dat = new java.util.Date();
		java.text.SimpleDateFormat formatter =  new java.text.SimpleDateFormat("HH:mm:ss");

		return formatter.format(dat);
	}
	//Retorna a data informada por parâmetro em padrão americado (yyyy-MM-dd)
	public static String padraoAmericano(String s){
		if (s.length() == 10){
			String r="";
			String dia="";
			String mes="";
			String ano="";
			for(int i = 0; i<=9; i++){
				if ((i==0) || (i == 1)){
					dia = dia+Character.toString(s.charAt(i));
				}else if ((i==3) || (i == 4)){
					mes = mes+Character.toString(s.charAt(i));
				}else if ((i==6) || (i == 7) || (i == 8) || (i == 9)){
					ano = ano+Character.toString(s.charAt(i));
				}
			}
			r = ano+"-"+mes+"-"+dia;
			return r;
		}else{
			return s;
		}
	}
	//Retorna a data informada por parâmetro em formato brasileiro (dd/MM/yyyy)
	public static String padraoBrasileiro(String s){
		if (s.length() == 10){
			String r="";
			String dia="";
			String mes="";
			String ano="";
			for(int i = 0; i<=9; i++){
				if ((i==8) || (i == 9)){
					dia = dia+Character.toString(s.charAt(i));
				}else if ((i==5) || (i == 6)){
					mes = mes+Character.toString(s.charAt(i));
				}else if ((i==0) || (i == 1) || (i == 2) || (i == 3)){
					ano = ano+Character.toString(s.charAt(i));
				}
			}
			r = dia+"/"+mes+"/"+ano;
			return r;
		}else{
			return s;
		}
	}
}
