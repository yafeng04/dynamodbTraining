import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

import java.util.HashMap;

//3 写入数据:插入5-10条数据，包含DynamoDB所有数据类型。
public class PutItem {
    public static void main(String[] args) {
        Region region = Region.AP_SOUTHEAST_1;
        DynamoDbClient ddb = DynamoDbClient.builder().region(region).build();
        for (int i = 2; i < 6; i++) {
            String tableName = "Project_Yang_Feng_sdk";
            String projectName = "projectName";
            String projectNameVal = "projectName" + i;
            String projectType = "projectType";
            String projectTypeVal = "projectType" + i;
            String memberName = "memberName";
            String memberNameVal = "memberName" + i;
            String startDate = "startDate";
            String startDateVal = "2020-01-01";
            putItemInTable(ddb, tableName, projectName, projectNameVal, projectType, projectTypeVal, memberName, memberNameVal, startDate, startDateVal);
        }
        System.out.println("Done!");
    }

    public static void putItemInTable(DynamoDbClient ddb,
                                      String tableName,
                                      String projectName,
                                      String projectNameVal,
                                      String projectType,
                                      String projectTypeVal,
                                      String memberName,
                                      String memberNameVal,
                                      String startDate,
                                      String startDateVal) {

        HashMap<String, AttributeValue> itemValues = new HashMap<String, AttributeValue>();

        // Add all content to the table
        itemValues.put(projectName, AttributeValue.builder().s(projectNameVal).build());
        itemValues.put(projectType, AttributeValue.builder().s(projectTypeVal).build());
        itemValues.put(memberName, AttributeValue.builder().s(memberNameVal).build());
        itemValues.put(startDate, AttributeValue.builder().s(startDateVal).build());

        // Create a PutItemRequest object
        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(itemValues)
                .build();

        try {
            ddb.putItem(request);
            System.out.println(tableName + " was successfully updated");
        } catch (ResourceNotFoundException e) {
            System.err.format("Error: The table \"%s\" can't be found.\n", tableName);
            System.err.println("Be sure that it exists and that you've typed its name correctly!");
            System.exit(1);
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}


