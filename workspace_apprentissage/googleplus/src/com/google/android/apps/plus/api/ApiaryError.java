package com.google.android.apps.plus.api;

import com.google.android.apps.plus.json.EsJson;
import com.google.android.apps.plus.json.GenericJson;
import java.util.ArrayList;

public class ApiaryError extends GenericJson
{
  public static final EsJson<ApiaryError> JSON = EsJson.buildJson(ApiaryError.class, arrayOfObject);
  private static final EsJson<ApiaryError> NESTED_JSON = EsJson.buildJson(ApiaryError.class, new Object[] { "code", "domain", "reason", "message" });
  public Integer code;
  public String domain;
  public ArrayList<ApiaryError> errors;
  public String message;
  public String reason;

  static
  {
    Object[] arrayOfObject = new Object[6];
    arrayOfObject[0] = "code";
    arrayOfObject[1] = "domain";
    arrayOfObject[2] = "reason";
    arrayOfObject[3] = "message";
    arrayOfObject[4] = NESTED_JSON;
    arrayOfObject[5] = "errors";
  }

  public String toString()
  {
    return JSON.toPrettyString(this);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.ApiaryError
 * JD-Core Version:    0.6.2
 */