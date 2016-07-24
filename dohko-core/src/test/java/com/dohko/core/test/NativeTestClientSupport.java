package com.dohko.core.test;

import com.dohko.core.base.Request;
import com.dohko.core.base.Response;
import com.dohko.core.services.Executor;
import com.dohko.core.services.support.BaseClient;
import org.springframework.stereotype.Component;

/**
 * Created by xiangbin on 2016/6/20.
 */
@Component("core_nativeClientSupport")
public class NativeTestClientSupport extends BaseClient  {

    @Override
    protected void execute(Request request, ResponseListener listener) {

    }

    @Override
    protected Response execute(Request request) {
        Executor executor = appContext.getBean(Executor.class);
        return executor.execute(request);
    }
}
