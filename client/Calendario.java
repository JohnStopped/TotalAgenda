import java.util.*;


public class calendario {

  private enum semana{ 
    LUN,MAR,MIE,JUE,VIE,SAB,DOM;
  }

  private void calendar(int year, int month){
    Calendar cal = new GregorianCalendar();
    int dia_actual = cal.get(Calendar.DATE);
    int mes_actual = cal.get(Calendar.MONTH);
    int anio_actual = cal.get(Calendar.YEAR);

    Calendar cal_iterador = new GregorianCalendar(year,month,1);
    int ndias = cal_iterador.getActualMaximun(Calendar.DATE);
    boolean bisiesto = gCal.isLeapYear(year);
    cal_iterador.setFirstDayOfWeek(Calendar.MONDAY);

    int[][] matrix = matrix(dias_semana(year,month,1),ndias){

  }
  private void matrix(int dia_semana_1, int ndias){
    int[][] matrix = new int[4][7];
    int i;
    int j;
    for (i = dia_semana_1; i<dia_semana_1+ndias;i++ ){
      //TODO
      if()
    }
  }
  private int dia_semana(int year,int month,int day){
    int a = (14 - mes) / 12;
    int y = anio - a;
    int m = mes + 12 * a - 2;
    int d = (dia + y + y/4 - y/100 + y/400 + (31*m)/12) % 7;

    return d;
  }

