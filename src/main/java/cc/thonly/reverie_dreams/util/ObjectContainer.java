package cc.thonly.reverie_dreams.util;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ObjectContainer<T> {
    public T value;
}
