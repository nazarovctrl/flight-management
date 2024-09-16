package uz.ccrew.flightmanagement.entity;

import uz.ccrew.flightmanagement.enums.AircraftTypeCode;
import uz.ccrew.flightmanagement.enums.TravelClassCode;

import lombok.Getter;
import lombok.Builder;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Objects;
import java.io.Serializable;

@Entity
@Table(name = "travel_class_capacity")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelClassCapacity {
    @EmbeddedId
    private TravelClassCapacityId id;
    @Column(nullable = false)
    private Integer seatCapacity;


    @Embeddable
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TravelClassCapacityId implements Serializable {
        @Enumerated(EnumType.STRING)
        private AircraftTypeCode aircraftTypeCode;
        @Enumerated(EnumType.STRING)
        private TravelClassCode travelClassCode;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TravelClassCapacityId that = (TravelClassCapacityId) o;
            return Objects.equals(aircraftTypeCode, that.aircraftTypeCode) && Objects.equals(travelClassCode, that.travelClassCode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(aircraftTypeCode, travelClassCode);
        }
    }
}
