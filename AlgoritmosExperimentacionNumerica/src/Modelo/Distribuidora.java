/*
 * Empresa encargada de entregar el beneficio
 */
package Modelo;

import Utils.CargarArchivos;
import java.util.List;

public class Distribuidora {
    private List<LocalAtencion> agencias;
    private String nombre;
    
    public Distribuidora(String nombre, String archivoAgencias){
        this.nombre = nombre;
        this.agencias = CargarArchivos.CargarLocales(archivoAgencias);
    }
    
    public List<LocalAtencion> getAgencias() {
        return agencias;
    }

    public void setAgencias(List<LocalAtencion> agencias) {
        this.agencias = agencias;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
