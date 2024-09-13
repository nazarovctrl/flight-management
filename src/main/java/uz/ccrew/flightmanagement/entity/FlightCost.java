package uz.ccrew.flightmanagement.entity;

import uz.ccrew.flightmanagement.enums.AircraftTypeCode;

import lombok.Builder;
import lombok.Getter;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Objects;
import java.time.LocalDate;
import java.io.Serializable;

@Entity
@Table(name = "flight_costs")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FlightCost {
    @EmbeddedId
    private FlightCostsId id;

    @ManyToOne
    @MapsId("flightNumber")
    @JoinColumn(name = "flight_number", foreignKey = @ForeignKey(name = "flight_costs_f1"), nullable = false)
    private FlightSchedule flightSchedule;
    @ManyToOne
    @MapsId("validFromDate")
    @JoinColumn(name = "valid_from_date", foreignKey = @ForeignKey(name = "flight_costs_f2"), nullable = false)
    private RefCalendar validFromRefCalendar;

    @Column(name = "valid_to_date")
    private LocalDate validToDate;
    @ManyToOne
    @JoinColumn(name = "valid_to_date", foreignKey = @ForeignKey(name = "flight_costs_f3"), nullable = false, insertable = false, updatable = false)
    private RefCalendar validToRefCalendar;

    @Column
    private Long flightCost;


    @Embeddable
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FlightCostsId implements Serializable {
        private Long flightNumber;
        @Enumerated(EnumType.STRING)
        private AircraftTypeCode aircraftTypeCode;
        private LocalDate validFromDate;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FlightCostsId that = (FlightCostsId) o;
            return Objects.equals(flightNumber, that.flightNumber) &&
                    Objects.equals(aircraftTypeCode, that.aircraftTypeCode) &&
                    Objects.equals(validFromDate, that.validFromDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(flightNumber, aircraftTypeCode, validFromDate);
        }
    }
}
