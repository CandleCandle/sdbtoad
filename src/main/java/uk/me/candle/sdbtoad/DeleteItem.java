package uk.me.candle.sdbtoad;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.model.DeleteAttributesRequest;
import org.kohsuke.args4j.Argument;

public class DeleteItem extends AbstractCliAction {

    @Argument(index = 0, hidden = false, usage = "The name of the domain")
    private String domain;

    @Argument(index = 1, hidden = false, usage = "the name of the item")
    private String item;

    public DeleteItem(ClientConfiguration clientConfiguration) {
        super(clientConfiguration);
    }

    public Void call() throws Exception {
        deleteItem(getSdbClient(), domain, item);
        return null;
    }

    private static void deleteItem(AmazonSimpleDB client, String domain, String item) {
        DeleteAttributesRequest request = new DeleteAttributesRequest(domain, item);
        client.deleteAttributes(request);
    }

}
