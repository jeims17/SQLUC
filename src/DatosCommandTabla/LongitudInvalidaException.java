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
public class LongitudInvalidaException extends Exception {

    public LongitudInvalidaException(String campo, int longitudCamposI) {
        super("La longitud del campo: " + campo +
                " es mayor a la longitud establecida: " + longitudCamposI );
    }
    
}
