package org.ojothepojo.lifx.event;


import lombok.Data;
import lombok.Getter;

import org.ojothepojo.lifx.message.Message;

@Data
public class MessageReceivedEvent {

    @Getter
	private Message message;

    public MessageReceivedEvent(Message message) {
        this.message= message;
    }
    
    public Message getMessage(){
    	return this.message;
    }

}
