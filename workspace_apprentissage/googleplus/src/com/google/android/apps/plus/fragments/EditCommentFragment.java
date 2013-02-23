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
import com.google.android.apps.plus.phone.EditCommentActivity;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.SoftInput;
import com.google.android.apps.plus.views.MentionMultiAutoCompleteTextView;

public class EditCommentFragment extends Fragment
  implements TextView.OnEditorActionListener
{
  protected boolean mChanged;
  protected String mCommentId;
  protected MentionMultiAutoCompleteTextView mCommentTextView;
  protected Integer mPendingRequestId;
  protected EsServiceListener mServiceListener = new EsServiceListener()
  {
    public final void onEditComment$51e3eb1f(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      if ((EditCommentFragment.this.mPendingRequestId != null) && (EditCommentFragment.this.mPendingRequestId.intValue() == paramAnonymousInt))
        EditCommentFragment.this.handleEditComment(paramAnonymousServiceResult);
    }

    public final void onEditPhotoCommentComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      if ((EditCommentFragment.this.mPendingRequestId != null) && (EditCommentFragment.this.mPendingRequestId.intValue() == paramAnonymousInt))
        EditCommentFragment.this.handleEditComment(paramAnonymousServiceResult);
    }
  };

  private void handleEditComment(ServiceResult paramServiceResult)
  {
    this.mPendingRequestId = null;
    FragmentActivity localFragmentActivity = getActivity();
    if (localFragmentActivity != null)
    {
      localFragmentActivity.dismissDialog(207893);
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
      label100: Toast.makeText(localFragmentActivity, R.string.comment_edit_error, 0).show();
      continue;
      label114: localFragmentActivity.finish();
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.mChanged = paramBundle.getBoolean("changed", false);
      if (paramBundle.containsKey("request_id"))
        this.mPendingRequestId = Integer.valueOf(paramBundle.getInt("request_id"));
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.edit_comment_fragment, paramViewGroup, false);
    this.mCommentTextView = ((MentionMultiAutoCompleteTextView)localView.findViewById(R.id.text));
    Intent localIntent = getActivity().getIntent();
    EsAccount localEsAccount = (EsAccount)localIntent.getParcelableExtra("account");
    String str = localIntent.getStringExtra("activity_id");
    this.mCommentId = localIntent.getStringExtra("comment_id");
    this.mCommentTextView.init(this, localEsAccount, str, null);
    if (paramBundle == null)
      this.mCommentTextView.setHtml(localIntent.getStringExtra("comment"));
    this.mCommentTextView.addTextChangedListener(new TextWatcher()
    {
      public final void afterTextChanged(Editable paramAnonymousEditable)
      {
        EditCommentFragment.this.mChanged = true;
        EditCommentFragment.access$100(EditCommentFragment.this);
      }

      public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
      }

      public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
      }
    });
    this.mCommentTextView.setOnEditorActionListener(this);
    return localView;
  }

  public final void onDiscard()
  {
    SoftInput.hide(this.mCommentTextView);
    EditCommentActivity localEditCommentActivity = (EditCommentActivity)getActivity();
    if (this.mChanged)
      localEditCommentActivity.showDialog(901234);
    while (true)
    {
      return;
      localEditCommentActivity.setResult(0);
      localEditCommentActivity.finish();
    }
  }

  public boolean onEditorAction(TextView paramTextView, int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramTextView == this.mCommentTextView)
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
    SoftInput.hide(this.mCommentTextView);
    EditCommentActivity localEditCommentActivity = (EditCommentActivity)getActivity();
    if ((!this.mChanged) || (TextUtils.isEmpty(this.mCommentTextView.getText())))
    {
      localEditCommentActivity.setResult(0);
      localEditCommentActivity.finish();
      return;
    }
    Intent localIntent = localEditCommentActivity.getIntent();
    EsAccount localEsAccount = (EsAccount)localIntent.getParcelableExtra("account");
    String str1 = localIntent.getStringExtra("activity_id");
    String str2 = ApiUtils.buildPostableString$6d7f0b14(this.mCommentTextView.getText());
    if (localIntent.hasExtra("photo_id"));
    for (this.mPendingRequestId = Integer.valueOf(EsService.editPhotoComment(localEditCommentActivity, localEsAccount, str1, this.mCommentId, str2)); ; this.mPendingRequestId = Integer.valueOf(EsService.editComment(localEditCommentActivity, localEsAccount, str1, this.mCommentId, str2)))
    {
      localEditCommentActivity.showDialog(207893);
      break;
    }
  }

  public final void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mServiceListener);
    if ((this.mPendingRequestId != null) && (!EsService.isRequestPending(this.mPendingRequestId.intValue())))
      handleEditComment(EsService.removeResult(this.mPendingRequestId.intValue()));
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("changed", this.mChanged);
    if (this.mPendingRequestId != null)
      paramBundle.putInt("request_id", this.mPendingRequestId.intValue());
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.EditCommentFragment
 * JD-Core Version:    0.6.2
 */