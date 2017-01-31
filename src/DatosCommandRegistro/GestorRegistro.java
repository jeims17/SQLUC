/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatosCommandRegistro;

import DatosCommandTabla.CampoNoExisteException;
import DatosCommandTabla.GestorTablas;
import DatosCommandTabla.LongitudInvalidaException;
import DatosCommandTabla.Tabla;
import DatosCommandTabla.TablaNoExisteException;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 *
 * @author paul
 */
public class GestorRegistro {
    private final File ARCHIVO;

    public GestorRegistro(File ARCHIVO) {
        this.ARCHIVO = ARCHIVO;
    }
    
    public String crearRegistro(String texto) throws IOException, 
                                                    TablaNoExisteException,
                                                    NumeroCamposErroneoException,
                                                    LongitudInvalidaException,
                                                    RegistroExistenteException, 
                                                    CampoClaveVacioException 
    {
        int valor = texto.indexOf(" VALOR ");
        
        String nombreTabla = texto.substring(0, valor).trim();
        Tabla tabla = GestorTablas.getInstancia().getTabla(nombreTabla);      
        if ( tabla == null ) {
            throw new TablaNoExisteException(nombreTabla);
        }
               
        String[] campos = texto.substring(valor + 7, texto.length()).split(",");
        if ( campos.length != tabla.getnCampos() ) {
            throw new NumeroCamposErroneoException(campos.length, tabla.getnCampos());
        }
        if ( campos[tabla.getCampoClave()].trim().isEmpty() ) {
            throw new CampoClaveVacioException();
        }
        
        String formato = "%1$-" + tabla.getLongitud() + "s";
        for (int i=0; i<campos.length; i++) {
            String campo = String.format(formato, campos[i].trim());
            if ( campo.getBytes().length > tabla.getLongitud() )
                throw new LongitudInvalidaException(campo, campo.getBytes().length);
        }
        
        if ( tabla.hasRegistro(campos[tabla.getCampoClave()]) ) {
            throw new RegistroExistenteException(campos[tabla.getCampoClave()]);
        }
        
        RandomAccessFile archivoRAF = new RandomAccessFile("archivos/" + nombreTabla + ".csv", "rw");
        String linea;
        long punteroAnt = archivoRAF.getFilePointer();
        long punteroAct;
        
        if ( tabla.hayEliminados() ) {           
            while ((linea = archivoRAF.readLine()) != null) {
                punteroAct = archivoRAF.getFilePointer();

                if ( linea.trim().isEmpty() ) {
                    archivoRAF.seek(punteroAnt);
                    for (int i=0; i<campos.length; i++) {
                        String campo = campos[i].trim();
                        if ( i != (campos.length - 1) ) {
                            archivoRAF.write(String.format(formato, campo).getBytes());
                            archivoRAF.write(",".getBytes());
                        } else
                            archivoRAF.write(String.format(formato, campo).getBytes());
                    }
                    tabla.addPosRegistro(campos[tabla.getCampoClave()], punteroAnt);
                    archivoRAF.close();
                    break;
                }
                punteroAnt = punteroAct;
            }
            tabla.borrarEliminados();
        } else {
            tabla.addPosRegistro(campos[tabla.getCampoClave()], archivoRAF.length());
            archivoRAF.close();

            FileWriter archivo = new FileWriter("archivos/" + nombreTabla + ".csv", true);
            CsvWriter csvOutput = new CsvWriter(archivo, ',');
            csvOutput.setUseTextQualifier(false);

            for (int i=0; i<campos.length; i++) {
                String campo = campos[i].trim();
                csvOutput.write(String.format(formato, campo), true);
            }
            csvOutput.endRecord();
            csvOutput.close();
        }
        
        archivoRAF = new RandomAccessFile(ARCHIVO, "rw");
        punteroAnt = archivoRAF.getFilePointer();

        while ((linea = archivoRAF.readLine()) != null) {
            punteroAct = archivoRAF.getFilePointer();

            String[] tablaBD = linea.split(",");
            if ( linea.charAt(0)=='0' && tablaBD[1].equals(nombreTabla) ) {
                archivoRAF.seek(punteroAnt + tablaBD[1].getBytes().length + 
                                "0".getBytes().length + ",".getBytes().length*2 );
                archivoRAF.write(String.valueOf(Integer.parseInt(tablaBD[2].trim()) + 1).getBytes());
                break;
            }
            punteroAnt = punteroAct;
        }
        archivoRAF.close();
        
        return "Se ha creado el registro en la tabla: " + nombreTabla;        
    }
    
    public String modificarRegistro(String texto) throws TablaNoExisteException, 
                                                        FileNotFoundException, 
                                                        RegistroNoExisteException, 
                                                        IOException, 
                                                        CampoNoExisteException,
                                                        RegistroExistenteException,
                                                        LongitudInvalidaException
    {
        int clave = texto.indexOf(" CLAVE ");
        int campo = texto.indexOf(" CAMPO ");
        int por = texto.indexOf(" POR ");
        
        String nombreTabla = texto.substring(0, clave).trim();
        Tabla tabla = GestorTablas.getInstancia().getTabla(nombreTabla);      
        if ( tabla == null ) {
            throw new TablaNoExisteException(nombreTabla);
        }
        
        String campoClave = texto.substring(clave + 7, campo).trim();
        if ( !tabla.hasRegistro(campoClave) ) {
            throw new RegistroNoExisteException(campoClave);
        }
        
        String nombreCampo = texto.substring(campo + 7, por).trim();
        String formato = "%1$-" + tabla.getLongitud() + "s";
        CsvReader tablasImport = new CsvReader(new FileReader("archivos/" + nombreTabla + ".csv"));
        tablasImport.readHeaders();
        tablasImport.setUseTextQualifier(false);
        String[] campos = tablasImport.getHeaders();
        if ( !Arrays.asList(campos).contains(nombreCampo) ) {
            tablasImport.close();
            throw new CampoNoExisteException(nombreCampo);
        }
        tablasImport.close();
        
        RandomAccessFile archivoRAF = new RandomAccessFile("archivos/" + nombreTabla + ".csv", "rw");
        String campoNuevo = texto.substring(por + 5, texto.length()).trim();
        if ( campoNuevo.getBytes().length > tabla.getLongitud() ) {
            throw new LongitudInvalidaException(campoNuevo, tabla.getLongitud());
        }
        
        boolean esCampoClave = nombreCampo.equals(campos[tabla.getCampoClave()].trim());
        if ( esCampoClave && tabla.hasRegistro(campoNuevo)) {
            throw new RegistroExistenteException(campoNuevo);
        } else if ( esCampoClave ) {
            long punteroAct = tabla.getRegistro(campoClave);
            archivoRAF.seek( punteroAct + 
                            (tabla.getCampoClave()*(tabla.getLongitud()+1)) );
            archivoRAF.write(campoNuevo.getBytes());
            tabla.removePosRegistro(campoClave);
            tabla.addPosRegistro(campoNuevo, punteroAct);
            
        } else {
            archivoRAF.seek(tabla.getRegistro(campoClave) + 
                            (Arrays.asList(campos).indexOf(nombreCampo) * 
                                    (tabla.getLongitud()+1)) );
            archivoRAF.write(campoNuevo.getBytes());
        }
        archivoRAF.close();
        
        return "Se ha modificado el registro de la tabla: " + nombreTabla;
    }
    
    public String eliminarRegistro(String texto) throws TablaNoExisteException, 
                                                        RegistroNoExisteException,
                                                        FileNotFoundException,
                                                        IOException
    {
        int clave = texto.indexOf(" CLAVE ");
        
        String nombreTabla = texto.substring(0, clave).trim();
        Tabla tabla = GestorTablas.getInstancia().getTabla(nombreTabla);      
        if ( tabla == null ) {
            throw new TablaNoExisteException(nombreTabla);
        }
        
        String campoClave = texto.substring(clave + 7, texto.length()).trim();
        if ( !tabla.hasRegistro(campoClave) ) {
            throw new RegistroNoExisteException(campoClave);
        }
        
        RandomAccessFile archivoRAF = new RandomAccessFile("archivos/" + nombreTabla + ".csv", "rw");
        //CsvReader tablasImport = new CsvReader(new FileReader("archivos/" + nombreTabla + ".csv"));
        //tablasImport.setUseTextQualifier(false);
        //tablasImport.readHeaders();
        //int nCampos = tablasImport.getHeaders().length;
        int lenFormat = (tabla.getnCampos()-1)*(tabla.getLongitud()+1) + tabla.getLongitud();
        String formato = "%1$-" + lenFormat + "s";
        archivoRAF.seek(tabla.getRegistro(campoClave));
        archivoRAF.write(String.format(formato, "").getBytes());
        tabla.addPosicion(tabla.getRegistro(campoClave));
        tabla.removePosRegistro(campoClave);
         
        // Restar de los registros
        archivoRAF = new RandomAccessFile(ARCHIVO, "rw");
        long punteroAnt = archivoRAF.getFilePointer();
        long punteroAct;
        String linea;
        while ((linea = archivoRAF.readLine()) != null) {
            punteroAct = archivoRAF.getFilePointer();

            String[] tablaBD = linea.split(",");
            if ( linea.charAt(0)=='0' && tablaBD[1].equals(nombreTabla) ) {
                archivoRAF.seek(punteroAnt + tablaBD[1].getBytes().length + 
                                "0".getBytes().length + ",".getBytes().length*2 );
                archivoRAF.write(String.valueOf(Integer.parseInt(tablaBD[2].trim()) - 1).getBytes());
                break;
            }
            punteroAnt = punteroAct;
        }
        archivoRAF.close();
        
        return "Se ha eliminado el registro de la tabla: " + nombreTabla;
    }
    
}
