package DataAccess;

import java.util.logging.Logger;

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> tClass;
    public AbstractDAO(Class<T> tClass){
        this.tClass = tClass;
    }

}
