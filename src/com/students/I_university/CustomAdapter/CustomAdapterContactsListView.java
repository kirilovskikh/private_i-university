package com.students.I_university.CustomAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.students.I_university.Contacts.AsyncTaskDownloadImage;
import com.students.I_university.Helpers.ContactInfo;
import com.students.I_university.R;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kirilovskikh
 * Date: 19.07.13
 * Time: 2:50
 * To change this template use File | Settings | File Templates.
 */
public class CustomAdapterContactsListView extends ArrayAdapter<String>{

    private Context mContext;
    private String[] strings;
    private HashMap<Integer, ContactInfo> map;

    public CustomAdapterContactsListView(Context context, String[] s, HashMap<Integer, ContactInfo> map) {
        super(context, R.layout.list, s);
        this.mContext = context;
        this.strings = s;
        this.map = map;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    private int getItemViewType (String str) {
        return str == null ? 0 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        String phoneNumber = map.get(position).getPhoneNumber();
        return getItemViewType(phoneNumber);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view;

        if (convertView == null) {
            ViewGroup viewGroup;

            if (getItemViewType(position) == 1) {
                viewGroup = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.contact_with_phone, null);
                holder = new ViewHolderWithPhone((TextView)viewGroup.findViewById(R.id.textView), (ImageView) viewGroup.findViewById(R.id.imageView),
                        (ImageButton) viewGroup.findViewById(R.id.imageButton));
                viewGroup.setTag(holder);
            }
            else {
                viewGroup = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.contact_no_phone, null);
                holder = new ViewHolderNoPhone((TextView)viewGroup.findViewById(R.id.textView), (ImageView) viewGroup.findViewById(R.id.imageView));
                viewGroup.setTag(holder);
            }
            view = viewGroup;
        }

        else {
            view = convertView;

            if (getItemViewType(position) == 1)
                holder = (ViewHolderWithPhone) convertView.getTag();
            else
                holder = (ViewHolderNoPhone) convertView.getTag();
        }

        if (getItemViewType(position) == 1) {
            final String number = map.get(position).getPhoneNumber();

            holder.imageButton.setFocusable(false);
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callDialog(number);
                }
            });
        }

        holder.textView.setText(strings[position]);

        String url = map.get(position).getSmallImgUrl();

        AsyncTaskDownloadImage asyncTaskDownloadImage = new AsyncTaskDownloadImage(holder.imageView);
        asyncTaskDownloadImage.execute(url);

        return view;
    }

    private static class ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public ImageButton imageButton;
    }

    private static class ViewHolderWithPhone extends ViewHolder {

        public ViewHolderWithPhone (TextView textView, ImageView imageView, ImageButton imageButton) {
            this.textView = textView;
            this.imageView = imageView;
            this.imageButton = imageButton;
        }

    }

    private static class ViewHolderNoPhone extends ViewHolder {

        public ViewHolderNoPhone (TextView textView, ImageView imageView) {
            this.textView = textView;
            this.imageView = imageView;
        }

    }

    private void callDialog(final String number) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Внимание");
        builder.setMessage("Вы действительно хотите позвонить по номеру " + number + " ?");
        builder.setNegativeButton("Отмена", null);
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));
                mContext.startActivity(callIntent);
            }
        });
        builder.show();

    }

}
