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
public class SinCampoException extends Exception {

    public SinCampoException() {
        super("No se ha especificado el campo a modificar");
    }
    
}
