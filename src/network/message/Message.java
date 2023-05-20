package network.message;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private String message;
    public Message(){
        message = "";
    }
    public Message(String message){
        this.message = message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
