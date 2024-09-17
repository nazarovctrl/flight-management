package uz.ccrew.flightmanagement.controller;

import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.service.RefCalendarService;
import uz.ccrew.flightmanagement.dto.refcalendar.RefCalendarDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/ref-calendar")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Ref Calendar Controller", description = "Ref Calendar API")
public class RefCalendarController {
    private final RefCalendarService refCalendarService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Save Ref Calendar")
    public ResponseEntity<Response<RefCalendarDTO>> save(@RequestBody @Valid RefCalendarDTO dto) {
        RefCalendarDTO result = refCalendarService.save(dto);
        return ResponseMaker.ok(result);
    }
}
