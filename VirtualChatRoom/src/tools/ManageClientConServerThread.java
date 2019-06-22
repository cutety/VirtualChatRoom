/*
* 管理客户端与服务器保持通信的线程类
* */
package tools;

import java.util.HashMap;

public class ManageClientConServerThread {
    private static  HashMap hm = new HashMap<String, ClientConServerThread>();

    //把创建好的通信线程ClientConServerThread放到hashmap里
    public static  void addClientConServerThread(String id, ClientConServerThread ccst) {
        hm.put(id, ccst);
    }
    //可以通过id取得该线程
    public static ClientConServerThread getClientConServerThread(String id){
        return (ClientConServerThread)hm.get(id);
    }
    public static  ClientConServerThread removeClientConServerThread(String id){

        return  (ClientConServerThread)hm.remove(id);
    }
}
