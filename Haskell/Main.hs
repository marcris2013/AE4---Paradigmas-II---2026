-- Programa de demostracion para probar la parte del Integrante 1.
module Main where

import DatosAcademicos
import Integrante1

main :: IO ()
main = do
  putStrLn "=== AE4 - HASKELL / INTEGRANTE 1 ==="

  putStrLn "\n1) Alumnos PROMOCIONADOS:"
  mapM_ putStrLn (listarAlumnosPorSituacion Promocionado registrosEjemplo)

  putStrLn "\nAlumnos REGULARES:"
  mapM_ putStrLn (listarAlumnosPorSituacion Regular registrosEjemplo)

  putStrLn "\nAlumnos LIBRES:"
  mapM_ putStrLn (listarAlumnosPorSituacion Libre registrosEjemplo)

  putStrLn "\n2) Promedio de inasistencias en Programacion I:"
  case promedioInasistenciasMateria "programacion_i" registrosEjemplo of
    Nothing -> putStrLn "No hay inscripciones para esa materia."
    Just promedio -> putStrLn ("Promedio: " ++ show promedio)

  putStrLn "\nCaso sin alumnos inscriptos:"
  print (promedioInasistenciasMateria "materia_sin_inscriptos" registrosEjemplo)
