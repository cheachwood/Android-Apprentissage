package com.google.android.apps.plus.api;

import com.google.wireless.tacotruck.proto.Network.Response.ErrorCode;

public final class ServerException extends ProtocolException
{
  private static final long serialVersionUID = 1L;
  private final Network.Response.ErrorCode mTacoTruckErrorCode;

  public ServerException(Network.Response.ErrorCode paramErrorCode, String paramString)
  {
    super(paramErrorCode.getNumber(), paramString);
    this.mTacoTruckErrorCode = paramErrorCode;
  }

  public final Network.Response.ErrorCode getTacoTruckErrorCode()
  {
    return this.mTacoTruckErrorCode;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.ServerException
 * JD-Core Version:    0.6.2
 */