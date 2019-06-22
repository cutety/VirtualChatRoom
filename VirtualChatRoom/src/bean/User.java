package bean;


import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private static final long serialVersionUID = 2412036921120654284L;
    private String userId;
    private String userPwd;
    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    private String friendNum;

    public void setFriendNum(String friendNum) {
        this.friendNum = friendNum;
    }

    public String getFriendNum() {
        return friendNum;
    }

    private ArrayList<String> friendList;

    public void setFriendList(ArrayList<String> friendList) {
        this.friendList = friendList;
    }

    public ArrayList<String> getFriendList() {
        return friendList;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPwd() {
        return userPwd;
    }
}

