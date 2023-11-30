package wrath.branch

import framework.ItemList
import org.powbot.api.rt4.Inventory
import org.powbot.api.script.tree.Branch
import org.powbot.api.script.tree.TreeComponent
import wrath.LazWrath
import wrath.helpers.WrathHelpers
import wrath.leafs.EmptyPouch
import wrath.leafs.Runecraft
import wrath.leafs.ToCraftingGuild

class AtAltar(script: LazWrath) : Branch<LazWrath>(script, "At Altar?") {
    override val successComponent: TreeComponent<LazWrath> = WithEssence(script)
    override val failedComponent: TreeComponent<LazWrath> = AtCraftingGuild(script)

    override fun validate(): Boolean {
        return WrathHelpers.atAltar()
    }
}

class WithEssence(script: LazWrath) : Branch<LazWrath>(script, "With Essence?") {
    override val successComponent: TreeComponent<LazWrath> = InInventory(script)
    override val failedComponent: TreeComponent<LazWrath> = ToCraftingGuild(script)

    override fun validate(): Boolean {
        return script.essenceInPouch + Inventory.stream().id(ItemList.PURE_ESSENCE, ItemList.DAEYALT_ESSENCE).count() > 0
    }
}

class InInventory(script: LazWrath) : Branch<LazWrath>(script, "In Inventory?") {
    override val successComponent: TreeComponent<LazWrath> = Runecraft(script)
    override val failedComponent: TreeComponent<LazWrath> = EmptyPouch(script)

    override fun validate(): Boolean {
        return Inventory.stream().id(ItemList.PURE_ESSENCE, ItemList.DAEYALT_ESSENCE).count() > 0
    }
}