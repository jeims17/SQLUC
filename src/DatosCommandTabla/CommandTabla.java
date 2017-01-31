/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatosCommandTabla;

import DatosCommandTabla.CampoClaveInvalidoException;
import DatosCommandTabla.TablaNoExisteException;
import DatosCommandTabla.GestorTabla;
import DatosCommandTabla.LongitudInvalidaException;
import DatosCommandTabla.TablaExisteException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author paul
 */
public abstract class CommandTabla {
    
    protected final GestorTabla gestorTabla;
    
    public CommandTabla(GestorTabla gestorTabla) {
        this.gestorTabla = gestorTabla;
    }
    
    public abstract String ejecutar(String texto) throws IOException,
                                                FileNotFoundException,
                                                CampoClaveInvalidoException,
                                                CamposRepetidosException,
                                                CampoNoExisteException,
                                                NumberFormatException,
                                                LongitudInvalidaException,
                                                TablaExisteException,
                                                TablaNoExisteException,
                                                NumeroTablasException;
    
}
