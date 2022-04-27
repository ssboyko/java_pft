package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddToGroupTests extends TestBase {
    @BeforeMethod
    public void ensurePreconditions() {
        //Проверка предусловия, что есть группа. Если группы нет, создаём её
        GroupData group = new GroupData().withName("test1").withHeader("test2").withFooter("test3");
        Groups groups = app.db().groups();
        if(groups.size() == 0) {
            app.goTo().groupPage();
            app.group().create(group);
            app.goTo().groupPage();
        }

        //Проверка предусловия, что есть контакт. Если контакта нет, создаём его
        File photo = new File("src/test/resources/photo.jpeg");
        ContactData contactData = new ContactData()
                .withName("name").withMiddle_name("middle name").withLast_name("last name").withNickname("nickname").withTitle("title").withCompany("company")
                .withAddress("Some City, some Street, house 23, flat 12").withHomePhone("5555555").withMobile("+(7999)8885544").withWorkPhone("9-876-54321").withPhone2("+7(913)231-53-23")
                .withEmail("test@yandex.ru").withEmail2("123-test-123@gmail.com").withEmail3("dot.dot@email.ru").withDate("1").withMonth("February").withPhoto(photo)
                .withYear("1998");
        GroupData groupData = new GroupData().withName("test1").withHeader("test2").withFooter("test3");
        if (app.db().contacts().size() == 0) {
            create(groupData, contactData);
        }

    }


    @Test
    public void addContactToGroup () {
        //выбираем контакт из БД, который будем добавлять в группу
        ContactData contactBeforeAddingToGroup = app.db().contacts().iterator().next();
        //получаем список групп у этого контакта
        Groups contactGroupsBeforeAddingToGroup = contactBeforeAddingToGroup.getGroups();
        //Смотрим, какие группы у него есть до добавления
        System.out.println(">>>> contactBeforeAddingToGroup is " + contactBeforeAddingToGroup + "and it has groups " + contactGroupsBeforeAddingToGroup);
        //далее добавление контакта в группу
        app.contact().addToGroup(contactBeforeAddingToGroup);
        //Смотрим, какие группы у контакта после добавления
        //Groups ContactGroupsAfterAddingToGroup = contactBeforeAddingToGroup.getGroups();
        //System.out.println("contactBeforeAddingToGroup after adding has groups " + ContactGroupsAfterAddingToGroup);

        //здесь какая-то проверка, что списки групп у контакта до добавления и после не совпадают
        //Assert.assertNotEquals(ContactGroupsBeforeAddingToGroup, ContactGroupsAfterAddingToGroup);

        //ищем контакт после добавления
        int idContact = contactBeforeAddingToGroup.getId();
        ContactData contactInGroup = app.db().contacts().stream().filter(c -> c.getId() == idContact).findFirst().get();
        System.out.println(">>> contactBeforeAddingToGroup is " + contactBeforeAddingToGroup + " and contactInGroup is " + contactInGroup);

        //получаем его группы после добавления
        Groups contactGroupsAfterAddingToGroup = contactInGroup.getGroups();
        System.out.println(">>>>contactInGroup is " + contactInGroup + "and it has groups " + contactGroupsAfterAddingToGroup);
        app.goTo().homePage();

        MatcherAssert.assertThat(contactGroupsAfterAddingToGroup, CoreMatchers.equalTo(contactGroupsBeforeAddingToGroup.withAdded()));


    }
}
