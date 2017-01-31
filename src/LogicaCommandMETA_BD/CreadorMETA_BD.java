/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaCommandMETA_BD;

import GestorMETA_BD.META_BDSobreescritoException;
import GestorMETA_BD.SinTablasException;
import GestorMETA_BD.GestorMETA_BD;
import GestorMETA_BD.META_BDNuevoException;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author paul
 */
public class CreadorMETA_BD extends CommandMETA_BD {

    public CreadorMETA_BD(GestorMETA_BD gestorMETA_BD) {
        super(gestorMETA_BD);
    }
    
    @Override
    public void ejecutar(final File ARCHIVO) throws IOException, 
                                                    SinTablasException, 
                                                    META_BDSobreescritoException, 
                                                    META_BDNuevoException 
    {
        gestorMETA_BD.crearMETA_BD(ARCHIVO);
    }
    
}
