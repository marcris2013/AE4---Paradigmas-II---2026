% AE4 - Parte del Integrante 3.
% IMPORTANTE: estas reglas se pegan al FINAL de universidad.pl
% (usan los hechos alumno/3, matriculado/2, materia/6, inscripto/2,
% inasistencias/3, estado_inscripcion/3 y situacion_final/3).
% Para probar sin pegar:  swipl universidad.pl integrante2.pl integrante3.pl

% ---------------- CRITERIOS ----------------
% Maximo de inasistencias para poder promocionar.
limite_inasistencias_promocion(2).

% correlativa(IdMateria, IdRequisito).
% Para cursar IdMateria hay que tener aprobada IdRequisito.
correlativa(programacion_ii, programacion_i).
correlativa(bases_de_datos, programacion_ii).
correlativa(ingenieria_de_software, bases_de_datos).
correlativa(redes, programacion_ii).
correlativa(contabilidad_ii, contabilidad_i).
correlativa(impuestos, contabilidad_ii).

% Una materia esta aprobada si quedo regular o promocionado.
aprobada(Legajo, IdMateria) :-
    situacion_final(Legajo, IdMateria, regular).
aprobada(Legajo, IdMateria) :-
    situacion_final(Legajo, IdMateria, promocionado).

% ---------------- CONSULTA 5 ----------------
% Alumnos que pueden promocionar una materia: estan cursando, tienen como
% maximo dos inasistencias y aprobaron las correlativas de la materia.
% Ejemplo: ?- alumnos_que_pueden_promocionar(programacion_i, L, N, A).
alumnos_que_pueden_promocionar(IdMateria, Legajo, Nombre, Apellido) :-
    estado_inscripcion(Legajo, IdMateria, en_curso),
    cumple_requisitos_promocion(Legajo, IdMateria),
    alumno(Legajo, Nombre, Apellido).

% Cumple los requisitos aunque ya haya terminado de cursar.
% Ejemplo: ?- cumple_requisitos_promocion(1001, programacion_i).
cumple_requisitos_promocion(Legajo, IdMateria) :-
    inscripto(Legajo, IdMateria),
    inasistencias(Legajo, IdMateria, Cantidad),
    limite_inasistencias_promocion(Limite),
    Cantidad =< Limite,
    \+ correlativas_faltantes(Legajo, IdMateria, _).

% ---------------- CONSULTA 6 ----------------
% Correlativas que le faltan aprobar a un alumno para una materia.
% Ejemplo: ?- correlativas_faltantes(1002, programacion_ii, IdRequisito).
correlativas_faltantes(Legajo, IdMateria, IdRequisito) :-
    alumno(Legajo, _, _),
    correlativa(IdMateria, IdRequisito),
    \+ aprobada(Legajo, IdRequisito).

% Materias que puede cursar un alumno: es de su carrera, no la aprobo
% todavia y tiene aprobadas todas las correlativas.
% Ejemplo: ?- puede_cursar(1001, IdMateria).
puede_cursar(Legajo, IdMateria) :-
    matriculado(Legajo, IdCarrera),
    materia(IdMateria, _, IdCarrera, _, _, _),
    \+ aprobada(Legajo, IdMateria),
    \+ correlativas_faltantes(Legajo, IdMateria, _).

% Correlativas directas e indirectas (recursiva).
% Ejemplo: ?- correlativas_totales(ingenieria_de_software, IdRequisito).
correlativas_totales(IdMateria, IdRequisito) :-
    correlativa(IdMateria, IdRequisito).
correlativas_totales(IdMateria, IdRequisito) :-
    correlativa(IdMateria, IdIntermedia),
    correlativas_totales(IdIntermedia, IdRequisito).
