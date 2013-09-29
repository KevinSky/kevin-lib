package kevin.lib.pool.thrift;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TSocketFactory extends BasePoolableObjectFactory {
    /** 日志记录器 */
    public static final Logger log = LoggerFactory.getLogger(TSocketFactory.class);
    private TSocketConfig config;
    private ObjectValidator<TTransport> objValidator;

    public TSocketFactory(TSocketConfig config, ObjectValidator<TTransport> objValidator) {
        this.config = config;
        this.objValidator = objValidator;
    }

    public TSocketFactory(TSocketConfig config) {
        this.config = config;
    }

    @Override
    public void destroyObject(Object obj) throws Exception {
        TSocket tsocket = (TSocket) obj;
        tsocket.close();
        log.debug("TSocket closed, [" + config.getHost() + ":" + config.getPort() + "] ");
    }

    /**
     * 
     */
    @Override
    public Object makeObject() throws Exception {
        TSocket tsocket = config.getSoTimeout() > 0 ? new TSocket(config.getHost(), config.getPort(),
                config.getSoTimeout()) : new TSocket(config.getHost(), config.getPort());

        try {
            tsocket.open();
        } catch (TTransportException e) {
            log.error("error creating TSocket to:" + config.getHost() + ":" + config.getPort(), e);
            throw e;
        }

        log.debug("TSocket created, [" + config.getHost() + ":" + config.getPort() + "]");
        return tsocket;
    }

    @Override
    public boolean validateObject(Object obj) {
        if (objValidator != null) {
            return objValidator.isValid((TSocket) obj);
        } else {
            return true;
        }
    }
}