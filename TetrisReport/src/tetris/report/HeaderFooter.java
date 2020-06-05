package tetris.report;

import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorkerHelper;

class HeaderFooter extends PdfPageEventHelper {
    protected ElementList header;
    protected ElementList footer;
    public HeaderFooter(Report report) throws IOException {
        header = XMLWorkerHelper.parseToElementList(report.getHead(), null);
        footer = XMLWorkerHelper.parseToElementList(report.getFoot(), null);
    }
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        try {
            ColumnText ct = new ColumnText(writer.getDirectContent());
            ct.setSimpleColumn(new Rectangle(document.leftMargin()*4, document.getPageSize().getHeight()-10, document.getPageSize().getWidth()-((document.rightMargin()*4))-((document.leftMargin()*4)), ((document.topMargin()))));
            for (Element e : header) {
                ct.addElement(e);
            }
            ct.go();
            ct.setSimpleColumn(new Rectangle(document.leftMargin()*4, 10, document.getPageSize().getWidth()-((document.rightMargin()*4))-((document.leftMargin()*4)), document.bottomMargin()));
            for (Element e : footer) {
                ct.addElement(e);
            }
            ct.go();
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }
}
