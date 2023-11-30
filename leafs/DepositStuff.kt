package wrath.leafs

import framework.ItemList
import org.powbot.api.rt4.Bank
import org.powbot.api.script.tree.Leaf
import wrath.LazWrath

class DepositStuff(script: LazWrath) : Leaf<LazWrath>(script, "Deposit Stuff") {
    override fun execute() {
        Bank.depositAllExcept(ItemList.CRAFTING_CAPE, ItemList.CRAFTING_CAPET, ItemList.MYTHICAL_CAPE_22114,
            ItemList.COLOSSAL_POUCH, ItemList.COLOSSAL_POUCH_26786, ItemList.PURE_ESSENCE, ItemList.DAEYALT_ESSENCE)
    }
}