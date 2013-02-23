package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DeleteActivityRequestJson extends EsJson<DeleteActivityRequest>
{
  static final DeleteActivityRequestJson INSTANCE = new DeleteActivityRequestJson();

  private DeleteActivityRequestJson()
  {
    super(DeleteActivityRequest.class, new Object[] { "activityId", ApiaryFieldsJson.class, "commonFields", "enableTracing" });
  }

  public static DeleteActivityRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DeleteActivityRequestJson
 * JD-Core Version:    0.6.2
 */