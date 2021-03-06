package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hibernate.Session;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.collections.CollectionUtils;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddToGroupTests extends TestBase {
    @BeforeMethod
    public void ensurePreconditions() {
        GroupData group = new GroupData().withName("test1").withHeader("test2").withFooter("test3");
        //Получаем список групп
        Groups groups = app.db().groups();
        //выбираем контакт из БД, который будем добавлять в группу
        ContactData contactBeforeAddingToGroup = app.db().contacts().iterator().next();
        //получаем список групп у этого контакта
        Groups beforeGroups = contactBeforeAddingToGroup.getGroups();
        //Проверка предусловия, что есть группа. Если группы нет, создаём её
        //Также проверяем, что контакт добавлен не во все группы
        if (groups.size() == 0 || groups.size() == beforeGroups.size()) {
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
        if (app.db().contacts().size() == 0) {
            create(contactData);
        }
    }

    @Test
    public void addContactToGroupTest() {
        //Получаем список всех групп
        Groups groups = app.db().groups();
        //выбираем контакт из БД, который будем добавлять в группу
        ContactData contactBeforeAddingToGroup = app.db().contacts().iterator().next();
        //получаем список групп у этого контакта
        Groups beforeGroups = contactBeforeAddingToGroup.getGroups();
        System.out.println("beforeGroups " + beforeGroups);
        //чтобы найти какую-нибудь группу, в которую не входит контакт, можно из "большего" списка удалить все элементы "меньшего" списка
        groups.removeAll(beforeGroups);
        GroupData groupForAdding = groups.stream().filter(g -> !contactBeforeAddingToGroup.getGroups().contains(g)).findFirst().get();
        System.out.println("groupForAdding is " + groupForAdding);
        //далее добавление контакта в группу
        app.contact().addToGroup(contactBeforeAddingToGroup, groupForAdding);
        //Получаем список групп у контакта после добавления
        Groups afterGroups = app.db().contact(contactBeforeAddingToGroup.getId()).getGroups();
        System.out.println("afterGroups are " + afterGroups);
        //Сравнение списка групп до и после добавления
        assertThat(afterGroups, equalTo(
                beforeGroups.withAdded(groupForAdding)));

        app.goTo().homePage();
    }


}
