package me.may.attribute.api;

import me.may.attribute.dao.PlayerPotentialDao;
import me.may.attribute.dto.Attribute;
import me.may.attribute.dto.ClassAttributeEnum;
import me.may.gem.dao.GemDao;
import me.may.gem.dto.Gem;
import me.may.gem.dto.GemType;
import me.may.suit.Entry;
import me.may.suit.dao.SuitDao;
import me.may.suit.dto.Suit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.endlesscode.rpginventory.api.InventoryAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeAPI {

    public static boolean isNPC(Entity entity) {
        return entity.getClass().getName().contains("PlayerNPC");
    }

    public static double getAttributeTypeAttack(ClassAttributeEnum type, Attribute damagerAttribute) {
        if (type == null || damagerAttribute == null) {
            return 0D;
        }
        if (type.equals(ClassAttributeEnum.ICE)) {
            return damagerAttribute.getIce();
        }
        if (type.equals(ClassAttributeEnum.FIRE)) {
            return damagerAttribute.getFire();
        }
        if (type.equals(ClassAttributeEnum.XUAN)) {
            return damagerAttribute.getXuan();
        }
        if (type.equals(ClassAttributeEnum.POISON)) {
            return damagerAttribute.getPoison();
        }
        return 0;
    }
    public static double getAttributeTypeDefense(ClassAttributeEnum type, Attribute entityAttribute) {
        double result = 0D;
        switch (type) {
            case ICE:
                result = entityAttribute.getIceDefense();
                break;
            case FIRE:
                result = entityAttribute.getFireDefense();
                break;
            case XUAN:
                result = entityAttribute.getXuanDefense();
                break;
            case POISON:
                result = entityAttribute.getPoisonDefense();
                break;
        }
        return result;
    }

    public static Attribute getItemAttribute(ItemStack is) {
        Attribute att = new Attribute(null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        if (is != null && is.hasItemMeta() && is.getItemMeta().hasLore()) {
            if (is.getItemMeta().hasDisplayName()) {
                //检查是否为宝石
                if (!GemDao.gemNames.isEmpty()) {
                    for (String names : GemDao.gemNames) {
                        if (is.getItemMeta().getDisplayName().indexOf(names) != -1) {
                            return att;
                        }
                    }
                }
            }
            List<String> lore = is.getItemMeta().getLore();
            for (int i = 0; i < lore.size(); i++) {
                if (lore.get(i).contains("百分比血量加成:") || lore.get(i).contains("血量上限") || lore.get(i).contains("血上限")) {
                    att.setPercentHealth(att.getPercentHealth() + Double.valueOf(lore.get(i).split("\\+")[1].replaceAll("\\%", "")));
                } else if (lore.get(i).contains("冰攻击")) {
                    att.setIce(att.getIce() + Double.valueOf(lore.get(i).split("\\+")[1]));
                } else if (lore.get(i).contains("冰抗性")) {
                    att.setIceDefense(att.getIceDefense() + Double.valueOf(lore.get(i).split("\\+")[1]));
                } else if (lore.get(i).contains("火攻击")) {
                    att.setFire(att.getFire() + Double.valueOf(lore.get(i).split("\\+")[1]));
                } else if (lore.get(i).contains("火抗性")) {
                    att.setFireDefense(att.getFireDefense() + Double.valueOf(lore.get(i).split("\\+")[1]));
                } else if (lore.get(i).contains("玄攻击")) {
                    att.setXuan(att.getXuan() + Double.valueOf(lore.get(i).split("\\+")[1]));
                } else if (lore.get(i).contains("玄抗性")) {
                    att.setXuanDefense(att.getXuanDefense() + Double.valueOf(lore.get(i).split("\\+")[1]));
                } else if (lore.get(i).contains("毒攻击")) {
                    att.setPoison(att.getPoison() + Double.valueOf(lore.get(i).split("\\+")[1]));
                } else if (lore.get(i).contains("毒抗性")) {
                    att.setPoisonDefense(att.getPoisonDefense() + Double.valueOf(lore.get(i).split("\\+")[1]));
                } else if (lore.get(i).contains("血量:")) {
                    att.setHealth(att.getHealth() + Double.valueOf(lore.get(i).split("\\+")[1]));
                } else if (lore.get(i).contains("攻击")) {
                    att.setAttack(att.getAttack() + Double.valueOf(lore.get(i).split("\\+")[1]));
                } else if (lore.get(i).contains("会心防御")) {
                    att.setKnowingDefense(att.getKnowingDefense() + Double.valueOf(lore.get(i).split("\\+")[1]));
                } else if (lore.get(i).contains("防御")) {
                    att.setDefense(att.getDefense() + Double.valueOf(lore.get(i).split("\\+")[1]));
                } else if (lore.get(i).contains("命中")) {
                    att.setHit(att.getHit() + Double.valueOf(lore.get(i).split("\\+")[1]));
                } else if (lore.get(i).contains("闪避")) {
                    att.setDodge(att.getDodge() + Double.valueOf(lore.get(i).split("\\+")[1]));
                } else if (lore.get(i).contains("会心")) {
                    att.setKnowing(att.getKnowing() + Double.valueOf(lore.get(i).split("\\+")[1]));
                } else if (lore.get(i).contains("力量")) {
                    att.setStrenth(att.getStrenth() + Double.valueOf(lore.get(i).split("\\+")[1]));
                } else if (lore.get(i).contains("韧性")) {
                    att.setToughness(att.getToughness() + Double.valueOf(lore.get(i).split("\\+")[1]));
                } else if (lore.get(i).contains("定力")) {
                    att.setAbility(att.getAbility() + Double.valueOf(lore.get(i).split("\\+")[1]));
                } else if (lore.get(i).contains("身法")) {
                    att.setBody(att.getBody() + Double.valueOf(lore.get(i).split("\\+")[1]));
                } else if (lore.get(i).contains("体力")) {
                    att.setPhysicalPower(att.getPhysicalPower() + Double.valueOf(lore.get(i).split("\\+")[1]));
                }
            }
        }
        return att;
    }

    /**
     * 取人形实体装备的Attribute
     *
     * @param entity 实体
     * @return {@link Attribute}
     */
    public static Attribute getEntityEquipMentAttribute(LivingEntity entity) {
        Attribute att = new Attribute(null);
        List<ItemStack> items = new ArrayList<ItemStack>();
        for (int i = 0; i < entity.getEquipment().getArmorContents().length; i++) {
            items.add(entity.getEquipment().getArmorContents()[i]);
        }
        items.add(entity.getEquipment().getItemInMainHand());

        //获得rpginvetory里的被动物品 (如戒指 手套 神器)
        if (entity instanceof Player) {
            Player player = (Player) entity;
            items.addAll(InventoryAPI.getPassiveItems(player));
        }

        //计算物品上的各项属性 如攻击: +10之类的
        for (int i = 0; i < items.size(); i++) {
            att = sumAttributes(att, getItemAttribute(items.get(i)));
        }

        for (ItemStack item : items) {
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                List<String> lore = item.getItemMeta().getLore();
                /*<-- 宝石检测  -->*/
                for (String string : lore) { //遍历Lore
                    for (String names : GemDao.gemNames) { //遍历gemNames
                        if (string.indexOf(names) != -1) {
                            Attribute gemAtt = gemAddtionDataToAttribute(GemDao.getGem(names)); //取得该宝石的属性
                            att = sumAttributes(att, gemAtt); //将所有的宝石的属性加起来
                            continue;
                        }
                    }
                }

            }
        }

        //1.1新需求,如果是玩家才会进行套装计算
        if (entity instanceof Player) {

            Map<String, Integer> suits = getSuits(entity);
            Attribute suitAtt = null;
            Suit suit = null;
            for (String key : suits.keySet()) {
                suit = SuitDao.getSuit(key);
                suitAtt = suitAddtionDataToAttribute(suit, suits.get(key));
                if (suitAtt == null) {
                    break;
                }
                att = sumAttributes(att, suitAtt);
            }
			/*1.1新需求 ---> 潜力值*/
            att = sumAttributes(att, PlayerPotentialDao.getMap().get(entity.getName()));
        }
        return att;
    }

    /**
     * 取该实体的所有套装个数
     *
     * @param entity 实体
     * @return 套装名 ---> 个数
     */
    public static Map<String, Integer> getSuits(LivingEntity entity) {
        Map<String, Integer> suits = new HashMap<String, Integer>();
        List<ItemStack> equips = new ArrayList<ItemStack>();
        for (ItemStack item : entity.getEquipment().getArmorContents()) {
            equips.add(item);
        }
        equips.add(entity.getEquipment().getItemInMainHand());
        equips.add(entity.getEquipment().getItemInOffHand());
        if (entity instanceof Player) {
            Player player = (Player) entity;
            equips.addAll(InventoryAPI.getPassiveItems(player));
        }
        //遍历装备
        for (ItemStack item : equips) {
            //判断是否为Null
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                if (Entry.getInstance().isSuit(item)) {
                    Suit suit = SuitDao.getSuit(item.getItemMeta().getDisplayName());
                    if (suits.containsKey(suit.getName())) {
                        suits.put(suit.getName(), (suits.get(suit.getName()) + 1));
                    } else {
                        suits.put(suit.getName(), 1);
                    }
                }
            }
        }
        return suits;
    }

    /**
     * 套装加成数据转Attribute
     *
     * @param suit   套装
     * @param amount 套装个数
     * @return {@link Attribute}
     */
    public static Attribute suitAddtionDataToAttribute(Suit suit, Integer amount) {
        if (amount == 0 || amount == 1) {
            return null;
        }
        Attribute att = new Attribute(null);

        List<Attribute> dataList = suit.getAddtionData();

        for (int i = 2; i <= amount; i++) {
            int j = i - 2;
            att = sumAttributes(att, dataList.get(j));
        }
        return att;
    }

    /**
     * 宝石加成数据转Attribute
     *
     * @param gem 宝石
     */
    public static Attribute gemAddtionDataToAttribute(Gem gem) {
        Attribute att = new Attribute(null);
        Map<GemType, Double> data = gem.getGemAddtionData();
        for (Map.Entry<GemType, Double> set : data.entrySet()) {
            switch (set.getKey()) {
                case HEALTH:
                    att.setHealth(set.getValue());
                    continue;
                case PERCENTHEALTH:
                    att.setPercentHealth(set.getValue());
                    continue;
                case ATTACK:
                    att.setAttack(set.getValue());
                    continue;
                case DEFENSE:
                    att.setDefense(set.getValue());
                    continue;
                case HIT:
                    att.setHit(set.getValue());
                    continue;
                case DODGE:
                    att.setDodge(set.getValue());
                    continue;
                case KNOWING:
                    att.setKnowing(set.getValue());
                    continue;
                case KNOWINGDEFENSE:
                    att.setKnowingDefense(set.getValue());
                    continue;
                case STRENTH:
                    att.setStrenth(set.getValue());
                    continue;
                case TOUGHNESS:
                    att.setToughness(set.getValue());
                    continue;
                case ABILITY:
                    att.setAbility(set.getValue());
                    continue;
                case BODY:
                    att.setBody(set.getValue());
                    continue;
                case PHYSICALPOWER:
                    att.setPhysicalPower(set.getValue());
                    continue;
                case ICE:
                    att.setIce(set.getValue());
                    continue;
                case ICEDEFENSE:
                    att.setIceDefense(set.getValue());
                    continue;
                case FIRE:
                    att.setFire(set.getValue());
                    continue;
                case FIREDEFENSE:
                    att.setFireDefense(set.getValue());
                    continue;
                case XUAN:
                    att.setXuan(set.getValue());
                    continue;
                case XUANDEFENSE:
                    att.setXuanDefense(set.getValue());
                    continue;
                case POISON:
                    att.setPoison(set.getValue());
                    continue;
                case POISONDEFENSE:
                    att.setPoisonDefense(set.getValue());
                    continue;
                default:
                    break;
            }
        }
        return att;
    }

    /**
     * 自动计算多个Attribute的参数
     *
     * @param attributes
     * @return {@link Attribute}
     */
    public static Attribute sumAttributes(Attribute... attributes) {
        Attribute att = new Attribute(null);
        for (Attribute attribute : attributes) {
            att.setHealth(att.getHealth() + attribute.getHealth());
            att.setPercentHealth(att.getPercentHealth() + attribute.getPercentHealth());
            att.setAttack(att.getAttack() + attribute.getAttack());
            att.setDefense(att.getDefense() + attribute.getDefense());
            att.setHit(att.getHit() + attribute.getHit());
            att.setDodge(att.getDodge() + attribute.getDodge());
            att.setKnowing(att.getKnowing() + attribute.getKnowing());
            att.setKnowingDefense(att.getKnowingDefense() + attribute.getKnowingDefense());
            att.setStrenth(att.getStrenth() + attribute.getStrenth());
            att.setToughness(att.getToughness() + attribute.getToughness());
            att.setAbility(att.getAbility() + attribute.getAbility());
            att.setBody(att.getBody() + attribute.getBody());
            att.setPhysicalPower(att.getPhysicalPower() + attribute.getPhysicalPower());
            att.setIce(att.getIce() + attribute.getIce());
            att.setIceDefense(att.getIceDefense() + attribute.getIceDefense());
            att.setFire(att.getFire() + attribute.getFire());
            att.setFireDefense(att.getFireDefense() + attribute.getFireDefense());
            att.setXuan(att.getXuan() + attribute.getXuan());
            att.setXuanDefense(att.getXuanDefense() + attribute.getXuanDefense());
            att.setPoison(att.getPoison() + attribute.getPoison());
            att.setPoisonDefense(att.getPoisonDefense() + attribute.getPoisonDefense());
        }
        return att;
    }

    public static double getPotentialAttributeAmount(Attribute att) {
        return att.getStrenth() + att.getToughness() + att.getAbility() + att.getBody() + att.getPhysicalPower();
    }

}
