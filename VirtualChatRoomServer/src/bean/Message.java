package bean;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {

    private static final long serialVersionUID = 8141984962750547675L;
    private String messageType;
    private String sender;
    private String reciever;
    private String content;
    private String time;
    private String name;
    private String description;
    private String filePath;
    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    private String onlineList[];

    public String[] getOnlineList() {
        return onlineList;
    }

    public void setOnlineList(String[] onlineList) {
        this.onlineList = onlineList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private ArrayList<String> friendList;
    private ArrayList<String> friendNameList;

    public void setFriendNameList(ArrayList<String> friendNameList) {
        this.friendNameList = friendNameList;
    }

    public ArrayList<String> getFriendNameList() {
        return friendNameList;
    }

    private int friendNum;

    public void setFriendNum(int friendNum) {
        this.friendNum = friendNum;
    }

    public int getFriendNum() {
        return friendNum;
    }

    public void setFriendList(ArrayList<String> friendList) {
        this.friendList = friendList;
    }

    public ArrayList<String> getFriendList() {
        return friendList;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public String getReciever() {
        return reciever;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
