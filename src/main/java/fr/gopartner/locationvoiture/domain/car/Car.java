package fr.gopartner.locationvoiture.domain.car;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.gopartner.locationvoiture.domain.reservation.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mark;
    private String reference;
    private int numberOfDoors;
    private int numberOfPlace;
    private int numberOfHorses;
    private String gearbox;
    private Long price;
    private String color;
    private String image;
    private int numberOfAvailableCars;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();
}
