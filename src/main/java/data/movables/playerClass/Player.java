package data.movables.playerClass;

import data.equipment.Equipment;
import data.equipment.Item;
import data.equipment.armors.Armor;
import data.equipment.armors.LeatherArmor;
import data.equipment.weapons.BasicKnife;
import data.equipment.weapons.Weapon;
import data.movables.Coords;
import data.movables.Movable;

import java.util.LinkedList;
import java.util.List;

public abstract class Player implements Movable {

    private Coords coords;
    private int maxHp = 100;
    private int hp;
    private final String name;
    private int attack;
    private int defense;

    private boolean locked = false;

    private Equipment equipment;
    private LinkedList<Armor> armors = new LinkedList<>();
    private LinkedList<Weapon> weapons = new LinkedList<>();
    private LinkedList<String> messages = new LinkedList<>();


    public Player(String name) {
        this.coords =  new Coords(0, 0);
        this.name = name;
        this.hp = 100;
        armors.push(new LeatherArmor());
        weapons.push(new BasicKnife());
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
        if (armors.size() < 4) {
            armors.push(armor);
            equipment.removeItemFromEquipment(armor);
            addMessage("[INFO]: Added new armor: " + armor.getName());
        } else {
            addMessage("[WARN]: You are wearing already to many items! Limit = 4pcs");
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
        int sumAttack = attack;
        for (Weapon weapon : weapons) {
            sumAttack += weapon.getBonusAttack();
        }
        return sumAttack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        int sumDefense = defense;
        for (Armor armor : armors) {
            sumDefense += armor.getBonusDefense();
        }
        return sumDefense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public int getHP() {
        return hp;
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

    public LinkedList<String> getMessages() {
        return messages;
    }
}
