package model;

import bean.User;

public class ClientUser {
    public boolean checkUser(User u){
        return  new ClientConServer().sendLoginInfoToServer(u);
    }
}
