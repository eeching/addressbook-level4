package seedu.address.logic.commands;

import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.SecPhone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class SecondaryPhoneCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void execute_personAcceptedByModel_updateGenderSuccessful() throws Exception {
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).withSecPhone("23333333").build();

        SecPhone secPhone = new SecPhone("23333333");
        SecondaryPhoneCommand secondaryPhoneCommand = prepareCommand(INDEX_FIRST_PERSON, "update", secPhone);

        String expectedMessage = String.format(SecondaryPhoneCommand.MESSAGE_UPDATE_PERSON_PHONE_SUCCESS, updatedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(secondaryPhoneCommand, model, expectedMessage, expectedModel);
    }


    private SecondaryPhoneCommand prepareCommand(Index index, String action, SecPhone secPhone) {
        SecondaryPhoneCommand command = new SecondaryPhoneCommand(index, action, secPhone);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;

    }

    /**
     * A default model stub that have all of the methods failing.
     */

    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        public void addPersonAndUpdateSecondaryPhone(ReadOnlyPerson person) throws IllegalValueException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }
    }


    /**
     * A Model stub that always accept the person being added.
     */

    private class ModelStubAcceptingPersonSecondaryPhoneChanged extends SecondaryPhoneCommandTest.ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public void addPersonAndUpdateSecondaryPhone(ReadOnlyPerson person) throws IllegalValueException {
            personsAdded.add(new Person(person));
            Person personToUpdate = personsAdded.get(0);
            personToUpdate.setSecPhone(new SecPhone("23333333"));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
