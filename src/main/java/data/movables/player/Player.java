package data.movables.player;

import data.equipment.Equipment;
import data.equipment.Item;
import data.equipment.armors.Armor;
import data.equipment.weapons.Weapon;
import data.movables.Coords;
import data.movables.Movable;
import data.other.Preferences;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Player implements Movable {

    private Coords coords;
    private int maxHp = 100;
    private int hp;
    private final String name;
    private int attack;
    private int defense;
    private int level = 1;
    private Long experience = 0L;

    private boolean locked = false;

    private Equipment equipment = new Equipment(this);
    private LinkedList<Armor> armors = new LinkedList<>();
    private LinkedList<Weapon> weapons = new LinkedList<>();
    private LinkedList<String> messages = new LinkedList<>();


    public Player(String name) {
        this.coords =  new Coords(0, 0);
        this.name = name;
        this.hp = 100;
        weapons.push(equipment.getWeaponStore().get(0));
        armors.push(equipment.getArmorStore().get(0));
    }

    private void checkExp() {
        int[][] lvlMap = Preferences.levelingMap;
        int lvl = 0;
        for (int i = 2; i < lvlMap.length; i++) {
            if (experience >= lvlMap[i][1]) {
                lvl = lvlMap[i][0];
            }
        }
        if (lvl > this.level) {
            addMessage("Level up! " + lvl + " Health Restored to new max!");
            this.level = lvl;
            attack += lvlMap[lvl][2];
            defense += lvlMap[lvl][3];
            maxHp += lvlMap[lvl][4];
            hp = maxHp;
        }
    }

    public void gainExp(Long exp) {
        experience += exp;
        checkExp();
    }

    public String getName() {
        return name;
    }

    @Override
    public Coords getCoords() {
        return coords;
    }

    @Override
    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    @Override
    public int getX() {
        return coords.getX();
    }

    @Override
    public int getY() {
        return coords.getY();
    }

    @Override
    public void setX(int x) {
        coords.setX(x);
    }

    @Override
    public void setY(int y) {
        coords.setY(y);
    }

    public void equip(int key) {
        if (equipment.getItems().containsKey(key)) {
            Item item = equipment.getItems().get(key);
            if (item instanceof Weapon) {
                equipWeapon((Weapon) item);
            } else {
                equipArmor((Armor) item);
            }
        }
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void equipWeapon(Weapon weapon) {
        if (weapons.size() < 2) {
            weapons.push(weapon);
            equipment.removeItemFromEquipment(weapon);
            addMessage("[INFO]: Equipped new weapon: " + weapon.getName());
        } else {
            addMessage("[WARN]: You are handling already to many weapons! Limit = 2pcs");
        }
    }

    public void removeWeaponFromInventory() {
        if (!weapons.isEmpty()) {
            Weapon weapon = weapons.peek();
            if (equipment.addToEquipment(weapon)) {
                weapons.poll();
                addMessage("[INFO]: Removed weapon: " + weapon.getName());
            } else {
                addMessage("[WARN]: Your equipment is already full, cannot move item!");
            }
        } else {
            addMessage("[WARN]: You are not handling any weapon");
        }
    }

    public List<Armor> getArmorList() {
        return armors;
    }

    public void equipArmor(Armor armor) {
        if (armors.size() < 3) {
            armors.push(armor);
            equipment.removeItemFromEquipment(armor);
            addMessage("[INFO]: Added new armor: " + armor.getName());
        } else {
            addMessage("[WARN]: You are wearing already to many items! Limit = 3pcs");
        }
    }

    public void removeArmorFromInventory() {
        if (!armors.isEmpty()) {
            Armor armor = armors.peek();
            if (equipment.addToEquipment(armor)) {
                armors.poll();
                addMessage("[INFO]: Removed armor: " + armor.getName());
            } else {
                addMessage("[WARN]: Your equipment is already full, cannot move item!");
            }
        } else {
            addMessage("[WARN]: You are not wearing any armor");
        }
    }

    public int getAttack() {
        return attack + weapons
                .stream()
                .collect(Collectors.summingInt(Weapon::getAttack));
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense + armors
                .stream()
                .collect(Collectors.summingInt(Armor::getDefense));
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public int getHP() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setHp(int hp) {
        if (hp > maxHp) {
            this.hp = maxHp;
        } else {
            this.hp = hp;
        }
    }

    public void getHit(int damage) {
        hp -= damage;
        if (hp < 0) {
            addMessage("[INFO]: GAME OVER! Press any arrow to continue.");
            hp = 0;
        }
    }

    public void addMessage(String message) {
        if (messages.size() == 7) {
            messages.removeLast();
        }
        messages.push(message);
    }

    public void lock() {
        this.locked = true;
    }

    public void unlock() {
        this.locked = false;
    }

    public boolean isLocked() {
        if (locked) {
            addMessage("[WARN]: Can't do that while in combat!");

        }
        return locked;
    }

    public int getLevel() {
        return level;
    }

    public Long getExperience() {
        return experience;
    }

    public LinkedList<String> getMessages() {
        return messages;
    }
}