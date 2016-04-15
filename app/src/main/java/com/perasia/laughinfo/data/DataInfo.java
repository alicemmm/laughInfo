package com.perasia.laughinfo.data;


import android.os.Parcel;
import android.os.Parcelable;

public class DataInfo implements Parcelable {
    private int id;
    private String iconUrl;
    private String title;
    private String content;
    private String time;
    private int type;
    private int allPage;
    private int allNum;

    public DataInfo() {

    }

    public DataInfo(Parcel source) {
        id = source.readInt();
        iconUrl = source.readString();
        title = source.readString();
        content = source.readString();
        time = source.readString();
        type = source.readInt();
        allPage = source.readInt();
        allNum = source.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(iconUrl);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(time);
        dest.writeInt(type);
        dest.writeInt(allPage);
        dest.writeInt(allNum);
    }

    public static final Creator<DataInfo> CREATOR = new Creator<DataInfo>() {
        @Override
        public DataInfo createFromParcel(Parcel source) {
            return new DataInfo(source);
        }

        @Override
        public DataInfo[] newArray(int size) {
            return new DataInfo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public int getAllPage() {
        return allPage;
    }

    public void setAllPage(int allPage) {
        this.allPage = allPage;
    }

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }
}
