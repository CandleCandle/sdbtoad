package uk.me.candle.sdbtoad;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.model.BatchDeleteAttributesRequest;
import com.amazonaws.services.simpledb.model.DeletableItem;
import com.amazonaws.services.simpledb.model.Item;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import org.kohsuke.args4j.Argument;

public class DeleteItemsMatching extends AbstractCliAction {

    @Argument(metaVar = "domain", index = 0, hidden = false, usage = "The name of the domain from which to delete items")
    private String domainToList;

    @Argument(metaVar = "regex", index = 1, hidden = false, usage = "Java regex to match items")
    private String matchRegex;

    public DeleteItemsMatching(ClientConfiguration clientConfiguration) {
        super(clientConfiguration);
    }

    public Void call() throws Exception {
        deleteItemsMatching(getSdbClient(), domainToList, matchRegex);
        return null;
    }

    private static void deleteItemsMatching(AmazonSimpleDB client, String domain, final String regex) {
        Collection<Item> filtered = Collections2.filter(ListItems.getItemList(client, domain), new Predicate<Item>() {
            Pattern pattern = Pattern.compile(regex);
            @Override public boolean apply(Item input) {
                return pattern.matcher(input.getName()).matches();
            }
        });
        List<DeletableItem> items = Lists.transform(Lists.newArrayList(filtered), new Function<Item, DeletableItem>() {
            @Override public DeletableItem apply(Item input) {
                return new DeletableItem().withName(input.getName());
            }
        });
        int start = 0;
        int max = 25;
        System.out.println("found " + items.size() + " items");
        do {
            int end = Math.min(start+max, items.size()-start);
            System.out.println("start: " + start + " max: " + max + " items: " + items.size() + " end: " + end + " items-start: " + (items.size()-start) + " start+max: " + (start+max));
            System.out.println("deleting from " + start + " to " + end);
            BatchDeleteAttributesRequest request = new BatchDeleteAttributesRequest(domain, items.subList(start, end));
            client.batchDeleteAttributes(request);
            start = end;
        } while (items.size()-start > 0);

    }
}
