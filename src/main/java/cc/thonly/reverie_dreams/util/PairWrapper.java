package cc.thonly.reverie_dreams.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class PairWrapper<K,V> {
    K key;V value;
}
