package com.prepfortech.netflixclone.accessor;

import com.prepfortech.netflixclone.accessor.model.ProfileDTO;
import com.prepfortech.netflixclone.accessor.model.ProfileType;
import com.prepfortech.netflixclone.exceptions.DependencyFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

import java.util.UUID;

@Repository
public class ProfileAccessor {
    @Autowired
    private DataSource dataSource;

    public void addNewProfile(final String userId, final String name, final ProfileType type){
        String query = "insert into profile values (?, ?, ?, ?, ?)";
        try(Connection connection = dataSource.getConnection()){

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, UUID.randomUUID().toString());
            pstmt.setString(2,name);
            pstmt.setString(3,type.toString());
            pstmt.setDate(4,new Date(System.currentTimeMillis()));
            pstmt.setString(5,userId);

            pstmt.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }
    }


    public void deleteProfile(final String profileId){
        String query = "delete from profile where profileId = ?";
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1,profileId);

            pstmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }
    }

    public ProfileDTO getProfileByProfileId(final String profileId){
        String query = "SELECT name, type, createdAt, userId from profile where profileId = ?";
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, profileId);

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                ProfileDTO profileDTO = ProfileDTO.builder()
                        .profileId(profileId)
                        .name(resultSet.getString(1))
                        .type(ProfileType.valueOf(resultSet.getString(2)))
                        .createdAt(resultSet.getDate(3))
                        .userId(resultSet.getString(4))
                        .build();
                return profileDTO;
            }
            else {
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }
    }
}

