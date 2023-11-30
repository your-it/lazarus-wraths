package wrath.leafs

import org.powbot.api.rt4.Movement
import org.powbot.api.script.tree.Leaf
import wrath.LazWrath
import wrath.helpers.WrathHelpers

class TravelCave(script: LazWrath) : Leaf<LazWrath>(script, "Travel Cave") {
    override fun execute() {
        Movement.builder(WrathHelpers.tunnelTile).setWalkUntil{ WrathHelpers.tunnelTile.distance() < 10.0 }.move()
    }
}