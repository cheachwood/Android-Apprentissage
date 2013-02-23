package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.GetActivitiesResponse;
import com.google.api.services.plusi.model.GetActivitiesResponseJson;
import com.google.api.services.plusi.model.PlusEvent;
import com.google.api.services.plusi.model.PostActivityRequest;
import com.google.api.services.plusi.model.PostActivityRequestJson;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class CreateEventOperation extends PlusiOperation<PostActivityRequest, GetActivitiesResponse>
{
  private static final List<String> EVENT_EMBED_TYPES = Collections.unmodifiableList(Arrays.asList(new String[] { "PLUS_EVENT", "EVENT", "THING" }));
  private final AudienceData mAudience;
  private final String mExternalId;
  private final PlusEvent mPlusEvent;

  public CreateEventOperation(Context paramContext, EsAccount paramEsAccount, PlusEvent paramPlusEvent, AudienceData paramAudienceData, String paramString, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "postactivity", PostActivityRequestJson.getInstance(), GetActivitiesResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mPlusEvent = paramPlusEvent;
    this.mAudience = paramAudienceData;
    this.mExternalId = paramString;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.CreateEventOperation
 * JD-Core Version:    0.6.2
 */