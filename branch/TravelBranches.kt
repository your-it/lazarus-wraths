package wrath.branch

import org.powbot.api.script.tree.Branch
import org.powbot.api.script.tree.TreeComponent
import wrath.LazWrath
import wrath.helpers.WrathHelpers
import wrath.leafs.*

class AtCraftingGuild(script: LazWrath) : Branch<LazWrath>(script, "At Crafting Guild?") {
    override val successComponent: TreeComponent<LazWrath> = IsSaturated(script)
    override val failedComponent: TreeComponent<LazWrath> = AtMythGuild(script)

    override fun validate(): Boolean {
        return WrathHelpers.atCraftingGuild()
    }
}

class AtMythGuild(script: LazWrath) : Branch<LazWrath>(script, "At Myth Guild?") {
    override val successComponent: TreeComponent<LazWrath> = EnterStatue(script)
    override val failedComponent: TreeComponent<LazWrath> = AtCaveEntrance(script)

    override fun validate(): Boolean {
        return WrathHelpers.atMythsGuild()
    }
}

class AtCaveEntrance(script: LazWrath) : Branch<LazWrath>(script, "At Cave Entrance?") {
    override val successComponent: TreeComponent<LazWrath> = EnterCave(script)
    override val failedComponent: TreeComponent<LazWrath> = AtCave(script)

    override fun validate(): Boolean {
        return WrathHelpers.atCaveEntrance()
    }
}

class AtCave(script: LazWrath) : Branch<LazWrath>(script, "At Cave?") {
    override val successComponent: TreeComponent<LazWrath> = TravelCave(script)
    override val failedComponent: TreeComponent<LazWrath> = AtIsland(script)

    override fun validate(): Boolean {
        return WrathHelpers.atCave() && !WrathHelpers.atCaveEntrance()
    }
}

class AtIsland(script: LazWrath) : Branch<LazWrath>(script, "At Island?") {
    override val successComponent: TreeComponent<LazWrath> = EnterAltar(script)
    override val failedComponent: TreeComponent<LazWrath> = ToCraftingGuild(script)

    override fun validate(): Boolean {
        return WrathHelpers.atIsland()
    }
}
