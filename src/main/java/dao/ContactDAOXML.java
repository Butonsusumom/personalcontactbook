package dao;

import entity.Contact;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service("contactxml")
public class ContactDAOXML extends AbstractDAO implements ContactDAO{
    private final static Logger logger = getLogger(ContactDAOXML.class);
    private File inputFile;
    private Document document;
    private String pathToNode;
    private SAXReader reader;

    private ContactDAOXML() {
        reader = new SAXReader();
    }

    @Override
    public void setTypeDB(String pathToFileDB) {
        File file = new File(pathToFileDB + "contacts.xml");
        this.inputFile = new File(pathToFileDB + "contacts.xml");
        this.pathToNode = "/class/contact";
        if (!file.exists()) {
            Document newDocument = DocumentHelper.createDocument();
            Element root = newDocument.addElement("class");
            saveDocument(newDocument, inputFile);
        }
        try {
            document = reader.read(inputFile);
        } catch (DocumentException e) {
            logger.error("Can't access to file DB in setPathToFileDB: '" + pathToFileDB + "contacts.xml" + "'", e.getMessage());
        }
    }


    @Override
    public void create(Contact contact) {
        long maxId = getMaxId(document, pathToNode);

        Element classElement = document.getRootElement();
        Element contactElement = classElement.addElement("contact").addAttribute("id", String.valueOf(maxId + 1));
        contactElement.addElement("iduser").addText(String.valueOf(contact.getIdUser()));
        contactElement.addElement("lastname").addText(contact.getLastName());
        contactElement.addElement("name").addText(contact.getName());
        contactElement.addElement("middlename").addText(contact.getMiddleName());
        contactElement.addElement("mobilephone").addText(contact.getMobilePhone());
        contactElement.addElement("homephone").addText(contact.getHomePhone());
        contactElement.addElement("address").addText(contact.getAddress());
        contactElement.addElement("email").addText(contact.getEmail());
        saveDocument(document, inputFile);
    }

    @Override
    public List<Contact> getByIdUser(String idUser) {
        List<Contact> contacts = new ArrayList<>();
        Element classElement = document.getRootElement();
        List<Node> nodes = document.selectNodes(pathToNode);
        for (Node node : nodes) {
            if (idUser.equals(node.selectSingleNode("iduser").getText())) {
                contacts.add(getContactFromNode(node));
            }
        }
        return contacts;
    }

    @Override
    public List<Contact> getByIdUserAndName(String idUser, String lastName, String name, String mobilePhone) {
        List<Contact> contacts = new ArrayList<>();
        Element classElement = document.getRootElement();
        List<Node> nodes = document.selectNodes(pathToNode);
        for (Node node : nodes) {
            if (node.selectSingleNode("iduser").getText().toLowerCase().contains(idUser.toLowerCase()) &&
                    node.selectSingleNode("lastname").getText().toLowerCase().contains(lastName.toLowerCase()) &&
                    node.selectSingleNode("name").getText().toLowerCase().contains(name.toLowerCase()) &&
                    node.selectSingleNode("mobilephone").getText().contains(mobilePhone)) {
                contacts.add(getContactFromNode(node));
            }
        }
        return contacts;
    }

    @Override
    public Contact getContactById(String id) {
        Element classElement = document.getRootElement();
        List<Node> nodes = document.selectNodes(pathToNode);
        for (Node node : nodes) {
            if (id.equals(node.valueOf("@id"))) {
                return getContactFromNode(node);
            }
        }
        return null;
    }

    @Override
    public void update(Contact contact) {
        Element classElement = document.getRootElement();
        List<Node> nodes = document.selectNodes(pathToNode + "[@id='" + contact.getId() + "']");
        for (Node node : nodes) {
            node.selectSingleNode("lastname").setText(contact.getLastName());
            node.selectSingleNode("name").setText(contact.getName());
            node.selectSingleNode("middlename").setText(contact.getMiddleName());
            node.selectSingleNode("mobilephone").setText(contact.getMobilePhone());
            node.selectSingleNode("homephone").setText(contact.getHomePhone());
            node.selectSingleNode("address").setText(contact.getAddress());
            node.selectSingleNode("email").setText(contact.getEmail());
        }
        saveDocument(document, inputFile);
    }

    @Override
    public void delete(String id) {
        Element classElement = document.getRootElement();
        List<Node> nodes = document.selectNodes(pathToNode + "[@id='" + id + "']");
        for (Node node : nodes) {
            node.detach();
        }
        saveDocument(document, inputFile);
    }

    private Contact getContactFromNode(Node node) {
        Contact contact = new Contact();
        contact.setId(Long.parseLong(node.valueOf("@id")));
        contact.setIdUser(Long.parseLong(node.selectSingleNode("iduser").getText()));
        contact.setLastName(node.selectSingleNode("lastname").getText());
        contact.setName(node.selectSingleNode("name").getText());
        contact.setMiddleName(node.selectSingleNode("middlename").getText());
        contact.setMobilePhone(node.selectSingleNode("mobilephone").getText());
        contact.setHomePhone(node.selectSingleNode("homephone").getText());
        contact.setAddress(node.selectSingleNode("address").getText());
        contact.setEmail(node.selectSingleNode("email").getText());
        return contact;
    }
}
