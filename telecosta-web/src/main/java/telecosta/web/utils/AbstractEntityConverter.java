package telecosta.web.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import org.apache.log4j.Logger;

/**
 * Es necesario anotar la clase que hereda con
*
 * @author rcacacho
 */
public abstract class AbstractEntityConverter implements Converter {

    protected static final Logger log = Logger.getLogger(AbstractEntityConverter.class);

    protected abstract EntityManager getEntityManager();

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        if (entity == null) {
            return null;
        }
        try {
            Class clazz = entity.getClass();
            if (clazz.isAnnotationPresent(Entity.class)) {
                log.debug("Entity annotation present!");
                Field f = getEntityIdField(clazz);
                if (f == null) {
                    log.warn("@Id annotated field not found");
                    return null;
                }
                f.setAccessible(true);
                String asString = clazz.getCanonicalName() + ":" + f.get(entity);
                return asString;
            }

        } catch (IllegalArgumentException | IllegalAccessException e) {
            log.warn("Cannot convert", e);
        }
        log.warn("@Entity annotation not found in object");
        return null;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String string) {

        if (string == null) {
            return null;
        }
        try {
            String[] split = string.split(":");
            Class clazz = Class.forName(split[0]);
            Field f = getEntityIdField(clazz);
            if (f == null) {
                log.warn("@Id annotated field not found");
                return null;
            }
            EntityManager em = getEntityManager();
            if (em == null) {
                log.warn("EntityManager is null. Can´t convert to object.");
                return null;
            }

            if (f.getType().equals(String.class)) {
                return em.find(clazz, split[1]);
            }
            Method valueOfMethod = f.getType().getMethod("valueOf", String.class);
            return em.find(clazz, valueOfMethod.invoke(null, split[1]));

        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            log.warn("Cannot convert: " + e.getMessage());

        }
        return null;
    }

    private Field getEntityIdField(Class clazz) {

        while (clazz != null && clazz != Object.class) {
            try {
                Field[] fields = clazz.getDeclaredFields();
                for (Field f : fields) {
                    if (f.isAnnotationPresent(Id.class)) {
                        return f;
                    }
                }
                clazz = clazz.getSuperclass();
            } catch (SecurityException ex) {
                /*do nothing*/
                log.warn(ex.getMessage());
            }

        }
        return null;
    }
}
