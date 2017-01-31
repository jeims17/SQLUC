/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatosCommandRegistro;

/**
 *
 * @author paul
 */
public class RegistroNoExisteException extends Exception {

    public RegistroNoExisteException(String campoClave) {
        super("El registro con valor en campo clave: " + campoClave + " no existe");
    }
    
}
