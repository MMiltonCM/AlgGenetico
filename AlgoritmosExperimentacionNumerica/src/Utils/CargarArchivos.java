/*
 * Metodos utiles para cargar archivos, utilizar una estructura
 */
package Utils;

import Modelo.Beneficiario;
import Modelo.LocalAtencion;
import Modelo.Pais;
import Modelo.Ubigeo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CargarArchivos {

    public static List<LocalAtencion> CargarLocales(String archivoAgencias,Pais p) {
        List<List<String>> Data = Leer(archivoAgencias);
        List<LocalAtencion> locales = new ArrayList<>();
        for(List<String> linea : Data){
            Ubigeo u = p.buscarUbigeo(linea.get(1)+linea.get(2)+linea.get(3));
            String[] h_aux = linea.get(6).split(":");
            LocalTime inicio = LocalTime.of(Integer.parseInt(h_aux[0]),
                    Integer.parseInt(h_aux[1].substring(0, 2)));
            LocalTime fin = LocalTime.of(Integer.parseInt(h_aux[1].substring(h_aux[1].length() - 2, h_aux[1].length())),
                    Integer.parseInt(h_aux[2].substring(0, 2)));
            LocalAtencion la = new LocalAtencion(Integer.parseInt(linea.get(0)), u.latitud/360, 
                u.longitud/360 , u.codigo, Constantes.capacidad, inicio, fin);
            locales.add(la);
        }
        return locales;
    }

    public static List<Beneficiario> CargarPadron(String archivoPadron,Pais p) {
        List<List<String>> Data = Leer(archivoPadron);
        List<Beneficiario> padron = new ArrayList<>();
        for(List<String> linea : Data){
            Ubigeo u = p.buscarUbigeo(linea.get(1)+linea.get(2)+linea.get(3));
            /*String[] h_aux = linea.get(6).split(":");
            LocalTime inicio = LocalTime.of(Integer.parseInt(h_aux[0]),
                    Integer.parseInt(h_aux[1].substring(0, 2)));
            LocalTime fin = LocalTime.of(Integer.parseInt(h_aux[1].substring(h_aux[1].length() - 2, h_aux[1].length())),
                    Integer.parseInt(h_aux[2].substring(0, 2)));
            LocalAtencion la = new LocalAtencion(Integer.parseInt(linea.get(0)), u.latitud/360, 
                u.longitud/360 , u.codigo, Constantes.capacidad, inicio, fin);
            locales.add(la);*/
            
        }
        return padron;
    }
    
    public static void CargarUbigeos(String archivoUbigeo, Pais p){
        List<List<String>> Data = Leer(archivoUbigeo);
        for(List<String> linea : Data){
            String lat_string = linea.get(3);
            String lon_string = linea.get(4);
            int lat = Integer.parseInt(lat_string.substring(0,2)) * 3600
                    + Integer.parseInt(lat_string.substring(3,5)) * 60 
                    + Integer.parseInt(lat_string.substring(6,7));
            int lon = Integer.parseInt(lon_string.substring(0,2)) * 3600
                    + Integer.parseInt(lon_string.substring(3,5)) * 60 
                    + Integer.parseInt(lon_string.substring(6,7));
            Ubigeo u = new Ubigeo(linea.get(0),linea.get(1),linea.get(2),
                    lat,lon,linea.get(5));
            p.agregarUbigeo(u);
        }
    }
    
    private static List<List<String>> Leer(String filename) {
        List<List<String>> Data = new ArrayList<>();
        BufferedReader br = null;
        String line;
        try {
            br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {
                String[] lineArrayData = line.split(";");
                List<String> lineListData = Arrays.asList(lineArrayData);
                Data.add(lineListData);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return Data;
    }
}
