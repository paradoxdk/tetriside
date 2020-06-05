package tetris.report;

public class TetrisReportTest {
	public static void main(String[] args) {
		
		Report report = new Report("Test","<table cellpadding='0' cellspacing='0' border='0'><thead><tr><td><h1>Teste de Impressao</h1></td></tr></thead>", "<tr><td><h3>Imprimindo</h3><br/><h4>Tres</h4><br/>Linhas<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/>*<br/></td></tr>", "<tfoot><tr><td>3 linhas</td></tr></tfoot></table>", 0);
		//report.setMarginTop(30);
		//report.setMarginBottom(30);
		//report.setHead("<table border='1' width='100%'><tr><td align='center'>Cabecalho</td></tr></table>");
		//report.setFoot("<table border='1' width='100%'><tr><td align='center'>Rodape</td></tr></table>");
		report.preview();
	}
}
