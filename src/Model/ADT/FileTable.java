package Model.ADT;

import Model.Value.IValue;

import java.util.HashMap;
import java.util.Map;

public class FileTable<K, V> implements IFileTable<K, V>{
    private final Map<K, V> map;

    public FileTable() {
        map = new HashMap<K, V>();
    }
    @Override
    public void add(K key, V value) {
        map.put(key, value);
    }
    @Override
    public boolean isDescDef(K key) {
        return map.containsKey(key);
    }
    @Override
    public V lookUp(K key) {
        return map.get(key);
    }

    @Override
    public void remove(K key) {
        map.remove(key);
    }

    public String toString() {
        return map.toString();
    }

    public Map<K, V> getContent() {
        return map;
    }
}
