package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {
    @Test
    public void testContactModification() {
        ContactData contactData = new ContactData("name", "middle name", "last name", "nickname", "title", "company", "address", "home", "+79998885544", "1", "February", "1998", "test1");
        GroupData groupData = new GroupData(contactData.getGroup(), null, null);
        if (!app.getContactHelper().isThereAContact()) {
            createContact(groupData, contactData);
        }
        //получили коллекцию контактов перед модификацией
        List<ContactData> before = app.getContactHelper().getContactList();
        //выбрали последний контакт для модификации
        int indexOfContact = before.size()-1;
        app.getContactHelper().selectContact(indexOfContact);
        app.getContactHelper().initContactModification(before.get(indexOfContact).getId());
        //создали модельный объект и наполнили его данными для модификации, присвоив ему ID изменяемого контакта (последний контакт)
        ContactData contactData2 = new ContactData(before.get(indexOfContact).getId(),"name", "middle name", "last name", "nickname", "title", "company", "address", "home", "+79998885544", "1", "February", "1998", "test1");
        //заполнили форму данными из модельного объекта
        app.getContactHelper().fillContactForm(contactData2, false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToHomePage();
        //получили коллекцию контактов после модификации
        List<ContactData> after = app.getContactHelper().getContactList();
        //сравнили их размер
        Assert.assertEquals(after.size(), before.size());
        //удалили последний элемент первоначальной коллекции контактов, до модификации
        before.remove(indexOfContact);
        //добавили в первоначальную коллекцию контактов изменённый контакт, id его не изменился
        before.add(contactData2);
        //реализовали компаратор для сравнения по id двух коллекций, до и после модификации
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(),c2.getId());
        //упорядочили их по id
        before.sort(byId);
        after.sort(byId);
        //и сравнили их, в методе Equals класса ContactData сравнение ведётся по полям name, last_name
        Assert.assertEquals(before, after);
    }
}
