package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EventActionUserListJson extends EsJson<EventActionUserList>
{
  static final EventActionUserListJson INSTANCE = new EventActionUserListJson();

  private EventActionUserListJson()
  {
    super(EventActionUserList.class, new Object[] { EntityEventsDataChangedFieldsJson.class, "changedFields", "eventAction", "inviterOid", "isNew", EmbedsPersonJson.class, "user", "userOid" });
  }

  public static EventActionUserListJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventActionUserListJson
 * JD-Core Version:    0.6.2
 */