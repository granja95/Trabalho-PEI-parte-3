package app;

import org.json.JSONArray;
import org.json.JSONException;

import org.json.XML;
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
        JSONArray json1 = new JSONArray(res_t1_1);
        String xml1 = XML.toString(json1, "consulta_t1_1").replace("$", "");
        String xmlQueries = xml1;
        String query_t1_2 = "[{\"$project\":{year:{$year:'$OrderDate'},Store:1,month:{$month:'$OrderDate'},ReceiptID:1,ReceitpLineID:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{$group: {_id: {ReceiptID: '$ReceiptID' , Produtos: '$ReceitpLineID'},DifProductTotal: {$sum:1}}},{$sort:{'_id.ReceiptID':1}}]";
        String res_t1_2 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t1_2);
        JSONArray json2 = new JSONArray(res_t1_2);
        String xml2 = XML.toString(json2, "consulta_t1_2").replace("$", "");
        xmlQueries += xml2;
        String query_t1_3 = "[{\"$project\":{year:{$year:'$OrderDate'},Store:1,month:{$month:'$OrderDate'},ReceiptID:1,LineTotal:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{$group:{_id:'$ReceiptID',AvgProductPrice:{$avg:'$LineTotal'}}}]";
        String res_t1_3 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t1_3);
        JSONArray json3 = new JSONArray(res_t1_3);
        String xml3 = XML.toString(json3, "consulta_t1_3").replace("$", "");
        xmlQueries += xml3;
        String query_t2_1 = "[{\"$project\":{year:{$year:\"$OrderDate\"},Store:1,month:{$month:\"$OrderDate\"},Quantity:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{ $group : { _id: '$all', AllProducts: {$sum : '$Quantity'}}},{$sort: {_id:1}}]";
        String res_t2_1 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t2_1);
        JSONArray json4 = new JSONArray(res_t2_1);
        String xml4 = XML.toString(json4, "consulta_t2_1").replace("$", "");
        xmlQueries += xml4;
        String query_t2_2 = "[{\"$project\":{year:{$year:\"$OrderDate\"},Store:1,month:{$month:\"$OrderDate\"},ProductID:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{$group:{_id:\"$ProductID\"}}, {$count:\"Products\"}]";
        String res_t2_2 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t2_2);
        JSONArray json5 = new JSONArray(res_t2_2);
        String xml5 = XML.toString(json5, "consulta_t2_2").replace("$", "");
        xmlQueries += xml5;
        String query_t2_3 = "[{\"$project\":{year:{$year:\"$OrderDate\"},Store:1,month:{$month:\"$OrderDate\"},Costumer:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{$group:{_id:\"$Customer\"}}, {$count:\"Customer\"}]";
        String res_t2_3 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t2_3);
        JSONArray json6 = new JSONArray(res_t2_3);
        xmlQueries += XML.toString(json6, "consulta_t2_3").replace("$", "");
        String query_t2_4 = "[{\"$project\":{year:{$year:\"$OrderDate\"},Store:1,month:{$month:\"$OrderDate\"},LineTotal:1,Costumer:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{$group: { _id: \"$Customer\", SalesPerClient : {$sum :\"$LineTotal\"}}},{$sort: {_id: -1}}]";
        String res_t2_4 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t2_4);
        JSONArray json7 = new JSONArray(res_t2_4);
        xmlQueries += XML.toString(json7, "consulta_t2_4").replace("$", "");
        String query_t2_5 = "[{\"$project\":{year:{$year:\"$OrderDate\"},Store:1,month:{$month:\"$OrderDate\"},Quantity:1,ProductID:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{$group: { _id: \"$ProductID\", TotalProductSales : {$sum : \"$Quantity\" }}},{$sort: {_id:-1}}]";
        String res_t2_5 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t2_5);
        JSONArray json8 = new JSONArray(res_t2_5);
        xmlQueries += XML.toString(json8, "consulta_t2_5").replace("$", "");
        String query_t2_6 = "[{\"$project\":{year:{$year:\"$OrderDate\"},Store:1,month:{$month:\"$OrderDate\"},CurrencyRateID:1,LineTotal:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{$group: { _id: \"$CurrencyRateID\" , TotalSalesCurrency: {$sum : \"$LineTotal\"}}},{$sort: {_id: 1}}]";
        String res_t2_6 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t2_6);
        JSONArray json9 = new JSONArray(res_t2_6);
        xmlQueries += XML.toString(json9, "consulta_t2_6").replace("$", "");
        String query_t3_1 = "[{\"$project\":{year:{$year:\"$OrderDate\"},Store:1,month:{$month:\"$OrderDate\"},StoreName:1,Quantity:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{$group:{_id:\"$StoreName\",AllProductsSoldPerStore:{$sum:\"$Quantity\"}}}]";
        String res_t3_1 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t3_1);
        JSONArray json10 = new JSONArray(res_t3_1);
        xmlQueries += XML.toString(json10, "consulta_t3_1").replace("$", "");
        String query_t3_2 = "[{\"$project\":{year:{$year:\"$OrderDate\"},Store:1,month:{$month:\"$OrderDate\"},StoreName:1,LineTotal:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{ $group : { _id : \"$StoreName\", TotalSalesPerStore: { $sum: \"$LineTotal\"}}}]";
        String res_t3_2 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t3_2);
        JSONArray json11 = new JSONArray(res_t3_2);
        xmlQueries += XML.toString(json11, "consulta_t3_2").replace("$", "");
        String query_t3_3 = "[{\"$project\":{year:{$year:\"$OrderDate\"},Store:1,month:{$month:\"$OrderDate\"},StoreName:1,UnitPrice:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}},"
                + "{$group:{_id:\"$StoreName\",AvgProductSalesPerStore:{$avg:\"$UnitPrice\"}}},{$sort : {_id:1}} ]";
        String res_t3_3 = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_t3_3);
        JSONArray json12 = new JSONArray(res_t3_3);
        xmlQueries += XML.toString(json12, "consulta_t3_3").replace("$", "");
        String query_sales = "[{\"$project\":{year:{$year:\"$OrderDate\"},month:{$month:\"$OrderDate\"},ReceiptID:1,OrderDate:1,Customer:1,CurrencyRateID:1,SubTotal:1,TaxAmt:1,Store:1,StoreName:1,ReceiptLineID:1,Quantity:1,ProductID:1,UnitPrice:1,LineTotal:1}},"
                + "{$match:{$and:[{Store:" + storeID + "},{year:" + year + "},{month:" + month + "}]}}]";
        String res_sales = mongo.aggregateDataByQueryString("PEI2", "SalesDetails", query_sales);
        JSONArray json_sales = new JSONArray(res_sales);
        xmlQueries += XML.toString(json_sales, "Sales").replace("$", "");
        Integer product_ids[] = new Integer[json_sales.length()];
        for (int i = 0; i < json_sales.length(); i++) {
            product_ids[i] = json_sales.getJSONObject(i).getInt("ProductID");
        }

        String res_products[] = new String[json_sales.length()];
        String final_res_products = "";
        for (int i = 0; i < json_sales.length(); i++) {
            String query_products = "[{$match:{ProductID:" + product_ids[i] + "}}]";
            res_products[i] = mongo.aggregateDataByQueryString("PEI2", "ProductDetails", query_products);

            res_products[i] = res_products[i].replace("[", "");
            res_products[i] = res_products[i].replace("]", "");
            final_res_products += res_products[i] + ",";
        }
        final_res_products = "[" + final_res_products + "]";

        JSONArray json_products = new JSONArray(final_res_products);
        xmlQueries += XML.toString(json_products, "Products").replace("$", "");

        Integer currency_ids[] = new Integer[json_sales.length()];
        for (int i = 0; i < json_sales.length(); i++) {
            currency_ids[i] = json_sales.getJSONObject(i).getInt("CurrencyRateID");

        }
        String res_currency[] = new String[json_sales.length()];
        String final_res_currency = "";

        for (int i = 0; i < json_sales.length(); i++) {
            String query_currency = "[{$match:{CurrencyRateID:" + currency_ids[i] + "}}]";
            res_currency[i] = mongo.aggregateDataByQueryString("PEI2", "Currency", query_currency);
            res_currency[i] = res_currency[i].replace("[", "");
            res_currency[i] = res_currency[i].replace("]", "");
            final_res_currency += res_currency[i] + ",";
        }
        final_res_currency = "[" + final_res_currency + "]";
        JSONArray json_currency = new JSONArray(final_res_currency);
        xmlQueries += XML.toString(json_currency, "Taxas").replace("$", "");

        xmlQueries = "<?xml version='1.0' encoding='utf-8'?><root>" + xmlQueries + "</root>";

        XSLTransformer.transform("resources/", xmlQueries, "transformationRules.xsl", "newXMlDocument.xml");
        return xmlQueries;

    }
}
