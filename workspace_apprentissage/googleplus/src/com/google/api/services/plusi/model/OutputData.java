package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.math.BigInteger;
import java.util.List;

public final class OutputData extends GenericJson
{
  public List<LoggedCircle> circle;
  public List<LoggedCircleMember> circleMember;
  public String containerPropertyId;
  public LoggedCircle filterCircle;
  public Integer filterType;
  public Integer getStartedStepIndex;
  public String interest;
  public List<LoggedPhoto> photo;
  public List<BigInteger> photoAlbumId;
  public LoggedProfile profile;
  public String streamSort;
  public List<LoggedSuggestionInfo> suggestionInfo;
  public Integer tab;
  public List<LoggedUpdate> update;
  public List<UserInfo> userInfo;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OutputData
 * JD-Core Version:    0.6.2
 */