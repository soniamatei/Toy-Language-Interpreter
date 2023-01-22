package Model.ADT;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    private final Map<K, V> map;

    public MyDictionary() {
        map = new HashMap<>();
    }
    public MyDictionary(Map<K, V> map) { this.map = map; }
    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }
    @Override
    public boolean isVarDef(K key) {
        return map.containsKey(key);
    }
    @Override
    public void update(K key, V value) {
        map.put(key, value);
    }
    @Override
    public V lookUp(K key) {
        return map.get(key);
    }

    @Override
    public Map<K, V> getContent() {
        return map;
    }

    public String toString() {
        return map.toString();
    }

    public MyIDictionary<K, V> deepcopy() { return new MyDictionary<>(new HashMap<>(map)); }
}