package uz.ccrew.flightmanagement.controller;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/passenger")
@RequiredArgsConstructor
@Tag(name = "Passenger Controller", description = "Passenger API")
@SecurityRequirement(name = "Bearer Authentication")
public class PassengerController {}
