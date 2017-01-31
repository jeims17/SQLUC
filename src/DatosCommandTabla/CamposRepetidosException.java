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
public class CamposRepetidosException extends Exception {

    public CamposRepetidosException(String campo) {
        super("El campo: " + campo + " está repetido, no se puede agregar");
    }
    
}
