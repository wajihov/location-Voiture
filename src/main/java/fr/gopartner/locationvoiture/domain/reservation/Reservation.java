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
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private LocalDate dateReservation;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
