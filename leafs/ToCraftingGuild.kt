package wrath.leafs

import org.powbot.api.Condition
import org.powbot.api.rt4.Bank
import org.powbot.api.rt4.Equipment
import org.powbot.api.rt4.Inventory
import wrath.LazWrath
import org.powbot.api.script.tree.Leaf
import wrath.helpers.WrathHelpers

class ToCraftingGuild(script: LazWrath) : Leaf<LazWrath>(script, "To Crafting Guild") {
    override fun execute() {
        if (Bank.opened() && Bank.close()) {
            Condition.wait({ !Bank.opened() }, 75, 20)
        }

        var craftingCape = Inventory.stream().name("Crafting cape", "Crafting cape(t)").first()
        if (!craftingCape.valid()) {
            craftingCape = Equipment.stream().name("Crafting cape", "Crafting cape(t)").first()
        }

        if (craftingCape.interact("Teleport")) {
            Condition.wait({ WrathHelpers.atCraftingGuild() }, 75, 70)
        }
    }
}