package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EditActivityResponseJson extends EsJson<EditActivityResponse>
{
  static final EditActivityResponseJson INSTANCE = new EditActivityResponseJson();

  private EditActivityResponseJson()
  {
    super(EditActivityResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", UpdateJson.class, "update" });
  }

  public static EditActivityResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EditActivityResponseJson
 * JD-Core Version:    0.6.2
 */