package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetNotificationsRequestJson extends EsJson<GetNotificationsRequest>
{
  static final GetNotificationsRequestJson INSTANCE = new GetNotificationsRequestJson();

  private GetNotificationsRequestJson()
  {
    super(GetNotificationsRequest.class, arrayOfObject);
  }

  public static GetNotificationsRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetNotificationsRequestJson
 * JD-Core Version:    0.6.2
 */