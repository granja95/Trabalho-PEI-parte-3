package app;

import static com.jayway.jsonpath.internal.function.ParamType.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Building a RESTful Web Service retrieved from:
 * https://spring.io/guides/gs/rest-service/#scratch
 * https://spring.io/guides/gs/rest-service/#scratch Additionally see:
 * https://spring.io/guides/gs/accessing-mongodb-data-rest/
 */
@RestController
public class BycicleController {

    @RequestMapping("/getstore")
    public String getStore(@RequestParam(value = "value") String value) {
        MongoConnector mongo = new MongoConnector();
        String res = mongo.getData("PEI2", "SalesDetails", "Store", value);
        return res;

    }

    @RequestMapping("/aggregateStoreByQueryString")
    public String aggregateStoreByQueryString(@RequestParam(value = "storeid") String storeID,
            @RequestParam(value = "year") String year,
            @RequestParam(value = "month") String month) throws JSONException {
        //Example: %5B%7B%24group%3A%7B%22_id%22%3Anull%2Ccount%3A%7B%24sum%3A1%7D%7D%7D%5D para [{$group:{"_id":null,count:{$sum:1}}}] -> utilizar: https://meyerweb.com/eric/tools/dencoder/
        MongoConnector mongo = new MongoConnector();
        String query_t1_1 = "[{\"$project\":{year:{$year:\"$OrderDate\"},Store:1,month:{$month:\"$OrderDate\"},ReceiptID:1,Quantity:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{ $group : { _id: \"$ReceiptID\", ProductTotal: {$sum : \"$Quantity\"}}},{$sort: {_id:1}}]";
        String res_t1_1 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t1_1);
        String query_t1_2 = "[{\"$project\":{year:{$year:'$OrderDate'},Store:1,month:{$month:'$OrderDate'},ReceiptID:1,ReceitpLineID:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{$group: {_id: {ReceiptID: '$ReceiptID' , Produtos: '$ReceitpLineID'},count: {$sum:1}}},{$sort:{'_id.ReceiptID':1}}]";
        String res_t1_2 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t1_2);
        String query_t1_3 = "[{\"$project\":{year:{$year:'$OrderDate'},Store:1,month:{$month:'$OrderDate'},ReceiptID:1,LineTotal:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{$group:{_id:'$ReceiptID',avgPrice:{$avg:'$LineTotal'}}}]";
        String res_t1_3 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t1_3);

        String query_t2_1 = "[{\"$project\":{year:{$year:\"$OrderDate\"},Store:1,month:{$month:\"$OrderDate\"},Quantity:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{ $group : { _id: '$all', total: {$sum : '$Quantity'}}},{$sort: {_id:1}}]";
        String res_t2_1 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t2_1);
        String query_t2_2 = "[{\"$project\":{year:{$year:\"$OrderDate\"},Store:1,month:{$month:\"$OrderDate\"},ProductID:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{$group:{_id:\"$ProductID\"}}, {$count:\"Products\"}]";
        String res_t2_2 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t2_2);
        String query_t2_3 = "[{\"$project\":{year:{$year:\"$OrderDate\"},Store:1,month:{$month:\"$OrderDate\"},Costumer:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{$group:{_id:\"$Customer\"}}, {$count:\"Customer\"}]";
        String res_t2_3 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t2_3);
        String query_t2_4 = "[{\"$project\":{year:{$year:\"$OrderDate\"},Store:1,month:{$month:\"$OrderDate\"},LineTotal:1,Costumer:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{$group: { _id: \"$Customer\", total : {$sum :\"$LineTotal\"}}},{$sort: {_id: -1}}]";
        String res_t2_4 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t2_4);
        String query_t2_5 = "[{\"$project\":{year:{$year:\"$OrderDate\"},Store:1,month:{$month:\"$OrderDate\"},Quantity:1,ProductID:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{$group: { _id: \"$ProductID\", total : {$sum : \"$Quantity\" }}},{$sort: {_id:-1}}]";
        String res_t2_5 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t2_5);
        String query_t2_6 = "[{\"$project\":{year:{$year:\"$OrderDate\"},Store:1,month:{$month:\"$OrderDate\"},CurrencyRateID:1,LineTotal:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{$group: { _id: \"$CurrencyRateID\" , total: {$sum : \"$LineTotal\"}}},{$sort: {_id: 1}}]";
        String res_t2_6 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t2_6);

        String query_t3_1 = "[{\"$project\":{year:{$year:\"$OrderDate\"},Store:1,month:{$month:\"$OrderDate\"},StoreName:1,Quantity:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{$group:{_id:\"$StoreName\",total:{$sum:\"$Quantity\"}}}]";
        String res_t3_1 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t3_1);
        String query_t3_2 = "[{\"$project\":{year:{$year:\"$OrderDate\"},Store:1,month:{$month:\"$OrderDate\"},StoreName:1,LineTotal:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{ $group : { _id : \"$StoreName\", total: { $sum: \"$LineTotal\"}}}]";
        String res_t3_2 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t3_2);
        String query_t3_3 = "[{\"$project\":{year:{$year:\"$OrderDate\"},Store:1,month:{$month:\"$OrderDate\"},StoreName:1,UnitPrice:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{$group:{_id:\"$StoreName\",avgPrice:{$avg:\"$UnitPrice\"}}},{$sort : {_id:1}} ]";
        String res_t3_3 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t3_3);
        //Insert(res_t1_1);

        String final_result = res_t1_1 + "\n\n" + res_t1_2 + "\n\n" + res_t1_3 + "\n\n" + res_t2_1 + "\n\n" + res_t2_2 + "\n\n" + res_t2_3 + "\n\n" + res_t2_4 + "\n\n" + res_t2_5 + "\n\n" + res_t2_6 + "\n\n" + res_t3_1 + "\n\n" + res_t3_2 + "\n\n" + res_t3_3;
        MongoClient mongotest = new MongoClient();
        MongoDatabase database = mongotest.getDatabase("PEI2");
        MongoCollection collection = database.getCollection("Queries");
        String teste[] = new String[12];
        teste[0] = res_t1_1;
        teste[1] = res_t1_2;
        teste[2] = res_t1_3;
        teste[3] = res_t2_1;
        teste[4] = res_t2_2;
        teste[5] = res_t2_3;
        teste[6] = res_t2_4;
        teste[7] = res_t2_5;
        teste[8] = res_t2_6;
        teste[9] = res_t3_1;
        teste[10] = res_t3_2;
        teste[11] = res_t3_3;
        for (int x = 0; x < teste.length; x++) {
            teste[x] = teste[x].replace("[", "");
            teste[x] = teste[x].replace("]", "");
            Document doc = Document.parse(teste[x]);
            collection.insertOne(doc);
        }
        /*
        String soma = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
        for (int x = 0; x < teste.length; x++) {
            teste[x] = teste[x].replace("[", "");
            teste[x] = teste[x].replace("]", "");
            JSONObject json = new JSONObject(teste[x]);
            String xml = XML.toString(json);
            if (x < 0 && x < 3) {

                soma += "\t" + xml + "\n";
            }
            soma += "\t" + xml + "\n";
        }
        */
        return final_result;

    }
}
