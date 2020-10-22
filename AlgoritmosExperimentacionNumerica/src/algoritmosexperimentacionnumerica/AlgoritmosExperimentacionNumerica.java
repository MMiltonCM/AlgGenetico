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
        Integer numBucles = 100;
        Integer numParticulas = 15;
        Double aleatorizador = 1.0;
        Double inercia = 0.1;
        Double pesoLocal = 0.4;
        Double pesoGlobal = 0.5;
        
        Integer numDias = Constantes.numDias;
        
        AlgoritmoPSO algPSO = new AlgoritmoPSO(bono, LocalDate.of(2020,10,15),numDias);
        Calendario calPSO = algPSO.ejecutarPSOv2(numBucles, numParticulas, 
                aleatorizador, inercia, pesoLocal, pesoGlobal, false);
        Double x = calPSO.evaluarCalendario(banco.getAgencias() , personas.getBeneficiarios());
        return x;
    }

    public static void main(String[] args) {
        
        Constantes.capacidad = 50;      //  FACTOR #1 {50 , 100}
                                        //  Sin limite
        
        Integer limitePersonas = 800;   //  FACTOR #2 {800 , 1500}
                                        //  Maximo es 1000000 (1 millon y algo mas)
        
        Integer numAgencias = 300;       //  FACTOR #3 {300 , 500}
                                        //  Maximo es 551
        
        //                                  FACTOR #4 {PSO , Genetico}
        
        Constantes.bloqueXDia = 10;      //  FACTOR #5 {10 , 15}
                                        //  Poner -1 para que funcione con valores leidos
        
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
        
        Double x = AlgoritmosExperimentacionNumerica.ejecutarPSO(banco, bono, personas,
                Constantes.capacidad, numAgencias);
        
        
        int tamanoPoblacion = 30;
        int numeroGeneraciones = 70;
        
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
        
        System.out.print("\n\n\nFitness PSO / genético : " + x.intValue() + " / " + y.intValue() + "\n");
        
        // METRICAS: CUANDO ES LA ULTIMA CITA ASIGNADA
        // LA SUMA TOTAL DE TIEMPOS DESDE EL INICIO HASTA LA CITA
    }
    
}
