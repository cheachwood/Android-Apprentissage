package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class SocialGraphData extends GenericJson
{
  public Boolean blocked;
  public List<DataCircleData> circleData;
  public DataCirclePerson circlePerson;
  public Boolean muted;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SocialGraphData
 * JD-Core Version:    0.6.2
 */