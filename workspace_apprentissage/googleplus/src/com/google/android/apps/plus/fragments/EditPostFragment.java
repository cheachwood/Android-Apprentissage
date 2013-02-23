package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.api.ApiUtils;
import com.google.android.apps.plus.api.OzServerException;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.phone.EditPostActivity;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.SoftInput;
import com.google.android.apps.plus.views.MentionMultiAutoCompleteTextView;

public class EditPostFragment extends Fragment
  implements TextView.OnEditorActionListener
{
  private boolean mChanged;
  private Integer mEditRequestId;
  private MentionMultiAutoCompleteTextView mPostTextView;
  private EsServiceListener mServiceListener = new EsServiceListener()
  {
    public final void onEditActivity$63505a2b(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      if ((EditPostFragment.this.mEditRequestId != null) && (EditPostFragment.this.mEditRequestId.intValue() == paramAnonymousInt))
        EditPostFragment.this.handleEditPost(paramAnonymousServiceResult);
    }
  };

  private void handleEditPost(ServiceResult paramServiceResult)
  {
    this.mEditRequestId = null;
    FragmentActivity localFragmentActivity = getActivity();
    if (localFragmentActivity != null)
    {
      localFragmentActivity.dismissDialog(297895);
      if (!paramServiceResult.hasError())
        break label114;
      Exception localException = paramServiceResult.getException();
      if ((!(localException instanceof OzServerException)) || (((OzServerException)localException).getErrorCode() != 14))
        break label100;
      AlertFragmentDialog localAlertFragmentDialog = AlertFragmentDialog.newInstance(getString(R.string.post_not_sent_title), getString(R.string.post_restricted_mention_error), getString(R.string.ok), null);
      localAlertFragmentDialog.setTargetFragment(getTargetFragment(), 0);
      localAlertFragmentDialog.show(getFragmentManager(), "StreamPostRestrictionsNotSupported");
    }
    while (true)
    {
      return;
      label100: Toast.makeText(localFragmentActivity, R.string.edit_post_error, 0).show();
      continue;
      label114: localFragmentActivity.finish();
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      if (paramBundle.containsKey("edit_request_id"))
        this.mEditRequestId = Integer.valueOf(paramBundle.getInt("edit_request_id"));
      this.mChanged = paramBundle.getBoolean("changed", false);
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.edit_comment_fragment, paramViewGroup, false);
    this.mPostTextView = ((MentionMultiAutoCompleteTextView)localView.findViewById(R.id.text));
    Intent localIntent = getActivity().getIntent();
    EsAccount localEsAccount = (EsAccount)localIntent.getParcelableExtra("account");
    String str1 = localIntent.getStringExtra("activity_id");
    this.mPostTextView.init(this, localEsAccount, str1, null);
    String str2 = localIntent.getStringExtra("content");
    MentionMultiAutoCompleteTextView localMentionMultiAutoCompleteTextView = this.mPostTextView;
    if (str2 == null)
      str2 = "";
    localMentionMultiAutoCompleteTextView.setHtml(str2);
    this.mPostTextView.addTextChangedListener(new TextWatcher()
    {
      public final void afterTextChanged(Editable paramAnonymousEditable)
      {
        EditPostFragment.access$202(EditPostFragment.this, true);
        EditPostFragment.access$300(EditPostFragment.this);
      }

      public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
      }

      public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
      }
    });
    this.mPostTextView.setOnEditorActionListener(this);
    return localView;
  }

  public final void onDiscard()
  {
    SoftInput.hide(this.mPostTextView);
    EditPostActivity localEditPostActivity = (EditPostActivity)getActivity();
    if (this.mChanged)
      localEditPostActivity.showDialog(901235);
    while (true)
    {
      return;
      localEditPostActivity.setResult(0);
      localEditPostActivity.finish();
    }
  }

  public boolean onEditorAction(TextView paramTextView, int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramTextView == this.mPostTextView)
      switch (paramInt)
      {
      default:
      case 6:
      }
    for (boolean bool = false; ; bool = true)
    {
      return bool;
      SoftInput.hide(paramTextView);
    }
  }

  public final void onPause()
  {
    super.onPause();
    EsService.unregisterListener(this.mServiceListener);
  }

  public final void onPost()
  {
    SoftInput.hide(this.mPostTextView);
    EditPostActivity localEditPostActivity = (EditPostActivity)getActivity();
    if ((!this.mChanged) || (TextUtils.isEmpty(this.mPostTextView.getText())))
    {
      localEditPostActivity.setResult(0);
      localEditPostActivity.finish();
    }
    while (true)
    {
      return;
      Intent localIntent = localEditPostActivity.getIntent();
      EsAccount localEsAccount = (EsAccount)localIntent.getParcelableExtra("account");
      String str1 = localIntent.getStringExtra("activity_id");
      String str2 = ApiUtils.buildPostableString$6d7f0b14(this.mPostTextView.getText());
      boolean bool = localIntent.getBooleanExtra("reshare", false);
      localEditPostActivity.showDialog(297895);
      this.mEditRequestId = Integer.valueOf(EsService.editActivity(localEditPostActivity, localEsAccount, str1, str2, bool));
    }
  }

  public final void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mServiceListener);
    if ((this.mEditRequestId != null) && (!EsService.isRequestPending(this.mEditRequestId.intValue())))
      handleEditPost(EsService.removeResult(this.mEditRequestId.intValue()));
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mEditRequestId != null)
      paramBundle.putInt("edit_request_id", this.mEditRequestId.intValue());
    paramBundle.putBoolean("changed", this.mChanged);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.EditPostFragment
 * JD-Core Version:    0.6.2
 */