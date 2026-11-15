package sistema;

public class Alumno {
    private final String nombre;
    private final String apellido;
    private final int dni;
    private final int legajo;

    public Alumno(String nombre, String apellido, int dni, int legajo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.legajo = legajo;
    }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public int getDni() { return dni; }
    public int getLegajo() { return legajo; }

    @Override
    public String toString() {
        return apellido + ", " + nombre + " (Legajo " + legajo + ")";
    }
}
