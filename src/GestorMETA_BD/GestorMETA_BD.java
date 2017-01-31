/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestorMETA_BD;

import DatosCommandTabla.GestorTablas;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author paul
 */
public class GestorMETA_BD {
    
    public void crearMETA_BD(File ARCHIVO) throws IOException, SinTablasException, META_BDSobreescritoException, META_BDNuevoException {
        
        boolean alreadyExists = ARCHIVO.exists();
        
        if ( alreadyExists ) {
            List<String[]> tablas = GestorTablas.getInstancia(ARCHIVO).crearListTablas(ARCHIVO);
            
            if (tablas.isEmpty())
                throw new SinTablasException();
            
            for (int i = 0; i < tablas.size(); i++) {     
                String nombreArchivoTabla = "archivos/" + tablas.get(i)[0] + ".csv";
                File archivo = new File(nombreArchivoTabla);
                archivo.delete();
            }
            
        }
        
        if ( !ARCHIVO.getParentFile().exists() ) {
            ARCHIVO.getParentFile().mkdirs();
            ARCHIVO.createNewFile();
            throw new META_BDNuevoException();
        } else if ( !ARCHIVO.exists() ) {
            ARCHIVO.createNewFile();
            throw new META_BDNuevoException();
        }
        ARCHIVO.delete();
        ARCHIVO.createNewFile();
        
        throw new META_BDSobreescritoException();
        
    }
    
    public void recuperarMETA_BD(File ARCHIVO) throws IOException, META_BDNuevoException, META_BDRecuperadoException, SinTablasException {
             
        boolean parentAlreadyExists = ARCHIVO.getParentFile().exists();
        boolean alreadyExists = ARCHIVO.exists();
           
        if ( !parentAlreadyExists || !alreadyExists ) {
            ARCHIVO.getParentFile().mkdirs();
            ARCHIVO.createNewFile();
            GestorTablas.getInstancia(ARCHIVO);
            throw new META_BDNuevoException();
        } else if ( GestorTablas.getInstancia(ARCHIVO).recuperarTablas(ARCHIVO) ){
            throw new META_BDRecuperadoException();
        } else {
            throw new SinTablasException();
        }          
    }
    
}
