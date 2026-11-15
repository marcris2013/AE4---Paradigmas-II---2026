package sistema;

public class Finalizada implements EstadoInscripcion {

    private final SituacionFinal situacion;

    public Finalizada(SituacionFinal situacion) {
        this.situacion = situacion;
    }

    @Override
    public void registrarAsistencia(Inscripcion i) {
        throw new IllegalStateException("El cursado ya finalizó: no se puede registrar asistencia.");
    }

    @Override
    public void registrarInasistencia(Inscripcion i) {
        throw new IllegalStateException("El cursado ya finalizó: no se puede registrar inasistencia.");
    }

    @Override
    public void cargarSituacionFinal(Inscripcion i, SituacionFinal nueva) {
        throw new IllegalStateException("La situación final ya fue cargada (" + situacion + ").");
    }

    @Override public boolean estaFinalizada() { return true; }
    @Override public SituacionFinal getSituacion() { return situacion; }
    @Override public String descripcion() { return situacion.toString(); }
}
