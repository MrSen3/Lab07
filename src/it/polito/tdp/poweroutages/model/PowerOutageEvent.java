package it.polito.tdp.poweroutages.model;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class PowerOutageEvent {
	
	private int id;
	private Nerc nerc;
	private int eventTypeId;
	private LocalDateTime dateEventBegan;
	private LocalDateTime dateEventEnd;
	private int customersAffected;
//	private int diffDate;//Differenza tra la data di fine e quella di inizio, fatta calcolare al db
//	private LocalDateTime diffTime;//Differenza tra il tempo di fine e quello di inizio, // // //
	
	private long diffTime;
	private int year;
	
	
	public PowerOutageEvent(int id, Nerc nerc, int eventTypeId, LocalDateTime dateEventBegan, LocalDateTime dateEventEnd, int customersAffected) {
	
//	public PowerOutageEvent(int id, Nerc nerc, int eventTypeId, LocalDateTime dateEventBegan, LocalDateTime dateEventEnd, int customersAffected, int diffDate, LocalDateTime diffTime) {
		this.id = id;
		this.nerc = nerc;
		this.eventTypeId = eventTypeId;
		this.dateEventBegan = dateEventBegan;
		this.dateEventEnd = dateEventEnd;
		this.customersAffected = customersAffected;
//		this.diffDate=diffDate;
//		this.diffTime=diffTime;
		this.diffTime = dateEventBegan.until(dateEventEnd, ChronoUnit.HOURS);
		
		this.year = dateEventBegan.getYear();
}


	

	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public Nerc getNerc() {
		return nerc;
	}




	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}




	public int getEventTypeId() {
		return eventTypeId;
	}




	public void setEventTypeId(int eventTypeId) {
		this.eventTypeId = eventTypeId;
	}




	public LocalDateTime getDateEventBegan() {
		return dateEventBegan;
	}




	public void setDateEventBegan(LocalDateTime dateEventBegan) {
		this.dateEventBegan = dateEventBegan;
	}




	public LocalDateTime getDateEventEnd() {
		return dateEventEnd;
	}




	public void setDateEventEnd(LocalDateTime dateEventEnd) {
		this.dateEventEnd = dateEventEnd;
	}




	public int getCustomersAffected() {
		return customersAffected;
	}




	public void setCustomersAffected(int customersAffected) {
		this.customersAffected = customersAffected;
	}


	public long getDiffTime() {
		return diffTime;
	}



	public int getYear() {
		return year;
	}




	public void setYear(int year) {
		this.year = year;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutageEvent other = (PowerOutageEvent) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(id);
		return builder.toString();
	}
	
	
	

}
