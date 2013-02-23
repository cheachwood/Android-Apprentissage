package com.google.android.apps.plus.api;

import com.google.protobuf.MessageLite;
import com.google.wireless.tacotruck.proto.Network.Request.Type;

final class EsRequest
{
  private final MessageLite mMessage;
  private final Network.Request.Type mRequestType;

  public final MessageLite getMessage()
  {
    return this.mMessage;
  }

  public final Network.Request.Type getType()
  {
    return this.mRequestType;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.EsRequest
 * JD-Core Version:    0.6.2
 */