package dao;

import db.DBConnection;
import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookDAO {

    public void addBook(Book book){

        try{

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO books(title,author,quantity) VALUES(?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1,book.getTitle());
            ps.setString(2,book.getAuthor());
            ps.setInt(3,book.getQuantity());

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ResultSet viewBooks(){

        ResultSet rs = null;

        try{

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM books";

            PreparedStatement ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

        }catch(Exception e){
            e.printStackTrace();
        }

        return rs;
    }

    public ResultSet searchBook(String title){

        ResultSet rs = null;

        try{

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM books WHERE title LIKE ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1,"%"+title+"%");

            rs = ps.executeQuery();

        }catch(Exception e){
            e.printStackTrace();
        }

        return rs;
    }

}