package com.hotel.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hotel.model.Room;
import com.hotel.util.HibernateUtil;

public class RoomDAO {
    public  boolean  saveRoom(Room room){

        Transaction transaction = null;
        try(Session session =HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(room);
            transaction.commit();
            return true;
            
        } catch (Exception e) { 
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }

    }
    public Room getById(int id) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Room.class, id);
            
        }
    }  
    public List<Room>  getAllRooms() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Room> query = session.createQuery("FROM Room",Room.class );
            return query.list();

        }
    }
    public List<Room> getAvailableRooms() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Room> query = session.createQuery("FROM Room WHERE status = 'AVAILABLE'",Room.class);
            return query.list();
        }
    }
      public List<Room> searchAvailableRooms(String checkIn, String checkOut, int guests) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Room> query = session.createQuery("FROM Room WHERE status = 'AVAILABLE'",Room.class);
            return query.list();
        }
    }
     public boolean updateRoom(Room room) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(room);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
     public boolean deleteRoom(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Room room = session.get(Room.class, id);
            if (room != null) {
                session.delete(room);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
    

}