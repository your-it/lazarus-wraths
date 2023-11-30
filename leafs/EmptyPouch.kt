package wrath.leafs

import framework.ItemList
import org.powbot.api.Condition
import org.powbot.api.rt4.Inventory
import wrath.LazWrath
import org.powbot.api.script.tree.Leaf

class EmptyPouch(script: LazWrath) : Leaf<LazWrath>(script, "Empty Pouch") {
    override fun execute() {
        val pouch = Inventory.stream().id(ItemList.COLOSSAL_POUCH).first()
        val essence = Inventory.stream().id(ItemList.PURE_ESSENCE).count()
        if (pouch.valid() && pouch.interact("Empty")) {
            Condition.wait({ Inventory.stream().id(ItemList.PURE_ESSENCE).count() > essence }, 75, 20)
        }
    }
}