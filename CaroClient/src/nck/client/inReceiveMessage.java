/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nck.client;

import nck.common.KMessage;
import java.io.IOException;

/**
 *
 * @author Nguyen Cao Ky
 */
public interface inReceiveMessage {
    
    public void ReceiveMessage(KMessage msg) throws IOException;
}
