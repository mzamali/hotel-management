package com.hotel.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import com.hotel.controller.AdminController;

public class RoomPanel  extends JPanel{
    public DefaultTableModel tableModel;
    public JTable table;
    public AdminController controller;
    public JTextField search;
    
    public RoomPanel() {
        controller = new AdminController(this);
        setLayout(new BorderLayout(8, 15));
        setBackground(Color.WHITE);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBorder(BorderFactory.createEmptyBorder(10,60,10,20));

        search = new JTextField(28);
        search.setFont(new Font("Sans Serif" ,Font.PLAIN,15) {
        });
        

        
        JButton addButton =AdminView.setButton("AddRoom");
        JButton delete =AdminView.setButton("DeleteRoom");
        JButton update =AdminView.setButton("UpdateRoom");
        JButton searchbtn =AdminView.setButton("SearchRoom");
        JButton exportButton = AdminView.setButton("Export to Excel");

        exportButton.addActionListener(e -> {
            controller.exportToFile();
        });

        addButton.addActionListener( e -> {
            controller.saveRoom();
        });
        delete.addActionListener(e -> {
            controller.deleteRoom();
        });
        search.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                controller.filter(search.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                controller.filter(search.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                controller.filter(search.getText());
            }

            
        });
       

        top.add(new JLabel("Search"));
        top.add(search);
        top.add(addButton);
        top.add(delete);
        top.add(update);
        top.add(searchbtn);
        top.add(exportButton);

        String[] cols = {"Id","RoomNumber","RoomType","Price","Status"};
        tableModel = new DefaultTableModel(cols,0) {
            @Override
            public  boolean isCellEditable(int r,int c) {
                return false;
            }
        };

        JTextArea area = new JTextArea();
        area.setText("ANY SEARCH RESULT WILL APPEAR HERE");
        area.setBorder(BorderFactory.createTitledBorder("Search display here"));
        area.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        area.setPreferredSize(new Dimension(300,120));
        area.setEditable(false);

        table = new JTable(tableModel);


        AdminView.setTable(table);
        JScrollPane pane = new JScrollPane(table);


        add(pane,BorderLayout.CENTER);
        add(area,BorderLayout.SOUTH);

        


        add(top,BorderLayout.NORTH);

        controller.load();
        


    }
    public void displayRooms(java.util.List<com.hotel.model.Room> rooms) {
        tableModel.setRowCount(0); 
        
        if (rooms == null || rooms.isEmpty()) {
            return;
        }
        
        for (com.hotel.model.Room room : rooms) {
            tableModel.addRow(new Object[]{
                room.getId(),
                room.getRoomNumber(),
                room.getRoomType(),
                room.getPrice(),
                room.getStatus()
            });
        }
    }
     public void setController(AdminController controller) {
        this.controller = controller;
    }
    
    public void refreshRoomList() {
        if (controller != null) {
            controller.load();
        }
    }
}
   

    

