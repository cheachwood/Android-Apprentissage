package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OutOfBoxViewJson extends EsJson<OutOfBoxView>
{
  static final OutOfBoxViewJson INSTANCE = new OutOfBoxViewJson();

  private OutOfBoxViewJson()
  {
    super(OutOfBoxView.class, new Object[] { OutOfBoxActionJson.class, "action", OutOfBoxDialogJson.class, "dialog", OutOfBoxFieldJson.class, "field", "header", "title" });
  }

  public static OutOfBoxViewJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OutOfBoxViewJson
 * JD-Core Version:    0.6.2
 */