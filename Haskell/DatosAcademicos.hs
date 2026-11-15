-- Modelo compartido para el procesamiento funcional de la AE4.
-- Cada RegistroAcademico representa la inscripcion de un alumno a una materia.
module DatosAcademicos
  ( SituacionFinal(..)
  , RegistroAcademico(..)
  , nombreCompleto
  , fichaAlumno
  , registrosEjemplo
  ) where

-- Debe coincidir con SituacionFinal.java y con los atomos usados en Prolog.
data SituacionFinal = Regular | Libre | Promocionado
  deriving (Eq, Show)

-- Nothing en situacionFinal significa que el alumno sigue cursando.
data RegistroAcademico = RegistroAcademico
  { legajo         :: Int
  , nombre         :: String
  , apellido       :: String
  , carreraId      :: String
  , materiaId      :: String
  , materiaNombre  :: String
  , inasistencias  :: Int
  , situacionFinal :: Maybe SituacionFinal
  } deriving (Eq, Show)

nombreCompleto :: RegistroAcademico -> String
nombreCompleto registro = nombre registro ++ " " ++ apellido registro

fichaAlumno :: RegistroAcademico -> String
fichaAlumno registro =
  nombreCompleto registro
    ++ " (legajo " ++ show (legajo registro) ++ ")"
    ++ " - " ++ materiaNombre registro

-- Datos ficticios para probar Haskell y Prolog con el mismo universo academico.
-- Deben reemplazarse o actualizarse cuando el grupo acuerde sus casos reales.
registrosEjemplo :: [RegistroAcademico]
registrosEjemplo =
  [ RegistroAcademico 1001 "Juan"  "Perez"  "ing_sistemas" "programacion_i"
      "Programacion I" 1 (Just Promocionado)
  , RegistroAcademico 1001 "Juan"  "Perez"  "ing_sistemas" "matematica_i_sistemas"
      "Matematica I" 2 (Just Regular)
  , RegistroAcademico 1002 "Maria" "Lopez"  "ing_sistemas" "programacion_i"
      "Programacion I" 3 (Just Libre)
  , RegistroAcademico 1003 "Pedro" "Ruiz"   "ing_sistemas" "programacion_i"
      "Programacion I" 4 Nothing
  , RegistroAcademico 1004 "Lucas" "Gomez"  "analista_contable" "matematica_i_contable"
      "Matematica I" 0 (Just Promocionado)
  , RegistroAcademico 1005 "Sofia" "Diaz"   "analista_contable" "contabilidad_i"
      "Contabilidad I" 2 (Just Regular)
  ]
