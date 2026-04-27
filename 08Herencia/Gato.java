public class Gato extends Animal{

    int num_vidas;

    public Gato(){

    }

    public Gato(String nom, String raz, String tipo_alime, int edad, int num_vidas){
        //como puedo obtenerlo ocupo la palabra reservada super
        super(nom, raz, tipo_alime, edad);
        this.num_vidas = num_vidas;
    }
    //quiero acceder a los atributos padre

    public int getNum_vidas(){
        return num_vidas;
    }

    public void seyNum_vidas(int num_vidas) {
        this.num_vidas = num_vidas;
    }

    public void mostrarGato(){
        System.out.println("El nombre del michi: " +getNombre()+"\n"
        +"su raza es: "+ getRaza()+ "\n"
        +"Su comida es: "+ getTipo_alimento()+"\n"
        +"Su edad es: "+ getEdad()+"\n"
        +"El numero de vidas restantes son: "+ getNum_vidas()+"\n");
    }
}
