package yosumo.src.logic;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by a-pol_000 on 9/20/2016.
 * AFP - 20160921     INIT Clase encargada de unificar operacines y unificar
 *
 */
public final class ManagerFormat {

    public static final String DATE_FORMAT = "yyyyMMdd";
    private static char[] c = new char[]{'k', 'm', 'b', 't'};

    /**
     * Recursive implementation, invokes itself for each factor of a thousand, increasing the class on each invokation.
     * @param n the number to format
     * @param iteration in fact this is the class from the array c
     * @return a String representing the number n formatted in a cool looking way.
     */
    public static String formatMoneyK(double n, int iteration) {
        double d = ((long) n / 100) / 10.0;
        boolean isRound = (d * 10) %10 == 0;//true if the decimal part is equal to 0 (then it's trimmed anyway)
        return (d < 1000? //this determines the class, i.e. 'k', 'm' etc
                ((d > 99.9 || isRound || (!isRound && d > 9.99)? //this decides whether to trim the decimals
                        (int) d * 10 / 10 : d + "" // (int) d * 10 / 10 drops the decimal
                ) + "" + c[iteration])
                : formatMoneyK(d, iteration+1));
    }

    /**
     *
     * @param n
     * @return
     */
    public static String formatMoney(double n){
        return "";
    }

    /**
     *
     * @param date
     * @return
     */
    public static Date formatDate(String date){
        try {
            DateFormat formato = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
            return formato.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date){
        try {
            DateFormat formato = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
            return formato.format(date);
        } catch (Exception a) {
            return null;
        }
    }

    /**
     *
     * @param nit
     * @return
     */
    public static String formatNIT(double nit){
        String resultado = "";
        DecimalFormat df = new DecimalFormat("#");

        int i =0;
        for(char c : df.format(nit).toCharArray()) {
            resultado += c;

            if(i==2 || i==5  ){
                resultado += ".";
            }else if(i==8 ){
                resultado += "-";
            }
            i++;

        }

        return resultado;
    }
}
