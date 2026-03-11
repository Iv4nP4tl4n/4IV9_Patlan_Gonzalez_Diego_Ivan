public class Infografia_Try_Catch{

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("        INFOGRAFIA TRY - CATCH");
        System.out.println("====================================");

        System.out.println("\n¿QUE ES TRY - CATCH?");
        System.out.println("try-catch es una estructura en Java que se usa para manejar\nerrores o excepciones durante la ejecución de un programa y\nevitar que el programa se detenga.");
        System.err.println("\nPermite detectar un error y continuar ejecutando el programa.");

        System.out.println("\nESTRUCTURA BASICA:");
        System.out.println("try {");
        System.out.println("   // codigo que puede generar error");
        System.out.println("} catch (Exception e) {");
        System.out.println("   // codigo para manejar el error");
        System.out.println("}");

        System.err.println("\n¿COMO FUNCIONA?\n");

        System.err.println("Try:\n");
        System.out.println("* El bloque try contiene el código donde puede ocurrir un error o excepción.\n");
        System.out.println("EJEMPLO: ");
        System.out.println("try {");
        System.out.println("    int resultado = 10 / 0;");
        System.out.println("}\n");
        System.out.println("Uso profesional:\nEn el desarrollo de software se utiliza cuando se ejecutan\noperaciones que pueden fallar, como leer archivos, conectarse\na bases de datos o realizar cálculos.\n");
        
        System.err.println("Catch:\n");
        System.out.println("* El bloque Catch captura la excepción que ocurre en el Try y permite manejar el error.\n");
        System.out.println("EJEMPLO: ");
        System.out.println("try {");
        System.out.println("    int resultado = 10 / 0;");
        System.out.println("} catch (ArithmeticException e) {");
        System.out.println("    System.out.println(\"No se puede dividir entre cero\");");
        System.out.println("}\n");
        System.out.println("Uso profesional:\nLos programadores lo usan para mostrar mensajes de error\nclaros, registrar errores en el sistema o evitar que el programa\nse detenga.\n");

        System.err.println("Finally: \n");
        System.out.println("* El bloque Finally siempre se ejecuta, ocurra o no una excepción.\n");
        System.out.println("EJEMPLO: ");
        System.out.println("try {");
        System.out.println("    int num = 5;");
        System.out.println("} catch (Exception e) {");
        System.out.println("    System.out.println(\"Error\");");
        System.out.println("} finally {");
        System.out.println("    System.out.println(\"El programa terminó\");");
        System.out.println("}\n");
        System.out.println("Uso profesional:\nSe usa para cerrar archivos, conexiones de base de datos o\nliberar recursos después de ejecutar el programa.\n");

        System.out.println("Múltiples Catch: \n");
        System.out.println("* Permite manejar diferentes tipos de errores en un mismo bloque Try\n");
        System.out.println("EJEMPLO: ");
        System.out.println("try {");
        System.out.println("    int[] numeros = new int[3];");
        System.out.println("    numeros[5] = 10;");
        System.out.println("} catch (ArrayIndexOutOfBoundsException e) {");
        System.out.println("    System.out.println(\"Índice fuera del arreglo\");");
        System.out.println("} catch (Exception e) {");
        System.out.println("    System.out.println(\"Error general\");");
        System.out.println("}\n");
        System.out.println("Uso profesional:\nEn aplicaciones grandes se usa para manejar distintos errores\nde forma específica, facilitando la depuración y el\nmantenimiento del software.");

        System.out.println("\nTIPOS DE EXCEPCIONES:");
        System.out.println("- ArithmeticException:\nError matemático (división entre 0)");
        System.out.println("- NullPointerException:\nUsar un objeto que es null");
        System.out.println("- ArrayIndexOutOfBoundsException:\nÍndice fuera del tamaño del arreglo");
        System.out.println("- NumberFormatException:\nError al convertir texto a número");



        System.out.println("\nVENTAJAS:");
        System.out.println("- Evita que el programa se cierre");
        System.out.println("- Permite controlar errores");
        System.out.println("- Hace el programa mas seguro");

        System.out.println("\nCONCLUSION: ");
        System.out.println("El try-catch es una herramienta fundamental en Java para\ncontrolar errores durante la ejecución del programa,\npermitiendo que las aplicaciones sean más seguras, estables y\nfáciles de mantener.");

        System.out.println("\n====================================");
        System.out.println("         FIN DE LA INFOGRAFIA");
        System.out.println("====================================");

        System.out.println("Hecho por: Diego Ivan Patlan Gonzalez");
        System.out.println("                4IV9");

        System.out.println(" (\\_/)");
        System.out.println(" (o.o) (Trikitrakatelas!!)");
        System.out.println(" /|_|\\");

    }
}