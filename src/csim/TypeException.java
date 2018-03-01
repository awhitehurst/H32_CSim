/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package csim;

/**
 *
 * @author Alan
 */
public class TypeException extends Exception {
    /**
     * This method is a builder for TypeException class with no parameters.
     * Uses builder of superclass.
     */
    public TypeException() {
    }
    /**
     * This method is a builder for TypeException class with one parameter.
     * @param String.
     * Uses builder of superclass.
     */
    public TypeException(String message) {
        super(message);
    }
    /**
     * This method is a builder for TypeException class with two parameters.
     * @param String this is the first parameter.
     * @param Throwable this is the second parameter.
     * Uses builder of superclass.
     */
    public TypeException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * This method is a builder for TypeException class with one parameter.
     * @param Throwable.
     * Uses builder of superclass.
     */
    public TypeException(Throwable cause) {
        super(cause);
    }
    /**
     * This method is a builder for TypeException class with four parameters.
     * @param String this is the first parameter.
     * @param Throwable this is the second parameter.
     * @param boolean this is the third parameter.
     * @param boolean this is the fourth parameter.
     * Uses builder of superclass.
     */
    public TypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
