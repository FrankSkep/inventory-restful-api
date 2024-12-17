package com.fran.inventory_api.service.Impl;

import com.fran.inventory_api.dto.MovementResponse;
import com.fran.inventory_api.dto.ProductResponseBasic;
import com.fran.inventory_api.exception.InvalidReportType;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportService {

    public byte[] generateProductsReport(List<ProductResponseBasic> productos) {
        // ByteArrayOutputStream to generate the PDF in memory
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);

        // Create the pdf document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Título del reporte
        document.add(new Paragraph("Inventory Report").setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Generation date: " + java.time.LocalDate.now()).setItalic().setFontSize(12).setTextAlignment(TextAlignment.CENTER));

        float pageWidth = 595; // Width de una página A4 en puntos
        float margin = 36; // Default margin en puntos
        float maxTableWidth = pageWidth - 2 * margin; // Maximum table width

        Table table = new Table(6);
        table.setWidth(maxTableWidth);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        table.addCell(new Paragraph("ID").setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Name").setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Category").setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Stock").setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Price").setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Image").setTextAlignment(TextAlignment.CENTER));

        if (productos.isEmpty()) {
            document.add(new Paragraph("Inventory is empty"));
            document.close();
            return baos.toByteArray();
        }

        // Llenado de tabla con los productos
        for (ProductResponseBasic p : productos) {
            table.addCell(p.getId().toString());
            table.addCell(p.getName());
            table.addCell(p.getCategory());
            table.addCell(p.getStock().toString());
            table.addCell(p.getPrice().toString());

            // Agregar la imagen del producto
            if (p.getImageUrl() != null) {
                try {
                    ImageData imageData = ImageDataFactory.create(new URL(p.getImageUrl()));
                    Image image = new Image(imageData);
                    image.setWidth(40);
                    image.setHeight(40);
                    table.addCell(image.setHorizontalAlignment(HorizontalAlignment.CENTER));
                } catch (
                        Exception e) {
                    table.addCell("Image not available");
                }
            } else {
                table.addCell("No image");
            }
        }

        document.add(table);
        document.close();

        // return the PDF as a byte array
        return baos.toByteArray();
    }

    public byte[] generateMovementsReport(List<MovementResponse> movements, String reportType) {
        // ByteArrayOutputStream to generate the PDF in memory
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);

        // Create the pdf document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        if (!reportType.equals("all") && !reportType.equals("entry") && !reportType.equals("exit")) {
            throw new InvalidReportType("Report type must be 'all', 'entry' or 'exit'");
        }

        // Report title
        String reportTitle = reportType.equals("all") ? "Inventory movements report" :
                reportType.equals("entry") ? "Stock entries report" : "Stock out report";

        document.add(new Paragraph(reportTitle).setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Generation date: " + java.time.LocalDate.now()).setItalic().setFontSize(12).setTextAlignment(TextAlignment.CENTER));

        float pageWidth = 595; // Width of an A4 page in points
        float margin = 36; // Default margin in points
        float maxTableWidth = pageWidth - 2 * margin; // Maximum table width

        boolean mostrarCosto = reportType.equals("all") || reportType.equals("entry");

        Table table = new Table(mostrarCosto ? 6 : 5);
        table.setWidth(maxTableWidth);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        table.addCell(new Paragraph("ID").setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Product").setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Quantity").setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Type").setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Date").setTextAlignment(TextAlignment.CENTER));
        if (mostrarCosto) {
            table.addCell(new Paragraph("Acquisition cost").setTextAlignment(TextAlignment.CENTER));
        }

        if (movements.isEmpty()) {
            document.add(new Paragraph("No stock movements recorded"));
            document.close();
            return baos.toByteArray();
        }

        // Llenado de tabla con los movimientos
        for (MovementResponse m : movements) {
            table.addCell(m.getId().toString());
            table.addCell(m.getProduct().getName());
            table.addCell(m.getQuantity().toString());
            table.addCell(m.getMovementType().toString());
            table.addCell(m.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            if (mostrarCosto) {
                table.addCell(m.getAcquisitionCost() != null ? m.getAcquisitionCost().toString() : "N/A");
            }
        }
        document.add(table);
        document.close();

        // return the PDF as a byte array
        return baos.toByteArray();
    }
}
