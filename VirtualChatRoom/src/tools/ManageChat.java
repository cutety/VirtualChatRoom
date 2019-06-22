package tools;

import frame.ChatFrame;

import java.util.HashMap;

public class ManageChat {
    public  static HashMap hm=new HashMap<String,ChatFrame>();
    //加入
    public static void addChat(String userIdAndFriendId,ChatFrame chatFrame){
        hm.put(userIdAndFriendId,chatFrame);
    }
    //取出
    public static ChatFrame getChat(String userIdAndFriendId){
        return (ChatFrame)hm.get(userIdAndFriendId);
    }
    public static boolean hasChat(String userIdAndFriendId){
        return hm.containsKey(userIdAndFriendId);
    }
}
