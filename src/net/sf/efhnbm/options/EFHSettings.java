/*
 *                          Sun Public License Notice
 *
 * The contents of this file are subject to the Sun Public License Version
 * 1.0 (the "License"). You may not use this file except in compliance with
 * the License. A copy of the License is available at http://www.sun.com/
 *
 * The Original Code is the "Explore from here" NetBeans Module.
 * The Initial Developer of the Original Code is alessandro negrin.
 * Portions created by alessandro negrin are Copyright (C) 2005.
 * All Rights Reserved.
 *
 * Contributor(s): alessandro negrin.
 *
 */
package net.sf.efhnbm.options;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import net.sf.efhnbm.ExploreFromHere;
import net.sf.efhnbm.LaunchersFactory;
import net.sf.efhnbm.utils.Utils;
import org.openide.util.HelpCtx;
import org.openide.util.NbPreferences;
import org.openide.util.SharedClassObject;

/**
 * module settings
 *
 * @author alessandro negrin
 * @version $Id$
 */
public final class EFHSettings extends SharedClassObject {

    public static final String PROP_OPTION = "option"; // NOI18N
    public static final String PROP_OPTION_BUNDLE = "bundle"; // NOI18N
    public static final String PROP_OPTION_CLASS = "class"; // NOI18N
    public static final String PROP_OPTION_COMMAND = "command"; // NOI18N
    public static final String PROP_LAUNCHER_CLASS = "launcher.class"; // NOI18N
    public static final String PROP_COMMAND_EXPLORE = "command.explore";// NOI18N
    public static final String PROP_COMMAND_SELECT = "command.select"; // NOI18N
    private static final EFHSettings INSTANCE = SharedClassObject.findObject(EFHSettings.class, true);
    private static final long serialVersionUID = 588225648352926127L;

    /**
     * Creates a new instance of EFHSettings.
     */
    private EFHSettings() {
    }

    public static EFHSettings getInstance() {
        return INSTANCE;
    }

    private void putPreference(String key, String value) throws BackingStoreException {
        Preferences prefs = NbPreferences.forModule(ExploreFromHere.class);
        if (prefs == null || key == null || value == null) {
            //do nothing
        } else {
            prefs.put(key, value);
            prefs.flush();//we have only 4 prefs, there's no reason to worry about performances :)
        }
    }

    private String getPreference(String key, String defaultValue) {
        Preferences prefs = NbPreferences.forModule(ExploreFromHere.class);
        if (prefs == null) {
            return defaultValue;
        } else {
            return prefs.get(key, defaultValue);
        }
    }

    /**
     * init props.
     */
    @Override
    protected void initialize() {
        super.initialize();

        putProperty(PROP_COMMAND_EXPLORE, getPreference(PROP_COMMAND_EXPLORE, "rundll32 url.dll,FileProtocolHandler {0}"), true); // NOI18N
        putProperty(PROP_LAUNCHER_CLASS, getPreference(PROP_LAUNCHER_CLASS, "net.sf.efhnbm.launchers.Win32Launcher"), true); // NOI18N
        putProperty(PROP_OPTION, getPreference(PROP_OPTION, PROP_OPTION_BUNDLE), true);

        putProperty(PROP_COMMAND_SELECT, getPreference(PROP_COMMAND_SELECT, "explorer /e,/select,{0}"), true); // NOI18N
    }

    /**
     * save the state.
     */
    private void save() {
        try {
            putPreference(PROP_COMMAND_EXPLORE, getCommandExplore());
            putPreference(PROP_LAUNCHER_CLASS, getLauncherClass());
            putPreference(PROP_OPTION, getOption());
            putPreference(PROP_COMMAND_SELECT, getCommandSelect());
        } catch (BackingStoreException bse) {
            Utils.showErrorMessage("can't save"); // NOI18N
        }
    }

    /**
     * serialize options.
     *
     * @param out
     * @throws java.io.IOException
     */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
    }

    /**
     * deserialize options.
     *
     * @param in
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
    }

    /**
     * get help context for action.
     *
     * @return the help context (default)
     */
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    public String getOption() {
        return (String) getProperty(PROP_OPTION);
    }

    public void setOption(String option) {
        putProperty(PROP_OPTION, option, true);
    }

    public String getLauncherClass() {
        return (String) getProperty(PROP_LAUNCHER_CLASS);
    }

    public void setLauncherClass(String launcherClass) {
        putProperty(PROP_LAUNCHER_CLASS, launcherClass, true);
    }

    public String getCommandExplore() {
        return (String) getProperty(PROP_COMMAND_EXPLORE);
    }

    public void setCommandExplore(String command) {
        putProperty(PROP_COMMAND_EXPLORE, command, true);
    }

    public String getCommandSelect() {
        return (String) getProperty(PROP_COMMAND_SELECT);
    }

    public void setCommandSelect(String command) {
        putProperty(PROP_COMMAND_SELECT, command, true);
    }

    /**
     * is a property has changed launchers factory must be reset.
     *
     * @param name
     * @param oldValue
     * @param newValue
     */
    @Override
    protected void firePropertyChange(String name, Object oldValue, Object newValue) {
        super.firePropertyChange(name, oldValue, newValue);
        save();
        LaunchersFactory.getInstance().reset();
    }

    /**
     * fake propertychange to fire save.
     */
    public void firePropertiesHaveBeenChanged() {
        firePropertyChange(PROP_OPTION, null, getOption());
    }
}
