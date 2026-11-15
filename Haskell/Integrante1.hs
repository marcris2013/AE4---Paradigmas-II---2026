-- Funcionalidades asignadas al Integrante 1 para la AE4.
module Integrante1
  ( alumnosPorSituacion
  , listarAlumnosPorSituacion
  , promedioInasistenciasMateria
  , contarRecursivo
  ) where

import DatosAcademicos

-- Parte A, punto 1:
-- Obtiene las inscripciones cuya situacion final coincide con la buscada.
-- Se excluyen las inscripciones que siguen en curso (situacionFinal = Nothing).
alumnosPorSituacion :: SituacionFinal -> [RegistroAcademico] -> [RegistroAcademico]
alumnosPorSituacion situacionBuscada =
  filter ((== Just situacionBuscada) . situacionFinal)

-- Transforma los registros filtrados en fichas legibles.
-- Esta definicion usa composicion de funciones y map.
listarAlumnosPorSituacion :: SituacionFinal -> [RegistroAcademico] -> [String]
listarAlumnosPorSituacion situacionBuscada =
  map fichaAlumno . alumnosPorSituacion situacionBuscada

-- Cuenta elementos mediante recursion explicita.
-- La recursion satisface el requisito de implementar al menos una funcion recursiva.
contarRecursivo :: [a] -> Int
contarRecursivo []     = 0
contarRecursivo (_:xs) = 1 + contarRecursivo xs

-- Parte A, punto 2:
-- Calcula el promedio de inasistencias de todos los alumnos inscriptos
-- en la materia indicada, esten cursando o hayan finalizado.
-- Devuelve Nothing si no hay inscripciones para ese identificador.
promedioInasistenciasMateria :: String -> [RegistroAcademico] -> Maybe Double
promedioInasistenciasMateria idMateria registros =
  let inscripcionesMateria = filter ((== idMateria) . materiaId) registros
      totalInasistencias = foldr ((+) . inasistencias) 0 inscripcionesMateria
      cantidad = contarRecursivo inscripcionesMateria
  in if cantidad == 0
       then Nothing
       else Just (fromIntegral totalInasistencias / fromIntegral cantidad)
