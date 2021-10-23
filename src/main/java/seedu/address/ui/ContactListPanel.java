package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.contact.Contact;

/**
 * Panel containing the list of contacts.
 */
public class ContactListPanel extends UiPart<Region> {
    private static final String FXML = "ContactListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ContactListPanel.class);

    @FXML
    private ListView<Contact> contactListView;

    /**
     * Creates a {@code ContactListPanel} with the given {@code ObservableList}.
     */
    public ContactListPanel(ObservableList<Contact> contactList, MainWindow mainWindow) {
        super(FXML);
        contactListView.setItems(contactList);
        contactListView.setCellFactory(listView -> new ContactListViewCell(mainWindow));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Contact} using a {@code ContactCard}.
     */
    class ContactListViewCell extends ListCell<Contact> {
        private MainWindow mainWindow;

        public ContactListViewCell (MainWindow mainWindow) {
            this.mainWindow = mainWindow;
        }

        @Override
        protected void updateItem(Contact contact, boolean empty) {
            super.updateItem(contact, empty);

            if (empty || contact == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ContactCard(contact, getIndex() + 1, mainWindow).getRoot());
            }
        }
    }

}
