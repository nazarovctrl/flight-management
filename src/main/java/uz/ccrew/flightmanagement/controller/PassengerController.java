package uz.ccrew.flightmanagement.controller;

import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.service.PassengerService;
import uz.ccrew.flightmanagement.dto.passenger.PassengerDTO;
import uz.ccrew.flightmanagement.dto.passenger.PassengerCreateDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/passenger")
@RequiredArgsConstructor
@Tag(name = "Passenger Controller", description = "Passenger API")
@SecurityRequirement(name = "Bearer Authentication")
public class PassengerController {
    private final PassengerService passengerService;

    @PostMapping("/add")
    public ResponseEntity<Response<PassengerDTO>> add(@RequestBody @Valid PassengerCreateDTO dto) {
        PassengerDTO result = passengerService.add(dto);
        return ResponseMaker.ok(result);
    }
}
