package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SECONDARY_PHONE;

import java.util.StringTokenizer;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SecondaryPhoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.SecPhone;

/**
 * Parses input arguments and creates a new secphone Command object
 */
public class SecondaryPhoneCommandParser implements Parser<SecondaryPhoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GenderCommand
     * and returns a GenderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SecondaryPhoneCommand parse(String args) throws ParseException {
        try {
            StringTokenizer st = new StringTokenizer(args);
            Index index = ParserUtil.parseIndex(st.nextToken());
            String command = st.nextToken();
            if (st.hasMoreTokens()) {
                String phoneInput = st.nextToken();
                String prefix = phoneInput.substring(0, 3);

                if (!prefix.equals(PREFIX_SECONDARY_PHONE.getPrefix())) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SecondaryPhoneCommand.MESSAGE_USAGE));
                }
                SecPhone phone = new SecPhone(phoneInput.substring(3));
                return new SecondaryPhoneCommand(index, "update", phone);

            } else {
                return new SecondaryPhoneCommand(index, "remove", new SecPhone());
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SecondaryPhoneCommand.MESSAGE_USAGE));
        }
    }

}
