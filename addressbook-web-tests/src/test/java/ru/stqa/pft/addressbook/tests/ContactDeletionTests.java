package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        if (!app.getContactHelper().isThereAContact()) {
            if(!app.getContactHelper().isThereAGroupAtContactCreationForm()) {
                app.getNavigationHelper().goToGroupPage();
                app.getGroupsHelper().createGroup(new GroupData("test1", null, null));
            }
            app.getContactHelper().createContact(new ContactData("name", "middle name", "last name", "nickname", "title", "company", "address", "home", "+79998885544", "1", "February", "1998", "test1"));
        }
        app.getContactHelper().selectFirstContact();
        app.getContactHelper().deleteContact();
        app.wd.switchTo().alert().accept();
    }

}
