package sistema;

import java.util.Optional;

public class ValidadorNoInscripto extends ValidadorInscripcion {
    @Override
    protected Optional<String> verificar(SolicitudInscripcion s) {
        for (Inscripcion ins : s.getInscripcionesExistentes()) {
            if (ins.getAlumno().getLegajo() == s.getAlumno().getLegajo()
                    && ins.getMateria() == s.getMateria()) {
                return Optional.of("El alumno ya se encuentra inscripto a esta materia.");
            }
        }
        return Optional.empty();
    }
}
