package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {
    @DataProvider
    public Iterator<Object[]> validGroups() {
        List<Object[]> list = new ArrayList<Object[]>();
        list.add(new Object[]{"test1, header 1", "footer 1"});
        list.add(new Object[]{"test2, header 2", "footer 2"});
        list.add(new Object[]{"test3, header 3", "footer 3"});
        return list.iterator();
    }

    @Test(dataProvider = "validGroups")
    public void testGroupCreation(String name, String header, String footer) throws Exception {
        GroupData group = new GroupData().withName(name).withHeader(header).withFooter(footer);
        System.out.println(group);
//        app.goTo().groupPage();
//        Groups before = app.group().all();
//        app.group().initGroupCreation();
//        app.group().fillGroupForm(group);
//        app.group().submitGroupCreation();
//        app.goTo().groupPage();
//        assertThat(app.group().count(),equalTo(before.size() + 1));
//        Groups after = app.group().all();
//        assertThat(after, equalTo(
//                before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
//        app.logout();
    }

    @Test
    public void testBadGroupCreation() throws Exception {
        app.goTo().groupPage();
        Groups before = app.group().all();
        app.group().initGroupCreation();
        GroupData group = new GroupData().withName("test2'");
        app.group().fillGroupForm(group);
        app.group().submitGroupCreation();
        app.goTo().groupPage();
        assertThat(app.group().count(), equalTo(before.size()));
        Groups after = app.group().all();
        assertThat(after, equalTo(before));
        app.logout();
    }

}
