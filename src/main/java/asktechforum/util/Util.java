package asktechforum.util;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Reune metodos uteis para algumas classes do sistema
 * @author Pamela Beatriz
 *
 */
public class Util {
	
	/**
	 * Converte uma data do tipo SQL para uma data do tipo String	  
	 * @param formatoData - Formato para conversao da data(dd/MM/yyyy)
	 * @param dataSQL - Data a ser formatada
	 * @return String - data formatada
	 */
	public static String converterDataToString(String formatoData, Date dataSQL){
		String d = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat(formatoData);
		d = sdf.format(dataSQL);
		
		return d;
	}
	
	/**
	 * Converte uma data do tipo String para Data do tipo SQL. 
	 * @param formatoData - Formato para conversao da data(dd/MM/yyyy)
	 * @param data - Data a ser formatada
	 * @return uma data do tipo SQL.Date
	 * @throws ParseException - Exececao lancada caso haja erro na conversao
	 */
	public static Date converterStringToDate(String formatoData, String data) throws ParseException{
		java.sql.Date d = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat(formatoData);
		
		 d = new java.sql.Date(sdf.parse(data).getTime());
		
		return d;
	}
	/**
	 * Converte uma hora do tipo String para hora do tipo SQL.Time
	 * @param formatoHora - Formato para conversao da hora(HH:mm)
	 * @param hora - Hora a ser formatada
	 * @return hora do tipo SQL.Time
	 * @throws ParseException - Exececao lancada caso ocorra erro na conversao
	 */
	public static Time converterStringToTime(String formatoHora, String hora) throws ParseException{
		
		java.sql.Time t = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		t = new java.sql.Time(sdf.parse(hora).getTime());
		
		return t;
	}
	
	/**
	 * Converte uma hora do tipo SQL.Time para uma hora do tipo String
	 * @param formatoHora - Formato para conversao da hora(HH:mm)
	 * @param hora - Hora a ser formatada
	 * @return hora do tipo String
	 */
	public static String converterTimeToString(String formato, Time hora){
		String t = null;
		
		t = hora.toString();
		
		return t;
	}
	
	/**
	 * Retorna a data do sistema
	 * @return um objeto do tipo String com data do sistema 
	 */
	public static String getDataSistema(){
		
	    StringBuilder sb = new StringBuilder();  
	    GregorianCalendar d = new GregorianCalendar();  
	       
	    sb.append( d.get( GregorianCalendar.DAY_OF_MONTH ) );  
	    sb.append( "/" );  
	    sb.append( d.get( GregorianCalendar.MONTH ) );  
	    sb.append( "/" );  
	    sb.append( d.get( GregorianCalendar.YEAR ) );  
	   
	    return sb.toString();  
	}
	
	/**
	 * Retorna a hora do sistema
	 * @return um objeto do tipo String contendo a hora do sistema
	 */
	public static String getHoraSistema(){
		
	    StringBuilder sb = new StringBuilder();   
	    GregorianCalendar d = new GregorianCalendar();  
	      
	    sb.append( d.get( GregorianCalendar.HOUR ) );  
	    sb.append( ":" );  
	    sb.append( d.get( GregorianCalendar.MINUTE ) );  
	    sb.append( ":" );  
	    sb.append( d.get( GregorianCalendar.SECOND ) );  
	       
	    return sb.toString();  
	}
}