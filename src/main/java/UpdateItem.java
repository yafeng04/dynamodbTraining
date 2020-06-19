import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;

//2 更新Table: 更新上表，添加Attributes: memberName, startDate 4. 更新数据: 尝试更新已存在的数据。
public class UpdateItem {
    public static void main(String[] args) {
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(Region.AP_SOUTHEAST_1)
                .build();

        Map<String, AttributeValue> keys = new HashMap<>();
        keys.put("projectName", AttributeValue.builder().s("projectName1").build());
        keys.put("projectType", AttributeValue.builder().s("projectType1").build());

        Map<String, AttributeValueUpdate> attributeUpdates = new HashMap<>();
        attributeUpdates.put("memberName", AttributeValueUpdate.builder().value(AttributeValue.builder().s("memberNameUpdated").build()).build());

        ddb.updateItem(UpdateItemRequest.builder().tableName("Project_Yang_Feng_sdk").key(keys).attributeUpdates(attributeUpdates).build());
    }
}

