package sistema;

/** Situaciones posibles al finalizar el cursado (antes era un String libre). */
public enum SituacionFinal {
    REGULAR("Regular"),
    LIBRE("Libre"),
    PROMOCIONADO("Promocionado");

    private final String texto;

    SituacionFinal(String texto) { this.texto = texto; }

    @Override
    public String toString() { return texto; }
}
