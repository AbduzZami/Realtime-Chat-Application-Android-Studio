package com.example.pijeonchat;

public class Chat {
    String sender;
    String receiver;
    String message;
    String seenstatus ;


    public Chat(String sender, String receiver, String message , String seenstatus) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.seenstatus = seenstatus ;

    }

    public Chat() {
    }



    public String getSeenstatus() {
        return seenstatus;
    }

    public void setSeenstatus(String seenstatus) {
        this.seenstatus = seenstatus;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
