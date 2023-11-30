package wrath.leafs

import framework.ItemList
import org.powbot.api.Condition
import org.powbot.api.rt4.GameObject
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Objects
import wrath.LazWrath
import org.powbot.api.script.tree.Leaf

class Runecraft(script: LazWrath) : Leaf<LazWrath>(script, "Runecraft") {
    override fun execute() {
        val altar = Objects.stream().within(10).type(GameObject.Type.INTERACTIVE).name("Altar").first()
        if (altar.valid() && altar.interact("Craft-rune")) {
            Condition.wait({ Inventory.stream().id(ItemList.PURE_ESSENCE, ItemList.DAEYALT_ESSENCE).isEmpty()}, 75, 35)
        }
    }
}