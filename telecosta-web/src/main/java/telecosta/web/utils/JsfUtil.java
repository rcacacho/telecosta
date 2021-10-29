package telecosta.web.utils;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

public class JsfUtil {

    private static final Logger log = Logger.getLogger(JsfUtil.class);

    public static String ANIO_INICIAL = "AÑO_INICIAL";
    public static String ANIO_FINAL = "AÑO_FINAL";
    public static String USUARIO_LOGUEADO = "dvherrera";

    public static Long ID_TIPO_DEPENDENCIA_FUNCIONAL = 1L;
    public static Long ID_TIPO_DEPENDENCIA_PRESUPUESTAL = 2L;
    public static Long ID_DOCUMENTO_ACUERDO = 1L;
    public static Long ID_ENTIDAD_MP = 1L;
    public static String ID_GRUPO_RENGLON_RRHH = "0";

    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne, String placeholder) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", placeholder);
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "---");
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }

    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message);
        }
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addErrorMessage(String key, String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(key, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }

    public static Date now() {
        Calendar rightNow = Calendar.getInstance();
        return rightNow.getTime();
    }

    public static String getHour(Date now) {
        return (now.getHours() + 1) + ":" + (now.getMinutes() < 10 ? "0" + now.getMinutes() : now.getMinutes());
    }

    public static void redirectTo(String pageTo) {
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + pageTo);
        } catch (IOException e) {
            log.error(e);
        }
    }

}
