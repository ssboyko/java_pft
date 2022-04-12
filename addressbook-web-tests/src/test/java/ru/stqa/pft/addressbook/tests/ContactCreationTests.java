package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {
    @Test
    public void testContactCreation() throws Exception {
        ContactData contactData = new ContactData()
                .withName("name").withMiddle_name("middle name").withLast_name("last name").withNickname("nickname").withTitle("title").withCompany("company")
                .withAddress("Some City, some Street, house 23, flat 12").withHomePhone("123456").withMobile("+79998885544").withWorkPhone("987654321")
                .withEmail("test@yandex.ru").withEmail2("123-test-123@gmail.com").withEmail3("dot.dot@email.ru").withDate("1").withMonth("February")
                .withYear("1998").withGroup("test1");
        GroupData groupData = new GroupData().withName(contactData.getGroup());
        Contacts before = app.contact().all();
        create(groupData, contactData);
        Contacts after = app.contact().all();
        assertThat(after.size(), equalTo(before.size() + 1));

        assertThat(after,equalTo(
                before.withAdded(contactData.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))
        ));
    }
}




