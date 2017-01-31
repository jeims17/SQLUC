/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ComandoChainOfResponsability;

/**
 *
 * @author paul
 */
public class SinLongitudException extends Exception {

    public SinLongitudException() {
        super("No se ha especificado la longitud de los campos");
    }
    
}
