package me.may.attribute.dto;

public class Attribute {

    private String name;
    private double health = 20D;
    private double percentHealth = 0D;
    private double attack = 0D;
    private double defense = 0D;
    private double hit = 0D;
    private double dodge = 0D;
    private double knowing = 0D;
    private double knowingDefense = 0D;
    private double strenth = 0D;
    private double toughness = 0D;
    private double ability = 0D;
    private double body = 0D;
    private double physicalPower = 0D;
    private double ice = 0D;
    private double iceDefense = 0D;
    private double fire = 0D;
    private double fireDefense = 0D;
    private double xuan = 0D;
    private double xuanDefense = 0D;
    private double poison = 0D;
    private double poisonDefense = 0D;

    public Attribute(String name) {
    }

    /**
     * 构造方法
     *
     * @param name           属性名
     * @param health         血量
     * @param percentHealth  百分比血量
     * @param attack         攻击
     * @param defense        防御
     * @param hit            命中
     * @param dodge          闪避
     * @param knowing        会心攻击
     * @param knowingDefense 会心防御
     * @param strenth        力量
     * @param toughness      韧性
     * @param ability        定力
     * @param body           身法
     * @param physicalPower  体力
     */
    public Attribute(String name, double health, double percentHealth, double attack, double defense, double hit,
                     double dodge, double knowing, double knowingDefense, double strenth, double toughness, double ability,
                     double body, double physicalPower) {
        this.name = name;
        this.health = health;
        this.percentHealth = percentHealth;
        this.attack = attack;
        this.defense = defense;
        this.hit = hit;
        this.dodge = dodge;
        this.knowing = knowing;
        this.knowingDefense = knowingDefense;
        this.strenth = strenth;
        this.toughness = toughness;
        this.ability = ability;
        this.body = body;
        this.physicalPower = physicalPower;
    }

    /**
     * 构造方法
     *
     * @param name           属性名
     * @param health         血量
     * @param percentHealth  百分比血量
     * @param attack         攻击
     * @param defense        防御
     * @param hit            命中
     * @param dodge          闪避
     * @param knowing        会心攻击
     * @param knowingDefense 会心防御
     * @param strenth        力量
     * @param toughness      韧性
     * @param ability        定力
     * @param body           身法
     * @param physicalPower  体力
     * @param ice            冰攻
     * @param iceDefense     冰抗
     * @param fire           火攻
     * @param fireDefense    火抗
     * @param xuan           玄攻
     * @param xuanDefense    玄抗
     * @param poison         毒攻
     * @param poisonDefense  毒抗
     */
    public Attribute(String name, double health, double percentHealth, double attack, double defense, double hit, double dodge, double knowing, double knowingDefense, double strenth, double toughness, double ability, double body, double physicalPower, double ice, double iceDefense, double fire, double fireDefense, double xuan, double xuanDefense, double poison, double poisonDefense) {
        this.name = name;
        this.health = health;
        this.percentHealth = percentHealth;
        this.attack = attack;
        this.defense = defense;
        this.hit = hit;
        this.dodge = dodge;
        this.knowing = knowing;
        this.knowingDefense = knowingDefense;
        this.strenth = strenth;
        this.toughness = toughness;
        this.ability = ability;
        this.body = body;
        this.physicalPower = physicalPower;
        this.ice = ice;
        this.iceDefense = iceDefense;
        this.fire = fire;
        this.fireDefense = fireDefense;
        this.xuan = xuan;
        this.xuanDefense = xuanDefense;
        this.poison = poison;
        this.poisonDefense = poisonDefense;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "name='" + name + '\'' +
                ", health=" + health +
                ", percentHealth=" + percentHealth +
                ", attack=" + attack +
                ", defense=" + defense +
                ", hit=" + hit +
                ", dodge=" + dodge +
                ", knowing=" + knowing +
                ", knowingDefense=" + knowingDefense +
                ", strenth=" + strenth +
                ", toughness=" + toughness +
                ", ability=" + ability +
                ", body=" + body +
                ", physicalPower=" + physicalPower +
                ", ice=" + ice +
                ", iceDefense=" + iceDefense +
                ", fire=" + fire +
                ", fireDefense=" + fireDefense +
                ", xuan=" + xuan +
                ", xuanDefense=" + xuanDefense +
                ", poison=" + poison +
                ", poisonDefense=" + poisonDefense +
                '}';
    }

    public Attribute clone() {
        return new Attribute(name, health, percentHealth, attack, defense, hit, dodge, knowing, knowingDefense, strenth,
                toughness, ability, body, physicalPower);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getPercentHealth() {
        return percentHealth;
    }

    public void setPercentHealth(double percentHealth) {
        this.percentHealth = percentHealth;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public double getHit() {
        return hit;
    }

    public void setHit(double hit) {
        this.hit = hit;
    }

    public double getDodge() {
        return dodge;
    }

    public void setDodge(double dodge) {
        this.dodge = dodge;
    }

    public double getKnowing() {
        return knowing;
    }

    public void setKnowing(double knowing) {
        this.knowing = knowing;
    }

    public double getKnowingDefense() {
        return knowingDefense;
    }

    public void setKnowingDefense(double knowingDefense) {
        this.knowingDefense = knowingDefense;
    }

    public double getStrenth() {
        return strenth;
    }

    public void setStrenth(double strenth) {
        this.strenth = strenth;
    }

    public double getToughness() {
        return toughness;
    }

    public void setToughness(double toughness) {
        this.toughness = toughness;
    }

    public double getAbility() {
        return ability;
    }

    public void setAbility(double ability) {
        this.ability = ability;
    }

    public double getBody() {
        return body;
    }

    public void setBody(double body) {
        this.body = body;
    }

    public double getPhysicalPower() {
        return physicalPower;
    }

    public void setPhysicalPower(double physicalPower) {
        this.physicalPower = physicalPower;
    }

    public double getIce() {
        return ice;
    }

    public void setIce(double ice) {
        this.ice = ice;
    }

    public double getIceDefense() {
        return iceDefense;
    }

    public void setIceDefense(double iceDefense) {
        this.iceDefense = iceDefense;
    }

    public double getFire() {
        return fire;
    }

    public void setFire(double fire) {
        this.fire = fire;
    }

    public double getFireDefense() {
        return fireDefense;
    }

    public void setFireDefense(double fireDefense) {
        this.fireDefense = fireDefense;
    }

    public double getXuan() {
        return xuan;
    }

    public void setXuan(double xuan) {
        this.xuan = xuan;
    }

    public double getXuanDefense() {
        return xuanDefense;
    }

    public void setXuanDefense(double xuanDefense) {
        this.xuanDefense = xuanDefense;
    }

    public double getPoison() {
        return poison;
    }

    public void setPoison(double poison) {
        this.poison = poison;
    }

    public double getPoisonDefense() {
        return poisonDefense;
    }

    public void setPoisonDefense(double poisonDefense) {
        this.poisonDefense = poisonDefense;
    }
}
