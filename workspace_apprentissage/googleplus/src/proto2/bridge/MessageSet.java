package proto2.bridge;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite.ExtendableBuilder;
import com.google.protobuf.GeneratedMessageLite.ExtendableMessage;
import com.google.protobuf.GeneratedMessageLite.ExtendableMessage.ExtensionWriter;
import java.io.IOException;
import java.io.ObjectStreamException;

public final class MessageSet extends GeneratedMessageLite.ExtendableMessage<MessageSet>
  implements MessageSetOrBuilder
{
  private static final MessageSet defaultInstance = new MessageSet();
  private static final long serialVersionUID;
  private byte memoizedIsInitialized = -1;
  private int memoizedSerializedSize = -1;

  private MessageSet()
  {
  }

  private MessageSet(Builder paramBuilder)
  {
    super(paramBuilder);
  }

  public static MessageSet getDefaultInstance()
  {
    return defaultInstance;
  }

  public final int getSerializedSize()
  {
    int i = this.memoizedSerializedSize;
    if (i != -1);
    int j;
    for (int k = i; ; k = j)
    {
      return k;
      j = 0 + extensionsSerializedSizeAsMessageSet();
      this.memoizedSerializedSize = j;
    }
  }

  public final boolean isInitialized()
  {
    int i = 1;
    int j = this.memoizedIsInitialized;
    if (j != -1)
      if (j != i);
    while (true)
    {
      return i;
      i = 0;
      continue;
      if (!extensionsAreInitialized())
      {
        this.memoizedIsInitialized = 0;
        i = 0;
      }
      else
      {
        this.memoizedIsInitialized = i;
      }
    }
  }

  protected final Object writeReplace()
    throws ObjectStreamException
  {
    return super.writeReplace();
  }

  public final void writeTo(CodedOutputStream paramCodedOutputStream)
    throws IOException
  {
    getSerializedSize();
    newMessageSetExtensionWriter().writeUntil(536870912, paramCodedOutputStream);
  }

  public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<MessageSet, Builder>
    implements MessageSetOrBuilder
  {
    private MessageSet buildPartial()
    {
      return new MessageSet(this, (byte)0);
    }

    private Builder clone()
    {
      return new Builder().mergeFrom(buildPartial());
    }

    private Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
      throws IOException
    {
      int i;
      do
      {
        i = paramCodedInputStream.readTag();
        switch (i)
        {
        default:
        case 0:
        }
      }
      while (parseUnknownField(paramCodedInputStream, paramExtensionRegistryLite, i));
      return this;
    }

    public final boolean isInitialized()
    {
      if (!extensionsAreInitialized());
      for (boolean bool = false; ; bool = true)
        return bool;
    }

    public final Builder mergeFrom(MessageSet paramMessageSet)
    {
      if (paramMessageSet == MessageSet.getDefaultInstance());
      while (true)
      {
        return this;
        mergeExtensionFields(paramMessageSet);
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     proto2.bridge.MessageSet
 * JD-Core Version:    0.6.2
 */