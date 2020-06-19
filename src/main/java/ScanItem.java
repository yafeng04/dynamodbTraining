import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


//7. 扫描数据: 全表扫描并按一定规则过滤数据(比如projectName)。
public class ScanItem {
    public static void main(String[] args) {
        String tableName = "Project_Yang_Feng_sdk";

        Region region = Region.AP_SOUTHEAST_1;
        DynamoDbClient ddb = DynamoDbClient.builder().region(region).build();
        scanItems(ddb, tableName);
    }

    public static void scanItems(DynamoDbClient ddb, String tableName) {
        try {
            Map<String, Condition> scanFilters = new HashMap<>();
            scanFilters.put("projectName", Condition.builder().comparisonOperator(ComparisonOperator.EQ)
                    .attributeValueList(AttributeValue.builder().s("projectName1").build()).build());
            ScanRequest scanRequest = ScanRequest.builder()
                    .tableName(tableName)
                    .scanFilter(scanFilters)
                    .build();

            ScanResponse response = ddb.scan(scanRequest);
            for (Map<String, AttributeValue> item : response.items()) {
                Set<String> keys = item.keySet();
                for (String key : keys) {
                    System.out.println("The key name is " + key);
                    System.out.println("The value is " + item.get(key).s() + "\n");
                }
            }
        } catch (DynamoDbException e) {
            e.printStackTrace();
        }
    }
}
