package componentes.modelo.bancodedados;

public class Criptografia {
	public static String Criptografar(String s){

		String r="";
		for(int i=0; i < s.length();i++){
			r=r+Criptografa(Character.toString(s.charAt(i)));
		}
		return r;
	}

	public static String Descriptografar(String s){

		String r="";
		for(int i=0; i < s.length();i++){
			r=r+Descriptografa(Character.toString(s.charAt(i)));
		}
		return r;
	}

	public static String Criptografa(String s){
		/*0-7
		  1-4
		  2-5
		  3-1
		  4-9
		  5-0
		  6-3
		  7-2
		  8-6
		  9-8
		 */
		if (s.equals("0")){
			return "7";
		}else if (s.equals("1")){
			return "4";
		}else if (s.equals("2")){
			return "5";
		}else if (s.equals("3")){
			return "1";
		}else if (s.equals("4")){
			return "9";
		}else if (s.equals("5")){
			return "0";
		}else if (s.equals("6")){
			return "3";
		}else if (s.equals("7")){
			return "2";
		}else if (s.equals("8")){
			return "6";
		}else if (s.equals("9")){
			return "8";
		}else if (s.equals("a")){
			return "e";
		}else if (s.equals("A")){
			return "E";
		}else if (s.equals("b")){
			return "m";
		}else if (s.equals("B")){
			return "M";
		}else if (s.equals("c")){
			return "j";
		}else if (s.equals("C")){
			return "J";
		}else if (s.equals("d")){
			return "s";
		}else if (s.equals("D")){
			return "S";
		}else if (s.equals("e")){
			return "r";
		}else if (s.equals("E")){
			return "R";
		}else if (s.equals("f")){
			return "z";
		}else if (s.equals("F")){
			return "Z";
		}else if (s.equals("g")){
			return "p";
		}else if (s.equals("G")){
			return "P";
		}else if (s.equals("h")){
			return "o";
		}else if (s.equals("H")){
			return "O";
		}else if (s.equals("i")){
			return "t";
		}else if (s.equals("I")){
			return "T";
		}else if (s.equals("j")){
			return "n";
		}else if (s.equals("J")){
			return "N";
		}else if (s.equals("k")){
			return "d";
		}else if (s.equals("K")){
			return "D";
		}else if (s.equals("l")){
			return "f";
		}else if (s.equals("L")){
			return "F";
		}else if (s.equals("m")){
			return "a";
		}else if (s.equals("M")){
			return "A";
		}else if (s.equals("n")){
			return "i";
		}else if (s.equals("N")){
			return "I";
		}else if (s.equals("o")){
			return "l";
		}else if (s.equals("O")){
			return "L";
		}else if (s.equals("p")){
			return "g";
		}else if (s.equals("P")){
			return "G";
		}else if (s.equals("q")){
			return "b";
		}else if (s.equals("Q")){
			return "B";
		}else if (s.equals("r")){
			return "u";
		}else if (s.equals("R")){
			return "U";
		}else if (s.equals("s")){
			return "k";
		}else if (s.equals("S")){
			return "K";
		}else if (s.equals("t")){
			return "c";
		}else if (s.equals("T")){
			return "C";
		}else if (s.equals("u")){
			return "h";
		}else if (s.equals("U")){
			return "H";
		}else if (s.equals("w")){
			return "q";
		}else if (s.equals("W")){
			return "Q";
		}else if (s.equals("v")){
			return "y";
		}else if (s.equals("V")){
			return "Y";
		}else if (s.equals("y")){
			return "w";
		}else if (s.equals("Y")){
			return "W";
		}else if (s.equals("x")){
			return "v";
		}else if (s.equals("X")){
			return "V";
		}else if (s.equals("z")){
			return "x";
		}else if (s.equals("Z")){
			return "X";
		}else if (s.equals("ç")){
			return "\\";
		}else if (s.equals("Ç")){
			return "/";
		}else if (s.equals("/")){
			return "Ç";
		}else if (s.equals("\\")){
			return "ç";
		}else{
			return s;
		}
	}

	public static String Descriptografa(String s){
		/*0-7
		  1-4
		  2-5
		  3-1
		  4-9
		  5-0
		  6-3
		  7-2
		  8-6
		  9-8
		 */
		if (s.equals("7")){
			return "0";
		}else if (s.equals("4")){
			return "1";
		}else if (s.equals("5")){
			return "2";
		}else if (s.equals("1")){
			return "3";
		}else if (s.equals("9")){
			return "4";
		}else if (s.equals("0")){
			return "5";
		}else if (s.equals("3")){
			return "6";
		}else if (s.equals("2")){
			return "7";
		}else if (s.equals("6")){
			return "8";
		}else if (s.equals("8")){
			return "9";
		}else if (s.equals("e")){
			return "a";
		}else if (s.equals("E")){
			return "A";
		}else if (s.equals("m")){
			return "b";
		}else if (s.equals("M")){
			return "B";
		}else if (s.equals("j")){
			return "c";
		}else if (s.equals("J")){
			return "C";
		}else if (s.equals("s")){
			return "d";
		}else if (s.equals("S")){
			return "D";
		}else if (s.equals("r")){
			return "e";
		}else if (s.equals("R")){
			return "E";
		}else if (s.equals("z")){
			return "f";
		}else if (s.equals("Z")){
			return "F";
		}else if (s.equals("p")){
			return "g";
		}else if (s.equals("P")){
			return "G";
		}else if (s.equals("o")){
			return "h";
		}else if (s.equals("O")){
			return "H";
		}else if (s.equals("t")){
			return "i";
		}else if (s.equals("T")){
			return "I";
		}else if (s.equals("n")){
			return "j";
		}else if (s.equals("N")){
			return "J";
		}else if (s.equals("d")){
			return "k";
		}else if (s.equals("D")){
			return "K";
		}else if (s.equals("f")){
			return "l";
		}else if (s.equals("F")){
			return "L";
		}else if (s.equals("a")){
			return "m";
		}else if (s.equals("A")){
			return "M";
		}else if (s.equals("i")){
			return "n";
		}else if (s.equals("I")){
			return "N";
		}else if (s.equals("l")){
			return "o";
		}else if (s.equals("L")){
			return "O";
		}else if (s.equals("g")){
			return "p";
		}else if (s.equals("G")){
			return "P";
		}else if (s.equals("b")){
			return "q";
		}else if (s.equals("B")){
			return "Q";
		}else if (s.equals("u")){
			return "r";
		}else if (s.equals("U")){
			return "R";
		}else if (s.equals("k")){
			return "s";
		}else if (s.equals("K")){
			return "S";
		}else if (s.equals("c")){
			return "t";
		}else if (s.equals("C")){
			return "T";
		}else if (s.equals("h")){
			return "u";
		}else if (s.equals("H")){
			return "U";
		}else if (s.equals("q")){
			return "w";
		}else if (s.equals("Q")){
			return "W";
		}else if (s.equals("y")){
			return "v";
		}else if (s.equals("Y")){
			return "V";
		}else if (s.equals("w")){
			return "y";
		}else if (s.equals("W")){
			return "Y";
		}else if (s.equals("v")){
			return "x";
		}else if (s.equals("V")){
			return "X";
		}else if (s.equals("x")){
			return "z";
		}else if (s.equals("X")){
			return "Z";
		}else if (s.equals("\\")){
			return "ç";
		}else if (s.equals("/")){
			return "Ç";
		}else if (s.equals("ç")){
			return "\\";
		}else if (s.equals("Ç")){
			return "/";
		}else{
			return s;
		}
	}
}
