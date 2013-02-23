package com.google.android.apps.plus.api;

import com.google.android.apps.plus.json.EsJson;
import com.google.android.apps.plus.json.GenericJson;
import java.util.ArrayList;

public class ApiaryErrorResponse extends GenericJson
{
  public static final String AGE_RESTRICTED = "AGE_RESTRICTED";
  public static final String APP_UPGRADE_REQUIRED = "APP_UPGRADE_REQUIRED";
  public static final String BAD_PROFILE = "BAD_PROFILE";
  public static final String ES_BLOCKED_FOR_DOMAIN_BY_ADMIN = "ES_BLOCKED_FOR_DOMAIN_BY_ADMIN";
  public static final String ES_STREAM_POST_RESTRICTIONS_NOT_SUPPORTED = "ES_STREAM_POST_RESTRICTIONS_NOT_SUPPORTED";
  public static final String FORBIDDEN = "FORBIDDEN";
  public static final String HAS_PLUSONE_OPT_IN_REQUIRED = "HAS_PLUSONE_OPT_IN_REQUIRED";
  public static final String INVALID_ACTION_TOKEN = "INVALID_ACTION_TOKEN";
  public static final String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
  public static final String INVALID_VALUE = "INVALID_VALUE";
  public static final EsJson<ApiaryErrorResponse> JSON = EsJson.buildJson(ApiaryErrorResponse.class, arrayOfObject);
  public static final String NETWORK_ERROR = "NETWORK_ERROR";
  public static final String NOT_FOUND = "NOT_FOUND";
  public static final String OUT_OF_BOX_REQUIRED = "OUT_OF_BOX_REQUIRED";
  public static final String PERMISSION_ERROR = "PERMISSION_ERROR";
  public static final String SERVICE_UNAVAILABLE = "SERVICE_UNAVAILABLE";
  public ApiaryError error;

  static
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = ApiaryError.JSON;
    arrayOfObject[1] = "error";
  }

  public String getErrorMessage()
  {
    String str;
    if (this.error != null)
      if (this.error.message != null)
        str = this.error.message;
    while (true)
    {
      return str;
      if (this.error.errors != null)
      {
        ArrayList localArrayList = this.error.errors;
        int i = 0;
        int j = localArrayList.size();
        while (true)
        {
          if (i >= j)
            break label92;
          ApiaryError localApiaryError = (ApiaryError)localArrayList.get(i);
          if (localApiaryError.message != null)
          {
            str = localApiaryError.message;
            break;
          }
          i++;
        }
      }
      label92: str = null;
    }
  }

  public String getErrorType()
  {
    String str;
    if (this.error != null)
      if (this.error.reason != null)
        str = this.error.reason;
    while (true)
    {
      return str;
      if (this.error.errors != null)
      {
        ArrayList localArrayList = this.error.errors;
        int i = 0;
        int j = localArrayList.size();
        while (true)
        {
          if (i >= j)
            break label92;
          ApiaryError localApiaryError = (ApiaryError)localArrayList.get(i);
          if (localApiaryError.reason != null)
          {
            str = localApiaryError.reason;
            break;
          }
          i++;
        }
      }
      label92: str = null;
    }
  }

  public String toString()
  {
    return JSON.toPrettyString(this);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.ApiaryErrorResponse
 * JD-Core Version:    0.6.2
 */