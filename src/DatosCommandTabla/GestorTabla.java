/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatosCommandTabla;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 *
 * @author paul
 */
public class GestorTabla {
    private final File ARCHIVO;

    public GestorTabla(File ARCHIVO) {
        this.ARCHIVO = ARCHIVO;
    }
    
    public String crearTabla(String texto) throws IOException, 
                                                TablaExisteException, 
                                                CampoClaveInvalidoException,
                                                NumberFormatException,
                                                LongitudInvalidaException,
                                                CamposRepetidosException
    {

        FileWriter archivo = new FileWriter(ARCHIVO, true);
        CsvWriter csvOutput = new CsvWriter(archivo, ',');
        csvOutput.setUseTextQualifier(false);

        int campos = texto.indexOf(" CAMPOS ");
        int clave = texto.indexOf(" CLAVE ");
        int longitud = texto.indexOf(" LONGITUD ");
        
        String nombreTabla = texto.substring(0, campos).trim();
        List<String> listCampos = Arrays.asList(texto.substring(campos + 8, clave).split(","));
        Set<String> setCampos = new HashSet<>();
        final ListIterator<String> lista = listCampos.listIterator();
        while ( lista.hasNext() ) {
            String campo = lista.next().trim();
            if ( setCampos.add(campo) )
                lista.set(campo);
            else
                throw new CamposRepetidosException(campo);
        }
        
        String campoClave = texto.substring(clave + 7, longitud).trim();
        if ( !listCampos.contains(campoClave) ) {
            throw new CampoClaveInvalidoException(campoClave);
        }
        
        String longitudCampos = texto.substring(longitud + 10, texto.length()).trim();
        Integer longitudCamposI = Integer.parseInt(longitudCampos);
        for ( String campo : listCampos ) {
            if ( campo.getBytes().length > longitudCamposI )
                throw new LongitudInvalidaException(campo, longitudCamposI);
        }

        if ( GestorTablas.getInstancia().addNombreTabla( 
                                        new Tabla(nombreTabla, 
                                        listCampos.indexOf(campoClave), 
                                        longitudCamposI,
                                        listCampos.size()),listCampos) ) 
        {
            throw new TablaExisteException(nombreTabla);
        } else {
            String formato = "%1$-" + longitudCamposI + "s";
            csvOutput.write("0");
            csvOutput.write(nombreTabla);
            csvOutput.write("0        ", true);
            csvOutput.write(String.format(formato, campoClave), true);
            csvOutput.write(longitudCampos);
            
            Iterator<String> camposIterator = listCampos.iterator();          
            String campo = camposIterator.next();
            csvOutput.write(String.format(formato, campo), true);
            csvOutput.setDelimiter(';');
            
            while (camposIterator.hasNext()) {
                campo = camposIterator.next();
                if (!campo.isEmpty())
                    csvOutput.write(String.format(formato, campo), true);
            }
            csvOutput.endRecord();
        }              
        
        csvOutput.close();       
        return "Se ha creado la tabla: " + nombreTabla;
    }
    
    private boolean nombreCampoVerificador(RandomAccessFile archivoRAF, String nombreCampo) throws IOException {        
        for (int i = 1; i < nombreCampo.length(); i++) 
        { 
             byte caracter = archivoRAF.readByte(); 
             if (caracter != nombreCampo.charAt(i)) return false; 
        }   
        return true; 
    } 
    
    private int aparicionesCadena(String texto, String cadena) {
        if ( texto.lastIndexOf(cadena) == -1 )
            return 0;
        else
            return aparicionesCadena(texto.substring(texto.indexOf(cadena) + 
                                    cadena.length(), texto.length()), cadena) + 1;
    }
    
    public String modificarTabla(String texto) throws FileNotFoundException, 
                                                    IOException, 
                                                    TablaNoExisteException, 
                                                    CampoNoExisteException, 
                                                    LongitudInvalidaException, 
                                                    CamposRepetidosException 
    {   
        int campo = texto.indexOf(" CAMPO ");
        int por = texto.indexOf(" POR ");
        
        String nombreTabla = texto.substring(0, campo).trim();
        String nombreCampo = texto.substring(campo + 7, por).trim();
        String nombreCampoNuevo = texto.substring(por + 5, texto.length()).trim();
        
        RandomAccessFile archivoRAF = new RandomAccessFile(ARCHIVO, "rw");
        CsvReader tablasImport = new CsvReader(new FileReader(ARCHIVO));
        tablasImport.setUseTextQualifier(false);
        long punteroAnt = archivoRAF.getFilePointer();
        long punteroAct;
        String linea;
        
        while ( (linea = archivoRAF.readLine()) != null ) {
            punteroAct = archivoRAF.getFilePointer();
            tablasImport.readRecord();
            
            if ( linea.charAt(0) == '0' && tablasImport.getValues()[1].equals(nombreTabla) ) {
                Integer longitud = GestorTablas.getInstancia().getLongitud(nombreTabla);
                if ( nombreCampoNuevo.getBytes().length > longitud ) {
                    throw new LongitudInvalidaException(nombreCampoNuevo, longitud);
                }
                String formato = "%1$-" + longitud + "s";
                boolean existeCampo = Arrays.asList(tablasImport.getValues()[5].split(";")).
                                        contains(String.format(formato, nombreCampo));
                boolean campoRepetido = Arrays.asList(tablasImport.getValues()[5].split(";")).
                                        contains(String.format(formato, nombreCampoNuevo));
                if ( existeCampo && !campoRepetido ) {
                    int apariciones = aparicionesCadena(linea, nombreCampo);                  
                    archivoRAF.seek(punteroAnt);
                    byte caracter;
                    while (true) {
                        punteroAct = archivoRAF.getFilePointer();
                        if ( (caracter = archivoRAF.readByte()) == nombreCampo.charAt(0) ) {
                            boolean verificador = nombreCampoVerificador(archivoRAF, nombreCampo);
                            if ( verificador && apariciones == 1 ) 
                                break;
                            else if ( verificador && apariciones == 2 ) {
                                Tabla tabla = GestorTablas.getInstancia().getTabla(nombreTabla);
                                archivoRAF.seek(punteroAct);
                                archivoRAF.write(String.format(formato, nombreCampoNuevo).getBytes());
                                apariciones--;
                                //modificarCampoNuevo
                                tabla.setCampoClave(Arrays.asList(tablasImport.getValues()[5].split(";")).
                                        indexOf(tablasImport.getValues()[3]));
                                
                            }
                        }
                    }
                    archivoRAF.seek(punteroAct);
                    archivoRAF.write(String.format(formato, nombreCampoNuevo).getBytes());                    
                    archivoRAF.close();
                    
                    archivoRAF = new RandomAccessFile("archivos/" + nombreTabla + ".csv", "rw");
                    while (true) {
                        punteroAct = archivoRAF.getFilePointer();
                        if ( (caracter = archivoRAF.readByte()) == nombreCampo.charAt(0) ) {
                            if ( nombreCampoVerificador(archivoRAF, nombreCampo)  ) 
                                break;
                        }
                    }
                    archivoRAF.seek(punteroAct);
                    archivoRAF.write(String.format(formato, nombreCampoNuevo).getBytes());                    
                    archivoRAF.close();
                    
                    return "Se ha modificado la tabla: " + nombreTabla;
                } else if ( campoRepetido) {
                    throw new CamposRepetidosException(nombreCampoNuevo);
                }else {
                    throw new CampoNoExisteException(nombreCampo);
                }             
            } 
            punteroAnt = punteroAct;
        } 
        throw new TablaNoExisteException(nombreTabla);
    }
    
    public String eliminarTabla(String nombreTabla) throws FileNotFoundException, IOException, TablaNoExisteException {
        
        String nombreArchivoTabla = "archivos/" + nombreTabla + ".csv";
        File archivo = new File(nombreArchivoTabla);
        
        if ( archivo.delete() ) {
            String linea = null;
            RandomAccessFile archivoRAF = new RandomAccessFile(ARCHIVO, "rw");
            long punteroAnt = archivoRAF.getFilePointer();
            long punteroAct;
            
            while ((linea = archivoRAF.readLine()) != null) {
                punteroAct = archivoRAF.getFilePointer();

                if ( linea.charAt(0)=='0' && linea.split(",")[1].equals(nombreTabla) ) {
                    archivoRAF.seek(punteroAnt);
                    archivoRAF.write("1".getBytes());
                    GestorTablas.getInstancia().removeTabla(nombreTabla);
                    archivoRAF.close();
                    return "Se ha eliminado la tabla: " + nombreTabla;
                }
                punteroAnt = punteroAct;
            }
            archivoRAF.close();         
        }
        throw new TablaNoExisteException(nombreTabla);
        
    }
    
    public List<String[]> seleccionarTabla(String texto) throws TablaNoExisteException, 
                                                        FileNotFoundException, 
                                                        IOException,
                                                        CampoNoExisteException
    {
        int donde = texto.indexOf(" DONDE ");
        int igual = texto.indexOf("=");
        
        String nombreTabla = texto.substring(0, donde).trim();
        Tabla tabla = GestorTablas.getInstancia().getTabla(nombreTabla);      
        if ( tabla == null ) {
            throw new TablaNoExisteException(nombreTabla);
        }
        
        String nombreCampo = texto.substring(donde + 7, igual).trim();
        CsvReader tablasImport = new CsvReader(new FileReader("archivos/" + nombreTabla + ".csv"));
        tablasImport.setUseTextQualifier(false);
        tablasImport.readHeaders();
        List<String> campos = Arrays.asList(tablasImport.getHeaders());
        if ( !campos.contains(nombreCampo) ) {
            tablasImport.close();
            throw new CampoNoExisteException(nombreCampo);
        }
        
        int indexCampo = campos.indexOf(nombreCampo);
        String valorCampo = texto.substring(igual + 1, texto.length()).trim();
        List<String[]> registros = new ArrayList<>();
        String[] valuesTablas = tablasImport.getHeaders();
        registros.add(valuesTablas);
        
        while ( tablasImport.readRecord() ) {
            valuesTablas = tablasImport.getValues();
            if ( valuesTablas.length == tabla.getnCampos() ) {
                if ( valorCampo.compareTo(valuesTablas[indexCampo].trim()) == 0 ) {
                    registros.add(valuesTablas);
                }
            }
        }
        tablasImport.close();
        
        return registros;
    }
    
    public List<String[]> unirTablas(String texto) throws TablaNoExisteException, 
                                                    FileNotFoundException, 
                                                    IOException, 
                                                    CampoNoExisteException,
                                                    NumeroTablasException
    {
        int por = texto.indexOf(" POR ");
        int igual = texto.indexOf("=");
        
        String[] nombresTablas = texto.substring(0, por).split(",");
        if ( nombresTablas.length != 2) {
            throw new NumeroTablasException(nombresTablas.length);
        }
        Tabla[] tablas = new Tabla[2];
        for ( int i=0; i<2; i++) {
            tablas[i] = GestorTablas.getInstancia().getTabla(nombresTablas[i].trim());
            if ( tablas[i] == null ) {
                throw new TablaNoExisteException(nombresTablas[i]);
            }
        }            
        
        String nombreCampo = texto.substring(por + 5, igual).trim();
        CsvReader tablasImport = null;
        List<String>[] camposTablas = new List[2];
        int[] indexCampo = new int[2];
        for (int i=0; i<2; i++) {
            tablasImport = new CsvReader(new FileReader("archivos/" + tablas[i].getNombre() + ".csv"));
            tablasImport.setUseTextQualifier(false);
            tablasImport.readHeaders();
            camposTablas[i] = Arrays.asList(tablasImport.getHeaders());
            if ( !camposTablas[i].contains(nombreCampo) ) {
                tablasImport.close();
                throw new CampoNoExisteException(nombreCampo);
            }
            indexCampo[i] = camposTablas[0].indexOf(nombreCampo);
        }
        tablasImport.close();
                
        String valorCampo = texto.substring(igual + 1, texto.length()).trim();
        
        String[] registroActual = new String[camposTablas[0].size() + camposTablas[1].size()];
        List<String[]> registros = new ArrayList<>();
        
        System.arraycopy(camposTablas[0].toArray(), 0, registroActual, 0, camposTablas[0].size());
        System.arraycopy(camposTablas[1].toArray(), 0, registroActual, camposTablas[0].size(), camposTablas[1].size());
        registros.add(registroActual);
        
        String[] valuesUnir = null;
        String[] valuesTablas;
        
        for (int i=0; i<2; i++) {
            tablasImport = new CsvReader(new FileReader("archivos/" + tablas[i].getNombre() + ".csv"));
            tablasImport.setUseTextQualifier(false);
            tablasImport.readHeaders();

            while ( tablasImport.readRecord() ) {
                valuesTablas = tablasImport.getValues();
                if ( valuesTablas.length == tablas[i].getnCampos() ) {
                    if ( valorCampo.compareTo(valuesTablas[indexCampo[i]].trim()) == 0) {
                        if ( i==0 ) { 
                            valuesUnir = valuesTablas;
                            break;
                        } else if ( valuesUnir != null ) { 
                            System.arraycopy(valuesUnir, 0, registroActual, 0, valuesUnir.length);
                            System.arraycopy(valuesTablas, 0, registroActual, valuesUnir.length, valuesTablas.length);
                            registros.add(registroActual);
                        }
                    }
                }
            }
            tablasImport.close();
        }
        
        return registros;
    }
}
