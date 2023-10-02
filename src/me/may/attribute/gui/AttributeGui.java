package me.may.attribute.gui;

import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.classes.RPGClass;
import me.may.attribute.Entry;
import me.may.attribute.dao.CacheDao;
import me.may.attribute.dao.ClassTypeDao;
import me.may.attribute.dao.PlayerPointDao;
import me.may.attribute.dto.Attribute;
import me.may.attribute.utils.DoubleUtil;
import me.may.attribute.utils.SkillUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@SuppressWarnings("deprecation")
public class AttributeGui {

    public static ItemStack is;

    static {
        is = new ItemStack(Entry.getInstance().getConfig().getInt("Gui.Item.Material"), 1, (short) Entry.getInstance().getConfig().getInt("Gui.Item.Data"));
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(Entry.getInstance().getConfig().getString("Gui.Item.DisplayName").replaceAll("&", "§"));
        is.setItemMeta(im);
        im = null;
    }

    public static void openAttribute(Player player) {
        if (!player.isOnline()) {
            return;
        }
        if (CacheDao.getPlayerAttribute(player.getName()) == null) {
            return;
        }
        Inventory inv = Bukkit.createInventory(null, InventoryType.DISPENSER, Entry.getInstance().invName);

        RPGClass rpgClass = SkillAPI.getClass(SkillUtil.getPlayerClass(player));
        double baseHealth = 20D;
        if (rpgClass != null) {
            baseHealth = rpgClass.getBaseHealth();
        }

        Attribute att = CacheDao.getPlayerAttribute(player.getName());
        ItemMeta im = is.getItemMeta();
        List<String> lore = Entry.getInstance().getConfig().getStringList("Gui.Item.Lore");
        for (int i = 0; i < lore.size(); i++) {
            lore.set(i, lore.get(i)
                    .replaceAll("&", "§")
                    .replaceAll("%attribute_type%", String.valueOf(DoubleUtil.formatDouble(ClassTypeDao.getMap().get(ClassTypeDao.getClassType(SkillUtil.getPlayerClass(player))), "##.##")))
                    .replaceAll("%ice_defense%", String.valueOf(DoubleUtil.formatDouble(att.getIceDefense(), "##.##")))
                    .replaceAll("%ice%", String.valueOf(DoubleUtil.formatDouble(att.getIce(), "##.##")))
                    .replaceAll("%fire_defense%", String.valueOf(DoubleUtil.formatDouble(att.getFireDefense(), "##.##")))
                    .replaceAll("%fire%", String.valueOf(DoubleUtil.formatDouble(att.getFire(), "##.##")))
                    .replaceAll("%xuan_defense%", String.valueOf(DoubleUtil.formatDouble(att.getXuanDefense(), "##.##")))
                    .replaceAll("%xuan%", String.valueOf(DoubleUtil.formatDouble(att.getXuan(), "##.##")))
                    .replaceAll("%poison_defense%", String.valueOf(DoubleUtil.formatDouble(att.getPoisonDefense(), "##.##")))
                    .replaceAll("%poison%", String.valueOf(DoubleUtil.formatDouble(att.getPoison(), "##.##")))
                    .replaceAll("%point%", PlayerPointDao.getMap().get(player.getName()).toString())
                    .replaceAll("%lowest_damage%", String.valueOf(DoubleUtil.formatDouble(Double.valueOf(5 + player.getLevel() * 5), "##.##")))
                    .replaceAll("%physical_power%", String.valueOf(DoubleUtil.formatDouble(att.getPhysicalPower(), "##.##")))
                    .replaceAll("%ability%", String.valueOf(DoubleUtil.formatDouble(att.getAbility(), "##.##")))
                    .replaceAll("%body%", String.valueOf(DoubleUtil.formatDouble(att.getBody(), "##.##")))
                    .replaceAll("%toughness%", String.valueOf(DoubleUtil.formatDouble(att.getToughness(), "##.##")))
                    .replaceAll("%strenth%", String.valueOf(DoubleUtil.formatDouble(att.getStrenth(), "##.##")))
                    .replaceAll("%knowing_defense%", String.valueOf(DoubleUtil.formatDouble(att.getKnowingDefense(), "##.##")))
                    .replaceAll("%knowing%", String.valueOf(DoubleUtil.formatDouble(att.getKnowing(), "##.##")))
                    .replaceAll("%dodge%", String.valueOf(DoubleUtil.formatDouble(att.getDodge(), "##.##")))
                    .replaceAll("%hit%", String.valueOf(DoubleUtil.formatDouble(att.getHit(), "##.##")))
                    .replaceAll("%defense%", String.valueOf(DoubleUtil.formatDouble(att.getDefense(), "##.##")))
                    .replaceAll("%attack%", String.valueOf(DoubleUtil.formatDouble(att.getAttack(), "##.##")))
                    .replaceAll("%percent_health%", String.valueOf(DoubleUtil.formatDouble(att.getPercentHealth(), "##.##")))
                    .replaceAll("%health%", String.valueOf(DoubleUtil.formatDouble((att.getHealth() + baseHealth), "##.##"))))
            ;
        }
        im.setLore(lore);
        is.setItemMeta(im);
        inv.setItem(4, is);
        lore = null;
        im = null;

        player.closeInventory();
        player.openInventory(inv);

    }
}
