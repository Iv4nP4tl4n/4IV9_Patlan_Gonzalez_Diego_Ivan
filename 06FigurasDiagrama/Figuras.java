import java.util.Scanner;
public class Figuras {

    Scanner entrada = new Scanner(System.in);

    public void menu(){
        int opcion;

        do { 
            System.out.println("----------------------------------------");
            System.out.println("Calculadora de Areas y Perimentros ");
            System.out.println("Elige una opcion ");
            System.out.println("1.- Circulo ");
            System.out.println("2.- Cuadrado ");
            System.out.println("3.- Triangulo ");
            System.out.println("4.- Pentagono ");
            System.out.println("5.- Hexagono ");
            System.out.println("6.- Salir \n");
            System.out.println("----------------------------------------");

            opcion = entrada.nextInt();

        switch (opcion) {
            case 1:
                circulo();
                break;
            case 2:
                cuadrado();
                break;
            case 3:
                triangulo();
                break;
            case 4:
                pentagono();
                break;
            case 5:
                hexagono();
                break;
            case 6:
                System.out.println("Saliendo...");
                System.out.println("-------------------------------");
                System.out.println(" /\\_/\\");
                System.out.println("( o.o )  (Saqueme 10 digo Miau!!....)");
                System.out.println("(> ^ <)");
                System.out.println("-------------------------------");
                break;
            default:
                System.out.println("Opcion invalida");
            }

        } while (opcion != 6);
    }

    public void circulo(){
        double radio, area, perimetro;

        System.out.println("Ingrese el radio:  ");
        radio = entrada.nextDouble();

        perimetro = radio*2*Math.PI;
        area = Math.PI*radio*radio;

        System.out.println("----------------------------------------");
        System.out.println("Perimetro: "+perimetro);
        System.out.println("Area: "+area);

    }

    public void cuadrado(){
        double lado, area, perimetro;

        System.out.println("Ingrese el lado:  ");
        lado = entrada.nextDouble();

        perimetro = lado*4;
        area = lado*lado;

        System.out.println("----------------------------------------");
        System.out.println("Perimetro: "+perimetro);
        System.out.println("Area: "+area);
    }

    public void triangulo(){
        double base, altura, lado1, lado2, lado3, area, perimetro;

        System.out.println("Ingrese lado 1:  ");
        lado1 = entrada.nextDouble();

        System.out.println("Ingrese lado 2:  ");
        lado2 = entrada.nextDouble();

        System.out.println("Ingrese lado 3:  ");
        lado3 = entrada.nextDouble();

        System.out.println("Ingrese la base:  ");
        base = entrada.nextDouble();

        System.out.println("Ingrese la altura:  ");
        altura = entrada.nextDouble();

        area = (base * altura) / 2;
        perimetro = lado1 + lado2 + lado3;

        System.out.println("----------------------------------------");
        System.out.println("Perimetro: "+perimetro);
        System.out.println("Area: "+area);
    }

    public void pentagono(){
        double lado, apotema, area, perimetro;

        System.out.println("Ingrese el lado:");
        lado = entrada.nextDouble();
        System.out.println("Ingrese el apotema:");
        apotema = entrada.nextDouble();

        perimetro = 5 * lado;
        area = (perimetro * apotema) / 2;

        System.out.println("----------------------------------------");
        System.out.println("Perimetro: " + perimetro);
        System.out.println("Area: " + area);
    }

    public void hexagono(){
        double lado, area, perimetro;

        System.out.println("Ingrese el lado:");
        lado = entrada.nextDouble();

        perimetro = 6 * lado;
        area = (3 * Math.sqrt(3) * lado * lado) / 2;

        System.out.println("----------------------------------------");
        System.out.println("Perimetro: " + perimetro);
        System.out.println("Area: " + area);
    }

    public static void main(String[] args) {
        Figuras obj = new Figuras();
        obj.menu();
    }

}
