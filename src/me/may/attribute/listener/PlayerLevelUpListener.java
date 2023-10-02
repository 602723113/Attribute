package me.may.attribute.listener;

import com.sucy.skill.api.event.PlayerLevelUpEvent;
import me.may.attribute.Entry;
import me.may.attribute.dao.PlayerPointDao;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerLevelUpListener implements Listener {

    @EventHandler
    public void onUp(PlayerLevelUpEvent e) {
        PlayerPointDao.getMap().put(e.getPlayerData().getPlayerName(), PlayerPointDao.getMap().get(e.getPlayerData().getPlayerName()) + Entry.getInstance().upPoint * e.getAmount());
        e.getPlayerData().getPlayer().sendMessage("§8[§6人物属性§8] §e>> §7你升级了!");
    }
}
