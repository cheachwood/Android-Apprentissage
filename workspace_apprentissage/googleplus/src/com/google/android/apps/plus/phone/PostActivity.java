package com.google.android.apps.plus.phone;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.content.DbEmotishareMetadata;
import com.google.android.apps.plus.content.DbLocation;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import com.google.android.apps.plus.fragments.PostFragment;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.GalleryUtils;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.ImageUtils.InsertCameraPhotoDialogDisplayer;
import com.google.android.apps.plus.util.MediaStoreUtils;
import java.util.ArrayList;
import java.util.Iterator;

public class PostActivity extends EsFragmentActivity
  implements DialogInterface.OnClickListener, ImageUtils.InsertCameraPhotoDialogDisplayer
{
  protected EsAccount mAccount;
  protected PostFragment mFragment;
  private boolean mShakeDetectorWasRunning;
  private View mShareButton;

  private boolean buildPostFragment(EsAccount paramEsAccount)
  {
    this.mAccount = paramEsAccount;
    Bundle localBundle = getPostFragmentArguments();
    if (localBundle == null);
    for (boolean bool = false; ; bool = true)
    {
      return bool;
      localBundle.putParcelable("account", paramEsAccount);
      PostFragment localPostFragment = new PostFragment();
      localPostFragment.setArguments(localBundle);
      FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
      localFragmentTransaction.add(R.id.post_container, localPostFragment, "post_tag");
      localFragmentTransaction.commit();
    }
  }

  protected final EsAccount getAccount()
  {
    return this.mAccount;
  }

  protected Bundle getPostFragmentArguments()
  {
    Bundle localBundle = new Bundle();
    Intent localIntent = getIntent();
    String str1 = localIntent.getAction();
    if (EsLog.isLoggable("PostActivity", 3))
      Log.d("PostActivity", "Intent action: " + str1);
    localBundle.putString("action", str1);
    if (localIntent.hasExtra("android.intent.extra.TEXT"))
    {
      String str4 = localIntent.getStringExtra("android.intent.extra.TEXT");
      if (str4 == null)
      {
        CharSequence localCharSequence = localIntent.getCharSequenceExtra("android.intent.extra.TEXT");
        if (localCharSequence != null)
          str4 = localCharSequence.toString();
      }
      if (EsLog.isLoggable("PostActivity", 3))
        Log.d("PostActivity", "    EXTRA_TEXT: " + str4);
      localBundle.putString("android.intent.extra.TEXT", str4);
    }
    if (localIntent.hasExtra("activity_id"))
    {
      if (EsLog.isLoggable("PostActivity", 3))
        Log.d("PostActivity", "    EXTRA_ACTIVITY_ID: " + localIntent.getStringExtra("activity_id"));
      String str3 = localIntent.getStringExtra("activity_id");
      localBundle.putString("activity_id", str3);
    }
    if (localIntent.hasExtra("location"))
    {
      DbLocation localDbLocation = (DbLocation)localIntent.getParcelableExtra("location");
      if (EsLog.isLoggable("PostActivity", 3))
        Log.d("PostActivity", "    EXTRA_LOCATION: " + localDbLocation);
      localBundle.putParcelable("location", localDbLocation);
    }
    if (localIntent.hasExtra("typed_image_embed"))
    {
      DbEmotishareMetadata localDbEmotishareMetadata = (DbEmotishareMetadata)localIntent.getParcelableExtra("typed_image_embed");
      if (EsLog.isLoggable("PostActivity", 3))
        Log.d("PostActivity", "    EXTRA_EMOTISHARE: " + localDbEmotishareMetadata);
      localBundle.putParcelable("typed_image_embed", localDbEmotishareMetadata);
    }
    boolean bool1 = localIntent.hasExtra("android.intent.extra.STREAM");
    int i = 0;
    int j = 0;
    ArrayList localArrayList1;
    ArrayList localArrayList2;
    if (bool1)
    {
      localArrayList1 = new ArrayList();
      if ("android.intent.action.SEND_MULTIPLE".equals(str1))
      {
        localArrayList2 = localIntent.getExtras().getParcelableArrayList("android.intent.extra.STREAM");
        if (localArrayList2.size() > 250)
        {
          int m = R.string.post_max_photos;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Integer.valueOf(250);
          Toast.makeText(this, getString(m, arrayOfObject), 1).show();
          localBundle = null;
        }
      }
    }
    Parcelable localParcelable1;
    label689: 
    do
    {
      return localBundle;
      Iterator localIterator = localArrayList2.iterator();
      while (true)
      {
        boolean bool3 = localIterator.hasNext();
        j = 0;
        if (!bool3)
          break;
        Parcelable localParcelable3 = (Parcelable)localIterator.next();
        if ((localParcelable3 instanceof MediaRef))
        {
          localArrayList1.add((MediaRef)localParcelable3);
        }
        else if ((localParcelable3 instanceof Uri))
        {
          Uri localUri2 = (Uri)localParcelable3;
          if (MediaStoreUtils.isMediaStoreUri(localUri2))
            localArrayList1.add(new MediaRef(this.mAccount.getGaiaId(), 0L, null, localUri2, MediaRef.MediaType.IMAGE));
          else if (GalleryUtils.isGalleryContentUri(localUri2))
            localArrayList1.add(new MediaRef(null, 0L, localUri2.toString(), null, MediaRef.MediaType.IMAGE));
          else if ("content".equals(localUri2.getScheme()))
            localArrayList1.add(new MediaRef(this.mAccount.getGaiaId(), 0L, null, localUri2, MediaRef.MediaType.IMAGE));
          else if ("file".equals(localUri2.getScheme()))
            localArrayList1.add(new MediaRef(this.mAccount.getGaiaId(), 0L, null, localUri2, MediaRef.MediaType.IMAGE));
          else
            i = 1;
        }
      }
      localParcelable1 = localIntent.getExtras().getParcelable("android.intent.extra.STREAM");
      if (!(localParcelable1 instanceof MediaRef))
        break;
      localArrayList1.add((MediaRef)localParcelable1);
      localBundle.putParcelableArrayList("android.intent.extra.STREAM", localArrayList1);
      if (localIntent.hasExtra("insert_photo_request_id"))
      {
        int k = localIntent.getIntExtra("insert_photo_request_id", 0);
        localBundle.putInt("insert_photo_request_id", k);
      }
      if (localIntent.hasExtra("audience"))
      {
        Parcelable localParcelable2 = localIntent.getParcelableExtra("audience");
        localBundle.putParcelable("audience", localParcelable2);
      }
    }
    while (i == 0);
    if (j != 0);
    for (String str2 = getString(R.string.post_invalid_photos_unsupported); ; str2 = getString(R.string.post_invalid_photos_remote))
    {
      Toast.makeText(this, str2, 1).show();
      break;
      boolean bool2 = localParcelable1 instanceof Uri;
      i = 0;
      j = 0;
      if (!bool2)
        break label689;
      Uri localUri1 = (Uri)localParcelable1;
      if (MediaStoreUtils.isMediaStoreUri(localUri1))
      {
        localArrayList1.add(new MediaRef(this.mAccount.getGaiaId(), 0L, null, localUri1, MediaRef.MediaType.IMAGE));
        i = 0;
        j = 0;
        break label689;
      }
      if (GalleryUtils.isGalleryContentUri(localUri1))
      {
        localArrayList1.add(new MediaRef(null, 0L, localUri1.toString(), null, MediaRef.MediaType.IMAGE));
        i = 0;
        j = 0;
        break label689;
      }
      if ("content".equals(localUri1.getScheme()))
      {
        localArrayList1.add(new MediaRef(this.mAccount.getGaiaId(), 0L, null, localUri1, MediaRef.MediaType.IMAGE));
        i = 0;
        j = 0;
        break label689;
      }
      if ("file".equals(localUri1.getScheme()))
      {
        localArrayList1.add(new MediaRef(this.mAccount.getGaiaId(), 0L, null, localUri1, MediaRef.MediaType.IMAGE));
        i = 0;
        j = 0;
        break label689;
      }
      j = 1;
      i = 1;
      break label689;
    }
  }

  public OzViews getViewForLogging()
  {
    return OzViews.COMPOSE;
  }

  protected int getViewId()
  {
    return R.layout.post_activity;
  }

  public final void hideInsertCameraPhotoDialog()
  {
    dismissDialog(2131361854);
  }

  public void invalidateMenu()
  {
    if (this.mShareButton != null)
    {
      this.mShareButton.setEnabled(this.mFragment.canPost());
      this.mShareButton.invalidate();
    }
  }

  public final void onAttachFragment(Fragment paramFragment)
  {
    super.onAttachFragment(paramFragment);
    if ((paramFragment instanceof PostFragment))
      this.mFragment = ((PostFragment)paramFragment);
  }

  public void onBackPressed()
  {
    this.mFragment.onDiscard(false);
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    switch (paramInt)
    {
    default:
    case -1:
    case -2:
    case -3:
    }
    while (true)
    {
      return;
      startActivity(Intents.getLocationSettingActivityIntent());
      continue;
      this.mFragment.setLocationChecked(false);
      continue;
      EsAccountsData.saveLocationDialogSeenPreference(this, this.mAccount, true);
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(getViewId());
    View localView = findViewById(R.id.cancel_button);
    if (localView != null)
      localView.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          if (PostActivity.this.mFragment != null)
            PostActivity.this.mFragment.onDiscard(true);
        }
      });
    this.mShareButton = findViewById(R.id.share_button);
    if (this.mShareButton != null)
      this.mShareButton.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          if (PostActivity.this.mFragment != null)
            PostActivity.this.mFragment.post();
        }
      });
    EsAccount localEsAccount;
    String str2;
    if (paramBundle == null)
    {
      Intent localIntent1 = getIntent();
      if (localIntent1.hasExtra("account"))
      {
        if (!buildPostFragment((EsAccount)localIntent1.getParcelableExtra("account")))
          finish();
      }
      else
      {
        while (true)
        {
          return;
          localEsAccount = EsService.getActiveAccount(this);
          if ((localEsAccount == null) || (!localEsAccount.hasGaiaId()))
            break label278;
          if (buildPostFragment(localEsAccount))
            break;
          finish();
        }
        String str1 = localIntent1.getStringExtra("com.google.android.apps.plus.SENDER_ID");
        boolean bool = "com.google.android.apps.plus.GOOGLE_BIRTHDAY_POST".equals(localIntent1.getAction());
        if (((bool) || (!TextUtils.isEmpty(str1))) && (!TextUtils.equals(localEsAccount.getGaiaId(), str1)))
        {
          int i = R.string.share_account_warning;
          Object[] arrayOfObject = new Object[1];
          if (!bool)
            break label268;
          str2 = localEsAccount.getDisplayName();
          label219: arrayOfObject[0] = str2;
          Toast.makeText(this, getString(i, arrayOfObject), 1).show();
        }
      }
      recordLaunchEvent();
    }
    while (true)
    {
      ShakeDetector localShakeDetector = ShakeDetector.getInstance(getApplicationContext());
      if (localShakeDetector == null)
        break;
      this.mShakeDetectorWasRunning = localShakeDetector.stop();
      break;
      label268: str2 = localEsAccount.getName();
      break label219;
      label278: Intent localIntent2 = getIntent();
      localIntent2.setPackage(PostActivity.class.getPackage().getName());
      startActivity(Intents.getAccountsActivityIntent(this, localIntent2));
      finish();
      break;
      this.mAccount = ((EsAccount)paramBundle.getParcelable("account"));
    }
  }

  public Dialog onCreateDialog(int paramInt, Bundle paramBundle)
  {
    Object localObject;
    switch (paramInt)
    {
    default:
      localObject = null;
    case 29341608:
    case 30875012:
    case 2131361854:
    }
    while (true)
    {
      return localObject;
      AlertDialog.Builder localBuilder2 = new AlertDialog.Builder(this);
      localBuilder2.setMessage(R.string.location_provider_disabled);
      localBuilder2.setPositiveButton(R.string.yes, this);
      localBuilder2.setNegativeButton(R.string.no, this);
      localObject = localBuilder2.create();
      continue;
      AlertDialog.Builder localBuilder1 = new AlertDialog.Builder(this);
      localBuilder1.setTitle(R.string.post_location_dialog_title);
      localBuilder1.setMessage(R.string.post_location_dialog_message);
      localBuilder1.setNeutralButton(17039370, this);
      localBuilder1.setCancelable(false);
      localObject = localBuilder1.create();
      continue;
      localObject = ImageUtils.createInsertCameraPhotoDialog(this);
    }
  }

  protected void onDestroy()
  {
    super.onDestroy();
    if (this.mShakeDetectorWasRunning)
    {
      ShakeDetector localShakeDetector = ShakeDetector.getInstance(getApplicationContext());
      if (localShakeDetector != null)
        localShakeDetector.start();
    }
  }

  protected void onResume()
  {
    super.onResume();
    if (SignOnActivity.finishIfNoAccount(this, this.mAccount));
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mAccount != null)
      paramBundle.putParcelable("account", this.mAccount);
  }

  protected void recordLaunchEvent()
  {
    recordUserAction(OzActions.PLATFORM_OPEN_SHAREBOX);
  }

  public final void showInsertCameraPhotoDialog()
  {
    showDialog(2131361854);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.PostActivity
 * JD-Core Version:    0.6.2
 */