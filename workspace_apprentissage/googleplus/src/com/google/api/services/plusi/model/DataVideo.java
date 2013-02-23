package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class DataVideo extends GenericJson
{
  public String downloadUrl;
  public Long durationMillis;
  public String id;
  public String status;
  public List<DataVideoStream> stream;
  public DataTimedTextMetaData timedText;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataVideo
 * JD-Core Version:    0.6.2
 */