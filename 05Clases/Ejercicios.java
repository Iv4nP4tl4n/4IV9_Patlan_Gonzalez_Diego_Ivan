import java.util.Scanner;

public class Ejercicios {

    Scanner entrada = new Scanner(System.in);

    public void menu() {
        int opcion;

        do {
            System.out.println("\nBienvenido a la calculadora");
            System.out.println("Elige una opcion");
            System.out.println("1.- Sumar");
            System.out.println("2.- Restar");
            System.out.println("3.- Multiplicar");
            System.out.println("4.- Dividir");
            System.out.println("5.- Salir");

            opcion = entrada.nextInt();

            switch (opcion) {
                case 1:
                    suma();
                    break;
                case 2:
                    resta();
                    break;
                case 3:
                    multiplicacion();
                    break;
                case 4:
                    division();
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }

        } while (opcion != 5);
    }

    public void suma() {
        double num1, num2, resultado;

        System.out.println("Ingresa el primer numero:");
        num1 = entrada.nextDouble();

        System.out.println("Ingresa el segundo numero:");
        num2 = entrada.nextDouble();

        resultado = num1 + num2;

        System.out.println("Resultado: " + resultado);
    }

    public void resta() {
        double num1, num2, resultado;

        System.out.println("Ingresa el primer numero:");
        num1 = entrada.nextDouble();

        System.out.println("Ingresa el segundo numero:");
        num2 = entrada.nextDouble();

        resultado = num1 - num2;

        System.out.println("Resultado: " + resultado);
    }

    public void multiplicacion() {
        double num1, num2, resultado;

        System.out.println("Ingresa el primer numero:");
        num1 = entrada.nextDouble();

        System.out.println("Ingresa el segundo numero:");
        num2 = entrada.nextDouble();

        resultado = num1 * num2;

        System.out.println("Resultado: " + resultado);
    }

    public void division() {
        double num1, num2, resultado;

        System.out.println("Ingresa el primer numero:");
        num1 = entrada.nextDouble();

        System.out.println("Ingresa el segundo numero:");
        num2 = entrada.nextDouble();

        if (num2 == 0) {
            System.out.println("No se puede dividir entre 0");
        } else {
            resultado = num1 / num2;
            System.out.println("Resultado: " + resultado);
        }
    }

    public static void main(String[] args) {
        Ejercicios obj = new Ejercicios();
        obj.menu();
    }
}