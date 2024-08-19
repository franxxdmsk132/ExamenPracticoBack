package com.eabmodel.casopracticoexamenback.Reporte;

import com.eabmodel.casopracticoexamenback.Model.Producto;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class Reporte {
    private List<Producto> listarProductos;

    public Reporte(List<Producto> listarProductos) {
        super();
        this.listarProductos = listarProductos;
    }
    private void escribirProductos(PdfPTable table){
        for (Producto producto : listarProductos) {
            table.addCell(String.valueOf(producto.getId()));
            table.addCell(producto.getNombre());
            table.addCell(producto.getDescripcion());
            table.addCell(producto.getCategoria().getNombre_categoria());
            table.addCell(String.valueOf(producto.getStock()));
            table.addCell(producto.getUbicacion());
            table.addCell(producto.getFecha_caducidad());

        }
    }
    private void escribirCabecera(PdfPTable table){
        PdfPCell cell = new PdfPCell();

        cell.setBackgroundColor(Color.DARK_GRAY);
        cell.setPadding(5);
        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Nombre", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Descripcion", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Categoria", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Stock", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Ubicacion", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Fecha Caducidad", font));
        table.addCell(cell);
    }
    public void exportarReporte(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.DARK_GRAY);
        font.setSize(18);

        Paragraph paragraph = new Paragraph("Lista de Productos", font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);
        table.setWidths(new float[]{1f, 3f, 6f, 2.4f, 2f, 3f, 3f});
        table.setWidthPercentage(110);

        escribirCabecera(table);
        escribirProductos(table);
        document.add(table);
        document.close();
    }
}
