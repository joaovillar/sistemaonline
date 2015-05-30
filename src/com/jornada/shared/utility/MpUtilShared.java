package com.jornada.shared.utility;

import java.text.DecimalFormat;


public class MpUtilShared {


    public static String getDecimalFormatedOneDecimalMultipleFive(double doubleNumber){
        
        String strNumber="";
        int parteInteira = (int)doubleNumber;
        int parteDecimal = (int)Math.round((doubleNumber - (int)doubleNumber) * 100);
        
        if(parteDecimal>=0 && parteDecimal<=25){
            strNumber = Integer.toString(parteInteira);
        }else if(parteDecimal>=25 && parteDecimal<=74){
            double doubleTemp = ((double)parteInteira) + 0.5;
            strNumber = Double.toString(doubleTemp);
        }else if(parteDecimal>=75 && parteDecimal<=99){
            parteInteira++;
            strNumber = Integer.toString(parteInteira);
        }
        
        return strNumber;

    }
    
//    public static String getDecimalFormatedOneDecimal(double doubleNumber){
//        DecimalFormat oneDigit = new DecimalFormat("0.#");
//        String strDouble = oneDigit.format(doubleNumber).replace(",", ".");
//        return strDouble;
//    }
//    
//    public static String getDecimalFormatedTwoDecimal(double doubleNumber){
//        DecimalFormat oneDigit = new DecimalFormat("0.##");
//        String strDouble = oneDigit.format(doubleNumber).replace(",", ".");
//        return strDouble;
//    }
    

}
