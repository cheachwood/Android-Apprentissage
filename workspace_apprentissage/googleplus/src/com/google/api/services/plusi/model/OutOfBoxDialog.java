package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class OutOfBoxDialog extends GenericJson
{
  public List<OutOfBoxAction> action;
  public OutOfBoxError error;
  public String header;
  public String text;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OutOfBoxDialog
 * JD-Core Version:    0.6.2
 */