package sistema;

import java.util.Optional;

public class ValidadorMateriaEnCarrera extends ValidadorInscripcion {
    @Override
    protected Optional<String> verificar(SolicitudInscripcion s) {
        if (!s.getCarrera().tieneMateria(s.getMateria())) {
            return Optional.of("La materia '" + s.getMateria().getNombre()
                    + "' no pertenece a la carrera '" + s.getCarrera().getNombre() + "'.");
        }
        return Optional.empty();
    }
}
