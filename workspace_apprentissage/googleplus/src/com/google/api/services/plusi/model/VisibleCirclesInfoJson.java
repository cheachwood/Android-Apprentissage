package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class VisibleCirclesInfoJson extends EsJson<VisibleCirclesInfo>
{
  static final VisibleCirclesInfoJson INSTANCE = new VisibleCirclesInfoJson();

  private VisibleCirclesInfoJson()
  {
    super(VisibleCirclesInfo.class, new Object[] { "allCirclesVisible", "displayIncomingEdges", "networkVisibility", PersonalCircleJson.class, "personalCircle" });
  }

  public static VisibleCirclesInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.VisibleCirclesInfoJson
 * JD-Core Version:    0.6.2
 */