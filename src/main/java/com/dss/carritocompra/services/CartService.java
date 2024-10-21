package com.dss.carritocompra.services;

import com.dss.carritocompra.domain.ProductRepository;
import com.dss.carritocompra.entities.Product;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

import com.itextpdf.text.pdf.PdfWriter;


import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CartService {

    private final ProductRepository productRepository;
    private final List<Product> productsOnCart = new ArrayList<>();

    public void addProduct(Product product) {
        if (productsOnCart.contains(product)) {
            throw new IllegalArgumentException("Product already on cart");
        }
        productsOnCart.add(product);
    }

    public void deleteProduct(Long id) {
        productsOnCart.removeIf(product -> product.getId().equals(id));
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        productsOnCart.removeIf(product -> !products.contains(product));

        return productsOnCart;
    }

    public byte[] exportCartProductsToPDF() throws DocumentException {
        Document ticket = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);


        PdfWriter.getInstance(ticket, outputStream);
        ticket.open();

        Paragraph titleParagraph = new Paragraph("Your Cart", titleFont);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        ticket.add(titleParagraph);

        Paragraph dateParagraph = new Paragraph("Date: " + java.time.LocalDate.now());
        dateParagraph.setAlignment(Element.ALIGN_RIGHT);
        ticket.add(dateParagraph);

        ticket.add(new Paragraph("\n\n"));

        PdfPTable table = new PdfPTable(4); // 4 columns

        table.addCell(new PdfPCell(new Paragraph("Product", boldFont)));
        table.addCell(new PdfPCell(new Paragraph("Quantity", boldFont)));
        table.addCell(new PdfPCell(new Paragraph("Name", boldFont)));
        table.addCell(new PdfPCell(new Paragraph("Price (€/u)", boldFont)));

        for (Product product : productsOnCart) {
            table.addCell(String.valueOf(product.getId()));
            table.addCell("1");
            table.addCell(product.getName());
            table.addCell(product.getPrice() + "€");
        }

        PdfPCell totalCell = new PdfPCell(new Paragraph("Total", boldFont));
        totalCell.setColspan(3);
        totalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(totalCell);
        table.addCell(productsOnCart.stream().mapToDouble(Product::getPrice).sum() + "€");

        ticket.add(table);
        ticket.close();
        return outputStream.toByteArray();
    }
}
