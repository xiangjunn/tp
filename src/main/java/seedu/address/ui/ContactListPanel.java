package seedu.address.ui;

import java.util.function.Supplier;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactDisplaySetting;

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
    public ContactListPanel(ObservableList<Contact> contactList, MainWindow mainWindow,
                            Supplier<ContactDisplaySetting> displaySettingSupplier) {
        super(FXML);
        contactListView.setItems(contactList);
        contactListView.setCellFactory(listView -> new ContactListViewCell(mainWindow, displaySettingSupplier));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Contact} using a {@code ContactCard}.
     */
    class ContactListViewCell extends ListCell<Contact> {
        private final MainWindow mainWindow;
        private final Supplier<ContactDisplaySetting> displaySettingSupplier;

        public ContactListViewCell(
            MainWindow mainWindow, Supplier<ContactDisplaySetting> displaySettingSupplier) {
            this.mainWindow = mainWindow;
            this.displaySettingSupplier = displaySettingSupplier;
        }

        @Override
        protected void updateItem(Contact contact, boolean empty) {
            super.updateItem(contact, empty);

            if (empty || contact == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ContactCard(contact, getIndex() + 1, mainWindow,
                    displaySettingSupplier.get()).getRoot());
            }
        }
    }

}
