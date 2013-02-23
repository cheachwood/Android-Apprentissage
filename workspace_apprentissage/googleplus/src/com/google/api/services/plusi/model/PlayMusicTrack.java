package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class PlayMusicTrack extends GenericJson
{
  public AudioObject audio;
  public String audioEmbedUrlWithSessionIndex;
  public String audioUrlWithSessionIndex;
  public MusicGroup byArtist;
  public String description;
  public String explicitType;
  public String imageUrl;
  public PlayMusicAlbum inAlbum;
  public String name;
  public String offerUrlWithSessionIndex;
  public List<Offer> offers;
  public String previewToken;
  public String purchaseStatus;
  public String storeId;
  public String url;
  public String urlWithSessionIndex;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlayMusicTrack
 * JD-Core Version:    0.6.2
 */