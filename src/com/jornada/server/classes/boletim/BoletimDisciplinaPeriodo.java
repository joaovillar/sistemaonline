package com.jornada.server.classes.boletim;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import com.jornada.shared.classes.boletim.TabelaBoletim;

/**
 *
 * @author waf039
 */
public class BoletimDisciplinaPeriodo
{
    
    
    public BoletimDisciplinaPeriodo(){
        
    }
    
    public static String[][] getBoletim(ArrayList<TabelaBoletim> listTabelaBoletim)
    {
        String[] arrayPeriodo = getPeriodosFromArray(listTabelaBoletim);
        String[] arrayDisciplinas = getDisciplinaFromArray(listTabelaBoletim);
        String[][] arrayNotas = getNotasFromArray(listTabelaBoletim, arrayPeriodo, arrayDisciplinas);
        String[] arrayBoletimHeader = getMoutingHeader(arrayPeriodo);
        String[][] arrayBoletim = getMountingBoletim(arrayBoletimHeader, arrayDisciplinas, arrayNotas);

        return arrayBoletim;

    }
    
    
   private static String[][] getMountingBoletim(String[] arrayBoletimHeader, String[] arrayDisciplinas, String[][] arrayNotas){
        
        String[][] arrayBoletim = new String [arrayBoletimHeader.length][arrayDisciplinas.length+1];
        
        for(int column=0;column<arrayBoletimHeader.length;column++){
            
            for(int row=0;row<arrayDisciplinas.length+1;row++){
                
                if (row == 0)
                {
                    arrayBoletim[column][row] = arrayBoletimHeader[column];
                }
                else
                {
                    if(column==0)
                    {
                        arrayBoletim[column][row]=arrayDisciplinas[row-1];
                    }
                    else
                    {
                        arrayBoletim[column][row] = arrayNotas[column-1][row-1];
                    }
                }
                
            }
            
        }     
        
        return arrayBoletim;
        
    }    
   
   
   
    private static String[] getMoutingHeader(String[] arrayPeriodo){
        
        String[] arrayBoletimHeader = new String [arrayPeriodo.length+1];
        
                //Mounting header boletim
        for(int i=0;i<arrayPeriodo.length+1;i++){
            if(i==0){
                arrayBoletimHeader[i]="Disciplinas";
            }
            else
            {
                arrayBoletimHeader[i]=arrayPeriodo[i-1];
            }
        }    
        
        return arrayBoletimHeader;
    }

    
    
     private static String[][] getNotasFromArray(ArrayList<TabelaBoletim> listTabelaBoletim, String[] arrayPeriodo, String[] arrayDisciplinas){
          String[][] arrayBoletim = new String[arrayPeriodo.length][arrayDisciplinas.length];
        
        //arrayBoletim[0][0] = "Disciplina";
        for(int intDisciplina=0;intDisciplina<arrayDisciplinas.length;intDisciplina++){
            
            String strDisciplina = arrayDisciplinas[intDisciplina];
            
            double somaMedia=0;
            for(int intPeriodo=0;intPeriodo<arrayPeriodo.length;intPeriodo++){
                
                String strPeriodo = arrayPeriodo[intPeriodo];
                
                for(TabelaBoletim boletim : listTabelaBoletim){

                    if(boletim.getNomeDisciplina().equals(strDisciplina)){
                        if(boletim.getNomePeriodo().equals(strPeriodo)){
                            
//                            String strNotaBoletim = arrayBoletim[intPeriodo][intDisciplina];
//                            if(strNotaBoletim==null || strNotaBoletim.isEmpty()){
//                                somaMedia = strNotaBoletim;
//                                arrayBoletim[intPeriodo][intDisciplina] = boletim.getNota();
                            if(arrayBoletim[intPeriodo][intDisciplina]==null || arrayBoletim[intPeriodo][intDisciplina].isEmpty()){
                                arrayBoletim[intPeriodo][intDisciplina] = boletim.getNota();
                                if(boletim.getNota()!=null){
                                    somaMedia = Double.parseDouble(boletim.getNota());
                                }
                            }
                            else{
                                if (boletim.getNota() != null && !boletim.getNota().isEmpty())
                                {
                                    double notaDoDB = Double.parseDouble(boletim.getNota());
//                                    double notaDoBoletim = Double.parseDouble(strNotaBoletim);
                                    
                                    somaMedia = (notaDoDB + somaMedia);

                                    
                                }
                            }

                        }
                    }
                }
                int numeroParaMedia = getNumeroDeNotasParaCalcularMedia(listTabelaBoletim,strPeriodo, strDisciplina);
                if (numeroParaMedia > 0) {
                    double mediaDisciplinaPeriodo = somaMedia / numeroParaMedia;
                    DecimalFormat oneDigit = new DecimalFormat("0.##");
//                    arrayBoletim[intPeriodo][intDisciplina] = Double.toString(mediaDisciplinaPeriodo);
                    arrayBoletim[intPeriodo][intDisciplina] = oneDigit.format(mediaDisciplinaPeriodo);
                }
            }
            
        }
        return arrayBoletim;
     }
     
     
    private static int getNumeroDeNotasParaCalcularMedia(ArrayList<TabelaBoletim> listTabelaBoletim, String strPeriodo, String strDisciplina)
    {
        int intNumber = 0;

        for (TabelaBoletim object : listTabelaBoletim)
        {

            if (object.getNomePeriodo().equals(strPeriodo))
            {
                if (object.getNomeDisciplina().equals(strDisciplina))
                {
                    if (object.getNota() != null && !object.getNota().isEmpty())
                    {
                        intNumber++;
                    }
                }
            }
        }

        return intNumber;

    }
        
    private static String[] getPeriodosFromArray(ArrayList<TabelaBoletim> listTabelaBoletim) {

        // Avoiding Duplicity
        HashSet<String> hashPeriodos = new HashSet<String>();
        for (TabelaBoletim object : listTabelaBoletim) {
            hashPeriodos.add(object.getNomePeriodo());
        }
        String[] auxArrayPeriodos = new String[hashPeriodos.size()];

        // Mounting Periodos
        int i = 0;
        for (String strPeriodo : hashPeriodos) {
            auxArrayPeriodos[i++] = strPeriodo;
        }
        Arrays.sort(auxArrayPeriodos);

        return auxArrayPeriodos;

    }
           
    private static String[] getDisciplinaFromArray(ArrayList<TabelaBoletim> listTabelaBoletim)
    {
        HashSet<String> hashDisciplina = new HashSet<String>();
        for (TabelaBoletim object : listTabelaBoletim)
        {
            hashDisciplina.add(object.getNomeDisciplina());
        }
        

        String[] arrayDisciplina = new String[hashDisciplina.size()];

        //Mounting Periodos
        int i = 0;
        for (String strDisciplina : hashDisciplina)
        {
            arrayDisciplina[i++] = strDisciplina;
        }
        Arrays.sort(arrayDisciplina);
        
        return arrayDisciplina;
    }
            

    
}
