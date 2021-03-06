package data.equipment.bootstrap;

import data.equipment.armors.Armor;
import data.equipment.armors.CasualArmor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ArmorStore implements Map<Integer, Armor> {

    private final Map<Integer, Armor> armorStore = new HashMap<>();

    private static final Logger log = Logger.getLogger(ArmorStore.class.toString());

    private ArmorStore() {
        log.info("Bootstrapping armor store");
        bootstrap();
    }

    public static ArmorStore getInstance() {
        return new ArmorStore();
    }

    private void bootstrap() {
        Path path = Paths.get("src/resources/bootstrapFiles/armors.txt");
        List<String> armors = null;

        try {
            armors = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
            log.log(Level.WARNING, "Troubles with reading file!" + path.getFileName(), e);
        }

        int i = 0;
        for (String item : armors) {
            String[] itemData = item.split(";");
            armorStore.put(i++, CasualArmor.getInstance(itemData[0], itemData[1], Integer.parseInt(itemData[2])));
        }
    }

    @Override
    public int size() {
        return armorStore.size();
    }

    @Override
    public boolean isEmpty() {
        return armorStore.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return armorStore.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return armorStore.containsValue(value);
    }

    @Override
    public Armor get(Object key) {
        return armorStore.get(key);
    }

    @Override
    public Armor put(Integer key, Armor value) {
        return armorStore.put(key, value);
    }

    @Override
    public Armor remove(Object key) {
        return armorStore.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Armor> m) {
        armorStore.putAll(m);
    }

    @Override
    public void clear() {
        armorStore.clear();
    }

    @Override
    public Set<Integer> keySet() {
        return armorStore.keySet();
    }

    @Override
    public Collection<Armor> values() {
        return armorStore.values();
    }

    @Override
    public Set<Entry<Integer, Armor>> entrySet() {
        return armorStore.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
