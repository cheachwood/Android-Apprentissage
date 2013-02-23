package com.google.android.apps.plus.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.apps.plus.content.AccountSettingsData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.api.services.plusi.model.MobileOutOfBoxResponse;

public class OobIntents
  implements Parcelable
{
  public static final Parcelable.Creator<OobIntents> CREATOR = new Parcelable.Creator()
  {
  };
  private final boolean mInitial;
  private final int mStep;

  private OobIntents(int paramInt, boolean paramBoolean)
  {
    this.mStep = paramInt;
    this.mInitial = paramBoolean;
  }

  public OobIntents(Parcel paramParcel)
  {
    this.mStep = paramParcel.readInt();
    if (paramParcel.readInt() != 0);
    for (boolean bool = true; ; bool = false)
    {
      this.mInitial = bool;
      return;
    }
  }

  public static Intent getInitialIntent(Context paramContext, EsAccount paramEsAccount, MobileOutOfBoxResponse paramMobileOutOfBoxResponse, AccountSettingsData paramAccountSettingsData, String paramString)
  {
    Intent localIntent;
    if (paramMobileOutOfBoxResponse != null)
      localIntent = Intents.getOutOfBoxActivityIntent(paramContext, paramEsAccount, new OobIntents(0, true), paramMobileOutOfBoxResponse, paramString);
    while (true)
    {
      return localIntent;
      int i = nextStep(paramContext, paramEsAccount, paramAccountSettingsData, 0);
      if (i == 5)
        localIntent = null;
      else
        localIntent = getStepIntent(paramContext, paramEsAccount, paramAccountSettingsData, new OobIntents(i, true));
    }
  }

  private static Intent getStepIntent(Context paramContext, EsAccount paramEsAccount, AccountSettingsData paramAccountSettingsData, OobIntents paramOobIntents)
  {
    Intent localIntent;
    switch (paramOobIntents.mStep)
    {
    default:
      localIntent = null;
    case 1:
    case 2:
    case 3:
    case 4:
    }
    while (true)
    {
      return localIntent;
      localIntent = Intents.getOobSelectPlusPageActivityIntent(paramContext, paramEsAccount, paramAccountSettingsData, paramOobIntents);
      continue;
      localIntent = Intents.getOobSuggestedPeopleActivityIntent(paramContext, paramEsAccount, paramOobIntents);
      continue;
      localIntent = Intents.getOobContactsSyncIntent(paramContext, paramEsAccount, paramOobIntents);
      continue;
      localIntent = Intents.getOobInstantUploadIntent(paramContext, paramEsAccount, paramOobIntents);
    }
  }

  private static int nextStep(Context paramContext, EsAccount paramEsAccount, AccountSettingsData paramAccountSettingsData, int paramInt)
  {
    int i = 5;
    while (true)
    {
      switch (paramInt)
      {
      case 4:
      default:
      case 0:
      case 1:
      case 2:
      case 3:
      }
      while (true)
      {
        return i;
        if (paramAccountSettingsData != null)
        {
          i = 1;
        }
        else
        {
          paramInt = 1;
          break;
          if ((!EsAccountsData.hasSeenWarmWelcome(paramContext, paramEsAccount)) && (!paramEsAccount.isPlusPage()))
          {
            i = 2;
          }
          else
          {
            paramInt = 2;
            break;
            if ((EsAccountsData.needContactSyncOob(paramContext, paramEsAccount)) && (!paramEsAccount.isPlusPage()))
            {
              i = 3;
            }
            else
            {
              paramInt = 3;
              break;
              if ((!EsAccountsData.needInstantUploadOob(paramContext, paramEsAccount)) || (paramEsAccount.isPlusPage()))
                break label131;
              i = 4;
            }
          }
        }
      }
      label131: paramInt = 4;
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public final Intent getNextIntent(Context paramContext, EsAccount paramEsAccount, AccountSettingsData paramAccountSettingsData)
  {
    int i = nextStep(paramContext, paramEsAccount, paramAccountSettingsData, this.mStep);
    Intent localIntent;
    if (i == 5)
    {
      localIntent = null;
      return localIntent;
    }
    if (this.mStep == 0);
    for (boolean bool = true; ; bool = false)
    {
      localIntent = getStepIntent(paramContext, paramEsAccount, paramAccountSettingsData, new OobIntents(i, bool));
      break;
    }
  }

  public final boolean isInitialIntent()
  {
    return this.mInitial;
  }

  public final boolean isLastIntent(Context paramContext, EsAccount paramEsAccount, AccountSettingsData paramAccountSettingsData)
  {
    if (nextStep(paramContext, paramEsAccount, paramAccountSettingsData, this.mStep) == 5);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.mStep);
    if (this.mInitial);
    for (int i = 1; ; i = 0)
    {
      paramParcel.writeInt(i);
      return;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.OobIntents
 * JD-Core Version:    0.6.2
 */