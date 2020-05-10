package dao;



import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.XMLWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

abstract class AbstractDAO {
    private final static Logger logger = getLogger(AbstractDAO.class);

    void saveFile(JSONObject jsonObject, String pathToFile) {
        try {
            FileWriter file = new FileWriter(pathToFile);
            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            logger.error("Can't write in file DB as JSON format in saveFile: '" + pathToFile + "'", e.getMessage());
        }
    }

    void saveDocument(Document document, File inputFile) {
        try {
            FileWriter fileWriter = new FileWriter(inputFile);
            XMLWriter output = new XMLWriter(fileWriter);
            output.write(document);
            output.close();
            fileWriter.close();
        } catch (IOException e) {
            logger.error("Can't write in file DB as XML format in saveDocument: '" + inputFile + "'", e.getMessage());
        }
    }

    long getMaxId(Document document, String pathToNode) {
        long maxId = 0;
        Element classElement = document.getRootElement();
        List<Node> nodes = document.selectNodes(pathToNode);
        for (Node node : nodes) {
            if (maxId < Long.parseLong(node.valueOf("@id"))) {
                maxId = Long.parseLong(node.valueOf("@id"));
            }
        }
        return maxId;
    }

    long getMaxId(JSONArray jArray) {
        long maxId = 0;
        for (Object o : jArray) {
            JSONObject jo = (JSONObject) o;
            if (maxId < Long.parseLong(jo.get("id").toString())) {
                maxId = Long.parseLong(jo.get("id").toString());
            }
        }
        return maxId;
    }
}