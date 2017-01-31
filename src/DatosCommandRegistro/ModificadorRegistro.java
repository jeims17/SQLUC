/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatosCommandRegistro;

import DatosCommandTabla.CampoNoExisteException;
import DatosCommandTabla.LongitudInvalidaException;
import DatosCommandTabla.TablaNoExisteException;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author paul
 */
public class ModificadorRegistro extends CommandRegistro {

    public ModificadorRegistro(GestorRegistro gestorRegistro) {
        super(gestorRegistro);
    }

    @Override
    public String ejecutar(String texto) throws TablaNoExisteException, 
                                                RegistroNoExisteException, 
                                                IOException, 
                                                FileNotFoundException, 
                                                CampoNoExisteException, 
                                                RegistroExistenteException, 
                                                LongitudInvalidaException {
        return gestorRegistro.modificarRegistro(texto);
    }
    
}
