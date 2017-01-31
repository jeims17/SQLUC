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
public class SinTablasException extends Exception {

    public SinTablasException() {
        super("No existían tablas en META_BD anterior, trabajando con el archivo existente");
    }
    
}
