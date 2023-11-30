package wrath.leafs

import org.powbot.api.Condition
import org.powbot.api.rt4.GameObject
import org.powbot.api.rt4.Objects
import wrath.LazWrath
import org.powbot.api.script.tree.Leaf
import wrath.helpers.WrathHelpers

class EnterAltar(script: LazWrath) : Leaf<LazWrath>(script, "Enter Altar") {
    override fun execute() {
        val ruins = Objects.stream().within(10).type(GameObject.Type.INTERACTIVE).name("Mysterious ruins").first()
        if (ruins.valid() && ruins.interact("Enter")) {
            Condition.wait({ WrathHelpers.atAltar() }, 75, 50)
        }
    }
}