package businessLogic.validators;

import model.Client;
import presentation.AddController;

/**
 * Validator class for validating Client objects.
 * It ensures that the client's age is greater than 0 and validates the client's email.
 */
public class ClientValidator implements Validator<Client> {
    @Override
    public void validate(Client client) {
        EmailValidator emailValidator = new EmailValidator();
        emailValidator.validate(client);
        if(client.getAge() <= 0)
        {
            AddController.showErrorDialog("Age must be greater than 0.", "Input error");
            throw new IllegalArgumentException("Age must be greater than 0.");
        }
    }
}
