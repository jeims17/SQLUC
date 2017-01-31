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
public class NumeroCamposErroneoException extends Exception {

    public NumeroCamposErroneoException(int length, Integer nCampos) {
        super("Error, se ha ingresado " + length + " campos, pero son necesarios " +
                nCampos);
    }
    
}
