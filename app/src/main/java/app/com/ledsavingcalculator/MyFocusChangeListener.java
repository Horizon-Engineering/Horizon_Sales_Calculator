package app.com.ledsavingcalculator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


public class MyFocusChangeListener extends Activity implements View.OnFocusChangeListener {

    private int numberOfFixture;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberOfFixture = R.id.numberOfFixture;
    }

    /**
     * Called when the focus state of a view has changed.
     *
     * @param v        The view whose state has changed.
     * @param hasFocus The new focus state of v.
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if (v.getId() == R.id.numberOfFixture  && hasFocus) {

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


}