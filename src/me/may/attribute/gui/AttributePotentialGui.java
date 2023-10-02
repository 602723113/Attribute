package me.may.attribute.gui;

import com.sucy.skill.SkillAPI;
import com.sucy.skill.api.classes.RPGClass;
import me.may.attribute.Entry;
import me.may.attribute.dao.CacheDao;
import me.may.attribute.dao.PlayerPointDao;
import me.may.attribute.dto.Attribute;
import me.may.attribute.utils.DoubleUtil;
import me.may.attribute.utils.SkillUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@SuppressWarnings("deprecation")
public class AttributePotentialGui {

    private static FileConfiguration config;
    private static ItemStack strenth;
    private static ItemStack toughness;
    private static ItemStack ability;
    private static ItemStack boby;
    private static ItemStack physicalPower;
    private static ItemStack about;

    static {
        config = Entry.getInstance().getConfig();
        ItemMeta im;
        String path = "Gui.Potential.Items.";

        strenth = new ItemStack(config.getInt(path + "Strenth.Material"), 1, (short) config.getInt(path + "Strenth.Data"));
        im = strenth.getItemMeta();
        im.setDisplayName(config.getString(path + "Strenth.DisplayName").replaceAll("&", "§"));
        List<String> lore = config.getStringList(path + "Strenth.Lore");
        for (int i = 0; i < lore.size(); i++) {
            lore.set(i, lore.get(i).replaceAll("&", "§"));
        }
        im.setLore(lore);
        strenth.setItemMeta(im);

        toughness = new ItemStack(config.getInt(path + "Toughness.Material"), 1, (short) config.getInt(path + "Toughness.Data"));
        im = toughness.getItemMeta();
        im.setDisplayName(config.getString(path + "Toughness.DisplayName").replaceAll("&", "§"));
        lore = config.getStringList(path + "Toughness.Lore");
        for (int i = 0; i < lore.size(); i++) {
            lore.set(i, lore.get(i).replaceAll("&", "§"));
        }
        im.setLore(lore);
        toughness.setItemMeta(im);

        ability = new ItemStack(config.getInt(path + "Ability.Material"), 1, (short) config.getInt(path + "Ability.Data"));
        im = ability.getItemMeta();
        im.setDisplayName(config.getString(path + "Ability.DisplayName").replaceAll("&", "§"));
        lore = config.getStringList(path + "Ability.Lore");
        for (int i = 0; i < lore.size(); i++) {
            lore.set(i, lore.get(i).replaceAll("&", "§"));
        }
        im.setLore(lore);
        ability.setItemMeta(im);

        boby = new ItemStack(config.getInt(path + "Body.Material"), 1, (short) config.getInt(path + "Body.Data"));
        im = boby.getItemMeta();
        im.setDisplayName(config.getString(path + "Body.DisplayName").replaceAll("&", "§"));
        lore = config.getStringList(path + "Body.Lore");
        for (int i = 0; i < lore.size(); i++) {
            lore.set(i, lore.get(i).replaceAll("&", "§"));
        }
        im.setLore(lore);
        boby.setItemMeta(im);

        physicalPower = new ItemStack(config.getInt(path + "PhysicalPower.Material"), 1, (short) config.getInt(path + "PhysicalPower.Data"));
        im = physicalPower.getItemMeta();
        im.setDisplayName(config.getString(path + "PhysicalPower.DisplayName").replaceAll("&", "§"));
        lore = config.getStringList(path + "PhysicalPower.Lore");
        for (int i = 0; i < lore.size(); i++) {
            lore.set(i, lore.get(i).replaceAll("&", "§"));
        }
        im.setLore(lore);
        physicalPower.setItemMeta(im);

    }

    public static ItemStack getAbout(Player player) {
        about = new ItemStack(config.getInt("Gui.Potential.Items.About.Material"), 1, (short) config.getInt("Gui.Potential.Items.About.Data"));
        ItemMeta im = about.getItemMeta();
        im.setDisplayName(config.getString("Gui.Potential.Items.About.DisplayName").replaceAll("&", "§").replaceAll("%player_name%", player.getName()).replaceAll("%point%", PlayerPointDao.getMap().get(player.getName()).toString()));
        List<String> lore = config.getStringList("Gui.Potential.Items.About.Lore");

        RPGClass rpgClass = SkillAPI.getClass(SkillUtil.getPlayerClass(player));
        double baseHealth = 20D;
        if (rpgClass != null) {
            baseHealth = rpgClass.getBaseHealth();
        }

        Attribute att = CacheDao.getPlayerAttribute(player.getName());

        for (int i = 0; i < lore.size(); i++) {
            lore.set(i, lore.get(i)
                    .replaceAll("&", "§")
                    .replaceAll("%ice_defense%", String.valueOf(DoubleUtil.formatDouble(att.getIceDefense(), "##.##")))
                    .replaceAll("%ice%", String.valueOf(DoubleUtil.formatDouble(att.getIce(), "##.##")))
                    .replaceAll("%fire_defense%", String.valueOf(DoubleUtil.formatDouble(att.getFireDefense(), "##.##")))
                    .replaceAll("%fire%", String.valueOf(DoubleUtil.formatDouble(att.getFire(), "##.##")))
                    .replaceAll("%xuan_defense%", String.valueOf(DoubleUtil.formatDouble(att.getXuanDefense(), "##.##")))
                    .replaceAll("%xuan%", String.valueOf(DoubleUtil.formatDouble(att.getXuan(), "##.##")))
                    .replaceAll("%poison_defense%", String.valueOf(DoubleUtil.formatDouble(att.getPoisonDefense(), "##.##")))
                    .replaceAll("%poison%", String.valueOf(DoubleUtil.formatDouble(att.getPoison(), "##.##")))
                    .replaceAll("%player_name%", player.getName())
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
                    .replaceAll("%health%", String.valueOf(DoubleUtil.formatDouble((att.getHealth() + baseHealth), "##.##"))));
        }
        im.setLore(lore);
        about.setItemMeta(im);
        return about;
    }

    public static void openPotentialGui(Player player) {
        if (!player.isOnline()) {
            return;
        }
        Inventory inv = Bukkit.createInventory(null, 4 * 9, Entry.getInstance().potentialInvName);
        inv.setItem(4, getAbout(player));
        inv.setItem(11, strenth);
        inv.setItem(12, toughness);
        inv.setItem(13, ability);
        inv.setItem(14, boby);
        inv.setItem(15, physicalPower);
        player.closeInventory();
        player.openInventory(inv);
    }
}
