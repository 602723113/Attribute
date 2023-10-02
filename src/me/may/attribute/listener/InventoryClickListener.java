package me.may.attribute.listener;

import me.may.attribute.Entry;
import me.may.attribute.dao.PlayerPointDao;
import me.may.attribute.dao.PlayerPotentialDao;
import me.may.attribute.dto.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getRawSlot() < 0) {
            return;
        }
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }
        if (e.getInventory().getTitle().equals(Entry.getInstance().invName)) {
            e.setCancelled(true);
        }

        if (e.getInventory().getTitle().equals(Entry.getInstance().potentialInvName)) {
            Player player = (Player) e.getWhoClicked();
            e.setCancelled(true);

            if (!PlayerPointDao.hasPoint(player.getName(), 1)) {
                player.sendMessage("§8[§6人物属性§8] §e>> §7你没有足够的潜力点");
                return;
            }
            Attribute att = PlayerPotentialDao.getMap().get(player.getName());
            switch (e.getRawSlot()) {
                case 11:
                    att.setStrenth(att.getStrenth() + 1);
                    break;
                case 12:
                    att.setToughness(att.getToughness() + 1);
                    break;
                case 13:
                    att.setAbility(att.getAbility() + 1);
                    break;
                case 14:
                    att.setBody(att.getBody() + 1);
                    break;
                case 15:
                    att.setPhysicalPower(att.getPhysicalPower() + 1);
                    break;
                default:
                    return;
            }
            PlayerPointDao.getMap().put(player.getName(), PlayerPointDao.getMap().get(player.getName()) - 1);
            player.sendMessage("§8[§6人物属性§8] §e>> §7升级成功!");
        }
    }
}
