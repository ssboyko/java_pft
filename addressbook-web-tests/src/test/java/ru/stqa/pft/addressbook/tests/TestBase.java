package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class TestBase {

    protected static final ApplicationManager app = new ApplicationManager(BrowserType.CHROME);

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite
    public void tearDown() throws Exception {
        app.stop();
    }

    public void createGroupIfItNotExists(GroupData groupData) {
        if (!app.getContactHelper().isThereAGroupAtContactCreationForm()) {
            app.goTo().groupPage();
            app.group().create(groupData);
        }
    }

    public void createContact(GroupData groupData, ContactData contactData) {
        app.getContactHelper().initContactCreation();
        createGroupIfItNotExists(groupData);
        app.getContactHelper().createContact(contactData);
    }
}

