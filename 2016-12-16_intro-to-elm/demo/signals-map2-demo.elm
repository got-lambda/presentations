import Graphics.Element exposing (..)
import Window exposing (..)
import Mouse exposing (..)

showUpOrDown wH mY =
  let upDown =
    if mY < wH // 2 then "Up" else "Down"
  in
    show upDown

main =
  Signal.map2 showUpOrDown Window.height Mouse.y
