package com.google.android.apps.plus.api;

import java.io.IOException;

public class ProtocolException extends IOException
{
  private static final long serialVersionUID = -7095525015550203782L;
  private final int mErrorCode;

  public ProtocolException()
  {
    this.mErrorCode = 0;
  }

  public ProtocolException(int paramInt, String paramString)
  {
    super(paramString);
    this.mErrorCode = paramInt;
  }

  public ProtocolException(String paramString)
  {
    this(0, paramString);
  }

  public final int getErrorCode()
  {
    return this.mErrorCode;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.ProtocolException
 * JD-Core Version:    0.6.2
 */