package ru.stqa.pft.addressbook.model;

public class ContactData {
    private final String name;
    private final String middle_name;
    private final String last_name;
    private final String nickname;
    private final String title;
    private final String company;
    private final String address;
    private final String home;
    private final String mobile;
    private final String date;
    private final String month;
    private final String year;

    public ContactData(String name, String middle_name, String last_name, String nickname, String title, String company, String address, String home, String mobile, String date, String month, String year) {
        this.name = name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.nickname = nickname;
        this.title = title;
        this.company = company;
        this.address = address;
        this.home = home;
        this.mobile = mobile;
        this.date = date;
        this.month = month;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    public String getHome() {
        return home;
    }

    public String getMobile() {
        return mobile;
    }

    public String getDate() {
        return date;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }
}
