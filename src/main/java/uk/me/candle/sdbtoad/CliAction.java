package uk.me.candle.sdbtoad;

import com.amazonaws.ClientConfiguration;
import java.util.concurrent.Callable;

public interface CliAction extends Callable<Void> {
    static enum Actions {
        HELP("help") {
            @Override public CliAction newInstance(ClientConfiguration clientConfiguration) { return new Help(); }
        },
        LIST_DOMAINS("list-domains") {
            @Override public CliAction newInstance(ClientConfiguration clientConfiguration) { return new ListDomains(clientConfiguration); }
        },
        LIST_ITEMS("list-items") {
            @Override public CliAction newInstance(ClientConfiguration clientConfiguration) { return new ListItems(clientConfiguration); }
        },
        DELETE_ITEMS("delete-item") {
            @Override public CliAction newInstance(ClientConfiguration clientConfiguration) { return new DeleteItem(clientConfiguration); }
        },
        DELETE_ITEMS_MATCHING("delete-items-matching") {
            @Override public CliAction newInstance(ClientConfiguration clientConfiguration) { return new DeleteItemsMatching(clientConfiguration); }
        },
        ;

        private final String command;
        private Actions(String command) { this.command = command; }
        public String getCommand() { return command; }
        public abstract CliAction newInstance(ClientConfiguration clientConfiguration);
    }

}
