package Model.ADT;

import java.util.Map;

public interface IFileTable<K, V> {
    void add(K key, V value);
    boolean isDescDef(K key);
    V lookUp(K key);
    void remove(K key);
    Map<K, V> getContent();
}
