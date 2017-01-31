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
public class TablaExisteException extends Exception {

    public TablaExisteException(String tabla) {
        super("Ya existe la tabla " + tabla);
    }
    
}
