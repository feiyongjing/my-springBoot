package com.github.feiyongjing.service.spring.common.util;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;

public class ObjectUtil {
    /**
     * 将String转换为目标类型
     *
     * @param targetType 目标类型
     * @param s          string
     * @throws NumberFormatException 当string到number时，如果string不是数字，则抛出NumberFormatException
     */
    public static Object convert(Class<?> targetType, String s) {
        PropertyEditor editor = PropertyEditorManager.findEditor(targetType);
        editor.setAsText(s);
        return editor.getValue();
    }
}
