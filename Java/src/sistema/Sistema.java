package sistema;

import java.util.List;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

/** Interfaz de consola. Toda regla de negocio se delega a SistemaAcademicoFacade. */
public class Sistema {

    private final Scanner scanner = new Scanner(System.in);
    private final SistemaAcademicoFacade fachada = new SistemaAcademicoFacade();

    public void menu() {
        int opcion;
        do {
            System.out.println("\n--- SISTEMA DE GESTION ACADEMICA ---");
            System.out.println("1. Matricular alumno en una carrera");
            System.out.println("2. Inscribir alumno a una materia");
            System.out.println("3. Registrar asistencia / inasistencia");
            System.out.println("4. Cargar situacion final");
            System.out.println("5. Mostrar carreras y materias");
            System.out.println("6. Mostrar alumnos de una materia (cursando/finalizados)");
            System.out.println("7. Mostrar condicion de un alumno");
            System.out.println("0. Salir");
            opcion = leerEntero("Opcion: ");

            switch (opcion) {
                case 1 -> matricularAlumno();
                case 2 -> inscribirAlumno();
                case 3 -> registrarAsistencia();
                case 4 -> cargarSituacionFinal();
                case 5 -> mostrarCarreras();
                case 6 -> mostrarAlumnosMateria();
                case 7 -> mostrarCondicionAlumno();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion incorrecta.");
            }
        } while (opcion != 0);
    }

    private void matricularAlumno() {
        System.out.println("\n--- MATRICULAR ALUMNO ---");
        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        int dni = leerEntero("DNI: ");
        int legajo = leerEntero("Legajo: ");
        int indice = elegirCarrera();
        if (indice >= 0) System.out.println(fachada.matricularAlumno(nombre, apellido, dni, legajo, indice));
    }

    private void inscribirAlumno() {
        System.out.println("\n--- INSCRIBIR ALUMNO A MATERIA ---");
        int legajo = leerEntero("Legajo del alumno: ");
        int indice = elegirCarrera();
        if (indice < 0) return;
        Carrera carrera = fachada.getCarreras().get(indice);
        System.out.println("Materias de " + carrera.getNombre() + ":");
        for (Materia materia : carrera.getMaterias()) System.out.println("- " + materia.getNombre());
        String nombreMateria = leerTexto("Nombre de la materia: ");
        System.out.println(fachada.inscribirAlumno(legajo, indice, nombreMateria));
    }

    private void registrarAsistencia() {
        System.out.println("\n--- REGISTRAR ASISTENCIA DE UNA CLASE ---");
        int indice = elegirCarrera();
        if (indice < 0) return;
        Carrera carrera = fachada.getCarreras().get(indice);
        Materia materia = elegirMateria(carrera);
        if (materia == null) return;

        List<Inscripcion> inscriptos = fachada.inscripcionesDeMateria(materia);
        boolean hayAlumnosEnCurso = false;
        for (Inscripcion inscripcion : inscriptos) {
            if (inscripcion.estaFinalizada()) continue;
            hayAlumnosEnCurso = true;
            System.out.println("\nAlumno: " + inscripcion.getAlumno());
            System.out.println("1. Presente\n2. Ausente");
            int opcion = leerEntero("Seleccione: ");
            if (opcion == 1 || opcion == 2) {
                System.out.println(fachada.registrarAsistencia(
                        inscripcion.getAlumno().getLegajo(), materia.getNombre(), opcion == 1));
            } else {
                System.out.println("Opcion incorrecta; no se registro la asistencia.");
            }
        }
        if (!hayAlumnosEnCurso) {
            System.out.println("No hay alumnos cursando esta materia.");
        }
    }

    private void cargarSituacionFinal() {
        System.out.println("\n--- CARGAR SITUACION FINAL ---");
        int legajo = leerEntero("Legajo: ");
        String materia = leerTexto("Nombre de la materia: ");
        String estado = leerTexto("Estado final (Regular, Libre o Promocionado): ");
        System.out.println(fachada.cargarSituacionFinal(legajo, materia, estado));
    }

    private void mostrarCarreras() {
        System.out.println("\n--- CARRERAS REGISTRADAS ---");
        for (Carrera carrera : fachada.getCarreras()) {
            System.out.println(carrera);
            for (Materia materia : carrera.getMaterias()) System.out.println("  - " + materia);
        }
    }

    private void mostrarAlumnosMateria() {
        System.out.println("\n--- ALUMNOS DE UNA MATERIA ---");
        int indice = elegirCarrera();
        if (indice < 0) return;
        Carrera carrera = fachada.getCarreras().get(indice);
        Materia materia = elegirMateria(carrera);
        if (materia == null) return;
        System.out.println("1. Alumnos que estan cursando\n2. Alumnos que finalizaron");
        int tipo = leerEntero("Seleccione: ");
        if (tipo != 1 && tipo != 2) {
            System.out.println("Opcion incorrecta.");
            return;
        }
        boolean mostrarFinalizados = tipo == 2;
        boolean encontrado = false;
        for (Inscripcion inscripcion : fachada.inscripcionesDeMateria(materia)) {
            if (inscripcion.estaFinalizada() != mostrarFinalizados) continue;
            encontrado = true;
            System.out.print("- " + inscripcion.getAlumno()
                    + " | Inasistencias: " + inscripcion.getInasistencias());
            if (mostrarFinalizados) System.out.print(" | Situacion final: " + inscripcion.getSituacionFinal());
            else System.out.print(" | Estado: " + inscripcion.getDescripcionEstado());
            System.out.println();
        }
        if (!encontrado) System.out.println("No hay alumnos en esa condicion.");
    }

    private void mostrarCondicionAlumno() {
        int legajo = leerEntero("Legajo: ");
        Alumno alumno = fachada.buscarAlumnoPorLegajo(legajo);
        if (alumno == null) {
            System.out.println("No existe ese alumno.");
            return;
        }
        Carrera carrera = fachada.carreraDelAlumno(alumno);
        System.out.println("Alumno: " + alumno);
        System.out.println("Carrera: " + (carrera == null ? "Sin matricula" : carrera.getNombre()));
        boolean existe = false;
        for (Inscripcion inscripcion : fachada.getInscripciones()) {
            if (inscripcion.getAlumno().getLegajo() == legajo) {
                existe = true;
                System.out.println("- " + inscripcion.getMateria().getNombre()
                        + " | Asistencias: " + inscripcion.getAsistencias()
                        + " | Inasistencias: " + inscripcion.getInasistencias()
                        + " | Situacion: " + inscripcion.getDescripcionEstado());
            }
        }
        if (!existe) System.out.println("El alumno no tiene inscripciones a materias.");
    }

    private int elegirCarrera() {
        List<Carrera> carreras = fachada.getCarreras();
        System.out.println("Carreras disponibles:");
        for (int i = 0; i < carreras.size(); i++) {
            System.out.println((i + 1) + ". " + carreras.get(i).getNombre());
        }
        int indice = leerEntero("Seleccione carrera: ") - 1;
        if (indice < 0 || indice >= carreras.size()) {
            System.out.println("Opcion de carrera invalida.");
            return -1;
        }
        return indice;
    }


    private Materia elegirMateria(Carrera carrera) {
        List<Materia> materias = carrera.getMaterias();
        if (materias.isEmpty()) {
            System.out.println("La carrera no tiene materias cargadas.");
            return null;
        }
        System.out.println("Materias de " + carrera.getNombre() + ":");
        for (int i = 0; i < materias.size(); i++) {
            System.out.println((i + 1) + ". " + materias.get(i).getNombre());
        }
        int indice = leerEntero("Seleccione materia: ") - 1;
        if (indice < 0 || indice >= materias.size()) {
            System.out.println("Opcion de materia invalida.");
            return null;
        }
        return materias.get(indice);
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException ex) {
                System.out.println("Ingrese un numero entero valido.");
            }
        }
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    public static void main(String[] args) {
        System.setOut(new java.io.PrintStream(System.out, true, StandardCharsets.UTF_8));
        new Sistema().menu();
    }
}
