import java.util.*;


public class calendario {

  //Mes empieza a 1
  public static int[][] calendar(int year, int month){
    GregorianCalendar cal_iterador = new GregorianCalendar(year,month,0); //Mes empieza a 0
    cal_iterador.setFirstDayOfWeek(GregorianCalendar.MONDAY);
    int ndias = cal_iterador.getActualMaximum(GregorianCalendar.DATE);
    //boolean bisiesto = cal_iterador.isLeapYear(year);

    System.out.println("Año: " + year);
    System.out.println("Mes: " + month);
    System.out.println("Numero Dias: " + ndias);
    System.out.println("Dia de semana que empieza: " + dia_semana(year,month,1));
    int[][] matrix = matrix(dia_semana(year,month,1),ndias);
    return matrix;
  }

  private static void imprimeMatrix(int [][] matrix){
    int i;
    int j;
    for (i = 0; (i<6) ;i++ ){
      for (j=0; (j<7) ;j++){
        System.out.println(matrix[i][j]);
      }
      System.out.println();
    }
  }

  //Dia del mes y dia de la semana empiezan a 1
  private static int[][] matrix(int dia_semana_1, int ndias){
    int numerodias = ndias;

    //Se rellena una matriz de 6 filas con 0 si pertenece al día anterior, y el número del día si pertenece a este mes.
    int[][] matrix = new int[6][7];
    int z = 1;
    int i;
    int j;
    for (i = 0; (i<6) && (z<numerodias+1) ;i++ ){
      for (j = 0; (j<7) && (z<numerodias+1) ;j++){

        if ((i==0)&&(j<dia_semana_1-1)){
          matrix[i][j] = 0;
        }else{
          matrix[i][j] = z;
          z++;
        }

      }
    }
    return matrix;
  }

  //Mes, dia del mes y dia de la semana empiezan a 1
  private static int dia_semana(int year,int month,int day){
    int dias[] = {7,1,2,3,4,5,6};
    int a = (14 - month) / 12;
    int y = year - a;
    int m = month + 12 * a - 2;

    //Devuelve 0 para domingo, 1 para lunes...
    int d = (day + y + y/4 - y/100 + y/400 + (31*m)/12) % 7;

    //Devuelve 1 para lunes, 2 para martes,...
    return dias[d];
  }


}

