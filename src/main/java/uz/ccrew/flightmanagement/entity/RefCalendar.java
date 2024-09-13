package uz.ccrew.flightmanagement.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "ref_calendars")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RefCalendar {
    @Id
    private LocalDate dayDate;
    @Column
    private Integer dayNumber;
    @Column
    private Boolean businessDayYn;
}
