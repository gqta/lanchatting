/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSide;

/**
 *
 * @author PST91
 */
public class QueueMessage {
    private int uID;
    private String mgs;

    public QueueMessage() {
    }

    public QueueMessage(int uID, String mgs) {
        this.uID = uID;
        this.mgs = mgs;
    }

    public String getMgs() {
        return mgs;
    }

    public int getuID() {
        return uID;
    }
    
    
}
