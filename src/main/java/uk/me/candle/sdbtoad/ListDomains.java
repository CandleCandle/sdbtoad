package uk.me.candle.sdbtoad;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.model.ListDomainsRequest;
import com.amazonaws.services.simpledb.model.ListDomainsResult;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListDomains extends AbstractCliAction {
    private static final Logger LOG = LoggerFactory.getLogger(ListDomains.class);

    public ListDomains(ClientConfiguration clientConfiguration) {
        super(clientConfiguration);
    }

    public Void call() throws Exception {
        listDomains(getSdbClient());
        return null;
    }

    private static void listDomains(AmazonSimpleDB client) {
        LOG.debug("listing domains");
        List<String> domains = new ArrayList<String>();

        ListDomainsResult result;
        ListDomainsRequest req = new ListDomainsRequest();
        do {
            result = client.listDomains(req);
            domains.addAll(result.getDomainNames());
            req = new ListDomainsRequest().withNextToken(result.getNextToken());
        } while (null != result.getNextToken());

        for (String domain : domains) {
            System.out.println(domain);
        }
    }

}
