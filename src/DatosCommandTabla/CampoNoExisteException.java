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
public class CampoNoExisteException extends Exception {

    public CampoNoExisteException(String nombreCampo) {
        super("El campo: " + nombreCampo + " no existe en la tabla");
    }
    
}
