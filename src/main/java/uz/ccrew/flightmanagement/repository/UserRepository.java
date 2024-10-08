package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.User;

import java.util.Optional;

public interface UserRepository extends BasicRepository<User, Long> {
    Optional<User> findByLogin(String login);
}