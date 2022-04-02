package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class TestBase {

    protected final ApplicationManager app = new ApplicationManager(BrowserType.CHROME);

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
    }

    public void createGroupIfItNotExists(GroupData groupData) {
        if (!app.getContactHelper().isThereAGroupAtContactCreationForm()) {
            app.getNavigationHelper().goToGroupPage();
            app.getGroupsHelper().createGroup(groupData);
        }
    }

    public void createContact(GroupData groupData, ContactData contactData) {
        if (!app.getContactHelper().isThereAGroupAtContactCreationForm()) {
            createGroupIfItNotExists(groupData);
        }
        app.getContactHelper().createContact(contactData);
    }
}

