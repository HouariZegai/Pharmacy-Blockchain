package com.tiaretdevgroup.openhackathon.java.dao;

import com.tiaretdevgroup.openhackathon.java.security.MD5Hashing;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao {

    public int checkUsernameAndPassword(String user, String pass) {
        
        String sql = "SELECT * FROM `users` WHERE username=? AND password =?;";
        Connection con = null;
        PreparedStatement prest = null;

        try {
            con = SqliteConnection.getConnection();

            if (con == null) {
                return -1;
            }

            prest = null;
            prest = con.prepareStatement(sql);
            prest.setString(1, user);
            prest.setString(2, MD5Hashing.getHash(pass));
            ResultSet rs = prest.executeQuery();

            if (rs.next()) {
                return 0;
            }

        } catch (SQLException ex) {
            System.out.println("Error msg: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (prest != null) {
                    prest.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return 1;
    }

    public int addUser(String user, String pass) {
        String sql = "INSERT INTO `users`(username, password) VALUES(?, ?);";
        Connection con = null;
        PreparedStatement prest = null;

        try {
            con = SqliteConnection.getConnection();

            if (con == null) {
                return -1;
            }

            prest = null;
            prest = con.prepareStatement(sql);
            prest.setString(1, user);
            prest.setString(2, MD5Hashing.getHash(pass));
            prest.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error msg: " + ex.getMessage());
            return 1;
        } finally {
            try {
                if (prest != null) {
                    prest.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return 0;
    }

}
