package ru.stqa.pft.addressbook.tests;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {
    @DataProvider
    public Iterator<Object[]> validContactsFromJson() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
            String json = "";
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
            }.getType());
            return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
        }
    }

    @Test(dataProvider = "validContactsFromJson")
    public void testContactCreation(ContactData contactData) throws Exception {
        GroupData group = new GroupData().withName("test1").withHeader("test2").withFooter("test3");
        Groups groups = app.db().groups();
        File photo = new File("src/test/resources/photo.jpeg");
        //реализовать предусловие - если нет никакой группы, то нужно её создать
        if(groups.size() == 0) {
            app.goTo().groupPage();
            app.group().create(group);
            app.goTo().groupPage();
        }
        contactData.withPhoto(photo).inGroup(groups.iterator().next());

        GroupData groupData = new GroupData().withName(contactData.getGroups().iterator().next().getName());
        Contacts before = app.db().contacts();
        create(groupData, contactData);
        Contacts after = app.db().contacts();
        assertThat(after.size(), equalTo(before.size() + 1));

        assertThat(after, equalTo(
                before.withAdded(contactData.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))
        ));
    }

    @Ignore
    @Test
    public void testCurrentDir() {
        File currentDir = new File(".");
        System.out.println(currentDir.getAbsolutePath());
        File photo = new File("src/test/resources/photo.jpg");
        System.out.println(photo.getAbsolutePath());
        System.out.println(photo.exists());
    }
}




