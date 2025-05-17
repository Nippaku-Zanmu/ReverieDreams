package cc.thonly.touhoumod.util;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ObjectContainer<T> {
    public T value;
}
