package com.fran.inventory_api.system.controller;

import com.fran.inventory_api.system.dto.MovementResponse;
import com.fran.inventory_api.system.entity.Movement;
import com.fran.inventory_api.system.service.Impl.ReportService;
import com.fran.inventory_api.system.service.MovementService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        Movement createdMovement = movementService.createMovement(movement);
        return ResponseEntity.created(null).body(createdMovement);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<Void> deleteAllMovements() {
        movementService.deleteAllMovements();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<Void> deleteMovement(@PathVariable Long id) {
        movementService.deleteMovement(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/entries")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<Void> deleteAllEntries() {
        movementService.deleteAllEntries();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/outputs")
    public ResponseEntity<Void> deleteAllOutputs() {
        movementService.deleteAllOutputs();
        return ResponseEntity.noContent().build();
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
