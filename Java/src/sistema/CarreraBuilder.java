package sistema;

import java.util.ArrayList;
import java.util.List;

/** PATRON BUILDER: construye una Carrera validando que este completa y coherente. */
public class CarreraBuilder {

    private String nombre;
    private int duracion;
    private Coordinador coordinador;
    private double precioInscripcion = -1;
    private double precioCuota = -1;
    private final List<Materia> materias = new ArrayList<>();

    public CarreraBuilder nombre(String nombre) { this.nombre = nombre; return this; }
    public CarreraBuilder duracion(int anios) { this.duracion = anios; return this; }
    public CarreraBuilder coordinador(Coordinador c) { this.coordinador = c; return this; }
    public CarreraBuilder precioInscripcion(double p) { this.precioInscripcion = p; return this; }
    public CarreraBuilder precioCuota(double p) { this.precioCuota = p; return this; }
    public CarreraBuilder agregarMateria(Materia m) { this.materias.add(m); return this; }

    public Carrera build() {
        if (nombre == null || nombre.isBlank())
            throw new IllegalStateException("La carrera necesita un nombre.");
        if (duracion <= 0)
            throw new IllegalStateException("La duración debe ser mayor a 0.");
        if (coordinador == null)
            throw new IllegalStateException("La carrera necesita un coordinador.");
        if (precioInscripcion < 0 || precioCuota < 0)
            throw new IllegalStateException("Los precios deben estar definidos y ser >= 0.");

        Carrera carrera = new Carrera(this);
        for (Materia m : materias) {
            if (m.getCurso() < 1 || m.getCurso() > duracion)
                throw new IllegalStateException("La materia '" + m.getNombre()
                        + "' tiene un curso fuera de la duración de la carrera.");
            if (!carrera.agregarMateria(m))
                throw new IllegalStateException("Materia duplicada: " + m.getNombre());
        }
        return carrera;
    }

    // Acceso de solo lectura para Carrera (mismo paquete)
    String getNombre() { return nombre; }
    int getDuracion() { return duracion; }
    Coordinador getCoordinador() { return coordinador; }
    double getPrecioInscripcion() { return precioInscripcion; }
    double getPrecioCuota() { return precioCuota; }
}
