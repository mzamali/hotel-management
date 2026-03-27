package com.hotel.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

 @Entity
@Table(name = "rooms")

public  class Room {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(name ="room_number",unique=true,nullable=false)
    private String room_number;
    @Column(name="room_type",nullable=false)
    private String room_type;
    @Column(name="price",nullable=false)
    private double price;
    @Column(nullable = false)
    private String status;

    public Room() {

    }
    

    public Room(String room_number, String room_type, double price, String status) {
        
        this.room_number = room_number;
        this.room_type = room_type;
        this.price = price;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return room_number;
    }

    public void setRoomNumber(String room_number) {
        this.room_number = room_number;
    }

    public String getRoomType() {
        return room_type;
    }

    public void setRoomType(String room_type) {
        this.room_type = room_type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Room [id=" + id + ", room_number=" + room_number + ", room_type=" + room_type + ", price=" + price
                + ", status=" + status + "]";
    }
      


   

}