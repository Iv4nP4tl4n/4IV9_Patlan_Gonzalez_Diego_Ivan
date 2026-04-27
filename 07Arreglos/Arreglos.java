import java.util.Scanner;
public class Arreglos {
    //vamos a realizar un arreglo de una sola dimension
    //Un arreglo solo puede ser de un tipo de dato primitivo

    public static void main(String[] args){
        Scanner entrada = new Scanner(System.in);
        int j=0;
/* 
        int arreglo[]= new int[10];

        int valor =0;

        for(int j = 0; j < arreglo.length; j++){
            System.out.println("Ingrese los valores para el arreglo: ");
            arreglo[j] = entrada.nextInt();
        }

        for(int i = 0; i < arreglo.length; i++){
            System.out.println("vamos a recorrer el arreglo: "+arreglo[i] + valor);

        }*/

        int matriz [][] = new int [3][3];
        for(int i = 0; i < matriz.length; i++){

        for(j = 0; j < matriz.length; j++){

            System.out.println(j);
            System.out.println("Ingresa el valor de la fila: "+ i + "Ingresa la columna; "+j);

            

            }

        }
    }
}

