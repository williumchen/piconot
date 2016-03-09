package piconot.internal

object RightHand extends ourDSL {
  picobot ("maze")(
      state ("north")(
          (blocked (east) & open(north), move (up), to ("north")),
          (blocked (north, east), move (stay), to ("west")),
          (open (east), move (east), to ("east"))
      ), 
      state ("east")(
          (blocked (south) + open (right), move (right), to ("east")),
          (blocked (east, south), move (stay), to ("north")),
          (open (south), move (down), to ("south"))
      ),
      state ("west")(
          (blocked (north) & open(west), move (west), to ("west")),
          (blocked (north, west), move (stay), to ("south")),
          (open (north), move (up), to ("north"))
      ),
      state ("south")(
          (blocked (west) + open (south), move (south), to ("south")),
          (blocked (south, west), move (stay), to ("east")),
          (open (west), move (west), to ("west"))
      )
   )
}