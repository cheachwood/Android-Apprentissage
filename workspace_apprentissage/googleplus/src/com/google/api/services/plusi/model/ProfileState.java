package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class ProfileState extends GenericJson
{
  public Integer blockCount;
  public List<String> blockReason;
  public String nameCheckState;
  public OffenderStatus offenderStatus;
  public String value;
  public Long willBeBlockedAtUsec;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ProfileState
 * JD-Core Version:    0.6.2
 */