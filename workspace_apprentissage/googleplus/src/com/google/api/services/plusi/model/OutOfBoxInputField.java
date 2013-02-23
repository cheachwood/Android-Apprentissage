package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class OutOfBoxInputField extends GenericJson
{
  public Boolean hasError;
  public String helpText;
  public String id;
  public String label;
  public Boolean mandatory;
  public String type;
  public OutOfBoxFieldValue value;
  public List<OutOfBoxFieldOption> valueOption;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OutOfBoxInputField
 * JD-Core Version:    0.6.2
 */