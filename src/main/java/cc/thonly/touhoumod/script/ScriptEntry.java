package cc.thonly.touhoumod.script;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class ScriptEntry {
    public String name;
    public String code;
}
