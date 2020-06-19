import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

//8. 创建GSI: 对memberName创建全局二级索引
public class UpdateTable {
    public static void main(String[] args) {
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(Region.AP_SOUTHEAST_1)
                .build();

        UpdateTableRequest updateTableRequest = UpdateTableRequest.builder()
                .tableName("Project_Yang_Feng_sdk")
                .billingMode(BillingMode.PAY_PER_REQUEST)
                .attributeDefinitions(AttributeDefinition.builder().attributeName("memberName").attributeType(ScalarAttributeType.S).build())
                .globalSecondaryIndexUpdates(
                        GlobalSecondaryIndexUpdate.builder()
                                .create(
                                        CreateGlobalSecondaryIndexAction.builder()
                                                .indexName("memberName-index")
                                                .keySchema(KeySchemaElement.builder().attributeName("memberName").keyType(KeyType.HASH).build())
                                                .projection(Projection.builder().projectionType(ProjectionType.ALL).build()).
                                                build())
                                .build())
                .build();

        ddb.updateTable(updateTableRequest);
    }
}
