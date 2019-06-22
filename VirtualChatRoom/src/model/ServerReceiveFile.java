package model;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;

public class ServerReceiveFile extends ServerSocket {
    private static DecimalFormat df = null;

    static {
        // 设置数字格式，保留一位有效小数
        df = new DecimalFormat("#0.0");
        df.setRoundingMode(RoundingMode.HALF_UP);
        df.setMinimumFractionDigits(1);
        df.setMaximumFractionDigits(1);
    }

    public ServerReceiveFile() throws Exception {
        super(9996);
    }
    //使用线程处理每个客户端传输的文件
    public void load() throws Exception{
        Socket socket=this.accept();
        new Thread(new Task(socket)).start();
    }

    //处理客户端传输过来的文件线程类
    private String getFormatFileSize(long length) {
        double size = ((double) length) / (1 << 30);
        if (size >= 1) {
            return df.format(size) + "GB";
        }
        size = ((double) length) / (1 << 20);
        if (size >= 1) {
            return df.format(size) + "MB";
        }
        size = ((double) length) / (1 << 10);
        if (size >= 1) {
            return df.format(size) + "KB";
        }
        return length + "B";
    }

    class Task implements Runnable {
        private DataInputStream dis;
        private FileOutputStream fos;
        private Socket socket;

        public Task(Socket socket) {
            this.socket=socket;
        }

        public void run() {

            try {
                dis = new DataInputStream(socket.getInputStream());
                //目前下面的这句话会抛出异常，问题应该是在于我重复上传了同一个文件
                String fileName = dis.readUTF();
                long fileLength = dis.readLong();
                File directory = new File("C:\\Users\\Administrator.DESKTOP-NECSBS8\\Desktop\\newR");
                if (!directory.exists()) {
                    directory.mkdir();
                }
                File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
                fos = new FileOutputStream(file);
                //开始接受文件
                byte[] bytes = new byte[1024];
                int length = 0;
                while ((length = dis.read(bytes, 0, bytes.length)) != -1) {
                    fos.write(bytes, 0, length);
                    fos.flush();
                }
                System.out.println("======== 文件接收成功 [File Name：" + fileName + "] [Size：" + getFormatFileSize(fileLength) + "] ========");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null)
                        fos.close();
                    if (dis != null) {
                        dis.close();
                    }
                    socket.close();

                } catch (Exception ef) {
                    ef.printStackTrace();
                }
            }
        }
    }
}





