package controller;


import entity.Contact;
import entity.CustomUser;
import entity.UserMail;
import entity.UserRole;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.ConstantsRegexPattern;
import services.ContactService;
import services.MailService;
import services.UserService;


import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class ControllerAuthorisation {
    private final static Logger logger = getLogger(ControllerAuthorisation.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private MailService notificationService;

    @RequestMapping("/")
    public String indexPage(Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        CustomUser dbUser = userService.getUserByLogin(login);

        List<Contact> contacts = contactService.getByIdUser(String.valueOf(dbUser.getId()));
        contacts.sort(new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                int resCompare = String.CASE_INSENSITIVE_ORDER.compare(o1.getLastName(), o2.getLastName());
                return (resCompare != 0) ? resCompare : o1.getLastName().compareTo(o2.getLastName());
            }
        });

        logger.info("Login user: '" + dbUser + "'");
        model.addAttribute("userLogin", dbUser.getFullName()+"'s");
        model.addAttribute("contacts", contacts);

        return "Index";
    }


    @RequestMapping("/send")
    public String gotosend(@RequestParam(value = "emailcheckbox", required = false) String[] idContacts,
                       Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserMail usermail=new UserMail();
        if ((idContacts == null) || (idContacts.length == 0)) {
            return "redirect:/";
        }

        CustomUser dbUser = userService.getUserByLogin(user.getUsername());

        Contact contact = contactService.getById(idContacts[0]); //only first checked is selecting
        if (contact == null){
            return "redirect:/";
        }
        String rez="";
        for (String id:idContacts) {
            rez+=id;
            rez+=" ";
        }
        model.addAttribute("mail", rez);
        return "Contact";
    }

    @RequestMapping("/mail")
    public String send(@RequestParam(value = "mail", required = false) String ids,
                       @RequestParam(value = "message", required = false) String text,
                       @RequestParam(value = "subject", required = false) String subject,
                       Model model) {
        String[] idContacts = ids.split(" ");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserMail usermail=new UserMail();
        if (idContacts == null) {
            return "redirect:/";
        }

        CustomUser dbUser = userService.getUserByLogin(user.getUsername());

        Contact cont = contactService.getById(idContacts[0]); //only first checked is selecting
           if (cont == null){
            return "redirect:/";
        }

        if ((text !=null) && (subject != null)) {
            for (String id:idContacts) {

                Contact contact = contactService.getById(id);
                usermail.setFirstName(contact.getName());
                usermail.setLastName(contact.getLastName());
                usermail.setEmailAddress(contact.getEmail()); //Receiver's email address
                usermail.setSubject(subject);
                String mess=text+"\n\n\n\n"+"Message was sended with the help of CONTACT BOOK app"+"\n\n"+"Sender Info:\n"+"       User:  "+dbUser.getFullName()+"\n"+"       Mail:  "+dbUser.getLogin();
                usermail.setText(mess);
                try {
                    notificationService.sendEmail(usermail);
                } catch (MailException mailException) {
                    System.out.println(mailException);
                }





                usermail.setFirstName(dbUser.getFullName());
                usermail.setEmailAddress(dbUser.getLogin()); //Receiver's email address
                usermail.setSubject("CONTACT BOOK Sanded Message");
                String msg="Reciver:\n"+contact.getName()+" "+contact.getLastName()+"\n\n"+"Subject:\n"+subject+"\n\n"+"Message:\n"+text;
                usermail.setText(msg);
                /*
                 * Here we will call sendEmail() for Sending mail to the sender.
                 */
                try {
                    notificationService.sendEmail(usermail);
                } catch (MailException mailException) {
                    System.out.println(mailException);
                }
                logger.info("CustomUser sends mail to contact : '" + dbUser + "' contact '" + contact.getId() + "'");
            }
            return "redirect:/";
        }

        return "Contact";
    }

    @RequestMapping("/registration")
    public String authorization(
            @RequestParam(value = "name", required = false) String fullName,
            @RequestParam(value = "login", required = false) String login,
            @RequestParam(value = "password", required = false) String password,
            Model model) {

        if (fullName == null && login == null && password == null) {
            return "RegistrationPage";
        }

        if ((fullName == null) || (fullName.length() < 5)) {
            model.addAttribute("warningMessage", "Full name is to short: < 5 letters!");
            return "RegistrationPage";
        }
        if ((login == null) || (login.length() < 3)) {
            model.addAttribute("warningMessage", "Email is to short: < 3 letters!");
            return "RegistrationPage";
        }

        Pattern p = Pattern.compile(ConstantsRegexPattern.getPatternLogin());
        if (!p.matcher(login).matches()) {
            model.addAttribute("warningMessage", "Incorrect email form!");
            return "RegistrationPage";
        }

        if ((password == null) || (password.length() < 5)) {
            model.addAttribute("warningMessage", "Password is to short: < 5 letters!");
            return "RegistrationPage";
        }

        CustomUser customUser = userService.getUserByLogin(login);
        if (customUser != null) {
            model.addAttribute("warningMessage", "User with such email already exists!");
            return "RegistrationPage";
        }
      //  ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder();
     //    String encodedPassword = passwordEncoder.encode(password,null);
      //  String encodedPassword = password;
        String encodedPassword = (new ShaPasswordEncoder(256)).encodePassword(password, null);
        userService.create(new CustomUser(fullName, login, encodedPassword, UserRole.USER));
        customUser = userService.getUserByLogin(login);
        logger.info("Registration user: '" + customUser + "'");
        return "Login";
    }

    @RequestMapping("/view")
    public String viewContacts(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CustomUser dbUser = userService.getUserByLogin(user.getUsername());
        List<Contact> contacts = contactService.getByIdUser(String.valueOf(dbUser.getId()));
        contacts.sort(new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                int resCompare = String.CASE_INSENSITIVE_ORDER.compare(o1.getLastName(), o2.getLastName());
                return (resCompare != 0) ? resCompare : o1.getLastName().compareTo(o2.getLastName());
            }
        });

        model.addAttribute("userLogin", dbUser.getFullName()+"'s");
        model.addAttribute("contacts", contacts);
        return "Index";
    }

    @RequestMapping("/edit")
    private String editDeleteContact(@RequestParam(value = "editcheckbox", required = false) String[] idContacts,
                                     @RequestParam(value = "delete", required = false) String isDelete, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if ((idContacts == null) || (idContacts.length == 0)) {
            return "redirect:/";
        }

        CustomUser dbUser = userService.getUserByLogin(user.getUsername());
        model.addAttribute("userLogin", dbUser.getFullName()+"'s");
        model.addAttribute("titleUpdateContact", "Update contact");

        Contact contact = contactService.getById(idContacts[0]); //only first checked is selecting
        if (contact == null){
            return "redirect:/";
        }
        model.addAttribute("idContact", contact.getId());
        model.addAttribute("lastName", contact.getLastName());
        model.addAttribute("name", contact.getName());
        model.addAttribute("middleName", contact.getMiddleName());
        model.addAttribute("mobilePhone", contact.getMobilePhone());
        model.addAttribute("homePhone", contact.getHomePhone());
        model.addAttribute("address", contact.getAddress());
        model.addAttribute("email", contact.getEmail());

        logger.info("CustomUser edits contact : '" + dbUser + "' contact '" + contact.getId() + "'");
        return "CreateContact";
    }

    @RequestMapping("/delete")
    private String deleteContact(@RequestParam(value = "deletecheckbox", required = false) String[] idContacts,
                                 @RequestParam(value = "delete", required = false) String isDelete, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if ((idContacts == null) || (idContacts.length == 0) || (!"Delete".equals(isDelete))) {
            return "redirect:/";
        }

        for (String idCont : idContacts) {
            contactService.delete(idCont);
        }

        CustomUser dbUser = userService.getUserByLogin(user.getUsername());
        List<Contact> contacts = contactService.getByIdUser(String.valueOf(dbUser.getId()));
        contacts.sort(new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                int resCompare = String.CASE_INSENSITIVE_ORDER.compare(o1.getLastName(), o2.getLastName());
                return (resCompare != 0) ? resCompare : o1.getLastName().compareTo(o2.getLastName());
            }
        });

        model.addAttribute("contacts", contacts);
        return "Index";
    }
}