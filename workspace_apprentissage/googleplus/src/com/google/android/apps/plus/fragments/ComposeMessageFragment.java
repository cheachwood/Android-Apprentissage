package com.google.android.apps.plus.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.phone.Intents.PhotosIntentBuilder;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.ImageUtils.InsertCameraPhotoDialogDisplayer;
import com.google.wireless.realtimechat.proto.Client.Typing.Type;

public class ComposeMessageFragment extends Fragment
  implements TextView.OnEditorActionListener, ChoosePhotoDialog.PhotoHandler
{
  private boolean mAllowSendImages;
  private boolean mAllowSendMessage;
  private Client.Typing.Type mCurrentTypingStatus;
  private Handler mHandler = new Handler();
  private Integer mInsertCameraPhotoRequestId;
  private Listener mListener;
  private EditText mMessageText;
  private View mSendButton;
  private EsServiceListener mServiceListener = new EsServiceListener()
  {
    public final void onInsertCameraPhotoComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      if ((ComposeMessageFragment.this.mInsertCameraPhotoRequestId != null) && (ComposeMessageFragment.this.mInsertCameraPhotoRequestId.intValue() == paramAnonymousInt))
      {
        ComposeMessageFragment.this.insertCameraPhoto(EsService.getLastCameraMediaLocation());
        ComposeMessageFragment.access$002(ComposeMessageFragment.this, null);
      }
    }
  };
  private long mTimeSinceLastTypingEvent;
  private Runnable mTypingTimeoutRunnable = new Runnable()
  {
    public final void run()
    {
      ComposeMessageFragment.access$200(ComposeMessageFragment.this);
    }
  };

  private void dispatchSendMessageEvent()
  {
    if (this.mListener != null)
      if (this.mMessageText != null)
        break label47;
    label47: for (String str = null; ; str = this.mMessageText.getText().toString().trim())
    {
      if ((str != null) && (str.length() > 0))
        this.mListener.onSendTextMessage(str);
      this.mMessageText.setText("");
      return;
    }
  }

  private void dispatchSendPhotoEvent(String paramString, int paramInt)
  {
    if (this.mListener != null)
      this.mListener.onSendPhoto(paramString, paramInt);
  }

  private void dispatchTypingStatusChangedEvent(Client.Typing.Type paramType)
  {
    if (this.mListener != null)
      this.mListener.onTypingStatusChanged(paramType);
  }

  private EsAccount getAccount()
  {
    return (EsAccount)getActivity().getIntent().getParcelableExtra("account");
  }

  private void insertCameraPhoto(String paramString)
  {
    FragmentActivity localFragmentActivity = getActivity();
    if (paramString != null)
      dispatchSendPhotoEvent(paramString, 2);
    while (true)
    {
      if ((localFragmentActivity instanceof ImageUtils.InsertCameraPhotoDialogDisplayer))
        ((ImageUtils.InsertCameraPhotoDialogDisplayer)localFragmentActivity).hideInsertCameraPhotoDialog();
      return;
      Toast.makeText(localFragmentActivity, getString(R.string.camera_photo_error), 1).show();
    }
  }

  private void updateSendButtonState()
  {
    String str;
    if (this.mMessageText == null)
    {
      str = null;
      if ((!this.mAllowSendMessage) || (str == null) || (str.length() <= 0))
        break label73;
    }
    label73: for (boolean bool = true; ; bool = false)
    {
      if ((this.mSendButton != null) && (this.mSendButton.isEnabled() != bool))
        this.mSendButton.setEnabled(bool);
      return;
      str = this.mMessageText.getText().toString().trim();
      break;
    }
  }

  public final void allowSendingImages(boolean paramBoolean)
  {
    this.mAllowSendImages = paramBoolean;
    View localView;
    if (getView() != null)
    {
      localView = getView().findViewById(R.id.photo_button);
      if (!paramBoolean)
        break label33;
      localView.setVisibility(0);
    }
    while (true)
    {
      return;
      label33: localView.setVisibility(8);
    }
  }

  public final void doPickPhotoFromAlbums(int paramInt)
  {
    Intents.PhotosIntentBuilder localPhotosIntentBuilder = Intents.newAlbumsActivityIntentBuilder(getActivity());
    localPhotosIntentBuilder.setAccount(getAccount()).setPersonId(getAccount().getPersonId()).setPhotosHome(Boolean.valueOf(true)).setShowCameraAlbum(Boolean.valueOf(true)).setPhotoPickerMode(Integer.valueOf(1)).setPhotoPickerTitleResourceId(Integer.valueOf(R.string.photo_picker_album_label_messenger));
    startActivityForResult(localPhotosIntentBuilder.build(), 1);
  }

  public final void doRepositionCoverPhoto()
  {
  }

  public final void doTakePhoto()
  {
    getActivity();
    startActivityForResult(Intents.getCameraIntentPhoto$3a35108a("camera-p.jpg"), 2);
  }

  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt1)
    {
    default:
    case 1:
    case 2:
    }
    while (true)
    {
      return;
      if ((paramInt2 == -1) && (paramIntent != null))
      {
        String str = paramIntent.getStringExtra("photo_url");
        if (str != null)
        {
          dispatchSendPhotoEvent(str, paramInt1);
          continue;
          if (paramInt2 == -1)
          {
            FragmentActivity localFragmentActivity = getActivity();
            if ((localFragmentActivity instanceof ImageUtils.InsertCameraPhotoDialogDisplayer))
              ((ImageUtils.InsertCameraPhotoDialogDisplayer)localFragmentActivity).showInsertCameraPhotoDialog();
            this.mInsertCameraPhotoRequestId = EsService.insertCameraPhoto(localFragmentActivity, getAccount(), "camera-p.jpg");
          }
        }
      }
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    if ((paramBundle != null) && (paramBundle.containsKey("insert_camera_photo_req_id")))
      this.mInsertCameraPhotoRequestId = Integer.valueOf(paramBundle.getInt("insert_camera_photo_req_id"));
    View localView1 = paramLayoutInflater.inflate(R.layout.compose_message, paramViewGroup);
    this.mSendButton = localView1.findViewById(R.id.send_button);
    this.mCurrentTypingStatus = Client.Typing.Type.CLEAR;
    this.mMessageText = ((EditText)localView1.findViewById(R.id.message_text));
    this.mMessageText.addTextChangedListener(new TextWatcher()
    {
      public final void afterTextChanged(Editable paramAnonymousEditable)
      {
      }

      public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
      }

      public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        ComposeMessageFragment.this.updateSendButtonState();
        ComposeMessageFragment.access$200(ComposeMessageFragment.this);
      }
    });
    this.mMessageText.setOnEditorActionListener(this);
    localView1.findViewById(R.id.send_button).setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        ComposeMessageFragment.this.dispatchSendMessageEvent();
      }
    });
    View localView2 = localView1.findViewById(R.id.photo_button);
    localView2.setOnCreateContextMenuListener(this);
    if (this.mAllowSendImages)
      localView2.setVisibility(0);
    while (true)
    {
      localView2.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          ComposeMessageFragment.access$500(ComposeMessageFragment.this);
        }
      });
      this.mAllowSendMessage = false;
      updateSendButtonState();
      return localView1;
      localView2.setVisibility(8);
    }
  }

  public boolean onEditorAction(TextView paramTextView, int paramInt, KeyEvent paramKeyEvent)
  {
    if (((paramInt == 4) || ((paramKeyEvent != null) && (paramKeyEvent.getAction() == 0))) && (this.mMessageText.getText().length() > 0))
      dispatchSendMessageEvent();
    return true;
  }

  public final void onPause()
  {
    super.onPause();
    EsService.unregisterListener(this.mServiceListener);
  }

  public final void onResume()
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

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mInsertCameraPhotoRequestId != null)
      paramBundle.putInt("insert_camera_photo_req_id", this.mInsertCameraPhotoRequestId.intValue());
  }

  public final void requestFocus()
  {
    this.mMessageText.requestFocus();
  }

  public final void setAllowSendMessage(boolean paramBoolean)
  {
    this.mAllowSendMessage = paramBoolean;
    updateSendButtonState();
  }

  public final void setListener(Listener paramListener)
  {
    this.mListener = paramListener;
  }

  public static abstract interface Listener
  {
    public abstract void onSendPhoto(String paramString, int paramInt);

    public abstract void onSendTextMessage(String paramString);

    public abstract void onTypingStatusChanged(Client.Typing.Type paramType);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.ComposeMessageFragment
 * JD-Core Version:    0.6.2
 */