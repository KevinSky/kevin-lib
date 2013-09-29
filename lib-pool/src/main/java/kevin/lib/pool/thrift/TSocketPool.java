package kevin.lib.pool.thrift;

import kevin.lib.pool.CommonPool;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TSocketPool extends CommonPool<TSocket> {
    private static final Logger log = LoggerFactory.getLogger(TSocketPool.class);

    private TSocketConfig config;
    private ObjectValidator<TTransport> objValidator;

    public TSocketConfig getConfig() {
        return config;
    }

    public void setConfig(TSocketConfig config) {
        this.config = config;
    }

    public ObjectValidator<TTransport> getObjValidator() {
        return objValidator;
    }

    public void setObjValidator(ObjectValidator<TTransport> objValidator) {
        this.objValidator = objValidator;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        PoolableObjectFactory poolableObjectFactory = new TSocketFactory(config, objValidator);
        setPoolableObjectFactory(poolableObjectFactory);
        super.afterPropertiesSet();
    }

    @Override
    public void returnCon(TSocket socket) {
        try {
            if (socket.isOpen())
                objectPool.returnObject(socket);
        } catch (Exception e) {
            log.error("returnCon() error", e);
        }
    }
    
}
