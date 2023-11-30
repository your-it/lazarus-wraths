package wrath.leafs

import org.powbot.api.Condition
import org.powbot.api.rt4.*
import org.powbot.api.script.tree.Leaf
import org.powbot.mobile.script.ScriptManager
import wrath.LazWrath

class FixPouch(script: LazWrath) : Leaf<LazWrath>(script, "Fix Pouch") {
    override fun execute() {
        if (Inventory.stream().name("Astral rune").isEmpty() && Bank.withdraw("Astral rune", Bank.Amount.ALL)) {
            Condition.wait { Inventory.stream().name("Astral rune").first().valid() }
        }
        if (Inventory.stream().name("Cosmic rune").isEmpty() && Bank.withdraw("Cosmic rune", Bank.Amount.ALL)) {
            Condition.wait { Inventory.stream().name("Cosmic rune").first().valid() }
        }
        if (Inventory.stream().name("Air rune").isEmpty() && Bank.withdraw("Air rune", Bank.Amount.ALL)) {
            Condition.wait { Inventory.stream().name("Air rune").first().valid() }
        }

        if (Bank.close()) {
            Condition.wait { !Bank.opened() }
        }

        if (!Magic.LunarSpell.NPC_CONTACT.canCast()) {
            ScriptManager.stop()
        }

        if (!Magic.LunarSpell.NPC_CONTACT.cast("Dark Mage")) {
            if (Magic.LunarSpell.NPC_CONTACT.cast("Cast") && Condition.wait({ Widget(75).component(0).visible() }, 75, 50)) {
                val darkMage = Widget(75).find { it!!.actions().contains("Dark Mage") }
                if (darkMage != null && darkMage.interact("Dark Mage")) {
                    Condition.wait({ Chat.chatting() }, 75, 60)
                }
            }
        } else {
            Condition.wait({ Chat.chatting() }, 75, 60)
        }

        if (Chat.canContinue()) {
            Chat.completeChat("Can you repair my pouches?")
        }

    }
}