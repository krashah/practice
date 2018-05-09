package com.capgemini.cobigen.eclipse.test.common;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotPerspective;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import com.capgemini.cobigen.eclipse.test.common.junit.TmpMavenProjectRule;
import com.capgemini.cobigen.eclipse.test.common.utils.EclipseUtils;

/**
 * Abstract test implementation providing the commonly used setup and tear down methods as well as JUnit rules
 * and resets the SWTBot accordingly.
 */
public abstract class SystemTest {

    /** Rule for creating temporary {@link IJavaProject}s per test. */
    @Rule
    public TmpMavenProjectRule tmpMavenProjectRule = new TmpMavenProjectRule();

    /** Rule for creating temporary files and folders */
    @Rule
    public TemporaryFolder tmpFolderRule = new TemporaryFolder();

    /** {@link SWTBot} for UI controls */
    protected static SWTWorkbenchBot bot = new SWTWorkbenchBot();

    /**
     * Setup workbench appropriately for tests
     * @throws Exception
     *             test fails
     */
    @BeforeClass
    public static void setupTest() throws Exception {
        try {
            bot.viewByTitle("Welcome").close();
        } catch (WidgetNotFoundException e) {
            // ignore
        }
        bot.resetWorkbench();
        EclipseUtils.cleanWorkspace(true);

        SWTBotPerspective perspective = bot.perspectiveById(JavaUI.ID_PERSPECTIVE);
        perspective.activate();

        // this flag is set to be true and will suppress ErrorDialogs,
        // which is completely strange, so we enable them again.
        ErrorDialog.AUTOMATED_MODE = false;
    }

    /**
     * Reset workbench and open java perspective
     * @throws Exception
     *             test fails
     */
    @Before
    public void beforeTest() throws Exception {
        bot.resetWorkbench();
        EclipseUtils.cleanWorkspace(false);

        SWTBotPerspective perspective = bot.perspectiveById(JavaUI.ID_PERSPECTIVE);
        perspective.activate();
    }
}
