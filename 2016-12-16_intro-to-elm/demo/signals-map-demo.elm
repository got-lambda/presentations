import Graphics.Element exposing (..)
import Keyboard exposing (..)
import Char exposing (..)

characters =
  Signal.map Char.fromCode Keyboard.presses

pressedUpper =
  Signal.map Char.isUpper characters

main =
  Signal.map show pressedUpper
