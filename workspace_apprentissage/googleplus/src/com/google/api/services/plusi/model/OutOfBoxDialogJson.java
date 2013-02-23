package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OutOfBoxDialogJson extends EsJson<OutOfBoxDialog>
{
  static final OutOfBoxDialogJson INSTANCE = new OutOfBoxDialogJson();

  private OutOfBoxDialogJson()
  {
    super(OutOfBoxDialog.class, new Object[] { OutOfBoxActionJson.class, "action", OutOfBoxErrorJson.class, "error", "header", "text" });
  }

  public static OutOfBoxDialogJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OutOfBoxDialogJson
 * JD-Core Version:    0.6.2
 */