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
import DatosCommandTabla.LongitudInvalidaException;
import DatosCommandTabla.TablaExisteException;
import DatosCommandTabla.TablaNoExisteException;
import DatosCommandTabla.CreadorTabla;
import DatosCommandTabla.NumeroTablasException;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author paul
 */
public class CrearTablaParser extends GestorTablaParser {

    public CrearTablaParser() {
        commandTabla = new CreadorTabla(gestorTabla);
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
        if ( puedeManejarComando(comando, "CREAR TABLA ") ) {
            int longitud = comando.length();
            if ( !puedeManejarComando(comando, "CREAR TABLA ", "CAMPOS") ) {
                throw new SinTablaException();
            } else if ( !puedeManejarComando(comando, " CAMPOS ", "CLAVE") ) {
                throw new SinCamposException();
            } else if ( !puedeManejarComando(comando, " CLAVE ", "LONGITUD") ) {        
                throw new SinClaveException();
            } else if ( !puedeManejarComando(comando, " LONGITUD ") ) {
                throw new SinLongitudException();
            } else {
                return ejecutar(comando.substring(11, comando.length()));
            }
        }
        else {
            return super.analizar(comando);
        }
    }
    
}
