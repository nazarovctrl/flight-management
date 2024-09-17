package uz.ccrew.flightmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.service.ReportService;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
@Tag(name = "Report Controller", description = "Report API")
@SecurityRequirement(name = "Bearer Authentication")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/total-sales/{flightNumber}")
    @Operation(summary = "Calculation of total sales for given flight")
    public ResponseEntity<Response<Long>> getTotalSalesForFlight(@PathVariable("flightNumber") Long flightNumber) {
        Long result = reportService.calculateTotalSalesByFlightNumber(flightNumber);
        return ResponseMaker.ok(result);
    }
}
