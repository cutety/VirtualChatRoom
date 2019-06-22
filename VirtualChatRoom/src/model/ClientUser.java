package model;

import bean.User;

public class ClientUser {
    public boolean checkUser(User u){
        return  new ClientConServer().sendLoginInfoToServer(u);
    }
    public boolean checkRegist(User u){
        return new ClientConServer().senRegistInfoToServer(u);
    }
}
