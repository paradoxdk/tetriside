package tetris.report;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;

import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class Report {
	private String title, header, detail, sumary, head, foot;
	private int orientacao, marginTop, marginLeft, marginBottom, marginRight;

	public static final int POTRAIT=0, RETRATO=0, PAISAGEM=1;

	//Constructor
	public Report(){
		this.title = "";
		this.header = "";
		this.detail = "";
		this.sumary = "";
		this.orientacao=RETRATO;
		head="";
		foot="";
		marginLeft=10;
		marginRight=10;
		marginTop=10;
		marginBottom=10;
	}

	public Report(String title, String header, String detail, String sumary) {
		super();
		this.title = title;
		this.header = header;
		this.detail = detail;
		this.sumary = sumary;
		this.orientacao=RETRATO;
		head="";
		foot="";
		marginLeft=10;
		marginRight=10;
		marginTop=10;
		marginBottom=10;
	}

	public Report(String title, String header, String detail, String sumary, int orientacao) {
		this(title, header, detail, sumary);
		
		this.orientacao=orientacao;
	}

	//Show Preview

	public void preview(){
		String html="<html>\n"
				+ "	<head>\n"
				+ "		<title>"+title+"</title>\n"
				+ "	</head>\n"
				+ "	<body>\n"
				+ "		"+header+"\n"
				+"		"+detail+"\n"
				+"		"+sumary+"\n"
				+"	</body>\n"
				+"</html>";
		int count=0;
		
		for(count=0;count<10;count++){
			try{
				Document document = null;
				if(orientacao==0){
					document = new Document(PageSize.A4, marginLeft, marginRight, marginTop, marginBottom);
				}else{
					document = new Document(PageSize.A4.rotate(), marginLeft, marginRight, marginTop, marginBottom);
				}
				File filePathPDF = new File(System.getProperty("user.home")+"/tetris/");
				if(!filePathPDF.exists()){
					filePathPDF.mkdirs();filePathPDF.mkdir();
				}

				PdfWriter pdfWriter = PdfWriter.getInstance
						(document, new FileOutputStream(System.getProperty("user.home")+"/tetris/report"+count+".pdf"));
				pdfWriter.setPageEvent(new HeaderFooter(this));
				document.open();
				document.addAuthor("TetrisReport");
				document.addCreator("TetrisReport");
				document.addSubject("");
				document.addCreationDate();
				document.addTitle("Report");

				XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
			
				worker.parseXHtml(pdfWriter, document, new StringReader(html));
				document.close();
				break;
			}catch(Exception exc){
				if(count==9){
					JOptionPane.showMessageDialog(null, "TetrisReport Error:\n"+exc);
					return;
				}
			}
		}

		try{
			Desktop desktop = Desktop.getDesktop();
			File filePDF = new File(System.getProperty("user.home")+"/tetris/report"+count+".pdf");
			desktop.open(filePDF);
			
			do{
				Thread.sleep(4000);
			}while(!filePDF.delete());
		}catch(Exception exc){
			new JDialogTetrisReport(html, orientacao, marginLeft, marginRight, marginTop, marginBottom).preview();
		}
	}

	public void print(){
		new JDialogTetrisReport(header+detail+sumary, orientacao, marginLeft, marginRight, marginTop, marginBottom).print();
	}

	//Getters and Setters
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getSumary() {
		return sumary;
	}

	public void setSumary(String sumary) {
		this.sumary = sumary;
	}

	public int getOrientacao() {
		return orientacao;
	}

	public void setOrientacao(int orientacao) {
		this.orientacao = orientacao;
	}

	public int getOrientation() {
		return orientacao;
	}

	public void setOrientation(int orientacao) {
		this.orientacao = orientacao;
	}

	public int getMarginTop() {
		return marginTop;
	}

	public void setMarginTop(int marginTop) {
		this.marginTop = marginTop;
	}

	public int getMarginLeft() {
		return marginLeft;
	}

	public void setMarginLeft(int marginLeft) {
		this.marginLeft = marginLeft;
	}

	public int getMarginBottom() {
		return marginBottom;
	}

	public void setMarginBottom(int marginBottom) {
		this.marginBottom = marginBottom;
	}

	public int getMarginRight() {
		return marginRight;
	}

	public void setMarginRight(int marginRight) {
		this.marginRight = marginRight;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getFoot() {
		return foot;
	}

	public void setFoot(String foot) {
		this.foot = foot;
	}

}
