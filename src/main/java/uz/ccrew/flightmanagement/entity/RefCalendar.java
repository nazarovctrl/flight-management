package uz.ccrew.flightmanagement.entity;

import lombok.Getter;
import lombok.Builder;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@Table(name = "ref_calendars")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefCalendar extends Auditable {
    @Id
    private LocalDate dayDate;
    @Column
    private Integer dayNumber;
    @Column
    private Boolean businessDayYn;
}
