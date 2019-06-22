package frame;

import model.MyServer;

import javax.swing.*;
import javax.xml.ws.Service;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyServerFrame extends JFrame implements ActionListener {
    private  JPanel jp1;
    private JButton start,stop;

    public static void main(String[] args) {
        new MyServerFrame();
    }
    public MyServerFrame(){
        jp1=new JPanel();
        start=new JButton("启动服务器");
        stop=new JButton("关闭服务器");
        start.addActionListener(this);
        stop.addActionListener(this);
        jp1.add(start);
        jp1.add(stop);
        this.add(jp1);
        this.setTitle("服务器设置");
        this.setSize(250,200);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==start){
            new MyServerThread().start();
            JOptionPane.showMessageDialog(null,"启动服务器成功");

        }else if(e.getSource()==stop){
            System.exit(0);

        }
    }
    class MyServerThread extends Thread{
        public void run(){
            new MyServer();

        }
    }
}
