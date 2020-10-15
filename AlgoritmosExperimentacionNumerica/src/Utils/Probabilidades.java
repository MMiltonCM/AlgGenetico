/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Probabilidades {
    
    private static Random R = new Random();
    
    public static List<Double> normalizar(List<Double> probs){
        List<Double> nuevo = new ArrayList<>();
        Double suma = 0.0;
        for(Integer i = 0; i<probs.size(); i++)
            suma += probs.get(i);
        for(Integer i = 0; i<probs.size(); i++)
            nuevo.set(i, nuevo.get(i)/suma);
        return nuevo;
    }
    
    public static Integer seleccionar(List<Double> probs){
        Double number = R.nextDouble();
        for(Integer i = 0; i<probs.size(); i++){
            if (number > probs.get(i)){
                number -= probs.get(i);
            }else{
                return i;
            }
        }
        return probs.size()-1;
    }
}
