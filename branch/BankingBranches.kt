package wrath.branch

import framework.ItemList
import org.powbot.api.rt4.*
import org.powbot.api.script.tree.Branch
import org.powbot.api.script.tree.TreeComponent
import runedragons.extension.missingHitpoints
import wrath.LazWrath
import wrath.helpers.WrathHelpers
import wrath.leafs.*

class IsSaturated(script: LazWrath) : Branch<LazWrath>(script, "Is Saturated?") {
    override val successComponent: TreeComponent<LazWrath> = ToMythsGuild(script)
    override val failedComponent: TreeComponent<LazWrath> = BankOpened(script)

    override fun validate(): Boolean {
        return WrathHelpers.isSaturated(script.essenceInPouch, script.config.food)
    }
}

class BankOpened(script: LazWrath) : Branch<LazWrath>(script, "Bank Opened?") {
    override val successComponent: TreeComponent<LazWrath> = ShouldWithdrawCape(script)
    override val failedComponent: TreeComponent<LazWrath> = OpenBank(script)

    override fun validate(): Boolean {
        return Bank.opened()
    }
}

class ShouldWithdrawCape(script: LazWrath) : Branch<LazWrath>(script, "Should Withdraw Cape") {
    override val successComponent: TreeComponent<LazWrath> = WithdrawCape(script)
    override val failedComponent: TreeComponent<LazWrath> = ShouldFixPouch(script)

    override fun validate(): Boolean {
        return !Inventory.stream().name("Mythical cape").first().valid()
    }
}

class ShouldFixPouch(script: LazWrath) : Branch<LazWrath>(script, "Should Fix Pouch?") {
    override val successComponent: TreeComponent<LazWrath> = FixPouch(script)
    override val failedComponent: TreeComponent<LazWrath> = ShouldDeposit(script)

    override fun validate(): Boolean {
        return Inventory.stream().id(ItemList.COLOSSAL_POUCH_26786).first().valid()
    }
}

class ShouldDeposit(script: LazWrath) : Branch<LazWrath>(script, "Should Deposit?") {
    override val successComponent: TreeComponent<LazWrath> = DepositStuff(script)
    override val failedComponent: TreeComponent<LazWrath> = ShouldEat(script)

    override fun validate(): Boolean {
        return Inventory.stream().id(ItemList.WRATH_RUNE, ItemList.COSMIC_RUNE, ItemList.ASTRAL_RUNE, ItemList.AIR_RUNE,
            ItemList.VIAL, ItemList.STAMINA_POTION1, ItemList.STAMINA_POTION2, ItemList.STAMINA_POTION3,
            ItemList.STAMINA_POTION4).first().valid()
    }
}

class ShouldEat(script: LazWrath) : Branch<LazWrath>(script, "Should Eat?") {
    override val successComponent: TreeComponent<LazWrath> = Eat(script)
    override val failedComponent: TreeComponent<LazWrath> = ShouldStamina(script)

    override fun validate(): Boolean {
        val heal = WrathHelpers.heals[WrathHelpers.food.indexOf(script.config.food)]

        return Combat.missingHitpoints() > heal
    }
}

class ShouldStamina(script: LazWrath) : Branch<LazWrath>(script, "Should Stamina?") {
    override val successComponent: TreeComponent<LazWrath> = Stamina(script)
    override val failedComponent: TreeComponent<LazWrath> = ShouldFillPouch(script)

    override fun validate(): Boolean {
        return Movement.energyLevel() < script.config.energy && !Players.local().isStaminaActive()
    }
}

class ShouldFillPouch(script: LazWrath) : Branch<LazWrath>(script, "Should Fill Pouch?") {
    override val successComponent: TreeComponent<LazWrath> = FillPouch(script)
    override val failedComponent: TreeComponent<LazWrath> = WithdrawEssence(script)

    override fun validate(): Boolean {
        return script.essenceInPouch < 40 && Inventory.isFull()
    }
}