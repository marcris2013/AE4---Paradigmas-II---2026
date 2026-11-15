package sistema;

import java.util.List;

public class SolicitudInscripcion {
    private final Alumno alumno;
    private final Carrera carrera;
    private final Materia materia;
    private final List<Inscripcion> inscripcionesExistentes;

    public SolicitudInscripcion(Alumno alumno, Carrera carrera, Materia materia,
                                List<Inscripcion> inscripcionesExistentes) {
        this.alumno = alumno;
        this.carrera = carrera;
        this.materia = materia;
        this.inscripcionesExistentes = inscripcionesExistentes;
    }

    public Alumno getAlumno() { return alumno; }
    public Carrera getCarrera() { return carrera; }
    public Materia getMateria() { return materia; }
    public List<Inscripcion> getInscripcionesExistentes() { return inscripcionesExistentes; }
}
