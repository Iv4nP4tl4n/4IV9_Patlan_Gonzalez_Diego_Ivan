/**
 *
 * @author ivanp
 */
package herenciapersona;

public class Estudiante extends Persona {

    private int numBoleta;

    public Estudiante(int numBoleta, String nombre, int edad, char genero) {
        super(nombre, edad, genero);
        this.numBoleta = numBoleta;
    }

    public int getNumBoleta() {
        return numBoleta;
    }

    public void setNumBoleta(int numBoleta) {
        this.numBoleta = numBoleta;
    }
}