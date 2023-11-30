package wrath.leafs

import org.powbot.api.Condition
import org.powbot.api.rt4.GameObject
import org.powbot.api.rt4.Objects
import org.powbot.api.script.tree.Leaf
import wrath.LazWrath

class EnterStatue(script: LazWrath) : Leaf<LazWrath>(script, "Enter Statue") {
    override fun execute() {
        val entrance = Objects.stream().within(6.0).type(GameObject.Type.INTERACTIVE).name("Mythic Statue").first()

        if (entrance.valid() && entrance.interact("Enter", false)) {
            Condition.wait({ !entrance.valid() }, 75, 70)
        }
    }
}