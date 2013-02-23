package com.google.android.apps.plus.service;

import com.google.android.apps.plus.network.HttpOperation;

public final class ServiceResult
{
  private final int mErrorCode;
  private final Exception mException;
  private final String mReasonPhrase;

  public ServiceResult()
  {
    this(200, "Ok", null);
  }

  public ServiceResult(int paramInt, String paramString, Exception paramException)
  {
    this.mErrorCode = paramInt;
    this.mReasonPhrase = paramString;
    this.mException = paramException;
  }

  public ServiceResult(HttpOperation paramHttpOperation)
  {
    this.mErrorCode = paramHttpOperation.getErrorCode();
    this.mReasonPhrase = paramHttpOperation.getReasonPhrase();
    this.mException = paramHttpOperation.getException();
  }

  public ServiceResult(boolean paramBoolean)
  {
  }

  public final int getErrorCode()
  {
    return this.mErrorCode;
  }

  public final Exception getException()
  {
    return this.mException;
  }

  public final String getReasonPhrase()
  {
    return this.mReasonPhrase;
  }

  public final boolean hasError()
  {
    if (this.mErrorCode != 200);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final String toString()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = Integer.valueOf(this.mErrorCode);
    arrayOfObject[1] = this.mReasonPhrase;
    arrayOfObject[2] = this.mException;
    return String.format("ServiceResult(errorCode=%d, reasonPhrase=%s, exception=%s)", arrayOfObject);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.ServiceResult
 * JD-Core Version:    0.6.2
 */