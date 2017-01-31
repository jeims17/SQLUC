/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestorMETA_BD;

/**
 *
 * @author paul
 */
public class META_BDSobreescritoException extends Exception {

    public META_BDSobreescritoException() {
        super("El archivo META_BD ha sido sobreescrito");
    }
    
}
