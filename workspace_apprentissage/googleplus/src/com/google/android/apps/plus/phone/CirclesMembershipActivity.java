package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.CirclesMultipleSelectFragment;
import com.google.android.apps.plus.fragments.CirclesMultipleSelectFragment.OnCircleSelectionListener;
import com.google.android.apps.plus.fragments.EsFragmentActivity;
import java.util.ArrayList;
import java.util.Collections;

public class CirclesMembershipActivity extends EsFragmentActivity
  implements View.OnClickListener, CirclesMultipleSelectFragment.OnCircleSelectionListener
{
  private CirclesMultipleSelectFragment mFragment;
  private View mPositiveButton;

  private String getPersonId()
  {
    return getIntent().getExtras().getString("person_id");
  }

  private boolean isEmptySelectionAllowed()
  {
    return getIntent().getExtras().getBoolean("empty_selection_allowed", false);
  }

  protected final EsAccount getAccount()
  {
    return (EsAccount)getIntent().getParcelableExtra("account");
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.CONTACTS_CIRCLELIST;
  }

  public final void onAttachFragment(Fragment paramFragment)
  {
    if ((paramFragment instanceof CirclesMultipleSelectFragment))
    {
      this.mFragment = ((CirclesMultipleSelectFragment)paramFragment);
      this.mFragment.setCircleUsageType(2);
      this.mFragment.setNewCircleEnabled(true);
      this.mFragment.setPersonId(getPersonId());
      this.mFragment.setOnCircleSelectionListener(this);
    }
  }

  public final void onCircleSelectionChange()
  {
    if (isEmptySelectionAllowed())
      return;
    View localView;
    if ((this.mFragment != null) && (this.mPositiveButton != null))
    {
      localView = this.mPositiveButton;
      if (this.mFragment.getSelectedCircleIds().size() <= 0)
        break label50;
    }
    label50: for (boolean bool = true; ; bool = false)
    {
      localView.setEnabled(bool);
      break;
      break;
    }
  }

  public void onClick(View paramView)
  {
    int i = paramView.getId();
    if (i == R.id.ok)
    {
      ArrayList localArrayList1 = this.mFragment.getOriginalCircleIds();
      if (localArrayList1 != null)
      {
        ArrayList localArrayList2 = this.mFragment.getSelectedCircleIds();
        Collections.sort(localArrayList1);
        Collections.sort(localArrayList2);
        if (localArrayList1.equals(localArrayList2))
        {
          setResult(0);
          finish();
        }
        Intent localIntent = new Intent();
        localIntent.putExtra("person_id", getPersonId());
        localIntent.putExtra("display_name", getIntent().getExtras().getString("display_name"));
        localIntent.putExtra("original_circle_ids", localArrayList1);
        localIntent.putExtra("selected_circle_ids", localArrayList2);
        setResult(-1, localIntent);
        finish();
      }
    }
    while (true)
    {
      return;
      if (i == R.id.cancel)
        finish();
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.circle_selection_activity);
    setTitle(R.string.add_to_circles_dialog_title);
    this.mPositiveButton = findViewById(R.id.ok);
    this.mPositiveButton.setEnabled(isEmptySelectionAllowed());
    this.mPositiveButton.setOnClickListener(this);
    findViewById(R.id.cancel).setOnClickListener(this);
  }

  public void onResume()
  {
    super.onResume();
    if (!isIntentAccountActive())
      finish();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.CirclesMembershipActivity
 * JD-Core Version:    0.6.2
 */