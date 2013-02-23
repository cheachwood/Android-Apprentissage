package com.google.protobuf;

import java.util.List;

public final class UninitializedMessageException extends RuntimeException
{
  private static final long serialVersionUID = -7466929953374883507L;
  private final List<String> missingFields = null;

  public UninitializedMessageException()
  {
    super("Message was missing required fields.  (Lite runtime could not determine which fields were missing).");
  }

  public final InvalidProtocolBufferException asInvalidProtocolBufferException()
  {
    return new InvalidProtocolBufferException(getMessage());
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.protobuf.UninitializedMessageException
 * JD-Core Version:    0.6.2
 */