package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class DataShape extends GenericJson
{
  public DataRect32 bounds;
  public DataUser creator;
  public String id;
  public DataRectRelative relativeBounds;
  public String source;
  public String status;
  public List<DataUser> suggestion;
  public List<String> url;
  public DataUser user;
  public DataVideoThumbnail videoThumbnail;
  public Boolean viewerCanApprove;
  public Boolean viewerCanEdit;
  public Boolean viewerCanSuggest;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataShape
 * JD-Core Version:    0.6.2
 */