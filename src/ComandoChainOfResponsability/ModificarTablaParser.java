/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ComandoChainOfResponsability;

import DatosCommandRegistro.CampoClaveVacioException;
import DatosCommandRegistro.NumeroCamposErroneoException;
import DatosCommandRegistro.RegistroExistenteException;
import DatosCommandRegistro.RegistroNoExisteException;
import DatosCommandTabla.CampoClaveInvalidoException;
import DatosCommandTabla.CampoNoExisteException;
import DatosCommandTabla.CamposRepetidosException;
import DatosCommandTabla.EliminadorTabla;
import DatosCommandTabla.LongitudInvalidaException;
import DatosCommandTabla.ModificadorTabla;
import DatosCommandTabla.NumeroTablasException;
import DatosCommandTabla.TablaExisteException;
import DatosCommandTabla.TablaNoExisteException;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author paul
 */
public class ModificarTablaParser extends GestorTablaParser {
    
    public ModificarTablaParser(Parser sucesor) {
        commandTabla = new ModificadorTabla(gestorTabla);
        this.setSucesor(sucesor);
    }
    
    @Override
    public String analizar(String comando) throws IOException,
                                                FileNotFoundException,
                                                ComandoInvalidoException,
                                                SinCamposException,
                                                SinClaveException,
                                                SinLongitudException,
                                                SinCampoException,
                                                SinCampoNuevoException,
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
                                                SinRegistroException,
                                                SinTablaException,
                                                NumeroTablasException,
                                                CampoClaveVacioException,
                                                SinValorDeCampoException
    {
        if ( puedeManejarComando(comando, "MODIFICAR TABLA ")) {
            int longitud = comando.length();
            if ( !puedeManejarComando(comando, "MODIFICAR TABLA ", "CAMPO") ) {
                throw new SinTablaException();
            } else if ( !puedeManejarComando(comando, " CAMPO ", "POR") ) {
                throw new SinCampoException();
            } else if ( !puedeManejarComando(comando, " POR ") ) {        
                throw new SinCampoNuevoException();
            } else {
                return ejecutar(comando.substring(15, comando.length()));
            }
        } else {
            return super.analizar(comando);
        }
    }
    
}
