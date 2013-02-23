package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ApiaryFieldsJson extends EsJson<ApiaryFields>
{
  static final ApiaryFieldsJson INSTANCE = new ApiaryFieldsJson();

  private ApiaryFieldsJson()
  {
    super(ApiaryFields.class, new Object[] { "appVersion", "effectiveUser" });
  }

  public static ApiaryFieldsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ApiaryFieldsJson
 * JD-Core Version:    0.6.2
 */