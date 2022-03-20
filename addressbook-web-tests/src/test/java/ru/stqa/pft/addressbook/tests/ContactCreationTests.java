package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase{


    @Test
    public void testContactCreation() throws Exception {
        app.initContactCreation();
        app.fillContactForm(new ContactData("name", "middle name", "last name", "nickname", "title", "company", "address", "home", "+79998885544", "1", "February", "1998"));
        app.submit();
        app.getGroupsHelper().returnToHomePage();
        app.logout();
    }







}



