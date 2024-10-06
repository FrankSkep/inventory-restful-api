package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoBasicoDTO;
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
import java.util.List;

@Service
public class ReporteService {

    private final ProductoService productoService;

    public ReporteService(ProductoService productoService) {
        this.productoService = productoService;
    }

    public byte[] generarReporteInventario() {
        // Obtener la lista de productos
        List<ProductoBasicoDTO> productos = productoService.obtenerProductos();

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

        // Llenado de tabla con los productos
        for (ProductoBasicoDTO producto : productos) {
            table.addCell(producto.getId().toString());
            table.addCell(producto.getNombre());
            table.addCell(producto.getCategoria());
            table.addCell(producto.getCantidadStock().toString());
            table.addCell(producto.getPrecio().toString());

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

}
