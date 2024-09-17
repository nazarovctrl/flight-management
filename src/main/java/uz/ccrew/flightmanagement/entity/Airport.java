package uz.ccrew.flightmanagement.entity;

import lombok.Getter;
import lombok.Builder;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@Table(name = "airports")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Airport extends Auditable {
    @Id
    private String airportCode;
    @Column(nullable = false, unique = true)
    private String airportName;
    @Column(nullable = false)
    private String airportLocation;
    @Column
    private String otherDetails;
    @Column(nullable = false)
    private String city;
}
