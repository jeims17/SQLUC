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
public class CampoClaveInvalidoException extends Exception {

    public CampoClaveInvalidoException(String campoClave) {
        super("No existe un campo con el nombre: " + campoClave);
    }
    
}
