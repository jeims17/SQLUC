/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ComandoChainOfResponsability;

import DatosCommandRegistro.CampoClaveVacioException;
import DatosCommandRegistro.CommandRegistro;
import DatosCommandRegistro.GestorRegistro;
import DatosCommandRegistro.NumeroCamposErroneoException;
import DatosCommandRegistro.RegistroExistenteException;
import DatosCommandRegistro.RegistroNoExisteException;
import DatosCommandTabla.CampoClaveInvalidoException;
import DatosCommandTabla.CampoNoExisteException;
import DatosCommandTabla.CamposRepetidosException;
import DatosCommandTabla.CommandTabla;
import DatosCommandTabla.GestorTablas;
import DatosCommandTabla.LongitudInvalidaException;
import DatosCommandTabla.TablaExisteException;
import DatosCommandTabla.TablaNoExisteException;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author paul
 */
public class GestorRegistroParser extends Parser {
    
    protected CommandRegistro commandRegistro;
    protected final GestorRegistro gestorRegistro;

    public GestorRegistroParser() {
        this.gestorRegistro = GestorTablas.getInstancia().getGestorRegistro();
    }
    
    protected String ejecutar(String comando) throws IOException,
                                                    FileNotFoundException,
                                                    TablaExisteException, 
                                                    TablaNoExisteException,
                                                    CampoClaveInvalidoException,
                                                    CamposRepetidosException,
                                                    NumberFormatException,
                                                    LongitudInvalidaException,
                                                    CampoNoExisteException,
                                                    NumeroCamposErroneoException,
                                                    RegistroExistenteException,
                                                    RegistroNoExisteException,
                                                    CampoClaveVacioException
    {
        return commandRegistro.ejecutar(comando);
    }
    
}
