package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactModificationTests extends TestBase {
    @BeforeMethod
    public void ensurePreconditions() {
        ContactData contactData = new ContactData()
                .withName("name").withMiddle_name("middle name").withLast_name("last name").withNickname("nickname").withTitle("title").withCompany("company")
                .withAddress("Some City, some Street, house 23, flat 12").withHomePhone("123456").withMobile("+79998885544").withWorkPhone("987654321")
                .withEmail("test@yandex.ru").withEmail2("123-test-123@gmail.com").withEmail3("dot.dot@email.ru").withDate("1").withMonth("February")
                .withYear("1998").withGroup("test1");
        GroupData groupData = new GroupData().withName(contactData.getGroup());
        if (app.contact().all().size() == 0) {
            create(groupData, contactData);
        }
    }

    @Test
    public void testContactModification() {
        Contacts before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contactData2 = new ContactData()
                .withId(modifiedContact.getId()).withName("name").withMiddle_name("middle name").withLast_name("last name").withNickname("nickname")
                .withTitle("title").withCompany("company").withAddress("address").withHomePhone("home").withMobile("+79998885544").withDate("1").withMonth("February")
                .withYear("1998").withGroup("test1");
        app.contact().modify(before, contactData2);
        Contacts after = app.contact().all();
        Assert.assertEquals(after.size(), before.size());
        before.remove(modifiedContact);
        before.add(contactData2);
        Assert.assertEquals(before, after);
        MatcherAssert.assertThat(after, CoreMatchers.equalTo(before.without(modifiedContact).withAdded(contactData2)));
    }
}
