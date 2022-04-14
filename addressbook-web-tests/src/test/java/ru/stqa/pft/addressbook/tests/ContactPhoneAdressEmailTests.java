package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneAdressEmailTests extends TestBase{
    @BeforeMethod
    public void ensurePreconditions() {
        ContactData contactData = new ContactData()
                .withName("name").withMiddle_name("middle name").withLast_name("last name").withNickname("nickname").withTitle("title").withCompany("company")
                .withAddress("Some City, some Street, house 23, flat 12").withHomePhone("5555555").withMobile("+(7999)8885544").withWorkPhone("9-876-54321").withFax("+7(913)231-53-23")
                .withEmail("test@yandex.ru").withEmail2("123-test-123@gmail.com").withEmail3("dot.dot@email.ru").withDate("1").withMonth("February")
                .withYear("1998").withGroup("test1");
        GroupData groupData = new GroupData().withName(contactData.getGroup());
        if (app.contact().all().size() == 0) {
            create(groupData, contactData);
        }
    }

    @Test
    public void testContactPhones(){
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        System.out.println("All phones are: " + contact.getAllPhones());
        System.out.println("Merged phones: " + mergePhones(contactInfoFromEditForm));
        assertThat((contact.getAllPhones()), equalTo(mergePhones(contactInfoFromEditForm)));
        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
        assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(),contact.getMobilePhone(),contact.getWorkPhone())
                .stream().filter((s)-> !s.equals(""))
                .map(ContactPhoneAdressEmailTests::cleanedPhones)
                .collect(Collectors.joining("\n"));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(),contact.getEmail2(),contact.getEmail3())
                .stream().filter((s)-> !s.equals(""))
                .collect(Collectors.joining("\n"));
    }

    public static String cleanedPhones (String phone) {
        return phone.replaceAll("\\s","").replaceAll("[-()]","");
    }

}
