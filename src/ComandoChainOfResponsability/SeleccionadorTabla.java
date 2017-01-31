/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ComandoChainOfResponsability;

import DatosCommandTabla.CampoClaveInvalidoException;
import DatosCommandTabla.CampoNoExisteException;
import DatosCommandTabla.CamposRepetidosException;
import DatosCommandTabla.CommandTabla;
import DatosCommandTabla.GestorTabla;
import DatosCommandTabla.LongitudInvalidaException;
import DatosCommandTabla.TablaExisteException;
import DatosCommandTabla.TablaNoExisteException;
import GUI.MostrarTabla;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paul
 */
public class SeleccionadorTabla extends CommandTabla {
    
    public SeleccionadorTabla(GestorTabla gestorTabla) {
        super(gestorTabla);
    }

    @Override
    public String ejecutar(String texto) throws IOException,
                                                TablaNoExisteException,
                                                FileNotFoundException,
                                                CampoNoExisteException
    {
        MostrarTabla mostrarTabla = new MostrarTabla("Tabla seleccionar");
        mostrarTabla.generarTabla(gestorTabla.seleccionarTabla(texto));
        mostrarTabla.setVisible(true);
        
        int donde = texto.indexOf(" DONDE ");
        
        String nombreTabla = texto.substring(0, donde).trim();
        return "Se ha seleccionado de la tabla " + nombreTabla;
    }
}
