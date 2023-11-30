package wrath.leafs

import org.powbot.api.Condition
import org.powbot.api.rt4.GameObject
import org.powbot.api.rt4.Objects
import org.powbot.api.script.tree.Leaf
import wrath.LazWrath
import wrath.helpers.WrathHelpers

class EnterCave(script: LazWrath) : Leaf<LazWrath>(script, "Enter Cave") {
    override fun execute() {
        val entrance = Objects.stream().at(WrathHelpers.caveTile).type(GameObject.Type.INTERACTIVE).name("Cave").first()

        if (entrance.valid() && entrance.interact("Enter", false)) {
            Condition.wait({ !entrance.valid() }, 75, 70)
        }
    }
}