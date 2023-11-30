package wrath.leafs

import org.powbot.api.Condition
import org.powbot.api.rt4.Bank
import org.powbot.api.rt4.Inventory
import org.powbot.api.script.tree.Leaf
import wrath.LazWrath

class WithdrawCape(script: LazWrath) : Leaf<LazWrath>(script, "Withdraw Cape") {
    override fun execute() {
        if (!Bank.stream().name("Mythical cape").isEmpty()) {
            script.logger.info("No Mythical cape found in bank, stopping..")
        }

        if (Bank.withdraw("Mythical cape", Bank.Amount.ONE)) {
            Condition.wait({ Inventory.isFull() }, 75, 20)
        }
    }
}