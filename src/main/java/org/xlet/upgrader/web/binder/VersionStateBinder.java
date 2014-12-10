package org.xlet.upgrader.web.binder;

import org.xlet.upgrader.domain.VersionState;

import java.beans.PropertyEditorSupport;

/**
 * Creator: JimmyLin
 * DateTime: 14-11-3 下午4:45
 * Summary:
 */
public class VersionStateBinder extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        VersionState versionState = VersionState.valueOf(text.toUpperCase());
        super.setValue(versionState);
    }
}
