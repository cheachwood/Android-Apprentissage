package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class DataAlbum extends GenericJson
{
  public String albumAcl;
  public String albumType;
  public String audience;
  public List<Person> audienceMember;
  public String authkey;
  public List<String> childId;
  public DataPhoto cover;
  public Boolean coverSelectedByUser;
  public String deprecatedAudienceString;
  public String description;
  public String downloadUrl;
  public Long entityVersion;
  public List<String> eventId;
  public String id;
  public Boolean isReshareDisabled;
  public Long modTime;
  public Integer namedShapeCount;
  public Integer numGaiaRecipients;
  public Integer numGeoPhotos;
  public Integer numNonGaiaRecipients;
  public Integer numSoftDeletedPhotos;
  public DataUser owner;
  public Integer photoCount;
  public List<PlusEvent> plusEvent;
  public String provider;
  public String pwaManageUrl;
  public List<DataPhoto> sample;
  public Boolean showGeoInfo;
  public Boolean showVisibilityInspector;
  public String timestampSeconds;
  public String title;
  public Integer unnamedShapeCount;
  public List<Update> update;
  public String visibility;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataAlbum
 * JD-Core Version:    0.6.2
 */