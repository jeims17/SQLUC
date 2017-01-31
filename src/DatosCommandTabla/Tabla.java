/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatosCommandTabla;

import java.util.AbstractQueue;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 *
 * @author paul
 */
public class Tabla {
    private final Integer longitud;
    private final Integer nCampos;
    private Integer campoClave;
    private final String nombre;    
    private final Map<String, Long> registros;
    private final AbstractQueue<Long> eliminados;

    public Tabla(String nombre, Integer campoClave, Integer longitud, Integer nCampos) {
        this.longitud = longitud;
        this.nombre = nombre;
        this.campoClave = campoClave;
        this.nCampos = nCampos;
        this.registros = new HashMap<>();
        this.eliminados = new PriorityQueue<>();
    }

    public Integer getLongitud() {
        return longitud;
    }

    public Integer getnCampos() {
        return nCampos;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setCampoClave(Integer campoClave) {
        this.campoClave = campoClave;
    }
    
    public Integer getCampoClave() {
        return campoClave;
    }
    
    public Long getPosicion() {
        return eliminados.remove();
    }

    public void addPosicion(Long registrosEliminados) {
        this.eliminados.add(registrosEliminados);
    }
    
    public boolean hayEliminados() {
        return !this.eliminados.isEmpty();
    }
    
    public void borrarEliminados() {
        this.eliminados.clear();
    }
    
    public void addPosRegistro(String registro, Long clave) {
        registros.put(registro, clave);
    }
    
    public void removePosRegistro(String registro) {
        registros.remove(registro);
    }
    
    public Long getRegistro(String clave) {
        return registros.get(clave);
    }
    
    public boolean hasRegistro(String clave) {
        return registros.containsKey(clave);
    }
    
}
