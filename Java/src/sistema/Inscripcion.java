package sistema;

import java.util.ArrayList;
import java.util.List;

/** Contexto del State y Sujeto del Observer. */
public class Inscripcion {

    private final Alumno alumno;
    private final Materia materia;
    private int asistencias;
    private int inasistencias;
    private EstadoInscripcion estado;
    private final List<ObservadorInscripcion> observadores = new ArrayList<>();

    public Inscripcion(Alumno alumno, Materia materia) {
        this.alumno = alumno;
        this.materia = materia;
        this.estado = new EnCurso();
    }

    // ---- Observer ----
    public void agregarObservador(ObservadorInscripcion o) { observadores.add(o); }

    void notificar(EventoInscripcion evento) {
        for (ObservadorInscripcion o : observadores) o.actualizar(this, evento);
    }

    // ---- Operaciones delegadas al estado ----
    public void registrarAsistencia() { estado.registrarAsistencia(this); }
    public void registrarInasistencia() { estado.registrarInasistencia(this); }
    public void cargarSituacionFinal(SituacionFinal s) { estado.cargarSituacionFinal(this, s); }

    // ---- Uso interno de los estados ----
    void cambiarEstado(EstadoInscripcion nuevo) { this.estado = nuevo; }
    void sumarAsistencia() { asistencias++; }
    void sumarInasistencia() { inasistencias++; }

    // ---- Consultas ----
    public boolean estaFinalizada() { return estado.estaFinalizada(); }
    public SituacionFinal getSituacionFinal() { return estado.getSituacion(); }
    public String getDescripcionEstado() { return estado.descripcion(); }
    public Alumno getAlumno() { return alumno; }
    public Materia getMateria() { return materia; }
    public int getAsistencias() { return asistencias; }
    public int getInasistencias() { return inasistencias; }
}
