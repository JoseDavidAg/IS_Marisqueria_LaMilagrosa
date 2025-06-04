/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.util;
import com.IS.marisqueria3.model.MTablas.ReporteVentas;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ambro
 */
public class GeneradorReportes {

    // Fuentes para PDF (iText)
    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
    private static final Font BODY_FONT = new Font(Font.FontFamily.HELVETICA, 10);

    public static void generarPDF(List<ReporteVentas> reportes, String filePath, String titulo, String nombreResponsable, 
                                Date fechaInicio, Date fechaFin) throws DocumentException, IOException {
       // Fuentes
       final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

       // Calcular total
       float total = 0;
       for(ReporteVentas rp: reportes) {
           total += rp.getMontoTotal();
       }

       Document document = new Document(PageSize.A4.rotate());
       PdfWriter.getInstance(document, new FileOutputStream(filePath));
       document.open();

       // Formatear fechas (dd-MM-yyyy)
       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
       String strFechaInicio = sdf.format(fechaInicio);
       String strFechaFin = sdf.format(fechaFin);

       // ENCABEZADO CON FORMATO
       PdfPTable headerTable = new PdfPTable(2);
       headerTable.setWidthPercentage(100);
       headerTable.setSpacingAfter(15f);

       // Celda izquierda (Responsable y fechas)
       PdfPCell leftCell = new PdfPCell();
       leftCell.setBorder(Rectangle.NO_BORDER);
       leftCell.addElement(new Paragraph("Responsable: " + nombreResponsable, NORMAL_FONT));
       leftCell.addElement(new Paragraph("Fecha inicio: " + strFechaInicio, NORMAL_FONT));
       leftCell.addElement(new Paragraph("Fecha fin: " + strFechaFin, NORMAL_FONT));
       headerTable.addCell(leftCell);

       // Celda derecha (vacía para alineación)
       PdfPCell rightCell = new PdfPCell();
       rightCell.setBorder(Rectangle.NO_BORDER);
       headerTable.addCell(rightCell);

       document.add(headerTable);

       // Título del reporte (centrado)
       Paragraph title = new Paragraph(titulo, TITLE_FONT);
       title.setAlignment(Element.ALIGN_CENTER);
       title.setSpacingAfter(20f);
       document.add(title);

       // Tabla de datos
       PdfPTable table = new PdfPTable(6); 
       table.setWidthPercentage(100);
       table.setSpacingBefore(10f);
       table.setSpacingAfter(10f);

       // Encabezados
       addTableHeader(table);

       // Datos
       for (ReporteVentas reporte : reportes) {
           addReporteRow(table, reporte);
       }

       // FILA DE TOTALES (al final de la tabla)
       PdfPCell totalLabelCell = new PdfPCell(new Phrase("TOTAL VENTAS:", NORMAL_FONT));
       totalLabelCell.setColspan(5);
       totalLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
       totalLabelCell.setBorder(Rectangle.TOP);
       table.addCell(totalLabelCell);

       PdfPCell totalValueCell = new PdfPCell(new Phrase(String.format("%.2f", total), NORMAL_FONT));
       totalValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
       totalValueCell.setBorder(Rectangle.TOP);
       table.addCell(totalValueCell);

       document.add(table);
       document.close();
   }
    

    private static void addTableHeader(PdfPTable table) {
        String[] headers = {
            "ID Platillo", "Platillo", "Categoría", 
            "Precio Unitario", "Cantidad Vendida", 
            "Monto Total"
        };

        for (String header : headers) {
            PdfPCell cell = new PdfPCell();
            cell.setBackgroundColor(new BaseColor(0, 102, 204)); // Azul corporativo
            cell.setPadding(5);
            cell.setPhrase(new Phrase(header, HEADER_FONT));
            table.addCell(cell);
        }
    }

    private static void addReporteRow(PdfPTable table, ReporteVentas reporte) {
        table.addCell(createCell(String.valueOf(reporte.getPlatilloId())));
        table.addCell(createCell(reporte.getPlatilloNombre()));
        table.addCell(createCell(reporte.getCategoriaNombre()));
        table.addCell(createCell(reporte.getPrecioFormateado()));
        table.addCell(createCell(String.valueOf(reporte.getCantidadVendida())));
        table.addCell(createCell(reporte.getMontoTotalFormateado()));
        
        /*
        String fecha = (reporte.getFechaVenta() != null) 
            ? reporte.getFechaVenta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) 
            : "N/A";
        table.addCell(createCell(fecha));
        
        String hora = (reporte.getHoraVenta() != null) 
            ? reporte.getHoraVenta().format(DateTimeFormatter.ofPattern("HH:mm")) 
            : "N/A";*/
        //table.addCell(createCell(hora));
    }

    // Método auxiliar para crear celdas en PDF
    private static PdfPCell createCell(String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content, BODY_FONT));
        cell.setPadding(5);
        return cell;
    }

   public static void generarExcel(List<ReporteVentas> reportes, String filePath, String titulo, String responsable, Date fechaInicio, Date fechaFin) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reporte de Ventas");
            // Calcular total
            float total = 0;
            for (ReporteVentas rp : reportes) {
                total += rp.getMontoTotal();
            }
            // Formatear fechas (dd-MM-yyyy)
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String strFechaInicio = sdf.format(fechaInicio);
            String strFechaFin = sdf.format(fechaFin);
            // Crear estilo para moneda
            CellStyle currencyStyle = workbook.createCellStyle();
            currencyStyle.setDataFormat(workbook.createDataFormat().getFormat("$#,##0.00"));
            // Estilos para encabezados
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
             org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            // Estilo para información de responsable
            CellStyle infoStyle = workbook.createCellStyle();
             org.apache.poi.ss.usermodel.Font infoFont = workbook.createFont();
            infoFont.setBold(true);
            infoStyle.setFont(infoFont);
            // Estilo para título
            CellStyle titleStyle = workbook.createCellStyle();
             org.apache.poi.ss.usermodel.Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 14);
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            // Encabezado con datos del responsable
            int currentRow = 0;
            Row responsableRow = sheet.createRow(currentRow++);
            responsableRow.createCell(0).setCellValue("Responsable:");
            responsableRow.getCell(0).setCellStyle(infoStyle);
            responsableRow.createCell(1).setCellValue(responsable);
            Row periodoRow = sheet.createRow(currentRow++);
            periodoRow.createCell(0).setCellValue("Periodo:");
            periodoRow.getCell(0).setCellStyle(infoStyle);
            periodoRow.createCell(1).setCellValue(strFechaInicio + " - " + strFechaFin);
            // Título (centrado y con merge)
            Row titleRow = sheet.createRow(currentRow++);
            titleRow.createCell(0).setCellValue(titulo);
            titleRow.getCell(0).setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), 0, 5));
            // Encabezados de tabla
            Row headerRow = sheet.createRow(currentRow++);
            String[] headers = {
                "ID Platillo", "Platillo", "Categoría",
                "Precio Unitario", "Cantidad Vendida",
                "Monto Total"
            };
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            // Datos
            for (ReporteVentas reporte : reportes) {
                Row row = sheet.createRow(currentRow++);
                
                row.createCell(0).setCellValue(reporte.getPlatilloId());
                row.createCell(1).setCellValue(reporte.getPlatilloNombre());
                row.createCell(2).setCellValue(reporte.getCategoriaNombre());
                
                Cell precioCell = row.createCell(3);
                precioCell.setCellValue(reporte.getPrecioVenta());
                precioCell.setCellStyle(currencyStyle);
                
                row.createCell(4).setCellValue(reporte.getCantidadVendida());
                
                Cell montoCell = row.createCell(5);
                montoCell.setCellValue(reporte.getMontoTotal());
                montoCell.setCellStyle(currencyStyle);
            }
            // Fila de total
            Row totalRow = sheet.createRow(currentRow);
            totalRow.createCell(4).setCellValue("TOTAL:");
            totalRow.getCell(4).setCellStyle(infoStyle);
            Cell totalValueCell = totalRow.createCell(5);
            totalValueCell.setCellValue(total);
            totalValueCell.setCellStyle(currencyStyle);
            // Autoajustar columnas
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            // Guardar archivo
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }
        }
    }
}