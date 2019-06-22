package tools;

import frame.MainFrame;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ManageFirendList {
    //用于初始化与更新好友列表
    private static HashMap hm=new HashMap<String, MainFrame>();
    //添加主界面
    public static void addMainFrame(String id,MainFrame mainFrame){
        hm.put(id,mainFrame);

    }
    //返回主界面
    public static MainFrame getMainFrame(String id){
        return (MainFrame)hm.get(id);
    }
    public static void removeFrame(String id){
        hm.remove(id);
    }
    //新加入一个好友ID与昵称的映射表，方便在好友列表和聊天窗口等地方显示好友昵称。
    private static HashMap hm1=new HashMap<String,String>();
    //将好友信息加入到hashMap中
    public static HashMap getFriendListMap(){
        return hm1;
    }
    public static void addFriendInfo(String id,String name){
        hm1.put(id,name);
    }
    //通过好友的id取得与之对应的昵称
    public static String getFriendName(String id){
        return (String)hm1.get(id);
    }
    public static boolean isFriend(String id){
        return  hm1.containsKey("id");

    }
    public static String getFriendId(String name){
        Set<Map.Entry<String,String>> set=hm1.entrySet();
        String key="";
        for(Map.Entry<String,String>entry:set){
            if(entry.getValue().equals(name)){
                key=entry.getKey();
                break;
            }
        }
        return key;
    }
    public static HashMap getMap(){
        return hm1;
    }
}
