package dao;

import entity.CustomUser;
import entity.UserRole;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service("userxml")
public class UserDAOXML extends AbstractDAO implements UserDAO {
    private final static Logger logger = getLogger(UserDAOXML.class);
    private File inputFile;
    private Document document;
    private String pathToNode;
    private SAXReader reader;

    public UserDAOXML() {
        reader = new SAXReader();
    }

    @Override
    public void setPathToFileDB(String pathToFileDB) {
        this.inputFile = new File(pathToFileDB + "users.xml");
        this.pathToNode = "/class/user";
        File file = new File(pathToFileDB + "users.xml");
        if (!file.exists()) {
            Document newDocument = DocumentHelper.createDocument();
            Element root = newDocument.addElement("class");
            saveDocument(newDocument, inputFile);
        }
        try {
            document = reader.read(inputFile);
        } catch (DocumentException e) {
            logger.error("Can't access to file DB in setPathToFileDB: '" + pathToFileDB + "users.xml" + "'", e.getMessage());
        }
    }


    @Override
    public void create(CustomUser user) {
        long maxId = getMaxId(document, pathToNode);
        String tableName = user.getClass().getAnnotation(Table.class).name();

        Element classElement = document.getRootElement();
        Element element = classElement.addElement(tableName).addAttribute("id", String.valueOf(maxId + 1));
        element.addElement("fullname").addText(user.getFullName());
        element.addElement("login").addText(user.getLogin());
        element.addElement("password").addText(user.getPassword());
        element.addElement("role").addText(user.getRole().name());
        saveDocument(document, inputFile);
    }

    @Override
    public List<CustomUser> getAllUsers() {
        ArrayList<CustomUser> customUsers = new ArrayList<>();
        Element classElement = document.getRootElement();
        List<Node> nodes = document.selectNodes(pathToNode);
        for (Node node : nodes) {
            customUsers.add(getUserFromNode(node));
        }
        return customUsers;
    }

    @Override
    public CustomUser getByLogin(String login) {
        Element classElement = document.getRootElement();
        List<Node> nodes = document.selectNodes(pathToNode);
        for (Node node : nodes) {
            if (login.equals(node.selectSingleNode("login").getText())) {
                return getUserFromNode(node);
            }
        }
        return null;
    }


    @Override
    public CustomUser getUserById(String id) {
        Element classElement = document.getRootElement();
        List<Node> nodes = document.selectNodes(pathToNode);
        for (Node node : nodes) {
            if (id.equals(node.valueOf("@id"))) {
                return getUserFromNode(node);
            }
        }
        return null;
    }

    private CustomUser getUserFromNode(Node node) {
        CustomUser customUser = new CustomUser();
        customUser.setLogin(node.selectSingleNode("login").getText());
        customUser.setId(Long.parseLong(node.valueOf("@id")));
        customUser.setFullName(node.selectSingleNode("fullname").getText());
        customUser.setPassword(node.selectSingleNode("password").getText());
        customUser.setRole(UserRole.valueOf(node.selectSingleNode("role").getText()));
        return customUser;
    }

}
