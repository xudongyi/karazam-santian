package com.klzan.core.freemarker.tags;

import freemarker.ext.beans.StringModel;
import freemarker.template.*;

import java.util.Map;

public class TagSupport {

    protected void assertNoBody(TemplateDirectiveBody body) throws TemplateModelException {
        if (body != null)
            throw new TemplateModelException(String.format("%s directive does not allow body", getClass().getSimpleName()));
    }

    protected void assertHasBody(TemplateDirectiveBody body) throws TemplateModelException {
        if (body == null)
            throw new TemplateModelException(String.format("%s directive should have body", getClass().getSimpleName()));
    }

    protected String getRequiredStringParam(Map params, String key) throws TemplateModelException {
        Object value = params.get(key);
        if (!(value instanceof SimpleScalar))
            throw new TemplateModelException(String.format("%s param is required by %s, and must be string", key, getClass().getSimpleName()));
        return ((SimpleScalar) value).getAsString();
    }

    protected String getStringParam(Map params, String key) throws TemplateModelException {
        Object value = params.get(key);
        if (value == null) return null;
        if (!(value instanceof SimpleScalar))
            throw new TemplateModelException(String.format("%s param must be string in %s", key, getClass().getSimpleName()));
        return ((SimpleScalar) value).getAsString();
    }

    /**
     * return the real object wrapped by freemarker
     *
     * @param arguments
     * @param argName
     * @return
     * @throws TemplateModelException
     */
    protected Object getRealObject(Map arguments, String argName) throws TemplateModelException {
        Object arg = arguments.get(argName);
        if (arg == null) return null;
        if (arg.getClass().equals(SimpleSequence.class)) {
            return ((SimpleSequence) arg).toList();
        } else if (arg.getClass().equals(StringModel.class)) {
            return ((StringModel) arg).getWrappedObject();
        } else if (arg.getClass().equals(SimpleScalar.class)) {
            return ((SimpleScalar) arg).getAsString();
        } else if (arg.getClass().equals(SimpleNumber.class)) {
            return ((SimpleNumber) arg).getAsNumber();
        } else if (arg.getClass().equals(SimpleDate.class)) {
            return ((SimpleDate) arg).getAsDate();
        }
        return arguments.get(argName);
    }

}
