package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;

public class ContactModificationTests extends TestBase {
    @BeforeMethod
    public void ensurePreconditions() {
        File photo = new File("src/test/resources/photo.jpeg");
        ContactData contactData = new ContactData()
                .withName("name").withMiddle_name("middle name").withLast_name("last name").withNickname("nickname").withTitle("title").withCompany("company")
                .withAddress("Some City, some Street, house 23, flat 12").withHomePhone("5555555").withMobile("+(7999)8885544").withWorkPhone("9-876-54321").withPhone2("+7(913)231-53-23")
                .withEmail("test@yandex.ru").withEmail2("123-test-123@gmail.com").withEmail3("dot.dot@email.ru").withDate("1").withMonth("February")
                .withYear("1998").withGroup("test1").withPhoto(photo);
        GroupData groupData = new GroupData().withName(contactData.getGroup());
        if (app.db().contacts().size() == 0) {
            app.goTo().homePage();
            create(groupData, contactData);
        }
    }

    @Test
    public void testContactModification() {
        File photo = new File("src/test/resources/photo.jpeg");
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contactData2 = new ContactData()
                .withId(modifiedContact.getId()).withName("name").withMiddle_name("middle name").withLast_name("last name").withNickname("nickname")
                .withTitle("title").withCompany("company").withAddress("address").withHomePhone("home").withMobile("+79998885544").withDate("1").withMonth("February")
                .withYear("1998").withGroup("test1").withPhoto(photo);
        app.goTo().homePage();
        for(ContactData c: before){
            System.out.println("Before is " + c);
        }
        app.contact().modify(contactData2);
        Contacts after = app.db().contacts();
        for(ContactData c: after){
            System.out.println("After is " + c);
        }
        Assert.assertEquals(after.size(), before.size());
        before.remove(modifiedContact);
        before.add(contactData2);
        Assert.assertEquals(before, after);
        MatcherAssert.assertThat(after, CoreMatchers.equalTo(before.without(modifiedContact).withAdded(contactData2)));
    }
}
