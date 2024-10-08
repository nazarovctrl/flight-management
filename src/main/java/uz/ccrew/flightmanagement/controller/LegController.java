package uz.ccrew.flightmanagement.controller;

import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.leg.LegDTO;
import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.service.LegService;
import uz.ccrew.flightmanagement.dto.leg.LegUpdateDTO;
import uz.ccrew.flightmanagement.dto.leg.LegCreateDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/leg")
@RequiredArgsConstructor
@Tag(name = "Leg Controller", description = "Leg API")
@SecurityRequirement(name = "Bearer Authentication")
public class LegController {
    private final LegService legService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Add flight leg")
    public ResponseEntity<Response<LegDTO>> add(@RequestBody @Valid LegCreateDTO dto) {
        LegDTO result = legService.add(dto);
        return ResponseMaker.ok(result);
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Update flight leg")
    public ResponseEntity<Response<LegDTO>> update(@PathVariable("id") Long id, @RequestBody @Valid LegUpdateDTO dto) {
        LegDTO result = legService.update(id, dto);
        return ResponseMaker.ok(result);
    }
}
