package me.may.attribute.command;

import me.may.attribute.dao.SQLDao;
import me.may.attribute.gui.AttributeGui;
import me.may.attribute.gui.AttributePotentialGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AttributeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("attribute")) {
            if (args.length == 0) {
                sender.sendMessage("§7==== §8[§6人物属性§8] §7====");
                sender.sendMessage("§b/att open §7打开属性面板");
                sender.sendMessage("§b/att up §7打开潜力点面板");
                sender.sendMessage("§b/att potentClear [玩家] 清空该玩家的潜力数据");
                sender.sendMessage("§b/att potentAdd [玩家] [数量] 增加该玩家的潜力点");
                sender.sendMessage("§b/att potentReset [玩家] 重置该玩家的潜力数据");
                return true;
            }

            if (!(sender instanceof Player)) {
                sender.sendMessage("§f你必须是一名玩家!");
                return true;
            }

            Player player = (Player) sender;
            if (args[0].equalsIgnoreCase("open")) {
                AttributeGui.openAttribute(player);
                return true;
            }

            if (args[0].equalsIgnoreCase("up")) {
                AttributePotentialGui.openPotentialGui(player);
                return true;
            }

            if (args[0].equalsIgnoreCase("potentClear")) {
                if (args.length != 2) {
                    sender.sendMessage("§8[§6人物属性§8] §e>> §c参数不正确");
                    return true;
                }
                SQLDao.clearPlayerPotential(args[1]);
                sender.sendMessage("§8[§6人物属性§8] §e>> §a清空成功!");
                return true;
            }

            if (args[0].equalsIgnoreCase("potentAdd")) {
                if (args.length != 3) {
                    sender.sendMessage("§8[§6人物属性§8] §e>> §c参数不正确");
                    return true;
                }
                SQLDao.addPlayerPoint(args[1], Integer.valueOf(args[2]));
                sender.sendMessage("§8[§6人物属性§8] §e>> §a增加成功!");
                return true;
            }


            if (args[0].equalsIgnoreCase("potentReset")) {
                if (args.length != 2) {
                    sender.sendMessage("§8[§6人物属性§8] §e>> §c参数不正确");
                    return true;
                }
                SQLDao.resetPlayerPotential(args[1]);
                sender.sendMessage("§8[§6人物属性§8] §e>> §a重置成功!");
                return true;
            }
        }
        return false;
    }

}
