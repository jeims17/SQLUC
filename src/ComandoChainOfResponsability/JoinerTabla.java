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
import DatosCommandTabla.NumeroTablasException;
import DatosCommandTabla.TablaExisteException;
import DatosCommandTabla.TablaNoExisteException;
import GUI.MostrarTabla;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author paul
 */
public class JoinerTabla extends CommandTabla {
    
    public JoinerTabla(GestorTabla gestorTabla) {
        super(gestorTabla);
    }

    @Override
    public String ejecutar(String texto) throws IOException, 
                                                TablaNoExisteException,
                                                FileNotFoundException,
                                                CampoNoExisteException,
                                                NumeroTablasException
                                                
    {
        MostrarTabla mostrarTabla = new MostrarTabla("Tabla Unir");
        mostrarTabla.generarTabla(gestorTabla.unirTablas(texto));
        mostrarTabla.setVisible(true);
        
        int por = texto.indexOf(" POR ");
        
        String[] nombresTablas = texto.substring(0, por).split(",");
        return "Se ha unido la tabla: " + nombresTablas[0].trim() + " con " + nombresTablas[1].trim();
    }
}
