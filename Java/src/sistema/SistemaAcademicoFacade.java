package sistema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Fachada del sistema academico.
 * Centraliza los casos de uso y evita que el menu conozca las reglas internas.
 */
public class SistemaAcademicoFacade {

    private static final int LIMITE_INASISTENCIAS_ALERTA = 3;

    private final List<Carrera> carreras = new ArrayList<>();
    private final List<Alumno> alumnos = new ArrayList<>();
    private final List<Profesor> profesores = new ArrayList<>();
    private final List<Coordinador> coordinadores = new ArrayList<>();
    private final List<Inscripcion> inscripciones = new ArrayList<>();
    private final ValidadorInscripcion cadenaValidacion;

    public SistemaAcademicoFacade() {
        ValidadorInscripcion primero = new ValidadorMatriculado();
        primero.setSiguiente(new ValidadorMateriaEnCarrera())
               .setSiguiente(new ValidadorNoInscripto());
        this.cadenaValidacion = primero;
        cargarDatosIniciales();
    }

    /** Carga dos carreras y dos materias por cada curso de cada carrera. */
    private void cargarDatosIniciales() {
        Profesor carlos = new Profesor("Carlos", "Gomez", 12345678);
        Profesor laura = new Profesor("Laura", "Fernandez", 23456789);
        Profesor mariana = new Profesor("Mariana", "Ruiz", 34567890);
        Profesor pablo = new Profesor("Pablo", "Luna", 45678901);
        Profesor sofia = new Profesor("Sofia", "Paz", 56789012);
        Profesor roberto = new Profesor("Roberto", "Diaz", 67890123);

        Coordinador ana = new Coordinador("Ana", "Martinez", 87654321);
        Coordinador diego = new Coordinador("Diego", "Sosa", 98765432);

        profesores.addAll(List.of(carlos, laura, mariana, pablo, sofia, roberto));
        coordinadores.addAll(List.of(ana, diego));

        Carrera sistemas = new CarreraBuilder()
                .nombre("Ing en Sistemas")
                .duracion(3)
                .coordinador(ana)
                .precioInscripcion(15000.0)
                .precioCuota(20000.0)
                .agregarMateria(new Materia("Programacion I", 1, 1, carlos))
                .agregarMateria(new Materia("Matematica I", 1, 2, laura))
                .agregarMateria(new Materia("Programacion II", 2, 1, carlos))
                .agregarMateria(new Materia("Bases de Datos", 2, 2, mariana))
                .agregarMateria(new Materia("Ingenieria de Software", 3, 1, mariana))
                .agregarMateria(new Materia("Redes", 3, 2, pablo))
                .build();

        Carrera contable = new CarreraBuilder()
                .nombre("Analista Contable")
                .duracion(2)
                .coordinador(diego)
                .precioInscripcion(18000.0)
                .precioCuota(22000.0)
                .agregarMateria(new Materia("Contabilidad I", 1, 1, sofia))
                .agregarMateria(new Materia("Matematica I", 1, 2, laura))
                .agregarMateria(new Materia("Contabilidad II", 2, 1, sofia))
                .agregarMateria(new Materia("Impuestos", 2, 2, roberto))
                .build();

        carreras.add(sistemas);
        carreras.add(contable);
    }

    public List<Carrera> getCarreras() {
        return Collections.unmodifiableList(carreras);
    }

    public List<Alumno> getAlumnos() {
        return Collections.unmodifiableList(alumnos);
    }

    public List<Profesor> getProfesores() {
        return Collections.unmodifiableList(profesores);
    }

    public List<Coordinador> getCoordinadores() {
        return Collections.unmodifiableList(coordinadores);
    }

    public List<Inscripcion> getInscripciones() {
        return Collections.unmodifiableList(inscripciones);
    }

    public Alumno buscarAlumnoPorLegajo(int legajo) {
        for (Alumno alumno : alumnos) {
            if (alumno.getLegajo() == legajo) return alumno;
        }
        return null;
    }

    public Carrera buscarCarreraPorNombre(String nombre) {
        for (Carrera carrera : carreras) {
            if (carrera.getNombre().equalsIgnoreCase(nombre)) return carrera;
        }
        return null;
    }

    public Materia buscarMateriaEnCarrera(Carrera carrera, String nombreMateria) {
        if (carrera == null || nombreMateria == null) return null;
        return carrera.buscarMateria(nombreMateria);
    }

    private Materia buscarMateriaPorNombre(String nombreMateria) {
        if (nombreMateria == null) return null;
        for (Carrera carrera : carreras) {
            Materia materia = carrera.buscarMateria(nombreMateria);
            if (materia != null) return materia;
        }
        return null;
    }

    public Inscripcion buscarInscripcion(int legajo, String nombreMateria) {
        for (Inscripcion inscripcion : inscripciones) {
            if (inscripcion.getAlumno().getLegajo() == legajo
                    && inscripcion.getMateria().getNombre().equalsIgnoreCase(nombreMateria)) {
                return inscripcion;
            }
        }
        return null;
    }

    public String matricularAlumno(String nombre, String apellido, int dni,
                                   int legajo, int indiceCarrera) {
        if (nombre == null || nombre.isBlank() || apellido == null || apellido.isBlank()) {
            return "Error: nombre y apellido son obligatorios.";
        }
        if (legajo <= 0 || dni <= 0) return "Error: DNI y legajo deben ser positivos.";
        if (buscarAlumnoPorLegajo(legajo) != null) {
            return "Error: ya existe un alumno con ese legajo.";
        }
        if (indiceCarrera < 0 || indiceCarrera >= carreras.size()) {
            return "Opcion de carrera invalida.";
        }

        Carrera carrera = carreras.get(indiceCarrera);
        Alumno nuevoAlumno = new Alumno(nombre.trim(), apellido.trim(), dni, legajo);
        if (!carrera.matricularAlumno(nuevoAlumno)) {
            return "El alumno ya estaba matriculado en la carrera.";
        }
        alumnos.add(nuevoAlumno);
        return "Alumno matriculado con exito en " + carrera.getNombre();
    }

    public String inscribirAlumno(int legajo, int indiceCarrera, String nombreMateria) {
        Alumno alumno = buscarAlumnoPorLegajo(legajo);
        if (alumno == null) return "No existe un alumno con ese legajo.";
        if (indiceCarrera < 0 || indiceCarrera >= carreras.size()) {
            return "Opcion de carrera invalida.";
        }

        Carrera carrera = carreras.get(indiceCarrera);
        // Se busca dentro de la carrera seleccionada, porque puede haber materias
        // con el mismo nombre en carreras distintas.
        Materia materia = carrera.buscarMateria(nombreMateria);
        if (materia == null) {
            // Si existe en otra carrera, dejamos que la Chain informe la regla
            // exacta de pertenencia. La busqueda local tiene prioridad para
            // desambiguar materias con el mismo nombre en distintas carreras.
            materia = buscarMateriaPorNombre(nombreMateria);
        }
        if (materia == null) return "No existe una materia con ese nombre.";

        SolicitudInscripcion solicitud = new SolicitudInscripcion(
                alumno, carrera, materia, inscripciones);
        Optional<String> error = cadenaValidacion.validar(solicitud);
        if (error.isPresent()) return error.get();

        Inscripcion nueva = new Inscripcion(alumno, materia);
        nueva.agregarObservador(new NotificadorAlumno());
        nueva.agregarObservador(new AlertaCoordinador(
                carrera.getCoordinador(), LIMITE_INASISTENCIAS_ALERTA));
        inscripciones.add(nueva);
        return "Inscripcion realizada con exito.";
    }

    public String registrarAsistencia(int legajo, String nombreMateria, boolean presente) {
        Inscripcion inscripcion = buscarInscripcion(legajo, nombreMateria);
        if (inscripcion == null) return "No existe una inscripcion para ese alumno y materia.";
        try {
            if (presente) inscripcion.registrarAsistencia();
            else inscripcion.registrarInasistencia();
            return presente ? "Asistencia registrada correctamente."
                            : "Inasistencia registrada correctamente.";
        } catch (IllegalStateException ex) {
            return ex.getMessage();
        }
    }

    public String cargarSituacionFinal(int legajo, String nombreMateria, String estadoTexto) {
        Inscripcion inscripcion = buscarInscripcion(legajo, nombreMateria);
        if (inscripcion == null) return "No existe una inscripcion para ese alumno y materia.";
        if (estadoTexto == null) return "Debe indicar una situacion final.";

        SituacionFinal situacion;
        try {
            situacion = SituacionFinal.valueOf(estadoTexto.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            return "Situacion invalida. Use Regular, Libre o Promocionado.";
        }

        try {
            inscripcion.cargarSituacionFinal(situacion);
            return "Situacion final registrada correctamente.";
        } catch (IllegalStateException ex) {
            return ex.getMessage();
        }
    }

    public List<Inscripcion> inscripcionesDeMateria(Materia materia) {
        List<Inscripcion> resultado = new ArrayList<>();
        for (Inscripcion inscripcion : inscripciones) {
            if (inscripcion.getMateria() == materia) resultado.add(inscripcion);
        }
        return Collections.unmodifiableList(resultado);
    }

    public Carrera carreraDelAlumno(Alumno alumno) {
        for (Carrera carrera : carreras) {
            if (carrera.estaMatriculado(alumno)) return carrera;
        }
        return null;
    }
}
