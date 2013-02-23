package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class AllFocusGroupsJson extends EsJson<AllFocusGroups>
{
  static final AllFocusGroupsJson INSTANCE = new AllFocusGroupsJson();

  private AllFocusGroupsJson()
  {
    super(AllFocusGroups.class, new Object[] { PersonalCircleJson.class, "focusGroup" });
  }

  public static AllFocusGroupsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.AllFocusGroupsJson
 * JD-Core Version:    0.6.2
 */