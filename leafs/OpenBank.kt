package wrath.leafs

import org.powbot.api.Condition
import org.powbot.api.rt4.Bank
import wrath.LazWrath
import org.powbot.api.script.tree.Leaf

class OpenBank(script: LazWrath) : Leaf<LazWrath>(script, "Open Bank") {
    override fun execute() {
        if (Bank.open()) {
            Condition.wait({ Bank.opened() }, 75, 70)
        }
    }
}