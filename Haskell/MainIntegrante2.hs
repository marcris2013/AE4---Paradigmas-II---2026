-- Programa de demostracion para probar la parte del Integrante 2.
-- Ejecutar desde la carpeta Haskell:  runghc MainIntegrante2.hs
module Main where

import DatosAcademicos
import Integrante2

mostrarPorcentaje :: String -> IO ()
mostrarPorcentaje idMateria =
  case porcentajeAprobacionMateria idMateria registrosEjemplo of
    Nothing  -> putStrLn (idMateria ++ ": sin inscripciones finalizadas.")
    Just pct -> putStrLn (idMateria ++ ": " ++ show pct ++ "% de aprobacion")

main :: IO ()
main = do
  putStrLn "=== AE4 - HASKELL / INTEGRANTE 2 ==="

  putStrLn "\n1) Porcentaje de aprobacion por materia:"
  mapM_ mostrarPorcentaje
    [ "programacion_i"
    , "matematica_i_sistemas"
    , "contabilidad_i"
    , "materia_sin_inscriptos"
    ]

  putStrLn "\n2) Alumnos destacados (promocionados con hasta 2 inasistencias):"
  mapM_ putStrLn (listarAlumnosDestacados 2 registrosEjemplo)
