package me.may.attribute;

import me.may.attribute.api.AttributeAPI;
import me.may.attribute.command.AttributeCommand;
import me.may.attribute.dao.*;
import me.may.attribute.dto.Attribute;
import me.may.attribute.dto.ClassAttributeEnum;
import me.may.attribute.listener.EntityDamageByEntityListener;
import me.may.attribute.listener.InventoryClickListener;
import me.may.attribute.listener.PlayerLevelUpListener;
import me.may.attribute.listener.PluginDisableListener;
import me.may.attribute.utils.FileUtil;
import me.may.attribute.utils.SkillUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;

public class Entry extends JavaPlugin {

    public static File dataFile;
    private static Entry instance;
    private static File classesFile;
    private static File addtionFile;
    private static int time;
    public boolean debug;
    public String invName;
    public String potentialInvName;
    public int upPoint;
    public String dbname;
    public boolean isSQL;
    public BukkitTask attributeThreadId;
//    public BukkitTask potentialThreadId;

    /**
     * 取主类实例
     *
     * @return {@link Entry}
     */
    public static Entry getInstance() {
        return instance;
    }

    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§a[人物属性] §e>> §f已加载");
        Bukkit.getPluginCommand("attribute").setExecutor(new AttributeCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerLevelUpListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new PluginDisableListener(), this);

        classesFile = new File(getDataFolder().getAbsolutePath() + "\\classes\\");
        addtionFile = new File(getDataFolder().getAbsolutePath() + "\\addtions\\");
        dataFile = new File(getDataFolder().getAbsolutePath() + "\\potentialdatas\\");
        if (!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
            classesFile.mkdirs();
            addtionFile.mkdirs();
            dataFile.mkdirs();

            //初始化默认职业
            try {
                File file = new File(classesFile, "default.yml");
                file.createNewFile();
                FileConfiguration filec = FileUtil.loadYml(file);
                filec.set("AttributeType", ClassAttributeEnum.FIRE.toString());
                filec.set("health", 20);
                filec.set("percentHealth", 0);
                filec.set("attack", 0);
                filec.set("defense", 0);
                filec.set("hit", 0);
                filec.set("dodge", 0);
                filec.set("knowing", 0);
                filec.set("knowingDefense", 0);
                filec.set("strenth", 0);
                filec.set("toughness", 0);
                filec.set("physicalPower", 0);
                filec.set("body", 0);
                filec.set("ability", 0);
                FileUtil.saveYml(filec, file);

                file = new File(addtionFile, "default.yml");
                filec = FileUtil.loadYml(file);
                filec.set("strenth", 0);
                filec.set("toughness", 0);
                filec.set("ability", 0);
                filec.set("body", 0);
                filec.set("physicalPower", 0);
                filec.set("knowing", 0);
                filec.set("knowingDefense", 0);
                FileUtil.saveYml(filec, file);
            } catch (IOException e) {
                System.out.println("错误: " + e.getMessage());
            }

            //初始化所有职业
            for (String string : SkillUtil.getAllClasses()) {
                File temp = new File(classesFile, string + ".yml");
                try {
                    temp.createNewFile();
                    FileConfiguration filec = FileUtil.loadYml(temp);
                    filec.set("AttributeType", ClassAttributeEnum.FIRE.toString());
                    filec.set("health", 20);
                    filec.set("percentHealth", 0);
                    filec.set("attack", 0);
                    filec.set("defense", 0);
                    filec.set("hit", 0);
                    filec.set("dodge", 0);
                    filec.set("knowing", 0);
                    filec.set("knowingDefense", 0);
                    filec.set("strenth", 0);
                    filec.set("toughness", 0);
                    filec.set("physicalPower", 0);
                    filec.set("body", 0);
                    filec.set("ability", 0);
                    FileUtil.saveYml(filec, temp);
                } catch (IOException e) {
                    System.out.println("错误: " + e.getMessage());
                }
            }

            for (String string : SkillUtil.getAllClasses()) {
                File temp = new File(addtionFile, string + ".yml");
                try {
                    temp.createNewFile();
                    FileConfiguration filec = FileUtil.loadYml(temp);
                    filec.set("knowing", 1);
                    filec.set("knowingDefense", 1);
                    filec.set("strenth", 1);
                    filec.set("toughness", 1);
                    filec.set("physicalPower", 1);
                    filec.set("body", 1);
                    filec.set("ability", 1);
                    FileUtil.saveYml(filec, temp);
                } catch (IOException e) {
                    System.out.println("错误: " + e.getMessage());
                }
            }

            Bukkit.getConsoleSender().sendMessage("§a[人物属性] §e>> §f初始化成功!");
        }
        instance = this;
        time = getConfig().getInt("Time");
        invName = getConfig().getString("Gui.Title").replaceAll("&", "§");
        potentialInvName = getConfig().getString("Gui.Potential.Title").replaceAll("&", "§");
        debug = getConfig().getBoolean("Debug");
        upPoint = getConfig().getInt("Potential");
        isSQL = getConfig().getBoolean("Mysql.Use");

        try {
            //指定连接类型
            Class.forName("com.mysql.jdbc.Driver");
            String host = getConfig().getString("Mysql.Host");
            String port = getConfig().getString("Mysql.Port");
            dbname = getConfig().getString("Mysql.DataBase");
            String username = getConfig().getString("Mysql.User");
            String password = getConfig().getString("Mysql.PassWord");
            String ip = "jdbc:mysql://" + host + ":" + port + "/" + dbname + "?autoReconnect=true";
            String sql = "CREATE TABLE IF NOT EXISTS att_potential (" +
                    "id int(11) NOT NULL AUTO_INCREMENT," +
                    "player_name varchar(255) DEFAULT NULL," +
                    "point int(11) DEFAULT NULL," +
                    "strenth double DEFAULT NULL," +
                    "toughness double DEFAULT NULL," +
                    "ability double DEFAULT NULL," +
                    "body double DEFAULT NULL," +
                    "physical_power double DEFAULT NULL," +
                    "PRIMARY KEY(id) ) " +
                    "ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ;";
            Connection conn = DriverManager.getConnection(ip, username, password);
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
            BasicDao.setConnection(conn);
            Bukkit.getConsoleSender().sendMessage("§a[人物属性] §e> §f数据库连接成功!");
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
            Bukkit.getConsoleSender().sendMessage("§a[人物属性] §e> §f数据库连接失败!");
            Bukkit.getPluginManager().disablePlugin(instance);
        }

        //各种数据缓冲
        loadDefaultAttributes();
        loadClassAddtionAttriubtes();
        loadClassTypeData();
        checkPlayerAttributeThread();

        if (isSQL) {
            SQLDao.loadPlayerDatas();
//            savePotentialDataThread();
        } else {
            loadPotentialDatas();
        }
    }

    @Override
    public void onDisable() {
        //防止玩家有未关闭的界面造成刷物品
        for(Player player:Bukkit.getOnlinePlayers())
            player.closeInventory();

        if (isSQL) {
            savePlayerPotentialDataToSQL();
        }
        savePlayerPotentialData();
        attributeThreadId.cancel();
//        potentialThreadId.cancel();
    }

    public void loadClassTypeData() {
        Set<String> temp = getConfig().getConfigurationSection("Type").getKeys(false);
        for (String key : temp) {
            double amount = getConfig().getDouble("Type." + key);
            ClassTypeDao.getMap().put(ClassAttributeEnum.valueOf(key), amount);
        }
    }

    /**
     * 读取潜力值数据
     */
    public void loadPotentialDatas() {
        Attribute att;
        FileConfiguration filec = null;
        for (File file : dataFile.listFiles()) {
            filec = FileUtil.loadYml(file);
            att = new Attribute(file.getName().replaceAll(".yml", "")
                    , 0
                    , 0
                    , 0
                    , 0
                    , 0
                    , 0
                    , 0
                    , 0
                    , filec.getDouble("strenth")
                    , filec.getDouble("toughness")
                    , filec.getDouble("ability")
                    , filec.getDouble("body")
                    , filec.getDouble("physicalPower"));
            PlayerPotentialDao.getMap().put(file.getName().replaceAll(".yml", ""), att);
            PlayerPointDao.getMap().put(file.getName().replaceAll(".yml", ""), filec.getInt("Point"));
        }
    }

    /**
     * 读取默认属性
     */
    public void loadDefaultAttributes() {
        Attribute att;
        FileConfiguration filec = null;
        for (File file : classesFile.listFiles()) {
            filec = FileUtil.loadYml(file);
            String className = file.getName().replaceAll(".yml", "");
            ClassTypeDao.putClassType(className, ClassAttributeEnum.valueOf(filec.getString("AttributeType")));

            att = new Attribute(file.getName().replaceAll(".yml", "")
                    , filec.getDouble("health")
                    , filec.getDouble("percentHealth")
                    , filec.getDouble("attack")
                    , filec.getDouble("defense")
                    , filec.getDouble("hit")
                    , filec.getDouble("dodge")
                    , filec.getDouble("knowing")
                    , filec.getDouble("knowingDefense")
                    , filec.getDouble("strenth")
                    , filec.getDouble("toughness")
                    , filec.getDouble("ability")
                    , filec.getDouble("body")
                    , filec.getDouble("physicalPower"));
            ClassDao.putClassDefaultAttribute(file.getName().replaceAll(".yml", ""), att);
        }
    }

    /**
     * 读取加成数据
     */
    public void loadClassAddtionAttriubtes() {
        Attribute att;
        FileConfiguration filec = null;
        for (File file : addtionFile.listFiles()) {
            filec = FileUtil.loadYml(file);
            att = new Attribute(file.getName().replaceAll(".yml", "")
                    , 0
                    , 0
                    , 0
                    , 0
                    , 0
                    , 0
                    , filec.getDouble("knowing")
                    , filec.getDouble("knowingDefense")
                    , filec.getDouble("strenth")
                    , filec.getDouble("toughness")
                    , filec.getDouble("ability")
                    , filec.getDouble("body")
                    , filec.getDouble("physicalPower"));
            ClassAddtionDao.putClassAddtionAttribute(file.getName().replaceAll(".yml", ""), att);
        }
    }

    private void savePlayerPotentialDataToSQL() {
        Map<String, Attribute> temps = PlayerPotentialDao.getMap();
        for (String name : temps.keySet()) {
            SQLDao.updatePlayerPotential(name);
        }
    }

    private void savePlayerPotentialData() {
        Attribute att;
        File file = null;
        FileConfiguration filec = null;
        Map<String, Attribute> temps = PlayerPotentialDao.getMap();
        for (Map.Entry<String, Attribute> entry : temps.entrySet()) {
            file = new File(dataFile, entry.getKey() + ".yml");
            filec = FileUtil.loadYml(file);
            att = entry.getValue();
            filec.set("Name", entry.getKey());
            filec.set("Point", PlayerPointDao.getMap().get(entry.getKey()));
            filec.set("strenth", att.getStrenth());
            filec.set("toughness", att.getToughness());
            filec.set("ability", att.getAbility());
            filec.set("body", att.getBody());
            filec.set("physicalPower", att.getPhysicalPower());
            FileUtil.saveYml(filec, file);
        }
    }

    public void updatePlayerHealth(Player player) {
        Attribute att = CacheDao.getPlayerAttribute(player.getName());
        if (att.getHealth() <= 0) {
            att.setHealth(20D);
        }
        player.setMaxHealth(att.getHealth() + (att.getHealth() * att.getPercentHealth() / 100));
    }


    /**
     * 检查玩家数据线程
     */
    private void checkPlayerAttributeThread() {
        attributeThreadId = Bukkit.getScheduler().runTaskTimerAsynchronously(Entry.getInstance(), new Runnable() {
            Long startTime = System.currentTimeMillis();

            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!PlayerPointDao.getMap().containsKey(player.getName())) {
                        File file = new File(Entry.dataFile.getAbsolutePath() + "\\" + player.getName() + ".yml");
                        FileConfiguration filec = FileUtil.loadYml(file);
                        filec.set("Name", player.getName());
                        filec.set("Point", 0);
                        filec.set("strenth", 0);
                        filec.set("toughness", 0);
                        filec.set("ability", 0);
                        filec.set("body", 0);
                        filec.set("physicalPower", 0);
                        FileUtil.saveYml(filec, file);
                        PlayerPotentialDao.getMap().put(player.getName(), new Attribute(player.getName(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                        PlayerPointDao.getMap().put(player.getName(), 0);
                    }

                    Attribute att = new Attribute(player.getName(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                    Attribute attTemp = ClassDao.getMap().get(SkillUtil.getPlayerClass(player));
                    if (attTemp == null) {
                        attTemp = ClassDao.getMap().get("default");
                    }
                    att.setHealth(player.getLevel() * attTemp.getHealth());
                    att.setPercentHealth(player.getLevel() * attTemp.getPercentHealth());
                    att.setAttack(player.getLevel() * attTemp.getAttack());
                    att.setDefense(player.getLevel() * attTemp.getDefense());
                    att.setHit(player.getLevel() * attTemp.getHit());
                    att.setDodge(player.getLevel() * attTemp.getDodge());
                    att.setKnowing(player.getLevel() * attTemp.getKnowing());
                    att.setKnowingDefense(player.getLevel() * attTemp.getKnowingDefense());
                    att.setStrenth(player.getLevel() * attTemp.getStrenth());
                    att.setToughness(player.getLevel() * attTemp.getToughness());
                    att.setAbility(player.getLevel() * attTemp.getAbility());
                    att.setBody(player.getLevel() * attTemp.getBody());
                    att.setPhysicalPower(player.getLevel() * attTemp.getPhysicalPower());

                    att = AttributeAPI.sumAttributes(att, AttributeAPI.getEntityEquipMentAttribute(player));

					/*1.1新需求 ---> 门派加成系统*/
                    Attribute addtion = ClassAddtionDao.getClassAddtionAttribute(SkillUtil.getPlayerClass(player));
                    att.setAttack(att.getAttack() + att.getStrenth() * addtion.getStrenth());
                    att.setDefense(att.getDefense() + att.getToughness() * addtion.getToughness());
                    att.setHit(att.getHit() + att.getAbility() * addtion.getAbility());
                    att.setDodge(att.getDodge() + att.getBody() * addtion.getBody());
                    att.setHealth(att.getHealth() + att.getPhysicalPower() * addtion.getPhysicalPower());
                    att.setKnowing(att.getKnowing() + att.getKnowing() * addtion.getKnowing());
                    att.setKnowingDefense(att.getKnowingDefense() + att.getKnowingDefense() * addtion.getKnowingDefense());

                    CacheDao.putPlayerAttribute(player.getName(), att);
                    updatePlayerHealth(player);

                    if (!SQLDao.hasPlayerData(player.getName())) {
                        SQLDao.initialPlayerPotential(player.getName());
                    }
                    savePlayerPotentialDataToSQL();

                }
                if (debug) {
                    System.out.println("总耗时 " + (System.currentTimeMillis() - startTime - (time * 1000)) + "毫秒");
                    startTime = System.currentTimeMillis();
                }
            }
        }, 0L, time * 20L);
    }

    /**
     * 保存潜力点数据线程
     */
    public void savePotentialDataThread() {
        attributeThreadId = Bukkit.getScheduler().runTaskTimer(Entry.getInstance(), new Runnable() {
            @Override
            public void run() {

            }
        }, 0L, time * 20L);
    }

}