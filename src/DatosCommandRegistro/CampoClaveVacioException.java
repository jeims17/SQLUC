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
public class CampoClaveVacioException extends Exception {

    public CampoClaveVacioException() {
        super("El campo clave no puede estar vacío");
    }
    
}
