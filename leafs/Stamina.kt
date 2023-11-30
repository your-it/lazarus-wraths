package wrath.leafs

import framework.ItemList
import org.powbot.api.Condition
import org.powbot.api.Notifications
import org.powbot.api.rt4.Bank
import org.powbot.api.rt4.Inventory
import org.powbot.api.script.tree.Leaf
import org.powbot.mobile.script.ScriptManager
import wrath.LazWrath

class Stamina(script: LazWrath) : Leaf<LazWrath>(script, "Stamina") {
    override fun execute() {
        if (!Bank.stream().id(ItemList.STAMINA_POTION1, ItemList.STAMINA_POTION2, ItemList.STAMINA_POTION3, ItemList.STAMINA_POTION4).first().valid()) {
            Notifications.showNotification("No more stamina in bank, stopping..")
            script.logger.info("No more stamina in bank, stopping..")
            ScriptManager.stop()
        }
        val stamina = Bank.stream().id(ItemList.STAMINA_POTION1, ItemList.STAMINA_POTION2, ItemList.STAMINA_POTION3, ItemList.STAMINA_POTION4).first()

        if (Bank.withdraw(stamina.id, Bank.Amount.ONE) &&
            Condition.wait({ Inventory.stream().id(stamina.id).first().valid() }, 75, 20)) {
            if (Inventory.stream().id(stamina.id).first().interact("Drink")) {
                Condition.wait({ !Inventory.stream().id(stamina.id).first().valid() }, 75, 20)
            }
        }
    }
}