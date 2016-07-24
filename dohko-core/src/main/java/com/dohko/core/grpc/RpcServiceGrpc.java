package com.dohko.core.grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 0.14.0)",
    comments = "Source: service.proto")
public class RpcServiceGrpc {

  private RpcServiceGrpc() {}

  public static final String SERVICE_NAME = "core.RpcService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<com.dohko.core.grpc.RpcRequest,
      com.dohko.core.grpc.RpcResponse> METHOD_EXECUTE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "core.RpcService", "execute"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.dohko.core.grpc.RpcRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.dohko.core.grpc.RpcResponse.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RpcServiceStub newStub(io.grpc.Channel channel) {
    return new RpcServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RpcServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new RpcServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static RpcServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new RpcServiceFutureStub(channel);
  }

  /**
   */
  public static interface RpcService {

    /**
     */
    public void execute(com.dohko.core.grpc.RpcRequest request,
        io.grpc.stub.StreamObserver<com.dohko.core.grpc.RpcResponse> responseObserver);
  }

  @io.grpc.ExperimentalApi
  public static abstract class AbstractRpcService implements RpcService, io.grpc.BindableService {

    @java.lang.Override
    public void execute(com.dohko.core.grpc.RpcRequest request,
        io.grpc.stub.StreamObserver<com.dohko.core.grpc.RpcResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_EXECUTE, responseObserver);
    }

    @java.lang.Override public io.grpc.ServerServiceDefinition bindService() {
      return RpcServiceGrpc.bindService(this);
    }
  }

  /**
   */
  public static interface RpcServiceBlockingClient {

    /**
     */
    public com.dohko.core.grpc.RpcResponse execute(com.dohko.core.grpc.RpcRequest request);
  }

  /**
   */
  public static interface RpcServiceFutureClient {

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.dohko.core.grpc.RpcResponse> execute(
        com.dohko.core.grpc.RpcRequest request);
  }

  public static class RpcServiceStub extends io.grpc.stub.AbstractStub<RpcServiceStub>
      implements RpcService {
    private RpcServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RpcServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RpcServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RpcServiceStub(channel, callOptions);
    }

    @java.lang.Override
    public void execute(com.dohko.core.grpc.RpcRequest request,
        io.grpc.stub.StreamObserver<com.dohko.core.grpc.RpcResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_EXECUTE, getCallOptions()), request, responseObserver);
    }
  }

  public static class RpcServiceBlockingStub extends io.grpc.stub.AbstractStub<RpcServiceBlockingStub>
      implements RpcServiceBlockingClient {
    private RpcServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RpcServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RpcServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RpcServiceBlockingStub(channel, callOptions);
    }

    @java.lang.Override
    public com.dohko.core.grpc.RpcResponse execute(com.dohko.core.grpc.RpcRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_EXECUTE, getCallOptions(), request);
    }
  }

  public static class RpcServiceFutureStub extends io.grpc.stub.AbstractStub<RpcServiceFutureStub>
      implements RpcServiceFutureClient {
    private RpcServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RpcServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RpcServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RpcServiceFutureStub(channel, callOptions);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<com.dohko.core.grpc.RpcResponse> execute(
        com.dohko.core.grpc.RpcRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_EXECUTE, getCallOptions()), request);
    }
  }

  private static final int METHODID_EXECUTE = 0;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final RpcService serviceImpl;
    private final int methodId;

    public MethodHandlers(RpcService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_EXECUTE:
          serviceImpl.execute((com.dohko.core.grpc.RpcRequest) request,
              (io.grpc.stub.StreamObserver<com.dohko.core.grpc.RpcResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static io.grpc.ServerServiceDefinition bindService(
      final RpcService serviceImpl) {
    return io.grpc.ServerServiceDefinition.builder(SERVICE_NAME)
        .addMethod(
          METHOD_EXECUTE,
          asyncUnaryCall(
            new MethodHandlers<
              com.dohko.core.grpc.RpcRequest,
              com.dohko.core.grpc.RpcResponse>(
                serviceImpl, METHODID_EXECUTE)))
        .build();
  }
}
