package businessLogic.validators;

/**
 * The Validator interface defines a method for validating objects of type T.
 * @param <T> The type of object to be validated.
 */
public interface Validator<T> {
    public void validate(T t);
}
