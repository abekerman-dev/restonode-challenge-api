package com.truenorth.restonode.exception;

import java.util.List;

import com.truenorth.restonode.model.Resource;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends Exception {

	public ResourceNotFoundException(Class<? extends Resource> clazz, Long id) {
		super(createExceptionMessage(clazz.getSimpleName(), id.toString(), true));
	}

	public ResourceNotFoundException(Class<? extends Resource> clazz, List<Long> idList) {
		super(createExceptionMessage(clazz.getSimpleName(), idList.toString(), false));
	}

	private static String createExceptionMessage(String className, String idOrIds, boolean singleId) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Resource ");
		stringBuilder.append(className);
		stringBuilder.append(" not found given ID" + (singleId ? "" : "s") + "=");
		stringBuilder.append(idOrIds);

		return stringBuilder.toString();
	}

}
