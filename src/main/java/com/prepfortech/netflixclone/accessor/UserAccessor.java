package com.prepfortech.netflixclone.accessor;

import com.prepfortech.netflixclone.accessor.model.UserDTO;
import com.prepfortech.netflixclone.accessor.model.UserRole;
import com.prepfortech.netflixclone.accessor.model.UserState;
import com.prepfortech.netflixclone.exceptions.DependencyFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.UUID;


import static com.fasterxml.jackson.databind.util.ClassUtil.name;

@Repository
public class UserAccessor {

    @Autowired
    private DataSource dataSource;


    //GETS THE USER BASED ON HIS EMAIL, IF USER EXISTS RETURNS userDTO OBJECT ELSE RETURNS NULL;
    public UserDTO getUserByEmail(final String email) {

        String query = "select userId,name,email,password, phoneNo, state, userRole from user where email =?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, email); //this is to manage the question mark we had in the query.

            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next()) {
                UserDTO  userDTO = UserDTO.builder()
                        .userId(resultSet.getString(1))
                        .name(resultSet.getString(2))
                        .email(resultSet.getString(3))
                        .password(resultSet.getString(4))
                        .phoneNo(resultSet.getString(5))
                        .state(UserState.valueOf(resultSet.getString(6)))
                        .role(UserRole.valueOf(resultSet.getString(7)))
                        .build();
                return userDTO;



            }
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }

    }


    public void addNewUser(final String email,final String name,final String password,final String phoneNo , final UserState userState,
                           final UserRole userRole){
        String insertQuery = "insert into user values ( ?, ?, ?, ?, ?, ?, ?)" ;

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement pstmt = connection.prepareStatement(insertQuery);
            pstmt.setString(1,UUID.randomUUID().toString());
            pstmt.setString(2,name);
            pstmt.setString(3,email);
            pstmt.setString(4,password);
            pstmt.setString(5,phoneNo);
            pstmt.setString(6, userState.name());
            pstmt.setString(7,userRole.name());
            pstmt.execute();
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }

    }
     //THIS METHOD IS TO CHECK WETHER A PHONE NUMBER ALREADY EXISTS IN THE DB OR NOT
    public UserDTO getUserByPhoneNo(final String phoneNo){
        String query = "select userId,name,email,password, phoneNo, state, userRole from user where phoneNo =?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, phoneNo); //this is to manage the question mark we had in the query.

            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next()) {
                UserDTO  userDTO = UserDTO.builder()
                        .userId(resultSet.getString(1))
                        .name(resultSet.getString(2))
                        .email(resultSet.getString(3))
                        .password(resultSet.getString(4))
                        .phoneNo(resultSet.getString(5))
                        .state(UserState.valueOf(resultSet.getString(6)))
                        .role(UserRole.valueOf(resultSet.getString(7)))
                        .build();
                return userDTO;



            }
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }


    }



    public void updateUserRole(final String userId,final UserRole updatedRole){
        String query = "update user set userRole = ? where userId = ?";
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, String.valueOf(updatedRole));
            pstmt.setString(2,userId);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }
    }
}
