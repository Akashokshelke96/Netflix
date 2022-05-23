package com.prepfortech.netflixclone.accessor;


import com.prepfortech.netflixclone.accessor.model.AuthDTO;
import com.prepfortech.netflixclone.exceptions.DependencyFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Repository
public class AuthAccessor {

    @Autowired
    private DataSource dataSource;

    //Store the token for the given userId, if successful it will work, otherwise throws exception
    public void storeToken(final String userId,final String token){
        String query = "insert into auth(authId,token,userId) values(?,?,?)";

        String uuid = UUID.randomUUID().toString();

        try(Connection connection = dataSource.getConnection()){

            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1,uuid);
            pstmt.setString(2,token);
            pstmt.setString(3,userId);

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }
    }


    public AuthDTO getAuthByToken(final String token){
        String query = "select authId,token, userId from auth where token = ?";
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,token);
            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next()){
                AuthDTO authDTO = AuthDTO.builder()
                        .authId(resultSet.getString(1))
                        .token(resultSet.getString(2))
                        .userId(resultSet.getString(3))
                        .build();
                return authDTO;
            }
            return null;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }
    }


    public void deleteAuthByToken(final String token){
        String query = "delete from auth where token = ?";
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,token);

            pstmt.execute();
        } catch (SQLException ex ){
            ex.printStackTrace();
            throw new DependencyFailureException(ex);

        }
    }
}
