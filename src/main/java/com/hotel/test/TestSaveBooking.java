package com.hotel.test;

import com.hotel.model.Booking;
import com.hotel.model.Guest;
import com.hotel.model.Room;
import com.hotel.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestSaveBooking {
    public static void main(String[] args) {
        System.out.println("=== Testing Booking Save ===");
        
        Session session = null;
        Transaction transaction = null;
        
        try {
            // Get session
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            
            // First, let's get an existing guest and room from database
            // We'll use the ones we just created in previous tests
            
            // Get first guest (ID = 1)
            Guest guest = session.get(Guest.class, 1);
            if (guest == null) {
                System.out.println("❌ No guest found! Please run TestSaveGuest first.");
                return;
            }
            
            // Get first room (ID = 1)
            Room room = session.get(Room.class, 1);
            if (room == null) {
                System.out.println("❌ No room found! Please run TestSaveRoom first.");
                return;
            }
            
            System.out.println("Found Guest: " + guest.getName());
            System.out.println("Found Room: " + room.getRoomNumber());
            
            // Create dates for booking (today + 7 days)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();
            
            // Check-in today, check-out in 7 days
            long sevenDays = 7 * 24 * 60 * 60 * 1000L; // 7 days in milliseconds
            Date checkOut = new Date(today.getTime() + sevenDays);
            
            // Calculate total amount (price * number of nights)
            double pricePerNight = room.getPrice();
            long diffInMillies = checkOut.getTime() - today.getTime();
            long nights = diffInMillies / (24 * 60 * 60 * 1000);
            double totalAmount = pricePerNight * nights;
            
            // Create new booking
            Booking booking = new Booking();
            booking.setGuest(guest);
            booking.setRoom(room);
            booking.setCheckIn(today);
            booking.setCheckOut(checkOut);
            booking.setTotalAmount(totalAmount);
            booking.setStatus("CONFIRMED");
            
            // Save the booking
            System.out.println("\nSaving booking for: " + guest.getName());
            System.out.println("Room: " + room.getRoomNumber());
            System.out.println("Check-in: " + sdf.format(today));
            System.out.println("Check-out: " + sdf.format(checkOut));
            System.out.println("Nights: " + nights);
            System.out.println("Total Amount: $" + totalAmount);
            
            session.save(booking);
            
            // Commit transaction
            transaction.commit();
            System.out.println("\n✅ Booking saved successfully!");
            System.out.println("   Booking ID: " + booking.getId());
            
            // Now let's verify the relationship works both ways
            System.out.println("\n=== Verifying Relationships ===");
            
            // Get the guest again and check their bookings
            Guest guestWithBookings = session.get(Guest.class, guest.getId());
            List<Booking> guestBookings = guestWithBookings.getBookings();
            System.out.println("Guest " + guestWithBookings.getName() + " has " + 
                             guestBookings.size() + " booking(s)");
            
            // Get the room and check its bookings
            Room roomWithBookings = session.get(Room.class, room.getId());
            // Note: We haven't added a @OneToMany in Room yet, but we can still query
            System.out.println("Room " + roomWithBookings.getRoomNumber() + " is booked");
            
        } catch (Exception e) {
            System.out.println("❌ Error saving booking!");
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        
        HibernateUtil.shutdown();
        System.out.println("\n=== Test Complete ===");
    }
}