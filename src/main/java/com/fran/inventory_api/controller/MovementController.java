package com.fran.inventory_api.controller;

import com.fran.inventory_api.dto.MovementResponse;
import com.fran.inventory_api.entity.Movement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fran.inventory_api.service.MovementService;
import com.fran.inventory_api.service.Impl.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock-movements")
public class MovementController {

    private final MovementService movementService;
    private final ReportService reportService;

    public MovementController(MovementService movementService, ReportService reportService) {
        this.movementService = movementService;
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<List<MovementResponse>> getAllMovements() {
        return ResponseEntity.ok(movementService.getAllMovements());
    }

    @GetMapping("/entries")
    public ResponseEntity<List<MovementResponse>> getEntries() {
        return ResponseEntity.ok(movementService.getEntries());
    }

    @GetMapping("/outputs")
    public ResponseEntity<List<MovementResponse>> getOutputs() {
        return ResponseEntity.ok(movementService.getOutputs());
    }

    @PostMapping
    public ResponseEntity<Movement> createMovement(@RequestBody Movement movement) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movementService.createMovement(movement));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAllMovements() {
        movementService.deleteAllMovements();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMovement(@PathVariable Long id) {
        movementService.deleteMovement(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/entries")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAllEntries() {
        movementService.deleteAllEntries();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/outputs")
    public ResponseEntity<Void> deleteAllOutputs() {
        movementService.deleteAllOutputs();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/report/{type}")
    public ResponseEntity<byte[]> generateReport(@PathVariable String type) {
        // type = "all" o "entry" o "exit"
        try {
            byte[] pdfBytes;
            switch (type.toLowerCase()) {
                case "all":
                    pdfBytes = reportService.generateMovementsReport(movementService.getAllMovements(), type.toLowerCase());
                    break;
                case "entry":
                    pdfBytes = reportService.generateMovementsReport(movementService.getEntries(), type.toLowerCase());
                    break;
                case "exit":
                    pdfBytes = reportService.generateMovementsReport(movementService.getOutputs(), type.toLowerCase());
                    break;
                default:
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);

            String filename = "stock_movements_report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".pdf";
            headers.setContentDispositionFormData("filename", filename);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (
                Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
