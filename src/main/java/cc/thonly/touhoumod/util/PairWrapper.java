package cc.thonly.touhoumod.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class PairWrapper<K,V> {
    K key;V value;
}
