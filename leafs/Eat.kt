package wrath.leafs

import org.powbot.api.Condition
import org.powbot.api.Notifications
import org.powbot.api.rt4.Bank
import org.powbot.api.rt4.Inventory
import org.powbot.api.script.tree.Leaf
import org.powbot.mobile.script.ScriptManager
import wrath.LazWrath

class Eat(script: LazWrath) : Leaf<LazWrath>(script, "Eat") {
    override fun execute() {
        if (!Bank.stream().name(script.config.food).first().valid()) {
            Notifications.showNotification("No more ${script.config.food} in bank, stopping..")
            script.logger.info("No more ${script.config.food} in bank, stopping..")
            ScriptManager.stop()
        }

        if (Bank.withdraw(script.config.food, Bank.Amount.ONE) &&
            Condition.wait({ Inventory.stream().name(script.config.food).first().valid() }, 75, 20)) {
            if (Inventory.stream().name(script.config.food).first().interact("Eat")) {
                Condition.wait({ !Inventory.stream().name(script.config.food).first().valid() }, 75, 20)
            }
        }
    }
}