/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatosCommandRegistro;

import DatosCommandTabla.TablaNoExisteException;
import java.io.IOException;

/**
 *
 * @author paul
 */
public class EliminadorRegistro extends CommandRegistro {

    public EliminadorRegistro(GestorRegistro gestorRegistro) {
        super(gestorRegistro);
    }

    @Override
    public String ejecutar(String texto) throws TablaNoExisteException, 
                                                RegistroNoExisteException, 
                                                IOException {
        return gestorRegistro.eliminarRegistro(texto);
    }
    
}
