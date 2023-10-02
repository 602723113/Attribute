package me.may.attribute.listener;

import com.sucy.skill.hook.CitizensHook;
import me.may.attribute.Entry;
import me.may.attribute.Methods;
import me.may.attribute.api.AttributeAPI;
import me.may.attribute.dao.CacheDao;
import me.may.attribute.dao.ClassTypeDao;
import me.may.attribute.dto.Attribute;
import me.may.attribute.dto.ClassAttributeEnum;
import me.may.attribute.utils.SkillUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.fusesource.jansi.AnsiConsole;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageByEntityEvent e) {
        if (AttributeAPI.isNPC(e.getEntity())) {
            e.setCancelled(true);
            return;
        }
        if (e.getDamager() instanceof Player) {
            Player damager = (Player) e.getDamager();
            //最低伤害
            Double lowestDamage = (double) (5 + damager.getLevel() * 5);
            if (Entry.getInstance().debug) {
                System.out.println("[攻击者 - 玩家]最低伤害: " + lowestDamage);
            }

            Attribute damagerAtt = CacheDao.getPlayerAttribute(damager.getName());
            if (e.getEntity() instanceof LivingEntity) {
                LivingEntity entity = (LivingEntity) e.getEntity();
                Attribute entityAtt = AttributeAPI.getEntityEquipMentAttribute(entity);
                //检查命中
                e.setCancelled(Methods.checkHit(damager, entity));

                if (entityAtt == null || damagerAtt == null) {
                    e.setCancelled(true);
                    return;
                }

                //门派主属性x主属性加成-（对方抗性*4）+{(攻击/10)-(防御/25)}
                //
                String className = SkillUtil.getPlayerClass(damager.getName());
                ClassAttributeEnum type = ClassTypeDao.getClassType(className);
                double mainAttribute = AttributeAPI.getAttributeTypeAttack(type, damagerAtt);
                double attributeAddtion = ClassTypeDao.getMap().get(type);
                double typeDefense = AttributeAPI.getAttributeTypeDefense(ClassTypeDao.getClassType(className), entityAtt) * 4;
                double baseDamage = damagerAtt.getAttack() / 10 - entityAtt.getDefense() / 25;
                Double algor = mainAttribute * attributeAddtion - typeDefense + baseDamage;

                if (Entry.getInstance().debug) {
                    System.out.println("攻击者主属性: " + type.toString());
                    System.out.println("攻击者主属性值: " + mainAttribute);
                    System.out.println("攻击者主属性加成: " + attributeAddtion);
                    System.out.println("承受者实体类型: " + entity.getType().toString());
                    System.out.println("承受者防御属性计算: " + typeDefense);
                    System.out.println("基础伤害: " + baseDamage);
                    System.out.println("算法计算数据: " + algor);
                }
                Double damage = algor;
                if (damage < 0) {
                    damage = lowestDamage;
                }

                if (Methods.checkSuperHit(damager, entity)) {
                    if (Entry.getInstance().debug) {
                        System.out.println("已暴击");
                    }
                    damage = damage * 2;
                }
                if (Entry.getInstance().debug) {
                    System.out.println("最终伤害: " + damage);
                }
                e.setDamage(damage);
                return;
            } else {
                e.setCancelled(Methods.checkHit(damager, null));
                if (Entry.getInstance().debug) {
                    System.out.println("算法计算数据: " + (damagerAtt.getAttack() - (20 / 1.7)));
                }
                double outDamage = damagerAtt.getAttack() - (20 / 1.7);
                Double damage = lowestDamage + outDamage;
                if (damage < 0) {
                    damage = lowestDamage;
                }

                if (Methods.checkSuperHit(damager, null)) {
                    damage = damage * 2;
                }
                if (Entry.getInstance().debug) {
                    System.out.println("最终伤害: " + damage);
                }
                e.setDamage(damage);
            }
            return;
        }

        //判断怪物攻击
        if (e.getDamager() instanceof LivingEntity) {
            if (e.getDamager().isDead()) {
                return;
            }
            LivingEntity livingEntity = (LivingEntity) e.getDamager();
            Double damage = e.getDamage();
            if (Entry.getInstance().debug) {
                System.out.println("[攻击者 - 人形实体]最低伤害: " + damage);
            }
            Attribute damagerAtt = AttributeAPI.getEntityEquipMentAttribute(livingEntity);
            //判断是否攻击玩家
            if (e.getEntity() instanceof Player) {
                Player entity = (Player) e.getEntity();
                Attribute entityAtt = CacheDao.getPlayerAttribute(entity.getName());
                damage = (damagerAtt.getAttack() / 10) - (entityAtt.getDefense() / 25);
                //检查命中
                e.setCancelled(Methods.checkHit(livingEntity, entity));
                if (Methods.checkSuperHit(livingEntity, entity)) {
                    damage = damage * 2;
                }
                if (Entry.getInstance().debug) {
                    System.out.println("最终伤害: " + damage);
                }
                e.setDamage(damage);
            }
            return;
        }
    }
}