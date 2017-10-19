package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SECONDARY_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.SecPhone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Adds or updates the secondaryPhone of a person identified using it's last displayed index from the address book.
 */
public class SecondaryPhoneCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "secondaryPhone";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Specify the person's secondary phone identified by the index number used in the last person listing.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer)"
            + "command (remove/update)"
            + PREFIX_SECONDARY_PHONE + "PHONE\n"
            + "Example: " + COMMAND_WORD + " 1" + "update" + " sp/" + "00000000";

    public static final String MESSAGE_UPDATE_PERSON_PHONE_SUCCESS = "Updated Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;

    private final SecPhone secPhone;

    private final String command;

    public SecondaryPhoneCommand(Index targetIndex, String command, SecPhone secPhone) {
        this.targetIndex = targetIndex;
        this.command = command;
        this.secPhone = secPhone;
    }

    /**
     * Adds or Updates a Person's secondary phone
     */
    private Person updatePersonSecPhone(ReadOnlyPerson personToUpdateSecPhone, SecPhone update)
            throws IllegalValueException {
        Name name = personToUpdateSecPhone.getName();
        Phone phone = personToUpdateSecPhone.getPhone();
        Email email = personToUpdateSecPhone.getEmail();
        Address address = personToUpdateSecPhone.getAddress();
        Gender gender = personToUpdateSecPhone.getGender();
        SecPhone secPhone = personToUpdateSecPhone.getSecPhone();
        Set<Tag> tags = personToUpdateSecPhone.getTags();

        Person personUpdated;
        if (command.equals("update")) {
            personUpdated = new Person(name, phone, email, address, gender, update, tags);
        } else {
            personUpdated = new Person(name, phone, email, address, gender, new SecPhone(), tags);
        }

        return personUpdated;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException, IllegalValueException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUpdateSecPhone = lastShownList.get(targetIndex.getZeroBased());
        Person personUpdated = updatePersonSecPhone(personToUpdateSecPhone, secPhone);


        try {
            model.updatePerson(personToUpdateSecPhone, personUpdated);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_UPDATE_PERSON_PHONE_SUCCESS, personUpdated));
    }
}
