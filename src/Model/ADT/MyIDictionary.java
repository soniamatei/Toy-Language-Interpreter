package Model.ADT;

import java.util.Map;

public interface MyIDictionary<K, V> {
    void put(K key, V value);
    boolean isVarDef(K key);
    void update(K key, V value);
    V lookUp(K key);
    Map<K, V> getContent();
    MyIDictionary<K, V> deepcopy();
}
