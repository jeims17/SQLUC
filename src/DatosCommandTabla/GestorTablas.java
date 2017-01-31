/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatosCommandTabla;

import DatosCommandRegistro.GestorRegistro;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Estudiantes
 */
public class GestorTablas {

    private Map<String, Tabla> tablas;
    private final GestorTabla gestorTabla;
    private final GestorRegistro gestorRegistro;
    private static GestorTablas instancia = null;
    
    private GestorTablas(File ARCHIVO) { 
        tablas = new HashMap<>();
        gestorTabla = new GestorTabla(ARCHIVO);
        gestorRegistro = new GestorRegistro(ARCHIVO);
    }

    public static GestorTablas getInstancia() {
        if (instancia == null)
            instancia = new GestorTablas(null);
        
        return instancia;
    }
    
    public static GestorTablas getInstancia(final File ARCHIVO) {
        if (instancia == null)
            instancia = new GestorTablas(ARCHIVO);
        
        return instancia; 
    }

    public GestorTabla getGestorTabla() {
        return gestorTabla;
    }

    public GestorRegistro getGestorRegistro() {
        return gestorRegistro;
    }
    
    public boolean addNombreTabla(Tabla tabla, List<String> campos) throws IOException {
        if (tablas.containsKey(tabla.getNombre()))
            return true;
        else {
            tablas.put(tabla.getNombre(), tabla);
            crearArchivoTabla(tabla.getNombre(), campos, tabla.getLongitud());
            return false;
        }
    }
    
    public Integer getLongitud(String nombreTabla) {
        return tablas.get(nombreTabla).getLongitud();
    }
    
    public boolean containsTabla(String nombreTabla) {
        return tablas.containsKey(nombreTabla);
    }
    
    public Tabla getTabla(String nombreTabla) {
        return tablas.get(nombreTabla);
    }
    
    public void removeTabla(String nombre) {
        tablas.remove(nombre);
    }
    
    public void setTablas(Map<String, Tabla> tablas) {
        this.tablas = tablas;
    }
    
    private void crearArchivoTabla(String nombre, List<String> campos, Integer longitud) throws IOException {
        String archivo = "archivos/" + nombre + ".csv";
        File ficheroTabla = new File(archivo);
        
        if (!ficheroTabla.getParentFile().exists())
            ficheroTabla.getParentFile().mkdirs();
        ficheroTabla.createNewFile();
                        
        FileWriter archivoWriter = new FileWriter(ficheroTabla);
        CsvWriter csvOutput = new CsvWriter(archivoWriter, ',');
        csvOutput.setUseTextQualifier(false);
        
        String formato = "%1$-" + longitud + "s";
        for (String campo : campos) {
            csvOutput.write(String.format(formato, campo), true);
        }
        
        csvOutput.endRecord();
        csvOutput.flush();
        csvOutput.close();
    }
    
    public boolean recuperarTablas(File ARCHIVO) throws FileNotFoundException, IOException {        
        CsvReader tablasImport = new CsvReader(new FileReader(ARCHIVO));
        String[] valuesTablas;
        
        while ( tablasImport.readRecord() ) {
            valuesTablas = tablasImport.getValues();
            if ( valuesTablas[0].equals("0") ) {
                // proceso de contar las filas vacías
                String formato = "%1$-" + Integer.parseInt(valuesTablas[4]) + "s";
                Tabla tabla = new Tabla(valuesTablas[1],
                                    Arrays.asList(valuesTablas[5].split(";")).indexOf(String.format(formato, valuesTablas[3])),
                                    Integer.parseInt(valuesTablas[4]),
                                    valuesTablas[5].split(";").length);
                RandomAccessFile archivoRAF = new RandomAccessFile("archivos/" + valuesTablas[1] + ".csv", "r");
                String linea = archivoRAF.readLine();
                long punteroAnt = archivoRAF.getFilePointer();
                long punteroAct;
                while ((linea = archivoRAF.readLine()) != null) {
                    punteroAct = archivoRAF.getFilePointer();
                    if ( linea.trim().isEmpty() ) {
                        tabla.addPosicion(punteroAnt);
                    } else {
                        tabla.addPosRegistro(linea.split(",")[tabla.getCampoClave()].trim(), punteroAnt);
                    }                     
                    punteroAnt = punteroAct;
                }
                
                tablas.put(valuesTablas[1], tabla);
            }           
        } 
        return (tablas.isEmpty()) ? false : true;
    }
    
    public List<String[]> crearListTablas(File ARCHIVO) throws FileNotFoundException, IOException {        
        CsvReader tablasImport = new CsvReader(new FileReader(ARCHIVO));
        tablasImport.setUseTextQualifier(false);
        List<String[]> tabla = new ArrayList<>();
        
        while ( tablasImport.readRecord() ) {           
            String valuesTabla[] = tablasImport.getValues();
            if ( valuesTabla[0].equals("0") ) {
                valuesTabla[2] = valuesTabla[2].trim();
                String valuesCampos[] = valuesTabla[5].split(";");
                StringBuilder builder = new StringBuilder(valuesCampos[0].trim());
                               
                for (int i=1; i<valuesCampos.length; i++) {
                    builder.append(", ");
                    builder.append(valuesCampos[i].trim());
                }
                valuesTabla[5] = builder.toString();
                
                tabla.add(Arrays.copyOfRange(valuesTabla, 1, valuesTabla.length));  
            }                    
        }
        tablasImport.close();
        return tabla;
    }
    
    public List<String[]> crearListTabla(String nombreTabla) throws FileNotFoundException, IOException {
        CsvReader tablasImport = new CsvReader(new FileReader("archivos/" + nombreTabla + ".csv"));
        tablasImport.setUseTextQualifier(false);
        Tabla table = getTabla(nombreTabla);
        List<String[]> tabla = new ArrayList<>();
        String[] valuesTablas;
        while ( tablasImport.readRecord() ) {
            valuesTablas = tablasImport.getValues();
            if ( valuesTablas.length == table.getnCampos() ) {
                tabla.add(valuesTablas);
            }
        }
        tablasImport.close();
        return tabla;
    }
    
}
