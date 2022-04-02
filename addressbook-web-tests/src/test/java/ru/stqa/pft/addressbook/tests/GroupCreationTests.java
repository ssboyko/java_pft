package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() throws Exception {
        app.getNavigationHelper().goToGroupPage();
        List<GroupData> before = app.getGroupsHelper().getGroupList();
        app.getGroupsHelper().initGroupCreation();
        GroupData group = new GroupData("test1", null, null);
        app.getGroupsHelper().fillGroupForm(group);
        app.getGroupsHelper().submitGroupCreation();
        app.getNavigationHelper().goToGroupPage();
        List<GroupData> after = app.getGroupsHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size() +1);
        before.add(group);
        Comparator<? super GroupData> byId = (g1,g2) -> Integer.compare(g1.getId(),g2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
        app.logout();
    }

}
