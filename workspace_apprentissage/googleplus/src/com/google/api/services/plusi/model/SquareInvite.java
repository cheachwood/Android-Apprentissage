package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class SquareInvite extends GenericJson
{
  public String communityId;
  public String description;
  public String imageUrl;
  public String name;
  public Thumbnail proxiedImage;
  public String squareId;
  public List<String> successfulInviteeEmail;
  public List<String> successfulInviteeObfuscatedGaiaId;
  public String url;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SquareInvite
 * JD-Core Version:    0.6.2
 */