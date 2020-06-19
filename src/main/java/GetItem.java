import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//5. 读取数据: 根据主键读取数据。
public class GetItem {
    public static void main(String[] args) {
        String tableName = "Project_Yang_Feng_sdk";
        String projectName = "projectName";
        String projectNameVal = "projectName2";
        String projectType = "projectType";
        String projectTypeVal = "projectType2";

        Region region = Region.AP_SOUTHEAST_1;
        DynamoDbClient ddb = DynamoDbClient.builder().region(region).build();

        getDynamoDBItem(ddb, tableName, projectName, projectNameVal, projectType, projectTypeVal);
    }

    public static void getDynamoDBItem(DynamoDbClient ddb, String tableName, String key1, String keyVal1, String key2, String keyVal2) {
        HashMap<String, AttributeValue> keyToGet = new HashMap<>();

        keyToGet.put(key1, AttributeValue.builder()
                .s(keyVal1).build());
        keyToGet.put(key2, AttributeValue.builder()
                .s(keyVal2).build());

        GetItemRequest request = GetItemRequest.builder()
                .key(keyToGet)
                .tableName(tableName)
                .build();

        try {
            Map<String, AttributeValue> returnedItem = ddb.getItem(request).item();

            if (returnedItem != null) {
                Set<String> keys = returnedItem.keySet();
                System.out.println("Table Attributes: \n");

                for (String key : keys) {
                    System.out.format("%s: %s\n", key, returnedItem.get(key).toString());
                }
            } else {
                System.out.format("No item found with the key!\n");
            }
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}


