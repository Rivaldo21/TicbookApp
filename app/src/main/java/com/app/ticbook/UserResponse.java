package com.app.ticbook;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("user_id")
    private int userID;
    private String username;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("full_name")
    private String fullName;
    private String email;
    private String role;
    private String departement;

    public int getUserID() { return userID; }
    public void setUserID(int value) { this.userID = value; }

    public String getUsername() { return username; }
    public void setUsername(String value) { this.username = value; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String value) { this.firstName = value; }

    public String getLastName() { return lastName; }
    public void setLastName(String value) { this.lastName = value; }

    public String getFullName() { return fullName; }
    public void setFullName(String value) { this.fullName = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }

    public String getRole() { return role; }
    public void setRole(String value) { this.role = value; }

    public String getDepartement() { return departement; }
    public void setDepartement(String value) { this.departement = value; }
}
