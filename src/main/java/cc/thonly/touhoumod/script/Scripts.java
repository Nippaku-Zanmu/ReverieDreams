package cc.thonly.touhoumod.script;

import lombok.extern.slf4j.Slf4j;

import javax.script.*;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Map;

@Slf4j
public class Scripts {
    private static final ScriptEngine ENGINE = new ScriptEngineManager().getEngineByName("nashorn");

    /**
     * 执行脚本代码
     */
    public static Object eval(String code, Map<String, Object> bindings) {
        try {
            Bindings ctx = ENGINE.createBindings();
            if (bindings != null) ctx.putAll(bindings);
            return ENGINE.eval(code, ctx);
        } catch (ScriptException e) {
            log.error("Script error: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 从文件执行脚本
     */
    public static Object evalFile(File file, Map<String, Object> bindings) {
        try (Reader reader = new FileReader(file)) {
            Bindings ctx = ENGINE.createBindings();
            if (bindings != null) ctx.putAll(bindings);
            return ENGINE.eval(reader, ctx);
        } catch (Exception e) {
            log.error("Script file error: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 调用已加载脚本中的函数
     */
    public static Object call(String code, String function, Object... args) {
        try {
            ENGINE.eval(code);
            if (ENGINE instanceof Invocable invocable) {
                return invocable.invokeFunction(function, args);
            } else {
                log.error("Script engine is not invocable");
                return null;
            }
        } catch (Exception e) {
            log.error("Script call error: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 调用函数（带绑定）
     */
    public static Object callWithContext(String code, String function, Map<String, Object> bindings, Object... args) {
        try {
            Bindings ctx = ENGINE.createBindings();
            if (bindings != null) ctx.putAll(bindings);
            ENGINE.eval(code, ctx);
            if (ENGINE instanceof Invocable invocable) {
                return invocable.invokeFunction(function, args);
            }
        } catch (Exception e) {
            log.error("Script function call error: {}", e.getMessage(), e);
        }
        return null;
    }
}
