package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class JustificationListJson extends EsJson<JustificationList>
{
  static final JustificationListJson INSTANCE = new JustificationListJson();

  private JustificationListJson()
  {
    super(JustificationList.class, new Object[] { JustificationJson.class, "extraJustification", JustificationJson.class, "mainJustification", "title" });
  }

  public static JustificationListJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.JustificationListJson
 * JD-Core Version:    0.6.2
 */