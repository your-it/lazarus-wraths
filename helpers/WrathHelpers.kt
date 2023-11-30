package wrath.helpers

import framework.ItemList
import org.powbot.api.Area
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import runedragons.extension.missingHitpoints

object WrathHelpers {
    private val caveArea = Area(Tile(1943, 9017, 1), Tile(1919, 8962, 1))
    private val islandArea = Area(Tile(2440, 2831, 0), Tile(2452, 2817, 0))
    private val craftingGuildArea = Area(Tile(2929, 3289, 0), Tile(2938, 3278, 0))
    val caveTile = Tile(1939, 8967, 1)
    val tunnelTile = Tile(1938, 8967, 1)
    val food = arrayOf("Trout", "Salmon", "Tuna", "Lobster", "Bass", "Swordfish", "Monkfish", "Cooked karambwan", "Shark")
    val heals = arrayOf(7, 9, 10, 12, 13, 14, 16, 18, 20)

    fun atAltar(): Boolean {
        return Objects.stream().within(10).type(GameObject.Type.INTERACTIVE).name("Altar").first().valid()
    }

    fun atCraftingGuild(): Boolean {
        return craftingGuildArea.contains(Players.local())
    }

    fun atMythsGuild(): Boolean {
        return Objects.stream().within(6.0).type(GameObject.Type.INTERACTIVE).name("Mythic Statue").first().valid()
    }

    fun pouchFull(): Boolean {
        return Varpbits.varpbit(261, false) != 0
    }

    fun atCave(): Boolean {
        return caveArea.contains(Players.local())
//        return Objects.stream().within(6.0).type(GameObject.Type.INTERACTIVE).name("Fountain of Uhld").first().valid()
    }

    fun atIsland(): Boolean{
        return islandArea.contains(Players.local())
    }

    fun atCaveEntrance(): Boolean {
        return Objects.stream().at(caveTile).type(GameObject.Type.INTERACTIVE).name("Cave").first().inViewport()
    }

    fun isSaturated(essenceInPouch: Int, currentFood: String): Boolean {
        if (!Inventory.isFull()) {
            return false
        }

        if (essenceInPouch < 40) {
            return false
        }

        val heal = heals[food.indexOf(currentFood)]

        if (Combat.missingHitpoints() > heal) {
            return false
        }

        if (Inventory.stream().id(ItemList.COLOSSAL_POUCH_26786).first().valid()) {
            return false
        }

        if (Inventory.stream().id(ItemList.WRATH_RUNE).first().valid()) {
            return false
        }

        return true
    }
}