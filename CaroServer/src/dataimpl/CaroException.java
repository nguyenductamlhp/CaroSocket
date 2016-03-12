
package dataimpl;

public class CaroException extends Exception{
        
        public CaroException(String reason){
                super(reason);
        }
        public static void throwIt(String reason) throws CaroException{
                throw new CaroException(reason);
        }
}