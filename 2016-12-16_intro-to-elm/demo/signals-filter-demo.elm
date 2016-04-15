import Graphics.Element exposing (..)
import Keyboard exposing (..)
import Char exposing (..)

characters =
  Signal.map Char.fromCode Keyboard.presses

pressedUpperCharacter =
  Signal.filter Char.isUpper 'Z' characters

main =
  Signal.map show pressedUpperCharacter
