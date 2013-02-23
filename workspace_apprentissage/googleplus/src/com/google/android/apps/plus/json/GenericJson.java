package com.google.android.apps.plus.json;

import java.lang.reflect.Method;

public class GenericJson
{
  public String toPrettyString()
  {
    Class localClass = getClass();
    try
    {
      String str2 = ((EsJson)localClass.getClassLoader().loadClass(localClass.getName() + "Json").asSubclass(EsJson.class).getMethod("getInstance", new Class[0]).invoke(null, new Object[0])).toPrettyString(this);
      str1 = str2;
      return str1;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        String str1 = super.toString();
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.json.GenericJson
 * JD-Core Version:    0.6.2
 */