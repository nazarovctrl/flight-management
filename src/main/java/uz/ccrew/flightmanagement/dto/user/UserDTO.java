package uz.ccrew.flightmanagement.dto.user;

import uz.ccrew.flightmanagement.enums.UserRole;

import lombok.Builder;

@Builder
public record UserDTO(Long id, String login, UserRole role) {
}