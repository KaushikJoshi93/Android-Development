package com.example.practiceapp.models;

public class Users {
    String profilepic , userName , email , userId , password , lastMessage;

    public Users(String profilepic, String userName, String email, String userId, String password, String lastMessage) {
        this.profilepic = profilepic;
        this.userName = userName;
        this.email = email;
        this.userId = userId;
        this.password = password;
        this.lastMessage = lastMessage;
    }

    public Users(){}

//    SignUp Constructor
public Users( String userName, String email, String password) {
    this.userName = userName;
    this.email = email;
    this.password = password;
}

    public String getProfilepic() {
        return profilepic;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
