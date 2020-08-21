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
package net.sf.efhnbm.options.spi;

import org.netbeans.spi.options.AdvancedOption;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.NbBundle;

/**
 * spi option category.
 *
 * @author alessandro negrin
 * @version $Id$
 */
public class EFHOptionCategory extends AdvancedOption {

    /**
     * Creates a new instance of EFHOptionCategory.
     */
    public EFHOptionCategory() {
        super();
    }

    @Override
    @NbBundle.Messages("EFHOptionCategory.displayName=Explore from here settings")
    public String getDisplayName() {
        return Bundle.EFHOptionCategory_displayName();
    }

    @Override
    @NbBundle.Messages("EFHOptionCategory.tooltip=Explore from here settings")
    public String getTooltip() {
        return Bundle.EFHOptionCategory_tooltip();
    }

    @Override
    public OptionsPanelController create() {
        return new EFHPanelController();
    }

}
