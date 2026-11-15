package sistema;

/** Prueba manual automatizada sin bibliotecas externas (smoke test). */
public class PruebaIntegracion {
    public static void main(String[] args) {
        SistemaAcademicoFacade sistema = new SistemaAcademicoFacade();

        comprobar(sistema.getCarreras().size() == 2, "Debe inicializar dos carreras.");
        comprobar(sistema.getCarreras().get(0).getMaterias().size() == 6,
                "Ing en Sistemas debe tener dos materias por cada uno de sus tres cursos.");
        comprobar(sistema.getCarreras().get(1).getMaterias().size() == 4,
                "Analista Contable debe tener dos materias por cada uno de sus dos cursos.");

        comprobar(sistema.matricularAlumno("Juan", "Perez", 12345678, 1001, 0)
                .contains("exito"), "Debe matricular al alumno.");
        comprobar(sistema.inscribirAlumno(1001, 0, "Programacion I")
                .contains("exito"), "Debe permitir la inscripcion correcta.");
        comprobar(sistema.inscribirAlumno(1001, 0, "Programacion I")
                .toLowerCase().contains("inscripto"), "Debe impedir la inscripcion duplicada.");
        String intentoCarreraIncorrecta = sistema.inscribirAlumno(1001, 0, "Contabilidad I");
        comprobar(intentoCarreraIncorrecta.toLowerCase().contains("no pertenece"),
                "La Chain debe rechazar una materia de otra carrera.");

        sistema.registrarAsistencia(1001, "Programacion I", false);
        sistema.registrarAsistencia(1001, "Programacion I", false);
        sistema.registrarAsistencia(1001, "Programacion I", false);
        Inscripcion inscripcion = sistema.buscarInscripcion(1001, "Programacion I");
        comprobar(inscripcion != null && inscripcion.getInasistencias() == 3,
                "Debe actualizar la cantidad de inasistencias.");
        comprobar(sistema.cargarSituacionFinal(1001, "Programacion I", "Promocionado")
                .toLowerCase().contains("correctamente"), "Debe cargar la situacion final.");
        comprobar(inscripcion.estaFinalizada()
                && inscripcion.getSituacionFinal() == SituacionFinal.PROMOCIONADO,
                "State debe cambiar la inscripcion a finalizada.");
        comprobar(sistema.registrarAsistencia(1001, "Programacion I", true)
                .toLowerCase().contains("ya finaliz"), "No debe admitir asistencia luego de finalizar.");

        System.out.println("PRUEBAS DE INTEGRACION: OK");
    }

    private static void comprobar(boolean condicion, String mensaje) {
        if (!condicion) throw new IllegalStateException("FALLO: " + mensaje);
    }
}
