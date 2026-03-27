package com.hotel.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.hotel.controller.CustomerController;
import com.hotel.model.Room;

public class CustomerView extends JFrame {

    public CustomerController controller;
    public JPanel roomsPanel;
    private JTextField checkInField;
    private JTextField checkOutField;
    private JComboBox<Integer> guestsCombo;
    private JTextArea resultArea;
    private JButton searchBtn;
    
    public CustomerView() {
        setTitle("Hotel Booking System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(2, 86, 153));
        headerPanel.setPreferredSize(new Dimension(900,60));

        JLabel hotelName = new JLabel("🏨 HAVEN HOTEL");
        hotelName.setFont(new Font("Segoe UI", Font.BOLD, 24));
        hotelName.setForeground(Color.WHITE);
        hotelName.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));

        JLabel adminLink = new JLabel("Admin Login");
        adminLink.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        adminLink.setForeground(new Color(200, 200, 200));
        adminLink.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        adminLink.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        centralPanel.setBackground(Color.WHITE);
        

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(5,50,5,50));

        checkOutField = new JTextField(10);
        checkOutField.setText("2026-03-20");
        checkOutField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        JLabel searchTitle = new JLabel("Find Your Room");
        searchTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        searchTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel formRow = new JPanel(new FlowLayout(FlowLayout.CENTER,15,0));
        formRow.setBackground(Color.WHITE);

        checkInField = new JTextField(10);
        checkInField.setText("2026-03-15");
        checkInField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200),1));

        Integer [] guestOption = {1,2,3,4,5,6,7};
        guestsCombo = new JComboBox<>(guestOption);
        guestsCombo.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        searchBtn = new JButton("SEARCH");
        searchBtn.setBackground(new Color(46, 204, 113));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        searchBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        roomsPanel = new JPanel();
        roomsPanel.setLayout(new BoxLayout(roomsPanel, BoxLayout.Y_AXIS));
        roomsPanel.setBackground(Color.WHITE);
        roomsPanel.setBorder(BorderFactory.createEmptyBorder(5, 50, 30, 50));
        
        JScrollPane roomsScrollPane = new JScrollPane(roomsPanel);
        roomsScrollPane.setBorder(null);
        roomsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        roomsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        roomsScrollPane.setPreferredSize(new Dimension(900, 250)); 
        roomsScrollPane.setMaximumSize(new Dimension(900, 250));

        JLabel roomsTitle = new JLabel("Available Rooms");
        roomsTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        roomsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        roomsPanel.add(roomsTitle);
        roomsPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBackground(Color.WHITE);
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 20, 50));

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setBackground(new Color(245, 245, 245));
        resultArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JScrollPane resultScroll = new JScrollPane(resultArea);
        resultScroll.setPreferredSize(new Dimension(800, 80));
        resultScroll.setMaximumSize(new Dimension(800,80));
        resultsPanel.add(resultScroll, BorderLayout.CENTER);

        centralPanel.add(searchPanel);
        centralPanel.add(roomsScrollPane);
       
        formRow.add(new JLabel("📅 Check-in:"));
        formRow.add(checkInField);
        formRow.add(new JLabel("📅 Check-out:"));
        formRow.add(checkOutField);
        formRow.add(new JLabel("👥 Guests:"));
        formRow.add(guestsCombo);
        formRow.add(searchBtn);

        searchPanel.add(searchTitle);
        
        searchPanel.add(formRow);

        headerPanel.add(hotelName, BorderLayout.WEST);
        headerPanel.add(adminLink, BorderLayout.EAST);
        mainPanel.add(centralPanel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(resultsPanel, BorderLayout.SOUTH);
    
        add(mainPanel);
       
        searchBtn.addActionListener(e -> {
            if (controller != null) {
                controller.searchAvailableRooms();
            } else {
                System.out.println("Controller is null!");
            }
        });
        setVisible(true);
   }
    
    public void setController(CustomerController controller) {
        this.controller = controller;
    }
    
    public void displayRooms(List<Room> rooms) {
        roomsPanel.removeAll();

        if(rooms == null || rooms.isEmpty()) {
            JLabel noRooms = new JLabel("No room Available at the moment");
            noRooms.setAlignmentX(Component.CENTER_ALIGNMENT);
            roomsPanel.add(noRooms);
        } else {
            for(Room s : rooms) {
                JPanel roomCard = new JPanel(new BorderLayout());
                roomCard.setBackground(Color.WHITE);
                roomCard.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
                roomCard.setMaximumSize(new Dimension(800, 60));
                roomCard.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel roomInfo = new JLabel("Room " + s.getRoomNumber() + " - " + 
                    s.getRoomType() + " - $ " + s.getPrice() + " /night");
                roomInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));

                JButton bookBtn = new JButton("BOOK");
                bookBtn.setBackground(new Color(52, 152, 219));
                bookBtn.setForeground(Color.WHITE);
                bookBtn.setFocusPainted(false);

                bookBtn.putClientProperty("roomId", s.getId());
                bookBtn.putClientProperty("roomNumber", s.getRoomNumber());
                bookBtn.putClientProperty("roomType", s.getRoomType());
                bookBtn.putClientProperty("price", s.getPrice());

                bookBtn.addActionListener(e -> {
                    System.out.println("Book button clicked for room: " + 
                        bookBtn.getClientProperty("roomNumber"));
                });

                roomCard.add(roomInfo, BorderLayout.CENTER);
                roomCard.add(bookBtn, BorderLayout.EAST);

                roomsPanel.add(roomCard);
                roomsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        roomsPanel.revalidate();
        roomsPanel.repaint();
    }
    
    public String getCheckInDate() {
        return checkInField.getText();
    }
    
    public String getCheckOutDate() {
        return checkOutField.getText();
    }
    
    public int getGuests() {
        return (int) guestsCombo.getSelectedItem();
    }
    
    public JTextArea getResultArea() {
        return resultArea;
    }
    
    public JButton getSearchBtn() {
        return searchBtn;
    }
    
    public static void main(String[] args) {
        CustomerView view = new CustomerView();
        CustomerController controller = new CustomerController(view);
        view.setController(controller);
        controller.loadAllAvailableRooms();
    }
}