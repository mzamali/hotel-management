package com.hotel.controller;

import com.hotel.model.Room;
import com.hotel.dao.RoomDAO;
import com.hotel.view.CustomerView;
import javax.swing.*;
import java.util.List;

public class CustomerController {
    
    private CustomerView view;
    private RoomDAO roomDAO;
    
    public CustomerController(CustomerView view) {
        this.view = view;
        this.roomDAO = new RoomDAO();
    }
       public void loadAllAvailableRooms() {
        List<Room> rooms = roomDAO.getAvailableRooms();
        view.displayRooms(rooms);
        
        JTextArea area = view.getResultArea();
        area.setText("");
        if (rooms.isEmpty()) {
            area.append(" No rooms available at the moment.\n");
        } else {
            area.append("Showing " + rooms.size() + " available rooms.\n");
        }
    }
    public void searchAvailableRooms() {

         JTextArea displayArea = view.getResultArea();
         displayArea.setText("");
            String checkIn = view.getCheckInDate();
            String checkOut = view.getCheckOutDate();
            int guests = view.getGuests();

          if (checkIn.isEmpty() || checkOut.isEmpty()) {
            displayArea.append("Please enter dates\n");
            return;
        }
         List<Room> rooms = roomDAO.getAvailableRooms();

         if(rooms.isEmpty()) {
            displayArea.append("no room available\n ");

         }else{
            displayArea.append("Found " + rooms.size() + " rooms\n");
         }
        
           

           

          
        
    }
    

    
    
}