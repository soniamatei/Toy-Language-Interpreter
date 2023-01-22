package Model.ADT;

import java.util.HashMap;
import java.util.Map;

public class TypeEnv<K, V> implements ITypeEnv<K, V>{

    private final Map<K, V> map;

    public TypeEnv() {map = new HashMap<>();
    }

    public TypeEnv(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public boolean isVarDef(K key) { return false; }

    @Override
    public void update(K key, V value) {}

    @Override
    public V lookUp(K key) {
        return map.get(key);
    }

    @Override
    public Map<K, V>  getContent() {
        return map;
    }

    @Override
    public ITypeEnv<K, V> deepcopy() { return new TypeEnv<>(new HashMap<>(map)); }
}
