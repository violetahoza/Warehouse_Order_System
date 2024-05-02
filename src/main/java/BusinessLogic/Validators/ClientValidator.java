package BusinessLogic.Validators;

import Model.Client;
import Presentation.AddController;

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
