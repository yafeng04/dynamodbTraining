import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

//1 创建Table: Project_NAME, 分区键为projectName，排序键为projectType。
public class CreateTable {
    public static void main(String[] args) {
        Region region = Region.AP_SOUTHEAST_1;
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build();

        String tableName = "Project_Yang_Feng_sdk";
        String hashKey = "projectName";
        String rangeKey = "projectType";
        String result = createTable(ddb, tableName, hashKey, rangeKey);
        System.out.println("New table is " + result);
    }

    public static String createTable(DynamoDbClient ddb, String tableName, String hashKey, String rangeKey) {
        CreateTableRequest request = CreateTableRequest.builder()
                .attributeDefinitions(AttributeDefinition.builder()
                                .attributeName(hashKey)
                                .attributeType(ScalarAttributeType.S)
                                .build(),
                        AttributeDefinition.builder()
                                .attributeName(rangeKey)
                                .attributeType(ScalarAttributeType.S)
                                .build())
                .keySchema(KeySchemaElement.builder()
                        .attributeName(hashKey)
                        .keyType(KeyType.HASH)
                        .build(), KeySchemaElement.builder()
                        .attributeName(rangeKey)
                        .keyType(KeyType.RANGE)
                        .build())
                .billingMode(BillingMode.PAY_PER_REQUEST)
                .tableName(tableName)
                .build();

        String newTable = "";
        try {
            CreateTableResponse response = ddb.createTable(request);
            newTable = response.tableDescription().tableName();
            return newTable;
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return "";
    }
}
