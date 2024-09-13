package uz.ccrew.flightmanagement.repository;

import uz.ccrew.flightmanagement.entity.RefCalendar;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface RefCalendarRepository extends JpaRepository<RefCalendar, LocalDate> {
}
