package me.lukiiy.manneInventory;

import me.lukiiy.barrel.Barrel;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Mannequin;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MannequinInventoryManager {
    private static final NamespacedKey KEY = new NamespacedKey("manneinventory", "data");
    private static final Map<UUID, Inventory> cache = new ConcurrentHashMap<>();

    /**
     * Get a mannequin's inventory
     * @param mannequin A mannequin
     * @return An {@link Inventory}, or null if there isn't any
     */
    public static Inventory get(Mannequin mannequin) {
        UUID uuid = mannequin.getUniqueId();

        Inventory cached = cache.get(uuid);
        if (cached != null) return cached;

        String data = mannequin.getPersistentDataContainer().get(KEY, PersistentDataType.STRING);
        if (data == null) return null;

        ItemStack[] items = Barrel.deserializeArray(data);
        Inventory inventory = Bukkit.createInventory(null, items.length);

        inventory.setContents(items);
        cache.put(uuid, inventory);

        return inventory;
    }


    /**
     * Creates an inventory for a mannequin
     * @param mannequin A mannequin
     * @return An {@link Inventory}
     */
    public static Inventory create(Mannequin mannequin, int size) {
        Inventory inventory = Bukkit.createInventory(null, size);

        cache.put(mannequin.getUniqueId(), inventory);
        return inventory;
    }

    /**
     * Clear a mannequin's inventory
     * @param mannequin A mannequin
     */
    public static void clear(Mannequin mannequin) {
        mannequin.getPersistentDataContainer().remove(KEY);
        cache.remove(mannequin.getUniqueId());
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
        UUID uuid = mannequin.getUniqueId();

        if (data == null) {
            mannequin.getPersistentDataContainer().remove(KEY);
            cache.remove(uuid);
            return;
        }

        mannequin.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, data);
        cache.put(uuid, inventory);
    }
}
