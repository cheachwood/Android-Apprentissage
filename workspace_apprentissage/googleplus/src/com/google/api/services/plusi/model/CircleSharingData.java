package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class CircleSharingData extends GenericJson
{
  public String circleName;
  public String description;
  public List<String> memberKey;
  public List<CommonPerson> personForDisplay;
  public String sharerGender;
  public String sharerName;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CircleSharingData
 * JD-Core Version:    0.6.2
 */