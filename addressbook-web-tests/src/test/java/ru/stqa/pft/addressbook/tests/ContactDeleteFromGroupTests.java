package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;

public class ContactDeleteFromGroupTests extends TestBase{
    @BeforeMethod
    public void ensurePreconditions() {
        //Проверка предусловия, что есть группа. Если группы нет, создаём её
        GroupData group = new GroupData().withName("test1").withHeader("test2").withFooter("test3");
        Groups groups = app.db().groups();
        if (groups.size() == 0) {
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
    public void deleteGroupFromContact () {
        //Получаем список всех групп
        Groups groups = app.db().groups();
        //выбираем контакт из БД, который будем добавлять в группу
        ContactData contactBeforeDeleteToGroup = app.db().contacts().iterator().next();
        //получаем список групп у этого контакта
        Groups beforeGroups = contactBeforeDeleteToGroup.getGroups();
        System.out.println("beforeGroups " + beforeGroups);
        //проверяем, есть ли группы у контакта, чтобы можно было удалить контакт из группы
        if(beforeGroups.size() > 0) {
            //выбираем группу для удаления
            GroupData groupForRemove = beforeGroups.stream().iterator().next();
            //удаляем группу из контакта
            app.contact().deleteFromGroup(contactBeforeDeleteToGroup, groupForRemove);
            //проверяем список групп после удаления
            Groups afterGroups = app.db().contact(contactBeforeDeleteToGroup.getId()).getGroups();
            //Сравнение списка групп до и после добавления
            MatcherAssert.assertThat(afterGroups, CoreMatchers.equalTo(
                    beforeGroups.without(groupForRemove)));
        }
        //иначе контакт не состоит ни в одной из групп, поэтому, нужно его добавить
        else {
            //выбираем группу для удаления - > на этот раз, из общего списка групп рандомную, т.к контакт
            // не состоит ни в одной из групп
            GroupData groupForRemove = groups.stream().iterator().next();
            //добавляем контакт в группу
            app.contact().addToGroup(contactBeforeDeleteToGroup, groupForRemove);
            //удаляем контактиз группы
            app.contact().deleteFromGroup(contactBeforeDeleteToGroup, groupForRemove);
            //далее получаем список групп у контакта
            Groups afterGroups = app.db().contact(contactBeforeDeleteToGroup.getId()).getGroups();
            //далее проверка, что список групп после удаления снова пуст
            MatcherAssert.assertThat(afterGroups.size(),CoreMatchers.equalTo(0));
        }

    }
}
