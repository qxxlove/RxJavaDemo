package com.tjbool.httpwww.aidlclicentapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 描述 ：
 * 作者：Created by SEELE on 2018/7/3.
 * 邮箱：123123@163.com
 */

public class MessageBean   implements Parcelable {
    private String content;//需求内容
    private int level;//重要等级

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeInt(this.level);
    }

    //如果需要支持定向tag为out,inout，就要重写该方法
    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        this.content = dest.readString();
        this.level = dest.readInt();
    }

    protected MessageBean(Parcel in) {
        this.content = in.readString();
        this.level = in.readInt();
    }

    public MessageBean() { }

    public  static  final   Creator<MessageBean> CREATOR = new Creator<MessageBean>() {
        @Override
        public MessageBean createFromParcel(Parcel source) {
            return new MessageBean(source);
        }

        @Override
        public MessageBean[] newArray(int size) {
            return new MessageBean[size];
        }
    };


}
