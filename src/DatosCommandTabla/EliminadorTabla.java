/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatosCommandTabla;

import DatosCommandTabla.CommandTabla;
import DatosCommandTabla.GestorTabla;
import DatosCommandTabla.TablaNoExisteException;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author paul
 */
public class EliminadorTabla extends CommandTabla {

    public EliminadorTabla(GestorTabla gestorTabla) {
        super(gestorTabla);
    }

    @Override
    public String ejecutar(String texto) throws IOException, TablaNoExisteException {
        return gestorTabla.eliminarTabla(texto);
    }
 
}
