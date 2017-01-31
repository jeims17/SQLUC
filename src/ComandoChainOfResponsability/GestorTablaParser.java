/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ComandoChainOfResponsability;

import DatosCommandTabla.CampoClaveInvalidoException;
import DatosCommandTabla.CampoNoExisteException;
import DatosCommandTabla.CamposRepetidosException;
import DatosCommandTabla.GestorTabla;
import DatosCommandTabla.GestorTablas;
import DatosCommandTabla.LongitudInvalidaException;
import DatosCommandTabla.TablaExisteException;
import DatosCommandTabla.TablaNoExisteException;
import DatosCommandTabla.CommandTabla;
import DatosCommandTabla.NumeroTablasException;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author paul
 */
public class GestorTablaParser extends Parser {
    
    protected CommandTabla commandTabla;
    protected final GestorTabla gestorTabla;

    public GestorTablaParser() {
        this.gestorTabla = GestorTablas.getInstancia().getGestorTabla();
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
                                                    NumeroTablasException
    {
        return commandTabla.ejecutar(comando);
    }
    
}
