package wxm.com.androiddesign.module;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zero on 2015/7/3.
 */
public class User {
    @Expose
    public String action;
    @Expose
    String userId;
    @Expose
    public String userName;
    @Expose
    public String userPassword;
    @Expose
    public String userEmail;
    @Expose
    public String userPhone;
    @Expose
    public String userGender;
    @Expose
    public String userIcon;
    @Expose
    public String userAlbumIsPublic;
    @Expose
    public String qq;
    @Expose
    public String userLocation;
    @Expose
    public String userCredit;
    @Expose
    public String easemobId;
    @Expose
    public String userDepartment;
    @Expose
    public String userGrade;
    @Expose
    public String userRealName;
    @Expose
    public String userSignature;

    public String getUserSignature() {
        return userSignature;
    }

    public void setUserSignature(String userSignature) {
        this.userSignature = userSignature;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserDepartment() {
        return userDepartment;
    }

    public void setUserDepartment(String userDepartment) {
        this.userDepartment = userDepartment;
    }

    public String getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(String userGrade) {
        this.userGrade = userGrade;
    }

    @Expose
    List<String> userAlbum = new ArrayList<String>();
    @Expose
    String userCode;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public User() {
    }


    public User(String action, String userName, String userPassword, String userEmail, String userGender, String userIcon, String userAlbumIsPublic, String userCredit) {
        this.action = action;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userGender = userGender;
        this.userIcon = userIcon;
        this.userAlbumIsPublic = userAlbumIsPublic;
        this.userCredit = userCredit;
    }



    public User(String action, String userId, String userName, String userPassword, String userEmail, String userPhone, String userGender, String userIcon, String userAlbumIsPublic, String qq, String userLocation, String userCredit, String userAddress, List<String> userAlbum) {

        this.action = action;
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userGender = userGender;
        this.userIcon = userIcon;
        this.userAlbumIsPublic = userAlbumIsPublic;
        this.qq = qq;
        this.userLocation = userLocation;
        this.userCredit = userCredit;
        this.userAlbum = userAlbum;
    }

    public String getEasemobId() {
        return easemobId;
    }

    public void setEasemobId(String easemobId) {
        this.easemobId = easemobId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }


    public String getUserAlbumIsPublic() {
        return userAlbumIsPublic;
    }

    public void setUserAlbumIsPublic(String userAlbumIsPublic) {
        this.userAlbumIsPublic = userAlbumIsPublic;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getUserCredit() {
        return userCredit;
    }

    public void setUserCredit(String userCredit) {
        this.userCredit = userCredit;
    }


    public List<String> getUserAlbum() {
        return userAlbum;
    }

    public void setUserAlbum(List<String> userAlbum) {
        this.userAlbum = userAlbum;
    }
}
