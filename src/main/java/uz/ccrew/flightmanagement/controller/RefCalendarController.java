package uz.ccrew.flightmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.service.RefCalendarService;
import uz.ccrew.flightmanagement.dto.refcalendar.RefCalendarDTO;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ref-calendar")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Ref Calendar Controller", description = "Ref Calendar API")
public class RefCalendarController {
    private final RefCalendarService refCalendarService;

    @PostMapping("/save")
    @Operation(summary = "Save Ref Calendar")
    public ResponseEntity<Response<RefCalendarDTO>> save(@RequestBody @Valid RefCalendarDTO dto) {
        RefCalendarDTO result = refCalendarService.save(dto);
        return ResponseMaker.ok(result);
    }
}
