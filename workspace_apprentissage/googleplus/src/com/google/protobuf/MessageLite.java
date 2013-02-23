package com.google.protobuf;

import java.io.IOException;

public abstract interface MessageLite extends MessageLiteOrBuilder
{
  public abstract int getSerializedSize();

  public abstract Builder newBuilderForType();

  public abstract Builder toBuilder();

  public abstract byte[] toByteArray();

  public abstract ByteString toByteString();

  public abstract void writeTo(CodedOutputStream paramCodedOutputStream)
    throws IOException;

  public static abstract interface Builder extends MessageLiteOrBuilder, Cloneable
  {
    public abstract MessageLite build();

    public abstract MessageLite buildPartial();

    public abstract Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws IOException;

    public abstract Builder mergeFrom(byte[] paramArrayOfByte)
      throws InvalidProtocolBufferException;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.protobuf.MessageLite
 * JD-Core Version:    0.6.2
 */