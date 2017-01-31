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
public class META_BDNuevoException extends Exception {

    public META_BDNuevoException() {
        super("No existía un META_BD anterior, se ha creado uno nuevo");
    }
    
}
