package com.hotel.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.hotel.*;
import com.hotel.controller.AdminController;

import javax.swing.JTabbedPane;

public class AdminView  extends  JFrame{
    AdminController controller;
    public AdminView() {
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
         setTitle("Mzamali Hotel - Admin Panel");

        JPanel mainFrame = new JPanel(new BorderLayout());
        mainFrame.setBackground(Color.WHITE);
        add(mainFrame);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0,100,200));
        headerPanel.setPreferredSize(new Dimension(0,100));

        JPanel holder = new JPanel(new FlowLayout());
        holder.setBackground(new Color(0, 100, 200));


        JLabel title = new JLabel("MZAMALI HOTEL");
        title.setForeground(Color.BLACK);
        title.setFont(new Font("Arial",Font.BOLD,25));

        JLabel welcome = new JLabel("WELCOME ALL");
        welcome.setFont(new Font("sans Serif",Font.PLAIN,22));


        JTabbedPane pane = new JTabbedPane();
        pane.setFont(new Font("SansSerif", Font.PLAIN, 13));
        pane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));


       

        pane.addTab("Rooms", new RoomPanel());
        pane.addTab("Guests", new GuestPanel());
        pane.addTab("Bookings",new BookingPanel());
        pane.addTab("Reports",new  ReportPanel());
        mainFrame.add(pane,BorderLayout.CENTER);
        holder.add(title,FlowLayout.LEFT);
        
        

        headerPanel.add(holder,BorderLayout.WEST);
        headerPanel.add(welcome,BorderLayout.CENTER);


        

        mainFrame.add(headerPanel,BorderLayout.NORTH);

  
        setVisible(true);
    }
    public static JButton setButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(27, 58, 107));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return btn;

    }
    public static void setTable(JTable table) {
        table.setRowHeight(24);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(27, 58, 107));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(173, 214, 255));
        table.setGridColor(new Color(220, 220, 220));
        table.setShowGrid(true);

    }
     public static JTextField searchField(String placeholder) {
        JTextField tf = new JTextField(placeholder, 20);
        tf.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tf.setForeground(Color.GRAY);
        tf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (tf.getText().equals(placeholder)) { tf.setText(""); tf.setForeground(Color.BLACK); }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (tf.getText().isEmpty()) { tf.setText(placeholder); tf.setForeground(Color.GRAY); }
            }
        });
        return tf;
    }
   
   
    public static void main(String[] args) {
        new AdminView();
    }
    
}
