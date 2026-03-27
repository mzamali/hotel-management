package com.hotel.controller;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hotel.dao.RoomDAO;
import com.hotel.model.Room;
import com.hotel.view.RoomPanel;

public class AdminController {
    private RoomPanel panel;
    private RoomDAO dao;
    private List<Room> allRooms;
    
    

    public AdminController(RoomPanel panel) {
        this.panel=panel;
        this.allRooms = new ArrayList<>();
       this.dao=new RoomDAO();

    }
    
    public void saveRoom() {

        JDialog dialog = new JDialog((JFrame) null,"Add new room",true );
        dialog.setSize(350, 250);
        dialog.setLocationRelativeTo(null);

        JTextField fIsbn = new JTextField(20);
        JTextField fTitle = new JTextField(30);
        JTextField fEdition = new JTextField(10);
        JTextField fVersion = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 2,6,6));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        panel.add(new JLabel("RoomNumber")); panel.add(fIsbn);
        panel.add(new JLabel("RoomType"));panel.add(fTitle);
        panel.add(new JLabel("Price")); panel.add(fEdition);
        panel.add(new JLabel("Status")); panel.add(fVersion);

        JPanel buttonPanel = new JPanel();
        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        

        dialog.setLayout(new BorderLayout());
        dialog.add(panel,BorderLayout.CENTER);
        dialog.add(buttonPanel,BorderLayout.SOUTH);

        

        btnSave.addActionListener(e -> {

        String roomNumber = fIsbn.getText().trim();
        String roomType=fTitle.getText().trim();
        String Price=fEdition.getText().trim();
        String status =fVersion.getText().trim();

        if(roomNumber.isEmpty()||Price.isEmpty()) {
        JOptionPane.showMessageDialog(dialog, "Fill all fields","Validation error",JOptionPane.ERROR_MESSAGE);
        
            return;

        }
        try {
        double price =Double.parseDouble(Price);
        Room room = new Room(roomNumber, roomType, price, status);

         boolean success = dao.saveRoom(room);

         if(success) {
            JOptionPane.showMessageDialog(dialog, "Added succesfully","success",JOptionPane.INFORMATION_MESSAGE);
            
           load();
            
            dialog.dispose();
            
         }else{
             JOptionPane.showMessageDialog(dialog, "Book not added" ,"ERROR" ,JOptionPane.ERROR_MESSAGE);

         }
         
            
        } catch (Exception ex) {
             JOptionPane.showMessageDialog(dialog, "Price must be a valid number!", 
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
           

        }
        
       
       

        });

       




        btnCancel.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
        



    }
    
    public void load() {
        
        DefaultTableModel tableModel =panel.tableModel;

        tableModel.setRowCount(0);

        allRooms =dao.getAllRooms();
        if(allRooms ==null) {
            JOptionPane.showMessageDialog(null, "No room available" ,"ERROR",JOptionPane.ERROR_MESSAGE);
            return;
        }

        try{
            for(Room s : allRooms) {
                tableModel.addRow(new Object[] {
                    s.getId(),
                    s.getRoomNumber(),
                    s.getRoomType(),
                    s.getPrice(),
                    s.getStatus()
                });

                  System.out.println("Loaded " + allRooms.size() + " rooms");
            }
             

        }catch(Exception e) {
            e.printStackTrace();

        }

    }
    public void deleteRoom() {
        int selectedRow=panel.table.getSelectedRow();
         if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, 
                "Please select a room to delete!", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        int roomId= (int) panel.table.getValueAt(selectedRow, 0);
        String roomNumber=(String) panel.table.getValueAt(selectedRow,1);

        int confirm=JOptionPane.showConfirmDialog(null, "Are you sure you want to delete roomNumber" + roomNumber + "?","confirm",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);

        if(confirm ==JOptionPane.YES_OPTION) {
            dao.deleteRoom(roomId);
            load();
        }
    }
    public void filter(String searchText) {
         if (searchText == null || searchText.trim().isEmpty()) {
           
            panel.displayRooms(allRooms);
            return;
        }
        
       
         String searchLower = searchText.trim().toLowerCase();
        List<Room> all= new ArrayList<>();

        for(Room room : allRooms) {
            String roomType =room.getRoomType().toLowerCase();

            if(roomType.contains(searchLower)) {
            all.add(room);
            }
        }

          panel.displayRooms(all);
        System.out.println("Search found " + all.size() + " rooms");
        
    }
   public void exportToFile() {
       
        List<Room> rooms = dao.getAllRooms();
        
        if (rooms == null || rooms.isEmpty()) {
            JOptionPane.showMessageDialog(null,"No data to export!", "Export Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
       
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Excel File");
        fileChooser.setSelectedFile(new java.io.File("rooms_export.xlsx"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
        
        int userSelection = fileChooser.showSaveDialog(null);
        
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
        if (!filePath.endsWith(".xlsx")) {
            filePath += ".xlsx";
        }
        
       
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Rooms");
            
            
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Room Number", "Room Type", "Price", "Status"};
            
           
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            
           
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            
            
            int rowNum = 1;
            for (Room room : rooms) {
                Row row = sheet.createRow(rowNum);
                row.createCell(0).setCellValue(room.getId());
                row.createCell(1).setCellValue(room.getRoomNumber());
                row.createCell(2).setCellValue(room.getRoomType());
                row.createCell(3).setCellValue(room.getPrice());
                row.createCell(4).setCellValue(room.getStatus());
                rowNum++;
            }
            
            
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
            
            JOptionPane.showMessageDialog(null,"Export successful!\nFile saved to:\n" + filePath, 
                "Export Complete", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Export failed: " + e.getMessage(), 
                "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
}
