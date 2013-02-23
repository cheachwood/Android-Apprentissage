package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class DeleteCircleRequest extends GenericJson
{
  public List<DataCircleId> circleId;
  public ApiaryFields commonFields;
  public Boolean enableTracing;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DeleteCircleRequest
 * JD-Core Version:    0.6.2
 */