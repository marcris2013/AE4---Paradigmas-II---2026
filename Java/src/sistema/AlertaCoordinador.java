package sistema;

/** Alerta al Coordinador cuando un alumno alcanza el limite de inasistencias. */
public class AlertaCoordinador implements ObservadorInscripcion {

    private final Coordinador coordinador;
    private final int limite;

    public AlertaCoordinador(Coordinador coordinador, int limite) {
        this.coordinador = coordinador;
        this.limite = limite;
    }

    @Override
    public void actualizar(Inscripcion ins, EventoInscripcion evento) {
        if (evento == EventoInscripcion.INASISTENCIA_REGISTRADA && ins.getInasistencias() >= limite) {
            System.out.println("[ALERTA a Coordinador " + coordinador + "] "
                    + ins.getAlumno() + " acumula " + ins.getInasistencias()
                    + " inasistencias en " + ins.getMateria().getNombre() + " (riesgo de quedar libre).");
        }
    }
}
