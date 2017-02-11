package upday.mvvm.ui;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import upday.mvvm.R;
import upday.mvvm.model.Country;

class LanguageSpinnerAdapter extends ArrayAdapter<Country> {

    LanguageSpinnerAdapter(@NonNull Context context,
                           @LayoutRes int resource,
                           @NonNull List<Country> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView);
    }

    @NonNull
    private View getCustomView(final int position, @Nullable View convertView) {
        ViewHolder holder;
        View view = convertView;

        if (view == null) {
            view = inflateView();
            TextView textView = (TextView) view.findViewById(R.id.country_item_text);
            holder = new ViewHolder(textView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Country country = getItem(position);
        assert country != null;
        holder.setText(country.getName());

        return view;
    }

    @NonNull
    private View inflateView() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.country_item, null);
    }

    private static class ViewHolder {

        @NonNull
        private final TextView mTextView;

        ViewHolder(@NonNull TextView textView) {
            mTextView = textView;
        }

        void setText(@NonNull String text) {
            mTextView.setText(text);
        }
    }
}
