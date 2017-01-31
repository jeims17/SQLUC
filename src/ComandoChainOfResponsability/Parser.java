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
import DatosCommandTabla.NumeroTablasException;
import DatosCommandTabla.TablaExisteException;
import DatosCommandTabla.TablaNoExisteException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author paul
 */
public class Parser {
    
    private Parser sucesor;
   
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
        if ( getSucesor() != null ){
            return getSucesor().analizar(comando);
        }
        else {
            throw new ComandoInvalidoException();
        }
    }

    protected boolean puedeManejarComando(String comando, String formato, String siguiente) {
        return (comando != null) && (comando.contains(formato)) && validarComando(comando, formato, siguiente);
    }
    
    protected boolean puedeManejarComando(String comando, String formato) {
        return (comando != null) && (comando.contains(formato)) && validarComando(comando, formato);
    }
    
    private boolean validarComando(String comando, String sentencia, String siguiente) {
        int longitud = comando.indexOf(sentencia) + sentencia.length();
        while ( longitud < comando.length() ) {
            if ( comando.charAt(longitud++) != ' ' ) {
                if ( comando.length() - (--longitud) >= siguiente.length()) {
                    return !siguiente.equals(comando.substring(longitud, longitud + siguiente.length()));
                }
            }
        }
        return false;
    }
    
    protected boolean validarComando(String comando, String sentencia) {
        int longitud = comando.indexOf(sentencia) + sentencia.length();
        while ( longitud < comando.length() ) {
            if ( comando.charAt(longitud++) != ' ' ) {
                return true;
            }
        }
        return false;
    }
    
    Parser getSucesor() {
        return sucesor;
    }

    void setSucesor(Parser sucesor) {
        this.sucesor = sucesor;
    }
    
}
