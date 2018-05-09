package com.capgemini.cobigen.eclipse.wizard.generate.common;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.slf4j.MDC;

import com.capgemini.cobigen.eclipse.common.constants.InfrastructureConstants;
import com.capgemini.cobigen.eclipse.wizard.generate.model.SelectAttributesContentProvider;
import com.capgemini.cobigen.eclipse.wizard.generate.model.SelectAttributesLabelProvider;

/**
 * The {@link SelectAttributesPage} enables a specific generation of contents which should be shown in the
 * overview and detail pages
 */
public class SelectAttributesPage extends WizardPage {

    /**
     * All attributes retrieved from generation model
     */
    private Map<String, String> attributes;

    /**
     * {@link CheckboxTableViewer} displaying all attributes
     */
    private CheckboxTableViewer tableAttributes;

    /**
     * Creates a new page for selecting mandatory attributes
     * @param attributes
     *            to be displayed
     * @author mbrunnli (12.03.2013)
     */
    public SelectAttributesPage(Map<String, String> attributes) {
        super("Choose attributes displayed in the generated UI");
        this.attributes = attributes;
    }

    @Override
    public void createControl(Composite parent) {
        MDC.put(InfrastructureConstants.CORRELATION_ID, UUID.randomUUID().toString());

        Composite container = new Composite(parent, SWT.FILL);
        container.setLayout(new GridLayout());

        Label label = new Label(container, SWT.WRAP);
        label.setText("Every attribute selected will be displayed in the UI:");
        // label
        // .setText("Every attribute selected will be displayed as a column in the overview or search results
        // table:");

        tableAttributes = CheckboxTableViewer.newCheckList(container, SWT.BORDER | SWT.V_SCROLL);
        tableAttributes.setContentProvider(new SelectAttributesContentProvider());
        tableAttributes.setLabelProvider(new SelectAttributesLabelProvider());
        tableAttributes.setInput(attributes);
        tableAttributes.setAllChecked(true);
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.grabExcessVerticalSpace = true;
        gd.grabExcessHorizontalSpace = true;
        tableAttributes.getTable().setLayoutData(gd);

        // label = new Label(container, SWT.WRAP);
        // label
        // .setText("Every attribute selected will be displayed in the detail, creation or modification
        // dialog:");
        //
        // CheckboxTableViewer detailAttributes =
        // CheckboxTableViewer.newCheckList(container, SWT.BORDER | SWT.V_SCROLL);
        // detailAttributes.setContentProvider(new SelectAttributesContentProvider());
        // detailAttributes.setLabelProvider(new SelectAttributesLabelProvider());
        // detailAttributes.setInput(attributes);
        // detailAttributes.setAllChecked(true);
        // gd = new GridData(GridData.FILL_BOTH);
        // gd.grabExcessVerticalSpace = true;
        // gd.grabExcessHorizontalSpace = true;
        // detailAttributes.getTable().setLayoutData(gd);

        setControl(container);

        MDC.remove(InfrastructureConstants.CORRELATION_ID);
    }

    /**
     * Returns all unchecked attributes names
     * @return all unchecked attributes names
     * @author mbrunnli (21.03.2013)
     */
    public Set<String> getUncheckedAttributes() {
        Set<String> uncheckedAttributes = new HashSet<>();
        for (Entry<String, String> attr : attributes.entrySet()) {
            if (!tableAttributes.getChecked(attr)) {
                uncheckedAttributes.add(attr.getKey());
            }
        }
        return uncheckedAttributes;
    }

}
