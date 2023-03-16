package com.ars_vc.model;

import java.util.Date;

import com.ars_vc.entity.Airline;
import com.ars_vc.entity.Flight;
import com.ars_vc.entity.Passenger;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TicketBookingDTO {
	
	private int ticketId;
	private int no_of_passenger;
	private double totalFare;
	private Date date=new Date();
	private Flight flightId;
	private Airline airlineId;
	private Passenger passengerId;

}
