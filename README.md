# 高仿qq

## 数据库操作
在MySql中建立数据库vchatroom，运行vchatroom.sql对数据库进行初始化。
接着在VirtualChatRoomServer的工程文件里，把src/DAO/Dao.java里面的数据库配置给修改成自己的mysql账号密码。

## 传输文件（必看）
需要分别在客户端和服务器的工程文件里把model包里的ServerReceiveFile里面第67行的默认接收目录改为自己设置好的目录。
```  
File directory = new File("C:\\Users\\Administrator.DESKTOP-NECSBS8\\Desktop\\newF");
``` 


