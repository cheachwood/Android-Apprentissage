package com.google.api.services.pos.model;

import com.google.android.apps.plus.json.EsJson;

public final class ListJson extends EsJson<List>
{
  static final ListJson INSTANCE = new ListJson();

  private ListJson()
  {
    super(List.class, new Object[] { "continuationToken", PlusonesJson.class, "items", "kind" });
  }

  public static ListJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.pos.model.ListJson
 * JD-Core Version:    0.6.2
 */