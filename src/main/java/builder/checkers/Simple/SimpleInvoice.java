package builder.checkers.Simple;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleInvoice {
    private final List<SimpleItem> items = new ArrayList<>();

    public void addItem(SimpleItem item) {
        items.add(item);
    }

    public boolean removeItem(SimpleItem item) {
        return items.remove(item);
    }

    public double getValueToPay() {
        return items.stream().mapToDouble(SimpleItem::getValue).sum();
    }
}
