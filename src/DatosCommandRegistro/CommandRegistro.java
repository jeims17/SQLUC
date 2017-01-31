/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatosCommandRegistro;

import DatosCommandTabla.CampoNoExisteException;
import DatosCommandTabla.LongitudInvalidaException;
import DatosCommandTabla.TablaNoExisteException;
import java.io.IOException;

/**
 *
 * @author paul
 */
public abstract class CommandRegistro {
    protected final GestorRegistro gestorRegistro;
    
    public CommandRegistro(GestorRegistro gestorRegistro) {
        this.gestorRegistro = gestorRegistro;
    }
    
    public abstract String ejecutar(String texto) throws IOException, 
                                                    TablaNoExisteException,
                                                    NumeroCamposErroneoException,
                                                    LongitudInvalidaException,
                                                    RegistroExistenteException,
                                                    RegistroNoExisteException,
                                                    CampoNoExisteException,
                                                    CampoClaveVacioException;
}
