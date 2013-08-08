package uk.me.candle.sdbtoad;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;

public abstract class AbstractCliAction implements CliAction {

    ClientConfiguration clientConfiguration;

    public AbstractCliAction(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
    }

    AmazonSimpleDB getSdbClient() {
	    AmazonSimpleDB client = new AmazonSimpleDBClient(clientConfiguration);
		client.setEndpoint(App.EU_WEST_1_SDB_ENDPOINT);
        return client;
    }

}
