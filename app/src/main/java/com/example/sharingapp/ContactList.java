package com.example.sharingapp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ContactList {
    private ArrayList<Contact> contacts;
    private String FILENAME = "contacts.sav";

    public ContactList() {
        contacts = new ArrayList<Contact>();
    }

    public void setContacts(ArrayList<Contact> contact_list) { this.contacts = contact_list; }

    public ArrayList<Contact> getContacts() { return this.contacts; }

    public ArrayList<String> getAllUsernames() {
        ArrayList<String> contactsUsernames = new ArrayList<>();

        for (Contact c: this.contacts) {
            contactsUsernames .add(c.getUsername());
        }

        return contactsUsernames;
    }

    public void addContact(Contact contact) { this.contacts.add(contact); }

    public void deleteContact(Contact contact) { this.contacts.remove(contact); }

    public Contact getContact(int index) { return this.contacts.get(index); }

    public int getSize() { return this.contacts.size(); }

    public int getIndex(Contact contact) {
        int pos = 0;
        for (Contact c : this.contacts) {
            if (contact.getId().equals(c.getId())) {
                return pos;
            }
            pos = pos + 1;
        }
        return -1;
    }

    public boolean hasContact(Contact contact) {
        return this.getIndex(contact) != -1;
    }

    public Contact getContactByUsername(String username) {
        for (Contact contact: this.contacts) {
            if (contact.getUsername().equals(username)) {
                return contact;
            }
        }
        return null;
    }

    public void loadContacts(Context context) {

        try {
            FileInputStream fis = context.openFileInput(this.FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Contact>>() {}.getType();
            this.contacts = gson.fromJson(isr, listType); // temporary
            fis.close();
        } catch (FileNotFoundException e) {
            this.contacts = new ArrayList<Contact>();
        } catch (IOException e) {
            this.contacts = new ArrayList<Contact>();
        }

    }

    public void saveContacts(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(this.FILENAME, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(this.contacts, osw);
            osw.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isUsernameAvailable (String username) {
        return this.getContactByUsername(username) == null;
    }
}
