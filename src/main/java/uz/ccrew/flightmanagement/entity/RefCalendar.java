package uz.ccrew.flightmanagement.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@Table(name = "ref_calendars")
public class RefCalendar {
    @Id
    private LocalDate dayDate;
    @Column
    private Integer dayNumber;
    @Column
    private Boolean businessDayYn;
}
