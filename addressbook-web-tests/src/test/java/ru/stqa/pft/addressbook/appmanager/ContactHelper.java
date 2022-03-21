package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {

    public ContactHelper(FirefoxDriver wd) {
        super(wd);
    }

    public void fillContactForm(ContactData contactData) {
        type(By.name("firstname"), contactData.getName());
        type(By.name("middlename"), contactData.getMiddle_name());
        type(By.name("lastname"), contactData.getLast_name());
        type(By.name("nickname"), contactData.getNickname());
        type(By.name("title"), contactData.getTitle());
        type(By.name("company"), contactData.getCompany());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHome());
        type(By.name("mobile"), contactData.getMobile());
        click(By.name("bday"));
        new Select(wd.findElement(By.name("bday"))).selectByVisibleText(contactData.getDate());
        click(By.xpath("//option[@value='" + contactData.getDate() + "']"));
        click(By.name("bmonth"));
        new Select(wd.findElement(By.name("bmonth"))).selectByVisibleText(contactData.getMonth());
        click(By.xpath("//option[@value='" + contactData.getMonth() + "']"));
        type(By.name("byear"), contactData.getYear());
    }

    public void initContactCreation() {
        wd.findElement(By.linkText("add new")).click();
    }
}
