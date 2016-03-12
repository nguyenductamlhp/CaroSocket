/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package offical.caro.data;

import java.io.Serializable;

/**
 *
 * @author Nguyen Cao Ky
 */
public class KMessage implements Serializable{
    private int type;
    private String str;

    public KMessage(int type, String str) {
        this.type = type;
        this.str = str;
    }
    
    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the str
     */
    public String getstr() {
        return str;
    }

    /**
     * @param str the str to set
     */
    public void setstr(String str) {
        this.str = str;
    }
    
    
}
