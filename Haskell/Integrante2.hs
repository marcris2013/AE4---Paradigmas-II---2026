-- Funcionalidades asignadas al Integrante 2 para la AE4:
--   * Porcentaje de aprobacion de una materia.
--   * Alumnos destacados.
module Integrante2
  ( esAprobada
  , porcentajeAprobacionMateria
  , alumnosDestacados
  , listarAlumnosDestacados
  ) where

import Data.Maybe (isJust)
import DatosAcademicos

-- Una inscripcion esta aprobada si el alumno quedo Regular o Promocionado.
-- Libre y "en curso" (Nothing) no cuentan como aprobadas.
esAprobada :: RegistroAcademico -> Bool
esAprobada registro =
  situacionFinal registro `elem` [Just Regular, Just Promocionado]

-- Porcentaje de aprobacion de una materia (0 a 100).
-- Solo se consideran las inscripciones FINALIZADAS (situacionFinal /= Nothing),
-- porque quien sigue cursando todavia no aprobo ni desaprobo.
-- Devuelve Nothing si la materia no tiene inscripciones finalizadas.
porcentajeAprobacionMateria :: String -> [RegistroAcademico] -> Maybe Double
porcentajeAprobacionMateria idMateria registros =
  let finalizadas = filter (isJust . situacionFinal)
                  . filter ((== idMateria) . materiaId)
                  $ registros
      total     = length finalizadas
      aprobadas = length (filter esAprobada finalizadas)
  in if total == 0
       then Nothing
       else Just (100 * fromIntegral aprobadas / fromIntegral total)

-- Alumnos destacados: inscripciones Promocionadas con una cantidad de
-- inasistencias menor o igual al maximo indicado.
alumnosDestacados :: Int -> [RegistroAcademico] -> [RegistroAcademico]
alumnosDestacados maxInasistencias =
  filter (\r -> situacionFinal r == Just Promocionado
                && inasistencias r <= maxInasistencias)

-- Fichas legibles de los alumnos destacados (composicion de funciones + map).
listarAlumnosDestacados :: Int -> [RegistroAcademico] -> [String]
listarAlumnosDestacados maxInasistencias =
  map fichaAlumno . alumnosDestacados maxInasistencias
