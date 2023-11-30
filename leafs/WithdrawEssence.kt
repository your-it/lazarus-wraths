package wrath.leafs

import org.powbot.api.Condition
import org.powbot.api.rt4.Bank
import org.powbot.api.rt4.Inventory
import org.powbot.api.script.tree.Leaf
import wrath.LazWrath

class WithdrawEssence(script: LazWrath) : Leaf<LazWrath>(script, "Withdraw Essence") {
    override fun execute() {
        if (Bank.withdraw(script.config.essence, Bank.Amount.ALL)) {
            Condition.wait({ Inventory.isFull() }, 75, 20)
        }
    }
}