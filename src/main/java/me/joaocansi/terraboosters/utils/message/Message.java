package me.joaocansi.terraboosters.utils.message;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public abstract class Message implements IMessage {
    private String message;
    public Message(String message) {
        this.message = message;
    }
}
