package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.awt.*;

public class ContactModificationTests extends TestBase {
    @Test
    public void testContactModification() {
        ContactData contactData = new ContactData("name", "middle name", "last name", "nickname", "title", "company", "address", "home", "+79998885544", "1", "February", "1998", "test1");
        GroupData groupData = new GroupData("test1", null, null);
        if (!app.getContactHelper().isThereAContact()) {
            createContact(groupData, contactData);
        }
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(contactData, false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToHomePage();
    }
}
