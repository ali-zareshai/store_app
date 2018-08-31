package model;

public class ChatMsgModel {
    private String msg_txt,msg_img,msg_video,sender,lock_msg,recive_check,read_check,date;

    public ChatMsgModel(String msg_txt, String msg_img, String msg_video, String sender, String lock_msg, String recive_check, String read_check, String date) {
        this.msg_txt = msg_txt;
        this.msg_img = msg_img;
        this.msg_video = msg_video;
        this.sender = sender;
        this.lock_msg = lock_msg;
        this.recive_check = recive_check;
        this.read_check = read_check;
        this.date = date;
    }

    public String getMsg_txt() {
        return msg_txt;
    }

    public void setMsg_txt(String msg_txt) {
        this.msg_txt = msg_txt;
    }

    public String getMsg_img() {
        return msg_img;
    }

    public void setMsg_img(String msg_img) {
        this.msg_img = msg_img;
    }

    public String getMsg_video() {
        return msg_video;
    }

    public void setMsg_video(String msg_video) {
        this.msg_video = msg_video;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getLock_msg() {
        return lock_msg;
    }

    public void setLock_msg(String lock_msg) {
        this.lock_msg = lock_msg;
    }

    public String getRecive_check() {
        return recive_check;
    }

    public void setRecive_check(String recive_check) {
        this.recive_check = recive_check;
    }

    public String getRead_check() {
        return read_check;
    }

    public void setRead_check(String read_check) {
        this.read_check = read_check;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
