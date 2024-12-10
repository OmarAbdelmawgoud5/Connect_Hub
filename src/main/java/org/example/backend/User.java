package org.example.backend;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.swing.*;

public class User {

    private String id;
    private String userName;
    private String email;
    private LocalDate dateOfBirth;
    private String profilePhoto;
    private String coverPhoto;
    private String Bio;
    private String password;
    private String status;
    private ArrayList<Map<String,String>> groups;

    @JsonCreator
    public User(
            @JsonProperty("userName") String userName,
            @JsonProperty("email") String email,
            @JsonProperty("dateOfBirth") LocalDate dateOfBirth,
            @JsonProperty("profilePhoto") String profilePhoto,
            @JsonProperty("coverPhoto") String coverPhoto,
            @JsonProperty("bio") String bio, // Included the "bio" field
            @JsonProperty("password") String password,
            @JsonProperty("status") String status,
            @JsonProperty("id") String id
    ) {
        this.id = id ;// Assign an ID if not provided
        this.userName = userName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.profilePhoto = profilePhoto;
        this.coverPhoto = coverPhoto;
        this.Bio = bio;
        this.password = password;
        this.status = status;
    }





    public User(String userName, String email, LocalDate dateOfBirth, String profilePhoto, String coverPhoto, String Bio, String password, String status) {
        this.id = generateUserId();
        this.userName = userName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.profilePhoto = profilePhoto;
        this.coverPhoto = coverPhoto;
        this.Bio = Bio;
        this.password = password;
        this.status = status;
        groups = new ArrayList<>();
    }

    public void setUserId(String userId) {
        this.id = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setBio(String Bio) {
        this.Bio = Bio;
    }

    public void setPassword(String password) {
        this.password=Encryption.hashPassword(password);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getBio() {
        return Bio;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getStatus() {
        return status;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public ObjectNode toJsonNode() {
        ObjectNode userNode = new ObjectMapper().createObjectNode();
        userNode.put("userName", this.userName);
        userNode.put("email", this.email);
        userNode.put("dateOfBirth", this.dateOfBirth.toString());
        userNode.put("profilePhoto", this.profilePhoto);
        userNode.put("coverPhoto", this.coverPhoto);
        userNode.put("password", this.password);
        userNode.put("status", this.status);
        userNode.put("bio", this.Bio);
        userNode.put("id", this.id);
        return userNode;
    }
    public String getCoverPhoto() {
        return coverPhoto;
    }

    private String generateUserId() {
        return  UUID.randomUUID().toString();
    }

}
