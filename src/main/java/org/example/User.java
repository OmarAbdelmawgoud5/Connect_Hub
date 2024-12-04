package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

public class User {
    String userName;
    String email;
    LocalDate dateOfBirth;
    String profilePhoto;
    String coverPhoto;
    String password;
    String status;
    String id;
    String Bio;

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }

    @JsonCreator
    public User(
            @JsonProperty("userName") String userName,
            @JsonProperty("email") String email,
            @JsonProperty("dateOfBirth") LocalDate dateOfBirth,
            @JsonProperty("profilePhoto") String profilePhoto,
            @JsonProperty("coverPhoto") String coverPhoto,
            @JsonProperty("password") String password,
            @JsonProperty("status") String status,
            @JsonProperty("id") String id
    ) {
        this.userName = userName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.profilePhoto = profilePhoto;
        this.coverPhoto = coverPhoto;
        this.password = password;
        this.status = status;
        this.id = id;
    }



}
