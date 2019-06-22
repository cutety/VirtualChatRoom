package model;

import sun.reflect.generics.scope.Scope;
import tools.ManageClientConServerThread;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientTranserFile extends Socket {
    private Socket socket;
    private DataOutputStream dos;
    private FileInputStream fis;
    public ClientTranserFile() throws Exception{
        super("127.0.0.1",9997);
        this.socket=this;

        System.out.println("9997"+socket.getLocalPort()+"成功连接");
    }
    public void  sendFileToServer(String filePath) throws Exception{
        try {
            File file=new File(filePath);
            if(file.exists()){
                FileInputStream fis=new FileInputStream(file);
                DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
                //文件名和长度
                dos.writeUTF(file.getName());
                dos.flush();
                dos.writeLong(file.length());
                dos.flush();
                //开始传输文件
                System.out.println("======== 开始传输文件 ========");
                byte[] bytes=new byte[1024];
                int length=0;
                long progress=0;
                while((length=fis.read(bytes,0,bytes.length))!=-1){
                    dos.write(bytes,0,length);
                    dos.flush();
                    progress+=length;
                    System.out.println("| " + (100*progress/file.length()) + "% |");
                    System.out.println();
                    System.out.println("======== 文件传输成功 ========");
                }
            }
        }catch (Exception et){
            et.printStackTrace();
        }finally {
            if(fis!=null)
                fis.close();
            if(dos!=null)
                dos.close();
            socket.close();
        }
    }
}
