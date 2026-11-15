package sistema;

/** Avisa al alumno cuando hay una inasistencia o se carga su situación final. */
public class NotificadorAlumno implements ObservadorInscripcion {

    @Override
    public void actualizar(Inscripcion ins, EventoInscripcion evento) {
        String destino = "[Aviso a " + ins.getAlumno().getNombre() + " " + ins.getAlumno().getApellido() + "] ";
        switch (evento) {
            case INASISTENCIA_REGISTRADA -> System.out.println(destino + "Inasistencia en "
                    + ins.getMateria().getNombre() + ". Total: " + ins.getInasistencias());
            case SITUACION_CARGADA -> System.out.println(destino + "Su situación final en "
                    + ins.getMateria().getNombre() + " es: " + ins.getSituacionFinal());
            default -> { }
        }
    }
}
