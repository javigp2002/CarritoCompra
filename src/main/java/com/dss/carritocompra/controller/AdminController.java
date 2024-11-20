package com.dss.carritocompra.controller;

import com.dss.carritocompra.services.DatabaseExportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {
    private final DatabaseExportService databaseExportService;


    @GetMapping("/export")
    public ResponseEntity<byte[]> exportDatabase() {
        byte[] sqlScript = databaseExportService.exportDatabaseToSql();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=database.sql");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");

        return new ResponseEntity<>(sqlScript, headers, HttpStatus.OK);
    }
}
