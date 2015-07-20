package wxm.com.androiddesign.module;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zero on 2015/6/25.
 */

public class AtyItem implements Parcelable {
    String action;
    String userId;
    String userName;
    String userPhoto;
    String atyId;
    String atyName;
    String atyType;
    String atyStartTime;
    String atyEndTime;
    String atyPlace;
    String atyMembers;
    String atyContent;
    String atyLikes;
    String atyComment;
    String atyIsJoined;
    String atyIsLiked;
    String atyShares;
    List<String> atyAlbum = new ArrayList<String>();

    public String getAtyLikes() {
        return atyLikes;
    }

    public void setAtyLikes(String atyLikes) {
        this.atyLikes = atyLikes;
    }

    public String getAtyIsJoined() {
        return atyIsJoined;
    }

    public void setAtyIsJoined(String atyIsJoined) {
        this.atyIsJoined = atyIsJoined;
    }

    public String getAtyIsLiked() {
        return atyIsLiked;
    }

    public void setAtyIsLiked(String atyIsLiked) {
        this.atyIsLiked = atyIsLiked;
    }

    public String getAtyShares() {
        return atyShares;
    }

    public void setAtyShares(String atyShares) {
        this.atyShares = atyShares;
    }

    public String getAtyPlused() {
        return atyIsLiked;
    }

    public void setAtyPlused(String atyPlused) {
        this.atyIsLiked = atyPlused;
    }

    public String getAtyId() {
        return atyId;
    }

    public void setAtyId(String atyId) {
        this.atyId = atyId;
    }

    public String getAtyName() {
        return atyName;
    }

    public void setAtyName(String atyName) {
        this.atyName = atyName;
    }

    public String getAtyType() {
        return atyType;
    }

    public void setAtyType(String atyType) {
        this.atyType = atyType;
    }

    public String getAtyStartTime() {
        return atyStartTime;
    }

    public void setAtyStartTime(String atyStartTime) {
        this.atyStartTime = atyStartTime;
    }

    public String getAtyEndTime() {
        return atyEndTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setAtyEndTime(String atyEndTime) {
        this.atyEndTime = atyEndTime;
    }

    public String getAtyPlace() {
        return atyPlace;
    }

    public void setAtyPlace(String atyPlace) {
        this.atyPlace = atyPlace;
    }

    public String getAtyMembers() {
        return atyMembers;
    }

    public void setAtyMembers(String atyMembers) {
        this.atyMembers = atyMembers;
    }

    public String getAtyContent() {
        return atyContent;
    }

    public void setAtyContent(String atyContent) {
        this.atyContent = atyContent;
    }

    public String getAtyPlus() {
        return atyLikes;
    }

    public void setAtyPlus(String atyPlus) {
        this.atyLikes = atyPlus;
    }

    public String getAtyComment() {
        return atyComment;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setAtyComment(String atyComment) {
        this.atyComment = atyComment;
    }

    public String getAtyJoined() {
        return atyIsJoined;
    }

    public void setAtyJoined(String atyJoined) {
        this.atyIsJoined = atyJoined;
    }

    public String getAtyShare() {
        return atyShares;
    }

    public void setAtyShare(String atyShare) {
        this.atyShares = atyShare;
    }

    public List<String> getAtyAlbum() {
        return atyAlbum;
    }

    public void setAtyAlbum(List<String> atyAlbum) {
        this.atyAlbum = atyAlbum;
    }

    public AtyItem(Parcel in) {
        atyAlbum = new ArrayList<>();
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public AtyItem(String action, String userId, String userName, String userPhoto,
                   String atyName, String atyType, String atyStartTime, String atyEndTime,
                   String atyPlace, String atyMembers, String atyContent, String atyPlus,
                   String atyComment, String atyJoined, String atyPlused, String atyShare,
                   List<String> atyAlbum) {
        this.action = action;
        this.userId = userId;
        this.atyType = atyType;
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.atyName = atyName;
        this.atyStartTime = atyStartTime;
        this.atyEndTime = atyEndTime;
        this.atyPlace = atyPlace;
        this.atyMembers = atyMembers;
        this.atyContent = atyContent;
        this.atyLikes = atyPlus;
        this.atyComment = atyComment;
        this.atyIsJoined = atyJoined;
        this.atyIsLiked = atyPlused;
        this.atyShares = atyShare;
        this.atyAlbum = atyAlbum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AtyItem(String action, String userId, String atyId, String atyName,
                   String atyType, String atyStartTime, String atyEndTime,
                   String atyPlace, String atyMembers, String atyContent,
                   String atyPlus, String atyComment, String atyJoined,
                   String atyShare, List<String> atyAlbum) {
        this.action = action;
        this.userId = userId;
        this.atyId = atyId;
        this.atyName = atyName;
        this.atyType = atyType;
        this.atyStartTime = atyStartTime;
        this.atyEndTime = atyEndTime;
        this.atyPlace = atyPlace;
        this.atyMembers = atyMembers;
        this.atyContent = atyContent;
        this.atyLikes = atyPlus;
        this.atyComment = atyComment;
        this.atyIsJoined = atyJoined;
        this.atyShares = atyShare;
        this.atyAlbum = atyAlbum;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(action);
        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeString(userPhoto);
        dest.writeString(atyId);
        dest.writeString(atyName);
        dest.writeString(atyType);
        dest.writeString(atyStartTime);
        dest.writeString(atyEndTime);
        dest.writeString(atyPlace);
        dest.writeString(atyMembers);
        dest.writeString(atyContent);
        dest.writeString(atyLikes);
        dest.writeString(atyComment);
        dest.writeString(atyIsJoined);
        dest.writeString(atyIsLiked);
        dest.writeString(atyShares);
        dest.writeList(atyAlbum);
    }

    public void readFromParcel(Parcel in) {
        action = in.readString();
        userId = in.readString();
        userName = in.readString();
        userPhoto = in.readString();
        atyId = in.readString();
        atyName = in.readString();
        atyType = in.readString();
        atyStartTime = in.readString();
        atyEndTime = in.readString();
        atyPlace = in.readString();
        atyMembers = in.readString();
        atyContent = in.readString();
        atyLikes = in.readString();
        atyComment = in.readString();
        atyIsJoined = in.readString();
        atyIsLiked = in.readString();
        atyShares = in.readString();
        in.readList(atyAlbum, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<AtyItem> CREATOR =
            new Parcelable.Creator<AtyItem>() {
                public AtyItem createFromParcel(Parcel in) {
                    return new AtyItem(in);
                }

                public AtyItem[] newArray(int size) {
                    return new AtyItem[size];
                }
            };

    @Override
    public String toString() {
        return "AtyItem{" +
                "action='" + action + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userPhoto='" + userPhoto + '\'' +
                ", atyId='" + atyId + '\'' +
                ", atyName='" + atyName + '\'' +
                ", atyType='" + atyType + '\'' +
                ", atyStartTime='" + atyStartTime + '\'' +
                ", atyEndTime='" + atyEndTime + '\'' +
                ", atyPlace='" + atyPlace + '\'' +
                ", atyMembers='" + atyMembers + '\'' +
                ", atyContent='" + atyContent + '\'' +
                ", atyPlus='" + atyLikes + '\'' +
                ", atyComment='" + atyComment + '\'' +
                ", atyJoined='" + atyIsJoined + '\'' +
                ", atyPlused='" + atyIsLiked + '\'' +
                ", atyShare='" + atyShares + '\'' +
                ", atyAlbum=" + atyAlbum +
                '}';
    }
}
