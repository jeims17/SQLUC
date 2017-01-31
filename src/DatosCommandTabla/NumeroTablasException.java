/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatosCommandTabla;

/**
 *
 * @author paul
 */
public class NumeroTablasException extends Exception {

    public NumeroTablasException(int length) {
        super("No se puede unir " + length + " número de tablas");
    }
    
}
