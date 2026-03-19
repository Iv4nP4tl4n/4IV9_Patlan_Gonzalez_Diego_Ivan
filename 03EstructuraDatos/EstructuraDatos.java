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
9. Cuadrado magico
10. Triangulo equilatero y rombo
11. desarrollar una figura hueca.
12. Realizar algunos patrones.
13. Realizar un diamante.
14. Realizar una calculadora basica. + - / * 

Tarea: Terminar

*/
import java.util.Scanner;

public class EstructuraDatos{

    public static void main (String [] args){

        char repetir;
        int opcion;

        Scanner entrada = new Scanner(System.in);

        do{ 

            System.out.println("Bienvenido");
            System.out.println("1- Descuento por edad");
            System.out.println("2- Decimal a binario");
            System.out.println("3- Conversor de temperaturas (C -> F y K)");
            System.out.println("4- Positivos y negetivos");
            System.out.println("5- Tienda de productos");
            System.out.println("6- Area y perimetro de las figuras");
            System.out.println("7- Tabla de multiplicar");
            System.out.println("8- Calcular un factorial con recursividad.");
            System.out.println("9- Cuadrado magico");
            System.out.println("10- Triangulo equilatero y rombo");
            System.out.println("11- Figura hueca");
            System.out.println("12- Patrones");
            System.out.println("13- Diamante");
            System.out.println("14- Calculadora basica. + - / * \n");

            opcion = entrada.nextInt();

            switch (opcion) {





                case 1:
                    System.out.println("Ingrese su edad:  ");
                    int edad = entrada.nextInt();

                    System.out.println("Ingrese el precio del producto:  ");
                    double precio1 = entrada.nextDouble();

                    double descuento = 0.0;

                    if (edad >= 60) {
                        descuento = 0.25;
                    } else if (edad >= 18) {
                        descuento = 0.20;
                    } else {
                        descuento = 0.10;
                    }

                     double total = precio1 - (precio1 * descuento);

                    System.out.println("El descuento aplicado es: " + (descuento * 100) + "%");
                    System.out.println("El precio final es: " + total);
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
                    System.out.println("Conversor de temperaturas");
                    System.out.println("Ingrese los grados Celsius:");

                        int celcius = 0;
                        celcius = entrada.nextInt();

                        double fahrenheit = (celcius * 9 / 5) + 32;
                        double kelvin = celcius + 273.15;

                        System.out.println("Temperatura en Fahrenheit: " + fahrenheit);
                        System.out.println("Temperatura en Kelvin: " + kelvin);
                    
                    break;







                case 4:

                    System.out.println("Programa para contar numeros positivos y negativos");
                    System.out.println("Ingrese cuantos numeros desea ingresar:  ");

                    int cantidad = 0;
                    cantidad = entrada.nextInt();

                    int positivos = 0;
                    int negativos = 0;

                    if(cantidad > 0){

                        for(int i = 1; i <= cantidad; i++){
                            System.out.println("Ingre un numero:   ");
                            int numero = 0;
                            numero = entrada.nextInt();

                            if(numero > 0){
                                positivos = positivos + 1;
                            }else if(numero < 0){
                                negativos = negativos + 1;
                            }
                        }

                        System.out.println("Cantidad de numeros positivos:  " + positivos);
                        System.out.println("Cantidad de numeros negativos:  " + negativos);

                    }else{
                        System.out.println("Ingrese solo cantidades reales:  ");
                    }

                    break;







                case 5:
                    System.out.println("Bienvenido a la tienda Trikitrakatelas");
                    System.out.println("Ingrese cuantos productos va a comprar");

                    int elementosproducto = 0;
                    elementosproducto = entrada.nextInt();

                    float compra = 0;

                    if(elementosproducto > 0){

                        System.out.println("----- Lista de productos -----");

                        for(int i = 1; i <= elementosproducto; i++){

                            System.out.println("Ingresa nombre del producto: ");
                            String nombreproducto = "";
                            nombreproducto = entrada.next();

                            System.out.println("Ingrese la cantidad: ");
                            int cantidad5 = 0;
                            cantidad5 = entrada.nextInt();

                            System.out.println("Ingrese el precio: ");
                            float precio = 0;
                            precio = entrada.nextFloat();

                            float resultado;
                            resultado = precio * cantidad5;

                            compra = compra + resultado;

                            System.out.println("Producto: " + nombreproducto);
                            System.out.println("Cantidad: " + cantidad5);
                            System.out.println("Precio: " + precio);
                            System.out.println("Subtotal: " + resultado);
                            System.out.println("-----------------------------");

                        }

                        System.out.println("El total de la compra es: " + compra);

                    }else{
                        System.out.println("Ingrese solo cantidades positivas");
                    }

                    break;








                case 6:
                    System.out.println("Calculo de area y perimetro de figuras");
                    System.out.println("1- Cuadrado");
                    System.out.println("2- Rectangulo");
                    System.out.println("3- Triangulo");
                    System.out.println("4- Circulo");
                    System.out.println("5- Pentagono");

                    int figura = 0;
                    figura = entrada.nextInt();

                    switch (figura) {
                        case 1:
                            System.out.println("Ingrese el lado del cuadrado:");
                            double lado = entrada.nextDouble();

                            double areaC = lado*lado;
                            double perimetroC = lado * 4;

                            System.out.println("Area: " + areaC);
                            System.out.println("Perimetro: " + perimetroC);

                            break;

                            case 2:
                                System.out.println("Ingrese base:");
                                double base = entrada.nextDouble();

                                System.out.println("Ingrese altura:");
                                double altura = entrada.nextDouble();

                                double areaR = base * altura;
                                double perimetroR = 2 * (base + altura);

                                System.out.println("Area: " + areaR);
                                System.out.println("Perimetro: " + perimetroR);

                            case 3:
                                System.out.println("Ingrese base:");
                                double baseT = entrada.nextDouble();

                                System.out.println("Ingrese altura:");
                                double alturaT = entrada.nextDouble();

                                System.out.println("Ingrese lado 2:");
                                double lado2 = entrada.nextDouble();

                                System.out.println("Ingrese lado 3:");
                                double lado3 = entrada.nextDouble();

                                double areaT = (baseT * alturaT) / 2;
                                double perimetroT = baseT + lado2 + lado3;

                                System.out.println("Area: " + areaT);
                                System.out.println("Perimetro: " + perimetroT);
                                break;

                            case 4:
                                System.out.println("Ingrese el radio:");
                                double radio = entrada.nextDouble();

                                double areaCir = Math.PI * radio * radio;
                                double perimetroCir = 2 * Math.PI * radio;

                                System.out.println("Area: " + areaCir);
                                System.out.println("Perimetro: " + perimetroCir);
                                break;

                            case 5:
                                System.out.println("Ingrese el lado del pentagono:");
                                double ladoP = entrada.nextDouble();

                                System.out.println("Ingrese el apotema:");
                                double apotema = entrada.nextDouble();

                                double perimetroP = 5 * ladoP;
                                double areaP = (perimetroP * apotema) / 2;

                                System.out.println("Area: " + areaP);
                                System.out.println("Perimetro: " + perimetroP);
                                break;

                        default:
                            System.out.println("¡Figura no valida!  ");
                    }
                    break;







                case 7:
                    // Tabla de multiplicar
                    System.out.println("TABLA DE MULTIPLICAR");

                    for(int n = 1; n <= 10; n++){

                        System.out.println("----------------------------------------");
                        System.out.println("| " + n + " | " 
                            + (n * 10) + " | " 
                            + (n * 100) + " | " 
                            + (n * 1000) + " |");

                    }

                    System.out.println("----------------------------------------");
                    
                    break;

                case 8:
                    System.out.println("Calculo de factorial (forma recursiva simulada)");
                    System.out.println("Ingrese un numero:");

                    int numero = entrada.nextInt();

                    if(numero >= 0){

                        int resultado = 1;

                        for(int i = numero; i > 0; i--){
                            resultado = resultado * i;
                        }

                        System.out.println("El factorial de " + numero + " es: " + resultado);

                    }else{
                        System.out.println("El factorial no existe para numeros negativos");
                    }
                    
                    break;

                    case 9:

                        System.out.println("Vamos a realizar un dibujo de un cuadrado magico");
                        System.out.println("Ingrese el tamaño del cuadrado (Entre 1 y 20): ");

                        int n1 = entrada.nextInt();
                        if (n1 >= 1 && n1 <= 20) {
                            // Se imprime
                            for (int i = 1; i <= n1; i++) {
                                // Se recorren las columnas
                                for (int j = 1; j <= n1; j++) {
                                    System.out.print("* ");
                                }
                                System.out.println(" ");
                            }
                        } else {
                            System.out.println("Porfavor ingrese un numero entre 1 y 20");
                        }

                        break;

                case 10:
                    System.out.println("Triangulo equilatero");
                    System.out.println("Ingrese el lado:");
                    int lado = entrada.nextInt();

                    for(int i = 1; i <= lado; i++){
                        for(int j = 1; j <= i; j++){
                            System.out.print("* ");
                        }
                        System.out.println();
                    }

                    System.out.println("Rombo");
                    System.out.println("Ingrese tamaño: ");
                    int n = entrada.nextInt();

                    for(int i = 1; i <= n; i++){
                        for(int j = 1; j <= n - i; j++){
                            System.out.print(" ");
                        }
                        for(int j = 1; j <= n; j++){
                            System.out.print("* ");
                        }
                        System.out.println();
                    }

                    for(int i = n - 1; i >= 1; i--){
                        for(int j = 1; j <= n - i; j++){
                            System.out.print(" ");
                        }
                        for(int j = 1; j <= n; j++){
                            System.out.print("* ");
                        }
                        System.out.println();
                    }
                    
                    break;

                case 11:
                    System.out.println("Figura hueca");
                    System.out.println("Ingrese tamaño:");
                    int tam = entrada.nextInt();

                    for(int i = 1; i <= tam; i++){
                        for(int j = 1; j <= tam; j++){
                            if(i == 1 || i == tam || j == 1 || j == tam){
                                System.out.print("* ");
                            }else{
                                System.out.print("  ");
                            }
                        }
                        System.out.println();
                    }
                    break;

                case 12:
                    System.out.println("Patron de numeros");
                    System.out.println("Ingrese tamaño:");
                    int num = entrada.nextInt();

                    for(int i = 1; i <= num; i++){
                        for(int j = 1; j <= i; j++){
                            System.out.print(j + " ");
                        }
                        System.out.println();
                    }
                    break;

                case 13:
                    System.out.println("Diamante");
                    System.out.println("Ingrese tamaño:");
                    int d = entrada.nextInt();

                    for(int i = 1; i <= d; i++){
                        for(int j = 1; j <= d - i; j++){
                            System.out.print(" ");
                        }
                        for(int j = 1; j <= (2*i - 1); j++){
                            System.out.print("*");
                        }
                        System.out.println();
                    }

                    for(int i = d - 1; i >= 1; i--){
                        for(int j = 1; j <= d - i; j++){
                            System.out.print(" ");
                        }
                        for(int j = 1; j <= (2*i - 1); j++){
                            System.out.print("*");
                        }
                        System.out.println();
                    }
                    break;

                case 14:
                    System.out.println("Calculadora basica");
                    System.out.println("Ingrese primer numero:");
                    double na = entrada.nextDouble();

                    System.out.println("Ingrese segundo numero:");
                    double nb = entrada.nextDouble();

                    System.out.println("Seleccione operacion (+, -, *, /):");
                    char op = entrada.next().charAt(0);

                    double res = 0;

                    switch(op){
                        case '+':
                            res = na + nb;
                            break;
                        case '-':
                            res = na - nb;
                            break;
                        case '*':
                            res = na * nb;
                            break;
                        case '/':
                            if(nb != 0){
                                res = na / nb;
                            }else{
                                System.out.println("No se puede dividir entre 0");
                                break;
                            }
                            break;
                        default:
                            System.out.println("Operacion invalida");
                    }

                    System.out.println("Resultado: " + res);
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;

                }

            System.out.println("Si deseas repetir presiona s o S");
            System.out.println(" /\\_/\\");
            System.out.println("( o.o )");
            System.out.println(" > ^ <");
            
            repetir = entrada.next().charAt(0);

        }while(repetir == 's' || repetir == 'S');

    }
}


