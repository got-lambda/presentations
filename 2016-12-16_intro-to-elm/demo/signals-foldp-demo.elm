import Graphics.Element exposing (..)
import Mouse exposing (..)

state =
  Signal.foldp (\_ x -> x + 1) 0 Mouse.clicks

main =
  Signal.map show state
