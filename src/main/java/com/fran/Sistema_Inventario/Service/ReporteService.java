package com.fran.Sistema_Inventario.Service;

import com.fran.Sistema_Inventario.DTO.ProductoDTOs.ProductoBasicoDTO;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
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
        document.add(new Paragraph("Reporte de Inventario").setBold().setFontSize(20));

        // Tabla con encabezados
        float[] columnWidths = {1, 3, 2, 2};
        Table table = new Table(columnWidths);
        table.addCell("ID");
        table.addCell("Nombre");
        table.addCell("Categoria");
        table.addCell("Cantidad");
        table.addCell("Precio");

        // Llenado de tabla con los productos
        for (ProductoBasicoDTO producto : productos) {
            table.addCell(producto.getId().toString());
            table.addCell(producto.getNombre());
            table.addCell(producto.getCategoria());
            table.addCell(producto.getCantidadStock().toString());
            table.addCell(producto.getPrecio().toString());
        }

        document.add(table);
        document.close();

        // Se retorna el PDF como arreglo de bytes
        return baos.toByteArray();
    }

}
