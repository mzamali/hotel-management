package com.hotel.model;
import javax.persistence.*;

import org.hibernate.annotations.ManyToAny;
import java.util.*;

@Entity
@Table(name="bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private  int id;

    @ManyToOne
    @JoinColumn(name="guest_id",nullable=false)
    private Guest guest;

    
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
    
    @Column(name = "check_in", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date checkIn;
    
    @Column(name = "check_out", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date checkOut;
    
    @Column(name = "total_amount")
    private double totalAmount;
    
    @Column(nullable = false)
    private String status;

    public Booking() {
        
    }
  public Booking(Guest guest, Room room, Date checkIn, Date checkOut, String status) {
        this.guest = guest;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
        
    }
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public Guest getGuest() {
    return guest;
  }
  public void setGuest(Guest guest) {
    this.guest = guest;
  }
  public Room getRoom() {
    return room;
  }
  public void setRoom(Room room) {
    this.room = room;
  }
  public Date getCheckIn() {
    return checkIn;
  }
  public void setCheckIn(Date checkIn) {
    this.checkIn = checkIn;
  }
  public Date getCheckOut() {
    return checkOut;
  }
  public void setCheckOut(Date checkOut) {
    this.checkOut = checkOut;
  }
  public double getTotalAmount() {
    return totalAmount;
  }
  public void setTotalAmount(double totalAmount) {
    this.totalAmount = totalAmount;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  @Override
  public String toString() {
    return "Booking [id=" + id + ", guest=" + guest + ", room=" + room + ", checkIn=" + checkIn + ", checkOut="
            + checkOut + ", totalAmount=" + totalAmount + ", status=" + status + "]";
  }
    
    
    
}
