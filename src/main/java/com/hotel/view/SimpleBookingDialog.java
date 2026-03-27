package com.hotel.view;

import javax.swing.*;
import java.awt.*;

public class SimpleBookingDialog extends JDialog {
    
    private JTextField nameField;
    private JTextField phoneField;
    private boolean confirmed;
    
    public SimpleBookingDialog(JFrame parent) {
        super(parent, "New Booking", true);
        setSize(300, 200);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(3, 2, 5, 5));
        
        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);
        
        add(new JLabel("Phone:"));
        phoneField = new JTextField();
        add(phoneField);
        
        JButton okBtn = new JButton("OK");
        JButton cancelBtn = new JButton("Cancel");
        
        add(okBtn);
        add(cancelBtn);
        
        okBtn.addActionListener(e -> {
            confirmed = true;
            dispose();
        });
        
        cancelBtn.addActionListener(e -> dispose());
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public String getName() {
        return nameField.getText();
    }
    
    public String getPhone() {
        return phoneField.getText();
    }
}