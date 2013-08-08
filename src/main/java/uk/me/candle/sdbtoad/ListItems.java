package uk.me.candle.sdbtoad;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.amazonaws.services.simpledb.model.SelectResult;
import java.util.ArrayList;
import java.util.List;
import org.kohsuke.args4j.Argument;

public class ListItems extends AbstractCliAction {

    @Argument(index = 0, hidden = false, usage = "The name of the domain to list")
    private String domainToList;

    public ListItems(ClientConfiguration clientConfiguration) {
        super(clientConfiguration);
    }

    public Void call() throws Exception {
        listItems(getSdbClient(), domainToList);
        return null;
    }

    static List<Item> getItemList(AmazonSimpleDB client, String domain) {
        List<Item> items = new ArrayList<Item>();

        SelectRequest request = new SelectRequest("select * from `" + domain + "`");
        SelectResult result;
        do {
            result = client.select(request);
            items.addAll(result.getItems());
            request = request.withNextToken(result.getNextToken());
        } while (null != result.getNextToken());

        return items;
    }
    private static void listItems(AmazonSimpleDB client, String domain) {
        for (Item s : getItemList(client, domain)) {
            System.out.println(s.getName());
        }
    }
}
