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

    public byte[] genInventoryReport(List<ProductResponseBasic> productos) {
        // ByteArrayOutputStream para generar el PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);

        // Crear el documento PDF
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Título del reporte
        document.add(new Paragraph("Reporte de Inventario").setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Fecha de generación: " + java.time.LocalDate.now()).setItalic().setFontSize(12).setTextAlignment(TextAlignment.CENTER));

        float pageWidth = 595; // Ancho de la página A4 en puntos
        float margin = 36; // Margen predeterminado en puntos
        float maxTableWidth = pageWidth - 2 * margin; // Ancho máximo de la tabla

        Table table = new Table(6);
        table.setWidth(maxTableWidth);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        table.addCell(new Paragraph("ID").setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Nombre").setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Categoria").setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Existencias").setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Precio").setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Imagen").setTextAlignment(TextAlignment.CENTER));

        if (productos.isEmpty()) {
            document.add(new Paragraph("No hay productos registrados"));
            document.close();
            return baos.toByteArray();
        }

        // Llenado de tabla con los productos
        for (ProductResponseBasic producto : productos) {
            table.addCell(producto.getId().toString());
            table.addCell(producto.getName());
            table.addCell(producto.getCategory());
            table.addCell(producto.getStock().toString());
            table.addCell(producto.getPrice().toString());

            // Agregar la imagen del producto
            if (producto.getImageUrl() != null) {
                try {
                    ImageData imageData = ImageDataFactory.create(new URL(producto.getImageUrl()));
                    Image image = new Image(imageData);
                    image.setWidth(40);
                    image.setHeight(40);
                    table.addCell(image.setHorizontalAlignment(HorizontalAlignment.CENTER));
                } catch (
                        Exception e) {
                    table.addCell("Imagen no disponible");
                }
            } else {
                table.addCell("Sin imagen");
            }
        }

        document.add(table);
        document.close();

        // Se retorna el PDF como arreglo de bytes
        return baos.toByteArray();
    }

    public byte[] genMovementsReport(List<MovementResponse> movimientos, String tipoReporte) {
        // ByteArrayOutputStream para generar el PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);

        // Crear el documento PDF
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        if (!tipoReporte.equals("general") && !tipoReporte.equals("entrada") && !tipoReporte.equals("salida")) {
            throw new InvalidReportType("Tipo de reporte inválido");
        }

        // Título del reporte
        String reportTitle = tipoReporte.equals("general") ? "Reporte de movimientos de inventario" :
                tipoReporte.equals("entrada") ? "Reporte de entradas de inventario" : "Reporte de salidas de inventario";

        document.add(new Paragraph(reportTitle).setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Fecha de generación: " + java.time.LocalDate.now()).setItalic().setFontSize(12).setTextAlignment(TextAlignment.CENTER));

        float pageWidth = 595; // Ancho de la página A4 en puntos
        float margin = 36; // Margen predeterminado en puntos
        float maxTableWidth = pageWidth - 2 * margin; // Ancho máximo de la tabla

        boolean mostrarCosto = tipoReporte.equals("general") || tipoReporte.equals("entrada");

        Table table = new Table(mostrarCosto ? 6 : 5);
        table.setWidth(maxTableWidth);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        table.addCell(new Paragraph("ID").setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Producto").setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Cantidad").setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Tipo").setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Paragraph("Fecha").setTextAlignment(TextAlignment.CENTER));
        if (mostrarCosto) {
            table.addCell(new Paragraph("Costo adquisición").setTextAlignment(TextAlignment.CENTER));
        }

        if (movimientos.isEmpty()) {
            document.add(new Paragraph("No hay movimientos registrados"));
            document.close();
            return baos.toByteArray();
        }

        // Llenado de tabla con los movimientos
        for (MovementResponse mov : movimientos) {
            table.addCell(mov.getId().toString());
            table.addCell(mov.getProduct().getName());
            table.addCell(mov.getQuantity().toString());
            table.addCell(mov.getMovementType().toString());
            table.addCell(mov.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            if (mostrarCosto) {
                table.addCell(mov.getAcquisitionCost() != null ? mov.getAcquisitionCost().toString() : "N/A");
            }
        }
        document.add(table);
        document.close();
        // Se retorna el PDF como arreglo de bytes
        return baos.toByteArray();
    }
}
