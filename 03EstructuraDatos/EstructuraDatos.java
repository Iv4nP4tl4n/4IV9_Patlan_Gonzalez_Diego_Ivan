/*
En java:
1. Desarrollar un programa para calcular el bono de un descuento por edad. 
2. Convertir números  decimales a binarios. 
3. Converti temperatura de entre los 3 principales grados C -> F y K.
4. Realizar un programa para contar numero de positivos  negativos de una serie de numeros.
5. Desarroller una tiendita para agregar productos y precios.
6. Desarrollar un programa para calcular el area y perimetro de 5 diferentes figuras.
7. desarolar un programa para desarollar una tabla. 
8. Desarrollar un programa para calcular un factorial con recursividad.
10. desarrollar una figura hueca.
11. Realizar algunos patrones.
12. Realizar un diamante.
13. Realizar una calculadora basica. + - / * 

Tarea: que es un cry catch

*/
import java.util.Scanner;

public class EstructuraDatos{

    public static void main (String [] args){

        char repetir;
        int opcion;

        Scanner entrada = new Scanner(System.in);

        do{ 

            System.out.println("Bienvenido");
            System.out.println("1- ");
            System.out.println("2- ");
            System.out.println("3- ");
            System.out.println("4- ");
            System.out.println("5- ");
            System.out.println("6- ");
            System.out.println("7- ");
            System.out.println("8- ");
            System.out.println("9- ");
            System.out.println("10- ");
            System.out.println("11- ");
            System.out.println("12- ");
            System.out.println("13- ");
            System.out.println("14- ");

            opcion = entrada.nextInt();

            switch (opcion) {

                case 1:
                    
                    break;

                case 2:
                
                System.out.println("Ingrese un numero positivo que se desee convertir a binario");

                    int numbinario;
                    String guardarbinario ="";

                    numbinario = entrada.nextInt();

                    if (numbinario > 0) {

                        while(numbinario > 0){

                            if (numbinario % 2 == 0 ){
                                guardarbinario = "0" + guardarbinario;
                            }else{
                                guardarbinario = "1" + guardarbinario;
                            }

                            numbinario = (int)numbinario/2;
                        }

                    }else if(numbinario == 0){
                        guardarbinario = "0";
                    }else{
                        guardarbinario = "No se puede convertir a binario, solo se pueden positivos";
                    }

                    System.out.println("El numero convertido a binario es: " + guardarbinario);
                    break;
                case 3:
                    
                    break;

                case 4:
                    
                    break;

                case 5:
                    
                    break;

                case 6:
                    
                    break;

                case 7:
                    
                    break;

                case 8:
                    
                    break;

                case 9:
                    
                    break;

                case 10:
                    
                    break;

                case 11:
                    
                    break;

                case 12:
                    
                    break;

                case 13:
                    
                    break;

                case 14:
                    
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;

                }

            System.out.println("Si deseas repetir presiona s o S");
            repetir = entrada.next().charAt(0);

        }while(repetir == 's' || repetir == 'S');

    }
}


