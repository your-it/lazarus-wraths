package wrath.leafs

import org.powbot.api.Condition
import org.powbot.api.rt4.Bank
import org.powbot.api.rt4.Inventory
import wrath.LazWrath
import org.powbot.api.script.tree.Leaf
import wrath.helpers.WrathHelpers

class ToMythsGuild(script: LazWrath) : Leaf<LazWrath>(script, "To Myths Guild") {
    override fun execute() {
        if (Bank.opened() && Bank.close()) {
            Condition.wait({ !Bank.opened() }, 75, 20)
        }

        val mythsCape = Inventory.stream().name("Mythical cape").first()

        if (mythsCape.valid() && mythsCape.interact("Teleport")) {
            Condition.wait({ WrathHelpers.atMythsGuild() }, 75, 70)
        }
    }
}