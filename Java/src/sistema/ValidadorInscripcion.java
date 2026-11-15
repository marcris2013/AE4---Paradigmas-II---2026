package sistema;

import java.util.Optional;

/** PATRON CADENA DE RESPONSABILIDAD: cada eslabon valida una regla; el primero que falla corta la cadena. */
public abstract class ValidadorInscripcion {

    private ValidadorInscripcion siguiente;

    /** Devuelve el siguiente eslabon para poder encadenar: a.setSiguiente(b).setSiguiente(c). */
    public ValidadorInscripcion setSiguiente(ValidadorInscripcion siguiente) {
        this.siguiente = siguiente;
        return siguiente;
    }

    /** @return mensaje de error, o vacío si la solicitud es válida. */
    public final Optional<String> validar(SolicitudInscripcion s) {
        Optional<String> error = verificar(s);
        if (error.isPresent() || siguiente == null) return error;
        return siguiente.validar(s);
    }

    protected abstract Optional<String> verificar(SolicitudInscripcion s);
}
