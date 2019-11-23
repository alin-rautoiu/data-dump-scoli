package datadump.web;

import com.mongodb.Block;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class JudetController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/Judete", method = RequestMethod.GET)
    public String Index(Model model){

        MongoCollection collection = null;
        collection = mongoTemplate.getCollection("Judet");

        Map<String, Object> firstJudet = (Map<String, Object>) collection.find().first();
        List headerList = new ArrayList<>();
        Iterator it = firstJudet.entrySet().iterator();
        it.next(); it.remove(); //sarim peste _id
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            String val = (String) pair.getKey();
            headerList.add(val);
            it.remove();
        }

        List<String> judete = new ArrayList<>();
        FindIterable<Document> iterable = collection.find();

        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                Map<String, Object> item = (Map<String, Object>) document;
                Iterator itr = item.entrySet().iterator();
                itr.next(); itr.remove(); //sarim peste _id
                while (itr.hasNext()){
                    Map.Entry pair = (Map.Entry)itr.next();
                    String val = (String) pair.getValue();
                    judete.add(val);
                    itr.remove();
                }
            }
        });
        /*DBCursor cursor = collection.find();
        while (cursor.hasNext()) {
            DBObject obj = cursor.next();
            judete.add((Map<String, Object>) obj);
        }*/
        //judete = (ArrayList<Map<String, Object>>) collection.find();

        //List<DBObject> all = collection.find().toArray();
        model.addAttribute("headerList", headerList);
        model.addAttribute("valuesList", judete);
        model.addAttribute("nrHeaderValues", headerList.size());

        return "displayJudete";
    }
}

/*for (Object judet: judete) {

            Document query = new Document("judet", judet.getString("judet"));
            //BSONObject q = new BasicBSONObject();
            //q.put("judet", ((Map<String, Object>) item).get("judet"));

			//List results = new ArrayList<>();
			//collection.find(query).into(results);
            Document oldValue =  (Document) collection.find(query).first();
            if(oldValue != null){
                collection.replaceOne(oldValue ,judet);
            }else{
                collection.insertOne(judet);
            }
        }*/