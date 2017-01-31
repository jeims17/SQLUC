/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ComandoChainOfResponsability;

import DatosCommandRegistro.CampoClaveVacioException;
import DatosCommandRegistro.CreadorRegistro;
import DatosCommandRegistro.EliminadorRegistro;
import DatosCommandRegistro.NumeroCamposErroneoException;
import DatosCommandRegistro.RegistroExistenteException;
import DatosCommandRegistro.RegistroNoExisteException;
import DatosCommandTabla.CampoClaveInvalidoException;
import DatosCommandTabla.CampoNoExisteException;
import DatosCommandTabla.CamposRepetidosException;
import DatosCommandTabla.LongitudInvalidaException;
import DatosCommandTabla.NumeroTablasException;
import DatosCommandTabla.TablaExisteException;
import DatosCommandTabla.TablaNoExisteException;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author paul
 */
public class EliminarRegistroParser extends GestorRegistroParser {
    
    public EliminarRegistroParser(Parser sucesor) {
        commandRegistro = new EliminadorRegistro(gestorRegistro);
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
        if ( puedeManejarComando(comando, "ELIMINAR REGISTRO ") ) {
            int longitud = comando.length();
            if ( !puedeManejarComando(comando, "ELIMINAR REGISTRO ", "CLAVE") ) {
                throw new SinRegistroException();
            } else if ( !puedeManejarComando(comando, " CLAVE ") ) {
                throw new SinClaveException();
            } else {
                return ejecutar(comando.substring(17, comando.length()));
            }
        }
        else {
            return super.analizar(comando);
        }
    }
    
}
