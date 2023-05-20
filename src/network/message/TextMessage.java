package network.message;

import java.io.Serializable;

public class TextMessage implements Serializable {
    private String data;
    public TextMessage(String data){
        this.data = data;
    }

    public String getData(){
        return this.data;
    }

    @Override
    public String toString() {
        return "TextMessage = '" + data + "'";
    }
}
