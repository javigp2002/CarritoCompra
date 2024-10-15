package com.dss.carritocompra.controller;

import com.dss.carritocompra.services.DatabaseExportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller()
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {
    private final DatabaseExportService databaseExportService;


    @GetMapping("/export")
    public String exportDatabase() {
        databaseExportService.exportDatabaseToSql();

        return "redirect:/admin";
    }
}
