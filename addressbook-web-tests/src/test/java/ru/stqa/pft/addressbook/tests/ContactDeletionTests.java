package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        GroupData groupData = new GroupData("test1", null, null);
        ContactData contactData = new ContactData("name", "middle name", "last name", "nickname", "title", "company", "address", "home", "+79998885544", "1", "February", "1998", "test1");
        if(!app.getContactHelper().isThereAContact()){
            createContact(groupData,contactData);
        }
        app.getContactHelper().selectFirstContact();
        app.getContactHelper().deleteContact();
        app.wd.switchTo().alert().accept();
    }

}
