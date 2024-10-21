package com.dss.carritocompra.services;

import com.dss.carritocompra.domain.ProductRepository;
import com.dss.carritocompra.entities.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@AllArgsConstructor
public class DatabaseExportService {

    private final ProductRepository productRepository;

    public byte[] exportDatabaseToSql(){
        List<Product> products = productRepository.findAll();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("-- Crear la base de datos;\n");
        sqlBuilder.append(" CREATE DATABASE IF NOT EXISTS ecommerce_bd;\n");
        sqlBuilder.append(" USE ecommerce_bd;\n");

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS products (\n");
        sqlBuilder.append("id INT PRIMARY KEY,\n");
        sqlBuilder.append("name VARCHAR(255),\n");
        sqlBuilder.append("price DECIMAL(10, 2)\n");
        sqlBuilder.append(");\n");

        sqlBuilder.append("INSERT INTO products (id, name, price) VALUES\n");

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            sqlBuilder.append(String.format("(%d, '%s', %.2f)", product.getId(), product.getName(), product.getPrice()));
            if (i < products.size() - 1) {
                sqlBuilder.append(",\n");
            } else {
                sqlBuilder.append(";\n");
            }
        }

        return sqlBuilder.toString().getBytes(StandardCharsets.UTF_8);
    }
}
