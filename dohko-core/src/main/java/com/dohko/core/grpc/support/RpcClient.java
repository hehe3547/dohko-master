package com.dohko.core.grpc.support;

import com.dohko.core.PlatResultInfo;
import com.dohko.core.base.Request;
import com.dohko.core.base.Response;
import com.dohko.core.grpc.RpcRequest;
import com.dohko.core.grpc.RpcResponse;
import com.dohko.core.grpc.RpcServiceGrpc;
import com.dohko.core.grpc.util.RpcDataUtils;
import com.dohko.core.services.support.BaseClient;
import com.dohko.core.services.support.LoadBalanceClient;
import com.dohko.core.util.LogUtils;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by xiangbin on 2016/6/17.
 */
public abstract class RpcClient extends LoadBalanceClient {

    private Logger logger = LoggerFactory.getLogger(RpcClient.class);

    @Override
    public void execute(Request request, Referer referer, final BaseClient.ResponseListener listener) {
        try {
            RefererImpl impl = (RefererImpl)referer;
            RpcRequest rpcRequest = RpcDataUtils.toRpcRequest(request);
            StreamObserver<RpcResponse> responseObserver = new StreamObserver<RpcResponse>() {
                @Override
                public void onNext(RpcResponse response) {
                    logger.info("Finished trip with {0} points. Passed {1} features. Travelled {2} meters. It took {3} seconds.");
                    listener.onCompleted(RpcDataUtils.toResponse(response));
                }

                @Override
                public void onError(Throwable t) {
                    Status status = Status.fromThrowable(t);
                    logger.error("RecordRoute Failed: {0}", status);
                    // finishLatch.countDown();
                }

                @Override
                public void onCompleted() {
                    logger.info("Finished RecordRoute");
                    // finishLatch.countDown();
                }
            };
            impl.getServiceStub().execute(rpcRequest, responseObserver);
        } catch (StatusRuntimeException e) {
            logger.error("RPC failed: {0}", e.getStatus());
        }
    }

    @Override
    public Response execute(Request request, Referer referer) {
        try {
            RefererImpl impl = (RefererImpl)referer;
            RpcRequest rpcRequest = RpcDataUtils.toRpcRequest(request);
            //RpcResponse rpcResponse = impl.getFutureStub().execute(rpcRequest).get();
            RpcResponse rpcResponse = impl.getBlcokingStub().execute(rpcRequest);
            return RpcDataUtils.toResponse(rpcResponse);
        } catch (StatusRuntimeException e) {
            LogUtils.errorException(e, logger);
            return Response.Builder.create(request.getTranceId(), PlatResultInfo.SYSTEM_ERROR);
//        } catch (InterruptedException e) {
//            LogUtils.errorException(e, logger);
//            e.printStackTrace();
//            return Response.Builder.error(PlatResultInfo.SYSTEM_ERROR);
//        } catch (ExecutionException e) {
//            LogUtils.errorException(e, logger);
//            e.printStackTrace();
//            return Response.Builder.error(PlatResultInfo.SYSTEM_ERROR);
        }
    }

    protected class RefererImpl implements Referer {
        private final String host;
        private final int port;
        ManagedChannel channel;
        RpcServiceGrpc.RpcService serviceStub;
        RpcServiceGrpc.RpcServiceFutureStub futureStub;
        RpcServiceGrpc.RpcServiceBlockingStub blcokingStub;
        public RefererImpl(String host, int port) {
            this.host = host;
            this.port = port;
        }

        public void start() {
            channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
            serviceStub = RpcServiceGrpc.newStub(channel);
            futureStub = RpcServiceGrpc.newFutureStub(channel);
            blcokingStub = RpcServiceGrpc.newBlockingStub(channel);
        }

        public void shutdown() {
            try {
                LogUtils.info("start shutdown host [" + host + "] port [" + port + "]", logger);
                if (channel != null) {
                    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
                }
            } catch (InterruptedException e) {
                LogUtils.errorException(e, logger);
            }
        }

        public RpcServiceGrpc.RpcServiceBlockingStub getBlcokingStub() {
            return blcokingStub;
        }

        @Override
        public String toString() {
            return host + ":" + port;
        }

        public ManagedChannel getChannel() {
            return channel;
        }

        public RpcServiceGrpc.RpcService getServiceStub() {
            return serviceStub;
        }

        public RpcServiceGrpc.RpcServiceFutureStub getFutureStub() {
            return futureStub;
        }

        public int getPort() {
            return port;
        }

        public String getHost() {
            return host;
        }
    }
}
