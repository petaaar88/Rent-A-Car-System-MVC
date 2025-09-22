package com.metropolitan.rent_a_car_system.dto;

import java.util.List;

public class AllReservationsDTO {
    public List<ReservationDTO> current;
    public List<ReservationDTO> past;

    public AllReservationsDTO() {}


    public AllReservationsDTO(List<ReservationDTO> current, List<ReservationDTO> past) {
        this.current = current;
        this.past = past;
    }

}
