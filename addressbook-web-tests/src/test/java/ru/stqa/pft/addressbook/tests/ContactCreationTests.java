package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {
    @Test
    public void testContactCreation() throws Exception {
        ContactData contactData = new ContactData("name", "middle name", "last name", "nickname", "title", "company", "address", "home", "+79998885544", "1", "February", "1998", "test1");
        GroupData groupData = new GroupData("test1", null, null);
        //получили коллекцию контактов перед созданием контакта
        List<ContactData> before = app.getContactHelper().getContactList();
        createContact(groupData, contactData);
        //получили коллекцию контактов после создания контакта
        List<ContactData> after = app.getContactHelper().getContactList();
        //Сравнили коллекции, после добавления коллекция стала больше на 1 добавленный контакт
        Assert.assertEquals(after.size(), before.size() +1);
        //добавили в первоначальную коллекцию контактов добавленный контакт
        before.add(contactData);
        //реализовали компаратор для сравнения по id двух коллекций, до и после модификации
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(),c2.getId());
        //упорядочили их по id
        before.sort(byId);
        after.sort(byId);
        //и сравнили их, в методе Equals класса ContactData сравнение ведётся по полям name, last_name
        Assert.assertEquals(before, after);
        app.logout();
    }
    }




