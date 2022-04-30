package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ContactsHelper extends HelperBase {
    public ContactsHelper(WebDriver wd) {
        super(wd);
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getName());
        type(By.name("middlename"), contactData.getMiddle_name());
        type(By.name("lastname"), contactData.getLast_name());
        attach(By.name("photo"), contactData.getPhoto());
        type(By.name("nickname"), contactData.getNickname());
        type(By.name("title"), contactData.getTitle());
        type(By.name("company"), contactData.getCompany());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("phone2"), contactData.getPhone2());
        type(By.name("email"), contactData.getEmail());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());
        click(By.name("bday"));
        new Select(wd.findElement(By.name("bday"))).selectByVisibleText(contactData.getDate());
        click(By.xpath("//option[@value='" + contactData.getDate() + "']"));
        click(By.name("bmonth"));
        new Select(wd.findElement(By.name("bmonth"))).selectByVisibleText(contactData.getMonth());
        click(By.xpath("//option[@value='" + contactData.getMonth() + "']"));
        type(By.name("byear"), contactData.getYear());

        if (creation) {
            if( contactData.getGroups().size() > 0){
                Assert.assertTrue(contactData.getGroups().size() == 1);
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName()    );
            }

        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void initContactCreation() {
        wd.findElement(By.linkText("add new")).click();
    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[id='" + id + "']")).click();
    }

    public void deleteContact() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void initContactModification(int id) {
//        WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']",id)));
//        WebElement row = checkbox.findElement(By.xpath("./../.."));
//        List<WebElement> cells = row.findElements(By.tagName("td"));
//        cells.get(7).findElement(By.tagName("a")).click();

        //wd.findElement(By.xpath(String.format("//input[@value='%s']/../../td[8]/a",id))).click();
        //wd.findElement(By.xpath(String.format("//tr[.//input[@value='%s']]/td[8]/a",id))).click();
        click(By.xpath("//a[@href='edit.php?id=" + id + "']"));
    }

    public void clickAddToGroupButton(){
        click(By.name("add"));
    }

    public void submitContactModification() {
        click(By.xpath("//div[@id='content']/form/input[22]"));
    }

    public void returnToHomePage() {
        click(By.linkText("home"));
    }

    public void submitContactCreation() {
        click(By.name("submit"));
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public boolean isThereAGroupAtContactCreationForm(String groupName) {
        List<WebElement> options = new Select(wd.findElement(By.name("new_group"))).getOptions();
        return options.stream().anyMatch(webElement -> webElement.getText().equals(groupName));
    }

    public List getGroupsNamesAtMainPaige() {
        List<WebElement> options = new Select(wd.findElement(By.name("to_group"))).getOptions();
        List<String> groupNamesInSelectOfGroups = new ArrayList<>();
        for (WebElement e: options){
            groupNamesInSelectOfGroups.add(e.getText());
        }
        return groupNamesInSelectOfGroups;
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        }
        contactCache = new Contacts();
        List<WebElement> elements = wd.findElements(By.xpath("//*[@id='maintable']/tbody/tr[@name = 'entry']"));
        for (WebElement element : elements) {
            List<WebElement> cells = element.findElements(By.tagName("td"));
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            String lastname = cells.get(1).getText();
            String name = cells.get(2).getText();
            String address = cells.get(3).getText();
            String allEmails = cells.get(4).getText();
            String allPhones = cells.get(5).getText();
            contactCache.add(new ContactData().withId(id).withName(name).withLast_name(lastname).withAddress(address).withAllPhones(allPhones).withAllEmails(allEmails));
        }
        return contactCache;
    }

    public void createContact(ContactData contactData) {
        initContactCreation();
        fillContactForm(contactData, true);
        submitContactCreation();
        contactCache = null;
        //returnToHomePage();
    }

    public void modify(ContactData contactData) {
        selectContactById(contactData.getId());
        initContactModification(contactData.getId());
        fillContactForm(contactData, false);
        submitContactModification();
        contactCache = null;
        returnToHomePage();
    }

    public void addToGroup(ContactData contactData, GroupData group) {
        selectContactById(contactData.getId());
        new Select(wd.findElement(By.name("to_group"))).selectByValue(String.valueOf(group.getId()));
        //click(By.xpath("//option[@value=" + group.getId() + "]"));
        clickAddToGroupButton();
        returnToHomePage();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteContact();
        wd.switchTo().alert().accept();
        contactCache = null;
        returnToHomePage();
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModification(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String homePhone = wd.findElement(By.name("home")).getAttribute("value");
        String mobilePhone = wd.findElement(By.name("mobile")).getAttribute("value");
        String workPhone = wd.findElement(By.name("work")).getAttribute("value");
        String faxPhone = wd.findElement(By.name("phone2")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        String email1 = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");

        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withName(firstname).withLast_name(lastname).withAddress(address)
                .withHomePhone(homePhone).withMobile(mobilePhone).withWorkPhone(workPhone).withPhone2(faxPhone).withEmail(email1)
                .withEmail2(email2).withEmail3(email3);
    }
}
