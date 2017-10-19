package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's secPhone in the address book.
 * Guarantees: immutable.
 */
public class SecPhone {

    public static final String MESSAGE_PHONE_CONSTRAINTS =
            "Phone numbers can only contain numbers, and should be at least 3 digits long or it can be empty";

    public static final String PHONE_VALIDATION_REGEX = "\\d{3,}";

    public final String value;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public SecPhone(String phone) throws IllegalValueException {
        if (phone.equals("")) {
            this.value = "";
        } else {
            requireNonNull(phone);
            String trimmedPhone = phone.trim();
            if (!isValidPhone(trimmedPhone)) {
                throw new IllegalValueException(MESSAGE_PHONE_CONSTRAINTS);
            }
            this.value = trimmedPhone;
        }
    }

    public SecPhone() throws IllegalValueException {
        this.value = "";
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(PHONE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SecPhone // instanceof handles nulls
                && this.value.equals(((SecPhone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
