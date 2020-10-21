/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmosexperimentacionnumerica;

import Algoritmo.AlgoritmoGenetico;
import Modelo.Beneficiario;
import Modelo.Beneficio;
import Modelo.Calendario;
import Modelo.Cita;
import Modelo.Distribuidora;
import Modelo.LocalAtencion;
import Modelo.Padron;
import Modelo.Pais;
import PSO.AlgoritmoPSO;
import PSO.ParticulaPSO;
import Utils.CargarArchivos;
import Utils.Constantes;
import Utils.Hora;
import Utils.Printer;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diego Paredes
 */
public class AlgoritmosExperimentacionNumerica {
    
    public static Double ejecutarPSO(Distribuidora banco,
            Beneficio bono, Padron personas, Integer capacidad, 
            Integer numAgencias){
        Integer numBucles = 50;
        Integer numParticulas = 10;
        Double aleatorizador = 1.0;
        Double inercia = 0.1;
        Double pesoLocal = 0.25;
        Double pesoGlobal = 0.65;
        
        Integer numDias = Constantes.numDias;
        
        AlgoritmoPSO algPSO = new AlgoritmoPSO(bono, LocalDate.of(2020,9,10),numDias);
        Calendario calPSO = algPSO.ejecutarPSOv2(numBucles, numParticulas, 
                aleatorizador, inercia, pesoLocal, pesoGlobal, true);
        Double x = calPSO.evaluarCalendario(banco.getAgencias() , personas.getBeneficiarios());
        return x;
    }

    public static void main(String[] args) {
        
        Constantes.capacidad = 20;      //  FACTOR #1 {20 , 50}
        Integer limitePersonas = 300;   //  FACTOR #2 {300 , 800}
        Integer numAgencias = 40;       //  FACTOR #3 {40 , 80
        //                                  FACTOR #4 {PSO , Genetico}
        Integer numBloquesAtencion = 5; //  FACTOR #5 {5 , 10}
        
        
        // Data comun a los archivos. Ubicaciones geograficas del peru
        Pais peru = new Pais();
        CargarArchivos.CargarUbigeos("ubigeos.txt", peru);
        
        // Primero crearemos la empresa que distribuye el beneficio
        Distribuidora banco = new Distribuidora("Banco Exp", "agencias.txt",peru);
        
        banco.setAgencias( banco.getAgencias().subList(0, numAgencias) );
        
        // Ahora registramos un padron donde estaran los beneficiarios
        
        Padron personas = new Padron("Familias afectadas economicamente", "padron_final.txt",peru,limitePersonas);
        
        // Con la informacion construimos el beneficio creara los bloques de horarios posibles
        Beneficio bono = new Beneficio("Bono Experimentacion", banco, personas, LocalDate.of(2020,10,15));
        
        
        //PSO
        Integer capacidad = 20;
        
        Double x = AlgoritmosExperimentacionNumerica.ejecutarPSO(banco, bono, personas,
                capacidad, numAgencias);
        
        
        int tamanoPoblacion = 10;
        int numeroGeneraciones = 50;
        
        // Despues le brindamos esta informacion a nuestros algoritmos para que nos devuelvan un Calendario de citas
        AlgoritmoGenetico AG = new AlgoritmoGenetico(bono);
        Calendario calGenetico = AG.ejecutar(tamanoPoblacion, numeroGeneraciones);
        Double y = calGenetico.evaluarCalendario(banco.getAgencias() , personas.getBeneficiarios());
        
        /*
        for(Cita cita : calGenetico.getCitas()){
            System.out.print("" + cita.getHorario().getInicio() + " - " + 
                    cita.getHorario().getFin());
        }
        */
        // Algoritmo 2
        
        // Imprimir metricas de algoritmo
        Printer.ReporteEstadisticas(AG, calGenetico);
        
        System.out.print("\n\n\nFitness PSO / genético : " + x + " / " + y + "\n");
        
        // METRICAS: CUANDO ES LA ULTIMA CITA ASIGNADA
        // LA SUMA TOTAL DE TIEMPOS DESDE EL INICIO HASTA LA CITA
    }
    
}
