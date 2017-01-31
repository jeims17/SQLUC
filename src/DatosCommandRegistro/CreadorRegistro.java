/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatosCommandRegistro;

import DatosCommandTabla.LongitudInvalidaException;
import DatosCommandTabla.TablaNoExisteException;
import java.io.IOException;

/**
 *
 * @author paul
 */
public class CreadorRegistro extends CommandRegistro {

    public CreadorRegistro(GestorRegistro gestorRegistro) {
        super(gestorRegistro);
    }

    @Override
    public String ejecutar(String texto) throws IOException, 
                                                TablaNoExisteException,
                                                NumeroCamposErroneoException,
                                                LongitudInvalidaException,
                                                RegistroExistenteException,
                                                CampoClaveVacioException {
        return gestorRegistro.crearRegistro(texto);
    }
    
}
