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
public class RegistroExistenteException extends Exception {

    public RegistroExistenteException(String campo) {
        super("El registro con valor en su campo clave: " + campo + " ya existe");
    }
    
}
