
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


esPromocionada :: RegistroAcademico -> Bool
esPromocionada = (== Just Promocionado) . situacionFinal


quitarRepetidos :: Eq a => [a] -> [a]
quitarRepetidos []     = []
quitarRepetidos (x:xs) = x : quitarRepetidos (filter (/= x) xs)

materiasDistintas :: [RegistroAcademico] -> [String]
materiasDistintas = quitarRepetidos . map materiaId


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

porcentajesPorMateria :: [RegistroAcademico] -> [(String, Double)]
porcentajesPorMateria registros =
  [ (idMateria, pct)
  | idMateria <- materiasDistintas registros
  , Just pct  <- [porcentajePromocionados idMateria registros]
  ]


mayorPorcentaje :: [(String, Double)] -> Maybe (String, Double)
mayorPorcentaje []     = Nothing
mayorPorcentaje (x:xs) =
  case mayorPorcentaje xs of
    Nothing -> Just x
    Just y  -> if snd y > snd x then Just y else Just x


materiaConMayorPromocion :: [RegistroAcademico] -> Maybe (String, Double)
materiaConMayorPromocion = mayorPorcentaje . porcentajesPorMateria


nombreDeMateria :: String -> [RegistroAcademico] -> Maybe String
nombreDeMateria idMateria registros =
  case filter ((== idMateria) . materiaId) registros of
    []      -> Nothing
    (r : _) -> Just (materiaNombre r)
