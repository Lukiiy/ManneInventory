package me.lukiiy.manneInventory;

import me.lukiiy.barrel.Barrel;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Mannequin;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class MannequinInventoryManager {
    private static final NamespacedKey KEY = new NamespacedKey("manneinventory", "data");
    private static final Map<Mannequin, Inventory> cache = new HashMap<>();

    /**
     * Get a mannequin's inventory
     * @param mannequin A mannequin
     * @return An {@link Inventory}, or null if there isn't any
     */
    public static Inventory get(Mannequin mannequin) {
        String data = mannequin.getPersistentDataContainer().get(KEY, PersistentDataType.STRING);
        if (data == null) return null;

        ItemStack[] items = Barrel.deserializeArray(data);
        Inventory inventory = Bukkit.createInventory(null, items.length);

        inventory.setContents(items);
        return inventory;
    }


    /**
     * Creates an inventory for a mannequin
     * @param mannequin A mannequin
     * @return An {@link Inventory}
     */
    public static Inventory create(Mannequin mannequin, int size) {
        Inventory inventory = Bukkit.createInventory(null, size);

        set(mannequin, inventory);
        return inventory;
    }

    /**
     * Clear a mannequin's inventory
     * @param mannequin A mannequin
     */
    public static void clear(Mannequin mannequin) {
        mannequin.getPersistentDataContainer().remove(KEY);
    }

    /**
     * Sets a mannequin's inventory
     * @param mannequin A mannequin
     * @param inventory An inventory
     */
    public static void set(Mannequin mannequin, Inventory inventory) {
        if (inventory == null) {
            clear(mannequin);
            return;
        }

        String data = Barrel.serializeArray(inventory.getContents());
        if (data != null) mannequin.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, data);
    }
}
