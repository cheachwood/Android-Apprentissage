package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class OutOfBoxView extends GenericJson
{
  public List<OutOfBoxAction> action;
  public OutOfBoxDialog dialog;
  public List<OutOfBoxField> field;
  public String header;
  public String title;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OutOfBoxView
 * JD-Core Version:    0.6.2
 */