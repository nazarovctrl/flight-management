package uz.ccrew.flightmanagement.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "airports")
public class Airport {
    @Id
    private String airportCode;
    @Column
    private String airportName;
    @Column
    private String airportLocation;
    @Column
    private String otherDetails;
}
