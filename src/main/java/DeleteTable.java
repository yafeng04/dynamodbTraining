import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

public class DeleteTable {
    public static void main(String[] args) {
        String tableName = "Project_Yang_Feng_sdk";
        System.out.format("Deleting table %s...\n", tableName);
        Region region = Region.AP_SOUTHEAST_1;
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build();
        try {
            ddb.deleteTable(DeleteTableRequest.builder().tableName("Project_Yang_Feng_sdk").build());
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        System.out.println("Done!");
    }
}

