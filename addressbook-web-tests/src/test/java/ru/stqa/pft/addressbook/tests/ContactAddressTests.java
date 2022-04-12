package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTests extends TestBase{
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
    public void testContactAddresses(){
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
    }
}
