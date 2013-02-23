package com.google.android.apps.plus.fragments;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData.ProfileAndContactData;
import com.google.android.apps.plus.phone.HostedFragment;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.views.EsImageView;
import com.google.api.services.plusi.model.GoogleReviewProto;
import com.google.api.services.plusi.model.PriceLevelProto;
import com.google.api.services.plusi.model.PriceLevelsProto;
import com.google.api.services.plusi.model.ZagatAspectRatingProto;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class WriteReviewFragment extends HostedFragment
  implements LoaderManager.LoaderCallbacks<EsPeopleData.ProfileAndContactData>, View.OnClickListener, RadioGroup.OnCheckedChangeListener
{
  private static final int[] BUCKETED_PRICE_DRAWABLES;
  private static final HashMap<Integer, String> sRatingValues;
  private static final HashMap<String, Integer> sRatingViews;
  private EsAccount mAccount;
  private EditText mAspectCost;
  private TextWatcher mAspectCostTextWatcher = new TextWatcher()
  {
    public final void afterTextChanged(Editable paramAnonymousEditable)
    {
    }

    public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
    }

    public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      WriteReviewFragment.this.mYourReview.price.valueDisplay = WriteReviewFragment.this.mAspectCost.getText().toString().trim();
    }
  };
  private LinearLayout mAspectRatings;
  private LinearLayout mBucketedPriceContainer;
  private RadioGroup mBucketedPriceGroup;
  private TextView mBucketedPriceTip;
  private RadioButton[] mBucketedPrices = new RadioButton[4];
  private TextView mBusinessAddress;
  private EsImageView mBusinessPhoto;
  private TextView mBusinessTitle;
  private Button mCancelButton;
  private String mCid;
  private LinearLayout mContinuousCostContainer;
  private TextView mCostCurrencySymbol;
  private TextView mCostExplanation;
  private TextView mCostLabel;
  private Integer mPendingDeleteRequestId;
  private Integer mPendingWriteRequestId;
  private String mPersonId;
  private TextView mPostingPubliclyNotice;
  private final HashMap<Integer, PriceLevelProto> mPriceLevels = new HashMap();
  private Button mPublishButton;
  private boolean mReviewExists;
  private final EsServiceListener mServiceListener = new ServiceListener((byte)0);
  private EditText mWriteReview;
  private TextWatcher mWriteReviewTextWatcher = new TextWatcher()
  {
    public final void afterTextChanged(Editable paramAnonymousEditable)
    {
    }

    public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
    }

    public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      WriteReviewFragment.this.mYourReview.fullText = WriteReviewFragment.this.mWriteReview.getText().toString().trim();
    }
  };
  private GoogleReviewProto mYourReview;

  static
  {
    int[] arrayOfInt = new int[4];
    arrayOfInt[0] = R.drawable.bucketed_price_one_coin;
    arrayOfInt[1] = R.drawable.bucketed_price_two_coins;
    arrayOfInt[2] = R.drawable.bucketed_price_three_coins;
    arrayOfInt[3] = R.drawable.bucketed_price_four_coins;
    BUCKETED_PRICE_DRAWABLES = arrayOfInt;
    sRatingViews = new HashMap();
    sRatingValues = new HashMap();
    sRatingViews.put("0", Integer.valueOf(R.id.aspect_rating_0));
    sRatingViews.put("1", Integer.valueOf(R.id.aspect_rating_1));
    sRatingViews.put("2", Integer.valueOf(R.id.aspect_rating_2));
    sRatingViews.put("3", Integer.valueOf(R.id.aspect_rating_3));
    sRatingValues.put(Integer.valueOf(R.id.aspect_rating_0), "0");
    sRatingValues.put(Integer.valueOf(R.id.aspect_rating_1), "1");
    sRatingValues.put(Integer.valueOf(R.id.aspect_rating_2), "2");
    sRatingValues.put(Integer.valueOf(R.id.aspect_rating_3), "3");
  }

  private void handleDeleteReviewCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mPendingDeleteRequestId == null) || (this.mPendingDeleteRequestId.intValue() != paramInt));
    while (true)
    {
      return;
      this.mPendingDeleteRequestId = null;
      DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("write_review_request_pending");
      if (localDialogFragment != null)
        localDialogFragment.dismiss();
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
      {
        Toast.makeText(getActivity(), R.string.delete_review_operation_failed, 0).show();
      }
      else
      {
        Toast.makeText(getActivity(), R.string.delete_review_operation_successful, 0).show();
        getActivity().finish();
      }
    }
  }

  private void handleWriteReviewCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mPendingWriteRequestId == null) || (this.mPendingWriteRequestId.intValue() != paramInt));
    while (true)
    {
      return;
      this.mPendingWriteRequestId = null;
      DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("write_review_request_pending");
      if (localDialogFragment != null)
        localDialogFragment.dismiss();
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
      {
        Toast.makeText(getActivity(), R.string.write_review_operation_failed, 0).show();
      }
      else
      {
        Toast.makeText(getActivity(), R.string.write_review_operation_successful, 0).show();
        getActivity().finish();
      }
    }
  }

  private void showProgressDialog(String paramString)
  {
    ProgressFragmentDialog.newInstance(null, paramString, false).show(getFragmentManager(), "write_review_request_pending");
  }

  private void updateBucketedPriceViews(PriceLevelsProto paramPriceLevelsProto)
  {
    this.mBucketedPriceContainer.setVisibility(0);
    this.mCostLabel.setText(paramPriceLevelsProto.labelDisplay);
    this.mCostExplanation.setText(getString(R.string.write_review_optional));
    int i = 0;
    this.mPriceLevels.clear();
    Iterator localIterator = paramPriceLevelsProto.priceLevel.iterator();
    if (localIterator.hasNext())
    {
      PriceLevelProto localPriceLevelProto = (PriceLevelProto)localIterator.next();
      RadioButton localRadioButton = this.mBucketedPrices[i];
      String str = localPriceLevelProto.labelDisplay;
      if (str != null)
        localRadioButton.setText(str);
      while (true)
      {
        if ((paramPriceLevelsProto.ratedValueId != null) && (paramPriceLevelsProto.ratedValueId.equals(localPriceLevelProto.valueId)))
          localRadioButton.setChecked(true);
        this.mPriceLevels.put(Integer.valueOf(localRadioButton.getId()), localPriceLevelProto);
        i++;
        break;
        localRadioButton.setBackgroundResource(BUCKETED_PRICE_DRAWABLES[i]);
      }
    }
  }

  private void updateZagatAspectViews(List<ZagatAspectRatingProto> paramList)
  {
    this.mAspectRatings.removeAllViews();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      ZagatAspectRatingProto localZagatAspectRatingProto = (ZagatAspectRatingProto)localIterator.next();
      LinearLayout localLinearLayout = (LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.write_review_aspect_rating, this.mAspectRatings, false);
      ((TextView)localLinearLayout.findViewById(R.id.aspect_label)).setText(localZagatAspectRatingProto.labelDisplay);
      this.mAspectRatings.addView(localLinearLayout);
      RadioGroup localRadioGroup = (RadioGroup)localLinearLayout.findViewById(R.id.aspect_rating_group);
      localRadioGroup.setOnCheckedChangeListener(this);
      localRadioGroup.setTag(localZagatAspectRatingProto);
      if (localZagatAspectRatingProto.valueDisplay != null)
        ((RadioButton)localLinearLayout.findViewById(((Integer)sRatingViews.get(localZagatAspectRatingProto.valueDisplay)).intValue())).setChecked(true);
    }
  }

  public final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.UNKNOWN;
  }

  public void onCheckedChanged(RadioGroup paramRadioGroup, int paramInt)
  {
    int i = paramRadioGroup.getId();
    if (i == R.id.aspect_rating_group)
      ((ZagatAspectRatingProto)paramRadioGroup.getTag()).valueDisplay = ((String)sRatingValues.get(Integer.valueOf(paramInt)));
    while (true)
    {
      return;
      if (i == R.id.bucketed_price_group)
      {
        this.mYourReview.priceLevel.ratedValueId = Long.valueOf(((PriceLevelProto)this.mPriceLevels.get(Integer.valueOf(paramInt))).valueId.longValue());
        int j = paramRadioGroup.getCheckedRadioButtonId();
        if (j != -1)
        {
          PriceLevelProto localPriceLevelProto = (PriceLevelProto)this.mPriceLevels.get(Integer.valueOf(j));
          this.mBucketedPriceTip.setVisibility(0);
          this.mBucketedPriceTip.setText(localPriceLevelProto.labelHintDisplay);
        }
        else
        {
          this.mBucketedPriceTip.setVisibility(8);
        }
      }
    }
  }

  public void onClick(View paramView)
  {
    int i = paramView.getId();
    int j;
    int k;
    if (i == R.id.publish_button)
    {
      j = 0;
      if (j < this.mAspectRatings.getChildCount())
      {
        int m = ((RadioGroup)this.mAspectRatings.getChildAt(j).findViewById(R.id.aspect_rating_group)).getCheckedRadioButtonId();
        k = 0;
        if (m == -1)
        {
          label56: if (k != 0)
            break label119;
          AlertDialog.Builder localBuilder = new AlertDialog.Builder(getActivity());
          localBuilder.setMessage(R.string.write_review_submit_warning);
          localBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
          {
            public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
              paramAnonymousDialogInterface.dismiss();
            }
          });
          localBuilder.show();
        }
      }
    }
    while (true)
    {
      return;
      j++;
      break;
      k = 1;
      break label56;
      label119: GoogleReviewProto localGoogleReviewProto = this.mYourReview;
      String str = this.mCid;
      showProgressDialog(getString(R.string.write_review_operation_pending));
      this.mPendingWriteRequestId = Integer.valueOf(EsService.writeReview(getActivity(), this.mAccount, this.mPersonId, localGoogleReviewProto, str));
      continue;
      if (i == R.id.cancel_button)
        getActivity().finish();
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    this.mAccount = ((EsAccount)localBundle.getParcelable("account"));
    this.mPersonId = localBundle.getString("person_id");
    if (paramBundle != null)
    {
      if (paramBundle.containsKey("write_review_request_id"))
        this.mPendingWriteRequestId = Integer.valueOf(paramBundle.getInt("write_review_request_id"));
      if (paramBundle.containsKey("delete_review_request_id"))
        this.mPendingDeleteRequestId = Integer.valueOf(paramBundle.getInt("delete_review_request_id"));
    }
    getLoaderManager().initLoader(1, null, this);
  }

  public final Loader<EsPeopleData.ProfileAndContactData> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    if (Log.isLoggable("WriteReviewFragment", 3))
      Log.d("WriteReviewFragment", "Loader<ProfileAndContactData> onCreateLoader()");
    return new ProfileLoader(getActivity(), this.mAccount, this.mPersonId, true);
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.write_review_fragment, paramViewGroup, false);
    this.mBusinessPhoto = ((EsImageView)localView.findViewById(R.id.business_photo));
    this.mBusinessTitle = ((TextView)localView.findViewById(R.id.business_title));
    this.mBusinessAddress = ((TextView)localView.findViewById(R.id.business_address));
    this.mWriteReview = ((EditText)localView.findViewById(R.id.write_review));
    this.mAspectRatings = ((LinearLayout)localView.findViewById(R.id.aspect_ratings));
    this.mContinuousCostContainer = ((LinearLayout)localView.findViewById(R.id.continuous_cost_container));
    this.mBucketedPriceContainer = ((LinearLayout)localView.findViewById(R.id.bucketed_price_container));
    this.mBucketedPriceTip = ((TextView)localView.findViewById(R.id.bucketed_price_tip));
    this.mCostLabel = ((TextView)localView.findViewById(R.id.cost_label));
    this.mCostExplanation = ((TextView)localView.findViewById(R.id.cost_explanation));
    this.mCostCurrencySymbol = ((TextView)localView.findViewById(R.id.cost_currency_symbol));
    this.mAspectCost = ((EditText)localView.findViewById(R.id.aspect_cost));
    this.mPostingPubliclyNotice = ((TextView)localView.findViewById(R.id.posting_publicly_text));
    this.mPublishButton = ((Button)localView.findViewById(R.id.publish_button));
    this.mCancelButton = ((Button)localView.findViewById(R.id.cancel_button));
    this.mBucketedPriceGroup = ((RadioGroup)localView.findViewById(R.id.bucketed_price_group));
    this.mBucketedPrices[0] = ((RadioButton)localView.findViewById(R.id.bucketed_price_1));
    this.mBucketedPrices[1] = ((RadioButton)localView.findViewById(R.id.bucketed_price_2));
    this.mBucketedPrices[2] = ((RadioButton)localView.findViewById(R.id.bucketed_price_3));
    this.mBucketedPrices[3] = ((RadioButton)localView.findViewById(R.id.bucketed_price_4));
    String str1 = getString(R.string.write_review_publish).toUpperCase();
    this.mPublishButton.setText(str1);
    this.mPublishButton.setOnClickListener(this);
    String str2 = getString(R.string.write_review_cancel).toUpperCase();
    this.mCancelButton.setText(str2);
    this.mCancelButton.setOnClickListener(this);
    this.mBucketedPriceGroup.setOnCheckedChangeListener(this);
    this.mAspectCost.addTextChangedListener(this.mAspectCostTextWatcher);
    this.mWriteReview.addTextChangedListener(this.mWriteReviewTextWatcher);
    return localView;
  }

  public final void onLoaderReset(Loader<EsPeopleData.ProfileAndContactData> paramLoader)
  {
  }

  public final boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == R.id.delete_review)
    {
      showProgressDialog(getString(R.string.delete_review_operation_pending));
      this.mPendingDeleteRequestId = Integer.valueOf(EsService.deleteReview(getActivity(), this.mAccount, this.mCid));
    }
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void onPause()
  {
    super.onPause();
    EsService.unregisterListener(this.mServiceListener);
  }

  public final void onPrepareOptionsMenu(Menu paramMenu)
  {
    if (this.mReviewExists)
      paramMenu.findItem(R.id.delete_review).setVisible(true);
  }

  public final void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mServiceListener);
    if ((this.mPendingWriteRequestId != null) && (!EsService.isRequestPending(this.mPendingWriteRequestId.intValue())))
    {
      ServiceResult localServiceResult2 = EsService.removeResult(this.mPendingWriteRequestId.intValue());
      handleWriteReviewCallback(this.mPendingWriteRequestId.intValue(), localServiceResult2);
    }
    if ((this.mPendingDeleteRequestId != null) && (!EsService.isRequestPending(this.mPendingDeleteRequestId.intValue())))
    {
      ServiceResult localServiceResult1 = EsService.removeResult(this.mPendingDeleteRequestId.intValue());
      handleDeleteReviewCallback(this.mPendingDeleteRequestId.intValue(), localServiceResult1);
    }
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mPendingWriteRequestId != null)
      paramBundle.putInt("write_review_request_id", this.mPendingWriteRequestId.intValue());
    if (this.mPendingDeleteRequestId != null)
      paramBundle.putInt("delete_review_request_id", this.mPendingDeleteRequestId.intValue());
  }

  public final void recordNavigationAction()
  {
  }

  private final class ServiceListener extends EsServiceListener
  {
    private ServiceListener()
    {
    }

    public final void onDeleteReviewComplete(int paramInt, ServiceResult paramServiceResult)
    {
      WriteReviewFragment.this.handleDeleteReviewCallback(paramInt, paramServiceResult);
    }

    public final void onWriteReviewComplete(int paramInt, ServiceResult paramServiceResult)
    {
      WriteReviewFragment.this.handleWriteReviewCallback(paramInt, paramServiceResult);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.WriteReviewFragment
 * JD-Core Version:    0.6.2
 */