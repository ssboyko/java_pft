package ru.stqa.pft.addressbook.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.openqa.selenium.remote.BrowserType.CHROME;

public class TestBase {

    Logger logger = LoggerFactory.getLogger(GroupCreationTests.class);

    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", CHROME));

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
    }

    @BeforeMethod(alwaysRun = true)
    public void logTestStart(Method m, Object[] p){
        logger.info("Start test " + m.getName() + "with parameters " + Arrays.asList(p));
    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method m, Object[] p){
        logger.info("Stop test " + m.getName() + "with parameters " + Arrays.asList(p));
    }

    public void createGroupIfItNotExists(GroupData groupData) {
        if (!app.contact().isThereAGroupAtContactCreationForm(groupData.getName())) {
            app.goTo().groupPage();
            app.group().create(groupData);
        }
    }

    public void create(GroupData groupData, ContactData contactData) {
        app.contact().initContactCreation();
        createGroupIfItNotExists(groupData);
        app.contact().createContact(contactData);
        app.contact().returnToHomePage();
    }
}

