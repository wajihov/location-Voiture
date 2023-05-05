package fr.gopartner.locationvoiture.domain.car;

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
    private int gearbox;
    private Long price;
    private String color;
    private String image;
    private int numberOfAvailableCars;

    @OneToMany(mappedBy = "car")
    private List<Reservation> reservations = new ArrayList<>();
}
