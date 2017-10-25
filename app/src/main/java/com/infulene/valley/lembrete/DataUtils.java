package com.infulene.valley.lembrete;



import java.util.Calendar;

/**
 * Created by user on 11-Oct-17.
 */

public class DataUtils {


//    public static  String dataDoEvento(int position,  List<Evento> lista_eventos ){
//        Date date_evento = lista_eventos.get(position).getData_evento();
//        Locale locale = new Locale("pt","BR");
//        DateFormat format = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy",locale);
//
//        return format.format(date_evento);
//    }

    public static String horaTexto (int hora,  int minuto){



        if (minuto == 0){
            return hora+":"+minuto+"0";
        }else {
            return hora+":"+minuto;
        }

    }
}
