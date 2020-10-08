/*
 * Empresa encargada de entregar el beneficio
 */
package Modelo;

import Utils.CargarArchivos;
import java.util.List;

public class Distribuidora {
    public List<LocalAtencion> agencias;
    public String nombre;
    
    public Distribuidora(String nombre, String archivoAgencias){
        this.nombre = nombre;
        this.agencias = CargarArchivos.CargarLocales(archivoAgencias);
    }

}
