package org.example.backend;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.security.KeyPair;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
    private Map<String,String> groups;

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
            @JsonProperty("id") String id,
            @JsonProperty("groups") Map<String,String>groups
    ) {
        this.id = id ;  // Assign an ID if not provided
        this.userName = userName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.profilePhoto = profilePhoto;
        this.coverPhoto = coverPhoto;
        this.Bio = bio;
        this.password = password;
        this.status = status;
        this.groups = groups;
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
        groups = new HashMap<>();
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

    public Map<String, String> getGroups() {
        return groups;
    }
    public void removeGroup(String groupId) {
        groups.remove(groupId);
    }

    public void addGroup(String groupId, String role) {
        this.groups.put(groupId, role);
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
        ObjectMapper mapper=new ObjectMapper();
        JsonNode groupsNode = mapper.valueToTree(this.groups);
        userNode.set("groups", groupsNode);
        return userNode;
    }
    public String getCoverPhoto() {
        return coverPhoto;
    }

    private String generateUserId() {
        return  UUID.randomUUID().toString();
    }

}
