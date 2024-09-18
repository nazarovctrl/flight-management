package uz.ccrew.flightmanagement.controller;

import uz.ccrew.flightmanagement.dto.Response;
import uz.ccrew.flightmanagement.dto.user.UserDTO;
import uz.ccrew.flightmanagement.dto.ResponseMaker;
import uz.ccrew.flightmanagement.service.UserService;
import uz.ccrew.flightmanagement.dto.user.UserUpdateDTO;

import org.springframework.data.domain.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/user")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "User Controller", description = "User API")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR','EMPLOYEE','CUSTOMER')")
    @Operation(summary = "Get user")
    public ResponseEntity<Response<UserDTO>> get() {
        UserDTO result = userService.get();
        return ResponseMaker.ok(result);
    }

    @GetMapping("/get/{userId}")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Get user by id for Administrator")
    public ResponseEntity<Response<UserDTO>> getById(@PathVariable(value = "userId") Long userId) {
        UserDTO result = userService.getById(userId);
        return ResponseMaker.ok(result);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR','EMPLOYEE','CUSTOMER')")
    @Operation(summary = "Update user")
    public ResponseEntity<Response<UserDTO>> update(@RequestBody UserUpdateDTO dto) {
        UserDTO result = userService.update(dto);
        return ResponseMaker.ok(result);
    }

    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Update user by id for Administrator")
    public ResponseEntity<Response<UserDTO>> updateById(@PathVariable("userId") Long userId, @RequestBody UserUpdateDTO dto) {
        UserDTO result = userService.updateById(userId, dto);
        return ResponseMaker.ok(result);
    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Delete user by id for Administrator")
    public ResponseEntity<Response<?>> deleteById(@PathVariable("userId") Long userId) {
        userService.deleteById(userId);
        return ResponseMaker.okMessage("User deleted");
    }

    @GetMapping("/get/list")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "User list for Administrator")
    public ResponseEntity<Response<Page<UserDTO>>> getList(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                           @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Page<UserDTO> result = userService.getList(page, size);
        return ResponseMaker.ok(result);
    }
}