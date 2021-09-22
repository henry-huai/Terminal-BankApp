package dev.huai.services;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class PasswordService {

    public String encryptPassword(String str){
        return generatePassword(str);
    }

    private String generatePassword(String str){

        try{
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(str.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            //generatedPassword = sb.toString();
            return sb.toString();
        } catch ( NoSuchAlgorithmException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
