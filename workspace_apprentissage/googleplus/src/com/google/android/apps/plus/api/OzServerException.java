package com.google.android.apps.plus.api;

import java.util.HashMap;

public final class OzServerException extends ProtocolException
{
  private static HashMap<String, Integer> sErrorCodeMap;
  private static final long serialVersionUID = -6479734361667965512L;
  private final ApiaryErrorResponse mError;

  public OzServerException(ApiaryErrorResponse paramApiaryErrorResponse)
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.OzServerException
 * JD-Core Version:    0.6.2
 */