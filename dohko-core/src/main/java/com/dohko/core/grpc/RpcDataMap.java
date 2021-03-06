// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: service.proto

package com.dohko.core.grpc;

/**
 * Protobuf type {@code core.RpcDataMap}
 */
public  final class RpcDataMap extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:core.RpcDataMap)
    RpcDataMapOrBuilder {
  // Use RpcDataMap.newBuilder() to construct.
  private RpcDataMap(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private RpcDataMap() {
    key_ = java.util.Collections.emptyList();
    value_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private RpcDataMap(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry) {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
              key_ = new java.util.ArrayList<com.dohko.core.grpc.RpcValue>();
              mutable_bitField0_ |= 0x00000001;
            }
            key_.add(input.readMessage(com.dohko.core.grpc.RpcValue.parser(), extensionRegistry));
            break;
          }
          case 18: {
            if (!((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
              value_ = new java.util.ArrayList<com.dohko.core.grpc.RpcValue>();
              mutable_bitField0_ |= 0x00000002;
            }
            value_.add(input.readMessage(com.dohko.core.grpc.RpcValue.parser(), extensionRegistry));
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw new RuntimeException(e.setUnfinishedMessage(this));
    } catch (java.io.IOException e) {
      throw new RuntimeException(
          new com.google.protobuf.InvalidProtocolBufferException(
              e.getMessage()).setUnfinishedMessage(this));
    } finally {
      if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
        key_ = java.util.Collections.unmodifiableList(key_);
      }
      if (((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
        value_ = java.util.Collections.unmodifiableList(value_);
      }
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.dohko.core.grpc.RpcServiceProto.internal_static_core_RpcDataMap_descriptor;
  }

  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.dohko.core.grpc.RpcServiceProto.internal_static_core_RpcDataMap_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.dohko.core.grpc.RpcDataMap.class, com.dohko.core.grpc.RpcDataMap.Builder.class);
  }

  public static final int KEY_FIELD_NUMBER = 1;
  private java.util.List<com.dohko.core.grpc.RpcValue> key_;
  /**
   * <code>repeated .core.RpcValue key = 1;</code>
   */
  public java.util.List<com.dohko.core.grpc.RpcValue> getKeyList() {
    return key_;
  }
  /**
   * <code>repeated .core.RpcValue key = 1;</code>
   */
  public java.util.List<? extends com.dohko.core.grpc.RpcValueOrBuilder> 
      getKeyOrBuilderList() {
    return key_;
  }
  /**
   * <code>repeated .core.RpcValue key = 1;</code>
   */
  public int getKeyCount() {
    return key_.size();
  }
  /**
   * <code>repeated .core.RpcValue key = 1;</code>
   */
  public com.dohko.core.grpc.RpcValue getKey(int index) {
    return key_.get(index);
  }
  /**
   * <code>repeated .core.RpcValue key = 1;</code>
   */
  public com.dohko.core.grpc.RpcValueOrBuilder getKeyOrBuilder(
      int index) {
    return key_.get(index);
  }

  public static final int VALUE_FIELD_NUMBER = 2;
  private java.util.List<com.dohko.core.grpc.RpcValue> value_;
  /**
   * <code>repeated .core.RpcValue value = 2;</code>
   */
  public java.util.List<com.dohko.core.grpc.RpcValue> getValueList() {
    return value_;
  }
  /**
   * <code>repeated .core.RpcValue value = 2;</code>
   */
  public java.util.List<? extends com.dohko.core.grpc.RpcValueOrBuilder> 
      getValueOrBuilderList() {
    return value_;
  }
  /**
   * <code>repeated .core.RpcValue value = 2;</code>
   */
  public int getValueCount() {
    return value_.size();
  }
  /**
   * <code>repeated .core.RpcValue value = 2;</code>
   */
  public com.dohko.core.grpc.RpcValue getValue(int index) {
    return value_.get(index);
  }
  /**
   * <code>repeated .core.RpcValue value = 2;</code>
   */
  public com.dohko.core.grpc.RpcValueOrBuilder getValueOrBuilder(
      int index) {
    return value_.get(index);
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    for (int i = 0; i < key_.size(); i++) {
      output.writeMessage(1, key_.get(i));
    }
    for (int i = 0; i < value_.size(); i++) {
      output.writeMessage(2, value_.get(i));
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < key_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, key_.get(i));
    }
    for (int i = 0; i < value_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, value_.get(i));
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  public static com.dohko.core.grpc.RpcDataMap parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.dohko.core.grpc.RpcDataMap parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.dohko.core.grpc.RpcDataMap parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.dohko.core.grpc.RpcDataMap parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.dohko.core.grpc.RpcDataMap parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return PARSER.parseFrom(input);
  }
  public static com.dohko.core.grpc.RpcDataMap parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return PARSER.parseFrom(input, extensionRegistry);
  }
  public static com.dohko.core.grpc.RpcDataMap parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return PARSER.parseDelimitedFrom(input);
  }
  public static com.dohko.core.grpc.RpcDataMap parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return PARSER.parseDelimitedFrom(input, extensionRegistry);
  }
  public static com.dohko.core.grpc.RpcDataMap parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return PARSER.parseFrom(input);
  }
  public static com.dohko.core.grpc.RpcDataMap parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return PARSER.parseFrom(input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.dohko.core.grpc.RpcDataMap prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessage.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code core.RpcDataMap}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:core.RpcDataMap)
      com.dohko.core.grpc.RpcDataMapOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.dohko.core.grpc.RpcServiceProto.internal_static_core_RpcDataMap_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.dohko.core.grpc.RpcServiceProto.internal_static_core_RpcDataMap_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.dohko.core.grpc.RpcDataMap.class, com.dohko.core.grpc.RpcDataMap.Builder.class);
    }

    // Construct using com.dohko.core.grpc.RpcDataMap.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        getKeyFieldBuilder();
        getValueFieldBuilder();
      }
    }
    public Builder clear() {
      super.clear();
      if (keyBuilder_ == null) {
        key_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        keyBuilder_.clear();
      }
      if (valueBuilder_ == null) {
        value_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
      } else {
        valueBuilder_.clear();
      }
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.dohko.core.grpc.RpcServiceProto.internal_static_core_RpcDataMap_descriptor;
    }

    public com.dohko.core.grpc.RpcDataMap getDefaultInstanceForType() {
      return com.dohko.core.grpc.RpcDataMap.getDefaultInstance();
    }

    public com.dohko.core.grpc.RpcDataMap build() {
      com.dohko.core.grpc.RpcDataMap result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public com.dohko.core.grpc.RpcDataMap buildPartial() {
      com.dohko.core.grpc.RpcDataMap result = new com.dohko.core.grpc.RpcDataMap(this);
      int from_bitField0_ = bitField0_;
      if (keyBuilder_ == null) {
        if (((bitField0_ & 0x00000001) == 0x00000001)) {
          key_ = java.util.Collections.unmodifiableList(key_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.key_ = key_;
      } else {
        result.key_ = keyBuilder_.build();
      }
      if (valueBuilder_ == null) {
        if (((bitField0_ & 0x00000002) == 0x00000002)) {
          value_ = java.util.Collections.unmodifiableList(value_);
          bitField0_ = (bitField0_ & ~0x00000002);
        }
        result.value_ = value_;
      } else {
        result.value_ = valueBuilder_.build();
      }
      onBuilt();
      return result;
    }

    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.dohko.core.grpc.RpcDataMap) {
        return mergeFrom((com.dohko.core.grpc.RpcDataMap)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.dohko.core.grpc.RpcDataMap other) {
      if (other == com.dohko.core.grpc.RpcDataMap.getDefaultInstance()) return this;
      if (keyBuilder_ == null) {
        if (!other.key_.isEmpty()) {
          if (key_.isEmpty()) {
            key_ = other.key_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureKeyIsMutable();
            key_.addAll(other.key_);
          }
          onChanged();
        }
      } else {
        if (!other.key_.isEmpty()) {
          if (keyBuilder_.isEmpty()) {
            keyBuilder_.dispose();
            keyBuilder_ = null;
            key_ = other.key_;
            bitField0_ = (bitField0_ & ~0x00000001);
            keyBuilder_ = 
              com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders ?
                 getKeyFieldBuilder() : null;
          } else {
            keyBuilder_.addAllMessages(other.key_);
          }
        }
      }
      if (valueBuilder_ == null) {
        if (!other.value_.isEmpty()) {
          if (value_.isEmpty()) {
            value_ = other.value_;
            bitField0_ = (bitField0_ & ~0x00000002);
          } else {
            ensureValueIsMutable();
            value_.addAll(other.value_);
          }
          onChanged();
        }
      } else {
        if (!other.value_.isEmpty()) {
          if (valueBuilder_.isEmpty()) {
            valueBuilder_.dispose();
            valueBuilder_ = null;
            value_ = other.value_;
            bitField0_ = (bitField0_ & ~0x00000002);
            valueBuilder_ = 
              com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders ?
                 getValueFieldBuilder() : null;
          } else {
            valueBuilder_.addAllMessages(other.value_);
          }
        }
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.dohko.core.grpc.RpcDataMap parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.dohko.core.grpc.RpcDataMap) e.getUnfinishedMessage();
        throw e;
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<com.dohko.core.grpc.RpcValue> key_ =
      java.util.Collections.emptyList();
    private void ensureKeyIsMutable() {
      if (!((bitField0_ & 0x00000001) == 0x00000001)) {
        key_ = new java.util.ArrayList<com.dohko.core.grpc.RpcValue>(key_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilder<
        com.dohko.core.grpc.RpcValue, com.dohko.core.grpc.RpcValue.Builder, com.dohko.core.grpc.RpcValueOrBuilder> keyBuilder_;

    /**
     * <code>repeated .core.RpcValue key = 1;</code>
     */
    public java.util.List<com.dohko.core.grpc.RpcValue> getKeyList() {
      if (keyBuilder_ == null) {
        return java.util.Collections.unmodifiableList(key_);
      } else {
        return keyBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .core.RpcValue key = 1;</code>
     */
    public int getKeyCount() {
      if (keyBuilder_ == null) {
        return key_.size();
      } else {
        return keyBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .core.RpcValue key = 1;</code>
     */
    public com.dohko.core.grpc.RpcValue getKey(int index) {
      if (keyBuilder_ == null) {
        return key_.get(index);
      } else {
        return keyBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .core.RpcValue key = 1;</code>
     */
    public Builder setKey(
        int index, com.dohko.core.grpc.RpcValue value) {
      if (keyBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureKeyIsMutable();
        key_.set(index, value);
        onChanged();
      } else {
        keyBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .core.RpcValue key = 1;</code>
     */
    public Builder setKey(
        int index, com.dohko.core.grpc.RpcValue.Builder builderForValue) {
      if (keyBuilder_ == null) {
        ensureKeyIsMutable();
        key_.set(index, builderForValue.build());
        onChanged();
      } else {
        keyBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .core.RpcValue key = 1;</code>
     */
    public Builder addKey(com.dohko.core.grpc.RpcValue value) {
      if (keyBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureKeyIsMutable();
        key_.add(value);
        onChanged();
      } else {
        keyBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .core.RpcValue key = 1;</code>
     */
    public Builder addKey(
        int index, com.dohko.core.grpc.RpcValue value) {
      if (keyBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureKeyIsMutable();
        key_.add(index, value);
        onChanged();
      } else {
        keyBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .core.RpcValue key = 1;</code>
     */
    public Builder addKey(
        com.dohko.core.grpc.RpcValue.Builder builderForValue) {
      if (keyBuilder_ == null) {
        ensureKeyIsMutable();
        key_.add(builderForValue.build());
        onChanged();
      } else {
        keyBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .core.RpcValue key = 1;</code>
     */
    public Builder addKey(
        int index, com.dohko.core.grpc.RpcValue.Builder builderForValue) {
      if (keyBuilder_ == null) {
        ensureKeyIsMutable();
        key_.add(index, builderForValue.build());
        onChanged();
      } else {
        keyBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .core.RpcValue key = 1;</code>
     */
    public Builder addAllKey(
        java.lang.Iterable<? extends com.dohko.core.grpc.RpcValue> values) {
      if (keyBuilder_ == null) {
        ensureKeyIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, key_);
        onChanged();
      } else {
        keyBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .core.RpcValue key = 1;</code>
     */
    public Builder clearKey() {
      if (keyBuilder_ == null) {
        key_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        keyBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .core.RpcValue key = 1;</code>
     */
    public Builder removeKey(int index) {
      if (keyBuilder_ == null) {
        ensureKeyIsMutable();
        key_.remove(index);
        onChanged();
      } else {
        keyBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .core.RpcValue key = 1;</code>
     */
    public com.dohko.core.grpc.RpcValue.Builder getKeyBuilder(
        int index) {
      return getKeyFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .core.RpcValue key = 1;</code>
     */
    public com.dohko.core.grpc.RpcValueOrBuilder getKeyOrBuilder(
        int index) {
      if (keyBuilder_ == null) {
        return key_.get(index);  } else {
        return keyBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .core.RpcValue key = 1;</code>
     */
    public java.util.List<? extends com.dohko.core.grpc.RpcValueOrBuilder> 
         getKeyOrBuilderList() {
      if (keyBuilder_ != null) {
        return keyBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(key_);
      }
    }
    /**
     * <code>repeated .core.RpcValue key = 1;</code>
     */
    public com.dohko.core.grpc.RpcValue.Builder addKeyBuilder() {
      return getKeyFieldBuilder().addBuilder(
          com.dohko.core.grpc.RpcValue.getDefaultInstance());
    }
    /**
     * <code>repeated .core.RpcValue key = 1;</code>
     */
    public com.dohko.core.grpc.RpcValue.Builder addKeyBuilder(
        int index) {
      return getKeyFieldBuilder().addBuilder(
          index, com.dohko.core.grpc.RpcValue.getDefaultInstance());
    }
    /**
     * <code>repeated .core.RpcValue key = 1;</code>
     */
    public java.util.List<com.dohko.core.grpc.RpcValue.Builder> 
         getKeyBuilderList() {
      return getKeyFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilder<
        com.dohko.core.grpc.RpcValue, com.dohko.core.grpc.RpcValue.Builder, com.dohko.core.grpc.RpcValueOrBuilder> 
        getKeyFieldBuilder() {
      if (keyBuilder_ == null) {
        keyBuilder_ = new com.google.protobuf.RepeatedFieldBuilder<
            com.dohko.core.grpc.RpcValue, com.dohko.core.grpc.RpcValue.Builder, com.dohko.core.grpc.RpcValueOrBuilder>(
                key_,
                ((bitField0_ & 0x00000001) == 0x00000001),
                getParentForChildren(),
                isClean());
        key_ = null;
      }
      return keyBuilder_;
    }

    private java.util.List<com.dohko.core.grpc.RpcValue> value_ =
      java.util.Collections.emptyList();
    private void ensureValueIsMutable() {
      if (!((bitField0_ & 0x00000002) == 0x00000002)) {
        value_ = new java.util.ArrayList<com.dohko.core.grpc.RpcValue>(value_);
        bitField0_ |= 0x00000002;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilder<
        com.dohko.core.grpc.RpcValue, com.dohko.core.grpc.RpcValue.Builder, com.dohko.core.grpc.RpcValueOrBuilder> valueBuilder_;

    /**
     * <code>repeated .core.RpcValue value = 2;</code>
     */
    public java.util.List<com.dohko.core.grpc.RpcValue> getValueList() {
      if (valueBuilder_ == null) {
        return java.util.Collections.unmodifiableList(value_);
      } else {
        return valueBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .core.RpcValue value = 2;</code>
     */
    public int getValueCount() {
      if (valueBuilder_ == null) {
        return value_.size();
      } else {
        return valueBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .core.RpcValue value = 2;</code>
     */
    public com.dohko.core.grpc.RpcValue getValue(int index) {
      if (valueBuilder_ == null) {
        return value_.get(index);
      } else {
        return valueBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .core.RpcValue value = 2;</code>
     */
    public Builder setValue(
        int index, com.dohko.core.grpc.RpcValue value) {
      if (valueBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureValueIsMutable();
        value_.set(index, value);
        onChanged();
      } else {
        valueBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .core.RpcValue value = 2;</code>
     */
    public Builder setValue(
        int index, com.dohko.core.grpc.RpcValue.Builder builderForValue) {
      if (valueBuilder_ == null) {
        ensureValueIsMutable();
        value_.set(index, builderForValue.build());
        onChanged();
      } else {
        valueBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .core.RpcValue value = 2;</code>
     */
    public Builder addValue(com.dohko.core.grpc.RpcValue value) {
      if (valueBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureValueIsMutable();
        value_.add(value);
        onChanged();
      } else {
        valueBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .core.RpcValue value = 2;</code>
     */
    public Builder addValue(
        int index, com.dohko.core.grpc.RpcValue value) {
      if (valueBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureValueIsMutable();
        value_.add(index, value);
        onChanged();
      } else {
        valueBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .core.RpcValue value = 2;</code>
     */
    public Builder addValue(
        com.dohko.core.grpc.RpcValue.Builder builderForValue) {
      if (valueBuilder_ == null) {
        ensureValueIsMutable();
        value_.add(builderForValue.build());
        onChanged();
      } else {
        valueBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .core.RpcValue value = 2;</code>
     */
    public Builder addValue(
        int index, com.dohko.core.grpc.RpcValue.Builder builderForValue) {
      if (valueBuilder_ == null) {
        ensureValueIsMutable();
        value_.add(index, builderForValue.build());
        onChanged();
      } else {
        valueBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .core.RpcValue value = 2;</code>
     */
    public Builder addAllValue(
        java.lang.Iterable<? extends com.dohko.core.grpc.RpcValue> values) {
      if (valueBuilder_ == null) {
        ensureValueIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, value_);
        onChanged();
      } else {
        valueBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .core.RpcValue value = 2;</code>
     */
    public Builder clearValue() {
      if (valueBuilder_ == null) {
        value_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
      } else {
        valueBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .core.RpcValue value = 2;</code>
     */
    public Builder removeValue(int index) {
      if (valueBuilder_ == null) {
        ensureValueIsMutable();
        value_.remove(index);
        onChanged();
      } else {
        valueBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .core.RpcValue value = 2;</code>
     */
    public com.dohko.core.grpc.RpcValue.Builder getValueBuilder(
        int index) {
      return getValueFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .core.RpcValue value = 2;</code>
     */
    public com.dohko.core.grpc.RpcValueOrBuilder getValueOrBuilder(
        int index) {
      if (valueBuilder_ == null) {
        return value_.get(index);  } else {
        return valueBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .core.RpcValue value = 2;</code>
     */
    public java.util.List<? extends com.dohko.core.grpc.RpcValueOrBuilder> 
         getValueOrBuilderList() {
      if (valueBuilder_ != null) {
        return valueBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(value_);
      }
    }
    /**
     * <code>repeated .core.RpcValue value = 2;</code>
     */
    public com.dohko.core.grpc.RpcValue.Builder addValueBuilder() {
      return getValueFieldBuilder().addBuilder(
          com.dohko.core.grpc.RpcValue.getDefaultInstance());
    }
    /**
     * <code>repeated .core.RpcValue value = 2;</code>
     */
    public com.dohko.core.grpc.RpcValue.Builder addValueBuilder(
        int index) {
      return getValueFieldBuilder().addBuilder(
          index, com.dohko.core.grpc.RpcValue.getDefaultInstance());
    }
    /**
     * <code>repeated .core.RpcValue value = 2;</code>
     */
    public java.util.List<com.dohko.core.grpc.RpcValue.Builder> 
         getValueBuilderList() {
      return getValueFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilder<
        com.dohko.core.grpc.RpcValue, com.dohko.core.grpc.RpcValue.Builder, com.dohko.core.grpc.RpcValueOrBuilder> 
        getValueFieldBuilder() {
      if (valueBuilder_ == null) {
        valueBuilder_ = new com.google.protobuf.RepeatedFieldBuilder<
            com.dohko.core.grpc.RpcValue, com.dohko.core.grpc.RpcValue.Builder, com.dohko.core.grpc.RpcValueOrBuilder>(
                value_,
                ((bitField0_ & 0x00000002) == 0x00000002),
                getParentForChildren(),
                isClean());
        value_ = null;
      }
      return valueBuilder_;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:core.RpcDataMap)
  }

  // @@protoc_insertion_point(class_scope:core.RpcDataMap)
  private static final com.dohko.core.grpc.RpcDataMap DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.dohko.core.grpc.RpcDataMap();
  }

  public static com.dohko.core.grpc.RpcDataMap getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<RpcDataMap>
      PARSER = new com.google.protobuf.AbstractParser<RpcDataMap>() {
    public RpcDataMap parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      try {
        return new RpcDataMap(input, extensionRegistry);
      } catch (RuntimeException e) {
        if (e.getCause() instanceof
            com.google.protobuf.InvalidProtocolBufferException) {
          throw (com.google.protobuf.InvalidProtocolBufferException)
              e.getCause();
        }
        throw e;
      }
    }
  };

  public static com.google.protobuf.Parser<RpcDataMap> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<RpcDataMap> getParserForType() {
    return PARSER;
  }

  public com.dohko.core.grpc.RpcDataMap getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

