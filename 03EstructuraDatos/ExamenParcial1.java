/*
Deberan de realizar un programa que se encargue de lo siguiente:

Desarrollar un programa que se encargue de realizar el calculo del costo para la colocación de piso, para 
ello el programa debe de tener las siguientes opciones


Debe de tener un menu con base a las siguientes opciones:
Al ingresar en la opcion 1 le debe de preguntar al usuario sus datos para la compra del piso, debe de 
solicitar los siguientes datos nombre apellido paterno, apellido materno, fecha de nacimiento, direccion 
residencial.
al ingresar en la opcion 2 se debe de debe de visualizar los datos del tipo de piso que se venden dentro de 
la empresa los cuales son:
Porcelanato 22.35 el metro cuadrado
Marmoleado 34.27 el metro cuadrado
Acrilico 22.94 el metro cuadrado
al oprimir la opcion 3 el programa debe de preguntar cuantos cuartos tiene el inmueble donde se desea
instalar el piso, se debe de validar que sea un numero entero positivo mayor a 1 pero menor de 5. Una
vez que se haya definido el numero de cuarto el programa debe de preguntar las medidas de largo y ancho 
de cada uno de los cuartos, asi como tambien debe de elegir alguna de las opciones listadas (si es 
porcelanato, si es marmoleado o si es acrilico). Finalmente debe de imprir en la pantalla el costo por 
cada cuarto con su correspondiente tipo de piso asi como el total general de la compra con los datos 
completos del comprador.
Una vez visualizados esos datos debe de preguntarle si desea realizar la compra en caso de ser afirmativa 
la respuesta debe de realizar a la compra total un descuento del 7.95% sobre el total de la compra. 
Considere que en el desploce de precios se debe de marcar el cobro del IVA del 16%
EL programa deberan de entregar su enlace al repositorio en github
Deberan de integrar en un documento las evidencias del paso a paso de que el programa realiza las acciones indicadas asi como su validación del tipo de dato.
Lean con atención las opciones y su logica.

*/
import java.util.Scanner;
public class ExamenParcial1 {
    
    public static void main (String [] args){
        char regresar;
        Scanner entrada = new Scanner(System.in);

        String nombre = " ";
        String ApellidoP = " ";
        String ApellidoM = " ";
        String Direccion = " ";
        String Fecha = " ";


        do{ 
            System.out.println("====== MENU ======");
            System.out.println("1.- Datos del cliente");
            System.out.println("2.- Tipos de piso");
            System.out.println("3.- Calculo de costo");
            System.out.println("4.- Salir");
            System.out.println("------------------");

            int opcion = entrada.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("-------------------------------");
                    System.out.println("Ingrese su nombre: ");
                    nombre = entrada.next();

                    System.out.println("Apellido paterno: ");
                    ApellidoP = entrada.next();

                    System.out.println("Apellido materno: ");
                    ApellidoM = entrada.next();

                    System.out.println("Fecha de nacimiento (d/m/a): ");
                    Fecha = entrada.next();

                    System.out.println("Direccion: ");
                    Direccion = entrada.next();
                    System.out.println("-------------------------------");

                    break;
                case 2:
                    System.out.println("-------------------------------");
                    System.out.println("Tipos de piso disponibles: ");
                    System.out.println("1.- Porcelanato $22.35 m2 ");
                    System.out.println("2.- Marmoleado $34.27 m2 ");
                    System.out.println("3.- Acrilico $22.94 m2 ");
                    System.out.println("-------------------------------");
                    break;
                case 3:
                    System.out.println("-------------------------------");
                    System.out.println("Cuantos cuartos tiene el inmueble (2 a 4): ");
                    int cuartos = entrada.nextInt();

                    System.out.println("-------------------------------");
                    if (cuartos > 1 && cuartos < 5){
                        double total = 0;

                        for (int i = 1; i <= cuartos; i++){
                            System.out.println("Cuarto: " + i);

                            System.out.println("Ingrese largo: ");
                            double largo = entrada.nextDouble();

                            System.out.println("Ingrese ancho: ");
                            double ancho = entrada.nextDouble();

                            double area = largo * ancho;

                            System.out.println("-------------------------------");
                            System.out.println("Seleccione tipo de piso: ");
                            System.out.println("1.- Porcelanato");
                            System.out.println("2.- Marmoleado");
                            System.out.println("3.- Acrilico");
                            System.out.println("-------------------------------");

                            int tipo = entrada.nextInt();

                            double precio = 0;
                            String tipoPiso = "";

                            if(tipo == 1){
                                precio = 22.35;
                                tipoPiso = "Porcelanato";
                            }else if(tipo == 2){
                                precio = 34.27;
                                tipoPiso = "Marmoleado";
                            }else if(tipo == 3){
                                precio = 22.94;
                                tipoPiso = "Acrilico";
                            }

                            double costo = area * precio;
                            total += costo;

                            System.out.println("-------------------------");
                            System.out.println("Cuarto " + i + " - " + tipoPiso);
                            System.out.println("Area: " + area);
                            System.out.println("Costo: " + costo);
                            System.out.println("-------------------------");
                        }

                        double iva = total * 0.16;
                        double totalIva = total + iva;

                        System.out.println("-------------------------");
                        System.out.println("Cliente: " + nombre + " " + ApellidoP + " " + ApellidoM);
                        System.out.println("Direccion: " + Direccion);
                        System.out.println("Fecha nacimiento: " + Fecha);
                        System.out.println("Subtotal: " + total);
                        System.out.println("IVA (16%): " + iva);
                        System.out.println("Total: " + totalIva);
                        System.out.println("-------------------------");

                        System.out.println("Desea realizar la compra? (s/n)");
                        char compra = entrada.next().charAt(0);
                        if(compra == 's' || compra == 'S'){
                            double descuento = totalIva * 0.0795;
                            double totalFinal = totalIva - descuento;

                            System.out.println("Descuento aplicado: " + descuento);
                            System.out.println("Total a pagar: " + totalFinal);
                        }

                    }else{
                        System.out.println("Numero de cuartos invalido");
                    }
                    break;
                case 4:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
            System.out.println("Desea regresar? precione 1");

            regresar = entrada.next().charAt(0);

        }while(regresar == '1' );

        System.out.println("-------------------------------");
        System.out.println(" /\\_/\\");
        System.out.println("( o.o )  (Saqueme 10 digo Miau!!....)");
        System.out.println("(> ^ <)");
        System.out.println("-------------------------------");

    }

}
