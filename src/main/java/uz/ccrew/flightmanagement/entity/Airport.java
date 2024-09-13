package uz.ccrew.flightmanagement.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "airports")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Airport {
    @Id
    private String airportCode;
    @Column(nullable = false)
    private String airportName;
    @Column(nullable = false)
    private String airportLocation;
    @Column
    private String otherDetails;
}
