-- Funcionalidad asignada al Integrante 3 para la AE4:
--   * Materia con mayor porcentaje de promocionados.
module Integrante3
  ( esPromocionada
  , materiasDistintas
  , porcentajePromocionados
  , porcentajesPorMateria
  , mayorPorcentaje
  , materiaConMayorPromocion
  , nombreDeMateria
  ) where

import Data.Maybe (isJust)
import DatosAcademicos

-- Una inscripcion esta promocionada si su situacion final es Promocionado.
-- Las que siguen en curso (Nothing) no cuentan.
esPromocionada :: RegistroAcademico -> Bool
esPromocionada = (== Just Promocionado) . situacionFinal

-- Quita los elementos repetidos con recursion explicita.
quitarRepetidos :: Eq a => [a] -> [a]
quitarRepetidos []     = []
quitarRepetidos (x:xs) = x : quitarRepetidos (filter (/= x) xs)

-- Identificadores de materia sin repetir.
-- Se usa el id y no el nombre porque Matematica I se repite en dos carreras.
materiasDistintas :: [RegistroAcademico] -> [String]
materiasDistintas = quitarRepetidos . map materiaId

-- Porcentaje de alumnos promocionados de una materia (0 a 100).
-- Solo se cuentan las inscripciones finalizadas, igual que en el
-- porcentaje de aprobacion del Integrante 2.
-- Devuelve Nothing si la materia no tiene inscripciones finalizadas.
porcentajePromocionados :: String -> [RegistroAcademico] -> Maybe Double
porcentajePromocionados idMateria registros =
  let finalizadas = filter (isJust . situacionFinal)
                  . filter ((== idMateria) . materiaId)
                  $ registros
      total         = length finalizadas
      promocionadas = length (filter esPromocionada finalizadas)
  in if total == 0
       then Nothing
       else Just (100 * fromIntegral promocionadas / fromIntegral total)

-- Lista de (idMateria, porcentaje) de las materias que tienen finalizadas.
porcentajesPorMateria :: [RegistroAcademico] -> [(String, Double)]
porcentajesPorMateria registros =
  [ (idMateria, pct)
  | idMateria <- materiasDistintas registros
  , Just pct  <- [porcentajePromocionados idMateria registros]
  ]

-- Busca el par con mayor porcentaje usando recursion.
-- Si hay empate queda el primero de la lista.
mayorPorcentaje :: [(String, Double)] -> Maybe (String, Double)
mayorPorcentaje []     = Nothing
mayorPorcentaje (x:xs) =
  case mayorPorcentaje xs of
    Nothing -> Just x
    Just y  -> if snd y > snd x then Just y else Just x

-- Punto 5: materia con mayor porcentaje de promocionados.
materiaConMayorPromocion :: [RegistroAcademico] -> Maybe (String, Double)
materiaConMayorPromocion = mayorPorcentaje . porcentajesPorMateria

-- Nombre de una materia a partir de su id.
nombreDeMateria :: String -> [RegistroAcademico] -> Maybe String
nombreDeMateria idMateria registros =
  case filter ((== idMateria) . materiaId) registros of
    []      -> Nothing
    (r : _) -> Just (materiaNombre r)
