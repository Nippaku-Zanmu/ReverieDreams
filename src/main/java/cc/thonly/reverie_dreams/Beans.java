package cc.thonly.reverie_dreams;

import jodd.petite.PetiteContainer;
import jodd.petite.meta.PetiteBean;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.util.Set;

@Slf4j
public class Beans {
    public static final PetiteContainer pc = new PetiteContainer();
    public static final Reflections reflections = new Reflections(Beans.class.getPackageName());

    public static void main(String[] args) {
        Set<Class<?>> beans = reflections.getTypesAnnotatedWith(PetiteBean.class);
        for (Class<?> bean : beans) {
            pc.registerPetiteBean(bean);
            log.info("Registered PetiteBean: {}", bean.getName());
        }
    }
}
