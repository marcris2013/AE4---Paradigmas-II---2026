package sistema;

/** PATRON STATE: el comportamiento de una Inscripcion depende de su estado (en curso / finalizada). */
public interface EstadoInscripcion {
    void registrarAsistencia(Inscripcion i);
    void registrarInasistencia(Inscripcion i);
    void cargarSituacionFinal(Inscripcion i, SituacionFinal situacion);
    boolean estaFinalizada();
    SituacionFinal getSituacion();
    String descripcion();
}
