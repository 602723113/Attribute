package me.may.attribute;

import me.may.attribute.api.AttributeAPI;
import me.may.attribute.dao.CacheDao;
import me.may.attribute.dto.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Methods {

    //命中算法
    public static boolean checkHit(LivingEntity damager, LivingEntity entity) {
        if (entity == null) {
            if (Math.random() < 0.1D) {
                if (Entry.getInstance().debug) {
                    System.out.println("非人形");
                }
                //damager.sendMessage("§7未命中...");
                return true;
            } else {
                return false;
            }
        }
        Attribute damagerAtt = null;
        Attribute entityAtt = null;

        if (damager instanceof Player) {
            damagerAtt = CacheDao.getPlayerAttribute(damager.getName());
        } else {
            damagerAtt = AttributeAPI.getEntityEquipMentAttribute(damager);
        }
        if (entity instanceof Player) {
            entityAtt = CacheDao.getPlayerAttribute(entity.getName());
        } else {
            entityAtt = AttributeAPI.getEntityEquipMentAttribute(entity);
        }

        if (Entry.getInstance().debug) {
            System.out.println("命 - Damager: " + damagerAtt);
            System.out.println("命 - Entity: " + entityAtt);
        }

        if (damagerAtt == null || entityAtt == null) {
            return true;
        }

        //算法  自身命中 / (自身命中 + 对方闪避) = 闪避几率
        double odds = (damagerAtt.getHit() + 1) / (damagerAtt.getHit() + entityAtt.getDodge() + 1);
        if (Entry.getInstance().debug) {
            System.out.println("命: " + odds);
        }
        if (odds < 0.1D) {//最低0.1闪避率
            odds = 0.1D;
        }
        //0.XXXXX;
        if (Math.random() > odds) {
            //damager.sendMessage("§7未命中...");
            return true;
        }
        return false;
    }

    //检查暴击算法
    public static boolean checkSuperHit(LivingEntity damager, LivingEntity entity) {
        if (entity == null) {
            if (Math.random() < 0.1D) {
                //damager.sendMessage("§7暴击!");
                return true;
            } else {
                return false;
            }
        }
        Attribute damagerAtt = null;
        Attribute entityAtt = null;

        if (damager instanceof Player) {
            damagerAtt = CacheDao.getPlayerAttribute(damager.getName());
        } else {
            damagerAtt = AttributeAPI.getEntityEquipMentAttribute(damager);
        }
        if (entity instanceof Player) {
            entityAtt = CacheDao.getPlayerAttribute(entity.getName());
        } else {
            entityAtt = AttributeAPI.getEntityEquipMentAttribute(entity);
        }

        if (damagerAtt == null || entityAtt == null) {
            return false;
        }

        if (Entry.getInstance().debug) {
            System.out.println("暴 - Damager: " + damagerAtt);
            System.out.println("暴 - Entity: " + entityAtt);
        }

        //暴击几率 = ( 会心攻击/(会心防御  * 10 )
        double odds = (damagerAtt.getKnowing() + 1) / (entityAtt.getKnowingDefense() * 10 + 1);
        if (Entry.getInstance().debug) {
            System.out.println("暴: " + odds);
        }
        if (odds < 0.1D) {//最低0.1暴击率
            odds = 0.1D;
        }
        //0.XXXXXXX
        if (Math.random() < odds) {
            //damager.sendMessage("§7暴击!");
            return true;
        }
        return false;
    }
}
