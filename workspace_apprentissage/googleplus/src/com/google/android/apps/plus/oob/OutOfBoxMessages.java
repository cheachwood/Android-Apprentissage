package com.google.android.apps.plus.oob;

import com.google.api.services.plusi.model.OutOfBoxInputField;

public final class OutOfBoxMessages
{
  public static boolean areEqual(Object paramObject1, Object paramObject2)
  {
    boolean bool;
    if (paramObject1 == null)
      if (paramObject2 == null)
        bool = true;
    while (true)
    {
      return bool;
      bool = false;
      continue;
      bool = paramObject1.equals(paramObject2);
    }
  }

  public static OutOfBoxInputField copyWithoutValue(OutOfBoxInputField paramOutOfBoxInputField)
  {
    OutOfBoxInputField localOutOfBoxInputField;
    if (paramOutOfBoxInputField == null)
      localOutOfBoxInputField = null;
    while (true)
    {
      return localOutOfBoxInputField;
      localOutOfBoxInputField = new OutOfBoxInputField();
      localOutOfBoxInputField.id = paramOutOfBoxInputField.id;
      localOutOfBoxInputField.type = paramOutOfBoxInputField.type;
      localOutOfBoxInputField.label = paramOutOfBoxInputField.label;
      localOutOfBoxInputField.mandatory = paramOutOfBoxInputField.mandatory;
      localOutOfBoxInputField.hasError = paramOutOfBoxInputField.hasError;
      localOutOfBoxInputField.valueOption = paramOutOfBoxInputField.valueOption;
      localOutOfBoxInputField.helpText = paramOutOfBoxInputField.helpText;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.oob.OutOfBoxMessages
 * JD-Core Version:    0.6.2
 */