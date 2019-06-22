package tools;

import java.security.MessageDigest;

public class MD5 {
    public static String enCrypt(String str){
        try {
            MessageDigest md=MessageDigest.getInstance("MD5");
            byte[] s=md.digest(str.getBytes());
            String ss="";
            String result="";
            for(int i=0;i<s.length;i++){
                ss=Integer.toHexString(s[i]&0xff);
                if(ss.length()==1) {
                    result += "0" + ss;
                }else{
                    result+=ss;
                }
            }
            return result;
        }catch (Exception em){
            em.printStackTrace();
        }
        return null;
    }
    public static String converMD5(String instr){
        char[] a=instr.toCharArray();
        for(int i=0;i<a.length;i++){
            a[i]=(char)(a[i]^'t');
        }
        String s=new String(a);
        return s;
    }
/*
    public static void main(String[] args) {
        String user="okdokay";
        System.out.println("原始字符" + user);
        System.out.println("MD5后"+MD5.enCrypt(user));
        System.out.println("加密后"+MD5.converMD5(user));
        System.out.println("解密后"+MD5.converMD5(MD5.converMD5(user)));
    }*/
}
