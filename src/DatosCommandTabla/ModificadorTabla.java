/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatosCommandTabla;

import DatosCommandTabla.CommandTabla;
import DatosCommandTabla.GestorTabla;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author paul
 */
public class ModificadorTabla extends CommandTabla {

    public ModificadorTabla(GestorTabla gestorTabla) {
        super(gestorTabla);
    }

    @Override
    public String ejecutar(String texto) throws FileNotFoundException, 
                                                IOException, 
                                                TablaNoExisteException, 
                                                CampoNoExisteException, 
                                                LongitudInvalidaException,  
                                                CamposRepetidosException  
    {
        return gestorTabla.modificarTabla(texto);
    }

    
    
}
