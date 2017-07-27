package com.jlava.app;

import com.jlava.service.*;
import com.jlava.service.impl.*;
import com.jlava.model.*;
import com.jlava.persistence.HibernateUtil;
import com.jlava.apputil.AppUtil;
import java.util.Date;

public class EmpManager {
    private RoleManager roleManager;
    private PersonManager personManager;
    private ContactManager contactManager;

    public EmpManager () {
        this.personManager = new PersonManagerImpl();
        this.roleManager = new RoleManagerImpl(personManager);
        this.contactManager = new ContactManagerImpl(personManager);
    }

    public static void main( String[] args ) {
        EmpManager empManager = new EmpManager();
        String option = "";

        AppUtil.println("\n\n========================");
        AppUtil.println("=== Employee Manager ===");
        AppUtil.println("========================");

        do {
            AppUtil.println("\n[Select Operation]");

            AppUtil.println("[PERSON]");
            AppUtil.println("\t[1] List Persons");
            AppUtil.println("\t[2] Add Person");
            AppUtil.println("\t[3] Update Person");
            AppUtil.println("\t[4] Delete Person");
            AppUtil.println("\t[5] Add Person Role");
            AppUtil.println("\t[6] Delete Person Role");
            AppUtil.println("\t[7] Add Contact");
            AppUtil.println("\t[8] Update Contact");
            AppUtil.println("\t[9] Delete Contact");

            AppUtil.println("[ROLE]");
            AppUtil.println("\t[A] List Roles");
            AppUtil.println("\t[B] Add Role");
            AppUtil.println("\t[C] Update Role");
            AppUtil.println("\t[D] Delete Role");
            AppUtil.println("\n\t[E] Exit ");

            AppUtil.print("\n[Option] : ");
            option = AppUtil.readLine(false, 1).toLowerCase();

            switch(option) {
                case "1":
                    empManager.listPersons();
                    break;
                case "2":
                    empManager.addPerson();
                    break;
                case "3":
                    empManager.updatePerson();
                    break;
                case "4":
                    empManager.deletePerson();
                    break;
                case "5":
                    empManager.addPersonRole();
                    break;
                case "6":
                    empManager.deletePersonRole();
                    break;
                case "7":
                    empManager.addContact();
                    break;
                case "8":
                    empManager.updateContact();
                    break;
                case "9":
                    empManager.deleteContact();
                    break;
                case "a":
                    empManager.listRoles();
                    break;
                case "b":
                    empManager.addRole();
                    break;
                case "c":
                    empManager.updateRole();
                    break;
                case "d":
                    empManager.deleteRole();
                    break;
                case "e":
                    AppUtil.println("\nShutting down...");
                    break;
                default :
                    AppUtil.println("\nInvalid input, try again...");
                    break;
            }

        } while (!option.equals("e"));

        HibernateUtil.shutdown();
    }

    private void listPersons() {
        AppUtil.println("[Sort persons by]");
        AppUtil.println("\t[1] General Weighted Average (GWA)");
        AppUtil.println("\t[2] Date Hired");
        AppUtil.println("\t[3] Last Name");

        AppUtil.print("Sort by : ");
        int sortBy = AppUtil.acceptValidInt(1, 3);

        personManager.listPersons(sortBy);
    }

    private void addPerson() {
        AppUtil.println("[Add new person] : ");

        AppUtil.print("Last Name : ");
        String lastName = AppUtil.readLine(false, 20);

        AppUtil.print("First Name : ");
        String firstName = AppUtil.readLine(false, 20);

        AppUtil.print("Middle Name : ");
        String midName = AppUtil.readLine(true, 20);

        AppUtil.print("Suffix : ");
        String suffix = AppUtil.readLine(true, 4);

        AppUtil.print("Title : ");
        String title = AppUtil.readLine(true, 3);

        AppUtil.print("Birth Date [MM/dd/yyyy] : ");
        Date birthDate = AppUtil.readDate(false);

        AppUtil.print("GWA : ");
        float gwa = AppUtil.readFloat(0, 100.00f);

        AppUtil.print("Date Hired [MM/dd/yyyy] : ");
        Date dateHired = AppUtil.readDate(true);

        AppUtil.print("Employed [y/n]: ");
        boolean employed = AppUtil.readBool();

        AppUtil.print("Street : ");
        String street = AppUtil.readLine(true, 50);

        AppUtil.print("Barangay : ");
        String barangay = AppUtil.readLine(true, 50);

        AppUtil.print("Municipality : ");
        String municipality = AppUtil.readLine(false, 50);

        AppUtil.print("Zipcode : ");
        int zipCode = AppUtil.acceptValidInt(1);

        int personId = personManager.addPerson(lastName, firstName, midName, suffix, title, birthDate, gwa, dateHired, employed, street, barangay, municipality, zipCode);
        personManager.searchPerson(personId);
    }
    
    private void updatePerson() {
        AppUtil.print("[Edit person with id] : ");
        int personId = AppUtil.acceptValidInt(1);

        Person person = personManager.getPerson(personId);
        if(person != null) {
            Address address = person.getAddress();
            String suffixStr = (person.getSuffix() != null)?" [" + person.getSuffix() + "] ":" ";
            String titleStr = (person.getTitle() != null)?" [" + person.getTitle() + "] ":" ";
            String midNameStr = (person.getMidName() != null)?" [" + person.getMidName() + "] ":" ";
            String gwaStr = (person.getGwa() >= 0)?" [" + String.valueOf(person.getGwa()) + "] ":" ";
            String dateHiredStr = (person.getDateHired() != null)?" [" + person.getDateHired() + "] ":" [MM/dd/yyyy] ";
            String employedStr = (person.isEmployed())?"[yes] " : "[no] ";

            String streetStr = (address.getStreet() != null)?" [" + address.getStreet() + "] ":" ";
            String brgyStr = (address.getBarangay() != null)?" [" + address.getBarangay() + "] ": " ";
            String zipStr = (address.getZipCode() > 0)?" [" + address.getZipCode() + "] ":" ";

            personManager.searchPerson(personId);
            AppUtil.println("\n[Editing person " + personId + "]");

            AppUtil.print("Last Name [" + person.getLastName() + "] : ");
            String lastName = AppUtil.readLine(person.getLastName(), false, 20);
            person.setLastName(lastName);

            AppUtil.print("First Name [" + person.getFirstName() + "] : ");
            String firstName = AppUtil.readLine(person.getFirstName(), false, 20);
            person.setFirstName(firstName);

            AppUtil.print("Middle Name" + midNameStr + ": ");
            String midName = AppUtil.readLine(person.getMidName(), true, 20);
            person.setMidName(midName);

            AppUtil.print("Suffix" + suffixStr + ": ");
            String suffix = AppUtil.readLine(person.getSuffix(), true, 4);
            person.setSuffix(suffix);

            AppUtil.print("Title" + titleStr + ": ");
            String title = AppUtil.readLine(person.getTitle(), true, 3);
            person.setTitle(title);

            AppUtil.print("Birth Date [" + person.getBirthDate() + "] : ");
            Date birthDate = AppUtil.readDate(person.getBirthDate(), false);
            person.setBirthDate(birthDate);

            AppUtil.print("GWA" + gwaStr + ": ");
            float gwa = AppUtil.readFloat(person.getGwa(), 0, 100.00f);
            person.setGwa(gwa);

            AppUtil.print("Date Hired" + dateHiredStr + ": ");
            Date dateHired = AppUtil.readDate(person.getDateHired(), true);
            person.setDateHired(dateHired);

            AppUtil.print("Employed [y/n]" + employedStr + ": ");
            boolean employed = AppUtil.readBool(person.isEmployed());
            person.setEmployed(employed);

            AppUtil.print("Street" + streetStr + ": ");
            String street = AppUtil.readLine(address.getStreet(), true, 50);
            address.setStreet(street);

            AppUtil.print("Barangay" + brgyStr + ": ");
            String barangay = AppUtil.readLine(address.getBarangay(), true, 50);
            address.setBarangay(barangay);

            AppUtil.print("Municipality [" + address.getMunicipality() + "] : ");
            String municipality = AppUtil.readLine(address.getMunicipality(), false, 50);
            address.setMunicipality(municipality);

            AppUtil.print("Zipcode" + zipStr + ": ");
            int zipCode = AppUtil.updateValidInt(address.getZipCode(), 1);
            address.setZipCode(zipCode);

            person.setAddress(address);
            personManager.updatePerson(person);
            personManager.searchPerson(personId);
        }
    }

    private void deletePerson() {
        AppUtil.println("[Delete person with id] : ");
        int personId = AppUtil.acceptValidInt(1);
        personManager.deletePerson(personId);
    }

    private void addPersonRole() {
        int roles = roleManager.listRoles();

        AppUtil.println("\n[Add person role]");
        if(roles > 0) {
            AppUtil.print("[Person Id] : ");
            int personId = AppUtil.acceptValidInt(1);

            AppUtil.print("[Role Id] : ");
            int roleId = AppUtil.acceptValidInt(1);

            personManager.addPersonRole(personId, roleId);
        }
    }

    private void deletePersonRole() {
        int roles = roleManager.listRoles();

        AppUtil.println("\n[Delete person role]");
        if(roles > 0) {
            AppUtil.print("[Person Id] : ");
            int personId = AppUtil.acceptValidInt(1);

            AppUtil.print("[Role Id] : ");
            int roleId = AppUtil.acceptValidInt(1);

            personManager.deletePersonRole(personId, roleId);
        }
    }


    private void addContact() {
        AppUtil.println("\n[Add Contact]");
        AppUtil.print("[Person Id] : ");
        int personId = AppUtil.acceptValidInt(1);

        AppUtil.print("Landline : ");
        String landline = AppUtil.readNumeric(true, 7);

        AppUtil.print("Mobile No. : ");
        String mobileNo = AppUtil.readNumeric(false, 12);

        AppUtil.print("Email : ");
        String email = AppUtil.readLine(false, 50);

        contactManager.addContact(personId, landline, mobileNo, email);
    }

    private void updateContact() {
        AppUtil.println("\n[Update Contact]");
        AppUtil.print("[Person Id] : ");
        int personId = AppUtil.acceptValidInt(1);

        AppUtil.print("[Contact Id] : ");
        int oldContactId = AppUtil.acceptValidInt(1);

        Contact contact = contactManager.getContact(personId, oldContactId);
        if(contact != null) {
            AppUtil.print("Landline : ");
            String landline = AppUtil.readNumeric(contact.getLandline(), true, 7);

            AppUtil.print("Mobile No. : ");
            String mobileNo = AppUtil.readNumeric(contact.getMobileNo(), false, 12);

            AppUtil.print("Email : ");
            String email = AppUtil.readLine(contact.getEmail(), false, 50);

            contactManager.updateContact(personId, oldContactId, landline, mobileNo, email);        
        }
    }

    private void deleteContact() {
        AppUtil.println("[Delete Contact]");
        AppUtil.print("[Person ID] : ");
        int personId = AppUtil.acceptValidInt(1);

        AppUtil.print("[Contact ID] : ");
        int contactId = AppUtil.acceptValidInt(1);

        contactManager.deleteContact(personId, contactId);
    }


    private void listRoles() {
        roleManager.listRoles();
    }

    private void addRole() {
        AppUtil.println("[Add new role]");
        AppUtil.print("[Description] : ");
        String roleDesc = AppUtil.readLine(false, 30);

        roleManager.addRole(roleDesc);
    }

    private void updateRole() {
        int roles = roleManager.listRoles();
        if(roles > 0) {
            AppUtil.print("[Role ID] : ");
            int roleId = AppUtil.acceptValidInt(1);

            Role role = roleManager.getRole(roleId);

            if(role != null) {
                AppUtil.print("[New Role Description] (" + role.getRoleDesc() + ") : ");
                String roleDesc = AppUtil.readLine(role.getRoleDesc(), false, 30);

                roleManager.updateRole(roleId, roleDesc);
            }
        }
    }

    private void deleteRole() {
        int roles = roleManager.listRoles();
        if(roles > 0) {
            AppUtil.print("[Role ID] : ");
            int roleId = AppUtil.acceptValidInt(1);

            roleManager.deleteRole(roleId);
        }
    }
}
