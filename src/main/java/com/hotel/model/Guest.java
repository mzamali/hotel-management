package com.hotel.model;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "guests")

public  class Guest {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private  int id;
    @Column(nullable = false)
    private  String name;
     
    @Column(unique = true)
    private String phone;
    
    @Column(unique = true)
    private String email;
    
    @Column(name = "id_number", unique = true)
    private String idNumber;
    
    private String address;

    @OneToMany(mappedBy="guest",cascade=CascadeType.ALL)
    private List<Booking> bookings;

    public Guest () {

    }

    public Guest(String name, String phone, String email, String idNumber, String address) {
        
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.idNumber = idNumber;
        this.address = address;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
    

    @Override
    public String toString() {
        return "Guest [id=" + id + ", name=" + name + ", phone=" + phone + ", email=" + email + ", idNumber=" + idNumber
                + ", address=" + address + "]";
    }


    
}

