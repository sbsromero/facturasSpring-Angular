package com.example.demo.view.pdf;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.example.demo.models.entity.Factura;
import com.example.demo.models.entity.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("factura/ver-factura")
public class FacturaPdfView extends AbstractPdfView{
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LocaleResolver localeResolver;

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Factura factura = (Factura) model.get("factura");
		PdfPTable tablaCliente = new PdfPTable(1);
		tablaCliente.setSpacingAfter(20);
		PdfPCell celda = null;
		
		celda = new PdfPCell(new Phrase("Datos del cliente"));
		//Para aplicar multilenguaje
		Locale locale = localeResolver.resolveLocale(request);
		MessageSourceAccessor mensajes = getMessageSourceAccessor();
		celda = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.cliente", null, locale)));
		//mensajes.getMessage("text.factura.ver.datos.cliente");
		
		celda.setBackgroundColor(new Color(184,218,255));
		celda.setPadding(8f);
		tablaCliente.addCell(celda);
		
		tablaCliente.addCell(factura.getCliente().getNombre()+" "+factura.getCliente().getApellido());
		tablaCliente.addCell(factura.getCliente().getEmail());
		
		PdfPTable tablaFactura = new PdfPTable(1);
		tablaFactura.setSpacingAfter(20);
		
		celda = new PdfPCell(new Phrase("Datos de la factura"));
		celda.setBackgroundColor(new Color(195,230,203));
		celda.setPadding(8f);
		
		tablaFactura.addCell(celda);
		tablaFactura.addCell("Folio: "+factura.getId());
		tablaFactura.addCell("Descripción: "+factura.getDescripcion());
		tablaFactura.addCell("Fecha: "+factura.getCreateAt());
		
		document.add(tablaCliente);
		document.add(tablaFactura);
		
		PdfPTable tablaDetalleFactura = new PdfPTable(4);
		
		tablaDetalleFactura.setWidths(new float[] {3.5f, 1, 1, 1});
		tablaDetalleFactura.addCell("Producto");
		tablaDetalleFactura.addCell("Precio");
		tablaDetalleFactura.addCell("Cantidad");
		tablaDetalleFactura.addCell("Total");
		
		for(ItemFactura item: factura.getItemsFactura()) {
			tablaDetalleFactura.addCell(item.getProducto().getNombre());
			tablaDetalleFactura.addCell(item.getProducto().getPrecio().toString());
			
			celda = new PdfPCell(new Phrase(item.getCantidad().toString()));
			celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			
			tablaDetalleFactura.addCell(celda);
			tablaDetalleFactura.addCell(item.calcularImporte().toString());
		}
		
		PdfPCell celdaTotal = new PdfPCell(new Phrase("Total: "));
		celdaTotal.setColspan(3);
		celdaTotal.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		tablaDetalleFactura.addCell(celdaTotal);
		tablaDetalleFactura.addCell(factura.getTotal().toString());
		
		document.add(tablaDetalleFactura);
	}
	
}
