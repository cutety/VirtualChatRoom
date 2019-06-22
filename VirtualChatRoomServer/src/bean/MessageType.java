package bean;

public interface MessageType {
    String message_success="1";//登陆成功
    String message_login_fail="2";//登陆失败
    String message_comm_mes="3";//普通信息包
    String message_get_onLineFriend="4";//请求在线好友的包
    String message_ret_onLineFriend="5";//返回在线好友的包
    String message_request_logout="6";//客户端退出
    String message_ret_logout="7";//返回客户端退出
    String message_get_logout="8";//获取下线好友
    String message_request_regist="9";//请求注册
    String message_regist_permit="10";//允许注册
    String message_regist_deny="11";//注册拒绝
    String message_request_addFriend="12";//请求加好友
    String message_addFriend_permit="13";//允许加好友
    String message_addFriend_deny="14";//拒绝加好友
    String message_newOnline_friend="15";//新上线好友
    String message_file_transfer="16";//文件传输
    String message_frameShake="17";//抖动窗口
    String message_comm_mes_group="18";//群聊消息
    String message_alter_name="19";//请求改网名
    String message_alter_des="20";//请求改签名
    String message_request_friend_info="21";//请求好友个人信息
}
