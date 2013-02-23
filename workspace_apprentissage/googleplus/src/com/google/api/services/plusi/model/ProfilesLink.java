package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class ProfilesLink extends GenericJson
{
  public String faviconImgUrl;
  public Boolean isVerified;
  public String label;
  public Metadata metadata;
  public List<String> normalizedUri;
  public String rel;
  public String type;
  public String url;
  public List<Verification> verification;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ProfilesLink
 * JD-Core Version:    0.6.2
 */