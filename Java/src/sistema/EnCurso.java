package sistema;

public class EnCurso implements EstadoInscripcion {

    @Override
    public void registrarAsistencia(Inscripcion i) {
        i.sumarAsistencia();
        i.notificar(EventoInscripcion.ASISTENCIA_REGISTRADA);
    }

    @Override
    public void registrarInasistencia(Inscripcion i) {
        i.sumarInasistencia();
        i.notificar(EventoInscripcion.INASISTENCIA_REGISTRADA);
    }

    @Override
    public void cargarSituacionFinal(Inscripcion i, SituacionFinal situacion) {
        i.cambiarEstado(new Finalizada(situacion));
        i.notificar(EventoInscripcion.SITUACION_CARGADA);
    }

    @Override public boolean estaFinalizada() { return false; }
    @Override public SituacionFinal getSituacion() { return null; }
    @Override public String descripcion() { return "En curso"; }
}
