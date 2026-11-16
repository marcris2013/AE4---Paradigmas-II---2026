-- Demostracion de las cinco funcionalidades Haskell de la AE4.
-- Ejecutar desde la carpeta Haskell:  runghc MainCompleto.hs
module Main where

import DatosAcademicos
import Integrante1
import Integrante2
import Integrante3

main :: IO ()
main = do
  putStrLn "=== AE4 - HASKELL / FUNCIONALIDADES 1 A 5 ==="

  putStrLn "\n1) Alumnos PROMOCIONADOS:"
  mapM_ putStrLn (listarAlumnosPorSituacion Promocionado registrosEjemplo)

  putStrLn "\n2) Promedio de inasistencias en Programacion I:"
  print (promedioInasistenciasMateria "programacion_i" registrosEjemplo)

  putStrLn "\n3) Porcentaje de aprobacion en Programacion I:"
  print (porcentajeAprobacionMateria "programacion_i" registrosEjemplo)

  putStrLn "\n4) Alumnos destacados (hasta 2 inasistencias):"
  mapM_ putStrLn (listarAlumnosDestacados 2 registrosEjemplo)

  putStrLn "\n5) Materia con mayor porcentaje de promocionados:"
  print (materiaConMayorPromocion registrosEjemplo)
