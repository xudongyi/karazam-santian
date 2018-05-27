package com.klzan.core.freemarker.function;

import com.klzan.core.freemarker.tags.CDNTagSupports;
import freemarker.core.SimpleCharStream;
import freemarker.ext.beans.StringModel;
import freemarker.template.*;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

public class FunctionSupport extends CDNTagSupports {
	
	@SuppressWarnings("all")
	public String[] convertArgToStrings(List arguments) {
		String[] args = new String[arguments.size()];
		int i = 0;
		Iterator iter = arguments.iterator();
		while(iter.hasNext()) {
			Object arg = iter.next();
			if(arg instanceof SimpleScalar) {
				args[i] = ((SimpleScalar)arg).getAsString();
			} else if(arg instanceof SimpleNumber) {
				Number number = ((SimpleNumber)arg).getAsNumber();
				if(number instanceof Long) {
					args[i] = String.valueOf(number.longValue());
				} else if(number instanceof Double) {
					args[i] = String.valueOf(number.doubleValue());
				} else if(number instanceof Float) {
					args[i] = String.valueOf(number.floatValue());
				} else if(number instanceof Integer) {
					args[i] = String.valueOf(number.intValue());
				} else if(number instanceof Short) {
					args[i] = String.valueOf(number.shortValue());
				}
			} else if(arg instanceof SimpleDate) {
				args[i] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(((SimpleDate)arg).getAsDate());
			} else if(arg instanceof SimpleCharStream) {
				args[i] = ((SimpleCharStream)arg).GetImage();
			} else {
				args[i] = arg.toString();
			}
			i++;
		}
		return args;
	}
	
	/**
	 * return the real object wrapped by freemarker
	 * @param arguments
	 * @param index
	 * @return
	 * @throws TemplateModelException
	 */
	public Object getRealObject(List arguments, int index) throws TemplateModelException {
		Object arg = arguments.get(index);
		if(arg.getClass().equals(SimpleSequence.class)) {
			return ((SimpleSequence) arg).toList();
		} else if (arg.getClass().equals(StringModel.class)) {
			return ((StringModel) arg).getWrappedObject();
		} else if(arg.getClass().equals(SimpleScalar.class)) {
			return ((SimpleScalar) arg).getAsString();
		} else if(arg.getClass().equals(SimpleNumber.class)) {
			return ((SimpleNumber) arg).getAsNumber();
		} else if(arg.getClass().equals(SimpleDate.class)) {
			return ((SimpleDate) arg).getAsDate();
		}
		return arguments.get(index);
	}
	
	public Object getDefaultValue(List arguments, int index) throws TemplateModelException {
		Object arg = arguments.get(index);
		if(arg.getClass().equals(SimpleSequence.class)) {
			return "[]";
		} else if (arg.getClass().equals(StringModel.class)) {
			return "{}";
		} else if(arg.getClass().equals(SimpleScalar.class)) {
			return "";
		} else if(arg.getClass().equals(SimpleNumber.class)) {
			return 0;
		} else {
			return null;
		}
	}
	
}
