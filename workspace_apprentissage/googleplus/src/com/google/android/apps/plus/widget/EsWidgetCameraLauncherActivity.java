package com.google.android.apps.plus.widget;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.ImageUtils;

public class EsWidgetCameraLauncherActivity extends FragmentActivity
{
  private Integer mInsertCameraPhotoRequestId;
  private EsServiceListener mServiceListener = new EsServiceListener()
  {
    public final void onInsertCameraPhotoComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      if ((EsWidgetCameraLauncherActivity.this.mInsertCameraPhotoRequestId != null) && (EsWidgetCameraLauncherActivity.this.mInsertCameraPhotoRequestId.intValue() == paramAnonymousInt))
      {
        EsWidgetCameraLauncherActivity.this.insertCameraPhoto(EsService.getLastCameraMediaLocation());
        EsWidgetCameraLauncherActivity.access$002(EsWidgetCameraLauncherActivity.this, null);
      }
    }
  };

  private void insertCameraPhoto(String paramString)
  {
    if (paramString != null)
    {
      Intent localIntent1 = Intents.getPostActivityIntent(this, null, new MediaRef(null, 0L, null, Uri.parse(paramString), MediaRef.MediaType.IMAGE));
      localIntent1.removeExtra("account");
      Intent localIntent2 = getIntent();
      if (localIntent2.hasExtra("audience"))
        localIntent1.putExtra("audience", (AudienceData)localIntent2.getParcelableExtra("audience"));
      startActivity(localIntent1);
    }
    while (true)
    {
      dismissDialog(2131361854);
      finish();
      return;
      Toast.makeText(this, getString(R.string.camera_photo_error), 1).show();
    }
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt1)
    {
    default:
    case 1:
    }
    while (true)
    {
      return;
      if (paramInt2 == -1)
      {
        showDialog(2131361854);
        this.mInsertCameraPhotoRequestId = EsService.insertCameraPhoto(this, (EsAccount)getIntent().getParcelableExtra("account"), "camera-p.jpg");
      }
      else
      {
        finish();
      }
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle == null)
      startActivityForResult(Intents.getCameraIntentPhoto$3a35108a("camera-p.jpg"), 1);
    while (true)
    {
      return;
      if (paramBundle.containsKey("insert_camera_photo_req_id"))
        this.mInsertCameraPhotoRequestId = Integer.valueOf(paramBundle.getInt("insert_camera_photo_req_id"));
    }
  }

  public Dialog onCreateDialog(int paramInt, Bundle paramBundle)
  {
    if (paramInt == 2131361854);
    for (Dialog localDialog = ImageUtils.createInsertCameraPhotoDialog(this); ; localDialog = super.onCreateDialog(paramInt, paramBundle))
      return localDialog;
  }

  public void onPause()
  {
    super.onPause();
    EsService.unregisterListener(this.mServiceListener);
  }

  public void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mServiceListener);
    if ((this.mInsertCameraPhotoRequestId != null) && (!EsService.isRequestPending(this.mInsertCameraPhotoRequestId.intValue())))
    {
      EsService.removeResult(this.mInsertCameraPhotoRequestId.intValue());
      insertCameraPhoto(EsService.getLastCameraMediaLocation());
      this.mInsertCameraPhotoRequestId = null;
    }
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mInsertCameraPhotoRequestId != null)
      paramBundle.putInt("insert_camera_photo_req_id", this.mInsertCameraPhotoRequestId.intValue());
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.widget.EsWidgetCameraLauncherActivity
 * JD-Core Version:    0.6.2
 */