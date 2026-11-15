package sistema;

public class Profesor {

    private final String nombre;
    private final String apellido;
    private final int dni;

    public Profesor(String nombre, String apellido, int dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public int getDni() { return dni; }

    @Override
    public String toString() { return nombre + " " + apellido; }
}
