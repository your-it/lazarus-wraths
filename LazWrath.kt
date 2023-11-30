package wrath

import framework.ItemList
import org.powbot.api.event.BreakEvent
import org.powbot.api.event.InventoryChangeEvent
import org.powbot.api.event.MessageEvent
import org.powbot.api.rt4.walking.model.Skill
import org.powbot.api.script.OptionType
import org.powbot.api.script.ScriptCategory
import org.powbot.api.script.ScriptConfiguration
import org.powbot.api.script.ScriptManifest
import org.powbot.api.script.paint.Paint
import org.powbot.api.script.paint.PaintBuilder
import org.powbot.api.script.tree.TreeComponent
import org.powbot.api.script.tree.TreeScript
import org.powbot.mobile.service.ScriptUploader
import wrath.branch.AtAltar
import wrath.helpers.WrathHelpers

@ScriptManifest(
    name = "Laz Wraths",
    description = "Runecrafts wraths runes. Stops when out of stamina(1) or eat. Start with a crafting cape, colossal pouch and mythical cape in your inventory",
    version = "0.0.7",
    category = ScriptCategory.Runecrafting,
    author = "Lazarus",
    scriptId = "2040776d-5b36-49f4-a16d-3bb24f387985",
    priv = false
)

@ScriptConfiguration.List(
    [
        ScriptConfiguration(
            name = "Food",
            description = "Food you want to eat",
            optionType = OptionType.STRING,
            defaultValue = "Lobster",
            allowedValues = arrayOf("Trout", "Salmon", "Tuna", "Lobster", "Bass", "Swordfish", "Monkfish", "Cooked karambwan", "Shark")
        ),
        ScriptConfiguration(
            name = "Essence",
            description = "Essence you want to use",
            optionType = OptionType.STRING,
            defaultValue = "Pure essence",
            allowedValues = arrayOf("Pure essence", "Daeyalt essence")
        ),
        ScriptConfiguration(
            name = "Stamina",
            description = "Drink stamina when energy is below:",
            optionType = OptionType.INTEGER,
            defaultValue = "60"
        )
    ]
)

class LazWrath : TreeScript() {
    var essenceInPouch = 0
    lateinit var config: WrathConfig
    val emptyPouch = "There is no essence in this pouch."

    override val rootComponent: TreeComponent<*> by lazy {
        AtAltar(this)
    }

    override fun poll() {
        if (WrathHelpers.pouchFull()) {
            essenceInPouch = 40
        }

        super.poll()
    }
    @com.google.common.eventbus.Subscribe
    fun onBreak(be: BreakEvent) {
        if (!WrathHelpers.atCraftingGuild()) {
            be.delay(1000)
        }
    }

    @com.google.common.eventbus.Subscribe
    fun onMessage(me: MessageEvent) {
        if (me.sender.isNotEmpty()) {
            return
        }

        if (me.message == emptyPouch) {
            logger.info("msg empty pouch")
            essenceInPouch = 0
        }
    }

    @com.google.common.eventbus.Subscribe
    fun onInventoryChange(ice: InventoryChangeEvent) {
        if (ice.itemId != ItemList.PURE_ESSENCE && ice.itemId != ItemList.DAEYALT_ESSENCE) {
            return
        }

        if (WrathHelpers.atAltar() && ice.quantityChange > 0) {
            essenceInPouch -= ice.quantityChange
        } else if (!WrathHelpers.atAltar() && ice.quantityChange < 0) {
            essenceInPouch -= ice.quantityChange
        }
    }

    override fun onStart() {
        addPaint()
        val food = getOption<String>("Food")
        val stamina = getOption<Int>("Stamina")
        val essence = getOption<String>("Essence")
        config = WrathConfig(food, stamina, essence)
    }

    private fun addPaint() {
        val p: Paint = PaintBuilder.newBuilder()
            .addString("Last leaf:") { lastLeaf.name }
            .trackSkill(Skill.Runecrafting)
//            .addString("Break time:") { }
            .trackInventoryItems(ItemList.WRATH_RUNE)
            .y(45)
            .x(40)
            .build()
        addPaint(p)
    }
}

fun main() {
    ScriptUploader().uploadAndStart("Laz Wraths", "", "emulator-5554", true)
}