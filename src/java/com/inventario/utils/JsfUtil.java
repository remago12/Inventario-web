package com.inventario.utils;

import java.util.StringTokenizer;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.validator.ValidatorException;

import org.primefaces.context.RequestContext;

public class JsfUtil {

    public static Object getBean(String expr) {
        FacesContext context = FacesContext.getCurrentInstance();
        Application app = context.getApplication();
        ValueBinding binding = app.createValueBinding("#{" + expr + "}");
        Object value = binding.getValue(context);
        return value;
    }

    public static void showFacesMsg(Exception e, String msg, String msgName,
                    Severity severityError) {

        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        String causeMsgs = "";
        String msgErr = "";

        if (e != null) {
            causeMsgs = getCauseMsgs(e.getCause());
            msgErr = getRootCauseErrMsg(e.getCause());
        }

        context.addMessage(null, new FacesMessage(severityError, msg, msgErr));
        if(requestContext != null){
            updateComponent(msgName);
        }
    }

    public static void updateComponent(String id){
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update(id);
    }
	
    public static void openDialog(String id){
        RequestContext.getCurrentInstance().execute(id+".show()");
    }

    public static void closeDialog(String id){
        RequestContext.getCurrentInstance().execute(id+".hide()");
    }
	
    private static String getCauseMsgs(Throwable cause) {
        String causeMsgs = "";
        if (cause == null) {
            return "";
        } else {
            if (cause.getCause() == null) {
                causeMsgs = cause.getMessage() + "/n";
            } else {
                causeMsgs = causeMsgs + getRootCauseErrMsg(cause.getCause());
            }
            return causeMsgs;
        }
    }

    private static String getRootCauseErrMsg(Throwable cause) {
        String errMsg = null;
        if (cause == null) {
            return "";
        } else {
            if (cause.getCause() == null) {
                errMsg = cause.getMessage();
            } else {
                errMsg = getRootCauseErrMsg(cause.getCause());
            }
            return errMsg;
        }
    }
	
    public static void restaurarValoresFormulario(String id) {
        UIComponent content = FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
        content.getChildren().clear();
    }
	
    public static void restaurarVistaActual(){
        //Estas lineas son utilizadas en caso se quiera restaurar los valores de toda la pagina
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        javax.faces.application.ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse(); 	

    }
	
    public static ValidatorException buildException(String msgKey) {
        FacesMessage msg = new FacesMessage(msgKey, msgKey);
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(msg);
    }

    private static void generateLogMsgs(Exception e, RequestContext requestContext, String causeMsgs) {
        StringTokenizer st = new StringTokenizer(e.getMessage(), "\n");

        while (st.hasMoreTokens()) {
            String textNode = st.nextElement().toString();
            requestContext.execute("PrimeFaces.info('" + textNode + "');");
        }

        st = new StringTokenizer(causeMsgs, "\n");

        while (st.hasMoreTokens()) {
            String textNode = st.nextElement().toString();
            if (textNode.length() > 0) {
                requestContext.execute("PrimeFaces.error('" + textNode + "');");
            }
        }
    }
}


