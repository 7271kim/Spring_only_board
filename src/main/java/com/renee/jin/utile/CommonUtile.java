package com.renee.jin.utile;

import java.security.MessageDigest;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtile {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtile.class);
    
    /*SHA-256 암호화 함수*/
    public String getSHA256(String passwd) {
        //스트링을 바이트로  
        byte[] byteArray = passwd.getBytes();  

        MessageDigest md = null;  

        try {  
            //암호화 방법 선택  
            md = MessageDigest.getInstance("SHA-256");  
        }catch(Exception e) {
            logger.info("MessageDigest Fail...");
            e.printStackTrace();
        }

        if(md == null)  
            return null;  

        md.reset();
        md.update(byteArray);  
        //암호화  
        byte digest[] = md.digest();  

        //암호화한 데이타 저장할 곳
        StringBuffer buffer = new StringBuffer();  

        //헥사 값으로 저장.  
        for (int i = 0; i < digest.length; i++) {  
            buffer.append(Integer.toHexString(0xFF & digest[i]));  
        }  
        
        return buffer.toString();
    }
    
    /* 랜덤 토큰 생성 */
    public String generateToken() {
        StringBuffer temp = new StringBuffer();
        Random rnd = new Random();
        // Random 함수 리눅스에서 사용 못하던 이슈 있었던것 같음 확인 필요!
        for (int i = 0; i < 20; i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
            case 0:
                // a-z
                temp.append((char) ((int) (rnd.nextInt(26)) + 97));
                break;
            case 1:
                // A-Z
                temp.append((char) ((int) (rnd.nextInt(26)) + 65));
                break;
            case 2:
                // 0-9
                temp.append((rnd.nextInt(10)));
                break;
            }
        }
        byte[] byteArray = temp.toString().getBytes();  
        MessageDigest md = null;  

        try {  
            //암호화 방법 선택  
            md = MessageDigest.getInstance("SHA-256");  
        }catch(Exception e) {
            e.printStackTrace();
        }

        if(md == null)  
            return null;  

        md.reset();
        md.update(byteArray);  
        //암호화  
        byte digest[] = md.digest();  

        //암호화한 데이타 저장할 곳
        StringBuffer buffer = new StringBuffer();  

        //헥사 값으로 저장.  
        for (int i = 0; i < digest.length; i++) {  
            buffer.append(Integer.toHexString(0xFF & digest[i]));  
        }  
        
        return buffer.toString();
    }
}
