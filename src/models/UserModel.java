
package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import useutils.DB;
import useutils.Util;


public class UserModel {
    
    
    public int userLogin( String userName, String password ) {
        int statu = -1;
        try {
            String query = "select * from user where uusername = ? and upassword = ?";
            String pw = new Util().md5Generator(password);
            DB db = new DB();
            
            PreparedStatement pre = db.fncPre(query);
            pre.setString(1, userName);
            pre.setString(2, pw);
            
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                statu = rs.getInt("ustatu");
                Util.us.setUid(rs.getInt("uid"));
                Util.us.setUname(rs.getString("uname"));
                Util.us.setUstatu(rs.getInt("ustatu"));
                Util.us.setUusername(rs.getString("uusername"));
            }
            
            db.close();
        } catch (Exception e) {
            
        }
        return statu;
    }
    
    
    //userInsert for Personnels
    public int userInsert(String uname, String uusername, String upassword, int ustatu, int upid) {
        int statu = -1;
        try {
            DB db = new DB();
            String query = "insert into user values ( null, ? , ? , ? , ? , ?)";
            String md5password = new Util().md5Generator(upassword);
            PreparedStatement pre = db.fncPre(query);
            pre.setString(1, uname);
            pre.setString(2, uusername);
            pre.setString(3, md5password);
            pre.setInt(4, ustatu);
            pre.setInt(5, upid);
            statu = pre.executeUpdate();
            db.close();
        } catch (Exception e) {
            System.err.println("userInsert error : " + e);
            PersonRegister pr = new PersonRegister();
            pr.userUsernameUniqueError();
        }
        return statu;
    }
    public int userUpdate(int uid, String uname, String uusername, String upassword) {
        int statu = -1;
        try {
            DB db = new DB();
            String query = "update user set uname = ?, uusername = ?, upassword = ? where uid = ?";
            String md5password = new Util().md5Generator(upassword);
            PreparedStatement pre = db.fncPre(query);
            pre.setString(1, uname);
            pre.setString(2, uusername);
            pre.setString(3, md5password);
            pre.setInt(4, uid);
            statu = pre.executeUpdate();
            db.close();
        } catch (Exception e) {
            System.err.println("userUpdate Error : " + e);
        }
        return statu;
    }

    private static class PersonRegister {

        public PersonRegister() {
        }

        private void userUsernameUniqueError() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    
    
}
