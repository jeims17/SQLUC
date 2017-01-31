/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatosCommandTabla;

import java.io.IOException;

/**
 *
 * @author paul
 */
public class CreadorTabla extends CommandTabla {

    public CreadorTabla(GestorTabla gestorTabla) {
        super(gestorTabla);
    }

    @Override
    public String ejecutar(String texto) throws IOException,
                                                CampoClaveInvalidoException,
                                                CamposRepetidosException,
                                                NumberFormatException,
                                                LongitudInvalidaException,
                                                TablaExisteException
    {
        return gestorTabla.crearTabla(texto);
    }
 
}
