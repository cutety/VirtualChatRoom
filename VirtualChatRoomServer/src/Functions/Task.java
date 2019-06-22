/*
package Functions;

import model.ManageClientThread;

import javax.sound.midi.Receiver;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.net.ServerSocket;
import java.net.Socket;

public class Task implements Runnable {
    private DataInputStream dis;
    private FileOutputStream fos;
    private String receiver;

    public Task(String receiver) {
        this.receiver = receiver;
    }

    public void run() {

        try {
            dis = new DataInputStream(ManageClientThread.getClientThread(receiver).getS().getInputStream());
            String fileName = dis.readUTF();
            long fileLength = dis.readLong();
            File directory = new File("C:\\Users\\Administrator.DESKTOP-NECSBS8\\Desktop");
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
            System.out.println("======== 文件接收成功 [File Name：" + fileName + "] [Size：" + (fileLength) + "] ========");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
                if (dis != null) {
                    dis.close();
                }
            } catch (Exception ef) {
                ef.printStackTrace();
            }
        }
    }

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
}
*/

