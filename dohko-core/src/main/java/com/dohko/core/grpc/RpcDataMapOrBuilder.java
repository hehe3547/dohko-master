// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: service.proto

package com.dohko.core.grpc;

public interface RpcDataMapOrBuilder extends
    // @@protoc_insertion_point(interface_extends:core.RpcDataMap)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated .core.RpcValue key = 1;</code>
   */
  java.util.List<com.dohko.core.grpc.RpcValue> 
      getKeyList();
  /**
   * <code>repeated .core.RpcValue key = 1;</code>
   */
  com.dohko.core.grpc.RpcValue getKey(int index);
  /**
   * <code>repeated .core.RpcValue key = 1;</code>
   */
  int getKeyCount();
  /**
   * <code>repeated .core.RpcValue key = 1;</code>
   */
  java.util.List<? extends com.dohko.core.grpc.RpcValueOrBuilder> 
      getKeyOrBuilderList();
  /**
   * <code>repeated .core.RpcValue key = 1;</code>
   */
  com.dohko.core.grpc.RpcValueOrBuilder getKeyOrBuilder(
      int index);

  /**
   * <code>repeated .core.RpcValue value = 2;</code>
   */
  java.util.List<com.dohko.core.grpc.RpcValue> 
      getValueList();
  /**
   * <code>repeated .core.RpcValue value = 2;</code>
   */
  com.dohko.core.grpc.RpcValue getValue(int index);
  /**
   * <code>repeated .core.RpcValue value = 2;</code>
   */
  int getValueCount();
  /**
   * <code>repeated .core.RpcValue value = 2;</code>
   */
  java.util.List<? extends com.dohko.core.grpc.RpcValueOrBuilder> 
      getValueOrBuilderList();
  /**
   * <code>repeated .core.RpcValue value = 2;</code>
   */
  com.dohko.core.grpc.RpcValueOrBuilder getValueOrBuilder(
      int index);
}
