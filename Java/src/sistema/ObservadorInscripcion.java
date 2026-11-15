package sistema;

/** PATRON OBSERVER: interesados en los cambios de una Inscripcion. */
public interface ObservadorInscripcion {
    void actualizar(Inscripcion inscripcion, EventoInscripcion evento);
}
