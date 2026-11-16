-- Programa de demostracion para probar la parte del Integrante 3.
-- Ejecutar desde la carpeta Haskell:  runghc MainIntegrante3.hs
module Main where

import DatosAcademicos
import Integrante3

mostrarPorcentaje :: String -> IO ()
mostrarPorcentaje idMateria =
  case porcentajePromocionados idMateria registrosEjemplo of
    Nothing  -> putStrLn (idMateria ++ ": sin inscripciones finalizadas.")
    Just pct -> putStrLn (idMateria ++ ": " ++ show pct ++ "% de promocionados")

main :: IO ()
main = do
  putStrLn "=== AE4 - HASKELL / INTEGRANTE 3 ==="

  putStrLn "\n5) Porcentaje de promocionados por materia:"
  mapM_ mostrarPorcentaje
    [ "programacion_i"
    , "matematica_i_sistemas"
    , "matematica_i_contable"
    , "contabilidad_i"
    , "materia_sin_inscriptos"
    ]

  putStrLn "\nMateria con mayor porcentaje de promocionados:"
  case materiaConMayorPromocion registrosEjemplo of
    Nothing -> putStrLn "No hay inscripciones finalizadas."
    Just (idMateria, pct) ->
      putStrLn (maybe idMateria id (nombreDeMateria idMateria registrosEjemplo)
                ++ " [" ++ idMateria ++ "]: " ++ show pct ++ "%")

  putStrLn "\nCaso sin registros:"
  print (materiaConMayorPromocion [])
