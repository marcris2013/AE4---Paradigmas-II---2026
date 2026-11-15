% AE4 - Base de conocimiento del sistema universitario.
% Parte del Integrante 1: hechos iniciales + consultas 1 y 2.
% Los identificadores son atomos unicos, incluso cuando se repite el nombre
% de una materia en dos carreras distintas (por ejemplo, Matematica I).

% ---------------- CARRERAS ----------------
% carrera(IdCarrera, Nombre).
carrera(ing_sistemas, 'Ing en Sistemas').
carrera(analista_contable, 'Analista Contable').

% ---------------- ALUMNOS ----------------
% alumno(Legajo, Nombre, Apellido).
alumno(1001, 'Juan', 'Perez').
alumno(1002, 'Maria', 'Lopez').
alumno(1003, 'Pedro', 'Ruiz').
alumno(1004, 'Lucas', 'Gomez').
alumno(1005, 'Sofia', 'Diaz').

% ---------------- MATRICULACIONES ----------------
% matriculado(Legajo, IdCarrera).
matriculado(1001, ing_sistemas).
matriculado(1002, ing_sistemas).
matriculado(1003, ing_sistemas).
matriculado(1004, analista_contable).
matriculado(1005, analista_contable).

% ---------------- PROFESORES ----------------
% profesor(IdProfesor, Nombre, Apellido).
profesor(carlos_gomez, 'Carlos', 'Gomez').
profesor(laura_fernandez, 'Laura', 'Fernandez').
profesor(mariana_ruiz, 'Mariana', 'Ruiz').
profesor(pablo_luna, 'Pablo', 'Luna').
profesor(sofia_paz, 'Sofia', 'Paz').
profesor(roberto_diaz, 'Roberto', 'Diaz').

% ---------------- MATERIAS ----------------
% materia(IdMateria, Nombre, IdCarrera, Curso, Cuatrimestre, IdProfesor).
materia(programacion_i, 'Programacion I', ing_sistemas, 1, 1, carlos_gomez).
materia(matematica_i_sistemas, 'Matematica I', ing_sistemas, 1, 2, laura_fernandez).
materia(programacion_ii, 'Programacion II', ing_sistemas, 2, 1, carlos_gomez).
materia(bases_de_datos, 'Bases de Datos', ing_sistemas, 2, 2, mariana_ruiz).
materia(ingenieria_de_software, 'Ingenieria de Software', ing_sistemas, 3, 1, mariana_ruiz).
materia(redes, 'Redes', ing_sistemas, 3, 2, pablo_luna).

materia(contabilidad_i, 'Contabilidad I', analista_contable, 1, 1, sofia_paz).
materia(matematica_i_contable, 'Matematica I', analista_contable, 1, 2, laura_fernandez).
materia(contabilidad_ii, 'Contabilidad II', analista_contable, 2, 1, sofia_paz).
materia(impuestos, 'Impuestos', analista_contable, 2, 2, roberto_diaz).

% ---------------- INSCRIPCIONES DE PRUEBA ----------------
% inscripto(Legajo, IdMateria).
inscripto(1001, programacion_i).
inscripto(1001, matematica_i_sistemas).
inscripto(1002, programacion_i).
inscripto(1003, programacion_i).
inscripto(1004, matematica_i_contable).
inscripto(1005, contabilidad_i).

% inasistencias(Legajo, IdMateria, Cantidad).
inasistencias(1001, programacion_i, 1).
inasistencias(1001, matematica_i_sistemas, 2).
inasistencias(1002, programacion_i, 3).
inasistencias(1003, programacion_i, 4).
inasistencias(1004, matematica_i_contable, 0).
inasistencias(1005, contabilidad_i, 2).

% estado_inscripcion(Legajo, IdMateria, Estado).
estado_inscripcion(1001, programacion_i, finalizada).
estado_inscripcion(1001, matematica_i_sistemas, finalizada).
estado_inscripcion(1002, programacion_i, finalizada).
estado_inscripcion(1003, programacion_i, en_curso).
estado_inscripcion(1004, matematica_i_contable, finalizada).
estado_inscripcion(1005, contabilidad_i, finalizada).

% situacion_final(Legajo, IdMateria, Situacion).
% Las inscripciones en curso no tienen hecho situacion_final.
situacion_final(1001, programacion_i, promocionado).
situacion_final(1001, matematica_i_sistemas, regular).
situacion_final(1002, programacion_i, libre).
situacion_final(1004, matematica_i_contable, promocionado).
situacion_final(1005, contabilidad_i, regular).

% ---------------- CONSULTA 1 ----------------
% Alumnos matriculados en una carrera.
% Ejemplo: ?- alumnos_de_carrera(ing_sistemas, Legajo, Nombre, Apellido).
alumnos_de_carrera(IdCarrera, Legajo, Nombre, Apellido) :-
    matriculado(Legajo, IdCarrera),
    alumno(Legajo, Nombre, Apellido).

% ---------------- CONSULTA 2 ----------------
% Alumnos inscriptos en una materia.
% Ejemplo: ?- alumnos_de_materia(programacion_i, Legajo, Nombre, Apellido).
alumnos_de_materia(IdMateria, Legajo, Nombre, Apellido) :-
    inscripto(Legajo, IdMateria),
    alumno(Legajo, Nombre, Apellido).
