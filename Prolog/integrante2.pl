% AE4 - Parte del Integrante 2.
% IMPORTANTE: estas reglas se pegan al FINAL de universidad.pl
% (usan los hechos profesor/3, materia/6, inscripto/2, alumno/3 y situacion_final/3).

% ---------------- CONSULTA 3 ----------------
% Materias que dicta un profesor.
% Ejemplo: ?- materias_de_profesor(carlos_gomez, IdMateria, Nombre).
materias_de_profesor(IdProfesor, IdMateria, NombreMateria) :-
    materia(IdMateria, NombreMateria, _IdCarrera, _Curso, _Cuatrimestre, IdProfesor).

% ---------------- CONSULTA 4 ----------------
% Alumnos que quedaron en situacion REGULAR en una materia.
% Ejemplo: ?- alumnos_regulares(matematica_i_sistemas, Legajo, Nombre, Apellido).
alumnos_regulares(IdMateria, Legajo, Nombre, Apellido) :-
    situacion_final(Legajo, IdMateria, regular),
    alumno(Legajo, Nombre, Apellido).

% ---------------- CONSULTA 5 ----------------
% Companeros: alumnos que comparten al menos una materia con un alumno dado.
% Se excluye al propio alumno. Si comparten varias materias, aparece una vez por materia.
% Ejemplo: ?- companeros_de_materia(1001, Companero, IdMateria).
companeros_de_materia(Legajo, Companero, IdMateria) :-
    inscripto(Legajo, IdMateria),
    inscripto(Companero, IdMateria),
    Legajo \= Companero.

% Version con nombre completo del companero.
% Ejemplo: ?- alumnos_que_comparten_materia(1001, Legajo, Nombre, Apellido, IdMateria).
alumnos_que_comparten_materia(LegajoBase, Legajo, Nombre, Apellido, IdMateria) :-
    companeros_de_materia(LegajoBase, Legajo, IdMateria),
    alumno(Legajo, Nombre, Apellido).
