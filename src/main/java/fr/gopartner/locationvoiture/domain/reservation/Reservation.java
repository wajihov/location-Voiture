package fr.gopartner.locationvoiture.domain.reservation;

import fr.gopartner.locationvoiture.domain.car.Car;
import fr.gopartner.locationvoiture.domain.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String careAndSupport;
    private String discount;
    private LocalDate intakeOf;
    private LocalDate socketTo;
    private LocalDate restitutionOf;
    private LocalDate restitutionTo;
    private String status;
    private LocalDate dateReservation;

    @ManyToOne
    private Car car;
    @ManyToOne
    private Customer customer;

}
