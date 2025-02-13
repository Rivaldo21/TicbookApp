package com.app.ticbook;

import com.google.gson.annotations.SerializedName;

public class SubstituteResult {
    private int id;
    private String username;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private String email;

    public int getID() { return id; }
    public void setID(int value) { this.id = value; }

    public String getUsername() { return username; }
    public void setUsername(String value) { this.username = value; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String value) { this.firstName = value; }

    public String getLastName() { return lastName; }
    public void setLastName(String value) { this.lastName = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }
}
