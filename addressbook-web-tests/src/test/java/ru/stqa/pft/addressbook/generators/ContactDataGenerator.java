package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
    @Parameter(names = "-c", description = "Contact count")
    public int count;

    @Parameter(names = "-f", description = "Target file")
    public String file;

    @Parameter(names = "-d", description = "Data format")
    public String format;

    public static void main(String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);
        } catch (ParameterException ex) {
            jCommander.usage();
            return;
        }

        generator.run();
    }

    private void run() throws IOException {
        List<ContactData> contacts = generateContacts(count);
        if (format.equals("json")) {
            saveAsJson(contacts, new File(file));
        } else {
            System.out.println("Unrecognized format " + format);
        }

    }

    private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        Writer writer = new FileWriter(file);
        writer.write(json);
        writer.close();
    }

    private List<ContactData> generateContacts(int count) {
        List<ContactData> contacts = new ArrayList<ContactData>();
        for (int i = 0; i < count; i++) {
            contacts.add(new ContactData()
                    .withName(String.format("testName %s", i))
                    .withMiddle_name(String.format("testMiddleName %s", i))
                    .withLast_name(String.format("testLastName %s", i))
                    .withNickname(String.format("testNickname %s", i))
                    .withTitle(String.format("testTitle %s", i))
                    .withCompany(String.format("testCompany %s", i))
                    .withAddress(String.format("Some City, some Street, house 23, flat 1%s", i))
                    .withHomePhone(String.format("555555%s", i))
                    .withMobile(String.format("+(7999)888554%s", i))
                    .withWorkPhone(String.format("9-876-5432%s", i))
                    .withPhone2(String.format("+7(913)231-53-2%s", i))
                    .withEmail(String.format("test%s@yandex.ru ", i))
                    .withEmail2(String.format("123-test%s-123@gmail.com", i))
                    .withEmail3(String.format("dot.dot%s@email.ru", i))
                    .withDate("1")
                    .withMonth("February")
                    .withYear(String.format("199%s", i))
                    .withGroup("test1"));
        }
        return contacts;
    }
}