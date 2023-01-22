package Model.ADT;

import java.util.Map;

public interface ITypeEnv<K, V> {

    void put(K key, V value);
    V lookUp(K key);
    Map<K, V> getContent();
    boolean isVarDef(K key);
    void update(K key, V value);
    ITypeEnv<K, V> deepcopy();
}
