package pxl.be.goevent;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by kimprzybylski on 8/11/17.
 */

public class CustonListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] name;
    private final String[] date;

    public CustonListAdapter(Activity context, String[] name, String[] date) {
        super(context, R.layout.list_item_event, name);
        this.context = context;
        this.name = name;
        this.date = date;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item_event, null, true);

        final ImageView imageView = rowView.findViewById(R.id.list_image);
        TextView nametxt = rowView.findViewById(R.id.list_name);
        TextView datetxt = rowView.findViewById(R.id.list_date);

        nametxt.setText(name[position]);
        datetxt.setText(date[position]);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        Log.e("name", name[position]);
        storageReference.child("images/"+name[position]+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri.toString()).into(imageView);
            }
        });

        return rowView;
    }
}
