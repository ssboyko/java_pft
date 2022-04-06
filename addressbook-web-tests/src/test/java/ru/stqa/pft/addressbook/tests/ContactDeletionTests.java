package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        ContactData contactData = new ContactData("name", "middle name", "last name", "nickname", "title", "company", "address", "home", "+79998885544", "1", "February", "1998", "test1");
        GroupData groupData = new GroupData(contactData.getGroup(), null, null);
        if(!app.getContactHelper().isThereAContact()){
            createContact(groupData,contactData);
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        for (ContactData data : before) {
            System.out.println("before" + data);
        }
        app.getContactHelper().selectContact(before.size()-1);
        app.getContactHelper().deleteContact();
        app.wd.switchTo().alert().accept();
        app.getContactHelper().returnToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        for (ContactData data : after) {
            System.out.println("after" + data);
        }
        Assert.assertEquals(after.size(), before.size() - 1);
        before.remove(before.size()-1);
        Assert.assertEquals(before,after);
    }

}
