package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class AddressRangeProto extends GenericJson
{
  public List<Integer> number;
  public List<Float> parameter;
  public Boolean parameterIsSynthesized;
  public String prefix;
  public Boolean sameParity;
  public String suffix;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.AddressRangeProto
 * JD-Core Version:    0.6.2
 */