package edu.sjsu.cmpe172.starterdemo.controller;

import edu.sjsu.cmpe172.starterdemo.model.Reservation;
import edu.sjsu.cmpe172.starterdemo.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> getReservations() {
        return reservationService.getAllReservations();
    }

    @PostMapping
    public String reserve(@RequestBody Map<String, Long> body) {
        Long customerUserId = body.get("customerUserId");
        Long classId = body.get("classId");

        return reservationService.makeReservation(customerUserId, classId);
    }

    @GetMapping("/customer/{customerUserId}")
    public List<Reservation> getReservationsByCustomer(@PathVariable Long customerUserId) {
        return reservationService.getReservationsByCustomer(customerUserId);
    }

    @PostMapping("/cancel")
    public String cancelReservation(@RequestBody Map<String, Long> body) {
        Long reservationId = body.get("reservationId");
        Long customerUserId = body.get("customerUserId");

        return reservationService.cancelReservation(reservationId, customerUserId);
    }
}
