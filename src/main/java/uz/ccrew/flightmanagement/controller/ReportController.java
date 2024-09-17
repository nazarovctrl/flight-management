package uz.ccrew.flightmanagement.controller;

import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.service.ReportService;
import uz.ccrew.flightmanagement.dto.passenger.PassengerDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
@Tag(name = "Report Controller", description = "Report API")
@SecurityRequirement(name = "Bearer Authentication")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/flight-passengers/{flightNumber}")
    @Operation(summary = "Get all customers who have seats reserved on a given flight.")
    public ResponseEntity<Response<Page<PassengerDTO>>> findReservedSeats(@PathVariable("flightNumber") String flightNumber,
                                                                          @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                          @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Page<PassengerDTO> result = reportService.findPassengersWithReservedSeatsOnFlight(flightNumber, page, size);
        return ResponseMaker.ok(result);
    }

    @GetMapping("/total-sales/{flightNumber}")
    @Operation(summary = "Calculation of total sales for given flight")
    public ResponseEntity<Response<Long>> getTotalSalesForFlight(@PathVariable("flightNumber") Long flightNumber) {
        Long result = reportService.calculateTotalSalesByFlightNumber(flightNumber);
        return ResponseMaker.ok(result);
    }
}
