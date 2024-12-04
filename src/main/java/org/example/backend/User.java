package org.example.backend;

import java.time.LocalDate;
import java.util.UUID;

public class User {

    private String userId;
    private String userName;
    private String email;
    private LocalDate dateOfBirth;
    private String profilePhoto;
    private String coverPhoto;
    private String Bio;
    private String password;
    private String status;

    public User(String userName, String email, LocalDate dateOfBirth, String profilePhoto, String coverPhoto, String Bio, String password, String status) {
        this.userId = generateUserId();
        this.userName = userName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.profilePhoto = profilePhoto;
        this.coverPhoto = coverPhoto;
        this.Bio = Bio;
        this.password = password;
        this.status = status;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
        this.password = password;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getStatus() {
        return status;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    private String generateUserId() {
        return UUID.randomUUID().toString();
    }

}
