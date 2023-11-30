package wrath.leafs

import framework.ItemList
import org.powbot.api.Condition
import org.powbot.api.rt4.Inventory
import wrath.LazWrath
import org.powbot.api.script.tree.Leaf

class FillPouch(script: LazWrath) : Leaf<LazWrath>(script, "Fill Pouch") {
    override fun execute() {
        val pouch = Inventory.stream().id(ItemList.COLOSSAL_POUCH).first()
        val essence = Inventory.stream().name(script.config.essence).count()
        if (pouch.valid() && pouch.interact("Fill")) {
            Condition.wait({ Inventory.stream().name(script.config.essence).count() < essence }, 75, 20)
        }
    }
}