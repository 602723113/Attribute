package me.may.attribute.listener;

import me.may.attribute.Entry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

public class PluginDisableListener implements Listener {

    @EventHandler
    public void onClose(PluginDisableEvent e) {
        if (e.getPlugin().getName().equalsIgnoreCase("SkillAPI")) {
            Entry.getInstance().onDisable();
        }
    }

}
