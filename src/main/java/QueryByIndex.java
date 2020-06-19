import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

import java.util.HashMap;

//9. 查询GSI: 根据memberName查询数据。
public class QueryByIndex {
    public static void main(String[] args) {
        String tableName = "Project_Yang_Feng_sdk";

        String partitionKeyName = "memberName";
        String partitionKeyVal = "memberName3";
        String partitionAlias = "#memberName";

        System.out.format("Querying %s", tableName);

        Region region = Region.AP_SOUTHEAST_1;
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build();

        int count = queryTable(ddb, tableName, partitionKeyName, partitionKeyVal, partitionAlias);
        System.out.println("There were " + count + "record(s) returned");
    }

    public static int queryTable(DynamoDbClient ddb,
                                 String tableName,
                                 String partitionKeyName,
                                 String partitionKeyVal,
                                 String partitionAlias) {
        HashMap<String, String> attrNameAlias = new HashMap<>();

        attrNameAlias.put(partitionAlias, partitionKeyName);

        HashMap<String, AttributeValue> attrValues =
                new HashMap<String, AttributeValue>();
        attrValues.put(":" + partitionKeyName, AttributeValue.builder().s(partitionKeyVal).build());

        QueryRequest queryReq = QueryRequest.builder()
                .tableName(tableName)
                .indexName("memberName-index")
                .keyConditionExpression(partitionAlias + " = :" + partitionKeyName)
                .expressionAttributeNames(attrNameAlias)
                .expressionAttributeValues(attrValues)
                .build();

        try {
            QueryResponse response = ddb.query(queryReq);
            return response.count();
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return -1;
    }
}
