package model;

import java.util.HashMap;
import java.util.Iterator;

public class ManageClientThread {
    public static HashMap hm=new HashMap<String,ServerConClientThread>();
    public static void addClientThread(String uid,ServerConClientThread ct){
        hm.put(uid,ct);
    }
    public static ServerConClientThread getClientThread(String uid){
        return (ServerConClientThread) hm.get(uid);
    }
    //返回当前在线人
    public static  String getAllOnlineUserId(){
        Iterator it=hm.keySet().iterator();
        String res="";
        while(it.hasNext()){
            res+=it.next().toString()+" ";
        }
        return res;
    }

}
