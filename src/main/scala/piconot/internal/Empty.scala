package piconot.internal

object Empty extends ourDSL {
  picobot("empty")(
    state("move left")(
      (open(west), move(left), to("move left")),
      (blocked(west), move(up), to("move up"))),
    state("move up")(
      (open(up), move(up), to("move up")),
      (blocked(north) & open(south), move(south), to("fill"))),
    state("fill")(
      (open(down), move(down), to("fill")),
      (open(east) + blocked(south), move(right), to("fill top"))),
    state("fill top")(
      (open(north), move(north), to("fill top")),
      (open(right) & blocked(north), move(right), to("fill"))))
}