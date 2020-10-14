package Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pais {
    public List<Ubigeo> territorios;
    public Map<String, Ubigeo> buscador;
    
    public Pais(){
        this.territorios = new ArrayList<>();
        this.buscador = new HashMap<>();
    }
    
    public void agregarUbigeo(Ubigeo u){
        territorios.add(u);
        buscador.put(u.departamento + u.provincia + u.distrito, u);
    }
    
    public Ubigeo buscarUbigeo(String DepProvDist){
        return buscador.get(DepProvDist);
    }
}
