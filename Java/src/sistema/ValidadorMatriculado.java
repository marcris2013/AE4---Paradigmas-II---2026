package sistema;

import java.util.Optional;

public class ValidadorMatriculado extends ValidadorInscripcion {
    @Override
    protected Optional<String> verificar(SolicitudInscripcion s) {
        if (!s.getCarrera().estaMatriculado(s.getAlumno())) {
            return Optional.of("El alumno no está matriculado en la carrera '"
                    + s.getCarrera().getNombre() + "', a la que pertenece la materia.");
        }
        return Optional.empty();
    }
}
