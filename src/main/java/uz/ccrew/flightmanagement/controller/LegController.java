package uz.ccrew.flightmanagement.controller;

import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.leg.LegDTO;
import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.service.LegService;
import uz.ccrew.flightmanagement.dto.leg.LegCreateDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/leg")
@RequiredArgsConstructor
@Tag(name = "Leg Controller", description = "Leg API")
@SecurityRequirement(name = "Bearer Authentication")
public class LegController {
    private final LegService legService;

    @PostMapping("/add")
    @Operation(summary = "Add flight leg")
    public ResponseEntity<Response<LegDTO>> add(@RequestBody @Valid LegCreateDTO dto) {
        LegDTO result = legService.add(dto);
        return ResponseMaker.ok(result);
    }
}
