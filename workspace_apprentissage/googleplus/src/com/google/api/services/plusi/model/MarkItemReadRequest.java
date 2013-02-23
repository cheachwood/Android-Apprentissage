package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class MarkItemReadRequest extends GenericJson
{
  public ApiaryFields commonFields;
  public Boolean enableTracing;
  public List<String> itemIds;
  public String networkType;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MarkItemReadRequest
 * JD-Core Version:    0.6.2
 */