package cc.thonly.touhoumod.script;

import lombok.extern.slf4j.Slf4j;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

@Slf4j
public class ScriptManager {
    private static ScriptManager INSTANCE;
    private final ScriptEngine engine;
    private ScriptManager() {
        this.engine = new ScriptEngineManager().getEngineByName("nashorn");
    }

    /**
     * @param entry 脚本封装对象
     * @param input 传入脚本的参数对象（通常是 Map<String, Object>）
     * @return 脚本返回的执行结果（可能是 Map / List / 基本类型）
     */
    public Object parse(ScriptEntry entry, Object args) {
        try {
            engine.eval(entry.getCode());

            if (engine instanceof Invocable invocable) {
                return invocable.invokeFunction("apply", args);
            }
        } catch (Exception e) {
            log.error("Script execution failed: {}", e.getMessage(), e);
        }
        return null;
    }

    public static ScriptManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ScriptManager();
        }
        return INSTANCE;
    }
}
