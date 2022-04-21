package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.openqa.selenium.remote.BrowserType.CHROME;

public class TestBase {

    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", CHROME));

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite
    public void tearDown() throws Exception {
        app.stop();
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

